package com.fubang.lihaovv.entities;

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
 * 项目名称：Youliao
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2016-11-24 14:40
 * 修改人：zhuyunjian
 * 修改时间：2016-11-24 14:40
 * 修改备注：
 */
public class FollowListEnitty {
    private String roomid;
    private String roomname;
    private String roompic;
    private String roomrs;
    private String roompwd;
    private String rscount;
    private String gateway;



    public FollowListEnitty( String roomid, String roomname, String roompic, String roomrs, String roompwd, String rscount, String gateway) {
        this.roomid = roomid;
        this.roomname = roomname;
        this.roompic = roompic;
        this.roomrs = roomrs;
        this.roompwd = roompwd;
        this.rscount = rscount;
        this.gateway = gateway;
    }

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
