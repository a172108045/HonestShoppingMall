package com.song.honestshoppingmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.PrommotionBean;
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

public class PromotionFragment extends BaseFragment {

    private List<PrommotionBean.TopicBean> mData ;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        this.mData = new ArrayList<>();

    }

    @Override
    protected View initView() {

        View view = View.inflate(mContext, R.layout.fragment_prommotion, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_prommotion);

        return view;
    }


    @Override
    protected void initData() {
        initNetData();

    }

    private void initNetData() {
        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        final Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("pageNum", "8");
        apiRetrofitInstance.getPrommotionBean(map).enqueue(new Callback<PrommotionBean>() {
            @Override
            public void onResponse(Call<PrommotionBean> call, Response<PrommotionBean> response) {

            }

            @Override
            public void onFailure(Call<PrommotionBean> call, Throwable t) {

            }
        });
    }

}
