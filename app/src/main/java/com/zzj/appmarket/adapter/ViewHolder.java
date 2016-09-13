package com.zzj.appmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bjh on 16/9/13.
 */
public class ViewHolder extends RecyclerView.ViewHolder{

    private final Context mContext;
    private final View mItemView;
    private final SparseArray<View> mViews;

    public ViewHolder(Context context,View itemView) {

        super(itemView);
        mContext = context;
        mItemView = itemView;
        mViews = new SparseArray<>();
    }
    public static ViewHolder createViewHolder(Context context, View itemView){
        ViewHolder viewHolder = new ViewHolder(context,itemView);
        return viewHolder;
    }
    public static ViewHolder createViewHolder(Context context,ViewGroup parent,int layoutId){
        View itemView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        ViewHolder viewHolder = new ViewHolder(context,itemView);
        return viewHolder;
    }
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            view = mItemView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }
    public View getmItemView(){
        return mItemView;
    }
}
