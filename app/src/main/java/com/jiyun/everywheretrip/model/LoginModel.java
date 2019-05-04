package com.jiyun.everywheretrip.model;

import com.jiyun.everywheretrip.api.LoginApiServer;
import com.jiyun.everywheretrip.base.BaseModel;
import com.jiyun.everywheretrip.bean.login.VerifyCodeBean;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.net.RxUtils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by $sl on 2019/5/3 15:05.
 */
public class LoginModel extends BaseModel{
    private static final String TAG = "LoginModel";

    public void getVerifyCode(final ResultCallBack<VerifyCodeBean> callBack) {
        LoginApiServer apiserver = HttpUtils.getInstance().getApiserver(LoginApiServer.sBaseUrl, LoginApiServer.class);
        final Observable<VerifyCodeBean> verifyCode = apiserver.getVerifyCode();

        verifyCode.compose(RxUtils.<VerifyCodeBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<VerifyCodeBean>() {
                    @Override
                    public void error(String msg) {

                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(VerifyCodeBean verifyCodeBean) {
                        callBack.onSuccess(verifyCodeBean);
                    }
                });
    }
}
