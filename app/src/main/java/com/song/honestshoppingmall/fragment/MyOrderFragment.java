package com.song.honestshoppingmall.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.adapter.MyOrderAdapter;

/**
 * Created by Judy on 2017/1/8.
 */

public class MyOrderFragment extends BaseFragment implements View.OnClickListener {

    private Button mBt_back_myorder;
    private Button mBt_recent_order;
    private Button mBt_before_order;
    private Button mBt_cancelled_order;
    private ImageView mIv_no_order;
    private RecyclerView mLv_recent_order;
    private RecyclerView mLv_before_order;
    private RecyclerView mLv_cancelled_order;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_myorder, null);
        mBt_back_myorder = (Button) view.findViewById(R.id.bt_back_myorder);
        mBt_recent_order = (Button) view.findViewById(R.id.bt_recent_order);
        mBt_before_order = (Button) view.findViewById(R.id.bt_before_order);
        mBt_cancelled_order = (Button) view.findViewById(R.id.bt_cancelled_order);
        mIv_no_order = (ImageView) view.findViewById(R.id.iv_no_order);
        mLv_recent_order = (RecyclerView) view.findViewById(R.id.lv_recent_order);
        mLv_before_order = (RecyclerView) view.findViewById(R.id.lv_before_order);
        mLv_cancelled_order = (RecyclerView) view.findViewById(R.id.lv_cancelled_order);
        return view;
    }

    @Override
    protected void initData() {
        mBt_recent_order.setOnClickListener(this);
        mBt_before_order.setOnClickListener(this);
        mBt_cancelled_order.setOnClickListener(this);

        mLv_recent_order.setLayoutManager(new LinearLayoutManager(mContext));
        mLv_recent_order.setAdapter(new MyOrderAdapter());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_recent_order:
                mBt_recent_order.setEnabled(false);
                mBt_before_order.setEnabled(true);
                mBt_cancelled_order.setEnabled(true);

                mBt_recent_order.setTextColor(Color.WHITE);
                mBt_before_order.setTextColor(Color.BLACK);
                mBt_cancelled_order.setTextColor(Color.BLACK);

                mLv_recent_order.setVisibility(View.VISIBLE);
                mLv_before_order.setVisibility(View.INVISIBLE);
                mLv_cancelled_order.setVisibility(View.INVISIBLE);

                break;
            case R.id.bt_before_order:
                mBt_recent_order.setEnabled(true);
                mBt_before_order.setEnabled(false);
                mBt_cancelled_order.setEnabled(true);

                mBt_recent_order.setTextColor(Color.BLACK);
                mBt_before_order.setTextColor(Color.WHITE);
                mBt_cancelled_order.setTextColor(Color.BLACK);

                mLv_recent_order.setVisibility(View.INVISIBLE);
                mLv_before_order.setVisibility(View.VISIBLE);
                mLv_cancelled_order.setVisibility(View.INVISIBLE);

                break;
            case R.id.bt_cancelled_order:
                mBt_recent_order.setEnabled(true);
                mBt_before_order.setEnabled(true);
                mBt_cancelled_order.setEnabled(false);

                mBt_recent_order.setTextColor(Color.BLACK);
                mBt_before_order.setTextColor(Color.BLACK);
                mBt_cancelled_order.setTextColor(Color.WHITE);

                mLv_recent_order.setVisibility(View.INVISIBLE);
                mLv_before_order.setVisibility(View.INVISIBLE);
                mLv_cancelled_order.setVisibility(View.VISIBLE);

                break;

        }


    }

}
