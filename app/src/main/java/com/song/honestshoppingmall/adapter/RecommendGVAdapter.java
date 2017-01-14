package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.RecommendBean;
import com.song.honestshoppingmall.util.Urls;

/**
 * Created by yspc on 2017/1/14.
 */

public class RecommendGVAdapter extends BaseAdapter {
    private RecommendBean.BrandBean mData;
    private Context mContext;

    public RecommendGVAdapter(Context context, RecommendBean.BrandBean data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.getValue().size();
    }

    @Override
    public RecommendBean.BrandBean.ValueBean getItem(int position) {
        return mData.getValue().get(position);
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
            convertView = View.inflate(mContext, R.layout.grid_recommend, null);
            holder.iv_gv_recommend = (ImageView) convertView.findViewById(R.id.iv_gv_recommend);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RecommendBean.BrandBean.ValueBean item = getItem(position);
        Glide.with(mContext.getApplicationContext()).load(Urls.BASE_URL + item.getPic()).into(holder.iv_gv_recommend);

        return convertView;
    }

    class ViewHolder{
        ImageView iv_gv_recommend;
    }

}
