package com.fubang.lihaovv.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fubang.lihaovv.App;
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.R;
import com.fubang.lihaovv.SlidingTab.EmotionInputDetector;
import com.fubang.lihaovv.SlidingTab.SlidingTabLayout;
import com.fubang.lihaovv.adapters.EmotionAdapter;
import com.fubang.lihaovv.adapters.GiftAdapter;
import com.fubang.lihaovv.adapters.HomeTitleAdapter;
import com.fubang.lihaovv.adapters.RoomChatAdapter;
import com.fubang.lihaovv.adapters.UserAdapter;
import com.fubang.lihaovv.entities.FaceEntity;
import com.fubang.lihaovv.entities.GiftEntity;
import com.fubang.lihaovv.entities.RtmpEntity;
import com.fubang.lihaovv.fragment.CommonFragment_;
import com.fubang.lihaovv.fragment.LookFragment_;
import com.fubang.lihaovv.fragment.MicQuenFragment_;
import com.fubang.lihaovv.fragment.PersonFragment_;
import com.fubang.lihaovv.utils.GiftUtil;
import com.fubang.lihaovv.utils.GlobalOnItemClickManager;
import com.fubang.lihaovv.utils.ScreenUtils;
import com.fubang.lihaovv.utils.Utils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;
import com.xlg.android.protocol.BigGiftRecord;
import com.xlg.android.protocol.Header;
import com.xlg.android.protocol.JoinRoomResponse;
import com.xlg.android.protocol.MicState;
import com.xlg.android.protocol.RoomChatMsg;
import com.xlg.android.protocol.RoomKickoutUserInfo;
import com.xlg.android.protocol.RoomUserInfo;
import com.xlg.android.video.AudioPlay;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.yj.media.Media;
import org.yj.media.RecvRtmp;
import org.yj.media.RecvRtmpCallback;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.parser.android.BiliDanmukuParser;
import master.flame.danmaku.ui.widget.DanmakuView;
import sample.room.RoomMain;

@EActivity(R.layout.activity_room)
public class RoomActivity extends BaseActivity {

    @ViewById(R.id.linear_new_container)
    LinearLayout linearLayout;
    @ViewById(R.id.edit_new_text)
    EditText editText;
    @ViewById(R.id.room_new_chat_send)
    Button sendBtn;
    @ViewById(R.id.room_new_gift)
    ImageView giftImage;
    @ViewById(R.id.chat_image_btn)
    ImageButton faceButton;
    @ViewById(R.id.test_new_back_btn)
    ImageView backImage;
    @ViewById(R.id.test_new_controll)
    RelativeLayout testController;
    @ViewById(R.id.test_new_full)
    ImageView fullImage;
    @ViewById(R.id.iv_room_setting)
    ImageView ivRoomSetting;
    @ViewById(R.id.room_new_id_test)
    TextView roomIdTv;
    @ViewById(R.id.follow_new_image)
    ImageView followImage;
    @ViewById(R.id.room_back_image)
    SimpleDraweeView textBackImage;
    @ViewById(R.id.chat_new_input_line)
    LinearLayout chatLine;
    @ViewById(R.id.text_new_relative)
    RelativeLayout textRelative;
    @ViewById(R.id.room_new_control)
    RelativeLayout roomControl;
    @ViewById(R.id.room_new_control2)
    RelativeLayout roomControl2;
    @ViewById(R.id.room_new_control3)
    RelativeLayout roomControl3;
    @ViewById(R.id.emotion_new_layout)
    RelativeLayout emotionLayout;
    @ViewById(R.id.edit_new_text)
    EditText roomMessageEdit;
    @ViewById(R.id.emotion_new_button)
    ImageView emotionBtn;
    @ViewById(R.id.room_vp)
    ViewPager viewPager;
    @ViewById(R.id.game_btn)
    ImageView gameBtn;
    @ViewById(R.id.room_new_viewpager)
    ViewPager viewPager_content;
    @ViewById(R.id.room_new_tablayout)
    TabLayout tabLayout;
    private Button giftSendBtn;
    private GridView gridView;
    private ListView userList;
    private boolean isRunning = false;

    private List<RoomChatMsg> data = new ArrayList<>();

    private RoomChatAdapter adapter;

