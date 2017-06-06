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

    private LinkedList<Data> list;
    private Object locker;
    private int lastAACTime;

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

        d.Data = Arrays.copyOf(data, len);
        d.Flag = flag;
        d.Timespec = (int)(System.currentTimeMillis() - m_start);
        d.Type = 0; // 视频

        return addData(d);
    }

    public int SendAAC(byte []data, int len) {
        Data d = new Data();
        int aacTime = (int)(System.currentTimeMillis() - m_start);
        // 校正aac时间
        if(lastAACTime > 0 && (aacTime - lastAACTime) < 23) {
            System.out.println("===================: replace time:" + aacTime + ":" + lastAACTime);
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
            for(int i = list.size() - 1; i >= 0; i--) {
                Data e = list.get(i);
                if(e.Timespec < d.Timespec) {
                    list.add(i + 1, d);
                    return 0;
                }
            }

            // 加入队列队前
            list.addFirst(d);
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

    private void sendRun() {
        while(false != m_running) {
            Data data = null;

            // 从队列中读取
            synchronized (locker) {
                // 保证发出去的时间戳是正确的
                if(list.size() > 5) {
                    data = list.pop();
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
            System.out.println("===:type("+data.Type+")"+data.Timespec+":" + ret + ",Flag(" + data.Flag + ")");
            if(-1 != ret) {
                continue;
            }

            // 将数据放回队前
            if(0 == data.Type) {
                synchronized (locker) {
                    list.addFirst(data);
                }
            }

            // 先关闭对像
            _close(m_ins);
            // 再打开对像
            while(false != m_running) {
                ret = _open(m_ins, m_path,
                        m_vWidth, m_vHeight,
                        m_aSample, m_aChannel, m_aBit);
                if(0 == ret) {
                    System.out.println("===连接"+m_path+"成功");
                    break;
                }

                System.out.println("===连接"+m_path+"失败:" + ret);
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

