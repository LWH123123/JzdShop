package com.jzd.jzshop.utils;

public class Constants {

    private Constants() {
    }

    public static final String SP_DEVICES_ID = "devices_id";
    public static final String BUGLY_APP_ID = "f161427326";
    public static final String UMENG_APPKEY = "5f17d978c3bf3749c2b4d6d7";
    public static final String SHARE_LOGO_URL = "/addons/api_shopv1/static/images/app_shoare_logo.png";
    public static final String WEBVIEW_USER_AGENT_NAME = "Name/Jzd";

    //-----------------------H5统一url链接------------------start---------------
    /* 我的 我的订单skip h5 */
    public static final String MY_ORDER_JUMP_URL = "app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.url&returnUrl=";
    public static final String MY_ORDER = "order"; //全部
    public static final String MY_ORDER_STATUS_0 = "order&status=0"; //待付款
    public static final String MY_ORDER_STATUS_1 = "order&status=1"; //待发货
    public static final String MY_ORDER_STATUS_2 = "order&status=2"; //待收货
    public static final String MY_ORDER_STATUS_4 = "order&status=4"; //退换货
    public static final String MY_ORDER_STATUS_3 = "order&status=3"; //已完成
    public static final String MY_ORDER_DETAILS = "order.detail&id="; //订单详情

    public static final String MY_RECHARGE = "member.recharge"; //充值
    public static final String MY_PAGE_SIGN_RECORD = "sign.records"; //签到记录
    public static final String MY_PAGE_COMMISSION = "commission"; //推广中心
    public static final String MY_PAGE_SIGN = "sign"; //积分签到
    public static final String MY_PAGE_SALE_COUPON_MY = "sale.coupon.my"; //我的优惠券
    public static final String MY_PAGE_MEMBER_FAVORITE = "member.favorite"; //我的收藏
    public static final String MY_PAGE_SALE_COUPON = "sale.coupon"; //领券中心
    public static final String CREDIT_SHOP = "creditshop"; //积分商城
    public static final String MINE_SHOP_SELECT = "myshop.select"; //我的小店自选商品

    //-----------------------H5统一url链接------------------end---------------
    /*订单详情*/
    public static final String ORDER_DETAIL_CALC_ID_DISPATCH = "dispatch";
    public static final String ORDER_DETAIL_CALC_ID_DISPATCH_CITY = "dispatch_city";
    public static final String ORDER_DETAIL_CALC_ID_LOTTERY = "lottery";
    public static final String ORDER_DETAIL_CALC_ID_DEDUCTENOUGH = "deductenough";
    public static final String ORDER_DETAIL_CALC_ID_COUPON = "coupon";
    public static final String ORDER_DETAIL_CALC_ID_CARD_DEC = "card_dec";
    public static final String ORDER_DETAIL_CALC_ID_BUYAGAIN = "buyagain";
    public static final String ORDER_DETAIL_CALC_ID_DISCOUNT = "discount";
    public static final String ORDER_DETAIL_CALC_ID_ISDISCOUNT = "isdiscount";
    public static final String ORDER_DETAIL_CALC_ID_DEDUCT = "deduct";
    public static final String ORDER_DETAIL_CALC_ID_DEDUCT_MONEY = "deduct_money";
    public static final String ORDER_DETAIL_CALC_ID_SECKILL = "seckill";


    /*APP界面标识 skip*/
    public interface URL {
        //首页
        int APP_UI_SKIP_FLAG_FIRST = 0;
        int APP_UI_SKIP_FLAG_CATEGORY = 1;
        int APP_UI_SKIP_FLAG_CART = 2;
        int APP_UI_SKIP_FLAG_EXCHANGE = 3;
        int APP_UI_SKIP_FLAG_ABONUS = 4;
        int APP_UI_SKIP_FLAG_GOODS = 52;
        int APP_UI_SKIP_FLAG_NOTICE = 51;
        int APP_UI_SKIP_FLAG_DIVIDEND = 50;
        //商品属性
        int APP_UI_SKIP_FLAG_RECOMMAND = 5;
        int APP_UI_SKIP_FLAG_NEW = 6;
        int APP_UI_SKIP_FLAG_HOT = 7;
        int APP_UI_SKIP_FLAG_DISCOUNT = 8;
        int APP_UI_SKIP_FLAG_SENDFREE = 9;
        int APP_UI_SKIP_FLAG_TIME = 10;
        //会员中心
        int APP_UI_SKIP_FLAG_MEMBER = 11;
        int APP_UI_SKIP_FLAG_ORDER = 12;
        int APP_UI_SKIP_FLAG_STATUS0 = 13;
        int APP_UI_SKIP_FLAG_STATUS1 = 14;
        int APP_UI_SKIP_FLAG_STATUS2 = 15;
        int APP_UI_SKIP_FLAG_STATUS3 = 16;
        int APP_UI_SKIP_FLAG_STATUS4 = 17;

