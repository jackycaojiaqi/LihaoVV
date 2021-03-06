package sample.login;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.util.Log;
import android.widget.Toast;

import com.socks.library.KLog;
import com.xlg.android.LoginChannel;
import com.xlg.android.LoginHandler;
import com.xlg.android.protocol.LogonError;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.protocol.RegisterResponse;
import com.zhuyunjian.library.StartUtil;

import org.simple.eventbus.EventBus;


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
 * <p/>
 * 项目名称：fubangzhibo
 * 类描述：
 * 创建人：dell
 * 创建时间：2016-05-31 13:40
 * 修改人：dell
 * 修改时间：2016-05-31 13:40
 * 修改备注：
 */
public class LoginMain implements LoginHandler {
    private LoginChannel channel = new LoginChannel(this);
    private int id;
    private String pwd;
    private int flag;
    private int visitor;
    private String cldcode;
    private Context context;
    private int loginFlag;
    private LoginMain login;
    public static boolean is_login_success = false;

    public LoginMain(int id, String pwd, String cldcode, int flag, int visitor, Context context) {
        this.id = id;
        this.pwd = pwd;
        this.cldcode = cldcode;
        this.flag = flag;
        this.visitor = visitor;
        this.context = context;
    }


    public void start(int userId, String userPwd, int userVisitor, String userCldcode, int userFlag, Context mContext) {
        id = userId;
        pwd = userPwd;
        visitor = userVisitor;
        cldcode = userCldcode;
        flag = userFlag;
        context = mContext;
        login = new LoginMain(userId, userPwd, cldcode, userFlag, userVisitor, context);
        loginFlag = 1;
        KLog.e(id + " " + pwd);
        // 121.43.155.221:15518
        // 121.43.63.101:18517

//        login.channel.Connect("42.121.57.170",12111);
        login.channel.Connect("120.26.54.182", 19517);
//        login.channel.Connect("121.41.57.153", 19517 );
//        loginFlag = 1;
//        login.channel.Connect("121.43.148.99", 18517);

        try {
            Thread.sleep(1000 * 50);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectSuccessed() {
        is_login_success = true;
        loginFlag++;
        // TODO Auto-generated method stub
        KLog.e("连接服务器成功");
        Log.d("123", id + pwd);
        channel.SendHello();
        if (flag == 9) {
            channel.SendRegisterRequest(pwd, cldcode);
        } else {
            Log.d("123", id + "---" + visitor + "---" + pwd + "-----" + StartUtil.getDeviceId(context) + "----" + cldcode + "----" + flag);
            channel.SendLogonRequest(id, visitor, 1, pwd, StartUtil.getDeviceId(context), "", cldcode, flag);
        }

//        channel.SendLogonRequest(0, 2, 1, "", "android-test", "" ,cldcode,2);
//        channel.SendLogonRequest(0, 5, 1, "123456", "android-test", "" ,"12345678999",5);
//		channel.SendRegisterRequest("123456","12345678999");
    }

    @Override
    public void onConnectFailed() {
        is_login_success = false;
        if (loginFlag == 1) {
            login.channel.Connect(";120.26.54.182", 19518);
            loginFlag++;
        } else {
            // TODO Auto-generated method stub
            KLog.e("连接服务器失败");
            Toast.makeText(context, "连接服务器失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLogonResponse(LogonResponse res) {
        // TODO Auto-generated method stub
        KLog.e("登录回应");
//        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
        KLog.e("Calias: " + res.getCalias());
        KLog.e("Cidiograph: " + res.getCidiograph());
        KLog.e("Cidiograph: " + res.getCidiograph());
        KLog.e("Decocolor: " + res.getDecocolor());
        KLog.e("Nb: " + res.getNb());
        KLog.e("Ndeposit: " + res.getNdeposit());
        KLog.e("Nk: " + res.getNk());
        KLog.e("Nlevel: " + res.getNlevel());
        KLog.e("Nverison: " + res.getNverison());
        KLog.e("Userid: " + res.getUserid());
        KLog.e("Gender: " + res.getGender());
        KLog.e("Headpic: " + res.getHeadpic());
        KLog.e("Online_stat: " + res.getOnline_stat());
        KLog.e("PWD: " + res.getCuserpwd());
        StartUtil.putVersion(context, res.getNverison() + "");
        StartUtil.editInfo(context, res.getCalias(), res.getUserid() + "", res.getCuserpwd());
        KLog.e("Reserve: " + res.getReserve());
        EventBus.getDefault().post(res, "login_success");
        EventBus.getDefault().post(res, "splash_success");
        EventBus.getDefault().post(res, "relogin_success");
//        }
        channel.Close();
    }

    @Override
    public void onLogonError(LogonError err) {
        is_login_success = false;
        // TODO Auto-generated method stub
        KLog.e("登录失败 " + err.getErrorid() + " " + err.getErrmsg());
        EventBus.getDefault().post("0", "splash_fail");
    }

    @Override
    public void onDisconnected() {
        is_login_success = false;
        // TODO Auto-generated method stub
        KLog.e("与服务器断开");
    }

    @Override
    public void onRegisterResponse(RegisterResponse res) {
        EventBus.getDefault().post(res, "registerSuccess");
        KLog.e("注册成功-----" + res.getUserid());
        KLog.e("错误id-----" + res.getErrid());
    }
}
