package com.fubang.lihaovv.model.impl;

import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.entities.RoomEntity;
import com.fubang.lihaovv.model.BaseModel;
import com.fubang.lihaovv.model.RoomListModel;
import com.fubang.lihaovv.utils.ParamsMap;

import retrofit2.Callback;

/**
 * Created by dell on 2016/4/7.
 */
public class RoomListModelImpl extends BaseModel implements RoomListModel {
    private static volatile RoomListModelImpl instance = null;

    private RoomListModelImpl(){

    }

    public static RoomListModelImpl getInstance() {
        if (instance == null) {
            synchronized (RoomListModelImpl.class) {
                if (instance == null) {
                    instance = new RoomListModelImpl();
                }
            }
        }
        return instance;
    }
    @Override
    public void getRoomListData(Callback<RoomEntity> callback, int count, int page, int groupId) {
        ParamsMap map = ParamsMap.getInstance();
        map.put(AppConstant.COUNT,count);
        map.put(AppConstant.PAGE,page);
        map.put(AppConstant.GROUP,groupId);
        service.getRoomEntity(map).enqueue(callback);
    }
}
