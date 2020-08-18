package com.jzd.jzshop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/1/18 13:54
 */
public class OrderRefundProgressEntity implements Serializable {
    /**
     * result : {"status":1,"refund_id":"17","refund_status":0,"refund_type":2,"refund_msg":null,"process":["发起换货申请，并把快递单号录入系统","将需要换货的商品邮寄至商家指定地址，并在系统内输入快递单号","商家确认后货后重新发出商品","签收确认商品"],"refund_addr":{"address":"","realname":"","mobile":""},"express_info":{"rexpresscom":"","rexpresssn":"","express":"","expresscom":"","expresssn":""},"refund_info":{"refund_price":"895.60","refund_typestr":"换货","reason":"拍错了/订单信息错误","content":"退款说明测试测试","apply_time":"2020-01-18 13:49"},"express_data":[{"express_id":"shunfeng","express_name":"顺丰"},{"express_id":"shentong","express_name":"申通"},{"express_id":"yunda","express_name":"韵达快运"},{"express_id":"tiantian","express_name":"天天快递"},{"express_id":"yuantong","express_name":"圆通速递"},{"express_id":"zhongtong","express_name":"中通速递"},{"express_id":"ems","express_name":"ems快递"},{"express_id":"huitongkuaidi","express_name":"百世汇通"},{"express_id":"quanfengkuaidi","express_name":"全峰快递"},{"express_id":"zhaijisong","express_name":"宅急送"},{"express_id":"aae","express_name":"aae全球专递"},{"express_id":"anjie","express_name":"安捷快递"},{"express_id":"anxindakuaixi","express_name":"安信达快递"},{"express_id":"biaojikuaidi","express_name":"彪记快递"},{"express_id":"bht","express_name":"bht"},{"express_id":"baifudongfang","express_name":"百福东方国际物流"},{"express_id":"coe","express_name":"中国东方（COE）"},{"express_id":"changyuwuliu","express_name":"长宇物流"},{"express_id":"datianwuliu","express_name":"大田物流"},{"express_id":"debangwuliu","express_name":"德邦物流"},{"express_id":"dhl","express_name":"dhl"},{"express_id":"dpex","express_name":"dpex"},{"express_id":"dsukuaidi","express_name":"d速快递"},{"express_id":"disifang","express_name":"递四方"},{"express_id":"fedex","express_name":"fedex（国外）"},{"express_id":"feikangda","express_name":"飞康达物流"},{"express_id":"fenghuangkuaidi","express_name":"凤凰快递"},{"express_id":"feikuaida","express_name":"飞快达"},{"express_id":"guotongkuaidi","express_name":"国通快递"},{"express_id":"ganzhongnengda","express_name":"港中能达物流"},{"express_id":"guangdongyouzhengwuliu","express_name":"广东邮政物流"},{"express_id":"gongsuda","express_name":"共速达"},{"express_id":"hengluwuliu","express_name":"恒路物流"},{"express_id":"huaxialongwuliu","express_name":"华夏龙物流"},{"express_id":"haihongwangsong","express_name":"海红"},{"express_id":"haiwaihuanqiu","express_name":"海外环球"},{"express_id":"jiayiwuliu","express_name":"佳怡物流"},{"express_id":"jinguangsudikuaijian","express_name":"京广速递"},{"express_id":"jixianda","express_name":"急先达"},{"express_id":"jiajiwuliu","express_name":"佳吉物流"},{"express_id":"jymwl","express_name":"加运美物流"},{"express_id":"jindawuliu","express_name":"金大物流"},{"express_id":"jialidatong","express_name":"嘉里大通"},{"express_id":"jykd","express_name":"晋越快递"},{"express_id":"kuaijiesudi","express_name":"快捷速递"},{"express_id":"lianb","express_name":"联邦快递（国内）"},{"express_id":"lianhaowuliu","express_name":"联昊通物流"},{"express_id":"longbanwuliu","express_name":"龙邦物流"},{"express_id":"lijisong","express_name":"立即送"},{"express_id":"lejiedi","express_name":"乐捷递"},{"express_id":"minghangkuaidi","express_name":"民航快递"},{"express_id":"meiguokuaidi","express_name":"美国快递"},{"express_id":"menduimen","express_name":"门对门"},{"express_id":"ocs","express_name":"OCS"},{"express_id":"peisihuoyunkuaidi","express_name":"配思货运"},{"express_id":"quanchenkuaidi","express_name":"全晨快递"},{"express_id":"quanjitong","express_name":"全际通物流"},{"express_id":"quanritongkuaidi","express_name":"全日通快递"},{"express_id":"quanyikuaidi","express_name":"全一快递"},{"express_id":"rufengda","express_name":"如风达"},{"express_id":"santaisudi","express_name":"三态速递"},{"express_id":"shenghuiwuliu","express_name":"盛辉物流"},{"express_id":"sue","express_name":"速尔物流"},{"express_id":"shengfeng","express_name":"盛丰物流"},{"express_id":"saiaodi","express_name":"赛澳递"},{"express_id":"tiandihuayu","express_name":"天地华宇"},{"express_id":"tnt","express_name":"tnt"},{"express_id":"ups","express_name":"ups"},{"express_id":"wanjiawuliu","express_name":"万家物流"},{"express_id":"wenjiesudi","express_name":"文捷航空速递"},{"express_id":"wuyuan","express_name":"伍圆"},{"express_id":"wxwl","express_name":"万象物流"},{"express_id":"xinbangwuliu","express_name":"新邦物流"},{"express_id":"xinfengwuliu","express_name":"信丰物流"},{"express_id":"yafengsudi","express_name":"亚风速递"},{"express_id":"yibangwuliu","express_name":"一邦速递"},{"express_id":"youshuwuliu","express_name":"优速物流"},{"express_id":"youzhengguonei","express_name":"邮政快递包裹"},{"express_id":"youzhengguoji","express_name":"邮政国际包裹挂号信"},{"express_id":"yuanchengwuliu","express_name":"远成物流"},{"express_id":"yuanweifeng","express_name":"源伟丰快递"},{"express_id":"yuanzhijiecheng","express_name":"元智捷诚快递"},{"express_id":"yuntongkuaidi","express_name":"运通快递"},{"express_id":"yuefengwuliu","express_name":"越丰物流"},{"express_id":"yad","express_name":"源安达"},{"express_id":"yinjiesudi","express_name":"银捷速递"},{"express_id":"zhongtiekuaiyun","express_name":"中铁快运"},{"express_id":"zhongyouwuliu","express_name":"中邮物流"},{"express_id":"zhongxinda","express_name":"忠信达"},{"express_id":"zhimakaimen","express_name":"芝麻开门"},{"express_id":"annengwuliu","express_name":"安能物流"},{"express_id":"jd","express_name":"京东快递"},{"express_id":"weitepai","express_name":"微特派"},{"express_id":"jiuyescm","express_name":"九曳供应链"}]}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * status : 1
         * refund_id : 17
         * refund_status : 0
         * refund_type : 2
         * refund_msg : null
         * process : ["发起换货申请，并把快递单号录入系统","将需要换货的商品邮寄至商家指定地址，并在系统内输入快递单号","商家确认后货后重新发出商品","签收确认商品"]
         * refund_addr : {"address":"","realname":"","mobile":""}
         * express_info : {"rexpresscom":"","rexpresssn":"","express":"","expresscom":"","expresssn":""}
         * refund_info : {"refund_price":"895.60","refund_typestr":"换货","reason":"拍错了/订单信息错误","content":"退款说明测试测试","apply_time":"2020-01-18 13:49"}
         * express_data : [{"express_id":"shunfeng","express_name":"顺丰"},{"express_id":"shentong","express_name":"申通"},{"express_id":"yunda","express_name":"韵达快运"},{"express_id":"tiantian","express_name":"天天快递"},{"express_id":"yuantong","express_name":"圆通速递"},{"express_id":"zhongtong","express_name":"中通速递"},{"express_id":"ems","express_name":"ems快递"},{"express_id":"huitongkuaidi","express_name":"百世汇通"},{"express_id":"quanfengkuaidi","express_name":"全峰快递"},{"express_id":"zhaijisong","express_name":"宅急送"},{"express_id":"aae","express_name":"aae全球专递"},{"express_id":"anjie","express_name":"安捷快递"},{"express_id":"anxindakuaixi","express_name":"安信达快递"},{"express_id":"biaojikuaidi","express_name":"彪记快递"},{"express_id":"bht","express_name":"bht"},{"express_id":"baifudongfang","express_name":"百福东方国际物流"},{"express_id":"coe","express_name":"中国东方（COE）"},{"express_id":"changyuwuliu","express_name":"长宇物流"},{"express_id":"datianwuliu","express_name":"大田物流"},{"express_id":"debangwuliu","express_name":"德邦物流"},{"express_id":"dhl","express_name":"dhl"},{"express_id":"dpex","express_name":"dpex"},{"express_id":"dsukuaidi","express_name":"d速快递"},{"express_id":"disifang","express_name":"递四方"},{"express_id":"fedex","express_name":"fedex（国外）"},{"express_id":"feikangda","express_name":"飞康达物流"},{"express_id":"fenghuangkuaidi","express_name":"凤凰快递"},{"express_id":"feikuaida","express_name":"飞快达"},{"express_id":"guotongkuaidi","express_name":"国通快递"},{"express_id":"ganzhongnengda","express_name":"港中能达物流"},{"express_id":"guangdongyouzhengwuliu","express_name":"广东邮政物流"},{"express_id":"gongsuda","express_name":"共速达"},{"express_id":"hengluwuliu","express_name":"恒路物流"},{"express_id":"huaxialongwuliu","express_name":"华夏龙物流"},{"express_id":"haihongwangsong","express_name":"海红"},{"express_id":"haiwaihuanqiu","express_name":"海外环球"},{"express_id":"jiayiwuliu","express_name":"佳怡物流"},{"express_id":"jinguangsudikuaijian","express_name":"京广速递"},{"express_id":"jixianda","express_name":"急先达"},{"express_id":"jiajiwuliu","express_name":"佳吉物流"},{"express_id":"jymwl","express_name":"加运美物流"},{"express_id":"jindawuliu","express_name":"金大物流"},{"express_id":"jialidatong","express_name":"嘉里大通"},{"express_id":"jykd","express_name":"晋越快递"},{"express_id":"kuaijiesudi","express_name":"快捷速递"},{"express_id":"lianb","express_name":"联邦快递（国内）"},{"express_id":"lianhaowuliu","express_name":"联昊通物流"},{"express_id":"longbanwuliu","express_name":"龙邦物流"},{"express_id":"lijisong","express_name":"立即送"},{"express_id":"lejiedi","express_name":"乐捷递"},{"express_id":"minghangkuaidi","express_name":"民航快递"},{"express_id":"meiguokuaidi","express_name":"美国快递"},{"express_id":"menduimen","express_name":"门对门"},{"express_id":"ocs","express_name":"OCS"},{"express_id":"peisihuoyunkuaidi","express_name":"配思货运"},{"express_id":"quanchenkuaidi","express_name":"全晨快递"},{"express_id":"quanjitong","express_name":"全际通物流"},{"express_id":"quanritongkuaidi","express_name":"全日通快递"},{"express_id":"quanyikuaidi","express_name":"全一快递"},{"express_id":"rufengda","express_name":"如风达"},{"express_id":"santaisudi","express_name":"三态速递"},{"express_id":"shenghuiwuliu","express_name":"盛辉物流"},{"express_id":"sue","express_name":"速尔物流"},{"express_id":"shengfeng","express_name":"盛丰物流"},{"express_id":"saiaodi","express_name":"赛澳递"},{"express_id":"tiandihuayu","express_name":"天地华宇"},{"express_id":"tnt","express_name":"tnt"},{"express_id":"ups","express_name":"ups"},{"express_id":"wanjiawuliu","express_name":"万家物流"},{"express_id":"wenjiesudi","express_name":"文捷航空速递"},{"express_id":"wuyuan","express_name":"伍圆"},{"express_id":"wxwl","express_name":"万象物流"},{"express_id":"xinbangwuliu","express_name":"新邦物流"},{"express_id":"xinfengwuliu","express_name":"信丰物流"},{"express_id":"yafengsudi","express_name":"亚风速递"},{"express_id":"yibangwuliu","express_name":"一邦速递"},{"express_id":"youshuwuliu","express_name":"优速物流"},{"express_id":"youzhengguonei","express_name":"邮政快递包裹"},{"express_id":"youzhengguoji","express_name":"邮政国际包裹挂号信"},{"express_id":"yuanchengwuliu","express_name":"远成物流"},{"express_id":"yuanweifeng","express_name":"源伟丰快递"},{"express_id":"yuanzhijiecheng","express_name":"元智捷诚快递"},{"express_id":"yuntongkuaidi","express_name":"运通快递"},{"express_id":"yuefengwuliu","express_name":"越丰物流"},{"express_id":"yad","express_name":"源安达"},{"express_id":"yinjiesudi","express_name":"银捷速递"},{"express_id":"zhongtiekuaiyun","express_name":"中铁快运"},{"express_id":"zhongyouwuliu","express_name":"中邮物流"},{"express_id":"zhongxinda","express_name":"忠信达"},{"express_id":"zhimakaimen","express_name":"芝麻开门"},{"express_id":"annengwuliu","express_name":"安能物流"},{"express_id":"jd","express_name":"京东快递"},{"express_id":"weitepai","express_name":"微特派"},{"express_id":"jiuyescm","express_name":"九曳供应链"}]
         */

