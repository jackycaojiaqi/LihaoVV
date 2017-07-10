package com.fubang.lihaovv;

import java.io.File;

/**
 * 常量工具类
 * Created by dell on 2016/4/5.
 */
public class AppConstant {
    public static final String HOME_TYPE = "home_type";
//    public static final String[] HOME_TYPE_TITLE = {"大厅列表","热门主播","新人主播"};
    public static final String[] HOME_TYPE_TITLE = {"大厅列表"};
    public static final String[] ROOM_TYPE_TITLE = {"公聊","私聊","观众","麦序"};

    public static final String[] MY_TYPE_TITLE = {"VIP","座驾","活动","我的道具"};
//    public static final String[] BILLBOARD_TITLE = {"魅力之星","房间人气"};
    public static final String[] BILLBOARD_TITLE = {"房间人气"};
    public static final String BASE_URL = "http://120.26.54.182:888";
    public static final String BASE_MUSIC_URL = "http://120.26.127.210:9419";

    public static final String RICH_BASE= "http://lihao.nnnktv.com";
//    public static final String BASE_URL = "http://"+ StartUtil.getIpPort(App.getInstance());
//    public static final String BASE_URL = "http://115.29.11.107:9493";
//    public static final  String BASE_URL = "http://m.zzzktv.com/";
    public static final String STAR_URL= "http://www.bbbktv.com:9418/top/";
    public static final String HEAD_URL = "http://boke.nnnktv.com/dskj_admin/roompic/";
//    public static final String HEAD_URL = "http://"+StartUtil.getIpPort(App.getInstance())+"/home_dhhs/upload/user/";
    public static final String DOWNLOAD_URL = "http://61.153.104.118:9418/download/nhkh.apk";
    public static final String VER = "ver";
    public static final String OS = "os";
    public static final String TIME = "time";
    public static final String COUNT = "count";
    public static final String PAGE = "page";
    public static final String GROUP = "group";
    public static final String KEY_WORD = "ser_txt";
    //设置图片缓存地址
    public static final String IMAGE_CACHE = App_.getInstance().getCacheDir().getAbsolutePath()+"";
    public static File getCacheFile(){
        File file = new File(IMAGE_CACHE);
        return file;
    }


    public static final String GET_RTMP_URL = "http://120.26.50.117:88/rtmp_pub.php?";//李好客户端获取rtmp地址
    public static final String BASE_DOWNLOAD_MUSIC = "http://120.26.127.210:333/mp3_ge/";//音乐下载链接前缀
    public static final String BASE_DOWNLOAD_LRC = "http://120.26.127.210:333/mp3_lrc/";//音乐下载链接前缀
    public static final String MSG_GET_MP3 = "/index.php/app/get_mp3?";//获取Mp3列表
}
