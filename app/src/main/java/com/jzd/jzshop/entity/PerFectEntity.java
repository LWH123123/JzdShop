package com.jzd.jzshop.entity;

import java.util.List;

public class PerFectEntity {

    /**
     * result : {"avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/swibswvUoYmJpUjMDTChDa0LoJlyiapMbVKicpECFDO15SNFXPuTe6b64uWPuGib1cudfib1x2aEqr8Q8MRulYn8nsw/132","nickname":"MELI156","mobile":"18235822259","mobile_verify":1,"fields":[{"tp_type":0,"tp_key":"diynidemingzi","tp_name":"你的名字","tp_must":1,"tp_value":"","tp_holder":"请输入你的名字","tp_text":["大专","本科","硕士","博士","其它"],"tp_max":5,"tp_area":0,"tp_name2":"确认密码"},{"tp_type":1,"tp_key":"diyziwojieshao","tp_name":"自我介绍","tp_must":0,"tp_value":"","tp_holder":"请输入自我介绍"},{"tp_type":2,"tp_key":"diyxueli","tp_name":"学历","tp_must":0,"tp_value":"","tp_text":["大专","本科","硕士","博士","其它"]},{"tp_type":3,"tp_key":"diyxingquaihao","tp_name":"兴趣爱好","tp_must":0,"tp_value":[],"tp_text":["读书","打球","看电影","其它"]},{"tp_type":14,"tp_key":"diynianling","tp_name":"年龄","tp_must":0,"tp_value":"","tp_text":["0~20岁","21~40岁","41~60岁","61~80岁","81岁以上"]},{"tp_type":5,"tp_key":"diytupianzhanshi","tp_name":"图片展示","tp_must":0,"tp_value":[],"tp_max":5},{"tp_type":6,"tp_key":"diyshenfenzhenghaoma","tp_name":"身份证号码","tp_must":0,"tp_value":"","tp_holder":"请输入身份证号码"},{"tp_type":7,"tp_key":"diychushengriqi","tp_name":"出生日期","tp_must":0,"tp_value":"","tp_holder":"请输入出生日期"},{"tp_type":8,"tp_key":"diybiyeriqi","tp_name":"毕业日期","tp_must":0,"tp_value":["",""]},{"tp_type":9,"tp_key":"diyhujidi","tp_name":"户籍地","tp_must":0,"tp_holder":"请输入户籍地","tp_area":0,"tp_value":{"province":"","city":"","area":""}},{"tp_type":9,"tp_key":"diyzhuzhisuozaidi","tp_name":"住址所在地","tp_must":0,"tp_holder":"请输入住址所在地","tp_area":1,"tp_value":{"province":"","city":"","area":""}},{"tp_type":10,"tp_key":"diymima","tp_name":"密码","tp_must":0,"tp_name2":"确认密码","tp_value":[],"tp_holder":["请输入密码","请输入确认密码"]},{"tp_type":11,"tp_key":"diyshuijueshijian","tp_name":"睡觉时间","tp_must":0,"tp_value":"","tp_holder":"请输入睡觉时间"},{"tp_type":12,"tp_key":"diyshangbanshijian","tp_name":"上班时间","tp_must":0,"tp_value":[]},{"tp_type":13,"tp_key":"diywanshangerenxinxihoufangbianwomengenghaodeweininjinxingfuwu","tp_name":"完善个人信息后，方便我们更好的为您进行服务！","tp_must":0}]}
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
         * avatar : http://thirdwx.qlogo.cn/mmopen/vi_32/swibswvUoYmJpUjMDTChDa0LoJlyiapMbVKicpECFDO15SNFXPuTe6b64uWPuGib1cudfib1x2aEqr8Q8MRulYn8nsw/132
         * nickname : MELI156
         * mobile : 18235822259
         * mobile_verify : 1
         * fields : [{"tp_type":0,"tp_key":"diynidemingzi","tp_name":"你的名字","tp_must":1,"tp_value":"","tp_holder":"请输入你的名字"},{"tp_type":1,"tp_key":"diyziwojieshao","tp_name":"自我介绍","tp_must":0,"tp_value":"","tp_holder":"请输入自我介绍"},{"tp_type":2,"tp_key":"diyxueli","tp_name":"学历","tp_must":0,"tp_value":"","tp_text":["大专","本科","硕士","博士","其它"]},{"tp_type":3,"tp_key":"diyxingquaihao","tp_name":"兴趣爱好","tp_must":0,"tp_value":[],"tp_text":["读书","打球","看电影","其它"]},{"tp_type":14,"tp_key":"diynianling","tp_name":"年龄","tp_must":0,"tp_value":"","tp_text":["0~20岁","21~40岁","41~60岁","61~80岁","81岁以上"]},{"tp_type":5,"tp_key":"diytupianzhanshi","tp_name":"图片展示","tp_must":0,"tp_value":[],"tp_max":5},{"tp_type":6,"tp_key":"diyshenfenzhenghaoma","tp_name":"身份证号码","tp_must":0,"tp_value":"","tp_holder":"请输入身份证号码"},{"tp_type":7,"tp_key":"diychushengriqi","tp_name":"出生日期","tp_must":0,"tp_value":"","tp_holder":"请输入出生日期"},{"tp_type":8,"tp_key":"diybiyeriqi","tp_name":"毕业日期","tp_must":0,"tp_value":["",""]},{"tp_type":9,"tp_key":"diyhujidi","tp_name":"户籍地","tp_must":0,"tp_holder":"请输入户籍地","tp_area":0,"tp_value":{"province":"","city":"","area":""}},{"tp_type":9,"tp_key":"diyzhuzhisuozaidi","tp_name":"住址所在地","tp_must":0,"tp_holder":"请输入住址所在地","tp_area":1,"tp_value":{"province":"","city":"","area":""}},{"tp_type":10,"tp_key":"diymima","tp_name":"密码","tp_must":0,"tp_name2":"确认密码","tp_value":[],"tp_holder":["请输入密码","请输入确认密码"]},{"tp_type":11,"tp_key":"diyshuijueshijian","tp_name":"睡觉时间","tp_must":0,"tp_value":"","tp_holder":"请输入睡觉时间"},{"tp_type":12,"tp_key":"diyshangbanshijian","tp_name":"上班时间","tp_must":0,"tp_value":[]},{"tp_type":13,"tp_key":"diywanshangerenxinxihoufangbianwomengenghaodeweininjinxingfuwu","tp_name":"完善个人信息后，方便我们更好的为您进行服务！","tp_must":0}]
         */

