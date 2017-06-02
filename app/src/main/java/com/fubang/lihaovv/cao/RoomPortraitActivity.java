package com.fubang.lihaovv.cao;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.SlidingTab.EmotionInputDetector;
import com.fubang.lihaovv.SlidingTab.SlidingTabLayout;
import com.fubang.lihaovv.adapters.EmotionAdapter;
import com.fubang.lihaovv.adapters.RoomChatAdapter;
import com.fubang.lihaovv.entities.GiftEntity;
import com.fubang.lihaovv.ui.BaseActivity;
import com.fubang.lihaovv.utils.GlobalOnItemClickManager;
import com.xlg.android.protocol.MicState;
import com.xlg.android.protocol.RoomChatMsg;
import com.xlg.android.protocol.RoomKickoutUserInfo;
import com.xlg.android.protocol.RoomUserInfo;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import sample.room.MicNotify;
import sample.room.RoomMain;


/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * Created by jacky on 17/3/10.
 */
@EActivity(R.layout.activity_room_portrait)
public class RoomPortraitActivity extends BaseActivity implements MicNotify {
    @ViewById(R.id.text_new_surface)
    FrameLayout fragmentSurface;
//    @ViewById(R.id.sv_room_surface1)
//    SurfaceView surfaceView;
    @ViewById(R.id.lv_chat_info)
    ListView lvChatInfo;
    @ViewById(R.id.vp_surfaceview)
    ViewPager vpSurfaceView;
    //=======================键盘页面
    @ViewById(R.id.room_new_chat_send)
    Button roomChatSend;
    @ViewById(R.id.rll_room_input)
    RelativeLayout rllRoomInput;
    @ViewById(R.id.chat_new_input_line)
    LinearLayout llRoomInput;
    @ViewById(R.id.tv_room_input_close)
    TextView tvRoomInputClose;
    @ViewById(R.id.edit_new_text)
    EditText roomMessageEdit;
    @ViewById(R.id.emotion_new_button)
    ImageView emotionButton;
    @ViewById(R.id.emotion_new_layout)
    RelativeLayout emotionNewLayout;

    //=========================顶部布局
    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @ViewById(R.id.iv_room_anchor_small_pic)
    ImageView ivRoomAnchorSmallPic;
    @ViewById(R.id.tv_room_anchor_name)
    TextView tvRoomAnchorName;
    @ViewById(R.id.tv_room_anchor_online_num)
    TextView tvRoomAnchorOnlineNum;
    @ViewById(R.id.iv_room_add_favorite)
    ImageView ivRoomAddFavorite;
    @ViewById(R.id.tv_room_anchor_type)
    TextView tvRoomAnchorType;
    @ViewById(R.id.tv_room_anchor_feature)
    TextView tvRoomAnchorFeature;
    @ViewById(R.id.tv_room_id)
    TextView tvRoomId;
    @ViewById(R.id.iv_room_up_to_mic)
    ImageView ivRoomUpToMic;
    @ViewById(R.id.iv_room_exit)
    ImageView ivRoomExit;
    @ViewById(R.id.tv_room_kbi_num)
    TextView tvRoomKbiNum;
    //=========================底部布局
    @ViewById(R.id.iv_room_more)
    ImageView ivRoomMore;
    @ViewById(R.id.iv_room_chat)
    ImageView ivRoomChat;
    @ViewById(R.id.iv_room_share)
    ImageView ivRoomShare;
    @ViewById(R.id.iv_room_screen_change)
    ImageView ivRoomScreenChange;
    @ViewById(R.id.iv_room_gift)
    ImageView ivRoomGift;
    @ViewById(R.id.rl_room_right_menu)
    RelativeLayout rlRoomRightMenu;
    //=========================右滑布局
    @ViewById(R.id.ll_room_right_car)
    LinearLayout llRoomRightCar;
    @ViewById(R.id.iv_room_right_look_down)
    ImageView ivRoomRightLookDown;
    @ViewById(R.id.sb_lightness)
    SeekBar sbLightness;
    @ViewById(R.id.ib_room_right_lc_btn)
    ImageView ibRoomRightLcBtn;
    @ViewById(R.id.ib_room_right_gq_btn)
    ImageView ibRoomRightGqBtn;
    @ViewById(R.id.ib_room_right_cq_btn)
    ImageView ibRoomRightCqBtn;
    @ViewById(R.id.ll_room_right_goto_min)
    LinearLayout llRoomRightGotoMin;
    @ViewById(R.id.ll_room_right_siliao)
    LinearLayout llRoomRightSiliao;
    @ViewById(R.id.ll_room_right_backto_home)
    LinearLayout llRoomRightBacktoHome;
    @ViewById(R.id.ll_room_right_clear_screen)
    LinearLayout llRoomRightClearScreen;
    @ViewById(R.id.ll_room_right_only_mic)
    LinearLayout llRoomRightOnlyMic;
    @ViewById(R.id.ib_room_right_zbtz)
    ImageView ibRoomRightZbtz;


