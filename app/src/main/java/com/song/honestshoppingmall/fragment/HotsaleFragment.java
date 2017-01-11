package com.song.honestshoppingmall.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.adapter.RotateHotsaleVpAdapter;
import com.song.honestshoppingmall.bean.RotateBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by yspc on 2017/1/11.
 */

public class HotsaleFragment extends Fragment {
    private static final int TIME = 3000;

    private View view;
    private LinearLayout pointLl;
    private ViewPager viewPager;
    private List<RotateBean> datas;
    private RotateHotsaleVpAdapter vpAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_hotsale, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.rotate_hotsale_vp);
        pointLl = (LinearLayout) view.findViewById(R.id.rotate_point_container);

        buildDatas();

        vpAdapter = new RotateHotsaleVpAdapter(datas, getContext());
        viewPager.setAdapter(vpAdapter);

        viewPager.setCurrentItem(datas.size() * 100);

        handler = new Handler();
        startRotate();

        addPoints();

        changePoints();

        initView();

        return view;
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
            ImageView pointIv = new ImageView(getContext());
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
    public void onResume() {
        super.onResume();
        isRotate = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isRotate = false;
    }

    private void buildDatas() {
        datas = new ArrayList<>();
        datas.add(new RotateBean(R.mipmap.image1));
        datas.add(new RotateBean(R.mipmap.image2));
        datas.add(new RotateBean(R.mipmap.image3));
        datas.add(new RotateBean(R.mipmap.image4));
    }

    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("http://")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
