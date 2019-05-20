package com.jiyun.everywheretrip.ui.country.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.jaeger.library.StatusBarUtil;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.presenter.EmptyPresenter;
import com.jiyun.everywheretrip.ui.country.fragment.InLandFragment;
import com.jiyun.everywheretrip.ui.country.fragment.InternationalFragment;
import com.jiyun.everywheretrip.ui.main.adapter.MyPagerAdapter;
import com.jiyun.everywheretrip.view.EmptyView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {

    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.vp)
    ViewPager mVp;
    private ArrayList<Fragment> mList;

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);//亮色模式


        mList = new ArrayList<>();
        mList.add(new InLandFragment());
        mList.add(new InternationalFragment());

        mTab.addTab(mTab.newTab().setText("国内"));
        mTab.addTab(mTab.newTab().setText("国际"));

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), mList);
        mVp.setAdapter(adapter);
        //相互关联
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
        mVp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab));
    }
}
