package com.song.honestshoppingmall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.song.honestshoppingmall.adapter.CategoryThirdAdapter;
import com.song.honestshoppingmall.bean.ShopCategoryBean;
import com.song.honestshoppingmall.manager.CategoryDataManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @Creator Administrator
 * @CreatedDate 2017/1/9 21:39
 * @Description ${TODO}
 * @Updater $Author$
 * @UpdateTime $Date$
 * @UpdateDesc ${TODO}
 */

public class CategoryGridView extends GridView {

    private CategoryDataManager mCategoryDataManager;
    private List<ShopCategoryBean.CategoryBean> mThirdPartList = new ArrayList<>();
    private CategoryThirdAdapter mThirdAdapter;

    public CategoryGridView(Context context) {
        super(context);
    }

    public CategoryGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    //刷新右侧商品三级分类数据
    public void updateCategoryThirdBySecondId(Context context, int secondId) {
        mCategoryDataManager = CategoryDataManager.getInstance();
        //商品三级分类数据
        mThirdPartList.clear();
        mThirdPartList.addAll(mCategoryDataManager.getListByParentId(secondId));
        updateCategoryThird(context, mThirdPartList);
    }

    public void updateCategoryThird(Context context, List<ShopCategoryBean.CategoryBean> thirdList){
        if(mThirdAdapter == null){
            mThirdAdapter = new CategoryThirdAdapter(context, thirdList);
            this.setAdapter(mThirdAdapter);
        }else{
            mThirdAdapter.notifyDataSetChanged();
        }
    }

    public void addOnItemClickListener(){
        this.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //此处成功点击
                Log.d("CategoryGridView", "您点击了" + mThirdPartList.get(position).getName());
            }
        });
    }

}
