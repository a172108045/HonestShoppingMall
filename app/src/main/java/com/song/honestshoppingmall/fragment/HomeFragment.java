package com.song.honestshoppingmall.fragment;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;

/**
 * Created by Judy on 2017/1/8.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ImageView mIv_menu_home;
    private ImageView mIv_cart_home;

    @Override
    protected View initView(LayoutInflater inflater) {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_main);
        mNavigationView = (NavigationView) view.findViewById(R.id.nav_view);
        mIv_menu_home = (ImageView) view.findViewById(R.id.iv_menu_home);
        mIv_cart_home = (ImageView) view.findViewById(R.id.iv_cart_home);



        return view;
    }

    @Override
    protected void initData() {
        mIv_menu_home.setOnClickListener(this);
        mIv_cart_home.setOnClickListener(this);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return false;
            }
        });

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
