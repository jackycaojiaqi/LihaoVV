package com.fubang.lihaovv.model.impl;

import com.fubang.lihaovv.entities.RichListEntity;
import com.fubang.lihaovv.model.BaseRichModel;
import com.fubang.lihaovv.model.RichModel;
import com.fubang.lihaovv.utils.ParamsMap;
import com.zhuyunjian.library.DeviceUtil;

import retrofit2.Callback;

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
 * 项目名称：MyApplication
 * 类描述：
 * 创建人：dell
 * 创建时间：2016-06-24 17:03
 * 修改人：dell
 * 修改时间：2016-06-24 17:03
 * 修改备注：
 */
public class RichModelImpl extends BaseRichModel implements RichModel {
    private static volatile RichModelImpl instance = null;

    private RichModelImpl(){

    }

    public static RichModelImpl getInstance() {
        if (instance == null) {
            synchronized (RichModelImpl.class) {
                if (instance == null) {
                    instance = new RichModelImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void getRichEntityData(Callback<RichListEntity> callback) {
        ParamsMap map = ParamsMap.getInstance();
        map.put("type",1);
        service.getRichEntity(DeviceUtil.getMonth(),15).enqueue(callback);
    }
}
