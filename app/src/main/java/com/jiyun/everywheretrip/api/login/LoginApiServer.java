package com.jiyun.everywheretrip.api.login;

import com.jiyun.everywheretrip.bean.login.VerifyCodeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by $sl on 2019/5/4 16:56.
 */
public interface LoginApiServer {
    int SUCCESS_CODE =200;
    String sBaseUrl = "http://yun918.cn/study/public/index.php/";
    /**
     * 获取验证码
     * @return
     */
    @GET("verify")
    Observable<VerifyCodeBean> getVerifyCode();
}
