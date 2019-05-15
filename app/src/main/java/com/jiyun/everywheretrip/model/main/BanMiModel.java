package com.jiyun.everywheretrip.model.main;

import com.jiyun.everywheretrip.api.main.MainApiService;
import com.jiyun.everywheretrip.base.BaseModel;
import com.jiyun.everywheretrip.bean.main.BanMiListBean;
import com.jiyun.everywheretrip.bean.main.HomeBean;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.net.RxUtils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by $sl on 2019/5/5 21:16.
 */
public class BanMiModel extends BaseModel {
    private static final String TAG = "HomeModel----";
    public void getData(final ResultCallBack<BanMiListBean> callBack, int page,String string) {
        MainApiService service = HttpUtils.getInstance().getApiserver(MainApiService.url, MainApiService.class);
        Observable<BanMiListBean> observable = service.getBanMiData(page,string);
        observable.compose(RxUtils.<BanMiListBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<BanMiListBean>() {
                    @Override
                    public void error(String msg) {

                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BanMiListBean banMiListBean) {
                        callBack.onSuccess(banMiListBean);
                    }
                });
    }
}
