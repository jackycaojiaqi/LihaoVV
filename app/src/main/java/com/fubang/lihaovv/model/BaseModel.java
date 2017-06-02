package com.fubang.lihaovv.model;

import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.api.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dell on 2016/4/5.
 */
public class BaseModel {
    public ApiService service;
    public BaseModel(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }
}
