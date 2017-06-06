package org.yj.media;

/**
 * Created by yj on 2017/6/3.
 */

/**
 * H264数据存储类
 */
public class H264Data {
    public byte [] H264;
    public int Flags;
    public int Length;

    public H264Data(int width, int height) {
        H264 = new byte[width * height * 4];
    }
}