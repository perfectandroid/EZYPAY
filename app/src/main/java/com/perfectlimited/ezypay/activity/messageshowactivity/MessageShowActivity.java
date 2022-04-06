package com.perfectlimited.ezypay.activity.messageshowactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.perfectlimited.ezypay.R;
import com.perfectlimited.ezypay.activity.loginactivity.CheckPinActivity.CheckPinActivity;
import com.perfectlimited.ezypay.activity.mainactivity.MainActivity;
import com.perfectlimited.ezypay.global.Global;

public class MessageShowActivity extends AppCompatActivity {

    Button buttonOk;
    TextView textViewMessage;
    boolean pinChangeSuccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_show);

        pinChangeSuccess=getIntent().getBooleanExtra("pinChangeSuccess",false);
        buttonOk= (Button) findViewById(R.id.buttonOk);
        textViewMessage= (TextView) findViewById(R.id.textViewMessage);

       String message= getIntent().getStringExtra(Global.MESSAGEPASS);
        textViewMessage.setText(message);


        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pinChangeSuccess){
                    Intent intent=new Intent(MessageShowActivity.this, CheckPinActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(MessageShowActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }


            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(MessageShowActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
