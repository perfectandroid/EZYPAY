package com.perfectlimited.ezypay.activity.loginactivity.CheckPinActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.perfectlimited.ezypay.R;
import com.perfectlimited.ezypay.activity.loginactivity.LoginActivity;
import com.perfectlimited.ezypay.global.Global;

public class CheckPinActivity extends AppCompatActivity {

    private static final String TAG_FRAGMENT = "TAG_FRAGMENT_S";
    Global global;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_pin);

        global= (Global) getApplicationContext();

        boolean is_logged_in=false;

        try {
            is_logged_in=global.isUserLogin(CheckPinActivity.this);
        } catch (Exception e) {

            is_logged_in=false;
        }


        if (is_logged_in) {


            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentCheckPin fragmentPin = (FragmentCheckPin) fragmentManager.findFragmentByTag(TAG_FRAGMENT);
            if (fragmentPin == null) {
                fragmentPin = new FragmentCheckPin();
            }


            fragmentManager.beginTransaction().
                    replace(R.id.framelayout_check_pin, fragmentPin, TAG_FRAGMENT).commit();

        }else{
            Intent intent=new Intent(CheckPinActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
