package com.song.honestshoppingmall.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.song.honestshoppingmall.R;

/**
 * Created by Judy on 2017/1/8.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_myorder, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_id.setText("订单编号:20170101");
        holder.tv_price.setText("订单总额:¥ 200.0");
        holder.tv_date.setText("2017-01-08 21:26:08");
        holder.tv_state.setText("状态:未处理");
    }

    @Override
    public int getItemCount() {
        return 5;
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
