package com.jzd.jzshop.chat.model;

/**
 * @author LXB
 * @description:
 * @date :2020/4/26 9:41
 */
public class UploadPicResponse {

    /**
     * result : {"file":"http://test.gtt20.com/attachment/images/2/2020/04/f57755FSg5G5P5XgmGfJsSg5g5S67g.jpg"}
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
         * file : http://test.gtt20.com/attachment/images/2/2020/04/f57755FSg5G5P5XgmGfJsSg5g5S67g.jpg
         */

        private String file;

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }
    }
}
