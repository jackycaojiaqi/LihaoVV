package com.xlg.android.protocol;

/**
 * Created by jacky on 2017/6/8.
 */
public class GetWaitMicListInfo {
    @StructOrder(0)
    private int vcbid;  //房间id
    @StructOrder(1)
    private int userid;  //用户id

    public int getVcbid() {
        return vcbid;
    }

    public void setVcbid(int vcbid) {
        this.vcbid = vcbid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
