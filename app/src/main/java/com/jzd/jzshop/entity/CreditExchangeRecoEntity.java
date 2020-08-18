package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/5/9 17:13
 */
public class CreditExchangeRecoEntity {
    /**
     * result : {"total":1,"data":[{"log_id":"nxcdWql2qpPnUFRbAF7D_MoA_EupnCAMoMg","logno":"EE20200511170235049722","title":"红包001","optiontitle":null,"thumb":"http://test.gtt20.com/attachment/images/2/2019/10/B81nDSXfS8mP1sEMaYQ8111MgDnA51.jpg","type":3,"type1":0,"credit":"30","money":"0.00","status_str":"待领取"}]}
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
         * total : 1
         * data : [{"log_id":"nxcdWql2qpPnUFRbAF7D_MoA_EupnCAMoMg","logno":"EE20200511170235049722","title":"红包001","optiontitle":null,"thumb":"http://test.gtt20.com/attachment/images/2/2019/10/B81nDSXfS8mP1sEMaYQ8111MgDnA51.jpg","type":3,"type1":0,"credit":"30","money":"0.00","status_str":"待领取"}]
         */

        private int total;
        private List<DataBean> data;

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
             * log_id : nxcdWql2qpPnUFRbAF7D_MoA_EupnCAMoMg
             * logno : EE20200511170235049722
             * title : 红包001
             * optiontitle : null
             * thumb : http://test.gtt20.com/attachment/images/2/2019/10/B81nDSXfS8mP1sEMaYQ8111MgDnA51.jpg
             * type : 3
             * type1 : 0
             * credit : 30
             * money : 0.00
             * status_str : 待领取
             */

            private String log_id;
            private String logno;
            private String title;
            private Object optiontitle;
            private String thumb;
            private int type;
            private int type1;
            private String credit;
            private String money;
            private String status_str;

            private int state;
            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }
            public String getLog_id() {
                return log_id;
            }

            public void setLog_id(String log_id) {
                this.log_id = log_id;
            }

            public String getLogno() {
                return logno;
            }

            public void setLogno(String logno) {
                this.logno = logno;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getOptiontitle() {
                return optiontitle;
            }

            public void setOptiontitle(Object optiontitle) {
                this.optiontitle = optiontitle;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getType1() {
                return type1;
            }

            public void setType1(int type1) {
                this.type1 = type1;
            }

            public String getCredit() {
                return credit;
            }

            public void setCredit(String credit) {
                this.credit = credit;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getStatus_str() {
                return status_str;
            }

            public void setStatus_str(String status_str) {
                this.status_str = status_str;
            }
        }
    }
}
