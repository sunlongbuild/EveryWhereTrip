package com.jiyun.everywheretrip.ui.login.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseFragment;
import com.jiyun.everywheretrip.presenter.login.VerifyPresenter;
import com.jiyun.everywheretrip.view.login.VerifyView;
import com.jungly.gridpasswordview.GridPasswordView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by $sl on 2019/5/4 20:18.
 */
public class VerifyFragment extends BaseFragment<VerifyView, VerifyPresenter> implements VerifyView {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.code)
    GridPasswordView mCode;
    @BindView(R.id.tv_wait)
    TextView mTvWait;
    Unbinder unbinder;

    @Override
    protected VerifyPresenter initPresenter() {
        return new VerifyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_verify;
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        getActivity().finish();
    }

    @Override
    protected void initListenter() {
        mCode.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                mTvWait.setVisibility(View.VISIBLE);
            }
        });
    }
}
