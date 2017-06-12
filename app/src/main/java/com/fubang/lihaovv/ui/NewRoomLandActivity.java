package com.fubang.lihaovv.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
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
import com.fubang.lihaovv.R;
import com.fubang.lihaovv.App;
import com.fubang.lihaovv.adapters.FaceAdapter;
import com.fubang.lihaovv.adapters.GiftAdapter;
import com.fubang.lihaovv.adapters.RoomChatAdapter;
import com.fubang.lihaovv.adapters.UserAdapter;
import com.fubang.lihaovv.entities.FaceEntity;
import com.fubang.lihaovv.entities.GiftEntity;
import com.fubang.lihaovv.filter.IFilter;
import com.fubang.lihaovv.utils.ControllerUtil;
import com.fubang.lihaovv.utils.FaceUtil;
import com.fubang.lihaovv.utils.GiftUtil;
import com.fubang.lihaovv.view.MyEditText;
import com.xlg.android.protocol.BigGiftRecord;
import com.xlg.android.protocol.JoinRoomResponse;
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
import sample.room.MicNotify;
import sample.room.RoomMain;

@EActivity(R.layout.activity_room_land)
public class NewRoomLandActivity extends BaseActivity implements AVNotify, MicNotify, View.OnClickListener, View.OnTouchListener {
    @ViewById(R.id.linear_container)
    LinearLayout linearLayout;
    @ViewById(R.id.room_chat_edittext)
    MyEditText editText;
    @ViewById(R.id.room_chat_send)
    Button sendBtn;
    @ViewById(R.id.room_message_list)
    ListView listView;
    @ViewById(R.id.room_gift)
    ImageView giftImage;
    @ViewById(R.id.chat_image_btn)
    ImageButton faceButton;
    @ViewById(R.id.room_send_user)
    Button userSendBtn;
    @ViewById(R.id.test_back_btn)
    ImageView backImage;
    @ViewById(R.id.test_controll)
    RelativeLayout testController;
    @ViewById(R.id.test_full)
    ImageView fullImage;
    @ViewById(R.id.room_id_test)
    TextView roomIdTv;
    @ViewById(R.id.danmakuView)
    DanmakuView danmakuView;
    @ViewById(R.id.test_danmu_btn)
    ImageView danmuImage;
    @ViewById(R.id.text_back_image)
    SimpleDraweeView textBackImage;
    @ViewById(R.id.text_relative)
    RelativeLayout textRelative;
    @ViewById(R.id.room_change)
    TextView textChange;


    private Button giftSendBtn;
    private GridView gridView;
    private ListView userList;
    private boolean isRunning = true;

    private List<RoomChatMsg> data = new ArrayList<>();

    private RoomChatAdapter adapter;

    private AVModuleMgr mgr;
    @ViewById(R.id.text_surface)
    SurfaceView surfaceView;
    private Bitmap bmp;
    private static AudioPlay play = new AudioPlay();
    private RoomMain roomMain = new RoomMain();

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

    private String roomPwd;
    private String roomIp;
    private App app;
    private Configuration configuration;
    private DanmakuContext mContext;
    private BaseDanmakuParser mParser;

    @Override
    public void before() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        app = (App) getApplication();
        roomPwd = getIntent().getStringExtra("roomPwd");
        roomIp = getIntent().getStringExtra("roomIp");
        String[] Ips = roomIp.split(";");
        String[] ports = Ips[0].split(":");
        ip = ports[0];
        port = Integer.parseInt(ports[1]);
        roomId = Integer.parseInt(getIntent().getStringExtra("roomId"));
        Log.d("123", roomId + "roomId");
        EventBus.getDefault().register(this);
    }


    @Override
    public void initView() {
        initDanmu();
        textBackImage.setVisibility(View.VISIBLE);
        surfaceView.setVisibility(View.GONE);
        danmakuView.setVisibility(View.VISIBLE);
        danmakuView.show();
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        roomIdTv.setText(roomId + "");

        ControllerUtil.showAndHide(testController, textRelative);
        Log.d("123", "oncreate---");
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        RoomUserInfo roomUser = new RoomUserInfo();
        roomUser.setUseralias("大厅");
        userInfos.add(roomUser);
        RoomChatMsg joinMsg = new RoomChatMsg();
        joinMsg.setSrcid(Integer.parseInt(StartUtil.getUserId(this)));
        joinMsg.setContent("加入了房间");
        data.add(joinMsg);
        adapter = new RoomChatAdapter(data, this);
        configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            listView.setAdapter(adapter);
//            showWindow();
//            showFace();
            showUser();
        }
//        showWindow();
//        showFace();
        fullImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                startActivity(TestActivity_.intent(NewRoomLandActivity.this).extra("roomIp", roomIp).extra("roomId", roomId + "").extra("roomPwd", roomPwd + "").get());
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
        textChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
