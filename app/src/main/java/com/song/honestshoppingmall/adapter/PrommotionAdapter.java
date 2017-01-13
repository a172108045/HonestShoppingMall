package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.PrommotionBean;
import com.song.honestshoppingmall.util.Urls;

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
            holder.iv_prommotion = (ImageView) convertView.findViewById(R.id.iv_prommotion);
            holder.tv_prommotion = (TextView) convertView.findViewById(R.id.tv_prommotion);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PrommotionBean.TopicBean item = getItem(position);

        Glide.with(mContext.getApplicationContext()).load(Urls.BASE_URL+item.getPic()).into(holder.iv_prommotion);

        holder.tv_prommotion.setText(item.getName());

        return convertView;
    }

    class ViewHolder{
        ImageView iv_prommotion;
        TextView tv_prommotion;
    }

}
