package com.fubang.lihaovv.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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

import com.alibaba.livecloud.live.AlivcMediaFormat;
import com.alibaba.livecloud.live.AlivcMediaRecorder;
import com.alibaba.livecloud.live.AlivcMediaRecorderFactory;
import com.alibaba.livecloud.live.AlivcRecordReporter;
import com.alibaba.livecloud.live.AlivcStatusCode;
import com.alibaba.livecloud.live.OnLiveRecordErrorListener;
import com.alibaba.livecloud.live.OnNetworkStatusListener;
import com.alibaba.livecloud.live.OnRecordStatusListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.facebook.drawee.gestures.GestureDetector;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fubang.lihaovv.App;
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.R;
import com.fubang.lihaovv.SlidingTab.EmotionInputDetector;
import com.fubang.lihaovv.SlidingTab.SlidingTabLayout;
import com.fubang.lihaovv.adapters.EmotionAdapter;
import com.fubang.lihaovv.adapters.GiftAdapter;
import com.fubang.lihaovv.adapters.GiftNewAdapter;
import com.fubang.lihaovv.adapters.HomeTitleAdapter;
import com.fubang.lihaovv.adapters.MyPagerAdapter;
import com.fubang.lihaovv.adapters.MyTabFragmentPagerAdapter;
import com.fubang.lihaovv.adapters.RoomChatAdapter;
import com.fubang.lihaovv.adapters.UserAdapter;
import com.fubang.lihaovv.entities.FaceEntity;
import com.fubang.lihaovv.entities.GiftEntity;
import com.fubang.lihaovv.entities.GiftNewEntity;
import com.fubang.lihaovv.entities.RtmpEntity;
import com.fubang.lihaovv.fragment.CommonFragment_;
import com.fubang.lihaovv.fragment.LookFragment_;
import com.fubang.lihaovv.fragment.MicQuenFragment_;
import com.fubang.lihaovv.fragment.PersonFragment_;
import com.fubang.lihaovv.fragment.tabgiftfragment.TabFragment1;
import com.fubang.lihaovv.fragment.tabgiftfragment.TabFragment2;
import com.fubang.lihaovv.fragment.tabgiftfragment.TabFragment3;
import com.fubang.lihaovv.fragment.tabgiftfragment.TabFragment4;
import com.fubang.lihaovv.fragment.tabgiftfragment.TabFragment5;
import com.fubang.lihaovv.fragment.tabgiftfragment.TabFragment6;
import com.fubang.lihaovv.fragment.tabgiftfragment.TabFragment7;
import com.fubang.lihaovv.utils.FileUtils;
import com.fubang.lihaovv.utils.GiftUtil;
import com.fubang.lihaovv.utils.GlobalOnItemClickManager;
import com.fubang.lihaovv.utils.NetUtils;
import com.fubang.lihaovv.utils.ScreenUtils;
import com.fubang.lihaovv.utils.StringUtil;
import com.fubang.lihaovv.utils.ToastUtil;
import com.fubang.lihaovv.utils.Utils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;
import com.socks.library.KLog;
import com.umeng.analytics.MobclickAgent;
import com.viewpagerindicator.CirclePageIndicator;
import com.xlg.android.protocol.ActWaitMicUserInfo;
import com.xlg.android.protocol.BigGiftRecord;
import com.xlg.android.protocol.Header;
import com.xlg.android.protocol.JoinRoomResponse;
import com.xlg.android.protocol.MicState;
import com.xlg.android.protocol.RoomChatMsg;
import com.xlg.android.protocol.RoomKickoutUserInfo;
import com.xlg.android.protocol.RoomUserInfo;
import com.xlg.android.protocol.RoomVideoInfo;
import com.xlg.android.video.AudioPlay;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.yj.media.AudioCapture;
import org.yj.media.AudioCaptureCallback;
import org.yj.media.EncodeAAC;
import org.yj.media.EncodeH264;
import org.yj.media.Media;
import org.yj.media.MediaData;
import org.yj.media.PushRtmp;
import org.yj.media.RecvRtmp;
import org.yj.media.RecvRtmpCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import me.wcy.lrcview.LrcView;
import okhttp3.Call;
import sample.room.RoomMain;

@EActivity(R.layout.activity_room)
public class RoomActivity extends BaseActivity {

    @ViewById(R.id.ll_room_content)
    RelativeLayout llRoomContent;
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
    @ViewById(R.id.rll_control_view)
    RelativeLayout rllControlView;

    @ViewById(R.id.test_new_full)
    ImageView fullImage;
    @ViewById(R.id.iv_room_setting)
    ImageView ivRoomSetting;
    @ViewById(R.id.room_new_id_test)
    TextView roomIdTv;
    @ViewById(R.id.follow_new_image)
    ImageView followImage;
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
    //=========================视频静音、图像遮挡控制
    @ViewById(R.id.room_control_mute)
    ImageView roomControlMute;
    @ViewById(R.id.room_control_no_img)
    ImageView roomControlNoImg;
    //=========================上麦用户控制布局
    @ViewById(R.id.rll_mic_user_control_view)
    RelativeLayout rllMicUserControlView;
    @ViewById(R.id.ll_mic_user_control_view)
    LinearLayout llMicUserControlView;
    @ViewById(R.id.iv_mic_user_control_music)
    ImageView ivMicUserControlMusic;
    @ViewById(R.id.iv_mic_user_control_camera)
    ImageView ivMicUserControlCamera;
    @ViewById(R.id.iv_mic_user_control_beauty)
    ImageView ivMicUserControlBeauty;
    //========================歌词布局
    @ViewById(R.id.rll_live_lrc)
    protected RelativeLayout rllLiveLrc;
    @ViewById(R.id.lrc_live)
    protected LrcView lrcLive;
    @ViewById(R.id.tv_live_lrc_cancle)
    protected TextView tvLiveLrcCancle;
    //礼物布局
    @ViewById(R.id.ll_gift_layout_content)
    protected LinearLayout llGiftLayoutContent;
    @ViewById(R.id.tv_gift_gone)
    protected TextView tvGiftGone;

    PLVideoTextureView plVider1;
    PLVideoTextureView plVider2;
    PLVideoTextureView plVider3;
    private Button giftSendBtn;
    private GridView gridView;
    private ListView userList;
    private boolean isRunning = false;

    private List<RoomChatMsg> data = new ArrayList<>();

