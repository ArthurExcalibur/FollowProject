package com.excalibur.followproject.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.excalibur.followproject.CountNumberView;
import com.excalibur.followproject.R;

/**
 * Created by lieniu on 2017/9/12.
 */

public class AnimActivity extends AppCompatActivity {

    CountNumberView countNumberView;
    CountNumberView countNumberView1;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            anim();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_anim);

        view1 = findViewById(R.id.anim1);
        view2 = findViewById(R.id.anim2);
        view3 = findViewById(R.id.anim3);

        countNumberView = (CountNumberView) findViewById(R.id.numberView2);
        countNumberView1 = (CountNumberView) findViewById(R.id.numberView3);

        handler.sendEmptyMessage(1);
//        handler.sendEmptyMessageDelayed(1,1000);
    }


    private View view1;
    private View view2;
    private View view3;

    public void anim(){
        Log.e("TestForCase","Animing...");
        view1.setVisibility(View.VISIBLE);
        view2.setVisibility(View.VISIBLE);
        view3.setVisibility(View.VISIBLE);

        PropertyValuesHolder anim1A = PropertyValuesHolder.ofFloat("alpha", 0,1f);
        PropertyValuesHolder anim1T = PropertyValuesHolder.ofFloat("translationY",30,-10,5,0);
        ObjectAnimator anim1 = ObjectAnimator.ofPropertyValuesHolder(view1, anim1T);
        PropertyValuesHolder anim2A = PropertyValuesHolder.ofFloat("alpha", 0,1f);
        PropertyValuesHolder anim2T = PropertyValuesHolder.ofFloat("translationY",30,-10,5,0);
        ObjectAnimator anim2 = ObjectAnimator.ofPropertyValuesHolder(view2, anim2T);
        PropertyValuesHolder anim3A = PropertyValuesHolder.ofFloat("alpha", 0,1f);
        PropertyValuesHolder anim3T = PropertyValuesHolder.ofFloat("translationY",30,-10,5,0);
        ObjectAnimator anim3 = ObjectAnimator.ofPropertyValuesHolder(view3, anim3T);

        ValueAnimator a = countNumberView.start(50);
        ValueAnimator a1 = countNumberView1.start(88);

        AnimatorSet set = new AnimatorSet();
        set.play(anim1).with(anim2).with(anim3).with(a).with(a1);
        set.setDuration(1000);
        set.setInterpolator(new LinearInterpolator());
        set.start();

    }
}
