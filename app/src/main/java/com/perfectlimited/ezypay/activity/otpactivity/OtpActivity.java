package com.perfectlimited.ezypay.activity.otpactivity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.perfectlimited.ezypay.R;
import com.perfectlimited.ezypay.activity.mainactivity.MainActivity;

public class OtpActivity extends AppCompatActivity {
    private static String TAG_FRAGMENT="tagotp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        String amount=getIntent().getStringExtra("amount");
        String customerId=getIntent().getStringExtra("customerId");
        String phoneNumber=getIntent().getStringExtra("phoneNumber");
        String remarks=getIntent().getStringExtra("remarks");


        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentOtp fragmentOtp= (FragmentOtp) fragmentManager.findFragmentByTag(TAG_FRAGMENT);
        if(fragmentOtp==null){
            fragmentOtp=new FragmentOtp();
            Bundle args=new Bundle();
            args.putString("amount",amount+"");
            args.putString("customerId",customerId+"");
            args.putString("phoneNumber",phoneNumber+"");
            args.putString("remarks",remarks+"");


            fragmentOtp.setArguments(args);
        }

        fragmentManager.beginTransaction().
                replace(R.id.framelayout_otp,fragmentOtp,TAG_FRAGMENT).commit();


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(OtpActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
