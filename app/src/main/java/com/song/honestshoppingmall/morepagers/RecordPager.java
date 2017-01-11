package com.song.honestshoppingmall.morepagers;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Judy on 2017/1/11.
 */

public class RecordPager extends BasePager {
    public RecordPager(Context context) {
        super(context);
    }

    @Override
    public View getRootView() {
        TextView tv = new TextView(mContext);
        tv.setText("浏览记录界面");
        return tv;
    }

    @Override
    public void initData() {

    }
}
