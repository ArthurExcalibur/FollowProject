package com.excalibur.followproject.view.read;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.excalibur.followproject.R;
import com.excalibur.followproject.fanye.PageWidget;
import com.excalibur.followproject.fanye.flip.FlipViewController;
import com.excalibur.followproject.view.novel.AutoAdjustTextView;
import com.excalibur.followproject.view.novel.AutoSplitTextView;

public class ReadView extends RelativeLayout {

    public static final int MODE_NONE = 0;
    public static final int MODE_UP = 1;
    public static final int MODE_COVER = 2;
    public static final int MODE_FOLD = 3;
    private int mode;
    private boolean pageEnable;

    private RelativeLayout noneLayout;
    private FlipViewController flipLayout;
    private RelativeLayout coverLayout;
    private ScrollView upLayout;

    private String contentString;

    private AutoSplitTextView autoSplitTextView;
    private AutoAdjustTextView autoAdjustTextView;

    public ReadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_layout,this);
        View rootView = getChildAt(0);
        initSimulationView(rootView);
    }

    private void initSimulationView(View rootView){
        simulationLayout = rootView.findViewById(R.id.simulationLayout);
        simulationAutoSplitTextView = (AutoSplitTextView) rootView.findViewById(R.id.simulationSplit);
        simulationLoading = (TextView) rootView.findViewById(R.id.simulationLoad);
        mPageWidget = (PageWidget) rootView.findViewById(R.id.simulationPageWidget);
        //initPageWidget();
    }

    public void setChangePageMode(int mode){
        this.mode = mode;
        initPageWidget();
    }

    private View simulationLayout;
    private PageWidget mPageWidget;
    private AutoSplitTextView simulationAutoSplitTextView;
    private TextView simulationLoading;
    private Bitmap mCurPageBitmap;
    private Bitmap mNextPageBitmap;
    private void initPageWidget(){
        mPageWidget.setScreen(getResources().getDisplayMetrics().widthPixels,getResources().getDisplayMetrics().heightPixels);
        mCurPageBitmap = Bitmap.createBitmap(getResources().getDisplayMetrics().widthPixels,getResources().getDisplayMetrics().heightPixels, Bitmap.Config.ARGB_8888);
        mNextPageBitmap = Bitmap.createBitmap(getResources().getDisplayMetrics().widthPixels,getResources().getDisplayMetrics().heightPixels, Bitmap.Config.ARGB_8888);
        mPageWidget.setBitmaps(mCurPageBitmap,mNextPageBitmap);
        mPageWidget.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if(!pageEnable)
                    return false;
                boolean ret;
                if (v == mPageWidget) {
                    if (e.getAction() == MotionEvent.ACTION_DOWN) {
                        mPageWidget.abortAnimation();
                        mPageWidget.calcCornerXY(e.getX(), e.getY());
                        if (mPageWidget.DragToRight()) {
                            simulationAutoSplitTextView.changePage(false);
                        } else {
                            simulationAutoSplitTextView.changePage(true);
                        }
                        mNextPageBitmap = getViewCapture(simulationLayout);
                        mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
                    }
                    ret = mPageWidget.doTouchEvent(e);
                    return ret;
                }
                return false;
            }
        });
        simulationAutoSplitTextView.setOnContentOverListener(new AutoSplitTextView.OnContentOverListener() {
            @Override
            public void onContentOver(boolean isNext) {
                simulationLoading.setVisibility(VISIBLE);
                pageEnable = false;
                if(null != listener)
                    listener.onNewContentLoaded();
            }
        });
    }
    private Bitmap getViewCapture(View view){
        view.setDrawingCacheEnabled(true);
        Bitmap tBitmap = view.getDrawingCache();
        // 拷贝图片，否则在setDrawingCacheEnabled(false)以后该图片会被释放掉
        tBitmap = Bitmap.createBitmap(tBitmap);
        view.setDrawingCacheEnabled(false);
        if (tBitmap != null) {
            return tBitmap;
        } else {
            return null;
        }
    }

    public void setContentString(String content,String title,boolean isNext){
        contentString = content;
        simulationAutoSplitTextView.setContent(contentString,title,isNext);
        pageEnable = true;
        simulationLoading.setVisibility(GONE);
        mCurPageBitmap = getViewCapture(simulationLayout);
        mPageWidget.setBitmaps(mCurPageBitmap,mNextPageBitmap);
        mPageWidget.invalidate();
    }

    private OnContentLoadListener listener;
    public interface OnContentLoadListener{
        void onNewContentLoaded();
    }
    public void setOnContentLoadListener(OnContentLoadListener l){
        listener = l;
    }
}
