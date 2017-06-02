package com.fubang.lihaovv.ui;

import android.view.View;
import android.widget.ImageView;

import com.fubang.lihaovv.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 通知页面
 */
@EActivity(R.layout.activity_message)
public class MessageActivity extends BaseActivity {
    @ViewById(R.id.message_back_btn)
    ImageView backImage;

    @Override
    public void initView() {
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
