package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.AddCartBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Judy on 2017/1/8.
 */

public class ShopCartFragment extends BaseFragment implements View.OnClickListener {
    @Override
    protected View initView() {

        View view = View.inflate(mContext, R.layout.fragment_shopcart, null);
        Button btn_get_shopcart = (Button) view.findViewById(R.id.btn_get_shopcart);
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
        Map<String,String> map = new HashMap<>();
        map.put("userId","20428");
        map.put("productId","2");
        map.put("productCount","2");
        map.put("propertyId","1");

        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        apiRetrofitInstance.getAddCartBean(map)
                .enqueue(new Callback<AddCartBean>() {
                    @Override
                    public void onResponse(Call<AddCartBean> call, Response<AddCartBean> response) {
                        if (response.isSuccessful()) {
                            AddCartBean body = response.body();
                            Toast.makeText(mContext, body.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddCartBean> call, Throwable t) {
                        Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
