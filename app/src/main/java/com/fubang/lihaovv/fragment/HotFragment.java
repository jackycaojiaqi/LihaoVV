package com.fubang.lihaovv.fragment;


import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.adapters.HotRoomAdapter;
import com.fubang.lihaovv.api.ApiService;
import com.fubang.lihaovv.entities.HotAnchorEntity;
import com.fubang.lihaovv.entities.HotAnchorListEntity;
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
@EFragment(R.layout.fragment_hot)
public class HotFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener, Callback<HotAnchorListEntity> {
    @ViewById(R.id.hot_ptlist)
    PullToRefreshGridView ptRefreshGv;
    private List<HotAnchorEntity> list = new ArrayList<>();
    private HotRoomAdapter adapter;
    private Call<HotAnchorListEntity> call;

    @Override
    public void before() {
        EventBus.getDefault().register(this);
//        type = getArguments().getString(AppConstant.HOME_TYPE);
    }

    @Override
    public void initView() {
        adapter = new HotRoomAdapter(list,getContext());
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
                        return new Converter<ResponseBody, HotAnchorListEntity>() {
                            @Override
                            public HotAnchorListEntity convert(ResponseBody value) throws IOException {
                                HotAnchorListEntity entity = null;
                                try {
                                    entity = new HotAnchorListEntity();
                                    List<HotAnchorEntity> list = new ArrayList<>();
                                    JSONArray array = new JSONArray(value.string());
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);
                                        HotAnchorEntity hotAnchorEntity = new HotAnchorEntity();
                                        hotAnchorEntity.setCalias(object.getString("calias"));
                                        hotAnchorEntity.setCphoto(object.getString("cphoto"));
                                        hotAnchorEntity.setRoomUrl(object.getString("roomUrl"));
                                        list.add(hotAnchorEntity);
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
        call = service.getHotAnchorEntity(1);
        call.enqueue(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        Call<HotAnchorListEntity> call2 = call.clone();
        call2.enqueue(this);
    }

    @Override
    public void onResponse(Call<HotAnchorListEntity> call, Response<HotAnchorListEntity> response) {
        ptRefreshGv.onRefreshComplete();
        call.cancel();
        list.clear();
        List<HotAnchorEntity> data = response.body().getList();
        if (data != null) {
            list.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(Call<HotAnchorListEntity> call, Throwable t) {
        Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
    }
}
