package com.song.honestshoppingmall.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.adapter.SearchDetailAdapter;
import com.song.honestshoppingmall.bean.SearchDetailBean;
import com.song.honestshoppingmall.decoration.SpacesItemDecoration;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.Constants;
import com.song.honestshoppingmall.util.DensityUtil;
import com.song.honestshoppingmall.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lizhenquan on 2017/1/10.
 */
public class SerchDetailFragment extends  BaseFragment implements View.OnClickListener {

    private String mSearch_value;
    private RecyclerView mRecycle_searchdetail;
    private List<SearchDetailBean.ProductListBean>  mData = new ArrayList<>();
    private SearchDetailAdapter mSearchDetailAdapter;
    private Spinner mSpinner;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_serch_detail, null);
        Bundle arguments = getArguments();
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        mSpinner = (Spinner) view.findViewById(R.id.spinner2);
        //获取上一个Fragment传递过来的String参数
        mSearch_value = arguments.getString(Constants.SEARCH_KEY);

        mRecycle_searchdetail = (RecyclerView) view.findViewById(R.id.recycle_searchdetail);

        return view;
    }

    @Override
    protected void initData() {
        initSpinner();
        int spacingInPixels = DensityUtil.dip2px(mContext, 10);
        mSearchDetailAdapter = new SearchDetailAdapter(mContext,mData);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL,false);
        mRecycle_searchdetail.setLayoutManager(gridLayoutManager);
        mRecycle_searchdetail.setHasFixedSize(true);
        mRecycle_searchdetail.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        mRecycle_searchdetail.setAdapter(mSearchDetailAdapter);
     /*   mSearchDetailAdapter.setOnItemClickListener(new CardRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Toast.makeText(mContext, "点击跳转到商品详情页面", Toast.LENGTH_SHORT).show();
            }
        });*/

            initNetData();
    }

    private void initSpinner() {

        //显示的数组
        final String arr[] = new String[]{
                "宝贝",
                "店铺",
                "活动"

        };
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, arr);
        mSpinner.setAdapter(arrayAdapter);
        //注册事件
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner spinner = (Spinner) parent;
                //  Toast.makeText(mContext.getApplicationContext(), "xxxx" + spinner.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //  Toast.makeText(mContext.getApplicationContext(), "没有改变的处理", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void initNetData() {

        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        Map<String,String> map = new HashMap<>();
        map.put("page","0");
        map.put("pageNum","10");
        map.put("orderby","saleDown");
        map.put("keyword",mSearch_value);
        apiRetrofitInstance.getSearchDetail(map).enqueue(new Callback<SearchDetailBean>() {

            @Override
            public void onResponse(Call<SearchDetailBean> call, Response<SearchDetailBean> response) {
                if (response.isSuccessful()) {
                    SearchDetailBean body = response.body();
                    if (body.error==null){//请求成功
                        List<SearchDetailBean.ProductListBean> productList = body.getProductList();
                        mData.clear();
                        mData.addAll(productList);

                        mSearchDetailAdapter.notifyDataSetChanged();

                    }else{
                        Toast.makeText(mContext, body.error, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchDetailBean> call, Throwable t) {
                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                ((HomeActivity) mContext).onBackPressed();
                break;

                    default:
                        break;

                }
    }
}
