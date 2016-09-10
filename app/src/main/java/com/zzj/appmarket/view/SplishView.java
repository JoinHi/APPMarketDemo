package com.zzj.appmarket.view;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zzj.appmarket.R;
import com.zzj.appmarket.customView.CustomVideoView;
import com.zzj.appmarket.fragment.ViewPagerFragment;
import com.zzj.appmarket.utils.DensityUtil;

public class SplishView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splish_view);
        init();
    }

    private void init() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = ViewPagerFragment.CreateViewPagerFragment(new SectionsPagerAdapter(getSupportFragmentManager()));
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.main_content, fragment);
        transaction.commit();

    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_splish_view, container, false);
            final Button mButton = (Button) rootView.findViewById(R.id.bt_splish);
            final CustomVideoView videoView = (CustomVideoView) rootView.findViewById(R.id.video);
            String videoName = null;
            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                    videoName = ""+R.raw.guide_1;
                    break;
                case 2:
                    videoName = ""+R.raw.guide_2;
                    break;
                case 3:
                    videoName = ""+R.raw.guide_3;
                    mButton.setVisibility(View.VISIBLE);
                    mButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getActivity(), ContentView.class), ActivityOptions.makeSceneTransitionAnimation(getActivity(), mButton, "mybtn").toBundle());
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    SystemClock.sleep(1500);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            getActivity().finish();
                                        }
                                    });
                                }
                            }).start();
                        }
                    });
                    break;
            }
            Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + videoName);
            videoView.setVideoURI(uri);
            WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            videoView.start();
            videoView.setWidthHeight(manager.getDefaultDisplay().getWidth(), manager.getDefaultDisplay().getHeight());

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    videoView.start();
                }
            });
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }


    }
}
