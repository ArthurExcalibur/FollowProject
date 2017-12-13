package com.excalibur.followproject;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by hch
 * 创建时间：17/8/11  下午4:54
 */
public class TitleView extends RelativeLayout {
    private Context context;
    private RelativeLayout leftWo;
    private RelativeLayout titleParent;
    private ImageView woImg;
    private ImageView woHongDian;
    private SwitchCompat tiwenSwitch;
    private TextView titleTv;
    private FrameLayout rightSousu;
    private ImageView sousuoImg;
    private FrameLayout rightFabu;
    private ImageView fabuImg;
    private TextView tijiao;
    private TextView sousuo;
    private TextView rili;
    private LinearLayout sousuo_ll;
    private ContainsEmojiEditText sousuo_et;

    /**
     * toolbar类型 0返回  1提问  2搜索 3首页  4直播 5 确认 6 日历
     */
    int viewType = 0;
    /**
     * 标题名
     */
    String titleName;
    /**
     * 搜索id
     */
    int sousuoImgID;
    /**
     * 发布id
     */
    int fabuImgID;
    /**
     * 返回id
     */
    int woImgID;

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray title_attrs = context.obtainStyledAttributes(attrs, R.styleable.title_attrs);
        viewType = title_attrs.getInteger(R.styleable.title_attrs_view_type, 0);
        titleName = title_attrs.getString(R.styleable.title_attrs_title_name);
        woImgID = title_attrs.getResourceId(R.styleable.title_attrs_wo_img, 0);
        sousuoImgID = title_attrs.getResourceId(R.styleable.title_attrs_sousuo_img, 0);
        fabuImgID = title_attrs.getResourceId(R.styleable.title_attrs_fabu_img, 0);
        initView(context, viewType, titleName, sousuoImgID, fabuImgID);
    }

    private void initView(Context context, int viewType, String titleName, int sousuoImgID, int fabuImgID) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.layout_title, this, true);
        leftWo = (RelativeLayout) findViewById(R.id.left_wo);
        titleParent = (RelativeLayout) findViewById(R.id.title_rl);
        woImg = (ImageView) findViewById(R.id.wo_img);
        woHongDian = (ImageView) findViewById(R.id.wo_hong_dian);
        tiwenSwitch = (SwitchCompat) findViewById(R.id.tiwen_switch);
        titleTv = (TextView) findViewById(R.id.title_tv);
        rightSousu = (FrameLayout) findViewById(R.id.right_sousu);
        sousuoImg = (ImageView) findViewById(R.id.sousuo_img);
        rightFabu = (FrameLayout) findViewById(R.id.right_fabu);
        fabuImg = (ImageView) findViewById(R.id.fabu_img);
        tijiao = (TextView) findViewById(R.id.tijiao);
        sousuo = (TextView) findViewById(R.id.sousuo);
        rili = (TextView) findViewById(R.id.rili);
        sousuo_ll = (LinearLayout) findViewById(R.id.sousuo_ll);
        sousuo_et = (ContainsEmojiEditText) findViewById(R.id.sousuo_et);

        if (viewType == 0) {//平常,只有返回和titletext
            titleTv.setVisibility(VISIBLE);
            tiwenSwitch.setVisibility(GONE);
            setTitleText(titleName);
            setWoImgId(woImgID);
            setSouSuoImgId(0);
            setFaBuImgId(0);
        } else if (viewType == 1) {//提问专用
            titleTv.setVisibility(GONE);
            tiwenSwitch.setVisibility(VISIBLE);
            setSouSuoImgId(sousuoImgID);
            setFaBuImgId(fabuImgID);
        } else if (viewType == 2) {//搜索专用
            titleTv.setVisibility(GONE);
            tiwenSwitch.setVisibility(GONE);
            setWoImgId(woImgID);
            setSouSuoImgId(0);
            setFaBuImgId(0);
            sousuo.setVisibility(VISIBLE);
            sousuo_ll.setVisibility(VISIBLE);
        } else if (viewType == 3) {//围观
            titleTv.setVisibility(VISIBLE);
            tiwenSwitch.setVisibility(GONE);
            setTitleText(titleName);
            setSouSuoImgId(sousuoImgID);
            setFaBuImgId(fabuImgID);
            sousuo.setVisibility(GONE);
            sousuo_ll.setVisibility(GONE);
        } else if (viewType == 5) {//红色背景提交按钮
            titleTv.setVisibility(VISIBLE);
            tiwenSwitch.setVisibility(GONE);
            setTitleText(titleName);
            setWoImgId(woImgID);
            setSouSuoImgId(0);
            setFaBuImgId(0);
            tijiao.setVisibility(VISIBLE);
        } else if (viewType == 6) {//日历白色字体
            titleTv.setVisibility(VISIBLE);
            tiwenSwitch.setVisibility(GONE);
            rili.setVisibility(VISIBLE);
            setTitleText(titleName);
            setWoImgId(woImgID);
            setSouSuoImgId(0);
            setFaBuImgId(0);
        }
    }

    public void setViewOnClick(final ViewOnClick viewOnClick) {
        leftWo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOnClick.viewLeftWoOnClick(v);
            }
        });
        rightFabu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOnClick.viewSousuOnClick(v);
            }
        });
        tijiao.setOnClickListener(new NoDoubleClickListener(5000) {

            @Override
            public void onNoDoubleClick(View view) {
                viewOnClick.viewSousuOnClick(view);
            }
        });
        sousuo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOnClick.viewSousuOnClick(v);
            }
        });
        rightFabu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOnClick.viewFabuOnClick(v);
            }
        });
        rili.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOnClick.viewFabuOnClick(v);
            }
        });
    }

    public void setOnLeftBackListener(final OnbackClickListener leftBackListener){
        leftWo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                leftBackListener.viewLeftWoOnClick(v);
            }
        });
    }

    public void setSwitchOnCheck(CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        tiwenSwitch.setOnCheckedChangeListener(checkedChangeListener);
    }

    public SwitchCompat getTiwenSwitch() {
        return tiwenSwitch;
    }

    public void setHongDian(int visible) {
        woHongDian.setVisibility(visible);
    }

    /**
     * 设置标题名称
     *
     * @param text
     */
    public void setTitleText(String text) {
        titleTv.setText(text);
    }

    public void setTijiaoTest(String text) {
        tijiao.setText(text);
    }

    public void setRiliText(String text) {
        rili.setText(text);
    }

    /**
     * 设置返回
     *
     * @param id
     */
    public void setWoImgId(int id) {
        if (id == 0) {
            leftWo.setVisibility(View.GONE);
        } else {
            woImg.setImageResource(id);
            leftWo.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置搜索图片
     *
     * @param id
     */
    public void setSouSuoImgId(int id) {
        if (id == 0) {
            rightSousu.setVisibility(View.GONE);
        } else {
            sousuoImg.setImageResource(id);
            rightSousu.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 设置发布图片
     *
     * @param id
     */
    public void setFaBuImgId(int id) {
        if (id == 0) {
            rightFabu.setVisibility(View.GONE);
        } else {
            fabuImg.setImageResource(id);
            rightFabu.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 提交按钮是否可以点击
     *
     * @param isEnabled
     */
    public void setTiJiaobg(boolean isEnabled) {
        if (isEnabled) {
            tijiao.setBackgroundResource(R.drawable.bg_fapinglun_btn);
        } else {
            tijiao.setBackgroundResource(R.drawable.bg_yanzhengma_off_btn);
        }
        tijiao.setEnabled(isEnabled);
    }

    public TextView getTijiao(){
        return tijiao;
    }

    /**
     * 获取搜索框内容
     *
     * @return
     */
    public String getSouSuoNeiRong() {
        return sousuo_et.getText().toString();
    }


    public void setTitleBackGroundAlpha(int alpha) {
        titleParent.getBackground().mutate().setAlpha(alpha);
    }

    public TextView getRili() {
        return rili;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public interface ViewOnClick {
        void viewLeftWoOnClick(View view);

        void viewSousuOnClick(View view);

        void viewFabuOnClick(View view);
    }

    public interface OnbackClickListener{
        void viewLeftWoOnClick(View view);
    }

}

