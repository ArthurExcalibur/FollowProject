package com.excalibur.followproject.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.excalibur.followproject.R;
import com.excalibur.followproject.fanye.PageWidget;
import com.excalibur.followproject.fanye.Rotate3dAnimation;
import com.excalibur.followproject.fanye.flip.FlipViewController;
import com.excalibur.followproject.fanye.flip.Texture;
import com.excalibur.followproject.view.FlipView;
import com.excalibur.followproject.view.novel.AutoAdjustTextView;
import com.excalibur.followproject.view.novel.AutoSplitTextView;

import org.zackratos.ultimatebar.UltimateBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReadActivity extends AppCompatActivity {

    protected FlipViewController flipView;

    private AutoSplitTextView autoSplitTextView;
    //private TextView title;

    private static final String content = "客户懂点代码是最致命的毒药\n" +
            "       这里绝对不是吐槽，只是记录一个非常搞笑的事情。\n" +
            "\n" +
            "       台企业向来以绝对的服务称道，即使客户 Naive ，Simple。作为银行的网站，在项目快上线的时候，一声惊雷传来，将所有逻辑实现类的代码拷贝至 一个logic.cs类中，将所有实体类拷贝至Model.cs类中。没错,没有听错，老夫驰骋项目多年，第一次听过如此奇葩的要求！！！给出的理由绝对惊讶： 客户懂代码，逻辑类分开写看的不舒服！\n" +
            "\n" +
            "这个荒唐的理由下，需要做的处理：\n" +
            "\n" +
            "    ①原来面向各个界面的逻辑实现类的类名以序列编号重命名的形式全部拷贝至Logic.cs中 \n" +
            "\n" +
            "    ②所有实体类拷贝至Model.cs类中\n" +
            "\n" +
            "    ③所有的控制器中调用的方法全部重新设置路径\n" +
            "\n" +
            "    ④所有视图中引用的实体类文件路径重新设置\n" +
            "\n" +
            "    ⑤对翻新后的文件进行测试\n" +
            "\n" +
            "      想象着完成之后，整个项目简洁明了（光秃秃），尤其是逻辑层就两个类文件，无任何文件夹，仅仅只有一个Logic.cs 和 Model.cs 两个文件，后期的维护怎么做？哦，不是，客户懂点代码，想着是，文件个数少，以后维护可自行动手，不用再花钱。这算盘。。。\n" +
            "\n" +
            "      粗略算了下，负重的Logic.cs文件 里面会有近20K行的代码。整个项目翻新一遍，加上测试，两个人差不多需要四到五天的时间，说不准，一个星期之后，客户又觉得不好，还能再改回来，毕竟这样的事情经常发生，一步一注释，十步一备注，步步才能走的稳呐。\n" +
            "\n" +
            "     作为一名开发人员，尽量以最优、最简明的目的去对待项目。如果客户什么都不懂，那么我们给出各种方案，客户认可的标准就是 运行正常、（高效）、简单操作。恰恰如果客户懂点代码，但是又是那种仅仅懂点皮毛的门外汉，那真是叫痛苦，想来什么就是什么，需求没有不合理之说，如果界面管，代码结构管，实现方式管，这都是开发文档中明确的事情，无可厚非，如果客户连这种傻逼都都知道是荒谬的需求却还要一意孤行的去执行，而sa又向来弱势，那么坑爹的就是程序员；大家普遍对台企印象不好，或者说很差，其实也是有道理的。我在这家台企工作快两年，算大公司，所在的研发分部就有不下1000人，不加班，双休，5天七小时，13薪，年涨薪20% （起薪很低），日常工作轻松自由，可以磨洋工，去楼顶吹风，去楼下花园散散步，时间自由轻松。看来还算不错。弊端就是，员工无积极性。企业的态度是唯用户至上，如客户仅仅提供几个思路，实际是无稽之谈，完全不可行，但是也要开发人员短时间内给出一定的开发测试。在客户面前，台湾的sa毫无主见（反正也不是他们开发）,照单全收，然后统统转交至开发部，当开发人员在请求确认的时候，往往一两个星期都得不到反馈，甚至更久，看看人家强哥的24小时必回邮件制度。出了问题，还被强行背锅，有一次一个简单的需求，就是改下存储过程中X表的一栏位，结果sa描述成其它的意思，最后用户发现不对，质问sa，于是陆陆续续出现了A、B、C、D、E、F 六个版本，结果sa仍然没有搞清楚用户的需求。最后小组长被强行背锅，理由也是十分荒唐，一个运行十年的项目，维护的时候开发人员不能正确理解sa的要求。这强加的理由也是醉了，作为开发人员，按照需求来维护项目，更改的需求本身就是错的，还能怪到开发身上。大公司中很多项目都能拖上个一两年才出来一个版本，项目管理有时候很混乱，部门繁杂，流程拖沓。毕竟不差钱，养的起人，交的起电费。除了台企，也真是没谁了。长此以往，也导致了很多开发人员离职。然后长时间又招收不到合适的替补人员，用大量的实习生来做技术储备，水平又参差不齐，有的毕业了还培训过的却连断点都不知道怎么打，想想真是可笑。这么说来，实力，提升自身实力是第一目标，只有自己强大，才能有底气的强势些，证明自己的存在感，只管糊墙不看图纸的泥瓦匠不是一个好的程序员。   \n" +
            "\n" +
            "     岁月悠悠，每天两点一线的生活，公司-住处，下班闲暇之余买些专业书来啃，唯恐被淘汰，毕竟对公司来说，创造价值与成本比是第一位的。多读书，理想还是要有的，说不定那天就实现了呢，保持一份简单的心，毕竟我们每天都在做着改变世界的事情！！！\n" +
            "\n" +
            " \n" +
            "\n" +
            "大家好，我是新来的小白，文未佳，却已创。转载请声明（博客园-郎中令）出处，谢谢 ---市人皆大笑，举手揶揄之";


    public void saveBitmap(Bitmap bm) {
        File f =  new File(Environment.getExternalStorageDirectory() + "/111.png");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.e("TestForCase", "已经保存");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                autoSplitTextView.setContent(content,"",true);
                autoAdjustTextView.setText(autoSplitTextView.getContent());
//                handler.sendEmptyMessageDelayed(2,1000);
            }else if(msg.what == 2){
//                Bitmap foreImage = getScreenCapture(baseView);
//                autoSplitTextView.changePage(true);
//                Bitmap bgImage = getScreenCapture(baseView);
            }else if(msg.what == 3){
                pageWidget.setVisibility(View.INVISIBLE);
            }

//            Bitmap foreImage = getScreenCapture(baseView);
//            saveBitmap(foreImage);

//            autoSplitTextView.changePage(true);
//            Bitmap bgImage = getScreenCapture(baseView);
//            pageWidget.setBgImage(bgImage);
//            pageWidget.setForeImage(foreImage);
            //title.setVisibility(autoSplitTextView.getCurrentPageNumber() == 0 ? View.VISIBLE : View.GONE);
//            mCurPageBitmap = getScreenCapture(autoSplitTextView);
//            mPageWidget.setBitmaps(mCurPageBitmap,mCurPageBitmap);
        }
    };

    /** Called when the activity is first created. */
    private PageWidget mPageWidget;
    Bitmap mCurPageBitmap, mNextPageBitmap;
    View baseView;
    TextView autoAdjustTextView;

    com.excalibur.followproject.PageWidget pageWidget;
    UltimateBar bar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_read);

        bar = new UltimateBar(this);
        bar.setHintBar();

        baseView = findViewById(R.id.base_read);
        //mPageWidget = (PageWidget) findViewById(R.id.page);
        autoSplitTextView = (AutoSplitTextView) findViewById(R.id.base_read_autoSplit);
        autoAdjustTextView = (TextView) findViewById(R.id.text);
        pageWidget = (com.excalibur.followproject.PageWidget) findViewById(R.id.page);

        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        Log.e("TestForCase","screenWidth = " + width);
        Log.e("TestForCase","screenHeight = " + height);



