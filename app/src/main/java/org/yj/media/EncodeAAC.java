package org.yj.media;

/**
 * Created by yj on 2017/6/3.
 */

public class EncodeAAC {
    private long m_ins;

    private static native int _open(long m_ins, int sample, int channel);
    private static native int _getSpecificInfo(long m_ins, byte []info);
    private static native int _encode(long m_ins, byte[] pcm, MediaData dst);
    private static native int _close(long m_ins);
    private static native int _release(long m_ins);

    private MediaData aacData;

    protected EncodeAAC(long ins) {
        m_ins = ins;
    }

    public int Open(int sample, int channel) {
        aacData = new MediaData(sample * channel);
        return _open(m_ins, sample, channel);
    }

    public byte[] GetSpecificInfo() {
        byte[] buf = new byte[2];

        if(0 != _getSpecificInfo(m_ins, buf)) {
            return null;
        }

        return buf;
    }

    public MediaData Encode(byte [] pcm) {
        aacData.Clean();

        int ret = _encode(m_ins, pcm, aacData);
        if(0 == ret) {
            return aacData;
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
