package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.bean.PrommoItem1Bean;
import com.song.honestshoppingmall.fragment.GoodsDetailsFragment;
import com.song.honestshoppingmall.util.Urls;

import java.util.List;

/**
 * Created by yspc on 2017/1/14.
 */

public class PrommoItem1Adapter extends BaseAdapter {
    private List<PrommoItem1Bean.ProductListBean> mData;
    private Context mContext;

    public PrommoItem1Adapter(Context context, List<PrommoItem1Bean.ProductListBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public PrommoItem1Bean.ProductListBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.list_prommo1_item, null);
            holder.iv_prommoitem1 = (ImageView) convertView.findViewById(R.id.iv_prommoitem1);
            holder.tv_prommoitem1_name1 = (TextView) convertView.findViewById(R.id.tv_prommoitem1_name1);
            holder.tv_prommoitem1_name2 = (TextView) convertView.findViewById(R.id.tv_prommoitem1_name2);
            holder.tv_prommoitem1_name3 = (TextView) convertView.findViewById(R.id.tv_prommoitem1_name2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PrommoItem1Bean.ProductListBean item = getItem(position);
        Glide.with(mContext.getApplicationContext()).load(Urls.BASE_URL + item.getPic()).into(holder.iv_prommoitem1);
        holder.tv_prommoitem1_name1.setText(item.getName());
        holder.tv_prommoitem1_name1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_prommoitem1_name2.setText(item.getMarketPrice() + "");
        holder.tv_prommoitem1_name3.setText("$"+item.getPrice());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetailsFragment fragment = new GoodsDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pId", mData.get(position).getId());
                fragment.setArguments(bundle);
                ((HomeActivity)mContext).changeFragment(fragment, "GoodsDetailsFragment");
            }
        });

        return convertView;
    }

    class ViewHolder{
        ImageView iv_prommoitem1;
        TextView tv_prommoitem1_name1;
        TextView tv_prommoitem1_name2;
        TextView tv_prommoitem1_name3;
    }

}
