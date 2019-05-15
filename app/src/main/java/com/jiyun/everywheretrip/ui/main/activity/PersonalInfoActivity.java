package com.jiyun.everywheretrip.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.base.Constants;
import com.jiyun.everywheretrip.presenter.personal.PersonalPresenter;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.utils.ToastUtil;
import com.jiyun.everywheretrip.view.personal.PersonalView;

import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalInfoActivity extends BaseActivity<PersonalView, PersonalPresenter> implements PersonalView, View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.tv_userName)
    TextView mTvUserName;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_write)
    TextView mTvWrite;
    @BindView(R.id.ll_head)
    LinearLayout mLlHead;
    @BindView(R.id.ll_ninkName)
    LinearLayout mLlNinkName;
    @BindView(R.id.ll_sex)
    LinearLayout mLlSex;
    @BindView(R.id.ll_signature)
    LinearLayout mLlSignature;
    private PopupWindow mPopupWindow;
    private Button mBtnPopMan;
    private Button mBtnPopWoman;
    private Button mBtnPopSecret;
    private Button mBtnPopCancel;
    private String mName;
    private String mSignature;
    String token = (String) SpUtil.getParam(Constants.TOKEN, "");
    private String mPhoto;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, PersonalInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected PersonalPresenter initPresenter() {
        return new PersonalPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);

        String name = (String) SpUtil.getParam(Constants.USER_NAME, "");
        String singnature = (String) SpUtil.getParam(Constants.SINGNATURE, "");
        String gender = (String) SpUtil.getParam(Constants.GENDER, "");
        mPhoto = (String) SpUtil.getParam(Constants.PHOTO, "");


        Glide.with(this).load(mPhoto).into(mIvHead);
        mTvUserName.setText(name);
        mTvSex.setText(gender);
        mTvWrite.setText(singnature);
    }


    @OnClick({R.id.iv_back, R.id.ll_head, R.id.ll_ninkName, R.id.ll_sex, R.id.ll_signature})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_head:
                Intent intent = new Intent(this, HeadActivity.class);
                startActivityForResult(intent, 100);//跳转到修改头像页面
                break;
            case R.id.ll_ninkName:
                Intent intent1 = new Intent(this, UserNameActivity.class);
                startActivityForResult(intent1, 300);//跳转到修改昵称页面
                break;
            case R.id.ll_sex:
                initPop();
                break;
            case R.id.ll_signature:
                Intent intent2 = new Intent(this, SignatureActivity.class);
                startActivityForResult(intent2, 500);//跳转到修改签名页面
                break;
        }
    }

    //回传的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {//传回来的头像
            mPhoto = data.getStringExtra(Constants.PHOTO);
            Glide.with(this).load(mPhoto).into(mIvHead);
        }
        if (requestCode == 300 && resultCode == 400) {//传回来的昵称
            mName = data.getStringExtra("name");
            if (!TextUtils.isEmpty(mName)) {
                mTvUserName.setText(mName);
                ToastUtil.showShort("修改成功!");
            } else {
                ToastUtil.showShort("不能为空");
            }
        }
        if (requestCode == 500 && resultCode == 600) {//传回来的签名
            mSignature = data.getStringExtra("write");
            if (!TextUtils.isEmpty(mSignature)) {
                mTvWrite.setText(mSignature);
                ToastUtil.showShort("修改成功!");
            } else {
                ToastUtil.showShort("不能为空");
            }
        }

        updateInfo();
    }

    public void updateInfo() {
        mPresenter.upDateInfo(mPhoto, mTvUserName.getText().toString(), mTvSex.getText().toString(), mTvWrite.getText().toString(), token);
    }

    private void initPop() {
        mPopupWindow = new PopupWindow();
        View inflate = LayoutInflater.from(PersonalInfoActivity.this).inflate(R.layout.personal_sex_popupwindow, null);
        mPopupWindow.setContentView(inflate);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setBackgroundDrawable(null);
        mPopupWindow.showAtLocation(mTvWrite, Gravity.BOTTOM, 0, 0);

        popBackground();//popupWindow背景

        mBtnPopMan = inflate.findViewById(R.id.btn_pop_man);
        mBtnPopWoman = inflate.findViewById(R.id.btn_pop_woman);
        mBtnPopSecret = inflate.findViewById(R.id.btn_pop_secret);
        mBtnPopCancel = inflate.findViewById(R.id.btn_pop_cancel);

        mBtnPopMan.setOnClickListener(this);
        mBtnPopWoman.setOnClickListener(this);
        mBtnPopSecret.setOnClickListener(this);
        mBtnPopCancel.setOnClickListener(this);
    }

    private void popBackground() {
        /**
         * 点击popupWindow让背景变暗
         */
        final WindowManager.LayoutParams lp = PersonalInfoActivity.this.getWindow().getAttributes();
        lp.alpha = 0.7f;//代表透明程度，范围为0 - 1.0f
        PersonalInfoActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        PersonalInfoActivity.this.getWindow().setAttributes(lp);
        /**
         * 退出popupWindow时取消暗背景
         */
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1.0f;
                PersonalInfoActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                PersonalInfoActivity.this.getWindow().setAttributes(lp);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pop_man:
                String man = mBtnPopMan.getText().toString();
                mTvSex.setText(man);
                updateInfo();
                break;
            case R.id.btn_pop_woman:
                String woman = mBtnPopWoman.getText().toString();
                mTvSex.setText(woman);
                updateInfo();
                break;
            case R.id.btn_pop_secret:
                String secret = mBtnPopSecret.getText().toString();
                mTvSex.setText(secret);
                updateInfo();
                break;
            case R.id.btn_pop_cancel:
                break;
        }
        ToastUtil.showShort("修改成功!");
        mPopupWindow.dismiss();
    }

    @Override
    public void onSuccess(String string) {
        SpUtil.setParam(Constants.USER_NAME, mTvUserName.getText().toString());
        SpUtil.setParam(Constants.SINGNATURE, mTvWrite.getText().toString());
        SpUtil.setParam(Constants.GENDER, mTvSex.getText().toString());
        SpUtil.setParam(Constants.PHOTO, mPhoto);
    }

    @Override
    public void onFailed(String string) {

    }
    //400-188-5040-1
}
