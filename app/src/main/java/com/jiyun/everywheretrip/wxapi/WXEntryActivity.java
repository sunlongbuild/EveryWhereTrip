package com.jiyun.everywheretrip.wxapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.jiyun.everywheretrip.R;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
    }

}
