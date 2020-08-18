package com.jzd.jzshop.data.local;

import com.jzd.jzshop.entity.UserInfo;
import com.slodonapp.ywj_release.wxapi.WXUserInfo;

/**
 * Created by jzd on 2019/3/26.
 */
public interface LocalDataSource {
    /**
     * 保存用户名
     */
    void saveUserName(String userName);

    /**
     * 保存用户密码
     */

    void savePassword(String password);

    /**
     * 获取用户名
     */
    String getUserName();

    /**
     * 获取用户密码
     */
    String getPassword();

    /**
     * 获取用户token
     * @return
     */
    String getUserToken();
    /**
     * 保存用户token
     * @return
     */
    void saveUserToken(String token);

    WXUserInfo getWXUserInfo();

    void saveWXUserInfo(String info);


    void saveShopCarnumber(int number);
    int getShopCarnumber();


    //商品id
    void saveShopID(String shopid);
    String getShopID();

    //规格组id
    void saveOptionID(String optionid);
    String getOptionID();

    //购买数量
    void saveGoodsNumber(String number);
    String getGoodsNumber();

    //赠品ID
    void saveGiftID(String giftid);
    String getGIftID();
    //选择的地址
    void saveAddress(String add_id);
    String getAddress();

    //跳转打开标识
    void saveOpenFlag(String openFlag);
    String getOpenFlag();

    //保存用户信息
    void saveUserInfo(UserInfo info);
    UserInfo getUserInfo();

    void saveLocationCity(String city);
    String getLoacationCity();


    void saveUserMessage(int messgae);

    int getUserMessage();

    //保存会员金额查看状态
    void saveMemberlook(int status);

    int getMemberLook();


}
