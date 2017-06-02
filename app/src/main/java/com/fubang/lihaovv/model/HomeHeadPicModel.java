package com.fubang.lihaovv.model;

import com.fubang.lihaovv.entities.HomeHeadPicEntity;

import retrofit2.Callback;

/**
 * Created by dell on 2016/4/5.
 */
public interface HomeHeadPicModel {
    void getHeadPicData(Callback<HomeHeadPicEntity> callback);
}
