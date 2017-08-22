package com.excalibur.followproject.activity.touming;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.excalibur.followproject.R;

import org.zackratos.ultimatebar.UltimateBar;

/**
 * 透明状态栏控件
 * 这些操作一般都是操作状态栏的，导航栏需要额外进行操作
 * http://mp.weixin.qq.com/s/MHAhvGLMLiFHK-FgH964sw
 */
public class TouMingZhuangTaiLanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touming);

        UltimateBar bar = new UltimateBar(this);

        //自定义颜色状态栏
        //bar.setColorBar(ContextCompat.getColor(this,R.color.colorAccent));

        //半透明状态栏
        bar.setTransparentBar(Color.BLUE,50);

        //完全透明状态栏
        //bar.setImmersionBar();

    }

    //隐藏状态栏与导航栏
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
//            UltimateBar bar = new UltimateBar(this);
//            bar.setHintBar();
        }
    }
}
