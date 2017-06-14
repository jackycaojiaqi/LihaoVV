package com.fubang.lihaovv;

import android.app.Application;

import com.duanqu.qupai.jni.ApplicationGlue;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.fubang.lihaovv.utils.DbUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import org.androidannotations.annotations.EApplication;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.sharesdk.framework.ShareSDK;
import okhttp3.OkHttpClient;

/**
 * 娶妻娶德不娶色，嫁人嫁心不嫁财，
 * 交友交心不交利、当面责骂那是友，
 * 背后乱叫那是狗，真正的好朋友，
 * 互损不会翻脸，疏远不会猜疑，
 * 出钱不会计较，地位不分高低，
 * 成功无需巴结，失败不会离去。
 * 奋斗的时候搭把手，迷茫的时候拉把手，
 * 开心的时候干杯酒，难过的日子一起走。
 * Created by dell on 2016/4/5.
 */
@EApplication
public class App extends Application {
//    private AVModuleMgr mgr ;
    private static volatile App instance = null;
//
//    // private constructor suppresses
//    private App(){
//        mgr = AVModuleMgr.getInstance();
//    }
//
    public static App getInstance() {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (App.class) {
                if (instance == null) {
                    instance = new App();
                }
            }
        }

        return instance;
    }

    /**
     * 初始化
     */
    @Override
    public void onCreate() {
        super.onCreate();
        System.loadLibrary("gnustl_shared");
//        System.loadLibrary("ijkffmpeg");//目前使用微博的ijkffmpeg会出现1K再换wifi不重连的情况
        System.loadLibrary("qupai-media-thirdparty");
//        System.loadLibrary("alivc-media-jni");
        System.loadLibrary("qupai-media-jni");
        ApplicationGlue.initialize(this);
//        setMgr(new AVModuleMgr());
//        Log.d("123",mgr+"------mgr");
        //初始化Fresco
//        FrescoHelper.getInstance().init(this);


        Fresco.initialize(this);
        //初始化数据库类
        DbUtil.init(this);
        //初始化ShareSDK
        ShareSDK.initSDK(this);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        //使用内存保持cookie，app退出后，cookie消失
        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
        OkGo.getInstance().init(this)
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)//全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .setOkHttpClient(builder.build());
    }
}
