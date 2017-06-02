package sample.login;

import com.xlg.android.LoginChannel;
import com.xlg.android.LoginHandler;
import com.xlg.android.protocol.LogonError;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.protocol.RegisterResponse;

public class TestLogin implements LoginHandler {
	private LoginChannel channel = new LoginChannel(this);


	public static void main(String[] args) {
		TestLogin login = new TestLogin();

		// 121.43.155.221:15518
		// 121.43.63.101:18517
//		login.channel.Connect("121.40.138.189", 19517);
//		login.channel.Connect("218.244.146.158",12111);
//		login.channel.Connect("42.121.57.170",12111);
		login.channel.Connect("120.26.54.182",19517);
		try {
			Thread.sleep(1000 * 50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            	}

	//macaddr随便填什么
	@Override
	public void onConnectSuccessed() {
		// TODO Auto-generated method stub
		System.out.println("连接服务器成功");
		
		channel.SendHello();
		
//		channel.SendLogonRequest(10, 0, 1, "426032", "android-test",  "", "",0);
//		channel.SendLogonRequest(99887, 0, 1, "1", "android-test", "" ,"",0);
		channel.SendLogonRequest(0, 2, 1, "", "12213213123131", "" ,"122345678999",2);
//		channel.SendRegisterRequest("123456","12345678999");
	}

	@Override
	public void onConnectFailed() {
		// TODO Auto-generated method stub
		System.out.println("连接服务器失败");
	}

	@Override
	public void onLogonResponse(LogonResponse res) {
		// TODO Auto-generated method stub
		System.out.println("登录回应");
		System.out.println("Userid: " + res.getUserid());
		System.out.println("Nlevel: " + res.getNlevel());
		System.out.println("nfamilyid: "+ res.getNfamilyid());
		System.out.println("Ndeposit: " + res.getNdeposit());
		System.out.println("Nk: " + res.getNk());
		System.out.println("Nb: " + res.getNb());
		System.out.println("Decocolor: " + res.getDecocolor());
		System.out.println("Nverison: " + res.getNverison());
		System.out.println("Online_stat: " + res.getOnline_stat());
		System.out.println("Headpic: " + res.getHeadpic());
		System.out.println("Reserve: " + res.getReserve());
		System.out.println("Gender: " + res.getGender());
		System.out.println("Calias: " + res.getCalias());
		System.out.println("Cidiograph: " + res.getCidiograph());
		System.out.println("psw:" + res.getCuserpwd());
		channel.Close();
	}

	@Override
	public void onLogonError(LogonError err) {
		// TODO Auto-generated method stub
		System.out.println("登录失败");
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		System.out.println("与服务器断开");
	}

	@Override
	public void onRegisterResponse(RegisterResponse res) {
		System.out.println("注册成功-----"+res.getUserid());
		System.out.println("错误id-----"+res.getErrid());
	}
}
