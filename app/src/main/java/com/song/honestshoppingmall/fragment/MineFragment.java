package com.song.honestshoppingmall.fragment;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.LoginResultBean;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.RetrofitUtil;

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
    private TextInputLayout mTextInputLayout;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_mine, null);



        bt_title_login = (Button) view.findViewById(R.id.bt_title_login);
        bt_title_register = (Button) view.findViewById(R.id.bt_title_register);
        mLogin = (Button) view.findViewById(R.id.bt_login_login);
        mHelp = (TextView) view.findViewById(R.id.tv_help);

        mTextInputLayout = (TextInputLayout) view.findViewById(R.id.textinputlayou_pass_confirm);

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
                    mTextInputLayout.setVisibility(View.GONE);
                    bt_title_login.setEnabled(false);
                    bt_title_register.setEnabled(true);
                    mLogin.setText("登陆");




                break;
            case R.id.bt_title_register:
                    mTextInputLayout.setVisibility(View.VISIBLE);
                    bt_title_register.setEnabled(false);
                    bt_title_login.setEnabled(true);
                    mLogin.setText("注册");

                break;
            case R.id.bt_login_login:
                String loginText = mLogin.getText().toString();
                if(loginText.equals("登陆")) {
                    APIRetrofit apiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
                    apiRetrofitInstance.login("test","test").enqueue(new Callback<LoginResultBean>() {
                        @Override
                        public void onResponse(Call<LoginResultBean> call, Response<LoginResultBean> response) {
                            if (response.isSuccessful()) {
                                LoginResultBean body = response.body();
                                Toast.makeText(mContext, body.toString(), Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<LoginResultBean> call, Throwable t) {
                            Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Toast.makeText(getContext(), "丑拒", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_help:
                Toast.makeText(getContext(), "就这么着吧,爱用不用!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
