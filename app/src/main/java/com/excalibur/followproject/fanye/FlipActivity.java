package com.excalibur.followproject.fanye;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.excalibur.followproject.R;
import com.excalibur.followproject.fanye.flip.FlipViewController;
import com.excalibur.followproject.view.read.ReadView;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Handler;

public class FlipActivity extends AppCompatActivity {

    View back;
//    View background;
    RelativeLayout ll;

    public static final String content = "在说分布式事务之前，我们先从数据库事务说起。 数据库事务可能大家都很熟悉，在开发过程中也会经常使用到。但是即使如此，可能对于一些细节问题，很多人仍然不清楚。比如很多人都知道数据库事务的几个特性：原子性(Atomicity )、一致性( Consistency )、隔离性或独立性( Isolation)和持久性(Durabilily)，简称就是ACID。但是再往下比如问到隔离性指的是什么的时候可能就不知道了，或者是知道隔离性是什么但是再问到数据库实现隔离的都有哪些级别，或者是每个级别他们有什么区别的时候可能就不知道了。\n" +
            "\n" +
            "本文并不打算介绍这些数据库事务的这些东西，有兴趣可以搜索一下相关资料。不过有一个知识点我们需要了解，就是假如数据库在提交事务的时候突然断电，那么它是怎么样恢复的呢？ 为什么要提到这个知识点呢？ 因为分布式系统的核心就是处理各种异常情况，这也是分布式系统复杂的地方，因为分布式的网络环境很复杂，这种“断电”故障要比单机多很多，所以我们在做分布式系统的时候，最先考虑的就是这种情况。这些异常可能有 机器宕机、网络异常、消息丢失、消息乱序、数据错误、不可靠的TCP、存储数据丢失、其他异常等等...\n" +
            "\n" +
            "我们接着说本地事务数据库断电的这种情况，它是怎么保证数据一致性的呢？我们使用SQL Server来举例，我们知道我们在使用 SQL Server 数据库是由两个文件组成的，一个数据库文件和一个日志文件，通常情况下，日志文件都要比数据库文件大很多。数据库进行任何写入操作的时候都是要先写日志的，同样的道理，我们在执行事务的时候数据库首先会记录下这个事务的redo操作日志，然后才开始真正操作数据库，在操作之前首先会把日志文件写入磁盘，那么当突然断电的时候，即使操作没有完成，在重新启动数据库时候，数据库会根据当前数据的情况进行undo回滚或者是redo前滚，这样就保证了数据的强一致性。\n" +
            "\n" +
            "接着，我们就说一下分布式事务。当我们的单个数据库的性能产生瓶颈的时候，我们可能会对数据库进行分区，这里所说的分区指的是物理分区，分区之后可能不同的库就处于不同的服务器上了，这个时候单个数据库的ACID已经不能适应这种情况了，而在这种ACID的集群环境下，再想保证集群的ACID几乎是很难达到，或者即使能达到那么效率和性能会大幅下降，最为关键的是再很难扩展新的分区了，这个时候如果再追求集群的ACID会导致我们的系统变得很差，这时我们就需要引入一个新的理论原则来适应这种集群的情况，就是 CAP 原则或者叫CAP定理，那么CAP定理指的是什么呢？\n" +
            "\n" +
            "CAP定理\n" +
            "\n" +
            "CAP定理是由加州大学伯克利分校Eric Brewer教授提出来的，他指出WEB服务无法同时满足一下3个属性：\n" +
            "\n" +
            "一致性(Consistency) ： 客户端知道一系列的操作都会同时发生(生效)\n" +
            "可用性(Availability) ： 每个操作都必须以可预期的响应结束\n" +
            "分区容错性(Partition tolerance) ： 即使出现单个组件无法可用,操作依然可以完成\n" +
            "具体地讲在分布式系统中，在任何数据库设计中，一个Web应用至多只能同时支持上面的两个属性。显然，任何横向扩展策略都要依赖于数据分区。因此，设计人员必须在一致性与可用性之间做出选择。\n" +
            "\n" +
            "这个定理在迄今为止的分布式系统中都是适用的！ 为什么这么说呢？\n" +
            "\n" +
            "这个时候有同学可能会把数据库的2PC（两阶段提交）搬出来说话了。OK，我们就来看一下数据库的两阶段提交。\n" +
            "\n" +
            "对数据库分布式事务有了解的同学一定知道数据库支持的2PC，又叫做 XA Transactions。";

