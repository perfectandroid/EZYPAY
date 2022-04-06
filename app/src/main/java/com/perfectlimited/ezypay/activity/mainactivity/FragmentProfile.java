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
import android.widget.ListView;
import android.widget.TextView;

import com.perfectlimited.ezypay.MyContentProvider;
import com.perfectlimited.ezypay.R;
import com.perfectlimited.ezypay.adapter.AdapterProfile;
import com.perfectlimited.ezypay.global.Global;
import com.perfectlimited.ezypay.model.ModelProfileListview;
import com.perfectlimited.ezypay.model.ModelServerResponse;
import com.perfectlimited.ezypay.model.parse.ModelparseProfile;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {


    public FragmentProfile() {
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
        outState.putString("textViewProfileName",textViewProfileName.getText().toString().trim());

        outState.putBoolean("imageButtonRetryTransaction",imageButtonRetryprofile.isShown());

    }

    @Override
    public void onDetach() {
        super.onDetach();
        attach =false;

     }

    boolean attach;

    ProgressDialog progressDialog;
    ListView listView;
    ArrayList<ModelProfileListview> arrayList;

    AdapterProfile adapter;
    TextView textViewProfileName;
    ImageButton imageButtonRetryprofile;


    de.hdodenhof.circleimageview.CircleImageView imageViewProfile;

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_profile, container, false);
        setRetainInstance(true);
        attach =true;

         progressDialog=new ProgressDialog(getActivity());
         progressDialog.setMessage("Please wait...");
         progressDialog.setCancelable(false);
         listView= (ListView) rootView.findViewById(R.id.listview_profile);
         textViewProfileName= (TextView) rootView.findViewById(R.id.textViewProfileName);
         imageButtonRetryprofile= (ImageButton) rootView.findViewById(R.id.imageButtonRetryprofile);
         imageViewProfile= (CircleImageView) rootView.findViewById(R.id.imageViewProfile);

         String path = getActivity().getFilesDir().toString().concat("/").concat(Global.PROFILE_PIC_NAME);
         File fileProfile = new File(path);

         float PixelImage = Global.convertDpToPixel(125, getActivity());
         int pixelImages = Math.round(PixelImage);
         if (Global.DEBUG) Log.e("PixelImage", pixelImages + "");


         Picasso.with(getActivity()).load(fileProfile).
                 centerCrop().noFade().placeholder(R.drawable.profiledefault).
                 error(R.drawable.profiledefault).
                 resize(pixelImages, pixelImages).into(imageViewProfile);


         if(savedInstanceState!=null){

             if (savedInstanceState.getBoolean("imageButtonRetryTransaction")){
                 imageButtonRetryprofile.setVisibility(View.VISIBLE);
             }else{
                 imageButtonRetryprofile.setVisibility(GONE);
             }

             textViewProfileName.setText(savedInstanceState.getString("textViewProfileName"));

             if (savedInstanceState.getBoolean("progressDialog")){
                 progressDialog.show();
             }else{
                 progressDialog.dismiss();
             }

         }else {
             arrayList=new ArrayList<>();
             adapter=new AdapterProfile(arrayList,getActivity());
             imageButtonRetryprofile.setVisibility(GONE);
             new AsyncSetName().execute("");
             new AsyncProfileServer().execute("");

         }

         if(adapter==null)adapter=new AdapterProfile(arrayList,getActivity());
         listView.setAdapter(adapter);


         imageButtonRetryprofile.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 new AsyncProfileServer().execute("");
             }
         });

        return rootView;
    }

    private class AsyncProfileServer extends AsyncTask<String,String,ModelServerResponse> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            imageButtonRetryprofile.setVisibility(GONE);

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
                        "CashlessMerchantProfile?merchantId="+ URLEncoder.encode(userId, "UTF-8")+
                        "&merchantMobileNo="+ URLEncoder.encode(phoneNumber, "UTF-8")+
                        "&pin="+URLEncoder.encode(pin, "UTF-8")+"&transDate="+URLEncoder.encode(Global.getTime(), "UTF-8");

                modelServerResponse=Global.callServer(createdUrl,getActivity());



                if(modelServerResponse.getException()==null){
                    String response=modelServerResponse.getResponse();


                    JSONObject jsonObject=new JSONObject(response);

                    String customerName="";
                    String customerAddress1="";
                    String customerAddress2="";
                    String customerAddress3="";
                    String mobileNo="";

                    //======acc det======
                    String acno="";
                    String lastacessdate="";
                    String acType="";
                    String branchCode="";
                    String branchName="";
                    String AvailableBal="";
                    String UnClrBal="";



                    if(jsonObject.getString("merchantProfile").equals("null")){
                        modelServerResponse.setException(new Exception("Merchant transaction null exception"));

                    }else {
                        JSONArray jsonArray = jsonObject.getJSONArray("merchantProfile");


                        if (jsonArray.length() != 0) {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jobj = jsonArray.getJSONObject(i);


                                customerName=jobj.getString("customerName");
                                customerAddress1=jobj.getString("customerAddress1");
                                customerAddress2=jobj.getString("customerAddress2");
                                customerAddress3=jobj.getString("customerAddress3");
                                mobileNo=jobj.getString("mobileNo");

                                JSONArray jsonArrayAccDet=jobj.getJSONArray("merchantAccounts");
                                if(jsonArrayAccDet.length()!=0){
                                    for (int j=0;j<jsonArrayAccDet.length();j++){
                                        JSONObject jObjacc=jsonArrayAccDet.getJSONObject(j);

                                        acno=jObjacc.getString("acno");
                                        lastacessdate=jObjacc.getString("lastacessdate");
                                        acType=jObjacc.getString("acType");
                                        branchCode=jObjacc.getString("branchCode");
                                        branchName=jObjacc.getString("branchName");
                                        AvailableBal=jObjacc.getString("AvailableBal");
                                        UnClrBal=jObjacc.getString("UnClrBal");

                                        break;
                                    }
                                }else{
                                    //todo throw exceptin or not
                                }


                                break;
                            }


                        } else {
                            throw new Exception("Info array null");
                        }

                    }

                    //parse response here
                    ModelparseProfile modelparseProfile=new ModelparseProfile();
                    modelparseProfile.setCustomerName (customerName);
                    modelparseProfile.setCustomerAddress1(customerAddress1);
                    modelparseProfile.setCustomerAddress2(customerAddress2);
                    modelparseProfile.setCustomerAddress3(customerAddress3);
                    modelparseProfile.setMobileNo(mobileNo);
                    modelparseProfile.setAcno(acno);
                    modelparseProfile.setLastacessdate(lastacessdate);
                    modelparseProfile.setAcType(acType);
                    modelparseProfile.setBranchCode(branchCode);
                    modelparseProfile.setBranchName(branchName);
                    modelparseProfile.setAvailableBal(AvailableBal);
                    modelparseProfile.setUnClrBal(UnClrBal);
                    modelServerResponse.setModelparseProfile(modelparseProfile);
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

                ModelparseProfile modelparseProfile=modelServerResponse.getModelparseProfile();


                String customerName=modelparseProfile.getCustomerName();
               textViewProfileName.setText(customerName);
                String customerAddress1=modelparseProfile.getCustomerAddress1();
                String customerAddress2=modelparseProfile.getCustomerAddress2();
                String customerAddress3=modelparseProfile.getCustomerAddress3();
                String mobileNo=modelparseProfile.getMobileNo();

                String acno=modelparseProfile.getAcno();
                String lastacessdate=modelparseProfile.getLastacessdate();
                String acType=modelparseProfile.getAcType();
                String branchCode=modelparseProfile.getBranchCode();
                String branchName=modelparseProfile.getBranchName();
                String AvailableBal=modelparseProfile.getAvailableBal();
                String UnClrBal=modelparseProfile.getUnClrBal();

                arrayList.clear();

                ModelProfileListview modelProfileListview=new ModelProfileListview();
                modelProfileListview.setTitle("Address 1");
                modelProfileListview.setText(customerAddress1);
                arrayList.add(modelProfileListview);

                ModelProfileListview modelProfileaddress2=new ModelProfileListview();
                modelProfileaddress2.setTitle("Address 2");
                modelProfileaddress2.setText(customerAddress2);
                arrayList.add(modelProfileaddress2);

                ModelProfileListview modelProfileAddress3=new ModelProfileListview();
                modelProfileAddress3.setTitle("Address 3");
                modelProfileAddress3.setText(customerAddress3);
                arrayList.add( modelProfileAddress3);


                ModelProfileListview modelProfileMobile=new ModelProfileListview();
                modelProfileMobile.setTitle("Mobile");
                modelProfileMobile.setText(mobileNo);
                arrayList.add( modelProfileMobile);

                ModelProfileListview modelProfileAccNo=new ModelProfileListview();
                modelProfileAccNo.setTitle("Account number");
                modelProfileAccNo.setText(acno);
                arrayList.add( modelProfileAccNo );


                ModelProfileListview modelProfilelastaccess=new ModelProfileListview();
                modelProfilelastaccess.setTitle("Last access date");
                modelProfilelastaccess.setText(lastacessdate);
                arrayList.add( modelProfilelastaccess );

                ModelProfileListview modelProfileAcType=new ModelProfileListview();
                modelProfileAcType.setTitle("Account type");
                modelProfileAcType.setText(acType);
                arrayList.add( modelProfileAcType );

                ModelProfileListview modelProfilebranchCode=new ModelProfileListview();
                modelProfilebranchCode.setTitle("Branch code");
                modelProfilebranchCode.setText(branchCode);
                arrayList.add( modelProfilebranchCode );

                ModelProfileListview modelProfilebranchName=new ModelProfileListview();
                modelProfilebranchName.setTitle("Branch name");
                modelProfilebranchName.setText(branchName);
                arrayList.add( modelProfilebranchName );

                ModelProfileListview modelProfileAvailableBal=new ModelProfileListview();
                modelProfileAvailableBal.setTitle("Available balance");
                modelProfileAvailableBal.setText(AvailableBal);
                arrayList.add( modelProfileAvailableBal );

                ModelProfileListview modelProfileUnClrBal=new ModelProfileListview();
                modelProfileUnClrBal.setTitle("UnClr balance");
                modelProfileUnClrBal.setText(UnClrBal);
                arrayList.add( modelProfileUnClrBal );

                adapter.notifyDataSetChanged();
                imageButtonRetryprofile.setVisibility(GONE);

             }else{


                imageButtonRetryprofile.setVisibility(View.VISIBLE);

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


            textViewProfileName.setText(s);

        }
    }

}
