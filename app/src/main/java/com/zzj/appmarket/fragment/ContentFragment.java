package com.zzj.appmarket.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;


import android.view.View;
import android.view.ViewGroup;

import com.zzj.appmarket.R;
import com.zzj.appmarket.view.ContentView;

/**
 * Created by bjh on 16/8/23.
 */
public class ContentFragment extends Fragment {
    private ContentView mActivity;
    private String[] titles = new String[]{"首页","应用","游戏","专题","推荐","分类","排行"};
    private TabLayout tabLayout;
    private Toolbar mToolbar;
    private CardView tablayoutContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (ContentView) getActivity();
        mToolbar = mActivity.getToolbar();
        System.out.println("ContentFragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content,container,false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout_cententfragment);
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager_cententfragment);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(mActivity.getSupportFragmentManager());
        mViewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        tablayoutContainer = (CardView) view.findViewById(R.id.cardview_tablayoutcontainer);
//        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        return view;
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return TabFragment.getInstance(mToolbar,tablayoutContainer,position);
        }

        @Override
        public int getCount() {
//            return titles.length;
            return 1;
        }

//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView();
//        }

//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            TextView tv = new TextView(getActivity());
//            tv.setText(titles[position]);
//            return super.instantiateItem(container, position);
//        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    public void onDestroy() {
        System.out.println("ContentFragment onDestroy");
        super.onDestroy();

    }
}
