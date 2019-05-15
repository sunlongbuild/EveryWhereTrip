package com.jiyun.everywheretrip.api.personal;

import com.jiyun.everywheretrip.bean.main.MyInfoBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by $sl on 2019/5/8 19:06.
 */
public interface PersonalApiService {

    String baseUrl = "http://api.banmi.com/";

    //修改个人信息中的昵称
    @POST("api/3.0/account/updateInfo")
    @FormUrlEncoded
    Observable<ResponseBody> upDateInfo(@Field("photo") String photo,
                                        @Field("userName") String userName,
                                        @Field("gender") String gender,
                                        @Field("description") String signature,
                                        @Header("banmi-app-token") String token);

    //5.5 用户信息
    @GET("api/3.0/account/info")
    Observable<MyInfoBean> getInfo(@Header("banmi-app-token") String token);
}
