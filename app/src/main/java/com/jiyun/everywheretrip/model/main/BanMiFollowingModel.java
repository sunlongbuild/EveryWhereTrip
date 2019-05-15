package com.jiyun.everywheretrip.model.main;

import com.jiyun.everywheretrip.api.main.MainApiService;
import com.jiyun.everywheretrip.base.BaseModel;
import com.jiyun.everywheretrip.bean.main.BanMiMyFollowingBean;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.net.RxUtils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by $sl on 2019/5/15 8:59.
 */
public class BanMiFollowingModel extends BaseModel {
    public void getBanMiFollowing(String token, int page, final ResultCallBack<BanMiMyFollowingBean> callBack) {
        MainApiService apiserver = HttpUtils.getInstance().getApiserver(MainApiService.url, MainApiService.class);
        Observable<BanMiMyFollowingBean> observable = apiserver.getMyFollowing(token, page);
        observable.compose(RxUtils.<BanMiMyFollowingBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<BanMiMyFollowingBean>() {
                    @Override
                    public void onNext(BanMiMyFollowingBean banMiMyFollowingBean) {
                        callBack.onSuccess(banMiMyFollowingBean);
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
