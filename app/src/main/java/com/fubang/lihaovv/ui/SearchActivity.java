package com.fubang.lihaovv.ui;


import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.adapters.HistoryAdapter;
import com.fubang.lihaovv.adapters.SearchAdapter;
import com.fubang.lihaovv.entities.HistoryEnity;
import com.fubang.lihaovv.entities.RoomEntity;
import com.fubang.lihaovv.entities.RoomListEntity;
import com.fubang.lihaovv.presenter.impl.RoomListPresenterImpl;
import com.fubang.lihaovv.view.RoomListView;
import com.socks.library.KLog;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索页面
 */
@EActivity(R.layout.activity_search)
public class SearchActivity extends BaseActivity implements RoomListView {
    //    @ViewById(R.id.search_view)
//    SearchView searchView;
    @ViewById(R.id.search_to_search)
    TextView searchText;
    @ViewById(R.id.search_list)
    ListView searchList;
    @ViewById(R.id.search_back_btn)
    ImageView searchBackBtn;
    @ViewById(R.id.search_edittext)
    EditText editText;


    private List<RoomListEntity> list = new ArrayList<>();
    private RoomListPresenterImpl presenter;

    private List<RoomListEntity> data = new ArrayList<>();
    private List<String> ips = new ArrayList<>();
    private SearchAdapter adapter;
    private boolean flag = false;
    private Context context;

    @Override
    public void before() {
        context = this;
        EventBus.getDefault().register(this);

    }

    @Override
    public void initView() {
        searchBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new SearchAdapter(data, context);
        searchList.setAdapter(adapter);
//        searchView.setOnQueryTextListener(this);
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String roomId = ((TextView) view).getText().toString();
//                searchView.setQuery(roomId,true);
                for (int i = 0; i < data.size(); i++) {
                    if (roomId.equals(data.get(i))) {
                        context.startActivity(RoomActivity_.intent(context)
                                .extra("roomIp", list.get(position).getGateway())
                                .extra("roomId", list.get(position).getRoomid())
                                .extra("roomPwd", list.get(position).getRoompwd()).get());
                    }
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String key = s.toString().trim();
                presenter = new RoomListPresenterImpl((RoomListView) context, 20, 1, 0, key);
                presenter.getRoomList();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void successRoomList(RoomEntity entity) {
        data.clear();
        data = entity.getRoomlist();
        KLog.e(data.size() + " ");
        adapter.notifylist(data);
    }

    @Override
    public void faidedRoomList() {
    }
}
