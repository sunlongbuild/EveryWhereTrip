package com.jiyun.everywheretrip.api.cover;

import com.jiyun.everywheretrip.bean.cover.CityTabBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by $sl on 2019/5/16 20:48.
 */
public interface CoverApiService {
    String url = "https://api.banmi.com/";

    @GET("api/3.0/map/spots")
    Observable<CityTabBean> getCityTab(@Header("banmi-app-token") String token, @Query("cityID") int cityID, @Query("tagID") int tagID);
}
