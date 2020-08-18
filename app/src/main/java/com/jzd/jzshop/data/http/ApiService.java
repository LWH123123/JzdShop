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
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by jzd on 2017/6/15.
 */

public interface ApiService {

    /**
     * 无参数请求 要去掉注解  @FormUrlEncoded
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=index")
    Observable<BaseResponse<HomeEntity>> postHomeData(@Field("os") String os);

    /**
     * openid nickname gender avatar province city area
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.login")
    Observable<BaseResponse> postLogin(@Field("openid") String openid,
                                       @Field("nickname") String nickname,
                                       @Field("gender") int gender,
                                       @Field("avatar") String avatar,
                                       @Field("province") String province,
                                       @Field("city") String city,
                                       @Field("area") String area,
                                       @Field("unionid") String unionid);

    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member")
    Observable<BaseResponse> postMemberData(@Field("user_token") String user_token);


    /**
     * 无参数请求 要去掉注解  @FormUrlEncoded
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=goods&mod=category")
    Observable<BaseResponse<HomeEntity>> postCategoryData(@Field("os") String os);

    /**
     * 购物车列表
     *
     * @param user_token
     * @return
     */
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=cart")
    Observable<BaseResponse<ShoppcarEntry>> postShoppingCart(@Field("user_token") String user_token);

    /**
     * 添加到购物车
     *
     * @param user_token
     * @return
     */
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=cart&mod=joins")
    Observable<BaseResponse> postAddShoppingCart(@Field("user_token") String user_token,
                                                 @Field("gid") String gid,
                                                 @Field("optionid") String optionid,
                                                 @Field("total") String total);

    /**
     * 商品列表
     *
     * @param merch_id 商户ID（从商户店铺进入商品详情页时传递该值，方便统计）
     *                 order  销量：salesreal； 价格：minprice
     *                 by    升序：asc； 降序：desc
     * @return
     */
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=goods")
    Observable<BaseResponse> postGoodsList(@Field("page") String page,
                                           @Field("isnew") String isnew,
                                           @Field("ishot") String ishot,
                                           @Field("isrecommand") String isrecommand,
                                           @Field("issendfree") String issendfree,
                                           @Field("keywords") String keywords,
                                           @Field("cate") String cate,
                                           @Field("order") String order,
                                           @Field("by") String by,
                                           @Field("merch_id") String merch_id);

    /**
     * 商品详情
     *
     * @param merchid 商户ID（从商户店铺进入商品详情页时传递该值，方便统计）
     * @return
     */
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=goods&mod=detail")
    Observable<BaseResponse> postGoods(@Field("user_token") String user_token, @Field("gid") String gid, @Field("merchid") String merchid,@Field("shop_id")String shopid);

    /**
     * 商品规格
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=goods&mod=picker")
    Observable<BaseResponse> postGoodsSpce(@Field("user_token") String user_token, @Field("gid") String gid);

    /**
     * 领取优惠券
     *
     * @return
     */
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=sale.coupon&mod=get")
    Observable<BaseResponse> postGetGoupon(@Field("user_token") String user_token, @Field("coupon_id") String coupon_id);

    /**
     * 商品详情评价
     * <p>
     * 评价类型。all：全部(默认); good：好评；normal：中评；bad：差评；pic：晒图
     *
     * @return
     */

    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=goods&mod=comments")
    Observable<BaseResponse> postGoodsComments(@Field("gid") String gid, @Field("level") String level, @Field("page") String page);

    Observable<BaseResponse<ShoppcarEntry>> postCart(@Field("user_token") String user_token);

    //更新商品数据的接口
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.cart&mod=update")
    Observable<BaseResponse> postUpdaterShop(@Field("user_token") String token, @Field("cart_id") String cartid, @Field("total") int total, @Field("optionid") int optionid);


    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=cart")
    Observable<BaseResponse<ShoppcarEntry>> postCarts(@Field("user_token") String user_token);

    //移除购物车
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.cart&mod=remove")
    Observable<BaseResponse> postUdapterRemove(@Field("user_token") String user_token, @Field("cart_ids") String cartids);


    //新增 编辑   收货地址
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.address&mod=submit")
    Observable<BaseResponse> postCompileAddress(@Field("user_token") String user_token, @Field("addr_id") String addr_id, @Field("realname") String name, @Field("mobile") String mobile, @Field("province") String province, @Field("city") String city, @Field("area") String area, @Field("address") String address, @Field("isdefault") int isdefault);

