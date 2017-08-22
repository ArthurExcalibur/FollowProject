package com.excalibur.followproject.adapter.zidongfanhui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.excalibur.followproject.R;

/**
 * Created by lieniu on 2017/8/14.
 */

public class ZiDongFanHuiAdapter extends RecyclerView.Adapter<ZiDongFanHuiAdapter.ZiDongFanHuiHolder> {

    private LayoutInflater layoutInflater;

    public ZiDongFanHuiAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ZiDongFanHuiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ZiDongFanHuiHolder(layoutInflater.inflate(R.layout.items_zidongfanhui,parent,false));
    }

    @Override
    public void onBindViewHolder(ZiDongFanHuiHolder holder, int position) {
        holder.textView.setText("会尽快释放哈师大尽快发货");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class ZiDongFanHuiHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ZiDongFanHuiHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.items_zidongfanhui_textview);
        }
    }
}
