package com.excalibur.followproject.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    public static final int DEBUG_LEVEL = 1;
    public static final int RELEASE_LEVEL = 2;
    private static final int LEVEL = 1;

    public static void toast(Context context,String msg, int level){
        if(level > LEVEL)
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
