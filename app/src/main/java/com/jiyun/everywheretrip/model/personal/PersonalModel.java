package com.jiyun.everywheretrip.model.personal;

import com.jiyun.everywheretrip.api.personal.PersonalApiService;
import com.jiyun.everywheretrip.base.BaseModel;
import com.jiyun.everywheretrip.net.BaseObserver;
import com.jiyun.everywheretrip.net.HttpUtils;
import com.jiyun.everywheretrip.net.ResultCallBack;
import com.jiyun.everywheretrip.net.RxUtils;

import java.io.IOException;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by $sl on 2019/5/8 19:04.
 */
public class PersonalModel extends BaseModel {
    public void upDateInfo(String photo,String name,String gender,String signature ,String token, final ResultCallBack<String> callBack) {
        PersonalApiService service = HttpUtils.getInstance().getApiserver(PersonalApiService.baseUrl, PersonalApiService.class);
        service.upDateInfo(photo,name,gender,signature, token)
                .compose(RxUtils.<ResponseBody>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            callBack.onSuccess(string);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
