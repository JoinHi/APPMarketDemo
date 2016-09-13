package com.zzj.appmarket.tabsfamily;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzj.appmarket.R;
import com.zzj.appmarket.TabFragmentIntfSet;
import com.zzj.appmarket.adapter.MyTabpageRecyclerAdapter;
import com.zzj.appmarket.bean.HomeBean;

/**
 * Created by bjh on 16/8/23.
 */
public class TabFragment extends Fragment implements TabFragmentIntfSet.ViewI{
    public static final String ARGS_PAGE = "args_page";
    private static int height;
    private  int pageNum;
    private Context mContext;
    private static View mTablayout;
    private static Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private View animView;
    private TabPresenter tabPresenter;
    private HomeBean mHomeBean;

    public static TabFragment getInstance(Toolbar toolbar,View tabLayout,int pageNum){
        mToolbar = toolbar;
        mTablayout = tabLayout;
        Bundle mBundle = new Bundle();
        mBundle.putInt(ARGS_PAGE,pageNum);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(mBundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNum = getArguments().getInt(ARGS_PAGE);
        mContext = getActivity();
        animView = mTablayout.findViewById(R.id.view_anim_contentfg);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tabPresenter = new TabPresenter(this);
        View view = inflater.inflate(R.layout.fragment_tabpage, container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_tabpage);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void loadFirstDateSuccess(HomeBean homeBean) {
        mHomeBean = homeBean;
        mRecyclerView.setAdapter(new MyTabpageRecyclerAdapter(this,mHomeBean));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addOnScrollListener(new PageScrollListener());
    }

    /**
     * 监听recycleview的滚动，改变toolbar和tablayout的移动动画
     */
    private float tablayoutY;
    private boolean isShow;
    class PageScrollListener extends RecyclerView.OnScrollListener{
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            height = mToolbar.getHeight();
            int tabHeight = 168;
            int tabTop = 700;
            int num = tabTop + 168 ;
            int toolbarTop = 84;
            tablayoutY +=dy;
            if (tablayoutY<=num){
                mTablayout.setTranslationY(-tablayoutY);
            }else {
                mTablayout.setTranslationY(-num);
            }
            if (tablayoutY<=num -toolbarTop && tablayoutY>=tabTop-height-toolbarTop){
                mToolbar.setTranslationY(tabTop - tablayoutY-height - toolbarTop);
            }else {
                if (tablayoutY>num)
                    mToolbar.setTranslationY(-height-tabHeight);
                if (tablayoutY<num-height)
                    mToolbar.setTranslationY(0);
            }
            if (tablayoutY>=tabTop-height -toolbarTop&& !isShow){
                openAnim();
                isShow = true;
            }else if (tablayoutY<tabTop-height-toolbarTop && isShow){
                closeAnim();
                isShow = false;
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE){

            }
        }
    }

    private void closeAnim() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(animView, "scaleY", -18f, 1f);
        anim.setDuration(500);
        anim.start();
    }

    private void openAnim() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(animView, "scaleY", 1f, -18f);
        anim.setDuration(500);
        anim.start();
    }
}
