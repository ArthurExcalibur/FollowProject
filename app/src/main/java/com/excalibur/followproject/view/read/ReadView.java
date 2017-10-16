package com.excalibur.followproject.view.read;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class ReadView extends RelativeLayout {

    public static final int MODE_NONE = 0;
    public static final int MODE_UP = 1;
    public static final int MODE_COVER = 2;
    public static final int MODE_FOLD = 3;
    private int mode;

    public ReadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setChangePageMode(int mode){
        this.mode = mode;
    }

}
