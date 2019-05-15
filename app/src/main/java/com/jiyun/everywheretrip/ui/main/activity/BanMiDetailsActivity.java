package com.jiyun.everywheretrip.ui.main.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.bean.main.BanMiDetailsBean;
import com.jiyun.everywheretrip.presenter.main.BanMiDetailsPresenter;
import com.jiyun.everywheretrip.ui.main.adapter.MyPagerAdapter;
import com.jiyun.everywheretrip.ui.main.fragment.CircuitFragment;
import com.jiyun.everywheretrip.ui.main.fragment.DynamicStateFragment;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.view.main.BanMiDetailsView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BanMiDetailsActivity extends BaseActivity<BanMiDetailsView, BanMiDetailsPresenter> implements BanMiDetailsView {

    private static final String TAG = "BanMiDetailsActivity----";

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.toolBar_title)
    TextView mToolBarTitle;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.iv_care)
    ImageView mIvCare;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.tv_caring)
    TextView mTvCaring;
    @BindView(R.id.tv_cared)
    TextView mTvCared;
    private ArrayList<Fragment> mList;
    private int mBanmiId;
    private String mToken;

    @Override
    protected BanMiDetailsPresenter initPresenter() {
        return new BanMiDetailsPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ban_mi_details;
    }

    @Override
    protected void initData() {
        showLoading();
        mPresenter.getData(mBanmiId, mToken, 1);

    }

    //请求回来的数据
    @SuppressLint("LongLogTag")
    @Override
    public void setData(BanMiDetailsBean banMiDetailsBean) {
        BanMiDetailsBean.ResultBean.BanmiBean banmiBean = banMiDetailsBean.getResult().getBanmi();

        mTvName.setText(banmiBean.getName());
        mTvLocation.setText(banmiBean.getLocation());
        mTvType.setText(banmiBean.getOccupation());
        mTvContent.setText(banmiBean.getIntroduction());
        mTvCaring.setText(banmiBean.getFollowing() + "人关注");
        Glide.with(this).load(banmiBean.getPhoto()).into(mIvHead);
        hideLoading();
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);//设置亮色模式
        Intent intent = getIntent();
        mBanmiId = intent.getIntExtra("banmiId", 0);
        mToken = (String) SpUtil.getParam("token", "");


        mList = new ArrayList<>();
        mList.add(new DynamicStateFragment());
        mList.add(new CircuitFragment(mBanmiId));

        mTab.addTab(mTab.newTab().setText("动态"));
        mTab.addTab(mTab.newTab().setText("线路"));

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), mList);
        mVp.setAdapter(adapter);
        //相互关联
        mVp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab));
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mVp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @OnClick({R.id.iv_back, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                break;
        }
    }
}
