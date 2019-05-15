package com.jiyun.everywheretrip.model.main;

import android.util.Log;

import com.jiyun.everywheretrip.api.main.MainApiService;
import com.jiyun.everywheretrip.base.BaseModel;
import com.jiyun.everywheretrip.bean.login.VerifyCodeBean;
import com.jiyun.everywheretrip.bean.main.HomeBean;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.net.RxUtils;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by $sl on 2019/5/5 21:16.
 */
public class HomeModel extends BaseModel {
    private static final String TAG = "HomeModel----";
    public void getData(final ResultCallBack<HomeBean> callBack, int page,String string) {
        MainApiService service = HttpUtils.getInstance().getApiserver(MainApiService.url, MainApiService.class);
        Observable<HomeBean> observable = service.getData(page,string);

        observable.compose(RxUtils.<HomeBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<HomeBean>() {
                    @Override
                    public void error(String msg) {

                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(HomeBean homeBean) {
                        callBack.onSuccess(homeBean);
                    }
                });
    }
}
