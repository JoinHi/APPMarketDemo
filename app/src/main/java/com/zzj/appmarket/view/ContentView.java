package com.zzj.appmarket.view;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zzj.appmarket.R;
import com.zzj.appmarket.fragment.ContentFragment;
import com.zzj.appmarket.fragment.GalleryFragment;
import com.zzj.appmarket.fragment.SlideshowFragment;

public class ContentView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ContentFragment contentFragment;
    private FragmentManager manager;
    private Fragment currentFragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_content_view);


        findViewById(R.id.drawer_layout).setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        manager = getSupportFragmentManager();
        replaceFragment();
    }

    private void replaceFragment() {
        FragmentTransaction transaction = manager.beginTransaction();
        if (currentFragment != null){
            transaction.hide(currentFragment);
        }
        if (contentFragment == null) {
            contentFragment = new ContentFragment();
            transaction.replace(R.id.screen_content, contentFragment);
        }else {
            transaction.show(contentFragment);
        }
        transaction.commit();
    }

    public Toolbar getToolbar(){
        return toolbar;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            replaceFragment();
        } else if (id == R.id.nav_gallery) {
            showFragment(GalleryFragment.class.getName());
        } else if (id == R.id.nav_slideshow) {
            showFragment(SlideshowFragment.class.getName());
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showFragment(String className) {
        try {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.hide(contentFragment);
            if (currentFragment == null || !currentFragment.getClass().getName().equals(className)){
                if (currentFragment != null){
                    transaction.remove(currentFragment);
                }
                currentFragment = (Fragment) Class.forName(className).newInstance();
                transaction.add(R.id.screen_content, currentFragment);
            }else {
                transaction.show(currentFragment);
            }
            transaction.commit();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
