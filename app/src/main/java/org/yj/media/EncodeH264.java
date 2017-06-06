package org.yj.media;

/**
 * Created by yj on 2017/5/24.
 */

public class EncodeH264 {
    private long m_ins;

    private static native int _open(long ins, int width, int height);
    private static native int _release(long ins);
    private static native int _encode(long ins, byte[] src, H264Data dst);
    private static native int _close(long ins);

    private H264Data m_h264Cache;

    // 构建类, 必须通过 Media.CreateEncodeH264 来创建
    protected EncodeH264(long ins) {
        System.out.println("EncodeH264::EncodeH264()"+ins);
        m_ins = ins;
    }

    /**
     * 释放资源，不调用此函数会导致内存泄漏
     */
    public void Release() {
        _release(m_ins);
    }

    /**
     * 打开编码器
     * @param width 源数据宽度
     * @param height 源数据高度
     * @return
     */
    public int Open(int width, int height) {
        m_h264Cache = new H264Data(width, height);
        return _open(m_ins, width, height);
    }

    /**
     * 编码操作
     * @param data
     * @return
     */
    public H264Data Encode(byte []data) {
        // 重置变量
        m_h264Cache.Length = 0;
        m_h264Cache.Flags = 0;
        // 进行编码
        int ret = _encode(m_ins, data, m_h264Cache);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>:" + ret);
        if (0 == ret) {
            return m_h264Cache;
        }

        return null;
    }

    public void Close() {
        _close(m_ins);
    }

  
}
