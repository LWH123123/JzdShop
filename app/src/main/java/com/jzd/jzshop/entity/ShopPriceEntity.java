package com.jzd.jzshop.entity;

/**
 * @author LWH
 * @description:
 * @date :2019/12/30 15:27
 * @date :2019/12/30 14:57
 */
public class ShopPriceEntity {
    private String optionid;
    private String total;
    private String price;


    public ShopPriceEntity() {
    }

    public String getOptionid() {
        return optionid;
    }

    public void setOptionid(String optionid) {
        this.optionid = optionid;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ShopPriceEntity{" +
                "optionid='" + optionid + '\'' +
                ", total='" + total + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
