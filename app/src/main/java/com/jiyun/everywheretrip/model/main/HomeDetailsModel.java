package com.jiyun.everywheretrip.model.main;

import com.jiyun.everywheretrip.api.main.MainApiService;
import com.jiyun.everywheretrip.base.BaseModel;
import com.jiyun.everywheretrip.bean.main.HomeBean;
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
public class HomeDetailsModel extends BaseModel {
    public void getData(int cid, final ResultCallBack<HomeDetailsBean> callBack) {
        MainApiService apiserver = HttpUtils.getInstance().getApiserver(MainApiService.url, MainApiService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("banmi-app-token", "xNYt6wXlaGwZTPzYp13SrUcIMmwuS9ASwCCF3GPID3AzQt6HB2OdklZmphWDyeXBGVF40tBf8l8CYVNDLRlaBY0y7T183EE7sMKasRNLJ6uPSH2a5TGtO2DaHhysbp9e3Q");
        Observable<HomeDetailsBean> homeParticulars = apiserver.getHomeDetails(cid, map);
        homeParticulars.compose(RxUtils.<HomeDetailsBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<HomeDetailsBean>() {
                    @Override
                    public void onNext(HomeDetailsBean HomeDetailsBean) {
                        callBack.onSuccess(HomeDetailsBean);
                    }

                    @Override
                    public void error(String msg) {

                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }
                });
    }
}
