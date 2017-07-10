package com.xlg.android.protocol;

public class MicState {
    @StructOrder(0)
    private int vcbid;         //房间id
    @StructOrder(1)
    private int runnerid;      //操作人员id
    @StructOrder(2)
    private int userid;        //被操作人员id
    @StructOrder(3)
    private int udmic;        //mic序列号上麦拼接rtmp地址用
    @StructOrder(4)
    private short chargemicgiftid;    //用于收费麦的礼物id
    @StructOrder(5)
    private short chargemicgiftcount;  //用于收费麦的礼物数目
    @StructOrder(6)
    private short micstate;      //麦克状态,注意下面的麦状态标志
    @StructOrder(7)
    private byte micindex;      //麦克序号(notify中有数据)
    @StructOrder(8)
    private short reserve;       //保留
    // add by:baddie@126.com  上麦的状态返回 计算麦时用
    @StructOrder(9)
    private long micendtime;             //麦时结束时间
    @StructOrder(10)
    private long micnowtime;           // 现在麦时过了多久
    @StructOrder(11)
    private int isallowupmic;        //是否允许抱麦

    public int getUdmic() {
        return udmic;
    }

    public void setUdmic(int udmic) {
        this.udmic = udmic;
    }

    public int getVcbid() {
        return vcbid;
    }

    public void setVcbid(int vcbid) {
        this.vcbid = vcbid;
    }

    public int getRunnerid() {
        return runnerid;
    }

    public void setRunnerid(int runnerid) {
        this.runnerid = runnerid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public short getChargemicgiftid() {
        return chargemicgiftid;
    }

    public void setChargemicgiftid(short chargemicgiftid) {
        this.chargemicgiftid = chargemicgiftid;
    }

    public short getChargemicgiftcount() {
        return chargemicgiftcount;
    }

    public void setChargemicgiftcount(short chargemicgiftcount) {
        this.chargemicgiftcount = chargemicgiftcount;
    }

    public short getMicstate() {
        return micstate;
    }

    public void setMicstate(short micstate) {
        this.micstate = micstate;
    }

    public byte getMicindex() {
        return micindex;
    }

    public void setMicindex(byte micindex) {
        this.micindex = micindex;
    }

    public short getReserve() {
        return reserve;
    }

    public void setReserve(short reserve) {
        this.reserve = reserve;
    }

    public long getMicendtime() {
        return micendtime;
    }

    public void setMicendtime(long micendtime) {
        this.micendtime = micendtime;
    }

    public long getMicnowtime() {
        return micnowtime;
    }

    public void setMicnowtime(long micnowtime) {
        this.micnowtime = micnowtime;
    }

    public int getIsallowupmic() {
        return isallowupmic;
    }

    public void setIsallowupmic(int isallowupmic) {
        this.isallowupmic = isallowupmic;
    }

}
