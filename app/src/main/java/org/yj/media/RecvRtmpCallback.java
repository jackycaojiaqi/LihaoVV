package org.yj.media;

import android.graphics.Bitmap;

/**
 * Created by yj on 2017/6/10.
 */

public interface RecvRtmpCallback {
    public void OnRecvRtmpStart();
    public void OnRecvRtmpPlayVideo(Bitmap bmp);
    public void OnRecvRtmpPlayAudio(byte[] pcm, int sample, int channel);
    public void OnRecvRtmpStop();
}