//
//        mCurPageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        mNextPageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//


//        mPageWidget.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent e) {
//                boolean ret;
//                if (v == mPageWidget) {
//                    if (e.getAction() == MotionEvent.ACTION_DOWN) {
//                        mPageWidget.abortAnimation();
//                        mPageWidget.calcCornerXY(e.getX(), e.getY());
//                        autoSplitTextView.changePage(mPageWidget.DragToRight());
//                        mNextPageBitmap = getScreenCapture(autoSplitTextView);
//                        mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
//                    }
//                    ret = mPageWidget.doTouchEvent(e);
//                    return ret;
//                }
//                return false;
//            }
//        });

        handler.sendEmptyMessageDelayed(1,1000);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Bitmap bitmap = getScreenCapture(autoAdjustTextView);
            saveBitmap(bitmap);
//            pageWidget.setVisibility(View.VISIBLE);
//            Bitmap bitmap = getScreenCapture(baseView);
//            pageWidget.setForeImage(bitmap);
//            autoSplitTextView.changePage(true);
//            autoAdjustTextView.setText(autoSplitTextView.getContent());
//            Bitmap bitmap1 = getScreenCapture(baseView);
//            pageWidget.setForeImage(bitmap1);
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
//            pageWidget.doAnim(true,(int)event.getX(),(int)event.getY());
//            handler.sendEmptyMessageDelayed(3,1100);
//            Bitmap bitmap = getScreenCapture(baseView);
//            saveBitmap(bitmap);
//            pageWidget.doAnim(true,(int)event.getX(),(int)event.getY());
        }
        return true;
    }

    private Bitmap getScreenCapture(View mLayoutSource){
//        mLayoutSource.setDrawingCacheEnabled(true);
//        Bitmap tBitmap = mLayoutSource.getDrawingCache();
//        // 拷贝图片，否则在setDrawingCacheEnabled(false)以后该图片会被释放掉
//        tBitmap = Bitmap.createBitmap(tBitmap);
//        mLayoutSource.setDrawingCacheEnabled(false);
//        if (tBitmap != null) {
//            Toast.makeText(getApplicationContext(), "获取成功", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_SHORT).show();
//        }
//        return tBitmap;
        View view = getWindow().getDecorView();

        //允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        //获取状态栏高度
//        Rect rect = new Rect();
//        view.getWindowVisibleDisplayFrame(rect);
//        int statusBarHeight = rect.top;
//
//        WindowManager windowManager = getWindowManager();
//
//        //获取屏幕宽和高
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        windowManager.getDefaultDisplay().getMetrics(outMetrics);
//        int width = outMetrics.widthPixels;
//        int height = outMetrics.heightPixels;

        //去掉状态栏
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());

        //销毁缓存信息
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.base_read);
//
//        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.base_read);
//        FlipView view = new FlipView(this);
//        view.setWidth(getWindowManager().getDefaultDisplay().getWidth(),getWindowManager().getDefaultDisplay().getHeight());
//        view.setPivotY(getWindowManager().getDefaultDisplay().getHeight() / 2);
//        view.setPivotX(0);
//        view.setRotationY(-45);
//        Camera camera = new Camera();
//        camera.rotateY(-45);
//        relativeLayout.addView(view);