    //管理地址页面
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.address")
    Observable<BaseResponse<AddressListEntity>> postManageAddress(@Field("user_token") String usertoken, @Field("page") int page, @Field("pagesize") int pagesize);

    //删除地址
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.address&mod=delete")
    Observable<BaseResponse> postDeleteAddress(@Field("user_token") String usertoken, @Field("addr_id") String addr_id);

    //获取地址常量数据

    @GET("http://test.gtt20.com/addons/api_shopv1/data/area.json")
    Observable<List<AreaAddressEntity>> getAddressData();

    //设置默认地址
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.address&mod=setdefault")
    Observable<BaseResponse> postSetDefaultAddress(@Field("user_token") String usertoken, @Field("addr_id") String addr_id);

    //切话购物车商品选择状态
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.cart&mod=select")
    Observable<BaseResponse> postSetShopSelect(@Field("user_token") String user_token, @Field("cart_id") String cartid, @Field("select") int select);


    //确认订单
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.create")
    //从商品详情进入订单
    Observable<BaseResponse<FirmOrderEntity>> postOrderCreate(@Field("user_token") String user_token,
                                                              @Field("addr_id") String addr_id,
                                                              @Field("gid") String gid,
                                                              @Field("optionid") String optionid,
                                                              @Field("total") String total,
                                                              @Field("gift_id") String gift_id);


    //从购物车进入订单
    Observable<BaseResponse<FirmOrderEntity>> postOrderCreate(@Field("user_token") String user_token,
                                                              @Field("addr_id") String addr_id);


    //提交订单
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.submit")
    Observable<BaseResponse> postOrderSubmit(@Field("user_token") String user_token,
                                             @Field("addr_id") String addressid,
                                             @Field("remark") String remark,
                                             @Field("gid") String gid,
                                             @Field("optionid") String optionid,
                                             @Field("total") String total,
                                             @Field("gift_id") String gift_id,
                                             @Field("coupon_log_ids") String coupon_log_ids);

