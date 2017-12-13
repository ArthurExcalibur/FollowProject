package com.excalibur.followproject.http;

import com.excalibur.followproject.bean.ZhangjieEntitiys;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lieniu on 2017/12/6.
 */

public interface ZhangjieApi {

    @GET("findbook/zhangjie?action=index")
    Observable<ZhangjieEntitiys> getZhangjieLiebiao(@Query("url") String url);

    @GET("findbook/zhangjie?action=zhangjie")
    Observable<ResponseBody> getZhangjieNeirong(@Query("url") String url);

}
