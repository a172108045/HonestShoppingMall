package com.song.honestshoppingmall.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.fragment.CategoryFragment;
import com.song.honestshoppingmall.fragment.HomeFragment;
import com.song.honestshoppingmall.fragment.MineFragment;
import com.song.honestshoppingmall.fragment.SettingFragment;
import com.song.honestshoppingmall.fragment.ShopCartFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mActivitySecond;
    private FrameLayout mFlHome;
    private ImageView mIvHome;
    private ImageView mIvCategory;
    private ImageView mIvShopcart;
    private ImageView mIvMine;
    private ImageView mIvSetting;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initData();

    }

    private void initView() {
        mActivitySecond = (LinearLayout) findViewById(R.id.activity_second);
        mFlHome = (FrameLayout) findViewById(R.id.fl_home);
        mIvHome = (ImageView) findViewById(R.id.iv_home);
        mIvCategory = (ImageView) findViewById(R.id.iv_category);
        mIvShopcart = (ImageView) findViewById(R.id.iv_shopcart);
        mIvMine = (ImageView) findViewById(R.id.iv_mine);
        mIvSetting = (ImageView) findViewById(R.id.iv_setting);
    }

    private void initData() {
        mIvHome.setOnClickListener(this);
        mIvCategory.setOnClickListener(this);
        mIvShopcart.setOnClickListener(this);
        mIvMine.setOnClickListener(this);
        mIvSetting.setOnClickListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, new HomeFragment()).commit();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, new HomeFragment()).commit();
                break;
            case R.id.iv_category:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, new CategoryFragment()).commit();
                break;
            case R.id.iv_shopcart:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, new ShopCartFragment()).commit();
                break;
            case R.id.iv_mine:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, new MineFragment()).commit();
                break;
            case R.id.iv_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, new SettingFragment()).commit();
                break;
        }
    }
}
