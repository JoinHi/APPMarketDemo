package com.zzj.appmarket.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zzj.appmarket.R;
import com.zzj.appmarket.utils.DensityUtil;

/**
 * Created by bjh on 16/8/31.
 */
public class ViewPagerFragment extends Fragment {
    private ViewPager mViewPager;
    private FrameLayout pointgroup;
    private View redpoint;
    private LinearLayout grayPoints;
    private int pointSize = 7;
    private FrameLayout.LayoutParams redPointParams;
    private Context mContext;
    private static PagerAdapter mPagerAdapter;

    public static ViewPagerFragment CreateViewPagerFragment(PagerAdapter pagerAdapter){
        mPagerAdapter = pagerAdapter;
        return new ViewPagerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getContext();
        View view = inflater.inflate(R.layout.tabviewpager,container,false);
        initView(view);
        initData();
        initEvent();
        return view;
    }
    private void initView(View view) {
        mViewPager =  (ViewPager) view.findViewById(R.id.viewpager_tabimg);
        pointgroup = (FrameLayout) view.findViewById(R.id.fl_tabimg_pointgroup);
        redpoint =    view.findViewById(R.id.v_tabimg_redpoint);
        grayPoints = (LinearLayout) view.findViewById(R.id.ll_tabimg_graypoints);
        for (int i = 0;i<mPagerAdapter.getCount();i++){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DensityUtil.dip2px(getContext(), pointSize), DensityUtil.dip2px(mContext, pointSize));
            if (i != 0){
                params.leftMargin = DensityUtil.dip2px(mContext, pointSize);
            }
            View grayPoint = new View(mContext);
            grayPoint.setBackgroundResource(R.drawable.gray_point);
            grayPoint.setLayoutParams(params);
            grayPoints.addView(grayPoint);
        }
        redPointParams = new FrameLayout.LayoutParams(
                DensityUtil.dip2px(mContext, pointSize), DensityUtil.dip2px(mContext, pointSize));

    }
    private void initData(){
        mViewPager.setAdapter(mPagerAdapter);
    }

    private void initEvent(){
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                redPointParams.leftMargin = (int) (DensityUtil.dip2px(mContext,2*pointSize)*(position+positionOffset));
                redpoint.setLayoutParams(redPointParams);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
