package com.jzd.jzshop.data.http;

import com.jzd.jzshop.entity.AddressListEntity;
import com.jzd.jzshop.entity.AreaAddressEntity;
import com.jzd.jzshop.entity.AssetsEntity;
import com.jzd.jzshop.entity.AssetsRecordEntity;
import com.jzd.jzshop.entity.AttractInvestmentEntity;
import com.jzd.jzshop.entity.AwardListEntity;
import com.jzd.jzshop.entity.AwarddetailEntity;
import com.jzd.jzshop.entity.BalanceDataEntity;
import com.jzd.jzshop.entity.BonuswithdrawEntity;
import com.jzd.jzshop.entity.CancelRefundEntity;
import com.jzd.jzshop.entity.CommisWithdraRecordEntity;
import com.jzd.jzshop.entity.CouponCenterEntity;
import com.jzd.jzshop.entity.CreditCommentEntity;
import com.jzd.jzshop.entity.CreditDetailsLogsEntity;
import com.jzd.jzshop.entity.CreditExchangeRecoEntity;
import com.jzd.jzshop.entity.CreditExpressEntity;
import com.jzd.jzshop.entity.CreditGetRedBagsEntity;
import com.jzd.jzshop.entity.CreditOrderDetailEntity;
import com.jzd.jzshop.entity.CreditGoodsDetailsEntity;
import com.jzd.jzshop.entity.CreditPayEntity;
import com.jzd.jzshop.entity.CreditToSureReceiptEntity;
import com.jzd.jzshop.entity.CreditsAllGoodsEntity;
import com.jzd.jzshop.entity.CreditsConfirmOrderEntity;
import com.jzd.jzshop.entity.CreditsStoreEntity;
import com.jzd.jzshop.entity.ExchageGoodsNumEntity;
import com.jzd.jzshop.entity.FirmOrderEntity;
import com.jzd.jzshop.entity.HomeEntity;
import com.jzd.jzshop.entity.HomeGoodsEntity;
import com.jzd.jzshop.entity.InCommisWithdrawEntity;
import com.jzd.jzshop.entity.LocalLifeEntity;
import com.jzd.jzshop.entity.LogisticInfoEntity;
import com.jzd.jzshop.entity.LogisticListEntity;
import com.jzd.jzshop.entity.LotteryEntity;
import com.jzd.jzshop.entity.MerchEntity;
import com.jzd.jzshop.entity.MessageCenterChatEntity;
import com.jzd.jzshop.entity.MessageCenterEntity;
import com.jzd.jzshop.entity.MessageMerchEntity;
import com.jzd.jzshop.entity.MineOrderEntity;
import com.jzd.jzshop.entity.MineStoreEntity;
import com.jzd.jzshop.entity.MineTeamEntity;
import com.jzd.jzshop.entity.OrderDetailEntity;
import com.jzd.jzshop.entity.OrderRefundEntity;
import com.jzd.jzshop.entity.OrderRefundProgressEntity;
import com.jzd.jzshop.entity.OrderToPayEntity;
import com.jzd.jzshop.entity.PartakeRecordEntity;
import com.jzd.jzshop.entity.PosterNewEntity;
import com.jzd.jzshop.entity.PromotCommissionEntity;
import com.jzd.jzshop.entity.PromotionListEntity;
import com.jzd.jzshop.entity.PromotionOrderEntity;
import com.jzd.jzshop.entity.RechargeRecordEntity;
import com.jzd.jzshop.entity.ShopEntity;
import com.jzd.jzshop.entity.ShoppcarEntry;
import com.jzd.jzshop.entity.SignDateEntity;
import com.jzd.jzshop.entity.SignEntity;
import com.jzd.jzshop.entity.SignReceiveEntity;
import com.jzd.jzshop.entity.ToSignEntity;
import com.jzd.jzshop.entity.TuiguangEntity;
import com.jzd.jzshop.entity.WithdrawApplyEntity;
import com.jzd.jzshop.entity.WithdrawalsRecordEntity;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by jzd on 2019/3/26.
 */
