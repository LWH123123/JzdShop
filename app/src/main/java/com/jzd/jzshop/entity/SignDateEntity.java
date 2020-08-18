package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description: 获取 签到日期
 * @date :2020/4/7 14:47
 */
public class SignDateEntity {
    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * title :
         * color :
         * year : 2020
         * month : 04
         * day : 01
         * signed : 0
         * signold : 1
         */

        private String title;
        private String color;
        private String year;
        private String month;
        private String day;
        private int signed;
        private int signold;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public int getSigned() {
            return signed;
        }

        public void setSigned(int signed) {
            this.signed = signed;
        }

        public int getSignold() {
            return signold;
        }

        public void setSignold(int signold) {
            this.signold = signold;
        }
    }
}
