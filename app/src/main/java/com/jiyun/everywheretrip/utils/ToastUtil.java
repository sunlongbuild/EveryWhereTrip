package com.jiyun.everywheretrip.utils;

import android.widget.Toast;

import com.jiyun.everywheretrip.base.BaseApp;

/**
 * Created by $sl on 2019-4-30 10:23:29
 */

public class ToastUtil {
    public static void showShort(String msg){
        //避免内存泄漏的一个方法,用到上下文的地方,能用application的就application
        Toast.makeText(BaseApp.getInstance(),msg,Toast.LENGTH_SHORT).show();
    }
    public static void showLong(String msg){
        //避免内存泄漏的一个方法,用到上下文的地方,能用application的就application
        Toast.makeText(BaseApp.getInstance(),msg,Toast.LENGTH_LONG).show();
    }
}
