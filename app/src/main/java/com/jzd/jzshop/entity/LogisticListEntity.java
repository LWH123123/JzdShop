package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LWH
 * @description:
 * @date :2020/1/16 13:46
 */
public class LogisticListEntity {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * order_id : 986
         * sendtype : 1
         * bundle : A
         * data : [{"gid":"zofCiDl_mR7X_HMeTRMXMadT_AN9E_omonk8QMw","title":"2件59元】打底衫女高领内搭紧身上衣服2019秋冬新款纯棉长袖T恤潮","thumb":"http://img.alicdn.com/imgextra/i1/4145308922/O1CN01gA20HE2FmKBgy6p8N_!!0-item_pic.jpg","spec_title":"","total":1}]
         */

        private int order_id;
        private int sendtype;
        private String bundle;
        private String title;
        private String thumb;
        private int total;
        private List<DataBean> data;

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

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getSendtype() {
            return sendtype;
        }

        public void setSendtype(int sendtype) {
            this.sendtype = sendtype;
        }

        public String getBundle() {
            return bundle;
        }

        public void setBundle(String bundle) {
            this.bundle = bundle;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * gid : zofCiDl_mR7X_HMeTRMXMadT_AN9E_omonk8QMw
             * title : 2件59元】打底衫女高领内搭紧身上衣服2019秋冬新款纯棉长袖T恤潮
             * thumb : http://img.alicdn.com/imgextra/i1/4145308922/O1CN01gA20HE2FmKBgy6p8N_!!0-item_pic.jpg
             * spec_title :
             * total : 1
             */

            private String gid;
            private String title;
            private String thumb;
            private String spec_title;
            private int total;
            private int order_id;
            private int sendtype;
            private String bundle;
            private int first;
            private int finilly;

            public int getFinilly() {
                return finilly;
            }

            public void setFinilly(int finilly) {
                this.finilly = finilly;
            }

            public int getFirst() {
                return first;
            }

            public void setFirst(int first) {
                this.first = first;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public int getSendtype() {
                return sendtype;
            }

            public void setSendtype(int sendtype) {
                this.sendtype = sendtype;
            }

            public String getBundle() {
                return bundle;
            }

            public void setBundle(String bundle) {
                this.bundle = bundle;
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

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getSpec_title() {
                return spec_title;
            }

            public void setSpec_title(String spec_title) {
                this.spec_title = spec_title;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }
    }
}
