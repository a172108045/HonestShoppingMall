package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.RecommendBean;
import com.song.honestshoppingmall.view.MyGridView;

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
    public RecommendBean.BrandBean getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.item_recommend, null);
            holder.tv_lv_recommend = (TextView) convertView.findViewById(R.id.tv_lv_recommend);
            holder.gv_recommend = (MyGridView) convertView.findViewById(R.id.gv_recommend);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RecommendGVAdapter mGVAdapter = new RecommendGVAdapter(mContext, mData.get(position));
        RecommendBean.BrandBean item = getItem(position);
        holder.tv_lv_recommend.setText(item.getKey());
        holder.gv_recommend.setAdapter(mGVAdapter);
        return convertView;
    }

    class ViewHolder{
        TextView tv_lv_recommend;
        MyGridView gv_recommend;
    }

}
