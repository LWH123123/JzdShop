package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/1/10 9:39
 */
public class PosterNewEntity {

    /**
     * result : {"invit_id":21,"poster":["http://test.gtt20.com/addons/ewei_shopv2/data/poster/2/1ba6a915794545da35392a9b4d557517.jpg","http://test.gtt20.com/addons/ewei_shopv2/data/poster/2/dfdd2fc67f84acbade64cbc86e2fb04e.jpg","http://test.gtt20.com/addons/ewei_shopv2/data/poster/2/2a9c957b696cab14ca9c88f220c7e8fb.jpg","http://test.gtt20.com/addons/ewei_shopv2/data/poster/2/a0853c7cdc4adbb6d9b830f4c23813b4.jpg"]}
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
         * invit_id : 21
         * poster : ["http://test.gtt20.com/addons/ewei_shopv2/data/poster/2/1ba6a915794545da35392a9b4d557517.jpg","http://test.gtt20.com/addons/ewei_shopv2/data/poster/2/dfdd2fc67f84acbade64cbc86e2fb04e.jpg","http://test.gtt20.com/addons/ewei_shopv2/data/poster/2/2a9c957b696cab14ca9c88f220c7e8fb.jpg","http://test.gtt20.com/addons/ewei_shopv2/data/poster/2/a0853c7cdc4adbb6d9b830f4c23813b4.jpg"]
         */

        private int invit_id;
        private List<String> poster;

        public int getInvit_id() {
            return invit_id;
        }

        public void setInvit_id(int invit_id) {
            this.invit_id = invit_id;
        }

        public List<String> getPoster() {
            return poster;
        }

        public void setPoster(List<String> poster) {
            this.poster = poster;
        }
    }
}
