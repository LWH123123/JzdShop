package com.jzd.jzshop.entity;

import java.util.List;

public class BalanceDataEntity {


    /**
     * result : {"money":"75913.76","applytype":[{"id":"wechat"},{"id":"alipay"},{"id":"bankcard"}],"banklist":[{"bank_id":"5","bank_name":"中国工商银行"},{"bank_id":"6","bank_name":"中国农业银行"},{"bank_id":"7","bank_name":"中国建设银行"}],"lastinfo":{"realname":"李武辉","alipay":"18235822259","bankcard":"","bankname":""},"poundage":{"percent":"0","begin":"0","end":"0"}}
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
         * money : 75913.76
         * applytype : [{"id":"wechat"},{"id":"alipay"},{"id":"bankcard"}]
         * banklist : [{"bank_id":"5","bank_name":"中国工商银行"},{"bank_id":"6","bank_name":"中国农业银行"},{"bank_id":"7","bank_name":"中国建设银行"}]
         * lastinfo : {"realname":"李武辉","alipay":"18235822259","bankcard":"","bankname":""}
         * poundage : {"percent":"0","begin":"0","end":"0"}
         */

        private String money;
        private String withdrawmoney;
        private LastinfoBean lastinfo;
        private PoundageBean poundage;
        private List<ApplytypeBean> applytype;
        private List<BanklistBean> banklist;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getWithdrawmoney() {
            return withdrawmoney;
        }

        public void setWithdrawmoney(String withdrawmoney) {
            this.withdrawmoney = withdrawmoney;
        }

        public LastinfoBean getLastinfo() {
            return lastinfo;
        }

        public void setLastinfo(LastinfoBean lastinfo) {
            this.lastinfo = lastinfo;
        }

        public PoundageBean getPoundage() {
            return poundage;
        }

        public void setPoundage(PoundageBean poundage) {
            this.poundage = poundage;
        }

        public List<ApplytypeBean> getApplytype() {
            return applytype;
        }

        public void setApplytype(List<ApplytypeBean> applytype) {
            this.applytype = applytype;
        }

        public List<BanklistBean> getBanklist() {
            return banklist;
        }

        public void setBanklist(List<BanklistBean> banklist) {
            this.banklist = banklist;
        }

        public static class LastinfoBean {
            /**
             * realname : 李武辉
             * alipay : 18235822259
             * bankcard :
             * bankname :
             */

            private String realname;
            private String alipay;
            private String bankcard;
            private String bankname;

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getAlipay() {
                return alipay;
            }

            public void setAlipay(String alipay) {
                this.alipay = alipay;
            }

            public String getBankcard() {
                return bankcard;
            }

            public void setBankcard(String bankcard) {
                this.bankcard = bankcard;
            }

            public String getBankname() {
                return bankname;
            }

            public void setBankname(String bankname) {
                this.bankname = bankname;
            }
        }

        public static class PoundageBean {
            /**
             * percent : 0
             * begin : 0
             * end : 0
             */

            private String percent;
            private String begin;
            private String end;

            public String getPercent() {
                return percent;
            }

            public void setPercent(String percent) {
                this.percent = percent;
            }

            public String getBegin() {
                return begin;
            }

            public void setBegin(String begin) {
                this.begin = begin;
            }

            public String getEnd() {
                return end;
            }

            public void setEnd(String end) {
                this.end = end;
            }
        }

        public static class ApplytypeBean {
            /**
             * id : wechat
             */

            private String id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public static class BanklistBean {
            /**
             * bank_id : 5
             * bank_name : 中国工商银行
             */

            private String bank_id;
            private String bank_name;

            public String getBank_id() {
                return bank_id;
            }

            public void setBank_id(String bank_id) {
                this.bank_id = bank_id;
            }

            public String getBank_name() {
                return bank_name;
            }

            public void setBank_name(String bank_name) {
                this.bank_name = bank_name;
            }
        }
    }
}
