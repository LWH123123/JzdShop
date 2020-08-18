package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/5/18 15:27
 */
public class HomeGoodsEntity {
    /**
     * result : {"total":24,"data":[{"gid":"yJ2Nadgy_U5X_O3CMo9fDDIGmQWonFB8gMg","title":"白色纯棉印花体恤2020新款半袖上衣短袖t恤女ins宽松大码韩版女装","price":"72.00","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/sgC6H6zqbd6lPauWU1lC66APylUg7dF3.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":1},{"gid":"nNu4Y2y_ppKA_SGxpYWXpjRz2oexpn88Mw","title":"2件69.9】夏天2020新款纯棉短袖t恤女韩版宽松半袖牛油果绿色上衣","price":"72.00","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/ND2TB7CY8e8TzrrTDdg8QzGr19DTz8C2.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"zD49Cuuy_l7X_GFHdcjKWnOD6i2onua80Mg","title":"2件69】2020年新款纯棉夏装宽松短袖t恤女装夏季半袖白色上衣女士","price":"198.00","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/WDXySYHD2G777K8a8Y25Y2KxAhaG17lw.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"Ayw4AHNlQ_uAT_YCAVoKtCaGi2wolnZswQMQ","title":"2件69】2020年新款纯棉上衣服春夏季白色短袖t恤女装宽松韩版夏装","price":"198.00","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/pIx3IiIFxxYIiIYBZp4FlbtdKi883FL5.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"WzT4yDykP_sAF_HnYAEekAJGH_jAC_aomnCsQUMQ","title":"2件79.9】2020新款春装上衣服长袖t恤女装春秋韩版女士春季打底衫","price":"198.00","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/blrQuIqLijBmMrysImjNRmrUQMRsR2m5.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"FwwIKegwZrTCtEWfXWmzd6RoFnfsCoMQ","title":"测试伤害","price":"300.00","thumb":"http://test.gtt20.com/attachment/images/2/merch/121/kewsfWA5FiAEEzeWWPlTZAYWm444EK.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"wdHQhPr_Re1X_iwWCQWDMQ42_Atqf_onELEs4Mw","title":"接吻猫2019冬季新款休闲时尚流行加绒加厚粗跟过膝高筒靴超高靴","price":"2399.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/g16o6PS9ojeFEJEtLTSBO9OFLJPtVwwF.jpg","label":null,"sales_real":2,"is_sendfree":0,"is_new":0},{"gid":"mU84XtPr_ErA_JgSFY5IXK3D6m3onths8Mg","title":"苏泊尔电压力锅家用智能5L高压锅饭煲官方2特价3旗舰店4正品5-6人","price":"799.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/xB0ckKkkdczOH0hthUdDXcTzkXo85zHY.jpg","label":null,"sales_real":3,"is_sendfree":0,"is_new":0},{"gid":"mNoqDGyrI3QXQXCxKD2_z9uA_oqT9ns0Mw","title":"美的电压力锅家用智能5L升高压锅饭煲1官方2特价3旗舰店正品4-6人","price":"209.02","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/Juf9v7rpGV972UuYIfzh27XzY7ugpmy2.jpg","label":null,"sales_real":1,"is_sendfree":0,"is_new":0},{"gid":"wu4IKb02_aDA_GRyEa4YDnvGWqqomB4cQMg","title":"联想吃鸡游戏本笔记本电脑轻薄便携商务办公学生分期14/15.6英寸","price":"5666.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/g6gG5jg1E2cYe1Qy2zgXN1e1C7QNx77g.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0}]}
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
         * total : 24
         * data : [{"gid":"yJ2Nadgy_U5X_O3CMo9fDDIGmQWonFB8gMg","title":"白色纯棉印花体恤2020新款半袖上衣短袖t恤女ins宽松大码韩版女装","price":"72.00","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/sgC6H6zqbd6lPauWU1lC66APylUg7dF3.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":1},{"gid":"nNu4Y2y_ppKA_SGxpYWXpjRz2oexpn88Mw","title":"2件69.9】夏天2020新款纯棉短袖t恤女韩版宽松半袖牛油果绿色上衣","price":"72.00","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/ND2TB7CY8e8TzrrTDdg8QzGr19DTz8C2.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"zD49Cuuy_l7X_GFHdcjKWnOD6i2onua80Mg","title":"2件69】2020年新款纯棉夏装宽松短袖t恤女装夏季半袖白色上衣女士","price":"198.00","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/WDXySYHD2G777K8a8Y25Y2KxAhaG17lw.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"Ayw4AHNlQ_uAT_YCAVoKtCaGi2wolnZswQMQ","title":"2件69】2020年新款纯棉上衣服春夏季白色短袖t恤女装宽松韩版夏装","price":"198.00","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/pIx3IiIFxxYIiIYBZp4FlbtdKi883FL5.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"WzT4yDykP_sAF_HnYAEekAJGH_jAC_aomnCsQUMQ","title":"2件79.9】2020新款春装上衣服长袖t恤女装春秋韩版女士春季打底衫","price":"198.00","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/blrQuIqLijBmMrysImjNRmrUQMRsR2m5.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"FwwIKegwZrTCtEWfXWmzd6RoFnfsCoMQ","title":"测试伤害","price":"300.00","thumb":"http://test.gtt20.com/attachment/images/2/merch/121/kewsfWA5FiAEEzeWWPlTZAYWm444EK.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"wdHQhPr_Re1X_iwWCQWDMQ42_Atqf_onELEs4Mw","title":"接吻猫2019冬季新款休闲时尚流行加绒加厚粗跟过膝高筒靴超高靴","price":"2399.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/g16o6PS9ojeFEJEtLTSBO9OFLJPtVwwF.jpg","label":null,"sales_real":2,"is_sendfree":0,"is_new":0},{"gid":"mU84XtPr_ErA_JgSFY5IXK3D6m3onths8Mg","title":"苏泊尔电压力锅家用智能5L高压锅饭煲官方2特价3旗舰店4正品5-6人","price":"799.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/xB0ckKkkdczOH0hthUdDXcTzkXo85zHY.jpg","label":null,"sales_real":3,"is_sendfree":0,"is_new":0},{"gid":"mNoqDGyrI3QXQXCxKD2_z9uA_oqT9ns0Mw","title":"美的电压力锅家用智能5L升高压锅饭煲1官方2特价3旗舰店正品4-6人","price":"209.02","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/Juf9v7rpGV972UuYIfzh27XzY7ugpmy2.jpg","label":null,"sales_real":1,"is_sendfree":0,"is_new":0},{"gid":"wu4IKb02_aDA_GRyEa4YDnvGWqqomB4cQMg","title":"联想吃鸡游戏本笔记本电脑轻薄便携商务办公学生分期14/15.6英寸","price":"5666.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/g6gG5jg1E2cYe1Qy2zgXN1e1C7QNx77g.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0}]
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
             * gid : yJ2Nadgy_U5X_O3CMo9fDDIGmQWonFB8gMg
             * title : 白色纯棉印花体恤2020新款半袖上衣短袖t恤女ins宽松大码韩版女装
             * price : 72.00
             * thumb : http://test.gtt20.com/attachment/images/2/2020/05/sgC6H6zqbd6lPauWU1lC66APylUg7dF3.jpg
             * label : null
             * sales_real : 0
             * is_sendfree : 0
             * is_new : 1
             */

            private String gid;
            private String title;
            private String price;
            private String thumb;
            private Object label;
            private int sales_real;
            private int is_sendfree;
            private int is_new;

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public Object getLabel() {
                return label;
            }

            public void setLabel(Object label) {
                this.label = label;
            }

            public int getSales_real() {
                return sales_real;
            }

            public void setSales_real(int sales_real) {
                this.sales_real = sales_real;
            }

            public int getIs_sendfree() {
                return is_sendfree;
            }

            public void setIs_sendfree(int is_sendfree) {
                this.is_sendfree = is_sendfree;
            }

            public int getIs_new() {
                return is_new;
            }

            public void setIs_new(int is_new) {
                this.is_new = is_new;
            }
        }
    }
}
