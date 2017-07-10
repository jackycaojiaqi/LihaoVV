package com.fubang.lihaovv.fragment.tabgiftfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.lihaovv.R;
import com.fubang.lihaovv.adapters.GiftAdapter;
import com.fubang.lihaovv.adapters.GiftNewAdapter;
import com.fubang.lihaovv.utils.GiftUtil;
import com.socks.library.KLog;
import com.viewpagerindicator.CirclePageIndicator;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;


/**
 * Created by HongJay on 2016/8/11.
 */
public class TabFragment3 extends Fragment {
    private ArrayList<View> imageList;

    ViewPager mViewPager;
    CirclePageIndicator indicator;
    private GiftNewAdapter adapter;
    private GridView recyclerView;
    private GiftNewAdapter adapter2;
    private GridView recyclerView2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment1, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_tab_headview);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        initdata();//初始化数据
        return view;
    }

    private void initdata() {
        imageList = new ArrayList<View>();
        imageList.clear();
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.pop_recycle, null);
        View view2 = layoutInflater.inflate(R.layout.pop_recycle, null);
        recyclerView = null;
        adapter = null;
        recyclerView = (GridView) view1.findViewById(R.id.rv_gift);
        adapter = new GiftNewAdapter(GiftUtil.getGiftsGroup3Page1(), getActivity());
        recyclerView = (GridView) view1.findViewById(R.id.rv_gift);
        recyclerView.setAdapter(adapter);
        imageList.add(view1);

//        recyclerView2 = null;
//        adapter2 = null;
//        recyclerView2 = (GridView) view2.findViewById(R.id.rv_gift);
//        adapter2 = new GiftNewAdapter(GiftUtil.getGiftsGroup1Page2(), getActivity());
//        recyclerView2 = (GridView) view2.findViewById(R.id.rv_gift);
//        recyclerView2.setAdapter(adapter2);
//        imageList.add(view2);

        TabFragment3.MyPagerAdapter adapter3 = new TabFragment3.MyPagerAdapter();
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(adapter3);
        indicator.setViewPager(mViewPager);

        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(GiftUtil.getGiftsGroup3Page1().get(position),"gift_select");
            }
        });

    }


    //viewpager的adapter
    class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageList.get(position));
            return imageList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Integer.toString(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}




