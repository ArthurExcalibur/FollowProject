package com.excalibur.followproject.activity.quanxian;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.joker.annotation.PermissionsCustomRationale;
import com.joker.annotation.PermissionsDenied;
import com.joker.annotation.PermissionsGranted;
import com.joker.annotation.PermissionsNonRationale;
import com.joker.annotation.PermissionsRationale;
import com.joker.api.Permissions4M;
import com.joker.api.wrapper.Wrapper;

/**
 * 权限申请框架
 * http://mp.weixin.qq.com/s/23N-ouoalLc_IKsPSZEJgw
 */

public class QuanXianActivity extends AppCompatActivity {


    /**
     * 在 Activity 或 Fragment 中，需要手动添加 onRequestPermissionsResult方法以支持权限申请回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        Permissions4M.onRequestPermissionsResult(this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 申请单个权限的方法
     * 将会回调相应的 @PermissionsGranted
     * 、@PermissionsDenied
     * 、@PermissionsRationale/PermissionsCustomRationale
     * 、@PermissionsNonRationale所修饰的方法
     */
    public void requestSinglePermission(){
        Permissions4M.get(this)
                // 是否强制弹出权限申请对话框，建议设置为 true，默认为 true
                // .requestForce(true)
                // 是否支持 5.0 权限申请，默认为 false
                // .requestUnderM(false)
                // 权限，单权限申请仅只能填入一个
                .requestPermissions(Manifest.permission.RECORD_AUDIO)
                // 权限码
                .requestCodes(0)
                // 如果需要使用 @PermissionNonRationale 注解的话，建议添加如下一行
                // 返回的 intent 是跳转至**系统设置页面**
                // .requestPageType(Permissions4M.PageType.MANAGER_PAGE)
                // 返回的 intent 是跳转至**手机管家页面**
                // .requestPageType(Permissions4M.PageType.ANDROID_SETTING_PAGE)
                .request();
    }

    /**
     * 申请多条权限的方法
     * 1.使用 @PermissionsRequestSync 修饰 Activity 或 Fragment
     * 2.传入两组参数：value 数组：请求码 permission 数组：请求权限
     * 3.使用 Permissions4M.get(BookEffectActivity.this).requestSync(); 进行同步权限申请
     * Note:同步申请默认强制申请(requestForce(true))，同步申请不支持 @PermissionsNonRationale
     */
    public void requestMutiPermission(){
        /**例如:@PermissionsRequestSync(
                permission = {Manifest.permission.BODY_SENSORS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_CALENDAR},
                value = {SENSORS_CODE,
                        LOCATION_CODE,
                        CALENDAR_CODE})**/
        Permissions4M.get(this).requestSync();
    }

    public static final int LOCATION_CODE = 0x11;

    /**
     * 授权成功时回调，注解中需要传入参数，分为两种情况：
     *      1.单参数：@PermissionsGranted(LOCATION_CODE)，被修饰函数可不传入参数(如果传入则必须传入int值)
     *      2.多参数：@PermissionsGranted({LOCATION_CODE, SENSORS_CODE, CALENDAR_CODE})，被修饰函数需要传入一个 int 参数，
     */
    @PermissionsGranted(LOCATION_CODE)
    public void granted(int code) {
        Toast.makeText(this,"授权成功",Toast.LENGTH_SHORT).show();
    }

    /**
     * 授权失败时回调，注解中需要传入参数，分为两种情况(与授权成功一样)
     */
    @PermissionsDenied(LOCATION_CODE)
    public void denied() {
        Toast.makeText(this,"授权失败",Toast.LENGTH_SHORT).show();
    }

    /**
     * 二次授权时回调，用于解释为何需要此权限，注解中需要传入参数，分为两种情况(同上)
     * Note：注：系统弹出权限申请 dialog 与 toast 提示是异步操作，
     * 所以如果希望自行弹出一个 dialog 后（或其他同步需求）再弹出系统对话框，那么请使用 @PermissionsCustomRationale
     */
    @PermissionsRationale(LOCATION_CODE)
    public void rationale() {
        Toast.makeText(this,"请开启权限",Toast.LENGTH_SHORT).show();
    }

