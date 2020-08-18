package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author lwh
 * @description :
 * @date 2020/4/7.
 */
public class MineStoreEntity {

    /**
     * result : {"status":200,"data":{"is_self":1,"shop_id":"wIC3XDm_hNZX_noYQIaCfOlWSrrS0mwMw","name":"啦啦啦啦","logo":"","signs":"","total":18,"goods":[{"gid":"wm5dPXUr_m0X_XviQIYWDr62_xnA_MHonUgs4Mg","title":"接吻猫2019冬季新款休闲时尚流行加绒加厚粗跟过膝高筒靴超高靴","price":"2399.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/g16o6PS9ojeFEJEtLTSBO9OFLJPtVwwF.jpg"},{"gid":"m4nCqXr_gRWA_SdmuFIXLsSD6oLAhns8Mw","title":"苏泊尔电压力锅家用智能5L高压锅饭煲官方2特价3旗舰店4正品5-6人","price":"799.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/xB0ckKkkdczOH0hthUdDXcTzkXo85zHY.jpg"},{"gid":"zmqNBGkynrJXJQaXhDI2j_HAZ_QoonksI0MQ","title":"美的电压力锅家用智能5L升高压锅饭煲1官方2特价3旗舰店正品4-6人","price":"209.02","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/Juf9v7rpGV972UuYIfzh27XzY7ugpmy2.jpg"},{"gid":"wKbIKUJ2_riA_scyEwYYDxmGWgHomATcQMg","title":"联想吃鸡游戏本笔记本电脑轻薄便携商务办公学生分期14/15.6英寸","price":"0.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/g6gG5jg1E2cYe1Qy2zgXN1e1C7QNx77g.jpg"},{"gid":"xzgdrbGlr_TAR_kSyENeLDIWGWrobmbcNsMQ","title":"【酷睿i7+16G内存】 2019年新款MAINGEAR U953窄边框15.6英寸笔记本电脑轻薄便携学生女生商务办公金属游戏本","price":"2619.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/WDMi0m3fiKmRzSFPfM7lFff7MW7rI7y7.jpg"},{"gid":"nYF3vKwrBtzC5Lx7sCW6eAmomcJgK4Mw","title":"Asus/华硕 VivoBook15/14 V5000/V4000英特尔酷睿i5轻薄商务办公笔记本电脑15.6英寸","price":"6999.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/MVINiZV59i8o9uWIJoJ9G5w59J3wGn43.jpg"},{"gid":"Eyb4SaBxXrbSHUNYBXrzfqyoumuMZQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png"},{"gid":"DyctbaWwM_EAM_oSiUMbUADGemfoSmyMQUMQ","title":"111博世电钻充电钻家用手电钻12V博士电动螺丝刀工具手枪钻GSR120-LI","price":"359.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/MXkjzz8q5RJq4Kk5OjFOQgOJ4mf405jk.jpg"},{"gid":"ysLYDnUqqcti5ZsKCEBTmsDomPlMgMg","title":"佳能80D单反相机专业级高级18-200套机vlog蚂蚁摄影数码高清旅游","price":"8499.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/HQ9sPD3kk9KVtD9FVTpQNtDnjd9ZNhKZ.jpg"},{"gid":"zUINLwQr_LZX_rBSRYpICyWGufsomxhMkMg","title":"可减145+当天发送无线充Xiaomi / 小米9Pro 5G小米5G全网通手机官方旗舰店骁龙855plus正品10小米95G官网新品","price":"3798.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/Uw31jw22wl7oKjjqLEz73V7e1lJ0uh6P.jpg"}]}}
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
         * status : 200
         * data : {"is_self":1,"shop_id":"wIC3XDm_hNZX_noYQIaCfOlWSrrS0mwMw","name":"啦啦啦啦","logo":"","signs":"","total":18,"goods":[{"gid":"wm5dPXUr_m0X_XviQIYWDr62_xnA_MHonUgs4Mg","title":"接吻猫2019冬季新款休闲时尚流行加绒加厚粗跟过膝高筒靴超高靴","price":"2399.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/g16o6PS9ojeFEJEtLTSBO9OFLJPtVwwF.jpg"},{"gid":"m4nCqXr_gRWA_SdmuFIXLsSD6oLAhns8Mw","title":"苏泊尔电压力锅家用智能5L高压锅饭煲官方2特价3旗舰店4正品5-6人","price":"799.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/xB0ckKkkdczOH0hthUdDXcTzkXo85zHY.jpg"},{"gid":"zmqNBGkynrJXJQaXhDI2j_HAZ_QoonksI0MQ","title":"美的电压力锅家用智能5L升高压锅饭煲1官方2特价3旗舰店正品4-6人","price":"209.02","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/Juf9v7rpGV972UuYIfzh27XzY7ugpmy2.jpg"},{"gid":"wKbIKUJ2_riA_scyEwYYDxmGWgHomATcQMg","title":"联想吃鸡游戏本笔记本电脑轻薄便携商务办公学生分期14/15.6英寸","price":"0.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/g6gG5jg1E2cYe1Qy2zgXN1e1C7QNx77g.jpg"},{"gid":"xzgdrbGlr_TAR_kSyENeLDIWGWrobmbcNsMQ","title":"【酷睿i7+16G内存】 2019年新款MAINGEAR U953窄边框15.6英寸笔记本电脑轻薄便携学生女生商务办公金属游戏本","price":"2619.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/WDMi0m3fiKmRzSFPfM7lFff7MW7rI7y7.jpg"},{"gid":"nYF3vKwrBtzC5Lx7sCW6eAmomcJgK4Mw","title":"Asus/华硕 VivoBook15/14 V5000/V4000英特尔酷睿i5轻薄商务办公笔记本电脑15.6英寸","price":"6999.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/MVINiZV59i8o9uWIJoJ9G5w59J3wGn43.jpg"},{"gid":"Eyb4SaBxXrbSHUNYBXrzfqyoumuMZQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png"},{"gid":"DyctbaWwM_EAM_oSiUMbUADGemfoSmyMQUMQ","title":"111博世电钻充电钻家用手电钻12V博士电动螺丝刀工具手枪钻GSR120-LI","price":"359.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/MXkjzz8q5RJq4Kk5OjFOQgOJ4mf405jk.jpg"},{"gid":"ysLYDnUqqcti5ZsKCEBTmsDomPlMgMg","title":"佳能80D单反相机专业级高级18-200套机vlog蚂蚁摄影数码高清旅游","price":"8499.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/HQ9sPD3kk9KVtD9FVTpQNtDnjd9ZNhKZ.jpg"},{"gid":"zUINLwQr_LZX_rBSRYpICyWGufsomxhMkMg","title":"可减145+当天发送无线充Xiaomi / 小米9Pro 5G小米5G全网通手机官方旗舰店骁龙855plus正品10小米95G官网新品","price":"3798.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/Uw31jw22wl7oKjjqLEz73V7e1lJ0uh6P.jpg"}]}
         */

