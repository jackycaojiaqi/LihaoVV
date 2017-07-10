package com.fubang.lihaovv.ui;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.fubang.lihaovv.R;
import com.umeng.analytics.MobclickAgent;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_base)
public class BaseActivity extends AppCompatActivity {

    @AfterInject
    public void before() {

    }

    @AfterViews
    public final void init() {
        initView();
        initListener();
        initData();
    }

    public void initListener() {

    }

    public void initData() {

    }

    public void initView() {

    }


    //布局10秒后隐藏
    public void animaView(final View view) {
        AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.0f);
        animation1.setDuration(100 * 100);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.setAnimation(animation1);
        animation1.start();
    }
}
