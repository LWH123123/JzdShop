package com.jzd.jzshop.entity;

import java.util.List;

/**
 * @author LWH
 * @description:
 * @date :2019/12/10 14:28
 */
public class SecondAddressEntity {

    private List<ProvincesBean> provinces;

    public List<ProvincesBean> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<ProvincesBean> provinces) {
        this.provinces = provinces;
    }

    public static class ProvincesBean {
        /**
         * name : 北京市
         * level : 1
         * code : 1100
         * cities : [{"name":"北京市","level":"1","code":"1100"}]
         */

        private String name;
        private String level;
        private String code;
        private List<CitiesBean> cities;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<CitiesBean> getCities() {
            return cities;
        }

        public void setCities(List<CitiesBean> cities) {
            this.cities = cities;
        }

        public static class CitiesBean {
            /**
             * name : 北京市
             * level : 1
             * code : 1100
             */

            private String name;
            private String level;
            private String code;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }
        }
    }
}
