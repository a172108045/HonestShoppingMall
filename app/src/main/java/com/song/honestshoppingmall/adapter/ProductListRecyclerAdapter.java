package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.bean.FilterProductListBean;
import com.song.honestshoppingmall.fragment.GoodsDetailsFragment;
import com.song.honestshoppingmall.util.DensityUtil;
import com.song.honestshoppingmall.util.Urls;

import java.util.List;

/**
 * @Creator Administrator
 * @CreatedDate 2017/1/12 15:33
 * @Description ${TODO}
 * @Updater $Author$
 * @UpdateTime $Date$
 * @UpdateDesc ${TODO}
 */

public class ProductListRecyclerAdapter extends RecyclerView.Adapter {
    private final List<FilterProductListBean.ProductListBean> mDatas;
    private final Context mContext;

    public ProductListRecyclerAdapter(Context context, List<FilterProductListBean.ProductListBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_goodslist_productdetail, null);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof RecyclerViewHolder){
            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
            final FilterProductListBean.ProductListBean productListBean = mDatas.get(position);

            Glide.with(mContext.getApplicationContext()).load(Urls.BASE_URL + productListBean.getPic()).override(DensityUtil.dip2px(mContext, 150), DensityUtil.dip2px(mContext, 250)).into(recyclerViewHolder.iv_pic);
            recyclerViewHolder.tv_name.setText(productListBean.getName());
            recyclerViewHolder.tv_price.setText("￥" + productListBean.getPrice());
            recyclerViewHolder.tv_comment.setText("评论：" + productListBean.getCommentCount());

            recyclerViewHolder.iv_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    //第二个参数为当前选中的商品pId( productListBean.getId() ),进入商品详情
                    bundle.putInt("pId", productListBean.getId());
                    ((HomeActivity) mContext).changeFragment(new GoodsDetailsFragment(), "GoodsDetailsFragment", bundle);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public ImageView iv_pic;
        public TextView tv_name;
        public TextView tv_price;
        public TextView tv_comment;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            //商品图片
            iv_pic = (ImageView) itemView.findViewById(R.id.iv_item_productlist_pic);
            //商品名称
            tv_name = (TextView) itemView.findViewById(R.id.tv_item_productlist_name);
            //商品价格
            tv_price = (TextView) itemView.findViewById(R.id.tv_item_productlist_price);
            //商品评论数
            tv_comment = (TextView) itemView.findViewById(R.id.tv_item_productlist_comment);
        }
    }
}
