package com.fubang.lihaovv.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
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
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fubang.lihaovv.R;
import com.fubang.lihaovv.App;
import com.fubang.lihaovv.Config;
import com.fubang.lihaovv.SlidingTab.EmotionInputDetector;
import com.fubang.lihaovv.adapters.GiftAdapter;
import com.fubang.lihaovv.adapters.LookUserAdapter;
import com.fubang.lihaovv.adapters.RoomChatAdapter;
import com.fubang.lihaovv.cao.ScreenUtils;
import com.fubang.lihaovv.entities.GiftEntity;
import com.fubang.lihaovv.fragment.EmotionMainFragment;
import com.fubang.lihaovv.fragment.EmotionMainFragment1;
import com.fubang.lihaovv.utils.GiftUtil;
import com.fubang.lihaovv.utils.RecyclerViewDivider;
import com.fubang.lihaovv.utils.ShareUtil;
import com.xlg.android.protocol.AuthorityRejected;
import com.xlg.android.protocol.BigGiftRecord;
import com.xlg.android.protocol.JoinRoomResponse;
import com.xlg.android.protocol.MicState;
import com.xlg.android.protocol.RoomChatMsg;
import com.xlg.android.protocol.RoomKickoutUserInfo;
import com.xlg.android.protocol.RoomUserInfo;
import com.xlg.android.protocol.UseridList;
import com.xlg.android.video.AVModuleMgr;
import com.xlg.android.video.AVNotify;
import com.xlg.android.video.AudioPlay;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.parser.android.BiliDanmukuParser;
import sample.room.MicNotify;
import sample.room.RoomMain;

@EActivity(R.layout.activity_new_room)
public class NewRoomActivity extends BaseActivity implements MicNotify, View.OnClickListener, AVNotify {
    @ViewById(R.id.new_room_roomid)
    TextView roomidTv;
    @ViewById(R.id.new_room_person)
    TextView personTv;
    @ViewById(R.id.new_room_follow)
    ImageView followBtn;
    @ViewById(R.id.new_room_maike)
    ImageView maikeBtn;
    @ViewById(R.id.new_room_back_btn)
    ImageView roomBackBtn;
    @ViewById(R.id.new_room_kb_number)
    TextView kbNumbaerTv;
    @ViewById(R.id.new_room_listview)
    ListView chatListview;
    @ViewById(R.id.new_room_viewpager)
    ViewPager viewPager;
    @ViewById(R.id.new_room_back_image)
    SimpleDraweeView roomBackImage;
    @ViewById(R.id.new_room_more)
    ImageView moreBtn;
    @ViewById(R.id.new_room_chat)
    ImageView chatBtn;
    @ViewById(R.id.new_room_share)
    ImageView shareBtn;
    @ViewById(R.id.new_room_screen)
    ImageView screenBtn;
    @ViewById(R.id.new_room_gift)
    ImageView giftBtn;

    private Configuration configuration;
    private RoomMain roomMain = new RoomMain();
    private String roomPwd;
    private String roomIp;
    private App app;
    private int roomId;
    private String ip;
    private int port;
    private AVModuleMgr mgr;
    private boolean isplaying;

    private List<RoomChatMsg> data = new ArrayList<>();
    private RoomChatAdapter adapter;
    private List<RoomChatMsg> personData = new ArrayList<>();
    private RoomChatAdapter personAdapter;
    private List<RoomUserInfo> userInfos = new ArrayList<>();
    private RoomUserInfo sendToUser;

    private static AudioPlay play  = new AudioPlay();
    private Bitmap bmp;
    private Bitmap bmp1;
    private Bitmap bmp2;
    private int mic0 = 0;
    private int mic1 = 1;
    private int mic2 = 2;
    private int micFlag = 0;
    private List<RoomUserInfo> micUsers;
    private String mediaIp ;
    private int mediaPort;
    private int mediaRand;
    private EditText giftToUser;

    private List<View> views = new ArrayList<>();
    private View view1,view2,view3;
    private SurfaceView surfaceView;
    private SurfaceView surfaceView2;
    private SurfaceView surfaceView3;

    private PopupWindow popupWindow;
    private PopupWindow faceWindow;
    private PopupWindow userWindow;

    @ViewById(R.id.activity_new_room)
    DrawerLayout drawerLayout;
    @ViewById(R.id.new_room_linear)
    LinearLayout linearLayout;
    //=======================键盘页面
    @ViewById(R.id.new_room_chat_input)
    FrameLayout rllRoomInput;
    @ViewById(R.id.new_room_chat_listview)
    TextView listView;
    @ViewById(R.id.new_room_chatline)
    LinearLayout chatLine;


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
    private boolean chatFlag;
//    private Intent intent;
    private PopupWindow maiWindow;
    private EmotionInputDetector mDetector;

//    ==================>私聊布局
    @ViewById(R.id.person_chat_linear)
    LinearLayout personLinear;
    @ViewById(R.id.person_chat_list)
    ListView personList;
    @ViewById(R.id.person_chat_down)
    ImageView personDown;
//    @ViewById(R.id.room_chat_send)
//    Button personSend;
//    @ViewById(R.id.edit_text)
//    EditText editText;
    @ViewById(R.id.person_chat_listview)
    TextView personChatListview;

    @ViewById(R.id.user_listview)
    ExpandableListView userList;
    @ViewById(R.id.user_linear)
    LinearLayout userLinear;
    @ViewById(R.id.userlist_back)
    ImageView userListBack;
    @ViewById(R.id.new_room_chat_list)
    RelativeLayout chatRelative;

    private LookUserAdapter userAdapter;
    private List<RoomUserInfo> userData = new ArrayList<>();

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
    }
    private EmotionMainFragment emotionMainFragment;
    private EmotionMainFragment1 emotionMainFragment2;
    private boolean isShow;
    //===============麦序列表
    @ViewById(R.id.mai_list_eplist)
    ExpandableListView maiListEp;
    private LookUserAdapter maiAdapter;
    @ViewById(R.id.list_check_radio)
    RadioGroup listCheckRadio;
    @ViewById(R.id.down_mai_image)
    ImageView downMaiImage;


    @Override
    public void initView() {
        maiAdapter = new LookUserAdapter(paiUserList,this);
        maiListEp.setAdapter(maiAdapter);
        userAdapter = new LookUserAdapter(userInfos,this);
        userList.setAdapter(userAdapter);
        personAdapter = new RoomChatAdapter(personData,this);
        personList.setAdapter(personAdapter);


        drawerLayout.setScrimColor(Color.TRANSPARENT);//去掉DrawerLayout遮板
        initEmotionMainFragment();
//        initEmotionMainFragment2();
        //亮度调节
        int ligntness = getScreenBrightness(this);
        int ligntness_f = (int) (ligntness / (float) 255 * 100);
        sbLightness.setMax(100);
        sbLightness.setProgress(ligntness_f);

        kbNumbaerTv.setText(StartUtil.getUserKbi(this));

        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.page_surface, null);
        view2 = inflater.inflate(R.layout.page_surface2,null);
        view3 = inflater.inflate(R.layout.page_surface3,null);
