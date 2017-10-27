package com.excalibur.followproject;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;

public class BookPageFactory {

    private int mWidth;
    private int mHeight;

    public BookPageFactory(int w, int h) {
        mWidth = w;
        mHeight = h;
    }

    public void onDraw(Canvas c,Bitmap bitmap) {
        c.drawColor(Color.parseColor("#FFFFFFFF"));
        Rect rect = new Rect(0,0,mWidth,mHeight);
        c.drawBitmap(bitmap,rect,rect,null);
    }

}
