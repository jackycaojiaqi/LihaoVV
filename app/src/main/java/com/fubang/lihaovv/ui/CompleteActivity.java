package com.fubang.lihaovv.ui;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fubang.lihaovv.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 支付完成页面
 */
@EActivity(R.layout.activity_complete)
public class CompleteActivity extends BaseActivity {
    @ViewById(R.id.complete_back_btn)
    ImageView backImage;
    @ViewById(R.id.complete_ok)
    TextView okTv;
    @ViewById(R.id.complete_btn)
    Button okBtn;
    @ViewById(R.id.complete_money)
    TextView moneyTv;
    private int money;

    @Override
    public void before() {
        money = getIntent().getIntExtra("RECHARGE_MONEY",5);
    }

    @Override
    public void initView() {
        moneyTv.setText("￥"+ money + ".00");
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
