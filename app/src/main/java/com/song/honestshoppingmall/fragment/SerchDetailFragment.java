package com.song.honestshoppingmall.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
public class SerchDetailFragment extends BaseFragment implements View.OnClickListener {

    private String       mSearch_value;
    private RecyclerView mRecycle_searchdetail;
    private List<SearchDetailBean.ProductListBean> mData = new ArrayList<>();
    private SearchDetailAdapter mSearchDetailAdapter;
    private Spinner             mSpinner;
    private TextView            mBtn_sales_evaluate;
    private TextView            mBtn_sales_price;
    private TextView            mBtn_sales_time;
    private TextView            mBtn_sales_volume;
    private View                mView;
    private int type_price = 0;
    private ImageView mIv_price;

    @Override
    protected View initView() {
        if (mView == null) {
            mView = View.inflate(mContext, R.layout.fragment_serch_detail, null);
            Bundle arguments = getArguments();
            Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
            btn_cancel.setOnClickListener(this);
            mSpinner = (Spinner) mView.findViewById(R.id.spinner2);

            RelativeLayout relative_price = (RelativeLayout) mView.findViewById(R.id.relative_price);
            mIv_price = (ImageView) mView.findViewById(R.id.iv_price);

            relative_price.setOnClickListener(this);

            mBtn_sales_evaluate = (TextView) mView.findViewById(R.id.btn_sales_evaluate);
            mBtn_sales_price = (TextView) mView.findViewById(R.id.btn_sales_price);
            mBtn_sales_time = (TextView) mView.findViewById(R.id.btn_sales_time);
            mBtn_sales_volume = (TextView) mView.findViewById(R.id.btn_sales_volume);

            Button btn_sales_filtrate = (Button) mView.findViewById(R.id.btn_sales_filtrate);
            //        mSlideMenu = (MySlideMenu) view.findViewById(R.id.id_menu);


            mBtn_sales_evaluate.setOnClickListener(this);
            mBtn_sales_time.setOnClickListener(this);
            mBtn_sales_volume.setOnClickListener(this);
            btn_sales_filtrate.setOnClickListener(this);

            //获取上一个Fragment传递过来的String参数
            mSearch_value = arguments.getString(Constants.SEARCH_KEY);

            mRecycle_searchdetail = (RecyclerView) mView.findViewById(R.id.recycle_searchdetail);
        }


        return mView;
    }

    @Override
    protected void initData() {
        initSpinner();
        int spacingInPixels = DensityUtil.dip2px(mContext, 10);
        mSearchDetailAdapter = new SearchDetailAdapter(mContext, mData);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false);
        mRecycle_searchdetail.setLayoutManager(gridLayoutManager);
        mRecycle_searchdetail.setHasFixedSize(true);
        mRecycle_searchdetail.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        mRecycle_searchdetail.setAdapter(mSearchDetailAdapter);
        mSearchDetailAdapter.setOnItemClickListener(new SearchDetailAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                //TODO 进入商品详情页面
            }
        });

        initNetData("saleDown");
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void initNetData(String orderBy) {

        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        Map<String, String> map = new HashMap<>();
        map.put("page", "0");
        map.put("pageNum", "10");
        map.put("orderby", orderBy);
        map.put("keyword", mSearch_value);
        apiRetrofitInstance.getSearchDetail(map).enqueue(new Callback<SearchDetailBean>() {

            @Override
            public void onResponse(Call<SearchDetailBean> call, Response<SearchDetailBean> response) {
                if (response.isSuccessful()) {
                    SearchDetailBean body = response.body();
                    if (body.error == null) {//请求成功
                        List<SearchDetailBean.ProductListBean> productList = body.getProductList();
                        mData.clear();
                        mData.addAll(productList);

                        mSearchDetailAdapter.notifyDataSetChanged();

                    } else {
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
            case R.id.btn_sales_evaluate:  //评价

                break;
            case R.id.btn_sales_filtrate:  //筛选
                //弹出侧滑菜单
                //               // startActivity(new Intent(mContext, TestActivity.class));
                //                mSlideMenu.toggle();

                break;

            case R.id.btn_sales_time:       //时间
                break;
            case R.id.btn_sales_volume:   //销量


                break;
            case R.id.relative_price:

                switch (type_price) {
                    case 0:
                        mBtn_sales_price.setTextColor(this.getResources().getColor(R.color.black));
                       mIv_price.setImageResource(R.mipmap.up_down_pink_new);
                        type_price = 1;
                        break;
                    case 1:
                        mBtn_sales_price.setTextColor(this.getResources().getColor(R.color.pink));
                        mIv_price.setImageResource(R.mipmap.up_pink_new);
                        type_price = 2;

                        break;
                    case 2:
                        mBtn_sales_price.setTextColor(this.getResources().getColor(R.color.pink));
                        mIv_price.setImageResource(R.mipmap.down_pink_new);
                        type_price = 0;

                        break;

                }
                break;
            default:
                break;

        }
    }
}
