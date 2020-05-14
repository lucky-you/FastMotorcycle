package com.zhowin.motorbike.common.http;

import androidx.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.zhowin.motorbike.common.model.UserInfo;
import com.zhowin.motorbike.common.utils.RxSchedulers;

/**
 * author Z_B
 * date :2020/5/13 17:58
 * description:
 */
public class HttpRequest {

    static ApiRequest apiRequest;

    static {
        apiRequest = RetrofitFactory.getInstance().initRetrofit().create(ApiRequest.class);
    }


    /**
     * 返回值是string的 数据结构
     */
    public static void resultStringData(LifecycleOwner activity, String token, String param, final HttpCallBack<String> callBack) {
        apiRequest.resultStringData(token, param)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<String>() {

                    @Override
                    public void onSuccess(String demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 返回值是Object的 数据结构
     */
    public static void resultObjectData(LifecycleOwner activity, String token, String param, final HttpCallBack<Object> callBack) {
        apiRequest.resultObjectData(token, param)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<Object>() {

                    @Override
                    public void onSuccess(Object demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 返回值是boolean的 数据结构
     */
    public static void resultBooleanData(LifecycleOwner activity, String token, String param, final HttpCallBack<Boolean> callBack) {
        apiRequest.resultBooleanData(token, param)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<Boolean>() {

                    @Override
                    public void onSuccess(Boolean demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 返回的是用户信息  登录，注册 ，获取用户信息通用
     */
    public static void loadUserInfoMessage(LifecycleOwner activity, String token, String param, final HttpCallBack<UserInfo> callBack) {
        apiRequest.returnUserInfoMessage(token, param)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<UserInfo>() {

                    @Override
                    public void onSuccess(UserInfo demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }


}
