package com.zhowin.motorbike.common.utils;

import android.util.Log;

import com.zhowin.motorbike.BuildConfig;

public class LogUtils {

    static String className;
    static String methodName;
    static int lineNumber;

    private LogUtils() {

    }

    public static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(methodName);
        buffer.append(":");
        buffer.append(lineNumber);
        buffer.append("]");
        buffer.append(log);

        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void i(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void d(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void v(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }

    public static void w(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

    public static void wtf(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(className, createLog(message));
    }

}