        int APP_UI_SKIP_FLAG_FAVORITE = 18;
        int APP_UI_SKIP_FLAG_HISTORY = 20;
        int APP_UI_SKIP_FLAG_RECHARGE = 21;
        int APP_UI_SKIP_FLAG_LOG = 22;
        int APP_UI_SKIP_FLAG_WITHDRAW = 23;
        int APP_UI_SKIP_FLAG_INFO = 24;
        int APP_UI_SKIP_FLAG_RANK = 25;
        int APP_UI_SKIP_FLAG_ORDER_RANK = 26;
        int APP_UI_SKIP_FLAG_FULLBACK = 27;
        int APP_UI_SKIP_FLAG_VERIFYGOODS = 28;
        int APP_UI_SKIP_FLAG_ORDER_LIST = 29;
        int APP_UI_SKIP_FLAG_MEMBER_ADDRESS = 30;
        int APP_UI_SKIP_FLAG_INDEX = 31;
        //超级分销
        int APP_UI_SKIP_FLAG_COMMISSION = 32;
        int APP_UI_SKIP_FLAG_REGISTER = 33;
        int APP_UI_SKIP_FLAG_MYSHOP = 34;
        int APP_UI_SKIP_FLAG_commission_WITHDRAW = 35;
        int APP_UI_SKIP_FLAG_COMMISSION_ORDER = 36;
        int APP_UI_SKIP_FLAG_COMMISSION_DOWN = 37;
        int APP_UI_SKIP_FLAG_COMMISSION_LOG = 38;
        int APP_UI_SKIP_FLAG_COMMISSION_QRCODE = 39; //邀请好友
        int APP_UI_SKIP_FLAG_COMMISSION_MYSHOP_SET = 40;
        int APP_UI_SKIP_FLAG_COMMISSION_RANK = 41;
        int APP_UI_SKIP_FLAG_COMMISSION_MYSHOP_SELECT = 42;
        //文章营销
        int APP_UI_SKIP_FLAG_CARTICLE_LIST = 43;
        //超级券
        int APP_UI_SKIP_FLAG_SALE_COUPON = 44;
        int APP_UI_SKIP_FLAG_COUPON_MY = 45;
        //积分商城
        int APP_UI_SKIP_FLAG_CREDITSHOP = 46;
        int APP_UI_SKIP_FLAG_CREDITSHOP_LISTS = 47;
        int APP_UI_SKIP_FLAG_CREDITSHOP_LOG = 48;
        int APP_UI_SKIP_FLAG_CREDITLOG = 49;
        int APP_UI_SKIP_FLAG_APP_MERCH = 53;
        //商家入驻
        int APP_UI_SKIP_FLAG_APP_MERCH_REGIST = 54;
        /*本地生活*/
        int APP_UI_SKIP_FLAG_APP_LOCAL_LIFE = 55;
        /*签到*/
        int APP_UI_SKIP_FLAG_APP_SIGN = 56;
        /*商家店铺*/
        int APP_UI_SKIP_FLAG_APP_MYSHOP = 57;
        /*商品详情*/
        int APP_UI_SKIP_FLAG_APP_GOODS_DETAIL = 58;
        /*积分商城 商品详情*/
        int APP_UI_SKIP_FLAG_APP_CREDITSHOP_DETAIL = 59;


        /*关于App 阅读协议*/

