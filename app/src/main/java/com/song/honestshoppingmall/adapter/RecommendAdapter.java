package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.song.honestshoppingmall.bean.RecommendBean;

import java.util.List;

/**
 * Created by yspc on 2017/1/13.
 */

public class RecommendAdapter extends BaseAdapter {

    private List<RecommendBean.BrandBean> mData;
    private Context mContext;

    public RecommendAdapter(Context context, List<RecommendBean.BrandBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }
}
