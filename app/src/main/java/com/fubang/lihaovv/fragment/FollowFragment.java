package com.fubang.lihaovv.fragment;


import android.support.v4.app.Fragment;
import android.widget.GridView;
import android.widget.Toast;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.adapters.FollowAdapter;
import com.fubang.lihaovv.entities.FollowEntity;
import com.fubang.lihaovv.entities.FollowListEnitty;
import com.fubang.lihaovv.presenter.impl.FollowPresenterImpl;
import com.fubang.lihaovv.view.FollowView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注界面
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_follow)
public class FollowFragment extends BaseFragment implements FollowView,PullToRefreshBase.OnRefreshListener {
    @ViewById(R.id.follow_ptlist)
    PullToRefreshGridView ptRefreshGv;
    private List<FollowListEnitty> list = new ArrayList<>();
    private FollowAdapter adapter;
    private FollowPresenterImpl presenter;

    @Override
    public void before() {
        EventBus.getDefault().register(this);
        presenter = new FollowPresenterImpl(this,Integer.parseInt(StartUtil.getUserId(getContext())));
    }

    @Override
    public void initView() {
        ptRefreshGv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ptRefreshGv.setOnRefreshListener(this);
        GridView gridView = new GridView(getContext());
        gridView.getAdapter();

    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        new FollowPresenterImpl(this,Integer.parseInt(StartUtil.getUserId(getContext()))).getFollowList();
    }



    @Override
    public void initData() {
        adapter = new FollowAdapter(list,getContext());
        ptRefreshGv.setAdapter(adapter);
        presenter.getFollowList();
    }

    @Override
    public void successFollowList(FollowEntity enitty) {
        ptRefreshGv.onRefreshComplete();
        list.clear();
        if (enitty .getDatalist() != null) {
            List<FollowListEnitty> enitties = enitty.getDatalist();
            EventBus.getDefault().post(enitties.size(),"followNumber");
            list.addAll(enitties);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void failFollowList() {
        ptRefreshGv.onRefreshComplete();
        Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
