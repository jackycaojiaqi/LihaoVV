package com.fubang.lihaovv.fragment;


import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.adapters.RoomChatAdapter;
import com.socks.library.KLog;
import com.xlg.android.protocol.RoomChatMsg;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_person)
public class PersonFragment extends BaseFragment {

    @ViewById(R.id.person_list)
    ListView listView;
    private List<RoomChatMsg> data = new ArrayList<>();

    private RoomChatAdapter adapter;

    @Override
    public void before() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        adapter = new RoomChatAdapter(data, getContext());
        listView.setAdapter(adapter);
    }

    //接收服务器发送的消息更新列表
    @Subscriber(tag = "PersonMsg")
    public void getRoomChatMsg(RoomChatMsg msg) {
        KLog.e(msg.getSrcalias() + "  " + msg.getContent());
        data.add(msg);
        adapter.notifyDataSetChanged();
        listView.setSelection(listView.getCount() - 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
