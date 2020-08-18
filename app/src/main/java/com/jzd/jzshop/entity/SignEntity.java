package com.jzd.jzshop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author LXB
 * @description: 签到首页
 * @date :2020/4/7 14:46
 */
public class SignEntity {
    /**
     * result : {"nickname":"向阳而生","avatar":"http://test.gtt20.com/attachment/images/2/2020/02/VQxfQx7e3MANkPq58t58w75XtMfPyA.jpg","points":999562,"continue_day":0,"all_day":10,"can_sign":1,"signold_type":1,"signold_price":"4","sign_rule":"","continue_rule":[{"day":3,"points":30,"signed":2},{"day":5,"points":50,"signed":3},{"day":7,"points":70,"signed":3},{"day":10,"points":100,"signed":3},{"day":20,"points":200,"signed":3},{"day":28,"points":300,"signed":3}],"all_rule":[{"day":5,"points":500,"signed":2},{"day":10,"points":1000,"signed":2},{"day":15,"points":1500,"signed":3},{"day":20,"points":2000,"signed":3},{"day":25,"points":2500,"signed":3}],"calendar":[{"title":"","color":"","year":"2020","month":"04","day":"01","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"02","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"03","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"04","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"05","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"06","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"07","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"08","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"09","signed":0,"signold":0},{"title":"端午奖励","color":"#00ff00","year":"2020","month":"04","day":"10","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"11","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"12","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"13","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"14","signed":0,"signold":0},{"title":"中秋奖励","color":"#0000ff","year":"2020","month":"04","day":"15","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"16","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"17","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"18","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"19","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"20","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"21","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"22","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"23","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"24","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"25","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"26","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"27","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"28","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"29","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"30","signed":0,"signold":0}]}
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
         * nickname : 向阳而生
         * avatar : http://test.gtt20.com/attachment/images/2/2020/02/VQxfQx7e3MANkPq58t58w75XtMfPyA.jpg
         * points : 999562
         * continue_day : 0
         * all_day : 10
         * can_sign : 1
         * signold_type : 1
         * signold_price : 4
         * sign_rule :
         * continue_rule : [{"day":3,"points":30,"signed":2},{"day":5,"points":50,"signed":3},{"day":7,"points":70,"signed":3},{"day":10,"points":100,"signed":3},{"day":20,"points":200,"signed":3},{"day":28,"points":300,"signed":3}]
         * all_rule : [{"day":5,"points":500,"signed":2},{"day":10,"points":1000,"signed":2},{"day":15,"points":1500,"signed":3},{"day":20,"points":2000,"signed":3},{"day":25,"points":2500,"signed":3}]
         * calendar : [{"title":"","color":"","year":"2020","month":"04","day":"01","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"02","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"03","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"04","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"05","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"06","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"07","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"08","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"09","signed":0,"signold":0},{"title":"端午奖励","color":"#00ff00","year":"2020","month":"04","day":"10","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"11","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"12","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"13","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"14","signed":0,"signold":0},{"title":"中秋奖励","color":"#0000ff","year":"2020","month":"04","day":"15","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"16","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"17","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"18","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"19","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"20","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"21","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"22","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"23","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"24","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"25","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"26","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"27","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"28","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"29","signed":0,"signold":0},{"title":"","color":"","year":"2020","month":"04","day":"30","signed":0,"signold":0}]
         */

        private String nickname;
        private String avatar;
        private int points;
        private int continue_day;
        private int all_day;
        private int can_sign;
        private int signold_type;
        private String signold_price;
        private String sign_rule;
        private List<ContinueRuleBean> continue_rule;
        private List<AllRuleBean> all_rule;
        private List<CalendarBean> calendar;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public int getContinue_day() {
            return continue_day;
        }

        public void setContinue_day(int continue_day) {
            this.continue_day = continue_day;
        }

        public int getAll_day() {
            return all_day;
        }

        public void setAll_day(int all_day) {
            this.all_day = all_day;
        }

        public int getCan_sign() {
            return can_sign;
        }

        public void setCan_sign(int can_sign) {
            this.can_sign = can_sign;
        }

        public int getSignold_type() {
            return signold_type;
        }

        public void setSignold_type(int signold_type) {
            this.signold_type = signold_type;
        }

        public String getSignold_price() {
            return signold_price;
        }

        public void setSignold_price(String signold_price) {
            this.signold_price = signold_price;
        }

        public String getSign_rule() {
            return sign_rule;
        }

        public void setSign_rule(String sign_rule) {
            this.sign_rule = sign_rule;
        }

        public List<ContinueRuleBean> getContinue_rule() {
            return continue_rule;
        }

        public void setContinue_rule(List<ContinueRuleBean> continue_rule) {
            this.continue_rule = continue_rule;
        }

        public List<AllRuleBean> getAll_rule() {
            return all_rule;
        }

        public void setAll_rule(List<AllRuleBean> all_rule) {
            this.all_rule = all_rule;
        }

        public List<CalendarBean> getCalendar() {
            return calendar;
        }

        public void setCalendar(List<CalendarBean> calendar) {
            this.calendar = calendar;
        }

        public static class ContinueRuleBean {
            /**
             * day : 3
             * points : 30
             * signed : 2
             */

            private int day;
            private int points;
            private int signed;

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getPoints() {
                return points;
            }

            public void setPoints(int points) {
                this.points = points;
            }

            public int getSigned() {
                return signed;
            }

            public void setSigned(int signed) {
                this.signed = signed;
            }
        }

        public static class AllRuleBean {
            /**
             * day : 5
             * points : 500
             * signed : 2
             */

            private int day;
            private int points;
            private int signed;

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getPoints() {
                return points;
            }

            public void setPoints(int points) {
                this.points = points;
            }

            public int getSigned() {
                return signed;
            }

            public void setSigned(int signed) {
                this.signed = signed;
            }
        }

        public static class CalendarBean  {
            /**
             * title :
             * color :
             * year : 2020
             * month : 04
             * day : 01
             * signed : 0
             * signold : 0
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
}
