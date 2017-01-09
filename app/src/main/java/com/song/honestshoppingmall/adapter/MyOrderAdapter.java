package com.song.honestshoppingmall.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.MyOrderBean;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Judy on 2017/1/8.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private List<MyOrderBean.OrderListBean> mOrderList;

    public MyOrderAdapter(List<MyOrderBean.OrderListBean> orderList) {
        this.mOrderList = orderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_myorder, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        System.out.println("里面" + mOrderList.toString());
        MyOrderBean.OrderListBean bean = mOrderList.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        holder.tv_id.setText("订单编号:" + bean.getOrderId());
        holder.tv_price.setText("订单总额:¥ " + bean.getPrice());
        holder.tv_date.setText(sdf.format(Long.parseLong(bean.getTime())));
        holder.tv_state.setText("状态:" + bean.getStatus());
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_id;
        public TextView tv_price;
        public TextView tv_date;
        public TextView tv_state;


        public ViewHolder(View itemView) {
            super(itemView);
            TextView tv_id = (TextView) itemView.findViewById(R.id.tv_id_item_myorder);
            TextView tv_price = (TextView) itemView.findViewById(R.id.tv_price_item_myorder);
            TextView tv_date = (TextView) itemView.findViewById(R.id.tv_date_item_myorder);
            TextView tv_state = (TextView) itemView.findViewById(R.id.tv_state_item_myorder);
            this.tv_id = tv_id;
            this.tv_price = tv_price;
            this.tv_date = tv_date;
            this.tv_state = tv_state;
        }
    }

}
