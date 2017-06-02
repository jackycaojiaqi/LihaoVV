package com.fubang.lihaovv.model.impl;

import com.fubang.lihaovv.entities.FollowEntity;
import com.fubang.lihaovv.model.BaseModel;
import com.fubang.lihaovv.model.FollowModel;

import retrofit2.Callback;

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
 * 项目名称：Youliao
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2016-11-24 15:13
 * 修改人：zhuyunjian
 * 修改时间：2016-11-24 15:13
 * 修改备注：
 */
public class FollowModelImpl extends BaseModel implements FollowModel {
    private static volatile FollowModelImpl instance = null;

    private FollowModelImpl(){

    }

    public static FollowModelImpl getInstance() {
        if (instance == null) {
            synchronized (FollowModelImpl.class) {
                if (instance == null) {
                    instance = new FollowModelImpl();
                }
            }
        }
        return instance;
    }
    @Override
    public void getFollowData(Callback<FollowEntity> callback, int userid) {
        service.getFollowEntity(userid).enqueue(callback);
    }
}
