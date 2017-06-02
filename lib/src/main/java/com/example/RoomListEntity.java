package com.example;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

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
 * <p>
 * 项目名称：newfubangzhibo
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2016-07-26 09:14
 * 修改人：zhuyunjian
 * 修改时间：2016-07-26 09:14
 * 修改备注：
 */
public class RoomListEntity {
    public static void main(String[] args) {
//        roomid: "80000",
//        roomname: "80000",
//        roompic: "",
//        roomrs: "300",
//        roompwd: "",
//        rscount: "201",
//        gateway:""
        Schema schema = new Schema(1,"com.fubang.nihaokehu.entities");
        schema.setDefaultJavaPackageDao("com.fubang.nihaokehu.dao");
        Entity roomListEntity = schema.addEntity("RoomListEntity");
        roomListEntity.addIdProperty();
        roomListEntity.addStringProperty("roomid");
        roomListEntity.addStringProperty("roomname");
        roomListEntity.addStringProperty("roompic");
        roomListEntity.addStringProperty("roomrs");
        roomListEntity.addStringProperty("roompwd");
        roomListEntity.addStringProperty("rscount");
        roomListEntity.addStringProperty("gateway");
        try {
            DaoGenerator generator = new DaoGenerator();
            generator.generateAll(schema,"app/src/main/java");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
