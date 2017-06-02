package com.fubang.lihaovv.entities;

import com.google.gson.annotations.SerializedName;

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
 * 创建时间：2016-09-28 10:15
 * 修改人：zhuyunjian
 * 修改时间：2016-09-28 10:15
 * 修改备注：
 */
public class HistoryEnity {


    /**
     * roomid : 123
     * roomname : 123yyyy
     * roompic :
     * roomrs : 300
     * roompwd : 
     * rscount : 4
     * gateway : 120.26.54.182:12529;120.26.54.182:12529;120.26.54.182:12529;:12529
     */
    @SerializedName("roomid")
    private String roomid;
    @SerializedName("roomname")
    private String roomname;
    @SerializedName("roompic")
    private String roompic;
    @SerializedName("roomrs")
    private String roomrs;
    @SerializedName("roompwd")
    private String roompwd;
    @SerializedName("rscount")
    private String rscount;
    @SerializedName("gateway")
    private String gateway;

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getRoompic() {
        return roompic;
    }

    public void setRoompic(String roompic) {
        this.roompic = roompic;
    }

    public String getRoomrs() {
        return roomrs;
    }

    public void setRoomrs(String roomrs) {
        this.roomrs = roomrs;
    }

    public String getRoompwd() {
        return roompwd;
    }

    public void setRoompwd(String roompwd) {
        this.roompwd = roompwd;
    }

    public String getRscount() {
        return rscount;
    }

    public void setRscount(String rscount) {
        this.rscount = rscount;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }
}
