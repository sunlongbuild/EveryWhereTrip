package com.jiyun.everywheretrip.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.TokenWatcher;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseFragment;
import com.jiyun.everywheretrip.base.Constants;
import com.jiyun.everywheretrip.bean.main.HomeBean;
import com.jiyun.everywheretrip.presenter.main.HomePresenter;
import com.jiyun.everywheretrip.ui.main.activity.HomeDetailsActivity;
import com.jiyun.everywheretrip.ui.main.activity.ImageTripActivity;
import com.jiyun.everywheretrip.ui.main.adapter.MyHomeAdapter;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.view.main.HomeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by $sl on 2019/5/5 20:06.
 */
public class HomeFragment extends BaseFragment<HomeView, HomePresenter> implements HomeView, OnLoadMoreListener, OnRefreshListener, MyHomeAdapter.OnItemClickListener {
    @BindView(R.id.lv)
    RecyclerView mLv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private ArrayList<HomeBean.ResultBean.BannersBean> mBannerList;
    private ArrayList<HomeBean.ResultBean.RoutesBean> mArticleList;
    private MyHomeAdapter mAdapter;
    private static final String TAG = "HomeFragment----";
    private int page = 1;

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mBannerList = new ArrayList<>();
        mArticleList = new ArrayList<>();

        mAdapter = new MyHomeAdapter(getContext(), mBannerList, mArticleList);
        mLv.setAdapter(mAdapter);
        mLv.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    protected void initData() {
        String token = (String) SpUtil.getParam(Constants.TOKEN, "");
        mPresenter.getData(page, token);
    }

    @Override
    public void onSuccess(HomeBean bean) {
        mBannerList.addAll(bean.getResult().getBanners());
        mArticleList.addAll(bean.getResult().getRoutes());
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onFailed(String string) {
        Log.e(TAG, "onFailed: " + string);
    }

    @Override
    protected void initListenter() {
        mRefreshLayout.setOnLoadMoreListener(this);//加载更多
        mRefreshLayout.setOnRefreshListener(this);//下拉刷新
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        initData();
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mArticleList.clear();
        mBannerList.clear();
        initData();
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh();
    }


    @Override
    public void onClick(HomeBean.ResultBean.RoutesBean bean, int position) {
        int id = bean.getId();
        String price = bean.getPrice();

        if (bean.getType().equals("bundle")) {
            Intent intent = new Intent(getContext(), ImageTripActivity.class);
            intent.putExtra("url", bean.getContentURL());
            intent.putExtra("title",bean.getTitle());
            startActivity(intent);
        } else {
            Intent intent1 = new Intent(getContext(), HomeDetailsActivity.class);
            intent1.putExtra("id", id);
            intent1.putExtra("price", price);
            startActivity(intent1);
        }
    }
}
