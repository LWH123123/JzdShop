package com.jzd.jzshop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author LXB
 * @description: 首页 热点公告
 * @date :2020/3/29 11:35
 */
public class HomeNoticeEntity implements Serializable {

    private String background;
    private String color;
    private String iconurl;
    private int speed;
    private  List<HomeEntity.ResultBean.DataBeanX.DataBean> data;
    private HomeEntity.ResultBean.DataBeanX dataBeanX;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }



    public List<HomeEntity.ResultBean.DataBeanX.DataBean> getData() {
        return data;
    }

    public void setData(List<HomeEntity.ResultBean.DataBeanX.DataBean> data) {
        this.data = data;
    }

    public HomeEntity.ResultBean.DataBeanX getDataBeanX() {
        return dataBeanX;
    }

    public void setDataBeanX(HomeEntity.ResultBean.DataBeanX dataBeanX) {
        this.dataBeanX = dataBeanX;
    }

    public HomeNoticeEntity(HomeEntity.ResultBean.DataBeanX dataBean) {
        this.dataBeanX = dataBean;
        this.background= dataBean.getBackground();
        this.color = dataBean.getColor();
        this.iconurl = dataBean.getIconurl();
        this.speed = dataBean.getSpeed();
        this.data  = dataBean.getData();
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }


    public static class DataBean {
        /**
         * linkurl : http://test.gtt20.com/app/index.phpm=api_shopv1&c=entry&a=app&do=app&r=member.url&returnUrl=sale.coupon&user_token=
         * title : 2020年，B站是营销趋势吗？
         */

        private Object linkurl;
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getLinkurl() {
            return linkurl;
        }

        public void setLinkurl(Object linkurl) {
            this.linkurl = linkurl;
        }


    }

    @Override
    public String toString() {
        return "HomeNoticeEntity{" +
                "background='" + background + '\'' +
                ", color='" + color + '\'' +
                ", iconurl='" + iconurl + '\'' +
                ", speed=" + speed +
                ", data=" + data +
                ", dataBeanX=" + dataBeanX +
                '}';
    }
}
