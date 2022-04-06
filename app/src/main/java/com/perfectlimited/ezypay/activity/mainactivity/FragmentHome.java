package com.perfectlimited.ezypay.activity.mainactivity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.perfectlimited.ezypay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {


    public interface CallbackHome{
        void callToRecive();
    }
    CallbackHome callbackHome;

    boolean attatched;
    Button buttonHome;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attatched=true;
        callbackHome= (CallbackHome) context;
     }

    @Override
    public void onDetach() {
        super.onDetach();
        attatched=false;
        callbackHome=null;
     }

    public FragmentHome() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView= inflater.inflate(R.layout.fragment_mainactivity_home, container, false);
        setRetainInstance(true);

        attatched=true;

        buttonHome= (Button) rootView.findViewById(R.id.buttonHome);

        if(savedInstanceState!=null){

        }else{

        }


        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackHome.callToRecive();
            }
        });

        return rootView;
    }







}
