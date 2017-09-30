package com.excalibur.followproject.activity.changlianjie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dhh.websocket.RxWebSocketUtil;
import com.dhh.websocket.WebSocketInfo;

import okhttp3.WebSocket;
import okio.ByteString;
import rx.Subscription;
import rx.functions.Action1;

public class ChangLianJieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        RxWebSocketUtil.getInstance().setShowLog(true);//显示日志
        RxWebSocketUtil.getInstance().getWebSocket("")
                .subscribe(new Action1<WebSocket>() {
                    @Override
                    public void call(WebSocket webSocket) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注意取消订阅,有多种方式,比如 rxlifecycle
//        Subscription mSubscription = RxWebSocketUtil.getInstance().getWebSocketInfo("")
//                .subscribe(new Action1<WebSocketInfo>() {
//                    @Override
//                    public void call(WebSocketInfo webSocketInfo) {
//                        WebSocket mWebSocket = webSocketInfo.getWebSocket();
//                        if (webSocketInfo.isOnOpen()) {
//                            Log.d("MainActivity", " on WebSocket open");
//                        } else {
//
//                            String string = webSocketInfo.getString();
//                            if (string != null) {
//                                Log.d("MainActivity", string);
//                                //textview.setText(Html.fromHtml(string));
//
//                            }
//
//                            ByteString byteString = webSocketInfo.getByteString();
//                            if (byteString != null) {
//                                Log.d("MainActivity", "webSocketInfo.getByteString():" + byteString);
//
//                            }
//                        }
//                    }
//                });
//        //注销
//        if (mSubscription != null) {
//            mSubscription.unsubscribe();
//        }
    }
}
