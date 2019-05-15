package com.jiyun.everywheretrip.ui.main.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.base.Constants;
import com.jiyun.everywheretrip.bean.main.BanMiMyFollowingBean;
import com.jiyun.everywheretrip.presenter.main.BanMiFollowingPresenter;
import com.jiyun.everywheretrip.ui.main.adapter.BanMiMyFollowingAdapter;
import com.jiyun.everywheretrip.utils.SpUtil;
import com.jiyun.everywheretrip.view.main.BanMiFollowingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BanMiFollowingActivity extends BaseActivity<BanMiFollowingView, BanMiFollowingPresenter> implements BanMiFollowingView {


    @BindView(R.id.lv)
    RecyclerView mLv;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    private List<BanMiMyFollowingBean.ResultBean.BanmiBean> mList;
    private BanMiMyFollowingAdapter mAdapter;

    @Override
    protected BanMiFollowingPresenter initPresenter() {
        return new BanMiFollowingPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ban_mi_following;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);
        mList = new ArrayList<>();

        mAdapter = new BanMiMyFollowingAdapter(this, mList);
        mLv.setAdapter(mAdapter);
        mLv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        String token = (String) SpUtil.getParam(Constants.TOKEN, "");
        mPresenter.getMyFollowing(token, 1);
    }

    @Override
    public void setData(BanMiMyFollowingBean banMiMyFollowingBean) {
        mList.addAll(banMiMyFollowingBean.getResult().getBanmi());
        mAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
