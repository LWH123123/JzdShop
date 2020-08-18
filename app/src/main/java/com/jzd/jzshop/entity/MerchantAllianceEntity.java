package com.jzd.jzshop.entity;

import java.util.List;

public class MerchantAllianceEntity {

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
         * total : 2
         * data : [{"imgurl":"http://test.gtt20.com/attachment/images/2/2019/11/J0Y01AoA422VzvqFgg25QIywuOcovV.jpg","linkurl":null},{"imgurl":"http://test.gtt20.com/attachment/images/2/2019/11/gvwOBokwpw0Z3p93f0O03Ou99PuUdk.jpg","linkurl":null}]
         */

        private String id;
        private int total;
        private List<DataBean> data;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * imgurl : http://test.gtt20.com/attachment/images/2/2019/11/J0Y01AoA422VzvqFgg25QIywuOcovV.jpg
             * linkurl : null
             */

            private String imgurl;
            private String linkurl;
            private String merch_id;
            private String merch_name;
            private String merch_logo;

            public String getMerch_id() {
                return merch_id;
            }

            public void setMerch_id(String merch_id) {
                this.merch_id = merch_id;
            }

            public String getMerch_name() {
                return merch_name;
            }

            public void setMerch_name(String merch_name) {
                this.merch_name = merch_name;
            }

            public String getMerch_logo() {
                return merch_logo;
            }

            public void setMerch_logo(String merch_logo) {
                this.merch_logo = merch_logo;
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
