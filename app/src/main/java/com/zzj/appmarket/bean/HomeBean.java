package com.zzj.appmarket.bean;

import java.util.List;

public class HomeBean implements BeanI{
	public List<AppInfoBean>	list;
	public List<String>			picture;

	public int					position;		//recycler滚动的item位置

}
