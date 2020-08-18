package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/5/11 9:41
 */
public class PartakeRecordEntity {
    /**
     * result : {"total":151,"member":{"points":1004885},"data":[{"log_id":"fyHYlKD1u_pAj_jipYuYzWj2LyjpvnZsKgMQ","title":null,"thumb":null,"time":"2020-05-07 11:37:16","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"VwIYZHriM9E3ZIwZaWezZmbpcnXsHkMQ","title":null,"thumb":null,"time":"2020-05-06 18:54:55","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"mCktSRrw9iOy9V6MDGUD_HzA_G1pnEZs0Mg","title":null,"thumb":null,"time":"2020-04-24 20:10:03","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"wRXN3bfj9NyiBoaNDA6GmS7pmRTcUMg","title":null,"thumb":null,"time":"2020-01-15 20:27:40","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"wb0IXP7m_MhX_n73Ar6fDabWmDSpmAIcsMg","title":null,"thumb":null,"time":"2019-12-27 14:40:07","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"yIg05Lr9oXSiZIQKvWGSjcMpmcPikgMw","title":null,"thumb":null,"time":"2019-12-27 13:37:28","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"mtG9LGx_IhBX_Hv0udNXRB9zmpIL5mcwMw","title":null,"thumb":null,"time":"2019-12-26 20:27:26","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"amaICHyhC_cXz_TiJ4KbqCqzi2ppxmqMuUMQ","title":null,"thumb":null,"time":"2019-12-23 20:50:33","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"n9x5ffk_ThXA_yzB18YAp57W6pYQrmMoMw","title":null,"thumb":null,"time":"2019-12-21 13:33:09","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"zhbdLtvn_AyX_eLCEhYfXfWWWD4pmsrMsMg","title":null,"thumb":null,"time":"2019-12-21 13:26:36","credit":null,"money":null,"type":0,"status_str":"已兑换"}]}
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
         * total : 151
         * member : {"points":1004885}
         * data : [{"log_id":"fyHYlKD1u_pAj_jipYuYzWj2LyjpvnZsKgMQ","title":null,"thumb":null,"time":"2020-05-07 11:37:16","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"VwIYZHriM9E3ZIwZaWezZmbpcnXsHkMQ","title":null,"thumb":null,"time":"2020-05-06 18:54:55","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"mCktSRrw9iOy9V6MDGUD_HzA_G1pnEZs0Mg","title":null,"thumb":null,"time":"2020-04-24 20:10:03","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"wRXN3bfj9NyiBoaNDA6GmS7pmRTcUMg","title":null,"thumb":null,"time":"2020-01-15 20:27:40","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"wb0IXP7m_MhX_n73Ar6fDabWmDSpmAIcsMg","title":null,"thumb":null,"time":"2019-12-27 14:40:07","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"yIg05Lr9oXSiZIQKvWGSjcMpmcPikgMw","title":null,"thumb":null,"time":"2019-12-27 13:37:28","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"mtG9LGx_IhBX_Hv0udNXRB9zmpIL5mcwMw","title":null,"thumb":null,"time":"2019-12-26 20:27:26","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"amaICHyhC_cXz_TiJ4KbqCqzi2ppxmqMuUMQ","title":null,"thumb":null,"time":"2019-12-23 20:50:33","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"n9x5ffk_ThXA_yzB18YAp57W6pYQrmMoMw","title":null,"thumb":null,"time":"2019-12-21 13:33:09","credit":null,"money":null,"type":0,"status_str":"已兑换"},{"log_id":"zhbdLtvn_AyX_eLCEhYfXfWWWD4pmsrMsMg","title":null,"thumb":null,"time":"2019-12-21 13:26:36","credit":null,"money":null,"type":0,"status_str":"已兑换"}]
         */

        private int total;
        private MemberBean member;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class MemberBean {
            /**
             * points : 1004885
             */

            private int points;

            public int getPoints() {
                return points;
            }

            public void setPoints(int points) {
                this.points = points;
            }
        }

        public static class DataBean {
            /**
             * log_id : fyHYlKD1u_pAj_jipYuYzWj2LyjpvnZsKgMQ
             * title : null
             * thumb : null
             * time : 2020-05-07 11:37:16
             * credit : null
             * money : null
             * type : 0
             * status_str : 已兑换
             */

            private String log_id;
            private String title;
            private String thumb;
            private String time;
            private String credit;
            private String money;
            private int type;
            private String status_str;

            public String getLog_id() {
                return log_id;
            }

            public void setLog_id(String log_id) {
                this.log_id = log_id;
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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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
