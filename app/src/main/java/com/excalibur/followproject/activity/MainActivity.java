package com.excalibur.followproject.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.TextAlignment;
import com.excalibur.followproject.CountNumberView;
import com.excalibur.followproject.NovelParser;
import com.excalibur.followproject.NovelTextView;
import com.excalibur.followproject.R;
import com.excalibur.followproject.TextJustification;
import com.excalibur.followproject.adapter.MyAdapter;
import com.excalibur.followproject.adapter.SpaceItemDeration;
import com.excalibur.followproject.anim.AnimActivity;
import com.excalibur.followproject.view.novel.AutoAdjustTextView;
import com.excalibur.followproject.view.novel.AutoSplitTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.width;

public class MainActivity extends AppCompatActivity {

//    @BindView(R.id.recycler)
//    RecyclerView recyclerView;

//    @BindView(R.id.image)
    ImageView image;
    NovelTextView novelTextView;
    TextView normalTextView;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            initViewAfterLoadData(content);
        }
    };

    String content = "在开始解析Glide源码之前，我想先和大家谈一下该如何阅读源码，这个问题也是我平时被问得比较多的，因为很多人都觉得阅读源码是一件比较困难的事情。\n" +
            "那么阅读源码到底困难吗？这个当然主要还是要视具体的源码而定。比如同样是图片加载框架，我读Volley的源码时就感觉酣畅淋漓，并且对Volley的架构设计和代码质量深感佩服。读Glide的源码时却让我相当痛苦，代码极其难懂。当然这里我并不是说Glide的代码写得不好，只是因为Glide和复杂程度和Volley完全不是在一个量级上的。那么，虽然源码的复杂程度是外在的不可变条件，但我们却可以通过一些技巧来提升自己阅读源码的能力。这里我和大家分享一下我平时阅读源码时所使用的技巧，简单概括就是八个字：抽丝剥茧、点到即止。应该认准一个功能点，然后去分析这个功能点是如何实现的。但只要去追寻主体的实现逻辑即可，千万不要试图去搞懂每一行代码都是什么意思，那样很容易会陷入到思维黑洞当中，而且越陷越深。因为这些庞大的系统都不是由一个人写出来的，每一行代码都想搞明白，就会感觉自己是在盲人摸象，永远也研究不透。如果只是去分析主体的实现逻辑，那么就有比较明确的目的性，这样阅读源码会更加轻松，也更加有成效。\n" +
            "而今天带大家阅读的Glide源码就非常适合使用这个技巧，因为Glide的源码太复杂了，千万不要试图去搞明白它每行代码的作用，而是应该只分析它的主体实现逻辑。那么我们本篇文章就先确立好一个目标，就是要通过阅读源码搞明白下面这行代码：" +
            "Glide.with(this).load(url).into(imageView);\n" +
            "到底是如何实现将一张网络图片展示到ImageView上面的。先将Glide的一整套图片加载机制的基本流程梳理清楚，然后我们再通过后面的几篇文章具体去了解Glide源码方方面面的细节。\n" +
            "准备好了吗？那么我们现在开始。\n" +
            "开始阅读\n" +
            "好的，本篇文章到此结束。\n" +
            "没错，我没有在跟你开玩笑，相信我，这是一篇你无论如何都不想在手机上阅读的文章。因为Glide的源码实在是太复杂、太长了，在手机上根本就完全没有办法阅读。因此，最好的办法就是打开电脑，在浏览器上输入下面的网址进入到我的博客当中去阅读。\n" +
            "http://blog.csdn.net/guolin_blog/article/details/53939176\n" +
            "当然，如果你一定要在手机上阅读的话，也可以点击最下方的 阅读原文 直接跳转到我的博客。\n" +
            "这是一篇质量很高，难度也很高的文章，我花了两个星期的时间才把它写出来，希望你可以用心阅读，也希望你能有所收获。";

    AutoSplitTextView autoAdjustTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);
        autoAdjustTextView = (AutoSplitTextView) findViewById(R.id.auto);
        handler.sendEmptyMessageDelayed(1,1000);

//        image = (ImageView) findViewById(R.id.image);

//        novelTextView = (TextView) findViewById(R.id.novel);
       // novelTextView.setText(content);
//        Log.e("Tes2341234124","" + getWindowManager().getDefaultDisplay().getWidth() + "," + getWindowManager().getDefaultDisplay().getHeight());

//        handler.sendEmptyMessageDelayed(1,1000);
//        DocumentView documentView = new DocumentView(this, DocumentView.PLAIN_TEXT);  // Support plain text
//        documentView.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);