        private int status;
        private String refund_id;
        private int refund_status;
        private int refund_type;
        private String refund_msg;
        private RefundAddrBean refund_addr;
        private ExpressInfoBean express_info;
        private RefundInfoBean refund_info;
        private List<String> process;
        private List<ExpressDataBean> express_data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRefund_id() {
            return refund_id;
        }

        public void setRefund_id(String refund_id) {
            this.refund_id = refund_id;
        }

        public int getRefund_status() {
            return refund_status;
        }

        public void setRefund_status(int refund_status) {
            this.refund_status = refund_status;
        }

        public int getRefund_type() {
            return refund_type;
        }

        public void setRefund_type(int refund_type) {
            this.refund_type = refund_type;
        }

        public String getRefund_msg() {
            return refund_msg;
        }

        public void setRefund_msg(String refund_msg) {
            this.refund_msg = refund_msg;
        }

        public RefundAddrBean getRefund_addr() {
            return refund_addr;
        }

        public void setRefund_addr(RefundAddrBean refund_addr) {
            this.refund_addr = refund_addr;
        }

        public ExpressInfoBean getExpress_info() {
            return express_info;
        }

        public void setExpress_info(ExpressInfoBean express_info) {
            this.express_info = express_info;
        }

        public RefundInfoBean getRefund_info() {
            return refund_info;
        }

