package com.song.honestshoppingmall.fragment;

import android.view.View;

import com.song.honestshoppingmall.R;

/**
 * Created by Judy on 2017/1/8.
 */

public class HomeFragment extends BaseFragment  {

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);

        return view;
    }

    @Override
    protected void initData() {



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu_home:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;

            case R.id.iv_cart_home:
                ((HomeActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, new ShopCartFragment()).commit();
                break;
        }
    }
}
