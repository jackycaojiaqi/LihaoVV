package com.fubang.lihaovv.fragment;


import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.adapters.NewAnchorAdapter;
import com.fubang.lihaovv.api.ApiService;
import com.fubang.lihaovv.entities.NewAnchorEntity;
import com.fubang.lihaovv.entities.NewAnchorListEntity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_new)
public class NewFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener, Callback<NewAnchorListEntity> {
    @ViewById(R.id.new_ptlist)
    PullToRefreshGridView ptRefreshGv;
    private List<NewAnchorEntity> list = new ArrayList<>();
    private NewAnchorAdapter adapter;
    private Call<NewAnchorListEntity> call;

    @Override
    public void before() {
        EventBus.getDefault().register(this);
//        type = getArguments().getString(AppConstant.HOME_TYPE);
    }

    @Override
    public void initView() {
        adapter = new NewAnchorAdapter(list,getContext());
        ptRefreshGv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ptRefreshGv.setOnRefreshListener(this);
        ptRefreshGv.setAdapter(adapter);
    }

    @Override
    public void initData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(AppConstant.BASE_URL)
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, NewAnchorListEntity>() {
                            @Override
                            public NewAnchorListEntity convert(ResponseBody value) throws IOException {
                                NewAnchorListEntity entity = null;
                                try {
                                    entity = new NewAnchorListEntity();
                                    List<NewAnchorEntity> list = new ArrayList<>();
                                    JSONArray array = new JSONArray(value.string());
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);
                                        NewAnchorEntity newAnchorEntity = new NewAnchorEntity();
                                        newAnchorEntity.setCalias(object.getString("calias"));
                                        newAnchorEntity.setCphoto(object.getString("cphoto"));
                                        newAnchorEntity.setRoomUrl(object.getString("roomUrl"));
                                        list.add(newAnchorEntity);
                                    }
                                    entity.setList(list);
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                                return entity;
                            }
                        };
                    }
                }).build();
        ApiService service = retrofit.create(ApiService.class);
        call = service.getNewAnchorListEntity(1);
        call.enqueue(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        Call<NewAnchorListEntity> call2 = call.clone();
        call2.enqueue(this);
    }

    @Override
    public void onResponse(Call<NewAnchorListEntity> call, Response<NewAnchorListEntity> response) {
        ptRefreshGv.onRefreshComplete();
        call.cancel();
        list.clear();
        List<NewAnchorEntity> data = response.body().getList();
        if(data != null) {
            list.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(Call<NewAnchorListEntity> call, Throwable t) {
        Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
    }


}