        public void setRefund_info(RefundInfoBean refund_info) {
            this.refund_info = refund_info;
        }

        public List<String> getProcess() {
            return process;
        }

        public void setProcess(List<String> process) {
            this.process = process;
        }

        public List<ExpressDataBean> getExpress_data() {
            return express_data;
        }

        public void setExpress_data(List<ExpressDataBean> express_data) {
            this.express_data = express_data;
        }

        public static class RefundAddrBean {
            /**
             * address :
             * realname :
             * mobile :
             */

            private String address;
            private String realname;
            private String mobile;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
        }

        public static class ExpressInfoBean implements Serializable {
            /**
             * rexpresscom :
             * rexpresssn :
             * express :
             * expresscom :
             * expresssn :
             */

            private String rexpresscom;
            private String rexpresssn;
            private String express;
            private String expresscom;
            private String expresssn;

            public String getRexpresscom() {
                return rexpresscom;
            }

            public void setRexpresscom(String rexpresscom) {
                this.rexpresscom = rexpresscom;
            }

            public String getRexpresssn() {
                return rexpresssn;
            }

            public void setRexpresssn(String rexpresssn) {
                this.rexpresssn = rexpresssn;
            }

            public String getExpress() {
                return express;
            }

            public void setExpress(String express) {
                this.express = express;
            }

