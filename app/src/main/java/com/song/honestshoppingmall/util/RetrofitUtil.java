package com.song.honestshoppingmall.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lizhenquan on 2017/1/8.
 */

public class RetrofitUtil {
    private static Retrofit retrofit;

    /**
     * @return retrofit 实例
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Urls.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    /**
     * @return 接口实例
     *
     */
    public static APIRetrofit getAPIRetrofitInstance() {
        return getRetrofitInstance().create(APIRetrofit.class);
    }


}
