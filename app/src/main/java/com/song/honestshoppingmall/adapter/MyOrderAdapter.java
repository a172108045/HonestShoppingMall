package com.song.honestshoppingmall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.bean.MyOrderBean;
import com.song.honestshoppingmall.fragment.OrderDetailFragment;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Judy on 2017/1/8.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private static final int TYPE_COMMON_VIEW = 0;
    private static final int TYPE_FOOTER_VIEW = 1;
    private String mGetType;
    private Context mContext;
    private List<MyOrderBean.OrderListBean> mOrderList;
    private RelativeLayout mFooterLayout;
    private OnLoadMoreListener mLoadMoreListener;

    public MyOrderAdapter(Context context, List<MyOrderBean.OrderListBean> orderList, String getType) {
        this.mContext = context;
        this.mOrderList = orderList;
        this.mGetType = getType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        switch (viewType) {
            case TYPE_COMMON_VIEW:
                View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_item_myorder, parent, false);
                holder = new ViewHolder(view);
                break;

            case TYPE_FOOTER_VIEW:
                if (mFooterLayout == null) {
                    mFooterLayout = new RelativeLayout(mContext);
                }
                holder = new ViewHolder(View.inflate(mContext, R.layout.footer_view, null));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_COMMON_VIEW:
                final MyOrderBean.OrderListBean bean = mOrderList.get(position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("orderId", bean.getOrderId());
                        bundle.putString("getType", mGetType);
                        ((HomeActivity)mContext).changeFragment(new OrderDetailFragment(), "OrderDetailFragment", bundle);

                    }
                });
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                holder.tv_id.setText("订单编号:" + bean.getOrderId());
                holder.tv_price.setText("订单总额:¥ " + bean.getPrice());
                holder.tv_date.setText(sdf.format(Long.parseLong(bean.getTime())));
                holder.tv_state.setText("状态:" + bean.getStatus());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
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
            this.itemView = itemView;
        }

    }

    private boolean isFooterView(int position) {
        return position >= getItemCount() - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooterView(position)) {
            return TYPE_FOOTER_VIEW;
        } else {
            return TYPE_COMMON_VIEW;
        }

    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isFooterView(holder.getLayoutPosition())) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isFooterView(position)) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    public void startLoadMore(RecyclerView recyclerView, final RecyclerView.LayoutManager layoutManager) {
        if (mLoadMoreListener == null) {
            return;
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (findLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {
                        scrollLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (findLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {
                    scrollLoadMore();
                }
            }
        });
    }

    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        return layoutManager.getChildCount() - 1;

    }

    public interface OnLoadMoreListener {
        public void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mLoadMoreListener = onLoadMoreListener;
    }

    private void scrollLoadMore() {
        mLoadMoreListener.onLoadMore();
    }


}
