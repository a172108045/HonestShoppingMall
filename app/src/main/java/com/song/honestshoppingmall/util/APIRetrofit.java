package com.song.honestshoppingmall.util;

import com.song.honestshoppingmall.bean.HomeMsgBean;
import com.song.honestshoppingmall.bean.LoginResultBean;
import com.song.honestshoppingmall.bean.SerchResultBean;
import com.song.honestshoppingmall.bean.Userbean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by lizhenquan on 2017/1/8.
 */

public interface APIRetrofit {
    /**
     *
     * 获取主页数据
     * @return
     */
    @GET("home")
    Call<HomeMsgBean> getHomeMsg();

    /**
     * 搜索
     * @param params
     * @return
     */
    //?page=0&pageNum=10&orderby=saleDown&keyword=奶粉
    @GET("search")
    Call<SerchResultBean> search(@QueryMap Map<String,String> params);

    /**
     * 登录时获取的数据
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Call<LoginResultBean> login(@Field("username") String username, @Field("password") String password);

    /**
     * 用Map的方式登录
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Call<LoginResultBean> loginByFieldMap(@FieldMap Map<String, String> params);


    //Header参数传递  获取用户数据
    @GET("userinfo")
    Call<Userbean>  getUserInfo(@Header("userid") String value);
}