package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/4/7 13:57
 */
public class SignRecordEntity {
    /**
     * result : {"total":10,"daat":[{"text":"日常签到+2积分","date":"2020-02-15 16:30:08","points":"2","type":0},{"text":"日常签到+2积分","date":"2020-01-20 14:48:03","points":"2","type":0},{"text":"日常签到+2积分","date":"2020-01-09 19:44:49","points":"2","type":0},{"text":"日常签到+2积分","date":"2020-01-01 20:23:18","points":"2","type":0},{"text":"日常签到+2积分","date":"2019-12-31 19:20:04","points":"2","type":0},{"text":"日常签到+2积分","date":"2019-12-30 09:38:43","points":"2","type":0},{"text":"日常签到+2积分","date":"2019-12-20 21:44:40","points":"2","type":0},{"text":"日常签到+2积分","date":"2019-12-11 19:33:36","points":"2","type":0},{"text":"日常签到+2积分","date":"2019-12-07 12:22:54","points":"2","type":0},{"text":"首次签到+50积分","date":"2019-11-28 19:30:09","points":"50","type":0}]}
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
         * total : 10
         * daat : [{"text":"日常签到+2积分","date":"2020-02-15 16:30:08","points":"2","type":0},{"text":"日常签到+2积分","date":"2020-01-20 14:48:03","points":"2","type":0},{"text":"日常签到+2积分","date":"2020-01-09 19:44:49","points":"2","type":0},{"text":"日常签到+2积分","date":"2020-01-01 20:23:18","points":"2","type":0},{"text":"日常签到+2积分","date":"2019-12-31 19:20:04","points":"2","type":0},{"text":"日常签到+2积分","date":"2019-12-30 09:38:43","points":"2","type":0},{"text":"日常签到+2积分","date":"2019-12-20 21:44:40","points":"2","type":0},{"text":"日常签到+2积分","date":"2019-12-11 19:33:36","points":"2","type":0},{"text":"日常签到+2积分","date":"2019-12-07 12:22:54","points":"2","type":0},{"text":"首次签到+50积分","date":"2019-11-28 19:30:09","points":"50","type":0}]
         */

        private int total;
        private List<DaatBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DaatBean> getDaat() {
            return data;
        }

        public void setDaat(List<DaatBean> daat) {
            this.data = daat;
        }

        public static class DaatBean {
            /**
             * text : 日常签到+2积分
             * date : 2020-02-15 16:30:08
             * points : 2
             * type : 0
             */

            private String text;
            private String date;
            private String points;
            private int type;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
