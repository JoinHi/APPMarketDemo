package com.zzj.appmarket.tabsfamily;

import com.zzj.appmarket.TabFragmentIntfSet;
import com.zzj.appmarket.bean.AppInfoBean;
import com.zzj.appmarket.bean.HomeBean;
import com.zzj.appmarket.bean.TabBeans;
import com.zzj.appmarket.utils.RetrofitUtils;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by bjh on 16/9/1.
 */
public class TabModel implements TabFragmentIntfSet.ModelI{
    public TabModel(){
    }
    @Override
    public Observable<HomeBean> getPageInfo() {

        return RetrofitUtils.getBeanObservable(0)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<AppInfoBean>> getAppInfo(){
        return RetrofitUtils.getAppBeanObservable(0)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<TabBeans> getPageInfos(){
        return Observable.zip(getPageInfo(), getAppInfo(), new Func2<HomeBean, List<AppInfoBean>, TabBeans>() {
            @Override
            public TabBeans call(HomeBean homeBean, List<AppInfoBean> appInfoBeans) {
                return new TabBeans().setHomeBean(homeBean).setAppInfoBeans(appInfoBeans);
            }
        });
    }


}
