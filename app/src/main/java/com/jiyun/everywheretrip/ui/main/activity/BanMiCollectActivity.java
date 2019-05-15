package com.jiyun.everywheretrip.ui.main.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.presenter.EmptyPresenter;
import com.jiyun.everywheretrip.view.EmptyView;

import butterknife.BindView;
import butterknife.OnClick;

public class BanMiCollectActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {
    @BindView(R.id.lv)
    RecyclerView mLv;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ban_mi_collect;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);


    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


}
