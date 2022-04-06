package com.perfectlimited.ezypay.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.perfectlimited.ezypay.R;
import com.perfectlimited.ezypay.global.Global;
import com.perfectlimited.ezypay.model.parse.ModelArrayListTransaction;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by akash on 12/9/2016.
 */

public class AdapterTransaction extends BaseAdapter {
    ArrayList<ModelArrayListTransaction> arrayList  ;
    Activity activity;
    private static LayoutInflater inflater=null;
    public AdapterTransaction(ArrayList<ModelArrayListTransaction> arrayList,
                              Activity activity) {
        this.activity=activity;
        this.arrayList=arrayList;
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        if (arrayList==null) return  0;
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static class ViewHolder{


        com.uncopt.android.widget.text.justify.JustifiedTextView
                textViewTime,textViewTransAmount,
                textViewTransNarration,textViewBalance;

        public TextView
                textViewTransAvailBal;
       LinearLayout linearlayoutDetails,linearlayoutAvailable;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){


            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.itemtransaction, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();


            holder.textViewTransAvailBal=(TextView) vi.findViewById(R.id.textViewTransAvailBal);
            holder.linearlayoutDetails= (LinearLayout) vi.findViewById(R.id.linearlayoutDetails);
            holder.linearlayoutAvailable= (LinearLayout) vi.findViewById(R.id.linearlayoutAvailable);


            holder.textViewTime= (JustifiedTextView) vi.findViewById(R.id.textViewTime);
            holder.textViewTransAmount= (JustifiedTextView) vi.findViewById(R.id.textViewAmount);
            holder.textViewTransNarration= (JustifiedTextView) vi.findViewById(R.id.textViewNarration);
            holder.textViewBalance= (JustifiedTextView) vi.findViewById(R.id.textViewBalance);
             /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if (!arrayList.get(position).isShowAvailable()) {



            String transType="";

            try {
                transType = arrayList.get(position).getTransType();
                if (transType.equalsIgnoreCase("c")) {
                    transType = " Cr";

                } else if (transType.equalsIgnoreCase("d")) {
                    transType = " Dr";

                } else {
                transType="";
                }
            }catch (Exception e){
                transType = "";

            }

            String time=arrayList.get(position).getEffectDate();
           if(Global.DEBUG) Log.e("timeget",time+"---");

            if(!time.equals("null")||!time.equals("")){
              String  timeTemp=  Global.getFormattedString(time, Global.DATEFORMAT_SERVER,"hh:mm a");
                if (timeTemp.equals(time)){
                    time="...";
                }else{
                    time=timeTemp;
                }
            }else{
                time="...";
            }

            String amount=arrayList.get(position).getAmount();
            String narration=arrayList.get(position).getNarration();
            String openBalanceEach=arrayList.get(position).getTransOpenBalance();

            if (narration.equals("null"))narration="...";
            if (openBalanceEach.equals("null"))openBalanceEach="...";
            if (amount.equals("null"))amount="...";

            amount=amount+transType;



            holder.textViewTime.setText(time);
            holder.textViewTransAmount.setText(amount);
            holder.textViewTransNarration.setText(narration);
            holder.textViewBalance.setText(openBalanceEach);



            holder.linearlayoutAvailable.setVisibility(View.GONE);
            holder.linearlayoutDetails.setVisibility(View.VISIBLE);

        }else{
            holder.linearlayoutAvailable.setVisibility(View.VISIBLE);
            holder.linearlayoutDetails.setVisibility(View.GONE);

            holder.textViewTransAvailBal.setText(arrayList.get(position).getAvailableBal()+"");

        }


        return vi;
    }

}
