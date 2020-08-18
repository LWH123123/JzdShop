package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author lwh
 * @description : 表单列表数据合集
 * @date 2020/4/24.
 */
public class BaseFormEntity {

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

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
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
