package com.fubang.lihaovv.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.adapters.HomeTitleAdapter;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 排行界面
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_live)
public class LiveFragment extends BaseFragment {
    @ViewById(R.id.billboard_tablayout)
    TabLayout tabLayout;
    @ViewById(R.id.billboard_viewpage)
    ViewPager viewPager;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    public void initView() {
        for (int i = 0; i < AppConstant.BILLBOARD_TITLE.length; i++) {
            titles.add(AppConstant.BILLBOARD_TITLE[i]);
        }
//        fragments.add(GiftTopFragment_.builder().arg(AppConstant.HOME_TYPE,titles.get(0)).build());
//        fragments.add(RichFragment_.builder().arg(AppConstant.HOME_TYPE,titles.get(1)).build());
//        fragments.add(AnchorFragment_.builder().arg(AppConstant.HOME_TYPE,titles.get(2)).build());
        fragments.add(RoomSumFragment_.builder().arg(AppConstant.HOME_TYPE,titles.get(0)).build());
    }

    @Override
    public void initData() {
        HomeTitleAdapter adapter = new HomeTitleAdapter(getChildFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
