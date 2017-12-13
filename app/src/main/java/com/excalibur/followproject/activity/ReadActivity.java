package com.excalibur.followproject.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.excalibur.PageContentController;
import com.excalibur.followproject.BookPageFactory;
import com.excalibur.followproject.R;
import com.excalibur.followproject.fanye.PageWidget;
import com.excalibur.followproject.fanye.Rotate3dAnimation;
import com.excalibur.followproject.fanye.flip.FlipViewController;
import com.excalibur.followproject.fanye.flip.Texture;
import com.excalibur.followproject.view.FlipView;
import com.excalibur.followproject.view.bookeffect.MagicBookView;
import com.excalibur.followproject.view.bookeffect.PageContainer;
import com.excalibur.followproject.view.crul.CurlActivity;
import com.excalibur.followproject.view.crul.CurlPage;
import com.excalibur.followproject.view.crul.CurlView;
import com.excalibur.followproject.view.novel.AutoAdjustTextView;
import com.excalibur.followproject.view.novel.AutoSplitTextView;

import org.zackratos.ultimatebar.UltimateBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;

import static com.excalibur.followproject.view.crul.CurlView.SHOW_ONE_PAGE;

public class ReadActivity extends AppCompatActivity {

    protected FlipViewController flipView;

    private AutoSplitTextView autoSplitTextView;
    //private TextView title;

