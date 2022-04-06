package com.perfectlimited.ezypay.activity.otpactivity;


import android.app.ProgressDialog;
import android.content.Context;
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
import com.perfectlimited.ezypay.activity.messageshowactivity.TransactionMessageActivity;
import com.perfectlimited.ezypay.global.Global;
import com.perfectlimited.ezypay.model.ModelServerResponse;
import com.perfectlimited.ezypay.model.parse.ModelParseOtp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOtp extends Fragment {


    public FragmentOtp() {
        // Required empty public constructor
    }

    @Override
    public void onDetach() {
        super.onDetach();
        attatch=false;
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
        progressDialog.dismiss();

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

         outState.putString("editTextOtp",editTextOtp.getText().toString().trim());
        outState.putBoolean("progressDialog",progressDialog.isShowing());

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attatch=true;
    }
    boolean attatch;

    String amount,customerId,phoneNumber,remarks;
    EditText editTextOtp;
    Button buttonOtp;
    AlertDialog alertDialog;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_otp, container, false);
        setRetainInstance(true);
        amount=getArguments().getString("amount");
        customerId=getArguments().getString("customerId");
        phoneNumber=getArguments().getString("phoneNumber");
        remarks=getArguments().getString("remarks");



         editTextOtp= (EditText) rootView.findViewById(R.id.editTextOtp);
        buttonOtp = (Button) rootView.findViewById(R.id.buttonOtp);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);


        if(savedInstanceState!=null){

             editTextOtp.setText(savedInstanceState.getString("editTextOtp"));
            if (savedInstanceState.getBoolean("progressDialog")){
                progressDialog.show();
            }else{
                progressDialog.dismiss();
            }

        }else{

        }
        buttonOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String otp=editTextOtp.getText().toString().trim();

                if(otp.equals("")){
                    Global.showAlert("Enter OTP",getActivity(),alertDialog);

                }else {
                    new AsyncOtpServer().execute(otp);
                }

            }
        });

        return rootView;
    }
    private class AsyncOtpServer extends AsyncTask<String,String,ModelServerResponse> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();

        }

        @Override
        protected ModelServerResponse doInBackground(String... strings) {
            ModelServerResponse modelServerResponse=new ModelServerResponse();

           String otp=strings[0];
             String userId="";
             String token="";
            String  phone="";
             try {

                 userId=  Global.getFromCustomer(MyContentProvider.COLUMN_CUSTOMER_ID);
                 token=  Global.getFromCustomer(MyContentProvider.COLUMN_CUSTOMER_TOKEN);
                 phone=Global.getFromCustomer(MyContentProvider.COLUMN_CUSTOMER_MOBILE);


           String createdUrl=Global.getBaseUrl()+
                   Global.getApiName()+
                   "CashlessMerchantTransaction?merchantId="+
                   URLEncoder.encode(userId, "UTF-8")+
                   "&cusMobileNumber="+
                   URLEncoder.encode(phoneNumber, "UTF-8")+
                   "&cusRegitrstionId="+
                   URLEncoder.encode(customerId, "UTF-8")+
                   "&otp="+
                   URLEncoder.encode(otp, "UTF-8")+"&transAmount="+
                   URLEncoder.encode(amount, "UTF-8")  +
                   "&transDate="+URLEncoder.encode(Global.getTime(), "UTF-8")
                   +"&merchantRemarks="+
                   URLEncoder.encode(remarks, "UTF-8")+"&merchantMobileNo="+
                   URLEncoder.encode(phone, "UTF-8") ;;


            modelServerResponse=Global.callServer(createdUrl,getActivity());



            if(modelServerResponse.getException()==null){
                String response=modelServerResponse.getResponse();


                JSONObject jsonObject=new JSONObject(response);

                String message="";
                 String balance="";
                String reponseCode="";

                if(jsonObject.getString("merchantTrans").equals("null")){
                    modelServerResponse.setException(new Exception("Merchant transaction null exception"));

                }else {
                    JSONArray jsonArray = jsonObject.getJSONArray("merchantTrans");


                    if (jsonArray.length() != 0) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobj = jsonArray.getJSONObject(i);


                            message=jobj.getString("message");

                            balance=jobj.getString("balance");
                            reponseCode=jobj.getString("response");

                            break;
                        }


                    } else {
                        throw new Exception("Info array null");
                    }

                }


 
                    //parse response here
                ModelParseOtp modelParseOtp=new ModelParseOtp();
                modelParseOtp.setBalance (balance);
                modelParseOtp.setMessage (message);
                if(Global.DEBUG)   Log.e("message get",message+"ss");
                modelParseOtp.setReponseCode(reponseCode);

                modelServerResponse.setModelParseOtp(modelParseOtp);
            }

             } catch (Exception e) {


                 if(Global.DEBUG)   Log.e("exception",e.toString()+"");

                 modelServerResponse.setException(e);

             }

            return modelServerResponse;
        }

        @Override
        protected void onPostExecute(ModelServerResponse modelServerResponse) {
            super.onPostExecute(modelServerResponse);

             if(modelServerResponse.getException()==null){



                 String message=modelServerResponse.getModelParseOtp().getMessage();;
                 String responseCode=modelServerResponse.getModelParseOtp().getReponseCode();


                 String balance=modelServerResponse.getModelParseOtp().getBalance();
//                 String finalmessage=message+"\n"+"Balance: "+balance+"\n"
//                         +"Bill no: "+remarks;

                 Intent intent=new Intent(getActivity(), TransactionMessageActivity.class);
                 intent.putExtra("responseCode",responseCode+"");
                 intent.putExtra("message",message+"");
                 intent.putExtra("balance",balance+"");
                 intent.putExtra("amount",amount+"");
                 intent.putExtra("remarks",remarks+"");
                 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);


            }else{
                Intent intent=new Intent(getActivity(), MessageShowActivity.class);
                intent.putExtra(Global.MESSAGEPASS,"Transaction failed");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            progressDialog.dismiss();
        }
    }
}