        private int status;
        private DataBean data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * is_self : 1
             * shop_id : wIC3XDm_hNZX_noYQIaCfOlWSrrS0mwMw
             * name : 啦啦啦啦
             * logo :
             * signs :
             * total : 18
             * goods : [{"gid":"wm5dPXUr_m0X_XviQIYWDr62_xnA_MHonUgs4Mg","title":"接吻猫2019冬季新款休闲时尚流行加绒加厚粗跟过膝高筒靴超高靴","price":"2399.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/g16o6PS9ojeFEJEtLTSBO9OFLJPtVwwF.jpg"},{"gid":"m4nCqXr_gRWA_SdmuFIXLsSD6oLAhns8Mw","title":"苏泊尔电压力锅家用智能5L高压锅饭煲官方2特价3旗舰店4正品5-6人","price":"799.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/xB0ckKkkdczOH0hthUdDXcTzkXo85zHY.jpg"},{"gid":"zmqNBGkynrJXJQaXhDI2j_HAZ_QoonksI0MQ","title":"美的电压力锅家用智能5L升高压锅饭煲1官方2特价3旗舰店正品4-6人","price":"209.02","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/Juf9v7rpGV972UuYIfzh27XzY7ugpmy2.jpg"},{"gid":"wKbIKUJ2_riA_scyEwYYDxmGWgHomATcQMg","title":"联想吃鸡游戏本笔记本电脑轻薄便携商务办公学生分期14/15.6英寸","price":"0.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/g6gG5jg1E2cYe1Qy2zgXN1e1C7QNx77g.jpg"},{"gid":"xzgdrbGlr_TAR_kSyENeLDIWGWrobmbcNsMQ","title":"【酷睿i7+16G内存】 2019年新款MAINGEAR U953窄边框15.6英寸笔记本电脑轻薄便携学生女生商务办公金属游戏本","price":"2619.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/WDMi0m3fiKmRzSFPfM7lFff7MW7rI7y7.jpg"},{"gid":"nYF3vKwrBtzC5Lx7sCW6eAmomcJgK4Mw","title":"Asus/华硕 VivoBook15/14 V5000/V4000英特尔酷睿i5轻薄商务办公笔记本电脑15.6英寸","price":"6999.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/12/MVINiZV59i8o9uWIJoJ9G5w59J3wGn43.jpg"},{"gid":"Eyb4SaBxXrbSHUNYBXrzfqyoumuMZQMQ","title":"支付测试使用","price":"0.01","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/bneiYV7VXfhVcIyI1yph8Y6vVNfY8V.png"},{"gid":"DyctbaWwM_EAM_oSiUMbUADGemfoSmyMQUMQ","title":"111博世电钻充电钻家用手电钻12V博士电动螺丝刀工具手枪钻GSR120-LI","price":"359.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/11/MXkjzz8q5RJq4Kk5OjFOQgOJ4mf405jk.jpg"},{"gid":"ysLYDnUqqcti5ZsKCEBTmsDomPlMgMg","title":"佳能80D单反相机专业级高级18-200套机vlog蚂蚁摄影数码高清旅游","price":"8499.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/HQ9sPD3kk9KVtD9FVTpQNtDnjd9ZNhKZ.jpg"},{"gid":"zUINLwQr_LZX_rBSRYpICyWGufsomxhMkMg","title":"可减145+当天发送无线充Xiaomi / 小米9Pro 5G小米5G全网通手机官方旗舰店骁龙855plus正品10小米95G官网新品","price":"3798.00","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/Uw31jw22wl7oKjjqLEz73V7e1lJ0uh6P.jpg"}]
             */

            private int is_self;
            private String shop_id;
            private String name;
            private String logo;
            private String signs;
            private int total;
            private List<GoodsBean> goods;

            public int getIs_self() {
                return is_self;
            }

            public void setIs_self(int is_self) {
                this.is_self = is_self;
            }

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
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

            public String getSigns() {
                return signs;
            }

            public void setSigns(String signs) {
                this.signs = signs;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class GoodsBean {
                /**
                 * gid : wm5dPXUr_m0X_XviQIYWDr62_xnA_MHonUgs4Mg
                 * title : 接吻猫2019冬季新款休闲时尚流行加绒加厚粗跟过膝高筒靴超高靴
                 * price : 2399.00
                 * thumb : http://test.gtt20.com/attachment/images/2/2019/12/g16o6PS9ojeFEJEtLTSBO9OFLJPtVwwF.jpg
                 */

                private String gid;
                private String title;
                private String price;
                private String thumb;

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
            }
        }
    }
}
