package com.fubang.lihaovv.fragment;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.fubang.lihaovv.R;
import com.fubang.lihaovv.ui.HistoryActivity_;
import com.fubang.lihaovv.ui.SearchActivity_;
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.adapters.HomeTitleAdapter;
import com.fubang.lihaovv.utils.ToastUtil;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


import io.github.xudaojie.qrcodelib.CaptureActivity;

import static android.app.Activity.RESULT_OK;

/**
 * 首页
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    @ViewById(R.id.home_viewpager)
    ViewPager viewPager;
    @ViewById(R.id.home_tablayout)
    TabLayout tabLayout;
    @ViewById(R.id.main_head_icon)
    ImageView iconImage;
    @ViewById(R.id.main_head_scanner)
    ImageView scannerImage;
    @ViewById(R.id.main_head_search)
    FrameLayout searchImage;
    @ViewById(R.id.main_head_history)
    ImageView historyImage;
    @ViewById(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    private ArrayList<Integer> localImages = new ArrayList<>();
    private int REQUEST_QR_CODE = 0x121;
    @Override
    public void initView() {
        localImages.add(getResId("banner1",R.mipmap.class));
        localImages.add(getResId("banner1",R.mipmap.class));
        localImages.add(getResId("banner1",R.mipmap.class));
//        localImages.add("http://120.26.86.83:9419/home_qqyl/Tpl/default/Room/ad/1.jpg");
//        localImages.add("http://120.26.86.83:9419/home_qqyl/Tpl/default/Room/ad/2.jpg");
//        localImages.add("http://120.26.86.83:9419/home_qqyl/Tpl/default/Room/ad/3.jpg");
//        localImages.add("http://120.26.86.83:9419/home_qqyl/Tpl/default/Room/ad/4.jpg");
//        localImages.add("http://120.26.86.83:9419/home_qqyl/Tpl/default/Room/ad/5.jpg");

        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator,R.mipmap.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        convenientBanner.startTurning(2000);
        for (int i = 0; i < AppConstant.HOME_TYPE_TITLE.length; i++) {
            titles.add(AppConstant.HOME_TYPE_TITLE[i]);
        }
        fragments.add(HomeItemFragment_.builder().arg(AppConstant.HOME_TYPE,titles.get(0)).build());
//        fragments.add(HotFragment_.builder().arg(AppConstant.HOME_TYPE,titles.get(0)).build());
//        fragments.add(NewFragment_.builder().arg(AppConstant.HOME_TYPE,titles.get(1)).build());
        scannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //google官方扫描二维码
//                new IntentIntegrator(getActivity()).initiateScan();
                //第三方的扫描
                Intent i = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(i, REQUEST_QR_CODE);
            }
        });
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchActivity_.intent(getContext()).get());
            }
        });
        historyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(HistoryActivity_.intent(getContext()).get());
            }
        });
    }


    @Override
    public void initData() {
        HomeTitleAdapter adapter = new HomeTitleAdapter(getChildFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setVisibility(View.GONE);
    }


    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
//            GenericDraweeHierarchyBuilder builder =
//                    new GenericDraweeHierarchyBuilder(getResources());
//            GenericDraweeHierarchy hierarchy = builder
//                    .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
//                    .setPlaceholderImage(getContext().getResources().getDrawable(R.mipmap.ktv_background))
//                    .build();
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            imageView.setHierarchy(hierarchy);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
//            DraweeController controller = Fresco.newDraweeControllerBuilder()
//                    .setUri(Uri.parse(data))
//                    .setAutoPlayAnimations(true)
//                    .build();
//            imageView.setController(controller);
        }
    }
    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == REQUEST_QR_CODE
                && data != null) {
            String result = data.getStringExtra("result");
            EventBus.getDefault().post(result,"scane_room_id");
        }
    }
}
