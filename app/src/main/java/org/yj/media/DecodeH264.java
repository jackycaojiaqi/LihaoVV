package org.yj.media;

/**
 * Created by yj on 2017/6/10.
 */

public class DecodeH264 {
    private long m_ins;

    private static native int _open(long ins, int mode);
    private static native int _decode(long ins, byte[] h264, MediaData dst);
    private static native int _close(long ins);
    private static native int _release(long ins);

    private MediaData rgbData;

    protected DecodeH264(long ins) {
        m_ins = ins;
    }

    /**
     *
     * @param mode: 0-yuv420sp;1-RGB888;2-RGB565;3-ARGB
     * @return
     */
    public int Open(int mode) {
        rgbData = new MediaData(1280*720*3);
        return _open(m_ins, mode);
    }

    public MediaData Decode(byte [] h264) {
        rgbData.Clean();

        int ret = _decode(m_ins, h264, rgbData);
        if(0 == ret) {
            return rgbData;
        }

        return null;
    }

    public void Close() {
        _close(m_ins);
    }

    public void Release() {
        _release(m_ins);
    }

}
