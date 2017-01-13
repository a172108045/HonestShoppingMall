package com.song.honestshoppingmall.util;

import com.song.honestshoppingmall.bean.AddCartBean;
import com.song.honestshoppingmall.bean.AddressBean;
import com.song.honestshoppingmall.bean.CheckOutBean;
import com.song.honestshoppingmall.bean.DeleteCartBean;
import com.song.honestshoppingmall.bean.DesenoBean;
import com.song.honestshoppingmall.bean.FilterProductListBean;
import com.song.honestshoppingmall.bean.GoodsBean;
import com.song.honestshoppingmall.bean.HelpBean;
import com.song.honestshoppingmall.bean.HelpDetailBean;
import com.song.honestshoppingmall.bean.HomeMsgBean;
import com.song.honestshoppingmall.bean.LoginResultBean;
import com.song.honestshoppingmall.bean.LogoutBean;
import com.song.honestshoppingmall.bean.MyOrderBean;
import com.song.honestshoppingmall.bean.NewonBean;
import com.song.honestshoppingmall.bean.OrderDetailBean;
import com.song.honestshoppingmall.bean.OrderSubmitBean;
import com.song.honestshoppingmall.bean.ProductCommentBean;
import com.song.honestshoppingmall.bean.PrommotionBean;
import com.song.honestshoppingmall.bean.RecommendBean;
import com.song.honestshoppingmall.bean.RegisterBean;
import com.song.honestshoppingmall.bean.ScareBuyBean;
import com.song.honestshoppingmall.bean.SearchDetailBean;
import com.song.honestshoppingmall.bean.SearchRecommandBean;
import com.song.honestshoppingmall.bean.SerchCardBean;
import com.song.honestshoppingmall.bean.SerchResultBean;
import com.song.honestshoppingmall.bean.ShopCategoryBean;
import com.song.honestshoppingmall.bean.UpDateCartBean;
import com.song.honestshoppingmall.bean.Userbean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by lizhenquan on 2017/1/8.
 */

public interface APIRetrofit {
    /**
     * 获取主页数据
     *
     * @return
     */
    @GET("home")
    Call<HomeMsgBean> getHomeMsg();

    /**
     * 搜索
     *
     * @param params
     * @return
     */
    //?page=0&pageNum=10&orderby=saleDown&keyword=奶粉
    @GET("search")
    Call<SerchResultBean> search(@QueryMap Map<String, String> params);

    /**
     * 登录时获取的数据
     *
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Call<LoginResultBean> login(@Field("username") String username, @Field("password") String password);

    /**
     * 用Map的方式登录
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Call<LoginResultBean> loginByFieldMap(@FieldMap Map<String, String> params);


    //Header参数传递  获取用户数据
    @GET("userinfo")
    Call<Userbean> getUserInfo(@Header("userid") String value);


    /**
     * 添加购物车
     *
     * @param params
     * @return
     */
    @GET("addCart")
    Call<AddCartBean> getAddCartBean(@QueryMap Map<String, String> params);

    /**
     * 查询购物车
     *
     * @param userId
     * @return
     */
    @GET("selectCart")
    Call<SerchCardBean> getSerchCartBean(@Query("userId") String userId);

    /**
     * 更新购物车
     *
     * @return
     */
    @GET("updateCart")
    Call<UpDateCartBean> getUpdateCart(@QueryMap Map<String, String> params);

    /**
     * 删除购物车
     * @param params
     * @return
     */
    @GET("deleteCart")
    Call<DeleteCartBean> deleteCart(@QueryMap Map<String,String> params);

    /**
     * 订单列表操作
     *
     * @param params
     * @param value
     * @return
     */
    @GET("orderlist")
    Call<MyOrderBean> getMyOrderBean(@QueryMap Map<String, String> params, @Header("userid") String value);


    @GET("orderdetail")
    Call<OrderDetailBean> getOrderDetailBean(@Query("orderId") String orderId, @Header("userid") String value);

    @FormUrlEncoded
    @POST("ordercancel")
    Call<LoginResultBean> cancelOrder(@Field("orderId") String orderId, @Header("userid") String value);

    @FormUrlEncoded
    @POST("checkout")
    Call<CheckOutBean> getCheckOutBean(@Field("sku") String sku, @Header("userid") String userid);

    /**
     * 获取商品分类数据
     */
    @GET("category")
    Call<ShopCategoryBean> getCategoryMsg();

    /**
     * 获取筛选商品列表
     * @param params
     * @return
     */
    @GET("productlist")
    Call<FilterProductListBean> getFilterProductList(@QueryMap Map<String, String> params);

    /**
     * 获取商品详情
     */
    @GET("product")
    Call<GoodsBean> getProductData(@Query("pId") int pId);

    /***
     * 搜索推荐
     *
     * @return
     */
    @GET("search/recommend")
    Call<SearchRecommandBean> getSearchRecommand();

    @GET("search")
    Call<SearchDetailBean> getSearchDetail(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("ordersumbit")
    Call<OrderSubmitBean> getOrderSubmitBean(@FieldMap Map<String, String> params, @Header("userid") String userid);

    @FormUrlEncoded
    @POST("register")
    Call<RegisterBean> sendRegister(@FieldMap Map<String, String> registerMap);

    @GET("limitbuy")
    Call<ScareBuyBean> getScareBuy(@QueryMap Map<String,String> params);

    @POST("logout")
    Call<LogoutBean> logOut(@Header("userid") String userid);

    @GET("topic")
    Call<PrommotionBean> getPrommotionBean(@QueryMap Map<String, String> params);

    @GET("newproduct")
    Call<DesenoBean> getDesenoBean(@QueryMap Map<String, String> params);

    @GET("help")
    Call<HelpBean> getHelpBean();

    @GET("helpDetail")
    Call<HelpDetailBean> getHelpDetailBean(@Query("id") String id);

    @GET("addresslist")
    Call<AddressBean> getAddressBean(@Header("userid") String userid);

    @GET("hotproduct")
    Call<NewonBean> getNewonBean(@QueryMap Map<String, String> params);

    @GET("brand")
    Call<RecommendBean> getRecommendBean(@QueryMap Map<String, String> params);

    @GET("product/comment")
    Call<ProductCommentBean> getProductCommentBean(@Query("pId") String pId, @Query("page") String page, @Query("pageNum") String pageNum);



}
