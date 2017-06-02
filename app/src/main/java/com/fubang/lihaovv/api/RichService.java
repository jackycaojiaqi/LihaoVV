package com.fubang.lihaovv.api;


import com.fubang.lihaovv.entities.AnchorListEntity;
import com.fubang.lihaovv.entities.GiftTopListEntity;
import com.fubang.lihaovv.entities.RichListEntity;
import com.fubang.lihaovv.entities.RoomSumListEnity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * <p/>
 * 项目名称：MyApplication
 * 类描述：
 * 创建人：dell
 * 创建时间：2016-06-27 11:17
 * 修改人：dell
 * 修改时间：2016-06-27 11:17
 * 修改备注：
 */
public interface RichService {
    @GET("/top/newtop_app.php?fun=getXiaofeiTop")
    Call<RichListEntity> getRichEntity(@Query("from") String from, @Query("limit") int limit);

    @GET("/top/newtop_app.php?fun=getGiftStar")
    Call<GiftTopListEntity> getGiftTopEntity(@Query("from") String from, @Query("limit") int limit);

    @GET("/top/newtop_app.php?fun=jifen_yiren")
    Call<AnchorListEntity> getAnchorEntity(@Query("from") String from, @Query("limit") int limit);

    @GET("/top/newtop_app.php?fun=getRoomSum")
    Call<RoomSumListEnity> getRoomSumEntity(@Query("from") String from, @Query("limit") int limit);
}
