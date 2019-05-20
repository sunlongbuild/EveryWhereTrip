package com.jiyun.everywheretrip.model.country;

import com.jiyun.everywheretrip.api.country.CountryApiService;
import com.jiyun.everywheretrip.base.BaseModel;
import com.jiyun.everywheretrip.bean.country.CountryBean;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.net.RxUtils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by $sl on 2019/5/17 15:51.
 */
public class CountryModel extends BaseModel {
    public void getCountryData(String token, final ResultCallBack<CountryBean> callBack){
        CountryApiService apiserver = HttpUtils.getInstance().getApiserver(CountryApiService.baseUrl, CountryApiService.class);
        Observable<CountryBean> countryBean = apiserver.getCountryBean(token);
        countryBean.compose(RxUtils.<CountryBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<CountryBean>() {
                    @Override
                    public void onNext(CountryBean countryBean) {
                        callBack.onSuccess(countryBean);
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
