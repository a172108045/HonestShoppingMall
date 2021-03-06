package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.bean.LogoutBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.Constants;
import com.song.honestshoppingmall.util.RetrofitUtil;
import com.song.honestshoppingmall.util.SpUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zan on 2017/1/9.
 */

public class UserFragment extends BaseFragment implements View.OnClickListener {

    private Button bt_logout;
    private LinearLayout mOrder;
    private ImageView address_user;
    private ImageView mIv_collect_user;

    @Override
    protected View initView() {
        ((HomeActivity)mContext).changeTitle("个人中心");
        View view = View.inflate(mContext, R.layout.fragment_mine_user, null);

        bt_logout = (Button) view.findViewById(R.id.bt_logout_user);
        mOrder = (LinearLayout) view.findViewById(R.id.linearlayout_myorder);
        address_user = (ImageView) view.findViewById(R.id.iv_address_user);
        mIv_collect_user = (ImageView) view.findViewById(R.id.iv_collect_user);

        initclick();

        return view;
    }

    private void initclick() {
        address_user.setOnClickListener(this);
        bt_logout.setOnClickListener(this);
        mOrder.setOnClickListener(this);
        mIv_collect_user.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_logout_user:
                SpUtil.saveString(mContext, Constants.USERID, null);
                APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
                apiRetrofitInstance.logOut(SpUtil.getString(mContext, Constants.USERID, "")).enqueue(new Callback<LogoutBean>() {
                    @Override
                    public void onResponse(Call<LogoutBean> call, Response<LogoutBean> response) {
                        if (response.isSuccessful()) {
                            if (response.body().error == null) {
                                logOutSetting();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LogoutBean> call, Throwable t) {
                        logOutSetting();
                    }
                });
                break;
            case R.id.linearlayout_myorder:
                ((HomeActivity) mContext).changeFragment(new MyOrderFragment(), "MyOrderFragment");
                break;

            case R.id.iv_address_user:
                ((HomeActivity) mContext).changeFragment(new AddressFragment(), "AddressFragment");
                break;

            case R.id.iv_collect_user:
                ((HomeActivity) mContext).changeFragment(new CollectionFragment(), "CollectionFragment");
                break;
        }
    }

    private void logOutSetting() {
        SpUtil.saveBoolean(mContext, Constants.LOGIN_STATE, false);
        SpUtil.saveString(mContext, Constants.USERID, null);
        SpUtil.saveBoolean(mContext, Constants.CHECKBOX, false);
        ((HomeActivity) mContext).changeFragment(new MineFragment(), "MineFragment");
        Toast.makeText(mContext, "Good Bye,Honey!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity)mContext).changeTitle("个人中心");
    }
}
