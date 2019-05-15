package com.jiyun.everywheretrip.model.main;

import android.util.Log;

import com.jiyun.everywheretrip.api.main.MainApiService;
import com.jiyun.everywheretrip.base.BaseModel;
import com.jiyun.everywheretrip.bean.main.BanMiCirCuitBean;
import com.jiyun.everywheretrip.bean.main.BanMiDetailsBean;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.net.RxUtils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by $sl on 2019/5/5 21:16.
 */
public class BanMiCircuitModel extends BaseModel {
    public void getData(int banmiId, int page, String token, final ResultCallBack<BanMiCirCuitBean> callBack) {
        MainApiService apiserver = HttpUtils.getInstance().getApiserver(MainApiService.url, MainApiService.class);
        Observable<BanMiCirCuitBean> observable = apiserver.getBanmiCirCuit(banmiId, page, token);
        observable.compose(RxUtils.<BanMiCirCuitBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<BanMiCirCuitBean>() {
                    @Override
                    public void onNext(BanMiCirCuitBean banMiCirCuitBean) {
                        callBack.onSuccess(banMiCirCuitBean);
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
