package com.jiyun.everywheretrip.model.cover;

import com.jiyun.everywheretrip.api.cover.CoverApiService;
import com.jiyun.everywheretrip.base.BaseModel;
import com.jiyun.everywheretrip.bean.cover.CityTabBean;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.net.RxUtils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by $sl on 2019/5/16 20:47.
 */
public class CoverModel extends BaseModel {
    public void getCityTab(String token, int cityID, final ResultCallBack<CityTabBean> callBack){
        CoverApiService apiserver = HttpUtils.getInstance().getApiserver(CoverApiService.url, CoverApiService.class);
        Observable<CityTabBean> observable = apiserver.getCityTab(token, cityID,33);
        observable.compose(RxUtils.<CityTabBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<CityTabBean>() {
                    @Override
                    public void onNext(CityTabBean cityTabBean) {
                        callBack.onSuccess(cityTabBean);
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
