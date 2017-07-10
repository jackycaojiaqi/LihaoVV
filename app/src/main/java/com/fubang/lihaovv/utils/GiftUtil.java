package com.fubang.lihaovv.utils;

import com.fubang.lihaovv.R;
import com.fubang.lihaovv.entities.GiftEntity;
import com.fubang.lihaovv.entities.GiftNewEntity;

import java.util.ArrayList;
import java.util.List;


public class GiftUtil {

    public static List<GiftEntity> getGifts() {

        List<GiftEntity> list = new ArrayList<>();
        list.clear();
        list.add(new GiftEntity(32, R.mipmap.g1617, "鼓掌(100)"));
        list.add(new GiftEntity(33, R.mipmap.g1618, "啤酒(300)"));
        list.add(new GiftEntity(35, R.mipmap.g1620, "歌神(1000)"));
        list.add(new GiftEntity(36, R.mipmap.g1621, "爱心(2000)"));
        list.add(new GiftEntity(37, R.mipmap.g1622, "呀美女(3000)"));
        list.add(new GiftEntity(38, R.mipmap.g1623, "嗨帅哥(3000)"));
        list.add(new GiftEntity(39, R.mipmap.g1624, "亲一口(7000)"));
        list.add(new GiftEntity(40, R.mipmap.g1625, "钻戒(1W)"));
//        list.add(new GiftEntity(41, R.mipmap.g1626, "药不停(2w)"));
//        list.add(new GiftEntity(42, R.mipmap.g1627, "小菊花(3w)"));
//        list.add(new GiftEntity(43, R.mipmap.g1628, "大香蕉(3w)"));
//        list.add(new GiftEntity(44, R.mipmap.g1629, "大冰棍(70w)"));
//        list.add(new GiftEntity(45, R.mipmap.g1630, "梦幻城堡(700w)"));
//        list.add(new GiftEntity(46, R.mipmap.g1631, "私人飞机(1000W)"));
//        list.add(new GiftEntity(65, R.mipmap.g1632, "豪华游轮(2000w)"));
//        list.add(new GiftEntity(64, R.mipmap.g1633, "浪漫烟花(3000w)"));
//        list.add(new GiftEntity(62, R.mipmap.g1634, "超级跑车(3000w)"));
//        list.add(new GiftEntity(63, R.mipmap.g1635, "热气球(7000W)"));
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

    public static List<GiftNewEntity> getGiftsGroup1Page1() {
        List<GiftNewEntity> list = new ArrayList<>();
        list.clear();
        list.add(new GiftNewEntity(0, "幸运", new GiftNewEntity.ItemBean(301, "幸运桃花(100)", R.mipmap.p301)));
        list.add(new GiftNewEntity(0, "幸运", new GiftNewEntity.ItemBean(302, "幸运棒棒糖(100)", R.mipmap.p302)));
        list.add(new GiftNewEntity(0, "幸运", new GiftNewEntity.ItemBean(303, "幸运香吻(200)", R.mipmap.p303)));
        list.add(new GiftNewEntity(0, "幸运", new GiftNewEntity.ItemBean(304, "幸运苹果(200)", R.mipmap.p304)));
        list.add(new GiftNewEntity(0, "幸运", new GiftNewEntity.ItemBean(305, "幸运啤酒(200)", R.mipmap.p305)));
        list.add(new GiftNewEntity(0, "幸运", new GiftNewEntity.ItemBean(313, "幸运金玫瑰(200)", R.mipmap.p313)));
        list.add(new GiftNewEntity(0, "幸运", new GiftNewEntity.ItemBean(314, "幸运雪茄(200)", R.mipmap.p314)));
        list.add(new GiftNewEntity(0, "幸运", new GiftNewEntity.ItemBean(306, "幸运天使(300)", R.mipmap.p306)));


        return list;
    }

    public static List<GiftNewEntity> getGiftsGroup1Page2() {
        List<GiftNewEntity> list = new ArrayList<>();
        list.clear();
        list.add(new GiftNewEntity(0, "幸运", new GiftNewEntity.ItemBean(307, "幸运香水(1000)", R.mipmap.p307)));
        list.add(new GiftNewEntity(0, "幸运", new GiftNewEntity.ItemBean(308, "幸运钻戒(1000)", R.mipmap.p308)));
        list.add(new GiftNewEntity(0, "幸运", new GiftNewEntity.ItemBean(309, "幸运红包(2000)", R.mipmap.p309)));
        list.add(new GiftNewEntity(0, "幸运", new GiftNewEntity.ItemBean(315, "幸运金鸡(5000)", R.mipmap.p315)));
        list.add(new GiftNewEntity(0, "幸运", new GiftNewEntity.ItemBean(310, "幸运丘比特(1W)", R.mipmap.p310)));
        list.add(new GiftNewEntity(0, "幸运", new GiftNewEntity.ItemBean(312, "恭喜发财(2W)", R.mipmap.p312)));
        list.add(new GiftNewEntity(0, "幸运", new GiftNewEntity.ItemBean(311, "幸运金龙(4W)", R.mipmap.p311)));
        return list;
    }

    public static List<GiftNewEntity> getGiftsGroup2Page1() {
        List<GiftNewEntity> list = new ArrayList<>();
        list.clear();
        list.add(new GiftNewEntity(1, "爱情", new GiftNewEntity.ItemBean(320, "中意你(女)(100)", R.mipmap.p320)));
        list.add(new GiftNewEntity(1, "爱情", new GiftNewEntity.ItemBean(321, "中意你(男)(100)", R.mipmap.p321)));
        list.add(new GiftNewEntity(1, "爱情", new GiftNewEntity.ItemBean(322, "老喜欢你了(100)", R.mipmap.p322)));
        list.add(new GiftNewEntity(1, "爱情", new GiftNewEntity.ItemBean(323, "我爱你(100)", R.mipmap.p323)));

        list.add(new GiftNewEntity(1, "爱情", new GiftNewEntity.ItemBean(324, "你是我的唯一(100)", R.mipmap.p324)));
        list.add(new GiftNewEntity(1, "爱情", new GiftNewEntity.ItemBean(325, "保卫老婆(200)", R.mipmap.p325)));
        list.add(new GiftNewEntity(1, "爱情", new GiftNewEntity.ItemBean(326, "心心相印(200)", R.mipmap.p326)));
        list.add(new GiftNewEntity(1, "爱情", new GiftNewEntity.ItemBean(327, "心动(200)", R.mipmap.p327)));
        return list;
    }

    public static List<GiftNewEntity> getGiftsGroup2Page2() {
        List<GiftNewEntity> list = new ArrayList<>();
        list.clear();
        list.add(new GiftNewEntity(1, "爱情", new GiftNewEntity.ItemBean(328, "爱你久久(200)", R.mipmap.p328)));
        list.add(new GiftNewEntity(1, "爱情", new GiftNewEntity.ItemBean(329, "双宿双飞(200)", R.mipmap.p329)));
        return list;
    }

    public static List<GiftNewEntity> getGiftsGroup3Page1() {
        List<GiftNewEntity> list = new ArrayList<>();
        list.clear();
        list.add(new GiftNewEntity(2, "抢星", new GiftNewEntity.ItemBean(47, "欢迎光临(100)",  R.mipmap.p47)));
        list.add(new GiftNewEntity(2, "抢星", new GiftNewEntity.ItemBean(48, "鞭炮(100)", R.mipmap.p48)));
        list.add(new GiftNewEntity(2, "抢星", new GiftNewEntity.ItemBean(49, "鲜花(200)", R.mipmap.p49)));
        list.add(new GiftNewEntity(2, "抢星", new GiftNewEntity.ItemBean(51, "猪头(200)", R.mipmap.p51)));

        list.add(new GiftNewEntity(2, "抢星", new GiftNewEntity.ItemBean(52, "香吻(200)", R.mipmap.p52)));
        list.add(new GiftNewEntity(2, "抢星", new GiftNewEntity.ItemBean(53, "香水(500)", R.mipmap.p53)));
        list.add(new GiftNewEntity(2, "抢星", new GiftNewEntity.ItemBean(54, "钻戒(500)", R.mipmap.p54)));
        list.add(new GiftNewEntity(2, "抢星", new GiftNewEntity.ItemBean(55, "跑车(1000)", R.mipmap.p55)));


        return list;
    }

//    public static List<GiftNewEntity> getGiftsGroup3Page2() {
//        List<GiftNewEntity> list = new ArrayList<>();
//        list.clear();
//
//        list.add(new GiftNewEntity(2, "抢星", new GiftNewEntity.ItemBean(56, "飞机(1000)", R.mipmap.p56)));
//        return list;
//    }

    public static List<GiftNewEntity> getGiftsGroup4Page1() {
        List<GiftNewEntity> list = new ArrayList<>();
        list.clear();
        list.add(new GiftNewEntity(3, "豪华", new GiftNewEntity.ItemBean(500, "让子弹飞(22W)", R.mipmap.p500)));
        list.add(new GiftNewEntity(3, "豪华", new GiftNewEntity.ItemBean(501, "一见钟情(25W)",  R.mipmap.p501)));
        list.add(new GiftNewEntity(3, "豪华", new GiftNewEntity.ItemBean(502, "丘比特(30W)",  R.mipmap.p502)));
        list.add(new GiftNewEntity(3, "豪华", new GiftNewEntity.ItemBean(503, "定情信物(28W)",  R.mipmap.p503)));
        list.add(new GiftNewEntity(3, "豪华", new GiftNewEntity.ItemBean(504, "你是我的天使(28W)",  R.mipmap.p504)));
        list.add(new GiftNewEntity(3, "豪华", new GiftNewEntity.ItemBean(505, "生日快乐(42W)",  R.mipmap.p505)));
        list.add(new GiftNewEntity(3, "豪华", new GiftNewEntity.ItemBean(506, "拜堂成亲(42W)",  R.mipmap.p506)));
        list.add(new GiftNewEntity(3, "豪华", new GiftNewEntity.ItemBean(507, "我心永恒(45W)",  R.mipmap.p507)));

        return list;
    }

    public static List<GiftNewEntity> getGiftsGroup4Page2() {
        List<GiftNewEntity> list = new ArrayList<>();
        list.clear();

        list.add(new GiftNewEntity(3, "豪华", new GiftNewEntity.ItemBean(508, "同心结(38W)",  R.mipmap.p508)));
        list.add(new GiftNewEntity(3, "豪华", new GiftNewEntity.ItemBean(509, "心连心(38W)",  R.mipmap.p509)));
        list.add(new GiftNewEntity(3, "豪华", new GiftNewEntity.ItemBean(510, "5920我就爱你(22W)",  R.mipmap.p510)));
        list.add(new GiftNewEntity(3, "豪华", new GiftNewEntity.ItemBean(511, "有你真好(20W)",  R.mipmap.p511)));
        list.add(new GiftNewEntity(3, "豪华", new GiftNewEntity.ItemBean(512, "我的好兄弟(30W)",  R.mipmap.p512)));
        list.add(new GiftNewEntity(3, "豪华", new GiftNewEntity.ItemBean(401, "小烟花（20W）",  R.mipmap.p401)));
        list.add(new GiftNewEntity(3, "豪华", new GiftNewEntity.ItemBean(402, "大烟花(60W)",  R.mipmap.p402)));
        return list;
    }

    public static List<GiftNewEntity> getGiftsGroup5Page1() {
        List<GiftNewEntity> list = new ArrayList<>();
        list.clear();

        list.add(new GiftNewEntity(4, "至尊", new GiftNewEntity.ItemBean(450, "招财进宝(42W)", R.mipmap.p450)));
        list.add(new GiftNewEntity(4, "至尊", new GiftNewEntity.ItemBean(451, "航空母舰(45W)", R.mipmap.p451)));
        list.add(new GiftNewEntity(4, "至尊", new GiftNewEntity.ItemBean(452, "玫瑰庄园(45W)", R.mipmap.p452)));
        list.add(new GiftNewEntity(4, "至尊", new GiftNewEntity.ItemBean(453, "飞机(50W)", R.mipmap.p453)));
        list.add(new GiftNewEntity(4, "至尊", new GiftNewEntity.ItemBean(454, "离婚证(50W)", R.mipmap.p454)));
        list.add(new GiftNewEntity(4, "至尊", new GiftNewEntity.ItemBean(455, "豪华游艇(60W)", R.mipmap.p455)));
        list.add(new GiftNewEntity(4, "至尊", new GiftNewEntity.ItemBean(457, "导弹(80W)", R.mipmap.p457)));
        list.add(new GiftNewEntity(4, "至尊", new GiftNewEntity.ItemBean(458, "宇宙飞船(80W)", R.mipmap.p458)));


        return list;
    }

    public static List<GiftNewEntity> getGiftsGroup5Page2() {
        List<GiftNewEntity> list = new ArrayList<>();
        list.clear();


        list.add(new GiftNewEntity(4, "至尊", new GiftNewEntity.ItemBean(459, "世界导弹(100W)", R.mipmap.p459)));
        list.add(new GiftNewEntity(4, "至尊", new GiftNewEntity.ItemBean(750, "金麦克(80W)", R.mipmap.p750)));
        list.add(new GiftNewEntity(4, "至尊", new GiftNewEntity.ItemBean(1000, "崇拜(80W)", R.mipmap.p1000)));

        return list;
    }

    public static List<GiftNewEntity> getGiftsGroup6Page1() {
        List<GiftNewEntity> list = new ArrayList<>();
        list.clear();


        list.add(new GiftNewEntity(5, "货币", new GiftNewEntity.ItemBean(231, "铜钱(500)", R.mipmap.p231)));
        list.add(new GiftNewEntity(5, "货币", new GiftNewEntity.ItemBean(232, "金币(1000)", R.mipmap.p232)));
        list.add(new GiftNewEntity(5, "货币", new GiftNewEntity.ItemBean(233, "美元(5000)", R.mipmap.p233)));
        list.add(new GiftNewEntity(5, "货币", new GiftNewEntity.ItemBean(234, "金条(1W)", R.mipmap.p234)));
        list.add(new GiftNewEntity(5, "货币", new GiftNewEntity.ItemBean(235, "劳力士(5W)", R.mipmap.p235)));
        list.add(new GiftNewEntity(5, "货币", new GiftNewEntity.ItemBean(236, "财神(5W)", R.mipmap.p236)));
        list.add(new GiftNewEntity(5, "货币", new GiftNewEntity.ItemBean(238, "聚宝盆(20W)", R.mipmap.p238)));
        list.add(new GiftNewEntity(5, "货币", new GiftNewEntity.ItemBean(239, "百宝箱(50W)", R.mipmap.p239)));


        return list;
    }

    public static List<GiftNewEntity> getGiftsGroup6Page2() {
        List<GiftNewEntity> list = new ArrayList<>();
        list.clear();
        list.add(new GiftNewEntity(5, "货币", new GiftNewEntity.ItemBean(250, "支票(100W)", R.mipmap.p250)));

        return list;
    }

    public static List<GiftNewEntity> getGiftsGroup7Page1() {
        List<GiftNewEntity> list = new ArrayList<>();
        list.clear();
        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(32, "鼓掌(100)", R.mipmap.p32)));
        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(33, "啤酒(300)",  R.mipmap.p33)));
        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(34, "雷到了(700)",  R.mipmap.p34)));
        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(35, "歌神(1000)",  R.mipmap.p35)));
        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(36, "爱心(2000)",  R.mipmap.p36)));
        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(37, "呀美女(3000)",  R.mipmap.p37)));
        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(38, "嗨帅哥(3000)",  R.mipmap.p38)));
        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(39, "亲一口(7000)",  R.mipmap.p39)));
        return list;
    }
    public static List<GiftNewEntity> getGiftsGroup7Page2() {
        List<GiftNewEntity> list = new ArrayList<>();
        list.clear();

        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(40, "钻戒(1W)",  R.mipmap.p40)));
        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(41, "药不停(2W)",  R.mipmap.p41)));
        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(42, "小菊花(3W)",  R.mipmap.p42)));
        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(43, "大香蕉(3W)",  R.mipmap.p43)));
        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(44, "大冰棍（70W）",  R.mipmap.p44)));
        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(45, "梦幻城堡(700w)",  R.mipmap.p45)));
        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(46, "私人飞机（1000W）",  R.mipmap.p46)));
        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(64, "浪漫烟花（3000W）",  R.mipmap.p64)));

        return list;
    }
    public static List<GiftNewEntity> getGiftsGroup7Page3() {
        List<GiftNewEntity> list = new ArrayList<>();
        list.clear();

        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(65, "豪华游轮（2000W）",  R.mipmap.p65)));
        list.add(new GiftNewEntity(6, "手机", new GiftNewEntity.ItemBean(63, "热气球（7000W）",  R.mipmap.p63)));
        return list;
    }

    public static List<GiftNewEntity> getAllGift(){
        List<GiftNewEntity> list = new ArrayList<>();
        list.clear();
        list.addAll(getGiftsGroup1Page1());
        list.addAll(getGiftsGroup1Page2());
        list.addAll(getGiftsGroup2Page1());
        list.addAll(getGiftsGroup2Page2());
        list.addAll(getGiftsGroup3Page1());
        list.addAll(getGiftsGroup4Page1());
        list.addAll(getGiftsGroup4Page2());
        list.addAll(getGiftsGroup5Page1());
        list.addAll(getGiftsGroup5Page2());
        list.addAll(getGiftsGroup6Page1());
        list.addAll(getGiftsGroup6Page2());
        list.addAll(getGiftsGroup7Page1());
        list.addAll(getGiftsGroup7Page2());
        list.addAll(getGiftsGroup7Page3());
        return list;
    }
}
