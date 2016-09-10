package com.zzj.appmarket.bean;

import java.util.List;

/**
 * Created by bjh on 16/9/8.
 */
public class TabBeans implements BeanI{
    private HomeBean homeBean;
    private List<AppInfoBean> appInfoBeans;

    public HomeBean getHomeBean() {
        return homeBean;
    }

    public TabBeans setHomeBean(HomeBean homeBean) {
        this.homeBean = homeBean;
        return this;
    }

    public List<AppInfoBean> getAppInfoBeans() {
        return appInfoBeans;
    }

    public TabBeans setAppInfoBeans(List<AppInfoBean> appInfoBeans) {
        this.appInfoBeans = appInfoBeans;
        return this;
    }
}