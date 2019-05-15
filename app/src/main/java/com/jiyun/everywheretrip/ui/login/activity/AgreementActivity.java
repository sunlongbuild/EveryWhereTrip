package com.jiyun.everywheretrip.ui.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseActivity;
import com.jiyun.everywheretrip.presenter.EmptyPresenter;
import com.jiyun.everywheretrip.view.EmptyView;
import com.just.agentweb.AgentWeb;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by $sl on 2019/5/5 10:18.
 */
public class AgreementActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {
    @BindView(R.id.container)
    LinearLayout mcontainer;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    private AgentWeb mAgentWeb;

    public static void startActivity(Context context){
        Intent intent = new Intent(context, AgreementActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void initView() {
        //亮色的模式,会将状态栏的文字修改为黑色
        StatusBarUtil.setLightMode(this);

        mToolBar.setTitle("");
        mToolBar.setNavigationIcon(R.drawable.back_white);
        setSupportActionBar(mToolBar);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) mcontainer, new LinearLayout.LayoutParams(-1, -1))
                .closeIndicator()//关闭进度条
                .createAgentWeb()
                .ready()
                .go("https://api.banmi.com/app2017/agreement.html");

            //获取网页的标题设置给ToolBar
            mAgentWeb.getWebCreator().getWebView().setWebChromeClient(new WebChromeClient(){
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    if(!TextUtils.isEmpty(title)){
                        mTvTitle.setText(title);
                    }
                    super.onReceivedTitle(view, title);
                }
            });
    }
    //点击返回按钮关闭页面
    @Override
    protected void initListener() {
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAgentWeb.back()) {
                    finish();
                }
            }
        });
    }

    /**
     * 跟随 Activity Or Fragment 生命周期,释放CPU更省电 。
     */
    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

}
