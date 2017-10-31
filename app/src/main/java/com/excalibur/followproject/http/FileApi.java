package com.excalibur.followproject.http;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/2 0002.
 */

public interface FileApi {

    @POST("file/upload")
    @Multipart
    Observable<ResponseBody> upload(@Part MultipartBody.Part file);

    @POST("file/uploadmohu")
    @Multipart
    Observable<ResponseBody> uploadMohu(@Part MultipartBody.Part file);

}
