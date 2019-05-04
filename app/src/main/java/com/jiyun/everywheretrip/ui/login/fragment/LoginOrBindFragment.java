package com.jiyun.everywheretrip.ui.login.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.base.BaseFragment;
import com.jiyun.everywheretrip.presenter.login.LoginOrBindPresenter;
import com.jiyun.everywheretrip.view.login.LoginOrBindView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by $sl on 2019/5/4 16:27.
 */
public class LoginOrBindFragment extends BaseFragment<LoginOrBindView, LoginOrBindPresenter> implements LoginOrBindView {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.btn_send_verify)
    Button mBtnSendVerify;
    @BindView(R.id.iv_wechat)
    ImageView mIvWechat;
    @BindView(R.id.iv_qq)
    ImageView mIvQq;
    @BindView(R.id.iv_sina)
    ImageView mIvSina;

    @Override
    protected LoginOrBindPresenter initPresenter() {
        return new LoginOrBindPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_bind;
    }

    @Override
    public String getPhone() {
        return mEtPhone.getText().toString().trim();
    }

    @OnClick({R.id.btn_send_verify, R.id.iv_wechat, R.id.iv_qq, R.id.iv_sina})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send_verify:
                addVerifyFragment();
                break;
            case R.id.iv_wechat:
                break;
            case R.id.iv_qq:
                break;
            case R.id.iv_sina:
                break;
        }
    }

    private void addVerifyFragment() {
        if (TextUtils.isEmpty(getPhone())){
            return;
        }
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        //添加到回退栈
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.fl_container,new VerifyFragment()).commit();
    }

    @Override
    protected void initListenter() {
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changedBtnState(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    //改变按钮的状态
    private void changedBtnState(CharSequence s) {
        if(TextUtils.isEmpty(s)){
            mBtnSendVerify.setBackgroundResource(R.drawable.bg_btn_ea_r15);
        }else {
            mBtnSendVerify.setBackgroundResource(R.drawable.bg_btn_fa6a13_r15);
        }
    }
}