//        surfaceView  = (SurfaceView) view1.findViewById(R.id.surface1);
//        surfaceView2 = (SurfaceView) view2.findViewById(R.id.surface2);
//        surfaceView3 = (SurfaceView) view3.findViewById(R.id.surface3);

        views.add(view1);
        views.add(view2);
        views.add(view3);
        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return views.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
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
                if (micUsers != null) {
                    for (int i = 0; i < micUsers.size(); i++) {
                        if (micUsers.get(i).getMicindex() == micFlag) {
                            giftToUser.setText(micUsers.get(i).getUseralias() + "(" + micUsers.get(i).getUserid() + ")");
//                            Log.d("123","toid---"+toid+"toName"+toName);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//            //播放尺寸调节
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(viewPager.getLayoutParams());
//        params.height = ScreenUtils.getScreenWidth(this) / 4 * 3;//竖屏 高是宽度四分之三
//        params.setMargins(0, ScreenUtils.Dp2Px(this, 115), 0, 0);
//        viewPager.setLayoutParams(params);

//        initDanmu();
//        for (int i = 0; i < AppConstant.ROOM_TYPE_TITLE.length; i++) {
//            titles.add(AppConstant.ROOM_TYPE_TITLE[i]);
//        }
//        fragments.add(CommonFragment_.builder().arg(AppConstant.HOME_TYPE,titles.get(0)).build());
//        fragments.add(PersonFragment_.builder().arg(AppConstant.HOME_TYPE,titles.get(1)).build());
//        fragments.add(LookFragment_.builder().arg(AppConstant.HOME_TYPE,titles.get(2)).build());
        roomidTv.setText(roomId+"");
//        roomActivity = this;
        roomBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        roomIdTv.setText(roomId+"");

//        ControllerUtil.showAndHide(testController,roomControl);
//        Log.d("123","oncreate---");
        RoomUserInfo roomUser = new RoomUserInfo();
        roomUser.setUseralias("大厅");
        userInfos.add(roomUser);

        adapter = new RoomChatAdapter(data,this);
        chatListview.setAdapter(adapter);
        showWindow();
        showMai();
//            showFace();
//            showUser();
//        showSet();
        setBackgroundAlpha(this,1f);
        //收藏房间
        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        roomMain.getRoom().getChannel().followRoom(roomId,Integer.parseInt(StartUtil.getUserId(NewRoomActivity.this)));
                    }
                }).start();
                Toast.makeText(NewRoomActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            }
        });
        //全屏切换
//            fullImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    finish();
//                    startActivity(RoomLandActivity_.intent(RoomLandNewActivity.this).extra("roomIp",roomIp).extra("roomId",roomId+"").extra("roomPwd",roomPwd+"").get());
//                }
//            });
        //游戏
        maikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String pwd = StartUtil.getUserPwd(NewRoomActivity.this);
//                String mdPwd = stringToMD5(pwd);
//                String userId = StartUtil.getUserId(NewRoomActivity.this);
//                String mac = StartUtil.getDeviceId(NewRoomActivity.this);
//                StringBuilder gameUrl = new StringBuilder(GAME_URL);
//                //游戏地址url
//                gameUrl.append(userId).append("&passwd=")
//                        .append(mdPwd).append("&sysserial=01f77905-6ea6-4d6b-8b5e-edcc59487f89&mac=")
//                        .append(mac);
//                Uri uri=Uri.parse(gameUrl.toString());//网址一定要加http
//                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
//                startActivity(intent);

