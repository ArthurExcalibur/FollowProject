package com.excalibur.followproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.excalibur.followproject.util.CameraUtils;
import com.excalibur.followproject.util.PermissionUtils;
import com.excalibur.followproject.util.TipUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * @author 李军辉
 * @time 2017/5/8 0008  上午 10:05
 * @desc 问题反馈界面
 */

public class WenTiFanKuiActivity extends AppCompatActivity {
    Unbinder unbinder;
//    @BindView(R.id.wentifankui_yijian_et)
//    EditText wentifankuiYijianEt;
//    @BindView(R.id.wentifankui_lianxifangshi_et)
//    EditText wentifankuiLianxifangshiEt;
//    @BindView(R.id.wentifankui_imagepicker_gv)
//    GridView wentifankuiImagepickerGv;
//    @BindView(R.id.my_titleview)
//    TitleView myTitleview;

    private final int photoRequestCode = 10;
    private final int cameraRequestCode = 11;

    ImagePickerAdapter imagePickerAdapter;
    private File file;
    private TipUtils tip;

//    @Override
//    protected void initData() {
//        initImagePickerAdapter();
//    }
//
//    @Override
//    protected void setContentView() {
//        setContentView(R.layout.activity_wentifankui);
//        unbinder = ButterKnife.bind(this);
//        tip = new TipUtils(this);
//    }
//
//    @Override
//    protected void initView() {
//        myTitleview.setViewOnClick(new TitleView.ViewOnClick() {
//            @Override
//            public void viewLeftWoOnClick(View view) {
//                finish();
//            }
//
//            @Override
//            public void viewSousuOnClick(View view) {
//                //提交操作
//                collectInfoAndDoPost();
//            }
//
//            @Override
//            public void viewFabuOnClick(View view) {
//
//            }
//        });
//    }

    //初始化图片选择器adapter
    private void initImagePickerAdapter() {
//        ArrayList<Object> datas = new ArrayList<>();
//        datas.add(getResources().getDrawable(R.mipmap.imagepicker));
//
//
//        imagePickerAdapter = new ImagePickerAdapter(datas, this);
//        wentifankuiImagepickerGv.setAdapter(imagePickerAdapter);
//        wentifankuiImagepickerGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position == imagePickerAdapter.getCount() - 1) {
//                    if (imagePaths.size() < 3) {
//                        showDialog(parent, view);
//                    } else {
//                        Toast.makeText(WenTiFanKuiActivity.this, "最多选择3张图片", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            }
//        });
    }


    /**
     * 弹出对话框，进行选择是通过相册还是通过相机进行获取图片
     */
    private void showDialog(View parent, View view) {
//        ImagePickerDialog imagePickerDialog = new ImagePickerDialog(this, R.style.dialog);
//        imagePickerDialog.setListener(new ImagePickerDialog.OnItemClickListener() {
//            @Override
//            public void onGoPhotoClick() {
//                getPhoto();
//            }
//
//            @Override
//            public void onGoCameraClick() {
//                getCamera();
//            }
//        });
//        imagePickerDialog.show();
    }

