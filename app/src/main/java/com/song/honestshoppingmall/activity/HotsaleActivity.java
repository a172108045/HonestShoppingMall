package com.song.honestshoppingmall.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.adapter.RotateHotsaleVpAdapter;
import com.song.honestshoppingmall.bean.RotateBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yspc on 2017/1/9.
 */

public class HotsaleActivity extends AppCompatActivity {
    private static final int TIME = 3000;

    private ViewPager viewPager;
    private LinearLayout pointLl;
    private List<RotateBean> datas;
    private RotateHotsaleVpAdapter vpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_hotsale);
        viewPager = (ViewPager) findViewById(R.id.rotate_hotsale_vp);
        pointLl = (LinearLayout) findViewById(R.id.rotate_point_container);

        buildDatas();

        vpAdapter = new RotateHotsaleVpAdapter(datas, this);
        viewPager.setAdapter(vpAdapter);

        viewPager.setCurrentItem(datas.size() * 100);

        handler = new Handler();
        startRotate();

        addPoints();

        changePoints();

        initView();

    }

    private void initView() {

    }

    private void changePoints() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (isRotate) {

                    for (int i = 0; i < datas.size(); i++) {
                        ImageView pointIv = (ImageView) pointLl.getChildAt(i);
                        pointIv.setImageResource(R.mipmap.dot_normal);
                    }

                    ImageView iv = (ImageView) pointLl.getChildAt(position % datas.size());
                    iv.setImageResource(R.mipmap.dot_focus);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addPoints() {

        for (int i = 0; i < datas.size(); i++) {
            ImageView pointIv = new ImageView(this);
            pointIv.setPadding(5, 5, 5, 5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            pointIv.setLayoutParams(params);

            if (i == 0) {
                pointIv.setImageResource(R.mipmap.dot_focus);
            } else {
                pointIv.setImageResource(R.mipmap.dot_normal);
            }
            pointLl.addView(pointIv);
        }
    }


    private Handler handler;
    private boolean isRotate = false;
    private Runnable rotateRunnable;

    private void startRotate() {
        rotateRunnable = new Runnable() {
            @Override
            public void run() {
                int nowIndex = viewPager.getCurrentItem();
                viewPager.setCurrentItem(++nowIndex);
                if (isRotate) {
                    handler.postDelayed(rotateRunnable, TIME);
                }
            }
        };
        handler.postDelayed(rotateRunnable, TIME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRotate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRotate = false;
    }

    private void buildDatas() {
        datas = new ArrayList<>();
        datas.add(new RotateBean(R.mipmap.image1));
        datas.add(new RotateBean(R.mipmap.image2));
        datas.add(new RotateBean(R.mipmap.image3));
        datas.add(new RotateBean(R.mipmap.image4));
        datas.add(new RotateBean(R.mipmap.image5));
        datas.add(new RotateBean(R.mipmap.image6));
    }
}
