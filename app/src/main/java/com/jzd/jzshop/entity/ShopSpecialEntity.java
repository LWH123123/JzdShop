package com.jzd.jzshop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author LXB
 * @description: 重组 商品专题 实体
 * @date :2020/3/26 15:52
 */
public class ShopSpecialEntity implements Serializable {
    private String background;
    private String navstyle;
    private int showtype;
    private int pagenum;
    private int rownum;
    private List<HomeEntity.ResultBean.DataBeanX.DataBean> data;
    private HomeEntity.ResultBean.DataBeanX dataBeanX;

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

    public ShopSpecialEntity(HomeEntity.ResultBean.DataBeanX dataBean) {
        this.dataBeanX = dataBean;
        this.background = dataBean.getBackground();
        this.navstyle = dataBean.getNavstyle();
        this.showtype = dataBean.getShowtype();
        this.pagenum = dataBean.getPagenum();
        this.rownum = dataBean.getRownum();
        this.data = dataBean.getData();
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getNavstyle() {
        return navstyle;
    }

    public void setNavstyle(String navstyle) {
        this.navstyle = navstyle;
    }

    public int getShowtype() {
        return showtype;
    }

    public void setShowtype(int showtype) {
        this.showtype = showtype;
    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public int getRownum() {
        return rownum;
    }

    public void setRownum(int rownum) {
        this.rownum = rownum;
    }


    public static class DataBean implements Serializable{
        /**
         * imgurl : http://test.gtt20.com/attachment/images/2/2020/01/mEuzoRcMh8n43Y8kU4E9F9hrA0um9U.png
         * linkurl : null
         * text : 积分商城
         */

        private String imgurl;
        private Object linkurl;
        private String text;
        //手动添加字段
        private String background;
        private String navstyle;

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public String getNavstyle() {
            return navstyle;
        }

        public void setNavstyle(String navstyle) {
            this.navstyle = navstyle;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public Object getLinkurl() {
            return linkurl;
        }

        public void setLinkurl(Object linkurl) {
            this.linkurl = linkurl;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    @Override
    public String toString() {
        return "HomeMenuEntity{" +
                "background='" + background + '\'' +
                ", navstyle='" + navstyle + '\'' +
                ", showtype=" + showtype +
                ", pagenum=" + pagenum +
                ", rownum=" + rownum +
                ", data=" + data +
                ", dataBeanX=" + dataBeanX +
                '}';
    }
}
