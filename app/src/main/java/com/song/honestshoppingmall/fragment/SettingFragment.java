package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.TextView;

import com.song.honestshoppingmall.R;

/**
 * Created by Judy on 2017/1/8.
 */

public class SettingFragment extends BaseFragment {
    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_setting,null);
        TextView textView = (TextView) view.findViewById(R.id.title_setting);
        textView.setText("设置");
        return view;
    }

    @Override
    protected void initData() {

    }
}
