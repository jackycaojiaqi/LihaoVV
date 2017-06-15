package com.fubang.lihaovv.utils;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.entities.LookMessageEntity;

import java.util.ArrayList;
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
 * <p>
 * 项目名称：Wanghong
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2017-01-03 15:24
 * 修改人：zhuyunjian
 * 修改时间：2017-01-03 15:24
 * 修改备注：
 */
public class LookMessageUtil {
    public static List<LookMessageEntity> getMessageEntity() {
        List<LookMessageEntity> list = new ArrayList<>();
        list.add(new LookMessageEntity(R.mipmap.mai1, "抱上1麦"));
        list.add(new LookMessageEntity(R.mipmap.mai2, "抱上2麦"));
        list.add(new LookMessageEntity(R.mipmap.mai3, "抱上3麦"));
        list.add(new LookMessageEntity(R.mipmap.tellqiaoqiao, "私聊"));
        return list;
    }

    public static List<LookMessageEntity> getMicQuenEntity() {
        List<LookMessageEntity> list = new ArrayList<>();
        list.add(new LookMessageEntity(R.mipmap.mai1, "抱上1麦"));
        list.add(new LookMessageEntity(R.mipmap.mai2, "抱上2麦"));
        list.add(new LookMessageEntity(R.mipmap.mai3, "抱上3麦"));
        list.add(new LookMessageEntity(R.mipmap.del, "取消麦序"));
        return list;
    }
}
