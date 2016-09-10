package com.zzj.appmarket.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by bjh on 16/9/7.
 */
public class BaseApplication extends Application {
    private static Context mContext;
    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {

        mContext = getApplicationContext();
        super.onCreate();
    }
}
