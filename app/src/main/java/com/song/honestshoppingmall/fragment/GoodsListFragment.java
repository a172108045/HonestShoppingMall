package com.song.honestshoppingmall.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.adapter.ProductListRecyclerAdapter;
import com.song.honestshoppingmall.bean.FilterProductListBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.RetrofitUtil;
import com.song.honestshoppingmall.view.RadioButtonSort;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.song.honestshoppingmall.R.id.rb_fragment_goodslist_price;

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
    private int mCategoryId;
    private String mFilter = "";
    private RecyclerView mRv_fragment_goodslist_product;
    private RadioButtonSort mRb_fragment_goodslist_sale;
    private RadioButtonSort mRb_fragment_goodslist_price;
    private RadioButtonSort mRb_fragment_goodslist_time;
    private RadioButtonSort mRb_fragment_goodslist_comment;
    private Button mBtn_fragment_goodslist_filter;
    private RadioGroup mRadioGroup;
    private String mOrderByStr;

    @Override
    protected View initView() {
        ((HomeActivity)mContext).changeTitle("商品列表");

        if (mRootView == null) {
            mRootView = View.inflate(mContext, R.layout.fragment_goodslist, null);

            mRadioGroup = (RadioGroup) mRootView.findViewById(R.id.radiogroup);
            //初始化获取需要动态设置的子控件view
            //商品recyclerview
            mRv_fragment_goodslist_product = (RecyclerView) mRootView.findViewById(R.id.rv_fragment_goodslist_product);
            //销量
            mRb_fragment_goodslist_sale = (RadioButtonSort) mRootView.findViewById(R.id.rb_fragment_goodslist_sale);
            mRb_fragment_goodslist_sale.setTriangle_Type(RadioButtonSort.Enum_RadioButton_Triangle_Type.SINGLE);
            //价格
            mRb_fragment_goodslist_price = (RadioButtonSort) mRootView.findViewById(rb_fragment_goodslist_price);
            mRb_fragment_goodslist_price.setTriangle_Type(RadioButtonSort.Enum_RadioButton_Triangle_Type.DOUBLE);
            //时间
            mRb_fragment_goodslist_time = (RadioButtonSort) mRootView.findViewById(R.id.rb_fragment_goodslist_time);
            mRb_fragment_goodslist_time.setTriangle_Type(RadioButtonSort.Enum_RadioButton_Triangle_Type.SINGLE);
            //评价
            mRb_fragment_goodslist_comment = (RadioButtonSort) mRootView.findViewById(R.id.rb_fragment_goodslist_comment);
            mRb_fragment_goodslist_comment.setTriangle_Type(RadioButtonSort.Enum_RadioButton_Triangle_Type.SINGLE);
            //筛选
            mBtn_fragment_goodslist_filter = (Button) mRootView.findViewById(R.id.btn_fragment_goodslist_filter);

            //添加按钮事件监听
            addBtnOnClickListener();

        }
        return mRootView;
    }

    @Override
    protected void initData() {
            //从商品分类传递过来的分类id，访问网络接口时需要该cId
            mCategoryId = (int) this.getArguments().get("cId");
            requestNetData(1, 10, mCategoryId, "saleDown", mFilter);
    }

    private void addBtnOnClickListener() {
        //销量
        mRb_fragment_goodslist_sale.setOnClickListener(this);
        //价格
        mRb_fragment_goodslist_price.setOnClickListener(this);
        //时间
        mRb_fragment_goodslist_time.setOnClickListener(this);
        //评价
        mRb_fragment_goodslist_comment.setOnClickListener(this);
        //筛选
        mBtn_fragment_goodslist_filter.setOnClickListener(this);
    }

    /**
     * @param page    第几页
     * @param pageNum 每页个数
     * @param cId     分类ID
     * @param orderBy 排序
     * @param filter  筛选
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
                        if (response.isSuccessful()) {
//                            Toast.makeText(mContext, "GoodsListFragment " + requestMap.toString() + " 请求成功:" + filterProductListBean.toString() , Toast.LENGTH_SHORT).show();
                            Log.d("GoodsListFragment " + requestMap.toString() + " 请求成功:", filterProductListBean.toString());
                            refreshProductListByBean(filterProductListBean);
                        } else {
                            Toast.makeText(mContext, "GoodsListFragment " + requestMap.toString() + " 请求错误码:" + filterProductListBean.error_code, Toast.LENGTH_SHORT).show();
//                            Log.d("GoodsListFragment " + " 请求错误码:" + requestMap.toString(), filterProductListBean.error_code);
                        }
                    }

                    @Override
                    public void onFailure(Call<FilterProductListBean> call, Throwable t) {
                        Toast.makeText(mContext, "GoodsListFragment " + "请求失败： " + requestMap.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //通过网络请求返回的bean数据刷新商品列表recyclerview
    private void refreshProductListByBean(FilterProductListBean filterProductListBean) {

//        mRv_fragment_goodslist_product.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,true));
//        mRv_fragment_goodslist_product.setLayoutManager(new GridLayoutManager(mContext,3,GridLayoutManager.HORIZONTAL,false));
//        mRv_fragment_goodslist_product.setLayoutManager(new GridLayoutManager(mContext,2));
        mRv_fragment_goodslist_product.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        ProductListRecyclerAdapter recyclerAdapter = new ProductListRecyclerAdapter(mContext, filterProductListBean.getProductList());
        mRv_fragment_goodslist_product.setAdapter(recyclerAdapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //销量
            case R.id.rb_fragment_goodslist_sale:
                if(v instanceof  RadioButtonSort){
                    if(((RadioButtonSort) v).isAscendingSort()){
                        this.mOrderByStr = "saleUp";
                    }else{
                        this.mOrderByStr = "saleDown ";
                    }
                }

                break;
            //价格
            case rb_fragment_goodslist_price:
                if(v instanceof  RadioButtonSort){
                    if(((RadioButtonSort) v).isAscendingSort()){
                        this.mOrderByStr = "priceUp";
                    }else{
                        this.mOrderByStr = "priceDown ";
                    }
                }
                break;
            //时间
            case R.id.rb_fragment_goodslist_time:
                if(v instanceof  RadioButtonSort){
                    if(((RadioButtonSort) v).isAscendingSort()){
                        this.mOrderByStr = "shelvesUp";
                    }else{
                        this.mOrderByStr = "shelvesDown ";
                    }
                }
                break;
            //评价
            case R.id.rb_fragment_goodslist_comment:
                if(v instanceof  RadioButtonSort){
                    if(((RadioButtonSort) v).isAscendingSort()){
                        this.mOrderByStr = "commentUp";
                    }else{
                        this.mOrderByStr = "commentDown ";
                    }
                }
                break;
            //筛选
            case R.id.btn_fragment_goodslist_filter:

                break;


        }
        requestNetData(1, 10, this.mCategoryId, this.mOrderByStr, mFilter);
    }

}
