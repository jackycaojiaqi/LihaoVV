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
 * 创建时间：2016-09-28 09:26
 * 修改人：zhuyunjian
 * 修改时间：2016-09-28 09:26
 * 修改备注：
 */
public class RoomSumEntity {
    @SerializedName("nvcbid")
    private String nvcbid;
    @SerializedName("renqi")
    private String renqi;
    @SerializedName("calias")
    private String calias;
    @SerializedName("cname")
    private String cname;
    @SerializedName("ncreateid")
    private String ncreateid;
    @SerializedName("croompic")
    private String croompic;

    public String getNvcbid() {
        return nvcbid;
    }

    public void setNvcbid(String nvcbid) {
        this.nvcbid = nvcbid;
    }

    public String getRenqi() {
        return renqi;
    }

    public void setRenqi(String renqi) {
        this.renqi = renqi;
    }

    public String getCalias() {
        return calias;
    }

    public void setCalias(String calias) {
        this.calias = calias;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getNcreateid() {
        return ncreateid;
    }

    public void setNcreateid(String ncreateid) {
        this.ncreateid = ncreateid;
    }

    public String getCroompic() {
        return croompic;
    }

    public void setCroompic(String croompic) {
        this.croompic = croompic;
    }
}
