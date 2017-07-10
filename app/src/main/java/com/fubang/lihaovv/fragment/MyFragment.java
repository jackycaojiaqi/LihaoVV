package com.fubang.lihaovv.fragment;


import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fubang.lihaovv.R;
import com.fubang.lihaovv.ui.HistoryActivity_;
import com.fubang.lihaovv.ui.MessageActivity_;
import com.fubang.lihaovv.ui.PersonActivity_;
import com.fubang.lihaovv.ui.PrivilegeActivity_;
import com.fubang.lihaovv.ui.RechargeActivity_;
import com.fubang.lihaovv.ui.SearchActivity_;
import com.fubang.lihaovv.ui.ServiceActivity_;
import com.fubang.lihaovv.ui.SettingActivity_;
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.adapters.HomeTitleAdapter;
import com.fubang.lihaovv.api.ApiService;
import com.fubang.lihaovv.entities.HistoryEnity;
import com.fubang.lihaovv.entities.HistoryListEntiy;
import com.fubang.lihaovv.entities.UserEntity;
import com.fubang.lihaovv.utils.StringUtil;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
@EFragment(R.layout.fragment_my)
public class MyFragment extends BaseFragment implements Callback<HistoryListEntiy> {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    //    @ViewById(R.id.my_viewpage)
//    ViewPager viewPager;
//    @ViewById(R.id.my_tablayout)
//    TabLayout tabLayout;
    @ViewById(R.id.my_user_headicon)
    SimpleDraweeView userIcon;
    @ViewById(R.id.fm_mine_search)
    ImageView fmMineSearch;

    @ViewById(R.id.my_username)
    TextView userName;
    @ViewById(R.id.my_recharge)
    LinearLayout myRecharge;
    @ViewById(R.id.my_message)
    LinearLayout myMessage;
    @ViewById(R.id.my_privileges)
    LinearLayout myPrivileges;
    @ViewById(R.id.my_setting)
    LinearLayout mySetting;
    @ViewById(R.id.my_service)
    LinearLayout myService;
    @ViewById(R.id.my_kb)
    TextView kbTv;
    @ViewById(R.id.my_vip)
    TextView vipTv;
    @ViewById(R.id.my_fragment_history)
    TextView historyTv;
    @ViewById(R.id.my_history_linear)
    LinearLayout historyLinear;

    private Call<HistoryListEntiy> call;

    @Override
    public void before() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        for (int i = 0; i < AppConstant.MY_TYPE_TITLE.length; i++) {
            titles.add(AppConstant.MY_TYPE_TITLE[i]);
        }
        fragments.add(MyItemFragment_.builder().arg(AppConstant.HOME_TYPE, titles.get(0)).build());
        fragments.add(MyItemFragment_.builder().arg(AppConstant.HOME_TYPE, titles.get(1)).build());
        fragments.add(MyItemFragment_.builder().arg(AppConstant.HOME_TYPE, titles.get(2)).build());
        myRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RechargeActivity_.intent(getContext()).get());
            }
        });
        myMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MessageActivity_.intent(getContext()).get());
            }
        });
        myPrivileges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PrivilegeActivity_.intent(getContext()).get());
            }
        });
        mySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SettingActivity_.intent(getContext()).get());
            }
        });
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PersonActivity_.intent(getContext()).get());
            }
        });
        myService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ServiceActivity_.intent(getContext()).get());
            }
        });
        historyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(HistoryActivity_.intent(getContext()).get());
            }
        });
        fmMineSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchActivity_.intent(getContext()).get());
            }
        });
    }

    @Override
    public void initData() {
        Log.d("123", StartUtil.getUserIcon(getContext()).length() + "length");
        if (StartUtil.getUserIcon(getContext()).length() > 30) {
            userIcon.setImageURI(Uri.parse(StartUtil.getUserIcon(getContext())));
        } else if (StartUtil.getUserIcon(getContext()).length() > 10) {
            String url = AppConstant.HEAD_URL + StartUtil.getUserIcon(getContext());
            userIcon.setImageURI(Uri.parse(url));
            Log.d("123", "head url-------" + url);
        } else {
            String[] pichead = StartUtil.getUserIcon(getContext()).split("\\.");
//        userIcon.setImageURI(Uri.parse(StartUtil.getUserIcon(getContext())));
            Log.d("123", pichead[0]);
            userIcon.setImageResource(getResourceByReflect(pichead[0]));
        }
        userName.setText(StartUtil.getUserName(getContext()));
        HomeTitleAdapter adapter = new HomeTitleAdapter(getChildFragmentManager(), fragments, titles);
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        kbTv.setText(StartUtil.getUserKbi(getContext()) + " K币");
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
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                return entity;
                            }
                        };
                    }
                }).build();
        ApiService service = retrofit.create(ApiService.class);
        if (!StringUtil.isEmptyandnull(StartUtil.getUserId(getContext()))) {
            call = service.getHistoryEnity(Integer.parseInt(StartUtil.getUserId(getContext())));
            call.enqueue(this);
        }

    }

    /**
     * 获取图片名称获取图片的资源id的方法
     *
     * @param imageName
     * @return
     */
    public int getResourceByReflect(String imageName) {
        Class drawable = R.drawable.class;
        Field field = null;
        int r_id;
        try {
            field = drawable.getField(imageName);
            r_id = field.getInt(field.getName());
        } catch (Exception e) {
            r_id = R.drawable.head0;
            Log.e("ERROR", "PICTURE NOT　FOUND！");
        }
        return r_id;
    }

    @Subscriber(tag = "UserInfo")
    public void getUserInfo(UserEntity userEntity) {
        Log.d("123", userEntity.getUserIcon() + userEntity.getUserName());
        userIcon.setImageURI(Uri.parse(userEntity.getUserIcon()));
        userName.setText(userEntity.getUserName());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!StringUtil.isEmptyandnull(StartUtil.getUserId(getContext()))) {
            Call<HistoryListEntiy> call2 = call.clone();
            call2.enqueue(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResponse(Call<HistoryListEntiy> call, Response<HistoryListEntiy> response) {
        call.cancel();
        List<HistoryEnity> list = response.body().getEnities();
        if (list != null) {
            Log.d("123", list.size() + "------------rich");
            historyTv.setText(list.size() + "");
        }
    }

    @Override
    public void onFailure(Call<HistoryListEntiy> call, Throwable t) {

    }
}
