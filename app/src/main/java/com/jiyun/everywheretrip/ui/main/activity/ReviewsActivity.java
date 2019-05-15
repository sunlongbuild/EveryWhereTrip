package com.jiyun.everywheretrip.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.bean.main.ReviewsBean;
import com.jiyun.everywheretrip.presenter.main.ReviewsPresenter;
import com.jiyun.everywheretrip.ui.main.adapter.MyReviewsAdapter;
import com.jiyun.everywheretrip.utils.Logger;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.view.main.ReviewsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReviewsActivity extends BaseActivity<ReviewsView, ReviewsPresenter> implements ReviewsView {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.lv)
    RecyclerView mLv;
    private List<ReviewsBean.ResultBean.ReviewBean> mList;
    private MyReviewsAdapter mAdapter;
    private int mRouteId;
    private String mToken;
    private static final String TAG = "ReviewsActivity----";

    @Override
    protected ReviewsPresenter initPresenter() {
        return new ReviewsPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reviews;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);

        Intent intent = getIntent();
        mRouteId = intent.getIntExtra("id", 0);
        mToken = (String) SpUtil.getParam("token", "");

        mList = new ArrayList<>();

        mAdapter = new MyReviewsAdapter(this, mList);
        mLv.setAdapter(mAdapter);
        mLv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        showLoading();
        mPresenter.getReviews(mRouteId, 1, mToken);
    }

    @Override
    public void setData(ReviewsBean reviewBean) {
        mList.addAll(reviewBean.getResult().getReview());
        mAdapter.setList(reviewBean.getResult().getReview());
        mAdapter.notifyDataSetChanged();
        hideLoading();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
