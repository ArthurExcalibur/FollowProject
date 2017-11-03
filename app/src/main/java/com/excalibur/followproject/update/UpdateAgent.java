package com.excalibur.followproject.update;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class UpdateAgent {
    private Activity mActivity;

    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    private Uri destinationUri;

    public static final String SDCARD_ROOT =
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/wengubao";

    public UpdateAgent(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void checkUpdate() {
        this.checkUpdate(false);
    }

    private boolean show1;

    public void checkUpdate(final boolean show1) {
        this.show1 = show1;
        mNotifyManager = (NotificationManager) mActivity.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(mActivity);
//        int versioncode = Integer.parseInt(AllUtils.getVersionCode(mActivity));
//        HttpUtils.getRetrofit().create(BaseApi.class)
//                .jianchaGengxin()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(b -> {
//                    try {
//                        String result = b.string();
//                        JSONObject jo = new JSONObject(result);
//                        if (jo.getBoolean("status")) {
//                            JSONObject joData = jo.getJSONObject("data");
//                            int banbenHao = joData.getInt("ban_ben_hao");
//                            String xiazaiDizhi = joData.getString("xia_zai_di_zhi");
//                            if (versioncode>=banbenHao) {
//                                if (!show1) {
//                                    Toast.makeText(mActivity, "已是最新版本", Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                if (!show1) {
//                                    showUpdateDialog(xiazaiDizhi, joData.optString("gen_xin").equals("true"));
//                                } else if (show1) {
//                                    upDate(xiazaiDizhi);
//                                }
//                            }
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }, e -> {
//                });

    }


    private void upDate(final String url) throws PackageManager.NameNotFoundException {
//        destinationUri = Uri.parse(SDCARD_ROOT + File.separator + FormatUtil.getFileNameFromUrl(url));
//
//        Intent downloadApkIntent = new Intent(mActivity, DownApkService.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("downloadUrl", url);
//        downloadApkIntent.putExtra("download", bundle);
//        mActivity.startService(downloadApkIntent);

    }



    private void showUpdateDialog(final String url, boolean falg) {
//        if (url != null) {
//            MaterialDialog.Builder builder = new MaterialDialog.Builder(mActivity).title("升级新版本");
//            builder.positiveText("立刻升级").negativeText("取消").content("升级新版本");
//            builder.callback(new MaterialDialog.ButtonCallback() {
//                @Override
//                public void onPositive(MaterialDialog dialog) {
//                    try {
//                        Intent downloadApkIntent = new Intent(mActivity, DownApkService.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("downloadUrl", url);
//                        downloadApkIntent.putExtra("download", bundle);
//                        mActivity.startService(downloadApkIntent);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).show();
//        }
    }

}

