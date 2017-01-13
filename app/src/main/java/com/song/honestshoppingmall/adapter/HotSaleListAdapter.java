package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.bean.ScareBuyBean;
import com.song.honestshoppingmall.fragment.GoodsDetailsFragment;
import com.song.honestshoppingmall.util.Urls;

import java.util.List;

/**
 * Created by yspc on 2017/1/11.
 */

public class HotSaleListAdapter extends BaseAdapter {
    private Context mContext;
    private List<ScareBuyBean.ProductListBean> mData;

    public HotSaleListAdapter(Context context, List<ScareBuyBean.ProductListBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public ScareBuyBean.ProductListBean getItem(int position) {
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
            convertView = View.inflate(mContext, R.layout.list_hotsale_item, null);
            holder.iv_hotsale = (ImageView) convertView.findViewById(R.id.iv_hotsale);
            holder.tv_hotsale_name = (TextView) convertView.findViewById(R.id.tv_hotsale_name);
            holder.tv_hotsale_origin_price = (TextView) convertView.findViewById(R.id.tv_hotsale_origin_price);
            holder.tv_hotsale_nowprice = (TextView) convertView.findViewById(R.id.tv_hotsale_nowprice);
            holder.btn_hotsale_buy = (Button) convertView.findViewById(R.id.btn_hotsale_buy);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ScareBuyBean.ProductListBean item = getItem(position);


        Glide.with(mContext.getApplicationContext()).load(Urls.BASE_URL+item.getPic()).into(holder.iv_hotsale);


        holder.tv_hotsale_name.setText(item.getName());
        holder.tv_hotsale_origin_price.setText(""+item.getPrice());
        holder.tv_hotsale_nowprice.setText("$"+item.getLimitPrice());
        holder.tv_hotsale_origin_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );

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

    class ViewHolder {
        ImageView iv_hotsale;
        TextView tv_hotsale_name;
        TextView tv_hotsale_origin_price;
        TextView tv_hotsale_nowprice;
        Button btn_hotsale_buy;
    }
}