    private SurfaceView surfaceView;
    private SurfaceView surfaceView2;
    private SurfaceView surfaceView3;
    @ViewById(R.id.car_road)
    DanmakuView danmakuView;
    private DanmakuContext mContext;
    private BaseDanmakuParser mParser;
    private Bitmap bmp;
    private Bitmap bmp1;
    private Bitmap bmp2;
    private int micFlag = 0;
    private int mic0 = 0;
    private int mic1 = 1;
    private int mic2 = 2;
    private static AudioPlay play = new AudioPlay();
    private RoomMain roomMain = new RoomMain();
    private Context context;

    private PopupWindow popupWindow;
    private PopupWindow faceWindow;
    private PopupWindow userWindow;
    private List<GiftEntity> gifts = new ArrayList<>();
    private List<FaceEntity> faces = new ArrayList<>();
    private boolean isShow = false;
    private int toid;
    private int giftId;
    private int roomId;
    private String ip;
    private int port;
    private int ssrc;
    private int topline;
    private String toName;
    public static List<RoomUserInfo> userInfos = new ArrayList<>();
    private RoomUserInfo sendToUser;
    private UserAdapter userAdapter;

    private String roomPwd;
    private String roomIp;
    private App app;
    private Configuration configuration;
    private int buddyid;
    private int actorid;
    private boolean followflag = true;
    private int connectNumbaer = 1;
    private List<RoomUserInfo> micUsers = new ArrayList<>();
    ;
    private String mediaIp;
    private int mediaPort;
    private int mediaRand;
    private boolean isplaying;
    private String pwd = "";
    private boolean mStop = false;

    static Activity roomActivity;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private int chatToFlag = 0;
    private int ssrcFlag;
    private EmotionInputDetector mDetector;

    private List<View> views = new ArrayList<>();

    private View view1, view2, view3;
    private RecvRtmp rtmp, rtmp2, rtmp3;
    private org.yj.media.AudioPlay pcmPlay = new org.yj.media.AudioPlay();
    private org.yj.media.AudioPlay pcmPlay2 = new org.yj.media.AudioPlay();
    private org.yj.media.AudioPlay pcmPlay3 = new org.yj.media.AudioPlay();

    //开始加入房间
    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;

        configuration = getResources().getConfiguration();
        switch (micFlag) {
            case 0:
                if (rtmp != null) {
                    rtmp.Start();
                }
                if (pcmPlay != null) {
                    pcmPlay.start();
                }
                break;
            case 1:
                if (rtmp2 != null) {
                    rtmp2.Start();
                }
                if (pcmPlay2 != null) {
                    pcmPlay2.start();
                }
                break;
            case 2:
                if (rtmp3 != null) {
                    rtmp3.Start();
                }
                if (pcmPlay3 != null) {
                    pcmPlay3.start();
                }
                break;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                mStop = false;
                if (roomMain.getRoom() != null) {
                    if (!roomMain.getRoom().isOK()) {
                        roomMain.Start(roomId, Integer.parseInt(StartUtil.getUserId(RoomActivity.this)), StartUtil.getUserPwd(RoomActivity.this), ip, port, pwd);
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
        mStop = true;
        switch (micFlag) {
            case 0:
                if (rtmp != null) {
                    rtmp.Stop();
                }
                if (pcmPlay != null) {
                    pcmPlay.stop();
                }
                break;
            case 1:
                if (rtmp2 != null) {
                    rtmp2.Stop();
                }
                if (pcmPlay2 != null) {
                    pcmPlay2.stop();
                }
                break;
            case 2:
                if (rtmp3 != null) {
                    rtmp3.Stop();
                }
                if (pcmPlay3 != null) {
                    pcmPlay3.stop();
                }
                break;
        }

    }

    @Override
    protected void onDestroy() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (roomMain.getRoom() != null) {
                    roomMain.getRoom().getChannel().kickOutRoom(Integer.parseInt(StartUtil.getUserId(context)));
                    roomMain.getRoom().getChannel().Close();
                }
            }
        }).start();
        isRunning = false;
        rtmp.Release();
        pcmPlay.stop();
        pcmPlay = null;
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    @Override
    public void before() {
        // 防止锁屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        app = (App) getApplication();
        roomPwd = getIntent().getStringExtra("roomPwd");
        roomIp = getIntent().getStringExtra("roomIp");
        String[] Ips = roomIp.split(";");
        String[] ports = Ips[0].split(":");
        ip = ports[0];
        port = Integer.parseInt(ports[1]);
        roomId = Integer.parseInt(getIntent().getStringExtra("roomId"));
        EventBus.getDefault().register(this);
        context = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.Start(roomId, Integer.parseInt(StartUtil.getUserId(RoomActivity.this)), StartUtil.getUserPwd(RoomActivity.this), ip, port, pwd);
            }
        }).start();


    }


    @Override
    public void initView() {

        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.page_surface, null);
        view2 = inflater.inflate(R.layout.page_surface2, null);
        view3 = inflater.inflate(R.layout.page_surface3, null);
        surfaceView = (SurfaceView) view1.findViewById(R.id.surface1);
        surfaceView2 = (SurfaceView) view2.findViewById(R.id.surface2);
        surfaceView3 = (SurfaceView) view3.findViewById(R.id.surface3);


        initDanmu();
        for (int i = 0; i < AppConstant.ROOM_TYPE_TITLE.length; i++) {
            titles.add(AppConstant.ROOM_TYPE_TITLE[i]);
        }
        fragments.add(CommonFragment_.builder().arg(AppConstant.HOME_TYPE, titles.get(0)).build());
        fragments.add(PersonFragment_.builder().arg(AppConstant.HOME_TYPE, titles.get(1)).build());
        fragments.add(LookFragment_.builder().arg(AppConstant.HOME_TYPE, titles.get(2)).build());
        fragments.add(MicQuenFragment_.builder().arg(AppConstant.HOME_TYPE, titles.get(1)).build());
        roomIdTv.setText(roomId + "");
        roomActivity = this;
