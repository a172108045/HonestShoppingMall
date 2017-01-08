package com.song.honestshoppingmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.adapter.GuideAdapter;
import com.song.honestshoppingmall.util.SpUtil;

public class GuideActivity extends AppCompatActivity {

    private ViewPager mVp_guide;
    private int[] mImgIds;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
    }

    private void initData() {
        mImgIds = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
        GuideAdapter adapter = new GuideAdapter(this, mImgIds);
        mVp_guide.setAdapter(adapter);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUtil.saveBoolean(getApplicationContext(), "isFirst", false);
                startActivity(new Intent(GuideActivity.this, HomeActivity.class));
                finish();
            }
        });

        mVp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == mImgIds.length - 1) {
                    mButton.setVisibility(View.VISIBLE);
                } else {
                    mButton.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        mVp_guide = (ViewPager) findViewById(R.id.vp_guide);
        mButton = (Button) findViewById(R.id.bt_enter);
    }
}
