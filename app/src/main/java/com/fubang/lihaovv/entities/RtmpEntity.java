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
 * 项目名称：QiniuDemo
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2017-03-27 16:34
 * 修改人：zhuyunjian
 * 修改时间：2017-03-27 16:34
 * 修改备注：
 */
public class RtmpEntity {

    /**
     * state : 200
     * publishUrl : rtmp://pili-publish.fbyxsp.com/wanghong/wh_99888_99887?e=1490613625&token=rgNGguFNzZb47-3LXCxtm4H5iMjbMG-5dhhOR512:CMm4-H0BDJPp6RaQoT3t9wtHMK0=
     * RTMPPlayURL : rtmp://pili-live-rtmp.fbyxsp.com/wanghong/wh_99888_99887
     */

    private int state;
    private String publishUrl;
    private String RTMPPlayURL;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPublishUrl() {
        return publishUrl;
    }

    public void setPublishUrl(String publishUrl) {
        this.publishUrl = publishUrl;
    }

    public String getRTMPPlayURL() {
        return RTMPPlayURL;
    }

    public void setRTMPPlayURL(String RTMPPlayURL) {
        this.RTMPPlayURL = RTMPPlayURL;
    }
}
