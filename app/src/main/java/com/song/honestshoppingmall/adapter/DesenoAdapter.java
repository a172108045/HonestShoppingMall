package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.bean.DesenoBean;
import com.song.honestshoppingmall.fragment.GoodsDetailsFragment;
import com.song.honestshoppingmall.util.Urls;

import java.util.List;

/**
 * Created by yspc on 2017/1/11.
 */

public class DesenoAdapter extends BaseAdapter {
    private Context mContext;
    private List<DesenoBean.ProductListBean> mData;

    public DesenoAdapter(Context context, List<DesenoBean.ProductListBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public DesenoBean.ProductListBean getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.list_deseno_item, null);
            holder.iv_deseno = (ImageView) convertView.findViewById(R.id.iv_deseno);
            holder.tv_deseno_name = (TextView) convertView.findViewById(R.id.tv_deseno_name);
            holder.tv_deseno_name_x = (TextView) convertView.findViewById(R.id.tv_deseno_name_x);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DesenoBean.ProductListBean item = getItem(position);


        Glide.with(mContext.getApplicationContext()).load(Urls.BASE_URL+item.getPic()).into(holder.iv_deseno);


        holder.tv_deseno_name.setText(item.getName());
        holder.tv_deseno_name_x.setText("$"+item.getPrice());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetailsFragment fragment = new GoodsDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pId", mData.get(position).getId());
                fragment.setArguments(bundle);
                ((HomeActivity) mContext).changeFragment(fragment, "GoodsDetailsFragment");
            }
        });

        return convertView;
    }

    class ViewHolder{
        ImageView iv_deseno;
        TextView tv_deseno_name;
        TextView tv_deseno_name_x;
    }

}
