package com.fubang.lihaovv.ui;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fubang.lihaovv.R;
import com.zhuyunjian.library.AsyncPayTask;
import com.zhuyunjian.library.PayResult;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 充值页面
 */
@EActivity(R.layout.activity_recharge)
public class RechargeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener  ,AsyncPayTask.PayCallBack{
    @ViewById(R.id.recharge_back_btn)
    ImageView backImage;
    @ViewById(R.id.recharge_confirm_btn)
    Button confirmBtn;
    @ViewById(R.id.recharge_group)
    RadioGroup radioGroup;
    private int moneyB = 1000;
    @ViewById(R.id.recharge_5)
    RadioButton radioButton5;
    @ViewById(R.id.recharge_10)
    RadioButton radioButton10;
    @ViewById(R.id.recharge_20)
    RadioButton radioButton20;
    @ViewById(R.id.recharge_50)
    RadioButton radioButton50;
    @ViewById(R.id.recharge_100)
    RadioButton radioButton100;
    @ViewById(R.id.recharge_200)
    RadioButton radioButton200;
    @ViewById(R.id.recharge_user_tv)
    TextView userTv;
    @ViewById(R.id.recharge_kbi_tv)
    TextView kbiTv;

    public final static String RETURNURL = "http://61.153.104.118:9418/index.php/Alipay/callbak";
    @Override
    public void initView() {
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        radioGroup.setOnCheckedChangeListener(this);
        confirmBtn.setOnClickListener(this);
        userTv.setText("充值账号:"+ StartUtil.getUserId(this));
        kbiTv.setText("余额:"+StartUtil.getUserKbi(this));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId()==R.id.recharge_group){
            switch (checkedId){
                case R.id.recharge_5:
                    moneyB = 500000;
                    break;
                case R.id.recharge_10:
                    moneyB = 1100000;
                    break;
                case R.id.recharge_20:
                    moneyB = 2200000;
                    break;
                case R.id.recharge_50:
                    moneyB = 5750000;
                    break;
                case R.id.recharge_100:
                    moneyB = 12000000;
                    break;
                case R.id.recharge_200:
                    moneyB = 24000000;
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "确定充值"+moneyB+"币", Toast.LENGTH_SHORT).show();
        new AsyncPayTask(this).pay(StartUtil.getUserId(this),RETURNURL,moneyB+"");
    }

    @Override
    public void callBack(PayResult result) {
        switch (result.getResultStatus()) {
            case "9000":
                Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
                startActivity(CompleteActivity_.intent(this).extra("RECHARGE_MONEY",moneyB).get());
                break;
            case "8000":
                Toast.makeText(this, "支付处理中", Toast.LENGTH_LONG).show();
                break;
            case "4000":
                Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
                break;
            case "6001":
                Toast.makeText(this, "用户取消", Toast.LENGTH_LONG).show();
                break;
            case "6002":
                Toast.makeText(this, "网络问题", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "未知错误" + result.getResultStatus(), Toast.LENGTH_LONG).show();
                break;

        }
    }

}