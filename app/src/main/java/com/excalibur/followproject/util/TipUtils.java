package com.excalibur.followproject.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by lieniu on 2017/12/13.
 */

public class TipUtils {

    private Context context;

    ProgressDialog p;

    public TipUtils(Context context) {
        this.context = context;
    }

    public void showTip() {
        showTip("正在提交", "请稍等...");
    }
    public void showTip(String title, String content) {
        p = ProgressDialog.show(context, title, content);
    }

    public void closeTip() {
        if (p != null) {
            p.dismiss();
        }
    }

}
