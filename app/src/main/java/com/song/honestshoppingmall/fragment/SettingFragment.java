package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;

/**
 * Created by Judy on 2017/1/8.
 */

public class SettingFragment extends BaseFragment {
    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_setting,null);
        TextView textView = (TextView) view.findViewById(R.id.title_setting);
        textView.setText("设置");

        //张业松跳转我的订单界面用
        Button button1 = new Button(mContext);
        button1.setText("我的订单界面");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)mContext).changeFragment(new MyOrderFragment(), "MyOrderFragment");
            }
        });
        ((LinearLayout)view).addView(button1);


        return view;
    }

    @Override
    protected void initData() {

    }
}
