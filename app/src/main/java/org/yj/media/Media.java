package org.yj.media;

/**
 * Created by yj on 2017/5/25.
 */

public class Media {
    // 库的初始化
    private static native void _init();
    // 创建EncodeH264
    private static native long _createEncodeH264();
    // 创建EncodeAAC类
    private static native long _createEncodeAAC();
    // 创建视频发送类
    private static native long _createPushRtmp();

    static {
        System.loadLibrary("mymedia");
    }

    // 初始化库
    public static void Init() {
        _init();
    }

    // 创建EncodeH264
    public static EncodeH264 CreateEncodeH264() {

        long ins = _createEncodeH264();

        EncodeH264 enc = new EncodeH264(ins);

        return enc;
    }

    // 创建AAC
    public static EncodeAAC CreateEncodeAAC() {
        long ins = _createEncodeAAC();

        return new EncodeAAC(ins);
    }

    // 创建发送类
    public static PushRtmp CreatePushRtmp() {
        long ins = _createPushRtmp();

        return new PushRtmp(ins);
    }

    // NV21 => YUV420
    public static void NV21toYUV420(byte[] yuv, int width, int height, byte[] cache) {
        int ue = width * height / 4;
        int ve = width * height / 2;
        int ui = 0;
        int vi = width * height / 4;

        for(int i = width*height; i < yuv.length; i++) {
            if(i % 2 == 0) {
                // y 分量
                if(vi < ve) {
                    cache[vi] = yuv[i];
                    vi++;
                }
            } else {
                // u 分量
                if(ui < ue) {
                    cache[ui] = yuv[i];
                    ui++;
                }
            }
        }

        System.arraycopy(cache, 0, yuv, width*height, width * height / 2);
    }

    // NV21 旋转90度
    public static void NV21Rotate90(byte[] nv21, int width, int height, byte[] cache) {
        byte[] yuv = cache;// new byte[width * height * 3 / 2];
        // Rotate the Y luma
        int i =0;
        for(int x =0;x < width; x++) {
            for(int y = height-1;y >=0;y--){
                yuv[i]= nv21[y*width+x];
                i++;}

        }

        // Rotate the U and V color components
        i = width*height*3/2-1;for(int x = width-1;x >0;x=x-2){for(int y =0;y < height/2;y++){
            yuv[i]= nv21[(width*height)+(y*width)+x];
            i--;
            yuv[i]= nv21[(width*height)+(y*width)+(x-1)];
            i--;}
        }

        System.arraycopy(yuv, 0, nv21, 0, nv21.length);
    }
}
