package com.fubang.lihaovv.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.fragment.EmotionMainFragment;

public class DemoActivity extends AppCompatActivity {
    private FrameLayout rllRoomInput;
    private TextView listView;
    private EmotionMainFragment emotionMainFragment;
    private ImageView imageView;
    private boolean flag;
    private LinearLayout linearLayout,linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        //=======================键盘页面
        linearLayout = (LinearLayout) findViewById(R.id.new_room_chatline);
        rllRoomInput = (FrameLayout) findViewById(R.id.new_room_chat_input);
        listView = (TextView) findViewById(R.id.new_room_chat_listview);
        imageView = (ImageView) findViewById(R.id.demo_image);
        linear = (LinearLayout) findViewById(R.id.demo_linear);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag){
                    linearLayout.setVisibility(View.VISIBLE);
                    flag = true;
                }
            }
        });
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag){
                    linearLayout.setVisibility(View.GONE);
                    flag = false;
                }
            }
        });
        initEmotionMainFragment();
    }
    /**
     * 初始化表情面板
     */
    public void initEmotionMainFragment(){
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT,true);
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN,false);
        //替换fragment
        //创建修改实例
        emotionMainFragment = EmotionMainFragment.newInstance(EmotionMainFragment.class,bundle);
        emotionMainFragment.bindToContentView(listView);
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        transaction.replace(R.id.new_room_chat_input,emotionMainFragment);
        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        /**
         * 判断是否拦截返回键操作
         */
        if (!emotionMainFragment.isInterceptBackPress()) {
            super.onBackPressed();
        }
    }
}
