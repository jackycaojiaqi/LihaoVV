package sample.room;


import android.util.Log;

import com.socks.library.KLog;
import com.xlg.android.RoomChannel;
import com.xlg.android.RoomHandler;
import com.xlg.android.protocol.ActWaitMicUserInfo;
import com.xlg.android.protocol.AddClosedFriendInfo;
import com.xlg.android.protocol.AuthorityRejected;
import com.xlg.android.protocol.BigGiftRecord;
import com.xlg.android.protocol.ChestBonusAmount;
import com.xlg.android.protocol.DecoColor;
import com.xlg.android.protocol.DevState;
import com.xlg.android.protocol.DigTreasureResponse;
import com.xlg.android.protocol.ForbidUserChat;
import com.xlg.android.protocol.GiveColorbarInfo;
import com.xlg.android.protocol.GrabRedPagerRequest;
import com.xlg.android.protocol.JoinRoomResponse;
import com.xlg.android.protocol.LocateIPResp;
import com.xlg.android.protocol.LotteryGiftNotice;
import com.xlg.android.protocol.LotteryNotice;
import com.xlg.android.protocol.MicState;
import com.xlg.android.protocol.OpenChestInfo;
import com.xlg.android.protocol.PreTradeGift;
import com.xlg.android.protocol.RedPagerRequest;
import com.xlg.android.protocol.RoomBKGround;
import com.xlg.android.protocol.RoomBaseInfo;
import com.xlg.android.protocol.RoomChatMsg;
import com.xlg.android.protocol.RoomKickoutUserInfo;
import com.xlg.android.protocol.RoomManagerInfo;
import com.xlg.android.protocol.RoomMediaInfo;
import com.xlg.android.protocol.RoomNotice;
import com.xlg.android.protocol.RoomState;
import com.xlg.android.protocol.RoomUserInfo;
import com.xlg.android.protocol.RoomVideoInfo;
import com.xlg.android.protocol.SendSeal;
import com.xlg.android.protocol.SetUserProfileResp;
import com.xlg.android.protocol.SetUserPwdResp;
import com.xlg.android.protocol.SiegeInfo;
import com.xlg.android.protocol.SysCastNotice;
import com.xlg.android.protocol.TransMediaInfo;
import com.xlg.android.protocol.UserAddMicTimeInfo;
import com.xlg.android.protocol.UserAlias;
import com.xlg.android.protocol.UserBankDepositRespInfo;
import com.xlg.android.protocol.UserChestNumInfo;
import com.xlg.android.protocol.UserHeadPic;
import com.xlg.android.protocol.UserPayResponse;
import com.xlg.android.protocol.UserPriority;
import com.xlg.android.protocol.UseridList;
import com.xlg.android.utils.Tools;

import org.simple.eventbus.EventBus;


public class MyRoom implements RoomHandler {
    private RoomChannel channel = new RoomChannel(this);
    public static boolean isConnected = false;

    public String videoIP;
    public int videoPort;
    public int videoRand;
    public int videoUID;


    public final int FT_ROOMUSER_STATUS_PUBLIC_MIC = 0x1;


    public RoomChannel getChannel() {
        return channel;
    }

    public boolean isOK() {
        return isConnected;
    }

    @Override
    public void onConnectSuccessed() {
        // TODO Auto-generated method stub
        isConnected = true;
        // 连接成功
        System.out.println("onConnectSuccessed: 连接成功");
        // 加入房间
        channel.SendHello();
        // 发送连接
        channel.SendJoinRoomReq();
    }

    @Override
    public void onConnectFailed() {
        EventBus.getDefault().post(false, "ConnectFailed");
        KLog.e("socket链接失败");
    }

    @Override
    public void onJoinRoomResponse(JoinRoomResponse obj) {
        // TODO Auto-generated method stub
        EventBus.getDefault().post(obj, "JoinRoomResponse");
        System.out.println("onJoinRoomResponse: ");
        Tools.PrintObject(obj);
        String str = obj.getMediaserver();
        String ips[] = str.split(";");
        if (ips.length > 0) {
            String s[] = ips[0].split(":");
            if (s.length > 1) {
                videoIP = s[0];
                videoPort = Integer.valueOf(s[1]).intValue();
//				videoPort = Integer.valueOf(s[1]);
            }
        }

        videoRand = obj.getReserve1();
    }

