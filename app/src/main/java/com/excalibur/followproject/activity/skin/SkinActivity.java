package com.excalibur.followproject.activity.skin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.excalibur.followproject.R;

import solid.ren.skinlibrary.SkinConfig;
import solid.ren.skinlibrary.SkinLoaderListener;
import solid.ren.skinlibrary.base.SkinBaseActivity;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * 自动换肤框架
 */

public class SkinActivity extends SkinBaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
    }

    public void change(View view){
        if(SkinManager.getInstance().isNightMode()){
            SkinManager.getInstance().restoreDefaultTheme();
        }else{
            Log.e("TestForCase","萨达所大所");
            SkinManager.getInstance().NightMode();
        }
//        SkinManager.getInstance().loadSkin("Your skin file name in assets(eg:theme.skin)",
//                new SkinLoaderListener() {
//                    @Override
//                    public void onStart() {
//                        Toast.makeText(getApplicationContext(), "正在切换中", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        Toast.makeText(getApplicationContext(), "切换成功", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailed(String errMsg) {
//                        Toast.makeText(getApplicationContext(), "切换失败", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onProgress(int progress) {
//
//                    }
//                }
//        );
    }
}
