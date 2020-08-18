package com.jzd.jzshop.entity.bean;

import com.jzd.jzshop.entity.CreditsStoreEntity;
import com.jzd.jzshop.entity.MyPagerEntity;

import java.util.List;

/**
 * @author LXB
 * @description: 本地要显示的数据模型
 * @date :2020/5/8 16:43
 */
public class CreditsStoreEntityLocal {

    public CreditsStoreEntityLocal(String title, List<CreditsStoreEntity.ResultBean.DataBeanX.DataBean> list) {
        this.title = title;
        this.list = list;
    }
    private String title;
    private List<CreditsStoreEntity.ResultBean.DataBeanX.DataBean> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CreditsStoreEntity.ResultBean.DataBeanX.DataBean> getList() {
        return list;
    }

    public void setList(List<CreditsStoreEntity.ResultBean.DataBeanX.DataBean> list) {
        this.list = list;
    }



    private CreditsStoreEntity.ResultBean result;

    public CreditsStoreEntity.ResultBean getResult() {
        return result;
    }

    public void setResult(CreditsStoreEntity.ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * member : {"points":1004883}
         * category : [{"cate_id":"yduS5brqtOMnIWcLNCGqJF5rmAMw","cate_name":"3C","cate_thumb":"http://test.gtt20.com/attachment/images/2/2019/10/vEMK0bFmSM9dqS0cZ8E4e2f8dpVsBK.jpg"},{"cate_id":"wP5dXzTg9oRyEDHdDPzT_okA_L5rmw4QMg","cate_name":"优惠券","cate_thumb":"http://test.gtt20.com/attachment/images/2/2020/05/KvErr0s5VQlhMpKkHHmR0pqPMRLJrE.jpg"},{"cate_id":"AnCNlKFxK9unTAKdJWSzF_xAp_brnnbgMQ","cate_name":"余额","cate_thumb":"http://test.gtt20.com/attachment/images/2/2019/10/dHcO4OE3PKk9zHnzUCh44nNZ9dhevQ.jpg"},{"cate_id":"dmR4ceKwQqDyO4kYbXWzPiTrnnSwMQ","cate_name":"红包","cate_thumb":"http://test.gtt20.com/attachment/images/2/2019/10/UpT0A7zt7s2tkAs2hTcLx2z70a343z.jpg"},{"cate_id":"zfeoeS42_fmA_GtSVdCPARFWmu9rnWwAMg","cate_name":"衣服","cate_thumb":"http://test.gtt20.com/attachment/images/2/2019/10/U0QbBQqGVxpKBKokBtP99Z0ow5kZ9K.jpg"}]
         * data : [{"id":"lotterydraws","data":[{"gid":"NnU9IaV1P_QXR_NHzIkaWXvWDqipAnzgMQ","title":"红包002","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/n4m3NZ3M334432Yp6p4PoNomCn443O.jpg","credit":20000,"money":"0.00","type":3},{"gid":"mtx4WZo1rTqC9SrMCSKGuuvpmn8AMg","title":"抽奖余额20元","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/yLrY4emLLE4Ll72iWLIzRi152Nyl1M.jpg","credit":300,"money":"0.00","type":2},{"gid":"wNE7nPnqQoknMaWz2DmuOQsokwMw","title":"满200减40","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/zyreTQTA8otMiTzwqBOr1M0TQ1sr8r.jpg","credit":30,"money":"0.01","type":1},{"gid":"yx8YeQny_VRX_atC4C4ZWsKGWC5ony9QMg","title":"小米（MI）小爱智能音箱Pro 无线蓝牙音响 语音控制智能家居 APP远程操控 专业DTS 音效 黑色","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/Q1AGEArDieZwDIA1MMWZdZUgM1NDv7.jpg","credit":300000,"money":"0.00","type":0}]},{"id":"exchanges","data":[{"gid":"zsIIfapkrR8C5CBMDgmG6uWonYTAMg","title":"华为 HUAWEI Mate 30 Pro 8GB+256GB 亮黑色 麒麟990芯片 4000万徕卡电影四摄 超曲面OLED环幕屏 超级快充 屏内指纹 4G全网通双卡双待手机","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/PZb27s22DXZd6xLLH9fdD27SBDbdbs.jpg","credit":1000000,"money":"0.00","type":0},{"gid":"wdVfuXm9bBWyMZGyGW2WF1vonwMw","title":"小米手环4石墨黑 AI彩屏心率运动手环游泳姿势识别50米防水6轴传感器24小时高精准心率监测","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/iia9dG5C51snUN17dFfuaviNv1WDzz.jpg","credit":20000,"money":"0.00","type":0},{"gid":"YyENdfwks9Oyc4TdqCd2y6KoXnhgMQ","title":"现货速发【现货国行4788元起-可分期】Apple 苹果 iPhone 11 手机 黑色 全网通 64GB","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/UtR25Z7c3g96k593k7mat956g99rm9.jpg","credit":500000,"money":"499.00","type":0}]},{"id":"coupons","data":[{"gid":"PzftgWXyn_YAI_MCcdqPZCpmqqtpwmYgMQ","title":"满300减60","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/EZaskDX779UaaM1YXRxSZaRAYKkj7A.jpg","credit":500,"money":"5.00","type":1},{"gid":"m4Eccbl_bXEA_nF1QAeAho3W_AgcF_okiU1gMw","title":"满100减30","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/TL131TfJll61M48phIK48Z6lHuH056.jpg","credit":100,"money":"0.00","type":1}]},{"id":"balances","data":[{"gid":"inadj3ekv_oAM_HHYAOdDCLTQiRpSmOwMQ","title":"兑换余额10元","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/mece3N3JjRPPgTU5CnJC7t7P99G59J.jpg","credit":100,"money":"1.00","type":2}]},{"id":"redbags","data":[{"gid":"zopEofr_iS2A_yDe24WAkT3Gypw0dmQMw","title":"红包001","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/B81nDSXfS8mP1sEMaYQ8111MgDnA51.jpg","credit":30,"money":"0.00","type":3}]}]
         */

        private CreditsStoreEntity.ResultBean.MemberBean member;
        private List<CreditsStoreEntity.ResultBean.CategoryBean> category;
        private List<CreditsStoreEntity.ResultBean.DataBeanX> data;

        public CreditsStoreEntity.ResultBean.MemberBean getMember() {
            return member;
        }

        public void setMember(CreditsStoreEntity.ResultBean.MemberBean member) {
            this.member = member;
        }

        public List<CreditsStoreEntity.ResultBean.CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CreditsStoreEntity.ResultBean.CategoryBean> category) {
            this.category = category;
        }

