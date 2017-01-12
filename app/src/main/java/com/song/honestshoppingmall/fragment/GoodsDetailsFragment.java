package com.song.honestshoppingmall.fragment;

import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.adapter.GoodsPagerAdapter;
import com.song.honestshoppingmall.bean.GoodsBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.DialogAlertUtils;
import com.song.honestshoppingmall.util.RetrofitUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Creator Administrator
 * @CreatedDate 2017/1/10 20:07
 * @Description ${TODO}
 * @Updater $Author$
 * @UpdateTime $Date$
 * @UpdateDesc ${TODO}
 */

public class GoodsDetailsFragment extends BaseFragment implements View.OnClickListener {

    private ViewPager mVp_fragment_goods_details;
    private TextView mTv_fragment_goods_rawPrice;
    private TextView mTv_fragment_goods_curPrice;
    private RatingBar mRb_fragment_goods_star;
    private TextView mTv_fragment_goods_name;
    private GoodsBean.ProductBean mProductBean;
    private PagerAdapter mPagerAdapter;
    private View mRootView;

    @Override
    protected View initView() {
        if(mRootView == null){
            mRootView = View.inflate(mContext, R.layout.fragment_goods, null);

            ImageView iv_fragment_goods_back = (ImageView) mRootView.findViewById(R.id.iv_fragment_goods_back);
            iv_fragment_goods_back.setOnClickListener(this);

            //获取商品详情界面需要动态设置属性的子控件
            //商品的翻页viewpager
            mVp_fragment_goods_details = (ViewPager) mRootView.findViewById(R.id.vp_fragment_goods_details);
            //原始价格
            mTv_fragment_goods_rawPrice = (TextView) mRootView.findViewById(R.id.tv_fragment_goods_rawPrice);
            //当前价格
            mTv_fragment_goods_curPrice = (TextView) mRootView.findViewById(R.id.tv_fragment_goods_curPrice);
            //商品评分
            mRb_fragment_goods_star = (RatingBar) mRootView.findViewById(R.id.rb_fragment_goods_star);
            //商品名称
            mTv_fragment_goods_name = (TextView) mRootView.findViewById(R.id.tv_fragment_goods_name);
            //收藏按钮
            Button btn_fragment_goods_collection =  (Button) mRootView.findViewById(R.id.btn_fragment_goods_collection);
            //加入购物车按钮
            Button btn_fragment_goods_addCart = (Button) mRootView.findViewById(R.id.btn_fragment_goods_addCart);
            //购买按钮
            Button btn_fragment_goods_buy =  (Button) mRootView.findViewById(R.id.btn_fragment_goods_buy);

            btn_fragment_goods_collection.setOnClickListener(this);
            btn_fragment_goods_addCart.setOnClickListener(this);
            btn_fragment_goods_buy.setOnClickListener(this);
        }

        return mRootView;
    }

    @Override
    protected void initData() {
        getProductDataByPid(1);
    }



    private void getProductDataByPid(int pId) {
        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
        apiRetrofitInstance.getProductData(pId)
                .enqueue(new Callback<GoodsBean>() {
                    @Override
                    public void onResponse(Call<GoodsBean> call, Response<GoodsBean> response) {
                        if(response.isSuccessful()){
                            mProductBean = response.body().getProduct();
                            String successResponse = mProductBean.toString();
//                            Toast.makeText(mContext, "商品详情成功返回数据：" + successResponse, Toast.LENGTH_SHORT).show();
                            Log.d("GoodsDetailsFragment", successResponse);

                            //设置商品图片翻页
                            setViewPager(mProductBean.getPics());
                            //设置价格
                            setPrice(mProductBean.getPrice());
                            //设置市场价格
                            setMarketPrice(mProductBean.getMarketPrice());
                            //设置商品评分(星星表示)
                            setScore(mProductBean.getScore());
                            //设置商品名称
                            setProductName(mProductBean.getName());

                        }else{
                            Toast.makeText(mContext, "商品详情请求错误码：" + response.body().error_code, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GoodsBean> call, Throwable t) {
                        Toast.makeText(mContext, "商品详情请求失败：", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_fragment_goods_back:
                ((HomeActivity) mContext).popBackStack();
                break;
            case R.id.btn_fragment_goods_collection:
                //收藏
                break;
            case R.id.btn_fragment_goods_addCart:
                //加入购物车
                if(mProductBean != null){
                    DialogAlertUtils dialogAlertUtils  = new DialogAlertUtils(mContext,mProductBean);
                }
                break;
            case R.id.btn_fragment_goods_buy:
                //购买


                break;
        }
    }

    //设置商品市场价
    private void setMarketPrice(int marketPrice){
        if(mTv_fragment_goods_rawPrice != null){
            mTv_fragment_goods_rawPrice.setText("￥" + marketPrice);
            //设置原价view添加中间横线
            mTv_fragment_goods_rawPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    //设置商品现价
    private void setPrice(int price){
        if(mTv_fragment_goods_curPrice != null){
            mTv_fragment_goods_curPrice.setText("￥" + price);
        }
    }

    //设置图片翻页
    private void setViewPager(List<String> picsList){
        if(picsList != null){
            if(mPagerAdapter == null){
                mPagerAdapter = new GoodsPagerAdapter(mContext, picsList);
                mVp_fragment_goods_details.setAdapter(mPagerAdapter);
            }else{
                mPagerAdapter.notifyDataSetChanged();
            }
        }
    }

    //设置商品评分
    private void setScore(int score){
        if(mRb_fragment_goods_star != null){
            mRb_fragment_goods_star.setRating(score);
        }
    }

    //设置商品名称
    private void setProductName(String name){
        if(mTv_fragment_goods_name != null){
            mTv_fragment_goods_name.setText(name);
        }
    }

}
