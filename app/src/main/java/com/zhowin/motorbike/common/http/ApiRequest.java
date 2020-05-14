package com.zhowin.motorbike.common.http;

import com.zhowin.motorbike.common.model.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author Z_B
 * date :2020/5/13 17:55
 * description: 网络请求
 */
public interface ApiRequest {
    String HEADER_URL = "/api/v1";
    String TOKEN = "token";
    String PARAM = "param";
    String BASE_METHOD_KEY = "method";

    /**
     * 返回值 String
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ApiResponse<String>> resultStringData(@Field(TOKEN) String token, @Field(PARAM) String param);

    /**
     * 返回值 Object
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ApiResponse<Object>> resultObjectData(@Field(TOKEN) String token, @Field(PARAM) String param);

    /**
     * 返回值 boolean
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ApiResponse<Boolean>> resultBooleanData(@Field(TOKEN) String token, @Field(PARAM) String param);

    /**
     * 返回的是用户信息 登录，注册 ，获取用户信息通用
     */
    @FormUrlEncoded
    @POST(HEADER_URL)
    Observable<ApiResponse<UserInfo>> returnUserInfoMessage(@Field(TOKEN) String token, @Field(PARAM) String param);
}
