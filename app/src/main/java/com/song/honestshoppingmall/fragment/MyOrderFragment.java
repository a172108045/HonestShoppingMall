package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.song.honestshoppingmall.R;

/**
 * Created by Judy on 2017/1/8.
 */

public class MyOrderFragment extends BaseFragment {

    private Button mBt_back_myorder;
    private Button mBt_recent_order;
    private Button mBt_before_order;
    private Button mBt_cancelled_order;
    private ImageView mIv_no_order;
    private ListView mLv_recent_order;
    private ListView mLv_before_order;
    private ListView mLv_cancelled_order;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_myorder, null);
        mBt_back_myorder = (Button) view.findViewById(R.id.bt_back_myorder);
        mBt_recent_order = (Button) view.findViewById(R.id.bt_recent_order);
        mBt_before_order = (Button) view.findViewById(R.id.bt_before_order);
        mBt_cancelled_order = (Button) view.findViewById(R.id.bt_cancelled_order);
        mIv_no_order = (ImageView) view.findViewById(R.id.iv_no_order);
        mLv_recent_order = (ListView) view.findViewById(R.id.lv_recent_order);
        mLv_before_order = (ListView) view.findViewById(R.id.lv_before_order);
        mLv_cancelled_order = (ListView) view.findViewById(R.id.lv_cancelled_order);
        return view;
    }

    @Override
    protected void initData() {

    }
}
