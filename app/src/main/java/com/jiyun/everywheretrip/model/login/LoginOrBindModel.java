package com.jiyun.everywheretrip.model.login;

import com.jiyun.everywheretrip.api.login.EveryWhereService;
import com.jiyun.everywheretrip.base.BaseModel;
import com.jiyun.everywheretrip.bean.login.LoginInfo;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.net.RxUtils;

import io.reactivex.disposables.Disposable;

/**
 * Created by $sl on 2019/5/3 15:05.
 */
public class LoginOrBindModel extends BaseModel{
    public void loginSina(final ResultCallBack<LoginInfo> callBack, String uid) {
        EveryWhereService apiserver = HttpUtils.getInstance().getApiserver(EveryWhereService.BASE_URL, EveryWhereService.class);
        apiserver.postWeiboLogin(uid)
                .compose(RxUtils.<LoginInfo>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<LoginInfo>() {
                    @Override
                    public void error(String msg) {

                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(LoginInfo loginInfo) {
                        if (loginInfo != null) {
                            if (loginInfo.getCode() == EveryWhereService.SUCCESS_CODE) {
                                callBack.onSuccess(loginInfo);
                            } else {
                                callBack.onFail(loginInfo.getDesc());
                            }
                        }
                    }
                });
    }
}
