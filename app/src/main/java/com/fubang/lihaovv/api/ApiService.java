package com.fubang.lihaovv.api;


import com.fubang.lihaovv.entities.ActorEntity;
import com.fubang.lihaovv.entities.AnchorListEntity;
import com.fubang.lihaovv.entities.FavoriteEntity;
import com.fubang.lihaovv.entities.FollowEntity;
import com.fubang.lihaovv.entities.GiftTopListEntity;
import com.fubang.lihaovv.entities.HistoryListEntiy;
import com.fubang.lihaovv.entities.HomeEntity;
import com.fubang.lihaovv.entities.HomeHeadPicEntity;
import com.fubang.lihaovv.entities.HomeIconEntity;
import com.fubang.lihaovv.entities.HotAnchorListEntity;
import com.fubang.lihaovv.entities.NewAnchorListEntity;
import com.fubang.lihaovv.entities.RichListEntity;
import com.fubang.lihaovv.entities.RoomEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by dell on 2016/4/5.
 */
public interface ApiService {
    @POST("touch/apps.banner?")
    Call<HomeHeadPicEntity> getHomeHeadPicEntity(@QueryMap Map<String, String> map);

    @POST("static/apps.recommend/game.json?")
    Call<HomeIconEntity> getHomeIconEntity(@QueryMap Map<String, String> map);

    @POST("static/live.index/recommend-apps.json?")
    Call<HomeEntity> getHomeEntity(@QueryMap Map<String, String> map);

    @GET("/index.php/app/roomlist?")
    Call<RoomEntity> getRoomEntity(@QueryMap Map<String, String> map);

    @GET("/index.php/Rank/richTop?")
    Call<RichListEntity> getRichEntity(@Query("type") int type);

    @GET("/index.php/Rank/giftTopS?")
    Call<GiftTopListEntity> getGiftTopEntity(@Query("type") int type);

    @GET("/index.php/Rank/anchorTopS?")
    Call<AnchorListEntity> getAnchorEntity(@Query("type") int type);

    @GET("/index.php/Room/getHotAnchor?")
    Call<HotAnchorListEntity> getHotAnchorEntity(@Query("type") int type);

    @GET("/index.php/Room/getNewAnchor?")
    Call<NewAnchorListEntity> getNewAnchorListEntity(@Query("type") int type);

    @GET("/index.php/App/room_fav?")
    Call<FavoriteEntity> getFavoriteEntity(@Query("nuserid") int type);

    @GET("/index.php/App/user_follow?")
    Call<ActorEntity> getActorEntity(@Query("nuserid") int type);

    @GET("/index.php/app/get_history?")
    Call<HistoryListEntiy> getHistoryEnity(@Query("nuserid") int nuserid);

    @GET("/index.php/App/room_fav?")
    Call<FollowEntity> getFollowEntity(@Query("nuserid") int nuserid);

//    @GET("/index.php/app/get_meili")
//    Call
}