//                startActivity(RoomLandActivity_.intent(RoomActivity.this).extra("roomIp",roomIp).extra("roomId",roomId+"").get());
                if (micUsers.size() != 1) {
                    mgr.DelAudioStream(ssrc);
                    mgr.DelVideoStream(ssrc);
                    if (toid == micUsers.get(0).getUserid()) {
                        buddyid = micUsers.get(1).getUserid();
                        toName = micUsers.get(1).getUseralias();
                        StartAV(mediaIp, mediaPort, mediaRand, micUsers.get(micUsers.size() - 1).getUserid());
                    } else {
                        buddyid = micUsers.get(0).getUserid();
                        toName = micUsers.get(0).getUseralias();
                        StartAV(mediaIp, mediaPort, mediaRand, micUsers.get(0).getUserid());
                    }
                }
            }
        });
        testController.setOnTouchListener(this);
    }

    private void showUser() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_user_list, null);
        userList = (ListView) view.findViewById(R.id.room_user_list);

        userWindow = new PopupWindow(view);
        userWindow.setFocusable(true);
        userAdapter = new UserAdapter(userInfos, this);
        userList.setAdapter(userAdapter);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("123", gifts.get(position) + "------------>");
                sendToUser = userInfos.get(position);
                userSendBtn.setText(sendToUser.getUseralias());
                userWindow.dismiss();
            }
        });
        userWindow.setWidth(200);
        ColorDrawable dw = new ColorDrawable(0xb0ffffff);
        userWindow.setBackgroundDrawable(dw);
        userWindow.setHeight(500);
        userWindow.setOutsideTouchable(true);
    }

    private void showFace() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_face_grid, null);
        gridView = (GridView) view.findViewById(R.id.room_face_list);

        faceWindow = new PopupWindow(view);
        faceWindow.setFocusable(true);
        faces.addAll(FaceUtil.getFaces());
        FaceAdapter faceAdapter = new FaceAdapter(faces, this);
        gridView.setAdapter(faceAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("123", faces.get(position) + "------------>");
                if (position < 9) {
                    int faceNumber = position + 1;
                    editText.setText(editText.getText() + "/mr70" + faceNumber);
                }
                if (position >= 9) {
                    int faceNumber = position + 1;
                    editText.setText(editText.getText() + "/mr7" + faceNumber);
                }
                faceWindow.dismiss();
            }
        });
        faceWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(0xb0ffffff);
        faceWindow.setBackgroundDrawable(dw);
        faceWindow.setHeight(300);
        faceWindow.setOutsideTouchable(true);
    }

    private void showWindow() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_gift_grid, null);
        gridView = (GridView) view.findViewById(R.id.room_gift_list);
        giftSendBtn = (Button) view.findViewById(R.id.gift_send_btn);
        final TextView giftName = (TextView) view.findViewById(R.id.gift_name_txt);
        final EditText giftCount = (EditText) view.findViewById(R.id.gift_count);

        popupWindow = new PopupWindow(view);
        popupWindow.setFocusable(true);
        gifts.addAll(GiftUtil.getGifts());
        GiftAdapter giftAdapter = new GiftAdapter(gifts, this);
//        gridView.setAdapter(giftAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("123", gifts.get(position) + "------------>");
                giftId = gifts.get(position).getGiftId();
                giftName.setText(giftId + "");
