package com.jzd.jzshop.entity;

import java.io.Serializable;
import java.util.List;

public class HomeEntity implements Serializable{

    public List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    //-----------------------object------------------

    public ResultBeanObject getResultBeanObject() {
        return resultBeanObject;
    }

    public void setResultBeanObject(ResultBeanObject resultBeanObject) {
        this.resultBeanObject = resultBeanObject;
    }

    public ResultBeanObject resultBeanObject;

    public static class ResultBeanObject implements Serializable{
        private String id;
        private DataBeanObject data;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public DataBeanObject getData() {
            return data;
        }

        public void setData(DataBeanObject data) {
            this.data = data;
        }
    }

    public static class DataBeanObject implements Serializable {
        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }

        private String percent;
    }
    //-----------------------object------------------


    public static class ResultBean implements Serializable {
        /**
         * id : menu
         * data : [{"background":"#ffffff","navstyle":"normal","showtype":0,"pagenum":8,"rownum":4,"data":[{"imgurl":"http://test.gtt20.com/attachment/images/2/2020/01/mEuzoRcMh8n43Y8kU4E9F9hrA0um9U.png","linkurl":null,"text":"积分商城"},{"imgurl":"http://test.gtt20.com/attachment/images/2/2020/01/p5btf85AFax8FNu4T8AA5F188dBxZK.png","linkurl":null,"text":"整点秒杀"},{"imgurl":"http://test.gtt20.com/attachment/images/2/2020/01/sX3zIMfMbSf4LfZOMrz74euXXxl7Pi.png","linkurl":null,"text":"全民社区"},{"imgurl":"http://test.gtt20.com/attachment/images/2/2020/01/N62aaEkgi5zKa9Ir5vsaS9sKiasA9k.png","linkurl":null,"text":"商家联盟"}]},{"background":"#ffff80","navstyle":"radius","showtype":1,"pagenum":3,"rownum":3,"data":[{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-1.png","linkurl":null,"text":"按钮文字1"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-2.png","linkurl":null,"text":"按钮文字2"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-3.png","linkurl":null,"text":"按钮文字3"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-4.png","linkurl":null,"text":"按钮文字4"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-1.png","linkurl":null,"text":"按钮文字1"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-1.png","linkurl":null,"text":"按钮文字1"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-1.png","linkurl":null,"text":"按钮文字1"}]},{"background":"#8080ff","navstyle":"circle","showtype":1,"pagenum":6,"rownum":3,"data":[{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-1.png","linkurl":null,"text":"按钮文字1"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-2.png","linkurl":null,"text":"按钮文字2"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-3.png","linkurl":null,"text":"按钮文字3"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-4.png","linkurl":null,"text":"按钮文字4"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-1.png","linkurl":null,"text":"按钮文字1"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-1.png","linkurl":null,"text":"按钮文字1"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-1.png","linkurl":null,"text":"按钮文字1"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-1.png","linkurl":null,"text":"按钮文字1"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-1.png","linkurl":null,"text":"按钮文字1"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-1.png","linkurl":null,"text":"按钮文字1"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-1.png","linkurl":null,"text":"按钮文字1"},{"imgurl":"http://test.gtt20.com/addons/ewei_shopv2/plugin/diypage/static/images/default/icon-1.png","linkurl":null,"text":"按钮文字1"}]}]
         */

        private String id;
        private List<DataBeanX> data;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<DataBeanX> getData() {
            return data;
        }

        public void setData(List<DataBeanX> data) {
            this.data = data;
        }

        public static class DataBeanX implements Serializable {
            /**
             * background : #ffffff
             * navstyle : normal
             * showtype : 0
             * pagenum : 8
             * rownum : 4
             * data : [{"imgurl":"http://test.gtt20.com/attachment/images/2/2020/01/mEuzoRcMh8n43Y8kU4E9F9hrA0um9U.png","linkurl":null,"text":"积分商城"},{"imgurl":"http://test.gtt20.com/attachment/images/2/2020/01/p5btf85AFax8FNu4T8AA5F188dBxZK.png","linkurl":null,"text":"整点秒杀"},{"imgurl":"http://test.gtt20.com/attachment/images/2/2020/01/sX3zIMfMbSf4LfZOMrz74euXXxl7Pi.png","linkurl":null,"text":"全民社区"},{"imgurl":"http://test.gtt20.com/attachment/images/2/2020/01/N62aaEkgi5zKa9Ir5vsaS9sKiasA9k.png","linkurl":null,"text":"商家联盟"}]
             */

            private String background;
            private String navstyle;
            private int showtype;
            private int pagenum;
            private int rownum;
            private List<DataBean> data;
            private String imgurl;
            private String linkurl;
            private String gid;
            private String title;
            //private double price;
            private String price;
            private String thumb;
            private int enoughs;
            private int salegit;
            private int seckill;
            //君子权 广告位
            private String percent;
            // notice
            private String iconurl;
            private String color;
            private int speed;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
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

//            public double getPrice() {
//                return price;
//            }
//
//            public void setPrice(double price) {
//                this.price = price;
//            }

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

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public String getPercent() {
                return percent;
            }

            public void setPercent(String percent) {
                this.percent = percent;
            }

            public static class DataBean implements Serializable {
                /**
                 * imgurl : http://test.gtt20.com/attachment/images/2/2020/01/mEuzoRcMh8n43Y8kU4E9F9hrA0um9U.png
                 * linkurl : null
                 * text : 积分商城
                 */

                private String imgurl;
                private String linkurl;
                //手动添加字段
                private String text;     // menu
                private String title;       // notice


                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
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

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }
            }
        }
    }
}
