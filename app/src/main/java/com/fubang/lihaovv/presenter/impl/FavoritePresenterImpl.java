package com.fubang.lihaovv.presenter.impl;

import com.fubang.lihaovv.entities.FavoriteEntity;
import com.fubang.lihaovv.model.ModelFactory;
import com.fubang.lihaovv.presenter.FavoritePresenter;
import com.fubang.lihaovv.view.FavoriteView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
 * 创建时间：2016-07-11 15:05
 * 修改人：zhuyunjian
 * 修改时间：2016-07-11 15:05
 * 修改备注：
 */
public class FavoritePresenterImpl implements FavoritePresenter {
    private FavoriteView favoriteView;
    private int userid;

    public FavoritePresenterImpl(FavoriteView favoriteView, int userid) {
        this.favoriteView = favoriteView;
        this.userid = userid;
    }

    @Override
    public void getFavorite() {
        ModelFactory.getInstance().getFavoriteModelImpl().getFavoriteData(new Callback<FavoriteEntity>() {
            @Override
            public void onResponse(Call<FavoriteEntity> call, Response<FavoriteEntity> response) {
                favoriteView.successFavorite(response.body());
            }

            @Override
            public void onFailure(Call<FavoriteEntity> call, Throwable t) {
                favoriteView.failFavorite();
            }
        },userid);
    }
}
