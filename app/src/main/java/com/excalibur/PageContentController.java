package com.excalibur;

import android.util.Log;

import java.util.List;

public class PageContentController {

    public int startPageNumber;
    public int currentIndex;

    public List<String> formerList;
    public List<String> list;
    public List<String> nextList;

    public String nonContentStatus;

    private static PageContentController controller;
    private PageContentController(){

    }
    public static PageContentController newInstance(List<String> formerList,List<String> list
            ,List<String> nextList, int startPageNumber,int currentIndex){
        if(null == controller){
            synchronized (PageContentController.class){
                if(null == controller){
                    controller = new PageContentController();
                    init(formerList,list,nextList,startPageNumber,currentIndex);
                }
            }
        }
        return controller;
    }

    private static void init(List<String> formerList,List<String> list
            ,List<String> nextList, int startPageNumber,int currentIndex){
        controller.formerList = formerList;
        controller.list = list;
        controller.nextList = nextList;
        controller.startPageNumber = startPageNumber;
        controller.currentIndex = currentIndex;
    }

    public String getCurrentContent(){
        return list.get(startPageNumber);
    }

    public String getContentByIndex(int index){
        if(index > currentIndex){
            if(startPageNumber < list.size() - 1){
                startPageNumber++;
            }else{
                if(nextList.isEmpty()){
                    return nonContentStatus;
                }
                formerList.clear();
                formerList.addAll(list);
                list.clear();
                list.addAll(nextList);
                if(null != listener)
                    listener.onContentParseOver(true);
                startPageNumber = 0;
            }
        }else if(index == currentIndex){
            return getCurrentContent();
        }else{
            if(startPageNumber > 1){
                startPageNumber--;
            }else{
                if(formerList.isEmpty()){
                    return nonContentStatus;
                }
                nextList.clear();
                nextList.addAll(list);
                list.clear();
                list.addAll(formerList);
                if(null != listener)
                    listener.onContentParseOver(false);
                startPageNumber = list.size() - 1;
            }
        }
        currentIndex = index;
        Log.e("TestForCase",index + "");
        return list.get(startPageNumber);
    }

    private OnContentParseOverListener listener;
    public interface OnContentParseOverListener{
        void onContentParseOver(boolean isNext);
    }
    public void setOnContentParseOverListener(OnContentParseOverListener l){
        listener = l;
    }
}
