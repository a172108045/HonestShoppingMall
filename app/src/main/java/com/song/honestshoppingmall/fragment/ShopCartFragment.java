package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.Button;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.RetrofitUtil;

import retrofit2.Retrofit;

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
        Retrofit retrofit = RetrofitUtil.getRetrofitInstance();

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

    }
}