public interface HttpDataSource {
    //首页
    Observable<BaseResponse<HomeEntity>> postHomeData();

    //登录
    Observable<BaseResponse> postLogin(String openid, String nickname, int gender,
                                       String avatar, String province, String city, String area, String unionid);

    //获取会员信息
    Observable<BaseResponse> postMember(String user_token);

    //分类
    Observable<BaseResponse<HomeEntity>> postCategoryData();

    //购物车
    Observable<BaseResponse<ShoppcarEntry>> postCarts(String user_token);

    //更新购物车
    Observable<BaseResponse> postUpdaterShop(String user_token, String cart_id, int total, int optionid);

    //移除购物车的数据
    Observable<BaseResponse> postUpdaterRemove(String user_token, String cart_ids);

    //新建 编辑 地址
    Observable<BaseResponse> postCompileAddress(String user_token, String addr_id, String realname, String mobile, String province, String city, String area, String address, int isdefault);

    //管理收货地址
    Observable<BaseResponse<AddressListEntity>> postManageAddress(String user_token, int page, int pagesize);

    //删除地址
    Observable<BaseResponse> postDeleteAddress(String user_token, String addr_id);

    //设置默认地址
    Observable<BaseResponse> postSetDefaultAddress(String usertoken, String addr_id);

    //地址常量数据
    Observable<List<AreaAddressEntity>> getAddressData();

    //更新购物车的选中状态
    Observable<BaseResponse> postSetSelect(String user_token, String cart_id, int select);

    //购物车
    Observable<BaseResponse<ShoppcarEntry>> postShoppingCart(String user_token);

    //add购物车
    Observable<BaseResponse> postAddShoppingCart(String user_token, String gid, String optionid, String total);

    /**
     * 商品列表
     *
     * @param merch_id 商户ID（从商户店铺进入商品详情页时传递该值，方便统计）
     *                 order  销量：salesreal； 价格：minprice
     *                 by    升序：asc； 降序：desc
     * @return
     */
    Observable<BaseResponse> postGoodsList(String page,
                                           String isnew,
                                           String ishot,
                                           String isrecommand,
                                           String issendfree,
                                           String keywords,
                                           String cate,
                                           String order,
                                           String by,
                                           String merch_id);

    //商品详情
    Observable<BaseResponse> postGoods(String user_token, String gid, String merchid,String shopid);

    //商品规格
    Observable<BaseResponse> postGoodsSpce(String user_token, String gid);

    //领取优惠券
    Observable<BaseResponse> postGetGoupon(String user_token, String coupon_id);

    //商品详情评价
    Observable<BaseResponse> postGoodsComments(String gid, String level, String page);

    /**
     * 确认订单
     */
    Observable<BaseResponse<FirmOrderEntity>> postOrderCreate(String user_token,
                                                              String addr_id,
                                                              String gid,
                                                              String optionid,
                                                              String total,
                                                              String gift_id);

    Observable<BaseResponse<FirmOrderEntity>> postOrderCreate(String user_token,
                                                              String addr_id
    );


    //提交订单
    Observable<BaseResponse> postOrderSubmit(String user_token,
                                             String addr_id,
                                             String remark,
                                             String gid,
                                             String optionid,
                                             String total,
                                             String gift_id,
                                             String coupon_log_ids);

    //收银台
    Observable<BaseResponse> postOrderPay(String user_token, String order_id);

    Observable<BaseResponse> postOrderPay(String user_token, String order_id, double peer_price);

    //第三方支付
    Observable<BaseResponse> postOrderToPay(String user_token,
                                            String order_id,
                                            String pay_type);

    //支付结果
    Observable<BaseResponse> postOrderPayIndex(String user_token,
                                               String order_id,
                                               String pay_type,
                                               String ali_result);

    Observable<BaseResponse> postOrderPayIndex(String user_token,
                                               String order_id,
                                               String pay_type);

