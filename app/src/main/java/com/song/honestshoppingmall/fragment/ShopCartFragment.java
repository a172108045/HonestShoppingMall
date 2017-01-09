package com.song.honestshoppingmall.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.adapter.CardRecyclerAdapter;
import com.song.honestshoppingmall.bean.SerchCardBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.DialogAlertUtils;
import com.song.honestshoppingmall.util.RetrofitUtil;

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
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mCardRecyclerAdapter.notifyDataSetChanged();
        }
    };
    public CardRecyclerAdapter mCardRecyclerAdapter;

    @Override
    protected View initView() {

        View view = View.inflate(mContext, R.layout.fragment_shopcart, null);
        mImageView = (ImageView) view.findViewById(R.id.iv_getdatafailed);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        Button btn_alert_dialog = (Button) view.findViewById(R.id.btn_alert_dialog);
        mRelative_pay = (RelativeLayout) view.findViewById(R.id.relative_pay);
        mCb_card_checkall = (CheckBox) view.findViewById(R.id.cb_card_checkall);
        mTv_price_card = (TextView) view.findViewById(R.id.tv_price_card);
        if (mCb_card_checkall!=null){
            mCb_card_checkall.setOnCheckedChangeListener(this);
        }
        Button btn_gotopay = (Button) view.findViewById(R.id.btn_gotopay);
        btn_gotopay.setOnClickListener(this);
        btn_alert_dialog.setOnClickListener(this);
        return view;
    }

    @Override
    protected void initData() {
        getShopCart();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_gotopay:
                Toast.makeText(mContext, "点击进入结算页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_alert_dialog:
                DialogAlertUtils.showScanNumberDialog(mContext);

                break;

            default:
                break;

        }
    }

    private void getShopCart() {
        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        apiRetrofitInstance.getSerchCartBean("20428")
                .enqueue(new Callback<SerchCardBean>() {
                    @Override
                    public void onResponse(Call<SerchCardBean> call, Response<SerchCardBean> response) {
                        if (response.isSuccessful()) {
                            mImageView.setVisibility(View.GONE);
                            mRelative_pay.setVisibility(View.VISIBLE);
                            SerchCardBean body = response.body();
                            //Toast.makeText(mContext, body.toString(), Toast.LENGTH_SHORT).show();


                            System.out.println("name=" + body.getCart().get(0).getProduct().getName());

                            //创建RecycleView,设置适配器
                            mCardRecyclerAdapter = new CardRecyclerAdapter(mContext, body, mTv_price_card, mCb_card_checkall);


                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                            mRecyclerView.setHasFixedSize(true);
                            mRecyclerView.setAdapter(mCardRecyclerAdapter);
                            mCardRecyclerAdapter.setOnItemClickListener(new CardRecyclerAdapter.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, String data) {
                                    Toast.makeText(mContext, "点击跳转到商品详情页面", Toast.LENGTH_SHORT).show();
                                }
                            });


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
        mHandler.sendEmptyMessage(0);
    }
}

