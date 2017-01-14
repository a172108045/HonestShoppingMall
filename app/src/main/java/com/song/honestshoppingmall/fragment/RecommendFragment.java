package com.song.honestshoppingmall.fragment;

import android.view.View;

import com.song.honestshoppingmall.activity.HomeActivity;

/**
 * Created by yspc on 2017/1/13.
 */

public class RecommendFragment extends BaseFragment {
    @Override
    protected View initView() {
        ((HomeActivity) mContext).changeTitle("推荐品牌");
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity)mContext).changeTitle("推荐品牌");
    }
}
