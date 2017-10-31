package com.excalibur.followproject.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.excalibur.followproject.R;
import com.excalibur.followproject.http.FileService;
import com.excalibur.followproject.util.CameraUtils;
import com.excalibur.followproject.util.PermissionUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;


public class UploadActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acyivity_upload);

        imageView = (ImageView) findViewById(R.id.image);
        textView = (TextView) findViewById(R.id.text);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoto();
            }
        });
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage("","");
            }
        });
    }

    /**
     * 获取图库地址
     */
    public void getPhoto() {
        isCammer = false;

        sdFlag = PermissionUtils.requestReadSDCardPermissions(this, MY_PERMISSIONS_REQUEST_READ_SDCARD);
        if (sdFlag) {
            new CameraUtils().FromPhoto(this, photoRequestCode);
        } else {
            Toast.makeText(this, "请打开访问SD卡权限", Toast.LENGTH_LONG).show();
        }

    }

    private final int photoRequestCode = 10;
    private File file;
    private final int cameraRequestCode = 11;

    /**
     * 获取相机地址
     */
    public void getCamera() {

        sdFlag = PermissionUtils.requestReadSDCardPermissions(this, MY_PERMISSIONS_REQUEST_READ_SDCARD);

        cmFlag = PermissionUtils.requestCamerPermissions(this, MY_PERMISSIONS_REQUEST_READ_CAMMER);
        isCammer = true;
        if (sdFlag && cmFlag) {
            file = new CameraUtils().FromCamera(this, cameraRequestCode);
        } else if (!sdFlag) {
            Toast.makeText(this, "请打开访问SD卡权限", Toast.LENGTH_LONG).show();
        } else if (!cmFlag) {
            Toast.makeText(this, "请打开相机权限", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CAMMER:
                if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (sdFlag) {
                        file = new CameraUtils().FromCamera(this, cameraRequestCode);
                    } else {
                        sdFlag = PermissionUtils.requestReadSDCardPermissions(this, MY_PERMISSIONS_REQUEST_READ_SDCARD);
                    }
                } else {
                    Toast.makeText(this, "请打开相机权限", Toast.LENGTH_LONG).show();
                }
                break;
            case MY_PERMISSIONS_REQUEST_READ_SDCARD:
                if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sdFlag = true;
                    if (cmFlag && isCammer) {
                        file = new CameraUtils().FromCamera(this, cameraRequestCode);
                    } else if (isCammer) {
                        cmFlag = PermissionUtils.requestCamerPermissions(this, MY_PERMISSIONS_REQUEST_READ_CAMMER);
                    } else {
                        new CameraUtils().FromPhoto(this, photoRequestCode);
                    }
                } else {
                    Toast.makeText(this, "请打开访问SD卡权限", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    public final static int MY_PERMISSIONS_REQUEST_READ_CAMMER = 11;
    public final static int MY_PERMISSIONS_REQUEST_READ_SDCARD = 111;
    private boolean sdFlag = false, cmFlag = false;
    private boolean isCammer = false;


    ArrayList<String> imagePaths = new ArrayList<>();

    //从相机还是相册返回的数据图片地址。
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 10:
                    String path = CameraUtils.UriToPath(UploadActivity.this, data);
//                    iv.setImageBitmap(ImageUtils.createThumbnail(CameraUtils.UriToPath(this, data),150,150));
//                    tiWenImagePickerAdapter.addImage(ImageUtils.createThumbnail(CameraUtils.UriToPath(getContext(), data),150,150));
                    imagePaths.add(path);
                    StringBuilder s = new StringBuilder();
                    for (String str : imagePaths) {
                        s.append(str).append("\n");
                    }
                    textView.setText(s.toString());
                    break;
                case 11:
                    String path1 = CameraUtils.FromCameraResult(data, UploadActivity.this, file);
                    imagePaths.add(path1);
//                    iv.setImageBitmap();
//                    tiWenImagePickerAdapter.addImage(ImageUtils.createThumbnail(CameraUtils.FromCameraResult(data,getContext(), file),150,150));

                    break;
            }
        }
    }

    private void uploadImage(String message, String phone) {
        Observable.from(imagePaths)
                .flatMap(new Func1<String, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(String s) {
                        return new FileService(UploadActivity.this).uploadFile(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onCompleted() {
                        //Log.i("Subscriber",new Gson().toJson(imageUrl).toString());
                        //upload(message, phone, new Gson().toJson(imageUrl));
                    }

                    @Override
                    public void onError(Throwable e) {
                        //tip.closeTip();
//                        toolbarBtn.setEnabled(true);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String value = responseBody.string();
                            Log.e("TestForCase",value);
//                            JSONObject object = new JSONObject(value);
//                            TuPianBean tuPianBean = AllUtils.parseJo2Tupianbean(object);
//                            imageUrl.add(tuPianBean);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
