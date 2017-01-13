package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;

/**
 * Created by zan on 2017/1/12.
 */

public class AddAddressFragment extends BaseFragment implements View.OnClickListener {

    private PopupWindow mPopupWindow;
    private EditText aadress_selector;

    @Override
    protected View initView() {
        ((HomeActivity) mContext).changeTitle("添加地址");
        View view = View.inflate(mContext, R.layout.fragment_address_add, null);

        aadress_selector = (EditText) view.findViewById(R.id.et_address_selector);

        initOnClick();
        return view;
    }

    private void initOnClick() {
        aadress_selector.setOnClickListener(this);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_address_selector:
                mPopupWindow.showAsDropDown(aadress_selector);
                break;
        }
    }
}
