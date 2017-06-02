package com.zhuyunjian.library;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Base64;

import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by jash
 * Date: 16-2-23
 * Time: 上午10:12
 */
public class AsyncPayTask extends AsyncTask<String, Void, PayResult> {
    public static final String PARTNER = "2088521383607315";
    public static final String SELLER = "495638888@qq.com";
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMqOGtzSHmffpPbBWHqdz9PGnDveegzLegnkSa3MS9giDrUuK68qlYsDLEWntdmjZoz0g1Q+kwSkKPUNPrv2BoZ4p9eoy7ffjVspYlfXSUwA+cuTESV+IThmAwGhkuf0j9cjB5WGctgamKbEYSGx64CzDjCNT4sChsuFt03pj+QLAgMBAAECgYA4P2P4RCULZVBy8Vf3nNE0lueVvH8aBHscOhe3uo6pMzPwXiTlXKbwVm453YujJzv4EoJRp5q95DQ3cHkmSvieahsAmbF/XcM86QRbO5CARYMk89ng5mtxuYvkoLG2ilGvQD790PXsPLoRmbefQkPNjXDcgMkzU3ipQKSDZGsAIQJBAPVKhj8lpl+O+EIpf1yLJiovkXJQ4fnviAubRnO1zZxf3CQ4eMi9wvy2kWeBJGs4Q+/SQXzaar+30ib/eP2R5/sCQQDTZfHuokwyWd3ihSA4hTx43/CEVOVkUGiVvnMxCbgdwEv+D2HWXrXKpWi9OpCyZNw4KjJO4CwNsVp/D40cvecxAkEAvC0ZAja7BRIkaHV3bKKfe7uUZFOimOBmySitCrXNcAqmUu8z0iSqAr94myQasVqv27q8XLEfNccqpJpW0ojtowJAF7v0Y7R5FZYdYVdM/MuItJF/8XuOnaqhc16ElFbhAwYaAhY8B9NuWagkkEpJAJ5uUyf5qf1lNMxaurVGRJnVoQJAIE1s3H/WoOoccJffx59981/CU8y2nhLUlamobq4ZIX19RUPt9N/a+wvutUFq3nRcvRJsstWZgwrg7XPomSbGiQ==".replace(" ", "");
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKjhrc0h5n36T2wVh6nc/Txpw73noMy3oJ5EmtzEvYIg61LiuvKpWLAyxFp7XZo2aM9INUPpMEpCj1DT679gaGeKfXqMu3341bKWJX10lMAPnLkxElfiE4ZgMBoZLn9I/XIweVhnLYGpimxGEhseuAsw4wjU+LAobLhbdN6Y/kCwIDAQAB".replace(" ", "");
    private Activity activity;
    private PayCallBack callBack;
    public AsyncPayTask(Activity activity) {
        this.activity = activity;
        if (activity instanceof PayCallBack) {
            callBack = (PayCallBack) activity;
        }
    }
    public void pay(String subject, String body, String price){
        execute(subject, body, price);
    }
    @Override
    protected PayResult doInBackground(String[] params) {
        String orderInfo = getOrderInfo(params[0], params[1], params[2]);
        String sign = sign(orderInfo);
        orderInfo = orderInfo + "&sign=\"" + sign +"\"&sign_type=\"RSA\"";
        String result = new PayTask(activity).pay(orderInfo,true);
        return new PayResult(result);
    }

    public void setCallBack(PayCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void onPostExecute(PayResult payResult) {
        super.onPostExecute(payResult);
        if (callBack != null) {
            callBack.callBack(payResult);
        }
    }

    public interface PayCallBack{
        void callBack(PayResult result);
    }
    /**
     * create the order info. 创建订单信息
     *
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://zhifu.zzzktv.com/alipay_app/notify_url.php"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    private String sign(String content){
        String result = null;
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(RSA_PRIVATE, Base64.NO_WRAP));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(privateKey);
            signature.update(content.getBytes("UTF-8"));
            byte[] sign = signature.sign();
            result = URLEncoder.encode(Base64.encodeToString(sign, Base64.NO_WRAP), "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
