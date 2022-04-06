package com.perfectlimited.ezypay.activity.mainactivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.perfectlimited.ezypay.R;
import com.perfectlimited.ezypay.global.Global;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHelp extends Fragment {


    public FragmentHelp() {
        // Required empty public constructor
    }


    ListView listviewHelp;
     ArrayAdapter<String> arrayAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_fragment_help, container, false);

        listviewHelp= (ListView) rootView.findViewById(R.id.listviewHelp);
        arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,helpArray);
        listviewHelp.setAdapter(arrayAdapter);

    return rootView;
    }

    public String[] helpArray={
            "Sms from customer phone  GET EZYPAY<SPACE>"+ Global.MOBILE_CODE_SMS+
                    "  to "+ Global.MOBILE_NUM_SMS+
                    " get eZypay id",
            "Click receive cash",
            "Enter all details including eZypay id,mobile number and amount but Reference number is not mandatory",
            "Enter continue button",
            "Wait for OTP message",
            "Enter OTP code and wait for transaction to complete",
            "You will get status for transaction after transaction completion"
    };

}
