package com.jzd.jzshop.entity.json_typeadapter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jzd.jzshop.entity.FirmOrderEntity;

import java.lang.reflect.Type;
import java.util.List;

public class FirmOrderEntityAdapter implements JsonDeserializer<FirmOrderEntity> {

    @Override
    public FirmOrderEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        FirmOrderEntity entity = new FirmOrderEntity();
        FirmOrderEntity.ResultBean bean = new FirmOrderEntity.ResultBean();
        entity.setResult(bean);
        JsonObject result = json.getAsJsonObject().getAsJsonObject("result");
        if (result.has("data")) {
            JsonArray jads = result.getAsJsonArray("data");
            Type type = new TypeToken<List<FirmOrderEntity.ResultBean.DataBean>>() {
            }.getType();
            List<FirmOrderEntity.ResultBean.DataBean> dataBean = new Gson().fromJson(jads.toString(), type);
            bean.setData(dataBean);
        }
        if (result.has("address")) {
            if (result.get("address").isJsonObject()) {
                FirmOrderEntity.ResultBean.AddressBean addressBean = new Gson().fromJson(result.get("address").toString(), FirmOrderEntity.ResultBean.AddressBean.class);
                bean.setAddress(addressBean);
            }
        }
        if(result.has("dispatch")){
            String dispatch = result.get("dispatch").getAsString();
            bean.setDispatch(dispatch);
        }
        if(result.has("coupon_data")){
            JsonArray coupon_data = result.getAsJsonArray("coupon_data");
          Type type=  new TypeToken<List<FirmOrderEntity.ResultBean.CouponDataBean>>(){
          }.getType();
            List<FirmOrderEntity.ResultBean.CouponDataBean> o = new Gson().fromJson(coupon_data.toString(), type);
            bean.setCoupon_data(o);
        }
        if(result.has("discount_shop")){
            if(result.get("discount_shop").isJsonObject()){
                FirmOrderEntity.ResultBean.DiscountShopBean discount_shop = new Gson().fromJson(result.get("discount_shop").toString(), FirmOrderEntity.ResultBean.DiscountShopBean.class);
                bean.setDiscount_shop(discount_shop);
            }
        }
        return entity;
    }
}
