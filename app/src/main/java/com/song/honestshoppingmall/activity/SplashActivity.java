package com.song.honestshoppingmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.song.honestshoppingmall.event.FirstEvent;
import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.util.DialogAlertUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private Button   mBtn_try;
    private TextView mTv;
    private Button   mBtn_alertshopcar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        mBtn_alertshopcar = (Button) findViewById(R.id.btn_alertshopcar);
        mBtn_alertshopcar.setOnClickListener(this);
        //注册EventBus

        EventBus.getDefault().register(this);

        mBtn_try = (Button) findViewById(R.id.btn_try);
        mTv = (TextView) findViewById(R.id.tv);
        mBtn_try.setOnClickListener(this);

    }

    @Subscribe
    public void onEventMainThread(FirstEvent event) {

        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.d("harvic", msg);
        mTv.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_alertshopcar:
                DialogAlertUtils.showScanNumberDialog(SplashActivity.this, "自定义文字", R.drawable.dialog_bg);

                break;
            case R.id.btn_try:
                Intent intent = new Intent(SplashActivity.this,
                        HomeActivity.class);
                startActivity(intent);

                break;


        }
    }
}
