package com.jiyun.everywheretrip.api.main;

import com.jiyun.everywheretrip.bean.main.BanMiCirCuitBean;
import com.jiyun.everywheretrip.bean.main.BanMiDetailsBean;
import com.jiyun.everywheretrip.bean.main.BanMiFollowingBean;
import com.jiyun.everywheretrip.bean.main.BanMiListBean;
import com.jiyun.everywheretrip.bean.main.BanMiMyFollowingBean;
import com.jiyun.everywheretrip.bean.main.DetailsCollectBean;
import com.jiyun.everywheretrip.bean.main.HomeBean;
import com.jiyun.everywheretrip.bean.main.HomeDetailsBean;
import com.jiyun.everywheretrip.bean.main.ReviewsBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by $sl on 2019/5/5 21:28.
 */
public interface MainApiService {
    String url = "http://api.banmi.com/";

    //首页
    @GET("api/3.0/content/routesbundles?")
    Observable<HomeBean> getData(@Query("page") int page, @Header("banmi-app-token") String s);

    //伴米
    @GET("api/3.0/banmi")
    Observable<BanMiListBean> getBanMiData(@Query("page") int page, @Header("banmi-app-token") String s);

    //路线详情
    @GET("api/3.0/content/routes/{cid}")
    Observable<HomeDetailsBean> getHomeDetails(@Path("cid") int cid, @HeaderMap Map<String, String> map);


    //4.8伴米详情
    @GET("api/3.0/banmi/{banmiId}")
    Observable<BanMiDetailsBean> getBanMiDetails(@Path("banmiId") int banmiId, @Header("banmi-app-token") String token, @Query("page") int page);

    //4.9伴米线路
    @GET("api/3.0/banmi/{banmiId}/routes")
    Observable<BanMiCirCuitBean> getBanmiCirCuit(@Path("banmiId") int banmiId, @Query("page") int page, @Header("banmi-app-token") String token);

    //4.10 线路详情--全部评价
    @GET("api/3.0/content/routes/{routeId}/reviews")
    Observable<ReviewsBean> getReviews(@Path("routeId") int routeId, @Query("page") int page, @Header("banmi-app-token") String token);

    //4.6收藏线路
    @POST("api/3.0/content/routes/{routeId}/like")
    Observable<DetailsCollectBean> getDetailsCollect(@Path("routeId") int routeId, @Header("banmi-app-token") String token);

    //4.7取消收藏
    @POST("api/3.0/content/routes/{routeId}/dislike")
    Observable<DetailsCollectBean> getDetailsCancelCollect(@Path("routeId") int routeId, @Header("banmi-app-token") String token);

    //4.4关注伴米
    @POST("api/3.0/banmi/{banmiId}/follow")
    Observable<BanMiFollowingBean> getBanMiFollowing(@Path("banmiId") int banmiId, @Header("banmi-app-token") String token);

    //4.4取消关注伴米
    @POST("api/3.0/banmi/{banmiId}/unfollow")
    Observable<BanMiFollowingBean> getBanMiUnFollowing(@Path("banmiId") int banmiId, @Header("banmi-app-token") String token);

    //5.4我的关注
    @GET("api/3.0/account/followedBanmi")
    Observable<BanMiMyFollowingBean> getMyFollowing(@Header("banmi-app-token") String token, @Query("page") int page);

}
