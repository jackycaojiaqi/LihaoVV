package com.fubang.lihaovv.ui;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;


import com.fubang.lihaovv.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.yj.media.AudioCapture;
import org.yj.media.EncodeAAC;
import org.yj.media.EncodeH264;
import org.yj.media.H264Data;
import org.yj.media.Media;
import org.yj.media.MediaData;
import org.yj.media.PushRtmp;
import okhttp3.Call;

public class PushrtmpTestActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PreviewCallback, AudioCapture.AudioCaptureCallback {
    private SurfaceView surfaceView;
    private Camera camera;
    private long lastCapture;

    private int vWidth = 352;
    private int vHeight = 288;
    private byte[] convertCache = new byte[vWidth * vHeight * 3 / 2];

    private EncodeH264 h264;
    private AudioCapture audio;
    private EncodeAAC aac;
    private PushRtmp rtmp;

    FileOutputStream fosH264;
    FileOutputStream fosAAC;

    // private String rtmpUrl = "rtmp://video-center.alivecdn.com/test/yj_lt_20170605?auth_key=1496623416-0-0-54fe34a9ab80198d807eda4ac0ff18e8";
    private String rtmpPath = "/test/yj_lt_20170605";
    private String rtmpKey = "1496623416-0-0-54fe34a9ab80198d807eda4ac0ff18e8";
    private String rtmpUrl = "rtmp://video-center.alivecdn.com" + rtmpPath + "?vhost=live1.nnnktv.com&auth_key=" + rtmpKey;
    //    private String rtmpUrl = "rtmp://pili-publish.fbyxsp.com/wanghong/wh_103921876_103921876?e=1496658188&token=rgNGguFNzZb47-3LXCxtm4H5iMjbMG-5dhhOR512:g8ROoNqtnBemwMPfmwdfsFf2HOU=";
    private String url_pub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushrtmp);

        initView();
        initEvent();

        // 开启视频采集
        h264 = Media.CreateEncodeH264();
        int ret = h264.Open(vHeight, vWidth);
        System.out.println("EncodeH264::Open()" + ret);

        // 开启声音采集
        audio = new AudioCapture(this);
        audio.Start(44100, 2);

        aac = Media.CreateEncodeAAC();
        ret = aac.Open(44100, 2);
        System.out.println("EncodeAAC::Open()" + ret);

        // 保存到sd card
        File sdCardDir = Environment.getExternalStorageDirectory();//获取SDCard目录
        File sdFile = new File(sdCardDir, "test.h264");
        File sdAAC = new File(sdCardDir, "test.aac");
        File sdFlv = new File(sdCardDir, "test.flv");

        System.out.println("===========:rtmp:" + rtmpUrl);
        rtmp = Media.CreatePushRtmp();
//        rtmp.Open(rtmpUrl, vHeight, vWidth, 44100, 2);
        String url = "http://121.41.18.41:88/rtmp_pub.php?streamKey=wh_103921876_103921876";
        //必须调用初始化
        OkGo.get(url)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, okhttp3.Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            // 启动推送线程
                            url_pub = object.getString("publishUrl");
                            rtmp.Open(url_pub, vHeight, vWidth, 44100, 2);
                            Log.e("url_pub", url_pub);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        try {
            fosH264 = new FileOutputStream(sdFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            fosAAC = new FileOutputStream(sdAAC);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        h264.Release();
        audio.Stop();
        aac.Release();
        rtmp.Release();
    }

    private void initEvent() {
        surfaceView.getHolder().addCallback(this);
    }

    private void initView() {
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        TextView change = (TextView) findViewById(R.id.tv_change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera = Camera.open(2);
            }
        });
    }

    // surface view callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        if(1 == 1) {
//            return;
//        }

        try {
            camera = Camera.open(1);
            // 设置预览界面
            camera.setPreviewDisplay(holder);
            // 设置参数
            Camera.Parameters param = camera.getParameters();

            camera.setDisplayOrientation(90);
            //param.setRotation(90);
            param.setPreviewFormat(ImageFormat.NV21);
            param.setPreviewSize(vWidth, vHeight);

            camera.setParameters(param);
            camera.startPreview();
            camera.setPreviewCallback(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    // 初始数据回调
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        // 判断时间是否超过80毫秒，帧率为12帧
        long curCapture = System.currentTimeMillis();
        if ((curCapture - lastCapture) < 80) {
            return;
        }

        lastCapture = curCapture;
        // 旋转90度
        Media.NV21Rotate90(data, vWidth, vHeight, convertCache);
        // 编码转换
        Media.NV21toYUV420(data, vHeight, vWidth, convertCache);
        // 执行编码功能
        H264Data d = h264.Encode(data);

        if (null != d && d.Length > 0) {
            // 测试时保存到文件
//            if(null != fosH264) {
//                try {
//                    fosH264.write(d.H264, 0, d.Length);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println("====================: " + data.length + ";" + (lastCapture / 1000) + ":" + d.Length);

            // 发送出去
            rtmp.SendH264(d.H264, d.Length, d.Flags);

        } else {
            System.out.println("xxxxxxxxxxxxxxxxxxxx: " + data.length + ";" + (lastCapture / 1000) + ":" + data.length);
        }

    }

    // 声音数据回调
    @Override
    public void onAudioFrame(byte[] data) {
        for (int i = 0; i < data.length; i += 4096) {
            int end = i + 4096;
            if (end > data.length) {
                end = data.length;
            }

            byte[] buf = Arrays.copyOfRange(data, i, end);
            System.out.println("=====:" + i + ":" + buf.length);

            MediaData md = aac.Encode(Arrays.copyOfRange(data, i, end));

            if (null == md) {
                System.out.println(">>>:" + i + "," + end);
                return;
            }

//            // 测试时保存到文件
//            try {
//                fosAAC.write(md.getData(), 0, md.getLength());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            // System.out.println("================:" + data.length);

            // 发送出去
            rtmp.SendAAC(md.getData(), md.getLength());
        }
    }
}
