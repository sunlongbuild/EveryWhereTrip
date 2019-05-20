package com.jiyun.everywheretrip.api.version;

import com.jiyun.everywheretrip.bean.version.VersionInfoBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by $sl on 2019/5/16 10:59.
 */
public interface VersionApiService {
    String baseUrl = "https://api.banmi.com/";

    @GET("api/app/common/getVersionInfo?operating_system=android")
    Observable<VersionInfoBean> getVersionInfo(@Header("banmi-app-token") String token);
}
