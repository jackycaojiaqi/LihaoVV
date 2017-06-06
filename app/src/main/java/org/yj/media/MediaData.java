package org.yj.media;

/**
 * Created by yj on 2017/6/3.
 */

public class MediaData {
    private byte [] byData;     // 缓存数据库
    private int byLength;
    private int flag;
    private int vWidth;
    private int vHeight;
    private int aSample;
    private int aChannel;
    private int aBit;

    public MediaData(int cap) {
        byData = new byte[cap];
    }

    public void Clean() {
        byLength = 0;
        flag = 0;
    }

    public byte[] getData() {
        return byData;
    }

    public int getLength() {
        return byLength;
    }

    public int getCap() {
        return byData.length;
    }

    public int getFlag() {
        return flag;
    }

    public int getVideoWidth() {
        return vWidth;
    }

    public int getVideoHeight() {
        return vHeight;
    }

    public int getAudioSample() {
        return aSample;
    }

    public int getAudioChannel() {
        return aChannel;
    }

    public int getAudioBit() {
        return aBit;
    }
}
