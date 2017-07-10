package com.fubang.lihaovv.ui;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fubang.lihaovv.R;
import com.umeng.analytics.MobclickAgent;
import com.zhuyunjian.library.AsyncPayTask;
import com.zhuyunjian.library.PayResult;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 充值页面
 */
@EActivity(R.layout.activity_recharge)
public class RechargeActivity extends BaseActivity implements View.OnClickListener, AsyncPayTask.PayCallBack {
    @ViewById(R.id.recharge_back_btn)
    ImageView backImage;
    private int moneyB = -1;
    @ViewById(R.id.tv_pay_1)
    TextView tvPay1;
    @ViewById(R.id.tv_pay_6)
    TextView tvPay6;
    @ViewById(R.id.tv_pay_30)
    TextView tvPay30;
    @ViewById(R.id.tv_pay_98)
    TextView tvPay98;
    @ViewById(R.id.tv_pay_298)
    TextView tvPay298;
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
        kbiTv.setText(" "+StartUtil.getUserKbi(this));
        tvPay1.setOnClickListener(this);
        tvPay6.setOnClickListener(this);
        tvPay30.setOnClickListener(this);
        tvPay98.setOnClickListener(this);
        tvPay298.setOnClickListener(this);

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pay_1:
                moneyB = 1;
                break;
            case R.id.tv_pay_6:
                moneyB = 6;
                break;
            case R.id.tv_pay_30:
                moneyB = 30;
                break;
            case R.id.tv_pay_98:
                moneyB = 98;
                break;
            case R.id.tv_pay_298:
                moneyB = 298;
                break;

        }
        if (moneyB != -1)
            new AsyncPayTask(this).pay(StartUtil.getUserId(this), RETURNURL, moneyB + "");
    }

    @Override
    public void callBack(PayResult result) {
        switch (result.getResultStatus()) {
            case "9000":
                Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
                startActivity(CompleteActivity_.intent(this).extra("RECHARGE_MONEY", moneyB).get());
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