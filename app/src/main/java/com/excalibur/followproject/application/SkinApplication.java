package com.excalibur.followproject.application;

import org.litepal.LitePalApplication;

import cn.jpush.android.api.JPushInterface;
import solid.ren.skinlibrary.base.SkinBaseApplication;


public class SkinApplication extends SkinBaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePalApplication.initialize(getApplicationContext());
        //SkinConfig.enableGlobalSkinApply();

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }
}
