package com.excalibur.followproject;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李军辉
 * @time 2017/5/2 0002  下午 4:48
 * @desc
 */

public class ImagePickerAdapter extends BaseAdapter {

    private List<Object> imageUrls;
    private Activity context;

    public ImagePickerAdapter(ArrayList<Object> imageUrls, Activity context){
        this.imageUrls = imageUrls;
        this.context = context;
    }

    public void addImageUrl(String imageUrl){
        imageUrls.add(getCount()-1,imageUrl);
        notifyDataSetInvalidated();
    }

    public void addImageUrls(List<String > list) {
        imageUrls.addAll(getCount() - 1, list);
        notifyDataSetInvalidated();
    }


    @Override
    public int getCount() {
        return imageUrls.size();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
//            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tiwen_imagepicker_item,null,false);
            holder = new ViewHolder();
//            holder.iv = (ImageView) convertView.findViewById(iv);
            DisplayMetrics dm = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int widthPixels = dm.widthPixels ;//高度
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(widthPixels/4.5),(int)(widthPixels/4.5));
//            params.bottomMargin = 10;
//            holder.iv.setLayoutParams(params);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Object obj = imageUrls.get(position);
        if(obj instanceof String){
//            holder.iv.setImageBitmap(ImageUtils.createThumbnail((String) imageUrls.get(position),90,90));
        }else if(obj instanceof Drawable){
            holder.iv.setImageDrawable((Drawable) obj);
        }



        return convertView;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        public ImageView iv;

    }


}

