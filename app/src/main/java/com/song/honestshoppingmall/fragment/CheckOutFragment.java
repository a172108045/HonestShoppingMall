package com.song.honestshoppingmall.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.util.Constants;
import com.song.honestshoppingmall.util.SpUtil;

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
    private TextView mTvShuliangzongji;
    private TextView mTvYunfei;
    private TextView mTvCuxiaoyouhui;
    private TextView mTvYingzhifujine;
    private TextView mTvCount;
    private TextView mTvSendPrice;
    private TextView mTvFreePrice;
    private TextView mTvDeservePrice;
    private TextView mTvChargeMsg;
    private LinearLayout mLl_send_address;
    private LinearLayout mLl_check_method;
    private LinearLayout mLl_send_time;
    private LinearLayout mLl_write_bill;

    private final String[] checkMethods = {"现金-到付", "到付-POS机", "支付宝"};
    private final String[] sendTimes = {"周一至周五送货", "双休日及公众假期送货", "时间不限，工作日双休日及公众假期均可送货"};

    private String sku;

    @Override
    protected View initView() {
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
        mTvShuliangzongji = (TextView) view.findViewById(R.id.tv_shuliangzongji);
        mTvYunfei = (TextView) view.findViewById(R.id.tv_yunfei);
        mTvCuxiaoyouhui = (TextView) view.findViewById(R.id.tv_cuxiaoyouhui);
        mTvYingzhifujine = (TextView) view.findViewById(R.id.tv_yingzhifujine);
        mTvCount = (TextView) view.findViewById(R.id.tv_count);
        mTvSendPrice = (TextView) view.findViewById(R.id.tv_send_price);
        mTvFreePrice = (TextView) view.findViewById(R.id.tv_free_price);
        mTvDeservePrice = (TextView) view.findViewById(R.id.tv_deserve_price);
        mTvChargeMsg = (TextView) view.findViewById(R.id.tv_charge_msg);
        mLl_send_address = (LinearLayout) view.findViewById(R.id.ll_send_address);
        mLl_check_method = (LinearLayout) view.findViewById(R.id.ll_check_method);
        mLl_send_time = (LinearLayout) view.findViewById(R.id.ll_send_time);
        mLl_write_bill = (LinearLayout) view.findViewById(R.id.ll_write_bill);
    }

    @Override
    protected void initData() {
        mLl_send_address.setOnClickListener(this);
        mLl_check_method.setOnClickListener(this);
        mLl_send_time.setOnClickListener(this);
        mLl_write_bill.setOnClickListener(this);

        initNetData();
    }

    private void initNetData() {
        String userid = SpUtil.getString(mContext, Constants.USERID, "");
        //RetrofitUtil.getAPIRetrofitInstance().getCheckOutBean()


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_send_address:

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
                        mTvCheckMethod.setText(sendTimes[which]);
                    }
                }).setNegativeButton("取消", null).show();
                break;
            case R.id.ll_write_bill:

                break;
        }
    }
}
