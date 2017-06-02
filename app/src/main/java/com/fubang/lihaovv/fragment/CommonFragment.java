package com.fubang.lihaovv.fragment;


import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.adapters.RoomChatAdapter;
import com.fubang.lihaovv.entities.GiftEntity;
import com.fubang.lihaovv.utils.GiftUtil;
import com.xlg.android.protocol.BigGiftRecord;
import com.xlg.android.protocol.RoomChatMsg;
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
    private List<GiftEntity> gifts = new ArrayList<>();
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
        gifts.addAll(GiftUtil.getGifts());

        adapter = new RoomChatAdapter(data,getContext());
        listView.setAdapter(adapter);
    }
    //接收礼物消息更新
    @Subscriber(tag="BigGiftRecord")
    public void getGiftRecord(BigGiftRecord obj){
        int getGiftId = obj.getGiftid();
        int count = obj.getCount();
        String giftTxt = "";
        if (count != 0) {
            for (int i = 0; i < gifts.size(); i++) {
                if (getGiftId == gifts.get(i).getGiftId()) {
                    if (getGiftId < 10)
                        giftTxt = "/g100" + getGiftId + "   x " + count;
                    if (getGiftId >= 10 && getGiftId < 100)
                        giftTxt = "/g10" + getGiftId + "   x " + count;
                    if (getGiftId >= 100)
                        giftTxt = "/g1" + getGiftId + "    x" + count;
                    if (getGiftId>31 && getGiftId<64) {
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
    @Subscriber(tag="CommonMsg")
    public void getRoomChatMsg(RoomChatMsg msg){
//        Log.d("123",msg.getContent());
//        if(msg.getMsgtype() == 0) {
//            if (msg.getToid()==0||msg.getToid()==Integer.parseInt(StartUtil.getUserId(getContext()))){
                //("<b><FONT style=\"FONT-FAMILY:宋体;FONT-SIZE:17px; COLOR:#FF0000\">/mr599</FONT></b>")) {
                //<b><FONT style="FONT-FAMILY:宋体;FONT-SIZE:17px; COLOR:#FF0000">/mr599</FONT></b>
                data.add(msg);
                adapter.notifyDataSetChanged();
                listView.setSelection(listView.getCount() - 1);
//            }
//        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
