package com.fubang.lihaovv.fragment;


import android.support.v4.app.Fragment;

import com.fubang.lihaovv.R;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * fragment基类
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_base)
public class BaseFragment extends Fragment {

    @AfterInject
    public void before(){

    }

    @AfterViews
    public final void init(){
        initView();
        initData();
    }

    public void initData() {

    }

    public void initView() {

    }

}
