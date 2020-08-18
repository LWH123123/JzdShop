package com.jzd.jzshop.ui.mine.mineshop;

import com.google.gson.JsonObject;
import com.jzd.jzshop.entity.BaseEntity;
import com.jzd.jzshop.entity.MineShopEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * @author lwh
 * @description :
 * @date 2020/4/1.
 */
public class ParseDataMineshop {



    public MineShopEntity.ResultBean parseData(String datas){
        MineShopEntity.ResultBean resultBean = new MineShopEntity.ResultBean();
        MineShopEntity.ResultBean.DataBean dataBean = new MineShopEntity.ResultBean.DataBean();
        try {
            JSONObject jsob = new JSONObject(datas);
            JSONObject result = jsob.optJSONObject("result");
            int status = result.optInt("status");
            resultBean.setStatus(status);
            if(status==200) {
                setDataEntity(resultBean, dataBean, result, status);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultBean;
    }

    private void setDataEntity(MineShopEntity.ResultBean resultBean, MineShopEntity.ResultBean.DataBean dataBean, JSONObject result, int status) {
        resultBean.setStatus(status);
        JSONObject data = result.optJSONObject("data");
        String refuse = data.optString("refuse");
        dataBean.setRefuse(refuse);
        JSONArray base = data.optJSONArray("base");
        List<MineShopEntity.ResultBean.DataBean.BaseBean> baseBeans = parseBase(base);
        dataBean.setBase(baseBeans);
        JSONArray personal = data.optJSONArray("personal");
        List<MineShopEntity.ResultBean.DataBean.PersonalBean> personalBeans = parsePersonal(personal);
        dataBean.setPersonal(personalBeans);
        dataBean.setPersonal(personalBeans);
        resultBean.setData(dataBean);
    }


    private List<MineShopEntity.ResultBean.DataBean.BaseBean>  parseBase(JSONArray base){
        ArrayList<MineShopEntity.ResultBean.DataBean.BaseBean> baselist = new ArrayList<>();
        for (int i = 0; i <base.length() ; i++) {
            try {
                JSONObject jsonObject = base.getJSONObject(i);
                initBaseDataParse(baselist,jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return baselist;
    }
    private List<MineShopEntity.ResultBean.DataBean.PersonalBean>  parsePersonal(JSONArray personal){

        ArrayList<MineShopEntity.ResultBean.DataBean.PersonalBean> personallist = new ArrayList<>();
        for (int i = 0; i <personal.length() ; i++) {
            try {
                JSONObject jsonObject = personal.getJSONObject(i);
                initPersonalDataParse(personallist,jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return personallist;
    }




    private void initBaseDataParse(List<MineShopEntity.ResultBean.DataBean.BaseBean> data, JSONObject object){

        MineShopEntity.ResultBean.DataBean.BaseBean baseBean = new MineShopEntity.ResultBean.DataBean.BaseBean();
        int tp_type = object.optInt("tp_type");
        int tp_must = object.optInt("tp_must");
        String tp_key = object.optString("tp_key");
        String tp_name = object.optString("tp_name");
        baseBean.setTp_type(tp_type);
        baseBean.setTp_must(tp_must);
        baseBean.setTp_key(tp_key);
        baseBean.setTp_name(tp_name+":");
        //普通文本
        if(tp_type==0||tp_type==1||tp_type==6||tp_type==7||tp_type==11||tp_type==13){
            String tp_value = object.optString("tp_value");
            String tp_holder = object.optString("tp_holder");
            baseBean.setTp_value(tp_value);
            baseBean.setTp_holder(tp_holder);
        }
        //下拉框
        if(tp_type==2||tp_type==14){
          String tpvalue=  object.optString("tp_value");
          JSONArray tp_text = object.optJSONArray("tp_text");
          ArrayList<String> objects = new ArrayList<>();
            for (int i = 0; i <tp_text.length() ; i++) {
                String s = tp_text.optString(i);
                objects.add(s);
            }
         baseBean.setTp_value(tpvalue);
         baseBean.setTp_textList(objects);
        }
        //多选框
        if(tp_type==3){
            JSONArray tp_value = object.optJSONArray("tp_value");
            ArrayList<String> valueList = new ArrayList<>();
            if(tp_value!=null&&tp_value.length()!=0){
                for (int i = 0; i <tp_value.length() ; i++) {
                    String anInt = tp_value.optString(i);
                    valueList.add(anInt);
                }
            }
            JSONArray tp_array = object.optJSONArray("tp_text");
            ArrayList<String> tp_text = new ArrayList<>();
            for (int j = 0; j < tp_array.length(); j++) {
                String s = tp_array.optString(j);
                tp_text.add(s);
            }
            baseBean.setTp_textList(tp_text);
            baseBean.setTp_valuelist(valueList);
        }
        //多图上传
        if(tp_type==5){
            int tp_max = object.optInt("tp_max");
            JSONArray tp_value = object.optJSONArray("tp_value");
             ArrayList<String> valueList = new ArrayList<>();
            if(tp_value!=null&&tp_value.length()!=0) {
                for (int j = 0; j < tp_value.length(); j++) {
                    String s = tp_value.optString(j);
                    valueList.add(s);
                }
            }
            baseBean.setTp_max(tp_max);
            baseBean.setTp_valuelist(valueList);
        }
        //时间范围
        if(tp_type==8||tp_type==12){
            JSONArray tp_value = object.optJSONArray("tp_value");
            ArrayList<String> tp_string = new ArrayList<>();
            if(tp_value!=null&&tp_value.length()!=0) {
                for (int j = 0; j < tp_value.length(); j++) {
                    String s = tp_value.optString(j);
                    tp_string.add(s);
                }
            }
            baseBean.setTp_valuelist(tp_string);
        }
        //确认文本
        if(tp_type==10){
            String tp_name2 = object.optString("tp_name2");
            JSONArray tp_value = object.optJSONArray("tp_value");
            ArrayList<String> tp_string = new ArrayList<>();
            if(tp_value!=null&&tp_value.length()!=0) {
                for (int j = 0; j < tp_value.length(); j++) {
                    String s = tp_value.optString(j);
                    tp_string.add(s);
                }
            }
            JSONArray holder_array = object.optJSONArray("tp_holder");
            ArrayList<String> tp_holder = new ArrayList<>();
            for (int j = 0; j < holder_array.length(); j++) {
                String o = holder_array.optString(j);
                tp_holder.add(o);
            }
            baseBean.setTp_name2(tp_name2);
            baseBean.setTp_valuelist(tp_string);
            baseBean.setTp_valuelist(tp_holder);
        }
        //地址
        if(tp_type==9){
            int tp_area = object.optInt("tp_area");
            String tp_holder = object.optString("tp_holder");
            JSONObject tp_value = object.optJSONObject("tp_value");
            String province = tp_value.optString("province");
            String city = tp_value.optString("city");
            String area = tp_value.optString("area");
            MineShopEntity.ResultBean.DataBean.TpvalueBean tpvalueBean =
               new MineShopEntity.ResultBean.DataBean.TpvalueBean(province,city,area);
            baseBean.setTp_holder(tp_holder);
            baseBean.setTp_valueaddress(tpvalueBean);
            baseBean.setArea(tp_area);
        }
        data.add(baseBean);
    }



    private void initPersonalDataParse(List<MineShopEntity.ResultBean.DataBean.PersonalBean> data, JSONObject object){

        MineShopEntity.ResultBean.DataBean.PersonalBean baseBean = new MineShopEntity.ResultBean.DataBean.PersonalBean();
        int tp_type = object.optInt("tp_type");
        int tp_must = object.optInt("tp_must");
        String tp_key = object.optString("tp_key");
        String tp_name = object.optString("tp_name");
        baseBean.setTp_type(tp_type);
        baseBean.setTp_must(tp_must);
        baseBean.setTp_key(tp_key);
        baseBean.setTp_name(tp_name+":");
        //普通文本
        if(tp_type==0||tp_type==1||tp_type==6||tp_type==7||tp_type==11||tp_type==13){
            String tp_value = object.optString("tp_value");
            String tp_holder = object.optString("tp_holder");
            baseBean.setTp_value(tp_value);
            baseBean.setTp_holder(tp_holder);
        }
        //下拉框
        if(tp_type==2||tp_type==14){
            String tpvalue=  object.optString("tp_value");
            JSONArray tp_text = object.optJSONArray("tp_text");
            ArrayList<String> objects = new ArrayList<>();
            for (int i = 0; i <tp_text.length() ; i++) {
                String s = tp_text.optString(i);
                objects.add(s);
            }
            baseBean.setTp_value(tpvalue);
            baseBean.setTp_textList(objects);
        }
        //多选框
        if(tp_type==3){
            JSONArray tp_value = object.optJSONArray("tp_value");
            ArrayList<String> valueList = new ArrayList<>();
            if(tp_value!=null&&tp_value.length()!=0){
                for (int i = 0; i <tp_value.length() ; i++) {
                    String anInt = tp_value.optString(i);
                    valueList.add(anInt);
                }
            }
            JSONArray tp_array = object.optJSONArray("tp_text");
            ArrayList<String> tp_text = new ArrayList<>();
            for (int j = 0; j < tp_array.length(); j++) {
                String s = tp_array.optString(j);
                tp_text.add(s);
            }
            baseBean.setTp_textList(tp_text);
            baseBean.setTp_valuelist(valueList);
        }
        //多图上传
        if(tp_type==5){
            int tp_max = object.optInt("tp_max");
            JSONArray tp_value = object.optJSONArray("tp_value");
            ArrayList<String> valueList = new ArrayList<>();
            if(tp_value!=null&&tp_value.length()!=0) {
                for (int j = 0; j < tp_value.length(); j++) {
                    String s = tp_value.optString(j);
                    valueList.add(s);
                }
            }
            baseBean.setTp_max(tp_max);
            baseBean.setTp_valuelist(valueList);
        }
        //时间范围
        if(tp_type==8||tp_type==12){
            JSONArray tp_value = object.optJSONArray("tp_value");
            ArrayList<String> tp_string = new ArrayList<>();
            if(tp_value!=null&&tp_value.length()!=0) {
                for (int j = 0; j < tp_value.length(); j++) {
                    String s = tp_value.optString(j);
                    tp_string.add(s);
                }
            }
            baseBean.setTp_valuelist(tp_string);
        }
        //确认文本
        if(tp_type==10){
            String tp_name2 = object.optString("tp_name2");
            JSONArray tp_value = object.optJSONArray("tp_value");
            ArrayList<String> tp_string = new ArrayList<>();
            if(tp_value!=null&&tp_value.length()!=0) {
                for (int j = 0; j < tp_value.length(); j++) {
                    String s = tp_value.optString(j);
                    tp_string.add(s);
                }
            }
            JSONArray holder_array = object.optJSONArray("tp_holder");
            ArrayList<String> tp_holder = new ArrayList<>();
            for (int j = 0; j < holder_array.length(); j++) {
                String o = holder_array.optString(j);
                tp_holder.add(o);
            }
            baseBean.setTp_name2(tp_name2);
            baseBean.setTp_valuelist(tp_string);
            baseBean.setTp_valuelist(tp_holder);
        }
        //地址
        if(tp_type==9){
            int tp_area = object.optInt("tp_area");
            String tp_holder = object.optString("tp_holder");
            JSONObject tp_value = object.optJSONObject("tp_value");
            String province = tp_value.optString("province");
            String city = tp_value.optString("city");
            String area = tp_value.optString("area");
            MineShopEntity.ResultBean.DataBean.TpvalueBean tpvalueBean =
                    new MineShopEntity.ResultBean.DataBean.TpvalueBean(province,city,area);
            baseBean.setTp_holder(tp_holder);
            baseBean.setTp_valueaddress(tpvalueBean);
            baseBean.setArea(tp_area);
        }
        data.add(baseBean);
    }


}
