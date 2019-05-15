package com.jiyun.everywheretrip.ui.main.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.presenter.EmptyPresenter;
import com.jiyun.everywheretrip.view.EmptyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageTripActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {
    @BindView(R.id.container)
    LinearLayout mcontainer;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.web)
    WebView mWeb;
    @BindView(R.id.iv_back)
    ImageView mIvBack;

    private String mUrl;
    private String mTitle;

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_trip;
    }

    @Override
    protected void initView() {
        //亮色的模式,会将状态栏的文字修改为黑色
        StatusBarUtil.setLightMode(this);

        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
        //在HomeFragment获取到的详情路径
        mUrl = getIntent().getStringExtra("url") + "?os=android";
        mTitle = getIntent().getStringExtra("title");
        mTvTitle.setText(mTitle);
        mWeb.getSettings().setJavaScriptEnabled(true);
        mWeb.setWebViewClient(new WebViewClient());
        mWeb.loadUrl(mUrl);

        AndroidJs androidJs = new AndroidJs(this);
        mWeb.addJavascriptInterface(androidJs, "android");

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
