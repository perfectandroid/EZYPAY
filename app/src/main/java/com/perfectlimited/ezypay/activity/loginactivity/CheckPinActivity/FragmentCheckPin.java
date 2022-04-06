package com.perfectlimited.ezypay.activity.loginactivity.CheckPinActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.perfectlimited.ezypay.activity.mainactivity.MainActivity;
import com.perfectlimited.ezypay.global.Global;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCheckPin extends Fragment {


    public FragmentCheckPin() {
        // Required empty public constructor
    }

    String pin="";

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
        outState.putParcelable("progressDialog",progressDialog.onSaveInstanceState());


    }

    Global global;
    SharedPreferences pref;

    boolean attatch;
    EditText editText_pin;
    Button button_pin;


    ProgressDialog progressDialog ;
    AlertDialog alertDialog;

int i;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_check_pin, container, false);
        setRetainInstance(true);
        attatch=true;
        global= (Global) getActivity().getApplicationContext();
        pref=getActivity().getSharedPreferences(Global.SHAREDPREFSTRING,Context.MODE_PRIVATE);


        editText_pin= (EditText) rootView.findViewById(R.id.editText_pin);
//        editText_pin.setImeActionLabel("Login", KeyEvent.KEYCODE_ENTER);
        button_pin= (Button) rootView.findViewById(R.id.button_pin);


        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Logging in,Please wait..");
        progressDialog.setCancelable(false);

        i=0;


        if(savedInstanceState!=null){

            editText_pin.setText(savedInstanceState.getString("editText_pin"));
             progressDialog.onRestoreInstanceState(savedInstanceState.getBundle("progressDialog"));

        }else{

        }

        button_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pin=editText_pin.getText().toString().trim();
                new AsyncEnterPin().execute(pin);


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


//        editText_pin.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                // If the event is a key-down event on the "enter" button
//                if ( (keyCode == KeyEvent.KEYCODE_ENTER)) {
//
//                    if(Global.DEBUG)Log.e("button clicked wwww","yes");
//
//
//                    pin=editText_pin.getText().toString().trim();
//                    if(i%2==0) {
//                        if (!pin.equals("")) {
//                            new AsyncEnterPin().execute(pin);
//                        }
//
//                    }
//
//                    i++;
//                    return true;
//                }
//                i++;
//
//                return false;
//            }
//        });



        return rootView;
    }


    private class AsyncEnterPin extends AsyncTask<String,String,Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean sucess) {
            progressDialog.dismiss();

            if(sucess){


                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();




            }else{
                 showAlert("Incorrect PIN");

            }

        }

        @Override
        protected Boolean doInBackground(String... strings) {

            String pin=strings[0];

            try {

                Cursor cur = getActivity().getContentResolver().query(MyContentProvider.URI_GET_CHECK_CORRECT_PIN,
                        null, null, null, null);


                if(cur!=null){
                    if(cur.getCount()==0){

                        cur.close();


                        return false;
                    }else{


                        if(cur.moveToFirst()) {


                            if(Global.DEBUG) Log.e("pin",cur.getString(cur.getColumnIndex(MyContentProvider.COLUMN_PIN_PIN)));

                            if(cur.getString(cur.getColumnIndex(MyContentProvider.COLUMN_PIN_PIN)).
                                    equals(pin)){
                                if(Global.DEBUG) Log.e("reach if","if");
                                cur.close();


                                if(attatch){
                                    Calendar calendar=Calendar.getInstance();
                                    SimpleDateFormat formatter=new SimpleDateFormat(Global.DATEFORMAT_SERVER);
                                    String currentDate=formatter.format(calendar.getTime());
                                    if(Global.DEBUG)Log.e("current date login",currentDate+"");

                                    ContentValues cvv=new ContentValues();
                                    cvv.put(MyContentProvider.COLUMN_CUSTOMER_LOGIN_TIME_DEVICE,currentDate);

                                    getActivity().getContentResolver().
                                            update(MyContentProvider.URI_UPDATE_USER_LOGIN_DEVICE,cvv,
                                                    null,null);
                                }


                                return true;
                            }else{
                                if(Global.DEBUG)  Log.e("reach if","else");

                                cur.close();

                                return false;
                            }



                        }
                        cur.close();


                        return false;
                    }
                }else {
                    return  false;
                }


            }catch (Exception e){
                if(Global.DEBUG) Log.e("Exception",e.toString()+"");
                 return false;
            }


        }
    }

    private void checkIfButtonEnable() {
        String username=editText_pin.getText().toString().trim();

        if(username.equals("")){



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



