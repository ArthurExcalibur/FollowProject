package com.excalibur.followproject.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class CameraUtils {
    /**
     * 从相机中选择
     */
    public File FromCamera(Activity activity, int requestCode) {
        String url = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/DCIM/";
        String path1 = url + AllUtils.getUUID() + ".jpg";
        File file = new File(path1);
        Uri uri = getUriForFile(activity, file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, requestCode);
        return file;
    }

    /**
     * 从相机中选择
     */
    public File FromCamera(Fragment activity, int requestCode) {
        String url = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/DCIM/";
        String path1 = url + AllUtils.getUUID() + ".jpg";
        File file = new File(path1);
        Uri uri = getUriForFile(activity.getActivity(), file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, requestCode);
        return file;
    }

    /**
     * 从图库中选择
     */
	/*public void FromPhoto(Activity activity, int requestCode) {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		activity.startActivityForResult(i, requestCode);
	}*/

/*
	public void FromPhoto(Fragment activity, int requestCode) {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		activity.startActivityForResult(i, requestCode);
	}
*/

    /**
     * 从图库中选择
     */
    public void FromPhoto(Activity activity, int requestCode, int maxNum) {
        boolean showCamera = false;
//        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
//        int cutMode = MultiImageSelectorActivity.MODE_CUT_NORMAL;
//
//        Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
//        // 是否显示拍摄图片
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, showCamera);
//        // 最大可选择图片数量
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
//        // 选择模式
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
//        // 是否裁剪
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_CUT_MODE, cutMode);
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/DCIM/";
//        // 剪切保存的路径
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_CUT_PATH, path);
////				// 默认选择
////				if (mSelectPath != null && mSelectPath.size() > 0) {
////					intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
////				}
//        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 从图库中选择
     */
    public void FromPhoto(Fragment activity, int requestCode, int maxNum) {
        boolean showCamera = false;
//        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
//        int cutMode = MultiImageSelectorActivity.MODE_CUT_NORMAL;
//
//        Intent intent = new Intent(activity.getActivity(), MultiImageSelectorActivity.class);
//        // 是否显示拍摄图片
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, showCamera);
//        // 最大可选择图片数量
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
//        // 选择模式
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
//        // 是否裁剪
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_CUT_MODE, cutMode);
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/DCIM/";
//        // 剪切保存的路径
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_CUT_PATH, path);
//				// 默认选择
//				if (mSelectPath != null && mSelectPath.size() > 0) {
//					intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
//				}
//        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 从相机中选择图片返回结果
     */
    public static String FromCameraResult(Intent data, Context context, File file) {
        try {
            if (file != null) {
                Uri u = Uri.parse(android.provider.MediaStore.Images.Media
                        .insertImage(context.getContentResolver(),
                                file.getAbsolutePath(), null, null));
                String path = getRealPathFromUri(context, u);
                return path;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 通过Data获取图库中的路径
     *
     * @param context
     * @param data
     * @return
     */
    public static String UriToPath(Context context, Intent data) {
        if (data == null) {
            return null;
        }
        Uri photoUri = data.getData();
        if (photoUri == null) {
            return null;
        }
        String path = getRealPathFromUri(context, photoUri);
        return path;
    }

    public static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context, "com.lieniu.css.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    public static String getRealPathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= 19) { // api >= 19
            return getRealPathFromUriAboveApi19(context, uri);
        } else { // api < 19
            return getRealPathFromUriBelowAPI19(context, uri);
        }
    }

    /**
     * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private static String getRealPathFromUriBelowAPI19(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];

                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     *
     * @return
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
}

