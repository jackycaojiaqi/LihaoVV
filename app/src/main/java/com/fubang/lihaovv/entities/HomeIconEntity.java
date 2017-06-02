package com.fubang.lihaovv.entities;

import java.util.List;

/**
 * 首页图标
 * Created by dell on 2016/4/5.
 */
public class HomeIconEntity {

    /**
     * code : 0
     * message :
     * data : [{"id":"7175","position":"appIndex.Game","chnId":"0","roomId":"0","matchId":"0","gameId":"6","spic":"http://dlpic.cdn.zhanqi.tv/uploads/2016/03/recommendspic-2016032217071535677.png","bpic":"","title":"英雄联盟","url":"","contents":"","startTime":"0","endTime":"0","weight":"100","positionType":""},{"id":"7176","position":"appIndex.Game","chnId":"0","roomId":"0","matchId":"0","gameId":"10","spic":"http://dlpic.cdn.zhanqi.tv/uploads/2016/03/recommendspic-2016031015451011142.png","bpic":"","title":"DOTA2","url":"","contents":"","startTime":"0","endTime":"0","weight":"99","positionType":""},{"id":"7228","position":"appIndex.Game","chnId":"0","roomId":"0","matchId":"0","gameId":"13","spic":"http://dlpic.cdn.zhanqi.tv/uploads/2016/03/recommendspic-2016031015480719059.png","bpic":"","title":"三国杀","url":"","contents":"","startTime":"0","endTime":"0","weight":"95","positionType":""},{"id":"7229","position":"appIndex.Game","chnId":"0","roomId":"0","matchId":"0","gameId":"9","spic":"http://dlpic.cdn.zhanqi.tv/uploads/2016/03/recommendspic-2016031015492485522.png","bpic":"","title":"炉石传说","url":"","contents":"","startTime":"0","endTime":"0","weight":"90","positionType":""},{"id":"7847","position":"appIndex.Game","chnId":"0","roomId":"0","matchId":"0","gameId":"28","spic":"http://dlpic.cdn.zhanqi.tv/uploads/2016/04/recommendspic-2016040511373787148.png","bpic":"","title":"手机游戏","url":"","contents":"","startTime":"0","endTime":"0","weight":"86","positionType":""},{"id":"7231","position":"appIndex.Game","chnId":"0","roomId":"0","matchId":"0","gameId":"22","spic":"http://dlpic.cdn.zhanqi.tv/uploads/2016/03/recommendspic-2016031015522752570.png","bpic":"","title":"DNF","url":"","contents":"","startTime":"0","endTime":"0","weight":"85","positionType":""},{"id":"7230","position":"appIndex.Game","chnId":"104","roomId":"0","matchId":"0","gameId":"49","spic":"http://dlpic.cdn.zhanqi.tv/uploads/2016/03/recommendspic-2016031015513820093.png","bpic":"","title":"单机游戏","url":"","contents":"","startTime":"0","endTime":"0","weight":"80","positionType":""},{"id":"7232","position":"appIndex.Game","chnId":"0","roomId":"0","matchId":"0","gameId":"67","spic":"http://dlpic.cdn.zhanqi.tv/uploads/2016/03/recommendspic-2016031015535897979.png","bpic":"","title":"射击游戏","url":"","contents":"","startTime":"0","endTime":"0","weight":"75","positionType":""}]
     */

    private int code;
    private String message;
    /**
     * id : 7175
     * position : appIndex.Game
     * chnId : 0
     * roomId : 0
     * matchId : 0
     * gameId : 6
     * spic : http://dlpic.cdn.zhanqi.tv/uploads/2016/03/recommendspic-2016032217071535677.png
     * bpic :
     * title : 英雄联盟
     * url :
     * contents :
     * startTime : 0
     * endTime : 0
     * weight : 100
     * positionType :
     */

    private List<DataEntity> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }


}
