package com.excalibur.followproject.activity.zhezhao;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.app.hubert.library.Controller;
import com.app.hubert.library.HighLight;
import com.app.hubert.library.NewbieGuide;
import com.app.hubert.library.OnGuideChangedListener;
import com.excalibur.followproject.R;

/**
 * Android引导蒙版（浮层）库
 * http://mp.weixin.qq.com/s/XGGivRNWE6ijlwe5kA2owg
 */

public class ZheZhaoActivity extends AppCompatActivity {

    public void lightView(View view){
        NewbieGuide.with(this)//传入activity
                .setLabel("guide1")//设置引导层标示，用于区分不同引导层，必传！否则报错
                .addHighLight(view, HighLight.Type.RECTANGLE)//添加需要高亮的view
                .setLayoutRes(R.layout.activity_anim)//自定义的提示layout，不要添加背景色，引导层背景色通过setBackgroundColor()设置
                .show();//显示引导层

        Controller controller = NewbieGuide.with(this)
                .setOnGuideChangedListener(new OnGuideChangedListener() {//设置监听
                    @Override
                    public void onShowed(Controller controller) {
                        //引导层显示
                    }

                    @Override
                    public void onRemoved(Controller controller) {
                        //引导层消失
                    }
                })
                .setBackgroundColor(Color.BLACK)//设置引导层背景色，建议有透明度，默认背景色为：0xb2000000
                .setEveryWhereCancelable(false)//设置点击任何区域消失，默认为true
                .setLayoutRes(R.layout.activity_anim, R.id.anim1)//自定义的提示layout,第二个可变参数为点击隐藏引导层view的id
                .alwaysShow(true)//是否每次都显示引导层，默认false
                .build();//构建引导层的控制器
        controller.resetLabel("guide1");
        controller.remove();//移除引导层
        controller.show();//显示引导层
    }
}
