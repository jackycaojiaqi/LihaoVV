package com.fubang.lihaovv;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.fubang.lihaovv.utils.DbUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import org.androidannotations.annotations.EApplication;

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
        OkGo.getInstance().init(this)                       //必须调用初始化
                .setOkHttpClient(builder.build());
    }
}
