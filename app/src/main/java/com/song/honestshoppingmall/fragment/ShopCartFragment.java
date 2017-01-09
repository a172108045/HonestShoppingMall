package com.song.honestshoppingmall.fragment;

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

    private ImageView    mImageView;
    private RecyclerView mRecyclerView;
    private RelativeLayout mRelative_pay;
    private TextView mTv_price_card;

    @Override
    protected View initView() {

        View view = View.inflate(mContext, R.layout.fragment_shopcart, null);
        Button btn_get_shopcart = (Button) view.findViewById(R.id.btn_get_shopcart);
        mImageView = (ImageView) view.findViewById(R.id.iv_getdatafailed);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        Button btn_alert_dialog = (Button) view.findViewById(R.id.btn_alert_dialog);
        mRelative_pay = (RelativeLayout) view.findViewById(R.id.relative_pay);
        CheckBox cb_card_checkall = (CheckBox) view.findViewById(R.id.cb_card_checkall);
        mTv_price_card = (TextView) view.findViewById(R.id.tv_price_card);
        if (cb_card_checkall!=null){
            cb_card_checkall.setOnCheckedChangeListener(this);
        }


        btn_get_shopcart.setOnClickListener(this);

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
            case R.id.btn_get_shopcart:
                getShopCart();

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
                            Toast.makeText(mContext, body.toString(), Toast.LENGTH_SHORT).show();


                            System.out.println("name=" + body.getCart().get(0).getProduct().getName());

                            //创建RecycleView,设置适配器
                            CardRecyclerAdapter cardRecyclerAdapter = new CardRecyclerAdapter(mContext, body,mTv_price_card);


                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

                            mRecyclerView.setAdapter(cardRecyclerAdapter);



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
        if (b){
            for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
                View childAt = mRecyclerView.getLayoutManager().getChildAt(i);
                CheckBox checkBox = (CheckBox) childAt.findViewById(R.id.cb_card);
                checkBox.setChecked(true);
            }
        }else{
            for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
                View childAt = mRecyclerView.getLayoutManager().getChildAt(i);
                CheckBox checkBox = (CheckBox) childAt.findViewById(R.id.cb_card);
                checkBox.setChecked(false);
            }
        }

    }
}
