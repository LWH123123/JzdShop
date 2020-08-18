package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/4/13 14:41
 */
public class AttractInvestmentEntity {


    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean{
        private String wait;
        private String ok;
        private List<DataBean> data;
        public String getWait() {
            return wait;
        }

        public void setWait(String wait) {
            this.wait = wait;
        }

        public String getOk() {
            return ok;
        }

        public void setOk(String ok) {
            this.ok = ok;
        }


        public List<DataBean> getDataBeans() {
            return data;
        }

        public void setDataBeans(List<DataBean> dataBeans) {
            this.data = dataBeans;
        }

        public static class DataBean {
            private String name;
            private String logo;
            private double money;


            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }


        }





    }
}