//        imageView.setPivotX(0);
//        imageView.setPivotY(getWindowManager().getDefaultDisplay().getHeight() / 2);
//        imageView.setScaleX(0.6f);
//        imageView.setScaleY(0.6f);
//        imageView.setRotationY(-45);


//        autoSplitTextView = (AutoSplitTextView) findViewById(R.id.base_read_autoSplit);
//        title = (TextView) findViewById(R.id.base_read_title);
//
//        handler.sendEmptyMessageDelayed(1,1000);


//        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.base_read);
//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int h = getWindowManager().getDefaultDisplay().getHeight() / 2;
//                Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(-90,-180,0,h,400,true);
//                rotate3dAnimation.setDuration(6000);
//                rotate3dAnimation.setFillAfter(true);
//                rotate3dAnimation.setInterpolator(new AccelerateInterpolator());
//                relativeLayout.startAnimation(rotate3dAnimation);
//            }
//        });
//
//        Point point;
//        Canvas canvas;
//        Path path;


//    }

    class ReadHolder{
        AutoSplitTextView autoSplitTextView;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TestForCase","");
        if(!TextUtils.isEmpty(autoSplitTextView.getContent()))
            autoSplitTextView.setContent();
//        bar.setHintBar();
        //autoSplitTextView.setCurrentPage();
//        flipView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        flipView.onPause();
    }
}