    /**
     * 获取图库地址
     */
    public void getPhoto() {
//        sdFlag = PermissionUtils.requestReadSDCardPermissions(MY_PERMISSIONS_REQUEST_READ_SDCARD);
//        if (sdFlag) {
//            new CameraUtils().FromPhoto(this, photoRequestCode);
//        } else {
//            Toast.makeText(this, "请打开访问SD卡权限", Toast.LENGTH_LONG).show();
//        }


        sdFlag = PermissionUtils.requestReadSDCardPermissions(this, MY_PERMISSIONS_REQUEST_READ_SDCARD);
        cmFlag = PermissionUtils.requestCamerPermissions(this, MY_PERMISSIONS_REQUEST_READ_CAMMER);
        isCammer = false;
        if (sdFlag && cmFlag) {
            new CameraUtils().FromPhoto(this, photoRequestCode, 3 - imagePaths.size());
        } else if (!sdFlag) {
            Toast.makeText(this, "请打开访问SD卡权限", Toast.LENGTH_LONG).show();
        } else if (!cmFlag) {
            Toast.makeText(this, "请打开相机权限", Toast.LENGTH_LONG).show();
        }

    }

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
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (sdFlag) {
                        file = new CameraUtils().FromCamera(this, cameraRequestCode);
                    }
                } else {
                    Toast.makeText(this, "请打开相机权限", Toast.LENGTH_LONG).show();
                }
                break;
            case MY_PERMISSIONS_REQUEST_READ_SDCARD:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (cmFlag && isCammer) {
                        file = new CameraUtils().FromCamera(this, cameraRequestCode);
                    } else {
                        new CameraUtils().FromPhoto(this, photoRequestCode, 3 - imagePaths.size());
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
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case photoRequestCode:
//                    ArrayList<String> mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
//                    boolean isAll = data.getBooleanExtra(MultiImageSelectorActivity.EXTRA_ALL_SIZE, false);
//                    if (mSelectPath != null && mSelectPath.size() > 0) {
//                        if (isAll) {
//                            imagePickerAdapter.addImageUrls(mSelectPath);
//                            imagePaths.addAll(mSelectPath);
//                        } else {
//                            Luban.with(this).load(mSelectPath).ignoreBy(500).setCompressListener(new OnCompressListener() {
//                                @Override
//                                public void onStart() {
//                                }
//
//                                @Override
//                                public void onSuccess(File file) {
//                                    imagePaths.add(file.getAbsolutePath());
//                                    imagePickerAdapter.addImageUrl(file.getAbsolutePath());
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//                                }
//                            }).launch();    //启动压缩
//                        }
//                    }
////					String path = CameraUtils.UriToPath(WenTiFanKuiActivity.this, data);
////                    iv.setImageBitmap(ImageUtils.createThumbnail(CameraUtils.UriToPath(this, data),150,150));
////                    tiWenImagePickerAdapter.addImage(ImageUtils.createThumbnail(CameraUtils.UriToPath(getContext(), data),150,150));
////					imagePaths.add(path);
//
////					imagePickerAdapter.addImageUrl(path);
////					BaseOnChildListview.calGridViewWidthAndHeigh(4, wentifankuiImagepickerGv);
//                    break;
//                case cameraRequestCode:
//                    if (file == null) return;
//                    Luban.with(this).load(file).ignoreBy(500).setCompressListener(new OnCompressListener() {
//                        @Override
//                        public void onStart() {
//                        }
//
//                        @Override
//                        public void onSuccess(File file) {
//                            imagePickerAdapter.addImageUrl(file.getAbsolutePath());
//                            imagePaths.add(file.getAbsolutePath());
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            imagePickerAdapter.addImageUrl(file.getAbsolutePath());
//                            imagePaths.add(file.getAbsolutePath());
//                        }
//                    }).launch();    //启动压缩
////					String path1 = CameraUtils.FromCameraResult(data, WenTiFanKuiActivity.this, file);
////					imagePaths.add(path1);
////					imagePickerAdapter.addImageUrl(path1);
////					BaseOnChildListview.calGridViewWidthAndHeigh(4, wentifankuiImagepickerGv);
//
////                    iv.setImageBitmap();
////                    tiWenImagePickerAdapter.addImage(ImageUtils.createThumbnail(CameraUtils.FromCameraResult(data,getContext(), file),150,150));
//
//                    break;
//            }
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 收集信息，并且将信息进行上传
     */
    private void collectInfoAndDoPost() {
//        String message = wentifankuiYijianEt.getText().toString();
//        String phone = wentifankuiLianxifangshiEt.getText().toString();
//        if (AllUtils.isNullToast(this, "请写下您的意见,谢谢", message)) {
//            return;
//        }
//        if (AllUtils.isNullToast(this, "请写下您的联系方式,谢谢", phone)) {
//            return;
//        }
//        tip.showTip();
//        if (imagePaths != null && imagePaths.size() > 0) {
//            uploadImage(message, phone);
//        } else {
//            upload(message, phone, null);
//        }
    }

    private void uploadImage(String message, String phone) {
//        List<TuPianBean> imageUrl = new ArrayList<>();
//        Observable.from(imagePaths)
//                .concatMap(new Func1<String, Observable<ResponseBody>>() {
//                    @Override
//                    public Observable<ResponseBody> call(String s) {
//                        return new FileApi().uploadFile(s);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<ResponseBody>() {
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        upload(message, phone, new Gson().toJson(imageUrl));
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        tip.closeTip();
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody responseBody) {
//                        try {
//                            String value = responseBody.string();
//                            JSONObject object = new JSONObject(value);
//                            TuPianBean tuPianBean = new TuPianBean();
//                            tuPianBean.setTupain(object.getString("image_url"));
//                            tuPianBean.setSuoluetu(object.getString("thumbnail_image_url"));
//                            tuPianBean.setHeight(object.optInt("height", 0));
//                            tuPianBean.setWidth(object.optInt("width", 0));
//                            imageUrl.add(tuPianBean);
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
    }

    /**
     * 上传
     *
     * @param message
     * @param phone
     * @param tupian
     */

    private void upload(String message, String phone, String tupian) {
//        CollegeWenTiFanKuiItemResult result = new CollegeWenTiFanKuiItemResult();
//        result.setAccount(SharedPrefrencesUtils.getParam(this, ChangLiang.USER.USER_ID, "") + "");
//        result.setLian_xi_fang_shi(phone);
//        result.setTu_pian(tupian);
//        result.setWen_zi_nei_rong(message);
//        Log.i("onghidjif", new Gson().toJson(result));
//        HttpUtils.getRetrofit().create(FanKuiApi.class)
//                .addFanKui(AllUtils.getRequestBody(new Gson().toJson(result)))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(r -> {
//                    try {
//                        String s = r.string();
//                        Log.i("onghidjif", s);
//                        CollegeWenTiFanKuiItemResult cwtfkir = new Gson().fromJson(s, CollegeWenTiFanKuiItemResult.class);
//                        //处理手机序列号验证重复登录
//                        AllUtils.parseError(cwtfkir, AllUtils.CLASS, WenTiFanKuiActivity.this);
//
//
//                        if (cwtfkir.isStatus()) {
//                            AllUtils.showToast(this, "反馈问题成功");
//                            finish();
//                        } else {
//                            AllUtils.showToast(this, cwtfkir.getError_info());
//                        }
//                    } catch (IOException e) {
//                        AllUtils.showToast(this, "反馈问题失败");
//
//                    }
//                    tip.closeTip();
//                }, e -> {
//                    AllUtils.showToast(this, "反馈问题失败");
//                    tip.closeTip();
//                })
//        ;


    }

}

