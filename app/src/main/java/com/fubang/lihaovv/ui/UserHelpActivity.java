package com.fubang.lihaovv.ui;

import android.view.View;
import android.widget.ImageView;

import com.fubang.lihaovv.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_user_help)
public class UserHelpActivity extends BaseActivity {
    @ViewById(R.id.userhelp_back_btn)
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
