package com.excalibur.followproject.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class AllUtils {

    public final static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static void log(Object context) {
        log(context, "");
    }

    /**
     * 获取当前日期是星期几<
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static void log(Object context, String tag) {
    }

    public static int getViewWidth(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredWidth();
    }

    public static int getViewHeight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredHeight();
    }

    public static int getDefaultDisplayWidth(Activity context) {
        return context.getWindowManager().getDefaultDisplay().getWidth();
    }

    public static int getDefaultDisplayHeight(Activity context) {
        return context.getWindowManager().getDefaultDisplay().getHeight();
    }

    public static void startIntent(Context context, Class c) {
        Intent intent = new Intent(context, c);
        context.startActivity(intent);
    }

    public static long getSystem() {
        return Calendar.getInstance().getTime().getTime();
    }

    public static Timestamp getSystemTime() {
        return new Timestamp(new Date().getTime());
    }

    public static String parseDate(long data) {
        return parseDate(data, "MM-dd HH:mm");
    }

    public static String parseDateYMD(long data) {
        return parseDate(data, "yyyy-MM-dd");
    }

    public static String parseDate(long data, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(data));
    }

    public static Object getClassString(Object c, String field_name) {
        Method method;
        String new_upper = field_name.substring(0, 1).toUpperCase()
                + field_name.substring(1, field_name.length());
        try {
            if (field_name.equals("status")) {
                method = c.getClass().getMethod("isStatus");
            } else {
                method = c.getClass().getMethod("get" + new_upper);
            }
            method.setAccessible(true);
            Object obj = method.invoke(c);
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取Drawable
     *
     * @param id
     * @param context
     * @return
     */
    public static Drawable getTextViewDrawable(int id, Context context) {
        Drawable drawable = context.getResources().getDrawable(id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        return drawable;
    }

    /**
     * 获取布局
     *
     * @param context
     * @param resourId
     * @return
     */
    public static View getLayoutView(Context context, int resourId) {
        View v = LayoutInflater.from(context).inflate(resourId, null);
        return v;
    }

    /**
     * 获取Color
     *
     * @param context
     * @param resourId
     * @return
     */
    public static int getColor(Context context, int resourId) {
        return context.getResources().getColor(resourId);
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static boolean isNull(Object obj) {
        if (obj == null || obj.equals("") || obj.equals("[]")) {
            return true;
        }
        return false;
    }


    public static void showToast(Context context, Object message) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }

    /**
     * @param context
     * @param message Toast的内容
     * @param value
     * @return
     */
    public static boolean isNullToast(Context context, Object message, Object value) {
        if (isNull(value)) {
            showToast(context, message);
            return true;
        } else {
            return false;
        }
    }

    public static RequestBody getRequestBody(String value) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), value);

    }

    public static String getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * get App versionName
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


    public interface onEtTextChangedLitener {
        void ontextchanged(Editable s);
    }


    public static <T> List<T> parseString2List(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<>();
        try {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }





    public static String convertFormat(float jifen){
        DecimalFormat fnum  = new DecimalFormat("##0.00");
        return fnum.format(jifen) + "";
    }


    public static String decimalFormat(float f2) {
        boolean f = f2 == (int) f2;
        float f3 = f2 * 10;
        boolean f1 = f3 == (int) f3;
        String s = "";
        if (f) {
            s = (int) f2 + ".00";
        } else if (f1) {
            s = f2 + "0";
        } else {
            s = f2 + "";
        }
        return s;
    }


}