    //收银台
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.pay")
    Observable<BaseResponse> postOrderPay(@Field("user_token") String user_token,
                                          @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.pay")
    Observable<BaseResponse> postOrderPay(@Field("user_token") String user_token,
                                          @Field("order_id") String order_id,
                                          @Field("peer_price") double peer_price);

    //第三方支付
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.pay.index&mod=toPay")
    Observable<BaseResponse> postOrderToPay(@Field("user_token") String user_token,
                                            @Field("order_id") String order_id,
                                            @Field("pay_type") String pay_type);

    //支付结果
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.pay.index")
    Observable<BaseResponse> postOrderPayIndex(@Field("user_token") String user_token,
                                               @Field("order_id") String order_id,
                                               @Field("pay_type") String pay_type,
                                               @Field("ali_result") String ali_result);

    Observable<BaseResponse> postOrderPayIndex(@Field("user_token") String user_token,
                                               @Field("order_id") String order_id,
                                               @Field("pay_type") String pay_type);

    //商品详情收藏商品
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.favorite&mod=toggle")
    Observable<BaseResponse> postLoveGoods(@Field("user_token") String user_token, @Field("gid") String gid, @Field("is_favorite") int favorite);

    //获取所有的店铺的数据
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=shop")
    Observable<BaseResponse<ShopEntity>> postShopData(@Field("merch_id") String shopid);


    //商家联盟
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=shop.merch")
    Observable<BaseResponse> postShopMerch(@Field("page") String page, @Field("keys") String keys, @Field("pagesize") int pagesize);

    /*登录*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.login_tel")
    Observable<BaseResponse> postVertifyLogin(@Field("mobile") String mobile, @Field("vcode") String vcode);

    /*发送短信*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.sendsms")
    Observable<BaseResponse> postSendVertifyCode(@Field("mobile") String mobile, @Field("type") String type);


    /*完善个人信息*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.info")
    Observable<BaseResponse> postPerfect(@Field("user_token") String user_token);


    /*提交个人信息*/
//    @Multipart
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.info&mod=submit")
    Observable<BaseResponse> postSubmit(@Field("user_token") String user_tiken, @Field("nickname") String nickname,
                                        @Field("avatar") File avatar,
                                        @Field("diynidemingzi") String diynidemingzi,
                                        @Field("diyziwojieshao") String diyziwojieshao,
                                        @Field("self-introduction") String self_introduction,
                                        @Field("diyxingquaihao") String diyxingquaihao,
                                        @Field("diytupianzhanshi") List<File> pic,
                                        @Field("diyshenfenzhenghaoma") String diyshenfenzhenghaoma,
                                        @Field("diychushengriqi") String diychushengriqi,
                                        @Field("diybiyeriqi") String diybiyeriqi,
                                        @Field("diyhujidi") String diyhujidi,
                                        @Field("diyzhuzhisuozaidi") String zhuzhisuozaidi,
                                        @Field("diymima") String diymima,
                                        @Field("diyshuijueshijian") String diyshuijueshijian,
                                        @Field("diyshangbanshijian") String diyshangbanshijian,
                                        @Field("diynianling") String diynianling
    );



/*    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.info&mod=submit")
    Observable<BaseResponse> postSubmits(@Body RequestBody body);*/

    @Multipart
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.info&mod=submit")
    Observable<BaseResponse> postSubmits(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);


    //绑定手机号
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.info&mod=bind")
    Observable<BaseResponse> postBindMobile(@Field("user_token") String user_token, @Field("mobile") String mobile, @Field("vcode") String vcode);


    //我的页面头像上传
    @Multipart
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.info&mod=avatar")
    Observable<BaseResponse> postAvatar(@Part List<MultipartBody.Part> requestBodyMap);

    @Multipart
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.info&mod=avatar")
    Observable<BaseResponse<RequestBody>> postAvatar(@Field("user_token") String user_token, @Part("avatar") RequestBody description, @Part MultipartBody.Part file);

    /*宝贝评价*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=goods&mod=comments")
    Observable<BaseResponse> postGoodsEvaluatList(@Field("gid") String gid, @Field("level") String level, @Field("page") int page, @Field("pagesize") int pagesize);

    /* app更新*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=version")
    Observable<BaseResponse> postAppUpdate(@Field("version") String version);


    /*领券中心*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=sale.coupon")
    Observable<BaseResponse<CouponCenterEntity>> postCouponCenter(@Field("user_token") String user_token, @Field("page") int page, @Field("pagesize") int pagesize);

    //我的订单
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order")
    Observable<BaseResponse<MineOrderEntity>> postMyOrder(@Field("user_token") String user_token, @Field("status") String status, @Field("page") int page, @Field("pagesize") int pagesize);


    /*推广中心*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.commission.index")
    Observable<BaseResponse<TuiguangEntity>> postMyTui(@Field("user_token") String usertoken);


    /*商家入驻*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=shop.merch&mod=showinfo")
    Observable<BaseResponse<MerchEntity>> postMerch(@Field("user_token") String usertoken);

    /* 邀请好友*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.commission.qrcode")
    Observable<BaseResponse<PosterNewEntity>> postPosterData(@Field("user_token") String user_token);

    /*订单详情*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.detail")
    Observable<BaseResponse<OrderDetailEntity>> postOrderDetailData(@Field("user_token") String user_token, @Field("order_id") String order_id);


    /*取消订单*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.op&mod=cancel")
    Observable<BaseResponse> postCancleOrder(@Field("user_token") String usertoken, @Field("order_id") String order);

    /*确认收货*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.op&mod=finish")
    Observable<BaseResponse> postConfirmGoods(@Field("user_token") String user, @Field("order_id") String order_id);


    /*删除订单*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.op&mod=delete")
    Observable<BaseResponse> postDeleteOrder(@Field("user_token") String user, @Field("order_id") String order_id);

    /*评价显示*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.comment")
    Observable<BaseResponse> postShowCommentData(@Field("user_token") String user, @Field("order_id") String order_id);

    /*评价提交*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.comment.submit")
    Observable<BaseResponse> postSubmitCommentData(@Field("user_token") String user, @Field("order_id") String order_id);

    /*包裹信息*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.bundle")
    Observable<BaseResponse<LogisticListEntity>> postlogistic(@Field("user_token") String user, @Field("order_id") String order_id);

    /*物流信息*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.express")
    Observable<BaseResponse<LogisticInfoEntity>> postLogisticDetails(@Field("user_token") String user, @Field("order_id") String order,
                                                                     @Field("sendtype") String sendtype, @Field("bundle") String bundle);

    /*申请退款、售后（展示）*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.refund")
    Observable<BaseResponse<OrderRefundEntity>> postOrderRefund(@Field("user_token") String user, @Field("order_id") String order_id);

    /*申请退款、售后（进度）*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.refund.schedule")
    Observable<BaseResponse<OrderRefundProgressEntity>> postOrderRefundProgress(@Field("user_token") String user, @Field("order_id") String order_id);

    /*申请退款、售后（取消）*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.refund.cancel")
    Observable<BaseResponse<CancelRefundEntity>> postCancelRefundOrder(@Field("user_token") String user_token, @Field("order_id") String order_id,
                                                                       @Field("refund_id") String refund_id);

    /*申请退款、售后（填写物流单号）*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.refund.write")
    Observable<BaseResponse> postSubmitExpress(@Field("user_token") String user_token, @Field("order_id") String order_id, @Field("refund_id") String refund_id,
                                               @Field("express") String express, @Field("expresscom") String expresscom, @Field("expresssn") String expresssn);

    /*申请退款、售后（查看物流信息） 换货再次发货 物流信息*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.refund.express")
    Observable<BaseResponse> postExchangeLogistics(@Field("user_token") String user_token, @Field("order_id") String order_id, @Field("refund_id") String refund_id);

    /*申请退款、售后（收货）*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=order.refund.receive")
    Observable<BaseResponse> postRefundReceive(@Field("user_token") String user_token, @Field("order_id") String order_id, @Field("refund_id") String refund_id);


    /*充值提交*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.recharge")
    Observable<BaseResponse<OrderToPayEntity>> postRecharge(@Field("user_token") String usertoken, @Field("money") String money, @Field("couponid") String couponid, @Field("pay_type") String pay_type);


    /* 余额 充值记录*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.recharge.logs")
    Observable<BaseResponse<RechargeRecordEntity>> postRechargeRecord(@Field("user_token") String user, @Field("page") int page,
                                                                      @Field("pagesize") int pagesize);

    /* 余额 提现记录*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.withdraw.logs")
    Observable<BaseResponse<WithdrawalsRecordEntity>> postWithdrawalsRecord(@Field("user_token") String user, @Field("page") int page,
                                                                            @Field("pagesize") int pagesize);

    /* 君子权资产*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=currency")
    Observable<BaseResponse<AssetsEntity>> postAssetsData(@Field("user_token") String user);

    /* 君子权记录*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=currency.order")
    Observable<BaseResponse<AssetsRecordEntity>> postAssetsRecord(@Field("user_token") String user, @Field("status") int status, @Field("page") int page);

    /*积分签到排行*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.sign.rank")
    Observable<BaseResponse<AssetsRecordEntity>> postSignRanking(@Field("user_token") String user, @Field("type") String type,
                                                                 @Field("page") int page, @Field("pagesize") int pagesize);

    /* 本地生活*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.commission.log")
    Observable<BaseResponse<LocalLifeEntity>> postLocalLife(@Field("user_token") String user, @Field("status") int status);

    /*奖金提现*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.commission.withdraw")
    Observable<BaseResponse<BonuswithdrawEntity>> postBonuswithdraw(@Field("user_token") String user);

    /*获取提现要素*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.withdraw")
    Observable<BaseResponse<BalanceDataEntity>> postGetwithDraw(@Field("user_token") String usertoken);


    /*提现*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.withdraw.submit")
    Observable<BaseResponse> postBalanceSubmit(@Field("user_token") String token, @Field("money") String money, @Field("applytype") String applytype, @Field("realname") String name, @Field("account") String account, @Field("bank_name") String bank_name);

    /* 奖励明细*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.commission.log")
    Observable<BaseResponse<AwardListEntity>> postAwardList(@Field("user_token") String user, @Field("status") int status);

    /* 奖励订单（推广订单）*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.commission.order")
    Observable<BaseResponse<PromotionOrderEntity>> postPromotionOrder(@Field("user_token") String user, @Field("status") int status);

    /* 奖励明细  - 查看提现详情*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.commission.log&mod=detail")
    Observable<BaseResponse<AwarddetailEntity>> postAwardDetail(@Field("user_token") String user, @Field("log_id") String log_id,
                                                                @Field("page") int page);

    /*消息中心*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=notice")
    Observable<BaseResponse<MessageCenterEntity>> postMessageData(@Field("user_token") String user_token,
                                                                  @Field("page") int page, @Field("pagesize") int pagesize);
    /*消息中心-- 互动消息*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=notice.chat")
    Observable<BaseResponse<MessageCenterChatEntity>> postMessageChatData(@Field("user_token") String user_token,
                                                                          @Field("page") int page, @Field("pagesize") int pagesize);

    /*清空所有消息*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=notice.readAll")
    Observable<BaseResponse> clearAllMessage(@Field("user_token") String usertoken);

    /*读取消息*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=notice.read")
    Observable<BaseResponse> postReadMessage(@Field("user_token") String usertoken, @Field("id") String id);


    /*我的小店*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.myshop.registshow")
    Observable<BaseResponse> postMineShop(@Field("user_token") String usertoken);

    /*商户消息详情*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=notice.merch")
    Observable<BaseResponse<MessageMerchEntity>> postMessageMerchData(@Field("user_token") String user_token, @Field("order_id") String order_id,
                                                                      @Field("type") String type, @Field("refund_id") String refund_id);

    /*小店首页*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.myshop")
    Observable<BaseResponse<MineStoreEntity>> postMineShopHome(@Field("user_token") String usertoken, @Field("shop_id") String shop_id, @Field("keywords") String keyworks, @Field("page") int page);


    /*小店设置(展示)*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.myshop.setshow")
    Observable<BaseResponse> postMineSetShop(@Field("user_token") String usertoken);


    /*签到记录*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.sign.records")
    Observable<BaseResponse<RechargeRecordEntity>> postSignRecord(@Field("user_token") String user, @Field("page") int page,
                                                                  @Field("pagesize") int pagesize);

    /*签到首页（展示）*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.sign")
    Observable<BaseResponse<SignEntity>> postGetSign(@Field("user_token") String usertoken);

    /*签到*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.sign.dosign")
    Observable<BaseResponse<ToSignEntity>> postToSign(@Field("user_token") String usertoken, @Field("date") String date);

    /*签到日期数据（展示）*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.sign.calendar")
    Observable<BaseResponse<SignDateEntity>> postGetSignDate(@Field("user_token") String usertoken, @Field("date") String date);

    /*领取积分*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.sign.doreward")
    Observable<BaseResponse<SignReceiveEntity>> postToReceive(@Field("user_token") String usertoken, @Field("type") String type, @Field("day") String data);

    /*推广佣金*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.commission.merge")
    Observable<BaseResponse<PromotCommissionEntity>> postPromotCommis(@Field("user_token") String user);

    /*推广明细*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.commission.merge.commissionlogs")
    Observable<BaseResponse<PromotionListEntity>> postPromotOrder(@Field("user_token") String user);

    /*招商*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.commission.merge.merchlogs")
    Observable<BaseResponse<AttractInvestmentEntity>> postBusiness(@Field("user_token") String user);

    /*佣金 -- 提现中*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=")
    Observable<BaseResponse<InCommisWithdrawEntity>> postInCommisWithdraw(@Field("user_token") String user);

    /*佣金 -- 提现记录*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.commission.merge.logs")
    Observable<BaseResponse<CommisWithdraRecordEntity>> postCommisWithdraRecord(@Field("user_token") String user,@Field("type") String type,
                                                                                @Field("page") int page);


    /*推广中心--提现申请*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.commission.merge.getWithdraw")
    Observable<BaseResponse<WithdrawApplyEntity>> postGetWithdrawData(@Field("user_token") String user);



    /*推广中心  - 提现*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.commission.merge.withdraw")
    Observable<BaseResponse> postWithdraw(@Field("user_token") String user);


    //我的团队
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=member.commission.down")
    Observable<BaseResponse<MineTeamEntity>> postCommissionDown(@Field("user_token")String user, @Field("page")int page);

    /* 积分商城 首页*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop")
    Observable<BaseResponse<CreditsStoreEntity>> postCreditShop(@Field("user_token") String user, @Field("merch_id") String merch_id);
    /* 积分商城 全部商品*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop.lists")
    Observable<BaseResponse<CreditsAllGoodsEntity>> postCreditAllGoods(@Field("user_token") String user, @Field("cate_id") String cate_id,
                                                                   @Field("keywords") String keywords, @Field("merch_id") String merch_id, @Field("page") int page);
    /* 积分商城  我的*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop.log.lists")
    Observable<BaseResponse<CreditExchangeRecoEntity>> postCreditExchangeRecod(@Field("user_token") String user, @Field("merch_id") String merch_id,
                                                                               @Field("status") int status, @Field("page") int page);
    /*积分商城 参与记录*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop.log")
    Observable<BaseResponse<PartakeRecordEntity>> postPartakeRecord(@Field("user_token") String user, @Field("merch_id") String merch_id, @Field("page") int page,
                                                                    @Field("pagesize") int pagesize);
    /* 积分商城 订单详情*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop.log.detail")
    Observable<BaseResponse<CreditOrderDetailEntity>> postCreditOrderDetailData(@Field("user_token") String user_token, @Field("log_id") String log_id,
                                                                                @Field("merch_id") String merch_id);

    /* 积分商城 确认订单*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop.create")
    Observable<BaseResponse<CreditsConfirmOrderEntity>> postCreditConfirmOrder(@Field("user_token") String user,@Field("addr_id") String addr_id,
              @Field("gid") String gid, @Field("optionid") String optionid, @Field("merch_id") String merch_id);

    /*积分商城  商品详情*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop.detail")
    Observable<BaseResponse<CreditGoodsDetailsEntity>> postCreditDetail(@Field("user_token")String usertoken, @Field("gid")String gid, @Field("merch_id")String merchid);


    /*积分商城 -商品详情 参与记录*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop.detail.logs")
    Observable<BaseResponse<CreditDetailsLogsEntity>> postCreditDetailsLogs(@Field("gid")String gid, @Field("page")int page, @Field("page_size")int pagesize);

    /*积分商城 - 收银台*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop.create.pay")
    Observable<BaseResponse<CreditPayEntity>> postCreditPay(@Field("user_token")String usertoken, @Field("gid")String gid, @Field("optionid") String option, @Field("addr_id")String addid, @Field("num")int num, @Field("merch_id")String merchid);


    /* 积分商城 确认订单  变更数量*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop.create.dispatch")
    Observable<BaseResponse<ExchageGoodsNumEntity>> requestExchageGoodsNum(@Field("user_token") String user, @Field("gid") String gid
            , @Field("optionid") String optionid,@Field("addr_id") String addr_id, @Field("num") String num, @Field("merch_id") String merch_id);

    /* 积分商城 订单详情 确认收货*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop.log.finish")
    Observable<BaseResponse<CreditToSureReceiptEntity>> postToSureReceipt(@Field("user_token") String user_token, @Field("log_id") String log_id,
                                                                          @Field("merch_id") String merch_id);
    /* 积分商城 订单详情 领取红包*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop.log.Receivepacket")
    Observable<BaseResponse<CreditGetRedBagsEntity>> postGetRedBags(@Field("user_token") String user_token, @Field("log_id") String log_id,
                                                                    @Field("merch_id") String merch_id);

    /*积分商城  支付接口*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop.create.topay")
    Observable<BaseResponse<OrderToPayEntity>> postCreditTopay(@Field("user_token")String usertoken,
                                                                @Field("paytype")String paytype,
                                                                @Field("gid")String gid,
                                                                @Field("optionid")int option,
                                                                @Field("addr_id")String addid,
                                                                @Field("num")int num);


    /*积分商城 物流明细*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop.log.express")
    Observable<BaseResponse<CreditExpressEntity>> postCreditExpress(@Field("user_token")String usertoken,
                                                                    @Field("log_id")String logid,
                                                                    @Field("merch_id")String merchid);


    /*积分商城 正式兑换 */
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop.create.lottery")
    Observable<BaseResponse<LotteryEntity>> postCreditLottery(@Field("user_token")String usertoken);


    /*积分商城 订单详情 评价*/
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=creditshop.comment")
    Observable<BaseResponse<CreditCommentEntity>> postShowCreditComment(@Field("user_token") String user, @Field("log_id") String log_id,
                                                                        @Field("merch_id") String merch_id);
    /*首页 商品组 */
    @FormUrlEncoded
    @POST("app/index.php?m=api_shopv1&c=entry&a=app&do=app&r=goods")
    Observable<BaseResponse<HomeGoodsEntity>> postHomeGoodsList(@Field("page") int page, @Field("pagesize") int pagesize);


}
