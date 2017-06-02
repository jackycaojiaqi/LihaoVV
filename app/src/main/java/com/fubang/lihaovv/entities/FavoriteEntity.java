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
 * 创建时间：2016-07-11 14:57
 * 修改人：zhuyunjian
 * 修改时间：2016-07-11 14:57
 * 修改备注：
 */
public class FavoriteEntity {

    /**
     * status : success
     * datalist : [{"id":"5","nuserid":"1","nvcbid":"123","ddate":"2011-01-01 00:00:00+08"}]
     */

    private String status;
    /**
     * id : 5
     * nuserid : 1
     * nvcbid : 123
     * ddate : 2011-01-01 00:00:00+08
     */

    private List<DatalistEntity> datalist;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DatalistEntity> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<DatalistEntity> datalist) {
        this.datalist = datalist;
    }


}
