package com.perfectlimited.ezypay.activity.loginactivity;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.perfectlimited.ezypay.MyContentProvider;
import com.perfectlimited.ezypay.R;
 import com.perfectlimited.ezypay.activity.mainactivity.MainActivity;
import com.perfectlimited.ezypay.global.Global;
import com.perfectlimited.ezypay.model.ModelServerResponse;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class  FragmentLogin extends Fragment {

    boolean attach=false;
    Global global;
    SharedPreferences pref;
     @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attach=true;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
         outState.putString("editTextPin",editTextPin.getText().toString().trim());
        outState.putString("editTextPhoneNumber",editTextPhoneNumber.getText().toString().trim());

        outState.putBoolean("progressDialog",progressDialog.isShowing());

    }

    @Override
    public void onDetach() {
        super.onDetach();
        attach=false;
        progressDialog.dismiss();

        if(alertDialog!=null){
            alertDialog.dismiss();
        }

    }

    public FragmentLogin() {
        // Required empty public constructor
    }
    boolean imageDownloading;

    EditText editTextPin,editTextPhoneNumber;
    Button buttonLogin;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_login, container, false);
        setRetainInstance(true);
        attach=true;
        global= (Global) getActivity().getApplicationContext();
        pref=getActivity().getSharedPreferences(Global.SHAREDPREFSTRING,Context.MODE_PRIVATE);


        editTextPin= (EditText) rootView.findViewById(R.id.editTextPassword);
        editTextPhoneNumber= (EditText) rootView.findViewById(R.id.editTextPhoneNumber);
        buttonLogin= (Button) rootView.findViewById(R.id.buttonLogin);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);


        if(savedInstanceState!=null){
            if (savedInstanceState.getBoolean("progressDialog")){
                progressDialog.show();
                if(Global.DEBUG)   Log.e("h","if");
            }else{
                progressDialog.dismiss();
                if(Global.DEBUG)   Log.e("h","else");
            }

             editTextPin.setText(savedInstanceState.getString("editTextPin"));
            editTextPhoneNumber.setText(savedInstanceState.getString("editTextPhoneNumber"));
        }else{

         }

         buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                 String pin=editTextPin.getText().toString().trim();
                String phone=editTextPhoneNumber.getText().toString().trim();

//                try {
//                    pin=  Global.SHA1(pin);
                     loginServer(pin,phone);

//                } catch (NoSuchAlgorithmException|UnsupportedEncodingException e) {
//
//                    Toast.makeText(getActivity(), "Unable to login...", Toast.LENGTH_SHORT).show();
//
//                }

