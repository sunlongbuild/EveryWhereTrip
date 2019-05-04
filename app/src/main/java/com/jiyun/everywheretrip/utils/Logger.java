package com.jiyun.everywheretrip.utils;

import android.util.Log;


import com.jiyun.everywheretrip.base.Constants;

/**
 * Created by $sl on 2019-4-30 10:23:29
 */
public class Logger {
    public static void logD(String tag,String msg){
        if (Constants.isDebug){
            Log.d(tag, "logD: "+msg);
        }
    }
}
