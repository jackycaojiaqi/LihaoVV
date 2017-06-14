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
import android.widget.RadioGroup;

import com.fubang.lihaovv.fragment.FollowFragment_;
import com.fubang.lihaovv.fragment.HomeFragment_;
import com.fubang.lihaovv.fragment.LiveFragment_;
import com.fubang.lihaovv.fragment.MyFragment_;
import com.fubang.lihaovv.ui.BaseActivity;
import com.loveplusplus.update.UpdateChecker;
import com.zhuyunjian.library.FragmentTabUtils;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

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
    public void initView() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                100);
        fragments.add(HomeFragment_.builder().build());
        fragments.add(LiveFragment_.builder().build());
        fragments.add(FollowFragment_.builder().build());
        fragments.add(MyFragment_.builder().build());
        //底部按钮切换fragment的工具类
        new FragmentTabUtils(this,getSupportFragmentManager(),radioGroup,fragments, R.id.main_contaner);
        int version = Integer.parseInt(StartUtil.getVersion(this));
        manager = this.getPackageManager();

        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int  versionCode = info.versionCode;
        String versionname = intToIp(version);
        String versionName = info.versionName;
        Log.d("123","versionName"+versionName+"versionname"+versionname);
//        int intversion = ipToInt(versionName);
        if (!versionname.equals(versionName)){
            UpdateChecker.checkForDialog(MainActivity.this, AppConstant.DOWNLOAD_URL);
        }
    }
    /**
     * 本机ip
     */
    @Override
    public void initData() {
//        获取wifi服务
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        判断wifi是否开启
        if (!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        Log.d("123",ip);
    }
    private String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    public int ipToInt(String ip) {
        String[] ips = ip.split("\\.");
        return (Integer.parseInt(ips[0]) << 24) + (Integer.parseInt(ips[1]) << 16)
                + (Integer.parseInt(ips[2]) << 8) + Integer.parseInt(ips[3]);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
