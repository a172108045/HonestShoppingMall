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
import com.song.honestshoppingmall.bean.CollectionBean;
import com.song.honestshoppingmall.fragment.GoodsDetailsFragment;
import com.song.honestshoppingmall.util.DensityUtil;
import com.song.honestshoppingmall.util.Urls;

import java.util.List;

/**
 * @Creator Administrator
 * @CreatedDate 2017/1/14 10:11
 * @Description ${TODO}
 * @Updater $Author$
 * @UpdateTime $Date$
 * @UpdateDesc ${TODO}
 */

public class ColletionAdapter extends BaseAdapter {


    private final List<CollectionBean.ProductListBean> mDatas;
    private final Context mContext;

    public ColletionAdapter(Context context, List<CollectionBean.ProductListBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.item_goodslist_productdetail, null);

            holder = new ViewHolder();
            //商品图片
            holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_item_productlist_pic);
            //商品名称
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_item_productlist_name);
            //商品价格
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_item_productlist_price);
            //商品评论隐藏
            holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_item_productlist_comment);
            holder.tv_comment.setVisibility(View.GONE);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        final CollectionBean.ProductListBean productListBean = mDatas.get(position);

        Glide.with(mContext.getApplicationContext()).load(Urls.BASE_URL + productListBean.getPic()).
                override(DensityUtil.dip2px(mContext, 150), DensityUtil.dip2px(mContext, 250)).into(holder.iv_pic);
        holder.tv_name.setText(productListBean.getName());
        holder.tv_price.setText("￥" + productListBean.getPrice());

        holder.iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                //第二个参数为当前选中的商品pId( productListBean.getId() ),进入商品详情
                bundle.putInt("pId", productListBean.getId());
                ((HomeActivity) mContext).changeFragment(new GoodsDetailsFragment(), "GoodsDetailsFragment", bundle);
            }
        });

        return convertView;
    }

    class ViewHolder{
        public ImageView iv_pic;
        public TextView tv_name;
        public TextView tv_price;
        public TextView tv_comment;
    }
}
