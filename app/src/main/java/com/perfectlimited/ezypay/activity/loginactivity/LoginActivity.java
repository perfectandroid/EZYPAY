package com.perfectlimited.ezypay.activity.loginactivity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.perfectlimited.ezypay.R;

public class LoginActivity extends AppCompatActivity {

    private static String TAG_FRAGMENT="tagloginfragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentManager fragmentManager=getSupportFragmentManager();
         FragmentLogin fragmentLogin= (FragmentLogin) fragmentManager.findFragmentByTag(TAG_FRAGMENT);
        if(fragmentLogin==null)fragmentLogin=new FragmentLogin();

        fragmentManager.beginTransaction().
                replace(R.id.framelayout_login,fragmentLogin,TAG_FRAGMENT).commit();





    }

}
