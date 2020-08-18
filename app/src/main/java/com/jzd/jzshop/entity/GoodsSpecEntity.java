package com.jzd.jzshop.entity;

import java.util.Date;
import java.util.List;

public class GoodsSpecEntity {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {

        private String thumb;
        private String default_price;
        private int show_total;
        private String total;
        private int show_commission;
        private int commission;
        private List<ListBean> list;
        private List<DataBean> data;

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getDefault_price() {
            return default_price;
        }

        public void setDefault_price(String default_price) {
            this.default_price = default_price;
        }

        public int getShow_total() {
            return show_total;
        }

        public void setShow_total(int show_total) {
            this.show_total = show_total;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public int getShow_commission() {
            return show_commission;
        }

        public void setShow_commission(int show_commission) {
            this.show_commission = show_commission;
        }

        public int getCommission() {
            return commission;
        }

        public void setCommission(int commission) {
            this.commission = commission;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class ListBean {

            private String type;
            private String title;
            private List<ItemsBean> items;

            public String getTitle() {

                return title;
            }

            public String getType() {

                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public static class ItemsBean {
                /**
                 * lid : 291
                 * title : 长裙银色
                 * thumb : http://test.gtt20.com/attachment/images/2/2019/10/ul1fP1CfOipfF1pWDfFpfOefAPYNLLmM.jpg
                 */

                private String lid;
                private String title;
                private String thumb;

                public String getLid() {
                    return lid;
                }

                public void setLid(String lid) {
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

        public static class DataBean {
            /**
             * optionid : 405
             * specs_id : 291_305
             * total : 99
             * price : 99.00
             */

            private String optionid;
            private String specs_id;
            private String total;
            private String price;

            public String getOptionid() {
                return optionid;
            }

            public void setOptionid(String optionid) {
                this.optionid = optionid;
            }

            public String getSpecs_id() {
                return specs_id;
            }

            public void setSpecs_id(String specs_id) {
                this.specs_id = specs_id;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