//                testlogin();


            }
        });

        checkIfButtonEnable();


        editTextPhoneNumber.addTextChangedListener(new TextWatcher() {
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

        editTextPin.addTextChangedListener(new TextWatcher() {
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

        return rootView;
    }

    private void loginServer(String   pin, String phoneNumber) {


        if(global.checkInternetConenction(getActivity())) {

            new CallServer().execute( pin,phoneNumber);
        }else{
            showAlert("Please connect to internet.");
        }
//testlogin();

    }

    private  class CallServer extends AsyncTask<String,String,ModelServerResponse>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();

        }

        @Override
        protected ModelServerResponse doInBackground(String... params) {
            // BEGIN_INCLUDE(get_inputstream)
             String pin=params[0];
            String phone=params[1];
            ModelServerResponse modelServerResponse=new ModelServerResponse();



                try {

                    String createdUrl=Global.getBaseUrl()+ Global.getApiName()+
                            "CashlessMerchantVerification?mobileNumber="+ URLEncoder.encode(phone, "UTF-8")+
                            "&pin="+URLEncoder.encode(pin, "UTF-8");
                    modelServerResponse= Global.callServer(createdUrl,getActivity());
                    if(modelServerResponse.getException()==null){

                        String response=modelServerResponse.getResponse();
                        String token="";
                        //parse

                        //save to db=============
                        String uuid_="";
//=============================================================
                    JSONObject jsonObject=new JSONObject(response);
                    String customerId = "";
                    String customerName = "";
                    String customerDescription="";
                    String photoData="";


                    if(jsonObject.getString("merchant").equals("null")){
                        modelServerResponse.setException(new Exception("Login failed"));

                    }else {
                        JSONArray jsonArray = jsonObject.getJSONArray("merchant");



                        if (jsonArray.length() != 0){

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jobj = jsonArray.getJSONObject(i);

                                customerId = jobj.getString("MerchantId");
                                customerName = jobj.getString("MerchantName");
                                customerDescription=jobj.getString("Description");
                  photoData= jobj.getString("photoData");
//                                photoData=getString(R.string.testimage);
                                if(Global.DEBUG)                  Log.e("photoData",photoData+"");


                                 break;
                            }

                            if (customerId.equals("null")||customerId.equals(""))
                                throw new  Exception("customer id null exception");

                        }else{
                            throw new Exception("Info array null exception");
                        }



//=============================



                    uuid_ = UUID.randomUUID().toString();


                    global.genkey(Global.ALIASLOGINEXISTS, getActivity());
                     global.genkey(Global.ALIASDBPASSWORD, getActivity());
                    if(Global.DEBUG)   Log.e("doInBackground","yes");

                    String keyLoginExist=  global.doEncription(Global.ALIASLOGINEXISTS,"true");
                     String keyDbpass=  global.doEncription(Global.ALIASDBPASSWORD,uuid_);
                    if(Global.DEBUG) Log.e("keyDbpass",keyDbpass+"   sssssss");
                    SharedPreferences.Editor editor=pref.edit();
                    editor.putString(Global.SHAREDPREFAL_ISALIAS_LOGIN,keyLoginExist);
                     editor.putString(Global.SHAREDPREFAL_DBPASS,keyDbpass);
                    editor.apply();





                    ContentValues cv=new ContentValues();
                    getActivity().getContentResolver().insert(
                            MyContentProvider.URI_CHANGE_DBPASS, cv);

                    cv=new ContentValues();
                    cv.put(MyContentProvider.COLUMN_CUSTOMER_PRIMARY,1);
                    cv.put(MyContentProvider.COLUMN_CUSTOMER_ID,customerId);
                    cv.put(MyContentProvider.COLUMN_CUSTOMER_NAME,customerName);
                    cv.put(MyContentProvider.COLUMN_CUSTOMER_TOKEN,"someToken");
                    cv.put(MyContentProvider.COLUMN_CUSTOMER_MOBILE,phone);
                        cv.put(MyContentProvider.COLUMN_CUSTOMER_DESCRIPTION,customerDescription);



                    getActivity().getContentResolver().insert(
                            MyContentProvider.URI_INSERT_USERDETAILS, cv);

                    cv=new ContentValues();
                    cv.put(MyContentProvider.COLUMN_PIN_PIN,pin);
                    cv.put(MyContentProvider.COLUMN_PIN_CURRENT_PIN,1);

                    getActivity().getContentResolver().insert(
                            MyContentProvider.URI_INSERT_PIN, cv);

                    }



                    FileOutputStream out = null;
                    try {
                         byte[] decodedString = Base64.decode(photoData, Base64.DEFAULT);
                        Bitmap bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                        String path=getActivity().getFilesDir().toString().concat("/").concat(Global.PROFILE_PIC_NAME);
                        File file=new File(path);
                        file.createNewFile();
                        out = new FileOutputStream(path);
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
                        // PNG is a lossless format, the compression factor (100) is ignored
                        if (Global.DEBUG) Log.e("bitmapc", "created");
                    } catch (Exception e) {
                        if (Global.DEBUG) Log.e("exxxxxx", e.toString());
                    } finally {
                        try {
                            if (out != null) {
                                out.close();
                            }
                        } catch (IOException e) {
                            if (Global.DEBUG) Log.e("exxxxxxxxxxasdf", e.toString());
                        }
                    }




//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Picasso.with(getActivity())
//                                    .load("https://lh4.googleusercontent.com/-v0soe-ievYE/AAAAAAAAAAI/AAAAAAADpsQ/2_LasUaYkYU/s0-c-k-no-ns/photo.jpg")
//                                    .into(target);
//                        }
//                    });
//                    imageDownloading=true;
//
//                    while (imageDownloading){
//
//                    }
                }
                }catch (Exception e){
                    if(Global.DEBUG) Log.e("Exceptionddd",e.toString()+"");

                    try {

                        String keyLoginExist = global.doEncription(Global.ALIASLOGINEXISTS, "false");
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(Global.SHAREDPREFAL_ISALIAS_LOGIN, keyLoginExist);
                        editor.apply();


                    }catch (Exception e1){
                        if(Global.DEBUG)      Log.e("Exception",e1.toString()+"");

                    }



                    modelServerResponse.setException(e);
                 }




return modelServerResponse;
         }


          @Override
        protected void onPostExecute(ModelServerResponse modelServerResponse) {
            super.onPostExecute(modelServerResponse);

//            if(Global.DEBUG)   Log.e("Response",modelServerResponse.getResponse()+"");


            if(modelServerResponse.getException()!=null){
                showAlert("Login failed");

            }else{


                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();


              }



            progressDialog.dismiss();

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



    private void checkIfButtonEnable() {
         String pin=editTextPin.getText().toString().trim();
        String phone=editTextPhoneNumber.getText().toString().trim();

        if(pin.equals("")){
            buttonLogin.setAlpha(0.5f);
            buttonLogin.setClickable(false);
        }else if(phone.equals("")){
            buttonLogin.setAlpha(0.5f);
            buttonLogin.setClickable(false);
        }
        else if(phone.length()!=10){
            buttonLogin.setAlpha(0.5f);
            buttonLogin.setClickable(false);
        }else{
            buttonLogin.setAlpha(1.0f);
            buttonLogin.setClickable(true);
        }

    }

    Target target = new Target()
    {

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            if(Global.DEBUG)   Log.e("onBitmapLoaded","onBitmapLoaded");

            try {
                String path=getActivity().getFilesDir().toString().concat("/").concat(Global.PROFILE_PIC_NAME);
                File fileProfile=new File(path);

                File filesDir=getActivity().getFilesDir();

                if (!filesDir.exists()) {
                    if(Global.DEBUG)  Log.e("filsdir","not exist");
                    boolean dirCreated=filesDir.mkdirs();
                    if(Global.DEBUG)  Log.e("dirCreated",dirCreated+"");
                }

                FileOutputStream out = new FileOutputStream(fileProfile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

                out.flush();
                out.close();
                if(Global.DEBUG) Log.e("onBitmapLoaded f","f");
                imageDownloading=false;

            } catch(Exception e){
                // some action

                if(Global.DEBUG)  Log.e("exceptiossn",e.toString()+"");
                imageDownloading=false;

            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            if(Global.DEBUG)   Log.e("onBitmapFailed","onBitmapFailed");
            imageDownloading=false;


        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            if(Global.DEBUG) Log.e("onPrepareLoad","onPrepareLoad");
//            imageDownloading=false;


        }


    };

}
