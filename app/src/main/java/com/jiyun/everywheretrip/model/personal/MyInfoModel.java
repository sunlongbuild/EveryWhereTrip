package com.jiyun.everywheretrip.model.personal;

import com.jiyun.everywheretrip.api.personal.PersonalApiService;
import com.jiyun.everywheretrip.base.BaseModel;
import com.jiyun.everywheretrip.bean.main.MyInfoBean;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.net.RxUtils;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by $sl on 2019/5/8 19:04.
 */
public class MyInfoModel extends BaseModel {
    public void getMyInfo(String token, final ResultCallBack<MyInfoBean> callBack) {
        PersonalApiService apiserver = HttpUtils.getInstance().getApiserver(PersonalApiService.baseUrl, PersonalApiService.class);
        Observable<MyInfoBean> observable = apiserver.getInfo(token);
        observable.compose(RxUtils.<MyInfoBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<MyInfoBean>() {
                    @Override
                    public void onNext(MyInfoBean MyInfoBean) {
                        callBack.onSuccess(MyInfoBean);
                    }

                    @Override
                    public void error(String msg) {
                        callBack.onFail(msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {

                    }
                });
    }
}