    private String roomPwd;
    private String roomIp;
    private String ip;
    private int port;
    private int roomId;

    RoomMain roomMain = new RoomMain(this);
    private Configuration configuration;
    private boolean isRunning = false;
    private boolean isplaying = false;
    private int ssrc;
    private PopupWindow pop_share;
    private Context context;
    private EmotionInputDetector mDetector;
    private FragmentTransaction ft;
//    private FragmentTabAdapter tabAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private List<RoomUserInfo> micUsers = new ArrayList<>();
    private List<RoomUserInfo> userInfos = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {

            @Override
            public void run() {
                roomMain.Start(roomId, Integer.parseInt(StartUtil.getUserId(RoomPortraitActivity.this)), StartUtil.getUserPwd(RoomPortraitActivity.this), ip, port, roomPwd);
            }

        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
//        KLog.e("onResume");
        configuration = getResources().getConfiguration();
        isRunning = true;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        KLog.e("onDestroy");
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.getRoom().getChannel().sendLeaveRoom(Integer.parseInt(StartUtil.getUserId(context)));
                roomMain.getRoom().onDisconnected();
            }
        }).start();
        isRunning = false;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void before() {
        roomPwd = getIntent().getStringExtra("roomPwd");
        roomIp = getIntent().getStringExtra("roomIp");
        String[] Ips = roomIp.split(";");
        String[] ports = Ips[0].split(":");
        ip = ports[0];
        port = Integer.parseInt(ports[1]);
        roomId = Integer.parseInt(getIntent().getStringExtra("roomId"));
        EventBus.getDefault().register(this);
        context = this;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void initView() {
        //设置状态栏透明
//        setTranslucentStatus();
        //设置滑动播放页面
//        MySurfaceViewFragmentPagerAdapter adapter = new MySurfaceViewFragmentPagerAdapter(getSupportFragmentManager(),
//                this, roomId);
//        vpSurfaceView.setOffscreenPageLimit(2);
//        vpSurfaceView.setAdapter(adapter);
        //设置表情适配器
        drawerLayout.setScrimColor(Color.TRANSPARENT);//去掉DrawerLayout遮板
        mDetector = EmotionInputDetector.with(this)
                .setEmotionView(emotionNewLayout)
                .bindToContent(llRoomInput)
                .bindToEditText(roomMessageEdit)
                .bindToEmotionButton(emotionButton)
                .build();
        setUpEmotionViewPager();
        //亮度调节
        int ligntness = getScreenBrightness(this);
        int ligntness_f = (int) (ligntness / (float) 255 * 100);
        sbLightness.setMax(100);
        sbLightness.setProgress(ligntness_f);
        //播放尺寸调节
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(vpSurfaceView.getLayoutParams());
        params.height = ScreenUtils.getScreenWidth(context) / 4 * 3;//竖屏 高是宽度四分之三
        params.setMargins(0, ScreenUtils.Dp2Px(context, 115), 0, 0);
        vpSurfaceView.setLayoutParams(params);
        //listview失去焦点
        lvChatInfo.setFocusable(false);
    }

    @Override
    public void initData() {
        tvRoomId.setText(roomId + " ");
        adapter = new RoomChatAdapter(list_msg, this);
        lvChatInfo.setAdapter(adapter);

    }

    private boolean is_software_show = false;
    public static boolean is_emoticon_show = false;

    @Override
    public void initListener() {
        //因为系统没有直接监听软键盘API，所以就用以下方法
        rllRoomInput.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() { //当界面大小变化时，系统就会调用该方法
                        Rect r = new Rect(); //该对象代表一个矩形（rectangle）
                        rllRoomInput.getWindowVisibleDisplayFrame(r); //将当前界面的尺寸传给Rect矩形
                        int deltaHeight = ScreenUtils.getScreenHeight(context) - r.bottom;  //弹起键盘时的变化高度，在该场景下其实就是键盘高度。
                        if (deltaHeight > 150) {  //该值是随便写的，认为界面高度变化超过150px就视为键盘弹起。
                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp.setMargins(0, 0, 0, deltaHeight);
                            rllRoomInput.setLayoutParams(lp);
                            is_software_show = true;
                        } else if (deltaHeight == 0) {
                            if (is_software_show) {
                                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(0, 0, 0, 0);
                                rllRoomInput.setLayoutParams(lp);
                                is_software_show = false;
                            }
                        }
                    }
                });

        sbLightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setScreenBrightness((float) seekBar.getProgress() / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //解决侧滑冲突
        sbLightness.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                drawerLayout.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    private InputMethodManager imm;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Click({R.id.iv_room_more, R.id.iv_room_exit, R.id.iv_room_share, R.id.iv_room_screen_change
            , R.id.iv_room_chat, R.id.tv_room_input_close, R.id.emotion_button, R.id.iv_room_gift
            , R.id.room_new_gift, R.id.iv_room_up_to_mic, R.id.room_new_chat_send, R.id.iv_room_add_favorite
    })
    void click(View view) {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (view.getId()) {
            case R.id.iv_room_more:
                drawerLayout.openDrawer(Gravity.RIGHT);
                break;
            case R.id.iv_room_exit:
                finish();
                break;
//            case R.id.iv_room_share:
//                doShareAction();
//                break;
            case R.id.iv_room_screen_change:
                if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                    KLog.e("SCREEN_ORIENTATION_LANDSCAPE"); // 横屏
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(vpSurfaceView.getLayoutParams());
                    params.height = ScreenUtils.getScreenWidth(context) / 4 * 3;//竖屏 高是宽度四分之三
                    params.setMargins(0, ScreenUtils.Dp2Px(context, 115), 0, 0);
                    vpSurfaceView.setLayoutParams(params);
                } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                    KLog.e("SCREEN_ORIENTATION_PORTRAIT"); // 竖屏
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(vpSurfaceView.getLayoutParams());
                    params.height = ScreenUtils.getScreenHeight(context);//竖屏 高是宽度四分之三
                    params.setMargins(0, 0, 0, 0);
                    vpSurfaceView.setLayoutParams(params);
                }
                break;
            case R.id.iv_room_chat:
                rllRoomInput.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!is_emoticon_show) {
                            if (imm != null) {
                                imm.showSoftInput(roomMessageEdit, 0);
                            }
                        }
                    }
                }, 200);
                break;
            case R.id.tv_room_input_close:
                rllRoomInput.setVisibility(View.GONE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(roomMessageEdit.getWindowToken(), 0);
                }
                break;
            case R.id.iv_room_gift:
            case R.id.room_new_gift:
//                showWindow();
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.showAsDropDown(ivRoomGift);
                }
                break;
//            case R.id.iv_room_up_to_mic:
//                startActivity(MicOrderActivity_.intent(context).get());
//                break;
            case R.id.room_new_chat_send:
                roomMain.getRoom().getChannel().sendChatMsg(0, (byte) 0x00, (byte) 0x00, "德玛西亚", "123", 0);
                rllRoomInput.setVisibility(View.GONE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(roomMessageEdit.getWindowToken(), 0);
                }
                break;
            //收藏房间
            case R.id.iv_room_add_favorite:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        roomMain.getRoom().getChannel().followRoom(roomId, Integer.parseInt(StartUtil.getUserId(context)));
                    }
                }).start();
                Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private EditText giftToUser;
    private PopupWindow popupWindow;
    private List<GiftEntity> gifts = new ArrayList<>();

//    //礼物的悬浮框
//    private void showWindow() {
//        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(R.layout.pop_gift_grid, null);
//        GridView gridView = (GridView) view.findViewById(R.id.room_gift_list);
//        Button giftSendBtn = (Button) view.findViewById(R.id.gift_send_btn);
//        final TextView giftName = (TextView) view.findViewById(R.id.gift_name_txt);
//        final EditText giftCount = (EditText) view.findViewById(R.id.gift_count);
////        final EditText
//        giftToUser = (EditText) view.findViewById(R.id.gift_to_user);
//        popupWindow = new PopupWindow(view);
//        popupWindow.setFocusable(true);
//        gifts.clear();
//        gifts.addAll(GiftUtil.getGifts());
//        GiftGridViewAdapter giftAdapter = new GiftGridViewAdapter(gifts, this);
//        gridView.setAdapter(giftAdapter);
//        //选择礼物
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Log.d("123",gifts.get(position)+"------------>");
////                giftId = gifts.get(position).getGiftId();
//                String name = gifts.get(position).getGiftName();
//                giftName.setText(name + "");
////                popupWindow.dismiss();
//            }
//        });
////        if (micUsers != null) {
////            for (int i = 0; i < micUsers.size(); i++) {
////                if (micUsers.get(i).getMicindex() == micFlag) {
////                    giftToUser.setText(micUsers.get(i).getUseralias() + "(" + micUsers.get(i).getUserid() + ")");
//////                            Log.d("123","toid---"+toid+"toName"+toName);
////                }
////            }
////        }
//        //发送礼物
//        giftSendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                toid = -1;
////                toName = "";
//////                if (giftToUser.getText().toString().equals("1")){
////                for (int i = 0; i < micUsers.size(); i++) {
////                    if (micUsers.get(i).getMicindex() == micFlag) {
////                        toid = micUsers.get(i).getUserid();
////                        toName = micUsers.get(i).getUseralias();
//////                            Log.d("123","toid---"+toid+"toName"+toName);
////                    }
////                }
////                }else if (giftToUser.getText().toString().equals("2")){
////                    for (int i = 0; i < micUsers.size(); i++) {
////                        if (micUsers.get(i).getMicindex()==1){
////                            toid = micUsers.get(i).getUserid();
////                            toName = micUsers.get(i).getUseralias();
//////                            Log.d("123","toid---"+toid+"toName"+toName);
////                        }
////                    }
////                }else if (giftToUser.getText().toString().equals("3")){
////                    for (int i = 0; i < micUsers.size(); i++) {
////                        if (micUsers.get(i).getMicindex()==2){
////                            toid = micUsers.get(i).getUserid();
////                            toName = micUsers.get(i).getUseralias();
//////                            Log.d("123","toid---"+toid+"toName"+toName);
////                        }
////                    }
////                }else
//////                        (giftToUser.getText().toString()!="1" &&giftToUser.getText().toString()!="2"&&giftToUser.getText().toString()!="3")
////                {
////                    Toast.makeText(RoomNewActivity.this, "赠送麦序错误,请重新选择", Toast.LENGTH_SHORT).show();
////                    toid = -1;
////                    toName = "";
////                }
//                final int count = Integer.parseInt(giftCount.getText().toString());
////                Log.d("123", "toid--" + toid + "---giftId---" + giftId + "---count---" + count + "---toName---" + toName);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
////                        roomMain.getRoom().getChannel().sendGiftRecord(toid, giftId, count, toName, StartUtil.getUserName(RoomActivity.this));
//                    }
//                }).start();
//
//                giftName.setText("送给");
//                popupWindow.dismiss();
////                sendControl.setVisibility(View.VISIBLE);
//            }
//        });
//        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        ColorDrawable dw = new ColorDrawable(0xffffffff);
//        popupWindow.setBackgroundDrawable(dw);
////        popupWindow.showAsDropDown(giftImage);
////        popupWindow.showAtLocation(roomInputLinear,Gravity.BOTTOM,0,0);
//        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
//        int height = wm.getDefaultDisplay().getHeight();
//        popupWindow.setHeight(height / 2);
//        popupWindow.setOutsideTouchable(true);
//    }

    /**
     * 表情页面
     */

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
//        globalOnItemClickListener.attachToEditText((EditText) findViewById(R.id.edit_new_text),this);

    }

