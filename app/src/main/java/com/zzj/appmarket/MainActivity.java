package com.zzj.appmarket;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;

import com.zzj.appmarket.view.ContentView;
import com.zzj.appmarket.view.SplishView;

public class MainActivity extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //可以将一下代码加到你的MainActivity中，或者在任意一个需要调用分享功能的activity当中
        String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS};
        ActivityCompat.requestPermissions(MainActivity.this, mPermissionList, 100);

        toContentView();
//        toGuideActivity();
    }

    private void toContentView() {
        Intent intent = new Intent(this, ContentView.class);
        startActivity(intent);
        finish();
    }

    private void toGuideActivity() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                Intent intent = new Intent(MainActivity.this, SplishView.class);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(
                        MainActivity.this,R.anim.in,R.anim.out);
                ActivityCompat.startActivity(MainActivity.this, intent, compat.toBundle());
                finish();
            }
        }).start();
    }
}
