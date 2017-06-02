
package com.fubang.lihaovv.utils;

import android.support.v4.util.ArrayMap;

import com.fubang.lihaovv.R;


/**
 * @author : zejian
 * @time : 2016年1月5日 上午11:32:33
 * @email : shinezejian@163.com
 * @description :表情加载类,可自己添加多种表情，分别建立不同的map存放和不同的标志符即可
 */
public class EmotionUtils1 {

	/**
	 * 表情类型标志符
	 */
	public static final int EMOTION_CLASSIC_TYPE=0x0001;//经典表情

	/**
	 * key-表情文字;
	 * value-表情图片资源
	 */
	public static ArrayMap<String, Integer> EMPTY_MAP;
	public static ArrayMap<String, Integer> EMOTION_CLASSIC_MAP;


	static {
		EMPTY_MAP = new ArrayMap<>();
		EMOTION_CLASSIC_MAP = new ArrayMap<>();
		EMOTION_CLASSIC_MAP.put("/mrEmotion001", R.drawable.emotion001);
		EMOTION_CLASSIC_MAP.put("/mrEmotion002", R.drawable.emotion002);
		EMOTION_CLASSIC_MAP.put("/mrEmotion003", R.drawable.emotion003);
		EMOTION_CLASSIC_MAP.put("/mrEmotion004", R.drawable.emotion004);
		EMOTION_CLASSIC_MAP.put("/mrEmotion005", R.drawable.emotion005);
		EMOTION_CLASSIC_MAP.put("/mrEmotion006", R.drawable.emotion006);
		EMOTION_CLASSIC_MAP.put("/mrEmotion007", R.drawable.emotion007);
		EMOTION_CLASSIC_MAP.put("/mrEmotion008", R.drawable.emotion008);
		EMOTION_CLASSIC_MAP.put("/mrEmotion009", R.drawable.emotion009);
		EMOTION_CLASSIC_MAP.put("/mrEmotion010", R.drawable.emotion010);
		EMOTION_CLASSIC_MAP.put("/mrEmotion011", R.drawable.emotion011);
		EMOTION_CLASSIC_MAP.put("/mrEmotion012", R.drawable.emotion012);
		EMOTION_CLASSIC_MAP.put("/mrEmotion013", R.drawable.emotion013);
		EMOTION_CLASSIC_MAP.put("/mrEmotion014", R.drawable.emotion014);
		EMOTION_CLASSIC_MAP.put("/mrEmotion015", R.drawable.emotion015);
		EMOTION_CLASSIC_MAP.put("/mrEmotion016", R.drawable.emotion016);
		EMOTION_CLASSIC_MAP.put("/mrEmotion017", R.drawable.emotion017);
		EMOTION_CLASSIC_MAP.put("/mrEmotion018", R.drawable.emotion018);
		EMOTION_CLASSIC_MAP.put("/mrEmotion019", R.drawable.emotion019);
		EMOTION_CLASSIC_MAP.put("/mrEmotion020", R.drawable.emotion020);
		EMOTION_CLASSIC_MAP.put("/mrEmotion021", R.drawable.emotion021);
		EMOTION_CLASSIC_MAP.put("/mrEmotion022", R.drawable.emotion022);
		EMOTION_CLASSIC_MAP.put("/mrEmotion023", R.drawable.emotion023);
		EMOTION_CLASSIC_MAP.put("/mrEmotion024", R.drawable.emotion024);
		EMOTION_CLASSIC_MAP.put("/mrEmotion025", R.drawable.emotion025);
		EMOTION_CLASSIC_MAP.put("/mrEmotion026", R.drawable.emotion026);
		EMOTION_CLASSIC_MAP.put("/mrEmotion027", R.drawable.emotion027);
		EMOTION_CLASSIC_MAP.put("/mrEmotion028", R.drawable.emotion028);
		EMOTION_CLASSIC_MAP.put("/mrEmotion029", R.drawable.emotion029);
		EMOTION_CLASSIC_MAP.put("/mrEmotion030", R.drawable.emotion030);
		EMOTION_CLASSIC_MAP.put("/mrEmotion031", R.drawable.emotion031);
		EMOTION_CLASSIC_MAP.put("/mrEmotion032", R.drawable.emotion032);
		EMOTION_CLASSIC_MAP.put("/mrEmotion033", R.drawable.emotion033);
		EMOTION_CLASSIC_MAP.put("/mrEmotion034", R.drawable.emotion034);
		EMOTION_CLASSIC_MAP.put("/mrEmotion035", R.drawable.emotion035);
		EMOTION_CLASSIC_MAP.put("/mrEmotion036", R.drawable.emotion036);
		EMOTION_CLASSIC_MAP.put("/mrEmotion037", R.drawable.emotion037);
		EMOTION_CLASSIC_MAP.put("/mrEmotion038", R.drawable.emotion038);
		EMOTION_CLASSIC_MAP.put("/mrEmotion039", R.drawable.emotion039);
		EMOTION_CLASSIC_MAP.put("/mrEmotion040", R.drawable.emotion040);
		EMOTION_CLASSIC_MAP.put("/mrEmotion041", R.drawable.emotion041);
		EMOTION_CLASSIC_MAP.put("/mrEmotion042", R.drawable.emotion042);
		EMOTION_CLASSIC_MAP.put("/mrEmotion043", R.drawable.emotion043);
		EMOTION_CLASSIC_MAP.put("/mrEmotion044", R.drawable.emotion044);
		EMOTION_CLASSIC_MAP.put("/mrEmotion045", R.drawable.emotion045);
		EMOTION_CLASSIC_MAP.put("/mrEmotion046", R.drawable.emotion046);
		EMOTION_CLASSIC_MAP.put("/mrEmotion047", R.drawable.emotion047);
		EMOTION_CLASSIC_MAP.put("/mrEmotion048", R.drawable.emotion048);
		EMOTION_CLASSIC_MAP.put("/mrEmotion049", R.drawable.emotion049);
		EMOTION_CLASSIC_MAP.put("/mrEmotion050", R.drawable.emotion050);
		EMOTION_CLASSIC_MAP.put("/mrEmotion051", R.drawable.emotion051);
		EMOTION_CLASSIC_MAP.put("/mrEmotion052", R.drawable.emotion052);
		EMOTION_CLASSIC_MAP.put("/mrEmotion053", R.drawable.emotion053);
		EMOTION_CLASSIC_MAP.put("/mrEmotion054", R.drawable.emotion054);
		EMOTION_CLASSIC_MAP.put("/mrEmotion055", R.drawable.emotion055);
		EMOTION_CLASSIC_MAP.put("/mrEmotion056", R.drawable.emotion056);
		EMOTION_CLASSIC_MAP.put("/mrEmotion057", R.drawable.emotion057);
		EMOTION_CLASSIC_MAP.put("/mrEmotion058", R.drawable.emotion058);
		EMOTION_CLASSIC_MAP.put("/mrEmotion059", R.drawable.emotion059);
		EMOTION_CLASSIC_MAP.put("/mrEmotion060", R.drawable.emotion060);
		EMOTION_CLASSIC_MAP.put("/mrEmotion061", R.drawable.emotion061);
		EMOTION_CLASSIC_MAP.put("/mrEmotion062", R.drawable.emotion062);
		EMOTION_CLASSIC_MAP.put("/mrEmotion063", R.drawable.emotion063);
		EMOTION_CLASSIC_MAP.put("/mrEmotion064", R.drawable.emotion064);
		EMOTION_CLASSIC_MAP.put("/mrEmotion065", R.drawable.emotion065);
		EMOTION_CLASSIC_MAP.put("/mrEmotion066", R.drawable.emotion066);
		EMOTION_CLASSIC_MAP.put("/mrEmotion067", R.drawable.emotion067);
		EMOTION_CLASSIC_MAP.put("/mrEmotion068", R.drawable.emotion068);
		EMOTION_CLASSIC_MAP.put("/mrEmotion069", R.drawable.emotion069);
		EMOTION_CLASSIC_MAP.put("/mrEmotion070", R.drawable.emotion070);
		EMOTION_CLASSIC_MAP.put("/mrEmotion071", R.drawable.emotion071);
		EMOTION_CLASSIC_MAP.put("/mrEmotion072", R.drawable.emotion072);
		EMOTION_CLASSIC_MAP.put("/mrEmotion073", R.drawable.emotion073);
		EMOTION_CLASSIC_MAP.put("/mrEmotion074", R.drawable.emotion074);
		EMOTION_CLASSIC_MAP.put("/mrEmotion075", R.drawable.emotion075);
		EMOTION_CLASSIC_MAP.put("/mrEmotion076", R.drawable.emotion076);
		EMOTION_CLASSIC_MAP.put("/mrEmotion077", R.drawable.emotion077);
		EMOTION_CLASSIC_MAP.put("/mrEmotion078", R.drawable.emotion078);
		EMOTION_CLASSIC_MAP.put("/mrEmotion079", R.drawable.emotion079);
		EMOTION_CLASSIC_MAP.put("/mrEmotion080", R.drawable.emotion080);
		EMOTION_CLASSIC_MAP.put("/mrEmotion081", R.drawable.emotion081);
		EMOTION_CLASSIC_MAP.put("/mrEmotion082", R.drawable.emotion082);
		EMOTION_CLASSIC_MAP.put("/mrEmotion083", R.drawable.emotion083);
		EMOTION_CLASSIC_MAP.put("/mrEmotion084", R.drawable.emotion084);
		EMOTION_CLASSIC_MAP.put("/mrEmotion085", R.drawable.emotion085);
	}

	/**
	 * 根据名称获取当前表情图标R值
	 * @param EmotionType 表情类型标志符
	 * @param imgName 名称
	 * @return
	 */
	public static int getImgByName(int EmotionType,String imgName) {
		Integer integer=null;
		switch (EmotionType){
			case EMOTION_CLASSIC_TYPE:
				integer = EMOTION_CLASSIC_MAP.get(imgName);
				break;
			default:
				LogUtils.e("the emojiMap is null!! Handle Yourself ");
				break;
		}
		return integer == null ? -1 : integer;
	}

	/**
	 * 根据类型获取表情数据
	 * @param EmotionType
	 * @return
	 */
	public static ArrayMap<String, Integer> getEmojiMap(int EmotionType){
		ArrayMap EmojiMap=null;
		switch (EmotionType){
			case EMOTION_CLASSIC_TYPE:
				EmojiMap=EMOTION_CLASSIC_MAP;
				break;
			default:
				EmojiMap=EMPTY_MAP;
				break;
		}
		return EmojiMap;
	}
}
