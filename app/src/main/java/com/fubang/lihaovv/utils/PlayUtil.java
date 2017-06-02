package com.fubang.lihaovv.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.SurfaceView;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * <p>
 * 项目名称：Youliao
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2016-11-22 08:48
 * 修改人：zhuyunjian
 * 修改时间：2016-11-22 08:48
 * 修改备注：
 */
public class PlayUtil {
    private Bitmap bmp;


    public static void drawSurface(Activity activity, final SurfaceView surfaceView, final SimpleDraweeView simpleDraweeView, int width, int height, byte[] img){
        if (simpleDraweeView.isShown()){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    simpleDraweeView.setVisibility(View.GONE);
                    surfaceView.setVisibility(View.VISIBLE);
                }
            });
            //删除旧的

        }
    }

}