//        novelTextView = (NovelTextView) findViewById(R.id.atv);
//        normalTextView = (TextView) findViewById(R.id.novel);
//        handler.sendEmptyMessageDelayed(1,1000);
//        //根据屏幕调整文字大小
//        mArticleTextView.setLineSpacing(0f, 1.5f);
//        mArticleTextView.setTextSize(8*(float)width/320f);
//
//        NovelTextView novelTextView = (NovelTextView) findViewById(R.id.atv);
//        //设置TextView
//        novelTextView.setText("\"在开始解析Glide源码之前，我想先和大家谈一下该如何阅读源码，这个问题也是我平时被问得比较多的，因为很多人都觉得阅读源码是一件比较困难的事情。\\n\" +\n" +
//                "                    \"那么阅读源码到底困难吗？这个当然主要还是要视具体的源码而定。比如同样是图片加载框架，我读Volley的源码时就感觉酣畅淋漓，并且对Volley的架构设计和代码质量深感佩服。读Glide的源码时却让我相当痛苦，代码极其难懂。当然这里我并不是说Glide的代码写得不好，只是因为Glide和复杂程度和Volley完全不是在一个量级上的。那么，虽然源码的复杂程度是外在的不可变条件，但我们却可以通过一些技巧来提升自己阅读源码的能力。这里我和大家分享一下我平时阅读源码时所使用的技巧，简单概括就是八个字：抽丝剥茧、点到即止。应该认准一个功能点，然后去分析这个功能点是如何实现的。但只要去追寻主体的实现逻辑即可，千万不要试图去搞懂每一行代码都是什么意思，那样很容易会陷入到思维黑洞当中，而且越陷越深。因为这些庞大的系统都不是由一个人写出来的，每一行代码都想搞明白，就会感觉自己是在盲人摸象，永远也研究不透。如果只是去分析主体的实现逻辑，那么就有比较明确的目的性，这样阅读源码会更加轻松，也更加有成效。\\n\" +\n" +
//                "                    \"而今天带大家阅读的Glide源码就非常适合使用这个技巧，因为Glide的源码太复杂了，千万不要试图去搞明白它每行代码的作用，而是应该只分析它的主体实现逻辑。那么我们本篇文章就先确立好一个目标，就是要通过阅读源码搞明白下面这行代码：\" +\n" +
//                "                    \"Glide.with(this).load(url).into(imageView);\\n\" +\n" +
//                "                    \"到底是如何实现将一张网络图片展示到ImageView上面的。先将Glide的一整套图片加载机制的基本流程梳理清楚，然后我们再通过后面的几篇文章具体去了解Glide源码方方面面的细节。\\n\" +\n" +
//                "                    \"准备好了吗？那么我们现在开始。\\n\" +\n" +
//                "                    \"开始阅读\\n\" +\n" +
//                "                    \"好的，本篇文章到此结束。\\n\" +\n" +
//                "                    \"没错，我没有在跟你开玩笑，相信我，这是一篇你无论如何都不想在手机上阅读的文章。因为Glide的源码实在是太复杂、太长了，在手机上根本就完全没有办法阅读。因此，最好的办法就是打开电脑，在浏览器上输入下面的网址进入到我的博客当中去阅读。\\n\" +\n" +
//                "                    \"http://blog.csdn.net/guolin_blog/article/details/53939176\\n\" +\n" +
//                "                    \"当然，如果你一定要在手机上阅读的话，也可以点击最下方的 阅读原文 直接跳转到我的博客。\\n\" +\n" +
//                "                    \"这是一篇质量很高，难度也很高的文章，我花了两个星期的时间才把它写出来，希望你可以用心阅读，也希望你能有所收获。\";");

//        TextJustification.justify(mArticleTextView,width);//首先设置TextView的显示字体大小和文本内容，这里设置字体大小根据屏幕尺寸调整。然后调用自定义的类Textustification中的justify方法来实现TextView的分散对齐，两个参数分别是TextView控件以及控件的宽度。

//        DocumentView atv = (DocumentView) findViewById(R.id.blogText);
//        atv.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
//        CountNumberView numberView = (CountNumberView) findViewById(R.id.numberView);
//        numberView.start(50);
//        CountNumberView numberView1 = (CountNumberView) findViewById(R.id.numberView1);
//        numberView1.start(5698);
//        CountNumberView numberView2 = (CountNumberView) findViewById(R.id.numberView2);
//        numberView2.start(12341);
//        CountNumberView numberView3 = (CountNumberView) findViewById(R.id.numberView3);
//        numberView3.start(2434);


//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, AnimActivity.class);
//                if(Build.VERSION.SDK_INT >= 21){
//                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, image,"share").toBundle());
//                }else{
//                    startActivity(intent);
//                }
//            }
//        });
//        Canvas canvas;
//        Point point = new Point();
//        Path path;

//        ButterKnife.bind(this);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
//        recyclerView.addItemDecoration(new SpaceItemDeration());
//        recyclerView.setAdapter(new MyAdapter(this));
//
//        MyAdapter adapter = new MyAdapter(this);
//        adapter.setOnEditTextValuesChangedListener(new MyAdapter.OnEditTextValuesChangedListener() {
//            @Override
//            public void onEditTextValuesChanged(CharSequence charSequence) {
//                //Change Total Amount here
//            }
//        });
    }

    private float size = 12;
    private void initViewAfterLoadData(String data){
        autoAdjustTextView.setContent(content,"",true);
        autoAdjustTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    autoAdjustTextView.changeTextFont(motionEvent.getRawX() >= 200);
                return true;
            }
        });
        autoAdjustTextView.setOnContentOverListener(new AutoSplitTextView.OnContentOverListener() {
            @Override
            public void onContentOver(boolean isNext) {
                Toast.makeText(MainActivity.this,isNext ? "读取上一章...." : "读取下一章....",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void click(View view){
        Intent intent = new Intent(MainActivity.this, AnimActivity.class);
        if(Build.VERSION.SDK_INT >= 21){
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, image,"share").toBundle());
        }else{
            startActivity(intent);
        }
    }

}
