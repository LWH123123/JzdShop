package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author lwh
 * @description : 公用商品规格 实体
 * @date 2020/5/13.
 */
public class BaseShopSpecEntity {

    private String type;
    private String title;
    private List<ItemsBean> items;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean{
        private int lid;
        private String title;
        private String thumb;

        public int getLid() {
            return lid;
        }

        public void setLid(int lid) {
            this.lid = lid;
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
    }

}
