package com.fubang.lihaovv.entities;

import java.util.List;

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
 * <p/>
 * 项目名称：MyApplication
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2016-07-20 16:37
 * 修改人：zhuyunjian
 * 修改时间：2016-07-20 16:37
 * 修改备注：
 */
public class ActorEntity {

    /**
     * status : success
     * datalist : [{"nuserid":"233","cteamname":"橙橙","nvcbid":"0","cphoto":"","media":""},{"nuserid":"509","cteamname":"小墨","nvcbid":"0","cphoto":"20160719040026_647.bmp","media":""},{"nuserid":"522","cteamname":"平平","nvcbid":"0","cphoto":"20160719060112_55.bmp","media":""}]
     */

    private String status;
    /**
     * nuserid : 233
     * cteamname : 橙橙
     * nvcbid : 0
     * cphoto :
     * media :
     */

    private List<ActorListEntity> datalist;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ActorListEntity> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<ActorListEntity> datalist) {
        this.datalist = datalist;
    }


}