        public List<CreditsStoreEntity.ResultBean.DataBeanX> getData() {
            return data;
        }

        public void setData(List<CreditsStoreEntity.ResultBean.DataBeanX> data) {
            this.data = data;
        }

        public static class MemberBean {
            /**
             * points : 1004883
             */

            private int points;

            public int getPoints() {
                return points;
            }

            public void setPoints(int points) {
                this.points = points;
            }
        }

        public static class CategoryBean {
            /**
             * cate_id : yduS5brqtOMnIWcLNCGqJF5rmAMw
             * cate_name : 3C
             * cate_thumb : http://test.gtt20.com/attachment/images/2/2019/10/vEMK0bFmSM9dqS0cZ8E4e2f8dpVsBK.jpg
             */

            private String cate_id;
            private String cate_name;
            private String cate_thumb;

            public String getCate_id() {
                return cate_id;
            }

            public void setCate_id(String cate_id) {
                this.cate_id = cate_id;
            }

            public String getCate_name() {
                return cate_name;
            }

            public void setCate_name(String cate_name) {
                this.cate_name = cate_name;
            }

            public String getCate_thumb() {
                return cate_thumb;
            }

            public void setCate_thumb(String cate_thumb) {
                this.cate_thumb = cate_thumb;
            }
        }

        public static class DataBeanX {
            /**
             * id : lotterydraws
             * data : [{"gid":"NnU9IaV1P_QXR_NHzIkaWXvWDqipAnzgMQ","title":"红包002","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/n4m3NZ3M334432Yp6p4PoNomCn443O.jpg","credit":20000,"money":"0.00","type":3},{"gid":"mtx4WZo1rTqC9SrMCSKGuuvpmn8AMg","title":"抽奖余额20元","thumb":"http://test.gtt20.com/attachment/images/2/2019/10/yLrY4emLLE4Ll72iWLIzRi152Nyl1M.jpg","credit":300,"money":"0.00","type":2},{"gid":"wNE7nPnqQoknMaWz2DmuOQsokwMw","title":"满200减40","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/zyreTQTA8otMiTzwqBOr1M0TQ1sr8r.jpg","credit":30,"money":"0.01","type":1},{"gid":"yx8YeQny_VRX_atC4C4ZWsKGWC5ony9QMg","title":"小米（MI）小爱智能音箱Pro 无线蓝牙音响 语音控制智能家居 APP远程操控 专业DTS 音效 黑色","thumb":"http://test.gtt20.com/attachment/images/2/2020/05/Q1AGEArDieZwDIA1MMWZdZUgM1NDv7.jpg","credit":300000,"money":"0.00","type":0}]
             */

            private String id;
            private List<CreditsStoreEntity.ResultBean.DataBeanX.DataBean> data;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public List<CreditsStoreEntity.ResultBean.DataBeanX.DataBean> getData() {
                return data;
            }

            public void setData(List<CreditsStoreEntity.ResultBean.DataBeanX.DataBean> data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * gid : NnU9IaV1P_QXR_NHzIkaWXvWDqipAnzgMQ
                 * title : 红包002
                 * thumb : http://test.gtt20.com/attachment/images/2/2019/10/n4m3NZ3M334432Yp6p4PoNomCn443O.jpg
                 * credit : 20000
                 * money : 0.00
                 * type : 3
                 */

                private String gid;
                private String title;
                private String thumb;
                private int credit;
                private String money;
                private int type;

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

                public String getThumb() {
                    return thumb;
                }

                public void setThumb(String thumb) {
                    this.thumb = thumb;
                }

                public int getCredit() {
                    return credit;
                }

                public void setCredit(int credit) {
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
            }
        }
    }


}
