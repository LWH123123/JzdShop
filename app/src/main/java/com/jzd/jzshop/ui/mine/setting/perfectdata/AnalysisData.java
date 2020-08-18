package com.jzd.jzshop.ui.mine.setting.perfectdata;

import com.jzd.jzshop.entity.PerFectEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author LWH
 * @description:
 * @date :2019/12/9 14:37
 */
public class AnalysisData {

    public PerFectEntity.ResultBean initDatas(String data) {
        /*tp_value*/
//字符串  2 14
//数组  3 5 8 10 12
//对象  9
        /*tp_holder*/
//字符串  9
//数组 10
//0 1 6 7 11
        PerFectEntity.ResultBean resultBean = new PerFectEntity.ResultBean();
        ArrayList<PerFectEntity.ResultBean.FieldsBean> fieds = new ArrayList<>();
        try {
            JSONObject jsob = new JSONObject(data);
            JSONObject result = jsob.optJSONObject("result");
            String avatar = result.optString("avatar");
            String nickname = result.optString("nickname");
            String mobile = result.optString("mobile");
            double mobile_verify = result.optDouble("mobile_verify");
            resultBean.setAvatar(avatar);
            resultBean.setMobile(mobile);
            resultBean.setMobile_verify(mobile_verify);
            resultBean.setNickname(nickname);
            JSONArray fields = result.optJSONArray("fields");
            for (int i = 0; i < fields.length(); i++) {
                JSONObject jsonObject = fields.optJSONObject(i);
                double tp_type = jsonObject.optDouble("tp_type");
                String tp_key = jsonObject.optString("tp_key");
                String name = jsonObject.optString("tp_name");
                StringBuffer strName = new StringBuffer();
                strName.append(name);
                if(tp_type!=13) {
                    strName.append(":");
                }
                String tp_name = strName.toString();
                double tp_must = jsonObject.optDouble("tp_must");
//                0 1 6 7 11
                if (tp_type == 0 || tp_type == 1 || tp_type == 6 || tp_type == 7 || tp_type == 11||tp_type==13) {
                    String tp_value = jsonObject.optString("tp_value");
                    String tp_holder = jsonObject.optString("tp_holder");
                    PerFectEntity.ResultBean.FieldsBean fieldsBean = new PerFectEntity.ResultBean.FieldsBean(tp_type, tp_key, tp_name, tp_must, tp_value, tp_holder);
                    fieds.add(fieldsBean);
                }
                //2 14  单选框
                if (tp_type == 2 || tp_type == 14) {
                    String tp_value = jsonObject.optString("tp_value");

                    JSONArray tp_array = jsonObject.optJSONArray("tp_text");
                    ArrayList<String> tp_text = new ArrayList<>();
                    for (int j = 0; j < tp_array.length(); j++) {
                        String s = tp_array.optString(j);
                        tp_text.add(s);
                    }
                    PerFectEntity.ResultBean.FieldsBean fieldsBean = new PerFectEntity.ResultBean.FieldsBean(tp_type, tp_key, tp_name, tp_must, tp_value, tp_text);
                    fieds.add(fieldsBean);
                }
                //3  复选框
                if (tp_type == 3) {
                    JSONArray tp_value = jsonObject.optJSONArray("tp_value");
                    ArrayList<String> tp_string = new ArrayList<>();
                    if(tp_value!=null&&tp_value.length()!=0){
                    for (int j = 0; j < tp_value.length(); j++) {
                        String s = tp_value.optString(j);
                        tp_string.add(s);
                    }
                    }
                    JSONArray tp_array = jsonObject.optJSONArray("tp_text");
                    ArrayList<String> tp_text = new ArrayList<>();
                    for (int j = 0; j < tp_array.length(); j++) {
                        String s = tp_array.optString(j);
                        tp_text.add(s);
                    }
                    PerFectEntity.ResultBean.FieldsBean fieldsBean = new PerFectEntity.ResultBean.FieldsBean(tp_type, tp_key, tp_name, tp_must, tp_string, tp_text);
                    fieds.add(fieldsBean);
                }
                //5 MAX
                if (tp_type == 5) {
                    double tp_max = jsonObject.optDouble("tp_max");
                    JSONArray tp_value = jsonObject.optJSONArray("tp_value");
                    ArrayList<String> tp_string = new ArrayList<>();
                    for (int j = 0; j < tp_value.length(); j++) {
                        String s = tp_value.optString(j);
                        tp_string.add(s);
                    }
                    PerFectEntity.ResultBean.FieldsBean fieldsBean = new PerFectEntity.ResultBean.FieldsBean(tp_type, tp_key, tp_name, tp_must, tp_string, tp_max);
                    fieds.add(fieldsBean);
                }
                //8  12   五个字段
                if (tp_type == 8 || tp_type == 12) {
                    JSONArray tp_value = jsonObject.optJSONArray("tp_value");
                    ArrayList<String> tp_string = new ArrayList<>();
                    if(tp_value!=null&&tp_value.length()!=0) {
                        for (int j = 0; j < tp_value.length(); j++) {
                            String s = tp_value.optString(j);
                            tp_string.add(s);
                        }
                    }

                    PerFectEntity.ResultBean.FieldsBean fieldsBean = new PerFectEntity.ResultBean.FieldsBean(tp_type, tp_key, tp_name, tp_must, tp_string);
                    fieds.add(fieldsBean);
                }
                //10 确认密码
                if (tp_type == 10) {
                    String tp_name2 = jsonObject.optString("tp_name2");
                    JSONArray tp_value = jsonObject.optJSONArray("tp_value");
                    ArrayList<String> tp_string = new ArrayList<>();
                    if(tp_value!=null&&tp_value.length()!=0) {
                        for (int j = 0; j < tp_value.length(); j++) {
                            String s = tp_value.optString(j);
                            tp_string.add(s);
                        }
                    }
                    JSONArray holder_array = jsonObject.optJSONArray("tp_holder");
                    ArrayList<String> tp_holder = new ArrayList<>();
                    for (int j = 0; j < holder_array.length(); j++) {
                        String o = holder_array.optString(j);
                        tp_holder.add(o);
                    }

                    PerFectEntity.ResultBean.FieldsBean fieldsBean = new PerFectEntity.ResultBean.FieldsBean(tp_type, tp_key, tp_name, tp_must, tp_string, tp_holder, tp_name2);
                    fieds.add(fieldsBean);
                }
                //9 地区
                if (tp_type == 9) {
                    double tp_area = jsonObject.optDouble("tp_area");
                    String tp_holder = jsonObject.optString("tp_holder");
                    JSONObject tp_value = jsonObject.optJSONObject("tp_value");
                    String province = tp_value.optString("province");
                    String city = tp_value.optString("city");
                    String area = tp_value.optString("area");
                    PerFectEntity.ResultBean.FieldsBean.TpvalueBean tpvalueBean = new PerFectEntity.ResultBean.FieldsBean.TpvalueBean(province, city, area);
                    PerFectEntity.ResultBean.FieldsBean fieldsBean = new PerFectEntity.ResultBean.FieldsBean(tp_type, tp_key, tp_name, tp_must, tpvalueBean, tp_holder, tp_area);
                    fieds.add(fieldsBean);
                }
              /*  //提示信息
                if (tp_type == 13) {
                    String tp_value = jsonObject.optString("tp_value");
                    String tp_holder = jsonObject.optString("tp_holder");
                    PerFectEntity.ResultBean.FieldsBean fieldsBean = new PerFectEntity.ResultBean.FieldsBean(tp_type, tp_key, tp_name, tp_must,tp_value,tp_holder);
                    fieds.add(fieldsBean);
                }*/
                resultBean.setFields(fieds);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultBean;
    }


}
