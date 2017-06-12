package org.yj.media;

/**
 * Created by yj on 2017/6/10.
 */

public class DecodeAAC {
    private long m_ins;

    private static native int _open(long m_ins);
    private static native int _decode(long m_ins, byte[] aac, MediaData dst);
    private static native int _close(long m_ins);
    private static native int _release(long m_ins);

    private MediaData pcmData;

    protected DecodeAAC(long ins) {
        m_ins = ins;
    }

    public int Open() {
        pcmData = new MediaData(44100 * 2);
        return _open(m_ins);
    }

    public MediaData Decode(byte [] aac) {
        pcmData.Clean();

        int ret = _decode(m_ins, aac, pcmData);
        if(0 == ret) {
            return pcmData;
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