    /**
     * 二次授权时回调，用于解释为何需要此权限，注解中需要传入参数，分为两种情况（同上）
     * Note：注：除上述以外的 dialog，开发者可以自定义其他展示效果，调用权限申请时请使用如下代码，否则会陷入无限调用自定义 Rationale 循环中
     *      Permissions4M.get(BookEffectActivity.this)
     *                  // 务必添加下列一行
     *                  .requestOnRationale()
     *                  .requestPermissions(Manifest.permission.RECORD_AUDIO)
     *                  .requestCodes(AUDIO_CODE)
     *                  .request();
     */
    @PermissionsCustomRationale(LOCATION_CODE)
    public void cusRationale() {
        new AlertDialog.Builder(this)
                .setMessage("读取地理位置权限申请：\n我们需要您开启读取地理位置权限(in activity with annotation)")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Permissions4M.get(QuanXianActivity.this)
                                // 注意添加 .requestOnRationale()
                                .requestOnRationale()
                                .requestPermissions(Manifest.permission.READ_SMS)
                                .requestCodes(LOCATION_CODE)
                                .request();
                    }
                })
                .show();
    }

    /**
     * 用户拒绝权限且不再提示（国产畸形权限适配扩展）情况下调用，此时意味着无论
     * 是 @PermissionsCustomRationale 或者 @PermissionsRationale 都不会被调用，
     * 无法给予用户提示。permission 将会返回一个跳转至手机管家界面或者应用设置
     * 界面的 intent，具体的设置方法请参考注解回调中 .requestPageType(int) 设置方法。
     * 此时该注解修饰的函数被调用，注解中需要传入参数，分为两种情况：
     *      1.单参数：@PermissionsNonRationale(LOCATION_CODE)，被修饰函数可只传入 Intent 参数
     *      2.多参数：@PermissionsNonRationale(AUDIO_CODE, CALL_LOG_CODE)，被修饰函数需传入 int 参数和 Intent 参数
     * @param intent
     * Note：注：请勿对同步请求的权限使用该注解，理由见项目答疑第1条
     */
    @PermissionsNonRationale({LOCATION_CODE})
    public void nonRationale(Intent intent) {
        startActivity(intent);
    }

    /**
     * 单个权限同步申请
     */
    public void requestSinglePermisssionByListener(){
        Permissions4M.get(QuanXianActivity.this)
                // 是否强制弹出权限申请对话框，建议为 true，默认为 true
                // .requestForce(true)
                // 是否支持 5.0 权限申请，默认为 false
                // .requestUnderM(false)
                // 权限
                .requestPermissions(Manifest.permission.READ_CONTACTS)
                // 权限码
                .requestCodes(LOCATION_CODE)
                // 权限请求结果
                .requestListener(new Wrapper.PermissionRequestListener() {
                    @Override
                    public void permissionGranted(int code) {
                        Toast.makeText(QuanXianActivity.this,"授权成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void permissionDenied(int code) {
                        Toast.makeText(QuanXianActivity.this,"授权失败",Toast.LENGTH_SHORT).show();;
                    }

                    @Override
                    public void permissionRationale(int code) {
                        Toast.makeText(QuanXianActivity.this,"请打开读取通讯录权限 in activity with listener",Toast.LENGTH_SHORT).show();
                    }
                })
                // 二次请求时回调
                .requestCustomRationaleListener(new Wrapper.PermissionCustomRationaleListener() {
                    @Override
                    public void permissionCustomRationale(int code) {
                        new AlertDialog.Builder(QuanXianActivity.this)
                                .setMessage("通讯录权限申请：\n我们需要您开启通讯录权限(in fragment with annotation)")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Permissions4M.get(QuanXianActivity.this)
                                                .requestOnRationale()
                                                .requestPermissions(Manifest.permission.READ_PHONE_STATE)
                                                .requestCodes(LOCATION_CODE)
                                                .request();
                                    }
                                })
                                .show();
                    }
                })
                // 权限完全被禁时回调函数中返回 intent 类型（手机管家界面）
                .requestPageType(Permissions4M.PageType.MANAGER_PAGE)
                // 权限完全被禁时回调函数中返回 intent 类型（系统设置界面）
                //.requestPageType(Permissions4M.PageType.ANDROID_SETTING_PAGE)
                // 权限完全被禁时回调，接口函数中的参数 Intent 是由上一行决定的
                .requestPage(new Wrapper.PermissionPageListener() {
                    @Override
                    public void pageIntent(final Intent intent) {
                        new AlertDialog.Builder(QuanXianActivity.this)
                                .setMessage("读取通讯录权限申请：\n我们需要您开启读取通讯录权限(in activity with listener)")
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
                    }
                })
                .request();
    }

    /**
     * 多个权限同步申请
     * Note：同步申请不支持 PermissionPageListener 回调，理由见项目答疑第1条
     */
    public void requestMutiPermissionByListener(){
        Permissions4M.get(QuanXianActivity.this)
                .requestPermissions(Manifest.permission.BODY_SENSORS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CALENDAR)
                .requestCodes(LOCATION_CODE, LOCATION_CODE, LOCATION_CODE)
                .requestListener(new Wrapper.PermissionRequestListener() {
                    @Override
                    public void permissionGranted(int code) {
                        switch (code) {
                            case LOCATION_CODE:
                                Toast.makeText(QuanXianActivity.this,"地理位置权限授权成功 in fragment with annotation",Toast.LENGTH_SHORT).show();
                                break;
//                            case SENSORS_CODE:
//                                ToastUtil.show("传感器权限授权成功 in fragment with annotation");
//                                break;
//                            case CALENDAR_CODE:
//                                ToastUtil.show("读取日历权限授权成功 in fragment with annotation");
//                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void permissionDenied(int code) {
                        switch (code) {
                            case LOCATION_CODE:
                                Toast.makeText(QuanXianActivity.this,"地理位置权限授权失败 in fragment with annotation",Toast.LENGTH_SHORT).show();
                                break;
//                            case SENSORS_CODE:
//                                ToastUtil.show("传感器权限授权失败 in fragment with annotation");
//                                break;
//                            case CALENDAR_CODE:
//                                ToastUtil.show("读取日历权限授权失败 in fragment with annotation");
//                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void permissionRationale(int code) {
                        switch (code) {
                            case LOCATION_CODE:
                                Toast.makeText(QuanXianActivity.this,"请开启地理位置权限  in fragment with annotation",Toast.LENGTH_SHORT).show();
                                break;
//                            case SENSORS_CODE:
//                                ToastUtil.show("请开启传感器权限 in fragment with annotation");
//                                break;
//                            case CALENDAR_CODE:
//                                ToastUtil.show("请开启读取日历权限 in fragment with annotation");
//                                break;
                            default:
                                break;
                        }
                    }
                })
                .requestCustomRationaleListener(new Wrapper.PermissionCustomRationaleListener() {
                    @Override
                    public void permissionCustomRationale(int code) {
                        switch (code) {
                            case LOCATION_CODE:
                                Toast.makeText(QuanXianActivity.this,"请开启地理位置权限  in fragment with annotation",Toast.LENGTH_SHORT).show();
                                Log.e("TAG", "permissionRationale: 请开启地理位置权限 ");

                                new AlertDialog.Builder(QuanXianActivity.this)
                                        .setMessage("地理位置权限权限申请：\n我们需要您开启地理位置权限(in fragment with " +
                                                "annotation)")
                                        .setPositiveButton("确定", new DialogInterface
                                                .OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Permissions4M.get(QuanXianActivity.this)
                                                        .requestOnRationale()
                                                        .requestPermissions(Manifest.permission
                                                                .ACCESS_FINE_LOCATION)
                                                        .requestCodes(LOCATION_CODE)
                                                        .request();
                                            }
                                        })
                                        .show();
                                break;
                            default:
                                break;
                        }

                    }
                })
                .request();
    }
}
