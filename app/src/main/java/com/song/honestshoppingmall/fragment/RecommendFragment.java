package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.adapter.RecommendAdapter;
import com.song.honestshoppingmall.bean.RecommendBean;
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
 * Created by yspc on 2017/1/13.
 */

public class RecommendFragment extends BaseFragment {
    private RecommendAdapter mAdapter;
    private View mView;
    private ListView lv_recommend;
    private ArrayList<RecommendBean.BrandBean> mData;

    @Override
    protected View initView() {
        ((HomeActivity) mContext).changeTitle("推荐品牌");
        mView = View.inflate(mContext, R.layout.fragment_recommend, null);
        lv_recommend = (ListView) mView.findViewById(R.id.lv_recommend);
        this.mData = new ArrayList<>();
        return mView;
    }

    @Override
    protected void initData() {
        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        Map<String, String> map = new HashMap<>();
        apiRetrofitInstance.getRecommendBean(map).enqueue(new Callback<RecommendBean>() {
            @Override
            public void onResponse(Call<RecommendBean> call, Response<RecommendBean> response) {
                if (response.isSuccessful()) {
                    RecommendBean body = response.body();
                    if (body.error == null) {
                        List<RecommendBean.BrandBean> brand = body.getBrand();
                        mData.clear();
                        mData.addAll(brand);
                        if (mAdapter == null) {
                            mAdapter = new RecommendAdapter(mContext, mData);
                            lv_recommend.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(mContext, body.error, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RecommendBean> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity)mContext).changeTitle("推荐品牌");
    }
}
