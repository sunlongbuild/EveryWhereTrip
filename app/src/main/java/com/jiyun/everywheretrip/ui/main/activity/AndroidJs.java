package com.jiyun.everywheretrip.ui.main.activity;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by $sl on 2019/5/15 16:29.
 */
public class AndroidJs extends Object {
    private Context mContext;

    public AndroidJs(Context context) {
        mContext = context;
    }

    private static final String TAG = "AndroidJs----";

    @JavascriptInterface
    public void callAndroid(String msg, int id) {
        if (msg.equals("route_details")) {
            Log.d(TAG, "callAndroid: " + id);
            Intent intent = new Intent(mContext, HomeDetailsActivity.class);
            intent.putExtra("id", id);
            mContext.startActivity(intent);
        }
    }

    @JavascriptInterface
    public void callAndroid(String msg) {
        if (msg.equals("subject_list")) {
            Log.d(TAG, "callAndroid: " + 1231);
            mContext.startActivity(new Intent(mContext, MainActivity.class));
        }
    }
}
