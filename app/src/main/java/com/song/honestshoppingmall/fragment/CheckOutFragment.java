package com.song.honestshoppingmall.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.bean.AddressBean;
import com.song.honestshoppingmall.bean.CheckOutBean;
import com.song.honestshoppingmall.bean.OrderSubmitBean;
import com.song.honestshoppingmall.util.Constants;
import com.song.honestshoppingmall.util.DensityUtil;
import com.song.honestshoppingmall.util.RetrofitUtil;
import com.song.honestshoppingmall.util.SpUtil;
import com.song.honestshoppingmall.util.Urls;
import com.song.honestshoppingmall.view.SelectAddressDialog;
import com.song.honestshoppingmall.view.WriteBillDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Judy on 2017/1/10.
 */

public class CheckOutFragment extends BaseFragment implements View.OnClickListener {
    private TextView mTvCustomName;
    private TextView mTvCustomPhone;
    private TextView mTvCustomAddress;
    private TextView mTvCheckMethod;
    private TextView mTvSendTime;
    private TextView mTvBillHead;
    private LinearLayout mLlProductList;
    private TextView mTvCount;
    private TextView mTvSendPrice;
    private TextView mTvFreePrice;
    private TextView mTvDeservePrice;
    private LinearLayout mLl_send_address;
    private LinearLayout mLl_check_method;
    private LinearLayout mLl_send_time;
    private LinearLayout mLl_write_bill;

    private final String[] checkMethods = {"现金-到付", "到付-POS机", "支付宝"};
    private final String[] sendTimes = {"周一至周五送货", "双休日及公众假期送货", "时间不限，工作日双休日及公众假期均可送货"};
    private final String[] billTitles = {"个人", "单位"};
    private final String[] billContents = {"图书", "音响", "图像", "软件"};
    private String billTitle = "请选择";
    private String billContent = "请选择";
    private CheckOutBean mCheckOutBean;
    private LinearLayout mLl_prom_msg;
    private Button mBt_check_now;

    private String sku;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    showData();
                    break;

                case 1:
                case 2:
                    Toast.makeText(mContext, "请求数据错误", Toast.LENGTH_SHORT).show();
                    ((HomeActivity)mContext).popBackStack();
                    break;

                case 3:
                    String orderId = mOrderSubmitBean.getOrderInfo().getOrderId();
                    Bundle bundle = new Bundle();
                    bundle.putString("orderId", orderId);
                    bundle.putString("totalPrice", mTvDeservePrice.getText().toString());
                    bundle.putString("checkMethod", mTvCheckMethod.getText().toString());
                    ((HomeActivity)mContext).removeAllFragment();
                    ((HomeActivity)mContext).changeFragment(new CheckSuccessFragment(), "CheckSuccessFragment", bundle);
                    break;