//                popupWindow.dismiss();
            }
        });
        giftSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(giftCount.getText().toString());
                roomMain.getRoom().getChannel().sendGiftRecord(toid, giftId, count, toName, StartUtil.getUserName(NewRoomLandActivity.this));
                popupWindow.dismiss();
            }
        });
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(0xb0ffffff);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setHeight(400);
        popupWindow.setOutsideTouchable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_container:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.chat_image_btn:
                popupWindow.dismiss();
                if (faceWindow.isShowing()) {
                    faceWindow.dismiss();
                } else {
                    Log.d("123", "showPop------------------");
                    faceWindow.showAsDropDown(faceButton);
                }
                break;
            case R.id.room_gift:
                faceWindow.dismiss();
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    Log.d("123", "showPop------------------");
                    popupWindow.showAsDropDown(giftImage);
                }
                break;
            case R.id.room_send_user:
                if (userWindow.isShowing()) {
                    userWindow.dismiss();
                } else {
                    userWindow.showAsDropDown(userSendBtn);
                }
                break;
        }
    }

    @Override
    public void initData() {
//        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            sendBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (!TextUtils.isEmpty(editText.getText())) {
//                        if (userSendBtn.getText().toString().contains("大厅")) {
//                            roomMain.getRoom().getChannel().sendChatMsg(0, (byte) 0, (byte) 0, "<FONT style=\"FONT-FAMILY:宋体;FONT-SIZE:14px; COLOR:#000000\">" + editText.getText() + "</FONT>",StartUtil.getUserName(RoomLandActivity.this));
//                            editText.setText("");
//                        } else if (!TextUtils.isEmpty(sendToUser.getUseralias())) {
//                            roomMain.getRoom().getChannel().sendChatMsg(sendToUser.getUserid(), (byte) 0, (byte) 1, "<FONT style=\"FONT-FAMILY:宋体;FONT-SIZE:14px; COLOR:#000000\">" + editText.getText() + "</FONT>",StartUtil.getUserName(RoomLandActivity.this));
//                            editText.setText("");
//                        } else {
//                            roomMain.getRoom().getChannel().sendChatMsg(0, (byte) 0, (byte) 0, "<FONT style=\"FONT-FAMILY:宋体;FONT-SIZE:14px; COLOR:#000000\">" + editText.getText() + "</FONT>",StartUtil.getUserName(RoomLandActivity.this),Integer.parseInt(StartUtil.getUserLevel(RoomLandActivity.this)));
//                            editText.setText("");
////                        }
//                    }
////                }
//            });
//            giftImage.setOnClickListener(this);
//            faceButton.setOnClickListener(this);
        linearLayout.setOnClickListener(this);
//            userSendBtn.setOnClickListener(this);
//        }
    }

    //接收礼物消息更新
    @Subscriber(tag = "BigGiftRecord")
    public void getGiftRecord(BigGiftRecord obj) {
        int getGiftId = obj.getGiftid();
        int count = obj.getCount();
        String giftTxt = "";
        if (count != 0) {
            if (getGiftId > 0 && getGiftId < 13) {
                RoomChatMsg msg = new RoomChatMsg();
                msg.setToid(-1);
                msg.setContent("g" + giftId + "");
                msg.setSrcid(obj.getSrcid());
                msg.setSrcalias(obj.getSrcalias());
                msg.setDstvcbid(count);
                data.add(msg);
                adapter.notifyDataSetChanged();
                listView.setSelection(listView.getCount() - 1);
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
//        roomText.setText(Html.fromHtml(msg.getContent()));
        Log.d("123", msg.getContent());
//        data.add(msg);
        Spanned spanned = Html.fromHtml(msg.getContent());
//        TextView tv = new TextView(this);
//        tv.setText(spanned);
//        tv.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//        tv.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        tv.setVisibility(View.GONE);
//        danmakuView.addItem(new DanmakuItem(this, spanned, tv.getWidth()));
//        userAdapter.notifyDataSetChanged();
//        listView.setSelection(listView.getCount() - 1);
        addDanmaku(false, spanned);
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
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            userAdapter.notifyDataSetChanged();
            userList.setAdapter(new UserAdapter(userInfos, this));
        }
    }

    //获取用户列表
    @Subscriber(tag = "userList")
    public void getUserList(RoomUserInfo userInfo) {
        userInfos.add(userInfo);
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            userAdapter.notifyDataSetChanged();
            userList.setAdapter(new UserAdapter(userInfos, this));
        }
        Log.d("123", userInfo.getUserid() + "-----------<<");
    }

    private int buddyid;

    @Subscriber(tag = "onMicUser")
    public void getonMicUser(RoomUserInfo obj) {
        micUsers.add(obj);
        buddyid = obj.getUserid();
//        actorid = obj.getActorid();
        toName = obj.getUseralias();
    }

    @Override
    protected void onResume() {
        super.onResume();
        configuration = getResources().getConfiguration();
        isRunning = true;
//        Log.d("123","onResume---");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    play.start();
                    Log.d("123", "chongxingqidong");
                    roomMain.Start(roomId, Integer.parseInt(StartUtil.getUserId(NewRoomLandActivity.this)), StartUtil.getUserPwd(NewRoomLandActivity.this), ip, port, roomPwd);
                }
            }
        }).start();
        if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
            danmakuView.resume();
        }
    }

