package com.song.honestshoppingmall.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.bean.LoginResultBean;
import com.song.honestshoppingmall.bean.OrderDetailBean;
import com.song.honestshoppingmall.util.RetrofitUtil;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Judy on 2017/1/9.
 */

public class OrderDetailFragment extends BaseFragment {
    private TextView mSendQuery;
    private TextView mTvOrderid;
    private TextView mTvAddress;
    private TextView mTvState;
    private TextView mTvPayMethod;
    private TextView mTvGeneratedTime;
    private TextView mTvSendTime;
    private TextView mTvHasBill;
    private TextView mTvBillHead;
    private TextView mTvSendRequest;
    private TextView mTvCount;
    private TextView mTvSendPrice;
    private TextView mTvPoint;
    private TextView mTvDeservePrice;
    private TextView mTvCancelOrder;
    private LinearLayout mLl_product_list;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String[] payMethod = {"货到付款", "货到POS机", "在线支付"};
    private String[] sendTime = {"周一至周五送货", "双休日及公众假期送货", "送货时间不限"};
    private String mOrderId;
    private OrderDetailBean mOrderDetailBean;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    showData();

                    break;
                case 1:
                    Toast.makeText(mContext, "没有该订单详情- -", Toast.LENGTH_SHORT).show();
                    ((HomeActivity)mContext).popBackStack();
                    break;
                case 2:
                    Toast.makeText(mContext, "删除订单成功", Toast.LENGTH_SHORT).show();
                    ((HomeActivity)mContext).popBackStack();
                    break;
                case 3:
                    Toast.makeText(mContext, "删除订单失败", Toast.LENGTH_SHORT).show();
                    ((HomeActivity)mContext).popBackStack();
                    break;
            }
        }
    };

    private void showData() {
        mTvOrderid.setText(mOrderId);
        mTvAddress.setText(mOrderDetailBean.getAddressInfo().getAddressArea() + mOrderDetailBean.getAddressInfo().getAddressDetail());

        mTvState.setText(mOrderDetailBean.getOrderInfo().getStatus());
        mTvPayMethod.setText(payMethod[mOrderDetailBean.getPaymentInfo().getType() - 1]);
        mTvGeneratedTime.setText(sdf.format(Long.parseLong(mOrderDetailBean.getOrderInfo().getTime())));
        mTvSendTime.setText(sdf.format(Long.parseLong(mOrderDetailBean.getOrderInfo().getTime())));
        mTvHasBill.setText("是");
        mTvBillHead.setText(mOrderDetailBean.getInvoiceInfo().getInvoiceTitle());
        mTvSendRequest.setText(sendTime[Integer.parseInt(mOrderDetailBean.getDeliveryInfo().getType()) - 1]);

        mTvCount.setText(mOrderDetailBean.getCheckoutAddup().getTotalCount() + "件");
        mTvSendPrice.setText("¥" + mOrderDetailBean.getCheckoutAddup().getFreight());
        mTvPoint.setText(mOrderDetailBean.getCheckoutAddup().getTotalPoint() + "");
        mTvDeservePrice.setText("¥" + mOrderDetailBean.getCheckoutAddup().getTotalPrice());

        int productCount = mOrderDetailBean.getProductList().size();
        for (int i = 0; i < productCount; i++) {
            View view = View.inflate(mContext, R.layout.product_list_item, null);
            OrderDetailBean.ProductListBean bean = mOrderDetailBean.getProductList().get(i);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_number = (TextView) view.findViewById(R.id.tv_number);
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
            LinearLayout ll_product_property = (LinearLayout) view.findViewById(R.id.ll_product_property);
            tv_name.setText(bean.getProduct().getName());
            tv_price.setText("¥" + bean.getProduct().getPrice());
            tv_number.setText(bean.getProdNum() + "件");

            int propertyCount = bean.getProduct().getProductProperty().size();
            for (int j = 0; j < propertyCount; j++) {
                View inflate = View.inflate(mContext, R.layout.product_property_item, null);
                TextView tv_key = (TextView) inflate.findViewById(R.id.tv_product_property_key);
                TextView tv_value = (TextView) inflate.findViewById(R.id.tv_product_property_value);
                tv_key.setText(bean.getProduct().getProductProperty().get(j).getK());
                tv_value.setText(bean.getProduct().getProductProperty().get(j).getV());
                ll_product_property.addView(inflate);
            }

            mLl_product_list.addView(view);
        }
    }


    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_orderdetail, null);
        assignViews(view);
        return view;
    }

    private void assignViews(View view) {
        mLl_product_list = (LinearLayout) view.findViewById(R.id.ll_product_list);
        mSendQuery = (TextView) view.findViewById(R.id.send_query);

        mTvOrderid = (TextView) view.findViewById(R.id.tv_orderid);
        mTvAddress = (TextView) view.findViewById(R.id.tv_address);

        mTvState = (TextView) view.findViewById(R.id.tv_state);
        mTvPayMethod = (TextView) view.findViewById(R.id.tv_pay_method);
        mTvGeneratedTime = (TextView) view.findViewById(R.id.tv_generated_time);
        mTvSendTime = (TextView) view.findViewById(R.id.tv_send_time);
        mTvHasBill = (TextView) view.findViewById(R.id.tv_has_bill);
        mTvBillHead = (TextView) view.findViewById(R.id.tv_bill_head);
        mTvSendRequest = (TextView) view.findViewById(R.id.tv_send_request);

        mTvCount = (TextView) view.findViewById(R.id.tv_count);
        mTvSendPrice = (TextView) view.findViewById(R.id.tv_send_price);
        mTvPoint = (TextView) view.findViewById(R.id.tv_free_price);
        mTvDeservePrice = (TextView) view.findViewById(R.id.tv_deserve_price);

        mTvCancelOrder = (TextView) view.findViewById(R.id.tv_cancel_order);
    }

    @Override
    protected void initData() {
        mTvCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderCancel();
            }
        });


        mOrderId = getArguments().getString("orderId", "");
        getNetData();
    }

    private void orderCancel() {
        RetrofitUtil.getAPIRetrofitInstance().cancelOrder(mOrderId, "20428").enqueue(new Callback<LoginResultBean>() {
            @Override
            public void onResponse(Call<LoginResultBean> call, Response<LoginResultBean> response) {
                if (response.isSuccessful()) {
                    if (response.body().response.equals("orderCancel")) {
                        mHandler.sendEmptyMessage(2);
                    } else {
                        mHandler.sendEmptyMessage(3);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResultBean> call, Throwable t) {

            }
        });
    }

    private void getNetData() {
        String orderId = mOrderId;
        System.out.println(orderId);
        String value = "20428";
        RetrofitUtil.getAPIRetrofitInstance().getOrderDetailBean(orderId, value).enqueue(new Callback<OrderDetailBean>() {
            @Override
            public void onResponse(Call<OrderDetailBean> call, Response<OrderDetailBean> response) {
                if (response.isSuccessful()) {
                    mOrderDetailBean = response.body();
                    System.out.println(mOrderDetailBean.toString());
                    if (mOrderDetailBean.getAddressInfo() != null) {
                        mHandler.sendEmptyMessage(0);
                    } else {
                        mHandler.sendEmptyMessage(1);
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDetailBean> call, Throwable t) {

            }
        });
    }


}
