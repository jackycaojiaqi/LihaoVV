package com.fubang.lihaovv.presenter.impl;

import com.fubang.lihaovv.entities.AnchorListEntity;
import com.fubang.lihaovv.model.ModelFactory;
import com.fubang.lihaovv.presenter.AnchorPresenter;
import com.fubang.lihaovv.view.AnchorView;

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
 * 创建时间：2016-06-24 17:20
 * 修改人：dell
 * 修改时间：2016-06-24 17:20
 * 修改备注：
 */
public class AnchorPresenterImpl implements AnchorPresenter {
    private AnchorView anchorView;

    public AnchorPresenterImpl(AnchorView anchorView) {
        this.anchorView = anchorView;
    }

    @Override
    public void getAnchor() {
        ModelFactory.getInstance().getAnchorModelImpl().getAnchorEntityData(new Callback<AnchorListEntity>() {
            @Override
            public void onResponse(Call<AnchorListEntity> call, Response<AnchorListEntity> response) {
                anchorView.successAnchor(response.body());
            }

            @Override
            public void onFailure(Call<AnchorListEntity> call, Throwable t) {
                anchorView.failedAnchor();
            }
        });
    }
}
