package com.song.honestshoppingmall.fragment;

import android.view.View;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;

/**
 * Created by zan on 2017/1/12.
 */

public class AddAddressFragment extends BaseFragment {
    @Override
    protected View initView() {
        ((HomeActivity)mContext).changeTitle("添加地址");
        View view = View.inflate(mContext, R.layout.fragment_address_add, null);
        return view;
    }

    @Override
    protected void initData() {

    }
}
