package com.fubang.lihaovv.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.fragment.VipFragment_;
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.adapters.HomeTitleAdapter;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 特权页面
 */
@EActivity(R.layout.activity_privilege)
public class PrivilegeActivity extends BaseActivity {
    @ViewById(R.id.privilege_tablayout)
    TabLayout tabLayout;
    @ViewById(R.id.privilege_viewpage)
    ViewPager viewPager;
    @ViewById(R.id.privilege_back_btn)
    ImageView backImage;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    @Override
    public void initView() {
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        for (int i = 0; i < AppConstant.MY_TYPE_TITLE.length; i++) {
            titles.add(AppConstant.MY_TYPE_TITLE[i]);
        }
        fragments.add(VipFragment_.builder().arg(AppConstant.HOME_TYPE,titles.get(0)).build());
        fragments.add(VipFragment_.builder().arg(AppConstant.HOME_TYPE,titles.get(1)).build());
        fragments.add(VipFragment_.builder().arg(AppConstant.HOME_TYPE,titles.get(2)).build());
        fragments.add(VipFragment_.builder().arg(AppConstant.HOME_TYPE,titles.get(3)).build());
        HomeTitleAdapter adapter = new HomeTitleAdapter(getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
