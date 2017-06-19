package com.xlg.android.protocol;

public class UserPayResponse {
	@StructOrder(0)
	private int	vcbid;				//房间ID
	@StructOrder(1)
	private int	userid;				//用户ID
	@StructOrder(2)
	private int	toid;				//对手ID
	@StructOrder(3)
	private long   	balance;			//余额
	@StructOrder(4)
	private long   	giftbalance;		//礼物金额
	@StructOrder(5)
	private int	reserve1;			//保留1
	@StructOrder(6)
	private int	reserve2;			//保留2
	@StructOrder(7)
	private long	        time;				//发生时间

	public int getToid() {
		return toid;
	}

	public void setToid(int toid) {
		this.toid = toid;
	}

	public int getVcbid() {
		return vcbid;
	}
	public void setVcbid(int vcbid) {
		this.vcbid = vcbid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
//	public int getToid() {
//		return toid;
//	}
//	public void setToid(int toid) {
//		this.toid = toid;
//	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public long getGiftbalance() {
		return giftbalance;
	}
	public void setGiftbalance(long giftbalance) {
		this.giftbalance = giftbalance;
	}
	public int getReserve1() {
		return reserve1;
	}
	public void setReserve1(int reserve1) {
		this.reserve1 = reserve1;
	}
	public int getReserve2() {
		return reserve2;
	}
	public void setReserve2(int reserve2) {
		this.reserve2 = reserve2;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	
}
