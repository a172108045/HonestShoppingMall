package com.song.honestshoppingmall.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    private ImageView mIv_salenum;
    private ImageView mIv_time;
    private ImageView mIv_evaluate;
    private ImageView mIv_error;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected View initView() {
        ((HomeActivity) mContext).changeTitle("搜索详情");
        if (mView == null) {
            mView = View.inflate(mContext, R.layout.fragment_serch_detail, null);

            Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
            mSpinner = (Spinner) mView.findViewById(R.id.spinner2);
            mIv_error = (ImageView) mView.findViewById(R.id.iv_error);
            mRecycle_searchdetail = (RecyclerView) mView.findViewById(R.id.recycle_searchdetail);
            mRecycle_searchdetail.setVisibility(View.VISIBLE);
            RelativeLayout relative_sale = (RelativeLayout) mView.findViewById(R.id.relative_sale);
            RelativeLayout relative_price = (RelativeLayout) mView.findViewById(R.id.relative_price);
            RelativeLayout relative_time = (RelativeLayout) mView.findViewById(R.id.relative_time);
            RelativeLayout relative_evaluate = (RelativeLayout) mView.findViewById(R.id.relative_evaluate);
            mIv_price = (ImageView) mView.findViewById(R.id.iv_price);
            mIv_salenum = (ImageView) mView.findViewById(R.id.iv_salenum);
            mIv_time = (ImageView) mView.findViewById(R.id.iv_time);
            mIv_evaluate = (ImageView) mView.findViewById(R.id.iv_evaluate);


            mBtn_sales_evaluate = (TextView) mView.findViewById(R.id.btn_sales_evaluate);
            mBtn_sales_price = (TextView) mView.findViewById(R.id.btn_sales_price);
            mBtn_sales_time = (TextView) mView.findViewById(R.id.btn_sales_time);
            mBtn_sales_volume = (TextView) mView.findViewById(R.id.btn_sales_volume);

            Button btn_sales_filtrate = (Button) mView.findViewById(R.id.btn_sales_filtrate);
            //        mSlideMenu = (MySlideMenu) view.findViewById(R.id.id_menu);
            relative_evaluate.setOnClickListener(this);
            relative_time.setOnClickListener(this);
            relative_price.setOnClickListener(this);
            relative_sale.setOnClickListener(this);
            btn_cancel.setOnClickListener(this);
            btn_sales_filtrate.setOnClickListener(this);

            //获取上一个Fragment传递过来的String参数
            Bundle arguments = getArguments();
            mSearch_value = arguments.getString(Constants.SEARCH_KEY);


        }


        return mView;
    }

    @Override
    protected void initData() {
        initSpinner();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSearchDetailAdapter != null) {
                    mSearchDetailAdapter.setOnItemClickListener(new SearchDetailAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, String data) {
                            Toast.makeText(mContext, "进入商品详情页面", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        },500);



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
                        if (mSearchDetailAdapter == null) {
                            mSearchDetailAdapter = new SearchDetailAdapter(mContext, mData);
                            int spacingInPixels = DensityUtil.dip2px(mContext, 10);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false);
                            mRecycle_searchdetail.setLayoutManager(gridLayoutManager);
                            mRecycle_searchdetail.setHasFixedSize(true);
                            mRecycle_searchdetail.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
                            mRecycle_searchdetail.setAdapter(mSearchDetailAdapter);
                        } else {
                            mSearchDetailAdapter.notifyDataSetChanged();
                        }


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
        mRecycle_searchdetail.setVisibility(View.VISIBLE);
        mIv_error.setVisibility(View.GONE);
        mBtn_sales_evaluate.setTextColor(getResources().getColor(R.color.black));
        mBtn_sales_volume.setTextColor(getResources().getColor(R.color.black));
        mBtn_sales_time.setTextColor(getResources().getColor(R.color.black));
        mBtn_sales_price.setTextColor(getResources().getColor(R.color.black));
        mIv_evaluate.setImageResource(R.mipmap.down_new);
        mIv_salenum.setImageResource(R.mipmap.down_new);
        mIv_time.setImageResource(R.mipmap.down_new);
        mIv_price.setImageResource(R.mipmap.up_down_pink_new);
        switch (view.getId()) {

            case R.id.btn_cancel:
                ((HomeActivity) mContext).onBackPressed();
                break;
            case R.id.relative_evaluate:  //评价
                mBtn_sales_evaluate.setTextColor(getResources().getColor(R.color.pink));
                mIv_evaluate.setImageResource(R.mipmap.down_selected_new);
               // initNetData("commentDown");
                mRecycle_searchdetail.setVisibility(View.GONE);
                mIv_error.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_sales_filtrate:  //筛选
                //弹出侧滑菜单
                //               // startActivity(new Intent(mContext, TestActivity.class));
                //                mSlideMenu.toggle();

                break;

            case R.id.relative_time:       //时间
                mBtn_sales_time.setTextColor(this.getResources().getColor(R.color.pink));
                mIv_time.setImageResource(R.mipmap.down_selected_new);
                initNetData("shelvesDown");
                break;
            case R.id.relative_sale:   //销量
                mBtn_sales_volume.setTextColor(this.getResources().getColor(R.color.pink));
                mIv_salenum.setImageResource(R.mipmap.down_selected_new);
                initNetData("saleDown");
                break;
            case R.id.relative_price:

                switch (type_price) {
                    case 0:
                        mBtn_sales_price.setTextColor(this.getResources().getColor(R.color.black));
                        mIv_price.setImageResource(R.mipmap.up_down_pink_new);
                        type_price = 1;   //价格升序
                        initNetData("priceUp");

                        break;
                    case 1:
                        mBtn_sales_price.setTextColor(this.getResources().getColor(R.color.pink));
                        mIv_price.setImageResource(R.mipmap.up_pink_new);
                        type_price = 2;
                        initNetData("priceDown");
                        break;
                    case 2:
                        mBtn_sales_price.setTextColor(this.getResources().getColor(R.color.pink));
                        mIv_price.setImageResource(R.mipmap.down_pink_new);
                        type_price = 1;
                        initNetData("priceUp");
                        break;

                }
                break;
            default:
                break;

        }
    }
}