        //特别申明
        String specialstatement = "addons/ewei_shopv2/html/app/about/shengming.html";
        //隐私政策
        String privacypolicy="addons/ewei_shopv2/html/app/about/yingsi.html";
        //软件许可服务协议
        String service = "addons/ewei_shopv2/html/app/about/xieyi.html";


        //-------------------------------------文本 图片混合 接口提交---------------start-------------------
        //修改头像
        String updateAvatar = "app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.info&mod=avatar";
        //完善资料
        String submitUserInfo = "app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.info&mod=submit";
        //商家入驻提交数据
        String submitStoreInfo="app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=shop.merch&mod=submit";
        /*评价提交*/
        String submit_comment="app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.comment.submit";
        /*申请退款、售后（提交）*/
        String refund_submit="app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.refund.submit";
        /* 意见反馈 提交*/
        String feedback_submit="app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=set.feedback";
        /*我的小店 提交*/
        String mineShop_submit="app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.myshop.regist";
        /*我的小店设置  提交  */
        String mineshopset_submit="app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.myshop.set";
        //-----------------建立 websocket 连接-----------start---------------
        String chat_history = "app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=notice.chat.window";
        //上传单图
        String updatePic = "app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=wxapp.upload";
        /*商品规格*/
        String goodsSpce = "app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=goods&mod=picker";
        /*把商品加入购物车*/
        String goodsAddShopcar = "app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=cart&mod=joins";
        //-----------------建立   websocket  连接----------------end------------
        /* 积分商城 商品详情 评价提交*/
        String submit_comment_credit="app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop.comment.submit";

        //-------------------------------------文本 图片混合 接口提交---------------end-------------------

    }

    // ================================================== bundle 传参  key  start ==================================================
    /**
     * 用户token
     */
    public static final String SPKEY_USER_TOKEN = "USER_TOKEN";
    /**
     * 通过属性按钮打开
     */
    public static final String GOODS_KEY_SPEC = "spec";
    /**
     * 通过立即购买打开
     */
    public static final String GOODS_KEY_BUYNOW = "buynow";
    /**
     * 通过商品参数打开
     */
    public static final String GOODS_KEY_PARAM = "param";
    /**
     * 通过购物车打开
     */
    public static final String GOODS_KEY_SHOPPING_CAR = "shappingcar";
    /**
     * 通过商品优惠券打开
     */
    public static final String GOODS_KEY_COUPON = "coupon";


    /*
    * 绑定手机号回调
    * */
    public static final String BINDMOBILE = "bindmobile";


    /**
     * 商品gid
     */
    public static final String GOODS_KEY_GID = "gid";
    public static final String GOODS_KEY_CHAT_NAME = "name";
    public static final String GOODS_KEY_CHAT_ID = "chat_id";
    public static final String GOODS_KEY_CHAT_LTYPE = "ltype";
    public static final String GOODS_KEY_USER_TOKEN = "usertoken";
    public static final String GOODS_KEY_GOODS_PRICE = "price";
    public static final String GOODS_KEY_GOODS_INFO = "shareinfo";
    public static final String GOODS_KEY_TYPE = "type";// 商品评价 类型
    public static final String GOODS_OPEN_FLAG = "openflag";//打开 类型
    public static final String ORDER_ID = "order_id";//打开 类型
    public static final String IS_EVALUATION = "is_evaluation";//打开 类型
    public static final String ORDER_PRICE="order_price";
    public static final String REFUND_ID="refund_id";
    public static final String SEE_REFUND_EXPRESS="see_refund_express";
    public static final String BUNDLE_KEY_JPUSH_MESSAGE_TYPE="jpush_message_type";


    //从小店进入商品详情
    public static final String SHOP_GOODS="shopTogoods";
    //从商户店铺进入商品详情
    public static final String GOODLIST_GOODS="goodlist_goods";

    //积分商城入口 传入的数据
    public static final String CREDIT_GOODS_DETAIL="credit_goods_details";

    /**
     * 规格id
     */
    public static final String GOODS_KEY_OPTIONID = "optionid";
    /**
     * 数量
     */
    public static final String GOODS_KEY_TOTAL = "total";
    /**
     * 赠品id
     */
    public static final String GOODS_KEY_GIFTID = "gift_id";
    /*购物车优惠明细*/
    public static final String SHOPCAR_KEY_COUPON_DETAIL = "gift_id";
    /*购物车领券*/
    public static final String SHOPCAR_KEY_COUPON = "gift_id";

