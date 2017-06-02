package com.fubang.lihaovv.ui;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.adapters.GuideAdapter;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * 引导界面
 */
@EActivity(R.layout.activity_guide)
public class GuideActivity extends BaseActivity {
    @ViewById(R.id.guide_viewpager)
    ViewPager guideVp;

    private ArrayList<View> list = new ArrayList<>();
    private Drawable[] drawables;
    @Override
    public void initView() {
        super.initView();
        initDrawable();
        GuideAdapter adapter = new GuideAdapter(list,this);
        guideVp.setAdapter(adapter);
    }

    private void initDrawable() {
        //从资源中得到引导图片数据源
        TypedArray array = getResources().obtainTypedArray(R.array.guide_pic);
        drawables = new Drawable[array.length()];
        for (int i = 0; i < array.length(); i++) {
            drawables[i] = array.getDrawable(i);
        }
        array.recycle();
        for (int i = 0; i < drawables.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            ));
            imageView.setImageDrawable(drawables[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            list.add(imageView);
        }
    }
}
