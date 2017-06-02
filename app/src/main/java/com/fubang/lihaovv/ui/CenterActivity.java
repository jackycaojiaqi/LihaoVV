package com.fubang.lihaovv.ui;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.App;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_center)
public class CenterActivity extends BaseActivity {
    private String ip;
    private String roomIp;
    private App app;
    private int port;
    private int roomId;
    @Override
    public void before() {
        app = (App) getApplication();
        roomIp = getIntent().getStringExtra("roomIp");
        String[] Ips = roomIp.split(";");
        String[] ports = Ips[0].split(":");
        ip = ports[0];
        port = Integer.parseInt(ports[1]);
        roomId = Integer.parseInt(getIntent().getStringExtra("roomId"));
//        Log.d("123", roomId + "roomId");
    }

    @Override
    public void initView() {
//        Log.d("123","roomcenterActivity");
        startActivity(RoomActivity_.intent(this).extra("roomIp",roomIp).extra("roomId",roomId+"").get());
        finish();
    }
}
