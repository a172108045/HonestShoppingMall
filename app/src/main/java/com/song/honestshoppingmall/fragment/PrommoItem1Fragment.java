package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.adapter.PrommoItem1Adapter;
import com.song.honestshoppingmall.bean.PrommoItem1Bean;
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

public class PrommoItem1Fragment extends BaseFragment {
    private PrommoItem1Adapter mAdapter;
    private View mView;
    private ListView lv_prommoitem1;
    private ArrayList<PrommoItem1Bean.ProductListBean> mData;

    @Override
    protected View initView() {
        if (mView==null){
            mView = View.inflate(mContext, R.layout.fragment_prommoitem1, null);
            lv_prommoitem1 = (ListView) mView.findViewById(R.id.lv_prommoitem1);
            ((HomeActivity) mContext).changeTitle("促销快报");
            this.mData = new ArrayList<>();
        }
        return mView;
    }

    @Override
    protected void initData() {
        int id = getArguments().getInt("id");
        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        map.put("page", "0");
        map.put("orderby", "saleDown");
        map.put("pageNum", "10");
        apiRetrofitInstance.getPrommoItem1Bean(map).enqueue(new Callback<PrommoItem1Bean>() {
            @Override
            public void onResponse(Call<PrommoItem1Bean> call, Response<PrommoItem1Bean> response) {
                if (response.isSuccessful()) {
                    PrommoItem1Bean body = response.body();
                    if (body.error == null) {

                        List<PrommoItem1Bean.ProductListBean> productList = body.getProductList();
                        if (productList.size() > 1) {
                            mData.clear();
                            mData.addAll(productList);
                            if (mAdapter == null) {
                                mAdapter = new PrommoItem1Adapter(mContext, mData);
                                lv_prommoitem1.setAdapter(mAdapter);
                            } else {
                                mAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(mContext, "没有数据！", Toast.LENGTH_SHORT).show();
                            ((HomeActivity)mContext).popBackStack();
                        }

                    } else {
                        Toast.makeText(mContext, body.error, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PrommoItem1Bean> call, Throwable t) {
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
