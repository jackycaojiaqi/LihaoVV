package com.fubang.lihaovv.fragment;


import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.adapters.LookUserAdapter;
import com.fubang.lihaovv.adapters.MicQuenAdapter;
import com.xlg.android.protocol.RoomKickoutUserInfo;
import com.xlg.android.protocol.RoomUserInfo;
import com.xlg.android.protocol.UseridList;

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
    private MicQuenAdapter maiAdapter;
    private List<RoomUserInfo> userInfos = new ArrayList<>();
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
                    EventBus.getDefault().post(userInfos.get(groupPosition), "SendToUser");
                } else if (childPosition == 1) {
                    Toast.makeText(getActivity(), "点击了" + childPosition, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(userInfos.get(groupPosition), "KickOut");
                } else if (childPosition == 2) {
                    Toast.makeText(getActivity(), "点击了" + childPosition, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(userInfos.get(groupPosition), "ForbidChat");
                } else if (childPosition == 3) {
                    Toast.makeText(getActivity(), "点击了" + childPosition, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(userInfos.get(groupPosition), "CancelForbidChat");
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

    //获取麦序列表
    @Subscriber(tag = "UseridList")
    public void getUseridList(UseridList obj) {
        for (int i = 0; i < obj.getList().length; i++) {
            for (int j = 0; j < userInfos.size(); j++) {
                if (obj.getList()[i] == userInfos.get(j).getUserid()) {
                    paiUserList.add(userInfos.get(j));
                    maiAdapter.notifyDataSetChanged();
                }
            }
//            if (obj.getList()[i] == userInfos)
        }
    }

    //用户离开房间
    @Subscriber(tag = "RoomKickoutUserInfo")
    public void getUserOut(RoomKickoutUserInfo obj) {
        int leaveId = obj.getToid();
        for (int i = 0; i < userInfos.size(); i++) {
            if (userInfos.get(i).getUserid() == leaveId) {
                userInfos.remove(i);
            }
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            adapter.notifyDataSetChanged();
//            userList.setAdapter(new LookUserAdapter(userInfos, getContext()));
        }
    }

    //获取用户列表
    @Subscriber(tag = "userList")
    public void getUserList(RoomUserInfo userInfo) {
        userInfos.add(userInfo);
//        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        adapter.notifyDataSetChanged();
//            userList.setAdapter(new UserAdapter(userInfos, this));
//        }
        Log.d("123", userInfo.getUserid() + "-----------<<");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

//    @Override
//    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//        if (childPosition == 0) {
//            Toast.makeText(getContext(), "点击了"+childPosition, Toast.LENGTH_SHORT).show();
//            EventBus.getDefault().post(userInfos.get(groupPosition),"SendToUser");
//        }else if (childPosition == 1){
//            Toast.makeText(getContext(), "点击了"+childPosition, Toast.LENGTH_SHORT).show();
//            EventBus.getDefault().post(userInfos.get(groupPosition),"KickOut");
//        }else if (childPosition == 2){
//            Toast.makeText(getContext(), "点击了"+childPosition, Toast.LENGTH_SHORT).show();
//            EventBus.getDefault().post(userInfos.get(groupPosition),"ForbidChat");
//        }else if (childPosition == 3){
//            Toast.makeText(getContext(), "点击了"+childPosition, Toast.LENGTH_SHORT).show();
//            EventBus.getDefault().post(userInfos.get(groupPosition),"CancelForbidChat");
//        }
//        return true;
//    }
}
