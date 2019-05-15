package com.jiyun.everywheretrip.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiyun.everywheretrip.utils.ToastUtil;
import com.jiyun.everywheretrip.widget.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by $sl on 2019/4/30 9:55.
 */
public abstract class BaseFragment<V extends BaseView, P extends BasePresenter> extends Fragment implements BaseView {
    protected P mPresenter;
    private LoadingDialog mLoadingDialog;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);
        mUnbinder = ButterKnife.bind(this, view);
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.bind((V) this);
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

    protected void initView() {

    }

    protected abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mPresenter.onDestory();
        mPresenter = null;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getContext());
        }
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void toastShort(String msg) {
        ToastUtil.showShort(msg);
    }
}
