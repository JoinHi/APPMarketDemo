package com.zzj.appmarket.utils;

import com.zzj.appmarket.bean.AppInfoBean;
import com.zzj.appmarket.bean.HomeBean;
import com.zzj.appmarket.conf.Constants;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by bjh on 16/9/1.
 */
public class RetrofitUtils {

    interface AppMarket{
        @GET("home")
        Observable<HomeBean> Beans(@Query("index") int index);

        @GET("app")
        Observable<List<AppInfoBean>> AppBeans(@Query("index") int index);
    }

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            //错误重连
            .retryOnConnectionFailure(true).build();

    private static final Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(okHttpClient)
            .build();
    private static final AppMarket APP_MARKET = mRetrofit.create(AppMarket.class);

    public static Observable<HomeBean> getBeanObservable(int index){
        return APP_MARKET.Beans(index);
    }

    public static Observable<List<AppInfoBean>> getAppBeanObservable(int index){
        return APP_MARKET.AppBeans(index);
    }

}
