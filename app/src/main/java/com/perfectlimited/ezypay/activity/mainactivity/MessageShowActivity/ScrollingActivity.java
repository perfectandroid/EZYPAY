package com.perfectlimited.ezypay.activity.mainactivity.MessageShowActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.perfectlimited.ezypay.R;
import com.perfectlimited.ezypay.global.Global;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

public class ScrollingActivity extends AppCompatActivity {

    JustifiedTextView textView_message,textView_subject,textView_time;
    TextView abc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView_message= (JustifiedTextView) findViewById(R.id.textview_msg_msg);
        textView_subject= (JustifiedTextView) findViewById(R.id.textview_subject_msg);
        textView_time= (JustifiedTextView) findViewById(R.id.textview_msg_time);

        String from=getIntent().getStringExtra("MessageFrom");
        String subject=getIntent().getStringExtra("MessageSubject");
        String msg=getIntent().getStringExtra("MessageMessage");
        String time=getIntent().getStringExtra("MessageTime");

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(from);
        }catch (NullPointerException e){

            if(Global.DEBUG)   Log.e("nulloint",e.toString());
         }


        textView_message.setText(msg);
        textView_subject.setText(subject);
        textView_time.setText(time);



    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            // Do stuff
            supportFinishAfterTransition();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }




}
