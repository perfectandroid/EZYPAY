package com.perfectlimited.ezypay.activity.mainactivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.perfectlimited.ezypay.MyContentProvider;
import com.perfectlimited.ezypay.R;
import com.perfectlimited.ezypay.activity.messageshowactivity.MessageShowActivity;
import com.perfectlimited.ezypay.activity.otpactivity.OtpActivity;
import com.perfectlimited.ezypay.global.Global;
import com.perfectlimited.ezypay.model.ModelServerResponse;
import com.perfectlimited.ezypay.model.parse.ModelParseReceive;

import java.net.URLEncoder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentReceive extends Fragment {


    public FragmentReceive() {
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

        outState.putString("editTextAmount",editTextAmount.getText().toString().trim());
        outState.putString("editTextId",editTextId.getText().toString().trim());
        outState.putString("editTextRemark",editTextRemark.getText().toString().trim());
        outState.putString("editTextPhone",editTextPhone.getText().toString().trim());


        outState.putBoolean("progressDialog",progressDialog.isShowing());

    }

    EditText editTextAmount,editTextId,editTextPhone,editTextRemark;
    Button buttonContinue;
    AlertDialog alertDialog,alertDialogConfirm;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_receive, container, false);
        setRetainInstance(true);
        attatch=true;

        editTextPhone= (EditText) rootView.findViewById(R.id.editTextPhone);

        editTextAmount= (EditText) rootView.findViewById(R.id.editTextAmount);
        editTextId= (EditText) rootView.findViewById(R.id.editTextId);
        editTextRemark= (EditText) rootView.findViewById(R.id.editTextRemark);
        buttonContinue= (Button) rootView.findViewById(R.id.buttonContinue);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);


        if(savedInstanceState!=null){

            editTextAmount.setText(savedInstanceState.getString("editTextAmount"));
            editTextId.setText(savedInstanceState.getString("editTextId"));
            editTextRemark.setText(savedInstanceState.getString("editTextRemark"));
            editTextPhone.setText(savedInstanceState.getString("editTextPhone"));



            if (savedInstanceState.getBoolean("progressDialog")){
                progressDialog.show();
             }else{
                progressDialog.dismiss();
             }

        }else{

        }

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String amount=editTextAmount.getText().toString().trim();
                final String id=editTextId.getText().toString().trim();
                final String phoneNumber=editTextPhone.getText().toString().trim();
                final String remark=editTextRemark.getText().toString().trim();

                if(id.equals("")){
                    Global.showAlert("Enter ezypay id",getActivity(),alertDialog);
                }
//                else if(id.length()!=6){
//                    Global.showAlert("Enter 6 ezypay id",getActivity(),alertDialog);
//
//                }
                else  if(phoneNumber.equals("")){
                    Global.showAlert("Enter mobile number",getActivity(),alertDialog);

                }else if(phoneNumber.length()!=10){
                    Global.showAlert("Enter 10 digit mobile number",getActivity(),alertDialog);

                }
                else  if(amount.equals("")){
                    Global.showAlert("Enter amount",getActivity(),alertDialog);

                }else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("Please confirm");
                    builder.setMessage("Id: "+id+
                            "\n"+"Mobile: "+phoneNumber+"\n"+"Amount: "+amount+"\n"
                    +"Reference: "+remark);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            new AsyncReceiveServer().execute(amount,id,phoneNumber,remark);
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
    private class AsyncReceiveServer extends AsyncTask<String,String,ModelServerResponse>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();

        }

        @Override
        protected ModelServerResponse doInBackground(String... strings) {
            ModelServerResponse modelServerResponse=new ModelServerResponse();
            String amount=strings[0];
            String customerId=strings[1];
            String phoneNumber=strings[2];
            String remarks=strings[3];

            if(remarks.equals(""))remarks="NILL";

            String userId="";
            String token="";
            String phone="";

             try{

                 userId=  Global.getFromCustomer(MyContentProvider.COLUMN_CUSTOMER_ID);
                 token=  Global.getFromCustomer(MyContentProvider.COLUMN_CUSTOMER_TOKEN);
                 phone=Global.getFromCustomer(MyContentProvider.COLUMN_CUSTOMER_MOBILE);



                  String createdUrl=Global.getBaseUrl()+ Global.getApiName()+
                         "CashlessCustomerOTPGeneration?MerchantId="+
                          URLEncoder.encode(userId, "UTF-8")+
                          "&cusMobileNumber="
                          + URLEncoder.encode(phoneNumber, "UTF-8")+
                          "&cusRegitrstionId="+
                          URLEncoder.encode(customerId, "UTF-8") +
                          "&transDate="+
                          URLEncoder.encode(Global.getTime(), "UTF-8")
                          +"&merchantMobileNo="+URLEncoder.encode(phone, "UTF-8")
                          ;

            modelServerResponse=Global.callServer(createdUrl,getActivity());



            if(modelServerResponse.getException()==null){
                String response=modelServerResponse.getResponse();
                if(!response.equals("true"))
                    throw new Exception("Otp initialzation exception 1");


                //parse response here
                ModelParseReceive modelParseReceive=new ModelParseReceive();
                modelParseReceive.setAmount(amount);
                modelParseReceive.setCustomerId(customerId);
                modelParseReceive.setPhoneNumber(phoneNumber);
                modelParseReceive.setRemarks(remarks);
                modelServerResponse.setModelParseReceive(modelParseReceive);

            }else{

            }

            } catch (Exception e) {

                modelServerResponse.setException(e);
            }
            return modelServerResponse;
        }

        @Override
        protected void onPostExecute(ModelServerResponse modelServerResponse) {
            super.onPostExecute(modelServerResponse);

            if(modelServerResponse.getException()==null){

                String amount=modelServerResponse.getModelParseReceive().getAmount();
                String customerId=modelServerResponse.getModelParseReceive().getCustomerId();
                String phoneNumber=modelServerResponse.getModelParseReceive().getPhoneNumber();
                String remarks=modelServerResponse.getModelParseReceive().getRemarks();

                Intent intent=new Intent(getActivity(), OtpActivity.class);
                intent.putExtra("amount",amount+"");
                intent.putExtra("customerId",customerId+"");
                intent.putExtra("phoneNumber",phoneNumber+"");
                intent.putExtra("remarks",remarks+"");

                startActivity(intent);


            }else{


                if(modelServerResponse.getException().getMessage().contains("Otp initialzation exception 1")){
                    Intent intent=new Intent(getActivity(), MessageShowActivity.class);
                    intent.putExtra(Global.MESSAGEPASS,"Incorrect detail is entered");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }else{
                    Intent intent=new Intent(getActivity(), MessageShowActivity.class);
                    intent.putExtra(Global.MESSAGEPASS,"OTP initialization failed");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }


            }

        progressDialog.dismiss();
        }
    }

}
