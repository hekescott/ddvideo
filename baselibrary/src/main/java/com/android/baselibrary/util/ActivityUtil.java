package com.android.baselibrary.util;import android.content.Intent;import android.os.Bundle;import com.android.baselibrary.BuildConfig;import com.android.baselibrary.base.BaseActivity;/** * Created by chen.huarong on 2019-05-09. */public class ActivityUtil {    private static BaseActivity sCurrentActivity;    public static void setCurrentActivity(BaseActivity activity) {        sCurrentActivity = activity;        if (BuildConfig.DEBUG) {            LogUtils.d("当前Activity:", (sCurrentActivity == null ? "null" :                    sCurrentActivity.getClass().getSimpleName()));        }    }    /**     * 通过类名启动Activity add     */    public static void navigateTo(Class pClass) {        if (sCurrentActivity != null) {            sCurrentActivity.openActivity(pClass, null);        }    }    /**     * 通过类名启动Activity，并且含有Bundle数据     */    public static void navigateTo(Class pClass, Bundle pBundle) {        if (sCurrentActivity != null) {            Intent intent = new Intent(sCurrentActivity, pClass);            if (pBundle != null) {                intent.putExtras(pBundle);            }            sCurrentActivity.startActivity(intent);        }    }}