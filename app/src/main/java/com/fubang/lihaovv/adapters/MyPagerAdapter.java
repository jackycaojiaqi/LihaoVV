package com.fubang.lihaovv.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.fubang.lihaovv.entities.GiftNewEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacky on 2017/6/30.
 */
public class MyPagerAdapter extends PagerAdapter {
    private List<View> list = new ArrayList<>();
    public MyPagerAdapter(List<View> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
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
