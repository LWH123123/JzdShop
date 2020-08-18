package com.jzd.jzshop.entity;

/**
 * @author LXB
 * @description:
 * @date :2019/12/21 15:11
 */
public class GoodsEvaluationTypeEntity {
    private String type;

    public GoodsEvaluationTypeEntity(String goodsType) {
        this.type = goodsType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
