package com.jzd.jzshop.entity;

import com.jzd.jzshop.utils.MoneyFormatUtils;

import java.util.List;

/**
 * 商品搜索 （关键词搜索）
 */
public class GoodsListEntity {

    /**
     * result : {"total":18,"data":[{"gid":"wc7dPhrr_yJX_S1iQOCWDz52_n3A_bQonO0s4Mg","title":"接吻猫2019冬季新款休闲时尚流行加绒加厚粗跟过膝高筒靴超高靴","price":2399,"thumb":"http://test.gtt20.com/attachment/images/2/2019/12/g16o6PS9ojeFEJEtLTSBO9OFLJPtVwwF.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"m4mNnXr_c6hA_Sfk7FIXbhND6oGl9ns8Mw","title":"苏泊尔电压力锅家用智能5L高压锅饭煲官方2特价3旗舰店4正品5-6人","price":"799.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/xB0ckKkkdczOH0hthUdDXcTzkXo85zHY.jpg","label":null,"sales_real":1,"is_sendfree":0,"is_new":0},{"gid":"mTKNGF2yrBcXQt3XDH92_VoA_GeonEHs0Mg","title":"美的电压力锅家用智能5L升高压锅饭煲1官方2特价3旗舰店正品4-6人","price":"209.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/Juf9v7rpGV972UuYIfzh27XzY7ugpmy2.jpg","label":null,"sales_real":2,"is_sendfree":0,"is_new":0},{"gid":"SwXIdKX2r_zAE_SyrEmYMDcGCWCoPmOcXQMQ","title":"联想吃鸡游戏本笔记本电脑轻薄便携商务办公学生分期14/15.6英寸","price":5666,"thumb":"http://test.gtt20.com/attachment/images/2/2019/12/g6gG5jg1E2cYe1Qy2zgXN1e1C7QNx77g.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"zBLdbK8l_CmA_KRSEofeDsZWWNJomKucsMg","title":"【酷睿i7+16G内存】 2019年新款MAINGEAR U953窄边框15.6英寸笔记本电脑轻薄便携学生女生商务办公金属游戏本","price":2619,"thumb":"http://test.gtt20.com/attachment/images/2/2019/12/WDMi0m3fiKmRzSFPfM7lFff7MW7rI7y7.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"nYRU3KwrunkC5LwnCCW6EPtomcg134Mw","title":"Asus/华硕 VivoBook15/14 V5000/V4000英特尔酷睿i5轻薄商务办公笔记本电脑15.6英寸","price":6999,"thumb":"http://test.gtt20.com/attachment/images/2/2019/12/MVINiZV59i8o9uWIJoJ9G5w59J3wGn43.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"y4EaaaxrFWBSUYolAXzqpJdomMA4kQMw","title":"支付测试使用","price":0.01,"thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png","label":null,"sales_real":43,"is_sendfree":1,"is_new":0},{"gid":"yton6aw_kIQA_SksVUbAx7XGmovj2mMUMw","title":"博世电钻充电钻家用手电钻12V博士电动螺丝刀工具手枪钻GSR120-LI","price":359,"thumb":"http://test.gtt20.com/attachment/images/2/2019/11/yCJvgyYz0F9NHF9049G1hA1CcTC1Gkk1.jpg","label":null,"sales_real":6,"is_sendfree":0,"is_new":0},{"gid":"PyBYmDRqgqKia5dKPCQTrmRoymdMigMQ","title":"佳能80D单反相机专业级高级18-200套机vlog蚂蚁摄影数码高清旅游","price":8499,"thumb":"http://test.gtt20.com/attachment/images/2/2019/10/HQ9sPD3kk9KVtD9FVTpQNtDnjd9ZNhKZ.jpg","label":null,"sales_real":1,"is_sendfree":0,"is_new":0},{"gid":"zNXwALr_Z3VX_SNx7RICb7NGuotzrmMkMw","title":"可减145+当天发送无线充Xiaomi / 小米9Pro 5G小米5G全网通手机官方旗舰店骁龙855plus正品10小米95G官网新品","price":3798,"thumb":"http://test.gtt20.com/attachment/images/2/2019/10/Uw31jw22wl7oKjjqLEz73V7e1lJ0uh6P.jpg","label":null,"sales_real":1,"is_sendfree":0,"is_new":0}]}
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
         * total : 18
         * data : [{"gid":"wc7dPhrr_yJX_S1iQOCWDz52_n3A_bQonO0s4Mg","title":"接吻猫2019冬季新款休闲时尚流行加绒加厚粗跟过膝高筒靴超高靴","price":2399,"thumb":"http://test.gtt20.com/attachment/images/2/2019/12/g16o6PS9ojeFEJEtLTSBO9OFLJPtVwwF.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"m4mNnXr_c6hA_Sfk7FIXbhND6oGl9ns8Mw","title":"苏泊尔电压力锅家用智能5L高压锅饭煲官方2特价3旗舰店4正品5-6人","price":"799.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/xB0ckKkkdczOH0hthUdDXcTzkXo85zHY.jpg","label":null,"sales_real":1,"is_sendfree":0,"is_new":0},{"gid":"mTKNGF2yrBcXQt3XDH92_VoA_GeonEHs0Mg","title":"美的电压力锅家用智能5L升高压锅饭煲1官方2特价3旗舰店正品4-6人","price":"209.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/Juf9v7rpGV972UuYIfzh27XzY7ugpmy2.jpg","label":null,"sales_real":2,"is_sendfree":0,"is_new":0},{"gid":"SwXIdKX2r_zAE_SyrEmYMDcGCWCoPmOcXQMQ","title":"联想吃鸡游戏本笔记本电脑轻薄便携商务办公学生分期14/15.6英寸","price":5666,"thumb":"http://test.gtt20.com/attachment/images/2/2019/12/g6gG5jg1E2cYe1Qy2zgXN1e1C7QNx77g.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"zBLdbK8l_CmA_KRSEofeDsZWWNJomKucsMg","title":"【酷睿i7+16G内存】 2019年新款MAINGEAR U953窄边框15.6英寸笔记本电脑轻薄便携学生女生商务办公金属游戏本","price":2619,"thumb":"http://test.gtt20.com/attachment/images/2/2019/12/WDMi0m3fiKmRzSFPfM7lFff7MW7rI7y7.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"nYRU3KwrunkC5LwnCCW6EPtomcg134Mw","title":"Asus/华硕 VivoBook15/14 V5000/V4000英特尔酷睿i5轻薄商务办公笔记本电脑15.6英寸","price":6999,"thumb":"http://test.gtt20.com/attachment/images/2/2019/12/MVINiZV59i8o9uWIJoJ9G5w59J3wGn43.jpg","label":null,"sales_real":0,"is_sendfree":0,"is_new":0},{"gid":"y4EaaaxrFWBSUYolAXzqpJdomMA4kQMw","title":"支付测试使用","price":0.01,"thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png","label":null,"sales_real":43,"is_sendfree":1,"is_new":0},{"gid":"yton6aw_kIQA_SksVUbAx7XGmovj2mMUMw","title":"博世电钻充电钻家用手电钻12V博士电动螺丝刀工具手枪钻GSR120-LI","price":359,"thumb":"http://test.gtt20.com/attachment/images/2/2019/11/yCJvgyYz0F9NHF9049G1hA1CcTC1Gkk1.jpg","label":null,"sales_real":6,"is_sendfree":0,"is_new":0},{"gid":"PyBYmDRqgqKia5dKPCQTrmRoymdMigMQ","title":"佳能80D单反相机专业级高级18-200套机vlog蚂蚁摄影数码高清旅游","price":8499,"thumb":"http://test.gtt20.com/attachment/images/2/2019/10/HQ9sPD3kk9KVtD9FVTpQNtDnjd9ZNhKZ.jpg","label":null,"sales_real":1,"is_sendfree":0,"is_new":0},{"gid":"zNXwALr_Z3VX_SNx7RICb7NGuotzrmMkMw","title":"可减145+当天发送无线充Xiaomi / 小米9Pro 5G小米5G全网通手机官方旗舰店骁龙855plus正品10小米95G官网新品","price":3798,"thumb":"http://test.gtt20.com/attachment/images/2/2019/10/Uw31jw22wl7oKjjqLEz73V7e1lJ0uh6P.jpg","label":null,"sales_real":1,"is_sendfree":0,"is_new":0}]
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
             * gid : wc7dPhrr_yJX_S1iQOCWDz52_n3A_bQonO0s4Mg
             * title : 接吻猫2019冬季新款休闲时尚流行加绒加厚粗跟过膝高筒靴超高靴
             * price : 2399
             * thumb : http://test.gtt20.com/attachment/images/2/2019/12/g16o6PS9ojeFEJEtLTSBO9OFLJPtVwwF.jpg
             * label : null
             * sales_real : 0
             * is_sendfree : 0
             * is_new : 0
             */

            private String gid;
            private String title;
            private String price;
            private String thumb;
            private String label;
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

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
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

            //===========手动增加方法===============
            public String getPriceText() {
                /*MoneyFormatUtils.keepTwoDecimal(price)*/ //前端处理price 显示
                return price;
            }

            public String getSales_realText() {
                return String.valueOf(sales_real);
            }
        }
    }
}