        private String avatar;
        private String nickname;
        private String mobile;
        private double mobile_verify;
        private List<FieldsBean> fields;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public double getMobile_verify() {
            return mobile_verify;
        }

        public void setMobile_verify(double mobile_verify) {
            this.mobile_verify = mobile_verify;
        }

        public List<FieldsBean> getFields() {
            return fields;
        }

        public void setFields(List<FieldsBean> fields) {
            this.fields = fields;
        }

        public static class FieldsBean {
            /**
             * tp_type : 0
             * tp_key : diynidemingzi
             * tp_name : 你的名字
             * tp_must : 1
             * tp_value :
             * tp_holder : 请输入你的名字
             * tp_text : ["大专","本科","硕士","博士","其它"]
             * tp_max : 5
             * tp_area : 0
             * tp_name2 : 确认密码
             */

            private double tp_type;
            private String tp_key;
            private String tp_name;
            private double tp_must;
            private String tp_string;
            private List<String> tp_array;
            private TpvalueBean tp_value;
            private Object tp_holder;
            private List<String> holder_array;
            private String holder_string;
            private double tp_max;
            private double tp_area;
            private String tp_name2;
            private List<String> tp_text;


            public FieldsBean() {
            }

            // 0 1 6 7 11
            public FieldsBean(double tp_type, String tp_key, String tp_name, double tp_must, String tp_string, String holder_string) {
                this.tp_type = tp_type;
                this.tp_key = tp_key;
                this.tp_name = tp_name;
                this.tp_must = tp_must;
                this.tp_string = tp_string;
                this.holder_string = holder_string;
            }

            /*tp_value*/
//字符串 0 1 2 14 6 7 11
//数组  3 5 8 10 12
//对象  9


/*tp_holder*/
//字符串 0 1 6 7 9 11
//数组 10


