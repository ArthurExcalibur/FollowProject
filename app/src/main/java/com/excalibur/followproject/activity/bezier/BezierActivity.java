package com.excalibur.followproject.activity.bezier;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.excalibur.followproject.R;
import com.excalibur.followproject.view.novel.BezierView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BezierActivity extends AppCompatActivity {

    @BindView(R.id.text1)
    TextView textView;
    @BindView(R.id.text2)
    TextView textView1;
    @BindView(R.id.layout)
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_bezier);

        ButterKnife.bind(this);
    }

    private Bitmap getBitmap(TextView tv){
        tv.setDrawingCacheEnabled(true);
        tv.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(tv.getDrawingCache());
        tv.destroyDrawingCache();
        return bitmap;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        BezierView view = null;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
//                view = new BezierView(this);
//                view.setBitmaps(getBitmap(textView),getBitmap(textView1),getBitmap(textView1),getWindowManager().getDefaultDisplay().getWidth(),getWindowManager().getDefaultDisplay().getHeight());
                relativeLayout.addView(view);
                break;
            case MotionEvent.ACTION_UP:
                relativeLayout.removeView(view);
                break;
        }
        return super.onTouchEvent(event);
    }
}
