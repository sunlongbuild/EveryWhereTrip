package com.jiyun.everywheretrip.ui.main.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseFragment;
import com.jiyun.everywheretrip.base.Constants;
import com.jiyun.everywheretrip.bean.cover.CityTabBean;
import com.jiyun.everywheretrip.presenter.EmptyPresenter;
import com.jiyun.everywheretrip.presenter.cover.CoverPresenter;
import com.jiyun.everywheretrip.ui.main.adapter.MyPagerAdapter;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.view.EmptyView;
import com.jiyun.everywheretrip.view.cover.CoverView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoverFragment extends BaseFragment<CoverView, CoverPresenter> implements CoverView {

    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.vp)
    ViewPager mVp;
    private String mToken;
    private static final int cityID = 10;
    private ArrayList<Fragment> mList;

    @Override
    protected CoverPresenter initPresenter() {
        return new CoverPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cover;
    }

    @Override
    protected void initView() {
        mList = new ArrayList<>();

    }

    @Override
    protected void initData() {
        mToken = (String) SpUtil.getParam(Constants.TOKEN, "");
        mPresenter.getCityTab(mToken, cityID);
    }

    @Override
    public void setData(CityTabBean cityTabBean) {
        List<CityTabBean.ResultBean.AllTagsBean> allTags = cityTabBean.getResult().getAllTags();
        for (int i = 0; i < allTags.size(); i++) {
            mTab.addTab(mTab.newTab().setText(allTags.get(i).getName()));
            mList.add(new MapFragment());
        }

        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager(), mList);
        mVp.setAdapter(adapter);

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
