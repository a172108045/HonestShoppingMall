package com.song.honestshoppingmall.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.adapter.CardRecyclerAdapter;
import com.song.honestshoppingmall.bean.SerchCardBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.Constants;
import com.song.honestshoppingmall.util.DialogAlertUtils;
import com.song.honestshoppingmall.util.RetrofitUtil;
import com.song.honestshoppingmall.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Judy on 2017/1/8.
 */

public class ShopCartFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ImageView      mImageView;
    private RecyclerView   mRecyclerView;
    private RelativeLayout mRelative_pay;
    private TextView       mTv_price_card;
    private CheckBox       mCb_card_checkall;
    private List<SerchCardBean.CartBean> mData = new ArrayList<>();

    public CardRecyclerAdapter mCardRecyclerAdapter;
    private View mView;

    @Override
    protected View initView() {
        if (mView == null){
            mView = View.inflate(mContext, R.layout.fragment_shopcart, null);
            mImageView = (ImageView) mView.findViewById(R.id.iv_getdatafailed);
            Button btn_select = (Button) mView.findViewById(R.id.btn_select);

            btn_select.setOnClickListener(this);
            mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
            Button btn_alert_dialog = (Button) mView.findViewById(R.id.btn_alert_dialog);
            mRelative_pay = (RelativeLayout) mView.findViewById(R.id.relative_pay);
            mCb_card_checkall = (CheckBox) mView.findViewById(R.id.cb_card_checkall);
            mTv_price_card = (TextView) mView.findViewById(R.id.tv_totalPrice);
            if (mCb_card_checkall != null) {
                mCb_card_checkall.setOnCheckedChangeListener(this);
            }
            Button btn_gotopay = (Button) mView.findViewById(R.id.btn_gotopay);
            btn_gotopay.setOnClickListener(this);
            btn_alert_dialog.setOnClickListener(this);
        }


        return mView;
    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(SpUtil.getString(mContext,Constants.USERID,null))){
            getShopCart();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setHasFixedSize(true);
        }else{
            mImageView.setVisibility(View.VISIBLE);
            mRelative_pay.setVisibility(View.GONE);
        }


    }


    public void refreshData() {
        if (mCardRecyclerAdapter != null) {
            getShopCart();
            mCardRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_gotopay:
                Toast.makeText(mContext, "点击进入结算页面", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mData.size(); i++) {
                    if (mCardRecyclerAdapter.checkedData[i]) {
                        int id = mData.get(i).getProductId();
                        int count = mData.get(i).getProductCount();
                        int ppid = mData.get(i).getProperty().getId();
                        if (i != mData.size() - 1) {
                            sb.append(id + ":" + count + ":" + ppid + "|");
                        } else {
                            sb.append(id + ":" + count + ":" + ppid);
                        }
                    }
                }
                bundle.putString("sku", sb.toString());
                ((HomeActivity) mContext).changeFragment(new CheckOutFragment(),"CheckOutFragment",bundle);
                break;
            case R.id.btn_alert_dialog:
                DialogAlertUtils.showScanNumberDialog(mContext);
                getShopCart();
                break;
            case R.id.btn_select:
                ((HomeActivity) mContext).changeFragment(new SerchFragment(), "SerchFragment");
                break;
            default:
                break;

        }
    }

    private void getShopCart() {
        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        apiRetrofitInstance.getSerchCartBean(SpUtil.getString(mContext, Constants.USERID, ""))
                .enqueue(new Callback<SerchCardBean>() {
                    @Override
                    public void onResponse(Call<SerchCardBean> call, Response<SerchCardBean> response) {
                        if (response.isSuccessful()) {
                            mImageView.setVisibility(View.GONE);
                            mRelative_pay.setVisibility(View.VISIBLE);
                            SerchCardBean body = response.body();


                            mData.clear();
                            mData.addAll(body.getCart());
                            if (mCardRecyclerAdapter == null) {
                                mCardRecyclerAdapter = new CardRecyclerAdapter(mContext, mData, mTv_price_card, mCb_card_checkall);
                                mRecyclerView.setAdapter(mCardRecyclerAdapter);
                                mCardRecyclerAdapter.setOnItemClickListener(new CardRecyclerAdapter.OnRecyclerViewItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, String data) {
                                        Toast.makeText(mContext, "点击跳转到商品详情页面", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                mCardRecyclerAdapter.notifyDataSetChanged();

                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<SerchCardBean> call, Throwable t) {
                        mImageView.setVisibility(View.VISIBLE);
                        mRelative_pay.setVisibility(View.GONE);
                        Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mCardRecyclerAdapter.notifyDataSetChanged();
    }

}

