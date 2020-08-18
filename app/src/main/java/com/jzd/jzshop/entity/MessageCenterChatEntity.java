package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/4/26 11:33
 */
public class MessageCenterChatEntity {
    /**
     * result : {"total":2,"data":[{"receive_id":"yNA4fDFm_F9A_nHiQPxeWiD2SIMunS1AMg","ltype":"member","name":"hjjj","logo":"","time":"2020-04-26 10:36:35","msg":"来咯来咯mo","tips_cnt":2},{"receive_id":"y4eDFfm_t88A_ibzEQeWp2U2Sur6WnAMw","ltype":"merch","name":"向阳而生","logo":"http://test.gtt20.com/attachment/images/2/2020/04/JrffuEff5vSYnXEtXvYhOyxc8hrrHc.jpg","time":"2020-04-26 10:36:35","msg":"来咯来咯mo","tips_cnt":2}]}
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
         * total : 2
         * data : [{"receive_id":"yNA4fDFm_F9A_nHiQPxeWiD2SIMunS1AMg","ltype":"member","name":"hjjj","logo":"","time":"2020-04-26 10:36:35","msg":"来咯来咯mo","tips_cnt":2},{"receive_id":"y4eDFfm_t88A_ibzEQeWp2U2Sur6WnAMw","ltype":"merch","name":"向阳而生","logo":"http://test.gtt20.com/attachment/images/2/2020/04/JrffuEff5vSYnXEtXvYhOyxc8hrrHc.jpg","time":"2020-04-26 10:36:35","msg":"来咯来咯mo","tips_cnt":2}]
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
             * receive_id : yNA4fDFm_F9A_nHiQPxeWiD2SIMunS1AMg
             * ltype : member
             * name : hjjj
             * logo :
             * time : 2020-04-26 10:36:35
             * msg : 来咯来咯mo
             * tips_cnt : 2
             */

            private String receive_id;
            private String ltype;
            private String name;
            private String logo;
            private String time;
            private String msg;
            private int tips_cnt;

            public String getReceive_id() {
                return receive_id;
            }

            public void setReceive_id(String receive_id) {
                this.receive_id = receive_id;
            }

            public String getLtype() {
                return ltype;
            }

            public void setLtype(String ltype) {
                this.ltype = ltype;
            }

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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public int getTips_cnt() {
                return tips_cnt;
            }

            public void setTips_cnt(int tips_cnt) {
                this.tips_cnt = tips_cnt;
            }
        }
    }
}
