package org.yj.media;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by yj on 2017/6/10.
 */

public class RecvRtmp {
    private long m_ins;
    private String m_url;
    private RecvRtmpCallback m_callback;
    public static boolean m_running = false;

    private static native int _open(long m_ins, String url);

    private static native int _readPack(long m_ins, MediaData dst);

    private static native int _close(long m_ins);

    private static native int _release(long m_ins);

    private MediaData mediaData;
    private Object h264Locker = new Object();
    private LinkedList<RecvRtmpData> h264List = new LinkedList<RecvRtmpData>();
    private Object aacLocker = new Object();
    private LinkedList<RecvRtmpData> aacList = new LinkedList<RecvRtmpData>();

    private Bitmap m_bmp;

    protected RecvRtmp(long ins) {
        m_ins = ins;
        mediaData = new MediaData(1024 * 1024);
    }

    public void SetUrl(String url) {
        m_url = url;
    }

    public void SetCallback(RecvRtmpCallback cb) {
        m_callback = cb;
    }

    // 执行接收循环
    public void Start() {

        m_running = true;
        // 创建接收线程
        new Thread() {
            public void run() {
                runRecv();
            }
        }.start();
        // 创建播放线程
        new Thread() {
            public void run() {
                runAudioPlay();
            }
        }.start();
        // 创建视频解码线程
        new Thread() {
            public void run() {
                runVideoPlay();
            }
        }.start();
    }

    public void Stop() {
        m_running = false;
        _close(m_ins);
    }
    public void Release() {
        _release(m_ins);
    }

    // 回调事件
    private void callStart() {
        if (null != m_callback) {
            m_callback.OnRecvRtmpStart();
        }
    }

    private void callPlayVideo(Bitmap bmp) {
        if (null != m_callback) {
            m_callback.OnRecvRtmpPlayVideo(bmp);
        }
    }

    private void callPlayAudio(byte[] pcm, int sample, int channel) {
        if (null != m_callback) {
            m_callback.OnRecvRtmpPlayAudio(pcm, sample, channel);
        }
    }

    private void callStop() {
        if (null != m_callback) {
            m_callback.OnRecvRtmpStop();
        }
    }

    // 执行接收循环
    private void runRecv() {
        if (TextUtils.isEmpty(m_url)) {
            return;
        }
        int ret;
        // 打开播放网址
        ret = _open(m_ins, m_url);
        if (0 != ret) {
            callStop();
            return;
        }

        callStart();
        // 开始接收数据
        try {
            while (false != m_running) {
                // 读取一帧数据
                ret = _readPack(m_ins, mediaData);
                if (0 != ret) {
                    break;
                }

                boolean needSleep = false;
                RecvRtmpData data = new RecvRtmpData();
                data.Timestamp = mediaData.getTimestamp();
                data.Data = Arrays.copyOfRange(mediaData.getData(), 0, mediaData.getLength());
//                System.out.println("================:::"+mediaData.getFlag()+":::" + mediaData.getTimestamp());

                // 分析是什么样的数据
                if (1 == (mediaData.getFlag() & 0xff)) {
                    // 音频
                    synchronized (aacLocker) {
                        aacList.addLast(data);
                        if (aacList.size() >= 1024) {
                            needSleep = true;
                        }
                    }
                } else {
                    // 视频
                    synchronized (h264Locker) {
                        h264List.addLast(data);
                        if (h264List.size() >= 1024) {
                            needSleep = true;
                        }
                    }
                }

                // 判断是否接收过快
                if (needSleep) {
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            callStop();
        }
    }

    // 开启视频解码
    private void runVideoPlay() {
        DecodeH264 dec = Media.CreateDecodeH264();
        long startTime = 0;
        long startTS = 0;

        try {
            // 打开解码器
            dec.Open(2); // RGB565格式
            // 开始解码
            while (false != m_running) {
                RecvRtmpData data = null;
                // 从队列中取出数据
                synchronized (h264Locker) {
                    if (h264List.size() > 0) {
                        data = h264List.pop();
                    }
                }

                // 暂停等待
                if (null == data) {
                    Thread.sleep(10);
                    continue;
                }

                MediaData rgb = dec.Decode(data.Data);
                if (null == rgb) {
                    continue;
                }

                // 构建图片
                if (null == m_bmp ||
                        (rgb.getVideoWidth() != m_bmp.getWidth() &&
                                rgb.getVideoHeight() != m_bmp.getHeight())) {
                    m_bmp = Bitmap.createBitmap(rgb.getVideoWidth(), rgb.getVideoHeight(),
                            Bitmap.Config.RGB_565);
                }

                ByteBuffer buffer = ByteBuffer.wrap(rgb.getData(), 0, rgb.getLength());
                m_bmp.copyPixelsFromBuffer(buffer);
                // 判断是否需要暂停
                long now = System.currentTimeMillis();

                if (0 == startTime) {
                    startTime = now;
                    startTS = data.Timestamp;
                }


                long ts = (data.Timestamp - startTS) - (now - startTime);
                if (ts > 2) {
                    Thread.sleep(ts - 1);
                }

                callPlayVideo(m_bmp);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            dec.Release();
        }
    }

    // 执行播放线程
    private void runAudioPlay() {
        DecodeAAC dec = Media.CreateDecodeAAC();
        long startTime = 0;
        long startTS = 0;

        try {
            // 打开解码器
            dec.Open();
            // 开始解码
            while (false != m_running) {
                RecvRtmpData data = null;
                // 从队列中取出数据
                synchronized (aacLocker) {
                    if (aacList.size() > 0) {
                        data = aacList.pop();
                    }
                }

                // 暂停等待
                if (null == data) {
                    Thread.sleep(10);
                    continue;
                }

                // 在这里处理丢包策略，凡是播放时间大于采集时间，就需要丢弃
                long now = System.currentTimeMillis();
                // 判断本帧是否需要丢掉
                if (0 == startTime) {
                    startTime = now;
                    startTS = data.Timestamp;
                }

                long ts = (data.Timestamp - startTS) - (now - startTime);
                if (ts < -23) {
                    continue; // 丢掉播放慢的包
                }

                // 解码pcm
                MediaData md = dec.Decode(data.Data);
                if (null == md) {
                    continue;
                }

                // 调用底成播放
                callPlayAudio(Arrays.copyOfRange(md.getData(), 0, md.getLength()),
                        md.getAudioSample(), md.getAudioChannel());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            dec.Release();
        }
    }
}
