package com.jiyun.everywheretrip.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jiyun.everywheretrip.widget.LoadingDialog;

import butterknife.ButterKnife;

/**
 * Created by $sl on 2019/4/30 9:33.
 */
public abstract class BaseActivity<V extends BaseView,P extends BasePresenter> extends AppCompatActivity implements BaseView {
    protected P mPresenter;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mPresenter = initPresenter();
        if(mPresenter != null){
            mPresenter.bind((V)this);
        }
        initView();
        initListener();
        initData();
    }

    protected abstract P initPresenter();

    protected void initData() {

    }

    protected void initListener() {

    }

    protected void initView(){

    }

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestory();
        mPresenter = null;
    }
    public void showLoading(){
        if(mLoadingDialog !=null){
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show();
    }
    public void hideLoading(){
        if(mLoadingDialog !=null && mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
        }
    }
}
