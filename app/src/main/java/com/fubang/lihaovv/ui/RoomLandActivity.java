package com.fubang.lihaovv.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import com.facebook.drawee.view.SimpleDraweeView;
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.entities.RtmpEntity;
import com.fubang.lihaovv.ui.BaseActivity;
import com.fubang.lihaovv.App;
import com.fubang.lihaovv.SlidingTab.EmotionInputDetector;
import com.fubang.lihaovv.SlidingTab.SlidingTabLayout;
import com.fubang.lihaovv.adapters.EmotionAdapter;
import com.fubang.lihaovv.adapters.FaceAdapter;
import com.fubang.lihaovv.adapters.GiftAdapter;
import com.fubang.lihaovv.adapters.RoomChatAdapter;
import com.fubang.lihaovv.adapters.UserAdapter;
import com.fubang.lihaovv.entities.FaceEntity;
import com.fubang.lihaovv.entities.GiftEntity;
import com.fubang.lihaovv.utils.ControllerUtil;
import com.fubang.lihaovv.utils.FaceUtil;
import com.fubang.lihaovv.utils.GiftUtil;
import com.fubang.lihaovv.utils.GlobalOnItemClickManager;
import com.fubang.lihaovv.R;
import com.fubang.lihaovv.utils.NetUtils;
import com.fubang.lihaovv.utils.ScreenUtils;
import com.fubang.lihaovv.utils.ToastUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;
import com.socks.library.KLog;
import com.xlg.android.protocol.BigGiftRecord;
import com.xlg.android.protocol.JoinRoomResponse;
import com.xlg.android.protocol.MicState;
import com.xlg.android.protocol.RoomChatMsg;
import com.xlg.android.protocol.RoomKickoutUserInfo;
import com.xlg.android.protocol.RoomUserInfo;
import com.xlg.android.video.AVModuleMgr;
import com.xlg.android.video.AVNotify;
import com.xlg.android.video.AudioPlay;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.InputStream;
import java.nio.ByteBuffer;
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
import okhttp3.Call;
import sample.room.MicNotify;
import sample.room.RoomMain;

/**
 * 全屏播放页面
 */
@EActivity(R.layout.activity_test)
public class RoomLandActivity extends BaseActivity implements View.OnClickListener {

    @ViewById(R.id.test_back_btn)
    ImageView backImage;
    @ViewById(R.id.test_controll)
    RelativeLayout testController;
    @ViewById(R.id.room_control)
    RelativeLayout roomControl;
    @ViewById(R.id.room_control2)
    RelativeLayout roomControl2;
    @ViewById(R.id.room_control3)
    RelativeLayout roomControl3;
    @ViewById(R.id.test_full)
    ImageView fullImage;
    @ViewById(R.id.room_id_test)
    TextView roomIdTv;
    @ViewById(R.id.danmakuView)
    DanmakuView danmakuView;
    @ViewById(R.id.test_danmu_btn)
    ImageView danmuImage;


    SimpleDraweeView textBackImage3;
    @ViewById(R.id.text_relative)
    LinearLayout textRelative;
    @ViewById(R.id.room_change)
    TextView textChange;
    @ViewById(R.id.pl_video_1)
    PLVideoTextureView plVideo1;
    @ViewById(R.id.pl_video_2)
    PLVideoTextureView plVideo2;
    @ViewById(R.id.pl_video_3)
    PLVideoTextureView plVideo3;
    @ViewById(R.id.rll_video_land_view1)
    RelativeLayout rllVideoLandView1;
    @ViewById(R.id.rll_video_land_view2)
    RelativeLayout rllVideoLandView2;
    @ViewById(R.id.rll_video_land_view3)
    RelativeLayout rllVideoLandView3;
    private Button giftSendBtn;
    private GridView gridView;
    private ListView userList;
    private boolean isRunning = false;

    private List<RoomChatMsg> data = new ArrayList<>();

    private RoomChatAdapter adapter;

    private AVModuleMgr mgr;

    private Bitmap bmp;
    private Bitmap bmp1;
    private Bitmap bmp2;
    private int mic0 = 0;
    private int mic1 = 1;
    private int mic2 = 2;
    private static AudioPlay play = new AudioPlay();
    private boolean mStop = false;
    private RoomMain roomMain = new RoomMain();
    private EmotionInputDetector mDetector;

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
    private List<RoomUserInfo> userInfos = new ArrayList<>();
    private RoomUserInfo sendToUser;
    private UserAdapter userAdapter;
    private int micid;

