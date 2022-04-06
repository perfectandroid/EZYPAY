//package com.perfectlimited.easyshare.support;
//
//import android.support.design.widget.BottomSheetBehavior;
//import android.util.Log;
//
//import com.perfectlimited.easyshare.global.Global;
//
//import java.lang.reflect.Field;
//
///**
// * Created by akash on 7/13/2016.
// */
//public class BottomSheetBehaviourExt {
//
//    class BottomSheetBehaviorExt extends BottomSheetBehavior {
//        public void setInitialState(int state) {
//            try {
//                Field field2 = BottomSheetBehavior.class.getDeclaredField("mState");
//                field2.setAccessible(true);
//                field2.set(this, state);
//            } catch (Exception e) {
//                if(Global.DEBUG) Log.e("REFLECTION", e.getMessage());
//            }
//        }
//    }
//
//}