                case 4:
                case 5:
                    Toast.makeText(mContext, "请求数据错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private OrderSubmitBean mOrderSubmitBean;


    @Override
    protected View initView() {
        ((HomeActivity)mContext).changeTitle("商品结算");
        View view = View.inflate(mContext, R.layout.fragment_checkout, null);
        assignViews(view);
        return view;
    }

    private void assignViews(View view) {
        mTvCustomName = (TextView) view.findViewById(R.id.tv_custom_name);
        mTvCustomPhone = (TextView) view.findViewById(R.id.tv_custom_phone);
        mTvCustomAddress = (TextView) view.findViewById(R.id.tv_custom_address);
        mTvCheckMethod = (TextView) view.findViewById(R.id.tv_check_method);
        mTvSendTime = (TextView) view.findViewById(R.id.tv_send_time);
        mTvBillHead = (TextView) view.findViewById(R.id.tv_bill_head);
        mLlProductList = (LinearLayout) view.findViewById(R.id.ll_product_list);

        mTvCount = (TextView) view.findViewById(R.id.tv_count);
        mTvSendPrice = (TextView) view.findViewById(R.id.tv_send_price);
        mTvFreePrice = (TextView) view.findViewById(R.id.tv_free_price);
        mTvDeservePrice = (TextView) view.findViewById(R.id.tv_deserve_price);
        mLl_prom_msg = (LinearLayout) view.findViewById(R.id.ll_prom_msg);
        mLl_send_address = (LinearLayout) view.findViewById(R.id.ll_send_address);
        mLl_check_method = (LinearLayout) view.findViewById(R.id.ll_check_method);
        mLl_send_time = (LinearLayout) view.findViewById(R.id.ll_send_time);
        mLl_write_bill = (LinearLayout) view.findViewById(R.id.ll_write_bill);
        mBt_check_now = (Button) view.findViewById(R.id.bt_check_now);

    }

    @Override
    protected void initData() {
        sku = getArguments().getString("sku", "");
        String userid = SpUtil.getString(mContext, Constants.USERID, "");
        if (TextUtils.isEmpty(userid)) {
            return;
        }
        RetrofitUtil.getAPIRetrofitInstance().getAddressBean(userid).enqueue(new Callback<AddressBean>() {
            @Override
            public void onResponse(Call<AddressBean> call, Response<AddressBean> response) {
                if (response.isSuccessful()) {
                    if (!response.body().response.equals("error")) {
                        final List<AddressBean.AddressListBean> addressList = response.body().getAddressList();
                        if (addressList != null) {
                            mTvCustomName.setText(addressList.get(0).getName());
                            mTvCustomPhone.setText(addressList.get(0).getPhoneNumber());
                            mTvCustomAddress.setText(addressList.get(0).getAddressArea() + addressList.get(0).getAddressDetail());
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<AddressBean> call, Throwable t) {

            }
        });


        mLl_send_address.setOnClickListener(this);
        mLl_check_method.setOnClickListener(this);
        mLl_send_time.setOnClickListener(this);
        mLl_write_bill.setOnClickListener(this);
        mBt_check_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCheck();
            }
        });

        initNetData();
    }

    private void submitCheck() {
        String userid = SpUtil.getString(mContext, Constants.USERID, "");
        Map<String,String> params = new HashMap<>();
        params.put("sku", sku);
        params.put("addressId", "139");
        params.put("paymentType", "1");
        params.put("deliveryType", "1");
        params.put("invoiceType", "1");
        params.put("invoiceTitle", mTvBillHead.getText().toString());
        params.put("invoiceContent", "1");

        RetrofitUtil.getAPIRetrofitInstance().getOrderSubmitBean(params, userid).enqueue(new Callback<OrderSubmitBean>() {
            @Override
            public void onResponse(Call<OrderSubmitBean> call, Response<OrderSubmitBean> response) {
                if (response.isSuccessful()) {
                    mOrderSubmitBean = response.body();
                    if (mOrderSubmitBean.getOrderInfo() != null) {
                        mHandler.sendEmptyMessage(3);
                    } else {
                        mHandler.sendEmptyMessage(4);
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderSubmitBean> call, Throwable t) {
                mHandler.sendEmptyMessage(5);
            }
        });

    }

    private void initNetData() {
        System.out.println("sku="+sku);
        String userid = SpUtil.getString(mContext, Constants.USERID, "");
        if (TextUtils.isEmpty(userid)) {
            Toast.makeText(mContext, "请先进行登录", Toast.LENGTH_SHORT).show();
            ((HomeActivity)mContext).popBackStack();
            return;
        }
        RetrofitUtil.getAPIRetrofitInstance().getCheckOutBean(sku, userid).enqueue(new Callback<CheckOutBean>() {
            @Override
            public void onResponse(Call<CheckOutBean> call, Response<CheckOutBean> response) {
                if (response.isSuccessful()) {
                    mCheckOutBean = response.body();
                    if (mCheckOutBean.getAddressInfo() != null) {
                        mHandler.sendEmptyMessage(0);
                    } else {
                        mHandler.sendEmptyMessage(1);
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckOutBean> call, Throwable t) {
                mHandler.sendEmptyMessage(2);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_send_address:
                SelectAddressDialog dialog1 = new SelectAddressDialog(mContext, R.style.MyDialog, mTvCustomName, mTvCustomPhone, mTvCustomAddress);
                dialog1.show();
                break;
            case R.id.ll_check_method:
                new AlertDialog.Builder(mContext).setTitle("支付方式").setSingleChoiceItems(checkMethods, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvCheckMethod.setText(checkMethods[which]);
                    }
                }).setNegativeButton("取消", null).show();
                break;
            case R.id.ll_send_time:
                new AlertDialog.Builder(mContext).setTitle("送货时间").setSingleChoiceItems(sendTimes, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvSendTime.setText(sendTimes[which]);
                    }
                }).setNegativeButton("取消", null).show();
                break;
            case R.id.ll_write_bill:
                WriteBillDialog dialog = new WriteBillDialog(mContext, billTitle, billContent, mTvBillHead, billTitles, billContents);
                dialog.show();

                break;
        }
    }

    private void showData() {

        mTvBillHead.setText(billTitle + "/" + billContent);
        System.out.println(mTvCustomName);
        System.out.println(mCheckOutBean);
        System.out.println(mCheckOutBean.getAddressInfo());

        mTvCustomName.setText(mCheckOutBean.getAddressInfo().getName());
        mTvCustomPhone.setText(mCheckOutBean.getAddressInfo().getPhoneNumber());
        mTvCustomAddress.setText(mCheckOutBean.getAddressInfo().getAddressArea() + mCheckOutBean.getAddressInfo().getAddressDetail());
        mTvCount.setText(mCheckOutBean.getCheckoutAddup().getTotalCount() + "件");
        mTvSendPrice.setText("¥" + mCheckOutBean.getCheckoutAddup().getFreight());
        mTvFreePrice.setText(mCheckOutBean.getCheckoutAddup().getTotalPoint() + "分");
        mTvDeservePrice.setText("¥" + mCheckOutBean.getCheckoutAddup().getTotalPrice());

        int chargeCount = mCheckOutBean.getCheckoutProm().size();
        for (int i = 0; i < chargeCount; i++) {
            TextView tv = new TextView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mLl_prom_msg.getWidth(), mLl_prom_msg.getHeight());
            params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            params.leftMargin = DensityUtil.dip2px(mContext, 30);
            tv.setLayoutParams(params);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(18);
            tv.setText(mCheckOutBean.getCheckoutProm().get(i));
            mLl_prom_msg.addView(tv);
        }

        int productCount = mCheckOutBean.getProductList().size();
        for (int i = 0; i < productCount; i++) {
            View view = View.inflate(mContext, R.layout.product_list_item, null);
            CheckOutBean.ProductListBean bean = mCheckOutBean.getProductList().get(i);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_number = (TextView) view.findViewById(R.id.tv_number);
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
            ImageView iv_product = (ImageView) view.findViewById(R.id.iv_product);

            Glide.with(mContext).load(Urls.BASE_URL + bean.getProduct().getPic()).into(iv_product);
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

            mLlProductList.addView(view);
        }

    }

}
