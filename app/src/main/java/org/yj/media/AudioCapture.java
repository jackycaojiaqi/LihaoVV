package org.yj.media;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import java.util.LinkedList;

/**
 * Created by yj on 2017/6/3.
 */

public class AudioCapture {
    private AudioRecord record;
    private boolean m_running;
    private int bufferCap;
    private AudioCaptureCallback callback;

    private Object analysisLocker;
    private LinkedList<byte[]> analysisList;

    public AudioCapture(AudioCaptureCallback cb) {
        callback = cb;
        analysisLocker = new Object();
        analysisList = new LinkedList<byte[]>();
    }

    public void Start(int sample, int channel) {
        if(null != record) {
            return;
        }

        int channelConfig = (2==channel)?AudioFormat.CHANNEL_IN_STEREO:AudioFormat.CHANNEL_IN_MONO;
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

        bufferCap = AudioRecord.getMinBufferSize(sample, channelConfig, audioFormat);

        record = new AudioRecord(MediaRecorder.AudioSource.MIC,
                sample, channelConfig, audioFormat, bufferCap);

        //  开始录音
        m_running = true;
        record.startRecording();

        new Thread(){
            public void run() {
                recordRun(); // 启动录音功能
            }
        }.start();
        new Thread(){
            public void run() {
                analysisRun(); // 启动分析线程
            }
        }.start();
    }

    public void Stop() {
        m_running = false;
        if(null != record) {
            record.stop();
            record = null;
        }
    }

    // 开始录音
    private void recordRun() {
        AudioRecord mic = record;

        while(false != m_running) {
            byte[] buf = new byte[bufferCap];
            // 读取数据
            int len = mic.read(buf, 0, bufferCap);
            if(len < 1) {
                break;
            }

            synchronized (analysisLocker) {
                if(analysisList.size() < 1024) {
                    analysisList.addLast(buf);
                }
            }
            // callback.onAudioFrame(buf);
        }
    }

    // 启用声音处理线程
    private void analysisRun() {
        while(false != m_running) {
            byte[] buf = null;
            // 读取缓存中的数据
            synchronized (analysisLocker) {
                if(analysisList.size() > 0) {
                    buf = analysisList.pop();
                }

//                System.out.println("====================:" + analysisList.size());
            }

            // 防止cpu暴死
            if(null == buf) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                continue;
            }

            callback.onAudioFrame(buf);
        }

        analysisList.clear();
    }

    public static interface AudioCaptureCallback {
        public void onAudioFrame(byte[] data);
    }
}
