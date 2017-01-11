package com.song.honestshoppingmall.morepagers;

import android.content.Context;
import android.view.View;

import com.song.honestshoppingmall.R;

/**
 * Created by Judy on 2017/1/11.
 */

public class AboutPager extends BasePager {
    public AboutPager(Context context) {
        super(context);
    }

    @Override
    public View getRootView() {
        View view = View.inflate(mContext, R.layout.pager_about, null);
        return view;
    }

    @Override
    public void initData() {

    }
}
