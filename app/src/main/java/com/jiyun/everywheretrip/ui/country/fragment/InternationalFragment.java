package com.jiyun.everywheretrip.ui.country.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseFragment;
import com.jiyun.everywheretrip.bean.country.CountryBean;
import com.jiyun.everywheretrip.presenter.EmptyPresenter;
import com.jiyun.everywheretrip.presenter.country.CountryPresenter;
import com.jiyun.everywheretrip.ui.country.adapter.MyStatePagerAdapter;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.view.EmptyView;
import com.jiyun.everywheretrip.view.country.CountryView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * A simple {@link Fragment} subclass.
 */
public class InternationalFragment extends BaseFragment<CountryView, CountryPresenter> implements CountryView {

    @BindView(R.id.tab)
    VerticalTabLayout mTab;
    @BindView(R.id.fl_container)
    FrameLayout mFl;
    private ArrayList<Fragment> mList;
    private MyStatePagerAdapter mAdapter;
    private FragmentManager mManager;
    private String mToken;

    @Override
    protected CountryPresenter initPresenter() {
        return new CountryPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_international;
    }

    @Override
    protected void initData() {
        mToken = (String) SpUtil.getParam("token", "");
        mPresenter.getCouontryData(mToken);
    }

    @Override
    protected void initView() {
        mList = new ArrayList<>();
        mManager = getChildFragmentManager();
        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.add(R.id.fl_container,new InternationalListFragment()).commit();
    }
    @Override
    public void setData(CountryBean countryBean) {
        List<CountryBean.ResultBean.CountriesBean> countries = countryBean.getResult().getCountries();
        for (int i = 0; i < countries.size(); i++) {
            mList.add(new InternationalListFragment(countries.get(i).getId()+""));
        }

        mTab.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return countries.size();
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                ITabView.TabTitle title = new ITabView.TabTitle.Builder()
                        .setContent(countries.get(position).getName())
                        .setTextColor(Color.RED,Color.BLACK)
                        .build();
                return title;
            }

            @Override
            public int getBackground(int position) {
                return 0;
            }
        });
    }


}
