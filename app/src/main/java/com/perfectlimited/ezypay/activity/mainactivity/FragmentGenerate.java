package com.perfectlimited.ezypay.activity.mainactivity;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.perfectlimited.ezypay.MyContentProvider;
import com.perfectlimited.ezypay.R;
import com.perfectlimited.ezypay.activity.messageshowactivity.MessageShowActivity;
import com.perfectlimited.ezypay.global.Global;
import com.perfectlimited.ezypay.model.ModelServerResponse;

import java.net.URLEncoder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGenerate extends Fragment {


    public FragmentGenerate() {
        // Required empty public constructor
    }

    @Override
    public void onDetach() {
        super.onDetach();
        attatch=false;
        if(alertDialog!=null){
            alertDialog.dismiss();
        }

        if(alertDialogConfirm!=null){
            alertDialogConfirm.dismiss();
        }


        progressDialog.dismiss();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attatch=true;
    }
    boolean attatch;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        outState.putString("editTextPhoneGenerate",editTextPhoneGenerate.getText().toString().trim());


        outState.putBoolean("progressDialog",progressDialog.isShowing());

    }

    EditText editTextPhoneGenerate;
    AlertDialog alertDialog,alertDialogConfirm;
    ProgressDialog progressDialog;
    Button buttonGen;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_generate, container, false);
        setRetainInstance(true);
        editTextPhoneGenerate= (EditText) rootView.findViewById(R.id.editTextPhoneGenerate);
        buttonGen= (Button) rootView.findViewById(R.id.buttonGen);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);


        if(savedInstanceState!=null){

            editTextPhoneGenerate.setText(savedInstanceState.getString("editTextPhoneGenerate"));

             if (savedInstanceState.getBoolean("progressDialog")){
                progressDialog.show();
            }else{
                progressDialog.dismiss();
            }

        }else{
//run for first time
        }
        buttonGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mobileNum=editTextPhoneGenerate.getText().toString().trim();
                if(mobileNum.equals("")){
                    Global.showAlert("Enter mobile number",getActivity(),alertDialog);

                }else if(mobileNum.length()!=10){
                    Global.showAlert("Enter 10 digit mobile number",getActivity(),alertDialog);

                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("Please confirm");
                    builder.setMessage("Mobile : "+mobileNum);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            new AsyncGenerateEzypayIdServer().execute(mobileNum);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialogConfirm=builder.create();
                    alertDialogConfirm.show();

                }
            }
        });
        return rootView;
    }
    private class AsyncGenerateEzypayIdServer extends AsyncTask<String,String,ModelServerResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }



        @Override
        protected ModelServerResponse doInBackground(String... strings) {
            ModelServerResponse modelServerResponse=new ModelServerResponse();

            String cusMobileNo=strings[0];
            String userId="";
            String phone="";
            try{

                userId=  Global.getFromCustomer(MyContentProvider.COLUMN_CUSTOMER_ID);
                phone=Global.getFromCustomer(MyContentProvider.COLUMN_CUSTOMER_MOBILE);



                String createdUrl=Global.getBaseUrl()+ Global.getApiName()+
                        "MerchantCustomerRegistration?" +
                        "merchantId="+URLEncoder.encode(userId, "UTF-8")+
                        "&CusMobileNumber="+URLEncoder.encode(cusMobileNo, "UTF-8")+
                        "&reqdate="+URLEncoder.encode(Global.getTime(), "UTF-8")+
                        "&merchantMobileNo="+URLEncoder.encode(phone, "UTF-8");


                modelServerResponse=Global.callServer(createdUrl,getActivity());
//


                if(modelServerResponse.getException()==null){
                    String response=modelServerResponse.getResponse();
                    if (response.equals("true")){
                        modelServerResponse.setException(null);
                    }else if (response.equals("false")){
                        throw new Exception("Response false exception");
                    }else{
                        throw new Exception("Response unknown exception");
                    }



                }
            } catch (Exception e) {

                if(Global.DEBUG)    Log.e("Exception",e.toString()+"");
                modelServerResponse.setException(e);
            }
            return modelServerResponse;


        }
        @Override
        protected void onPostExecute(ModelServerResponse modelServerResponse) {
            progressDialog.dismiss();

            if(modelServerResponse.getException()==null){

                Intent intent=new Intent(getActivity(), MessageShowActivity.class);
                intent.putExtra(Global.MESSAGEPASS,"eZypay id generated successfully");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);



            }else{

                Intent intent=new Intent(getActivity(), MessageShowActivity.class);
                intent.putExtra(Global.MESSAGEPASS,"eZypay id generation failed");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }

        }
    }


}
