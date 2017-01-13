package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;

/**
 * Created by zan on 2017/1/12.
 */

public class AddAddressFragment extends BaseFragment {

    private PopupWindow mPopupWindow;

    @Override
    protected View initView() {
        ((HomeActivity) mContext).changeTitle("添加地址");
        View view = View.inflate(mContext, R.layout.fragment_address_add, null);

        initOnClick();
        return view;
    }

    private void initOnClick() {

    }

    @Override
    protected void initData() {

        initPopupWindow();
    }

    private void initPopupWindow() {
        View contentView = View.inflate(mContext, R.layout.popupwindow_addaddress, null);

        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mPopupWindow = new PopupWindow(contentView, width, height, true);
    }


}
