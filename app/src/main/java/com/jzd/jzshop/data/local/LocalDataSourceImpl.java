package com.jzd.jzshop.data.local;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.jzd.jzshop.entity.UserInfo;
import com.jzd.jzshop.utils.Constants;
import com.slodonapp.ywj_release.wxapi.WXUserInfo;
import com.slodonapp.ywj_release.wxapi.WechatInfoSpHelper;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.StringUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 本地数据源，可配合Room框架使用
 * Created by jzd on 2019/3/26.
 */
public class LocalDataSourceImpl implements LocalDataSource {
    private volatile static LocalDataSourceImpl INSTANCE = null;

    public static LocalDataSourceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSourceImpl();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private LocalDataSourceImpl() {
        //数据库Helper构建
    }

    @Override
    public void saveUserName(String userName) {
        SPUtils.getInstance().put("UserName", userName);
    }

    @Override
    public void savePassword(String password) {
        SPUtils.getInstance().put("password", password);
    }

    @Override
    public String getUserName() {
        return SPUtils.getInstance().getString("UserName");
    }

    @Override
    public String getPassword() {
        return SPUtils.getInstance().getString("password");
    }

    public String getUserToken() {
        String token = SPUtils.getInstance().getString(Constants.SPKEY_USER_TOKEN);
        /*if (TextUtils.isEmpty(token)) {
            ToastUtils.showShort("用户需要重新登录");
        }*/
        //测试
//        return "dV9H2lmb5QTGd1uhQ9U";
        return token;
    }

    @Override
    public void saveUserToken(String token) {
        SPUtils.getInstance().put(Constants.SPKEY_USER_TOKEN, token);
    }

    String test = "{\"openid\":\"oj9N2wPfc2c2LGCdvrPtsUA8HOuc\",\"nickname\":\"哄哄\",\"sex\":0,\"language\":\"\",\"city\":\"\",\"province\":\"\",\"country\":\"\",\"headimgurl\":\"http://tx.haiqq.com/uploads/allimg/170830/0220115496-9.jpg\",\"privilege\":[],\"unionid\":\"oBjdj0UUM5L76eqj_QLzeQxMBNz4\"}";

    @Override
    public WXUserInfo getWXUserInfo() {
        String response = SPUtils.getInstance(WechatInfoSpHelper.WECHAT_SP_NAME).getString(WechatInfoSpHelper.WECHAT_SP_USER_KEY);
//            response=test;
        if (!StringUtils.isEmpty(response)) {
            return new Gson().fromJson(response, WXUserInfo.class);
        }
        return null;
    }

    @Override
    public void saveWXUserInfo(String info) {
        SPUtils.getInstance(WechatInfoSpHelper.WECHAT_SP_NAME).put(WechatInfoSpHelper.WECHAT_SP_USER_KEY, info);
    }

    @Override
    public void saveShopCarnumber(int number) {
        SPUtils.getInstance().put(Constants.MINE_GOOD,number);
    }

    @Override
    public int getShopCarnumber() {
        int number = SPUtils.getInstance().getInt(Constants.MINE_GOOD,0);
        return number;
    }

    @Override
    public void saveShopID(String shopid) {
        SPUtils.getInstance().put(Constants.SHOP_ID,shopid);
    }

    @Override
    public String getShopID() {
        String shopid = SPUtils.getInstance().getString(Constants.SHOP_ID);
        return shopid;
    }

    @Override
    public void saveOptionID(String optionid) {
        SPUtils.getInstance().put(Constants.OPTION_ID,optionid);

    }

    @Override
    public String getOptionID() {
        String optionid = SPUtils.getInstance().getString(Constants.OPTION_ID);
        return optionid;
    }

    @Override
    public void saveGoodsNumber(String number) {
        SPUtils.getInstance().put(Constants.GOODS_NUMBER,number);
    }

    @Override
    public String getGoodsNumber() {
        String number = SPUtils.getInstance().getString(Constants.GOODS_NUMBER);
        return number;
    }

    @Override
    public void saveGiftID(String giftid) {
        SPUtils.getInstance().put(Constants.GIFT_ID,giftid);
    }

    @Override
    public String getGIftID() {
        String giftid = SPUtils.getInstance().getString(Constants.GIFT_ID);
        return giftid;
    }

    @Override
    public void saveAddress(String add_id) {
        SPUtils.getInstance().put(Constants.ADDRESS_ID,add_id);
    }

    @Override
    public String getAddress() {
        String addid = SPUtils.getInstance().getString(Constants.ADDRESS_ID);
        return addid;
    }

    @Override
    public void saveUserInfo(UserInfo info) {
        SPUtils.getInstance().putSerializableEntity(Constants.USER_INFO,info);
    }

    @Override
    public UserInfo getUserInfo() {
        UserInfo userInfo = (UserInfo) SPUtils.getInstance().getSerializableEntity(Constants.USER_INFO);
        return userInfo;
    }

    @Override
    public void saveLocationCity(String city) {
        SPUtils.getInstance().put(Constants.SP.LOCATION_CITY,city);
    }

    @Override
    public String getLoacationCity() {
        String city = SPUtils.getInstance().getString(Constants.SP.LOCATION_CITY);
        return city;
    }

    @Override
    public void saveUserMessage(int messgae) {
        SPUtils.getInstance().put(Constants.USER_MESSAGE,messgae);
    }

    @Override
    public int getUserMessage() {
        int anInt = SPUtils.getInstance().getInt(Constants.USER_MESSAGE);
        return anInt;
    }

    @Override
    public void saveMemberlook(int status) {
        SPUtils.getInstance().put(Constants.USER_MONEY_LOOK,status);
    }

    @Override
    public int getMemberLook() {
        int anInt = SPUtils.getInstance().getInt(Constants.USER_MONEY_LOOK);
        return anInt;
    }

    @Override
    public void saveOpenFlag(String openFlag) {
        SPUtils.getInstance().put(Constants.SP.ACTIVITY_OPEN_FLAG,openFlag);
    }


    @Override
    public String getOpenFlag() {
        String openFlag = SPUtils.getInstance().getString(Constants.SP.ACTIVITY_OPEN_FLAG);
        return openFlag;
    }
}