    @Override
    public void onRoomUserNotify(RoomUserInfo obj) {
        // TODO Auto-generated method stub
        System.out.println("onRoomUserNotify: ");
        Tools.PrintObject(obj);
        EventBus.getDefault().post(obj, "userList");
    }

    @Override
    public void onGetOpenChestInfoResponse(OpenChestInfo obj) {
        // TODO Auto-generated method stub
        System.out.println("onGetOpenChestInfoResponse: ");
        Tools.PrintObject(obj);
    }

    @Override
    public void onOpenChestNotify(OpenChestInfo obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onOpenChestNotify: ");
    }

    @Override
    public void onChestBonusAmountNotify(ChestBonusAmount obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onChestBonusAmountNotify: ");
    }

    @Override
    public void onUserChestChangeNotify(UserChestNumInfo obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onUserChestChangeNotify: ");
    }

    //加入房间错误提示
    @Override
    public void onJoinRoomError(int err) {
        KLog.e(err);
        EventBus.getDefault().post(err, "joinRoomError");
    }

    //房间消息通知
    @Override
    public void onRoomNoticeNotify(RoomNotice[] obj) {
        // TODO Auto-generated method stub
        System.out.println("onRoomNoticeNotify: ");
        for (int i = 0; i < obj.length; i++) {
            Tools.PrintObject(obj[i]);
        }
    }

    //获取房间用户列表的返回
    @Override
    public void onGetRoomUserListResponse(int g1, RoomUserInfo[] obj) {
        // TODO Auto-generated method stub
        System.out.println("onGetRoomUserListResponse: " + g1);
        for (int i = 0; i < obj.length - 1; i++) {
            Tools.PrintObject(obj[i]);
//			Log.d("123",obj[i].getMicindex()+"----------micindex");
            EventBus.getDefault().post(obj[i], "userList");
            EventBus.getDefault().postSticky(obj[i], "userList");//方便fragment接收数据
            if (0 != (obj[i].getUserstate() & FT_ROOMUSER_STATUS_PUBLIC_MIC)) {
                EventBus.getDefault().post(obj[i], "onMicUser");
                Log.d("123", "micuser" + obj[i].getUserid() + "++++" + obj[i].getMicindex());
                if (obj[i].getMicindex() == 0) {
                    videoUID = obj[i].getUserid();
                    System.out.println("===================: find mic: " + videoUID + "===" + videoIP + "===" + videoPort);
                }
            }
        }
    }

    private void PrintUnknown(String string) {
        // TODO Auto-generated method stub
        System.out.println("==============================:" + string);
    }


    @Override
    public void onGetRoomMicListResponse(UseridList obj) {
        PrintUnknown("onGetRoomMicListResponse: ");
        EventBus.getDefault().post(obj, "UseridList");
//        System.out.println("============" + obj.getList()[0]);
        Tools.PrintObject(obj);
    }

    @Override
    public void onGetFlygiftListResponse(BigGiftRecord[] obj) {
        System.out.println("onGetFlygiftListResponse: ");
        for (int i = 0; i < obj.length; i++) {
            Tools.PrintObject(obj[i]);
        }
    }

    @Override
    public void onAuthorityRejected(AuthorityRejected obj) {
        KLog.e("onAuthorityRejected: ");
        Tools.PrintObject(obj);
        EventBus.getDefault().post(obj, "AuthorityRejected");
    }

    @Override
    public void onSiegeInfoNotify(SiegeInfo obj) {
        System.out.println("onSiegeInfoNotify: ");
        Tools.PrintObject(obj);
    }

    @Override
    public void onKickoutRoomUserNotify(RoomKickoutUserInfo obj) {
        PrintUnknown("onKickoutRoomUserNotify: ");
        Tools.PrintObject(obj);
        EventBus.getDefault().post(obj, "RoomKickoutUserInfo");

    }

