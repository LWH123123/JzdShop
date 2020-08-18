package com.jzd.jzshop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lxb on 2020/2/13.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class AwardListEntity implements Serializable{
    /**
     * result : {"total":0,"commission_total":0,"data":[]}
     */

    private AwardListEntity.ResultBean result;

    public AwardListEntity.ResultBean getResult() {
        return result;
    }

    public void setResult(AwardListEntity.ResultBean result) {
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
        private List<AwardListEntity.ResultBean.AssetsRecordBean> data;

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

        public List<AwardListEntity.ResultBean.AssetsRecordBean> getData() {
            return data;
        }

        public void setData(List<AwardListEntity.ResultBean.AssetsRecordBean> data) {
            this.data = data;
        }

        public static class AssetsRecordBean implements Serializable {
            private String log_id;
            private int type;
            private int status;
            private String deal_time;
            private double commission;
            private double real_money;
            private double deduction_money;

            public AssetsRecordBean(String log_id, int type, int status, String deal_time, double commission, double real_money, double deduction_money) {
                this.log_id = log_id;
                this.type = type;
                this.status = status;
                this.deal_time = deal_time;
                this.commission = commission;
                this.real_money = real_money;
                this.deduction_money = deduction_money;
            }

            public String getLog_id() {
                return log_id;
            }

            public void setLog_id(String log_id) {
                this.log_id = log_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getDeal_time() {
                return deal_time;
            }

            public void setDeal_time(String deal_time) {
                this.deal_time = deal_time;
            }

            public double getCommission() {
                return commission;
            }

            public void setCommission(double commission) {
                this.commission = commission;
            }

            public double getReal_money() {
                return real_money;
            }

            public void setReal_money(double real_money) {
                this.real_money = real_money;
            }

            public double getDeduction_money() {
                return deduction_money;
            }

            public void setDeduction_money(double deduction_money) {
                this.deduction_money = deduction_money;
            }

        }
    }
}
