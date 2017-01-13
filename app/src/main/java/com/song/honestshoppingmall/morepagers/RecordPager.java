package com.song.honestshoppingmall.morepagers;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.GoodsBean;
import com.song.honestshoppingmall.dao.RecordDao;
import com.song.honestshoppingmall.util.RetrofitUtil;
import com.song.honestshoppingmall.util.Urls;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Judy on 2017/1/11.
 */

public class RecordPager extends BasePager {

    private View mView;
    private LinearLayout mLl_record_pager;

    public RecordPager(Context context) {
        super(context);
    }

    @Override
    public View getRootView() {
        mView = View.inflate(mContext, R.layout.pager_record, null);
        mLl_record_pager = (LinearLayout) mView.findViewById(R.id.ll_record_pager);
        return mView;
    }

    @Override
    public void initData() {
        final List<String> data = new RecordDao(mContext).queryAll();
        for (int i = 0; i < data.size(); i++) {
            final View view = View.inflate(mContext, R.layout.product_list_item, null);
            RetrofitUtil.getAPIRetrofitInstance().getProductData(Integer.parseInt(data.get(i))).enqueue(new Callback<GoodsBean>() {
                @Override
                public void onResponse(Call<GoodsBean> call, Response<GoodsBean> response) {
                    if (response.isSuccessful()) {
                        GoodsBean.ProductBean product = response.body().getProduct();
                        if (product != null) {
                            ImageView iv_product = (ImageView) view.findViewById(R.id.iv_product);
                            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
                            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
                            if (product.getBigPic().size() > 0) {
                                Glide.with(mContext.getApplicationContext()).load(Urls.BASE_URL + product.getBigPic().get(0)).into(iv_product);
                                tv_name.setText(product.getName());
                                tv_price.setText(product.getPrice() + "元");
                                mLl_record_pager.addView(view);
                            }
                        }
                    } else {
                        Toast.makeText(mContext, "获取数据错误！", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GoodsBean> call, Throwable t) {
                    Toast.makeText(mContext, "获取网络请求错误！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