    @Override
    public void onChatMsgNotify(RoomChatMsg txt) {
        // TODO Auto-generated method stub
        System.out.println("onChatMsgNotify: ");
        EventBus.getDefault().post(txt, "RoomChatMsg");
        //txt.getContent 报空指针
        Tools.PrintObject(txt);
        // 回应消息
        // getChannel().sendChatMsg(0, (byte)0, (byte)0, "testChat");
    }

    //聊天消息错误
    @Override
    public void onChatMsgError(int err) {
        // TODO Auto-generated method stub
        PrintUnknown("onChatMsgError: ");
    }

    @Override
    public void onUserPayResponse(UserPayResponse obj) {
        // TODO Auto-generated method stub
        KLog.e("onUserPayResponse: ");
        Tools.PrintObject(obj);
    }

    @Override
    public void onSendSealNotify(SendSeal obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onSendSealNotify: ");

    }

    @Override
    public void onSetMicStateNotify(MicState obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onSetMicStateNotify: "+obj.getMicstate());
        Tools.PrintObject(obj);
        if (obj.getMicstate() == 1) {
            EventBus.getDefault().post(obj, "upMicState");
        }
        if (obj.getMicstate() == 8) {
            EventBus.getDefault().post(obj, "changeMicList");
        }
        if (obj.getMicstate() == 0) {
            EventBus.getDefault().post(obj, "downMicState");
        }
    }

    @Override
    public void onSetDevStateNotify(DevState obj) {
        // TODO Auto-generated method stub
        System.out.println("onSetDevStateNotify: ");
        Tools.PrintObject(obj);

    }

    @Override
    public void onSyncUserAliasNotify(UserAlias obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onSyncUserAliasNotify: ");
    }

    @Override
    public void onSyncUserHeadPicNotify(UserHeadPic obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onSyncUserHeadPicNotify: ");
    }

    @Override
    public void onSyncDecoColorNotify(DecoColor obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onSyncDecoColorNotify: ");
    }

    @Override
    public void onSetRoomBKgroupNotify(RoomBKGround obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onSetRoomBKgroupNotify: ");
    }

    @Override
    public void onSetRoomBaseInfoResponse() {
        // TODO Auto-generated method stub
        PrintUnknown("onSetRoomBaseInfoResponse: ");
    }

    @Override
    public void onRoomBaseInfoNotify(RoomBaseInfo obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onRoomBaseInfoNotify: ");
    }

    @Override
    public void onSetRoomManagersResponse() {
        // TODO Auto-generated method stub
        PrintUnknown("onSetRoomManagersResponse: ");
    }

    @Override
    public void onSetRoomManagersNotify(RoomManagerInfo obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onSetRoomManagersNotify: ");
    }

    @Override
    public void onRoomMediaURLNotify(RoomMediaInfo obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onRoomMediaURLNotify: ");
    }

    @Override
    public void onSetRoomRunStateNotify(RoomState obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onSetRoomRunStateNotify: ");
    }

    @Override
    public void onSetUserPriorityResponse() {
        // TODO Auto-generated method stub
        PrintUnknown("onSetUserPriorityResponse: ");
    }

    @Override
    public void onSetUserProfileResponse(SetUserProfileResp obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onSetUserProfileResponse: ");
    }

    @Override
    public void onSetUserPwdRepsonse(SetUserPwdResp obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onSetUserPwdRepsonse: ");
    }

    @Override
    public void onSetUserPwdError() {
        // TODO Auto-generated method stub
        PrintUnknown("onSetUserPwdError: ");
    }

    @Override
    public void onSetUserPriorityNotify(UserPriority obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onSetUserPriorityNotify: ");
    }

    @Override
    public void onTransMediaRequest(TransMediaInfo obj) {
        // TODO Auto-generated method stub
        System.out.println("onTransMediaRequest: ");
        Tools.PrintObject(obj);
    }