    private static final String content = "一点人物相关\n" +
            "\n" +
            "    主要角『色』：\n" +
            "\n" +
            "    陈俊：主角，莫名其妙获得希灵皇帝权限的高中生，并且莫名其妙地开发出了自己的精神力量，尽管人生目标是混吃等死，但身边随时跟着一个以征服世界为己任的萝莉显然是不可能让他的这个目标顺利实现的……\n" +
            "\n" +
            "    潘多拉：希灵帝国将军，外表是一个很可爱的盲眼萝莉，陈俊的第一个希灵下属，对外身份是陈俊的妹妹，对后者有相当深的依恋，个『性』冷漠，只有在面对“哥哥”时才会愿意多说话，但是隐藏在冰山外表下的是以征服世界为己任的暴力萝莉本质……能力是召唤并装备各种巨大的希灵重装兵器，将自己变成威力惊人的人形炮台，终极能力是变成巨大的星体要塞：潘多拉之耀，可以以引力『潮』汐摧毁行星。\n" +
            "\n" +
            "    珊多拉：女二号，另外一个希灵皇帝，一头张扬的金『色』长发以及漂亮的容貌是她的招牌，在其他人面前的时候是一个冷傲的希灵女帝，但是面对心灵相通的陈俊的时候却会变成一个有着傻乎乎笑容的穷开心野丫头，这种剧烈的『性』格转变是连潘多拉也无法准确解释的事情……能力是精神奴役和心灵攻击，由于曾经被深渊侵蚀，也可以短时间变形成为深渊生物，从而获得更强大的力量，她是一个夺灵者。\n" +
            "\n" +
            "    许浅浅：女一号，陈俊的青梅竹马，活泼可爱的邻家小妹，开始的时候是一个普通的女孩，逐渐被卷进了陈俊的事情中，后来在希灵母巢中受到水晶辐『射』而觉醒了特殊能力，能力是时间畸变，可以精确地控制时间以及历史流向……咳咳，大部分情况下精确地控制时间流向……咳咳，好吧，有时候能够精确地控制时间流向……（发动能力的时候会彻底转变『性』格，黑暗而冷酷，不过并没有改变对陈俊的感情。）\n" +
            "\n" +
            "    陈倩：陈俊的无血缘关系的姐姐，温柔可人的大姐姐，十分维护陈俊，同样被卷入了希灵帝国的事件，被希灵母巢的水晶辐『射』之后觉醒了特殊能力，能力是厄运烙印，类似于诅咒的力量，画个圈圈诅咒你是她的座右铭。\n" +
            "\n" +
            "    林雪：陈俊的朋友或者对头，人类异能组织的成员，开始的时候对陈俊没有什么好感，后来——还是没有什么好感，嘛，也有可能正好相反，谁知道呢？林雪的能力是感知以及预感，能够精确地感知细微的能量变动，精确度甚至超过了一般希灵使徒的能量雷达，更强大的是，林雪还有一定的预知能力，可以知道未来一小段发生的事情走向，当她经受了希灵母巢的水晶辐『射』之后，这一能力更加强大，甚至达到了预言的地步。\n" +
            "\n" +
            "    西卡罗：潘多拉军团的高阶指挥官，擅长防御战的大汉，来到地球之后对倒卖盗版光盘产生了浓厚的兴趣，成天打扮成黑超特警在街头倒卖盗版光盘，让整个k市的城管对他恨之入骨，拥有强大的单兵防御能力和阵地战指挥能力，最强防御技能“福音书”甚至可以硬抗希灵对星武器“创世纪”的攻击。\n" +
            "\n" +
            "    叮当：巴掌大小的小可爱，长着一双蜻蜓一样的翅膀，她的外表让人很难相信她是一个女神，尽管只是低级女神，她的力量却不容小视，擅长控制生命能量的她能够轻而易举地复活死亡没有多久的生命或者创造不太严谨的新物种，极端喜欢棒棒糖，对第一个给她棒棒糖吃的陈俊很是信任。\n" +
            "\n" +
            "    潘玲玲（西维斯）：潘多拉军团的高阶指挥官，潘多拉的副官，擅长指挥作战，但是本身没有太强力的战斗能力，做饭很好吃，当陈俊和珊多拉第一次来到她家做客的时候她曾经紧张地一个人连续做出了一整桌的满汉全席……\n" +
            "\n" +
            "    阿西达－阿西多拉：希灵空间双子，擅长空间控制，军团传送。\n" +
            "------------\n" +
            "\n" +
            "希灵帝国的标准战争载具（不包括特型舰）\n" +
            "\n" +
            "    希灵载具：盲目者级：小型载具，运兵数量在五人以下，武装力量一般，机动『性』强，适合侦查以及火力『骚』扰\n" +
            "\n" +
            "    狂热者级：小型载具，仅限乘三人，机动『性』超强，装备有强力的矢量粒子炮，火力强猛，但是装甲比较脆弱。\n" +
            "\n" +
            "    争斗者级：小型载具，运兵数量五十人，在小型载具中拥有最厚重的装甲，机动『性』较强，火力薄弱，但是拥有一套普通情况下只安装在中型以上载具上的相位护盾，典型的水牛型载具，十分适合初期运兵以及吸引火力。\n" +
            "\n" +
            "    远征级：体型瘦长的中型舰船，限乘五百人，拥有整合了空间扩展技术的超大补给仓和三套设置在异空间的无限聚能反应炉，装甲一般但是装配了强大的能量场护盾，配合经过精确设置的全套自动反击系统以及高『射』速的定向聚能武器，典型的自主维持远征舰船，十分适合在没有补给的情况下持续作战，缺点是造价较高。\n" +
            "\n" +
            "    边疆级：圆滚滚的中型舰船，最多载员两千人，生活区域设施完善，武器系统种类繁多而且配合巧妙，装甲厚重到令人发指，主要武器是七十七门舰载幽能炮，另外配置有大量浮游炮矩阵以及快速反登陆机甲，典型的阵地碉堡式舰船，用来作为半固定的前线火力平台再合适不过，缺点也很明显，速度太慢，假如没有支援的话，很容易沦为活靶子。\n" +
            "\n" +
            "    信仰级：中型舰船中的超级大个子，标准乘员五千人，如果情况需要，最多可以容纳一万乘员，信仰级拥有巨大的居住区域以及生活娱乐设施，甚至配备了两个殖民地级别的生态球，作为一艘非主力战斗舰船，信仰级的装甲一般，速度也不出『色』，火力仅仅能够用于自卫，但是很变态的，这么巨大的舰船竟然配置了整体跃迁规避系统，这个耗能大户每次启动都将让整艘船的非主要设备进入能量限制状态十分钟，因此只能用来保命，信仰级是典型的大型运输船，也可以作为殖民舰使用，在上面的生活会十分舒适，但要记住，永远别让周边的防护舰队远离自己……\n" +
            "\n" +
            "    永恒级：外形酷似棺材的大型战舰，船坚炮利，由于配置了先进的核心思维终端，绝大部分的『操』作可以交给战舰本身的意识来完成，因此这艘巨舰的最少配置人员竟然是——0！当然，这是一种极限情况，为了保证最大效率地发挥永恒级的恐怖战斗力，战舰的最低人员配置是三百人，作为最典型的人手少威力强的高效率战争机器，这艘船上大部分空间都被那些令人发指的武器系统占据，剩下的则是为了驱动这些武器系统而设置的大量异空间能量反应炉输出端口，以源源不断地从异空间的反应炉中收取能量，那些紫『色』的水晶阵列占据了战舰四分之一的体积！永恒级的装甲系统厚重而复杂，各种能量护盾和实体装甲互相嵌套，在这些装甲的间隔处到处都是防御短程登陆和远程打击的自动防御反击武器，让这个大杀器成了一个无从下嘴的刺猬，对付它唯一可用的手段估计只剩下麻雀『骚』扰战术搭配消耗战，毕竟这么大的家伙其灵活『性』必然不足，而且其能源系统再先进也不足以驱动那么多的超级武器长期运转。当然，假如你手下有一个可以用氦闪直接轰穿地壳的潘多拉级超级使徒存在……当我没说。\n" +
            "\n" +
            "    君临者级：希灵战争思想发展到某一个极端之后产生的怪物，外形看上去就好像一堆互相成角度的同心圆环，这样的造型经常让人把它和希灵空间哨站——维斯卡回廊搞混，尽管造型怪异，但是没有人敢质疑君临者的恐怖威力，因为在君临者的核心区域、几个同心圆力场交叠所形成的那块空间扭曲场里面，隐藏着三个终极武器：启示录，创世纪，以及福音书。\n" +
            "\n" +
            "    可以对整个星系实施状态调控的终极战争辅助系统启示录，可以瞬间湮灭整颗恒星的进攻『性』武器创世纪，还有能够硬抗世界末日的防守组件福音书，当这三个超级武器组合在一起的时候，即使是一个拥有高度军事科技的星系都将被君临者玩弄于股掌之中，因此君临者级也被称为希灵的终极战舰——即使是阶位排在它之上的“荣耀”也无法在毁灭力上与其相提并论。\n" +
            "\n" +
            "    但是君临者也有其巨大的缺陷，三大组件同时启动所需的恐怖能量即使是直接调用虚空能量也需要长达七天的缓慢充能，而且为了装配并控制这三个复杂之极的组件，君临者级不得不牺牲了其全部的常规武器系统和百分之八十的装甲，这导致君临者唯一的攻击手段其实就是它的三大招，而且在这三大招完成充能之前它简直就是个活靶子，在那一层层的同心圆环内侧到处都是惊人的能量流动，这样强烈的干扰导致君临者也无法装配任何形式的能量护盾，因此只要能够突破君临者级附近的防御部队，要摧毁这个过于极端的恐怖庞然大物简直是易如反掌——咳咳，当然，我已经说过了，前提是突破君临者级附近的防御部队……\n" +
            "\n" +
            "    荣耀（终极星体要塞，只有顶级的希灵使徒才能将之启动，例如星体要塞潘多拉之耀，巨大战争壁垒星伊凡诺娃荣耀堡垒，但是目前启动荣耀形态的通用密匙由于帝国沉睡已经无法调用）：希灵使徒个体实力的巅峰表现，首领级的使徒才可能拥有的能力，利用法则的力量构建出巨大的星体要塞，将自己的灵魂变成不灭的能量辐『射』融入整个要塞以驱动其移动，荣耀是机械，也是生命，更是一种信念，除非你拥有神的力量，可以瞬间毁灭整个星体要塞，否则它就能够不断地重生，复原，它不但代表了终极的科学力量，也拥有不可思议的神秘奥术能量，你能够想象一颗星球释放的禁咒有多么可怕吗？荣耀，这种拥有思想的星体要塞就能够办到，而且荣耀本身还是一个整合了全部希灵基层设施的母体星球，它内部有全套的生产、建筑、研发模块，可以源源不断地生产出希灵帝国的一切常规兵种并研发高级兵种，更重要的，它还有灵魂保存的能力，每一个死去的希灵使徒其灵魂都可以被附近的荣耀所回收、复生，可以这么说，即使整个帝国灰飞烟灭，只要还有一颗荣耀存在，那么整个帝国就可以在数千年后被完完整整地复制出来！\n" +
            "------------\n" +
            "\n" +
            "专门开了间房……\n" +
            "\n" +
            "然后郑重其事推荐一本书：无限文《英灵战场》来自白夜中的伪善，传送门在书下面的推荐里，同志们过去捧场啊！\n" +
            "------------\n" +
            "\n" +
            "封面，恩，就是一副封面\n" +
            "\n" +
            "\n" +
            "------------\n" +
            "\n" +
            "英雄资料卡01西维斯\n" +
            "\n" +
            "    西维斯（风暴指挥官）：长发的气质美女，『性』格谨慎严肃，拥有风暴指挥官称号的英雄单位，希灵使徒中最擅长团队强化和战地指挥的高阶族群成员，本身的战斗力可以说弱小，在首领级的希灵使徒中处于垫底的位置，但集群作战中可以发挥的实力堪称战略武器。\n" +
            "\n" +
            "    部分技能：\n" +
            "\n" +
            "    光荣突击：全范围增幅己方所有部队的攻击力，将所有弹道类武器的命中率强行提高到百分之百，并使己方士兵免疫过载伤害。\n" +
            "\n" +
            "    军团之敌：标定敌方的一支部队，对这支部队发动的任何攻击必定暴击，命中率提升为百分之百，并令对方无法以任何常规方式隐形。\n" +
            "\n" +
            "    战争预感：大幅度提升己方全体部队的防御能力和机动能力，对敌方发动的制导类武器增加额外的百分之三十完全免伤几率。\n" +
            "\n" +
            "    目标打击：标定目标范围，命令轰炸部队攻击目标，攻击力提升一倍，消耗降低一倍。\n" +
            "\n" +
            "    战斗掠夺：固有技能，风暴指挥官的直属部队在攻击任何敌人时都可以从对方身上吸取能量，恢复自身百分之一的能量消耗，并有一定几率掠夺对方身上的物质以修复自身。\n" +
            "\n" +
            "    入侵命令：命令全部定向武器攻击对方堡垒系目标，攻击力提升一倍，穿甲几率提升一倍，武器消耗减半，会触发战斗掠夺的效果。\n" +
            "\n" +
            "    荣耀突击：全范围增幅己方所有部队的速度，将所有定向类武器的命中率强行提高到百分之百，并使己方士兵免疫过载伤害。\n" +
            "\n" +
            "    帝国突击：全范围增幅己方所有部队的防御力，将所有制导类武器的命中率强行提高到百分之百，并使己方士兵免疫过载伤害。\n" +
            "\n" +
            "    独裁者：独自接管全部军队成员的思考回路，短时间内制定出最优战术方案，有较好效果。\n" +
            "\n" +
            "    荣耀的奉献：命令一部分希灵大兵冲锋自爆，提高自爆威力一倍，己方防御力暂时提升。\n" +
            "\n" +
            "    风暴截击斩：风暴指挥官为数不多的自身攻击技能，利用指挥刀发动快速凌厉的截击，无视对方任何护盾和装甲，近战攻击。\n" +
            "\n" +
            "    风暴拦截：风暴指挥官为数不多的自身攻击技能，高速跃迁到敌人身旁发动截击斩，无视对方任何护盾和装甲，攻击后本身进入三秒不可伤害的无相位状态。\n" +
            "------------\n" +
            "\n" +
            "人物资料卡无责任版－叮当\n" +
            "\n" +
            "    叮当\n" +
            "\n" +
            "    种族：真神\n" +
            "\n" +
            "    『性』别：女\n" +
            "\n" +
            "    年龄：无可用单位\n" +
            "\n" +
            "    身高：12cm——168cm\n" +
            "\n" +
            "    生命力（伪）：当前世界所有生命总和的立方。\n" +
            "\n" +
            "    魔法力：无上限\n" +
            "\n" +
            "    宠物形态战斗力：5\n" +
            "\n" +
            "    神形态战斗力：无可用单位\n" +
            "\n" +
            "    对有生目标威胁度：恐怖的\n" +
            "\n" +
            "    战斗评级：渣渣\n" +
            "\n" +
            "    战争评级：不可或缺的\n" +
            "\n" +
            "    战略评级：恐怖的\n" +
            "\n" +
            "    特殊天赋：无法被杀死，每次死亡都会立刻在最近的神殿复活，若本世界没有自己的神殿则将在神界复活，并因受到优等生们的嘲笑而陷入战斗力暴涨20%的暴走状态，没错，那时候叮当就是战斗力足足高达6的——渣渣了……\n" +
            "\n" +
            "    不可抗拒『性』，因为是真神，其意志具有不可抗拒『性』，可以在不消耗任何代价的情况下让现实呈现出自己意愿中的状态（只能在神形态下使用）。\n" +
            "\n" +
            "    人物ps：虽然是个万年吊车尾的笨蛋女神，在众神的学校里也是出了名的差生，但却因某些原因拥有某支神族正规军小队队长的身份，在神族中的人缘极好，将一个被她称为“大蜥蜴”的龙神视为自己一生的劲敌，发誓要在掰腕子比赛中打败对方，现在正努力吃饭以提高自己一般形态下的身高，好让自己首先长到能和大蜥蜴的指甲一样大的程度，下一步打算是和那家伙的爪子一样大，然后是和前肢一样大，最终达到和对方掰手腕的伟大目标，综上可以看出，叮当是个目的『性』和条理『性』很强的……笨蛋……\n" +
            "------------\n" +
            "\n" +
            "人物资料卡无责任版－潘多拉\n" +
            "\n" +
            "    姓名：潘多拉\n" +
            "\n" +
            "    『性』别：女\n" +
            "\n" +
            "    种族：希灵－科技构装体\n" +
            "\n" +
            "    年龄：未知，但至少在几十万岁以上\n" +
            "\n" +
            "    身高：……能不说吗？\n" +
            "\n" +
            "    兴趣：哥哥，打仗，长个子\n" +
            "\n" +
            "    最喜欢的事物：哥哥，打仗，长个子\n" +
            "\n" +
            "    最重要的东西：哥哥，打仗，长个子\n" +
            "\n" +
            "    最不喜欢的东西：有人说哥哥坏话，不能打仗的时候，自己的身高\n" +
            "\n" +
            "    力量类别：科技类重装兵器阵列，幽能驱动系的全部单兵武装和部分改装型的战舰级武装都能使用，拥有出『色』的速度和火力爆发力，瞬间远程压制可以达到军团级，据说曾以一人之力密集轰飞了敌人一整个集团军。\n" +
            "\n" +
            "    第一形态（矮冬瓜）战斗力：极高的\n" +
            "\n" +
            "    第二形态（星球要塞）战斗力：恐怖的\n" +
            "\n" +
            "    第三形态（世界仲裁机关）战斗力：不可抗拒的\n" +
            "\n" +
            "    战斗评级：极端强大\n" +
            "\n" +
            "    战争评级：很强大\n" +
            "\n" +
            "    战略评级：渣渣\n" +
            "\n" +
            "    注：因头脑过于发热而且在大规模战场上容易陷入军国狂热状态的冲动『性』格，副官不在身边的时候其战略价值会随着战争规模的扩大反而下降，因此其副官又被称作“潘多拉的外置型思考回路”。\n" +
            "\n" +
            "    特殊天赋：单兵要塞，以一人之力产生要塞级的火力，可以轻而易举地同时『操』控一千台以上的巨大兵器，在使用浮游炮的时候更可以将其攻击力提升一倍。\n" +
            "\n" +
            "    战争效率，标准的帝国军人，极端效率化的行为方式，任何行动的能量消耗都相当于普通使徒的50%，用极少的能量就可以启动高级武装，因此保证了其小型机体的巨大战斗力，在使用单兵武器的时候甚至可以达到无限续航战斗的程度，也因此，她能一个人使用舰炮战斗——尽管那只是让这丫头的身高问题更加悲剧而已。\n" +
            "------------\n" +
            "\n" +
            "人物资料卡无责任版－珊多拉\n" +
            "\n" +
            "    姓名：珊多拉·凯尔薇·尤拉西斯\n" +
            "\n" +
            "    『性』别：女\n" +
            "\n" +
            "    种族：希灵－混沌构装体\n" +
            "\n" +
            "    年龄：未知，但应该比地球生命史还古老\n" +
            "\n" +
            "    身高：175cm\n" +
            "\n" +
            "    最喜欢的事物：阿俊，饭\n" +
            "\n" +
            "    最重要的东西：阿俊，饭\n" +
            "\n" +
            "    最喜欢做的事情：和阿俊在一起吃饭\n" +
            "\n" +
            "    最擅长做的事情：和阿俊在一起吃饭\n" +
            "\n" +
            "    力量类别：混沌/深渊/吃货\n" +
            "\n" +
            "    混沌：拥有强大的精神力量，在召唤帝国机械化部队的同时还可以依靠自身精神力发动神秘系的技能，在科技层的知识渊博到令人发指，同时也对神秘学有一定了解，而且自身的主要技能：精神控制，也具有一定神秘学特『性』，在面对有神志目标时可立于不败之地。\n" +
            "\n" +
            "    深渊：自身被深渊力量彻底腐蚀过一次，奇迹般地保持了自身理智之后获得的力量，一定时间内可以变成一个深渊生物，以右半身巨大的爪子和身上的黑焰战斗，物理防御力提升数倍且完全免疫低级的能量攻击，近战威力惊人，混合了瞬间深渊释放的战斗方法被珊多拉自称为：深渊格斗术。\n" +
            "\n" +
            "    吃货：主要战斗形态之外的珊多拉第三形态，化身为容量无限的食欲少女，拥有在一个小时之内消灭满汉全席的恐怖实力，甚至连视线之内的杯盘碗筷都不会放过，曾经依靠这种能力吃霸王餐：饭店的服务员还以为没有给这间包厢里的客人上菜，但事实上那是因为所有的碗筷甚至连餐巾纸都已经被珊多拉吃下去了——不过后来还是付钱了，因为她把人家空调咬坏了……\n" +
            "\n" +
            "    威胁等级－混沌使徒：恐怖的\n" +
            "\n" +
            "    威胁等级－深渊恶魔：极为恐怖的\n" +
            "\n" +
            "    战斗评级：很强大\n" +
            "\n" +
            "    战争评级：极端强大\n" +
            "\n" +
            "    战略评级：恐怖的\n" +
            "\n" +
            "    注：曾经闻名遐迩的战歌公主，军事天赋出众，擅长用尽一切手段灭绝敌人，因为自身的精神控制技能，喜欢奇袭战，也有自己上战场引发巨大混『乱』然后派军联合突击的冒险战术。\n" +
            "\n" +
            "    特殊天赋：混沌使者，被陈俊称之为电路板形态，完全免疫任何类型的精神攻击，可以轻易『操』纵或毁灭敌人的心灵，但由于灵魂的精密『性』和自身能量太过强大，每次控制都会不可避免地对目标的灵魂造成损伤。\n" +
            "\n" +
            "    深渊掠杀（需要深渊形态），每次攻击都会将自身的深渊之火打入敌人灵魂深处，不论是否突破了对方护甲都可以造成一定程度的灵魂损伤，若连续多次被珊多拉的攻击命中，则不论其肉体防御是否被突破都将死于灵魂崩溃，即攻击必伤，连击必死效果。\n" +
            "------------\n" +
            "\n" +
            "地球君一路走好……";


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

