package com.zzj.appmarket.bean;

import java.util.List;

public class AppInfoBean implements BeanI{
	public String					des;			// 应用的描述
	public String					downloadUrl;	// 应用的下载地址
	public String					iconUrl;		// 应用的图标地址
	public long						id;			// 应用的id
	public String					name;			// 应用的名字
	public String					packageName;	// 应用的包名
	public long						size;			// 应用的大小
	public float					stars;			// 应用的评分


	/*--------------- 添加详情里面的部分字段 ---------------*/

	public String					author;		// 应用的所属公司
	public String					date;			// 应用的发布日期
	public String					downloadNum;	// 应用的下载量
	public String					version;		// 应用的版本

	public List<AppSafeInfoBean>	safe;			// Array
	public List<String>				screen;		// Array

	public class AppSafeInfoBean {
		public String	safeDes;		// 已通过安智市场安全检测，请放心使用
		public String	safeDesColor;	// 0
		public String	safeDesUrl;	// app/com.itheima.www/safeDesUrl0.jpg
		public String	safeUrl;		// app/com.itheima.www/safeIcon0.jpg
	}

	@Override
	public String toString() {
		return "AppInfoBean [des=" + des + ", downloadUrl=" + downloadUrl + ", iconUrl=" + iconUrl + ", id=" + id
				+ ", name=" + name + ", packageName=" + packageName + ", size=" + size + ", stars=" + stars
				+ ", author=" + author + ", date=" + date + ", downloadNum=" + downloadNum + ", version=" + version
				+ ", safe=" + safe + ", screen=" + screen + "]";
	}

}
