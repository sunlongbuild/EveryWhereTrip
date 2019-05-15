package com.jiyun.everywheretrip.model.main;

import android.util.Log;

import com.jiyun.everywheretrip.api.main.MainApiService;
import com.jiyun.everywheretrip.base.BaseModel;
import com.jiyun.everywheretrip.bean.main.BanMiDetailsBean;
import com.jiyun.everywheretrip.bean.main.HomeDetailsBean;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.net.RxUtils;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by $sl on 2019/5/5 21:16.
 */
public class BanMiDetailsModel extends BaseModel {
    private static final String TAG = "BanMiDetailsModel----";
    public void getData(int banmiId, String token, int page, final ResultCallBack<BanMiDetailsBean> callBack) {
        MainApiService apiserver = HttpUtils.getInstance().getApiserver(MainApiService.url, MainApiService.class);
        Observable<BanMiDetailsBean> observable = apiserver.getBanMiDetails(banmiId, token, page);
        observable.compose(RxUtils.<BanMiDetailsBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<BanMiDetailsBean>() {
                    @Override
                    public void onNext(BanMiDetailsBean banMiDetailsBean) {
                        Log.e(TAG, "onNext: " + banMiDetailsBean.getResult().getBanmi());
                        callBack.onSuccess(banMiDetailsBean);
                    }

                    @Override
                    public void error(String msg) {
                        callBack.onFail(msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }
                });
    }
}