    private PageContentController controller;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                autoSplitTextView.setContent(content + content,"",true,0);
//                autoAdjustTextView.setText(content);
//                autoSplitTextView.setContent(content,"",true,-1);
//                autoSplitTextView.setContent(content,"",true,1);
                autoSplitTextView.setOnContentOverListener(new AutoSplitTextView.OnContentOverListener() {
                    @Override
                    public void onContentOver(boolean isNext) {
                        autoSplitTextView.setContent(content,"",isNext,isNext ? 1 : -1);
                    }
                });
                autoAdjustTextView.setText(autoSplitTextView.getContent());
                mCurPageBitmap = getScreenCapture(baseView);
//                mPageWidget.setBitmaps(mCurPageBitmap,mCurPageBitmap);
                mCurlView.setPageProvider(new PageProvider());
                mCurlView.setSizeChangedObserver(new SizeChangedObserver());
//                mCurlView.setCurrentIndex(0);
//                Bitmap bitmap = getScreenCapture(baseView);
//                pageWidget.setForeImage(bitmap);
//                autoSplitTextView.changePage(true);
//                autoAdjustTextView.setText(autoSplitTextView.getContent());
//                Bitmap bitmap1 = getScreenCapture(baseView);
//                pageWidget.setBgImage(bitmap1);
//                handler.sendEmptyMessageDelayed(2,1000);
            }else if(msg.what == 2){
//                Bitmap foreImage = getScreenCapture(baseView);
//                autoSplitTextView.changePage(true);
//                Bitmap bgImage = getScreenCapture(baseView);
            }else if(msg.what == 3){
                //pageWidget.setVisibility(View.INVISIBLE);
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
//    private PageWidget mPageWidget;
    Bitmap mCurPageBitmap, mNextPageBitmap;
    View baseView;
    TextView autoAdjustTextView;
//    ImageView image;
    private CurlView mCurlView;
    private BookPageFactory bookPageFactory;
    private Canvas mCurPageCanvas;
    private Canvas mNextPageCanvas;

//    com.excalibur.followproject.PageWidget pageWidget;
    UltimateBar bar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_read);

        bar = new UltimateBar(this);
        bar.setHintBar();

        baseView = findViewById(R.id.base_read);