    public static final String content1 = "其中，XA 是一个两阶段提交协议，该协议分为以下两个阶段：\n" +
            "\n" +
            "第一阶段：事务协调器要求每个涉及到事务的数据库预提交(precommit)此操作，并反映是否可以提交.\n" +
            "第二阶段：事务协调器要求每个数据库提交数据。\n" +
            "其中，如果有任何一个数据库否决此次提交，那么所有数据库都会被要求回滚它们在此事务中的那部分信息。这样做的缺陷是什么呢? 咋看之下我们可以在数据库分区之间获得一致性。\n" +
            "\n" +
            "如果CAP 定理是对的，那么它一定会影响到可用性。\n" +
            "\n" +
            "如果说系统的可用性代表的是执行某项操作相关所有组件的可用性的和。那么在两阶段提交的过程中，可用性就代表了涉及到的每一个数据库中可用性的和。我们假设两阶段提交的过程中每一个数据库都具有99.9%的可用性，那么如果两阶段提交涉及到两个数据库，这个结果就是99.8%。根据系统可用性计算公式，假设每个月43200分钟，99.9%的可用性就是43157分钟, 99.8%的可用性就是43114分钟，相当于每个月的宕机时间增加了43分钟。\n" +
            "\n" +
            "以上，可以验证出来，CAP定理从理论上来讲是正确的，CAP我们先看到这里，等会再接着说。\n" +
            "\n" +
            "BASE理论\n" +
            "\n" +
            "在分布式系统中，我们往往追求的是可用性，它的重要程序比一致性要高，那么如何实现高可用性呢？ 前人已经给我们提出来了另外一个理论，就是BASE理论，它是用来对CAP定理进行进一步扩充的。BASE理论指的是：\n" +
            "\n" +
            "Basically Available（基本可用）\n" +
            "Soft state（软状态）\n" +
            "Eventually consistent（最终一致性）\n" +
            "BASE理论是对CAP中的一致性和可用性进行一个权衡的结果，理论的核心思想就是：我们无法做到强一致，但每个应用都可以根据自身的业务特点，采用适当的方式来使系统达到最终一致性（Eventual consistency）。\n" +
            "\n" +
            "有了以上理论之后，我们来看一下分布式事务的问题。";
    ReadView readView;

    private android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            readView.setChangePageMode(1);
            readView.setContentString(content,"title",true);
            readView.setOnContentLoadListener(new ReadView.OnContentLoadListener() {
                @Override
                public void onNewContentLoaded() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    readView.setContentString(content1,"title",true);
                }
            });
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip);

        final UltimateBar bar = new UltimateBar(this);
        bar.setHintBar();

        readView = (ReadView) findViewById(R.id.readView);
        handler.sendEmptyMessageDelayed(1,1000);

        //view = (TextView) findViewById(R.id.text);
//        back = (LinearLayout) findViewById(R.id.scroll);
        //back = findViewById(R.id.back);
        //ll = (RelativeLayout) findViewById(R.id.ll);
//        ViewGroup.LayoutParams params = back.getLayoutParams();
//        params.width = getWindowManager().getDefaultDisplay().getWidth() * 2;
//        params.height = getWindowManager().getDefaultDisplay().getHeight();
//        back.setLayoutParams(params);
//        back.scrollTo(getWindowManager().getDefaultDisplay().getWidth(),0);

