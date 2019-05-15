package com.jiyun.everywheretrip.presenter.login;

import com.jiyun.everywheretrip.base.BasePresenter;
import com.jiyun.everywheretrip.bean.login.VerifyCodeBean;
import com.jiyun.everywheretrip.model.login.LoginModel;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.view.login.LoginView;

/**
 * Created by $sl on 2019/5/3 15:08.
 */
public class LoginPresnter extends BasePresenter<LoginView> {
    private LoginModel mLoginModel;

    @Override
    protected void initModel() {
        mLoginModel = new LoginModel();
        mModels.add(mLoginModel);
    }

    public void getVerifyCode() {
        mLoginModel.getVerifyCode(new ResultCallBack<VerifyCodeBean>() {
            @Override
            public void onSuccess(VerifyCodeBean bean) {

            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
