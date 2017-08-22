package com.excalibur.followproject.activity.zidongfanhui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.excalibur.followproject.R;
import com.excalibur.followproject.adapter.zidongfanhui.ZiDongFanHuiAdapter;
import com.gaoneng.library.AutoScrollBackLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZiDongFanHuiActivity extends AppCompatActivity {

    @BindView(R.id.activity_zidongfanhui_autoscrollLayout)
    AutoScrollBackLayout autoScrollBackLayout;
    @BindView(R.id.activity_zidongfanhui_recycler)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zidongfanhui);

        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new ZiDongFanHuiAdapter(this));

        autoScrollBackLayout.bindScrollBack();
    }

}
