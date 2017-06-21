package com.fubang.lihaovv.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fubang.lihaovv.MainActivity;
import com.fubang.lihaovv.R;
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.utils.AlertDialogUtil;
import com.fubang.lihaovv.utils.ShareUtil;
import com.loveplusplus.update.UpdateChecker;
import com.socks.library.KLog;
import com.zhuyunjian.library.DeviceUtil;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 设置页面
 */
@EActivity(R.layout.activity_setting)
public class SettingActivity extends BaseActivity {
    @ViewById(R.id.setting_clear_cache)
    LinearLayout clearLayout;
    @ViewById(R.id.setting_recommed_friend)
    LinearLayout recommedLayout;
    @ViewById(R.id.setting_check_version)
    LinearLayout versionLayout;
    @ViewById(R.id.setting_now_cache)
    TextView clearText;
    @ViewById(R.id.setting_version_text)
    TextView versionText;
    @ViewById(R.id.setting_back_btn)
    ImageView backImage;
    @ViewById(R.id.setting_back_login)
    LinearLayout backLogin;

    private AlertDialog clearDialog;

    @Override
    public void initView() {
        String s = "当前缓存" + DeviceUtil.getFormatSize(DeviceUtil.getFolderSize(AppConstant.getCacheFile()));
        clearText.setText(s);
        clearDialog = AlertDialogUtil.setClearDialog(this, clearText);
        clearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDialog.show();
            }
        });
        recommedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtil.getInstance().share(SettingActivity.this);
            }
        });
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartUtil.deleteLogin(SettingActivity.this);
                startActivity(LoginActivity_.intent(SettingActivity.this).get());
                finish();
            }
        });
        try {
            PackageManager manager = this.getPackageManager();
            try {
                PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
                String versionName = info.versionName;
                versionText.setText("当前版本:" + versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initData() {
    }

}
