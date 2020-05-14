package com.zhowin.motorbike.common.http;

import android.util.Log;

import com.google.gson.JsonParseException;
import com.vondear.rxtool.RxNetTool;
import com.vondear.rxtool.view.RxToast;
import com.zhowin.motorbike.R;
import com.zhowin.motorbike.common.base.ActivityManager;
import com.zhowin.motorbike.common.base.BaseApplication;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.adapter.rxjava2.HttpException;

public abstract class ApiObserver<T> implements Observer<ApiResponse<T>> {

    public static final int NET_ERROR = -1;
    public static final int JSON_ERROR = -2;
    public static final int DATA_ERROR = -3;
    public static final int TOKEN_ERROR = -4;
    public static final int PERMISSION_ERROR = -5;

    @Override
    public void onSubscribe(Disposable d) {
        if (!RxNetTool.isNetworkAvailable(BaseApplication.getInstance())) {
            d.dispose();
            onFail(NET_ERROR, ActivityManager.getAppInstance().currentActivity().getString(R.string.net_bad));
            RxToast.normal( ActivityManager.getAppInstance().currentActivity().getString(R.string.net_bad));
        }
        subscribe(d);
    }

    @Override
    public void onNext(ApiResponse<T> response) {
        //在这边对 基础数据 进行统一处理  初步解析：
        if (response.getCode() == 1) {
            onSuccess(response.getData());
        } else {
            onFail(response.getCode(), response.getMsg());
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable throwable) {
        String error;
        if (throwable instanceof SocketTimeoutException  //网络超时,网络连接异常
                || throwable instanceof ConnectException   //均视为网络异常
                || throwable instanceof UnknownHostException) {
            Log.e("TAG", "网络连接异常: " + throwable.getMessage());
            error = ActivityManager.getAppInstance().currentActivity().getString(R.string.net_bad);
            onFail(NET_ERROR, error);
        } else if (throwable instanceof JsonParseException
                || throwable instanceof JSONException     //均视为解析错误
                || throwable instanceof java.text.ParseException) {
            Log.e("TAG", "数据解析异常: " + throwable.getMessage());
            error = "throwable.getMessage()";
            onFail(JSON_ERROR, error);
        } else if (throwable instanceof HttpException) {
//            if (((HttpException) throwable).code() == 401) {
//                NoNetWorkPopup noNetWorkPopup = new NoNetWorkPopup(BaseApplication.getInstance(), new OnUpdateAppListener() {
//                    @Override
//                    public void onUpdateApp() {
//
//                    }
//
//                    @Override
//                    public void onExitApp() {
//                        UserInfo.setUserInfo(new UserInfo());
//                        ((BaseActivity) ActivityManager.getAppInstance().currentActivity()).startActivity(LoginActivity.class);
//                    }
//                });
//                noNetWorkPopup.setTitles("登录状态失效或您的账号已经在另一台设备上登录，请注意账号安全", "重新登录");
//                noNetWorkPopup.showPopupWindow();
//            } else if (((HttpException) throwable).code() == 403) {
//                onFail(PERMISSION_ERROR, "没有权限");
//            } else {
//                error = throwable.getMessage();
//                onFail(DATA_ERROR, error);
//            }
            Log.e("TAG", "错误: " + throwable.getMessage());

        }

    }

    public abstract void onSuccess(T demo);

    public void subscribe(Disposable d) {

    }

    public abstract void onFail(int errorCode, String errorMsg);
}
