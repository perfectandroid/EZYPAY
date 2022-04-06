package com.perfectlimited.ezypay.activity.mainactivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
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
public class FragmentChangePin extends Fragment {


    public FragmentChangePin() {
        // Required empty public constructor
    }


    @Override
    public void onDetach() {
        super.onDetach();
        attatch=false;
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attatch=true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("editText_pin",editText_pin.getText().toString().trim());
        outState.putString("editText_confirm_pin",editText_confirm_pin.getText().toString().trim());
        outState.putParcelable("progressDialog",progressDialog.onSaveInstanceState());


    }

    Global global;
    SharedPreferences pref;

    boolean attatch;
    EditText editText_pin,editText_confirm_pin,editText_current_pin;
    Button button_pin;

    ProgressDialog progressDialog ;
    AlertDialog alertDialog;

    boolean isShowSnackBar;

    CoordinatorLayout CoordinatorLayoutFragmentChangePin;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_change_pin, container, false);
        setRetainInstance(true);
        attatch=true;
        global= (Global) getActivity().getApplicationContext();
        pref=getActivity().getSharedPreferences(Global.SHAREDPREFSTRING,Context.MODE_PRIVATE);
        CoordinatorLayoutFragmentChangePin= (CoordinatorLayout) rootView.findViewById(R.id.CoordinatorLayoutChangePin);
         if(!isShowSnackBar) {

//            Snackbar.make(CoordinatorLayoutFragmentChangePin, "Enter atleast four number", Snackbar.LENGTH_INDEFINITE).
//                    setAction("OK", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            isShowSnackBar=true;
//                        }
//                    }).
//                    show();
        }

        editText_pin= (EditText) rootView.findViewById(R.id.editText_pin);
        editText_confirm_pin= (EditText) rootView.findViewById(R.id.editText_confirm_pin);
        button_pin= (Button) rootView.findViewById(R.id.button_pin);
        editText_current_pin= (EditText) rootView.findViewById(R.id.editText_current_pin);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);




        if(savedInstanceState!=null){

            editText_pin.setText(savedInstanceState.getString("editText_pin"));
            editText_confirm_pin.setText(savedInstanceState.getString("editText_confirm_pin"));
            progressDialog.onRestoreInstanceState(savedInstanceState.getBundle("progressDialog"));

        }else{

        }

        button_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String  pinConfirm=editText_confirm_pin.getText().toString().trim();
                String pinCurrent=editText_current_pin.getText().toString().trim();
                String pin=editText_pin.getText().toString().trim();

//                if(pin.length()<4||pinConfirm.length()<4){
//                    showAlert("Enter atleast four number");
//
 if(!pin.equals(pinConfirm)){
//
                   showAlert("Please confirm new pin");
//
//
                     }else{
                    new AsyncChangePinServer().execute(pinConfirm,pinCurrent);

              }

            }
        });


        checkIfButtonEnable();


        editText_pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkIfButtonEnable();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText_confirm_pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkIfButtonEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText_current_pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkIfButtonEnable();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return rootView;
    }


    private class AsyncChangePinServer extends AsyncTask<String,String,ModelServerResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ModelServerResponse modelServerResponse) {
            progressDialog.dismiss();

            if(modelServerResponse.getException()==null){

                Intent intent=new Intent(getActivity(), MessageShowActivity.class);
                intent.putExtra(Global.MESSAGEPASS,"Pin changed successfully");
                intent.putExtra("pinChangeSuccess",true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);



            }else{

                Intent intent=new Intent(getActivity(), MessageShowActivity.class);
                intent.putExtra(Global.MESSAGEPASS,"Pin changing failed");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }

        }

        @Override
        protected ModelServerResponse doInBackground(String... strings) {
            ModelServerResponse modelServerResponse=new ModelServerResponse();

            String newPin=strings[0];
            String currentPin=strings[1];

            String userId="";
            String token="";
            String phone="";
            try{

                userId=  Global.getFromCustomer(MyContentProvider.COLUMN_CUSTOMER_ID);
                token=  Global.getFromCustomer(MyContentProvider.COLUMN_CUSTOMER_TOKEN);
                phone=Global.getFromCustomer(MyContentProvider.COLUMN_CUSTOMER_MOBILE);



                String createdUrl=Global.getBaseUrl()+ Global.getApiName()+
                        "CashlessMerchantChangePIN?merchantId="+
                        URLEncoder.encode(userId, "UTF-8")+
                        "&oldPin="+URLEncoder.encode(currentPin, "UTF-8")+"&newPin="+
                        URLEncoder.encode(newPin, "UTF-8")+"&transDate="+ URLEncoder.encode(Global.getTime(), "UTF-8")+
                        "&merchantMobileNo="+URLEncoder.encode(phone, "UTF-8");


                modelServerResponse=Global.callServer(createdUrl,getActivity());
//


                if(modelServerResponse.getException()==null){
                    String response=modelServerResponse.getResponse();
                    if (response.equals("true")){


                        ContentValues cv=new ContentValues();
                        cv.put(MyContentProvider.COLUMN_PIN_PIN,newPin);

                        String[] passProj = {newPin,currentPin};

                        getActivity().getContentResolver().update(MyContentProvider.URI_CHANGE_PIN,
                                cv,null,passProj);

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
    }

    private void checkIfButtonEnable() {
        String username=editText_pin.getText().toString().trim();
        String password=editText_confirm_pin.getText().toString().trim();
        String current_pass=editText_current_pin.getText().toString().trim();


        if(password.equals("")||username.equals("")||current_pass.equals("")

                ){



            button_pin.setAlpha(0.5f);
            button_pin.setClickable(false);


        }else{


                button_pin.setAlpha(1.0f);
                button_pin.setClickable(true);



        }

    }

    public void showAlert(String message){
        alertDialog=createAlert(message).create();
        alertDialog.show();
    }

    public AlertDialog.Builder createAlert(String message){
        android.support.v7.app.AlertDialog.Builder alertdialog=
                new android.support.v7.app.AlertDialog.Builder(getActivity());
        alertdialog.setMessage(message);
        alertdialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        return alertdialog;
    }

}
