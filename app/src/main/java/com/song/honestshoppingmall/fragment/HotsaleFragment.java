package com.song.honestshoppingmall.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.adapter.HotSaleListAdapter;
import com.song.honestshoppingmall.adapter.RotateHotsaleVpAdapter;
import com.song.honestshoppingmall.bean.RotateBean;
import com.song.honestshoppingmall.bean.ScareBuyBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by yspc on 2017/1/11.
 */

public class HotsaleFragment extends Fragment implements View.OnClickListener{
    private static final int TIME = 3000;

    private View view;
    private LinearLayout pointLl;
    private ViewPager viewPager;
    private List<RotateBean> datas;
    private RotateHotsaleVpAdapter vpAdapter;
    private ListView mLv_hotsale;
    private List<ScareBuyBean.ProductListBean> mData ;
    private Context mContext;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    private HotSaleListAdapter mAdapter;
    private LinearLayout mLiner_back;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        this.mData = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.item_hotsale, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.rotate_hotsale_vp);
        pointLl = (LinearLayout) view.findViewById(R.id.rotate_point_container);
        mLv_hotsale = (ListView) view.findViewById(R.id.lv_hotsale);
        mLiner_back = (LinearLayout) view.findViewById(R.id.liner_back);
        mLiner_back.setOnClickListener(this);
        initNetData();


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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

}

    private void initNetData() {
        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        final Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("pageNum","10");
        apiRetrofitInstance.getScareBuy(map).enqueue(new Callback<ScareBuyBean>() {



            @Override
            public void onResponse(Call<ScareBuyBean> call, Response<ScareBuyBean> response) {
                if (response.isSuccessful()) {
                    ScareBuyBean body = response.body();
                    if (body.error==null){
                        List<ScareBuyBean.ProductListBean> productList = body.getProductList();
                        mData.clear();
                        mData.addAll(productList);

                        if (mAdapter == null){
                            mAdapter = new HotSaleListAdapter(mContext, mData);
                            mLv_hotsale.setAdapter(mAdapter);
                        }else{
                            mAdapter.notifyDataSetChanged();
                        }


                    }else{
                        Toast.makeText(mContext, body.error, Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ScareBuyBean> call, Throwable t) {
            Toast.makeText(mContext,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        HotSaleListAdapter adapter = new HotSaleListAdapter(mContext,mData);
        mLv_hotsale.setAdapter(adapter);

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.liner_back:
                ((HomeActivity) mContext).changeFragment(new HomeFragment(),"HomeFragment");
                break;
        }
    }
}
