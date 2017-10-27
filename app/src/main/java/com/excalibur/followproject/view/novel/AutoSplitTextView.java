package com.excalibur.followproject.view.novel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.excalibur.followproject.R;

import java.util.ArrayList;
import java.util.List;

public class AutoSplitTextView extends FrameLayout{

    private TextView measureTextView;
    private AutoAdjustTextView autoAdjustTextView;
    private float titleViewHeight;
    private boolean splitContent = true;

    public AutoSplitTextView(Context context){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_auto_split_textview,this);
        init(context);
    }

    public AutoSplitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_auto_split_textview,this);
        init(context);
    }

    private void init(Context context){
        TypedArray a = context.obtainStyledAttributes(R.styleable.AutoSplitTextView);
        titleViewHeight = a.getInt(R.styleable.AutoSplitTextView_ast_titleHeight,0);
        a.recycle();

        FrameLayout container = (FrameLayout) getChildAt(0);
        measureTextView = (TextView) container.getChildAt(0);
        measureTextView.setVisibility(View.INVISIBLE);
        autoAdjustTextView = (AutoAdjustTextView) container.getChildAt(1);

        initSpaceString();

        pageList = new ArrayList<>();
        formerPageList = new ArrayList<>();
        nextPageList = new ArrayList<>();
    }

    //根据页数计算当前页能容纳的最大行数
    private int calContentCount(int pageNumber,int viewHeight,float titleHeight){
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
        measureTextView.getLineBounds(line,rect);
        return rect.bottom - rect.top;
    }

    private String title;
    private String content;
    private List<String> pageList;
    private List<String> formerPageList;
    private List<String> nextPageList;
    private int currentPageNumber = 0;

    private StringBuilder contentBuilder;
    private StringBuilder lineBuilder;
    private void autoSplitContent(boolean isNext,List<String> pageList){
        measureTextView.setText(content);
        pageList.clear();
        if(contentBuilder == null)
            contentBuilder = new StringBuilder();
        if(lineBuilder == null)
            lineBuilder = new StringBuilder();
        contentBuilder.delete(0,contentBuilder.length());
        lineBuilder.delete(0,lineBuilder.length());

        Paint paint = measureTextView.getPaint();
        int width = measureTextView.getMeasuredWidth() - measureTextView.getPaddingLeft() - measureTextView.getPaddingRight();
        int height = measureTextView.getMeasuredHeight() - measureTextView.getPaddingTop() - measureTextView.getPaddingBottom();
        int firstCellCount = calContentCount(0,height,titleViewHeight);//表示第一页最多存储多少行
        int maxCellCount = calContentCount(1,height,titleViewHeight);//表示一页最多存储多少行
        if(!splitContent){
            firstCellCount = Integer.MAX_VALUE;
        }
        int cellCount = 0;//表示当前StringBuilder中存储了多少行
        int lineWidth;
        String[] pages = content.replaceAll("\r","").split("\n");
        for(String value : pages) {
            lineBuilder.delete(0,lineBuilder.length());
            lineWidth = 0;
            value = clean(value);
            if (!TextUtils.isEmpty(value)) {
                if(value.equals(title)){
                    continue;
                }
                value = spaceString + value;
                if(paint.measureText(value) <= width){
                    contentBuilder.append(value);
                    //cellCount++;
                }else{
                    for(int i = 0;i < value.length();i++){
                        char ch = value.charAt(i);
                        lineWidth += paint.measureText(String.valueOf(ch));
                        if(lineWidth <= width){
                            lineBuilder.append(ch);
                            //contentBuilder.append(ch);
                        }else{
                            contentBuilder.append(fillLineBuilder(width));
                            contentBuilder.append("\n");
                            i--;
                            lineWidth = 0;
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
                if(lineBuilder.length() > 0)
                    contentBuilder.append(lineBuilder.toString());
                contentBuilder.append("\n");
                cellCount++;

                if(cellCount <= maxCellCount - 1){
                    contentBuilder.append("\n");
                    cellCount ++;
                }
//                else if(cellCount == maxCellCount - 1){
//                    contentBuilder.append("\n");
//                }
                else{
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
        //currentPageNumber = isNext ? 0 : pageList.size() - 1;
       // autoAdjustTextView.setContent(pageList.get(currentPageNumber));

//        Log.e("TestForCase","-----------------------------------");
//        for (int i = 0; i < pageList.size(); i++) {
//            Log.e("TestForCase",pageList.get(i));
//        }
//        Log.e("TestForCase","-----------------------------------");
    }

    private String fillLineBuilder(int mWidth){
        Paint paint = measureTextView.getPaint();
        String line = lineBuilder.toString();
        lineBuilder.delete(0,lineBuilder.length());
        float width = paint.measureText(line);
        int spaceNumber = (int) ((mWidth - width) / paint.measureText(space));
        for (int i = 0; i < spaceNumber; i++) {
            int random = (int) (Math.random() * line.length());
            line = line.substring(0,random) + " " + line.substring(random);
        }
        return line;
    }

    /**
     * 设置单章内容并且自动切割
     * @param content 章节内容
     * @param title 章节标题
     * @param isNext 是否切换下一章
     */
    public void setContent(String content,String title,boolean isNext,int type){
        this.content = content;
        this.title = title;
        if(type == 0)
            autoSplitContent(isNext,pageList);
        else if(type == -1)
            autoSplitContent(isNext,formerPageList);
        else if(type == 1)
            autoSplitContent(isNext,nextPageList);
    }

    /**
     * 为了统一化管理，所有行都要先去除行首的空白然后填充长度为两个中文字符长度的空格组
     */
    private static final String value = "段落";
    private static final String space = " ";
    private static String spaceString;
    private String clean(String value){
        value = value.trim();
        return value;
    }
    private void initSpaceString(){
        Paint paint = measureTextView.getPaint();
        float width = paint.measureText(value);
        int w = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            w += paint.measureText(space);
            builder.append(space);
            if(w >= width){
                break;
            }
        }
        spaceString = builder.toString();
    }

    //传入与设置的一律以sp为单位便于管理
    public void changeTextFont(boolean add){
        float newTextSize = measureTextView.getPaint().getTextSize();
        newTextSize = add ? newTextSize + 1 : newTextSize - 1;
        if(newTextSize < 12 || newTextSize > 30){
            return;
        }
        measureTextView.getPaint().setTextSize(newTextSize);
        autoAdjustTextView.getPaint().setTextSize(newTextSize);
        int formerPage = currentPageNumber;
        setContent(content,title,true,0);
        currentPageNumber = formerPage <= pageList.size() - 1 ? formerPage : pageList.size() - 1;
        autoAdjustTextView.setContent(pageList.get(currentPageNumber));
    }

    /**
     * 切换页面
     * @param isNext  是否切换下一页
     */
    public String changePage(boolean isNext){
        if(isNext){
            if(currentPageNumber == pageList.size() - 1){
                formerPageList.clear();
                formerPageList.addAll(pageList);
                pageList.clear();
                pageList.addAll(nextPageList);
                currentPageNumber = 0;
                if(null != listener) {
                    listener.onContentOver(true);
                }
                Log.e("TestForCase","下一章第一页...");
                return pageList.get(currentPageNumber);
            }
            else{
                Log.e("TestForCase","本章节下一页...");
                currentPageNumber++;
                autoAdjustTextView.setContent(pageList.get(currentPageNumber));
                return pageList.get(currentPageNumber);
            }
        }else{
            if(0 == currentPageNumber){
                nextPageList.clear();
                nextPageList.addAll(pageList);
                pageList.clear();
                pageList.addAll(formerPageList);
                currentPageNumber = pageList.size() - 1;
                if(null != listener) {
                    listener.onContentOver(false);
                }
                Log.e("TestForCase","上一章最后一页...");
                return pageList.get(currentPageNumber);
            }else{
                Log.e("TestForCase","本章上一页...");
                currentPageNumber--;
                autoAdjustTextView.setContent(pageList.get(currentPageNumber));
                return pageList.get(currentPageNumber);
            }
        }
    }

    public void setCurrentPageNumber(int number){
        if(number < 0){
            currentPageNumber = 0;
            return;
        }
        if(number >= pageList.size()){
            currentPageNumber = pageList.size() - 1;
            return;
        }
        currentPageNumber = number;
    }

    public String getContentByIndex(int index){
        if(index < 0 || index > pageList.size() - 1)
            return "无内容";
        return pageList.get(index);
    }

    public int getCurrentPageNumber(){
        return currentPageNumber;
    }

    public List<String> getPageList(){
        return pageList;
    }

    public void setContent(){
        if(TextUtils.isEmpty(getContent()))
            return;
        autoAdjustTextView.setText(pageList.get(currentPageNumber));
    }

    public String getContent(){
        if(pageList == null)
            return null;
        if(pageList.size() == 0)
            return null;
        if(currentPageNumber < 0 || currentPageNumber >= pageList.size())
            return null;
        return pageList.get(currentPageNumber);
    }

    /**
     * 设置是否分页
     * @param split
     */
    public void setSplitContent(boolean split){
        splitContent = split;
    }

    private OnContentOverListener listener;
    public interface OnContentOverListener{
        void onContentOver(boolean isNext);
    }
    public void setOnContentOverListener(OnContentOverListener l){
        listener = l;
    }
}
