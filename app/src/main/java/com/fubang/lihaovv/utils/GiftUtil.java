package com.fubang.lihaovv.utils;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.entities.GiftEntity;

import java.util.ArrayList;
import java.util.List;

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
 * 创建时间：2016-05-25 14:22
 * 修改人：dell
 * 修改时间：2016-05-25 14:22
 * 修改备注：添加礼物的工具类
 */
public class GiftUtil {

    public static List<GiftEntity> getGifts(){
        List<GiftEntity> list = new ArrayList<>();

        list.add(new GiftEntity(617, R.mipmap.g1617,"鼓掌(100)"));
        list.add(new GiftEntity(618, R.mipmap.g1618,"啤酒(300)"));
        list.add(new GiftEntity(619, R.mipmap.g1619,"雷到了(700)"));
        list.add(new GiftEntity(620, R.mipmap.g1620,"歌神(1000)"));
        list.add(new GiftEntity(621, R.mipmap.g1621,"爱心(2000)"));
        list.add(new GiftEntity(622, R.mipmap.g1622,"呀美女(3000)"));
        list.add(new GiftEntity(623, R.mipmap.g1623,"嗨帅哥(3000)"));
        list.add(new GiftEntity(624, R.mipmap.g1624,"亲一口(7000)"));
        list.add(new GiftEntity(625, R.mipmap.g1625,"钻戒(1W)"));
        list.add(new GiftEntity(626, R.mipmap.g1626,"药不停(2w)"));
        list.add(new GiftEntity(627, R.mipmap.g1627,"小菊花(3w)"));
        list.add(new GiftEntity(628, R.mipmap.g1628,"大香蕉(3w)"));
        list.add(new GiftEntity(629, R.mipmap.g1629,"大冰棍(70w)"));
        list.add(new GiftEntity(630, R.mipmap.g1630,"梦幻城堡(700w)"));
        list.add(new GiftEntity(631, R.mipmap.g1631,"私人飞机(1000W)"));
        list.add(new GiftEntity(632, R.mipmap.g1632,"豪华游轮(2000w)"));
        list.add(new GiftEntity(633, R.mipmap.g1633,"浪漫烟花(3000w)"));
        list.add(new GiftEntity(634, R.mipmap.g1634,"超级跑车(3000w)"));
        list.add(new GiftEntity(635, R.mipmap.g1635,"热气球(7000W)"));
//        list.add(new GiftEntity(78, R.mipmap.g1078,"四大美女"));
//        list.add(new GiftEntity(79, R.mipmap.g1079,"同心结"));
//        list.add(new GiftEntity(80, R.mipmap.g1080,"红鲤鱼"));
//        list.add(new GiftEntity(81, R.mipmap.g1081,"红玫瑰"));
//        list.add(new GiftEntity(82, R.mipmap.g1082,"万事如意"));
//        list.add(new GiftEntity(83, R.mipmap.g1083,"生日快乐"));
//        list.add(new GiftEntity(84, R.mipmap.g1084,"花篮"));
//        list.add(new GiftEntity(86, R.mipmap.g1086,"爱神之箭"));
//        list.add(new GiftEntity(170, R.mipmap.g1170,"大家好"));
//        list.add(new GiftEntity(171, R.mipmap.g1171,"美女"));
//        list.add(new GiftEntity(172, R.mipmap.g1172,"飞吻"));
//        list.add(new GiftEntity(173, R.mipmap.g1173,"勾引你"));
//        list.add(new GiftEntity(174, R.mipmap.g1174,"中国好声音"));
//        list.add(new GiftEntity(175, R.mipmap.g1175,"好听"));
//        list.add(new GiftEntity(192, R.mipmap.g1192,"佛光普照"));
//        list.add(new GiftEntity(212, R.mipmap.g1212,"一生一世"));
//        list.add(new GiftEntity(528, R.mipmap.g1528,"真好听"));
//        list.add(new GiftEntity(533, R.mipmap.g1533,"人民币"));
//        list.add(new GiftEntity(536, R.mipmap.g1536,"上花轿"));
//        list.add(new GiftEntity(537, R.mipmap.g1537,"小牙签"));
//        list.add(new GiftEntity(552, R.mipmap.g1552,"情人节"));
//        list.add(new GiftEntity(562, R.mipmap.g1562,"因为有你"));
//        list.add(new GiftEntity(564, R.mipmap.g1565,"记得我爱你"));
//        list.add(new GiftEntity(567, R.mipmap.g1567,"拜拜"));
//        list.add(new GiftEntity(568, R.mipmap.g1568,"我要68"));

        return list;
    }
}
