package com.fubang.lihaovv.model;

import com.fubang.lihaovv.entities.RoomEntity;

import retrofit2.Callback;

/**
 * Created by dell on 2016/4/7.
 */
public interface RoomListModel {
    void getRoomListData(Callback<RoomEntity> callback, int count, int page, int groupId);
}
