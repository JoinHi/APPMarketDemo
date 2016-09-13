package com.zzj.appmarket.tabsfamily;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.zzj.appmarket.TabFragmentIntfSet;
import com.zzj.appmarket.bean.AppInfoBean;
import com.zzj.appmarket.bean.HomeBean;
import com.zzj.appmarket.bean.TabBeans;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by bjh on 16/9/1.
 */
public class TabPresenter implements TabFragmentIntfSet.PresenterI{

    private final TabModel tabModel;
    private TabFragmentIntfSet.ViewI mActivity;
    public TabPresenter(TabFragmentIntfSet.ViewI activity){
        mActivity = activity;
        tabModel = new TabModel();
        firstLoad();
        load();
        getAppInfos();
    }

    private void load() {
        tabModel.getPageInfos().subscribe(new Action1<TabBeans>() {
            @Override
            public void call(TabBeans tabBeans) {
                Logger.d(tabBeans.getAppInfoBeans().toString() + "::" + tabBeans.getHomeBean().toString());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public void firstLoad(){
        tabModel.getPageInfo().subscribe(new Action1<HomeBean>() {
            @Override
            public void call(HomeBean homeBean) {
                if (homeBean !=null && homeBean.list != null)
                homeBean.list.remove(0);
                Logger.i( homeBean.picture.toString() + "|||||||" + homeBean.list.toString());
                mActivity.loadFirstDateSuccess(homeBean);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public void getAppInfos() {
        tabModel.getAppInfo().subscribe(new Action1<List<AppInfoBean>>() {
            @Override
            public void call(List<AppInfoBean> beans) {
                Logger.i( "____" + beans.toString());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Logger.i("________", "__throwable__");
            }
        });
    }
}
