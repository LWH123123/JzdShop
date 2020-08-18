package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author lwh
 * @description :
 * @date 2020/3/31.
 */
public class MineShopEntity {

    /**
     * result : {"status":200,"data":{"refuse":"","base":[{"tp_type":0,"tp_key":"gdnames","tp_name":"小店名称","tp_must":1,"tp_value":"","tp_holder":"请输入小店名称","tp_max":1},{"tp_type":0,"tp_key":"gddesc","tp_name":"小店介绍","tp_must":1,"tp_value":"","tp_holder":"请输入小店介绍"},{"tp_type":5,"tp_key":"gdlogo","tp_name":"小店logo","tp_must":0,"tp_value":"","tp_max":1},{"tp_type":5,"tp_key":"gdsigns","tp_name":"店招","tp_must":0,"tp_value":"","tp_max":1}],"personal":[{"tp_type":0,"tp_key":"gdmanage","tp_name":"小店管理","tp_must":0,"tp_value":"","tp_holder":"请输入小店管理人","tp_max":1},{"tp_type":0,"tp_key":"gdtel","tp_name":"联系方式","tp_must":0,"tp_value":"","tp_holder":"请输入小店管联系方式理人"},{"tp_type":5,"tp_key":"gdidcard1","tp_name":"身份证（正面）","tp_must":0,"tp_value":"","tp_max":1},{"tp_type":5,"tp_key":"gdidcard2","tp_name":"身份证（反面）","tp_must":0,"tp_value":"","tp_max":1}]}}
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
         * data : {"refuse":"","base":[{"tp_type":0,"tp_key":"gdnames","tp_name":"小店名称","tp_must":1,"tp_value":"","tp_holder":"请输入小店名称"},{"tp_type":0,"tp_key":"gddesc","tp_name":"小店介绍","tp_must":1,"tp_value":"","tp_holder":"请输入小店介绍"},{"tp_type":5,"tp_key":"gdlogo","tp_name":"小店logo","tp_must":0,"tp_value":"","tp_max":1},{"tp_type":5,"tp_key":"gdsigns","tp_name":"店招","tp_must":0,"tp_value":"","tp_max":1}],"personal":[{"tp_type":0,"tp_key":"gdmanage","tp_name":"小店管理","tp_must":0,"tp_value":"","tp_holder":"请输入小店管理人"},{"tp_type":0,"tp_key":"gdtel","tp_name":"联系方式","tp_must":0,"tp_value":"","tp_holder":"请输入小店管联系方式理人"},{"tp_type":5,"tp_key":"gdidcard1","tp_name":"身份证（正面）","tp_must":0,"tp_value":"","tp_max":1},{"tp_type":5,"tp_key":"gdidcard2","tp_name":"身份证（反面）","tp_must":0,"tp_value":"","tp_max":1}]}
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
             * refuse :
             * base : [{"tp_type":0,"tp_key":"gdnames","tp_name":"小店名称","tp_must":1,"tp_value":"","tp_holder":"请输入小店名称"},{"tp_type":0,"tp_key":"gddesc","tp_name":"小店介绍","tp_must":1,"tp_value":"","tp_holder":"请输入小店介绍"},{"tp_type":5,"tp_key":"gdlogo","tp_name":"小店logo","tp_must":0,"tp_value":"","tp_max":1},{"tp_type":5,"tp_key":"gdsigns","tp_name":"店招","tp_must":0,"tp_value":"","tp_max":1}]
             * personal : [{"tp_type":0,"tp_key":"gdmanage","tp_name":"小店管理","tp_must":0,"tp_value":"","tp_holder":"请输入小店管理人"},{"tp_type":0,"tp_key":"gdtel","tp_name":"联系方式","tp_must":0,"tp_value":"","tp_holder":"请输入小店管联系方式理人"},{"tp_type":5,"tp_key":"gdidcard1","tp_name":"身份证（正面）","tp_must":0,"tp_value":"","tp_max":1},{"tp_type":5,"tp_key":"gdidcard2","tp_name":"身份证（反面）","tp_must":0,"tp_value":"","tp_max":1}]
             */

            private String refuse;
            private List<BaseBean> base;
            private List<PersonalBean> personal;

            public String getRefuse() {
                return refuse;
            }

            public void setRefuse(String refuse) {
                this.refuse = refuse;
            }

            public List<BaseBean> getBase() {
                return base;
            }

            public void setBase(List<BaseBean> base) {
                this.base = base;
            }

            public List<PersonalBean> getPersonal() {
                return personal;
            }

            public void setPersonal(List<PersonalBean> personal) {
                this.personal = personal;
            }

            public static class BaseBean {
                /**
                 * tp_type : 0
                 * tp_key : gdnames
                 * tp_name : 小店名称
                 * tp_must : 1
                 * tp_value :
                 * tp_holder : 请输入小店名称
                 * tp_max : 1 图片上传可最多保存的数量
                 * tp_name2 : 确认文本
                 * tp_valuelist : 集合数据
                 * tp_holderlist : 确认文本提示字
                 * tp_valueaddress : 地址数据
                 * tp_textList : 选项值
                 * tp_area : 地址类型
                 */

                private int tp_type;
                private String tp_key;
                private String tp_name;
                private int tp_must;
                private String tp_value;
                private String tp_holder;
                private int tp_max;
                private String tp_name2;
                private List<String> tp_valuelist;
                private List<String> tp_holderlist;
                private TpvalueBean tp_valueaddress;
                private List<String> tp_textList;
                private int area;

                public int getArea() {
                    return area;
                }

