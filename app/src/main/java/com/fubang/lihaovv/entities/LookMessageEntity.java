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
 * 项目名称：Wanghong
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2017-01-03 15:23
 * 修改人：zhuyunjian
 * 修改时间：2017-01-03 15:23
 * 修改备注：
 */
public class LookMessageEntity {
    private int lookMessageId;
    private String lookMessageName;

    public LookMessageEntity(int lookMessageId, String lookMessageName) {
        this.lookMessageId = lookMessageId;
        this.lookMessageName = lookMessageName;
    }

    public String getLookMessageName() {
        return lookMessageName;
    }

    public void setLookMessageName(String lookMessageName) {
        this.lookMessageName = lookMessageName;
    }

    public int getLookMessageId() {
        return lookMessageId;
    }

    public void setLookMessageId(int lookMessageId) {
        this.lookMessageId = lookMessageId;
    }
}
