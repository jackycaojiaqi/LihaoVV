package com.xlg.android.protocol;

/**
 * Created by jacky on 2017/6/7.
 */
public class RoomVideoInfo {
    @StructOrder(0)
    private int vcbid;//房间号
    @StructOrder(1)
    private int userid;//操作人id
    @StructOrder(2)
    private int nvideowndtype;                //管麦    转换成16进制   0x0100  0x0111  左后3位表示   0 1 2 号是否管麦

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

    public int getNvideowndtype() {
        return nvideowndtype;
    }

    public void setNvideowndtype(int nvideowndtype) {
        this.nvideowndtype = nvideowndtype;
    }
}
