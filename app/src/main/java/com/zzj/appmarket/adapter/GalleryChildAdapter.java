package com.zzj.appmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zzj.appmarket.R;
import com.zzj.appmarket.base.BaseApplication;
import com.zzj.appmarket.bean.AppInfoBean;
import com.zzj.appmarket.conf.Constants;

import java.util.List;

/**
 * Created by bjh on 16/9/7.
 */
public class GalleryChildAdapter extends RecyclerView.Adapter<GalleryChildAdapter.GalleryChildHolder>{

    private final Context mContext;
    private final List<AppInfoBean> appInfoBeanList;

    public GalleryChildAdapter(List<AppInfoBean> beans){
        appInfoBeanList = beans;
        mContext = BaseApplication.getContext();
    }

    @Override
    public GalleryChildAdapter.GalleryChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gallerychild,parent,false);
        GalleryChildHolder holder = new GalleryChildHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GalleryChildAdapter.GalleryChildHolder holder, int position) {
        holder.appName.setText(appInfoBeanList.get(position).name);
        holder.score.setText(appInfoBeanList.get(position).stars+"分");
        Glide.with(mContext).load(Constants.BASEURL_PICTURE+appInfoBeanList.get(position).iconUrl).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return appInfoBeanList.size();
    }
    class GalleryChildHolder extends RecyclerView.ViewHolder{
        private final ImageView icon;
        private final TextView appName;
        private final TextView score;

        public GalleryChildHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.iv_appicon_tabfm);
            appName = (TextView) itemView.findViewById(R.id.tv_appname_tabfm);
            score = (TextView) itemView.findViewById(R.id.tv_score_tabfm);
        }
    }
}
