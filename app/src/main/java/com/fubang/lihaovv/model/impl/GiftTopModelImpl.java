package com.fubang.lihaovv.model.impl;

import com.fubang.lihaovv.entities.GiftTopListEntity;
import com.fubang.lihaovv.model.BaseModel;
import com.fubang.lihaovv.model.GiftTopModel;
import com.fubang.lihaovv.utils.ParamsMap;

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
 * 创建人：dell
 * 创建时间：2016-06-24 17:05
 * 修改人：dell
 * 修改时间：2016-06-24 17:05
 * 修改备注：
 */
public class GiftTopModelImpl extends BaseModel implements GiftTopModel {
    private static volatile GiftTopModelImpl instance = null;

    private GiftTopModelImpl(){

    }

    public static GiftTopModelImpl getInstance() {
        if (instance == null) {
            synchronized (GiftTopModelImpl.class) {
                if (instance == null) {
                    instance = new GiftTopModelImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void getGiftTopEntityData(Callback<GiftTopListEntity> callback) {
        ParamsMap map = ParamsMap.getInstance();
        map.put("type",1);
        service.getGiftTopEntity(1).enqueue(callback);
    }
}
