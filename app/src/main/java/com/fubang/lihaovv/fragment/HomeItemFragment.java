package com.fubang.lihaovv.fragment;


import android.support.v4.app.Fragment;
import android.widget.GridView;
import android.widget.Toast;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.AppConstant;
import com.fubang.lihaovv.adapters.HomeRoomAdapter;
import com.fubang.lihaovv.entities.RoomEntity;
import com.fubang.lihaovv.entities.RoomListEntity;
import com.fubang.lihaovv.presenter.impl.RoomListPresenterImpl;
import com.fubang.lihaovv.ui.RoomActivity_;
import com.fubang.lihaovv.utils.DbUtil;
import com.fubang.lihaovv.utils.ToastUtil;
import com.fubang.lihaovv.view.RoomListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_home_item)
public class HomeItemFragment extends BaseFragment implements RoomListView, PullToRefreshBase.OnRefreshListener2 {
    @ViewById(R.id.home_ptlist)
    PullToRefreshGridView ptRefreshGv;
    private String type;
    private List<RoomListEntity> list = new ArrayList<>();
    private HomeRoomAdapter adapter;
    private RoomListPresenterImpl presenter;
    private int count = 40;
    private int page = 1;
    private int group = 0;

    //    private View headView;
//    private ConvenientBanner<String> chooseView;
//    private Call<BannerEntity> callFirst;
//    private List<String> listFirst = new ArrayList<>();
    @Override
    public void before() {
        EventBus.getDefault().register(this);
        type = getArguments().getString(AppConstant.HOME_TYPE);
        presenter = new RoomListPresenterImpl(this, count, page, group, "");
    }

    @Override
    public void initView() {
//        listFirst.add("banner1");
//        listFirst.add("banner1");
//        listFirst.add("banner1");
        ptRefreshGv.setMode(PullToRefreshBase.Mode.BOTH);
        ptRefreshGv.setOnRefreshListener(this);
//        广告头
//        initView1();
        GridView gridView = new GridView(getContext());
        gridView.getAdapter();
    }

//    private void initView1() {
//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        headView = inflater.inflate(R.layout.choose_head,null);
//        chooseView = (ConvenientBanner) headView.findViewById(R.id.choose_frag_viewpager);
//
//        chooseRecycler = (RecyclerView) headView.findViewById(R.id.choose_frag_recycler);
//    }

    @Override
    public void initData() {
//        initData1();
        List<RoomListEntity> listEntities = DbUtil.getSession()
                .getRoomListEntityDao()
                .queryBuilder()
                .limit(40)
                .list();
        list.addAll(listEntities);
        adapter = new HomeRoomAdapter(list, getContext());
        EventBus.getDefault().post(list, "roomList");
//        ptRefreshGv.addHeaderView(headView);
        ptRefreshGv.setAdapter(adapter);
        presenter.getRoomList();

    }

    @Override
    public void successRoomList(RoomEntity entity) {
        ptRefreshGv.onRefreshComplete();
        if (page == 1) {
            list.clear();
        }
        List<RoomListEntity> roomListEntities = entity.getRoomlist();
        DbUtil.getSession().getRoomListEntityDao().insertOrReplaceInTx(roomListEntities);
        EventBus.getDefault().post(entity, "successRoomEntity");
        list.addAll(roomListEntities);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void faidedRoomList() {
        Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page = 1;
        new RoomListPresenterImpl(this, count, page, group, "").getRoomList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page = 2;
        new RoomListPresenterImpl(this, count, page, group, "").getRoomList();
        ptRefreshGv.onRefreshComplete();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private boolean has_scane_room = false;

    //网络异常，断开房间
    @Subscriber(tag = "scane_room_id")
    public void onScaneRoomId(String id) {
        for (RoomListEntity roomListEntity : list) {
            if (roomListEntity.getRoomid().equals(id)) {//如果扫描出来的roomid匹配 则进入房间
                getActivity().startActivity(RoomActivity_.intent(getActivity())
                        .extra("roomIp", roomListEntity.getGateway())
                        .extra("roomId", roomListEntity.getRoomid())
                        .extra("roomPwd", roomListEntity.getRoompwd()).get());
                has_scane_room = true;
            }
        }
        if (!has_scane_room) {
            ToastUtil.show(getActivity(), "找不到二维码匹配的房间");
        }
        has_scane_room = false;
    }
//    private void initData1() {
//        if (callFirst != null)
//            callFirst.cancel();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(UrlConstants.BANNERS_FIRST_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        callFirst = retrofit.create(BannerService.class).getBannerList();
//        callFirst.enqueue(new Callback<BannerEntity>() {
//            @Override
//            public void onResponse(Call<BannerEntity> call, Response<BannerEntity> response) {
//                BannerEntity entity = response.body();
//                List<Banners> banners = entity.getBanners().getBanners();
//                listFirst.clear();
//                for (int i = 0; i < banners.size(); i++) {
//                    listFirst.add(banners.get(i).getImage_url());
//                }
//                chooseView.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
//                    @Override
//                    public LocalImageHolderView createHolder() {
//                        return new LocalImageHolderView();
//                    }
//                },listFirst).setPageIndicator(new int[]{R.mipmap.ic_page_indicator,R.mipmap.ic_page_indicator_focused})
//                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                ;
//
//                chooseView.startTurning(2000);
//                chooseView.setImageUris(listFirst);
//                chooseView.setEffect(EffectConstants.CUBE_EFFECT);//更改图片切换的动画效果
//                pageAdapter.notifyDataSetChanged();
//            }

//            @Override
//            public void onFailure(Call<BannerEntity> call, Throwable t) {
//                t.printStackTrace();
////                Toast.makeText(getActivity(), "网络问题", Toast.LENGTH_SHORT).show();
//            }
//        });

//    }
//    public class LocalImageHolderView implements Holder<String> {
//        private SimpleDraweeView simpleDraweeView;
//        private ImageView imageView;
//        @Override
//        public View createView(Context context) {
//            imageView = new ImageView(context);
//            simpleDraweeView = new SimpleDraweeView(context);
//            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
//            GenericDraweeHierarchy hierarchy = builder.build();
//            hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
//            simpleDraweeView.setHierarchy(hierarchy);
//            return imageView;
//        }
//
//        @Override
//        public void UpdateUI(Context context, int position, String data) {
//            simpleDraweeView.setImageURI(Uri.parse(data));
//            imageView.setImageResource(R.mipmap.banner1);
//        }
//    }
}
