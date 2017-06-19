package com.fubang.lihaovv.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.fubang.lihaovv.MainActivity_;
import com.fubang.lihaovv.R;
import com.socks.library.KLog;
import com.xlg.android.protocol.LogonResponse;
import com.zhuyunjian.library.DeviceUtil;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import sample.login.LoginMain;

/**
 * 启动页面
 */
@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {
    @ViewById(R.id.splash_image)
    ImageView imageView;
    private int flag;

    @Override
    public void before() {
        EventBus.getDefault().register(this);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        Log.d("123", "deviceId" + DeviceUtil.getDeviceId(this));
        StartUtil.editDeviceId(this, DeviceUtil.getDeviceId(this));
        Toast.makeText(this, "Contact permission is granted", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            //结束你的activity
            finish();
            return;
        }
    }

    @Override
    public void initView() {
        PermissionGen.with(SplashActivity.this)
                .addRequestCode(100)
                .permissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
        super.initView();
        //创建启动夜间动画效果
        AlphaAnimation animation = new AlphaAnimation(1.0f, 1.0f);
        animation.setDuration(15 * 100);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startIntent();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.setAnimation(animation);
        animation.start();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent();
            }
        });
        StartUtil.deleteIp(this);
    }

    private void startIntent() {
        if (StartUtil.isFirst(this)) {
            startActivity(GuideActivity_.intent(this).get());
            finish();
        } else if (!TextUtils.isEmpty(StartUtil.getUserName(this))) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    flag = StartUtil.getCount(SplashActivity.this);
                    KLog.e( "flag--------------" + flag);
                    KLog.e(StartUtil.getUserId(SplashActivity.this)+" "+ StartUtil.getUserPwd(SplashActivity.this));
                    if (flag == 0) {
                        new LoginMain(Integer.parseInt(StartUtil.getUserId(SplashActivity.this)),
                                StartUtil.getUserPwd(SplashActivity.this), "", 0, 0, SplashActivity.this).start(Integer.parseInt(StartUtil.getUserId(SplashActivity.this)),
                                StartUtil.getUserPwd(SplashActivity.this), 0, "", 0, SplashActivity.this);
                    } else if (flag == 1) {
                        startActivity(LoginActivity_.intent(SplashActivity.this).get());
                        finish();
                    } else if (flag == 2 || flag == 3) {
                        KLog.e(StartUtil.getUserId(SplashActivity.this)+" "+ StartUtil.getUserPwd(SplashActivity.this));
                        new LoginMain(Integer.parseInt(StartUtil.getUserId(SplashActivity.this)),
                                StartUtil.getUserPwd(SplashActivity.this), "", 0, 0, SplashActivity.this).start(Integer.parseInt(StartUtil.getUserId(SplashActivity.this)),
                                StartUtil.getUserPwd(SplashActivity.this), 0, "", 0, SplashActivity.this);
                    }
                }
            }).start();
        } else {
            startActivity(LoginActivity_.intent(this).get());
            finish();
        }
    }

    @Subscriber(tag = "splash_success")
    public void loginSuccess(LogonResponse res) {
//        if (!TextUtils.isEmpty(res.getCvalue())){
//            StartUtil.putIpPort(this,res.getCvalue());
//        }
//        flag = 0;
        if (res != null) {
            StartUtil.editUserInfo(this, res.getNlevel() + "", res.getNdeposit() + "", res.getNk() + "", res.getNb() + "", res.getCidiograph());
            StartUtil.putVersion(this, res.getNverison() + "");
        }
        startActivity(MainActivity_.intent(SplashActivity.this).get());
        finish();
    }

    @Subscriber(tag = "splash_fail")
    public void loginFail(String loginflag) {
        if (loginflag == "0") {
            Toast.makeText(this, "账号密码错误", Toast.LENGTH_SHORT).show();
        }
        startActivity(LoginActivity_.intent(this).get());
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