    private RoomChatAdapter adapter;


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
    private List<GiftNewEntity> gifts = new ArrayList<>();
    private List<FaceEntity> faces = new ArrayList<>();
    private boolean isShow = false;
    private int toid;
    private int giftId = -1;
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
    private View mLoadingView;
    private ImageView iv_cover_1, iv_cover_2, iv_cover_3;
    private RelativeLayout rll_video1, rll_video2, rll_video3;
    private boolean is_personal_chat = false;

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        MobclickAgent.onResume(this);
        configuration = getResources().getConfiguration();
        switch (micFlag) {
            case 0:
                if (!map_trmp_play.get(0).equals("null"))
                    plVider1.start();
                break;
            case 1:
                if (!map_trmp_play.get(1).equals("null"))
                    plVider2.start();
                break;
            case 2:
                if (!map_trmp_play.get(2).equals("null"))
                    plVider3.start();
                break;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                mStop = false;
                if (roomMain.getRoom() != null) {
                    if (!roomMain.getRoom().isOK()) {
                        roomMain.getRoom().getChannel().Close();
                        roomMain.Start(roomId, Integer.parseInt(StartUtil.getUserId(RoomActivity.this)), StartUtil.getUserPwd(RoomActivity.this), ip, port, pwd);
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        isRunning = false;
        mStop = true;
        switch (micFlag) {
            case 0:
                plVider1.pause();
                break;
            case 1:
                plVider2.pause();
                break;
            case 2:
                plVider3.pause();
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
        if (userInfos != null) {
            userInfos.clear();
            userInfos = null;
        }

        isRunning = false;
        plVider1.stopPlayback();
        plVider2.stopPlayback();
        plVider3.stopPlayback();
        if (mMediaRecorder != null) {
            mMediaRecorder.release();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        handler.removeCallbacksAndMessages(null);
        mHandler.removeCallbacksAndMessages(null);
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
                KLog.e(StartUtil.getUserId(RoomActivity.this) + " " + StartUtil.getUserPwd(RoomActivity.this));
                roomMain.Start(roomId, Integer.parseInt(StartUtil.getUserId(RoomActivity.this)), StartUtil.getUserPwd(RoomActivity.this), ip, port, pwd);
            }
        }).start();
        gifts = GiftUtil.getAllGift();
    }


    @Override
    public void initView() {
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.page_surface, null);
        view2 = inflater.inflate(R.layout.page_surface2, null);
        view3 = inflater.inflate(R.layout.page_surface3, null);
        plVider1 = (PLVideoTextureView) view1.findViewById(R.id.VideoView1);
        plVider2 = (PLVideoTextureView) view2.findViewById(R.id.VideoView2);
        plVider3 = (PLVideoTextureView) view3.findViewById(R.id.VideoView3);
        iv_cover_1 = (ImageView) view1.findViewById(R.id.iv_cover_1);
        iv_cover_2 = (ImageView) view2.findViewById(R.id.iv_cover_2);
        iv_cover_3 = (ImageView) view3.findViewById(R.id.iv_cover_3);
        rll_video1 = (RelativeLayout) view1.findViewById(R.id.rll_video_view1);
        rll_video2 = (RelativeLayout) view2.findViewById(R.id.rll_video_view2);
        rll_video3 = (RelativeLayout) view3.findViewById(R.id.rll_video_view3);
        initDanmu();
        for (int i = 0; i < AppConstant.ROOM_TYPE_TITLE.length; i++) {
            titles.add(AppConstant.ROOM_TYPE_TITLE[i]);
        }
        fragments.add(CommonFragment_.builder().arg(AppConstant.HOME_TYPE, titles.get(0)).build());
        fragments.add(PersonFragment_.builder().arg(AppConstant.HOME_TYPE, titles.get(1)).build());
        fragments.add(LookFragment_.builder().arg(AppConstant.HOME_TYPE, titles.get(2)).build());
        fragments.add(MicQuenFragment_.builder().arg(AppConstant.HOME_TYPE, titles.get(1)).build());
        roomIdTv.setText(R.string.this_mic_no_anchor);
        roomActivity = this;
        mDetector = EmotionInputDetector.with(this)
                .setEmotionView(emotionLayout)
                .bindToContent(chatLine)
                .bindToEditText(roomMessageEdit)
                .bindToEmotionButton(emotionBtn)
                .build();
        setUpEmotionViewPager();
        _CameraSurface = (SurfaceView) findViewById(R.id.sv_upmic);
    }

    private boolean is_video1_play = false;
    private boolean is_video2_play = false;
    private boolean is_video3_play = false;
    private boolean is_control_view_show = true;

