package com.jiyun.everywheretrip.api.country;

import com.jiyun.everywheretrip.bean.country.CountryBean;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by $sl on 2019/5/17 15:13.
 */
public interface CountryApiService {
    String baseUrl = "https://api.banmi.com/";

    @GET("api/3.0/map/cities")
    Observable<CountryBean> getCountryBean(@Header("banmi-app-token") String token);
}
