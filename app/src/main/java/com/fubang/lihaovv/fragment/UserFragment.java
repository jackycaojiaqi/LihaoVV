package com.fubang.lihaovv.fragment;


import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.utils.AlertDialogUtil;
import com.fubang.lihaovv.utils.ShareUtil;
import com.zhuyunjian.library.DeviceUtil;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_user)
public class UserFragment extends BaseFragment {
    @ViewById(R.id.user_clear_cache)
    LinearLayout clearLayout;
    @ViewById(R.id.user_recommed_friend)
    LinearLayout recommedLayout;
    @ViewById(R.id.user_check_version)
    LinearLayout versionLayout;
    @ViewById(R.id.user_now_cache)
    TextView clearText;
    @ViewById(R.id.user_version_text)
    TextView versionText;
    @ViewById(R.id.user_seekbar_light)
    AppCompatSeekBar seekBar;
    @ViewById(R.id.user_seekbar_light_text)
    TextView lightTv;

    private AlertDialog clearDialog;

    @Override
    public void initView() {
        String s = "当前缓存"+ DeviceUtil.getFormatSize(DeviceUtil.getFolderSize(AppConstant.getCacheFile()));
        clearText.setText(s);
        clearDialog = AlertDialogUtil.setClearDialog(getContext(),clearText);
        clearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDialog.show();
            }
        });
        recommedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtil.getInstance().share(getContext());
            }
        });
    }

    @Override
    public void initData() {
        seekBar.setMax(255);
        int normal = Settings.System.getInt(getContext().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,255);
        seekBar.setProgress(normal);
        lightTv.setText(normal+"");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override

            public void onStopTrackingTouch(SeekBar seekBar) {
                int tmpInt = seekBar.getProgress();
                lightTv.setText(tmpInt+"");
                if (tmpInt < 80){
                    tmpInt = 80;
                }
                //根据当前进度改变亮度
                Settings.System.putInt(getContext().getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS,tmpInt);
                tmpInt = Settings.System.getInt(getContext().getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS,-1);
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                float tmpFloat = tmpInt / 255 ;
                if (tmpFloat > 0 && tmpFloat <= 1){
                    lp.screenBrightness = tmpFloat;
                }
                getActivity().getWindow().setAttributes(lp);

            }
        });
    }
}
