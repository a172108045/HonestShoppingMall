package com.song.honestshoppingmall.fragment;

import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.activity.HomeActivity;
import com.song.honestshoppingmall.bean.LoginResultBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.Constants;
import com.song.honestshoppingmall.util.RetrofitUtil;
import com.song.honestshoppingmall.util.SpUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Judy on 2017/1/8.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private Button bt_title_login;
    private Button bt_title_register;
    private Button mLogin;
    private TextView mHelp;
    private TextInputLayout et_pass_confirm;
    private TextInputLayout et_user_login;
    private TextInputLayout et_pass_login;
    private CheckBox cb_aotologin;
    private String mUsername;
    private String mPassword;
    private boolean mChecked;

    @Override
    protected View initView() {

        if (SpUtil.getBoolean(mContext, Constants.CHECKBOX, false) == true) {
            if (SpUtil.getString(mContext, Constants.USERID, null) != null) {
                ((HomeActivity) mContext).removeAllFragment();
                ((HomeActivity) mContext).changeFragment(new UserFragment(),"UserFragment");
            }
        }

        View view = View.inflate(mContext, R.layout.fragment_mine, null);

        bt_title_login = (Button) view.findViewById(R.id.bt_title_login);
        bt_title_register = (Button) view.findViewById(R.id.bt_title_register);
        mLogin = (Button) view.findViewById(R.id.bt_login_login);

        mHelp = (TextView) view.findViewById(R.id.tv_help);

        cb_aotologin = (CheckBox) view.findViewById(R.id.cb_aotologin_mine);

        et_pass_confirm = (TextInputLayout) view.findViewById(R.id.textinputlayou_pass_confirm);
        et_user_login = (TextInputLayout) view.findViewById(R.id.textinputlayout_user_login);
        et_pass_login = (TextInputLayout) view.findViewById(R.id.textinputlayou_pass_login);

        initClick();
        return view;
    }

    private void initClick() {

        bt_title_login.setOnClickListener(this);
        bt_title_register.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mHelp.setOnClickListener(this);

    }


    @Override
    protected void initData() {


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bt_title_login:
                et_pass_confirm.setVisibility(View.GONE);
                bt_title_login.setEnabled(false);
                bt_title_register.setEnabled(true);
                bt_title_login.setTextColor(Color.WHITE);
                bt_title_register.setTextColor(Color.BLACK);
                et_user_login.getEditText().setText(null);
                et_pass_login.getEditText().setText(null);
                et_pass_confirm.getEditText().setText(null);
                mLogin.setText("登陆");

                break;

            case R.id.bt_title_register:
                et_user_login.getEditText().setText(null);
                et_pass_login.getEditText().setText(null);
                et_pass_confirm.setVisibility(View.VISIBLE);
                bt_title_register.setEnabled(false);
                bt_title_login.setEnabled(true);
                bt_title_register.setTextColor(Color.WHITE);
                bt_title_login.setTextColor(Color.BLACK);
                mLogin.setText("注册");

                break;

            case R.id.bt_login_login:
                String loginText = mLogin.getText().toString();
                if (loginText.equals("登陆")) {
                    mUsername = et_user_login.getEditText().getText().toString().trim();
                    mPassword = et_pass_login.getEditText().getText().toString();
                    if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
                        Toast.makeText(mContext, "账号密码不能为空!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
                        apiRetrofitInstance.login(mUsername, mPassword).enqueue(new Callback<LoginResultBean>() {
                            @Override
                            public void onResponse(Call<LoginResultBean> call, Response<LoginResultBean> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().error == null) {
                                        mChecked = cb_aotologin.isChecked();
                                        if(mChecked == true) {
                                            SpUtil.saveBoolean(mContext, Constants.CHECKBOX, true);
                                        } else {
                                            SpUtil.saveBoolean(mContext, Constants.CHECKBOX, false);
                                        }
                                        String userid = response.body().getUserInfo().getUserid();
                                        SpUtil.saveString(getContext(), Constants.USERID, userid);
                                        ((HomeActivity) mContext).changeFragment(new UserFragment(), "UserFragment");
                                    } else {
                                        Toast.makeText(mContext,"你特么账号密码不正确", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(mContext, "反正你就是连不上!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginResultBean> call, Throwable t) {
                                Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else {
                        //注册按钮逻辑.


                    }

                break;

            case R.id.tv_help:
                Toast.makeText(getContext(), "反正我又解决不了!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
