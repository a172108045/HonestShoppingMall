package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.adapter.PrommoItem2Adapter;
import com.song.honestshoppingmall.bean.PrommoItem2Bean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yspc on 2017/1/14.
 */

public class PrommoItem2Fragment extends BaseFragment {
    private PrommoItem2Adapter mAdapter;
    private View mView;
    private ListView lv_prommoitem2;
    private ArrayList<PrommoItem2Bean.ProductListBean> mData;

    @Override
    protected View initView() {
        if (mView==null){
            mView = View.inflate(mContext, R.layout.fragment_prommoitem2, null);
            lv_prommoitem2 = (ListView) mView.findViewById(R.id.lv_prommoitem2);
            ((HomeActivity) mContext).changeTitle("促销快报");
            this.mData = new ArrayList<>();
        }
        return mView;
    }

    @Override
    protected void initData() {
        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        Map<String, String> map = new HashMap<>();
        map.put("id", "3");
        map.put("page", "0");
        map.put("orderby", "saleDown");
        map.put("pageNum", "10");
        apiRetrofitInstance.getPrommoItem2Bean(map).enqueue(new Callback<PrommoItem2Bean>() {
            @Override
            public void onResponse(Call<PrommoItem2Bean> call, Response<PrommoItem2Bean> response) {
                if (response.isSuccessful()) {
                    PrommoItem2Bean body = response.body();
                    if (body.error == null) {
                        List<PrommoItem2Bean.ProductListBean> productList = body.getProductList();
                        mData.clear();
                        mData.addAll(productList);
                        if (mAdapter == null) {
                            mAdapter = new PrommoItem2Adapter(mContext, mData);
                            lv_prommoitem2.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(mContext, body.error, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PrommoItem2Bean> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity)mContext).changeTitle("促销快报");
    }
}
