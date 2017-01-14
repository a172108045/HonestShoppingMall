package com.song.honestshoppingmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.song.honestshoppingmall.R;
import com.song.honestshoppingmall.bean.LoginResultBean;
import com.song.honestshoppingmall.db.RecordDBHelper;
import com.song.honestshoppingmall.util.APIRetrofit;
import com.song.honestshoppingmall.util.Constants;
import com.song.honestshoppingmall.util.RetrofitUtil;
import com.song.honestshoppingmall.util.SpUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initData();

    }

    private void initData() {
        if (SpUtil.getBoolean(this, Constants.CHECKBOX, false)) {
            String userid = SpUtil.getString(this, Constants.USERID, "");
            if (!TextUtils.isEmpty(userid)) {
                String username = SpUtil.getString(this, Constants.USERNAME, "");
                String password = SpUtil.getString(this, Constants.PASSWORD, "");
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    final APIRetrofit mApiRetrofitInstance = RetrofitUtil.getAPIRetrofitInstance();
                    mApiRetrofitInstance.login(username, password).enqueue(new Callback<LoginResultBean>() {
                        @Override
                        public void onResponse(Call<LoginResultBean> call, Response<LoginResultBean> response) {
                            if (response.isSuccessful()) {
                                if (response.body().error == null) {
                                    SpUtil.saveBoolean(SplashActivity.this, Constants.LOGIN_STATE, true);
                                    String userid = response.body().getUserInfo().getUserid();
                                    SpUtil.saveString(SplashActivity.this, Constants.USERID, userid);
                                } else {
                                    Toast.makeText(SplashActivity.this, "你特么账号密码不正确", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SplashActivity.this, "反正你就是连不上!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResultBean> call, Throwable t) {
                            Toast.makeText(SplashActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

        }

        //创建数据库和浏览记录表
        new RecordDBHelper(this).getWritableDatabase();

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                Boolean isFirst = SpUtil.getBoolean(SplashActivity.this, "isFirst", true);

                if (isFirst) {
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    finish();
                    return;
                }

                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();

            }
        }).start();

    }

}
