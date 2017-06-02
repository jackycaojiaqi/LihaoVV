package com.xlg.android.protocol;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * <p>
 * 项目名称：newfubangzhibo
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2016-08-22 09:43
 * 修改人：zhuyunjian
 * 修改时间：2016-08-22 09:43
 * 修改备注：
 */
public class SetMicStateNotify {

    @StructOrder(0)
    private int vcbid;      //房间id
    @StructOrder(1)
    private int runnerid;       //操作中id
    @StructOrder(2)
    private int userid;         //被操作者id
    @StructOrder(3)
//    private short chargemicgiftid;
    private short micstate;     //麦克状态
    @StructOrder(4)
    private byte  micindex;     //麦克序号
    @StructOrder(5)
    private short reserve;      //保留
    @StructOrder(6)
    private long micendtime;        //麦时结束时间
    @StructOrder(7)
    private long miclnowtime;       //现在麦时过了多久
    @StructOrder(8)
    private int isallowupmic;       //是否允许抱麦

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

    public long getMiclnowtime() {
        return miclnowtime;
    }

    public void setMiclnowtime(long miclnowtime) {
        this.miclnowtime = miclnowtime;
    }

    public int getIsallowupmic() {
        return isallowupmic;
    }

    public void setIsallowupmic(int isallowupmic) {
        this.isallowupmic = isallowupmic;
    }
}
