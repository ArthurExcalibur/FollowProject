package com.excalibur.followproject.application;

import org.litepal.LitePalApplication;
import solid.ren.skinlibrary.base.SkinBaseApplication;


public class SkinApplication extends SkinBaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePalApplication.initialize(getApplicationContext());
        //SkinConfig.enableGlobalSkinApply();
    }
}