                public void setArea(int area) {
                    this.area = area;
                }

                public String getTp_name2() {
                    return tp_name2;
                }

                public void setTp_name2(String tp_name2) {
                    this.tp_name2 = tp_name2;
                }

                public List<String> getTp_valuelist() {
                    return tp_valuelist;
                }

                public void setTp_valuelist(List<String> tp_valuelist) {
                    this.tp_valuelist = tp_valuelist;
                }

                public List<String> getTp_holderlist() {
                    return tp_holderlist;
                }

                public void setTp_holderlist(List<String> tp_holderlist) {
                    this.tp_holderlist = tp_holderlist;
                }

                public TpvalueBean getTp_valueaddress() {
                    return tp_valueaddress;
                }

                public void setTp_valueaddress(TpvalueBean tp_valueaddress) {
                    this.tp_valueaddress = tp_valueaddress;
                }

                public List<String> getTp_textList() {
                    return tp_textList;
                }

                public void setTp_textList(List<String> tp_textList) {
                    this.tp_textList = tp_textList;
                }

                public int getTp_type() {
                    return tp_type;
                }

                public void setTp_type(int tp_type) {
                    this.tp_type = tp_type;
                }

                public String getTp_key() {
                    return tp_key;
                }

                public void setTp_key(String tp_key) {
                    this.tp_key = tp_key;
                }

                public String getTp_name() {
                    return tp_name;
                }

                public void setTp_name(String tp_name) {
                    this.tp_name = tp_name;
                }

                public int getTp_must() {
                    return tp_must;
                }

                public void setTp_must(int tp_must) {
                    this.tp_must = tp_must;
                }

                public String getTp_value() {
                    return tp_value;
                }

                public void setTp_value(String tp_value) {
                    this.tp_value = tp_value;
                }

                public String getTp_holder() {
                    return tp_holder;
                }

                public void setTp_holder(String tp_holder) {
                    this.tp_holder = tp_holder;
                }

                public int getTp_max() {
                    return tp_max;
                }

                public void setTp_max(int tp_max) {
                    this.tp_max = tp_max;
                }
            }

            public static class PersonalBean {
                /**
                 * tp_type : 0
                 * tp_key : gdmanage
                 * tp_name : 小店管理
                 * tp_must : 0
                 * tp_value :
                 * tp_holder : 请输入小店管理人
                 * tp_max : 1
                 */

                private int tp_type;
                private String tp_key;
                private String tp_name;
                private int tp_must;
                private String tp_value;
                private String tp_holder;
                private int tp_max;
                private String tp_name2;
                private List<String> tp_valuelist;
                private List<String> tp_holderlist;
                private TpvalueBean tp_valueaddress;
                private List<String> tp_textList;


                private int area;

                public int getArea() {
                    return area;
                }

                public void setArea(int area) {
                    this.area = area;
                }

                public String getTp_name2() {
                    return tp_name2;
                }

                public void setTp_name2(String tp_name2) {
                    this.tp_name2 = tp_name2;
                }

                public List<String> getTp_valuelist() {
                    return tp_valuelist;
                }

                public void setTp_valuelist(List<String> tp_valuelist) {
                    this.tp_valuelist = tp_valuelist;
                }

                public List<String> getTp_holderlist() {
                    return tp_holderlist;
                }

                public void setTp_holderlist(List<String> tp_holderlist) {
                    this.tp_holderlist = tp_holderlist;
                }

                public TpvalueBean getTp_valueaddress() {
                    return tp_valueaddress;
                }

                public void setTp_valueaddress(TpvalueBean tp_valueaddress) {
                    this.tp_valueaddress = tp_valueaddress;
                }

                public List<String> getTp_textList() {
                    return tp_textList;
                }

                public void setTp_textList(List<String> tp_textList) {
                    this.tp_textList = tp_textList;
                }

                public int getTp_type() {
                    return tp_type;
                }

                public void setTp_type(int tp_type) {
                    this.tp_type = tp_type;
                }

                public String getTp_key() {
                    return tp_key;
                }

                public void setTp_key(String tp_key) {
                    this.tp_key = tp_key;
                }

                public String getTp_name() {
                    return tp_name;
                }

                public void setTp_name(String tp_name) {
                    this.tp_name = tp_name;
                }

                public int getTp_must() {
                    return tp_must;
                }

                public void setTp_must(int tp_must) {
                    this.tp_must = tp_must;
                }

                public String getTp_value() {
                    return tp_value;
                }

                public void setTp_value(String tp_value) {
                    this.tp_value = tp_value;
                }

                public String getTp_holder() {
                    return tp_holder;
                }

                public void setTp_holder(String tp_holder) {
                    this.tp_holder = tp_holder;
                }

                public int getTp_max() {
                    return tp_max;
                }

                public void setTp_max(int tp_max) {
                    this.tp_max = tp_max;
                }
            }




            public static class TpvalueBean{
                /* "province": "",
                         "city": "",
                         "area": */
                private String province;
                private String city;
                private String area;

                public String getProvince() {
                    return province;
                }

                public void setProvince(String province) {
                    this.province = province;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getArea() {
                    return area;
                }

                public void setArea(String area) {
                    this.area = area;
                }

                public TpvalueBean(String province, String city, String area) {
                    this.province = province;
                    this.city = city;
                    this.area = area;
                }

                @Override
                public String toString() {
                    return "TpvalueBean{" +
                            "province='" + province + '\'' +
                            ", city='" + city + '\'' +
                            ", area='" + area + '\'' +
                            '}';
                }
            }
        }
    }
}
