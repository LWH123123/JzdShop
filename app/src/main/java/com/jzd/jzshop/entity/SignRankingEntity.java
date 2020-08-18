package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/4/7 11:13
 */
public class SignRankingEntity {
    /**
     * result : {"total":10,"daat":[{"nickname":" ","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIwEwdCD6CWFsNWbvkXhficeUVEC9UYgTmpS9jzjOaDdqeS46GjfoJm7dwa3oaicGRXRpoEeytDpx7g/132","day":"2"}]}
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
         * daat : [{"nickname":" ","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIwEwdCD6CWFsNWbvkXhficeUVEC9UYgTmpS9jzjOaDdqeS46GjfoJm7dwa3oaicGRXRpoEeytDpx7g/132","day":"2"}]
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
             * nickname :  
             * avatar : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIwEwdCD6CWFsNWbvkXhficeUVEC9UYgTmpS9jzjOaDdqeS46GjfoJm7dwa3oaicGRXRpoEeytDpx7g/132
             * day : 2
             */

            private String nickname;
            private String avatar;
            private String day;

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

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }
        }
    }
}
