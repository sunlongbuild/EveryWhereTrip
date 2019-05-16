package com.jiyun.everywheretrip.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.base.Constants;
import com.jiyun.everywheretrip.bean.main.MyInfoBean;
import com.jiyun.everywheretrip.presenter.EmptyPresenter;
import com.jiyun.everywheretrip.presenter.personal.MyInfoPresenter;
import com.jiyun.everywheretrip.ui.main.adapter.MyPagerAdapter;
import com.jiyun.everywheretrip.ui.main.fragment.BanMiFragment;
import com.jiyun.everywheretrip.ui.main.fragment.CollectFragment;
import com.jiyun.everywheretrip.ui.main.fragment.HomeFragment;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.view.EmptyView;
import com.jiyun.everywheretrip.view.personal.MyInfoView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MyInfoView, MyInfoPresenter> implements MyInfoView, View.OnClickListener {

    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nv)
    NavigationView mNv;
    @BindView(R.id.dl)
    DrawerLayout mDl;
    private ArrayList<Fragment> mList;
    private TextView mName;
    private ImageView mImg;
    private TextView mWrite;
    private String mToken;//sp中获取的token
    private String mPhoto;//sp中获取的头像

    public static void startAct(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected MyInfoPresenter initPresenter() {
        return new MyInfoPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mToken = (String) SpUtil.getParam(Constants.TOKEN, "");

        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ab);
        setSupportActionBar(mToolbar);

        initNavigationView();

        StatusBarUtil.setLightMode(this);

        String hello = "你好.";

        mList = new ArrayList<>();
        mList.add(new HomeFragment());
        mList.add(new BanMiFragment());

        mTab.addTab(mTab.newTab().setText("首页").setIcon(R.drawable.main_activity_home));
        mTab.addTab(mTab.newTab().setText("伴米").setIcon(R.drawable.main_activity_banmi));

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), mList);
        mVp.setAdapter(adapter);

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

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getMyInfo(mToken);
    }

    private void initNavigationView() {
        View headerView = mNv.getHeaderView(0);
        // 头像
        mPhoto = (String) SpUtil.getParam(Constants.PHOTO, "");
        mImg = headerView.findViewById(R.id.iv_headPhoto);
        Glide.with(this).load(mPhoto).into(mImg);

        // 用户名
        String name = (String) SpUtil.getParam(Constants.USER_NAME, "");
        mName = headerView.findViewById(R.id.tv_name);
        mName.setText(name);

        // 签名
        String signature = (String) SpUtil.getParam(Constants.SINGNATURE, "");
        mWrite = headerView.findViewById(R.id.tv_write);
        mWrite.setText(signature);

        //收藏和个人信息页面
        TextView mTvMyCollcet = headerView.findViewById(R.id.tv_myCollect);
        RelativeLayout mRlPersonalInfo = headerView.findViewById(R.id.rl_personalInfo);
        TextView mTvFollowing = headerView.findViewById(R.id.tv_following);
        //收藏和个人信息页面监听
        mTvMyCollcet.setOnClickListener(this);
        mRlPersonalInfo.setOnClickListener(this);
        mTvFollowing.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_personalInfo://头像
                PersonalInfoActivity.startAct(MainActivity.this);
                break;
            case R.id.tv_myCollect://收藏
                Intent intent = new Intent(MainActivity.this, BanMiCollectActivity.class);
                startActivity(intent);
            case R.id.tv_following:
                Intent intent1 = new Intent(MainActivity.this, BanMiFollowingActivity.class);
                startActivity(intent1);
        }
    }

    @Override
    protected void initListener() {
        //点击toolbar中的图片开启侧滑菜单
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDl.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.getMyInfo(mToken);
    }

    @Override
    public void setData(MyInfoBean infoBean) {
        MyInfoBean.ResultBean result = infoBean.getResult();

        Glide.with(this).load(result.getPhoto()).into(mImg);
        mName.setText(result.getUserName());
        mWrite.setText(result.getDescription());

        SpUtil.setParam(Constants.USER_NAME, result.getUserName());
        SpUtil.setParam(Constants.SINGNATURE, result.getDescription());
        SpUtil.setParam(Constants.GENDER, result.getGender());
        SpUtil.setParam(Constants.PHOTO, result.getPhoto());
    }
}
