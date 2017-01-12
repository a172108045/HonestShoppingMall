package com.song.honestshoppingmall.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;

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

    @Override
    protected View initView() {
        ((HomeActivity)mContext).changeTitle("商品列表");
        if(mRootView == null){
            mRootView = View.inflate(mContext, R.layout.fragment_goodslist, null);

            //初始化获取需要动态设置的子控件view
            mBtn_fragment_goodslist_detail = (Button) mRootView.findViewById(R.id.btn_fragment_goodslist_detail);

            //添加按钮事件监听
            addBtnOnClickListener();
        }
        return mRootView;
    }

    @Override
    protected void initData() {
        //从商品分类传递过来的分类id，访问网络接口时需要该cId
        mCategoryId = (int) this.getArguments().get("cId");
        requestNetData();
    }

    private void addBtnOnClickListener(){
        mBtn_fragment_goodslist_detail.setOnClickListener(this);
    }

    private void requestNetData() {

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
