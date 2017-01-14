package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.adapter.NewonAdapter;
import com.song.honestshoppingmall.bean.NewonBean;
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
 * Created by yspc on 2017/1/12.
 */

public class NewonFragment extends BaseFragment {
    private ArrayList<NewonBean.ProductListBean> mData;
    private NewonAdapter mAdapter;
    private View mView;
    private ListView lv_newon;

    @Override
    protected View initView() {
        if (mView==null){
            mView = View.inflate(mContext, R.layout.fragment_newon, null);
            lv_newon = (ListView) mView.findViewById(R.id.lv_newon);
            ((HomeActivity) mContext).changeTitle("热门单品");
            this.mData = new ArrayList<>();
        }
        return mView;
    }


    @Override
    protected void initData() {
        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("pageNum", "10");
        map.put("orderby", "saleDown");
       apiRetrofitInstance.getNewonBean(map).enqueue(new Callback<NewonBean>() {
           @Override
           public void onResponse(Call<NewonBean> call, Response<NewonBean> response) {
               if (response.isSuccessful()) {
                   NewonBean body = response.body();
                   if (body.error == null) {
                       List<NewonBean.ProductListBean> productList = body.getProductList();
                       mData.clear();
                       mData.addAll(productList);
                       if (mAdapter == null) {
                           mAdapter = new NewonAdapter(mContext, mData);
                           lv_newon.setAdapter(mAdapter);
                       } else {
                           mAdapter.notifyDataSetChanged();
                       }
                   } else {
                       Toast.makeText(mContext, body.error, Toast.LENGTH_SHORT).show();
                   }
               }
           }

           @Override
           public void onFailure(Call<NewonBean> call, Throwable t) {
               Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
           }
       });
    }


}
