package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Judy on 2017/1/8.
 */

public class SettingFragment extends BaseFragment {
    @Override
    protected View initView() {
        TextView tv = new TextView(mContext);
        tv.setText("这是更多");
        return tv;
    }

    @Override
    protected void initData() {

    }
}