//                if (maiWindow.isShowing()){
//                    maiWindow.dismiss();
//                }else {
//                    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://120.26.10.198:88")
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build();
//                    RtmpService service = retrofit.create(RtmpService.class);
//                    service.getRtmpEntity("dskj_"+roomId+"_"+ StartUtil.getUserId(NewRoomActivity.this)).enqueue(new Callback<RtmpEntity>() {
//                        @Override
//                        public void onResponse(Call<RtmpEntity> call, Response<RtmpEntity> response) {
//                            rtmpUrl = response.body().getPublishUrl();
//                        }
//
//                        @Override
//                        public void onFailure(Call<RtmpEntity> call, Throwable t) {
//                            Toast.makeText(NewRoomActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    maiWindow.showAsDropDown(maikeBtn);
//                }
                userLinear.setVisibility(View.VISIBLE);
                isShow = true;
            }
        });
        personDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatLine.setVisibility(View.GONE);
                personLinear.setVisibility(View.GONE);
                isShow = false;
            }
        });
        llRoomRightSiliao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personLinear.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                chatLine.setVisibility(View.VISIBLE);
                isShow = true;
                drawerLayout.closeDrawer(Gravity.RIGHT);
            }
        });
        maiListEp.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                if (childPosition == 0) {
                    Toast.makeText(NewRoomActivity.this, "点击了"+childPosition, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(userInfos.get(groupPosition),"SendToUser");
                }else if (childPosition == 1){
                    Toast.makeText(NewRoomActivity.this, "点击了"+childPosition, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(userInfos.get(groupPosition),"KickOut");
                }else if (childPosition == 2){
                    Toast.makeText(NewRoomActivity.this, "点击了"+childPosition, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(userInfos.get(groupPosition),"ForbidChat");
                }else if (childPosition == 3){
                    Toast.makeText(NewRoomActivity.this, "点击了"+childPosition, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(userInfos.get(groupPosition),"CancelForbidChat");
                }else {
                    Toast.makeText(NewRoomActivity.this, "点击了-----------" + childPosition, Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
        listCheckRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkId) {
                switch (checkId){
                    case R.id.mai_list_btn:
                        maiListEp.setVisibility(View.VISIBLE);
                        userList.setVisibility(View.GONE);
                        break;
                    case R.id.person_list_btn:
                        maiListEp.setVisibility(View.GONE);
                        userList.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }



    private String rtmpUrl = "";
    public void showMai(){
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_choose_mai,null);
        ImageView firstMai = (ImageView) view.findViewById(R.id.pop_1mai);
        ImageView secendMai = (ImageView) view.findViewById(R.id.pop_2mai);
        ImageView thirdMai = (ImageView) view.findViewById(R.id.pop_3mai);
        maiWindow = new PopupWindow(view);
        maiWindow.setFocusable(true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        maiWindow.setWidth(width/2);
        maiWindow.setHeight(height/10);

//        firstMai.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               new Thread(new Runnable() {
//                   @Override
//                   public void run() {
//                       roomMain.getRoom().getChannel().upMicRequest(0);
//                   }
//               }).start();
//               maiWindow.dismiss();
//           }
//       });
//        secendMai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        roomMain.getRoom().getChannel().upMicRequest(1);
//                    }
//                }).start();
//                maiWindow.dismiss();
//            }
//        });
//        thirdMai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        roomMain.getRoom().getChannel().upMicRequest(2);
//                    }
//                }).start();
//                maiWindow.dismiss();
//            }
//        });
        maiWindow.setOutsideTouchable(true);

    }
    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    public static String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    @Override
    public void onBackPressed() {
        /**
         * 判断是否拦截返回键操作
         */
        if (isShow){
            userLinear.setVisibility(View.GONE);
            personLinear.setVisibility(View.GONE);
            chatLine.setVisibility(View.GONE);
            isShow = false;
        }else if (chatFlag){
            listView.setVisibility(View.GONE);
            chatLine.setVisibility(View.GONE);
            chatFlag = false;
        }else {
            super.onBackPressed();

        }
//        !mDetector.interceptBackPress() ||
//        else if ( !emotionMainFragment.isInterceptBackPress()) {
//            super.onBackPressed();
//        }

    }

//    private  FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    //表情页面
    /**
     * 初始化表情面板
     */
    public void initEmotionMainFragment(){
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT,true);
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN,false);
        //替换fragment
        //创建修改实例
        emotionMainFragment = EmotionMainFragment.newInstance(EmotionMainFragment.class,bundle);
        emotionMainFragment.bindToContentView(chatRelative);
         FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        transaction.add(R.id.new_room_chat_input,emotionMainFragment);
//        transaction.replace(R.id.person_chat_input,emotionMainFragment);
        transaction.addToBackStack(null);
//        //提交修改
        transaction.commit();
    }
    /**
     * 初始化表情面板
     */
    public void initEmotionMainFragment2(){
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment1.BIND_TO_EDITTEXT,true);
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment1.HIDE_BAR_EDITTEXT_AND_BTN,false);
        //替换fragment
        //创建修改实例
        emotionMainFragment2 = EmotionMainFragment1.newInstance(EmotionMainFragment1.class,bundle);
        emotionMainFragment2.bindToContentView(personList);
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
//        transaction.replace(R.id.person_chat_input,emotionMainFragment2);
//        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();
    }
    private boolean is_software_show = false;
    public static boolean is_emoticon_show = false;

    @Override
    public void initListener() {

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


    public int getResourceId(String name){
        try {
            Field field = R.drawable.class.getField(name);
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    private Button giftSendBtn;
    private RecyclerView gridView;
    private int giftId;
    private List<GiftEntity> gifts = new ArrayList<>();
    private int toid;
    private String toName;
    //礼物的悬浮框
    private void showWindow() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_gift_grid,null);
//        gridView = (RecyclerView) view.findViewById(R.id.room_gift_list);
        giftSendBtn = (Button) view.findViewById(R.id.gift_send_btn);
        final TextView giftName  = (TextView)view.findViewById(R.id.gift_name_txt);
        final EditText giftCount = (EditText) view.findViewById(R.id.gift_count);
//        final EditText
        giftToUser = (EditText)view.findViewById(R.id.gift_to_user);
        popupWindow = new PopupWindow(view);
        popupWindow.setFocusable(true);
        gifts.addAll(GiftUtil.getGifts());
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        gridView.setLayoutManager(linearLayoutManager);
        gridView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        GiftAdapter giftAdapter = new GiftAdapter(gifts,this);
//        gridView.setAdapter(giftAdapter);
//        giftAdapter.setOnItemClickListener(new GiftAdapter.MyItemClickListener() {
//            @Override
//            public void onItemClick(View view, GiftEntity data) {
//                giftId = data.getGiftId();
//                String name = data.getGiftName();
//                giftName.setText(name+"");
//            }
//        });

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
                    if (micUsers.get(i).getMicindex()==micFlag){
                        toid = micUsers.get(i).getUserid();
                        toName = micUsers.get(i).getUseralias();
//                            Log.d("123","toid---"+toid+"toName"+toName);
                    }
                }
                final int count = Integer.parseInt(giftCount.getText().toString());
                Log.d("123","toid--"+toid+"---giftId---"+giftId+"---count---"+count+"---toName---"+toName);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        roomMain.getRoom().getChannel().sendGiftRecord(toid,giftId,count,toName, StartUtil.getUserName(NewRoomActivity.this));
                    }
                }).start();

                giftName.setText("送给");
                popupWindow.dismiss();
//                sendControl.setVisibility(View.VISIBLE);
            }
        });
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        ColorDrawable dw = new ColorDrawable(0x000000);
//        popupWindow.setBackgroundDrawable(dw);
//        popupWindow.showAsDropDown(giftImage);
//        popupWindow.showAtLocation(roomInputLinear,Gravity.BOTTOM,0,0);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        popupWindow.setHeight(height/3);
        setBackgroundAlpha(this,0.5f);
        popupWindow.setOutsideTouchable(true);

