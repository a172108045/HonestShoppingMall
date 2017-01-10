package com.song.honestshoppingmall.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;

/**
 * Created by Judy on 2017/1/10.
 */

public class CheckSuccessFragment extends BaseFragment {
    private TextView mTvOrderid;
    private TextView mTvTotalPrice;
    private TextView mTvCheckMethod;
    private TextView mTvEnterShopping;
    private TextView mTvEnterQuery;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_checksuccess, null);
        assignViews(view);
        return view;
    }

    private void assignViews(View view) {
        mTvOrderid = (TextView) view.findViewById(R.id.tv_orderid);
        mTvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);
        mTvCheckMethod = (TextView) view.findViewById(R.id.tv_check_method);
        mTvEnterShopping = (TextView) view.findViewById(R.id.tv_enter_shopping);
        mTvEnterQuery = (TextView) view.findViewById(R.id.tv_enter_query);
    }

    @Override
    protected void initData() {
        final String orderId = getArguments().getString("orderId", "");
        String totalPrice = getArguments().getString("totalPrice", "");
        String checkMethod = getArguments().getString("checkMethod", "");
        mTvOrderid.setText(orderId);
        mTvTotalPrice.setText(totalPrice);
        mTvCheckMethod.setText(checkMethod);

        mTvEnterShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)mContext).removeAllFragment();
                ((HomeActivity)mContext).changeFragment(new CategoryFragment(), "CategoryFragment");
            }
        });

        mTvEnterQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("orderId", orderId);
                ((HomeActivity)mContext).removeAllFragment();
                ((HomeActivity)mContext).changeFragment(new OrderDetailFragment(), "OrderDetailFragment", bundle);
            }
        });

    }
}