            public String getExpresscom() {
                return expresscom;
            }

            public void setExpresscom(String expresscom) {
                this.expresscom = expresscom;
            }

            public String getExpresssn() {
                return expresssn;
            }

            public void setExpresssn(String expresssn) {
                this.expresssn = expresssn;
            }
        }

        public static class RefundInfoBean {
            /**
             * refund_price : 895.60
             * refund_typestr : 换货
             * reason : 拍错了/订单信息错误
             * content : 退款说明测试测试
             * apply_time : 2020-01-18 13:49
             */

            private String refund_price;
            private String refund_typestr;
            private String reason;
            private String content;
            private String apply_time;

            public String getRefund_price() {
                return refund_price;
            }

            public void setRefund_price(String refund_price) {
                this.refund_price = refund_price;
            }

            public String getRefund_typestr() {
                return refund_typestr;
            }

            public void setRefund_typestr(String refund_typestr) {
                this.refund_typestr = refund_typestr;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getApply_time() {
                return apply_time;
            }

            public void setApply_time(String apply_time) {
                this.apply_time = apply_time;
            }
        }

        public static class ExpressDataBean implements Serializable{
            /**
             * express_id : shunfeng
             * express_name : 顺丰
             */

            private String express_id;
            private String express_name;

            public String getExpress_id() {
                return express_id;
            }

            public void setExpress_id(String express_id) {
                this.express_id = express_id;
            }

            public String getExpress_name() {
                return express_name;
            }

            public void setExpress_name(String express_name) {
                this.express_name = express_name;
            }
        }
    }
}
