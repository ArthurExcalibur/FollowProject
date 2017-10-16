package com.excalibur.followproject.fanye;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.excalibur.followproject.R;
import com.excalibur.followproject.fanye.flip.FlipViewController;

public class FlipActivity extends AppCompatActivity {

    View back;
//    View background;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip);
        view = findViewById(R.id.text);
//        back = (LinearLayout) findViewById(R.id.scroll);
        back = findViewById(R.id.back);

//        ViewGroup.LayoutParams params = back.getLayoutParams();
//        params.width = getWindowManager().getDefaultDisplay().getWidth() * 2;
//        params.height = getWindowManager().getDefaultDisplay().getHeight();
//        back.setLayoutParams(params);
//        back.scrollTo(getWindowManager().getDefaultDisplay().getWidth(),0);

        FlipViewController flipView = new FlipViewController(this);
    }

    private View view;
    private void flip(){
//        float rotation = back.getRotationY();
//        ObjectAnimator animator = ObjectAnimator.ofFloat(back,"rotationY",rotation,90);
//        animator.setDuration(500);
//
//        animator.setInterpolator(new LinearInterpolator());
//        animator.start();

//        back.setPivotX(0);
//        back.setPivotY(getWindowManager().getDefaultDisplay().getHeight() / 2);
        back.setRotationY(-50);

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_flip);
//        back.startAnimation(animation);//开始动画
//
//        final float centerY = getWindowManager().getDefaultDisplay().getHeight() / 2.0f;
//
//        final Rotate3dAnimation rotation =
//                new Rotate3dAnimation(0, -90, 0, centerY, 0, false);
//        rotation.setDuration(5000);
//        rotation.setFillAfter(true);
//        rotation.setInterpolator(new AccelerateInterpolator());
//        //rotation.setAnimationListener(new DisplayNextView(position));
//        back.startAnimation(rotation);
    }

    private void getScreenCapture(){
        view.setDrawingCacheEnabled(true);
        Bitmap tBitmap = view.getDrawingCache();
        // 拷贝图片，否则在setDrawingCacheEnabled(false)以后该图片会被释放掉
        tBitmap = Bitmap.createBitmap(tBitmap);
        view.setDrawingCacheEnabled(false);
        if (tBitmap != null) {
            // mImageResult.setImageBitmap(tBitmap);
            Toast.makeText(getApplicationContext(), "获取成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_SHORT).show();
        }
        back.setBackground(new BitmapDrawable(tBitmap));
        view.setBackgroundColor(Color.BLUE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int w = 90 / getWindowManager().getDefaultDisplay().getWidth();
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            getScreenCapture();
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            float rY = (getWindowManager().getDefaultDisplay().getWidth() - event.getX()) * w;
            back.setRotationY(rY);
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            flip();
        }
        return true;
    }
}
