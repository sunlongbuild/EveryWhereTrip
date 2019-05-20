package com.jiyun.everywheretrip.ui.country.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import com.jiyun.everywheretrip.bean.country.CityBean;
import com.jiyun.everywheretrip.ui.country.adapter.InLandMyAdapter;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.widget.SideBar;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseFragment;
import com.jiyun.everywheretrip.bean.country.CountryBean;
import com.jiyun.everywheretrip.presenter.country.CountryPresenter;
import com.jiyun.everywheretrip.view.country.CountryView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class InLandFragment extends BaseFragment<CountryView, CountryPresenter> implements CountryView {

    @BindView(R.id.lv)
    ListView mLv;
    @BindView(R.id.side_bar)
    SideBar mSideBar;
    private List<CityBean> mList;
    private InLandMyAdapter mAdapter;
    private static final String TAG = "InLandFragment----";
    private String mToken;

    @Override
    protected CountryPresenter initPresenter() {
        return new CountryPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_in_land;
    }

    @Override
    protected void initView() {
        mList = new ArrayList<>();

        mAdapter = new InLandMyAdapter(getContext(), mList);
        mLv.setAdapter(mAdapter);
        mSideBar.setOnStrSelectCallBack(new SideBar.ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                for (int i = 0; i < mList.size(); i++) {
                    if (selectStr.equalsIgnoreCase(mList.get(i).getFirstLetter())) {
                        mLv.setSelection(i); // 选择到首字母出现的位置
                        return;
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        mToken = (String) SpUtil.getParam("token", "");
        mPresenter.getCouontryData(mToken);
    }

    @Override
    public void setData(CountryBean countryBean) {
        List<CountryBean.ResultBean.ChinaBean.CitiesBean> cities = countryBean.getResult().getChina().getCities();
        for (int i = 0; i <cities.size() ; i++) {
            mList.add(new CityBean(cities.get(i).getName()));
        }
        Collections.sort(mList); // 对list进行排序，需要让User实现Comparable接口重写compareTo方法
        mAdapter.notifyDataSetChanged();
    }


}
