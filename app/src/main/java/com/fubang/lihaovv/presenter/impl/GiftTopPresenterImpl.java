package com.fubang.lihaovv.presenter.impl;

import com.fubang.lihaovv.entities.GiftTopListEntity;
import com.fubang.lihaovv.model.ModelFactory;
import com.fubang.lihaovv.presenter.GiftTopPresenter;
import com.fubang.lihaovv.view.GiftTopView;

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
 * 创建人：dell
 * 创建时间：2016-06-24 17:19
 * 修改人：dell
 * 修改时间：2016-06-24 17:19
 * 修改备注：
 */
public class GiftTopPresenterImpl implements GiftTopPresenter {
    private GiftTopView giftTopView;

    public GiftTopPresenterImpl(GiftTopView giftTopView) {
        this.giftTopView = giftTopView;
    }

    @Override
    public void getGiftTop() {
        ModelFactory.getInstance().getGiftTopModelImpl().getGiftTopEntityData(new Callback<GiftTopListEntity>() {
            @Override
            public void onResponse(Call<GiftTopListEntity> call, Response<GiftTopListEntity> response) {
                giftTopView.successGiftTop(response.body());
            }

            @Override
            public void onFailure(Call<GiftTopListEntity> call, Throwable t) {
                giftTopView.failedGiftTop();
            }
        });
    }
}
