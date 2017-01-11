package com.song.honestshoppingmall.morepagers;

import android.content.Context;
import android.view.View;

/**
 * Created by Judy on 2017/1/11.
 */

public abstract class BasePager {
    public Context mContext;

    public BasePager(Context context) {
        this.mContext = context;
    }

    public abstract View getRootView();
    public abstract void initData();
}
