package com.song.honestshoppingmall.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.adapter.MyOrderAdapter;
import com.song.honestshoppingmall.bean.MyOrderBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.Constants;
import com.song.honestshoppingmall.util.RetrofitUtil;
import com.song.honestshoppingmall.util.SpUtil;

import java.util.HashMap;
import java.util.List;
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
    private int pager = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mIv_no_order.setVisibility(View.INVISIBLE);
                    mLv_recent_order.setVisibility(View.VISIBLE);
                    mRecentAdapter = null;
                    mRecentAdapter = new MyOrderAdapter(mContext, mOrderList, mGetType);
                    mRecentAdapter.setOnLoadMoreListener(new MyOrderAdapter.OnLoadMoreListener() {
                        @Override
                        public void onLoadMore() {
                            if ((pager + 1) * 5 <= mOrderList.size()) {
                                SystemClock.sleep(1000);
                                pager ++;
                                System.out.println("pager="+pager);
                                initNetData();
                                mRecentAdapter.notifyDataSetChanged();

                            }
                        }
                    });
                    mRecentAdapter.startLoadMore(mLv_recent_order, mLv_recent_order.getLayoutManager());
                    mLv_recent_order.setAdapter(mRecentAdapter);
                    break;

                case 1:
                    mIv_no_order.setVisibility(View.VISIBLE);
                    mLv_recent_order.setVisibility(View.INVISIBLE);
                    Toast.makeText(mContext, "没有查询到订单", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private List<MyOrderBean.OrderListBean> mOrderList;

    @Override
    protected View initView() {
        ((HomeActivity)mContext).changeTitle("我的订单");
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
        Map<String, String> map = new HashMap<>();
        map.put("type", mGetType);
        map.put("page", pager + "");
        map.put("pageNum", "5");
        APIRetrofit retrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        String userid = SpUtil.getString(mContext, Constants.USERID, "");
        retrofitInstance.getMyOrderBean(map, userid).enqueue(new Callback<MyOrderBean>() {
            @Override
            public void onResponse(Call<MyOrderBean> call, Response<MyOrderBean> response) {
                if (response.isSuccessful()) {
                    mMyOrderBean = response.body();
                    if (mOrderList == null) {
                        mOrderList = mMyOrderBean.getOrderList();
                    } else {
                        mOrderList.addAll(mMyOrderBean.getOrderList());
                    }
                    if (mOrderList != null && mOrderList.size() != 0) {
                        mHandler.sendEmptyMessage(0);
                    } else {
                        mHandler.sendEmptyMessage(1);
                    }
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
                pager = 0;
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
                pager = 0;
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
                pager = 0;
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

    @Override
    public void onResume() {
        super.onResume();
        switch (mGetType) {
            case "1":
                mBt_recent_order.performClick();
                break;
            case "2":
                mBt_before_order.performClick();
                break;
            case "3":
                mBt_cancelled_order.performClick();
                break;
        }
        initNetData();
    }
}
