package com.fubang.lihaovv.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell on 2016/4/5.
 */
public class HomeEntity {
    private String code;
    private String message;
    @SerializedName("data")
    private HomeDataEntity data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HomeDataEntity getData() {
        return data;
    }

    public void setData(HomeDataEntity data) {
        this.data = data;
    }
}
