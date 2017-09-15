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

/**
 * 自动返回控件
 * http://mp.weixin.qq.com/s/MoHjpARbFMo1Il3ufL_x1Q
 * attrs
 * app:show_scroll          是否展示返回顶部按钮，默认为true
 * app:scroll_distance      触发显示返回顶部按钮的距离，默认100pd
 * app:show_animation       显示返回顶部按钮的动画，默认R.anim.fab_scale_up
 * app:hide_animation       隐藏返回顶部按钮的动画，默认R.anim.fab_scale_down
 * app:auto_arrow_icon      返回顶部的按钮图标，默认R.drawable.go_top
 * app:scroll_gravity       返回顶部按钮的位置，默认Gravity.BOTTOM and Gravity.CENTER_HORIZONTAL
 */
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
