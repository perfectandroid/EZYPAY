package com.perfectlimited.ezypay.activity.mainactivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.perfectlimited.ezypay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAbout extends Fragment {


    public FragmentAbout() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_about, container, false);



//
//        PackageInfo pInfo = null;
//        try {
//            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
//            String version = pInfo.versionName;
//
//            textViewProductVersion.setText("Product Version : "+version);
//
//        } catch (PackageManager.NameNotFoundException e) {
//            textViewProductVersion.setText("Product Version : UNAVAILABLE");
//
//        }


        return rootView;
    }

}
