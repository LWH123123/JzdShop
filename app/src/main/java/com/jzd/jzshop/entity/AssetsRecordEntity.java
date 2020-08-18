package com.jzd.jzshop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lxb on 2020/2/11.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class AssetsRecordEntity implements Serializable {
    /**
     * result : {"total":0,"commission_total":0,"data":[]}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * total : 0
         * commission_total : 0
         * data : []
         */

        private int total;
        private List<AssetsRecordBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<AssetsRecordBean> getData() {
            return data;
        }

        public void setData(List<AssetsRecordBean> data) {
            this.data = data;
        }

        public static class AssetsRecordBean implements Serializable {
            private String ordersn;
            private String createtime;

            public String getOrdersn() {
                return ordersn;
            }

            public void setOrdersn(String ordersn) {
                this.ordersn = ordersn;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public double getCommission() {
                return commission;
            }

            public void setCommission(double commission) {
                this.commission = commission;
            }

            public List<GoodBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodBean> goods) {
                this.goods = goods;
            }

            private double commission;

            public AssetsRecordBean(String ordersn, String createtime, double commission, List<GoodBean> goods) {
                this.ordersn = ordersn;
                this.createtime = createtime;
                this.commission = commission;
                this.goods = goods;
            }

            private List<GoodBean> goods;

            public static class GoodBean {
                private String gid;
                private String title;
                private String thumb;
                private String number;
                private double commission;//预计奖励

                public GoodBean(String gid, String title, String thumb, String number, double commission) {
                    this.gid = gid;
                    this.title = title;
                    this.thumb = thumb;
                    this.number = number;
                    this.commission = commission;
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

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public double getCommission() {
                    return commission;
                }

                public void setCommission(double commission) {
                    this.commission = commission;
                }
            }
        }
    }
}
