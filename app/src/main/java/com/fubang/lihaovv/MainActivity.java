package com.fubang.lihaovv;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fubang.lihaovv.fragment.FollowFragment_;
import com.fubang.lihaovv.fragment.HomeFragment_;
import com.fubang.lihaovv.fragment.LiveFragment_;
import com.fubang.lihaovv.fragment.MyFragment_;
import com.fubang.lihaovv.ui.BaseActivity;
import com.fubang.lihaovv.ui.LoginActivity;
import com.loveplusplus.update.UpdateChecker;
import com.socks.library.KLog;
import com.umeng.analytics.MobclickAgent;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.utils.Tools;
import com.zhuyunjian.library.FragmentTabUtils;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.yj.media.Media;

/**
 * 主页面
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewById(R.id.main_bottom_radiogp)
    RadioGroup radioGroup;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private PackageManager manager;
    private PackageInfo info = null;

    @Override
    public void before() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                100);
        fragments.add(HomeFragment_.builder().build());
        fragments.add(LiveFragment_.builder().build());
        fragments.add(FollowFragment_.builder().build());
        fragments.add(MyFragment_.builder().build());
        //底部按钮切换fragment的工具类
        new FragmentTabUtils(this, getSupportFragmentManager(), radioGroup, fragments, R.id.main_contaner);
        //======================版本更新操作
        try {
            int version = Integer.parseInt(StartUtil.getVersion(this));
            String serverVersionName = intToIp(version);
            KLog.e(serverVersionName);
            manager = this.getPackageManager();
            try {
                info = manager.getPackageInfo(this.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            String versionName = info.versionName;
            KLog.e("me:" + versionName + " server:" + serverVersionName);
            if (!versionName.equals(serverVersionName)) {//当前版本号名称和服务器版本号不一致
                UpdateChecker.checkForDialog(MainActivity.this, AppConstant.DOWNLOAD_URL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    public int ipToInt(String ip) {
        String[] ips = ip.split("\\.");
        return (Integer.parseInt(ips[0]) << 24) + (Integer.parseInt(ips[1]) << 16)
                + (Integer.parseInt(ips[2]) << 8) + Integer.parseInt(ips[3]);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
