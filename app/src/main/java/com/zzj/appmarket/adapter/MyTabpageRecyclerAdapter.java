package com.zzj.appmarket.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zzj.appmarket.R;
import com.zzj.appmarket.base.BaseApplication;
import com.zzj.appmarket.bean.HomeBean;
import com.zzj.appmarket.conf.Constants;
import com.zzj.appmarket.fragment.ViewPagerFragment;

/**
 * Created by bjh on 16/9/7.
 */
public class MyTabpageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private float[] positions = new float[20];
    private final Context mContext;
    private final HomeBean mHomeBean;
    private final Fragment mFragment;

    public MyTabpageRecyclerAdapter(Fragment fragment, HomeBean homeBean){
        mFragment = fragment;
        mHomeBean = homeBean;
        mContext = BaseApplication.getContext();
    }
    @Override
    public int getItemViewType(int position) {
        int type = 0;
        if (position == 0){
            type = 1;
        }else {
            type = 2;
        }
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        if (viewType == 1){
            view = LayoutInflater.from(mContext).inflate(R.layout.tabviewpager_pages,parent,false);
            return new MyTabHeaderRecyclerVIewHolder(view);
        }else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_gallerygroup,parent,false);
            return new MyTabpageRecyclerViewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyTabpageRecyclerViewHolder) {
            ((MyTabpageRecyclerViewHolder)holder).holder_recycler.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
            ((MyTabpageRecyclerViewHolder)holder).holder_recycler.setAdapter(new GalleryChildAdapter(mHomeBean.list));
            ((MyTabpageRecyclerViewHolder)holder).holder_recycler.scrollToPosition((int) (positions[position]));

            ((MyTabpageRecyclerViewHolder)holder).holder_recycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        View childView = recyclerView.getChildAt(0);
                        if (childView != null){
                            int left = recyclerView.getChildAt(0).getLeft();
                            if (left <-222){
                                recyclerView.smoothScrollBy(455+left-35,0);
                            }else {
                                recyclerView.smoothScrollBy(left-35,0);
                            }
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int m = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0));
                    positions[position] = m;
//                        Log.i("___", m + "");
                }
            });
        }else if (holder instanceof MyTabHeaderRecyclerVIewHolder){

        }
    }


    @Override
    public int getItemCount() {
        return 20;
    }

    /**
     * item holder
     */
    class MyTabpageRecyclerViewHolder extends RecyclerView.ViewHolder{
        private TextView holer_tv;
        private  RecyclerView holder_recycler;

        public MyTabpageRecyclerViewHolder(View itemView) {
            super(itemView);
            holer_tv = (TextView) itemView.findViewById(R.id.tv_recycler_item);
            holder_recycler = (RecyclerView) itemView.findViewById(R.id.recycler_group_item);
        }
    }
    /**
     *  holder of recyclerview's header
     */
    class MyTabHeaderRecyclerVIewHolder extends RecyclerView.ViewHolder{



        public MyTabHeaderRecyclerVIewHolder(View itemView) {
            super(itemView);
            FragmentManager fm = mFragment.getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Fragment fragment = ViewPagerFragment.CreateViewPagerFragment(new HeaderPagerAdapter());

            transaction.replace(R.id.fl_pages_tabviewpage,fragment);
            transaction.commit();
        }
        /**
         * viewpager adpter of recyclerview's header
         */
        class HeaderPagerAdapter extends PagerAdapter {

            @Override
            public int getCount() {
                return mHomeBean.picture.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // 当前的设置的position Integer.MAX_VALUE / 2

                ImageView iv = new ImageView(mContext);
                // 设置scaleType
                iv.setScaleType(ImageView.ScaleType.FIT_XY);

                // 加载图片
                Glide.with(mContext)
                        .load(Constants.BASEURL_PICTURE + mHomeBean.picture.get(position))
                        .into(iv);
                // 加到ViewGroup
                container.addView(iv);

                return iv;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        }
    }

}