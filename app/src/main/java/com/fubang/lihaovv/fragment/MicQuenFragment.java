package com.fubang.lihaovv.fragment;


import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.adapters.LookUserAdapter;
import com.fubang.lihaovv.adapters.MicQuenAdapter;
import com.fubang.lihaovv.ui.RoomActivity;
import com.socks.library.KLog;
import com.xlg.android.protocol.MicState;
import com.xlg.android.protocol.RoomKickoutUserInfo;
import com.xlg.android.protocol.RoomUserInfo;
import com.xlg.android.protocol.UseridList;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_mic_quen)
public class MicQuenFragment extends BaseFragment {

    @ViewById(R.id.mai_list_eplist)
    ExpandableListView userList;
    @ViewById(R.id.tv_mic_queue_state)
    TextView tvMicQueueState;
    @ViewById(R.id.tv_mic_queue_control)
    TextView tvMicQueueControl;
    private MicQuenAdapter maiAdapter;
    private LookUserAdapter adapter;

    //    private int flag = 0;
    @Override
    public void before() {

        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
//        if (flag == 0) {
//            RoomUserInfo roomUserInfo = new RoomUserInfo();
//            roomUserInfo.setUserid(Integer.parseInt(StartUtil.getUserId(getContext())));
//            roomUserInfo.setUseralias(StartUtil.getUserName(getContext()));
//                roomUserInfo.setHeadid(0);
//            userInfos.add(roomUserInfo);
//            flag = 1;
//        }
        maiAdapter = new MicQuenAdapter(paiUserList, getActivity());
        userList.setAdapter(maiAdapter);
//        userList.setOnGroupClickListener(this);
        userList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (childPosition == 0) {
                    Toast.makeText(getActivity(), "点击了" + childPosition, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(RoomActivity.userInfos.get(groupPosition), "SendToUser");
                } else if (childPosition == 1) {
                    Toast.makeText(getActivity(), "点击了" + childPosition, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(RoomActivity.userInfos.get(groupPosition), "KickOut");
                } else if (childPosition == 2) {
                    Toast.makeText(getActivity(), "点击了" + childPosition, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(RoomActivity.userInfos.get(groupPosition), "ForbidChat");
                } else if (childPosition == 3) {
                    Toast.makeText(getActivity(), "点击了" + childPosition, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(RoomActivity.userInfos.get(groupPosition), "CancelForbidChat");
                } else {
                    Toast.makeText(getActivity(), "点击了-----------" + childPosition, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @Override
    public void initData() {

    }

    private List<RoomUserInfo> paiUserList = new ArrayList<>();
    private boolean is_list_has_date = false;

    //上麦    8
    @Subscriber(tag = "changeMicList")
    public void changeMicList(MicState obj) {
        is_list_has_date = false;
        if (obj.getRunnerid() == obj.getUserid()) {//表示排麦而非抱麦
            for (int i = 0; i < RoomActivity.userInfos.size(); i++) {
                if (obj.getUserid() == RoomActivity.userInfos.get(i).getUserid()) {//把排麦的放到麦序列表最后即可
                    for (int j = 0; j < paiUserList.size(); j++) {
                        if (paiUserList.get(j).getUserid() == obj.getUserid()) {
                            is_list_has_date = true;
                        }
                    }
                    if (!is_list_has_date) {
                        paiUserList.add(RoomActivity.userInfos.get(i));
                        maiAdapter.notifyDataSetChanged();
                    }

                }
            }
        } else {//表示抱麦
            for (int i = 0; i < RoomActivity.userInfos.size(); i++) {
                if (obj.getUserid() == RoomActivity.userInfos.get(i).getUserid()) {//把排麦的放到麦序列表第一即可
                    for (int j = 0; j < paiUserList.size(); j++) {
                        if (paiUserList.get(j).getUserid() == obj.getUserid()) {
                            is_list_has_date = true;
                        }
                    }
                    if (!is_list_has_date) {
                        paiUserList.add(RoomActivity.userInfos.get(i));
                        maiAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    //下麦提示  0
    @Subscriber(tag = "downMicState")
    public void downMicState(MicState obj) {
        for (int i = 0; i < paiUserList.size(); i++) {
            if (paiUserList.get(i).getUserid() == obj.getUserid()) {
                paiUserList.remove(i);
                maiAdapter = new MicQuenAdapter(paiUserList, getActivity());
                userList.setAdapter(maiAdapter);
            }
        }
    }

    //获取麦序列表
    @Subscriber(tag = "UseridList")
    public void getUseridList(UseridList obj) {
        paiUserList.clear();
        KLog.e(obj.getList().length + " ");
        for (int i = 0; i < obj.getList().length; i++) {
            for (int j = 0; j < RoomActivity.userInfos.size(); j++) {
                if (obj.getList()[i] == RoomActivity.userInfos.get(j).getUserid()) {
                    paiUserList.add(RoomActivity.userInfos.get(j));
                }
            }
        }
        KLog.e(paiUserList.size() + " ");
        maiAdapter = new MicQuenAdapter(paiUserList, getActivity());
        userList.setAdapter(maiAdapter);
    }

    //表示自己是被抱上麦
    @Subscriber(tag = "is_upmic")
    public void is_upmic(String obj) {
        if (obj.equals("is_upmic")) {
            is_up_mic = true;
            tvMicQueueControl.setText("下麦");
        }
    }

    //表示自己是被抱上麦
    @Subscriber(tag = "is_downmic")
    public void is_downmic(String obj) {
        if (obj.equals("is_downmic")) {
            is_up_mic = false;
            tvMicQueueControl.setText("排麦");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private boolean is_up_mic = false;

    @Click({R.id.tv_mic_queue_control})
    void click(View v) {
        switch (v.getId()) {
            case R.id.tv_mic_queue_control://排麦操作
                if (!is_up_mic) {
                    EventBus.getDefault().post(StartUtil.getUserId(getActivity()), "waitForMic");
                    tvMicQueueControl.setText("下麦");
                } else {
                    EventBus.getDefault().post(StartUtil.getUserId(getActivity()), "downForMic");
                    tvMicQueueControl.setText("排麦");
                }
                is_up_mic = !is_up_mic;
                break;
        }
    }
}