    //商品详情收藏
    Observable<BaseResponse> postLoveGood(String user_token, String gid, int favorite);


    //商家的店铺
    Observable<BaseResponse<ShopEntity>> postShopData(String shopid);

    //商家联盟
    Observable<BaseResponse> postShopMerch(String page, String keys, int pagesize);

    /*发送验证码*/
    Observable<BaseResponse> postSendVertifyCode(String mobile, String type);

    /*验证码登录*/
    Observable<BaseResponse> postVertifyLogin(String mobile, String vcode);


    /*完善个人信息*/
    Observable<BaseResponse> postPerfect(String user_token);

    /*提交个人信息*/
    Observable<BaseResponse> postsubmit(String user, String nickname, File avatar,
                                        String diynidemingzi,
                                        String diyziwojieshao,
                                        String self_introduction,
                                        String diyxingquaihao,
                                        List<File> pic,
                                        String diyshenfenzhenghaoma,
                                        String diychushengriqi,
                                        String diybiyeriqi,
                                        String diyhujidi,
                                        String zhuzhisuozaidi,
                                        String diymima,
                                        String diyshuijueshijian,
                                        String diyshangbanshijian,
                                        String diynianling
    );


    /*绑定手机号*/
    Observable<BaseResponse> postbindmobile(String user_token, String mobile, String vcode);


    //提交
//    Observable<BaseResponse> postSubmits(@Body RequestBody body);

    //提交
    Observable<BaseResponse> postSubmits(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);

    //我的页面头像上传
    Observable<BaseResponse> postAvatar(@Part List<MultipartBody.Part> requestBodyMap);


    Observable<BaseResponse<RequestBody>> postAvatar(@Field("user_token") String user_token, @Part RequestBody description, @Part MultipartBody.Part file);

    /*宝贝评价*/
    Observable<BaseResponse> postGoodsEvaluatList(String gid, String level, int page, int pagesize);

    /*app 更新*/
    Observable<BaseResponse> postAppUpdate(String version);

    /*领取优惠券*/
    Observable<BaseResponse<CouponCenterEntity>> postCouponCenter(String user_token, int page, int pagesize);

    /*我的订单*/
    Observable<BaseResponse<MineOrderEntity>> postMyOrder(String usertoken, String status, int page, int pagesize);

    /*推广中心*/
    Observable<BaseResponse<TuiguangEntity>> postMyTui(String token);

    /*商家入驻*/
    Observable<BaseResponse<MerchEntity>> postMerch(String merch);

    /* 邀请好友*/
    Observable<BaseResponse<PosterNewEntity>> postPosterData(String usertoken);

    /*订单详情*/
    Observable<BaseResponse<OrderDetailEntity>> postOrderDetailData(String usertoken, String order_id);

    /*取消订单*/
    Observable<BaseResponse> postCancleOrder(String usertoken, String order_id);

    /*确认收货*/
    Observable<BaseResponse> postConfirmGoods(String usertoken, String order_id);


    /*删除订单*/
    Observable<BaseResponse> postDeleteOrder(String usertoken, String order);

    /*评价显示*/
    Observable<BaseResponse> postShowCommentData(String usertoken, String order);

    /*评价提交*/
    Observable<BaseResponse> postSubmitCommentData(String usertoken, String order);

    /*物流信息*/
    Observable<BaseResponse<LogisticListEntity>> postLogistic(String user_token, String order);

    /*申请退款、售后（展示）*/
    Observable<BaseResponse<OrderRefundEntity>> postOrderRefund(String user_token, String order);

    /*包裹信息*/
    Observable<BaseResponse<LogisticInfoEntity>> postLogisticDetails(String user, String order, String sendtype, String bundle);

    /*申请退款、售后（取消）*/
    Observable<BaseResponse<CancelRefundEntity>> postCancelRefundOrder(String user, String order, String refund_id);

    /*申请退款、售后（进度）*/
    Observable<BaseResponse<OrderRefundProgressEntity>> postOrderRefundProgress(String user_token, String order);

