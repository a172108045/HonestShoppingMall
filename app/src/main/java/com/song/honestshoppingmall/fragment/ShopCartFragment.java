package com.song.honestshoppingmall.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Judy on 2017/1/8.
 */

public class ShopCartFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater) {
        TextView tv = new TextView(mContext);
        tv.setText("这是购物车");
        return tv;
    }

    @Override
    protected void initData() {

    }
}
