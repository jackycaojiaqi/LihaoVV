package com.fubang.lihaovv.presenter.impl;

import com.fubang.lihaovv.entities.ActorEntity;
import com.fubang.lihaovv.model.ModelFactory;
import com.fubang.lihaovv.presenter.ActorPresenter;
import com.fubang.lihaovv.view.ActorView;

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
 * 创建时间：2016-07-20 16:41
 * 修改人：zhuyunjian
 * 修改时间：2016-07-20 16:41
 * 修改备注：
 */
public class ActorPresenterImpl implements ActorPresenter {
    private ActorView actorView;
    private int userid;

    public ActorPresenterImpl(ActorView actorView, int userid) {
        this.actorView = actorView;
        this.userid = userid;
    }

    @Override
    public void getActorEntity() {
        ModelFactory.getInstance().getActorModelImpl().getActorData(new Callback<ActorEntity>() {
            @Override
            public void onResponse(Call<ActorEntity> call, Response<ActorEntity> response) {
                actorView.successActor(response.body());
            }

            @Override
            public void onFailure(Call<ActorEntity> call, Throwable t) {
                actorView.failActor();
            }
        },userid);
    }
}