    @Override
    public void initListener() {
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
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLoadingView = findViewById(R.id.LoadingView);
        plVider1.setBufferingIndicator(mLoadingView);
        plVider2.setBufferingIndicator(mLoadingView);
        plVider3.setBufferingIndicator(mLoadingView);
//        plVider1.setCoverView(findViewById(R.id.CoverView1));
//        plVider1.setCoverView(findViewById(R.id.CoverView2));
//        plVider1.setCoverView(findViewById(R.id.CoverView3));
        // 1 -> hw codec enable, 0 -> disable [recommended]
        final int codec = AVOptions.MEDIA_CODEC_AUTO;
        setOptions(codec);
        plVider1.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_ORIGIN);
        plVider2.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_ORIGIN);
        plVider3.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_ORIGIN);

        plVider1.setOnErrorListener(mOnErrorListener);
        plVider2.setOnErrorListener(mOnErrorListener);
        plVider3.setOnErrorListener(mOnErrorListener);
        plVider1.setOnInfoListener(new PLMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
                if (i == 3) {
                    is_video1_play = true;
                    iv_cover_1.setVisibility(View.GONE);
                }
                return false;
            }
        });
        plVider2.setOnInfoListener(new PLMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
                if (i == 3) {
                    is_video2_play = true;
                    iv_cover_2.setVisibility(View.GONE);
                }
                return false;
            }
        });
        plVider3.setOnInfoListener(new PLMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
                if (i == 3) {
                    is_video3_play = true;
                    iv_cover_3.setVisibility(View.GONE);
                }
                return false;
            }
        });
        plVider1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.e("12131");
            }
        });
        //====================在 SDK 解析出视频的尺寸信息后，会触发该回调，开发者可以在该回调中调整 UI 的视图尺寸。
        plVider1.setOnVideoSizeChangedListener(new PLMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int width, int height, int i2, int i3) {
                if (width < height) {//竖屏 处理UI也为竖屏
                    ViewGroup.LayoutParams params = rll_video1.getLayoutParams();
                    params.width = ScreenUtils.getScreenWidth(context) / 9 * 5;
                    rll_video1.setLayoutParams(params);
                } else {
                    ViewGroup.LayoutParams params = rll_video1.getLayoutParams();
                    params.width = ScreenUtils.getScreenWidth(context);
                    rll_video1.setLayoutParams(params);
                }
            }
        });
        plVider2.setOnVideoSizeChangedListener(new PLMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int width, int height, int i2, int i3) {
                if (width < height) {//竖屏 处理UI也为竖屏
                    ViewGroup.LayoutParams params = rll_video2.getLayoutParams();
                    params.width = ScreenUtils.getScreenWidth(context) / 9 * 5;
                    rll_video2.setLayoutParams(params);
                } else {
                    ViewGroup.LayoutParams params = rll_video2.getLayoutParams();
                    params.width = ScreenUtils.getScreenWidth(context);
                    rll_video2.setLayoutParams(params);
                }
            }
        });
        plVider3.setOnVideoSizeChangedListener(new PLMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int width, int height, int i2, int i3) {
                if (width < height) {//竖屏 处理UI也为竖屏
                    ViewGroup.LayoutParams params = rll_video3.getLayoutParams();
                    params.width = ScreenUtils.getScreenWidth(context) / 9 * 5;
                    rll_video3.setLayoutParams(params);
                } else {
                    ViewGroup.LayoutParams params = rll_video3.getLayoutParams();
                    params.width = ScreenUtils.getScreenWidth(context);
                    rll_video3.setLayoutParams(params);
                }
            }
        });
        plVider1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_control_view_show) {
                    rllControlView.setVisibility(View.VISIBLE);
                } else {
                    rllControlView.setVisibility(View.GONE);
                }
                KLog.e(is_control_view_show ? "123" : "456");
                is_control_view_show = !is_control_view_show;
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                micFlag = position;
                switch (micFlag) {
                    case 0:
                        if (!map_trmp_play.get(0).equals("null"))
                            plVider1.start();
                        plVider2.pause();
                        plVider3.pause();
                        rllMicUserControlView.setVisibility(View.GONE);
                        if (micUsers.size() > 0) {
                            if (micUsers.get(0).getUserid() == Integer.parseInt(StartUtil.getUserId(context))) {//当前麦主播是自己。显示视频控制view
                                rllMicUserControlView.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                    case 1:
                        plVider1.pause();
                        if (!map_trmp_play.get(1).equals("null"))
                            plVider2.start();
                        plVider3.pause();
                        rllMicUserControlView.setVisibility(View.GONE);
                        if (micUsers.size() > 1) {
                            if (micUsers.get(1).getUserid() == Integer.parseInt(StartUtil.getUserId(context))) {//当前麦主播是自己。显示视频控制view
                                rllMicUserControlView.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                    case 2:
                        plVider1.pause();
                        plVider2.pause();
                        if (!map_trmp_play.get(2).equals("null"))
                            plVider3.start();
                        rllMicUserControlView.setVisibility(View.GONE);
                        if (micUsers.size() > 2) {
                            if (micUsers.get(2).getUserid() == Integer.parseInt(StartUtil.getUserId(context))) {//当前麦主播是自己。显示视频控制view
                                rllMicUserControlView.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                }
                if (micUsers != null) {
                    for (int i = 0; i < micUsers.size(); i++) {
                        if (micUsers.get(i).getMicindex() == micFlag) {
                            if (giftToUser != null) {
                                giftToUser.setText(micUsers.get(i).getUseralias() + "(" + micUsers.get(i).getUserid() + ")");
                            }
                        }
                    }
                }
                boolean has_mic_user = false;
                //当前麦主播名字
                for (int i = 0; i < micUsers.size(); i++) {
                    if (micUsers.get(i).getMicindex() == micFlag) {
                        roomIdTv.setText((micFlag + 1) + "麦主播：" + micUsers.get(i).getUseralias() + " ");
                        has_mic_user = true;
                    }
                }
                if (!has_mic_user) {
                    roomIdTv.setText(R.string.this_mic_no_anchor);
                    has_mic_user = false;
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (roomMain.getRoom() != null) {
                            roomMain.getRoom().getChannel().kickOutRoom(Integer.parseInt(StartUtil.getUserId(context)));
                            roomMain.getRoom().getChannel().Close();
                        }
                    }
                }).start();
                is_goto_full = true;
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
                if (chatToFlag == 0) {//切换到公聊，则取消私聊
                    is_personal_chat = false;
                } else if (chatToFlag == 1) {//切换到私聊
                    is_personal_chat = true;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tvGiftGone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llGiftLayoutContent.setVisibility(View.GONE);
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


    private TextView giftToUser;
    private boolean is_mic_have = false;
    private int send_gift_type;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<Fragment> fragments_gift;
    private List<View> list_giftview = new ArrayList<>();
    private boolean is_first_gift = false;
    private TextView giftName;
    private EditText giftCount;

    //礼物的悬浮框
    private void showWindow() {
        llGiftLayoutContent.setVisibility(View.VISIBLE);
        if (is_first_gift == false) {
            mTabLayout = (TabLayout) findViewById(R.id.tab_main);
            ViewPager mViewPager = (ViewPager) findViewById(R.id.vp_gift_content);
            handlelGiftFragment(mViewPager);
            giftSendBtn = (Button) findViewById(R.id.gift_send_btn);
            giftName = (TextView) findViewById(R.id.gift_name_txt);
            giftCount = (EditText) findViewById(R.id.gift_count);
            giftToUser = (TextView) findViewById(R.id.gift_to_user);
            is_first_gift = true;
        }
        //选择礼物
        if (micUsers != null) {
            for (int i = 0; i < micUsers.size(); i++) {
                if (micUsers.get(i).getMicindex() == micFlag) {
                    giftToUser.setText(micUsers.get(i).getUseralias() + "(" + micUsers.get(i).getUserid() + ")");
                    is_mic_have = true;
                }
            }
        }
        //发送礼物
        giftSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (giftId == -1) {
                    ToastUtil.show(context, "请先选择礼物");
                    return;
                }
                if (send_gift_type == 1) {//送给主播的礼物

                    if (!is_mic_have) {
                        ToastUtil.show(context, "当前麦没有主播");
                        return;
                    }
                    toid = -1;
                    toName = "";
//                if (giftToUser.getText().toString().equals("1")){
                    for (int i = 0; i < micUsers.size(); i++) {
                        if (micUsers.get(i).getMicindex() == micFlag) {
                            toid = micUsers.get(i).getUserid();
                            toName = micUsers.get(i).getUseralias();
                        }
                    }
                } else if (send_gift_type == 2) {//送给个人的礼物
                    toid = persongift.getUserid();
                    toName = persongift.getUseralias();
                }
                final int count = Integer.parseInt(giftCount.getText().toString());
                KLog.e("toid--" + toid + "---giftId---" + giftId + "---count---" + count + "---toName---" + toName);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        roomMain.getRoom().getChannel().sendGiftRecord(toid, giftId, count, toName, StartUtil.getUserName(RoomActivity.this));
                        giftId = -1;
                    }
                }).start();
                is_mic_have = false;
                giftName.setText("送给");
                llGiftLayoutContent.setVisibility(View.GONE);
//                popupWindow.dismiss();、、
            }
        });
