package com.excalibur.followproject.update;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.FileProvider;
import android.widget.Toast;


import java.io.File;

/**
 * 下载更新
 * Created by hch on 2017/9/6 0006.
 */

public class DownApkService extends Service {
    Context context = this;
    private BroadcastReceiver receiver;
    private long enqueue;
    private File file = new File(UpdateAgent.SDCARD_ROOT + File.separator + "update.apk");

    public DownApkService() {

    }

    /**
     * 检查版本更新
     */
//    private void QiangZhi() {
//        sdFlag = PermissionUtils.requestReadSDCardPermissions(this, MY_PERMISSIONS_REQUEST_READ_SDCARD);
//        if (sdFlag) {
//            new UpdateAgent(this).checkUpdate(true);
//        } else {
//            Toast.makeText(this, "请打开访问SD卡权限", Toast.LENGTH_LONG).show();
//        }
//        //在这里进行版本检查，如果版本相同
//    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle downloadBundle = intent.getBundleExtra("download");
        if (downloadBundle != null) {
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                        long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                        if (enqueue == reference) {
                            if (file.exists()) {
                                // 通过Intent安装APK文件
                                Intent installAPKIntent = new Intent(Intent.ACTION_VIEW);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判读版本是否在7.0以上
                                    Uri apkUri = FileProvider.getUriForFile(context, "com.css.wgb.wengubaoforandroid.fileprovider", file);//在AndroidManifest中的android:authorities值
                                    installAPKIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                                    installAPKIntent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                                } else {
                                    installAPKIntent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                                    installAPKIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                }
                                installAPKIntent.addCategory("android.intent.category.DEFAULT");
                                startActivity(installAPKIntent);
                            }

                        }
                        stopSelf();
                    }
                }
            };
            registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            String downloadUrl = downloadBundle.getString("downloadUrl");
            enqueue = downloadApk(downloadUrl);
        }
        Toast.makeText(this, "开始下载新版本，稍后会开始安装", Toast.LENGTH_SHORT).show();

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private long downloadApk(String url) {
        if (file.exists()) {
            file.delete();
        }
        Uri downloadUri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
//		request.setDestinationInExternalPublicDir("download", context.getString(R.string.app_name) + ".apk");
//		request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, context.getString(R.string.app_name) + ".apk");
        request.setDestinationUri(Uri.fromFile(file));
//		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);
        // 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
//        request.setTitle(context.getString(R.string.app_name));
        request.setDescription("正在下载最新版本...");
        DownloadManager mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        return mDownloadManager.enqueue(request);
    }
}