package com.jzd.jzshop.entity;

/**
 * @author LXB
 * @description:
 * @date :2019/12/27 18:03
 */
public class AppUpdateEntity {
    /**
     * result : {"status":0,"version":"1.0","desc":"","size":"","url":""}
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
         * status : 0
         * version : 1.0
         * desc :
         * size :
         * url :
         */

        private int status;
        private String version;
        private String desc;
        private String size;
        private String url;
        private String hashMD5;

        public String getHashMD5() {
            return hashMD5;
        }

        public void setHashMD5(String hashMD5) {
            this.hashMD5 = hashMD5;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
