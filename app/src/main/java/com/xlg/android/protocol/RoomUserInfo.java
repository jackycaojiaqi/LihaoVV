package com.xlg.android.protocol;

import com.xlg.android.utils.Tools;

import java.util.Comparator;

public class RoomUserInfo  {
	@StructOrder(0)
	private int	userid;
	@StructOrder(1)
	private int	guardid;
	@StructOrder(2)
	private int	level1;				//用户级别
	@StructOrder(3)
	private int	guardlevel;			//守护级别
	@StructOrder(4)
	private int	udmic;			//马甲颜色
	@StructOrder(5)
	private int	decocolor;			//马甲颜色
	@StructOrder(6)
	private short	reserve;			//预留//用于表示是否为正常用户0-正常，1-机器人1
	@StructOrder(7)
	private short	sealid;				//盖章id
	@StructOrder(8)
	private long	sealbringtime;		//盖章时长（秒）1970后时长
	@StructOrder(9)
	private int	ipaddr;				//最后登录ip
	@StructOrder(10)
	private int	userstate;			//用户状态,用了低16位。
	@StructOrder(11)
	private int	starflag;			//周星标识
	@StructOrder(12)
	private int	activityflag;		//活动星标识
	@StructOrder(13)
	private short  chargemicgiftid;    //用于收费麦的礼物id
	@StructOrder(14)
	private short  chargemicgiftcount;  //用于收费麦的礼物数目
	@StructOrder(15)
	private byte			publicmixindex;		//公麦序。最大255
	@StructOrder(16)
	private byte			gender;				//性别（0－女，1－男，2－未知）
	@StructOrder(17)
	private short			colorbarnum;		//自己的彩条总数
	@StructOrder(18)
	private byte[]			useralias = new byte[32];//昵称
	// add by:baddie@126.com  返回金币做蝴蝶显示用 返回麦时麦序供限时麦用
	@StructOrder(19)
	private long		nk;                  //金币
	@StructOrder(20)
	private short           micindex;            //在1麦还是2麦
	@StructOrder(21)
	private long    micendtime;             //麦时结束时间
	@StructOrder(22)
	private long    micnowtime;           // 现在麦时过了多久
	@StructOrder(23)
	private byte[]            carname = new byte[32];              //座驾的名字
	@StructOrder(24)
	private int				isallowupmic;			//是否允许抱麦
	@StructOrder(25)
	private int				headid;					//头像id
	@StructOrder(26)
	private int				kingmic;			//是否允许抱麦
	@StructOrder(27)
	private byte[]				clastloginmac = new byte[32];					//头像id
	@StructOrder(28)
	private byte[]				photo = new byte[32];					//头像id

	private String rtmp_paly_url;
	private int jinmic;

	public int getGuardid() {
		return guardid;
	}

	public void setGuardid(int guardid) {
		this.guardid = guardid;
	}

	public int getGuardlevel() {
		return guardlevel;
	}

	public void setGuardlevel(int guardlevel) {
		this.guardlevel = guardlevel;
	}

	public int getUdmic() {
		return udmic;
	}

	public void setUdmic(int udmic) {
		this.udmic = udmic;
	}

	public int getKingmic() {
		return kingmic;
	}

	public void setKingmic(int kingmic) {
		this.kingmic = kingmic;
	}

	public int getJinmic() {
		return jinmic;
	}

	public void setJinmic(int jinmic) {
		this.jinmic = jinmic;
	}

	public String getRtmp_paly_url() {
		return rtmp_paly_url;
	}

	public void setRtmp_paly_url(String rtmp_paly_url) {
		this.rtmp_paly_url = rtmp_paly_url;
	}

	// 	int				nshiledgiftonsmallspeaker; //送的礼物是否上广播和跑道显示
// 	int				nshiledgiftshow;		//是否屏蔽礼物的滚动显示效果
// 	int				nshiledgifteffectsound;	//是否屏蔽礼物/烟花的flash效果和道具声音
// 	int				nshileddalabasound;		//是否屏蔽大喇叭声音效果
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getLevel1() {
		return level1;
	}
	public void setLevel1(int level1) {
		this.level1 = level1;
	}
	public int getDecocolor() {
		return decocolor;
	}
	public void setDecocolor(int decocolor) {
		this.decocolor = decocolor;
	}
	public short getReserve() {
		return reserve;
	}
	public void setReserve(short reserve) {
		this.reserve = reserve;
	}
	public short getSealid() {
		return sealid;
	}
	public void setSealid(short sealid) {
		this.sealid = sealid;
	}
	public long getSealbringtime() {
		return sealbringtime;
	}
	public void setSealbringtime(long sealbringtime) {
		this.sealbringtime = sealbringtime;
	}
	public int getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(int ipaddr) {
		this.ipaddr = ipaddr;
	}
	public int getUserstate() {
		return userstate;
	}
	public void setUserstate(int userstate) {
		this.userstate = userstate;
	}
	public int getStarflag() {
		return starflag;
	}
	public void setStarflag(int starflag) {
		this.starflag = starflag;
	}
	public int getActivityflag() {
		return activityflag;
	}
	public void setActivityflag(int activityflag) {
		this.activityflag = activityflag;
	}
	public short getChargemicgiftid() {
		return chargemicgiftid;
	}
	public void setChargemicgiftid(short chargemicgiftid) {
		this.chargemicgiftid = chargemicgiftid;
	}
	public short getChargemicgiftcount() {
		return chargemicgiftcount;
	}
	public void setChargemicgiftcount(short chargemicgiftcount) {
		this.chargemicgiftcount = chargemicgiftcount;
	}
	public byte getPublicmixindex() {
		return publicmixindex;
	}
	public void setPublicmixindex(byte publicmixindex) {
		this.publicmixindex = publicmixindex;
	}
	public byte getGender() {
		return gender;
	}
	public void setGender(byte gender) {
		this.gender = gender;
	}
	public short getColorbarnum() {
		return colorbarnum;
	}
	public void setColorbarnum(short colorbarnum) {
		this.colorbarnum = colorbarnum;
	}
	public String getUseralias() {
		return Tools.ByteArray2StringGBK(useralias);
	}
	public void setUseralias(String useralias) {
		Tools.String2ByteArrayGBK(this.useralias, useralias);
	}
	public long getNk() {
		return nk;
	}
	public void setNk(long nk) {
		this.nk = nk;
	}
	public short getMicindex() {
		return micindex;
	}
	public void setMicindex(short micindex) {
		this.micindex = micindex;
	}
	public long getMicendtime() {
		return micendtime;
	}
	public void setMicendtime(long micendtime) {
		this.micendtime = micendtime;
	}
	public long getMicnowtime() {
		return micnowtime;
	}
	public void setMicnowtime(long micnowtime) {
		this.micnowtime = micnowtime;
	}

	public String getCarname() {
		return Tools.ByteArray2StringGBK(carname);
	}
	public void setCarname(String carname) {
		Tools.String2ByteArrayGBK(this.carname, carname);
	}

	public int getIsallowupmic() {
		return isallowupmic;
	}
	public void setIsallowupmic(int isallowupmic) {
		this.isallowupmic = isallowupmic;
	}
	public int getHeadid() {
		return headid;
	}
	public void setHeadid(int headid) {
		this.headid = headid;
	}

	public String getCphoto() {
		return Tools.ByteArray2StringGBK(photo);
	}
	public void setCphoto(String photo) {
		Tools.String2ByteArrayGBK(this.photo, photo);
	}

}