    @Override
    public void onTransMediaResponse(TransMediaInfo obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onTransMediaResponse: ");
    }

    @Override
    public void onTransMediaError(TransMediaInfo obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onTransMediaError: ");
    }

    @Override
    public void onRoomUserKeepAliveResponse() {
        // TODO Auto-generated method stub
        System.out.println("onRoomUserKeepAliveResponse: 心跳回应");
    }

    @Override
    public void onForbidUserChatNotify(ForbidUserChat obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onForbidUserChatNotify: ");
    }

    @Override
    public void onTradeGiftResponse() {
        // TODO Auto-generated method stub
       KLog.e("onTradeGiftResponse: ");
    }

    @Override
    public void onTradeGiftError(int i) {
        // TODO Auto-generated method stub
        KLog.e("onTradeGiftError: "+i);
    }

    @Override
    public void onTradeGiftNotify(BigGiftRecord obj) {
        // TODO Auto-generated method stub
        KLog.e("onTradeGiftNotify: ");
        Tools.PrintObject(obj);
        EventBus.getDefault().post(obj, "BigGiftRecord");
    }

    @Override
    public void onLotteryGiftNotify(LotteryGiftNotice obj) {
        // TODO Auto-generated method stub
        KLog.e("onTradeGiftNotify: ");
        PrintUnknown("onLotteryGiftNotify: ");
    }

    @Override
    public void onLotteryNotify(LotteryNotice obj) {//中奖礼物
        PrintUnknown("onLotteryNotify: ");
        EventBus.getDefault().post(obj, "onLotteryNotify");
    }

    @Override
    public void onSysCastNotify(SysCastNotice obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onSysCastNotify: ");
    }

    @Override
    public void onLocateUserIPResponse(LocateIPResp obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onLocateUserIPResponse: ");
    }

    @Override
    public void onGiveColorbarNotify(GiveColorbarInfo obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onGiveColorbarNotify: ");
    }

    @Override
    public void onAddMicTimeNotify(UserAddMicTimeInfo obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onAddMicTimeNotify: ");
    }

    @Override
    public void onActWaitMicUserNotify(ActWaitMicUserInfo obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onActWaitMicUserNotify: ");
        EventBus.getDefault().post(obj, "onActWaitMicUserNotify");

    }

    @Override
    public void onAddClosedFriendNotify(AddClosedFriendInfo obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onAddClosedFriendNotify: ");
    }

    @Override
    public void onBankDepositResponse(UserBankDepositRespInfo obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onBankDepositResponse: ");
    }

    @Override
    public void onBankDepositError() {
        // TODO Auto-generated method stub
        PrintUnknown("onBankDepositError: ");
    }

    @Override
    public void onDoNotReachRoomServer() {
        // TODO Auto-generated method stub
        PrintUnknown("onDoNotReachRoomServer: ");
    }

    @Override
    public void onDigTreasureResponse(DigTreasureResponse obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onDigTreasureResponse: ");
    }

    @Override
    public void onRedPagerResponse(RedPagerRequest obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onRedPagerResponse: ");
    }

    @Override
    public void onRedPagerError(int i) {
        // TODO Auto-generated method stub
        PrintUnknown("onRedPagerError: ");
    }

    @Override
    public void onGrabRedPagerResponse(GrabRedPagerRequest obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onGrabRedPagerResponse: ");
    }

    @Override
    public void onPreTradeGiftResponse(PreTradeGift obj) {
        // TODO Auto-generated method stub
        PrintUnknown("onPreTradeGiftResponse: ");
    }

    @Override
    public void onRoomVideoNotify(RoomVideoInfo obj) {
        PrintUnknown("RoomVideoInfo: ");
        EventBus.getDefault().post(obj, "onRoomVideoNotify");
    }

    @Override
    public void onDisconnected() {
        // TODO Auto-generated method stub
        KLog.e("onDisconnected");
        EventBus.getDefault().post("onDisconnected", "onDisconnected");
        isConnected = false;
    }

}
