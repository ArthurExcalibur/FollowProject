package com.excalibur.followproject;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lieniu on 2017/9/15.
 */

public class NovelParser {

    private String title;
    private String content;
    private List<String> pageList;
    private int currentPageNumber = 0;
    private TextView targetTextView;
    private int titleViewHeight;
    private StringBuilder contentBuilder;

    public NovelParser(TextView textView,int titleViewHeight) {
        targetTextView = textView;
        this.titleViewHeight = titleViewHeight;
    }

    //根据页数计算当前页能容纳的最大行数
    private int calContentCount(int pageNumber,int viewHeight,int titleHeight){
        int normalHeight = viewHeight;
        if(pageNumber == 0){
            normalHeight -= titleHeight;
        }
        int firstH = getLineHeight(0);
        int otherH = getLineHeight(1);
        return (normalHeight - firstH) / otherH + 1;
    }

    //计算每一行在屏幕上所占据的高度
    private int getLineHeight(int line){
        Rect rect = new Rect();
        targetTextView.getLineBounds(line,rect);
        return rect.bottom - rect.top;
    }

    private void autoSplitContent(){
        targetTextView.setText(content);
        if(pageList == null)
            pageList = new ArrayList<>();
        pageList.clear();
        if(contentBuilder == null)
            contentBuilder = new StringBuilder();
        contentBuilder.delete(0,contentBuilder.length());

        Paint paint = targetTextView.getPaint();
        int width = targetTextView.getMeasuredWidth() - targetTextView.getPaddingLeft() - targetTextView.getPaddingRight();
        int height = targetTextView.getMeasuredHeight() - targetTextView.getPaddingTop() - targetTextView.getPaddingBottom();
        int firstCellCount = calContentCount(0,height,titleViewHeight);//表示第一页最多存储多少行
        int maxCellCount = calContentCount(1,height,titleViewHeight);//表示一页最多存储多少行
        int cellCount = 0;//表示当前StringBuilder中存储了多少行
        int lineWidth;
        String[] pages = content.replaceAll("\r","").split("\n");
        for(String value : pages) {
            lineWidth = 0;
            value = clean(value);
            if (!TextUtils.isEmpty(value)) {
                if(value.equals(title)){
                    continue;
                }
                if(paint.measureText(value) <= width){
                    contentBuilder.append(value);
                    cellCount++;
                }else{
                    value = "    " + value;
                    for(int i = 0;i < value.length();i++){
                        char ch = value.charAt(i);
                        lineWidth += paint.measureText(String.valueOf(ch));
                        if(lineWidth <= width){
                            contentBuilder.append(ch);
                        }else{
                            i--;
                            lineWidth = 0;
                            contentBuilder.append("\n");
                            cellCount++;
                            if(cellCount == firstCellCount){//解析出了新的一页
                                pageList.add(contentBuilder.toString());
                                contentBuilder.delete(0,contentBuilder.length());
                                cellCount = 0;
                                firstCellCount = maxCellCount;
                            }
                        }
                    }
                }
                contentBuilder.append("\n");
                cellCount++;

                if(cellCount < maxCellCount - 1){
                    contentBuilder.append("\n");
                    cellCount ++;
                }else if(cellCount == maxCellCount - 1){
                    contentBuilder.append("\n");
                }else{
                    pageList.add(contentBuilder.toString());
                    contentBuilder.delete(0,contentBuilder.length());
                    cellCount = 0;
                    firstCellCount = maxCellCount;
                }
            }
        }
        if(!TextUtils.isEmpty(contentBuilder)){
            pageList.add(contentBuilder.toString());
        }
        targetTextView.setText(pageList.get(currentPageNumber));
        targetTextView.postInvalidate();
    }

    private String clean(String value){
        value = value.trim();
        return value;
    }

    //传入与设置的一律以sp为单位便于管理
    public void changeTextFont(float newTextSize){
        if(newTextSize < 12 || newTextSize > 30){
            return;
        }
        float pxSize = 21;
        targetTextView.getPaint().setTextSize(pxSize);
        setContent(content,title);
        currentPageNumber = 0;
        targetTextView.setText(pageList.get(currentPageNumber));
        targetTextView.postInvalidate();
    }

    /**
     * 设置单章内容并且自动切割
     * @param content 章节内容
     * @param title 章节标题
     */
    public void setContent(String content,String title){
        this.content = content;
        this.title = title;
        autoSplitContent();
    }

    public String getContent(){
        return pageList.get(currentPageNumber);
    }

    /**
     * 切换页面
     * @param isNext  是否切换上一页
     */
    public String changePage(boolean isNext){
        if(isNext){
            if(currentPageNumber == pageList.size() - 1){
                if(null != listener) {
                    listener.onContentOver(true);
                }
            }
            else{
                currentPageNumber++;
                return pageList.get(currentPageNumber);
            }
        }else{
            if(0 == currentPageNumber){
                if(null != listener) {
                    listener.onContentOver(false);
                }
            }else{
                currentPageNumber--;
                return pageList.get(currentPageNumber);
            }
        }
        return null;
    }

    private OnContentOverListener listener;
    public interface OnContentOverListener{
        void onContentOver(boolean isNext);
    }
    public void setOnContentOverListener(OnContentOverListener l){
        listener = l;
    }
}
