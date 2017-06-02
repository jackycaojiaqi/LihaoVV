package com.fubang.lihaovv.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.fubang.lihaovv.ui.LoginActivity_;

import java.util.List;

/**
 * 引导界面viewpager的适配器
 * Created by dell on 2016/4/5.
 */
public class GuideAdapter extends PagerAdapter {
    private List<View> list;
    private Context context;

    public GuideAdapter(List<View> list, Context context) {
        this.list = list;
        this.context = context;
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
    public Object instantiateItem(final ViewGroup container, int position) {
        container.addView(list.get(position));
        list.get(list.size()-1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(LoginActivity_.intent(context).get());
            }
        });
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof View)
            container.removeView((View) object);
    }
}
