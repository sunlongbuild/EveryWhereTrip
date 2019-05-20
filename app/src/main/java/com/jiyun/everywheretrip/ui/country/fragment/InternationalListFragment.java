package com.jiyun.everywheretrip.ui.country.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseFragment;
import com.jiyun.everywheretrip.bean.country.CountryBean;
import com.jiyun.everywheretrip.presenter.country.CountryPresenter;
import com.jiyun.everywheretrip.view.country.CountryView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by $sl on 2019/5/19 10:33.
 */
@SuppressLint("ValidFragment")
public class InternationalListFragment extends BaseFragment<CountryView, CountryPresenter> implements CountryView {
    @BindView(R.id.lv)
    RecyclerView mLv;
    private String id;

    public InternationalListFragment() {}

    public InternationalListFragment(String id) {
        this.id = id;
    }

    @Override
    protected CountryPresenter initPresenter() {
        return new CountryPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_international_list;
    }

    @Override
    public void setData(CountryBean countryBean) {

    }

}
