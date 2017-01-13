package com.song.honestshoppingmall.fragment;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.bean.AddressProvince;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.RetrofitUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zan on 2017/1/12.
 */

public class AddAddressFragment extends BaseFragment implements View.OnClickListener {

    private PopupWindow mPopupWindow;
    private TextView aadress_selector;
    private List<AddressProvince.AreaListBean> mProcvinceList;
    private ListView mProvince;
    private ListView mCity;
    private ListView mDistrict;

    @Override
    protected View initView() {
        ((HomeActivity) mContext).changeTitle("添加地址");
        View view = View.inflate(mContext, R.layout.fragment_address_add, null);

        aadress_selector = (TextView) view.findViewById(R.id.tv_address_selector);

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
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setOutsideTouchable(true);

        mProvince = (ListView) contentView.findViewById(R.id.lv_addaddress_province);
        mCity = (ListView) contentView.findViewById(R.id.lv_addaddress_city);
        mDistrict = (ListView) contentView.findViewById(R.id.lv_addaddress_district);


    }


    @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_address_selector:
                    mPopupWindow.showAsDropDown(aadress_selector);
                    APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
                    apiRetrofitInstance.getProvinceList(0).enqueue(new Callback<AddressProvince>() {
                        @Override
                        public void onResponse(Call<AddressProvince> call, Response<AddressProvince> response) {
                            if(response.isSuccessful()) {
                                if(response.body().error == null) {
                                    mProcvinceList = response.body().getAreaList();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AddressProvince> call, Throwable t) {

                        }
                    });
                    break;
            }
        }
}
