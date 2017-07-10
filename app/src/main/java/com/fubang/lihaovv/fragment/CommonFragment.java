package com.fubang.lihaovv.fragment;


import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.adapters.RoomChatAdapter;
import com.fubang.lihaovv.entities.GiftEntity;
import com.fubang.lihaovv.entities.GiftNewEntity;
import com.fubang.lihaovv.ui.RoomActivity;
import com.fubang.lihaovv.utils.GiftUtil;
import com.socks.library.KLog;
import com.xlg.android.protocol.BigGiftRecord;
import com.xlg.android.protocol.LotteryNotice;
import com.xlg.android.protocol.RoomChatMsg;
import com.xlg.android.protocol.RoomUserInfo;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_common)
public class CommonFragment extends BaseFragment {
    @ViewById(R.id.common_message_list)
    ListView listView;
    private List<RoomChatMsg> data = new ArrayList<>();

    private RoomChatAdapter adapter;
    private List<GiftNewEntity> gifts = new ArrayList<>();

    @Override
    public void before() {
        RoomChatMsg joinMsg = new RoomChatMsg();
        joinMsg.setSrcid(Integer.parseInt(StartUtil.getUserId(getContext())));
        joinMsg.setSrcalias(StartUtil.getUserName(getContext()));
        joinMsg.setContent("加入了房间");
        data.add(joinMsg);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        gifts.clear();
        gifts = GiftUtil.getAllGift();
        adapter = new RoomChatAdapter(data, getContext());
        listView.setAdapter(adapter);
    }
    private String lottery_name;
    private String gift_name;
    //接收中奖礼物消息
    @Subscriber(tag = "onLotteryNotify")
    public void onLotteryNotify(LotteryNotice obj) {
        for (RoomUserInfo userInfo : RoomActivity.userInfos){
            if (userInfo.getUserid()==obj.getSrcid()){
                lottery_name = userInfo.getUseralias();
            }
        }
        for (GiftNewEntity giftNewEntity: gifts){
            if (giftNewEntity.getDate().getGiftId()==obj.getGiftid()){
                gift_name = giftNewEntity.getDate().getGiftName();
            }
        }
        RoomChatMsg msg = new RoomChatMsg();
        msg.setToid(8);
        msg.setContent(lottery_name+"送出："+gift_name+"  获得10倍x"+obj.getNumber1()+" 100倍x"+obj.getNumber2()+
                " 500倍x"+obj.getNumber3()+" 1000倍x"+obj.getNumber4());
        msg.setSrcid(obj.getSrcid());
        msg.setSrcalias(lottery_name);
        msg.setDstvcbid(0);
        data.add(msg);
        adapter.notifyDataSetChanged();
        listView.setSelection(listView.getCount() - 1);
    }

    //接收礼物消息更新
    @Subscriber(tag = "BigGiftRecord")
    public void getGiftRecord(BigGiftRecord obj) {
        int getGiftId = obj.getGiftid();
        int count = obj.getCount();
        String giftTxt = "";
        if (count != 0) {
            for (int i = 0; i < gifts.size(); i++) {
                if (getGiftId == gifts.get(i).getDate().getGiftId()) {
                    if (getGiftId > 0 && getGiftId < 9999) {
                        RoomChatMsg msg = new RoomChatMsg();
                        msg.setToid(-1);
                        msg.setContent("g" + getGiftId + "");
                        msg.setSrcid(obj.getSrcid());
                        msg.setSrcalias(obj.getSrcalias());
                        msg.setDstvcbid(count);
                        data.add(msg);
                        adapter.notifyDataSetChanged();
                        listView.setSelection(listView.getCount() - 1);
                    }
                }
            }
        }
    }

    //接收服务器发送的消息更新列表
    @Subscriber(tag = "CommonMsg")
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
