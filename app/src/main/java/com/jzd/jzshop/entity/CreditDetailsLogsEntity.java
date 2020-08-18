package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author lwh
 * @description :
 * @date 2020/5/13.
 */
public class CreditDetailsLogsEntity {

    /**
     * result : {"total":1,"data":[{"avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erBvLG9YJ0zvCw1xLMbLUyvPfKuemibmXGibMBiaohDF7mh1z9oV7HMQcL89oSeAicrlmn74g8U1OBv8A/132","nickname":"木南","time":"2020/05/13 09:33"}]}
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
         * data : [{"avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erBvLG9YJ0zvCw1xLMbLUyvPfKuemibmXGibMBiaohDF7mh1z9oV7HMQcL89oSeAicrlmn74g8U1OBv8A/132","nickname":"木南","time":"2020/05/13 09:33"}]
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
             * avatar : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erBvLG9YJ0zvCw1xLMbLUyvPfKuemibmXGibMBiaohDF7mh1z9oV7HMQcL89oSeAicrlmn74g8U1OBv8A/132
             * nickname : 木南
             * time : 2020/05/13 09:33
             */

            private String avatar;
            private String nickname;
            private String time;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
