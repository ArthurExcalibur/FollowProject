package com.excalibur.followproject.activity.fankui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.excalibur.followproject.R;
import com.excalibur.followproject.util.ToastUtil;
import com.joker.annotation.PermissionsCustomRationale;
import com.joker.annotation.PermissionsDenied;
import com.joker.annotation.PermissionsGranted;
import com.joker.annotation.PermissionsNonRationale;
import com.joker.annotation.PermissionsRationale;
import com.joker.annotation.PermissionsRequestSync;
import com.joker.api.Permissions4M;
import com.joker.api.wrapper.ListenerWrapper;
import com.joker.api.wrapper.Wrapper;

import static com.excalibur.followproject.activity.fankui.FanKuiActivity.CAMERA_CODE;
import static com.excalibur.followproject.activity.fankui.FanKuiActivity.GALLERY_CODE;

@PermissionsRequestSync(permission = {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,}, value = {CAMERA_CODE, GALLERY_CODE})
public class FanKuiActivity extends AppCompatActivity {

    public static final int CAMERA_CODE = 10;
    public static final int GALLERY_CODE = 11;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fankui);

        Button camera = (Button) findViewById(R.id.camera);
        Button gallery = (Button) findViewById(R.id.gallery);
        Button yijian = (Button) findViewById(R.id.yijian);

        // 拍照选取
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permissions4M
                        .get(FanKuiActivity.this)
                        .requestSync();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permissions4M.get(FanKuiActivity.this)
                        .requestPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .requestCodes(GALLERY_CODE)
                        .requestListener(new ListenerWrapper.PermissionRequestListener() {
                            @Override
                            public void permissionGranted(int code) {
                                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(i, 0x11);
                            }

                            @Override
                            public void permissionDenied(int code) {
                                Toast.makeText(FanKuiActivity.this, "请打开访问SD卡权限", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void permissionRationale(int code) {
                                Toast.makeText(FanKuiActivity.this, "请打开访问SD卡权限", Toast.LENGTH_LONG).show();
                            }
                        })
                        .requestPageType(Permissions4M.PageType.MANAGER_PAGE)
                        .requestPage(new Wrapper.PermissionPageListener() {
                            @Override
                            public void pageIntent(final Intent intent) {
                                new AlertDialog.Builder(FanKuiActivity.this)
                                        .setMessage("用户您好，我们需要您开启读取通讯录权限申请：\n请点击前往设置页面\n(in activity with" +
                                                " listener)")
                                        .setPositiveButton("前往设置页面", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();
                            }
                        })
                        .request();
            }
        });

        // 一键申请
        yijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permissions4M
                        .get(FanKuiActivity.this)
                        .requestSync();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Permissions4M.onRequestPermissionsResult(this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionsGranted(CAMERA_CODE)
    public void storageAndCallGranted() {
        Toast.makeText(FanKuiActivity.this, "相机权限以获取", Toast.LENGTH_LONG).show();
    }

    @PermissionsDenied(CAMERA_CODE)
    public void storageAndCallDenied() {
        Toast.makeText(FanKuiActivity.this, "请打开相机权限", Toast.LENGTH_LONG).show();
    }

    @PermissionsRationale(CAMERA_CODE)
    public void storageAndCallNonRationale() {
        Toast.makeText(FanKuiActivity.this, "请打开相机权限", Toast.LENGTH_LONG).show();
    }

    //====================================================================
    @PermissionsGranted(GALLERY_CODE)
    public void smsAndAudioGranted() {
        Toast.makeText(FanKuiActivity.this, "Gallery权限已获取", Toast.LENGTH_LONG).show();
    }

    @PermissionsDenied(GALLERY_CODE)
    public void smsAndAudioDenied() {
        Toast.makeText(FanKuiActivity.this, "请打开Gallery权限", Toast.LENGTH_LONG).show();
    }

    @PermissionsRationale(GALLERY_CODE)
    public void smsAndAudioRationale() {
        Toast.makeText(FanKuiActivity.this, "请打开Gallery权限", Toast.LENGTH_LONG).show();
    }

    private boolean isCamera;
    private boolean isGallery;
    @PermissionsGranted({CAMERA_CODE, GALLERY_CODE})
    public void syncGranted(int code) {
        switch (code) {
            case CAMERA_CODE:
                isCamera = true;
                break;
            case GALLERY_CODE:
                isGallery = false;
                break;
            default:
                break;
        }
        if(isCamera && isGallery){
            Toast.makeText(FanKuiActivity.this, "所有权限获取成功", Toast.LENGTH_LONG).show();
        }
    }

    @PermissionsDenied({CAMERA_CODE, GALLERY_CODE})
    public void syncDenied(int code) {
        switch (code) {
            case CAMERA_CODE:
                Toast.makeText(FanKuiActivity.this, "请打开相机权限", Toast.LENGTH_LONG).show();
                break;
            case GALLERY_CODE:
                Toast.makeText(FanKuiActivity.this, "请打开访问SD卡权限", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @PermissionsRationale({CAMERA_CODE, GALLERY_CODE})
    public void syncRationale(int code) {
        switch (code) {
            case CAMERA_CODE:
                Toast.makeText(FanKuiActivity.this, "请打开相机权限", Toast.LENGTH_LONG).show();
                break;
            case GALLERY_CODE:
                Toast.makeText(FanKuiActivity.this, "请打开访问SD卡权限", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @PermissionsNonRationale({CAMERA_CODE, GALLERY_CODE})
    public void storageAndCallRationale(int code, final Intent intent) {
        switch (code) {
            case CAMERA_CODE:
                new AlertDialog.Builder(FanKuiActivity.this)
                        .setMessage("用户您好，我们需要您开启相机权限\n请点击前往设置页面\n(in activity with listener)")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                break;
            case GALLERY_CODE:
                new AlertDialog.Builder(FanKuiActivity.this)
                        .setMessage("用户您好，我们需要您开启读取SD卡权限\n请点击前往设置页面\n(in activity with listener)")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }
}