//        if (mDetector != null) {
//            mDetector = null;
//        }
        mDetector = EmotionInputDetector.with(this)
                .setEmotionView(emotionLayout)
                .bindToContent(chatLine)
                .bindToEditText(roomMessageEdit)
                .bindToEmotionButton(emotionBtn)
                .build();

        setUpEmotionViewPager();

    }

    @Override
    public void initListener() {
        initRevListener();
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testController.setVisibility(View.VISIBLE);
                animaView(testController);
            }
        });
        surfaceView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testController.setVisibility(View.VISIBLE);
                animaView(testController);
            }
        });
        surfaceView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testController.setVisibility(View.VISIBLE);
                animaView(testController);
            }
        });
        views.add(view1);
        views.add(view2);
        views.add(view3);
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }
        };
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                micFlag = position;
                switch (micFlag) {
                    case 0:
                        KLog.e(micFlag);
                        rtmp.SetUrl("http://www.pppktv.com/test.flv");
                        rtmp.Start();
                        pcmPlay.start();
                        break;
                    case 1:
                        KLog.e(micFlag);
                        rtmp2.SetUrl("http://www.pppktv.com/test.flv");
                        rtmp2.Start();
                        pcmPlay2.start();
                        break;
                    case 2:
                        KLog.e(micFlag);
                        rtmp3.SetUrl("http://www.pppktv.com/test.flv");
                        rtmp3.Start();
                        pcmPlay3.start();
                        break;
                }

                if (micUsers != null) {
                    for (int i = 0; i < micUsers.size(); i++) {
                        if (micUsers.get(i).getMicindex() == micFlag) {
                            giftToUser.setText(micUsers.get(i).getUseralias() + "(" + micUsers.get(i).getUserid() + ")");
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //收藏房间
        followImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        roomMain.getRoom().getChannel().followRoom(roomId, Integer.parseInt(StartUtil.getUserId(RoomActivity.this)));
                    }
                }).start();

                Toast.makeText(RoomActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            }
        });
        //全屏切换
        fullImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RoomLandActivity_.intent(RoomActivity.this).extra("roomIp", roomIp).extra("roomId", roomId + "").extra("roomPwd", roomPwd + "").get());
            }
        });
        //游戏
        gameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = StartUtil.getUserPwd(RoomActivity.this);
                String mdPwd = Utils.stringToMD5(pwd);
                String userId = StartUtil.getUserId(RoomActivity.this);
                String mac = StartUtil.getDeviceId(RoomActivity.this);
                StringBuilder gameUrl = new StringBuilder(GAME_URL);
                //游戏地址url
                gameUrl.append(userId).append("&passwd=")
                        .append(mdPwd).append("&sysserial=01f77905-6ea6-4d6b-8b5e-edcc59487f89&mac=")
                        .append(mac);
                Uri uri = Uri.parse(gameUrl.toString());//网址一定要加http
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
//                startActivity(GameActivity_.intent(RoomNewActivity.this).get());
            }
        });
        viewPager_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                chatToFlag = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rtmp.SetUrl("http://www.pppktv.com/test.flv");
        rtmp.Start();
        pcmPlay.start();
    }

    private void initRevListener() {
        rtmp = Media.CreateRecvRtmp();
        rtmp.SetCallback(new RecvRtmpCallback() {
            @Override
            public void OnRecvRtmpStart() {

            }

            @Override
            public void OnRecvRtmpPlayVideo(Bitmap bmp) {
                if (micFlag != 0) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textBackImage.setVisibility(View.GONE);
                    }
                });
                try {
                    if (null != surfaceView.getHolder()) {
                        SurfaceHolder holder = surfaceView.getHolder();
                        if (null == holder) {
                            return;
                        }
                        // 显示照片
                        Canvas canvas = holder.lockCanvas();
                        if (canvas != null) {
                            Rect src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
                            Rect dst = new Rect(0, 0,
                                    ScreenUtils.getScreenWidth(context),
                                    ScreenUtils.dp2px(context, surfaceView.getHeight()));
                            canvas.drawBitmap(bmp, src, dst, null);
                            holder.unlockCanvasAndPost(canvas);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            @Override
            public void OnRecvRtmpPlayAudio(byte[] pcm, int sample, int channel) {
                if (micFlag != 0) {
                    return;
                }
                pcmPlay.setConfig(sample, channel);
                pcmPlay.play(pcm);
                KLog.d("*****************************: OnRtmpPlayAudio:" + sample + ":" + channel + ":" + pcm.length);
            }

            @Override
            public void OnRecvRtmpStop() {

            }
        });
        rtmp2 = Media.CreateRecvRtmp();
        rtmp2.SetCallback(new RecvRtmpCallback() {
            @Override
            public void OnRecvRtmpStart() {

            }

            @Override
            public void OnRecvRtmpPlayVideo(Bitmap bmp) {
                if (micFlag != 1) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textBackImage.setVisibility(View.GONE);
                    }
                });
                try {
                    if (null != surfaceView2.getHolder()) {
                        SurfaceHolder holder = surfaceView2.getHolder();
                        if (null == holder) {
                            return;
                        }
                        // 显示照片
                        Canvas canvas = holder.lockCanvas();
                        if (canvas != null) {
                            Rect src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
                            Rect dst = new Rect(0, 0,
                                    ScreenUtils.getScreenWidth(context),
                                    ScreenUtils.dp2px(context, surfaceView.getHeight()));
                            canvas.drawBitmap(bmp, src, dst, null);
                            holder.unlockCanvasAndPost(canvas);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            @Override
            public void OnRecvRtmpPlayAudio(byte[] pcm, int sample, int channel) {
                if (micFlag != 1) {
                    return;
                }
                pcmPlay2.setConfig(sample, channel);
                pcmPlay2.play(pcm);
                KLog.d("*****************************: OnRtmpPlayAudio:" + sample + ":" + channel + ":" + pcm.length);
            }

            @Override
            public void OnRecvRtmpStop() {

            }
        });
        rtmp3 = Media.CreateRecvRtmp();
        rtmp3.SetCallback(new RecvRtmpCallback() {
            @Override
            public void OnRecvRtmpStart() {

            }

            @Override
            public void OnRecvRtmpPlayVideo(Bitmap bmp) {
                if (micFlag != 2) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textBackImage.setVisibility(View.GONE);
                    }
                });

                try {
                    if (null != surfaceView3.getHolder()) {
                        SurfaceHolder holder = surfaceView3.getHolder();
                        if (null == holder) {
                            return;
                        }
                        // 显示照片
                        Canvas canvas = holder.lockCanvas();
                        if (canvas != null) {
                            Rect src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
                            Rect dst = new Rect(0, 0,
                                    ScreenUtils.getScreenWidth(context),
                                    ScreenUtils.dp2px(context, surfaceView.getHeight()));
                            canvas.drawBitmap(bmp, src, dst, null);
                            holder.unlockCanvasAndPost(canvas);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            @Override
            public void OnRecvRtmpPlayAudio(byte[] pcm, int sample, int channel) {
                if (micFlag != 2) {
                    return;
                }
                pcmPlay3.setConfig(sample, channel);
                pcmPlay3.play(pcm);
                KLog.d("*****************************: OnRtmpPlayAudio:" + sample + ":" + channel + ":" + pcm.length);
            }

            @Override
            public void OnRecvRtmpStop() {

            }
        });

    }

    public static final String GAME_URL = "http://120.26.108.184:98/game.htm?ip=120.26.108.184&port=9999&memberid=";


    @Override
    public void onBackPressed() {
        if (!mDetector.interceptBackPress()) {
            super.onBackPressed();
        }
    }

    //表情页面
    private void setUpEmotionViewPager() {
        final String[] titles = new String[]{"经典", "vip"};
        EmotionAdapter mViewPagerAdapter = new EmotionAdapter(getSupportFragmentManager(), titles);
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.new_pager);
//        if (mViewPager != null) {
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.setCurrentItem(0);
//        }
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_new_tabs);
        slidingTabLayout.setCustomTabView(R.layout.widget_tab_indicator, R.id.text);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorPrimary));
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(mViewPager);

        GlobalOnItemClickManager globalOnItemClickListener = GlobalOnItemClickManager.getInstance();
        globalOnItemClickListener.attachToEditText((EditText) findViewById(R.id.edit_new_text));

    }


    private EditText giftToUser;

    //礼物的悬浮框
    private void showWindow() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_gift_grid, null);
        gridView = (GridView) view.findViewById(R.id.room_gift_list);
        giftSendBtn = (Button) view.findViewById(R.id.gift_send_btn);
        final TextView giftName = (TextView) view.findViewById(R.id.gift_name_txt);
        final EditText giftCount = (EditText) view.findViewById(R.id.gift_count);
