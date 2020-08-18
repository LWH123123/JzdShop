package com.jzd.jzshop.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LWH
 * @description:
 * @date :2020/1/7 9:56
 */
public class MerchEntity {

    /**
     * result : {"status":-2,"reason":"","realname":"","mobile":"","merchname":"","salecate":"","desc":"","uname":"","open_protocol":1,"applytitle":"","applycontent":"","fields":[{"tp_key":"diysanzhengheyiyingyezhizhao","tp_name":"三证合一营业执照","tp_must":1,"tp_value":[]},{"tp_key":"diyyinxingkaihuxukezheng","tp_name":"银行开户许可证","tp_must":0,"tp_value":[]},{"tp_key":"diyfarenshenfenzhengzhengfanmian","tp_name":"法人身份证正反面","tp_must":0,"tp_value":[]},{"tp_key":"diyyibannashuirenzigezheng","tp_name":"一般纳税人资格证","tp_must":0,"tp_value":[]},{"tp_key":"diyziyingdianshouquanshu","tp_name":"自营店授权书","tp_must":0,"tp_value":[]},{"tp_key":"diyruzhushouquanshu","tp_name":"入驻授权书","tp_must":0,"tp_value":[]},{"tp_key":"diyxukezhengzigezheng","tp_name":"许可证/资格证","tp_must":0,"tp_value":[]}]}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Parcelable {
        /**
         * status : -2
         * reason :
         * realname :
         * mobile :
         * merchname :
         * salecate :
         * desc :
         * uname :
         * open_protocol : 1
         * applytitle :
         * applycontent :
         * fields : [{"tp_key":"diysanzhengheyiyingyezhizhao","tp_name":"三证合一营业执照","tp_must":1,"tp_value":[]},{"tp_key":"diyyinxingkaihuxukezheng","tp_name":"银行开户许可证","tp_must":0,"tp_value":[]},{"tp_key":"diyfarenshenfenzhengzhengfanmian","tp_name":"法人身份证正反面","tp_must":0,"tp_value":[]},{"tp_key":"diyyibannashuirenzigezheng","tp_name":"一般纳税人资格证","tp_must":0,"tp_value":[]},{"tp_key":"diyziyingdianshouquanshu","tp_name":"自营店授权书","tp_must":0,"tp_value":[]},{"tp_key":"diyruzhushouquanshu","tp_name":"入驻授权书","tp_must":0,"tp_value":[]},{"tp_key":"diyxukezhengzigezheng","tp_name":"许可证/资格证","tp_must":0,"tp_value":[]}]
         */

        private int status;
        private String reason;
        private String realname;
        private String mobile;
        private String merchname;
        private String salecate;
        private String desc;
        private String uname;
        private int open_protocol;
        private String applytitle;
        private String applycontent;
        private List<FieldsBean> fields;

        public ResultBean() {
        }

        public ResultBean(Parcel in) {
            status = in.readInt();
            reason = in.readString();
            realname = in.readString();
            mobile = in.readString();
            merchname = in.readString();
            salecate = in.readString();
            desc = in.readString();
            uname = in.readString();
            open_protocol = in.readInt();
            applytitle = in.readString();
            applycontent = in.readString();
        }

        public static final Creator<ResultBean> CREATOR = new Creator<ResultBean>() {
            @Override
            public ResultBean createFromParcel(Parcel in) {
                return new ResultBean(in);
            }

            @Override
            public ResultBean[] newArray(int size) {
                return new ResultBean[size];
            }
        };

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
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

        public String getMerchname() {
            return merchname;
        }

        public void setMerchname(String merchname) {
            this.merchname = merchname;
        }

        public String getSalecate() {
            return salecate;
        }

        public void setSalecate(String salecate) {
            this.salecate = salecate;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public int getOpen_protocol() {
            return open_protocol;
        }

        public void setOpen_protocol(int open_protocol) {
            this.open_protocol = open_protocol;
        }

        public String getApplytitle() {
            return applytitle;
        }

        public void setApplytitle(String applytitle) {
            this.applytitle = applytitle;
        }

        public String getApplycontent() {
            return applycontent;
        }

        public void setApplycontent(String applycontent) {
            this.applycontent = applycontent;
        }

        public ArrayList<? extends Parcelable> getFields() {
            return (ArrayList<? extends Parcelable>) fields;
        }

        public void setFields(List<FieldsBean> fields) {
            this.fields = fields;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(status);
            dest.writeString(reason);
            dest.writeString(realname);
            dest.writeString(mobile);
            dest.writeString(merchname);
            dest.writeString(salecate);
            dest.writeString(desc);
            dest.writeString(uname);
            dest.writeInt(open_protocol);
            dest.writeString(applytitle);
            dest.writeString(applycontent);
        }

        public static class FieldsBean implements Parcelable{
            /**
             * tp_key : diysanzhengheyiyingyezhizhao
             * tp_name : 三证合一营业执照
             * tp_must : 1
             * tp_value : []
             */

            private String tp_key;
            private String tp_name;
            private int tp_must;
            private String tp_value;
            private File file;


            protected FieldsBean(Parcel in) {
                tp_key = in.readString();
                tp_name = in.readString();
                tp_must = in.readInt();
                tp_value = in.readString();
            }

            public static final Creator<FieldsBean> CREATOR = new Creator<FieldsBean>() {
                @Override
                public FieldsBean createFromParcel(Parcel in) {
                    return new FieldsBean(in);
                }

                @Override
                public FieldsBean[] newArray(int size) {
                    return new FieldsBean[size];
                }
            };

            public File getFile() {
                return file;
            }

            public void setFile(File file) {
                this.file = file;
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


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(tp_key);
                dest.writeString(tp_name);
                dest.writeInt(tp_must);
                dest.writeString(tp_value);
            }
        }
    }
}
