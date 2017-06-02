package com.fubang.lihaovv.model.impl;

import com.fubang.lihaovv.entities.FavoriteEntity;
import com.fubang.lihaovv.model.BaseModel;
import com.fubang.lihaovv.model.FavoriteModel;

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
 * 创建时间：2016-07-11 15:02
 * 修改人：zhuyunjian
 * 修改时间：2016-07-11 15:02
 * 修改备注：
 */
public class FavoriteModelImpl extends BaseModel implements FavoriteModel {
    private static volatile FavoriteModelImpl instance = null;

    private FavoriteModelImpl(){

    }

    public static FavoriteModelImpl getInstance() {
        if (instance == null) {
            synchronized (FavoriteModelImpl.class) {
                if (instance == null) {
                    instance = new FavoriteModelImpl();
                }
            }
        }
        return instance;
    }
    @Override
    public void getFavoriteData(Callback<FavoriteEntity> callback, int userid) {
        service.getFavoriteEntity(userid).enqueue(callback);
    }
}
