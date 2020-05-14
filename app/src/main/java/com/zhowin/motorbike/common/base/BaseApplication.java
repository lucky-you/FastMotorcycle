package com.zhowin.motorbike.common.base;

import androidx.multidex.MultiDexApplication;

import com.zhowin.motorbike.common.utils.ExecutorsUtils;
import com.zhowin.motorbike.common.utils.SPUtils;


public class BaseApplication extends MultiDexApplication {
    private static BaseApplication application;
    private static ExecutorsUtils mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        SPUtils.init(this);
        application = this;
        mAppExecutors = new ExecutorsUtils();
    }


    public static BaseApplication getInstance() {
        return application;
    }

    public static ExecutorsUtils getExecutorsUtils() {
        return mAppExecutors;
    }


}
