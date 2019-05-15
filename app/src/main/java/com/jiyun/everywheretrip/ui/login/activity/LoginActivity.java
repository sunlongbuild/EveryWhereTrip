package com.jiyun.everywheretrip.ui.login.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.base.Constants;
import com.jiyun.everywheretrip.presenter.login.LoginPresnter;
import com.jiyun.everywheretrip.ui.login.fragment.LoginOrBindFragment;
import com.jiyun.everywheretrip.utils.Logger;
import com.jiyun.everywheretrip.utils.ToastUtil;
import com.jiyun.everywheretrip.view.login.LoginView;
import com.jungly.gridpasswordview.GridPasswordView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginView, LoginPresnter> implements LoginView {
    public static final int TYPE_LOGIN = 0;
    public static final int TYPE_BIND = 1;
    private int mType;
    public static String TAG = "loginFragment";

    /**
     * 启动当前Activiy
     *
     * @param context
     * @param type    如果是0:代表登录界面;1:代表要绑定手机
     */
    public static void startAct(Context context, int type) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Constants.TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected LoginPresnter initPresenter() {
        return new LoginPresnter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        getIntentData();
        FragmentManager manager = getSupportFragmentManager();
        LoginOrBindFragment loginOrBindFragment = LoginOrBindFragment.getInstance(mType);
        manager.beginTransaction().add(R.id.fl_container, loginOrBindFragment,TAG).commit();
    }

    private void getIntentData() {
        mType = getIntent().getIntExtra(Constants.TYPE, TYPE_LOGIN);
    }

    @Override
    protected void initData() {
        mPresenter.getVerifyCode();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄漏解决方案
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
