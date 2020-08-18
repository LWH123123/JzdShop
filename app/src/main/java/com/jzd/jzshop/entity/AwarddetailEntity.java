package com.jzd.jzshop.entity;

import java.util.List;

/**
 * Created by lxb on 2020/2/14.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class AwarddetailEntity {
    /**
     * result : {"total":0,"commission_total":0,"data":[]}
     */

    private AwarddetailEntity.ResultBean result;

    public AwarddetailEntity.ResultBean getResult() {
        return result;
    }

    public void setResult(AwarddetailEntity.ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * total : 0
         * commission_total : 0
         * data : []
         */

        private int total;
        private List<AwarddetailEntity.ResultBean.AwardDetailBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<AwarddetailEntity.ResultBean.AwardDetailBean> getData() {
            return data;
        }

        public void setData(List<AwarddetailEntity.ResultBean.AwardDetailBean> data) {
            this.data = data;
        }

        public static class AwardDetailBean {
            public double getCommission() {
                return commission;
            }

            public void setCommission(double commission) {
                this.commission = commission;
            }

            public String getOrdersn() {
                return ordersn;
            }

            public void setOrdersn(String ordersn) {
                this.ordersn = ordersn;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public List<AwarddetailEntity.ResultBean.AwardDetailBean.GoodBean> getGoods() {
                return goods;
            }

            public void setGoods(List<AwarddetailEntity.ResultBean.AwardDetailBean.GoodBean> goods) {
                this.goods = goods;
            }

            public AwardDetailBean(double commission, String ordersn, double money, List<AwarddetailEntity.ResultBean.AwardDetailBean.GoodBean> goods) {
                this.commission = commission;
                this.ordersn = ordersn;
                this.money = money;
                this.goods = goods;
            }

            private double commission;//申请提现金额
            private String ordersn;
            private double money;//订单金额
            private List<AwarddetailEntity.ResultBean.AwardDetailBean.GoodBean> goods;

            public static class GoodBean {
                private String title;
                private String thumb;
                private double commission;//奖励金额
                private int level;
                private int status;
                private String content;

                public GoodBean(String title, String thumb, double commission, int level, int status, String content) {
                    this.title = title;
                    this.thumb = thumb;
                    this.commission = commission;
                    this.level = level;
                    this.status = status;
                    this.content = content;
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

                public double getCommission() {
                    return commission;
                }

                public void setCommission(double commission) {
                    this.commission = commission;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }
        }
    }
}
