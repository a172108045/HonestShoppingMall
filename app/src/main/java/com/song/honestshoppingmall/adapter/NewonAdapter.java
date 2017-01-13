package com.song.honestshoppingmall.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.NewonBean;
import com.song.honestshoppingmall.util.Urls;

import java.util.List;

/**
 * Created by yspc on 2017/1/12.
 */

public class NewonAdapter extends BaseAdapter {
    private Context mContext;
    private List<NewonBean.ProductListBean> mData;


    public NewonAdapter(Context context, List<NewonBean.ProductListBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public NewonBean.ProductListBean getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.list_newon_item, null);
            holder.iv_newon = (ImageView) convertView.findViewById(R.id.iv_newon);
            holder.tv_newon_name = (TextView) convertView.findViewById(R.id.tv_newon_name);
            holder.tv_newon_name_x = (TextView) convertView.findViewById(R.id.tv_newon_name_x);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NewonBean.ProductListBean item = getItem(position);

        Glide.with(mContext.getApplicationContext()).load(Urls.BASE_URL+item.getPic()).into(holder.iv_newon);

        holder.tv_newon_name.setText(item.getName());
        holder.tv_newon_name_x.setText("$"+item.getPrice());
        return convertView;
    }

    class ViewHolder{
        ImageView iv_newon;
        TextView tv_newon_name;
        TextView tv_newon_name_x;
    }

}