package com.jiyun.everywheretrip.presenter.login;

import com.jiyun.everywheretrip.R;
import com.jiyun.everywheretrip.api.login.LoginApiServer;
import com.jiyun.everywheretrip.base.BaseApp;
import com.jiyun.everywheretrip.base.BasePresenter;
import com.jiyun.everywheretrip.base.BaseView;
import com.jiyun.everywheretrip.bean.login.VerifyCodeBean;
import com.jiyun.everywheretrip.model.login.LoginModel;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.utils.Logger;
import com.jiyun.everywheretrip.view.login.VerifyView;

/**
 * Created by $sl on 2019/5/4 20:19.
 */
public class VerifyPresenter extends BasePresenter<VerifyView> implements ResultCallBack<VerifyCodeBean> {
    private LoginModel mLoginModel;

    @Override
    protected void initModel() {
        mLoginModel = new LoginModel();
        mModels.add(mLoginModel);
    }

    public void getVerifyCode() {
        mLoginModel.getVerifyCode(this);
    }

    @Override
    public void onSuccess(VerifyCodeBean bean) {
        if (bean != null && bean.getCode() == LoginApiServer.SUCCESS_CODE){
            if (mBaseView != null){
                mBaseView.setData(bean.getData());
                Logger.println(bean.getData());
            }
        }else {
            if (mBaseView != null){
                mBaseView.toastShort(BaseApp.getRes().getString(R.string.get_verify_fail));
            }
        }
    }

    @Override
    public void onFail(String msg) {

    }
}
