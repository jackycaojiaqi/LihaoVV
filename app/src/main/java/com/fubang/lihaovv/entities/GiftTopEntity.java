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
 * <p/>
 * 项目名称：MyApplication
 * 类描述：
 * 创建人：dell
 * 创建时间：2016-06-24 16:36
 * 修改人：dell
 * 修改时间：2016-06-24 16:36
 * 修改备注：
 */
public class GiftTopEntity {

    /**
     * anchorName : 乖乖惹人爱
     * ncount : 11664572
     */
    @SerializedName("uername")
    private String uername;
    @SerializedName("meili")
    private String meili;
    @SerializedName("nuserid")
    private String nuserid;

    public String getUername() {
        return uername;
    }

    public void setUername(String uername) {
        this.uername = uername;
    }

    public String getMeili() {
        return meili;
    }

    public void setMeili(String meili) {
        this.meili = meili;
    }

    public String getNuserid() {
        return nuserid;
    }

    public void setNuserid(String nuserid) {
        this.nuserid = nuserid;
    }
}
