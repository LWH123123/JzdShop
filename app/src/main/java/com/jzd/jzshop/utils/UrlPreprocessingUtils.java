package com.jzd.jzshop.utils;

import com.jzd.jzshop.ui.category.CategoryActivity;
import com.jzd.jzshop.ui.home.HomeActivity;
import com.jzd.jzshop.ui.shoppingcar.ShoppingCarActivity;

import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description:  H5 url  预处理
 * @date :2019/12/10 17:02
 */
public class UrlPreprocessingUtils {

    public static int interceptUrl(String url){
        if (url.equals("app:first")) {  //不是协议，按app 指定标识跳
            return 0;
        } else if (url.equals("app:shop.category")) {
            return 1;
        } else if (url.equals("app:member.cart")) {
            return 2;
        }else if (url.equals("app:exchange")) {
            return 3;
        }else if (url.equals("app:abonus")) {
            return 4;
        }else if (url.equals("app:goods.recommand")) {
            return 5;
        }else if (url.equals("app:goods.new")) {
            return 6;
        }else if (url.equals("app:goods.hot")) {
            return 7;
        }else if (url.equals("app:goods.discount")) {
            return 8;
        }else if (url.equals("app:goods.sendfree")) {
            return 9;
        }else if (url.equals("app:goods.time")) {
            return 10;
        }else if (url.equals("app:member")) {
            return 11;
        }else if (url.equals("app:order")) {
            return 12;
        }else if (url.equals("app:order.status0")) {
            return 13;
        }else if (url.equals("app:order.status1")) {
            return 14;
        }else if (url.equals("app:order.status2")) {
            return 15;
        }else if (url.equals("app:order.status3")) {
            return 16;
        }else if (url.equals("app:order.status4")) {
            return 17;
        }else if (url.equals("app:member.favorite")) {
            return 18;
        }else if (url.equals("app:member.history")) {
            return 20;
        }else if (url.equals("app:member.recharge")) {
            return 21;
        }else if (url.equals("app:member.log")) {
            return 22;
        }else if (url.equals("app:member.withdraw")) {
            return 23;
        }else if (url.equals("app:member.info")) {
            return 24;
        }else if (url.equals("app:member.rank")) {
            return 25;
        }else if (url.equals("app:member.rank.order_rank")) {
            return 26;
        }else if (url.equals("app:member.fullback")) {
            return 27;
        }else if (url.equals("app:verifygoods")) {
            return 28;
        }else if (url.equals("app:cycelbuy.order.list")) {
            return 29;
        }else if (url.equals("app:member.address")) {
            return 30;
        }else if (url.equals("app:membercard.index")) {
            return 31;
        }else if (url.equals("app:commission")) {
            return 32;
        }else if (url.equals("app:commission.register")) {
            return 33;
        }else if (url.equals("app:commission.myshop")) {
            return 34;
        }else if (url.equals("app:commission.withdraw")) {
            return 35;
        }else if (url.equals("app:commission.order")) {
            return 36;
        }else if (url.equals("app:commission.down")) {
            return 37;
        }else if (url.equals("app:commission.log")) {
            return 38;
        }else if (url.equals("app:commission.qrcode")) { ////邀请好友
            return 39;
        }else if (url.equals("app:commission.myshop.set")) {
            return 40;
        }else if (url.equals("app:commission.rank")) {
            return 41;
        }else if (url.equals("app:commission.myshop.select")) {
            return 42;
        }else if (url.equals("app:article.list")) {
            return 43;
        }else if (url.equals("app:sale.coupon")) {
            return 44;
        }else if (url.equals("app:sale.coupon.my")) {
            return 45;
        }else if (url.equals("app:creditshop")) {
            return 46;
        }else if (url.equals("app:creditshop.lists")) {
            return 47;
        }else if (url.equals("app:creditshop.log")) {
            return 48;
        }else if (url.equals("app:creditshop.creditlog")) {
            return 49;
        }else if (url.equals("app:dividend")) {
            return 50;
        }else if (url.equals("app:shop.notice")) {
            return 51;
        }else if (url.equals("app:goods")) {
            return 52;
        }else if (url.equals("app:merch")){ //商家联盟
            return 53;
        }else if (url.equals("app:merch.regist")){ //商家入驻
            return 54;
        }else if (url.equals("app:locallife")){ //本地生活
            return 55;
        }else if(url.equals("app:sign")){//签到
            return 56;
        }else if(url.contains("app:business")){//商家店铺
            return 57;
        }else if (url.contains("app:goods.detail")){ //商品详情
            return 58;
        }else if (url.contains("app:creditshop.detail")){//积分商城 商品详情
            return 59;
        }
        return 100;

    }
}
