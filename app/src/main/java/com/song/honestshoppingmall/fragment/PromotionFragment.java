package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.adapter.PrommotionAdapter;
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
    private PrommotionAdapter mAdapter;
    private View mView;
    private GridView gv_prommotion;


    @Override
    protected View initView() {
        ((HomeActivity) mContext).changeTitle("促销快报");
        mView = View.inflate(mContext, R.layout.fragment_prommotion, null);
        gv_prommotion = (GridView) mView.findViewById(R.id.gv_prommotion);
        mData = new ArrayList<>();
        return mView;
    }


    @Override
    protected void initData() {
        initNetData();

    }

    private void initNetData() {

        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("pageNum", "8");
        apiRetrofitInstance.getPrommotionBean(map).enqueue(new Callback<PrommotionBean>() {
            @Override
            public void onResponse(Call<PrommotionBean> call, Response<PrommotionBean> response) {
                if (response.isSuccessful()) {
                    PrommotionBean body = response.body();
                    if (body.error == null) {
                        List<PrommotionBean.TopicBean> productList = body.getTopic();
                        mData.clear();
                        mData.addAll(productList);
                        if (mAdapter == null) {
                            mAdapter = new PrommotionAdapter(mContext, mData);
                            gv_prommotion.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(mContext, body.error, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PrommotionBean> call, Throwable t) {
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
