package com.excalibur.followproject.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class HttpInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
//        YongHuBean.Data yongHuBeanData = SharedPrefrencesUtils.getYongHu(MyApplication.mContext);

//        String accountToken = "";
//        String uid = "";
//        if (yongHuBeanData != null){
//            accountToken = yongHuBeanData.getAccount_token();
//            //Log.e("honghei1002",accountToken);
//            uid = yongHuBeanData.getUid();
//        }
        try {
            Request.Builder builder = chain.request().newBuilder();
            Request requst = builder
//                    .addHeader("appid", "1400029460")
//                    .addHeader("token", "36b5f78ef8306dda5e2ce0f514247bc9")
//                    .addHeader("Account", TextUtils.isEmpty(uid)?"":uid)
//                    .addHeader("account_token", TextUtils.isEmpty(accountToken)?"":accountToken)
//                    .addHeader("shebei", "webl")
                    .build();

            return chain.proceed(requst);
        } catch (Exception e) {
            return null;
        }
    }
}