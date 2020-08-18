package com.jzd.jzshop.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class AddressListEntity {


    /**
     * result : {"total":5,"data":[{"addr_id":"ml0IKeOx9D3iIQaaWVQGmsGrnwbMoMg","realname":"ought","mobile":"18235822259","province":"山西省","city":"大同市","area":"矿区","address":"楼梯","isdefault":1},{"addr_id":"yttamTh_OONX_isEEJNCvTIT_ASt0_rkowa8oMw","realname":"涛哥哥哥","mobile":"18335884849","province":"山西省","city":"大同市","area":"左云县","address":"发红包吧","isdefault":0},{"addr_id":"nNxIhbk_E7tA_yLw2QbDcZJj6rsg1k8sMw","realname":"顾GV高","mobile":"18235885659","province":"山东省","city":"青岛市","area":"城阳区","address":")娇娇女","isdefault":0},{"addr_id":"yIOumLqrKT5HQWZd2CTiaSmrk8zvxwMw","realname":"jxnd","mobile":"18358222358","province":"河南省","city":"郑州市","area":"市辖区","address":"nxjd","isdefault":0},{"addr_id":"RzXdUWy1brPSEcPeQWZmWqTraka8t0MQ","realname":"www","mobile":"18235828856","province":"广西壮族自治区","city":"钦州市","area":"浦北县","address":"zbjjsnk","isdefault":0}]}
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
         * total : 5
         * data : [{"addr_id":"ml0IKeOx9D3iIQaaWVQGmsGrnwbMoMg","realname":"ought","mobile":"18235822259","province":"山西省","city":"大同市","area":"矿区","address":"楼梯","isdefault":1},{"addr_id":"yttamTh_OONX_isEEJNCvTIT_ASt0_rkowa8oMw","realname":"涛哥哥哥","mobile":"18335884849","province":"山西省","city":"大同市","area":"左云县","address":"发红包吧","isdefault":0},{"addr_id":"nNxIhbk_E7tA_yLw2QbDcZJj6rsg1k8sMw","realname":"顾GV高","mobile":"18235885659","province":"山东省","city":"青岛市","area":"城阳区","address":")娇娇女","isdefault":0},{"addr_id":"yIOumLqrKT5HQWZd2CTiaSmrk8zvxwMw","realname":"jxnd","mobile":"18358222358","province":"河南省","city":"郑州市","area":"市辖区","address":"nxjd","isdefault":0},{"addr_id":"RzXdUWy1brPSEcPeQWZmWqTraka8t0MQ","realname":"www","mobile":"18235828856","province":"广西壮族自治区","city":"钦州市","area":"浦北县","address":"zbjjsnk","isdefault":0}]
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

        public static class DataBean implements Parcelable {
            /**
             * addr_id : ml0IKeOx9D3iIQaaWVQGmsGrnwbMoMg
             * realname : ought
             * mobile : 18235822259
             * province : 山西省
             * city : 大同市
             * area : 矿区
             * address : 楼梯
             * isdefault : 1
             */

            private String addr_id;
            private String realname;
            private String mobile;
            private String province;
            private String city;
            private String area;
            private String address;
            private int isdefault;

            protected DataBean(Parcel in) {
                addr_id = in.readString();
                realname = in.readString();
                mobile = in.readString();
                province = in.readString();
                city = in.readString();
                area = in.readString();
                address = in.readString();
                isdefault = in.readInt();
            }

            public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
                @Override
                public DataBean createFromParcel(Parcel in) {
                    return new DataBean(in);
                }

                @Override
                public DataBean[] newArray(int size) {
                    return new DataBean[size];
                }
            };

            public String getAddr_id() {
                return addr_id;
            }

            public void setAddr_id(String addr_id) {
                this.addr_id = addr_id;
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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getIsdefault() {
                return isdefault;
            }

            public void setIsdefault(int isdefault) {
                this.isdefault = isdefault;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(addr_id);
                dest.writeString(realname);
                dest.writeString(mobile);
                dest.writeString(province);
                dest.writeString(city);
                dest.writeString(area);
                dest.writeString(address);
                dest.writeInt(isdefault);
            }
        }
    }
}
