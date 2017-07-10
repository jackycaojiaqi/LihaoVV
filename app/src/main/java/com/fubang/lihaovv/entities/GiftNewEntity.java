package com.fubang.lihaovv.entities;

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
 * 项目名称：fubangzhibo
 * 类描述：
 * 创建人：dell
 * 创建时间：2016-05-24 15:28
 * 修改人：dell
 * 修改时间：2016-05-24 15:28
 * 修改备注：
 */
public class GiftNewEntity {
    public GiftNewEntity(int groupId, String groupName, ItemBean date) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.date = date;
    }

    private int groupId;
    private String groupName;
    public ItemBean date;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ItemBean getDate() {
        return date;
    }

    public void setDate(ItemBean date) {
        this.date = date;
    }

    public static class ItemBean {
        private int giftId;
        private String giftName;
        private int giftImage;

        public int getGiftId() {
            return giftId;
        }

        public void setGiftId(int giftId) {
            this.giftId = giftId;
        }

        public String getGiftName() {
            return giftName;
        }

        public void setGiftName(String giftName) {
            this.giftName = giftName;
        }

        public int getGiftImage() {
            return giftImage;
        }

        public void setGiftImage(int giftImage) {
            this.giftImage = giftImage;
        }

        public ItemBean(int giftId, String giftName, int giftImage) {
            this.giftId = giftId;
            this.giftName = giftName;
            this.giftImage = giftImage;

        }
    }
}
