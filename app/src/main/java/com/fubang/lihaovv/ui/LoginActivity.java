package com.fubang.lihaovv.ui;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fubang.lihaovv.MainActivity_;
import com.fubang.lihaovv.R;
import com.fubang.lihaovv.entities.UserEntity;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.utils.Tools;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import sample.login.LoginMain;

/**
 * 登录页面
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements View.OnClickListener, PlatformActionListener {
    @ViewById(R.id.login_qq_image)
    ImageView imageView;
    @ViewById(R.id.login_username)
    EditText userNameEdit;
    @ViewById(R.id.login_password)
    EditText userPswEdit;
    @ViewById(R.id.login_login)
    Button loginBtn;
    private int username;
    private String pwd;
    @ViewById(R.id.bt_username_clear)
    Button nameClearBtn;
    @ViewById(R.id.bt_pwd_clear)
    Button pwdClearBtn;
//    @ViewById(R.id.login_youke_btn)
//    Button youkeBtn;
    @ViewById(R.id.login_wechat_image)
ImageView wechatImage;
    @ViewById(R.id.user_help_tv)
    TextView helpTv;
//    @ViewById(R.id.register_user_tv)
//    TextView registerTv;

    private int flag = 0;
    private String userIcon;
    private String userName;
    private String userId;


    @Override
    public void before() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        imageView.setOnClickListener(this);
        wechatImage.setOnClickListener(this);
        nameClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameEdit.setText("");
            }
        });
        pwdClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPswEdit.setText("");
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            username = Integer.parseInt(userNameEdit.getText().toString());
            pwd = userPswEdit.getText().toString();
//            StartUtil.putCount(LoginActivity.this,flag);
            if (!TextUtils.isEmpty(userNameEdit.getText())&&!TextUtils.isEmpty(pwd)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new LoginMain(username, pwd,"", 0, 0,LoginActivity.this).start(username, pwd, 0, "",0,LoginActivity.this);
                    }
                }).start();
            }else {
                Toast.makeText(LoginActivity.this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
            }
//                Log.d("123",username+pwd);
            }
        });
        //游客登录
//        youkeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                flag = 1;
////                StartUtil.putCount(LoginActivity.this,flag);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        new LoginMain(0,"","",1,1).start(0,"",1,"",1);
//                    }
//                }).start();
//            }
//        });
        helpTv.setOnClickListener(this);
//        registerTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_qq_image:
                flag = 2;
//                StartUtil.putCount(LoginActivity.this,flag);
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.SSOSetting(false);
                qq.setPlatformActionListener(this);
                qq.authorize();
                qq.showUser(null);
                break;
            case R.id.login_wechat_image:
                flag = 3;
//                StartUtil.putCount(LoginActivity.this,flag);
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                wechat.SSOSetting(false);
                wechat.setPlatformActionListener(this);
                wechat.authorize();
                wechat.showUser(null);
                break;
            case R.id.user_help_tv:
                startActivity(UserHelpActivity_.intent(this).get());
                break;
//            case R.id.register_user_tv:
//                startActivity(RegisterActivity_.intent(this).get());
//                break;
        }


    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> res) {
        //用户资源都保存到res
        //通过打印res数据看看有哪些数据是你想要的
        if (i == Platform.ACTION_USER_INFOR) {
            PlatformDb platDB = platform.getDb();//获取数平台数据DB
            //通过DB获取各种数据
            String token = platDB.getToken();
            String userGender = platDB.getUserGender();
            userIcon = platDB.getUserIcon();
            userId = platDB.getUserId();
            userName = platDB.getUserName();
            EventBus.getDefault().post(new UserEntity(userIcon,userId,userName),"UserInfo");
//            StartUtil.editInfo(this,userName,userId,userIcon,"1");
            StartUtil.putQQid(this, userId);
            Log.d("123",token+"  "+userId+"===="+userGender+"====="+userIcon+"======"+userName);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new LoginMain(0, "",userId , flag, flag,LoginActivity.this).start(0, "", flag, userId,flag,LoginActivity.this);
                }
            }).start();
//                    startActivity(MainActivity_.intent(LoginActivity.this).extra("flag",flag).get());
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscriber(tag = "login_success")
    public void loginSuccess(LogonResponse res){
//        flag = 0;
        Tools.PrintObject(res);
        StartUtil.putCount(LoginActivity.this,flag);
        Log.d("123",flag+"flag");
        if (res != null){
            if (flag == 2 || flag == 3) {
                Log.d("123",userName+userIcon);
                StartUtil.editInfo(this, userName, res.getUserid()+"", userIcon, res.getCuserpwd());
            }else {
                Log.d("123",res.getHeadpic()+"lenth");
                if (res.getHeadpic()>15){
                    StartUtil.editInfo(this, res.getCalias(), res.getUserid() + "", res.getHeadpic()+"", res.getCuserpwd());
                }else {
                    Log.d("123","不是吧这走?");
                    StartUtil.editInfo(this, res.getCalias(), res.getUserid() + "", "head" + res.getHeadpic(), res.getCuserpwd());
                }
            }

            StartUtil.putVersion(this,res.getNverison()+"");
            StartUtil.editUserInfo(this,res.getNlevel()+"",res.getNdeposit()+"",res.getNk()+"",res.getNb()+"",res.getCidiograph());
            startActivity(MainActivity_.intent(LoginActivity.this).extra("flag",flag).get());
        }
    }
    @Subscriber(tag = "splash_fail")
    public void loginFail(String loginflag){
        if (loginflag == "0"){
            Toast.makeText(this, "账号密码错误", Toast.LENGTH_SHORT).show();
        }
    }
}
