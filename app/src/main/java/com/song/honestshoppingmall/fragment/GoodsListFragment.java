package com.song.honestshoppingmall.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.adapter.ProductListRecyclerAdapter;
import com.song.honestshoppingmall.bean.FilterProductListBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Creator Administrator
 * @CreatedDate 2017/1/12 10:47
 * @Description ${TODO}
 * @Updater $Author$
 * @UpdateTime $Date$
 * @UpdateDesc ${TODO}
 */

public class GoodsListFragment extends BaseFragment implements View.OnClickListener {

    private View mRootView;
    private Button mBtn_fragment_goodslist_detail;
    private int mCategoryId;
    private RecyclerView mRv_fragment_goodslist_product;

    @Override
    protected View initView() {
        ((HomeActivity)mContext).changeTitle("商品列表");
        if(mRootView == null){
            mRootView = View.inflate(mContext, R.layout.fragment_goodslist, null);

            //初始化获取需要动态设置的子控件view
            mBtn_fragment_goodslist_detail = (Button) mRootView.findViewById(R.id.btn_fragment_goodslist_detail);
            mRv_fragment_goodslist_product = (RecyclerView) mRootView.findViewById(R.id.rv_fragment_goodslist_product);

            //添加按钮事件监听
            addBtnOnClickListener();

        }
        return mRootView;
    }

    @Override
    protected void initData() {
        //从商品分类传递过来的分类id，访问网络接口时需要该cId
        mCategoryId = (int) this.getArguments().get("cId");
        requestNetData(1, 10, mCategoryId, "saleDown", "t1-s1-p8");
    }

    private void addBtnOnClickListener(){
        mBtn_fragment_goodslist_detail.setOnClickListener(this);
    }

    /**
     *
     * @param page 第几页
     * @param pageNum 每页个数
     * @param cId 分类ID
     * @param orderBy 排序
     * @param filter 筛选
     */
    private void requestNetData(int page, int pageNum, int cId, String orderBy, String filter) {
        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        final Map<String, String> requestMap = new HashMap<>();
        requestMap.put("page", page + "");
        requestMap.put("pageNum", pageNum + "");
        requestMap.put("cId", cId + "");
        requestMap.put("orderBy", orderBy);
        requestMap.put("filter", filter);

        apiRetrofitInstance.getFilterProductList(requestMap)
                .enqueue(new Callback<FilterProductListBean>() {
                    @Override
                    public void onResponse(Call<FilterProductListBean> call, Response<FilterProductListBean> response) {
                        FilterProductListBean filterProductListBean = response.body();
                        if(response.isSuccessful()){
                            Toast.makeText(mContext, "GoodsListFragment " + requestMap.toString() + " 请求成功:" + filterProductListBean.toString() , Toast.LENGTH_SHORT).show();
                            Log.d("GoodsListFragment " + requestMap.toString() + " 请求成功:", filterProductListBean.toString());
                            refreshProductListByBean(filterProductListBean);
                        }else {
                            Toast.makeText(mContext, "GoodsListFragment " + requestMap.toString() + " 请求错误码:" + filterProductListBean.error_code , Toast.LENGTH_SHORT).show();
                            Log.d("GoodsListFragment " + " 请求错误码:" + requestMap.toString(), filterProductListBean.error_code);
                        }
                    }

                    @Override
                    public void onFailure(Call<FilterProductListBean> call, Throwable t) {
                        Toast.makeText(mContext, "GoodsListFragment " + "请求失败： " + requestMap.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //通过网络请求返回的bean数据刷新商品列表recyclerview
    private void refreshProductListByBean(FilterProductListBean filterProductListBean){

//        mRv_fragment_goodslist_product.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,true));
//        mRv_fragment_goodslist_product.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.HORIZONTAL,false));
        mRv_fragment_goodslist_product.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        ProductListRecyclerAdapter recyclerAdapter = new ProductListRecyclerAdapter(mContext, filterProductListBean.getProductList());
        mRv_fragment_goodslist_product.setAdapter(recyclerAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_fragment_goodslist_detail:

                Bundle bundle = new Bundle();
                //第二个参数为当前选中的商品pId,进入商品详情需要此参数，目前测试为1，功能完善后通过new FilterProductListBean().getProductList().get(0).getId()传入
                bundle.putInt("pId", 1);
                ((HomeActivity) mContext).changeFragment(new GoodsDetailsFragment(), "GoodsDetailsFragment", bundle);

                break;
        }
    }

}
