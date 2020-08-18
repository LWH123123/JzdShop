package com.jzd.jzshop.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jzd.jzshop.entity.AreaAddressEntity;
import com.jzd.jzshop.entity.SecondAddressEntity;

import java.util.ArrayList;
import java.util.List;

public class AddressSelectUtils {



    public static ArrayList<String> provincedata = new ArrayList<>();
    public static ArrayList<ArrayList<String>> citydata = new ArrayList<>();
    //获取二级地址的数据
    private void secondDataAddress(Context context){
        String json = new GetJsonDataUtil().getJson(context, "address.json");
        String replace = json.replace(" ", "");
        SecondAddressEntity second = new Gson().fromJson(replace, SecondAddressEntity.class);
        List<SecondAddressEntity.ProvincesBean> provinces = second.getProvinces();
        for (SecondAddressEntity.ProvincesBean province : provinces) {
            provincedata.add(province.getName());
            ArrayList<String> city = new ArrayList<>();
            List<SecondAddressEntity.ProvincesBean.CitiesBean> cities = province.getCities();
            for (SecondAddressEntity.ProvincesBean.CitiesBean citiesBean : cities) {
                city.add(citiesBean.getName());
            }
            citydata.add(city);
        }
    }


    public static ArrayList<String> options1province = new ArrayList<>();// 省集合
    public static ArrayList<ArrayList<String>> options2city = new ArrayList<>();// 市集合
    public static ArrayList<ArrayList<ArrayList<String>>> options3area = new ArrayList<>();// 区集合

    //获取三级地址的数据
    private void thirdDataAddress(Context context) {

        String json = new GetJsonDataUtil().getJson(context, "mineaddress.json");
        String replace = json.replace(" ", "");
        List<AreaAddressEntity> area = new Gson().fromJson(replace, new TypeToken<List<AreaAddressEntity>>() {
        }.getType());
        for (int i = 0; i < area.size(); i++) {
            options1province.add(area.get(i).getText());
            //存放城市
            ArrayList<String> cityList = new ArrayList<>();
            //存放区
            ArrayList<ArrayList<String>> areaList = new ArrayList<>();
            for (int j = 0; j < area.get(i).getChildren().size(); j++) {
                String text = area.get(i).getChildren().get(j).getText();
                cityList.add(text);
                //给城市的地区列表
                ArrayList<String> city_address = new ArrayList<>();

                if (area.get(i).getChildren().get(j).getChildren() == null || area.get(i).getChildren().get(j).getChildren().size() == 0) {
                    city_address.add("");
                } else {
                    city_address.addAll(area.get(i).getChildren().get(j).getChildren());
                }
                areaList.add(city_address);
            }
            //城市的数据
            options2city.add(cityList);
            //区的数据
            options3area.add(areaList);
        }
    }


    //三级地址
    public void thirdAddress(Context context,OnOptionsSelectListener onOptionsSelectListener){
        thirdDataAddress(context);
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context,onOptionsSelectListener).setContentTextSize(20)
                .isCenterLabel(false)
                .setCancelColor(Color.GRAY)
                .setSubmitColor(Color.RED)
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .build();
        pvOptions.setPicker(options1province, options2city, options3area);
        pvOptions.show();



    }



    //二级地址
    public void secondAddress(Context context,OnOptionsSelectListener onOptionsSelectListener){
        secondDataAddress(context);
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context,onOptionsSelectListener).setContentTextSize(20)
                .isCenterLabel(false)
                .setCancelColor(Color.GRAY)
                .setSubmitColor(Color.RED)
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .build();
        pvOptions.setPicker(provincedata,citydata);
        pvOptions.show();
    }


}
