
package com.excalibur.followproject.view.bookeffect;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;

public class MainActivity extends Activity {
    MagicBookView mBookView;
    Button mButton1;
    Button mButton2;
    Button mButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //mBookView = (MagicBookView)findViewById(R.id.bookview);
        mButton1 = new Button(this);
        mButton2 = new Button(this);
        mButton3 = new Button(this);
        
        mButton1.setTextSize(200);
        mButton2.setTextSize(200);
        mButton3.setTextSize(200);

        PageContainer.IPageContainer pre = new PageContainer.IPageContainer(){
            @Override
            public void onInit(int page, PageContainer container) {
                container.setBackgroundColor(Color.RED);
                mButton1.setText(""+page);
                container.setContent(mButton1, new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,Gravity.CENTER));
            }
            
            @Override
            public void onTurnReload(boolean isTurnBack, int currentPage, int needReloadPage,
                    PageContainer container) {
                mButton1.setText(""+needReloadPage);
            }

            @Override
            public void onSetPage(int page, PageContainer container) {

            }
            
        };

        PageContainer.IPageContainer cur = new PageContainer.IPageContainer(){
            @Override
            public void onInit(int page, PageContainer container) {
                mButton2.setText(""+page);
                container.setBackgroundColor(Color.RED);
                container.setContent(mButton2, new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,Gravity.CENTER));
            }

            @Override
            public void onTurnReload(boolean isTurnBack, int currentPage, int needReloadPage,
                    PageContainer container) {
                mButton2.setText(""+needReloadPage);
            }

            @Override
            public void onSetPage(int page, PageContainer container) {

            }
            
        };

        PageContainer.IPageContainer next = new PageContainer.IPageContainer(){

            @Override
            public void onInit(int page, PageContainer container) {
                mButton3.setText(""+page);
                container.setBackgroundColor(Color.RED);
                container.setContent(mButton3, new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,Gravity.CENTER));
            }

            @Override
            public void onTurnReload(boolean isTurnBack, int currentPage, int needReloadPage,
                    PageContainer container) {
                mButton3.setText(""+needReloadPage);
            }

            @Override
            public void onSetPage(int page, PageContainer container) {

            }
            
        };
        
        mBookView.initBookView(50, 0, pre, cur, next);
    }
}