    private String roomPwd;
    private String roomIp;
    private App app;
    private DanmakuContext mContext;
    private BaseDanmakuParser mParser;
    private boolean isplaying;
    private Context context;

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
            danmakuView.resume();
        }
        if (is_video1_play = false && !map_trmp_play.get(0).equals("null")) {
            plVideo1.start();
        }
        if (is_video2_play = false && !map_trmp_play.get(1).equals("null")) {
            plVideo2.start();
            plVideo2.setVolume(0.0f,0.0f);
        }
        if (is_video3_play = false && !map_trmp_play.get(2).equals("null")) {
            plVideo3.start();
            plVideo3.setVolume(0.0f,0.0f);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                mStop = false;
                if (roomMain.getRoom() != null) {
                    if (!roomMain.getRoom().isOK()) {
                        roomMain.Start(roomId, Integer.parseInt(StartUtil.getUserId(RoomLandActivity.this)), StartUtil.getUserPwd(RoomLandActivity.this), ip, port, roomPwd);
                    }
                }
            }
        }).start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
        plVideo1.pause();
        plVideo2.pause();
        plVideo3.pause();
        is_video1_play = false;
        is_video2_play = false;
        is_video3_play = false;
    }

    @Override
    protected void onDestroy() {
        isRunning = false;
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
        plVideo1.stopPlayback();
        plVideo2.stopPlayback();
        plVideo3.stopPlayback();

        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void before() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        app = (App) getApplication();
        context = this;
        roomPwd = getIntent().getStringExtra("roomPwd");
        roomIp = getIntent().getStringExtra("roomIp");
        String[] Ips = roomIp.split(";");
        String[] ports = Ips[0].split(":");
        ip = ports[0];
        port = Integer.parseInt(ports[1]);
        roomId = Integer.parseInt(getIntent().getStringExtra("roomId"));
//        Log.d("123", roomId + "roomId");
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.Start(roomId, Integer.parseInt(StartUtil.getUserId(RoomLandActivity.this)), StartUtil.getUserPwd(RoomLandActivity.this), ip, port, roomPwd);
            }
        }).start();
        EventBus.getDefault().register(this);
    }

    private boolean is_video1_play = false;
    private boolean is_video2_play = false;
    private boolean is_video3_play = false;
    private ImageView iv_cover_1, iv_cover_2, iv_cover_3;

    @Override
    public void initListener() {

        // 1 -> hw codec enable, 0 -> disable [recommended]
        int codec = AVOptions.MEDIA_CODEC_AUTO;
        setOptions(codec);
        plVideo1.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
        plVideo2.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
        plVideo3.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
        plVideo1.setOnErrorListener(mOnErrorListener1);
        plVideo2.setOnErrorListener(mOnErrorListener2);
        plVideo3.setOnErrorListener(mOnErrorListener3);
        plVideo1.setOnInfoListener(new PLMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
                if (i == 3) {
                    is_video1_play = true;
                    iv_cover_1.setVisibility(View.GONE);
                }
                return false;
            }
        });
        plVideo2.setOnInfoListener(new PLMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
                if (i == 3) {
                    is_video2_play = true;
                    iv_cover_2.setVisibility(View.GONE);
                }
                return false;
            }
        });
        plVideo3.setOnInfoListener(new PLMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
                if (i == 3) {
                    is_video3_play = true;
                    iv_cover_3.setVisibility(View.GONE);
                }
                return false;
            }
        });
        //====================在 SDK 解析出视频的尺寸信息后，会触发该回调，开发者可以在该回调中调整 UI 的视图尺寸。
        plVideo1.setOnVideoSizeChangedListener(new PLMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int width, int height, int i2, int i3) {
                if (width < height) {//竖屏 处理UI也为竖屏
                    ViewGroup.LayoutParams params = rllVideoLandView1.getLayoutParams();
                    params.width = ScreenUtils.getScreenWidth(context) /5*2;
                    rllVideoLandView1.setLayoutParams(params);
                } else {
                    ViewGroup.LayoutParams params = rllVideoLandView1.getLayoutParams();
                    params.width = ScreenUtils.getScreenWidth(context)/3*2;
                    rllVideoLandView1.setLayoutParams(params);
                }
            }
        });
        plVideo2.setOnVideoSizeChangedListener(new PLMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int width, int height, int i2, int i3) {
                if (width < height) {//竖屏 处理UI也为竖屏
                    ViewGroup.LayoutParams params = rllVideoLandView2.getLayoutParams();
                    params.width = ScreenUtils.getScreenWidth(context) / 5;
                    rllVideoLandView2.setLayoutParams(params);
                } else {
                    ViewGroup.LayoutParams params = rllVideoLandView2.getLayoutParams();
                    params.width = ScreenUtils.getScreenWidth(context)/3;
                    rllVideoLandView2.setLayoutParams(params);
                }
            }
        });
        plVideo3.setOnVideoSizeChangedListener(new PLMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int width, int height, int i2, int i3) {
                if (width < height) {//竖屏 处理UI也为竖屏
                    ViewGroup.LayoutParams params = rllVideoLandView3.getLayoutParams();
                    params.width = ScreenUtils.getScreenWidth(context) /5;
                    rllVideoLandView3.setLayoutParams(params);
                } else {
                    ViewGroup.LayoutParams params = rllVideoLandView3.getLayoutParams();
                    params.width = ScreenUtils.getScreenWidth(context)/3;
                    rllVideoLandView3.setLayoutParams(params);
                }
            }
        });
    }

    @Override
    public void initView() {
        map_trmp_play.put(0, "null");
        map_trmp_play.put(1, "null");
        map_trmp_play.put(2, "null");
        map_trmp_play_temp.put(0, "null");
        map_trmp_play_temp.put(1, "null");
        map_trmp_play_temp.put(2, "null");
        iv_cover_1 = (ImageView) findViewById(R.id.iv_back_image1);
        iv_cover_2 = (ImageView) findViewById(R.id.iv_back_image2);
        iv_cover_3 = (ImageView) findViewById(R.id.iv_back_image3);
//        RoomActivity.roomActivity.finish();
        mDetector = EmotionInputDetector.with(this)
                .setEmotionView(findViewById(R.id.emotion_layout))
                .bindToContent(findViewById(R.id.room_message_list))
                .bindToEditText((EditText) findViewById(R.id.edit_text))
                .bindToEmotionButton(findViewById(R.id.emotion_button))
                .build();
        setUpEmotionViewPager();
        initDanmu();
        danmakuView.setVisibility(View.VISIBLE);
        danmakuView.show();
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (roomMain.getRoom() != null) {
                            roomMain.getRoom().getChannel().kickOutRoom(Integer.parseInt(StartUtil.getUserId(context)));
                            roomMain.getRoom().getChannel().Close();
                        }
                    }
                }).start();
                finish();
            }
        });
        roomIdTv.setText(roomId + "");
        fullImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (roomMain.getRoom() != null) {
                            roomMain.getRoom().getChannel().kickOutRoom(Integer.parseInt(StartUtil.getUserId(context)));
                            roomMain.getRoom().getChannel().Close();
                        }
                    }
                }).start();
                finish();
            }
        });
        danmuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (danmakuView.isShown()) {
                    danmakuView.setVisibility(View.GONE);
                    danmakuView.hide();
                    danmuImage.setImageResource(R.mipmap.icon_danmu_whole_pressed);
                } else {
                    danmakuView.setVisibility(View.VISIBLE);
                    danmakuView.show();
                    danmuImage.setImageResource(R.mipmap.icon_danmu_whole_normal);
                }
            }
        });
        roomControl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = map_trmp_play.get(0);
                map_trmp_play.put(0, map_trmp_play.get(1));
                map_trmp_play.put(1, temp);
                if (!map_trmp_play.get(0).equals("null")) {
                    plVideo1.setVideoPath(map_trmp_play.get(0));
                    plVideo1.start();
                    iv_cover_1.setVisibility(View.GONE);
                } else {
                    iv_cover_1.setVisibility(View.VISIBLE);
                }
                if (!map_trmp_play.get(1).equals("null")) {
                    plVideo2.setVideoPath(map_trmp_play.get(1));
                    plVideo2.start();
                    plVideo2.setVolume(0.0f,0.0f);
                    iv_cover_2.setVisibility(View.GONE);
                } else {
                    iv_cover_2.setVisibility(View.VISIBLE);
                }
            }
        });
        roomControl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = map_trmp_play.get(0);
                map_trmp_play.put(0, map_trmp_play.get(2));
                map_trmp_play.put(2, temp);
                if (!map_trmp_play.get(0).equals("null")) {
                    plVideo1.setVideoPath(map_trmp_play.get(0));
                    plVideo1.start();
                    iv_cover_1.setVisibility(View.GONE);
                } else {
                    iv_cover_1.setVisibility(View.VISIBLE);
                }
                if (!map_trmp_play.get(2).equals("null")) {
                    plVideo3.setVideoPath(map_trmp_play.get(2));
                    plVideo3.start();
                    plVideo3.setVolume(0.0f,0.0f);
                    iv_cover_3.setVisibility(View.GONE);
                } else {
                    iv_cover_3.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!mDetector.interceptBackPress()) {
            super.onBackPressed();
        }
    }

    private void setUpEmotionViewPager() {
        final String[] titles = new String[]{"经典", "vip"};
        EmotionAdapter mViewPagerAdapter = new EmotionAdapter(getSupportFragmentManager(), titles);
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setCustomTabView(R.layout.widget_tab_indicator, R.id.text);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorPrimary));
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(mViewPager);

        GlobalOnItemClickManager globalOnItemClickListener = GlobalOnItemClickManager.getInstance();
        globalOnItemClickListener.attachToEditText((EditText) findViewById(R.id.edit_text));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_container:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
        }
    }


    //接收礼物消息更新
    @Subscriber(tag = "BigGiftRecord")
    public void getGiftRecord(BigGiftRecord obj) {
        int getGiftId = obj.getGiftid();
        int count = obj.getCount();
        String giftTxt = "";
        for (int i = 0; i < gifts.size(); i++) {
            if (getGiftId == gifts.get(i).getGiftId()) {
                if (getGiftId < 10)
                    giftTxt = "/g100" + getGiftId + "   x " + count;
                if (getGiftId >= 10 && getGiftId < 100)
                    giftTxt = "/g10" + getGiftId + "   x " + count;
                if (getGiftId >= 100)
                    giftTxt = "/g1" + getGiftId + "    x" + count;
                RoomChatMsg msg = new RoomChatMsg();
                msg.setContent("<FONT style=\"FONT-FAMILY:宋体;FONT-SIZE:14px; COLOR:#000000\">" + giftTxt + "</FONT>");
                msg.setSrcid(obj.getSrcid());
                data.add(msg);
            }
        }

    }

    @Subscriber(tag = "JoinRoomResponse")
    public void getJoinRoomResponse(JoinRoomResponse obj) {
//        topline = obj.getTopline() - 1000;
    }

    //接收服务器发送的消息更新列表
    @Subscriber(tag = "RoomChatMsg")
    public void getRoomChatMsg(RoomChatMsg msg) {
//        data.add(msg);
        if (msg.getToid() == 0 || msg.getToid() == Integer.parseInt(StartUtil.getUserId(this))) {
            Spanned spanned = Html.fromHtml(msg.getContent());
            addDanmaku(false, spanned);
        }
    }

    private void addDanmaku(boolean islive, Spanned chatmsg) {
        BaseDanmaku danmaku = mContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || danmakuView == null) {
            return;
        }
        danmaku.text = chatmsg;
        danmaku.padding = 5;
        danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
        danmaku.isLive = islive;
        danmaku.time = danmakuView.getCurrentTime() + 1200;
        danmaku.textSize = 25f * (mParser.getDisplayer().getDensity() - 0.6f);
        danmaku.textColor = Color.WHITE;
        danmaku.textShadowColor = Color.WHITE;
        danmakuView.addDanmaku(danmaku);

    }

    //用户离开房间
    @Subscriber(tag = "RoomKickoutUserInfo")
    public void getUserOut(RoomKickoutUserInfo obj) {
        int leaveId = obj.getToid();
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
        KLog.e(userInfo.getUserid() + "加入");
    }

    private HashMap<Integer, String> map_trmp_play = new HashMap<>();
    private HashMap<Integer, String> map_trmp_play_temp = new HashMap<>();

    //麦上几个人就添加视频流
    @Subscriber(tag = "onMicUser")
    public void getonMicUser(final RoomUserInfo obj) {
        micUsers.add(obj);
        KLog.e(obj.getUserid());
        String room_name = "lihao_" + obj.getUserid() + "_" + obj.getUserid();
        OkGo.<String>get(AppConstant.GET_RTMP_URL)
                .params("streamKey", room_name)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, okhttp3.Response response) {
                        RtmpEntity rtmpentity = new Gson().fromJson(s, RtmpEntity.class);
                        switch (obj.getMicindex()) {
                            case 0:
                                KLog.e("0 mic");
                                map_trmp_play.put(0, rtmpentity.getRTMPPlayURL());
                                plVideo1.setVideoPath(map_trmp_play.get(0));
                                plVideo1.start();
                                iv_cover_1.setVisibility(View.GONE);
                                break;
                            case 1:
                                KLog.e("1 mic");
                                map_trmp_play.put(1, rtmpentity.getRTMPPlayURL());
                                plVideo2.setVideoPath(map_trmp_play.get(1));
                                iv_cover_2.setVisibility(View.GONE);
                                plVideo2.start();
                                plVideo2.setVolume(0.0f,0.0f);
                                break;
                            case 2:
                                KLog.e("2 mic");
                                map_trmp_play.put(2, rtmpentity.getRTMPPlayURL());
                                plVideo3.setVideoPath(map_trmp_play.get(2));
                                iv_cover_3.setVisibility(View.GONE);
                                plVideo3.start();
                                plVideo3.setVolume(0.0f,0.0f);
                                break;
                        }
                        KLog.e(obj.getMicindex() + rtmpentity.getRTMPPlayURL());
                    }

                });
    }

    //上公麦提示   1
    @Subscriber(tag = "upMicState")
    public void upMicState(final MicState obj) {
        KLog.e("==================" + obj.getUserid());
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
                    public void onSuccess(String s, Call call, okhttp3.Response response) {
                        RtmpEntity rtmp = new Gson().fromJson(s, RtmpEntity.class);
                        switch (obj.getMicindex()) {
                            case 0:
                                KLog.e("0 mic");
                                map_trmp_play.put(0, rtmp.getRTMPPlayURL());
                                plVideo1.setVideoPath(map_trmp_play.get(0));
                                plVideo1.start();
                                iv_cover_1.setVisibility(View.GONE);
                                break;
                            case 1:
                                KLog.e("1 mic");
                                map_trmp_play.put(1, rtmp.getRTMPPlayURL());
                                plVideo2.setVideoPath(map_trmp_play.get(1));
                                plVideo2.start();
                                plVideo2.setVolume(0.0f,0.0f);
                                iv_cover_2.setVisibility(View.GONE);
                                break;
                            case 2:
                                KLog.e("2 mic");
                                map_trmp_play.put(2, rtmp.getRTMPPlayURL());
                                plVideo3.setVideoPath(map_trmp_play.get(2));
                                plVideo3.start();
                                plVideo3.setVolume(0.0f,0.0f);
                                iv_cover_3.setVisibility(View.GONE);
                                break;
                        }
                        KLog.e(obj.getMicindex() + rtmp.getRTMPPlayURL());
                    }
                });

    }

    //下麦提示
    @Subscriber(tag = "downMicState")
    public void downMicState(MicState obj) {
        for (int i = 0; i < micUsers.size(); i++) {
            if (micUsers.get(i).getUserid() == obj.getUserid()) {
                //根据micindex关闭对应号的mic
                KLog.e(micUsers.get(i).getMicindex());
                switch (micUsers.get(i).getMicindex()) {
                    case 0:
                        map_trmp_play.put(0, "null");
                        iv_cover_1.setVisibility(View.VISIBLE);
                        plVideo1.pause();
                        break;
                    case 1:
                        map_trmp_play.put(1, "null");
                        iv_cover_2.setVisibility(View.VISIBLE);
                        plVideo2.pause();
                        break;
                    case 2:
                        map_trmp_play.put(2, "null");
                        iv_cover_3.setVisibility(View.VISIBLE);
                        plVideo3.pause();
                        break;
                }
                micUsers.remove(i);
            }
        }
    }


    private List<RoomUserInfo> micUsers = new ArrayList<>();
    private String mediaIp;
    private int mediaPort;
    private int mediaRand;


    //弹幕初始化
    public void initDanmu() {
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 8); // 滚动弹幕最大显示5行
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

    private void setOptions(int codecType) {
        AVOptions options = new AVOptions();
        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
        // Some optimization with buffering mechanism when be set to 1
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
        options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, codecType);

        // whether start play automatically after prepared, default value is 1
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);

        plVideo1.setAVOptions(options);
        plVideo2.setAVOptions(options);
        plVideo3.setAVOptions(options);
    }

    private PLMediaPlayer.OnErrorListener mOnErrorListener1 = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer mp, int errorCode) {
            KLog.e(errorCode + " ");
            boolean isNeedReconnect = false;
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                    KLog.e("Invalid URL !");
                    break;
                case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                    KLog.e("404 resource not found !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                    KLog.e("Connection refused !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                    KLog.e("Connection timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                    KLog.e("Empty playlist !");
                    break;
                case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                    KLog.e("Stream disconnected !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    is_video1_play = false;
                    iv_cover_1.setVisibility(View.VISIBLE);
                    KLog.e("Network IO Error !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                    KLog.e("Unauthorized Error !");
                    break;
                case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                    KLog.e("Prepare timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                    KLog.e("Read frame timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_HW_DECODE_FAILURE:
                    setOptions(AVOptions.MEDIA_CODEC_AUTO);
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    break;
                default:
                    KLog.e("unknown error !");
                    break;
            }
            if (isNeedReconnect) {
                sendReconnectMessage(0);
            }
            return true;
        }
    };
    private PLMediaPlayer.OnErrorListener mOnErrorListener2 = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer mp, int errorCode) {
            KLog.e(errorCode + " ");
            boolean isNeedReconnect = false;
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                    KLog.e("Invalid URL !");
                    break;
                case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                    KLog.e("404 resource not found !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                    KLog.e("Connection refused !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                    KLog.e("Connection timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                    KLog.e("Empty playlist !");
                    break;
                case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                    KLog.e("Stream disconnected !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    is_video2_play = false;
                    iv_cover_2.setVisibility(View.VISIBLE);
                    KLog.e("Network IO Error !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                    KLog.e("Unauthorized Error !");
                    break;
                case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                    KLog.e("Prepare timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                    KLog.e("Read frame timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_HW_DECODE_FAILURE:
                    setOptions(AVOptions.MEDIA_CODEC_AUTO);
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    break;
                default:
                    KLog.e("unknown error !");
                    break;
            }
            if (isNeedReconnect) {
                sendReconnectMessage(1);
            }
            return true;
        }
    };
    private PLMediaPlayer.OnErrorListener mOnErrorListener3 = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer mp, int errorCode) {
            KLog.e(errorCode + " ");
            boolean isNeedReconnect = false;
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                    KLog.e("Invalid URL !");
                    break;
                case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                    KLog.e("404 resource not found !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                    KLog.e("Connection refused !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                    KLog.e("Connection timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                    KLog.e("Empty playlist !");
                    break;
                case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                    KLog.e("Stream disconnected !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    is_video3_play = false;
                    iv_cover_3.setVisibility(View.VISIBLE);
                    KLog.e("Network IO Error !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                    KLog.e("Unauthorized Error !");
                    break;
                case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                    KLog.e("Prepare timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                    KLog.e("Read frame timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_HW_DECODE_FAILURE:
                    setOptions(AVOptions.MEDIA_CODEC_AUTO);
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    break;
                default:
                    KLog.e("unknown error !");
                    break;
            }
            if (isNeedReconnect) {
                sendReconnectMessage(2);
            }
            return true;
        }
    };

    private void sendReconnectMessage(int flag) {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_ID_RECONNECTING, flag), 2000);
    }

    private static final int MESSAGE_ID_RECONNECTING = 0x01;
    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_ID_RECONNECTING:
                    if (NetUtils.isNetworkAvailable(context)) {
                        int micFlag = (int) msg.obj;
                        switch (micFlag) {
                            case 0:
                                plVideo1.setVideoPath(map_trmp_play.get(0));
                                plVideo1.start();
                                iv_cover_1.setVisibility(View.GONE);
                                if (!is_video1_play)
                                    sendReconnectMessage(0);
                                break;
                            case 1:
                                plVideo2.setVideoPath(map_trmp_play.get(1));
                                plVideo2.start();
                                plVideo2.setVolume(0.0f,0.0f);
                                iv_cover_2.setVisibility(View.GONE);
                                if (!is_video2_play)
                                    sendReconnectMessage(1);
                                break;
                            case 2:
                                plVideo3.setVideoPath(map_trmp_play.get(2));
                                plVideo3.start();
                                plVideo3.setVolume(0.0f,0.0f);
                                iv_cover_3.setVisibility(View.GONE);
                                if (!is_video3_play)
                                    sendReconnectMessage(2);
                                break;
                        }
                        return;
                    } else {
                        ToastUtil.show(context, R.string.net_error);
                    }
                    break;
            }
        }
    };
}