    public static final String BUNDLE_KEY_CATEGORY_ID = "cate";
    public static final String BUNDLE_KEY_KEYWORDS = "keywords";
    public static final String BUNDLE_KEY_ORDERID = "order_id";
    public static final String BUNDLE_KEY_MONEY = "money";
    public static final String BUNDLE_KEY_PAY_TYPE = "pay_type";
    public static final String BUNDLE_KEY_ALI_RESULT = "ali_result";
    public static final String BUNDLE_KEY_URL = "URL";
    public static final String BUNDLE_KEY_TITLE = "title";
    public static final String BUNDLE_KEY_MERCH_ID = "merch_id";
    public static final String PAY_TYPE_ALIPAY = "alipay";
    public static final String PAY_TYPE_WX = "wechat";
    public static final String PAY_TYPE_CREDIT = "credit";
    public static final String PAY_TYPE_OTHER = "other";
    public static final String PAY_TYPE_CASH = "cash";
    public static final String BUNDLE_KEY_EXPRESS_INFO = "express_info";
    public static final String BUNDLE_KEY_EXPRESS_DATA = "express_data";
    public static final String BUNDLE_KEY_LOG_ID = "log_id";
    public static final String BUNDLE_KEY_LOCATION_CITY = "current_city";
    public static final String BUNDLE_KEY_CATE_NAME = "cate_name";
    public static final String BUNDLE_KEY_CATE_ID = "cate_id";
    public static final String BUNDLE_KEY_CREDITS_NUM = "credits_num";
    //积分商城
    public static final String GOODS_KEY_CREDIT_GOODS_TYPE = "goods_type";
    public static final String GOODS_KEY_CREDIT_ADDR_ID = "addr_id";
    public static final String GOODS_KEY_CREDIT_GOODS_NUMBER = "goods_number";
    public static final String GOODS_KEY_CREDIT_ISLOTTERY = "goods_islottery";
    public static final String GOODS_KEY_CREDIT_ISPAY = "goods_ispay";
    //积分商城 支付 抽奖 成功页面
    public static final String GOODS_KEY_CREDIT_PAY_RESULT = "credit_pay_result";
    public static final String GOODS_KEY_CREDIT_PAY_LOGID = "credit_pay_logid";




    // ================================================== bundle 传参  key  end ==================================================


    //全民社区
    public static final String HOME_QUANMING = "allpeople";

    /*
     * 我的页面
     * */

    //推广中心
    public static final String MINE_TUI_GUANG = "tuiguang";
    //奖金提现
    public static final String MINE_BONUS = "bonus";
    //余额提现
    public static final String MINE_YUE = "yue";
    //我的优惠券
    public static final String MINE_COUPON = "mycoupon";
    //我的收藏
    public static final String MINE_COLLECT = "collect";
    //领券中心
    public static final String MINE_COUPON_CENTER = "coupon_center";
    //商家入驻
    public static final String MINE_STORE = "store";
    //商户店铺ID
    public static final String MINE_SHOP = "shopid";
    //我的小店ID
    public static final String MINE_SMALL_STORE="mine_small_store";
    //商城
    public static final String MINE_MALL="mine_mall";
    //标志从我的小店进去奖励订单/推广订单
    public static final String MINE_PROMOT="mine_promot_shop";

    /**
     * 商品列表
     */
    //价格排序    //升序：asc； 降序：desc
    public static final String SORT_ASC = "asc";
    public static final String SORT_DESC = "desc";

      // ================================================== SP  key  start ==================================================
    //购物车数量
    public static final String MINE_GOOD = "good_number";
    //进入确认订单页面的标记
    public static final String TYPE = "jzsp";
    //选择地址
    public static final String ADDRESS_SELECR = "select_address";
    //商品id
    public static final String SHOP_ID = "shop_id";
    //规格组id
    public static final String OPTION_ID = "option_id";
    //购买数量
    public static final String GOODS_NUMBER = "good_numbers";
    //保存消息总数
    public static final String USER_MESSAGE="user_message";
    //赠品id
    public static final String GIFT_ID = "gift_id";
    //地址
    public static final String ADDRESS_ID = "select_address";
    //用户信息
    public static final String USER_INFO="user_info";
    //用户金额查看状态
    public static final String USER_MONEY_LOOK="user_money_look";

