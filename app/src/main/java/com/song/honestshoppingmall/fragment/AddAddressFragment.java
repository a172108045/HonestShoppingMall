package com.song.honestshoppingmall.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText et_name;
    private EditText et_num;
    private EditText et_location;
    private Button bt_manage;
    private Button bt_save;
    private Button setting_add;

    @Override
    protected View initView() {
        ((HomeActivity) mContext).changeTitle("添加地址");
        View view = View.inflate(mContext, R.layout.fragment_address_add, null);

        address_selector = (TextView) view.findViewById(R.id.tv_address_selector);
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_num = (EditText) view.findViewById(R.id.et_num);
        et_location = (EditText) view.findViewById(R.id.et_location);
        bt_manage = (Button) view.findViewById(R.id.bt_address_manage);
        bt_save = (Button) view.findViewById(R.id.bt_address_save);
        setting_add = (Button) view.findViewById(R.id.bt_setting_add_address);

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
                        SpUtil.saveString(mContext, Constants.ADDRESS_COUNTY, mCounty);
                        address_selector.setText(" " + mProvinceName + " " + mCity + " " + mCounty);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.bt_address_manage:
                bt_save.setTextColor(Color.WHITE);
                bt_save.setEnabled(false);
                ((HomeActivity) mContext).changeFragment(new AddressFragment(), "AddressFragment");

                break;
            case R.id.bt_address_save:
                String name = et_name.getText().toString().trim();
                String number = et_num.getText().toString().trim();
                String location = et_location.getText().toString();
                SpUtil.saveString(mContext, Constants.ADDRESS_NAME, name);
                SpUtil.saveString(mContext, Constants.ADDRESS_NUMBER, number);
                SpUtil.saveString(mContext, Constants.ADDRESS_LOCATION, location);
                bt_manage.setTextColor(Color.BLACK);
                bt_manage.setEnabled(true);

                break;
            case R.id.bt_setting_add_address:
                break;
        }
    }

}
