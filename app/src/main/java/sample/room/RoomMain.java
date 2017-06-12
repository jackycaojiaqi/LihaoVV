package sample.room;

public class RoomMain {
	private MyRoom room ;

	public RoomMain() {

	}

	public MyRoom getRoom() {
		return room;
	}

	public void setRoom(MyRoom room) {
		this.room = room;
	}

	public void Start(int roomId, int userId, String userPwd, String ip, int port, String pwd){
		room = new MyRoom();
		
		room.getChannel().setRoomID(roomId);
		room.getChannel().setUserID(userId);
		room.getChannel().setUserPwd(userPwd);
		room.getChannel().setRoomPwd(pwd);
		
		// 121.43.155.221:15518
		// 121.43.63.101:18517
		// tryroom://10000;121.43.63.101:10927;121.43.63.101:10927;121.43.63.101:10927;61.153.111.126:10927
		room.getChannel().Connect(ip, port);

		try {
			for(int i = 0; true; i++) {
				// 暂停10秒
				Thread.sleep(1000 * 10);
				if(room.isOK()) {
					// 发起心跳
					room.getChannel().SendKeepAliveReq();
//					发送聊天消息
//					room.getChannel().sendChatMsg(0, (byte)0, (byte)0, "这是测试");
				}else {
					break;
				}
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
