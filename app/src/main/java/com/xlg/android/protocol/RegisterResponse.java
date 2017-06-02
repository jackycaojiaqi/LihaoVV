package com.xlg.android.protocol;

import com.xlg.android.utils.SimpSecret;
import com.xlg.android.utils.Tools;

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
 * 创建时间：2016-09-30 09:44
 * 修改人：zhuyunjian
 * 修改时间：2016-09-30 09:44
 * 修改备注：
 */
public class RegisterResponse {
    @StructOrder(0)
    private int	userid;						//用户ID
    @StructOrder(1)
    private byte[]			cuserpwd = new byte[32];//用户密码
    @StructOrder(2)
    private short	errid;						//错误ID
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public String getCuserpwd() {
        byte [] data = new byte[cuserpwd.length];
        System.arraycopy(cuserpwd, 0, data, 0, cuserpwd.length);
        SimpSecret.SimpDecrypt(data);
        return Tools.ByteArray2StringGBK(data);
    }

    public void setCuserpwd(String pwd) {
        Tools.String2ByteArrayGBK(cuserpwd,pwd);
        SimpSecret.SimpEncrypt(cuserpwd);
    }
    public short getErrid() {
        return errid;
    }

    public void setErrid(short errid) {
        this.errid = errid;
    }
}
