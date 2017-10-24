package com.excalibur.followproject.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.excalibur.followproject.R;

public class FlipView extends View {

    private int degress = 45;
    private boolean reDraw = true;
    private int screenWidth = 720;
    private int screenHeight = 1080;
    private Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.renwen1);

    public FlipView(Context context) {
        super(context);
    }

    public FlipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setWidth(int width,int height){
        screenWidth = width;
        screenHeight = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(reDraw){
//            int h = (int) (Math.tan(degress) * screenWidth);
//            Path path = new Path();
//            path.lineTo(0,0);
//            path.lineTo(screenWidth,h);
//            path.lineTo(screenWidth,h + screenHeight);
//            path.lineTo(0,screenHeight);
//            path.lineTo(0,0);
//            canvas.clipPath(path);
//            canvas.drawBitmap(bitmap,0,0,null);

            Paint mPaint = new Paint();
            mPaint.setStyle(Paint.Style.FILL);

            Rect rect = new Rect(0, 0, screenWidth, screenHeight);
//
//            mPaint.setColor(Color.BLUE);
//            canvas.drawRect(rect, mPaint);

            canvas.skew(0,1.05f);
//            Matrix matrix = new Matrix();
//            float[] src = {0, 0, 0, screenHeight, screenWidth, screenHeight, screenWidth, 0};
//            int DX = 100;
//            float[] dst = {0, 0, 0, screenHeight, screenWidth, screenHeight - DX, screenWidth, 0 + DX};
//            matrix.setPolyToPoly(src, 0, dst, 0, 4);
//            canvas.drawBitmap(bitmap, matrix, null);


//            mPaint.setColor(Color.RED);
//            canvas.drawRect(rect, mPaint);
            canvas.drawBitmap(bitmap,rect,rect,null);
        }
        super.onDraw(canvas);
        canvas.restore();
    }


    private class Drawing_TASK implements Runnable{
        @Override
        public void run() {

        }
    }
}
