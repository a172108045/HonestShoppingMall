package com.song.honestshoppingmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.song.honestshoppingmall.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initData();

    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }
        }).start();
    }

}
