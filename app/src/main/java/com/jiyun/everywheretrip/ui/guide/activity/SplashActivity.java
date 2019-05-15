package com.jiyun.everywheretrip.ui.guide.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.base.Constants;
import com.jiyun.everywheretrip.presenter.EmptyPresenter;
import com.jiyun.everywheretrip.ui.login.activity.LoginActivity;
import com.jiyun.everywheretrip.ui.main.activity.MainActivity;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.view.EmptyView;

import butterknife.BindView;

public class SplashActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {

//    @BindView(R.id.iv_guide)
//    ImageView mIvGuide;

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        //StatusBarUtil.setDarkMode(this);//暗色模式
        StatusBarUtil.setLightMode(this);
        try {
            Thread.sleep(2000);//睡眠1秒开始执行下面的方法
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        guide();
    }

    public void guide() {
        //是否是第一次登陆
        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isFirstRun) {
            Log.e("debug", "第一次运行");
            editor.putBoolean("isFirstRun", false);
            editor.commit();
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this, GuideActivity.class);
            startActivity(intent);
            finish();
        } else {
            Log.e("debug", "不是第一次运行");
            String token = (String) SpUtil.getParam(Constants.TOKEN, "");
            if (TextUtils.isEmpty(token)) {
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
