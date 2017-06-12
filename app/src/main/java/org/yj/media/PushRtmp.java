package org.yj.media;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by yj on 2017/6/4.
 */

public class PushRtmp {
    private long m_ins;
    private long m_start;
    private boolean m_running;
    private String m_path;
    private int m_vWidth;
    private int m_vHeight;
    private int m_aSample;
    private int m_aChannel;
    private int m_aBit;
    private PushRtmpCallback m_callback;

    private LinkedList<Data> list;
    private Object locker;
    private int lastAACTime = -1;

    private static native int _open(long ins, String path, int vWidth, int vHeight, int aSample, int aChannel, int aBit);
    private static native int _send(long ins, int type, byte[] data, int flag, int timespec);
    private static native int _close(long ins);
    private static native int _release(long ins);

    protected PushRtmp(long ins) {
        m_ins = ins;
        locker = new Object();
        list = new LinkedList<Data>();
        m_start = System.currentTimeMillis();
    }

    public void SetCallback(PushRtmpCallback cb) {
        m_callback = cb;
    }

    public int Open(String path, int vWidth, int vHeight, int aSample, int aChannel) {
        synchronized (locker) {
            // 判断是否在运行
            if(false != m_running) {
                return 0;
            }

            // 保存路径
            m_path = path;
            m_vWidth = vWidth;
            m_vHeight = vHeight;
            m_aSample = aSample;
            m_aChannel = aChannel;
            m_aBit = 16;
            // 设置发送标志
            m_running = true;
        }

        new Thread() {
            public void run() {
                System.out.println("===开始"+m_path+"数据发送");
                sendRun();
                list.clear();
                System.out.println("===退出"+m_path+"数据发送");
            }
        }.start();

        return 0;
    }

    public void SendYUV(byte [] data, int len) {

    }

    public void SendPCM(byte [] data, int len) {

    }

    public int SendH264(byte [] data, int len, int flag) {
        Data d = new Data();
        int h264Time = (int)(System.currentTimeMillis() - m_start);
        // 防止与声音时间同步
        if((Math.abs(h264Time - lastAACTime) % 23) == 0) {
            h264Time += 1;
        }

        d.Data = Arrays.copyOf(data, len);
        d.Flag = flag;
        d.Timespec = h264Time;
        d.Type = 0; // 视频

        return addData(d);
    }

    public int SendAAC(byte []data, int len) {
        Data d = new Data();
        int aacTime = (int)(System.currentTimeMillis() - m_start);
        // 校正aac时间
        if(lastAACTime > 0) {
            aacTime = lastAACTime + 23;
        }

        lastAACTime = aacTime;

        d.Data = Arrays.copyOf(data, len);
        d.Flag = 0;
        d.Timespec = aacTime;
        d.Type = 1; // 声音

        return addData(d);
    }

    private int addData(Data d) {
        synchronized (locker) {
            // 判断队列是否过多
            if(list.size() > 1024) {
                return -3;
            }

            // 加入到时间戳相对的位置
            for(int i = 0; i < list.size(); i++) {
                Data t = list.get(i);
                if(d.Timespec < t.Timespec) {
                    list.add(0, d);
                    return 0;
                }
            }

            // 加入队列队前
            list.addLast(d);
        }

        return 0;
    }

    public int Close() {
        synchronized (locker) {
            m_running = false;
        }

        return 0;
    }

    public int Release() {
        Close();
        return _release(m_ins);
    }

    // 回调事件
    private void callStart() {
        if(null != m_callback) {
            m_callback.OnPushRtmpStart();
        }
    }

    private void callStop() {
        if(null != m_callback) {
            m_callback.OnPushRtmpStop();
        }
    }
    // 发送线程
    private void sendRun() {
        while(false != m_running) {
            Data data = null;

            // 从队列中读取
            synchronized (locker) {
                // 保证发出去的时间戳是正确的
                if(list.size() > 0) {
                    data = list.get(0);
                    if(-1 != lastAACTime &&
                            data.Timespec <= lastAACTime) {
                        data = list.pop();
                    } else {
                        data = null;
                    }
                }
            }

            // 如果取不到数据，需要暂停10豪秒，防止线程死锁
            if(null == data) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            // 执行发送数据
            int ret = _send(m_ins, data.Type, data.Data, data.Flag, data.Timespec + 100);
            // System.out.println("===:type("+data.Type+")"+data.Timespec+":" + ret + ",Flag(" + data.Flag + ")");
            if(-1 != ret) {
                continue;
            }

            // 将数据放回队前
            if(0 == data.Type) {
                synchronized (locker) {
                    list.addFirst(data);
                }
            }

            callStop();
            // 先关闭对像
            _close(m_ins);
            // 再打开对像
            while(false != m_running) {
                ret = _open(m_ins, m_path,
                        m_vWidth, m_vHeight,
                        m_aSample, m_aChannel, m_aBit);
                if(0 == ret) {
                    callStart();
                    System.out.println("=======connect <" + m_path + "> success" );
                    break;
                }

                System.out.println("=======connect <" + m_path + "> faild: " + ret);
                // 连接失败，需要暂停一会再试
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Data {
        byte[]  Data;
        int     Type;
        int     Timespec;
        int     Flag;
    }
}

