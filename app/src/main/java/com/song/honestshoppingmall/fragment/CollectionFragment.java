package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.adapter.ColletionAdapter;
import com.song.honestshoppingmall.bean.CollectionBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.Constants;
import com.song.honestshoppingmall.util.RetrofitUtil;
import com.song.honestshoppingmall.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Creator Administrator
 * @CreatedDate 2017/1/14 9:36
 * @Description ${TODO}
 * @Updater $Author$
 * @UpdateTime $Date$
 * @UpdateDesc ${TODO}
 */

public class CollectionFragment extends BaseFragment {

    private View mRootView;
    private GridView mGv_fragment_collection;
    private ImageView mIv_fragment_collection;
    private List<CollectionBean.ProductListBean> mProductList = new ArrayList<>();
    private ColletionAdapter mColletionAdapter;

    @Override
    protected View initView() {
        if(mRootView == null){
            ((HomeActivity)mContext).changeTitle("收藏夹");
            mRootView = View.inflate(mContext, R.layout.fragment_collection, null);

            mGv_fragment_collection = (GridView) mRootView.findViewById(R.id.gv_fragment_collection);
            mIv_fragment_collection = (ImageView) mRootView.findViewById(R.id.iv_fragment_collection);
        }
        return mRootView;
    }

    @Override
    protected void initData() {
        String userid = SpUtil.getString(mContext, Constants.USERID, "");
        requestColletionData(userid, 1, 10);
    }

    public void requestColletionData(String userid, int page, int pageNum){
        APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();

        apiRetrofitInstance.getCollectionData(page, pageNum, userid)
                .enqueue(new Callback<CollectionBean>() {
                    @Override
                    public void onResponse(Call<CollectionBean> call, Response<CollectionBean> response) {
                        CollectionBean collectionBean = response.body();
                        if (response.isSuccessful()) {
                            mProductList.clear();
                            if(collectionBean.getProductList() == null){
                                mIv_fragment_collection.setVisibility(View.VISIBLE);

                            }else{
                                mProductList.addAll(collectionBean.getProductList());
                                mIv_fragment_collection.setVisibility(View.GONE);
                            }

                            if(mColletionAdapter == null){
                                mColletionAdapter = new ColletionAdapter(mContext, mProductList);
                                mGv_fragment_collection.setAdapter(mColletionAdapter);
                            }else{
                                mColletionAdapter.notifyDataSetChanged();
                            }

                        } else {
                            Toast.makeText(mContext, "收藏夹请求错误码：" + collectionBean.error_code, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CollectionBean> call, Throwable t) {
                        Toast.makeText(mContext, "收藏夹请求失败：", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
