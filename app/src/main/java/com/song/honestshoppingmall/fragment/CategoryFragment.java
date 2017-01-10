package com.song.honestshoppingmall.fragment;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.adapter.CategoryLeftAdapter;
import com.song.honestshoppingmall.adapter.CategorySecondAdapter;
import com.song.honestshoppingmall.bean.ShopCategoryBean;
import com.song.honestshoppingmall.manager.CategoryDataManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Judy on 2017/1/8.
 */

public class CategoryFragment extends BaseFragment {
    private ListView mLv_category_left;
    private CategoryDataManager mCategoryDataManager;
    private CategoryLeftAdapter mCategoryLeftAdapter;
    private ListView mLv_category_right;
    private CategorySecondAdapter mCategorySecondAdapter;

    private int mCurSelectFirstId = 0;
    private List<ShopCategoryBean.CategoryBean> mFirstPartList = new ArrayList<>();
    private List<ShopCategoryBean.CategoryBean> mSecondPartList = new ArrayList<>();

    @Override
    protected View initView() {
//        TextView tv = new TextView(mContext);
//        tv.setText("这是分类");
//        return tv;
        View view = View.inflate(mContext, R.layout.fragment_category, null);

        mLv_category_left = (ListView) view.findViewById(R.id.lv_category_left);
        mLv_category_right = (ListView) view.findViewById(R.id.lv_category_right);

        return view;
    }

    @Override
    protected void initData() {
        mCategoryDataManager = CategoryDataManager.getInstance();
        mCategoryDataManager.requestCategoryData();

        showCategory();

        mCategoryDataManager.setOnCategoryUpdateListener(new CategoryDataManager.OnCategoryUpdateListener() {

            @Override
            public void OnCategoryUpdate(Response<ShopCategoryBean> response) {
                if(response == null){
                    //网络请求失败，没有连接上服务器，进行相关失败提示
                }
                if(response.isSuccessful()){
                    //获取网络数据成功，可以通过manager获得相应数据进行展示
                    showCategory();
                }else{
                    //获取网络回复成功，但结果出错，进行相关提示
                }

            }
        });

        mLv_category_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurSelectFirstId = mFirstPartList.get(position).getId();
                //刷新二级分类数据
                updateCategorySecondByFirstId(mCurSelectFirstId);
            }
        });
    }

    private void showCategory() {
        if(mFirstPartList != null){
            //刷新左侧一级分类List
            updateCategoryLeft();
            //刷新默认二级分类数据
            if(mFirstPartList.size() > 0) {
                updateCategorySecondByFirstId(mFirstPartList.get(0).getId());
            }
        }
    }

    //刷新左侧商品一级分类数据
    private void updateCategoryLeft() {
        //商品一级分类数据
        mFirstPartList.clear();
        mFirstPartList.addAll(mCategoryDataManager.getFirstPartList());

        if (mCategoryLeftAdapter == null) {
            mCategoryLeftAdapter = new CategoryLeftAdapter(mContext, mFirstPartList);
            mLv_category_left.setAdapter(mCategoryLeftAdapter);
        } else {
            mCategoryLeftAdapter.notifyDataSetChanged();
        }
    }

    //刷新右侧商品二级分类数据
    private void updateCategorySecondByFirstId(int firstId) {
        //商品二级分类数据
        mSecondPartList.clear();
        mSecondPartList.addAll(mCategoryDataManager.getListByParentId(firstId));

        if (mCategorySecondAdapter == null) {
            mCategorySecondAdapter = new CategorySecondAdapter(mContext, mSecondPartList);
            mLv_category_right.setAdapter(mCategorySecondAdapter);
        } else {
            mCategorySecondAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("CategoryFragment", "onResume");
    }
}
