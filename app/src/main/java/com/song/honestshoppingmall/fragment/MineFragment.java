package com.song.honestshoppingmall.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.song.honestshoppingmall.R;

/**
 * Created by Judy on 2017/1/8.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private Button bt_title_login;
    private Button bt_title_register;
    private Button mLogin;
    private TextView mHelp;

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_mine, null);
        bt_title_login = (Button) view.findViewById(R.id.bt_title_login);
        bt_title_register = (Button) view.findViewById(R.id.bt_title_register);
        mLogin = (Button) view.findViewById(R.id.bt_login_login);
        mHelp = (TextView) view.findViewById(R.id.tv_help);
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
                break;
            case R.id.bt_title_register:
                break;
            case R.id.bt_login_login:
                break;
            case R.id.tv_help:
                Toast.makeText(getContext(), "就这么着吧,爱用不用!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
