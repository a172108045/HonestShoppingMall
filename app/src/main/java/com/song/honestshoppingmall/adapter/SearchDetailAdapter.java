package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.bean.SearchDetailBean;
import com.song.honestshoppingmall.event.FirstEvent;
import com.song.honestshoppingmall.fragment.GoodsDetailsFragment;
import com.song.honestshoppingmall.util.Urls;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by lizhenquan on 2017/1/10.
 */

public class SearchDetailAdapter extends RecyclerView.Adapter<SearchDetailAdapter.MyViewHolder>  {
    private  Context mContext;
    private  List<SearchDetailBean.ProductListBean> mData;

    public SearchDetailAdapter(Context context, List<SearchDetailBean.ProductListBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_searchdetail_item, parent,false);
        SearchDetailAdapter.MyViewHolder holder = new SearchDetailAdapter.MyViewHolder(view);
        return holder;

    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final SearchDetailBean.ProductListBean productListBean = mData.get(position);
        String name = productListBean.getName();
        int marketPrice = productListBean.getMarketPrice();
        String pic = productListBean.getPic();

        Glide.with(mContext.getApplicationContext()).load(Urls.BASE_URL+pic).into(holder.mIv_searchdetail);
        holder.mTv_product_name.setText(name);
        holder.mTv_product_price.setText("$"+marketPrice);
        holder.myitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳到商品详情页面
                EventBus.getDefault().postSticky( new FirstEvent(productListBean.getId()));
                Bundle bundle = new Bundle();
                bundle.putInt("pId",productListBean.getId());
                ((HomeActivity) mContext).changeFragment(new GoodsDetailsFragment(),"GoodsDetailsFragment",bundle);


            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class MyViewHolder extends  RecyclerView.ViewHolder{

        private  View myitemView;
        ImageView mIv_searchdetail;
          TextView mTv_product_name;
          TextView mTv_product_price;
          TextView mTv_product_shoucang;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.myitemView = itemView;
            mIv_searchdetail = (ImageView) itemView.findViewById(R.id.iv_searchdetail);
            mTv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
            mTv_product_price = (TextView) itemView.findViewById(R.id.tv_product_price);
            mTv_product_shoucang = (TextView) itemView.findViewById(R.id.tv_product_shoucang);
        }
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
}
