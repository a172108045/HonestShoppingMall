package com.song.honestshoppingmall.fragment;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.adapter.RotateVpAdapter;
import com.song.honestshoppingmall.bean.RotateBean;
import com.song.honestshoppingmall.event.FirstEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 烈焰红唇  Blazing Red Lips
 * Created by Judy on 2017/1/8.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final int TIME = 3000;

    private ViewPager viewPager;
    private LinearLayout pointLl;
    private List<RotateBean> datas;
    private RotateVpAdapter vpAdapter;
    private ImageView mImager_hotsale;
    private ImageView mImager_promotion;
    private ImageView mImager_deseno;
    private ImageView mImager_newon;
    private ImageView mImager_recommend;
    private ImageView mImager_classify;
    private ImageView mCommodity1;
    private ImageView mCommodity2;

    @Override
    protected View initView() {
        //在要接收消息的页面注册Eventbus
        // EventBus.getDefault().register(this);

        ((HomeActivity)mContext).changeTitle("首页");
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        viewPager = (ViewPager) view.findViewById(R.id.rotate_vp);
        pointLl = (LinearLayout) view.findViewById(R.id.rotate_point_container);

        buildDatas();

        vpAdapter = new RotateVpAdapter(datas, mContext);
        viewPager.setAdapter(vpAdapter);

        viewPager.setCurrentItem(datas.size() * 100);

        handler = new Handler();
        startRotate();

        addPoints();

        changePoints();
        mImager_hotsale = (ImageView) view.findViewById(R.id.imager_hotsale);
        mImager_promotion = (ImageView) view.findViewById(R.id.imager_promotion);
        mImager_deseno = (ImageView) view.findViewById(R.id.imager_deseno);
        mImager_newon = (ImageView) view.findViewById(R.id.imager_newon);
        mImager_recommend = (ImageView) view.findViewById(R.id.imager_recommend);
        mImager_classify = (ImageView) view.findViewById(R.id.imager_classify);
        mCommodity1 = (ImageView) view.findViewById(R.id.commodity1);
        mCommodity2 = (ImageView) view.findViewById(R.id.commodity2);

        mImager_hotsale.setOnClickListener(this);
        mImager_promotion.setOnClickListener(this);
        mImager_deseno.setOnClickListener(this);
        mImager_newon.setOnClickListener(this);
        mImager_recommend.setOnClickListener(this);
        mImager_classify.setOnClickListener(this);
        mCommodity1.setOnClickListener(this);
        mCommodity2.setOnClickListener(this);


        return view;
    }

    @Override
    protected void initData() {

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
            ImageView pointIv = new ImageView(mContext);
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


    private void buildDatas() {
        datas = new ArrayList<>();
        datas.add(new RotateBean(R.mipmap.image1));
        datas.add(new RotateBean(R.mipmap.image2));
        datas.add(new RotateBean(R.mipmap.image3));
        datas.add(new RotateBean(R.mipmap.image4));
        datas.add(new RotateBean(R.mipmap.image5));
        datas.add(new RotateBean(R.mipmap.image6));
    }

    @Override
    public void onResume() {
        super.onResume();
        isRotate = true;
        ((HomeActivity)mContext).changeTitle("首页");
    }

    @Override
    public void onPause() {
        super.onPause();
        isRotate = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imager_hotsale:
                ((HomeActivity)mContext).changeFragment(new HotsaleFragment(), "HotsaleFragment");
                break;
            case R.id.imager_promotion:
                ((HomeActivity)mContext).changeFragment(new PromotionFragment(), "PromotionFragment");
                break;
            case R.id.imager_deseno:
                ((HomeActivity)mContext).changeFragment(new DesenoFragment(), "DesenoFragment");
                break;
            case R.id.imager_newon:
                ((HomeActivity) mContext).changeFragment(new NewonFragment(), "NewonFragment");
                break;
            case R.id.imager_recommend:
                ((HomeActivity) mContext).changeFragment(new RecommendFragment(), "RecommendFragment");
                break;
            case R.id.imager_classify:
                break;
            case R.id.commodity1:
                break;
            case R.id.commodity2:
                break;
        }
    }
    @Subscribe
    public void onEventMainThread(FirstEvent event) {
        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //反注册EventBus
        EventBus.getDefault().unregister(this);
    }


}
