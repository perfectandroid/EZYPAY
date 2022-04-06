package com.perfectlimited.ezypay.activity.mainactivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.perfectlimited.ezypay.MyContentProvider;
import com.perfectlimited.ezypay.R;
import com.perfectlimited.ezypay.adapter.AdapterTransaction;
import com.perfectlimited.ezypay.global.Global;
import com.perfectlimited.ezypay.model.ModelServerResponse;
import com.perfectlimited.ezypay.model.parse.ModelArrayListTransaction;
import com.perfectlimited.ezypay.model.parse.ModelTransactionList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTransactionList extends Fragment {


    public FragmentTransactionList() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attach =true;


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("progressDialog",progressDialog.isShowing());

        outState.putString("textViewAccountName",textViewAccountName.getText().toString().trim());
        outState.putString("textViewOpeningBalance",textViewOpeningBalance.getText().toString().trim());

        outState.putBoolean("imageButtonRetryTransaction", imageButtonRetryTransaction.isShown());
        outState.putBoolean("linearLayout", linearLayout.isShown());
        outState.putBoolean("listView", listView.isShown());
        outState.putBoolean("textViewNoTransaction", textViewNoTransaction.isShown());
        outState.putString("textViewDate", textViewDate.getText().toString().trim());



    }

    @Override
    public void onDetach() {
        super.onDetach();
        attach =false;

     }

    boolean attach;

    ProgressDialog progressDialog;
    ListView listView;
    LinearLayout linearLayout;
    ArrayList<ModelArrayListTransaction> arrayList;

    AdapterTransaction adapter;
    TextView textViewAccountName,textViewOpeningBalance,
            textViewNoTransaction,textViewDate;
    ImageButton imageButtonRetryTransaction;


     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_transaction, container, false);
        setRetainInstance(true);
         attach =true;
         progressDialog=new ProgressDialog(getActivity());
         progressDialog.setMessage("Please wait...");
         progressDialog.setCancelable(false);
         listView= (ListView) rootView.findViewById(R.id.listview_transaction);
         textViewAccountName= (TextView) rootView.findViewById(R.id.textViewAccNo);
         textViewOpeningBalance= (TextView) rootView.findViewById(R.id.textViewOpeningBalance);
         textViewNoTransaction= (TextView) rootView.findViewById(R.id.textViewNoTransaction);
         textViewDate= (TextView) rootView.findViewById(R.id.textViewDate);

          imageButtonRetryTransaction = (ImageButton) rootView.findViewById(R.id.imageButtonRetryTransaction);
         linearLayout= (LinearLayout) rootView.findViewById(R.id.linearLayout);
         if(savedInstanceState!=null){


             if (savedInstanceState.getBoolean("textViewNoTransaction")){
                 textViewNoTransaction.setVisibility(View.VISIBLE);
             }else{
                 textViewNoTransaction.setVisibility(GONE);
             }


             if (savedInstanceState.getBoolean("imageButtonRetryTransaction")){
                 imageButtonRetryTransaction.setVisibility(View.VISIBLE);
             }else{
                 imageButtonRetryTransaction.setVisibility(GONE);
             }

             if (savedInstanceState.getBoolean("linearLayout")){
                 linearLayout.setVisibility(View.VISIBLE);
             }else{
                 linearLayout.setVisibility(GONE);
             }


             if (savedInstanceState.getBoolean("listView")){
                 listView.setVisibility(View.VISIBLE);
             }else{
                 listView.setVisibility(GONE);
             }

             textViewAccountName.setText(savedInstanceState.getString("textViewAccountName"));
             textViewOpeningBalance.setText(savedInstanceState.getString("textViewOpeningBalance"));
             textViewDate.setText(savedInstanceState.getString("textViewDate"));


             if (savedInstanceState.getBoolean("progressDialog")){
                 progressDialog.show();
             }else{
                 progressDialog.dismiss();
             }

         }else {
             arrayList=new ArrayList<>();
             adapter=new AdapterTransaction(arrayList,getActivity());
             imageButtonRetryTransaction.setVisibility(GONE);
             linearLayout.setVisibility(GONE);
             listView.setVisibility(GONE);
             textViewNoTransaction.setVisibility(GONE);

             new AsyncTransactionListServer().execute("");


         }

         if(adapter==null)adapter=new AdapterTransaction(arrayList,getActivity());
         listView.setAdapter(adapter);


         imageButtonRetryTransaction.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 new AsyncTransactionListServer().execute("");
             }
         });

        return rootView;
    }

    private class AsyncTransactionListServer extends AsyncTask<String,String,ModelServerResponse> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            imageButtonRetryTransaction.setVisibility(GONE);
            textViewNoTransaction.setVisibility(GONE);
            listView.setVisibility(GONE);
            linearLayout.setVisibility(GONE);

        }

        @Override
        protected ModelServerResponse doInBackground(String... strings) {
            ModelServerResponse modelServerResponse=new ModelServerResponse();

            String userId="";
            String token="";
            String phoneNumber="";
            String pin="";
             try{

                userId=  Global.getFromCustomer(MyContentProvider.COLUMN_CUSTOMER_ID);
                token=  Global.getFromCustomer(MyContentProvider.COLUMN_CUSTOMER_TOKEN);
                phoneNumber=  Global.getFromCustomer(MyContentProvider.COLUMN_CUSTOMER_MOBILE);


                String [] proj={MyContentProvider.TABLE_NAME_PIN};
                Cursor cur = getActivity().getContentResolver().query(MyContentProvider.URI_QUERY_GET, proj,
                        MyContentProvider.COLUMN_PIN_PRIMARY + " =1 ", null, null);


                if (cur != null) {
                    if (cur.getCount() == 0) {

                        throw new Exception("cursor null");
                    } else {

                        if (cur.moveToFirst()) {


                            pin=     cur.getString
                                    (cur.getColumnIndex(MyContentProvider.COLUMN_PIN_PIN));


                        }else {
                            throw new Exception("Cursor unable to move first exception");
                        }

                    }

                    cur.close();

                } else {
                    throw new Exception("cursor null");
                }



                String createdUrl=Global.getBaseUrl()+ Global.getApiName()+
                        "CashlessMerchantTransactionList?merchantId="+ URLEncoder.encode(userId, "UTF-8")+
                        "&merchantMobileNo="+ URLEncoder.encode(phoneNumber, "UTF-8")+
                        "&pin="+URLEncoder.encode(pin, "UTF-8")+
                        "&transDate="+URLEncoder.encode(Global.getTime(), "UTF-8");

                modelServerResponse=Global.callServer(createdUrl,getActivity());



                if(modelServerResponse.getException()==null){
                    String response=modelServerResponse.getResponse();


                    JSONObject jsonObject=new JSONObject(response);

                    String acno="";
                    String AvailableBal="";
                    String OpeningBal="";
                    String asOnDate="";


                    //======trans det======
                    String transactions="";
                    String effectDate="";
                    String amount="";
                    String narration="";
                    String transType="";
                    String transOpenBalance="";




                    if(jsonObject.getString("merchantTransactions").equals("null")){
                        modelServerResponse.setException(new Exception("Merchant transaction null exception"));

                    }else {
                        JSONArray jsonArray = jsonObject.getJSONArray("merchantTransactions");

                        arrayList.clear();

                        if (jsonArray.length() != 0) {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jobj = jsonArray.getJSONObject(i);


                                acno=jobj.getString("acno");
                                AvailableBal=jobj.getString("AvailableBal");
                                OpeningBal=jobj.getString("OpeningBalance");
                                asOnDate=jobj.getString("lastacessdate");



                                JSONArray jsonArrayTransDet=jobj.getJSONArray("transactions");
                                if(jsonArrayTransDet.length()!=0){
                                    for (int j=0;j<jsonArrayTransDet.length();j++){
                                        JSONObject jObjacc=jsonArrayTransDet.getJSONObject(j);
                                        ModelArrayListTransaction modelArrayListTransaction=new ModelArrayListTransaction();


                                        transactions=jObjacc.getString("transaction");
                                        effectDate=jObjacc.getString("effectDate");
                                        amount=jObjacc.getString("amount");
                                        narration=jObjacc.getString("narration");
                                        transType=jObjacc.getString("transType");
                                        transOpenBalance=jObjacc.getString("OpeningBal");

                                        modelArrayListTransaction.setShowAvailable(false);
                                        modelArrayListTransaction.setAvailableBal("NILL");
                                        modelArrayListTransaction.setTransType(transType);
                                        modelArrayListTransaction.setTransactions(transactions);
                                        modelArrayListTransaction.setNarration(narration);
                                        modelArrayListTransaction.setAmount(amount);
                                        modelArrayListTransaction.setEffectDate(effectDate);
                                        modelArrayListTransaction.setTransOpenBalance(transOpenBalance);
                                         arrayList.add(modelArrayListTransaction);

                                    }
                                    ModelArrayListTransaction modelArrayListTransaction=new ModelArrayListTransaction();
                                    modelArrayListTransaction.setShowAvailable(true);
                                    modelArrayListTransaction.setAvailableBal(AvailableBal);
                                    arrayList.add(modelArrayListTransaction);

                                }else{
                                    arrayList.clear();
                                    //todo throw exceptin or not
                                }


                                break;
                            }


                        } else {
                            throw new Exception("Info array null");
                        }

                    }

                     ModelTransactionList modelTransactionList=new ModelTransactionList();
                    modelTransactionList.setAcno(acno);
                    modelTransactionList.setAsOnDate(asOnDate);
 //                    modelTransactionList.setModelArrayListTransactions(arrayList);

                    modelTransactionList.setOpeningBal(OpeningBal);

                    modelServerResponse.setModelTransactionList(modelTransactionList);
                }
            } catch (Exception e) {

                modelServerResponse.setException(e);
                 if(Global.DEBUG)   Log.e("Exception",e.toString()+"");
            }
            return modelServerResponse;
        }

        @Override
        protected void onPostExecute(ModelServerResponse modelServerResponse) {
            super.onPostExecute(modelServerResponse);

            if(modelServerResponse.getException()==null){

                ModelTransactionList transactionList=modelServerResponse.getModelTransactionList();
                String accNo=transactionList.getAcno();
                String openingBal=transactionList.getOpeningBal();
                String asOnDate=transactionList.getAsOnDate();
                textViewAccountName.setText(accNo);
                textViewOpeningBalance.setText(openingBal);

                if(!asOnDate.equals("null")){
                    String  timeTemp=  Global.getFormattedString(asOnDate, Global.DATEFORMAT_SERVER,
                            "dd/MM/yyyy");
                    if (timeTemp.equals(asOnDate)){
//                        asOnDate="...";
                    }else{
                        asOnDate=timeTemp;
                    }
                }else{
                    asOnDate="...";
                }


                textViewDate.setText(asOnDate);

                adapter.notifyDataSetChanged();

                imageButtonRetryTransaction.setVisibility(GONE);
                linearLayout.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
                if (arrayList.size()==0){
                    textViewNoTransaction.setVisibility(View.VISIBLE);
                    imageButtonRetryTransaction.setVisibility(View.VISIBLE);

                }

             }else{


                imageButtonRetryTransaction.setVisibility(View.VISIBLE);
                textViewNoTransaction.setVisibility(View.GONE);

                linearLayout.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
//                Intent intent=new Intent(getActivity(), MessageShowActivity.class);
//                intent.putExtra(Global.MESSAGEPASS,"Profile fetch failed");
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);

            }

            progressDialog.dismiss();
        }
    }

    private class AsyncSetName extends  AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {

            String name="Name";

            try {

                Cursor cur = getActivity().getContentResolver().
                        query(MyContentProvider.URI_GET_USER_DETAILS, null,
                                MyContentProvider.COLUMN_CUSTOMER_PRIMARY + " = 1", null, null);


//                if (Global.DEBUG)      Log.e("curlenght",cur.getCount()+"");



                if (cur != null) {
                    if (cur.getCount() == 0) {

                        throw new Exception("cursor null");
                    } else {

                        if (cur.moveToFirst()) {


                            name=cur.getString
                                    (cur.getColumnIndex(MyContentProvider.COLUMN_CUSTOMER_NAME));


                        }

                    }

                    cur.close();

                } else {
                    throw new Exception("cursor null");
                }
 //





            } catch (Exception e) {

                name="Name";

            }

            return name;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


//            textViewProfileName.setText(s);

        }
    }

}