    Observable<BaseResponse> postSubmitExpress(String user_token, String order, String refund_id, String express, String expresscom, String expresssn);

    /*申请退款、售后（查看物流信息）*/
    Observable<BaseResponse> postExchangeLogistics(String user_token, String order, String refund_id);

    /*申请退款、售后（收货）*/
    Observable<BaseResponse> postRefundReceive(String user_token, String order, String refund_id);

    /*余额 充值记录*/
    Observable<BaseResponse<RechargeRecordEntity>> postRechargeRecord(String user_token, int page, int pagesize);

    /*余额 提现记录*/
    Observable<BaseResponse<WithdrawalsRecordEntity>> postWithdrawalsRecord(String user_token, int page, int pagesize);

    /*君子权资产*/
    Observable<BaseResponse<AssetsEntity>> postAssetsData(String user_token);


    /*充值*/
    Observable<BaseResponse<OrderToPayEntity>> postRecharge(String usertoken, String money, String couponid, String pay_type);

    /*君子权记录*/
    Observable<BaseResponse<AssetsRecordEntity>> postAssetsRecord(String user_token, int status, int page);

    /*积分签到排行*/
    Observable<BaseResponse<AssetsRecordEntity>> postSignRanking(String user_token, String type, int page, int pagesize);

    /*本地生活*/
    Observable<BaseResponse<LocalLifeEntity>> postLocalLife(String user_token, int status);

    /*奖金提现*/
    Observable<BaseResponse<BonuswithdrawEntity>> postBonuswithdraw(String usertoken);

    /*奖励明细*/
    Observable<BaseResponse<AwardListEntity>> postAwardList(String user_token, int status);

    /*奖励订单（推广订单）*/
    Observable<BaseResponse<PromotionOrderEntity>> postPromotionOrder(String user_token, int status);

    /*奖励明细  查看提现详情*/
    Observable<BaseResponse<AwarddetailEntity>> postAwardDetail(String user_token, String log_id, int page);

    /*获取提现要素*/
    Observable<BaseResponse<BalanceDataEntity>> postGetwithDraw(String usertoken);

    /*提现*/
    Observable<BaseResponse> postBalanceSubmit(String token, String money, String applytype, String name, String account, String bank_name);

    /* 推送消息下发*/
    Observable<BaseResponse<MessageCenterEntity>> postMessageData(String token, int page, int pagesize);
    /* 推送  互动消息 下发*/
    Observable<BaseResponse<MessageCenterChatEntity>> postMessageChatData(String token, int page, int pagesize);

    /*清空所有消息*/
    Observable<BaseResponse> clearAllMessage(String token);

    /*读取消息*/
    Observable<BaseResponse> postReadMessage(String token, String id);

    /*我的小店*/
    Observable<BaseResponse> postMineShop(String usertoken);

    /*商户消息详情*/
    Observable<BaseResponse<MessageMerchEntity>> postMessageMerchData(String token, String order_id, String type, String refund_id);

    /*签到记录*/
    Observable<BaseResponse<RechargeRecordEntity>> postSignRecord(String user_token, int page, int pagesize);

    /*签到首页（展示）*/
    Observable<BaseResponse<SignEntity>> postGetSign(String user_token);

    /*签到*/
    Observable<BaseResponse<ToSignEntity>> postToSign(String user_token, String date);

    /*签到日期数据（展示）*/
    Observable<BaseResponse<SignDateEntity>> postGetSignDate(String user_token, String date);
    /**/
    Observable<BaseResponse<SignReceiveEntity>> postToReceive(String user_token, String type, String data);

    /*小店首页*/
    Observable<BaseResponse<MineStoreEntity>> postMineShopHome(String usertoken, String shopid, String keywords, int page);

    /*小店设置(展示)*/
    Observable<BaseResponse> postMineSetShop(String usertoken);

