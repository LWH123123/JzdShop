package com.jzd.jzshop.entity;

import java.util.List;

public class ShopEntity {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : banner
         * data : [{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/template/default5/banner_1.jpg","linkurl":""},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/template/default5/banner_2.jpg","linkurl":""},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/template/default5/banner_3.jpg","linkurl":""}]
         */

        private String id;
        private List<DataBean> data;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * imgurl : http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/template/default5/banner_1.jpg
             * linkurl :
             */


          /*  "gid": "ZyEtGaiwL_SAT_CSCUIbKAcGemCoFmNMfUMQ",
                    "title": "博世电钻充电钻家用手电钻12V博士电动螺丝刀工具手枪钻GSR120-LI",
                    "price": 359,
                    "thumb": "http:\/\/test.gtt20.com\/attachment\/images\/2\/2019\/11\/yCJvgyYz0F9NHF9049G1hA1CcTC1Gkk1.jpg",
                    "enoughs": 0,
                    "salegit": 0,
                    "seckill": 0*/
            private String gid;
            private String title;
            private String price;
            private String thumb;
            private int enoughs;
            private int salegit;
            private int seckill;
            private String imgurl;
            private String linkurl;

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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public int getEnoughs() {
                return enoughs;
            }

            public void setEnoughs(int enoughs) {
                this.enoughs = enoughs;
            }

            public int getSalegit() {
                return salegit;
            }

            public void setSalegit(int salegit) {
                this.salegit = salegit;
            }

            public int getSeckill() {
                return seckill;
            }

            public void setSeckill(int seckill) {
                this.seckill = seckill;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

            public String getLinkurl() {
                return linkurl;
            }

            public void setLinkurl(String linkurl) {
                this.linkurl = linkurl;
            }
        }
    }
}
