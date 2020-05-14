package com.zhowin.motorbike.common.http;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    public static String BASE_SERVER_URL = "https://6188.zhowin.com/";//SignServer动态配置
    public static String IM_URL = "http://192.168.1.137:8899/";
    public static final int DEFAULT_TIME = 30;
    public static RetrofitFactory httpUtils;
    private OkHttpClient okHttpClient;
    private static ApiRequest mApiUrl;

    /**
     * 单例模式
     */
    public static ApiRequest getApiUrl() {
        if (mApiUrl == null) {
            synchronized (RetrofitFactory.class) {
                if (mApiUrl == null) {
                    mApiUrl = getInstance().getRetrofit();
                }
            }
        }
        return mApiUrl;
    }

    private RetrofitFactory() {
        okHttpClient = initOkHttp();
    }


    public static RetrofitFactory getInstance() {
        if (httpUtils == null) {
            httpUtils = new RetrofitFactory();
        }
        return httpUtils;
    }

    public ApiRequest getRetrofit() {
        // 初始化Retrofit
        ApiRequest apiUrl = initRetrofit().create(ApiRequest.class);
        return apiUrl;
    }

    /**
     * 初始化Retrofit
     */
    @NonNull
    public Retrofit initRetrofit() {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_SERVER_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @NonNull
    public Retrofit initImRetrofit() {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(IM_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 初始化okhttp
     */
    @NonNull
    private OkHttpClient initOkHttp() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder()
                .readTimeout(DEFAULT_TIME, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(DEFAULT_TIME, TimeUnit.SECONDS)//设置写入超时时间
                .addInterceptor(new LogInterceptor())//添加打印拦截器
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
//                .addNetworkInterceptor(httpLoggingInterceptor)
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .build();
    }

    public class LogInterceptor implements Interceptor {
        private String TAG = "xy";

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            RequestBody body = request.body();
//            Log.e(TAG, "requestBody:" + body.toString());
//            Log.e(TAG, "request:" + request.toString());
            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
//            Log.e(TAG, String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
//                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
//            Log.e(TAG, "response body:" + content);

//            Request request = chain.request();
//            RequestBody requestBody = request.body();
//            String password = ApiService.ENCRYPTION_PASSWORD;
//            FormBody.Builder newFormBuilder = new FormBody.Builder();
//            if (requestBody instanceof FormBody) {
//                FormBody formBody = (FormBody) requestBody;
//                for (int i = 0; i < formBody.size(); i++) {
//                    if (TextUtils.equals("param", formBody.name(i))) { //只对param参数做加密
//                        String paramJson = formBody.value(i); //原始的json， 做加密
//                        RNCryptorNative rncryptor = new RNCryptorNative();
//                        String encrypted = new String(rncryptor.encrypt(paramJson, password));
//                        Log.e("xy", "加密前param数据：" + paramJson);
//                        Log.e("xy", "加密后param数据：" + encrypted);
//                        newFormBuilder.add("param", encrypted); //加密之后添加
//                    } else if (TextUtils.equals("token", formBody.name(i))) {//token 不用加密
//                        newFormBuilder.add(formBody.name(i), formBody.value(i));
//                    }
//                }
//            }
//            RequestBody newFormBody = newFormBuilder.build();
//            Request.Builder builder = request.newBuilder();
//            builder.post(newFormBody);
//            return chain.proceed(builder.build());
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }
}
