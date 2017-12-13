package com.excalibur;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class ImageUtils {
    /**
     * 对图片进行二次采样，生成缩略图。放置加载过大图片出现内存溢出
     */
    public static Bitmap createThumbnail(String filePath, int newWidth, int newHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;

        int ratioWidth = originalWidth / newWidth;
        int ratioHeight = originalHeight / newHeight;

        options.inSampleSize = ratioHeight > ratioWidth ? ratioHeight
                : ratioWidth;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 根据图片高宽获取缩略图大小
     * 1、宽度大于高度，宽度除以高度小于等于1.1，则等比例压缩成正方形，宽度为屏幕宽度的一半；
     * 2、高度大于宽度，高度除以宽度小于等于1.1，则等比例压缩成正方形，宽度为屏幕宽度的一半；
     * 3、宽度大于高度，宽度除以高度大于等于1.1，则等比例压缩成横向长方形，其中宽度的值是屏幕宽度的三分之二（含左边空白处），长方形的宽度和高度的比例是3:2；
     * 4、高度大于宽度，高度除以宽度大于等于1.1，则等比例压缩成纵向长方形，其中宽度的值是屏幕宽度的二分之一（含左边空白处），长方形的宽度和高度的比例是3:4；
     *
     * @param bean
     * @param MaxWidth 图片最大宽度
     * @return
     */
//    public static int[] createThumbnailSize(TuPianBean bean, int MaxWidth) {
//        int[] tbSize = new int[2];
//        float w = bean.getWidth();
//        float h = bean.getHeight();
//        if (w == 0 || h == 0) {
//            tbSize[0] = MaxWidth;
//            tbSize[1] = MaxWidth;
//        } else if (w > h && w / h <= 1.1f) {
//            tbSize[0] = MaxWidth / 2;
//            tbSize[1] = tbSize[0];
//        } else if (h > w && h / w <= 1.1f) {
//            tbSize[0] = MaxWidth / 2;
//            tbSize[1] = tbSize[0];
//        } else if (w > h && w / h > 1.1f) {
//            tbSize[0] = MaxWidth * 2 / 3;
//            tbSize[1] = tbSize[0] * 2 / 3;
//        } else if (h > w && h / w > 1.1f) {
//            tbSize[0] = MaxWidth / 2;
//            tbSize[1] = tbSize[0] * 4 / 3;
//        }
//        return tbSize;
//    }
}
