package com.song.honestshoppingmall.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.AddressBean;
import com.song.honestshoppingmall.util.Constants;
import com.song.honestshoppingmall.util.RetrofitUtil;
import com.song.honestshoppingmall.util.SpUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Judy on 2017/1/11.
 */

public class SelectAddressDialog extends Dialog {
    private TextView tvCustomName;
    private TextView tvCustomPhone;
    private TextView tvCustomAddress;
    private Context mContext;
    private List<AddressBean.AddressListBean> mAddressList;

    public SelectAddressDialog(Context context, int themeResId, TextView tvCustomName, TextView tvCustomPhone, TextView tvCustomAddress) {
        super(context, themeResId);
        this.mContext = context;
        this.tvCustomName = tvCustomName;
        this.tvCustomPhone = tvCustomPhone;
        this.tvCustomAddress = tvCustomAddress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_address);
        final ListView lv_select_address = (ListView) findViewById(R.id.lv_select_address);
        String userid = SpUtil.getString(mContext, Constants.USERID, "");
        RetrofitUtil.getAPIRetrofitInstance().getAddressBean(userid).enqueue(new Callback<AddressBean>() {
            @Override
            public void onResponse(Call<AddressBean> call, Response<AddressBean> response) {
                if (response.isSuccessful()) {
                    mAddressList = response.body().getAddressList();
                    if (mAddressList != null) {
                        BaseAdapter adapter = new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return mAddressList.size();
                            }

                            @Override
                            public AddressBean.AddressListBean getItem(int position) {
                                return mAddressList.get(position);
                            }

                            @Override
                            public long getItemId(int position) {
                                return position;
                            }

                            @Override
                            public View getView(final int position, View convertView, ViewGroup parent) {
                                AddressBean.AddressListBean bean = mAddressList.get(position);
                                View view = View.inflate(mContext, R.layout.list_item_address_select, null);
                                TextView tv_custom_name = (TextView) view.findViewById(R.id.tv_custom_name);
                                TextView tv_custom_phone = (TextView) view.findViewById(R.id.tv_custom_phone);
                                TextView tv_custom_address = (TextView) view.findViewById(R.id.tv_custom_address);
                                tv_custom_name.setText(bean.getName());
                                tv_custom_phone.setText(bean.getPhoneNumber());
                                tv_custom_address.setText(bean.getAddressArea() + bean.getAddressDetail());
                                view.setClickable(true);
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        tvCustomName.setText(mAddressList.get(position).getName());
                                        tvCustomPhone.setText(mAddressList.get(position).getPhoneNumber());
                                        tvCustomAddress.setText(mAddressList.get(position).getAddressArea() + mAddressList.get(position).getAddressDetail());
                                        dismiss();
                                    }
                                });
                                return view;
                            }
                        };

                        lv_select_address.setAdapter(adapter);



                    }
                }
            }

            @Override
            public void onFailure(Call<AddressBean> call, Throwable t) {

            }
        });

    }
}
