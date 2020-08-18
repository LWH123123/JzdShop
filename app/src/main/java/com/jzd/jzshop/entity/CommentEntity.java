package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/1/16 14:18
 */
public class CommentEntity {

    /**
     * result : {"status":2,"merch_name":"HUAWEI尚品","data":[{"id":"984","gid":"Iyk4qaLxFrHSGUVYkXUzjqAosmsMCQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png","spec_title":null,"total":1}]}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * status : 2
         * merch_name : HUAWEI尚品
         * data : [{"id":"984","gid":"Iyk4qaLxFrHSGUVYkXUzjqAosmsMCQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png","spec_title":null,"total":1}]
         */

        private int status;
        private String merch_name;
        private List<DataBean> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMerch_name() {
            return merch_name;
        }

        public void setMerch_name(String merch_name) {
            this.merch_name = merch_name;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 984
             * gid : Iyk4qaLxFrHSGUVYkXUzjqAosmsMCQMQ
             * title : 支付测试使用
             * price : 0.01
             * thumb : http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png
             * spec_title : null
             * total : 1
             */

            private String id;
            private String gid;
            private String title;
            private String price;
            private String thumb;
            private String spec_title;
            private int total;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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
