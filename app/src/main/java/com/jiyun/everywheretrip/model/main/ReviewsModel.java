package com.jiyun.everywheretrip.model.main;

import android.util.Log;

import com.jiyun.everywheretrip.api.main.MainApiService;
import com.jiyun.everywheretrip.base.BaseModel;
import com.jiyun.everywheretrip.base.BaseModel;
import com.jiyun.everywheretrip.bean.main.ReviewsBean;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.net.RxUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by $sl on 2019/5/14 9:51.
 */
public class ReviewsModel extends BaseModel {
    private static final String TAG = "ReviewsModel----";

    public void getData(int routeId, int page, String token, final ResultCallBack<ReviewsBean> callBack) {
        MainApiService apiserver = HttpUtils.getInstance().getApiserver(MainApiService.url, MainApiService.class);
        Observable<ReviewsBean> observable = apiserver.getReviews(routeId,page,token);
        observable.compose(RxUtils.<ReviewsBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<ReviewsBean>() {
                    @Override
                    public void onNext(ReviewsBean reviewsBean) {
                        callBack.onSuccess(reviewsBean);
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
