package com.excalibur.followproject.http;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public class HttpUtils {

    public static final String BASE_URL = "http://116.196.91.63:8080/ZNovel/";

    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.interceptors().add(new HttpInterceptor());
        return client.build();
    }

    public static Retrofit getRetrofit() {
        return getRetrofitBase(BASE_URL);
    }

    public static Retrofit getRetrofitBase(String path) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getOkHttpClient())
                .baseUrl(path)
                .build();
    }

}