//        mPageWidget = (PageWidget) findViewById(R.id.page);
        autoSplitTextView = (AutoSplitTextView) findViewById(R.id.base_read_autoSplit);
        autoAdjustTextView = (TextView) findViewById(R.id.text);
//        pageWidget = (com.excalibur.followproject.PageWidget) findViewById(R.id.page);
//        image = (ImageView) findViewById(R.id.image);

//        int width = getWindowManager().getDefaultDisplay().getWidth();
//        int height = getWindowManager().getDefaultDisplay().getHeight();

//        int index = 0;
//        if (getLastNonConfigurationInstance() != null) {
//            index = (Integer) getLastNonConfigurationInstance();
//        }
        mCurlView = (CurlView) findViewById(R.id.curl);
//        mCurlView.setViewMode(SHOW_ONE_PAGE);

//        mCurPageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        mNextPageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        mPageWidget.setScreen(width,height);
//
//        mCurPageCanvas = new Canvas(mCurPageBitmap);
//        mNextPageCanvas = new Canvas(mNextPageBitmap);
//        bookPageFactory = new BookPageFactory(width, height);
//
//        mPageWidget.setBitmaps(mCurPageBitmap, mCurPageBitmap);
//
//        mPageWidget.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent e) {
//                boolean ret;
//                if (v == mPageWidget) {
//                    if (e.getAction() == MotionEvent.ACTION_DOWN) {
//                        mPageWidget.abortAnimation();
//                        mPageWidget.calcCornerXY(e.getX(), e.getY());
//                        Bitmap bitmap = getScreenCapture(baseView);
//                        bookPageFactory.onDraw(mCurPageCanvas,bitmap);
//                        autoSplitTextView.changePage(mPageWidget.DragToRight());
//                        autoAdjustTextView.setText(autoSplitTextView.getContent());
//                        Bitmap bitmap1 = getScreenCapture(baseView);
//                        bookPageFactory.onDraw(mNextPageCanvas,bitmap1);
//                        mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
//                    }
//                    ret = mPageWidget.doTouchEvent(e);
//                    return ret;
//                }
//                return false;
//            }
//
//        });
//        int index = 0;
//        if (getLastNonConfigurationInstance() != null) {
//            index = (Integer) getLastNonConfigurationInstance();
//        }
//        mCurlView.setPageProvider(new PageProvider());
//        mCurlView.setSizeChangedObserver(new SizeChangedObserver());
//        mCurlView.setCurrentIndex(index);
//
//        mCurPageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        mNextPageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        mPageWidget.setBitmaps(mCurPageBitmap, mCurPageBitmap);
//        mPageWidget.setScreen(width,height);
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
//                        autoAdjustTextView.setText(autoSplitTextView.getContent());
//                        mNextPageBitmap = getScreenCapture(baseView);
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

    MagicBookView mBookView;
    private void initBookMagicView(){
        PageContainer.IPageContainer pre = new PageContainer.IPageContainer(){
            @Override
            public void onInit(int page, PageContainer container) {
                container.setContent(baseView,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                //container.setBackgroundColor(Color.RED);
                //mButton1.setText(""+page);
                //container.setContent(mButton1, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
            }

            @Override
            public void onTurnReload(boolean isTurnBack, int currentPage, int needReloadPage,
                                     PageContainer container) {
                autoSplitTextView.changePage(false);
                autoAdjustTextView.setText(autoSplitTextView.getContent());
            }

            @Override
            public void onSetPage(int page, PageContainer container) {

            }

        };

        PageContainer.IPageContainer cur = new PageContainer.IPageContainer(){
            @Override
            public void onInit(int page, PageContainer container) {
                container.setContent(baseView,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

            @Override
            public void onTurnReload(boolean isTurnBack, int currentPage, int needReloadPage,
                                     PageContainer container) {
//                autoSplitTextView.changePage(false);
                autoAdjustTextView.setText(autoSplitTextView.getContent());
            }

            @Override
            public void onSetPage(int page, PageContainer container) {

            }

        };

        PageContainer.IPageContainer next = new PageContainer.IPageContainer(){

            @Override
            public void onInit(int page, PageContainer container) {
                container.setContent(baseView,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

            @Override
            public void onTurnReload(boolean isTurnBack, int currentPage, int needReloadPage,
                                     PageContainer container) {
                autoSplitTextView.changePage(true);
                autoAdjustTextView.setText(autoSplitTextView.getContent());
            }

            @Override
            public void onSetPage(int page, PageContainer container) {

            }

        };

        mBookView.initBookView(50, 0, pre, cur, next);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if(event.getAction() == MotionEvent.ACTION_DOWN){
////            Bitmap bitmap = getScreenCapture(autoAdjustTextView);
////            saveBitmap(bitmap);
//            Bitmap bitmap = getScreenCapture(baseView);
////            image.setImageBitmap(bitmap);
//            //pageWidget.setForeImage(bitmap);
//            //autoSplitTextView.changePage(true);
//            //autoAdjustTextView.setText(autoSplitTextView.getContent());
//            //Bitmap bitmap1 = getScreenCapture(baseView);
//            //pageWidget.setBgImage(bitmap1);
//            //pageWidget.setVisibility(View.VISIBLE);
//        }
//        if(event.getAction() == MotionEvent.ACTION_UP){
//            //pageWidget.doAnim(true,(int)event.getX(),(int)event.getY());
//            //handler.sendEmptyMessageDelayed(3,1100);
////            Bitmap bitmap = getScreenCapture(baseView);
////            saveBitmap(bitmap);
////            pageWidget.doAnim(true,(int)event.getX(),(int)event.getY());
//        }
//        return true;
//    }

    private Bitmap getScreenCapture(View mLayoutSource){

        mLayoutSource.setDrawingCacheEnabled(true);
        Bitmap tBitmap = mLayoutSource.getDrawingCache();
        // 拷贝图片，否则在setDrawingCacheEnabled(false)以后该图片会被释放掉
        tBitmap = Bitmap.createBitmap(tBitmap);
//        saveBitmap(tBitmap);
        mLayoutSource.setDrawingCacheEnabled(false);
        if (tBitmap != null) {
            //Toast.makeText(getApplicationContext(), "获取成功", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_SHORT).show();
        }
        return tBitmap;
//        View view = getWindow().getDecorView();
//
//        //允许当前窗口保存缓存信息
//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
//
//        //获取状态栏高度
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
//
//        //去掉状态栏
//        //去掉状态栏
//        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeight, width,
//                height-statusBarHeight);
//
//
//        //销毁缓存信息
//        view.destroyDrawingCache();
//        view.setDrawingCacheEnabled(false);
//
//        return bitmap;
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

    private int fI = 0;
    /**
     * Bitmap provider.
     */
    private class PageProvider implements CurlView.PageProvider {

//        private int[] mBitmapIds = { R.mipmap.shijie, R.mipmap.shijie1,R.mipmap.shijie2,R.mipmap.shijie3,R.mipmap.shijie};
//
//        @Override
//        public int getPageCount() {
//            return Integer.MAX_VALUE;
//        }
//
//        private Bitmap loadBitmap(int width, int height, int index) {
//            Log.e("TestForCase","LoadBitmap For : " + index);
//            Bitmap b = Bitmap.createBitmap(width, height,
//                    Bitmap.Config.ARGB_8888);
//            b.eraseColor(0xFFFFFFFF);
//            Canvas c = new Canvas(b);
//            Drawable d = getResources().getDrawable(mBitmapIds[index % 5]);
//
//            int margin = 7;
//            int border = 3;
//            Rect r = new Rect(margin, margin, width - margin, height - margin);
//
//            int imageWidth = r.width() - (border * 2);
//            int imageHeight = imageWidth * d.getIntrinsicHeight()
//                    / d.getIntrinsicWidth();
//            if (imageHeight > r.height() - (border * 2)) {
//                imageHeight = r.height() - (border * 2);
//                imageWidth = imageHeight * d.getIntrinsicWidth()
//                        / d.getIntrinsicHeight();
//            }
//
//            r.left += ((r.width() - imageWidth) / 2) - border;
//            r.right = r.left + imageWidth + border + border;
//            r.top += ((r.height() - imageHeight) / 2) - border;
//            r.bottom = r.top + imageHeight + border + border;
//
//            Paint p = new Paint();
//            p.setColor(0xFFC0C0C0);
//            c.drawRect(r, p);
//            r.left += border;
//            r.right -= border;
//            r.top += border;
//            r.bottom -= border;
//
//            d.setBounds(r);
//            d.draw(c);
//
//            return b;
//        }
//
//        @Override
//        public void updatePage(CurlPage page, int width, int height, int index) {
//
//            switch (index % 5) {
//                // First case is image on front side, solid colored back.
//                case 0: {
//                    Bitmap front = loadBitmap(width, height, 0);
//                    page.setTexture(front, CurlPage.SIDE_FRONT);
//                    page.setColor(Color.rgb(180, 180, 180), CurlPage.SIDE_BACK);
//                    break;
//                }
//                // Second case is image on back side, solid colored front.
//                case 1: {
//                    Bitmap back = loadBitmap(width, height, 2);
//                    page.setTexture(back, CurlPage.SIDE_BACK);
//                    page.setColor(Color.rgb(127, 140, 180), CurlPage.SIDE_FRONT);
//                    break;
//                }
//                // Third case is images on both sides.
//                case 2: {
//                    Bitmap front = loadBitmap(width, height, 1);
//                    Bitmap back = loadBitmap(width, height, 3);
//                    page.setTexture(front, CurlPage.SIDE_FRONT);
//                    page.setTexture(back, CurlPage.SIDE_BACK);
//                    break;
//                }
//                // Fourth case is images on both sides - plus they are blend against
//                // separate colors.
//                case 3: {
//                    Bitmap front = loadBitmap(width, height, 2);
//                    Bitmap back = loadBitmap(width, height, 1);
//                    page.setTexture(front, CurlPage.SIDE_FRONT);
//                    page.setTexture(back, CurlPage.SIDE_BACK);
//                    page.setColor(Color.argb(127, 170, 130, 255),
//                            CurlPage.SIDE_FRONT);
//                    page.setColor(Color.rgb(255, 190, 150), CurlPage.SIDE_BACK);
//                    break;
//                }
//                // Fifth case is same image is assigned to front and back. In this
//                // scenario only one texture is used and shared for both sides.
//                case 4:
//                    Bitmap front = loadBitmap(width, height, 0);
//                    page.setTexture(front, CurlPage.SIDE_BOTH);
//                    page.setColor(Color.argb(127, 255, 255, 255),
//                            CurlPage.SIDE_BACK);
//                    break;
//            }
//        }

        @Override
        public int getPageCount() {
            return Integer.MAX_VALUE;
        }

        private Bitmap loadBitmap(int index,int type) {
            Log.e("TestForCase","loadBitmap:" + index);
            if(type == -1){
                autoSplitTextView.changePage(false);
                autoAdjustTextView.setText(autoSplitTextView.getContent());
            }else if(type == 0){
                autoAdjustTextView.setText(autoSplitTextView.getContent());
            }else{
                autoSplitTextView.changePage(true);
                autoAdjustTextView.setText(autoSplitTextView.getContent());
            }
            autoAdjustTextView.setText(autoSplitTextView.getContentByIndex(index));
            return getScreenCapture(baseView);
        }

        @Override
        public void updatePage(CurlPage page, int width, int height, int index) {
            Bitmap front;
            if(index < fI){
                fI--;
                front = loadBitmap(fI,-1);
            }else if(index == fI){
                front = loadBitmap(fI,0);
            }else{
                fI++;
                front = loadBitmap(fI,1);
            }
            page.setTexture(front, CurlPage.SIDE_FRONT);
            page.setColor(Color.rgb(180, 180, 180), CurlPage.SIDE_BACK);
        }

    }

    /**
     * CurlView size changed observer.
     */
    private class SizeChangedObserver implements CurlView.SizeChangedObserver {
        @Override
        public void onSizeChanged(int w, int h) {
//            if (w > h) {
//                mCurlView.setViewMode(CurlView.SHOW_TWO_PAGES);
//                mCurlView.setMargins(.1f, .05f, .1f, .05f);
//            } else {
//                mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
//                mCurlView.setMargins(.1f, .1f, .1f, .1f);
//            }
        }
    }

    class ReadHolder{
        AutoSplitTextView autoSplitTextView;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mCurlView.onResume();
//        if(!TextUtils.isEmpty(autoSplitTextView.getContent()))
//            autoSplitTextView.setContent();
//        bar.setHintBar();
        //autoSplitTextView.setCurrentPage();
//        flipView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mCurlView.onPause();
//        flipView.onPause();
    }
}