    /*推广佣金*/
    Observable<BaseResponse<PromotCommissionEntity>> postPromotCommis(String user_token);
    /*推广*/
    Observable<BaseResponse<PromotionListEntity>> postPromotOrder(String user_token);
    /*招商*/
    Observable<BaseResponse<AttractInvestmentEntity>> postBusiness(String user_token);
    /*佣金 -- 提现中*/
    Observable<BaseResponse<InCommisWithdrawEntity>> postInCommisWithdraw(String user_token);

    /*推广中心 -- 提现申请*/
    Observable<BaseResponse<WithdrawApplyEntity>> postGetWithdrawData(String user_token);
    /*推广中心-- 提现*/
    Observable<BaseResponse> postWithDraw(String with);
    Observable<BaseResponse<CommisWithdraRecordEntity>> postCommisWithdraRecord(String user_token,String type,int page);


    //我的团队
    Observable<BaseResponse<MineTeamEntity>> postCommissionDown(String usertoken, int page);

    /*积分商城*/
    Observable<BaseResponse<CreditsStoreEntity>> postCreditShop(String user_token, String merch_id);
    /*积分商城 全部商品*/
    Observable<BaseResponse<CreditsAllGoodsEntity>> postCreditAllGoods(String user_token, String cate_id,String keywords,String merch_id,int page);
    /*积分商城  我的 */
    Observable<BaseResponse<CreditExchangeRecoEntity>> postCreditExchangeRecod(String user_token, String merch_id, int status, int page);
    /*积分商城 参与记录*/
    Observable<BaseResponse<PartakeRecordEntity>> postPartakeRecord(String user_token, String merch_id,int page, int pagesize);
    /*积分商城  商品详情*/
    Observable<BaseResponse<CreditGoodsDetailsEntity>> postCreditDetail(String usertoken, String gid, String merchid);
    /*积分商城  商品详情 参与记录*/
    Observable<BaseResponse<CreditDetailsLogsEntity>> postCreditDetailsLogs(String gid, int page, int pagesize);

    /*积分商城 订单详情*/
    Observable<BaseResponse<CreditOrderDetailEntity>> postCreditOrderDetailData(String usertoken, String log_id,String merch_id);
    /*积分商城  确认订单*/
    Observable<BaseResponse<CreditsConfirmOrderEntity>> postCreditConfirmOrder(String user_token, String addr_id ,String gid, String optionid, String merch_id);
    /*积分商城  收银台*/
    Observable<BaseResponse<CreditPayEntity>> postCreditPay(String usertoken, String gid, String option, String addr_id, int num, String merch_id);


    /*积分商城  确认订单 更改数量*/
    Observable<BaseResponse<ExchageGoodsNumEntity>> requestExchageGoodsNum(String user_token, String gid, String optionid,
                                                                           String addr_id,String num,String merch_id);
    /*积分商城 订单详情  确认收货*/
    Observable<BaseResponse<CreditToSureReceiptEntity>> postToSureReceipt(String usertoken, String log_id, String merch_id);
    /*积分商城 订单详情  领取红包*/
    Observable<BaseResponse<CreditGetRedBagsEntity>> postGetRedBags(String usertoken, String log_id, String merch_id);

    /*积分商城 兑换 与支付*/
    Observable<BaseResponse<OrderToPayEntity>> postCreditTopay(String usertoken, String paytype, String gid, int option, String addid, int num);

    /*积分商城  物流明细*/
    Observable<BaseResponse<CreditExpressEntity>> postCreditExpress(String usertoken, String logid, String merchid);

    /*积分商城  正式兑换*/
    Observable<BaseResponse<LotteryEntity>> postCreditLottery(String usertoken);



    /*积分商城 订单详情 评价*/
    Observable<BaseResponse<CreditCommentEntity>> postShowCreditComment(String usertoken, String log_id,String merch_id);
    /*首页 商品组 */
    Observable<BaseResponse<HomeGoodsEntity>> postHomeGoodsList(int page, int pagesize);
}
