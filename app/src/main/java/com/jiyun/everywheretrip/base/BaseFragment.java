package com.jiyun.everywheretrip.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiyun.everywheretrip.widget.LoadingDialog;

import butterknife.ButterKnife;

/**
 * Created by $sl on 2019/4/30 9:55.
 */
public abstract class BaseFragment<V extends BaseView,P extends BasePresenter> extends Fragment implements BaseView {
    protected P mPresener;
    private LoadingDialog mLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);
        ButterKnife.bind(this,view);
        mPresener = initPresenter();
        if(mPresener != null){
            mPresener.bind((V)this);
        }
        initView();
        initListenter();
        initData();
        return view;
    }

    protected abstract P initPresenter();

    protected void initData() {

    }

    protected void initListenter() {

    }

    protected void initView(){

    }

    protected abstract int getLayoutId();

    public void showLoading(){
        if(mLoadingDialog !=null){
            mLoadingDialog = new LoadingDialog(getContext());
        }
        mLoadingDialog.show();
    }
    public void hideLoading(){
        if(mLoadingDialog !=null && mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
        }
    }
}