//        //添加pop窗口关闭事件
        popupWindow.setOnDismissListener(new poponDismissListener());
    }
    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            setBackgroundAlpha(NewRoomActivity.this,1f);
        }

    }

    /**
     * 设置页面的透明度
     * @param bgAlpha 1表示不透明
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
    }

    private int personId;
    @Override
    public void initData() {
        downMaiImage.setOnClickListener(this);
        giftBtn.setOnClickListener(this);
        chatBtn.setOnClickListener(this);
        moreBtn.setOnClickListener(this);
        screenBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
        roomBackImage.setOnClickListener(this);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm2.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    chatLine.setVisibility(View.GONE);
                    chatFlag = false;
            }
        });
//        personSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        roomMain.getRoom().getChannel().sendChatMsg(personId,(byte)0,(byte)1,"<FONT style=\"FONT-FAMILY:宋体;FONT-SIZE:14px; COLOR:#000000\">" + editText.getText() + "</FONT>",StartUtil.getUserName(NewRoomActivity.this),Integer.parseInt(StartUtil.getUserLevel(NewRoomActivity.this)));
//                    }
//                }).start();
//                editText.setText("");
//            }
//        });
        userListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLinear.setVisibility(View.GONE);
                isShow = false;
            }
        });
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                personId = userInfos.get(i).getUserid();
//                listView.setVisibility(View.GONE);
//                personLinear.setVisibility(View.VISIBLE);
//                chatLine.setVisibility(View.VISIBLE);
//
//                Log.d("123",personId+"-----personid");
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    private InputMethodManager imm;
    private int downMicId = 0;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.down_mai_image:
                for (int i = 0; i < userInfos.size(); i++) {
                    if (micFlag == userInfos.get(i).getMicindex()){
                        downMicId = userInfos.get(i).getUserid();
                    }
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        roomMain.getRoom().getChannel().downMicRequest(micFlag,downMicId);
                    }
                }).start();
                break;
            case R.id.new_room_back_image:
                break;
            case R.id.new_room_share:
                ShareUtil.getInstance().share(this);
                break;
            case R.id.new_room_chat:
//                if (chatFlag){
//                    chatLine.setVisibility(View.VISIBLE);
//                    chatFlag = false;
//                }else {
                    personLinear.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    chatLine.setVisibility(View.VISIBLE);
                    chatFlag = true;
//                }
                break;
            case R.id.tv_room_input_close:
                break;
            //点击礼物图标
            case R.id.new_room_gift:
                if (popupWindow.isShowing()){
                    popupWindow.dismiss();
                }else {
                    popupWindow.showAsDropDown(giftBtn);
//                    popupWindow.showAtLocation(findViewById(R.id.activity_new_room),
//                            Gravity.BOTTOM, 0, 0);
                    if (micUsers != null) {
                        for (int i = 0; i < micUsers.size(); i++) {
                            if (micUsers.get(i).getMicindex() == micFlag) {
                                giftToUser.setText(micUsers.get(i).getUseralias() + "(" + micUsers.get(i).getUserid() + ")");
//                            Log.d("123","toid---"+toid+"toName"+toName);
                            }
                        }
                    }
                }
                break;
            case R.id.new_room_more:
                drawerLayout.openDrawer(Gravity.RIGHT);
                break;
            //全屏
            case R.id.new_room_screen:
                if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                    KLog.e("SCREEN_ORIENTATION_LANDSCAPE"); // 横屏
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(viewPager.getLayoutParams());
                    params.height = ScreenUtils.getScreenWidth(this) / 4 * 3;//竖屏 高是宽度四分之三
                    params.setMargins(0, ScreenUtils.Dp2Px(this, 115), 0, 0);
                    viewPager.setLayoutParams(params);
                } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                    KLog.e("SCREEN_ORIENTATION_PORTRAIT"); // 竖屏
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(viewPager.getLayoutParams());
                    params.height = ScreenUtils.getScreenHeight(this);//竖屏 高是宽度四分之三
                    params.setMargins(0, 0, 0, 0);
                    viewPager.setLayoutParams(params);
                }
                break;

        }
    }
    //接收礼物消息更新
    @Subscriber(tag="BigGiftRecord")
    public void getGiftRecord(BigGiftRecord obj){
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
                    if (getGiftId>616 && getGiftId<636) {
                        RoomChatMsg msg = new RoomChatMsg();
                        msg.setToid(-1);
                        msg.setContent("g" + getGiftId + "");
                        msg.setSrcid(obj.getSrcid());
                        msg.setSrcalias(obj.getSrcalias());
                        msg.setDstvcbid(count);
                        data.add(msg);
                        adapter.notifyDataSetChanged();
                        chatListview.setSelection(chatListview.getCount() - 1);
                    }
                }
            }
        }
    }
    //聊天发送
    @Subscriber(tag = "msgText")
    public void sendMsgText(final String msgText){
        if (!isShow) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    roomMain.getRoom().getChannel().sendChatMsg(0, (byte) 0, (byte) 0, "<FONT style=\"FONT-FAMILY:宋体;FONT-SIZE:14px; COLOR:#000000\">" + msgText + "</FONT>", StartUtil.getUserName(NewRoomActivity.this), Integer.parseInt(StartUtil.getUserLevel(NewRoomActivity.this)));
                }
            }).start();

        }else {
        new Thread(new Runnable() {
                    @Override
                    public void run() {
                        roomMain.getRoom().getChannel().sendChatMsg(personId,(byte)0,(byte)1,"<FONT style=\"FONT-FAMILY:宋体;FONT-SIZE:14px; COLOR:#000000\">" + msgText + "</FONT>",StartUtil.getUserName(NewRoomActivity.this),Integer.parseInt(StartUtil.getUserLevel(NewRoomActivity.this)));
                    }
                }).start();
        }
    }

    //抱上1麦
    @Subscriber(tag = "FirstMic")
    public void getFirstMic(final RoomUserInfo obj){
//        sendToUser = obj;
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.getRoom().getChannel().baoMicRequest(0,obj.getUserid());
            }
        }).start();

    }

    //抱上2麦
    @Subscriber(tag = "SecondMic")
    public void getSecondMic(final RoomUserInfo obj){
//        sendToUser = obj;
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.getRoom().getChannel().baoMicRequest(1,obj.getUserid());
            }
        }).start();
    }

    //抱上3麦
    @Subscriber(tag = "ThirdMic")
    public void getThirdMic(final RoomUserInfo obj){
//        sendToUser = obj;
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.getRoom().getChannel().baoMicRequest(2, obj.getUserid());
            }
        }).start();
    }

    //私聊发送
    @Subscriber(tag = "PersonChat")
    public void getPersonChat(RoomUserInfo obj){
        personId = obj.getUserid();
//        personId = userInfos.get(i).getUserid();
        listView.setVisibility(View.GONE);
        personLinear.setVisibility(View.VISIBLE);
        chatLine.setVisibility(View.VISIBLE);

        Log.d("123",personId+"-----personid");
    }

    //私聊发送
    @Subscriber(tag = "SendToUser")
    public void getSendToUser(RoomUserInfo obj){
//        sendToUser = obj;
    }
    private String pwd = "";
    //加入房间错误
    @Subscriber(tag = "joinRoomError")
    public void jionRoomError(final int err){
        if (err == 503){
            Toast.makeText(this, "房间密码错误请输入", Toast.LENGTH_SHORT).show();
            // 弹出自定义dialog
            LayoutInflater inflater = LayoutInflater.from(NewRoomActivity.this);
            View view = inflater.inflate(R.layout.dialog_input_pwd, null);

            // 对话框
            final Dialog dialog = new Dialog(NewRoomActivity.this);
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
                            roomMain.Start(roomId,Integer.parseInt(StartUtil.getUserId(NewRoomActivity.this)), StartUtil.getUserPwd(NewRoomActivity.this), ip, port, pwd);
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
        }else if(err == 406 || err == 407 || err == 408){
            Toast.makeText(NewRoomActivity.this, "您已被封号请联系客服", Toast.LENGTH_SHORT).show();
            finish();
        }else if(err == 417){
            Toast.makeText(NewRoomActivity.this, "该房间限制等级进入", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isRunning = true;
    private int connectNumbaer = 1;
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
                        roomMain.Start(roomId, Integer.parseInt(StartUtil.getUserId(NewRoomActivity.this)), StartUtil.getUserPwd(NewRoomActivity.this), ip, port,pwd);
                    }
                }
            }).start();
            connectNumbaer ++;
        }else {
            Toast.makeText(NewRoomActivity.this, "加入房间失败", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    //加入房间返回消息
    @Subscriber(tag = "JoinRoomResponse")
    public void getJoinRoomResponse(JoinRoomResponse obj){
//        topline = obj.getTopline() - 1000;
        String str = obj.getMediaserver();
        String ips[] = str.split(";");
        if(ips.length > 0) {
            String s[] = ips[0].split(":");
            if(s.length > 1) {
                mediaIp = s[0];
                mediaPort = Integer.valueOf(s[1]).intValue();
//				videoPort = Integer.valueOf(s[1]);
            }
        }
    }
    //接收服务器发送的消息更新列表
    @Subscriber(tag="RoomChatMsg")
    public void getRoomChatMsg(RoomChatMsg msg){
        Log.d("123",msg.getContent());
        if(msg.getMsgtype() == 0) {
            if (msg.getIsprivate() == 0){
                //("<b><FONT style=\"FONT-FAMILY:宋体;FONT-SIZE:17px; COLOR:#FF0000\">/mr599</FONT></b>")) {
                //<b><FONT style="FONT-FAMILY:宋体;FONT-SIZE:17px; COLOR:#FF0000">/mr599</FONT></b>
//                EventBus.getDefault().post(msg,"CommonMsg");
//                listView.setSelection(listView.getCount() - 1);
                String spanned = String.valueOf(Html.fromHtml(msg.getContent()));
//                Log.d("123",spanned+"-----spanned");
                if (spanned.length()==14 || spanned.length() == 15){
                    String s = spanned.substring(6, 11);
//                    Log.d("123",s+"----s");
                    if (s.equals("tions")){
                        int nums = Integer.parseInt(spanned.substring(11,14));
//                        Log.d("123",nums+"-----nums");
                        if (nums<800 || nums >883){

                        }
                        else {
                            data.add(msg);
                            adapter.notifyDataSetChanged();
                            chatListview.setSelection(chatListview.getCount() - 1);
                        }
                    }
                    else {
                        data.add(msg);
                        adapter.notifyDataSetChanged();
                        chatListview.setSelection(chatListview.getCount() - 1);
                    }
                }else {
                    data.add(msg);
                    adapter.notifyDataSetChanged();
                    chatListview.setSelection(chatListview.getCount() - 1);
                }
//                chatListview.setVisibility(View.VISIBLE);
            }
            if (msg.getIsprivate() == 1){
                if (msg.getSrcid() == Integer.parseInt(StartUtil.getUserId(this)) || msg.getToid() == Integer.parseInt(StartUtil.getUserId(this))) {
                    //("<b><FONT style=\"FONT-FAMILY:宋体;FONT-SIZE:17px; COLOR:#FF0000\">/mr599</FONT></b>")) {
                    //<b><FONT style="FONT-FAMILY:宋体;FONT-SIZE:17px; COLOR:#FF0000">/mr599</FONT></b>
//                EventBus.getDefault().post(msg,"CommonMsg");
//                listView.setSelection(listView.getCount() - 1);
                    String spanned = String.valueOf(Html.fromHtml(msg.getContent()));
//                Log.d("123",spanned+"-----spanned");
                    if (spanned.length() == 14 || spanned.length() == 15) {
                        String s = spanned.substring(6, 11);
//                    Log.d("123",s+"----s");
                        if (s.equals("tions")) {
                            int nums = Integer.parseInt(spanned.substring(11, 14));
//                        Log.d("123",nums+"-----nums");
                            if (nums < 800 || nums > 883) {

                            } else {
                                personData.add(msg);
                                personAdapter.notifyDataSetChanged();
                                personList.setSelection(personList.getCount() - 1);
                            }
                        } else {
                            personData.add(msg);
                            personAdapter.notifyDataSetChanged();
                            personList.setSelection(personList.getCount() - 1);
                        }
                    } else {
                        personData.add(msg);
                        personAdapter.notifyDataSetChanged();
                        personList.setSelection(personList.getCount() - 1);
                    }
//                chatListview.setVisibility(View.VISIBLE);
                }
            }
        }
        if (msg.getMsgtype() == 12 && msg.getSrcid() == 2) {

            Spanned spanned = Html.fromHtml(msg.getContent());
            Log.d("123",spanned+"");
//            addDanmaku(false, spanned);
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
    //用户离开房间
    @Subscriber(tag = "RoomKickoutUserInfo")
    public void getUserOut(RoomKickoutUserInfo obj){
        int leaveId = obj.getToid();
        for (int i = 0; i < userInfos.size(); i++) {
            if (userInfos.get(i).getUserid() == leaveId){
                userInfos.remove(i);
            }
        }
    }
    //获取用户列表
    @Subscriber(tag = "userList")
    public void getUserList(RoomUserInfo userInfo){
        userInfos.add(userInfo);
        personTv.setText(userInfos.size()+"在线");
        userAdapter.notifyDataSetChanged();
    }

    private List<RoomUserInfo>  paiUserList = new ArrayList<>();
    //获取麦序列表
//    @Subscriber(tag = "UseridList")
//    public void getUseridList(UseridList obj){
//        for (int i = 0; i < obj.getList().length; i++) {
//            for (int j = 0; j < userInfos.size(); j++) {
//                if (obj.getList()[i] == userInfos.get(j).getUserid()){
//                    paiUserList.add(userInfos.get(j));
//                    maiAdapter.notifyDataSetChanged();
//                }
//            }
////            if (obj.getList()[i] == userInfos)
//        }
//    }

    //开始加入房间
    @Override
    protected void onResume() {
        super.onResume();
        micUsers = new ArrayList<>();
        configuration = getResources().getConfiguration();
        startPush = false;
        isRunning = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    play.start();
                    Log.d("123", "chongxingqidong");
//                    roomMain.Start(123, 10000, "123123", ip, port, pwd);
                    roomMain.Start(roomId, Integer.parseInt(StartUtil.getUserId(NewRoomActivity.this)), StartUtil.getUserPwd(NewRoomActivity.this), ip, port, pwd);
                }
            }
        }).start();
    }

//    @Override
//    protected void onPause() {
//        isRunning = false;
//        super.onPause();
////        mStop = true;
//        if (mgr != null){
//            final AVModuleMgr tmp = mgr;
//            mgr = null;
//            play.stop();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    tmp.StopRTPSession();
//                    tmp.Uninit();
//                    roomMain.getRoom().getChannel().sendLeaveRoom(Integer.parseInt(StartUtil.getUserId(NewRoomActivity.this)));
//                    roomMain.getRoom().onDisconnected();
//                }
//            }).start();
//        }
//
//    }

    //停止视频流,结束离开房间
    @Override
    public void onStop() {
        Log.d("123","onStop---");
        isRunning = false;
        super.onStop();
//        mStop = true;
        if (mgr != null){
            final AVModuleMgr tmp = mgr;
            mgr = null;
            play.stop();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    tmp.StopRTPSession();
                    tmp.Uninit();
//                    roomMain.getRoom().getChannel().sendLeaveRoom(Integer.parseInt(StartUtil.getUserId(NewRoomActivity.this)));
//                    roomMain.getRoom().onDisconnected();
                }
            }).start();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.getRoom().getChannel().sendLeaveRoom(Integer.parseInt(StartUtil.getUserId(NewRoomActivity.this)));
                roomMain.getRoom().onDisconnected();
            }
        }).start();

    }

    @Subscriber(tag = "AuthorityRejected")
    public void getAuthorityRejected(AuthorityRejected obj){
        Toast.makeText(this, obj.getErrmsg(), Toast.LENGTH_SHORT).show();
    }


    private int ssrc;
    private int micid;
    private int upMicFlag;
    private boolean startPush;
    //上麦提示
    @Subscriber(tag = "upMicState")
    public void upMicState(MicState obj){
        roomBackImage.setVisibility(View.GONE);
        if (obj.getUserid()==Integer.parseInt(StartUtil.getUserId(this))){
//            startActivity(new Intent());
            if (!startPush) {
                startPush = true;
                upMicFlag = obj.getMicindex();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(NewRoomActivity.this, RoomActivity.class);
                        intent.putExtra("RTMPURL", rtmpUrl);
                        intent.putExtra("roomIp", roomIp);
                        intent.putExtra("roomId", roomId);
                        intent.putExtra("roomPwd", roomPwd);
                        intent.putExtra("micId", upMicFlag);
                        startActivity(intent);
                    }
                });
            }
        }
//        roomBackImage.setVisibility(View.GONE);
        for (int i = 0; i < userInfos.size(); i++) {
            if (obj.getUserid()==userInfos.get(i).getUserid()){
                userInfos.get(i).setMicindex(obj.getMicindex());
                micUsers.add(userInfos.get(i));
            }
        }

        micid = obj.getUserid();
        ssrc = ~micid  + 0x1314;
//        ssrc = obj.getUserid();

        //创建视频接收流
        // TODO Auto-generated method stub
        if(null == mgr) {
            mgr = new AVModuleMgr();
            Log.d("123","mgr-----new--"+mgr);
            mgr.Init();
            Log.d("123", "===uid" + micid);
            mgr.CreateRTPSession(0);
            mgr.SetServerAddr2(mediaIp, mediaPort, 0);
            mgr.StartRTPSession();
        }
        mgr.AddRTPRecver(0, ssrc, 99, 1000);
        mgr.SetRTPRecverARQMode(ssrc, 99, 1);

        mgr.AddRTPRecver(0, ssrc, 97, 1000);
        mgr.SetRTPRecverARQMode(ssrc, 97, 1);
//        Log.d("123","ssrc====="+ssrc);
        mgr.AddVideoStream(ssrc,  0, 1, this);
        if (!isplaying)
            mgr.AddAudioStream(ssrc,1,this);

    }
    //下麦提示
    @Subscriber(tag = "downMicState")
    public void downMicState(MicState obj){
        for (int i = 0; i < micUsers.size(); i++) {
            if (micUsers.get(i).getUserid()==obj.getUserid()){
                micUsers.remove(i);
            }
        }
    }
    //麦上几个人就添加视频流
    @Subscriber(tag = "onMicUser")
    public void getonMicUser(RoomUserInfo obj){
        roomBackImage.setVisibility(View.GONE);
        if (obj.getMicindex() == 0){
            sendToUser = obj;
        }
        micUsers.add(obj);
//        actorid = obj.getActorid();
//        buddyid = obj.getUserid();
//        toName = obj.getUseralias();
        if(obj.getMicindex() != 0) {
            micid = obj.getUserid();
            Log.d("123", "micid=====" + micid);
            ssrc = ~micid + 0x1314;

            mgr.AddRTPRecver(0, ssrc, 99, 1000);
            mgr.SetRTPRecverARQMode(ssrc, 99, 1);

            mgr.AddRTPRecver(0, ssrc, 97, 1000);
            mgr.SetRTPRecverARQMode(ssrc, 97, 1);
            Log.d("123", "ssrc=====" + ssrc);
            mgr.AddVideoStream(ssrc, 0, 1, this);
            if (!isplaying) {
                mgr.AddAudioStream(ssrc, 1, this);
            }
        }


    }
    private int ssrcFlag;
    //开始接收添加视频接收流
    public void StartAV(String ip, int port, int rand, int uid) {

//        ssrc = uid;
//        ssrc = rand - uid;
//        if (rand < 1800000000)
//            rand = 1800000000;
//        ssrc = rand - uid;
        ssrc = ~uid + 0x1314;

        mgr.AddRTPRecver(0, ssrc, 99, 1000);
        mgr.SetRTPRecverARQMode(ssrc, 99, 1);

        mgr.AddRTPRecver(0, ssrc, 97, 1000);
        mgr.SetRTPRecverARQMode(ssrc, 97, 1);
        Log.d("123","ssrc====="+ssrc);
//        isplaying = true;
        mgr.AddAudioStream(ssrc, 1, this);
        mgr.AddVideoStream(ssrc,  0, 1, this);

    }
    //接收视频流
    @Override
    public void onVideo(int ssrc, int width, int height, byte[] img) {
//        if(false != mStop) {
//            return;
//        }
        Paint paint = new Paint();
        paint.setAntiAlias(false);
        paint.setFilterBitmap(true);
//        paint.setDither(true);
//        Paint paint = new Paint(Paint.DITHER_FLAG);
        System.out.println("==ssrc"+ssrc+"======== onVideo: " + width + ":" + height + "(" + img.length + ")");
        //判断几个在麦序上
        if (micUsers.size() == 1){
            //一麦显示一麦,二麦显示二麦,三麦显示三麦
            if ((micUsers.get(0).getMicindex() == mic0 && ssrc == (~micUsers.get(0).getUserid() + 0x1314))) {
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
            }else if ((micUsers.get(0).getMicindex() == mic1 && ssrc == (~micUsers.get(0).getUserid() + 0x1314))){
                // 删除旧的
                if (null != bmp1) {
                    if (width != bmp1.getWidth() || height != bmp1.getHeight()) {
                        bmp1 = null;
                    }
                }

                // 创建新的
                if (null == bmp1) {
                    bmp1 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                }

                ByteBuffer buf = ByteBuffer.wrap(img);
                bmp1.copyPixelsFromBuffer(buf);
                // 这是在线程里操作的，千万不要直接在画布上绘制
                // 在surfaceView中显示
                if (null != surfaceView2.getHolder()) {
                    Canvas canvas = surfaceView2.getHolder().lockCanvas();

                    if (null != canvas) {
                        try {
                            Rect rt1 = new Rect(0, 0, surfaceView2.getWidth(), surfaceView2.getHeight());
                            Rect rt2 = new Rect(0, 0, width, height);
                            canvas.drawBitmap(bmp1, rt2, rt1, null);
                        } finally {
                            // 必须要释放，不然下次不会再绘制
                            surfaceView2.getHolder().unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }else if ((micUsers.get(0).getMicindex() == mic2 && ssrc == (~micUsers.get(0).getUserid()+ 0x1314 )))
            {
                // 删除旧的
                if (null != bmp2) {
                    if (width != bmp2.getWidth() || height != bmp2.getHeight()) {
                        bmp2 = null;
                    }
                }

                // 创建新的
                if (null == bmp2) {
                    bmp2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                }

                ByteBuffer buf = ByteBuffer.wrap(img);
                bmp2.copyPixelsFromBuffer(buf);
                // 这是在线程里操作的，千万不要直接在画布上绘制
                // 在surfaceView中显示
                if (null != surfaceView3.getHolder()) {
                    Canvas canvas = surfaceView3.getHolder().lockCanvas();

                    if (null != canvas) {
                        try {
                            Rect rt1 = new Rect(0, 0, surfaceView3.getWidth(), surfaceView3.getHeight());
                            Rect rt2 = new Rect(0, 0, width, height);
                            canvas.drawBitmap(bmp2, rt2, rt1, null);
                        } finally {
                            // 必须要释放，不然下次不会再绘制
                            surfaceView3.getHolder().unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        }
        if (micUsers.size()==2){
            if ((micUsers.get(0).getMicindex() == mic0 && ssrc == (~micUsers.get(0).getUserid()+ 0x1314)) ||
                    (micUsers.get(1).getMicindex() == mic0 && ssrc == (~micUsers.get(1).getUserid()+ 0x1314 ))) {
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
            }else if ((micUsers.get(0).getMicindex() == mic1 && ssrc == (~micUsers.get(0).getUserid()+ 0x1314)) ||
                    (micUsers.get(1).getMicindex() == mic1 && ssrc == (~micUsers.get(1).getUserid() + 0x1314))){
                // 删除旧的
                if (null != bmp1) {
                    if (width != bmp1.getWidth() || height != bmp1.getHeight()) {
                        bmp1 = null;
                    }
                }

                // 创建新的
                if (null == bmp1) {
                    bmp1 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                }

                ByteBuffer buf = ByteBuffer.wrap(img);
                bmp1.copyPixelsFromBuffer(buf);
                // 这是在线程里操作的，千万不要直接在画布上绘制
                // 在surfaceView中显示
                if (null != surfaceView2.getHolder()) {
                    Canvas canvas = surfaceView2.getHolder().lockCanvas();

                    if (null != canvas) {
                        try {
                            Rect rt1 = new Rect(0, 0, surfaceView2.getWidth(), surfaceView2.getHeight());
                            Rect rt2 = new Rect(0, 0, width, height);
                            canvas.drawBitmap(bmp1, rt2, rt1, null);
                        } finally {
                            // 必须要释放，不然下次不会再绘制
                            surfaceView2.getHolder().unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }else if ((micUsers.get(0).getMicindex() == mic2 && ssrc == (~micUsers.get(0).getUserid()+ 0x1314 )) ||
                    (micUsers.get(1).getMicindex() == mic2 && ssrc == (~micUsers.get(1).getUserid()+ 0x1314 )))
            {
                // 删除旧的
                if (null != bmp2) {
                    if (width != bmp2.getWidth() || height != bmp2.getHeight()) {
                        bmp2 = null;
                    }
                }

                // 创建新的
                if (null == bmp2) {
                    bmp2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                }

                ByteBuffer buf = ByteBuffer.wrap(img);
                bmp2.copyPixelsFromBuffer(buf);
                // 这是在线程里操作的，千万不要直接在画布上绘制
                // 在surfaceView中显示
                if (null != surfaceView3.getHolder()) {
                    Canvas canvas = surfaceView3.getHolder().lockCanvas();

                    if (null != canvas) {
                        try {
                            Rect rt1 = new Rect(0, 0, surfaceView3.getWidth(), surfaceView3.getHeight());
                            Rect rt2 = new Rect(0, 0, width, height);
                            canvas.drawBitmap(bmp2, rt2, rt1, null);
                        } finally {
                            // 必须要释放，不然下次不会再绘制
                            surfaceView3.getHolder().unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        }
        if (micUsers.size()==3) {
            if ((micUsers.get(0).getMicindex() == mic0 && ssrc == (~micUsers.get(0).getUserid() + 0x1314)) ||
                    (micUsers.get(1).getMicindex() == mic0 && ssrc == (~micUsers.get(1).getUserid() + 0x1314)) ||
                    (micUsers.get(2).getMicindex() == mic0 && ssrc == (~micUsers.get(2).getUserid()+ 0x1314 ))) {

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
            }else if ((micUsers.get(0).getMicindex() == mic1 && ssrc == (~micUsers.get(0).getUserid()+ 0x1314)) ||
                    (micUsers.get(1).getMicindex() == mic1 && ssrc == (~micUsers.get(1).getUserid() + 0x1314)) ||
                    (micUsers.get(2).getMicindex() == mic1 && ssrc == (~micUsers.get(2).getUserid()+ 0x1314))){
                // 删除旧的
                if (null != bmp1) {
                    if (width != bmp1.getWidth() || height != bmp1.getHeight()) {
                        bmp1 = null;
                    }
                }

                // 创建新的
                if (null == bmp1) {
                    bmp1 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                }

                ByteBuffer buf = ByteBuffer.wrap(img);
                bmp1.copyPixelsFromBuffer(buf);
                // 这是在线程里操作的，千万不要直接在画布上绘制
                // 在surfaceView中显示
                if (null != surfaceView2.getHolder()) {
                    Canvas canvas = surfaceView2.getHolder().lockCanvas();

                    if (null != canvas) {
                        try {
                            Rect rt1 = new Rect(0, 0, surfaceView2.getWidth(), surfaceView2.getHeight());
                            Rect rt2 = new Rect(0, 0, width, height);
                            canvas.drawBitmap(bmp1, rt2, rt1, null);
                        } finally {
                            // 必须要释放，不然下次不会再绘制
                            surfaceView2.getHolder().unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }else if ((micUsers.get(0).getMicindex() == mic2 && ssrc == (~micUsers.get(0).getUserid() + 0x1314)) ||
                    (micUsers.get(1).getMicindex() == mic2 && ssrc == (~micUsers.get(1).getUserid()+ 0x1314 )) ||
                    (micUsers.get(2).getMicindex() == mic2 && ssrc == (~micUsers.get(2).getUserid()+ 0x1314 )))
            {
                // 删除旧的
                if (null != bmp2) {
                    if (width != bmp2.getWidth() || height != bmp2.getHeight()) {
                        bmp2 = null;
                    }
                }

                // 创建新的
                if (null == bmp2) {
                    bmp2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                }

                ByteBuffer buf = ByteBuffer.wrap(img);
                bmp2.copyPixelsFromBuffer(buf);
                // 这是在线程里操作的，千万不要直接在画布上绘制
                // 在surfaceView中显示
                if (null != surfaceView3.getHolder()) {
                    Canvas canvas = surfaceView3.getHolder().lockCanvas();

                    if (null != canvas) {
                        try {
                            Rect rt1 = new Rect(0, 0, surfaceView3.getWidth(), surfaceView3.getHeight());
                            Rect rt2 = new Rect(0, 0, width, height);
                            canvas.drawBitmap(bmp2, rt2, rt1, null);
                        } finally {
                            // 必须要释放，不然下次不会再绘制
                            surfaceView3.getHolder().unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        }



    }
    private boolean mStop = false;
    //音频接收播放
    @Override
    public void onAudio(int ssrc, int sample, int channel, byte[] pcm) {
        System.out.println("========== onAudio: " + sample + ":" + channel + "(" + pcm.length + ")");
        if (play != null) {
            isplaying = true;
            play.setConfig(sample, channel);
            play.play(pcm);
        }
    }

    @Override
    public void onMic(String ip, int port, int rand, int uid) {
        mediaIp = ip;
        mediaPort = port;
        mediaRand = rand;
        //创建视频接收流
        // TODO Auto-generated method stub
        if(null == mgr) {
            mgr = new AVModuleMgr();
            Log.d("123","mgr-----new--"+mgr);
            mgr.Init();
            Log.d("123", "===uid" + uid);
            mgr.CreateRTPSession(0);
            mgr.SetServerAddr2(ip, port, 0);
            mgr.StartRTPSession();
            StartAV(ip, port, rand, uid);
        }
    }
    //320086319
    @Override
    protected void onDestroy() {
//        Log.d("123","onDestory---");
       isRunning = false;
        super.onDestroy();
        if (mgr == null){

        }else {
            mgr.StopRTPSession();
            mgr.Uninit();
            play.stop();
        }
        EventBus.getDefault().unregister(this);
    }

    private void startStreamingActivity(final Intent intent1) {
//        if (!isPermissionOK()) {
//            return;
//        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                String publishUrl = null;
                intent1.putExtra(Config.EXTRA_KEY_PUB_URL, publishUrl);
                startActivity(intent1);
            }
        }).start();
    }

    private boolean isPermissionOK() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mPermissionEnabled = true;
            return true;
        }
        else {
            return checkPermission();
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    private boolean checkPermission() {
        boolean ret = true;

        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.CAMERA)) {
            permissionsNeeded.add("CAMERA");
        }
        if (!addPermission(permissionsList, Manifest.permission.RECORD_AUDIO)) {
            permissionsNeeded.add("MICROPHONE");
        }
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissionsNeeded.add("Write external storage");
        }

        if (permissionsNeeded.size() > 0) {
            // Need Rationale
            String message = "You need to grant access to " + permissionsNeeded.get(0);
            for (int i = 1; i <     permissionsNeeded.size(); i++) {
                message = message + ", " + permissionsNeeded.get(i);
            }
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permissionsList.get(0))) {
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
            }
            else {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
            ret = false;
        }

        return ret;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        boolean ret = true;
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            ret = false;
        }
        return ret;
    }
    private boolean mPermissionEnabled = false;
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(NewRoomActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

//
//    @Override
//    public boolean onTouch(View view, MotionEvent event) {
//        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//            //当手指按下的时候
//            x1 = event.getX();
//            y1 = event.getY();
//        }
//        if(event.getAction() == MotionEvent.ACTION_UP) {
//            //当手指离开的时候
//            x2 = event.getX();
//            y2 = event.getY();
//            if(y1 - y2 > 50) {
////                Toast.makeText(RoomActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
//            } else if(y2 - y1 > 50) {
////                Toast.makeText(RoomActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
//            } else if(x1 - x2 > 50) {
//                if (micUsers.size() != 1) {
///**
// *   if (micUsers.size()>1) {
// //                textBackImage.setVisibility(View.VISIBLE);
// //                surfaceView.setVisibility(View.GONE);
// //                textBackImage2.setVisibility(View.VISIBLE);
// //                surfaceView2.setVisibility(View.GONE);
// //                int a = mic0;
// //                mic0 = mic1;
// //                mic1 = a;
// ////                }
// */
//                    if (micFlag == 0){
//                        micFlag++;
////                        textBackImage.setVisibility(View.GONE);
//                        surfaceView2.setVisibility(View.VISIBLE);
//                        surfaceView.setVisibility(View.GONE);
//                        surfaceView3.setVisibility(View.GONE);
////                    Toast.makeText(this, "暂无更多主播", Toast.LENGTH_SHORT).show();
//                    }else if (micFlag == 1){
//                        micFlag++;
//                        surfaceView3.setVisibility(View.VISIBLE);
//                        surfaceView.setVisibility(View.GONE);
//                        surfaceView2.setVisibility(View.GONE);
//                    }
//
////                    mgr.DelAudioStream(ssrc);
////                    mgr.DelVideoStream(ssrc);
////                    if (toid == micUsers.get(0).getUserid()) {
////                        buddyid = micUsers.get(1).getUserid();
////                        toName = micUsers.get(1).getUseralias();
////                        StartAV(mediaIp, mediaPort, mediaRand, micUsers.get(micUsers.size() - 1).getUserid());
////                    }
//                }
////                Toast.makeText(RoomActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
//            } else if(x2 - x1 > 50) {
//                if (micFlag == 1){
//                    micFlag--;
////                    textBackImage.setVisibility(View.VISIBLE);
//                    surfaceView.setVisibility(View.VISIBLE);
//                    surfaceView2.setVisibility(View.GONE);
//                    surfaceView3.setVisibility(View.GONE);
////                    Toast.makeText(this, "暂无更多主播", Toast.LENGTH_SHORT).show();
//                }else if (micFlag == 2){
//                    micFlag--;
//
//                    surfaceView2.setVisibility(View.VISIBLE);
//                    surfaceView3.setVisibility(View.GONE);
//                    surfaceView.setVisibility(View.GONE);
//                }
////                if (micUsers.size() != 1) {
////                    mgr.DelAudioStream(ssrc);
////                    mgr.DelVideoStream(ssrc);
////                    if (toid == micUsers.get(1).getUserid()) {
////                        buddyid = micUsers.get(0).getUserid();
////                        toName = micUsers.get(0).getUseralias();
////                        StartAV(mediaIp, mediaPort, mediaRand, micUsers.get(0).getUserid());
////                    }
////                }
////                Toast.makeText(RoomActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
//            }
//            else {
//                if (testController.isShown()){
//                    testController.setVisibility(View.GONE);
//
//                }else {
//                    testController.setVisibility(View.VISIBLE);
//                    AlphaAnimation animation1 = new AlphaAnimation(1.0f,0.0f);
//                    animation1.setDuration(100 * 100);
//                    animation1.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                            testController.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
//                    testController.setAnimation(animation1);
//                    animation1.start();
//                }
//            }
//        }
//        if (micUsers != null) {
//            for (int i = 0; i < micUsers.size(); i++) {
//                if (micUsers.get(i).getMicindex() == micFlag) {
//                    giftToUser.setText(micUsers.get(i).getUseralias() + "(" + micUsers.get(i).getUserid() + ")");
////                            Log.d("123","toid---"+toid+"toName"+toName);
//                }
//            }
//        }
//        return true;
//    }
}
