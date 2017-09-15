package com.excalibur.followproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.excalibur.followproject.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    private LayoutInflater layoutInflater;

    public MyAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.items_recy,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.textView.setText(position + "");
    }

    @Override
    public int getItemCount() {
        return 60;
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyHolder(View itemView) {
            super(itemView);
//            textView = (TextView) itemView.findViewById(R.id.items_recy_text);
        }
    }

}
