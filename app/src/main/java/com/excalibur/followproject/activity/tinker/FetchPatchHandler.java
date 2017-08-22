package com.excalibur.followproject.activity.tinker;


import android.os.Handler;
import android.os.Message;

import com.tinkerpatch.sdk.TinkerPatch;

public class FetchPatchHandler extends Handler {

    public static final long HOUR_INTERVAL = 3600 * 1000;

    private long checkInterval;

    public void fetchPatchWithInterval(int hour){
        //设置TinkerPatch的时间间隔
        TinkerPatch.with().setFetchPatchIntervalByHours(hour);
        checkInterval = hour * HOUR_INTERVAL;
        //立即访问检查是否有更新
        sendEmptyMessage(0);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        TinkerPatch.with().fetchPatchUpdate(false);
        //每隔一段时间访问后台，增加十分钟的buffer时间
        sendEmptyMessageDelayed(0,checkInterval + 10 * 60 * 1000);
    }
}
