package com.excalibur.followproject.http;

import com.excalibur.followproject.bean.BaseBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface BookApi {

    @GET("findbook/shucheng")
    Observable<BaseBean> getShuChengBooks();

}
