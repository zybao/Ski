package com.oab.skyi;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Created by zhiyong.bao on 2017/10/27.
 */

public class MyApplication extends Application implements StateListener{
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();

    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public static int getColorFromRes(int paramInt) {
        return getAppContext().getResources().getColor(paramInt);
    }

    public static Drawable getDrawableFromRes(int paramInt) {
        return getAppContext().getResources().getDrawable(paramInt);
    }

    public static int getDimensionPixelSize(int paramInt) {
        return getAppContext().getResources().getDimensionPixelSize(paramInt);
    }

    public static Resources getRes() {
        return getAppContext().getResources();
    }

    public static String getStringFromRes(int paramInt) {
        return getAppContext().getResources().getString(paramInt);
    }

    public static String getStringFromRes(int paramInt, Object... paramVarArgs) {
        return getAppContext().getResources().getString(paramInt, paramVarArgs);
    }

    public static Object getSysService(String paramString) {
        return getAppContext().getSystemService(paramString);
    }

    @Override
    public void onBecomeBackground() {

    }

    @Override
    public void onBecomeForground() {

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

}