            public FieldsBean(double tp_type, String tp_key, String tp_name, double tp_must, String tp_string, List<String> tp_text) {
                this.tp_type = tp_type;
                this.tp_key = tp_key;
                this.tp_name = tp_name;
                this.tp_must = tp_must;
                this.tp_string = tp_string;
                this.tp_text = tp_text;
            }

            public FieldsBean(double tp_type, String tp_key, String tp_name, double tp_must, List<String> tp_array, List<String> tp_text) {
                this.tp_type = tp_type;
                this.tp_key = tp_key;
                this.tp_name = tp_name;
                this.tp_must = tp_must;
                this.tp_array = tp_array;
                this.tp_text = tp_text;
            }

            public FieldsBean(double tp_type, String tp_key, String tp_name, double tp_must, List<String> tp_array, double tp_max) {
                this.tp_type = tp_type;
                this.tp_key = tp_key;
                this.tp_name = tp_name;
                this.tp_must = tp_must;
                this.tp_array = tp_array;
                this.tp_max = tp_max;
            }

            public FieldsBean(double tp_type, String tp_key, String tp_name, double tp_must, List<String> tp_array) {
                this.tp_type = tp_type;
                this.tp_key = tp_key;
                this.tp_name = tp_name;
                this.tp_must = tp_must;
                this.tp_array = tp_array;
            }

            public FieldsBean(double tp_type, String tp_key, String tp_name, double tp_must, List<String> tp_array, List<String> holder_array, String tp_name2) {
                this.tp_type = tp_type;
                this.tp_key = tp_key;
                this.tp_name = tp_name;
                this.tp_must = tp_must;
                this.tp_array = tp_array;
                this.holder_array = holder_array;
                this.tp_name2 = tp_name2;
            }

            public FieldsBean(double tp_type, String tp_key, String tp_name, double tp_must, TpvalueBean tp_value, String holder_string, double tp_area) {
                this.tp_type = tp_type;
                this.tp_key = tp_key;
                this.tp_name = tp_name;
                this.tp_must = tp_must;
                this.tp_value = tp_value;
                this.holder_string = holder_string;
                this.tp_area = tp_area;
            }

      /*      public FieldsBean(double tp_type, String tp_key, String tp_name, double tp_must,String tp_value,String tp_holder) {
                this.tp_type = tp_type;
                this.tp_key = tp_key;
                this.tp_name = tp_name;
                this.tp_must = tp_must;
                this.tp_string=tp_value;
                this.holder_string=tp_holder;
            }
*/
            public List<String> getHolder_array() {
                return holder_array;
            }

            public void setHolder_array(List<String> holder_array) {
                this.holder_array = holder_array;
            }

            public String getHolder_string() {
                return holder_string;
            }

            public void setHolder_string(String holder_string) {
                this.holder_string = holder_string;
            }

            public String getTp_string() {
                return tp_string;
            }

            public void setTp_string(String tp_string) {
                this.tp_string = tp_string;
            }

            public List<String> getTp_array() {
                return tp_array;
            }

            public void setTp_array(List<String> tp_array) {
                this.tp_array = tp_array;
            }

            public double getTp_type() {
                return tp_type;
            }

            public void setTp_type(double tp_type) {
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

            public double getTp_must() {
                return tp_must;
            }

            public void setTp_must(double tp_must) {
                this.tp_must = tp_must;
            }

            public TpvalueBean getTp_value() {
                return tp_value;
            }

            public void setTp_value(TpvalueBean tp_value) {
                this.tp_value = tp_value;
            }

            public Object getTp_holder() {
                return tp_holder;
            }

            public void setTp_holder(Object tp_holder) {
                this.tp_holder = tp_holder;
            }

            public double getTp_max() {
                return tp_max;
            }

            public void setTp_max(double tp_max) {
                this.tp_max = tp_max;
            }

            public double getTp_area() {
                return tp_area;
            }

            public void setTp_area(double tp_area) {
                this.tp_area = tp_area;
            }

            public String getTp_name2() {
                return tp_name2;
            }

            public void setTp_name2(String tp_name2) {
                this.tp_name2 = tp_name2;
            }

            public List<String> getTp_text() {
                return tp_text;
            }

            public void setTp_text(List<String> tp_text) {
                this.tp_text = tp_text;
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
