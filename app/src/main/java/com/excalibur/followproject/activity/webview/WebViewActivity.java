package com.excalibur.followproject.activity.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.excalibur.followproject.R;
import com.excalibur.followproject.activity.MainActivity;

/**
 * Created by lieniu on 2017/10/13.
 */

@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends AppCompatActivity {

    WebView rewenzhengwenZhengwenTv;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        rewenzhengwenZhengwenTv = (WebView) findViewById(R.id.webView);

        rewenzhengwenZhengwenTv.setWebChromeClient(new WebChromeClient());
        // 启用javascript
        rewenzhengwenZhengwenTv.getSettings().setJavaScriptEnabled(true);
        // 随便找了个带图片的网站
        rewenzhengwenZhengwenTv.loadUrl("http://blog.csdn.net/huangxiaoguo1/article/details/54574713");
        // 添加js交互接口类，并起别名 imagelistner
        rewenzhengwenZhengwenTv.addJavascriptInterface(new JsCallJavaObj() {
            @android.webkit.JavascriptInterface
            @Override
            public void showBigImg(String url) {
                Toast.makeText(WebViewActivity.this, "圖片路勁"+url, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(WebViewActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        },"jsCallJavaObj");
//        rewenzhengwenZhengwenTv.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");
        rewenzhengwenZhengwenTv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setWebImageClick(view);
            }
        });



//        rewenzhengwenZhengwenTv.setVisibility(View.VISIBLE);
//        rewenzhengwenZhengwenTv.getSettings().setJavaScriptEnabled(true);
//        rewenzhengwenZhengwenTv.loadUrl("http://blog.csdn.net/huangxiaoguo1/article/details/54574713");
//        rewenzhengwenZhengwenTv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        rewenzhengwenZhengwenTv.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");
//        rewenzhengwenZhengwenTv.setWebViewClient(new MyWebViewClient());
    }

    private  void setWebImageClick(WebView view) {
        String jsCode="javascript:(function(){" +
                "var imgs=document.getElementsByTagName(\"img\");" +
                "for(var i=0;i<imgs.length;i++){" +
                "imgs[i].onclick=function(){" +
                "window.jsCallJavaObj.showBigImg(this.src);" +
                "}}})()";
        rewenzhengwenZhengwenTv.loadUrl(jsCode);
    }

    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        rewenzhengwenZhengwenTv.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    /**
     * Js調用Java接口
     */
    private interface JsCallJavaObj{
        void showBigImg(String url);
    }

    // js通信接口
    public class JavascriptInterface {
        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void openImage(String img) {
            Intent intent = new Intent();
            Log.e("TestForCase",img);
            intent.putExtra("TUPIAN", img);
            intent.setClass(context, MainActivity.class);
            context.startActivity(intent);
        }
    }

    // 监听
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

}
