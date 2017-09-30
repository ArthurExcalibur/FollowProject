package com.excalibur.followproject.activity.touming;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.excalibur.followproject.R;

import org.zackratos.ultimatebar.UltimateBar;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 透明状态栏控件
 * 这些操作一般都是操作状态栏的，导航栏需要额外进行操作
 * http://mp.weixin.qq.com/s/MHAhvGLMLiFHK-FgH964sw
 */
public class TouMingZhuangTaiLanActivity extends AppCompatActivity {

    private boolean isShown = true;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touming);

        final UltimateBar bar = new UltimateBar(this);
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        textView = (TextView) findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShown)
                    bar.setImmersionBar();
                else
                    bar.setHintBar();
                isShown = !isShown;
                getScreenShot();
            }
        });

        //自定义颜色状态栏
        //bar.setColorBar(ContextCompat.getColor(this,R.color.colorAccent));

        //半透明状态栏
        //bar.setTransparentBar(Color.BLUE,50);

        //完全透明状态栏
        //bar.setImmersionBar();
        bar.setHintBar();
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(receiver,filter);
    }
    TimeChangeReceiver receiver = new TimeChangeReceiver();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private Bitmap getScreenShot(){
        //打开图像缓存
        linearLayout.setDrawingCacheEnabled(true);
        //需要调用measure和layout方法
        linearLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                , View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //发送位置和尺寸到view及其所有的子view
        linearLayout.layout(0, 0, linearLayout.getMeasuredWidth(),linearLayout.getMeasuredHeight());
        //获得可视化组件的截图
        Bitmap bitmap = linearLayout.getDrawingCache();
        return bitmap;
    }

    @SuppressWarnings("unused")
    private Bitmap takeScreenShot(){
        View view = getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        //Rect rect = new Rect();
        //getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //int statusBarHeight = rect.top;
       // System.out.println(statusBarHeight);

        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();

        //Bitmap bitmap2 = Bitmap.createBitmap(bitmap,0,statusBarHeight, width, height - statusBarHeight);
        //view.destroyDrawingCache();
        return bitmap;
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
    TextView textView;

    private class TimeChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            SimpleDateFormat format=new SimpleDateFormat("HH:mm");
            textView.setText(format.format(new Date()));
        }
    }
}
