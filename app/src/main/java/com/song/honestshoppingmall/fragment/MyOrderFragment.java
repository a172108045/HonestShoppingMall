package com.song.honestshoppingmall.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.adapter.MyOrderAdapter;
import com.song.honestshoppingmall.bean.MyOrderBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Judy on 2017/1/8.
 */

public class MyOrderFragment extends BaseFragment implements View.OnClickListener {

    private Button mBt_recent_order;
    private Button mBt_before_order;
    private Button mBt_cancelled_order;
    private ImageView mIv_no_order;
    private RecyclerView mLv_recent_order;
    private MyOrderBean mMyOrderBean;
    private String mGetType = "1";
    private MyOrderAdapter mRecentAdapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
/*            if (mRecentAdapter == null && mMyOrderBean != null) {
                mRecentAdapter = new MyOrderAdapter(mMyOrderBean.getOrderList());
                mLv_recent_order.setAdapter(mRecentAdapter);
            } else {
                mRecentAdapter.notifyDataSetChanged();
            }*/
            mRecentAdapter = null;
            mRecentAdapter = new MyOrderAdapter(mMyOrderBean.getOrderList());
            mLv_recent_order.setAdapter(mRecentAdapter);
        }
    };

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_myorder, null);
        mBt_recent_order = (Button) view.findViewById(R.id.bt_recent_order);
        mBt_before_order = (Button) view.findViewById(R.id.bt_before_order);
        mBt_cancelled_order = (Button) view.findViewById(R.id.bt_cancelled_order);
        mIv_no_order = (ImageView) view.findViewById(R.id.iv_no_order);
        mLv_recent_order = (RecyclerView) view.findViewById(R.id.lv_recent_order);
        return view;
    }

    @Override
    protected void initData() {
        mBt_recent_order.setOnClickListener(this);
        mBt_before_order.setOnClickListener(this);
        mBt_cancelled_order.setOnClickListener(this);

        mLv_recent_order.setLayoutManager(new LinearLayoutManager(mContext));
        initNetData();


    }

    private void initNetData() {
        System.out.println("zhixingle");
        Map<String, String> map = new HashMap<>();
        map.put("type", mGetType);
        map.put("page", "0");
        map.put("pageNum", "10");
        APIRetrofit retrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        String value = 20428 + "";
        retrofitInstance.getMyOrderBean(map, value).enqueue(new Callback<MyOrderBean>() {
            @Override
            public void onResponse(Call<MyOrderBean> call, Response<MyOrderBean> response) {
                if (response.isSuccessful()) {
                    mMyOrderBean = response.body();
                    System.out.println(mMyOrderBean.toString());
                    mHandler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Call<MyOrderBean> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_recent_order:
                mGetType = "1";
                initNetData();

                mBt_recent_order.setEnabled(false);
                mBt_before_order.setEnabled(true);
                mBt_cancelled_order.setEnabled(true);

                mBt_recent_order.setTextColor(Color.WHITE);
                mBt_before_order.setTextColor(Color.BLACK);
                mBt_cancelled_order.setTextColor(Color.BLACK);


                break;
            case R.id.bt_before_order:
                mGetType = "2";
                initNetData();

                mBt_recent_order.setEnabled(true);
                mBt_before_order.setEnabled(false);
                mBt_cancelled_order.setEnabled(true);

                mBt_recent_order.setTextColor(Color.BLACK);
                mBt_before_order.setTextColor(Color.WHITE);
                mBt_cancelled_order.setTextColor(Color.BLACK);

                break;
            case R.id.bt_cancelled_order:
                mGetType = "3";
                initNetData();

                mBt_recent_order.setEnabled(true);
                mBt_before_order.setEnabled(true);
                mBt_cancelled_order.setEnabled(false);

                mBt_recent_order.setTextColor(Color.BLACK);
                mBt_before_order.setTextColor(Color.BLACK);
                mBt_cancelled_order.setTextColor(Color.WHITE);


                break;

        }


    }

}
