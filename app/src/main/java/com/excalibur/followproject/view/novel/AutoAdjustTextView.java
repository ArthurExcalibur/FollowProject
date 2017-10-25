package com.excalibur.followproject.view.novel;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

public class AutoAdjustTextView extends AppCompatTextView {
    private int mLineY = 0;//总行高
    private int mViewWidth;//TextView的总宽度
    private TextPaint paint;
    public AutoAdjustTextView(Context context) {
        super(context);
        init();
    }
    public AutoAdjustTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public AutoAdjustTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        mViewWidth = getMeasuredWidth();//获取textview的实际宽度
        String text = getText().toString();
        Layout layout = getLayout();
        int lineCount = layout.getLineCount();
        for (int i = 0; i < lineCount; i++) {//每行循环
            mLineY += getLineHeight();//写完一行以后，高度增加一行的高度
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i);
            String lineText = text.substring(lineStart, lineEnd);//获取TextView每行中的内容
            if (needScale(lineText)) {
                float width = StaticLayout.getDesiredWidth(text, lineStart, lineEnd, paint);
                drawScaleText(canvas, lineText, width);
            } else {
                canvas.drawText(lineText, 0, mLineY, paint);
            }
        }
    }

    public void setContent(String content){
        mLineY = 0;
        setText(content);
    }

    /**
     * 重绘此行.
     *
     * @param canvas    画布
     * @param lineText  该行所有的文字
     * @param lineWidth 该行每个文字的宽度的总和
     */
    private void drawScaleText(Canvas canvas, String lineText, float lineWidth) {
        float x = 0;
        //比如说一共有5个字，中间有4个间隔，
        //那就用整个TextView的宽度 - 5个字的宽度，
        //然后除以4，填补到这4个空隙中
        float interval = (mViewWidth - lineWidth) / (lineText.length() - 1);
        for (int i = 0; i < lineText.length(); i++) {
            String character = String.valueOf(lineText.charAt(i));
            float cw = StaticLayout.getDesiredWidth(character, paint);
            canvas.drawText(character, x, mLineY, paint);
            x += (cw + interval);
        }
    }

    /**
     * 判断需不需要缩放.
     * @param lineText 该行所有的文字
     */
    private boolean needScale(String lineText) {
        return Math.abs(mViewWidth - paint.measureText(lineText)) <= getTextSize();
    }

}
