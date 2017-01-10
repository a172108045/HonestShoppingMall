package com.song.honestshoppingmall.manager;

import android.util.Log;

import com.song.honestshoppingmall.bean.ShopCategoryBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Creator Administrator
 * @CreatedDate 2017/1/9 14:10
 * @Description ${TODO}
 * @Updater $Author$
 * @UpdateTime $Date$
 * @UpdateDesc ${TODO}
 */

public class CategoryDataManager {
    public static CategoryDataManager sInstance;

    //三级分类数据的id
    private int mFirstParentId = 0;
    //网络商品分类的原始list数据
    private List<ShopCategoryBean.CategoryBean> mRawCategoryBeanList;
    private OnCategoryUpdateListener mOnCategoryUpdateListener;
    private List<ShopCategoryBean.CategoryBean> mFirstPartList;

    //私有化构造函数，不让实例化，必须通过getInstance获得单例对象
    private CategoryDataManager(){
        if (mRawCategoryBeanList == null) {
            mRawCategoryBeanList = new ArrayList<>();
        }
    }

    public static CategoryDataManager getInstance(){
        if(sInstance == null){
            sInstance = new CategoryDataManager();
        }

        return sInstance;
    }

    public void requestCategoryData(){
        //如果商品分类数据不存在则向网络请求分类数据
        if (mRawCategoryBeanList.size() == 0) {
            APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
            apiRetrofitInstance.getCategoryMsg()
                    .enqueue(new Callback<ShopCategoryBean>() {
                        @Override
                        public void onResponse(Call<ShopCategoryBean> call, Response<ShopCategoryBean> response) {

                            if (response.isSuccessful()) {
                                mRawCategoryBeanList.addAll(response.body().getCategory());
                                Log.d("categoryBeanList", "商品分类请求数据成功:" + mRawCategoryBeanList.toString());
                            } else {
                                //
                                Log.d("categoryBeanList", "商品分类请求返回错误码：" + response.body().error_code);
                            }
                            //网络接收数据接口是否成功
                            if(CategoryDataManager.this.mOnCategoryUpdateListener != null){
                                CategoryDataManager.this.mOnCategoryUpdateListener.OnCategoryUpdate(response);
                            }
                        }

                        @Override
                        public void onFailure(Call<ShopCategoryBean> call, Throwable t) {
                            //网络接收数据接口是否成功
                            if(CategoryDataManager.this.mOnCategoryUpdateListener != null){
                                CategoryDataManager.this.mOnCategoryUpdateListener.OnCategoryUpdate(null);
                            }
                        }
                    });
        }
    }

    public List<ShopCategoryBean.CategoryBean> getCategoryData() {
        return mRawCategoryBeanList;
    }

    /**
     * 通过parentId在原始分类List中查找对应的结果数据
     *
     * @param parentId 商品的父id
     * @return 通过parentId在原始分类List中查找出来的结果数据
     */
    public List<ShopCategoryBean.CategoryBean> getListByParentId(int parentId) {
        //通过parentId在原始分类List中查找出来的结果数据
        List<ShopCategoryBean.CategoryBean> list = new ArrayList<>();
        //网络拉下来的原始商品分类List数据
        for (int i = 0; i < mRawCategoryBeanList.size(); i++) {
            ShopCategoryBean.CategoryBean categoryBean = mRawCategoryBeanList.get(i);
            if (categoryBean.getParentId() == parentId) {
                list.add(categoryBean);
            }
        }

        return list;
    }

    /**
     * 得到商品一级分类list数据
     *
     * @return
     */
    public List<ShopCategoryBean.CategoryBean> getFirstPartList() {
        if(mFirstPartList == null || mFirstPartList.size() == 0){
            mFirstPartList = getListByParentId(mFirstParentId);
        }

        return mFirstPartList;
    }

    /**
     * @param firstId 一级分类的id
     * @return 对应分类的二级分类数据list
     */
    public static List<ShopCategoryBean.CategoryBean> getSecondCategoryByParentId(int firstId) {
        List<ShopCategoryBean.CategoryBean> secondList = new ArrayList<>();

        return secondList;
    }

    public interface OnCategoryUpdateListener{
        //网络接收数据接口是否成功,response可以返回正确数据和错误码，如果response为Null则表示请求失败
        public void OnCategoryUpdate(Response<ShopCategoryBean> response);
    }

    public void setOnCategoryUpdateListener(OnCategoryUpdateListener listener){
        this.mOnCategoryUpdateListener = listener;
    }
}




