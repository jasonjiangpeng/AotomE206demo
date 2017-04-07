package com.aotomdemo.lostjason.aotome206demo.log;

import android.util.Log;

/**
 * Created by LostJason on 2017/4/5.
 */

public class Logshow {
    public static boolean islogshow=true;
    public static String  TAG="LogMessage=============";

    public static  void  logshow(String message){
        if (islogshow){
            Log.v(TAG,message);
        }

    }
}
