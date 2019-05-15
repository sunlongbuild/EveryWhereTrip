package com.jiyun.everywheretrip.ui.guide.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.presenter.EmptyPresenter;
import com.jiyun.everywheretrip.ui.guide.adapter.ViewPagerAdatper;
import com.jiyun.everywheretrip.ui.login.activity.LoginActivity;
import com.jiyun.everywheretrip.ui.main.activity.MainActivity;
import com.jiyun.everywheretrip.utils.PreviewIndicator;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.view.EmptyView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView, ViewPager.OnPageChangeListener {
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.indicator)
    PreviewIndicator mIndicator;
    @BindView(R.id.btn_begin)
    Button mBtnBegin;

    private ArrayList<View> mViewList;

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {

//        SharedPreferences sp = getSharedPreferences("GuidePager", MODE_PRIVATE);
//        boolean b = sp.getBoolean("flag", false);
//        if (b) {
//            //开始跳转到应用页面
//            startActivity(new Intent(GuideActivity.this, LoginActivity.class));
//            finish();
//        }

        mViewList = new ArrayList<View>();
        LayoutInflater layoutInflater = getLayoutInflater().from(GuideActivity.this);

        View view1 = layoutInflater.inflate(R.layout.guide_item1, null);
        View view2 = layoutInflater.inflate(R.layout.guide_item2, null);
        View view3 = layoutInflater.inflate(R.layout.guide_item3, null);

        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);

        ViewPagerAdatper adatper = new ViewPagerAdatper(mViewList);
        mVp.setAdapter(adatper);
    }

    @Override
    protected void initData() {
        mIndicator.initSize(80, 32, 6);
        mIndicator.setNumbers(3);

        mVp.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        mIndicator.setSelected(i);
        if (i == 2) {
            mBtnBegin.setVisibility(View.VISIBLE);
            mIndicator.setVisibility(View.GONE);
        } else {
            mBtnBegin.setVisibility(View.GONE);
            mIndicator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @OnClick(R.id.btn_begin)
    public void onViewClicked() {
        startActivity(new Intent(GuideActivity.this, LoginActivity.class));
    }

}
