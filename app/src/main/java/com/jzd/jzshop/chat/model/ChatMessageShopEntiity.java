package com.jzd.jzshop.chat.model;

/**
 * @author LXB
 * @description: msg_type=2时，商品
 * @date :2020/4/22 18:17
 */
public class ChatMessageShopEntiity {
    /**
     * gid : QwBIKKB2i_jAq_AyOEZYODiGVWIoHmicPQMQ
     * title : 联想吃鸡游戏本笔记本电脑轻薄便携商务办公学生分期14/15.6英寸
     * thumb : http://test.gtt20.com/attachment/images/2/2019/12/g6gG5jg1E2cYe1Qy2zgXN1e1C7QNx77g.jpg
     * price : 0.00
     */

    private String gid;
    private String title;
    private String thumb;
    private String price;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
