package com.perfectlimited.ezypay.activity.messageshowactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.perfectlimited.ezypay.R;
import com.perfectlimited.ezypay.activity.mainactivity.MainActivity;

public class TransactionMessageActivity extends AppCompatActivity {

    TextView textViewMessage,textViewFailMessage,textViewAmount
            ,textViewBalance,textViewBillNo;
    Button buttonOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_message);

        buttonOk= (Button) findViewById(R.id.buttonokay);

        textViewMessage= (TextView) findViewById(R.id.textViewMessage);
        textViewFailMessage= (TextView) findViewById(R.id.textViewFailMessage);
        textViewAmount= (TextView) findViewById(R.id.textViewAmount);
        textViewBillNo= (TextView) findViewById(R.id.textViewBillNo);
        textViewBalance= (TextView) findViewById(R.id.textViewBalance);
        String responseCode=getIntent().getStringExtra("responseCode");
        String message=getIntent().getStringExtra("message");
        String balance=getIntent().getStringExtra("balance");
        String amount=getIntent().getStringExtra("amount");
        String remarks=getIntent().getStringExtra("remarks");

        if (responseCode.equals("000")){
            //success
            textViewMessage.setText("Transaction successful");
            textViewFailMessage.setVisibility(View.GONE);
            textViewAmount.setText("Rs "+amount+" credited to your account");
            textViewBalance.setText("Current balance is Rs "+balance);

        }else{
            //fail
            textViewMessage.setText("Transaction failed");
            textViewAmount.setVisibility(View.GONE);
            textViewBalance.setVisibility(View.GONE);
            textViewFailMessage.setText(message);

        }

        textViewBillNo.setVisibility(View.GONE);
//        textViewBillNo.setText("Remark: "+remarks);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TransactionMessageActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(TransactionMessageActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
