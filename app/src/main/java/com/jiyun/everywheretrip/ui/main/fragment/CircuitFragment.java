package com.jiyun.everywheretrip.ui.main.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseFragment;
import com.jiyun.everywheretrip.bean.main.BanMiCirCuitBean;
import com.jiyun.everywheretrip.presenter.main.BanMiCircuitPresenter;
import com.jiyun.everywheretrip.ui.main.adapter.MyBanMiCircuitAdapter;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.view.main.BanMiCircuitView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class CircuitFragment extends BaseFragment<BanMiCircuitView, BanMiCircuitPresenter> implements BanMiCircuitView {

    @BindView(R.id.lv)
    RecyclerView mLv;
    private int banmiId;
    private ArrayList<BanMiCirCuitBean.ResultBean.RoutesBean> mList;
    private MyBanMiCircuitAdapter mAdapter;
    private static final String TAG = "CircuitFragment----";

    @Override
    protected BanMiCircuitPresenter initPresenter() {
        return new BanMiCircuitPresenter();
    }

    @SuppressLint("ValidFragment")
    public CircuitFragment(int banmiId) {
        this.banmiId = banmiId;
        Log.e(TAG, "CircuitFragment: " + banmiId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circuit;
    }

    @Override
    protected void initView() {
        mList = new ArrayList<>();
        mAdapter = new MyBanMiCircuitAdapter(getContext(), mList);
        mLv.setAdapter(mAdapter);
        mLv.setNestedScrollingEnabled(false);//解决滑动卡顿
        mLv.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    protected void initData() {
        String token = (String) SpUtil.getParam("token", "");
        showLoading();
        mPresenter.getData(banmiId, 1, token);
    }

    @Override
    public void setData(BanMiCirCuitBean banMiCirCuitBean) {
        mList.addAll(banMiCirCuitBean.getResult().getRoutes());
        mAdapter.notifyDataSetChanged();
        hideLoading();
    }

}
