package com.jzd.jzshop.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.jzd.jzshop.data.http.HttpDataSource;
import com.jzd.jzshop.data.local.LocalDataSource;
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
import com.jzd.jzshop.entity.CreditGoodsDetailsEntity;
import com.jzd.jzshop.entity.CreditOrderDetailEntity;
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
import com.jzd.jzshop.entity.UserInfo;
import com.jzd.jzshop.entity.WithdrawApplyEntity;
import com.jzd.jzshop.entity.WithdrawalsRecordEntity;
import com.slodonapp.ywj_release.wxapi.WXUserInfo;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.http.BaseResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * MVVM的Model层，统一模块的数据仓库，包含网络数据和本地数据（一个应用可以有多个Repositor）
 * Created by jzd on 2019/3/26.
 */
public class Repository extends BaseModel implements HttpDataSource, LocalDataSource {
    private volatile static Repository INSTANCE = null;
    private final HttpDataSource mHttpDataSource;

    private final LocalDataSource mLocalDataSource;

    private Repository(@NonNull HttpDataSource httpDataSource,
                       @NonNull LocalDataSource localDataSource) {
        this.mHttpDataSource = httpDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static Repository getInstance(HttpDataSource httpDataSource,
                                         LocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (Repository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Repository(httpDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public Observable<BaseResponse<HomeEntity>> postHomeData() {
        return mHttpDataSource.postHomeData();
    }


    @Override
    public Observable<BaseResponse> postLogin(String openid, String nickname, int gender, String avatar, String province, String city, String area, String unionid) {
        return mHttpDataSource.postLogin(openid, nickname, gender, avatar, province, city, area, unionid);
    }

    @Override
    public Observable<BaseResponse> postMember(String user_token) {
        return mHttpDataSource.postMember(user_token);
    }

    @Override
    public Observable<BaseResponse<HomeEntity>> postCategoryData() {
        return mHttpDataSource.postCategoryData();
    }

    @Override
    public Observable<BaseResponse<ShoppcarEntry>> postCarts(String user_token) {
        return mHttpDataSource.postCarts(user_token);
    }

    @Override
    public Observable<BaseResponse> postUpdaterShop(String user_token, String cart_id, int total, int optionid) {
        return mHttpDataSource.postUpdaterShop(user_token, cart_id, total, optionid);
    }

    @Override
    public Observable<BaseResponse> postUpdaterRemove(String user_token, String cart_ids) {
        return mHttpDataSource.postUpdaterRemove(user_token, cart_ids);
    }

    @Override
    public Observable<BaseResponse> postCompileAddress(String user_token, String addr_id, String realname, String mobile, String province, String city, String area, String address, int isdefault) {
        return mHttpDataSource.postCompileAddress(user_token, addr_id, realname, mobile, province, city, area, address, isdefault);
    }

    @Override
    public Observable<BaseResponse<AddressListEntity>> postManageAddress(String user_token, int page, int pagesize) {
        return mHttpDataSource.postManageAddress(user_token, page, pagesize);
    }

    @Override
    public Observable<BaseResponse> postDeleteAddress(String user_token, String addr_id) {
        return mHttpDataSource.postDeleteAddress(user_token, addr_id);
    }

    @Override
    public Observable<BaseResponse> postSetDefaultAddress(String usertoken, String addr_id) {
        return mHttpDataSource.postSetDefaultAddress(usertoken, addr_id);
    }

    @Override
    public Observable<List<AreaAddressEntity>> getAddressData() {
        return mHttpDataSource.getAddressData();
    }

//    @Override
//    public Observable<BaseResponse<OrderEntity>> postConfirmOrder(String user_token, String gid) {
//        return mHttpDataSource.postConfirmOrder(user_token,gid);
//    }

    @Override
    public Observable<BaseResponse> postSetSelect(String user_token, String cart_id, int select) {
        return mHttpDataSource.postSetSelect(user_token, cart_id, select);
    }

    @Override
    public Observable<BaseResponse<ShoppcarEntry>> postShoppingCart(String user_token) {
        return mHttpDataSource.postShoppingCart(user_token);
    }

    @Override
    public Observable<BaseResponse> postAddShoppingCart(String user_token, String gid, String optionid, String total) {
        return mHttpDataSource.postAddShoppingCart(user_token, gid, optionid, total);
    }

    @Override
    public Observable<BaseResponse> postGoodsList(String page, String isnew, String ishot, String isrecommand, String issendfree, String keywords, String cate, String order, String by, String merch_id) {
        return mHttpDataSource.postGoodsList(page, isnew, ishot, isrecommand, issendfree, keywords, cate, order, by, merch_id);
    }

    @Override
    public Observable<BaseResponse> postGoods(String user_token, String gid, String merchid,String shopid) {
        return mHttpDataSource.postGoods(user_token, gid, merchid,shopid);
    }

    @Override
    public Observable<BaseResponse> postGoodsSpce(String user_token, String gid) {
        return mHttpDataSource.postGoodsSpce(user_token, gid);
    }

    @Override
    public Observable<BaseResponse> postGetGoupon(String user_token, String coupon_id) {
        return mHttpDataSource.postGetGoupon(user_token, coupon_id);
    }

    @Override
    public Observable<BaseResponse> postGoodsComments(String gid, String level, String page) {
        return mHttpDataSource.postGoodsComments(gid, level, page);
    }

    /**
     * 确认订单
     * 这个接口逻辑上不会有地址id传进来 只能是在确认订单页面中或者地址列表中创建地址
     *
     * @param user_token
     * @param gid
     * @param optionid
     * @param total
     * @param gift_id
     */
    @Override
    public Observable<BaseResponse<FirmOrderEntity>> postOrderCreate(String user_token, String addr_id, String gid, String optionid, String total, String gift_id) {
        return mHttpDataSource.postOrderCreate(user_token, addr_id, gid, optionid, total, gift_id);
    }

    @Override
    public Observable<BaseResponse<FirmOrderEntity>> postOrderCreate(String user_token, String addr_id) {
        return mHttpDataSource.postOrderCreate(user_token, addr_id);
    }


    @Override
    public Observable<BaseResponse> postOrderSubmit(String user_token, String addr_id, String remark, String gid, String optionid, String total, String gift_id, String coupon_log_ids) {
        return mHttpDataSource.postOrderSubmit(user_token, addr_id, remark, gid, optionid, total, gift_id, coupon_log_ids);
    }

    @Override
    public Observable<BaseResponse> postOrderPay(String user_token, String order_id) {
        return mHttpDataSource.postOrderPay(user_token, order_id);
    }

    @Override
    public Observable<BaseResponse> postOrderPay(String user_token, String order_id, double peer_price) {
        return mHttpDataSource.postOrderPay(user_token, order_id, peer_price);
    }

    @Override
    public Observable<BaseResponse> postOrderToPay(String user_token, String order_id, String pay_type) {
        return mHttpDataSource.postOrderToPay(user_token, order_id, pay_type);
    }

    @Override
    public Observable<BaseResponse> postOrderPayIndex(String user_token, String order_id, String pay_type, String ali_result) {
        return mHttpDataSource.postOrderPayIndex(user_token, order_id, pay_type, ali_result);
    }

    @Override
    public Observable<BaseResponse> postOrderPayIndex(String user_token, String order_id, String pay_type) {
        return mHttpDataSource.postOrderPayIndex(user_token, order_id, pay_type);
    }

    @Override
    public Observable<BaseResponse> postLoveGood(String user_token, String gid, int favorite) {
        return mHttpDataSource.postLoveGood(user_token, gid, favorite);
    }

    @Override
    public Observable<BaseResponse<ShopEntity>> postShopData(String shopid) {
        return mHttpDataSource.postShopData(shopid);
    }

    @Override
    public Observable<BaseResponse> postShopMerch(String page, String keys, int pagesize) {
        return mHttpDataSource.postShopMerch(page, keys, pagesize);
    }

    @Override
    public Observable<BaseResponse> postSendVertifyCode(String mobile, String type) {
        return mHttpDataSource.postSendVertifyCode(mobile, type);
    }

    @Override
    public Observable<BaseResponse> postVertifyLogin(String mobile, String vcode) {
        return mHttpDataSource.postVertifyLogin(mobile, vcode);
    }

    @Override
    public Observable<BaseResponse> postPerfect(String user_token) {
        return mHttpDataSource.postPerfect(user_token);
    }

    @Override
    public Observable<BaseResponse> postsubmit(String user, String nickname, File avatar, String diynidemingzi, String diyziwojieshao, String self_introduction, String diyxingquaihao, List<File> pic, String diyshenfenzhenghaoma, String diychushengriqi, String diybiyeriqi, String diyhujidi, String zhuzhisuozaidi, String diymima, String diyshuijueshijian, String diyshangbanshijian, String diynianling) {
        return mHttpDataSource.postsubmit(user, nickname, avatar, diynidemingzi, diyziwojieshao, self_introduction, diyxingquaihao, pic, diyshenfenzhenghaoma, diychushengriqi, diybiyeriqi, diyhujidi, zhuzhisuozaidi, diymima, diyshuijueshijian, diyshangbanshijian, diynianling);
    }

    @Override
    public Observable<BaseResponse> postbindmobile(String user_token, String mobile, String vcode) {
        return mHttpDataSource.postbindmobile(user_token, mobile, vcode);
    }

    @Override
    public Observable<BaseResponse> postSubmits(Map<String, RequestBody> map, List<MultipartBody.Part> parts) {
        return mHttpDataSource.postSubmits(map, parts);
    }

    @Override
    public Observable<BaseResponse> postAvatar(List<MultipartBody.Part> requestBodyMap) {
        return mHttpDataSource.postAvatar(requestBodyMap);
    }

    @Override
    public Observable<BaseResponse<RequestBody>> postAvatar(String user_token, RequestBody description, MultipartBody.Part file) {
        return mHttpDataSource.postAvatar(user_token, description, file);
    }

    @Override
    public Observable<BaseResponse> postGoodsEvaluatList(String gid, String level, int page, int pagesize) {
        return mHttpDataSource.postGoodsEvaluatList(gid, level, page, pagesize);
    }

    @Override
    public Observable<BaseResponse> postAppUpdate(String version) {
        return mHttpDataSource.postAppUpdate(version);
    }

    @Override
    public Observable<BaseResponse<CouponCenterEntity>> postCouponCenter(String user_token, int page, int pagesize) {
        return mHttpDataSource.postCouponCenter(user_token, page, pagesize);
    }

    @Override
    public Observable<BaseResponse<MineOrderEntity>> postMyOrder(String usertoken, String status, int page, int pagesize) {
        return mHttpDataSource.postMyOrder(usertoken, status, page, pagesize);
    }

    @Override
    public Observable<BaseResponse<TuiguangEntity>> postMyTui(String token) {
        return mHttpDataSource.postMyTui(token);
    }

    @Override
    public Observable<BaseResponse<MerchEntity>> postMerch(String merch) {
        return mHttpDataSource.postMerch(merch);
    }

    @Override
    public Observable<BaseResponse<PosterNewEntity>> postPosterData(String user_token) {
        return mHttpDataSource.postPosterData(user_token);
    }

    @Override
    public Observable<BaseResponse> postCancleOrder(String usertoken, String order_id) {
        return mHttpDataSource.postCancleOrder(usertoken, order_id);
    }

    @Override
    public Observable<BaseResponse> postConfirmGoods(String usertoken, String order_id) {
        return mHttpDataSource.postConfirmGoods(usertoken, order_id);
    }

    @Override
    public Observable<BaseResponse> postDeleteOrder(String usertoken, String order) {
        return mHttpDataSource.postDeleteOrder(usertoken, order);
    }

    @Override
    public Observable<BaseResponse> postShowCommentData(String usertoken, String order) {
        return mHttpDataSource.postShowCommentData(usertoken, order);
    }

    @Override
    public Observable<BaseResponse> postSubmitCommentData(String usertoken, String order) {
        return mHttpDataSource.postSubmitCommentData(usertoken, order);
    }

    @Override
    public Observable<BaseResponse<LogisticListEntity>> postLogistic(String user_token, String order) {
        return mHttpDataSource.postLogistic(user_token, order);
    }

    @Override
    public Observable<BaseResponse<OrderRefundEntity>> postOrderRefund(String user_token, String order) {
        return mHttpDataSource.postOrderRefund(user_token, order);
    }

    @Override
    public Observable<BaseResponse<LogisticInfoEntity>> postLogisticDetails(String user, String order, String sendtype, String bundle) {
        return mHttpDataSource.postLogisticDetails(user, order, sendtype, bundle);
    }

    @Override
    public Observable<BaseResponse<CancelRefundEntity>> postCancelRefundOrder(String user, String order, String refund_id) {
        return mHttpDataSource.postCancelRefundOrder(user, order, refund_id);
    }

    @Override
    public Observable<BaseResponse<OrderRefundProgressEntity>> postOrderRefundProgress(String user_token, String order) {
        return mHttpDataSource.postOrderRefundProgress(user_token, order);
    }

    @Override
    public Observable<BaseResponse> postSubmitExpress(String user_token, String order, String refund_id, String express, String expresscom, String expresssn) {
        return mHttpDataSource.postSubmitExpress(user_token, order, refund_id, express, expresscom, expresssn);
    }

    @Override
    public Observable<BaseResponse> postExchangeLogistics(String user_token, String order, String refund_id) {
        return mHttpDataSource.postExchangeLogistics(user_token, order, refund_id);
    }

    @Override
    public Observable<BaseResponse> postRefundReceive(String user_token, String order, String refund_id) {
        return mHttpDataSource.postRefundReceive(user_token, order, refund_id);
    }

    @Override
    public Observable<BaseResponse<OrderToPayEntity>> postRecharge(String usertoken, String money, String couponid, String pay_type) {
        return mHttpDataSource.postRecharge(usertoken, money, couponid, pay_type);
    }

    @Override
    public Observable<BaseResponse<AssetsRecordEntity>> postAssetsRecord(String user_token, int status, int page) {
        return mHttpDataSource.postAssetsRecord(user_token, status, page);
    }

    @Override
    public Observable<BaseResponse<AssetsRecordEntity>> postSignRanking(String user_token, String type, int page, int pagesize) {
        return mHttpDataSource.postSignRanking(user_token,type,page,pagesize);
    }


    @Override
    public Observable<BaseResponse<LocalLifeEntity>> postLocalLife(String user_token, int status) {
        return mHttpDataSource.postLocalLife(user_token, status);
    }

    @Override
    public Observable<BaseResponse<BonuswithdrawEntity>> postBonuswithdraw(String usertoken) {
        return mHttpDataSource.postBonuswithdraw(usertoken);
    }

    @Override
    public Observable<BaseResponse<BalanceDataEntity>> postGetwithDraw(String usertoken) {
        return mHttpDataSource.postGetwithDraw(usertoken);
    }

    @Override
    public Observable<BaseResponse> postBalanceSubmit(String token, String money, String applytype, String name, String account, String bank_name) {
        return mHttpDataSource.postBalanceSubmit(token, money, applytype, name, account, bank_name);
    }

    @Override
    public Observable<BaseResponse<MessageCenterEntity>> postMessageData(String token,int page,int pagesize) {
        return mHttpDataSource.postMessageData(token,page,pagesize);
    }

    @Override
    public Observable<BaseResponse<MessageCenterChatEntity>> postMessageChatData(String token, int page, int pagesize) {
        return mHttpDataSource.postMessageChatData(token, page, pagesize);
    }

    @Override
    public Observable<BaseResponse> clearAllMessage(String token) {
        return mHttpDataSource.clearAllMessage(token);
    }

    @Override
    public Observable<BaseResponse> postReadMessage(String token, String id) {
        return mHttpDataSource.postReadMessage(token, id);
    }

    @Override
    public Observable<BaseResponse> postMineShop(String usertoken) {
        return mHttpDataSource.postMineShop(usertoken);
    }


    @Override
    public Observable<BaseResponse<MessageMerchEntity>> postMessageMerchData(String token, String order_id, String type, String refund_id) {
        return mHttpDataSource.postMessageMerchData(token, order_id, type, refund_id);
    }

    @Override
    public Observable<BaseResponse<RechargeRecordEntity>> postSignRecord(String user_token, int page, int pagesize) {
        return mHttpDataSource.postSignRecord(user_token, page, pagesize);
    }

    @Override
    public Observable<BaseResponse<SignEntity>> postGetSign(String user_token) {
        return mHttpDataSource.postGetSign(user_token);
    }

    @Override
    public Observable<BaseResponse<ToSignEntity>> postToSign(String user_token, String date) {
        return mHttpDataSource.postToSign(user_token, date);
    }

    @Override
    public Observable<BaseResponse<SignDateEntity>> postGetSignDate(String user_token, String date) {
        return mHttpDataSource.postGetSignDate(user_token, date);
    }

    @Override
    public Observable<BaseResponse<SignReceiveEntity>> postToReceive(String user_token, String type, String data) {
        return mHttpDataSource.postToReceive(user_token, type, data);
    }

    @Override
    public Observable<BaseResponse<MineStoreEntity>> postMineShopHome(String usertoken, String shopid, String keywords, int page) {
        return mHttpDataSource.postMineShopHome(usertoken,shopid,keywords,page);
    }

    @Override
    public Observable<BaseResponse> postMineSetShop(String usertoken) {
        return mHttpDataSource.postMineSetShop(usertoken);
    }

    @Override
    public Observable<BaseResponse<PromotCommissionEntity>> postPromotCommis(String user_token) {
        return mHttpDataSource.postPromotCommis(user_token);
    }

    @Override
    public Observable<BaseResponse<PromotionListEntity>> postPromotOrder(String user_token) {
        return mHttpDataSource.postPromotOrder(user_token);
    }

    @Override
    public Observable<BaseResponse<AttractInvestmentEntity>> postBusiness(String user_token) {
        return mHttpDataSource.postBusiness(user_token);
    }

    @Override
    public Observable<BaseResponse<InCommisWithdrawEntity>> postInCommisWithdraw(String user_token) {
        return mHttpDataSource.postInCommisWithdraw(user_token);
    }


    @Override
    public Observable<BaseResponse<CommisWithdraRecordEntity>> postCommisWithdraRecord(String user_token,String type,int page) {
        return mHttpDataSource.postCommisWithdraRecord(user_token,type,page);
    }

    @Override
    public Observable<BaseResponse<MineTeamEntity>> postCommissionDown(String usertoken, int page) {
        return mHttpDataSource.postCommissionDown(usertoken,page);
    }

    @Override
    public Observable<BaseResponse<CreditsStoreEntity>> postCreditShop(String user_token, String merch_id) {
        return mHttpDataSource.postCreditShop(user_token, merch_id);
    }

    @Override
    public Observable<BaseResponse<CreditsAllGoodsEntity>> postCreditAllGoods(String user_token, String cate_id, String keywords, String merch_id, int page) {
        return mHttpDataSource.postCreditAllGoods(user_token, cate_id, keywords, merch_id, page);
    }

    @Override
    public Observable<BaseResponse<CreditExchangeRecoEntity>> postCreditExchangeRecod(String user_token, String merch_id, int status, int page) {
        return mHttpDataSource.postCreditExchangeRecod(user_token, merch_id, status, page);
    }

    @Override
    public Observable<BaseResponse<PartakeRecordEntity>> postPartakeRecord(String user_token, String merch_id, int page, int pagesize) {
        return mHttpDataSource.postPartakeRecord(user_token, merch_id, page, pagesize);
    }

    @Override
    public Observable<BaseResponse<CreditGoodsDetailsEntity>> postCreditDetail(String usertoken, String gid, String merchid) {
        return mHttpDataSource.postCreditDetail(usertoken,gid,merchid);
    }

    @Override
    public Observable<BaseResponse<CreditDetailsLogsEntity>> postCreditDetailsLogs(String gid, int page, int pagesize) {
        return mHttpDataSource.postCreditDetailsLogs(gid,page,pagesize);
    }

    @Override
    public Observable<BaseResponse<CreditOrderDetailEntity>> postCreditOrderDetailData(String usertoken, String log_id,String merch_id) {
        return mHttpDataSource.postCreditOrderDetailData(usertoken, log_id,merch_id);
    }

    @Override
    public Observable<BaseResponse<CreditsConfirmOrderEntity>> postCreditConfirmOrder(String user_token, String addr_id, String gid, String optionid, String merch_id) {
        return mHttpDataSource.postCreditConfirmOrder(user_token, addr_id, gid, optionid, merch_id);
    }

    @Override
    public Observable<BaseResponse<ExchageGoodsNumEntity>> requestExchageGoodsNum(String user_token, String gid, String optionid, String addr_id, String num, String merch_id) {
        return mHttpDataSource.requestExchageGoodsNum(user_token, gid, optionid, addr_id, num, merch_id);
    }

    @Override
    public Observable<BaseResponse<CreditToSureReceiptEntity>> postToSureReceipt(String usertoken, String log_id, String merch_id) {
        return mHttpDataSource.postToSureReceipt(usertoken, log_id, merch_id);
    }

    @Override
    public Observable<BaseResponse<CreditGetRedBagsEntity>> postGetRedBags(String usertoken, String log_id, String merch_id) {
        return mHttpDataSource.postGetRedBags(usertoken, log_id, merch_id);
    }

    @Override
    public Observable<BaseResponse<OrderToPayEntity>> postCreditTopay(String usertoken, String paytype, String gid, int option, String addid, int num) {
        return mHttpDataSource.postCreditTopay(usertoken,paytype,gid,option,addid,num);
    }

    @Override
    public Observable<BaseResponse<CreditExpressEntity>> postCreditExpress(String usertoken, String logid, String merchid) {
        return mHttpDataSource.postCreditExpress(usertoken,logid,merchid);
    }

    @Override
    public Observable<BaseResponse<LotteryEntity>> postCreditLottery(String usertoken) {
        return mHttpDataSource.postCreditLottery(usertoken);
    }

    @Override
    public Observable<BaseResponse<CreditCommentEntity>> postShowCreditComment(String usertoken, String log_id, String merch_id) {
        return mHttpDataSource.postShowCreditComment(usertoken, log_id, merch_id);
    }

    @Override
    public Observable<BaseResponse<HomeGoodsEntity>> postHomeGoodsList(int page, int pagesize) {
        return mHttpDataSource.postHomeGoodsList(page, pagesize);
    }

    @Override
    public Observable<BaseResponse<CreditPayEntity>> postCreditPay(String usertoken, String gid, String option, String addr_id, int num, String merch_id) {
        return mHttpDataSource.postCreditPay(usertoken,gid,option,addr_id,num,merch_id);
    }

    @Override
    public Observable<BaseResponse<WithdrawApplyEntity>> postGetWithdrawData(String user_token) {
        return mHttpDataSource.postGetWithdrawData(user_token);
    }

    @Override
    public Observable<BaseResponse> postWithDraw(String with) {
        return mHttpDataSource.postWithDraw(with);
    }

    @Override
    public Observable<BaseResponse<AwardListEntity>> postAwardList(String user_token, int status) {
        return mHttpDataSource.postAwardList(user_token, status);
    }

    @Override
    public Observable<BaseResponse<PromotionOrderEntity>> postPromotionOrder(String user_token, int status) {
        return mHttpDataSource.postPromotionOrder(user_token, status);
    }

    @Override
    public Observable<BaseResponse<AwarddetailEntity>> postAwardDetail(String user_token, String log_id, int page) {
        return mHttpDataSource.postAwardDetail(user_token, log_id, page);
    }

    @Override
    public Observable<BaseResponse<RechargeRecordEntity>> postRechargeRecord(String user_token, int page, int pagesize) {
        return mHttpDataSource.postRechargeRecord(user_token, page, pagesize);
    }

    @Override
    public Observable<BaseResponse<WithdrawalsRecordEntity>> postWithdrawalsRecord(String user_token, int page, int pagesize) {
        return mHttpDataSource.postWithdrawalsRecord(user_token, page, pagesize);
    }

    @Override
    public Observable<BaseResponse<AssetsEntity>> postAssetsData(String user_token) {
        return mHttpDataSource.postAssetsData(user_token);
    }

    @Override
    public Observable<BaseResponse<OrderDetailEntity>> postOrderDetailData(String usertoken, String order_id) {
        return mHttpDataSource.postOrderDetailData(usertoken, order_id);
    }

/*    @Override
    public Observable<BaseResponse> postSubmits(RequestBody body) {
        return mHttpDataSource.postSubmits(body);
    }*/


    @Override
    public void saveUserName(String userName) {
        mLocalDataSource.saveUserName(userName);
    }

    @Override
    public void savePassword(String password) {
        mLocalDataSource.savePassword(password);
    }

    @Override
    public String getUserName() {
        return mLocalDataSource.getUserName();
    }

    @Override
    public String getPassword() {
        return mLocalDataSource.getPassword();
    }

    @Override
    public String getUserToken() {
        return mLocalDataSource.getUserToken();
    }

    @Override
    public void saveUserToken(String token) {
        mLocalDataSource.saveUserToken(token);
    }

    @Override
    public WXUserInfo getWXUserInfo() {
        return mLocalDataSource.getWXUserInfo();
    }

    @Override
    public void saveWXUserInfo(String info) {
        mLocalDataSource.saveWXUserInfo(info);
    }

    @Override
    public void saveShopCarnumber(int number) {
        mLocalDataSource.saveShopCarnumber(number);
    }

    @Override
    public int getShopCarnumber() {
        return mLocalDataSource.getShopCarnumber();
    }

    @Override
    public void saveShopID(String shopid) {
        mLocalDataSource.saveShopID(shopid);
    }

    @Override
    public String getShopID() {
        return mLocalDataSource.getShopID();
    }

    @Override
    public void saveOptionID(String optionid) {
        mLocalDataSource.saveOptionID(optionid);
    }

    @Override
    public String getOptionID() {
        return mLocalDataSource.getOptionID();
    }

    @Override
    public void saveGoodsNumber(String number) {
        mLocalDataSource.saveGoodsNumber(number);
    }

    @Override
    public String getGoodsNumber() {
        return mLocalDataSource.getGoodsNumber();
    }

    @Override
    public void saveGiftID(String giftid) {
        mLocalDataSource.saveGiftID(giftid);
    }

    @Override
    public String getGIftID() {
        return mLocalDataSource.getGIftID();
    }

    @Override
    public void saveAddress(String add_id) {
        mLocalDataSource.saveAddress(add_id);
    }

    @Override
    public String getAddress() {
        return mLocalDataSource.getAddress();
    }

    @Override
    public void saveUserInfo(UserInfo info) {
        mLocalDataSource.saveUserInfo(info);
    }

    @Override
    public UserInfo getUserInfo() {
        return mLocalDataSource.getUserInfo();
    }

    @Override
    public void saveLocationCity(String city) {
        mLocalDataSource.saveLocationCity(city);
    }

    @Override
    public String getLoacationCity() {
        return mLocalDataSource.getLoacationCity();
    }

    @Override
    public void saveUserMessage(int messgae) {
        mLocalDataSource.saveUserMessage(messgae);
    }

    @Override
    public int getUserMessage() {
        return mLocalDataSource.getUserMessage();
    }

    @Override
    public void saveMemberlook(int status) {
        mLocalDataSource.saveMemberlook(status);
    }

    @Override
    public int getMemberLook() {
        return mLocalDataSource.getMemberLook();
    }

    @Override
    public void saveOpenFlag(String openFlag) {
        mLocalDataSource.saveOpenFlag(openFlag);
    }

    @Override
    public String getOpenFlag() {
        return mLocalDataSource.getOpenFlag();
    }

}