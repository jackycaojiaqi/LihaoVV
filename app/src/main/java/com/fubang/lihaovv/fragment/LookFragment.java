package com.fubang.lihaovv.fragment;


import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.adapters.LookUserAdapter;
import com.xlg.android.protocol.RoomKickoutUserInfo;
import com.xlg.android.protocol.RoomUserInfo;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_look)
public class LookFragment extends BaseFragment {

    @ViewById(R.id.look_expanList)
    ExpandableListView userList;

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
        adapter = new LookUserAdapter(userInfos, getContext());
//        userList.setOnGroupClickListener(this);
        userList.setAdapter(adapter);
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



    //获取用户列表
    @Subscriber(tag = "lookfragment_notify")
    public void lookfragment_notify(List<RoomUserInfo>  obj) {
        adapter.NotifyList(obj);
    }

    //获取用户列表
    @Subscriber(tag = "userList")
    public void getUserList(RoomUserInfo userInfo) {
        userInfos.add(userInfo);
//        Collections.sort(userInfos, new Comparator<RoomUserInfo>() {
//            @Override
//            public int compare(RoomUserInfo o1, RoomUserInfo o2) {
//                if (o1.getLevel1() == o2.getLevel1()) {
//                    return o1.getUserid() > o2.getUserid() ? -1 : 1;
//                } else {
//                    return o1.getLevel1() > o2.getLevel1() ? -1 : 1;
//                }
//            }
//        });
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