//        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        ColorDrawable dw = new ColorDrawable(0xffffffff);
//        popupWindow.setBackgroundDrawable(dw);
//        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
//        int height = wm.getDefaultDisplay().getHeight();
//        popupWindow.setHeight(height / 2);
//        popupWindow.setOutsideTouchable(true);
    }

    private MyTabFragmentPagerAdapter mMyTabFragmentPagerAdapter;

    private void handlelGiftFragment(ViewPager mViewPager) {
        fragments_gift = new ArrayList<Fragment>();
        fragments_gift.clear();
        fragments_gift.add(new TabFragment1());
        fragments_gift.add(new TabFragment2());
        fragments_gift.add(new TabFragment3());
        fragments_gift.add(new TabFragment4());
        fragments_gift.add(new TabFragment5());
        fragments_gift.add(new TabFragment6());
        fragments_gift.add(new TabFragment7());
        mMyTabFragmentPagerAdapter = new MyTabFragmentPagerAdapter(getSupportFragmentManager()
                , fragments_gift);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(mMyTabFragmentPagerAdapter);
        //将TabLayout和ViewPager绑定在一起，使双方各自的改变都能直接影响另一方，解放了开发人员对双方变动事件的监听
        mTabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public void initData() {
        map_trmp_play.put(0, "null");
        map_trmp_play.put(1, "null");
        map_trmp_play.put(2, "null");
        HomeTitleAdapter adapter = new HomeTitleAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager_content.setAdapter(adapter);
        viewPager_content.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager_content);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //发送聊天消息
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText())) {
                    final String msgText = editText.getText().toString();
                    if (!is_personal_chat) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                roomMain.getRoom().getChannel().sendChatMsg(0, (byte) 0, (byte) 0, msgText, StartUtil.getUserName(RoomActivity.this), Integer.parseInt(StartUtil.getUserLevel(RoomActivity.this)));
                            }
                        }).start();
                        viewPager_content.setCurrentItem(0);
                    } else if (is_personal_chat) {
                        if (sendToUser == null) {
                            ToastUtil.show(context, "私聊必须先选择一个发送对象");
                            return;
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                roomMain.getRoom().getChannel().sendChatMsg(sendToUser.getUserid(), (byte) 0, (byte) 1, msgText, StartUtil.getUserName(RoomActivity.this), Integer.parseInt(StartUtil.getUserLevel(RoomActivity.this)));
                            }
                        }).start();
                        viewPager_content.setCurrentItem(1);
                    }
                    editText.setText("");
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(RoomActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }

    //添加弹幕
    private void addDanmaku(boolean islive, String chatmsg) {
        BaseDanmaku danmaku = mContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || danmakuView == null) {
            return;
        }
        KLog.e(chatmsg);
        danmaku.text = chatmsg;
        danmaku.priority = 1;  // 可能会被各种过滤器过滤并隐藏显示
        danmaku.isLive = islive;
        danmaku.time =  danmakuView.getCurrentTime()+1000;
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
        mContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(2.2f).setScaleTextSize(1.3f)
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
    @Subscriber(tag = "gift_select")
    public void Giftselect(GiftNewEntity obj) {
        giftId = obj.getDate().getGiftId();
        KLog.e(giftId + " ");
    }

    //接收跑道礼物消息  其他礼物在CommonFragment中处理
    @Subscriber(tag = "BigGiftRecord")
    public void getGiftRecord(BigGiftRecord obj) {
        if (obj.getFlyid() >= 0 && obj.getReserve() == 0) {//上跑道礼物
            for (GiftNewEntity giftNewEntity : gifts) {
                if (giftNewEntity.getDate().getGiftId() == obj.getGiftid()) {
                    addDanmaku(true, obj.getSrcalias() + "送给" + obj.getToalias() + "【" + obj.getCount() + "】" + "  个 【" + giftNewEntity.getDate().getGiftName() + "】");
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
//        rllControlView.setVisibility(View.GONE);
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
        rllControlView.setVisibility(View.VISIBLE);
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
    @Subscriber(tag = "PersonChat")
    public void PersonChat(RoomUserInfo obj) {
        is_personal_chat = true;
        sendToUser = obj;
        viewPager_content.setCurrentItem(1, true);
    }

    private RoomUserInfo persongift;

    //个人礼物
    @Subscriber(tag = "PersonGift")
    public void PersonGift(RoomUserInfo obj) {
        persongift = obj;
        send_gift_type = 2;
        showWindow();
//        popupWindow.showAsDropDown(giftImage);
    }

    //踢出房间
    @Subscriber(tag = "KickOut")
    public void getKickOut(final RoomUserInfo obj) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (obj.getUserid() != Integer.parseInt(StartUtil.getUserId(context))) {
                    roomMain.getRoom().getChannel().kickOutRoom(obj.getUserid());
                }
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
                            roomMain.getRoom().getChannel().Close();
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
        } else if (err == 405) {
            ToastUtil.show(context, "帐号密码错误");
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
                        roomMain.getRoom().getChannel().Close();
                        roomMain.Start(roomId, Integer.parseInt(StartUtil.getUserId(RoomActivity.this)), StartUtil.getUserPwd(RoomActivity.this), ip, port, pwd);
                    }
                }
            }).start();
            connectNumbaer++;
        }
    }

    //网络异常，断开房间
    @Subscriber(tag = "onDisconnected")
    public void onDisconnected(String msg) {
        if (!roomMain.getRoom().isOK()) {
            sendRoomReconnectMessage();
        }
    }

    //加入房间返回消息
    @Subscriber(tag = "JoinRoomResponse")
    public void getJoinRoomResponse(JoinRoomResponse obj) {
        mic_guan = obj.getNvideowndtype();
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
        if (msg.getMsgtype() == 12 || msg.getSrcid() == 2) {
            addDanmaku(true, msg.getContent());
        }
    }

    //管麦的int 获取
    @Subscriber(tag = "onRoomVideoNotify")
    public void onRoomVideoNotify(RoomVideoInfo msg) {
        KLog.e("管麦：" + msg.getNvideowndtype());
        mic_guan = msg.getNvideowndtype();
    }

    private boolean is_goto_full = false;

    //用户离开房间
    @Subscriber(tag = "RoomKickoutUserInfo")
    public void getUserOut(RoomKickoutUserInfo obj) {
        if (Integer.parseInt(StartUtil.getUserId(context)) == obj.getToid()) {//自己被踢出，重新连接
            if (!is_goto_full) {
                KLog.e(obj.getToid() + "离开房间" + obj.getReasonid() + " " + obj.getReserve() + " " + obj.getSrcid() + " " + obj.getVcbid() + " " + obj.getToid());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                roomMain.getRoom().getChannel().Close();
                                roomMain.Start(roomId, Integer.parseInt(StartUtil.getUserId(RoomActivity.this)), StartUtil.getUserPwd(RoomActivity.this), ip, port, pwd);
                            }
                        }).start();
                    }
                }).start();
                is_goto_full = false;
            }
        } else {
            int leaveId = obj.getToid();
            KLog.e(leaveId + "离开房间" + obj.getReasonid());
            for (int i = 0; i < userInfos.size(); i++) {
                if (userInfos.get(i).getUserid() == leaveId) {
                    userInfos.remove(i);
                }
            }
            EventBus.getDefault().post(userInfos, "lookfragment_notify");
        }
    }

    private boolean is_list_hava_people = false;

    //用户加入房间
    @Subscriber(tag = "userList")
    public void getUserList(RoomUserInfo userInfo) {
        for (RoomUserInfo roomUserInfo : userInfos) {
            if (roomUserInfo.getUserid() == userInfo.getUserid()) {
                return;
            }
        }
        userInfos.add(userInfo);
        Collections.sort(userInfos, new Comparator<RoomUserInfo>() {
            @Override
            public int compare(RoomUserInfo o1, RoomUserInfo o2) {
                if (o1.getLevel1() == o2.getLevel1()) {
                    return o1.getUserid() > o2.getUserid() ? -1 : 1;
                } else {
                    return o1.getLevel1() > o2.getLevel1() ? -1 : 1;
                }
            }
        });
        EventBus.getDefault().post(userInfos, "lookfragment_notify");
        KLog.e(userInfo.getUserid() + userInfo.getUseralias() + "加入房间" + roomId);
    }

    static Camera camera;
    private long lastCapture;

    private int vWidth = 352;
    private int vHeight = 288;
    private byte[] convertCache = new byte[vWidth * vHeight * 3 / 2];

    private EncodeH264 h264;
    private AudioCapture audio;
    private EncodeAAC aac;
    private PushRtmp rtmp_push;
    private String push_url;

    //取消麦序上的麦
    @Subscriber(tag = "CancleMicQuen")
    public void CancleMicQuen(final RoomUserInfo obj) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.getRoom().getChannel().cancleMicQueue(obj.getUserid());
            }
        }).start();
    }

    //取消麦序的回调，重新获取麦序
    @Subscriber(tag = "onActWaitMicUserNotify")
    public void onActWaitMicUserNotify(final ActWaitMicUserInfo obj) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.getRoom().getChannel().getMicList();
            }
        }).start();
    }

    //上公麦提示   1
    @Subscriber(tag = "upMicState")
    public void upMicState(final MicState obj) {

        if (obj.getUserid() == 8001 || obj.getUserid() == 8002 || obj.getUserid() == 8003) {//守麦员 不进行播放
            return;
        }
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
        //设置mic 主播名字
        for (int i = 0; i < micUsers.size(); i++) {
            if (micFlag == micUsers.get(i).getMicindex()) ;
            {
                roomIdTv.setText((micFlag + 1) + "麦主播：" + micUsers.get(i).getUseralias() + " ");
            }
        }
        KLog.e("udmic" + obj.getUdmic());

        String room_name = "BoBo_" + roomId + "_" + obj.getUserid() + "_" + obj.getUdmic();
        OkGo.<String>get(AppConstant.GET_RTMP_URL)
                .params("streamKey", room_name)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, okhttp3.Response response) {
                        RtmpEntity rtmp = new Gson().fromJson(s, RtmpEntity.class);
                        //如果上公麦的id是自己，则执行上麦逻辑
                        if (Integer.parseInt(StartUtil.getUserId(context)) == obj.getUserid()) {
                            try {
                                push_url = rtmp.getPublishUrl();
                                _CameraSurface.setVisibility(View.VISIBLE);
//                                animaView(llMicUserControlView);
//                                rllMicUserControlView.setVisibility(View.VISIBLE);
                                //通知麦序fragment  已经上麦了
                                EventBus.getDefault().post("is_upmic", "is_upmic");
//                                rllControlView.setVisibility(View.GONE);
                                if (is_surface_creat) {
                                    mMediaRecorder.startRecord(push_url);
                                } else {
                                    initCameraView();//初始化推流
                                }
                            } catch (Exception e) {
                            }
                        }
                        switch (obj.getMicindex()) {
                            case 0:
                                if (micFlag == 0) {
                                    if (Integer.parseInt(StartUtil.getUserId(context)) == obj.getUserid()) {
                                        rllMicUserControlView.setVisibility(View.VISIBLE);
                                    }
                                }
                                KLog.e("0 mic");
                                map_trmp_play.put(0, rtmp.getRTMPPlayURL());
                                plVider1.setVideoPath(map_trmp_play.get(0));
                                plVider1.start();
                                break;
                            case 1:
                                if (micFlag == 1) {
                                    if (Integer.parseInt(StartUtil.getUserId(context)) == obj.getUserid()) {
                                        rllMicUserControlView.setVisibility(View.VISIBLE);
                                    }
                                }
                                KLog.e("1 mic");
                                map_trmp_play.put(1, rtmp.getRTMPPlayURL());
                                plVider2.setVideoPath(map_trmp_play.get(1));
                                plVider2.start();
                                break;
                            case 2:
                                if (micFlag == 2) {
                                    if (Integer.parseInt(StartUtil.getUserId(context)) == obj.getUserid()) {
                                        rllMicUserControlView.setVisibility(View.VISIBLE);
                                    }
                                }
                                KLog.e("2 mic");
                                map_trmp_play.put(2, rtmp.getRTMPPlayURL());
                                plVider3.setVideoPath(map_trmp_play.get(2));
                                plVider3.start();
                                break;
                        }
                        KLog.e(obj.getMicindex() + rtmp.getRTMPPlayURL());

                    }

                });
    }

    //下麦提示
    @Subscriber(tag = "downMicState")
    public void downMicState(MicState obj) {
        KLog.e("downMicState：" + obj.getUserid());
        for (int i = 0; i < micUsers.size(); i++) {
            if (micUsers.get(i).getUserid() == obj.getUserid()) {
                //根据micindex关闭对应号的mic
                KLog.e(micUsers.get(i).getMicindex());
                switch (micUsers.get(i).getMicindex()) {
                    case 0:
                        map_trmp_play.put(0, "null");
                        iv_cover_1.setVisibility(View.VISIBLE);
                        roomIdTv.setText(R.string.this_mic_no_anchor);
                        plVider1.pause();
                        break;
                    case 1:
                        map_trmp_play.put(1, "null");
                        iv_cover_2.setVisibility(View.VISIBLE);
                        roomIdTv.setText(R.string.this_mic_no_anchor);
                        plVider2.pause();
                        break;
                    case 2:
                        map_trmp_play.put(2, "null");
                        iv_cover_3.setVisibility(View.VISIBLE);
                        roomIdTv.setText(R.string.this_mic_no_anchor);
                        plVider3.pause();
                        break;
                }
                micUsers.remove(i);
                //通知麦序fragment  已经下麦了
                EventBus.getDefault().post("is_downmic", "is_downmic");
            }
        }

        //如果上公麦的id是自己，则隐藏svupmic  并且关闭推流
        if (Integer.parseInt(StartUtil.getUserId(context)) == obj.getUserid()) {
            if (is_pushing) {
                KLog.e("downMicState");
                mMediaRecorder.stopRecord();
                mMediaRecorder.reset();
                _CameraSurface.setVisibility(View.GONE);
                rllMicUserControlView.setVisibility(View.GONE);
                is_pushing = false;
                if (mediaPlayer != null) {//关闭音乐播放器
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        mediaPlayer.reset();
                        lrcLive.onDrag(0);
                        rllLiveLrc.setVisibility(View.GONE);
                    }
                }
                rllLiveLrc.setVisibility(View.GONE);
                rllControlView.setVisibility(View.VISIBLE);
            }
        }
    }

    private HashMap<Integer, String> map_trmp_play = new HashMap<>();


    //麦上几个人就添加视频流
    @Subscriber(tag = "onMicUser")
    public void getonMicUser(final RoomUserInfo obj) {
        if (obj.getUserid() == 8001 || obj.getUserid() == 8002 || obj.getUserid() == 8003) {//守麦员 不进行播放
            return;
        }
        micUsers.add(obj);
        KLog.e("udmic" + obj.getUdmic());
        String room_name = "BoBo_" + roomId + "_" + obj.getUserid() + "_" + obj.getUdmic();
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
                                plVider1.setVideoPath(map_trmp_play.get(0));
                                plVider1.start();
                                break;
                            case 1:
                                KLog.e("1 mic");
                                map_trmp_play.put(1, rtmpentity.getRTMPPlayURL());
                                plVider2.setVideoPath(map_trmp_play.get(1));
                                plVider2.start();
                                break;
                            case 2:
                                KLog.e("2 mic");
                                map_trmp_play.put(2, rtmpentity.getRTMPPlayURL());
                                plVider3.setVideoPath(map_trmp_play.get(2));
                                plVider3.start();
                                break;
                        }
                        KLog.e(obj.getMicindex() + rtmpentity.getRTMPPlayURL());
                    }
                });
        //设置mic 主播名字
        if (micFlag == obj.getMicindex()) {
            roomIdTv.setText((micFlag + 1) + "麦主播：" + obj.getUseralias() + " ");
        }
    }

    private boolean is_mic_control_view_show = true;
    private boolean is_beautify = true;
    private int REQUEST_CODE_MUSIC = 0x6;
    private boolean is_play1_mute = false;
    private boolean is_play2_mute = false;
    private boolean is_play3_mute = false;
    private boolean is_play1_show = false;
    private boolean is_play2_show = false;
    private boolean is_play3_show = false;

    @Click({R.id.linear_new_container, R.id.chat_image_btn, R.id.room_new_gift, R.id.iv_room_setting,
            R.id.iv_mic_user_control_music, R.id.iv_mic_user_control_camera
            , R.id.iv_mic_user_control_beauty, R.id.tv_live_lrc_cancle, R.id.room_control_mute, R.id.room_control_no_img})
    void click(View v) {
        switch (v.getId()) {
            case R.id.linear_new_container:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.chat_image_btn:
                if (popupWindow != null) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
                if (faceWindow.isShowing()) {
                    faceWindow.dismiss();
                } else {
                    faceWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                }
                break;
            //点击礼物图标
            case R.id.room_new_gift:
                send_gift_type = 1;
//                if (popupWindow == null) {
                showWindow();
//                    popupWindow.showAsDropDown(giftImage);
//                } else {
//                    if (popupWindow.isShowing()) {
//                        popupWindow.dismiss();
//                    } else {
//                        popupWindow.showAsDropDown(giftImage);
//                        if (micUsers != null) {
//                            for (int i = 0; i < micUsers.size(); i++) {
//                                if (micUsers.get(i).getMicindex() == micFlag) {
//                                    giftToUser.setText(micUsers.get(i).getUseralias() + "(" + micUsers.get(i).getUserid() + ")");
//                                    is_mic_have = true;
//                                }
//                            }
//                        }
//                    }
//                }
                break;
            case R.id.iv_room_setting:
                View contentView = LayoutInflater.from(this).inflate(R.layout.pop_room_control, null);
                handleControlView(contentView);

                break;
//            case R.id.rll_mic_user_control_view:
//                if (is_mic_control_view_show) {
//                    llMicUserControlView.setVisibility(View.VISIBLE);
//                } else {
//                    llMicUserControlView.setVisibility(View.GONE);
//                }
//                is_mic_control_view_show = !is_mic_control_view_show;
//                break;
            case R.id.iv_mic_user_control_music:
                startActivityForResult(new Intent(context, LivePickMusicActivity.class), REQUEST_CODE_MUSIC);
                break;
            case R.id.iv_mic_user_control_camera:
                mMediaRecorder.switchCamera();
                break;
            case R.id.iv_mic_user_control_beauty:
                if (is_beautify) {
                    mMediaRecorder.removeFlag(AlivcMediaFormat.FLAG_BEAUTY_ON);
                } else {
                    mMediaRecorder.addFlag(AlivcMediaFormat.FLAG_BEAUTY_ON);
                }
                is_beautify = !is_beautify;
                break;
            case R.id.tv_live_lrc_cancle:
                mediaPlayer.pause();
                mediaPlayer.reset();
                lrcLive.onDrag(0);
                rllLiveLrc.setVisibility(View.GONE);
                break;
            case R.id.room_control_mute:
                switch (micFlag) {
                    case 0:
                        if (!is_play1_mute) {
                            plVider1.setVolume(0.0f, 0.0f);
                        } else {
                            plVider1.setVolume(0.8f, 0.8f);
                        }
                        is_play1_mute = !is_play1_mute;
                        break;
                    case 1:
                        if (!is_play2_mute) {
                            plVider2.setVolume(0.0f, 0.0f);
                        } else {
                            plVider2.setVolume(0.8f, 0.8f);
                        }
                        is_play2_mute = !is_play2_mute;
                        break;
                    case 2:
                        if (!is_play3_mute) {
                            plVider3.setVolume(0.0f, 0.0f);
                        } else {
                            plVider3.setVolume(0.8f, 0.8f);
                        }
                        is_play3_mute = !is_play3_mute;
                        break;
                }
                break;
            case R.id.room_control_no_img:
                switch (micFlag) {
                    case 0:
                        if (!is_play1_show) {
                            iv_cover_1.setVisibility(View.GONE);
                        } else {
                            iv_cover_1.setVisibility(View.VISIBLE);
                        }
                        is_play1_show = !is_play1_show;
                        break;
                    case 1:
                        if (!is_play2_show) {
                            iv_cover_2.setVisibility(View.GONE);
                        } else {
                            iv_cover_2.setVisibility(View.VISIBLE);
                        }
                        is_play2_show = !is_play2_show;
                        break;
                    case 2:
                        if (!is_play3_show) {
                            iv_cover_3.setVisibility(View.GONE);
                        } else {
                            iv_cover_3.setVisibility(View.VISIBLE);
                        }
                        is_play3_show = !is_play3_show;
                        break;
                }
                break;
        }
    }

    private int mic_guan = 0x0000;
    private boolean is_mic1_guan = false;
    private boolean is_mic2_guan = false;
    private boolean is_mic3_guan = false;

    private void handleControlView(View contentView) {
        final CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .setFocusable(true)//是否获取焦点，默认为ture
                .size(ScreenUtils.getScreenWidth(context), ScreenUtils.getScreenHeight(context))
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .create()//创建PopupWindow
                .showAtLocation(llRoomContent, Gravity.CENTER, 0, 0);//显示PopupWindow
        TextView tv_duomic = (TextView) contentView.findViewById(R.id.tv_room_control_duomic);
        TextView tv_shoumic = (TextView) contentView.findViewById(R.id.tv_room_control_shoumic);
        TextView tv_guanmic = (TextView) contentView.findViewById(R.id.tv_room_control_guanmai);
        TextView tv_guanmic_cancle = (TextView) contentView.findViewById(R.id.tv_room_control_guanmai_cancle);
        TextView tv_cancle = (TextView) contentView.findViewById(R.id.tv_room_control_cancle);
        tv_duomic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//夺麦
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < micUsers.size(); i++) {
                            if (micUsers.get(i).getMicindex() == micFlag) {//当前micindex  和主播列表中的micindex一致 则夺该主播的mic
                                roomMain.getRoom().getChannel().upMicRequest(StartUtil.getUserId(context), Header.MIC_STATUS_PUBLIC_MIC, micFlag);
                            }
                        }
                    }
                }).start();
                popWindow.dissmiss();
            }
        });
        tv_shoumic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < micUsers.size(); i++) {
                            if (micUsers.get(i).getMicindex() == micFlag) {//当前micindex  和主播列表中的micindex一致 则夺该主播的mic
                                roomMain.getRoom().getChannel().upMicRequest(String.valueOf(micUsers.get(i).getUserid()), Header.MIC_STATUS_DOWN_MIC, micFlag);
                            }
                        }
                    }
                }).start();
                popWindow.dissmiss();
            }
        });

        tv_guanmic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//增加管麦

                if (micFlag == 0) {
                    int temp = 0x0100;
                    mic_guan = mic_guan | temp;
                } else if (micFlag == 1) {
                    int temp = 0x0010;
                    mic_guan = mic_guan | temp;
                } else if (micFlag == 2) {
                    int temp = 0x0001;
                    mic_guan = mic_guan | temp;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        roomMain.getRoom().getChannel().guanMic(mic_guan);
                    }
                }).start();
                popWindow.dissmiss();
            }
        });
        tv_guanmic_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//增加管麦

                if (micFlag == 0) {
                    int temp = 0x1011;
                    mic_guan = mic_guan & temp;
                } else if (micFlag == 1) {
                    int temp = 0x1101;
                    mic_guan = mic_guan & temp;
                } else if (micFlag == 2) {
                    int temp = 0x1110;
                    mic_guan = mic_guan & temp;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        roomMain.getRoom().getChannel().guanMic(mic_guan);
                    }
                }).start();
                popWindow.dissmiss();
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                popWindow.dissmiss();
            }
        });
    }


    private void setOptions(int codecType) {
        AVOptions options = new AVOptions();
        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
        // Some optimization with buffering mechanism when be set to 1
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 0);
        options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, codecType);

        // whether start play automatically after prepared, default value is 1
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);

        plVider1.setAVOptions(options);
        plVider2.setAVOptions(options);
        plVider3.setAVOptions(options);
    }

    private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer mp, int errorCode) {
            KLog.e(errorCode + " ");
            boolean isNeedReconnect = false;
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                    KLog.e("Invalid URL !");
                    isNeedReconnect = true;
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
                    switch (micFlag) {
                        case 0:
                            is_video1_play = false;
                            break;
                        case 1:
                            is_video2_play = false;
                            break;
                        case 2:
                            is_video3_play = false;
                            break;
                    }
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
                sendReconnectMessage();
            } else {
//                finish();
            }
            // Return true means the error has been handled
            // If return false, then `onCompletion` will be called
            return true;
        }
    };
    private static final int MESSAGE_ID_RECONNECTING = 0x01;
    private static final int ROOM_RECONNECT = 0x02;

    private synchronized void sendReconnectMessage() {
        KLog.e("重新连接trmp播放地址");
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_ID_RECONNECTING), 6000);
    }

    private synchronized void sendRoomReconnectMessage() {
        KLog.e("重新连接房间");
//        mHandler.sendMessageDelayed(mHandler.obtainMessage(ROOM_RECONNECT), 5000);
    }

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_ID_RECONNECTING:
                    try {
                        if (NetUtils.isNetworkAvailable(context)) {
                            switch (micFlag) {
                                case 0:
                                    if (map_trmp_play.get(0).equals("null")) {
                                        return;
                                    }
                                    plVider1.setVideoPath(map_trmp_play.get(0));
                                    plVider1.start();
                                    if (!is_video1_play)
                                        sendReconnectMessage();
                                    break;
                                case 1:
                                    if (map_trmp_play.get(1).equals("null")) {
                                        return;
                                    }
                                    plVider2.setVideoPath(map_trmp_play.get(1));
                                    plVider2.start();
                                    if (!is_video2_play)
                                        sendReconnectMessage();
                                    break;
                                case 2:
                                    if (map_trmp_play.get(2).equals("null")) {
                                        return;
                                    }
                                    plVider3.setVideoPath(map_trmp_play.get(2));
                                    plVider3.start();
                                    if (!is_video3_play)
                                        sendReconnectMessage();
                                    break;
                            }
                            return;
                        } else {
                            ToastUtil.show(context, R.string.net_error);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case ROOM_RECONNECT:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (!StringUtil.isEmptyandnull(StartUtil.getUserId(context))) {
                                roomMain.getRoom().getChannel().Close();
                                roomMain.Start(roomId, Integer.parseInt(StartUtil.getUserId(RoomActivity.this)), StartUtil.getUserPwd(RoomActivity.this), ip, port, pwd);
                            }
                        }
                    }).start();
                    break;
            }
        }
    };
    private SurfaceView _CameraSurface;
    private Surface mPreviewSurface;
    private int resolution = AlivcMediaFormat.OUTPUT_RESOLUTION_480P;
    private boolean screenOrientation = false;
    private int cameraFrontFacing = 1;
    private int bestBitrate = 500;
    private int minBitrate = 400;
    private int maxBitrate = 600;
    private int initBitrate = 500;
    private int frameRate = 25;
    private AlivcMediaRecorder mMediaRecorder;
    private AlivcRecordReporter mRecordReporter;
    private int mPreviewWidth = 0;
    private int mPreviewHeight = 0;
    private Map<String, Object> mConfigure = new HashMap<>();

    //=================推流初始化
    private void initCameraView() {
        //采集
        _CameraSurface.getHolder().addCallback(_CameraSurfaceCallback);
        _CameraSurface.setOnTouchListener(mOnTouchListener);
        //对焦，缩放
        mGesDetector = new android.view.GestureDetector(_CameraSurface.getContext(), mGestureDetector);
        mScaleDetector = new ScaleGestureDetector(_CameraSurface.getContext(), mScaleGestureListener);
        mMediaRecorder = AlivcMediaRecorderFactory.createMediaRecorder();
        mMediaRecorder.init(context);
        mMediaRecorder.addFlag(AlivcMediaFormat.FLAG_BEAUTY_ON);
        /**
         * this method only can be called after mMediaRecorder.init(),
         * else will return null;
         */
        mRecordReporter = mMediaRecorder.getRecordReporter();
        mMediaRecorder.setOnRecordStatusListener(mRecordStatusListener);
        mMediaRecorder.setOnNetworkStatusListener(mOnNetworkStatusListener);
        mMediaRecorder.setOnRecordErrorListener(mOnPushErrorListener);
        mConfigure.put(AlivcMediaFormat.KEY_CAMERA_FACING, cameraFrontFacing);
        mConfigure.put(AlivcMediaFormat.KEY_MAX_ZOOM_LEVEL, 1);
        mConfigure.put(AlivcMediaFormat.KEY_OUTPUT_RESOLUTION, resolution);
        mConfigure.put(AlivcMediaFormat.KEY_MAX_VIDEO_BITRATE, maxBitrate * 1000);
        mConfigure.put(AlivcMediaFormat.KEY_BEST_VIDEO_BITRATE, bestBitrate * 1000);
        mConfigure.put(AlivcMediaFormat.KEY_MIN_VIDEO_BITRATE, minBitrate * 1000);
        mConfigure.put(AlivcMediaFormat.KEY_INITIAL_VIDEO_BITRATE, initBitrate * 100);
        mConfigure.put(AlivcMediaFormat.KEY_DISPLAY_ROTATION, screenOrientation ? AlivcMediaFormat.DISPLAY_ROTATION_90 : AlivcMediaFormat.DISPLAY_ROTATION_0);
        mConfigure.put(AlivcMediaFormat.KEY_EXPOSURE_COMPENSATION, -1);//曝光度
        mConfigure.put(AlivcMediaFormat.KEY_FRAME_RATE, frameRate);

    }

    private android.view.GestureDetector mGesDetector;
    private ScaleGestureDetector mScaleDetector;
    private android.view.GestureDetector.OnGestureListener mGestureDetector = new android.view.GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if (mPreviewWidth > 0 && mPreviewHeight > 0) {
                float x = motionEvent.getX() / mPreviewWidth;
                float y = motionEvent.getY() / mPreviewHeight;
                mMediaRecorder.focusing(x, y);
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }
    };

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mGesDetector.onTouchEvent(motionEvent);
            mScaleDetector.onTouchEvent(motionEvent);
            return true;
        }
    };

    private ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mMediaRecorder.setZoom(scaleGestureDetector.getScaleFactor());
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        }
    };

    private void startPreview(final SurfaceHolder holder) {
        mMediaRecorder.prepare(mConfigure, mPreviewSurface);
        mMediaRecorder.setZoom(0.5f);
        mMediaRecorder.setPreviewSize(_CameraSurface.getMeasuredWidth(), _CameraSurface.getMeasuredHeight() / 2);
        mMediaRecorder.startRecord(push_url);
//        rllControlView.setVisibility(View.GONE);
    }

    private boolean is_surface_creat = false;
    private final SurfaceHolder.Callback _CameraSurfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            is_surface_creat = true;
            holder.setKeepScreenOn(true);
            mPreviewSurface = holder.getSurface();
            KLog.e("_CameraSurfaceCallback");
            startPreview(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            mMediaRecorder.setPreviewSize(width, height);
            mPreviewWidth = width;
            mPreviewHeight = height;
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            is_surface_creat = false;
            mPreviewSurface = null;
            mMediaRecorder.stopRecord();
            mMediaRecorder.reset();
        }
    };
    private OnRecordStatusListener mRecordStatusListener = new OnRecordStatusListener() {
        @Override
        public void onDeviceAttach() {
            mMediaRecorder.addFlag(AlivcMediaFormat.FLAG_AUTO_FOCUS_ON);
        }

        @Override
        public void onDeviceAttachFailed(int facing) {

        }

        @Override
        public void onSessionAttach() {
            mMediaRecorder.focusing(0.5f, 0.5f);
//            if (is_connect_room) {//如果房间没有连接成功，则不显示控制页面

//            }
        }

        @Override
        public void onSessionDetach() {

        }

        @Override
        public void onDeviceDetach() {

        }

        @Override
        public void onIllegalOutputResolution() {
            KLog.e("selected illegal output resolution");
        }
    };

    private boolean is_pushing = false;
    private OnNetworkStatusListener mOnNetworkStatusListener = new OnNetworkStatusListener() {
        @Override
        public void onNetworkBusy() {
            KLog.e("==== on network busy ====");
        }

        @Override
        public void onNetworkFree() {
            KLog.e("network_status", "===== on network free ====");
        }

        @Override
        public void onConnectionStatusChange(int status) {
            KLog.e("ffmpeg Live stream connection status-->" + status);

            switch (status) {
                case AlivcStatusCode.STATUS_CONNECTION_START:
                    KLog.e("Start live stream connection!");
                    break;
                case AlivcStatusCode.STATUS_CONNECTION_ESTABLISHED:
                    is_pushing = true;
                    KLog.e("Live stream connection is established!");
                    break;
                case AlivcStatusCode.STATUS_CONNECTION_CLOSED:
                    KLog.e("Live stream connection is closed!");
                    break;
            }
        }

        @Override
        public boolean onNetworkReconnectFailed() {
            KLog.e("Reconnect timeout, not adapt to living");
            mMediaRecorder.stopRecord();
            return false;
        }
    };
    private OnLiveRecordErrorListener mOnPushErrorListener = new OnLiveRecordErrorListener() {
        @Override
        public void onError(int errorCode) {
            KLog.e("Live stream connection error-->" + errorCode);

            switch (errorCode) {
                case AlivcStatusCode.ERROR_ILLEGAL_ARGUMENT:
                    KLog.e("Live stream connection error-->" + "-22错误产生");
                case AlivcStatusCode.ERROR_SERVER_CLOSED_CONNECTION:
                case AlivcStatusCode.ERORR_OUT_OF_MEMORY:
                case AlivcStatusCode.ERROR_CONNECTION_TIMEOUT:
                case AlivcStatusCode.ERROR_BROKEN_PIPE:
                case AlivcStatusCode.ERROR_IO:
                case AlivcStatusCode.ERROR_NETWORK_UNREACHABLE:
                    KLog.e("Live stream connection error-->" + errorCode);
                    break;

                default:
            }
        }
    };

    //布局10秒后隐藏
    public void animaView(final View view) {
        AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.5f);
        animation1.setDuration(100 * 100);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                is_mic_control_view_show = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.setAnimation(animation1);
        animation1.start();
    }

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Handler handler = new Handler();
    /**
     * 根据MP3播放进度时事同步歌词组件
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer.isPlaying()) {
                long time = mediaPlayer.getCurrentPosition();
                lrcLive.updateTime(time);
            }
            handler.postDelayed(this, 200);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_MUSIC) {
                rllLiveLrc.setVisibility(View.VISIBLE);
                String music_id = data.getStringExtra("music_id");
                File file_lrc = new File(FileUtils.getLrcFiles() + music_id + ".lrc");
                if (file_lrc.exists()) {
                    lrcLive.loadLrc(file_lrc);//设置歌词资源
                }
                File file_music = new File(FileUtils.getMusicFiles() + music_id + ".mp3");

                if (file_music.exists()) {
                    try {
                        if (mediaPlayer != null) {
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.pause();
                            }
                        }
                        mediaPlayer.reset();
                        String path = file_music.getAbsolutePath();
                        KLog.e(path);
                        mediaPlayer.setDataSource(path);//设置MP3路径
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mediaPlayer.start();
                                handler.post(runnable);
                            }
                        });
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
