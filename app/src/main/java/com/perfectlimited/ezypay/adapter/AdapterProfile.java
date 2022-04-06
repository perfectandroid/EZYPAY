package com.perfectlimited.ezypay.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.perfectlimited.ezypay.R;
import com.perfectlimited.ezypay.model.ModelProfileListview;

import java.util.ArrayList;

/**
 * Created by akash on 12/9/2016.
 */

public class AdapterProfile extends BaseAdapter {
    ArrayList<ModelProfileListview> arrayList;
    Activity activity;
    private static LayoutInflater inflater=null;
    public AdapterProfile(ArrayList<ModelProfileListview> arrayList,
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

        public TextView textViewTitle,textViewText;


    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){


            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.itemprofile, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.textViewTitle = (TextView) vi.findViewById(R.id.textViewTitle);
            holder.textViewText=(TextView)vi.findViewById(R.id.textViewText);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();



        holder.textViewText.setText(arrayList.get(position).getText());
        holder.textViewTitle.setText(arrayList.get(position).getTitle());

        return vi;
    }

}
