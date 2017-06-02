package com.fubang.lihaovv.model;

import com.fubang.lihaovv.entities.HomeEntity;

import retrofit2.Callback;

/**
 * Created by dell on 2016/4/5.
 */
public interface HomeModel {
    void getHomeData(Callback<HomeEntity> callback);
}
