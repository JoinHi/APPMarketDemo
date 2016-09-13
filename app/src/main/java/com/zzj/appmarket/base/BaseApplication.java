package com.zzj.appmarket.base;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

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
        Logger.init("zzj").logLevel(LogLevel.FULL);
        mContext = getApplicationContext();
        super.onCreate();
    }

}
