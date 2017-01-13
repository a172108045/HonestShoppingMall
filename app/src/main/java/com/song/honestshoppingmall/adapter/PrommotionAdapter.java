package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.PrommotionBean;

import java.util.List;

/**
 * Created by yspc on 2017/1/11.
 */

public class PrommotionAdapter extends BaseAdapter {
    private Context mContext;
    private List<PrommotionBean.TopicBean> mData;

    public PrommotionAdapter(Context context, List<PrommotionBean.TopicBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public PrommotionBean.TopicBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_prommotion, null);
            ImageView iv_circle = (ImageView)convertView.findViewById(R.id.iv_circle);
        }
        return convertView;
    }

    class ViewHolder{
        ImageView image_circle;
    }

}
