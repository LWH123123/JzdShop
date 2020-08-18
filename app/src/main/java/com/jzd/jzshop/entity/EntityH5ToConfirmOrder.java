package com.jzd.jzshop.entity;

/**
 * @author LXB
 * @description: h5 跳转立即购买
 * @date :2019/12/16 17:46
 */
public class EntityH5ToConfirmOrder {

    /**
     * gid : mk14XO7r_riA_eNSFhfIXm5D6JzonO6s8Mg
     * optionid : 0
     * gift_id : 0
     * total : 1
     */

    private String gid;
    private int optionid;
    private String gift_id;
    private int total;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public int getOptionid() {
        return optionid;
    }

    public void setOptionid(int optionid) {
        this.optionid = optionid;
    }

    public String getGift_id() {
        return gift_id;
    }

    public void setGift_id(String gift_id) {
        this.gift_id = gift_id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