//        final EditText
        giftToUser = (EditText) view.findViewById(R.id.gift_to_user);
        popupWindow = new PopupWindow(view);
        popupWindow.setFocusable(true);
        gifts.addAll(GiftUtil.getGifts());
        GiftAdapter giftAdapter = new GiftAdapter(gifts, this);
        gridView.setAdapter(giftAdapter);
        //选择礼物
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("123",gifts.get(position)+"------------>");
                giftId = gifts.get(position).getGiftId();
                String name = gifts.get(position).getGiftName();
                giftName.setText(name + "");
//                popupWindow.dismiss();
            }
        });
        if (micUsers != null) {
            for (int i = 0; i < micUsers.size(); i++) {
                if (micUsers.get(i).getMicindex() == micFlag) {
                    giftToUser.setText(micUsers.get(i).getUseralias() + "(" + micUsers.get(i).getUserid() + ")");
//                            Log.d("123","toid---"+toid+"toName"+toName);
                }
            }
        }
        //发送礼物
        giftSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toid = -1;
                toName = "";
//                if (giftToUser.getText().toString().equals("1")){
                for (int i = 0; i < micUsers.size(); i++) {
                    if (micUsers.get(i).getMicindex() == micFlag) {
                        toid = micUsers.get(i).getUserid();
                        toName = micUsers.get(i).getUseralias();
                    }
                }
                final int count = Integer.parseInt(giftCount.getText().toString());
                Log.d("123", "toid--" + toid + "---giftId---" + giftId + "---count---" + count + "---toName---" + toName);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        roomMain.getRoom().getChannel().sendGiftRecord(toid, giftId, count, toName, StartUtil.getUserName(RoomActivity.this));
                    }
                }).start();

                giftName.setText("送给");
                popupWindow.dismiss();
            }
        });
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popupWindow.setBackgroundDrawable(dw);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        popupWindow.setHeight(height / 2);
        popupWindow.setOutsideTouchable(true);
    }


    @Override
    public void initData() {
        HomeTitleAdapter adapter = new HomeTitleAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager_content.setAdapter(adapter);
        viewPager_content.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager_content);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //发送聊天消息
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText())) {
                    final String msgText = editText.getText().toString();
                    if (chatToFlag == 0) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                roomMain.getRoom().getChannel().sendChatMsg(0, (byte) 0, (byte) 0, "<FONT style=\"FONT-FAMILY:宋体;FONT-SIZE:14px; COLOR:#000000\">" + msgText + "</FONT>", StartUtil.getUserName(RoomActivity.this), Integer.parseInt(StartUtil.getUserLevel(RoomActivity.this)));
                            }
                        }).start();

                    } else if (sendToUser != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                roomMain.getRoom().getChannel().sendChatMsg(sendToUser.getUserid(), (byte) 0, (byte) 1, "<FONT style=\"FONT-FAMILY:宋体;FONT-SIZE:14px; COLOR:#000000\">" + msgText + "</FONT>", StartUtil.getUserName(RoomActivity.this), Integer.parseInt(StartUtil.getUserLevel(RoomActivity.this)));
                                editText.setText("");
                            }
                        }).start();
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                roomMain.getRoom().getChannel().sendChatMsg(0, (byte) 0, (byte) 0, "<FONT style=\"FONT-FAMILY:宋体;FONT-SIZE:14px; COLOR:#000000\">" + msgText + "</FONT>", StartUtil.getUserName(RoomActivity.this), Integer.parseInt(StartUtil.getUserLevel(RoomActivity.this)));
                            }
                        }).start();
                    }
                    editText.setText("");
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(RoomActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }

    //添加弹幕
    private void addDanmaku(boolean islive, Spanned chatmsg) {
        BaseDanmaku danmaku = mContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || danmakuView == null) {
            return;
        }
        danmaku.text = chatmsg;
        danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
        danmaku.isLive = islive;
        danmaku.time = danmakuView.getCurrentTime() + 4800;
        danmaku.textSize = 25f * (mParser.getDisplayer().getDensity() - 0.6f);
        danmaku.textColor = Color.WHITE;
        danmaku.textShadowColor = Color.WHITE;
        danmakuView.addDanmaku(danmaku);
    }

    //弹幕初始化
    public void initDanmu() {
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 1); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);


        mContext = DanmakuContext.create();
        mContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f).setScaleTextSize(1.2f)
                .setCacheStuffer(new SpannedCacheStuffer(), new BaseCacheStuffer.Proxy() {
                    @Override
                    public void prepareDrawing(BaseDanmaku danmaku, boolean fromWorkerThread) {
                        danmakuView.invalidateDanmaku(danmaku, false);
                    }

                    @Override
                    public void releaseResource(BaseDanmaku danmaku) {

                    }
                }) // 图文混排使用SpannedCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair);
        if (danmakuView != null) {
            mParser = createParser(this.getResources().openRawResource(R.raw.comments));
            danmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {
                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
                }

                @Override
                public void prepared() {
                    danmakuView.start();
                }
            });

            danmakuView.prepare(mParser, mContext);
            danmakuView.enableDanmakuDrawingCache(true);

        }

    }

    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }

        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }

    //接收礼物消息更新
    @Subscriber(tag = "BigGiftRecord")
    public void getGiftRecord(BigGiftRecord obj) {
        int getGiftId = obj.getGiftid();
        int count = obj.getCount();
        String giftTxt = "";
        if (count != 0) {
            for (int i = 0; i < gifts.size(); i++) {
                if (getGiftId == gifts.get(i).getGiftId()) {
                    if (getGiftId < 10)
                        giftTxt = "/g100" + getGiftId + "   x " + count;
                    if (getGiftId >= 10 && getGiftId < 100)
                        giftTxt = "/g10" + getGiftId + "   x " + count;
                    if (getGiftId >= 100)
                        giftTxt = "/g1" + getGiftId + "    x" + count;
                    if (getGiftId > 549 && getGiftId < 563) {
                        RoomChatMsg msg = new RoomChatMsg();
                        msg.setToid(-1);
                        msg.setContent("g" + getGiftId + "");
                        msg.setSrcid(obj.getSrcid());
                        msg.setSrcalias(obj.getSrcalias());
                        msg.setDstvcbid(count);
                        data.add(msg);
                        adapter.notifyDataSetChanged();
//                        listView.setSelection(listView.getCount() - 1);
                    }
                }
            }
        }
    }

    //自己排麦
    @Subscriber(tag = "waitForMic")
    public void waitForMic(final String userid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.getRoom().getChannel().upMicRequest(userid, Header.MIC_STATUS_APPLICATE_MIC, micFlag);
            }
        }).start();

    }

    //自己下麦
    @Subscriber(tag = "downForMic")
    public void downForMic(final String userid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.getRoom().getChannel().upMicRequest(userid, Header.MIC_STATUS_DOWN_MIC, micFlag);
            }
        }).start();
    }

    //抱上1麦
    @Subscriber(tag = "FirstMic")
    public void getFirstMic(final RoomUserInfo obj) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.getRoom().getChannel().baoMicRequest(0, obj.getUserid());
            }
        }).start();

    }

    //抱上2麦
    @Subscriber(tag = "SecondMic")
    public void getSecondMic(final RoomUserInfo obj) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.getRoom().getChannel().baoMicRequest(1, obj.getUserid());
            }
        }).start();
    }

    //抱上3麦
    @Subscriber(tag = "ThirdMic")
    public void getThirdMic(final RoomUserInfo obj) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.getRoom().getChannel().baoMicRequest(2, obj.getUserid());
            }
        }).start();
    }


    //私聊发送
    @Subscriber(tag = "SendToUser")
    public void getSendToUser(RoomUserInfo obj) {
        sendToUser = obj;
        viewPager_content.setCurrentItem(1, true);
    }

    //踢出房间
    @Subscriber(tag = "KickOut")
    public void getKickOut(final RoomUserInfo obj) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.getRoom().getChannel().kickOutRoom(obj.getUserid());
            }
        }).start();

    }

    //禁言
    @Subscriber(tag = "ForbidChat")
    public void getForbidChat(final RoomUserInfo obj) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.getRoom().getChannel().forbidChat(obj.getUserid(), (short) 1);
            }
        }).start();

    }

    //取消禁言
    @Subscriber(tag = "CancelForbidChat")
    public void getCancelForbidChat(final RoomUserInfo obj) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.getRoom().getChannel().forbidChat(obj.getUserid(), (short) 0);
            }
        }).start();

    }

    //加入房间错误
    @Subscriber(tag = "joinRoomError")
    public void jionRoomError(final int err) {
        if (err == 503) {
            Toast.makeText(this, "房间密码错误请输入", Toast.LENGTH_SHORT).show();
            // 弹出自定义dialog
            LayoutInflater inflater = LayoutInflater.from(RoomActivity.this);
            View view = inflater.inflate(R.layout.dialog_input_pwd, null);

            // 对话框
            final Dialog dialog = new Dialog(RoomActivity.this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.show();

            // 设置宽度为屏幕的宽度
            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = (int) (display.getWidth()); // 设置宽度
            dialog.getWindow().setAttributes(lp);
            dialog.getWindow().setContentView(view);
            dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失

            final EditText et_obj = (EditText) view.findViewById(R.id.et_obj);// 密码
            Button tv_go = (Button) view.findViewById(R.id.tv_go);// 确认
            Button iv_close = (Button) view.findViewById(R.id.tv_return);// 取消
            tv_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pwd = et_obj.getText().toString();
                    dialog.dismiss();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            play.start();
                            roomMain.Start(roomId, Integer.parseInt(StartUtil.getUserId(RoomActivity.this)), StartUtil.getUserPwd(RoomActivity.this), ip, port, pwd);
                        }
                    }).start();
                }
            });
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else if (err == 406 || err == 407 || err == 408) {
            Toast.makeText(RoomActivity.this, "您已被封号请联系客服", Toast.LENGTH_SHORT).show();
            finish();
        } else if (err == 417) {
            Toast.makeText(RoomActivity.this, "该房间限制等级进入", Toast.LENGTH_SHORT).show();
        }
    }

    //加入房间失败时尝试换ip端口号再加入
    @Subscriber(tag = "ConnectFailed")
    public void Reconnect(boolean failed) {
        if (connectNumbaer < 4) {
            String[] Ips = roomIp.split(";");
            String[] ports = Ips[connectNumbaer].split(":");
            ip = ports[0];
            port = Integer.parseInt(ports[1]);
            roomId = Integer.parseInt(getIntent().getStringExtra("roomId"));
            isRunning = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isRunning) {
                        play.start();
//                        Log.d("123", "chongxingqidong");
                        roomMain.Start(roomId, Integer.parseInt(StartUtil.getUserId(RoomActivity.this)), StartUtil.getUserPwd(RoomActivity.this), ip, port, pwd);
                    }
                }
            }).start();
            connectNumbaer++;
        } else {
            Toast.makeText(RoomActivity.this, "加入房间失败", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //加入房间返回消息
    @Subscriber(tag = "JoinRoomResponse")
    public void getJoinRoomResponse(JoinRoomResponse obj) {
//        topline = obj.getTopline() - 1000;
    }

    //接收服务器发送的消息更新列表
    @Subscriber(tag = "RoomChatMsg")
    public void getRoomChatMsg(RoomChatMsg msg) {
        if (msg.getMsgtype() == 0) {
            if (msg.getIsprivate() == 0) {
                EventBus.getDefault().post(msg, "CommonMsg");
            }
        }
        if (msg.getMsgtype() == 0) {
            if (msg.getIsprivate() == 1) {
                if (msg.getToid() == sendToUser.getUserid() || msg.getToid() == Integer.parseInt(StartUtil.getUserId(this))) {
                    EventBus.getDefault().post(msg, "PersonMsg");
                }
            }
        }
        if (msg.getMsgtype() == 12 && msg.getSrcid() == 2) {

            Spanned spanned = Html.fromHtml(msg.getContent());
            Log.d("123", spanned + "");
            addDanmaku(false, spanned);
        }
    }


    //用户离开房间
    @Subscriber(tag = "RoomKickoutUserInfo")
    public void getUserOut(RoomKickoutUserInfo obj) {
        int leaveId = obj.getToid();
        KLog.e(leaveId + "离开房间");
        for (int i = 0; i < userInfos.size(); i++) {
            if (userInfos.get(i).getUserid() == leaveId) {
                userInfos.remove(i);
            }
        }
    }

    //获取用户列表
    @Subscriber(tag = "userList")
    public void getUserList(RoomUserInfo userInfo) {
        userInfos.add(userInfo);
        KLog.e(userInfo.getUserid() + "加入房间");
    }

    //上公麦提示   1
    @Subscriber(tag = "upMicState")
    public void upMicState(final MicState obj) {
        new Thread(new Runnable() {//上下麦后获取拍卖列表
            @Override
            public void run() {
                roomMain.getRoom().getChannel().getMicList();//获取排麦列表
            }
        }).start();
        for (int i = 0; i < userInfos.size(); i++) {
            if (obj.getUserid() == userInfos.get(i).getUserid()) {
                userInfos.get(i).setMicindex(obj.getMicindex());
                micUsers.add(userInfos.get(i));
            }
        }
        String room_name = "lihao_" + obj.getUserid() + "_" + obj.getUserid();
        OkGo.<String>get(AppConstant.GET_RTMP_URL)
                .params("streamKey", room_name)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        RtmpEntity rtmp = new Gson().fromJson(response.body(), RtmpEntity.class);
                        switch (obj.getMicindex()) {
                            case 0:
                                map_trmp_play.put(0, rtmp.getRTMPPlayURL());
                                break;
                            case 1:
                                map_trmp_play.put(1, rtmp.getRTMPPlayURL());
                                break;
                            case 2:
                                map_trmp_play.put(2, rtmp.getRTMPPlayURL());
                                break;
                        }
                    }
                });

    }

    //下麦提示
    @Subscriber(tag = "downMicState")
    public void downMicState(MicState obj) {
        for (int i = 0; i < micUsers.size(); i++) {
            if (micUsers.get(i).getUserid() == obj.getUserid()) {
                micUsers.remove(i);
            }
        }
    }

    private HashMap<Integer, String> map_trmp_play;

    //麦上几个人就添加视频流
    @Subscriber(tag = "onMicUser")
    public void getonMicUser(final RoomUserInfo obj) {
        textBackImage.setVisibility(View.GONE);
        micUsers.add(obj);
        String room_name = "lihao_" + obj.getUserid() + "_" + obj.getUserid();
        OkGo.<String>get(AppConstant.GET_RTMP_URL)
                .params("streamKey", room_name)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        RtmpEntity rtmp = new Gson().fromJson(response.body(), RtmpEntity.class);
                        switch (obj.getMicindex()) {
                            case 0:
                                map_trmp_play.put(0, rtmp.getRTMPPlayURL());
                                break;
                            case 1:
                                map_trmp_play.put(1, rtmp.getRTMPPlayURL());
                                break;
                            case 2:
                                map_trmp_play.put(2, rtmp.getRTMPPlayURL());
                                break;
                        }
                    }
                });
        //发起网络去请求主播的rtmp播放地址   未完成
    }

    @Click({R.id.linear_new_container, R.id.chat_image_btn, R.id.room_new_gift, R.id.iv_room_setting})
    void click(View v) {
        switch (v.getId()) {
            case R.id.linear_new_container:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.chat_image_btn:
                popupWindow.dismiss();
                if (faceWindow.isShowing()) {
                    faceWindow.dismiss();
                } else {
                    faceWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                }
                break;
            //点击礼物图标
            case R.id.room_new_gift:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.showAsDropDown(giftImage);
                    if (micUsers != null) {
                        for (int i = 0; i < micUsers.size(); i++) {
                            if (micUsers.get(i).getMicindex() == micFlag) {
                                giftToUser.setText(micUsers.get(i).getUseralias() + "(" + micUsers.get(i).getUserid() + ")");
                            }
                        }
                    }
                }
                break;
            case R.id.iv_room_setting:
                showWindow();
                break;
        }
    }


}
