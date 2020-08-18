package com.jzd.jzshop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lxb on 2020/2/14.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class PromotionOrderEntity implements Serializable{
    /**
     * result : {"total":0,"commission_total":0,"data":[]}
     */

    private PromotionOrderEntity.ResultBean result;

    public PromotionOrderEntity.ResultBean getResult() {
        return result;
    }

    public void setResult(PromotionOrderEntity.ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * total : 0
         * commission_total : 0
         * data : []
         */

        private int total;
        private double commission_total;
        private List<PromotionOrderEntity.ResultBean.PromoOrderBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public double getCommission_total() {
            return commission_total;
        }

        public void setCommission_total(double commission_total) {
            this.commission_total = commission_total;
        }

        public List<PromotionOrderEntity.ResultBean.PromoOrderBean> getData() {
            return data;
        }

        public void setData(List<PromotionOrderEntity.ResultBean.PromoOrderBean> data) {
            this.data = data;
        }

        public static class PromoOrderBean implements Serializable {
            private double commission;
            private int level;
            private String ordersn;
            private String createtime;

            public PromoOrderBean(double commission, int level, String ordersn, String createtime) {
                this.commission = commission;
                this.level = level;
                this.ordersn = ordersn;
                this.createtime = createtime;
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
        }
    }
}
