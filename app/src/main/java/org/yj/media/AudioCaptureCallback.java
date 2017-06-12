package org.yj.media;

/**
 * Created by yj on 2017/6/10.
 */

public interface AudioCaptureCallback {
    public void onAudioFrame(byte[] data);
}