//    /**
//     * 处理分享弹窗
//     */
//    private void doShareAction() {
//        View popupView = getLayoutInflater().inflate(R.layout.pop_share, null);
//        pop_share = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        pop_share.showAtLocation(tvRoomAnchorName, Gravity.TOP, 0, 0);
//        TextView tv_cancle = (TextView) popupView.findViewById(R.id.tv_share_cancle);
//        LinearLayout ll_wechat = (LinearLayout) popupView.findViewById(R.id.ll_share_wechat);
//        LinearLayout ll_wechat_circle = (LinearLayout) popupView.findViewById(R.id.ll_share_wechat_circle);
//        LinearLayout ll_qq = (LinearLayout) popupView.findViewById(R.id.ll_share_wechat_qq);
//        LinearLayout ll_sina = (LinearLayout) popupView.findViewById(R.id.ll_share_sina);
//        ll_wechat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Platform plat = ShareSDK.getPlatform(Wechat.NAME);
//                ShareUtil.getInstance().showShareNew(context, plat);
//            }
//        });
//        ll_wechat_circle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Platform plat = ShareSDK.getPlatform(WechatMoments.NAME);
//                ShareUtil.getInstance().showShareNew(context, plat);
//            }
//        });
//        ll_qq.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Platform plat = ShareSDK.getPlatform(QQ.NAME);
//                ShareUtil.getInstance().showShareNew(context, plat);
//            }
//        });
//        ll_sina.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Platform plat = ShareSDK.getPlatform(SinaWeibo.NAME);
//                ShareUtil.getInstance().showShareNew(context, plat);
//            }
//        });
//        tv_cancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pop_share.dismiss();
//            }
//        });
//    }

    private String mediaIp;
    private int mediaPort;
    private int mediaRand;


    private void setScreenBrightness(float b) {
//        KLog.e(b);
        //取得window属性保存在layoutParams中
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = b;//b已经除以10
        getWindow().setAttributes(layoutParams);
    }

    public static int getScreenBrightness(Activity activity) {
        int value = 0;
        ContentResolver cr = activity.getContentResolver();
        try {
            value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {

        }
        return value;
    }


    @Override
    public void onMic(String ip, int port, int rand, int uid) {
    }

    private List<RoomChatMsg> list_msg = new ArrayList<>();
    private RoomChatAdapter adapter;

    //接收服务器发送的消息更新列表
    @Subscriber(tag = "RoomChatMsg")
    public void getRoomChatMsg(RoomChatMsg msg) {
//        KLog.e(msg.getContent() + " ");
        if (msg.getMsgtype() == 0) {
            if (msg.getIsprivate() == 0) {
                //("<b><FONT style=\"FONT-FAMILY:宋体;FONT-SIZE:17px; COLOR:#FF0000\">/mr599</FONT></b>")) {
                //<b><FONT style="FONT-FAMILY:宋体;FONT-SIZE:17px; COLOR:#FF0000">/mr599</FONT></b>
//                EventBus.getDefault().post(msg,"CommonMsg");
//                listView.setSelection(listView.getCount() - 1);
                list_msg.add(msg);
                adapter.notifyDataSetChanged();
                lvChatInfo.setSelection(lvChatInfo.getCount() - 1);
                lvChatInfo.setVisibility(View.VISIBLE);
//                setAnimaAlpha(lvChatInfo);
            }
        }
//        if(msg.getMsgtype() == 0) {
//            if (msg.getIsprivate() == 1) {
//                if (msg.getToid() == sendToUser.getUserid() || msg.getToid() == Integer.parseInt(StartUtil.getUserId(this))) {
//                    EventBus.getDefault().post(msg,"PersonMsg");
//                }
//            }
//        }
        if (msg.getMsgtype() == 12 && msg.getSrcid() == 2) {

            Spanned spanned = Html.fromHtml(msg.getContent());
            Log.d("123", spanned + "");
        }

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
    }

    //上麦提示
    @Subscriber(tag = "upMicState")
    public void upMicState(MicState obj) {
        for (int i = 0; i < userInfos.size(); i++) {
            if (obj.getUserid() == userInfos.get(i).getUserid()) {
                userInfos.get(i).setMicindex(obj.getMicindex());
                if (micUsers.size() < 3) {
                    micUsers.add(userInfos.get(i));
                }
            }
        }
        EventBus.getDefault().post(micUsers, "FragmentWhich");
    }

    //下麦提示
    @Subscriber(tag = "downMicState")
    public void downMicState(MicState obj) {
        for (int i = 0; i < micUsers.size(); i++) {
            if (micUsers.get(i).getUserid() == obj.getUserid()) {
                micUsers.remove(i);
            }
        }
        EventBus.getDefault().post(micUsers, "FragmentWhich");
    }

    //麦上几个人就添加视频流
    @Subscriber(tag = "onMicUser")
    public void getonMicUser(RoomUserInfo obj) {
//        KLog.e("onMicUser" + obj.getUserid() + obj.getUseralias());
        micUsers.add(obj);
        EventBus.getDefault().post(micUsers, "FragmentWhich");
    }

}
