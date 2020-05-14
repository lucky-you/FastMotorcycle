package com.zhowin.motorbike;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.zhowin.motorbike.common.base.BaseApplication;

/**
 * author Z_B
 * date :2020/5/13 16:57
 * description:
 */
public class MotorApplication extends BaseApplication {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
