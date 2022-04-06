package com.perfectlimited.ezypay.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class RebootReciver extends BroadcastReceiver {
    public RebootReciver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

//
//        if(Global.DEBUG)  Log.e("onrecive", "yes");
//
//        Global global = (Global) context.getApplicationContext();
//
//        boolean is_logged_in = false;
//
//        try {
//            is_logged_in = global.isUserLogin(context);
//        } catch (Exception e) {
//
//            is_logged_in = false;
//        }
//
//        if (is_logged_in){
//
//            if (!global.isMyServiceRunning(ServiceSync.class)) {
//                Intent serviceIntent = new Intent(context, ServiceSync.class);
//                context.startService(serviceIntent);
//                if(Global.DEBUG)     Log.e("service started", "from broadcast android");
//            } else {
//                if(Global.DEBUG)   Log.e("service already started", "yes");
//
//            }
//    }else{
//            if(Global.DEBUG) Log.e("not logged","no");
//        }

    }
}
