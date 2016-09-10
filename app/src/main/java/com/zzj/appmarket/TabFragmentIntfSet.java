package com.zzj.appmarket;

import com.zzj.appmarket.bean.AppInfoBean;
import com.zzj.appmarket.bean.HomeBean;
import com.zzj.appmarket.bean.TabBeans;

import java.util.List;

import rx.Observable;

/**
 * Created by bjh on 16/9/1.
 */
public class TabFragmentIntfSet {
    public interface ViewI{
        void loadFirstDateSuccess(HomeBean homeBean);
    }
    public interface PresenterI{
//        void getPageInfo();
    }
    public interface ModelI{
        Observable<HomeBean> getPageInfo();
        Observable<TabBeans> getPageInfos();
        Observable<List<AppInfoBean>> getAppInfo();
    }
}
