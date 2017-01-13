package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.adapter.DesenoAdapter;
import com.song.honestshoppingmall.bean.DesenoBean;
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
 * Created by yspc on 2017/1/11.
 */

public class DesenoFragment extends BaseFragment {

    private View mView;
    private List<DesenoBean.ProductListBean> mData ;
    private DesenoAdapter mAdapter;
    private ListView lv_deseno;
    private LinearLayout fragment_deseno_break;


    @Override
    protected View initView() {
        ((HomeActivity)mContext).changeTitle("新品上架");
        this.mData = new ArrayList<>();
        mView = View.inflate(mContext, R.layout.fragment_deseno, null);
        lv_deseno = (ListView) mView.findViewById(R.id.lv_deseno);
        return mView;
    }

    @Override
    protected void initData() {

        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        final Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("pageNum", "10");
        map.put("orderby", "saleDown");
        apiRetrofitInstance.getDesenoBean(map).enqueue(new Callback<DesenoBean>() {
            @Override
            public void onResponse(Call<DesenoBean> call, Response<DesenoBean> response) {
                if (response.isSuccessful()) {
                    DesenoBean body = response.body();
                    if (body.error == null) {
                        List<DesenoBean.ProductListBean> productList = body.getProductList();
                        mData.clear();
                        mData.addAll(productList);

                        if (mAdapter == null) {
                            mAdapter = new DesenoAdapter(mContext, mData);
                            lv_deseno.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }

                    } else {
                        Toast.makeText(mContext, body.error, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DesenoBean> call, Throwable t) {
                Toast.makeText(mContext,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
