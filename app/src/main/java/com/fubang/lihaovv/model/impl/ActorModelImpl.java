package com.fubang.lihaovv.model.impl;

import com.fubang.lihaovv.entities.ActorEntity;
import com.fubang.lihaovv.model.ActorModel;
import com.fubang.lihaovv.model.BaseModel;

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
 * <p/>
 * 项目名称：MyApplication
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2016-07-20 16:43
 * 修改人：zhuyunjian
 * 修改时间：2016-07-20 16:43
 * 修改备注：
 */
public class ActorModelImpl extends BaseModel implements ActorModel {
    private static volatile ActorModelImpl instance = null;

    private ActorModelImpl(){

    }

    public static ActorModelImpl getInstance() {
        if (instance == null) {
            synchronized (ActorModelImpl.class) {
                if (instance == null) {
                    instance = new ActorModelImpl();
                }
            }
        }
        return instance;
    }
    @Override
    public void getActorData(Callback<ActorEntity> callback, int userid) {
        service.getActorEntity(userid).enqueue(callback);
    }
}
