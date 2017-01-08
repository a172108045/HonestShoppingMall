package com.song.honestshoppingmall.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.adapter.CardRecyclerAdapter;
import com.song.honestshoppingmall.bean.SerchCardBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Judy on 2017/1/8.
 */

public class ShopCartFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mImageView;
    private RecyclerView mRecyclerView;

    @Override
    protected View initView() {

        View view = View.inflate(mContext, R.layout.fragment_shopcart, null);
        Button btn_get_shopcart = (Button) view.findViewById(R.id.btn_get_shopcart);
        mImageView = (ImageView) view.findViewById(R.id.iv_getdatafailed);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        btn_get_shopcart.setOnClickListener(this);
        return view;
    }

    @Override
    protected void initData() {
        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
                    case R.id.btn_get_shopcart:
                        getShopCart();

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
                            SerchCardBean body = response.body();
                            Toast.makeText(mContext, body.toString(), Toast.LENGTH_SHORT).show();


                            System.out.println("name="+body.getCart().get(0).getProduct().getName());

                            //创建RecycleView,设置适配器
                            CardRecyclerAdapter cardRecyclerAdapter = new CardRecyclerAdapter(mContext,body);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                            mRecyclerView.setAdapter(cardRecyclerAdapter);

                        }
                    }

                    @Override
                    public void onFailure(Call<SerchCardBean> call, Throwable t) {
                        mImageView.setVisibility(View.VISIBLE);
                        Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();


                    }
                });

    }

}
