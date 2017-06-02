package com.fubang.lihaovv.ui;

import android.webkit.WebView;

import com.fubang.lihaovv.R;
import com.zhuyunjian.library.StartUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 备用游戏页面
 * http://120.26.108.184:98/game.htm?ip=120.26.108.184&port=9999&
 * memberid=1003&passwd=4ae72303977a82903a46e7addacb7c33&
 * sysserial=01f77905-6ea6-4d6b-8b5e-edcc59487f89&mac=74-27-EA-E5-A3-DA
 */

@EActivity(R.layout.activity_game)
public class GameActivity extends BaseActivity {
    @ViewById(R.id.game_web)
    WebView webView;

    public static final String GAME_URL = " http://120.26.108.184:98/game.htm?ip=120.26.108.184&port=9999&memberid=";
//            "memberid=1003&passwd=4ae72303977a82903a46e7addacb7c33&" +
//            "sysserial=01f77905-6ea6-4d6b-8b5e-edcc59487f89&mac=74-27-EA-E5-A3-DA";
    @Override
    public void before() {

    }

    @Override
    public void initView() {
        String pwd = StartUtil.getUserPwd(this);
        String mdPwd = stringToMD5(pwd);
        String userId = StartUtil.getUserId(this);
        String mac = StartUtil.getDeviceId(this);
        StringBuilder gameUrl = new StringBuilder(GAME_URL);
        gameUrl.append(userId).append("&passwd=")
                .append(mdPwd).append("&sysserial=01f77905-6ea6-4d6b-8b5e-edcc59487f89&mac=")
                .append(mac);
        webView.loadUrl(gameUrl.toString());

//        int w = View.MeasureSpec.makeMeasureSpec(0,
//                View.MeasureSpec.UNSPECIFIED);
//        int h = View.MeasureSpec.makeMeasureSpec(0,
//                View.MeasureSpec.UNSPECIFIED);
//        //重新测量
//        webView.measure(w, h);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    public static String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }


}