//    @Override
//    public void onConfigurationChanged(Configuration config) {
//        try {
//            super.onConfigurationChanged(config);
//            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                //横屏时要处理的代码，
//                //这里的代码是当屏幕横屏时当成竖屏显示
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//                //竖屏时要处理的代码
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//            }
//        } catch (Exception ex) {
//        }
//    }


    @Override
    public void onStop() {
        Log.d("123", "onStop---");
        isRunning = false;
        super.onStop();
        if (mgr != null) {
            final AVModuleMgr tmp = mgr;
            mgr = null;
            play.stop();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    tmp.StopRTPSession();
                    tmp.Uninit();
                }
            }).start();

        }
        if (roomMain.getRoom() != null) {
            roomMain.getRoom().getChannel().sendLeaveRoom(Integer.parseInt(StartUtil.getUserId(this)));
            roomMain.getRoom().onDisconnected();
        }
    }

    public void StartAV(String ip, int port, int rand, int uid) {
//        if (app.getMgr() == null) {
//        mgr.Init();
//        Log.d("123", "===uid" + uid);
//        mgr.CreateRTPSession(0);
//        mgr.SetServerAddr2(ip, port, 0);
//        mgr.StartRTPSession();
//        }
        toid = uid;
        if (rand < 1800000000)
            rand = 1800000000;

        ssrc = rand - uid;

        mgr.AddRTPRecver(0, ssrc, 99, 1000);
        mgr.SetRTPRecverARQMode(ssrc, 99, 1);

        mgr.AddRTPRecver(0, ssrc, 97, 1000);
        mgr.SetRTPRecverARQMode(ssrc, 97, 1);
        Log.d("123", "ssrc=====" + ssrc);
        mgr.AddAudioStream(ssrc, 1, this);
        mgr.AddVideoStream(ssrc, 0, 1, this);

    }

    @Override
    public void onVideo(int ssrc, int width, int height, byte[] img) {
        System.out.println("========== onVideo: " + width + ":" + height + "(" + img.length + ")");
        if (textBackImage.isShown()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textBackImage.setVisibility(View.GONE);
                    surfaceView.setVisibility(View.VISIBLE);
                }
            });
        }
        // 删除旧的
        if (null != bmp) {
            if (width != bmp.getWidth() || height != bmp.getHeight()) {
                bmp = null;
            }
        }

        // 创建新的
        if (null == bmp) {
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        ByteBuffer buf = ByteBuffer.wrap(img);
        bmp.copyPixelsFromBuffer(buf);
        // 这是在线程里操作的，千万不要直接在画布上绘制
        // 在surfaceView中显示
        if (null != surfaceView.getHolder()) {
            Canvas canvas = surfaceView.getHolder().lockCanvas();

            if (null != canvas) {
                try {
                    Rect rt1 = new Rect(0, 0, surfaceView.getWidth(), surfaceView.getHeight());
                    Rect rt2 = new Rect(0, 0, width, height);
                    canvas.drawBitmap(bmp, rt2, rt1, null);
                } finally {
                    // 必须要释放，不然下次不会再绘制
                    surfaceView.getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }

    }

    @Override
    public void onAudio(int ssrc, int sample, int channel, byte[] pcm) {
        System.out.println("========== onAudio: " + sample + ":" + channel + "(" + pcm.length + ")");
        if (play != null) {
            play.setConfig(sample, channel);
            play.play(pcm);
        }
    }

    private List<RoomUserInfo> micUsers = new ArrayList<>();
    private String mediaIp;
    private int mediaPort;
    private int mediaRand;

    @Override
    public void onMic(String ip, int port, int rand, int uid) {
        mediaIp = ip;
        mediaPort = port;
        mediaRand = rand;
        // TODO Auto-generated method stub
        if (null == mgr) {
            mgr = new AVModuleMgr();
            Log.d("123", "mgr-----new--" + mgr);
            mgr.Init();
            Log.d("123", "===uid" + uid);
            mgr.CreateRTPSession(0);
            mgr.SetServerAddr2(ip, port, 0);
            mgr.StartRTPSession();
            StartAV(ip, port, rand, uid);
        }
    }

    @Override
    protected void onDestroy() {
        Log.d("123", "onDestory---");
        isRunning = false;
        super.onDestroy();
        if (mgr == null) {

        } else {
            mgr.StopRTPSession();
            mgr.Uninit();
            play.stop();
        }
        EventBus.getDefault().unregister(this);
    }

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

    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            if (y1 - y2 > 50) {
//                Toast.makeText(RoomActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
            } else if (y2 - y1 > 50) {
//                Toast.makeText(RoomActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
            } else if (x1 - x2 > 50) {
                if (micUsers.size() != 1) {
                    mgr.DelAudioStream(ssrc);
                    mgr.DelVideoStream(ssrc);
                    if (toid == micUsers.get(0).getUserid()) {
                        buddyid = micUsers.get(1).getUserid();
                        toName = micUsers.get(1).getUseralias();
                        StartAV(mediaIp, mediaPort, mediaRand, micUsers.get(micUsers.size() - 1).getUserid());
                    }
                }
//                Toast.makeText(RoomActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
            } else if (x2 - x1 > 50) {
                if (micUsers.size() != 1) {
                    mgr.DelAudioStream(ssrc);
                    mgr.DelVideoStream(ssrc);
                    if (toid == micUsers.get(1).getUserid()) {
                        buddyid = micUsers.get(0).getUserid();
                        toName = micUsers.get(0).getUseralias();
                        StartAV(mediaIp, mediaPort, mediaRand, micUsers.get(0).getUserid());
                    }
                }
//                Toast.makeText(RoomActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}