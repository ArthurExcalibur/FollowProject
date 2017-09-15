package com.excalibur.followproject;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

/**
 * Created by lieniu on 2017/9/14.
 */

public class CountNumberView extends TextView {

    public static final int ANIM_TIME = 1500;

    public CountNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ValueAnimator start(int number){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,number);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setText(valueAnimator.getAnimatedValue() + "");
            }
        });
        return valueAnimator;
    }
}
