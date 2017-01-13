package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.TextView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.util.Constants;
import com.song.honestshoppingmall.util.SpUtil;

import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.OnAddressSelectedListener;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;

/**
 * Created by zan on 2017/1/12.
 */

public class AddAddressFragment extends BaseFragment implements View.OnClickListener {

    //    private PopupWindow mPopupWindow;
    private TextView address_selector;
    private String mProvinceName;
    private String mCity;
    private String mCounty;

    @Override
    protected View initView() {
        ((HomeActivity) mContext).changeTitle("添加地址");
        View view = View.inflate(mContext, R.layout.fragment_address_add, null);

        address_selector = (TextView) view.findViewById(R.id.tv_address_selector);

        initOnClick();
        return view;
    }

    private void initOnClick() {
        address_selector.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        //        initPopupWindow();

        //        initProvince();

    }


   /* private void initProvince() {
        AddressProvinceAdapter mAdapter = new AddressProvinceAdapter(mContext, mNewProvince);
        mProvince.setAdapter(mAdapter);
    }*/

   /* private void initPopupWindow() {
        View contentView = View.inflate(mContext, R.layout.popupwindow_addaddress, null);

        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mPopupWindow = new PopupWindow(contentView, width, height, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setOutsideTouchable(true);

        mProvince = (ListView) contentView.findViewById(R.id.lv_addaddress_province);
        mCity = (ListView) contentView.findViewById(R.id.lv_addaddress_city);
        mDistrict = (ListView) contentView.findViewById(R.id.lv_addaddress_district);


    }*/


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address_selector:
                final BottomDialog dialog = new BottomDialog(mContext);
                dialog.setOnAddressSelectedListener(new OnAddressSelectedListener() {
                    @Override
                    public void onAddressSelected(Province province, City city, County county, Street street) {
                        String s =
                                (province == null ? "" : province.name) +
                                        (city == null ? "" : "\n" + city.name) +
                                        (county == null ? "" : "\n" + county.name) +
                                        (street == null ? "" : "\n" + street.name);

//                        T.showShort(mContext, s);
                        mProvinceName = province.name.toString();
                        mCity = city.name.toString();
                        mCounty = county.name.toString();
                        SpUtil.saveString(mContext, Constants.ADDRESS_PROVINCE, mProvinceName);
                        SpUtil.saveString(mContext, Constants.ADDRESS_CITY, mCity);
                        SpUtil.saveString(mContext, Constants.ADDRESS_PROVINCE, mCounty);
                        address_selector.setText(" " + mProvinceName + " " + mCity + " " + mCounty);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }
    }

}