    public interface SP{
        String ACTIVITY_OPEN_FLAG = "openFlag";     //UI打开标识
        String LOCATION_CITY = "current_city";

        String CACHE_HOME_BANNER_DATA = "home_banner_data";
        String CACHE_HOME_MENU_DATA = "home_menu_data";
        String CACHE_HOME_SHOP_DATA = "home_shop_data";
        String CACHE_HOME_JUNZHIQ_DATA = "home_junzhiq_data";
        String CACHE_HOME_SHOPSPECIAL_DATA = "home_shopspecial_data";
        String CACHE_HOME_NOTICE_DATA = "home_notice_data";


        String CACHE_CATEGORY_DATA="category_data";
        String SHOPCAR_NUMBER="shopcar_number";

        String GOODS_INFO="goods_info";


    }
    // ================================================== SP  key     end ==================================================

    //标记从详情进入购物车
    public static final String GOODS_CAR="goodstocar";

    //标记从购物车进入登录页面
    public static final String CAR_LOGIN="car_login";

    //商家入驻第一步到第二步
    public static final String MerchFirst="merch";
    public static final String MerchFirstList="merchfirestList";

    //商家入驻第二步到第三步
    public static final String MerchSeconed="merchseconed";
    public static final String MerchSeconedList="MerchSeconedList";


    //我的页面跳转我的订单
    public static final String MyOrder="mineorder";
    public static final String ORDER_LOGISTICS="order_logistics";
    public static final String ORDER_SEND_TYPE="order_send_type";
    public static final String ORDER_BUNDLE="order_bundle";


    //我的小店设置
    public static final String MINE_SET="mine_shop_set";


    /*上传凭证*/
    public static final int UPLOAD_DOCUMENTS=10101;


    // ----------------activity  requestcode-------------------------
    public interface QUEST_CODE{
        int REQUESTCODE_SALES_APPLY_PROGRESS = 100;
        int REQUESTCODE_MESSAGE_CENTER = 101;
    }

    public static final String MINE_BALANCE="MINE_BALANCE";
    public static final String MINE_RECHARGE="MINE_RECHARGE";
    /*充值*/
    public static final String RECHARGE_TIME="Recharge_time";
    public static final String RECHARGE_MONEY="Recharge_money";
    public static final String RECHARGE_TYPE="Recharge_type";

    //------------------------jpush  message  type-------------------------
    //会员
    public static final int NOTIFY_MSG_TYPE_ORDER_PAYED = 0;//app:order.payed
    public static final int NOTIFY_MSG_TYPE_ORDER_REFUND = 1;//app:order.refund
    public static final int NOTIFY_MSG_TYPE_ORDER_SEND = 2;//app:order.send
    public static final int NOTIFY_MSG_TYPE_ORDER_END = 3;//app:order.end
    public static final int NOTIFY_MSG_TYPE_ORDER_CLOSE = 4;//app:order.close
    public static final int NOTIFY_MSG_TYPE_REFUND_NOSEND = 5;//app:order.refund.nosend
    public static final int NOTIFY_MSG_TYPE_RREFUND_SEND = 6;//app:order.refund.send
    public static final int NOTIFY_MSG_TYPE_REFUND_ENDS = 7;//app:order.refund.ends
    public static final int NOTIFY_MSG_TYPE_REFUND_REFUSE = 8;//app:order.refund.refuse
    //商户
    public static final int NOTIFY_MSG_TYPE_MERCH_ORDER_SEND = 9;//app:merch.order.send
    public static final int NOTIFY_MSG_TYPE_MERCH_REFUND = 10;//app:merch.refund
    public static final int NOTIFY_MSG_TYPE_MERCH_ORDER_END = 11;//app:merch.order.end
    public static final int NOTIFY_MSG_TYPE_MERCH_REFUND_SEND = 12;//app:merch.refund.send
    //咨询、回复通知
    public static final int NOTIFY_MSG_TYPE_CHAT_MESSAGE = 13;//app:chat.message
}
