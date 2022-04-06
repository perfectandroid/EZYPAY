package com.perfectlimited.ezypay.activity.mainactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.perfectlimited.ezypay.MyContentProvider;
import com.perfectlimited.ezypay.R;
import com.perfectlimited.ezypay.activity.loginactivity.LoginActivity;
import com.perfectlimited.ezypay.global.Global;
import com.perfectlimited.ezypay.model.ModelUserDetails;
  import com.squareup.picasso.Picasso;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentHome.CallbackHome
          {
    SharedPreferences pref;
    Global global;
    TextView textView_name,textViewDescription;
    de.hdodenhof.circleimageview.CircleImageView imageViewProfile;
    NavigationView navigationView;

    String TAG_FRAGMENT = "tagFragment";
    public static final String TAG_FRAGMENT_HOME = "TAG_FRAGMENT_HOME";
    private static final String TAG_FRAGMENT_CHANGE_PIN = "TAG_FRAGMENT_CHANGE_PIN";
    private static final String TAG_FRAGMENT_PROFILE = "TAG_FRAGMENT_PROFILE";
    private static final String TAG_FRAGMENT_HELP = "TAG_FRAGMENT_HELP";
              private static final String TAG_FRAGMENT_ABOUT = "TAG_FRAGMENT_ABOUT";
              private static final String TAG_FRAGMENT_GENERATE = "TAG_FRAGMENT_GENERATE";

              private static final String TAG_FRAGMENT_TRANSACTION_LIST = "TAG_FRAGMENT_TRANSACTION_LIST";

              private static final String TAG_FRAGMENT_RECEIVE = "TAG_FRAGMENT_RECEIVE";

    boolean mbound;

    private static final int URL_LOADER = 0;
    AlertDialog alertDialog;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        outState.putString(TAG_FRAGMENT, fragmentManager.
                findFragmentById(R.id.framelayout_mainactivity).getTag());

        outState.putString("textView_name", textView_name.getText().toString().trim());
        outState.putString("textViewDescription", textViewDescription.getText().toString().trim());



    }


    @Override
    protected void onResume() {
        super.onResume();
//        if (dataUpdateReceiver == null) dataUpdateReceiver = new DataUpdateReceiver();
//        IntentFilter intentFilter = new IntentFilter(Global.REFRESH_DATA_INTENT_FINISH);
//        registerReceiver(dataUpdateReceiver, intentFilter);

    }




    @Override
    protected void onPause() {
        super.onPause();
//        if (dataUpdateReceiver != null) unregisterReceiver(dataUpdateReceiver);
    }












    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        try {
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        } catch (NullPointerException e) {
            if (Global.DEBUG) Log.e("NullPointerException", e.toString() + "");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        global = (Global) getApplicationContext();
        pref = getSharedPreferences(Global.SHAREDPREFSTRING, MODE_PRIVATE);
        boolean is_logged_in;

        try {
            is_logged_in = global.isUserLogin(MainActivity.this);
        } catch (Exception e) {

            is_logged_in = false;
        }


        if (is_logged_in) {
            //logged in


            View header = navigationView.getHeaderView(0);
            textView_name = (TextView) header.findViewById(R.id.textView_name);
              imageViewProfile = (de.hdodenhof.circleimageview.CircleImageView) header.findViewById(R.id.imageView);
            textViewDescription= (TextView) header.findViewById(R.id.textViewDescription);


            String path = getFilesDir().toString().concat("/").concat(Global.PROFILE_PIC_NAME);
            File fileProfile = new File(path);

            float PixelImage = Global.convertDpToPixel(125, this);
            int pixelImages = Math.round(PixelImage);
            if (Global.DEBUG) Log.e("PixelImage", pixelImages + "");


            Picasso.with(MainActivity.this).load(fileProfile).
                    centerCrop().noFade().placeholder(R.drawable.profiledefault).
                    error(R.drawable.profiledefault).
                    resize(pixelImages, pixelImages).into(imageViewProfile);




            if (savedInstanceState != null) {
                //=========run after configChange

                FragmentManager fragmentManager = getSupportFragmentManager();
                String fragTag = savedInstanceState.getString(TAG_FRAGMENT);
                Fragment frag = fragmentManager.findFragmentByTag(fragTag);
                if (frag != null) {
                    initFragment(frag, fragTag, false);
                } else {
                    initFragment(new FragmentHome(), TAG_FRAGMENT_HOME, false);


                }

                textView_name.setText(savedInstanceState.getString("textView_name"));
                textViewDescription.setText(savedInstanceState.getString("textViewDescription"));

            } else {
                //=======run for the first time

                initFragment(new FragmentHome(), TAG_FRAGMENT_HOME, false);

            }


//========================set name=======
            try {

                Cursor cur = getContentResolver().
                        query(MyContentProvider.URI_GET_USER_DETAILS, null,
                                MyContentProvider.COLUMN_CUSTOMER_PRIMARY + " = 1", null, null);


//                if (Global.DEBUG)      Log.e("curlenght",cur.getCount()+"");

                ModelUserDetails modelUserDetails = new ModelUserDetails();


                if (cur != null) {
                    if (cur.getCount() == 0) {

                        throw new Exception("cursor null");
                    } else {

                        if (cur.moveToFirst()) {


                            modelUserDetails.setNameUser(cur.getString
                                    (cur.getColumnIndex(MyContentProvider.COLUMN_CUSTOMER_NAME)));

                            modelUserDetails.setUserDesciption(cur.getString
                                    (cur.getColumnIndex(MyContentProvider.COLUMN_CUSTOMER_DESCRIPTION)));



                        }

                    }

                    cur.close();

                } else {
                    throw new Exception("cursor null");
                }
                textView_name.setText(modelUserDetails.getNameUser() + "");
                textViewDescription.setText(modelUserDetails.getUserDesciption() + "");
//





            } catch (Exception e) {
                textView_name.setText("NAME");
                textViewDescription.setText("...");



            }
//====================end set name =======================


        } else {

            //not logged in-->Go to login
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
    }




//updateIntervalChanged=1 for changedSettings //else for not chnaged






    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragmentManager=getSupportFragmentManager();
            if(!fragmentManager.findFragmentById(R.id.framelayout_mainactivity).getTag()
                    .equals(TAG_FRAGMENT_HOME)){

                initFragment(new FragmentHome(),TAG_FRAGMENT_HOME,false);


                if(navigationView==null) navigationView = (NavigationView) findViewById(R.id.nav_view);
                if(navigationView!=null)  navigationView.getMenu().getItem(0).setChecked(true);
             }else{



                AlertDialog.Builder alertExit=new AlertDialog.Builder(MainActivity.this);
                alertExit.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                        finish();

                    }
                });
                alertExit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                   dialogInterface.dismiss();
                    }
                });
                alertExit.setMessage("Exit from application?");
                alertDialog=    alertExit.create();
                alertDialog.show();
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager=getSupportFragmentManager();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Fragment savedFragment=   fragmentManager.findFragmentByTag(TAG_FRAGMENT_HOME);
            if(savedFragment!=null){
                initFragment(savedFragment,TAG_FRAGMENT_HOME,false);
            }else{
                initFragment(new FragmentHome(),TAG_FRAGMENT_HOME,false);

            }
        }

        else if (id == R.id.nav_recive) {
            Fragment savedFragment=   fragmentManager.findFragmentByTag(TAG_FRAGMENT_RECEIVE);
            if(savedFragment!=null){
                initFragment(savedFragment,TAG_FRAGMENT_RECEIVE,false);
            }else{
                initFragment(new FragmentReceive(),TAG_FRAGMENT_RECEIVE,false);

            }
        }

        else if (id == R.id.nav_change_pin) {
            Fragment savedFragment=   fragmentManager.findFragmentByTag(TAG_FRAGMENT_CHANGE_PIN);
            if(savedFragment!=null){
                initFragment(savedFragment,TAG_FRAGMENT_CHANGE_PIN,false);
            }else{
                initFragment(new FragmentChangePin(),TAG_FRAGMENT_CHANGE_PIN,false);

            }
        }
        else if (id == R.id.nav_profile) {
            Fragment savedFragment=   fragmentManager.findFragmentByTag(TAG_FRAGMENT_PROFILE);
            if(savedFragment!=null){
                initFragment(savedFragment, TAG_FRAGMENT_PROFILE,false);
            }else{
                initFragment(new FragmentProfile(), TAG_FRAGMENT_PROFILE,false);

            }
        }
        else if (id == R.id.nav_transaction_list) {
            Fragment savedFragment=   fragmentManager.findFragmentByTag(TAG_FRAGMENT_TRANSACTION_LIST);
            if(savedFragment!=null){
                initFragment(savedFragment, TAG_FRAGMENT_TRANSACTION_LIST,false);
            }else{
                initFragment(new FragmentTransactionList(), TAG_FRAGMENT_TRANSACTION_LIST,false);

            }
        }
        else if (id == R.id.nav_help) {
            Fragment savedFragment=   fragmentManager.findFragmentByTag(TAG_FRAGMENT_HELP);
            if(savedFragment!=null){
                initFragment(savedFragment,TAG_FRAGMENT_HELP,false);
            }else{
                initFragment(new FragmentHelp(),TAG_FRAGMENT_HELP,false);

            }
        }
        else if (id == R.id.nav_about) {
            Fragment savedFragment=   fragmentManager.findFragmentByTag(TAG_FRAGMENT_ABOUT);
            if(savedFragment!=null){
                initFragment(savedFragment,TAG_FRAGMENT_ABOUT,false);
            }else{
                initFragment(new FragmentAbout(),TAG_FRAGMENT_ABOUT,false);

            }
        }
        else if (id == R.id.nav_generate) {
            Fragment savedFragment=   fragmentManager.findFragmentByTag(TAG_FRAGMENT_GENERATE);
            if(savedFragment!=null){
                initFragment(savedFragment,TAG_FRAGMENT_GENERATE,false);
            }else{
                initFragment(new FragmentGenerate(),TAG_FRAGMENT_GENERATE,false);

            }

        }
        else if (id == R.id.nav_quit) {

            AlertDialog.Builder alertExit=new AlertDialog.Builder(MainActivity.this);
            alertExit.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();
                    finish();

                }
            });
            alertExit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertExit.setMessage("Exit from application?");
            alertDialog=    alertExit.create();
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void initFragment(Fragment fragment, String tag, boolean backstack){

        String appBarText;

        if(tag.equals(TAG_FRAGMENT_HOME)){
            appBarText=getResources().getString(R.string.app_name);
        }
        else if(tag.equals(TAG_FRAGMENT_CHANGE_PIN)){
            appBarText=getString(R.string.changepin);
        }else if(tag.equals(TAG_FRAGMENT_RECEIVE)){
            appBarText=getResources().getString(R.string.recive);
        }else if(tag.equals(TAG_FRAGMENT_PROFILE)){
            appBarText=getString(R.string.profile);
        }
        else if(tag.equals(TAG_FRAGMENT_TRANSACTION_LIST)){
            appBarText=getString(R.string.transaction_list);
        }
        else if(tag.equals(TAG_FRAGMENT_ABOUT)){
            appBarText=getString(R.string.aboutx);
        }
        else if(tag.equals(TAG_FRAGMENT_GENERATE)){
            appBarText=getString(R.string.generate);
        }
        else if(tag.equals(TAG_FRAGMENT_HELP)){
            appBarText=getString(R.string.help);
        }
        else{
            appBarText=getResources().getString(R.string.app_name);

        }


        try{
            getSupportActionBar().setTitle(appBarText);
        }catch (NullPointerException e){
            if(Global.DEBUG)   Log.e("NullPointerException",e.toString()+"");
        }



        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.framelayout_mainactivity,fragment,tag);
        fragmentTransaction.commit();



    }


              @Override
              public void callToRecive() {
                  FragmentManager fragmentManager=getSupportFragmentManager();

                  Fragment savedFragment=   fragmentManager.findFragmentByTag(TAG_FRAGMENT_RECEIVE);
                  if(savedFragment!=null){
                      initFragment(savedFragment,TAG_FRAGMENT_RECEIVE,false);
                  }else{
                      initFragment(new FragmentReceive(),TAG_FRAGMENT_RECEIVE,false);

                  }
              }
          }
