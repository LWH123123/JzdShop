package com.jzd.jzshop.entity.json_typeadapter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jzd.jzshop.entity.HomeEntity;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/3/20 11:46
 */
public class HomeEntityTypeAdapter implements JsonDeserializer<HomeEntity> {
    @Override
    public HomeEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        HomeEntity entity = new HomeEntity();
        List<HomeEntity.ResultBean> resultBean = new HomeEntity().getResult();
        entity.setResult(resultBean);
        JsonObject result = json.getAsJsonObject().getAsJsonObject("result");

        if (result.has("data")) {
            if (result.get("data").isJsonObject()) {
                 HomeEntity.ResultBeanObject resultBeanObject = new Gson().fromJson(result.get("data").toString(),
                        HomeEntity.ResultBeanObject.class);
                entity.setResultBeanObject(resultBeanObject);
            } else if (result.get("data").isJsonArray()) {
                JsonArray jads = result.getAsJsonArray("data");
                Type type = new TypeToken<List<HomeEntity.ResultBean>>() {
                }.getType();
                List<HomeEntity.ResultBean> dataBean = new Gson().fromJson(jads.toString(), type);
                entity.setResult(dataBean);
            } else {
                entity.setResult(null);
            }
        }
        return entity;
    }
}
