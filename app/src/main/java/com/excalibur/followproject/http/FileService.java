package com.excalibur.followproject.http;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public class FileService {
    /**
     * 上传文件
     *
     * @param path
     * @return
     */
    public Observable<ResponseBody> uploadFile(String path) {
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentType(path)), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("files", file.getName(), requestFile);
        FileApi upLoadImageApi = HttpUtils.getRetrofit().create(FileApi.class);
        return upLoadImageApi.upload(body).subscribeOn(Schedulers.io());
    }

    /**
     * 上传模糊图片
     * @param path
     * @return
     */
    public Observable<ResponseBody> uploadMohuFile(String path) {
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentType(path)), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("files", file.getName(), requestFile);
        FileApi upLoadImageApi = HttpUtils.getRetrofit().create(FileApi.class);
        return upLoadImageApi.uploadMohu(body).subscribeOn(Schedulers.io());
    }

    /**
     * 上传模糊图片
     * @return
     */
    public Observable<ResponseBody> uploadMohuFile1(String path,File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentType(path)), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("files", file.getName(), requestFile);
        FileApi upLoadImageApi = HttpUtils.getRetrofit().create(FileApi.class);
        return upLoadImageApi.uploadMohu(body).subscribeOn(Schedulers.io());
    }



    //上传一张图片，经过模糊处理后才能，bei_jing_qian






    private String getContentType(String str) {
        if (str == null) {
            return null;
        }
        if (str.endsWith(".jpe") || str.endsWith(".JPE") || str.endsWith(".JPEG") || str.endsWith(
                ".jpeg") || str.endsWith(".jpg") || str.endsWith(".JPG")) {
            return "image/jpeg";
        }
        if (str.endsWith(".png") || str.endsWith(".PNG")) {
            return "image/png";
        }
        if (str.endsWith(".gif")) {
            return "image/gif";
        }
        return null;
    }


    private Activity mActivity;

    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    private Uri destinationUri;

    public static final String SDCARD_ROOT =
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/wgb";

    public FileService(Activity mActivity) {
        this.mActivity = mActivity;
    }


//    private void showUpdateDialog(final String url, String message, String tipMessage) {
//        if (url != null) {
//            try {
//                mBuilder.setContentTitle(mActivity.getString(R.string.app_name) + message)
//                        .setAutoCancel(true)
//                        .setSmallIcon(mActivity.getPackageManager()
//                                .getPackageInfo(mActivity.getPackageName(), 0).applicationInfo.icon);
//                destinationUri =
//                        Uri.parse(SDCARD_ROOT + File.separator + FormatUtil.getFileNameFromUrl(url));
//
//                FileDownloader.getImpl()
//                        .create(url)
//                        .setPath(SDCARD_ROOT + File.separator + FormatUtil.getFileNameFromUrl(url))
//                        .setListener(listener)
//                        .start();
//                Toast.makeText(mActivity, tipMessage, Toast.LENGTH_SHORT).show();
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    private FileDownloadListener listener = new FileDownloadListener() {
//
//        @Override
//        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//
//        }
//
//        @Override
//        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//            int progress = soFarBytes * 100 / totalBytes;
//            String content = String.format("正在下载:%1$d%%", progress);
//            mBuilder.setContentText(content).setProgress(totalBytes, soFarBytes, false);
//            PendingIntent pendingintent =
//                    PendingIntent.getActivity(mActivity, 0, new Intent(mActivity, MainActivity.class),
//                            PendingIntent.FLAG_CANCEL_CURRENT);
//            mBuilder.setContentIntent(pendingintent);
//            mNotifyManager.notify(0, mBuilder.build());
//        }
//
//        @Override
//        protected void blockComplete(BaseDownloadTask task) {
//
//        }
//
//        @Override
//        protected void completed(BaseDownloadTask task) {
//            // 下载完成
//            mBuilder.setContentText("下载成功（点击安装）").setProgress(0, 0, false);
//            mNotifyManager.notify(0, mBuilder.build());
//        }
//
//        @Override
//        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
//
//        }
//
//        @Override
//        protected void error(BaseDownloadTask task, Throwable e) {
//        }
//
//        @Override
//        protected void warn(BaseDownloadTask task) {
//
//        }
//    };


}