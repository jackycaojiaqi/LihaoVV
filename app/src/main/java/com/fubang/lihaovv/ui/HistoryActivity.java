package com.fubang.lihaovv.ui;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.adapters.HistoryAdapter;
import com.fubang.lihaovv.api.ApiService;
import com.fubang.lihaovv.entities.HistoryEnity;
import com.fubang.lihaovv.entities.HistoryListEntiy;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

@EActivity(R.layout.activity_history)
public class HistoryActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener, Callback<HistoryListEntiy> {
    @ViewById(R.id.history_listview)
    PullToRefreshListView listView;
    @ViewById(R.id.history_back_btn)
    ImageView backImage;


    private HistoryAdapter adapter;
    private List<HistoryEnity> data = new ArrayList<>();
    private Call<HistoryListEntiy> call;

    @Override
    public void initView() {
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new HistoryAdapter(data,this);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.setOnRefreshListener(this);
        listView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(AppConstant.BASE_URL)
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, HistoryListEntiy>() {
                            @Override
                            public HistoryListEntiy convert(ResponseBody value) throws IOException {
                                HistoryListEntiy entity = null;
                                try {
                                    entity = new HistoryListEntiy();
                                    List<HistoryEnity> list = new ArrayList<>();
                                    JSONArray array = new JSONArray(value.string());
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);
                                        HistoryEnity historyEnity = new HistoryEnity();
                                        historyEnity.setRoomname(object.getString("roomname"));
                                        historyEnity.setRoomid(object.getString("roomid"));
                                        historyEnity.setRoompic(object.getString("roompic"));
                                        historyEnity.setGateway(object.getString("gateway"));
                                        list.add(historyEnity);
                                    }
                                    entity.setEnities(list);
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                                return entity;
                            }
                        };
                    }
                }).build();
        ApiService service = retrofit.create(ApiService.class);
        call = service.getHistoryEnity(Integer.parseInt(StartUtil.getUserId(this)));
        call.enqueue(this);
    }



    @Override
    public void onResponse(Call<HistoryListEntiy> call, Response<HistoryListEntiy> response) {
        listView.onRefreshComplete();
        call.cancel();
        data.clear();
        List<HistoryEnity> list = response.body().getEnities();
        if (list != null) {
            Log.d("123", list.size() + "------------rich");
            data.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(Call<HistoryListEntiy> call, Throwable t) {
        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        Call<HistoryListEntiy> call2 = call.clone();
        call2.enqueue(this);
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
