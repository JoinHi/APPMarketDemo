package com.zzj.appmarket.fragment;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzj.appmarket.R;
import com.zzj.appmarket.customView.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjh on 16/8/24.
 */
public class GalleryFragment extends Fragment {

    private Context mContext;
    private List<String> mList;
    private GalleryAdapter mAdapter;
    private StateListAnimator stateListAnimator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("GalleryFragment onCreate");
        mContext = getActivity();
        initData();
    }

    private void initData() {
        mList = new ArrayList<String>();
        for (int i= 'A';i<'z';i++){
            mList.add("" + (char) i);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery,container,false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_gallery);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
        mAdapter = new GalleryAdapter();
        mRecyclerView.setAdapter(mAdapter);
        stateListAnimator = AnimatorInflater.loadStateListAnimator(mContext, R.anim.touch_raise);

//        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));
        return view;
    }

    @Override
    public void onDestroy() {
        System.out.println("GalleryFragment onDestroy");
        super.onDestroy();
    }
    class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>{

        @Override
        public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            GalleryViewHolder holder = new GalleryViewHolder(
                    LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_gallerygroup,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(GalleryViewHolder holder, int position) {
            holder.holer_tv.setText(mList.get(position));
            holder.holder_recycler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
            holder.holder_recycler.setAdapter(new GalleryChildAdapter());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
        class GalleryViewHolder extends RecyclerView.ViewHolder {

            private   TextView holer_tv;
            private  RecyclerView holder_recycler;

            public GalleryViewHolder(View itemView) {
                super(itemView);
                holer_tv = (TextView) itemView.findViewById(R.id.tv_recycler_item);
                holder_recycler = (RecyclerView) itemView.findViewById(R.id.recycler_group_item);
            }
        }
    }

    private class GalleryChildAdapter extends RecyclerView.Adapter<GalleryChildAdapter.GalleryChildHolder>{

        @Override
        public GalleryChildAdapter.GalleryChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_gallerychild,parent,false);
            GalleryChildHolder holder = new GalleryChildHolder(view);
//            holder.cardView.setStateListAnimator(stateListAnimator);
            return holder;
        }

        @Override
        public void onBindViewHolder(GalleryChildAdapter.GalleryChildHolder holder, int position) {
            holder.title.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
        class GalleryChildHolder extends RecyclerView.ViewHolder{

            private final TextView title;
            private final CardView cardView;

            public GalleryChildHolder(View itemView) {
                super(itemView);
                cardView = (CardView) itemView.findViewById(R.id.cardview_gallerychild_item);
                title = (TextView) itemView.findViewById(R.id.tv_gallerychild);
            }
        }
    }
}