//        FlipViewController flipView = new FlipViewController(this);
    }

    private TextView view;
    private void flip(){
//        float rotation = back.getRotationY();
//        ObjectAnimator animator = ObjectAnimator.ofFloat(back,"rotationY",rotation,90);
//        animator.setDuration(500);
//
//        animator.setInterpolator(new LinearInterpolator());
//        animator.start();

//        back.setPivotX(0);
//        back.setPivotY(getWindowManager().getDefaultDisplay().getHeight() / 2);
        back.setRotationY(-50);

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_flip);
//        back.startAnimation(animation);//开始动画
//
//        final float centerY = getWindowManager().getDefaultDisplay().getHeight() / 2.0f;
//
//        final Rotate3dAnimation rotation =
//                new Rotate3dAnimation(0, -90, 0, centerY, 0, false);
//        rotation.setDuration(5000);
//        rotation.setFillAfter(true);
//        rotation.setInterpolator(new AccelerateInterpolator());
//        //rotation.setAnimationListener(new DisplayNextView(position));
//        back.startAnimation(rotation);
    }

    private void getScreenCapture(){
        view.setDrawingCacheEnabled(true);
        Bitmap tBitmap = view.getDrawingCache();
        // 拷贝图片，否则在setDrawingCacheEnabled(false)以后该图片会被释放掉
        tBitmap = Bitmap.createBitmap(tBitmap);
        view.setDrawingCacheEnabled(false);
        if (tBitmap != null) {
            // mImageResult.setImageBitmap(tBitmap);
            Toast.makeText(getApplicationContext(), "获取成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_SHORT).show();
        }
        back.setBackground(new BitmapDrawable(tBitmap));
        view.setBackgroundColor(Color.BLUE);
    }

    private Bitmap formerBitmap;
    private Bitmap currentBitmap;
    private Bitmap nextBitmap;
    private Bitmap getViewCapture(View view){
        view.setDrawingCacheEnabled(true);
        Bitmap tBitmap = view.getDrawingCache();
        // 拷贝图片，否则在setDrawingCacheEnabled(false)以后该图片会被释放掉
        tBitmap = Bitmap.createBitmap(tBitmap);
        view.setDrawingCacheEnabled(false);
        if (tBitmap != null) {
            return tBitmap;
            //Toast.makeText(getApplicationContext(), "获取成功", Toast.LENGTH_SHORT).show();
        } else {
            return null;
            //Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    private PageWidget pageWidget;
    private void addPageWidget(){
        if(pageWidget == null){
            pageWidget = new PageWidget(this);
            pageWidget.setScreen(getWindowManager().getDefaultDisplay().getWidth(),
                    getWindowManager().getDefaultDisplay().getHeight());
        }
        pageWidget.setBitmaps(currentBitmap,nextBitmap);
        ll.addView(pageWidget);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
////        int w = 90 / getWindowManager().getDefaultDisplay().getWidth();
//        if(event.getAction() == MotionEvent.ACTION_DOWN){
//            if(formerBitmap != null && !formerBitmap.isRecycled())
//                formerBitmap.recycle();
//            if(currentBitmap != null && !currentBitmap.isRecycled())
//                currentBitmap.recycle();
//            if(nextBitmap != null && !nextBitmap.isRecycled())
//                nextBitmap.recycle();
//            currentBitmap = getViewCapture(view);
//            view.setText(Math.random() * 1000 + "");
//            formerBitmap = getViewCapture(view);
//            view.setText(Math.random() * 1000 + "");
//            nextBitmap = getViewCapture(view);
//            addPageWidget();
////            getScreenCapture();
//        }
////        else if(event.getAction() == MotionEvent.ACTION_MOVE){
////            float rY = (getWindowManager().getDefaultDisplay().getWidth() - event.getX()) * w;
////            back.setRotationY(rY);
////        }else if(event.getAction() == MotionEvent.ACTION_UP){
////            flip();
////        }
//        return false;
//    }
}
