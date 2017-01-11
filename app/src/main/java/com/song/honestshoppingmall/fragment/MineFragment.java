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
import com.song.honestshoppingmall.bean.RegisterBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.Constants;
import com.song.honestshoppingmall.util.RetrofitUtil;
import com.song.honestshoppingmall.util.SpUtil;

import java.util.HashMap;
import java.util.Map;

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
    private String mLoginText;
    private String mConfirm;


    @Override
    protected View initView() {
        System.out.println("登录状态："+SpUtil.getBoolean(mContext, Constants.LOGIN_STATE, false));
        System.out.println("自动登录状态："+SpUtil.getBoolean(mContext, Constants.CHECKBOX, false));
        System.out.println("用户ID："+SpUtil.getString(mContext, Constants.USERID, ""));

        if(SpUtil.getBoolean(mContext, Constants.LOGIN_STATE, false)) {
            if (SpUtil.getString(mContext, Constants.USERID, "") != "") {
                ((HomeActivity) mContext).removeAllFragment();
                ((HomeActivity) mContext).changeFragment(new UserFragment(), "UserFragment");
            }
        }

        if(SpUtil.getBoolean(mContext, Constants.CHECKBOX, false)) {
            if (SpUtil.getString(mContext, Constants.USERID, "") != "") {
                ((HomeActivity) mContext).removeAllFragment();
                ((HomeActivity) mContext).changeFragment(new UserFragment(), "UserFragment");
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
                cb_aotologin.setVisibility(View.VISIBLE);
                mHelp.setVisibility(View.VISIBLE);
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
                cb_aotologin.setVisibility(View.GONE);
                mHelp.setVisibility(View.GONE);
                mLogin.setText("注册");

                break;

            case R.id.bt_login_login:
                mLoginText = mLogin.getText().toString();
                mUsername = et_user_login.getEditText().getText().toString().trim();
                mPassword = et_pass_login.getEditText().getText().toString();
                if (mLoginText.equals("登陆")) {
                    if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
                        Toast.makeText(mContext, "账号密码不能为空!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    final APIRetrofit mApiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
                    mApiRetrofitInstance.login(mUsername, mPassword).enqueue(new Callback<LoginResultBean>() {
                        @Override
                        public void onResponse(Call<LoginResultBean> call, Response<LoginResultBean> response) {
                            if (response.isSuccessful()) {
                                if (response.body().error == null) {
                                    SpUtil.saveBoolean(mContext, Constants.LOGIN_STATE, true);
                                    boolean checked = cb_aotologin.isChecked();
                                    if (checked == true) {
                                        SpUtil.saveString(mContext, Constants.USERNAME, mUsername);
                                        SpUtil.saveString(mContext, Constants.PASSWORD, mPassword);
                                        SpUtil.saveBoolean(mContext, Constants.CHECKBOX, true);
                                    } else {
                                        SpUtil.saveBoolean(mContext, Constants.CHECKBOX, false);
                                    }

                                    String userid = response.body().getUserInfo().getUserid();
                                    SpUtil.saveString(getContext(), Constants.USERID, userid);
                                    ((HomeActivity) mContext).removeAllFragment();
                                    ((HomeActivity) mContext).changeFragment(new UserFragment(), "UserFragment");
                                } else {
                                    Toast.makeText(mContext, "你特么账号密码不正确", Toast.LENGTH_SHORT).show();
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

                } else {
                    //注册按钮逻辑.
                    Map<String, String> register = new HashMap<>();
                    mConfirm = et_pass_confirm.getEditText().getText().toString();
                    if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword) || TextUtils.isEmpty(mConfirm)) {
                        Toast.makeText(mContext, "用户资料不能为空!", Toast.LENGTH_SHORT).show();
                    } else if (mUsername.length() <= 4 || mUsername.length() >= 15) {
                        Toast.makeText(mContext, "4-15个字符的用户名", Toast.LENGTH_SHORT).show();
                    } else if (!mPassword.equals(mConfirm)) {
                        Toast.makeText(mContext, "密码不一致!", Toast.LENGTH_SHORT).show();
                    } else {

                        register.put("username", mUsername);
                        register.put("password", mPassword);
                        RetrofitUtil.getAPIRetrofitInstance().sendRegister(register).enqueue(new Callback<RegisterBean>() {
                            @Override
                            public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().error == null) {
                                        Toast.makeText(mContext, "恭喜注册成功!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(mContext, response.body().error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<RegisterBean> call, Throwable t) {
                                Toast.makeText(mContext, "不知为何,就是没注册成功!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }
                break;

            case R.id.tv_help:
                Toast.makeText(getContext(), "反正我又解决不了!", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
