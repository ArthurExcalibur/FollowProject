package com.excalibur.followproject.util;

import android.text.TextUtils;
import android.util.Log;

public class LogUtil {

    private static final boolean DEBUG = true;
    private static final String TAG = "TestForCase";

    public static void e(String msg){
        if(TextUtils.isEmpty(msg))
            return;
        if(DEBUG)
            Log.e(TAG,msg);
    }
}
