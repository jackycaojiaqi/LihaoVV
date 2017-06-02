package com.fubang.lihaovv.ui;


import android.text.TextUtils;
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
import com.fubang.lihaovv.entities.RoomEntity;
import com.fubang.lihaovv.entities.RoomListEntity;
import com.fubang.lihaovv.presenter.impl.RoomListPresenterImpl;
import com.fubang.lihaovv.view.RoomListView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索页面
 */
@EActivity(R.layout.activity_search)
public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener,RoomListView {
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
    @ViewById(R.id.search_edit_clear)
    ImageView clearImage;

    private List<RoomListEntity> list = new ArrayList<>();
    private RoomListPresenterImpl presenter;

    private List<String> data = new ArrayList<>();
    private List<String> ips = new ArrayList<>();
    private ArrayAdapter<String> adapter ;
    private boolean flag = false;
    @Override
    public void before() {
        EventBus.getDefault().register(this);
        presenter = new RoomListPresenterImpl(this,20,1,0);
    }

    @Override
    public void initView() {
        searchBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        presenter.getRoomList();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        searchList.setTextFilterEnabled(true);
        searchList.setAdapter(adapter);
//        searchView.setOnQueryTextListener(this);
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String roomId = ((TextView)view).getText().toString();
//                searchView.setQuery(roomId,true);
                for (int i = 0; i < data.size(); i++) {
                    if (roomId.equals(data.get(i))){
                        startActivity(TestActivity_.intent(SearchActivity.this) .extra("roomIp",ips.get(i)).extra("roomId",data.get(i)).get());
                    }
                }
            }
        });

        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < data.size(); i++) {
                    if (editText.getText().toString().equals(data.get(i))){
                        flag = true;
                        startActivity(TestActivity_.intent(SearchActivity.this).extra("roomIp",ips.get(i)).extra("roomId",data.get(i)).get());
                    }
                }
                if (flag==false)
                Toast.makeText(SearchActivity.this, "无此房间", Toast.LENGTH_SHORT).show();
            }
        });
        clearImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)){
            searchList.clearTextFilter();
        }else {
            searchList.setFilterText(newText);
        }
        return true;
    }

    @Override
    public void successRoomList(RoomEntity entity) {
        for (int i = 0; i < entity.getRoomlist().size(); i++) {
            data.add(entity.getRoomlist().get(i).getRoomid());
            ips.add(entity.getRoomlist().get(i).getGateway());
        }
        Log.d("123",data.size()+"");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void faidedRoomList() {
        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
    }
}
