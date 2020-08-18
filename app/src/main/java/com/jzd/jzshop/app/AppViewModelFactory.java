package com.jzd.jzshop.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.jzd.jzshop.chat.OpenChatViewModel;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.ui.base.viewmodel.BaseWebviewViewModel;
import com.jzd.jzshop.ui.MoreProductJumpLinkViewModel;
import com.jzd.jzshop.ui.SplashViewModel;
import com.jzd.jzshop.ui.category.CategoryViewModel;
import com.jzd.jzshop.ui.goods.GoodsViewModel;
import com.jzd.jzshop.ui.goods.evaluate.GoodsEvaluationViewModel;
import com.jzd.jzshop.ui.goods.goodslist.GoodsListViewModel;
import com.jzd.jzshop.ui.home.HomeViewModel;
import com.jzd.jzshop.ui.home.creditsstore.PartakeRecordViewModel;
import com.jzd.jzshop.ui.home.creditsstore.all_shop.AllCreditGoodsViewModel;
import com.jzd.jzshop.ui.home.creditsstore.comment.CreditCommentViewModel;
import com.jzd.jzshop.ui.home.creditsstore.confirm_order.CreditConfirmOrderViewModel;
import com.jzd.jzshop.ui.home.creditsstore.express.CreditShopExpressViewModel;
import com.jzd.jzshop.ui.home.creditsstore.goods_details.CreditGoodsDetailsViewModel;
import com.jzd.jzshop.ui.home.creditsstore.goods_details.details.CreditDetailsWebViewModel;
import com.jzd.jzshop.ui.home.creditsstore.goods_details.details.CreditShopLogViewModel;
import com.jzd.jzshop.ui.home.creditsstore.home.CreditsStoreViewModel;
import com.jzd.jzshop.ui.home.creditsstore.lottery.CreditLotteryViewModel;
import com.jzd.jzshop.ui.home.creditsstore.mine.CreditExchangeRecoViewModel;
import com.jzd.jzshop.ui.home.creditsstore.mine.CreditsMineViewModel;
import com.jzd.jzshop.ui.home.creditsstore.order_detail.CreditOrderDetailViewModel;
import com.jzd.jzshop.ui.home.creditsstore.pay.CreditPaySuccessViewModel;
import com.jzd.jzshop.ui.home.creditsstore.pay.CreditPayViewModel;
import com.jzd.jzshop.ui.home.invite_friends.InviteFriendsViewModel;
import com.jzd.jzshop.ui.home.local_life.LocalLifeViewModel;
import com.jzd.jzshop.ui.home.local_life.OfflineFoodFragViewModel;
import com.jzd.jzshop.ui.home.local_life.location.ChoiceCityViewModel;
import com.jzd.jzshop.ui.home.local_life.search.SearchKeyWordViewModel;
import com.jzd.jzshop.ui.home.merchantalliance.MerchantAllianceViewModel;
import com.jzd.jzshop.ui.home.news.HomePageViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyViewModel;
import com.jzd.jzshop.ui.login.bindmobile.BindMobileBViewModel;
import com.jzd.jzshop.ui.merch_alliance.ShopViewModel;
import com.jzd.jzshop.ui.message.MessageCenterViewModel;
import com.jzd.jzshop.ui.message.MessageMerchViewModel;
import com.jzd.jzshop.ui.mine.MyViewModel;
import com.jzd.jzshop.ui.mine.address.CompileAddressViewModel;
import com.jzd.jzshop.ui.mine.address.ReceiptAddressViewModel;
import com.jzd.jzshop.ui.mine.assets.AssetsIntroductionViewModel;
import com.jzd.jzshop.ui.mine.assets.AssetsRecoAllFragmViewModel;
import com.jzd.jzshop.ui.mine.assets.AssetsRecordViewModel;
import com.jzd.jzshop.ui.mine.assets.AssetsViewModel;
import com.jzd.jzshop.ui.mine.balance.MyBalanceViewModel;
import com.jzd.jzshop.ui.mine.collect.MineCollectViewModel;
import com.jzd.jzshop.ui.mine.coupon.MineCouponViewModel;
import com.jzd.jzshop.ui.mine.couponcenter.CouponCenterViewModel;
import com.jzd.jzshop.ui.mine.creditsign.CreditSignNewViewModel;
import com.jzd.jzshop.ui.mine.creditsign.SignRecordViewModel;
import com.jzd.jzshop.ui.mine.creditsign.TimeSelectViewModel;
import com.jzd.jzshop.ui.mine.creditsign.ranking.ContinuousSignInViewModel;
import com.jzd.jzshop.ui.mine.creditsign.ranking.SignRankingViewModel;
import com.jzd.jzshop.ui.mine.creditsign.shop.CreditShopViewModel;
import com.jzd.jzshop.ui.mine.feedback.FeedBackSuccessViewModel;
import com.jzd.jzshop.ui.mine.feedback.FeedBackViewModel;
import com.jzd.jzshop.ui.mine.merch.MerchFinallyViewModel;
import com.jzd.jzshop.ui.mine.merch.MerchNextViewModel;
import com.jzd.jzshop.ui.mine.merch.MerchSuccessViewModel;
import com.jzd.jzshop.ui.mine.merch.MerchViewModel;
import com.jzd.jzshop.ui.mine.merch.MerchantEntryViewModel;
import com.jzd.jzshop.ui.mine.mineshop.MineShopViewModel;
import com.jzd.jzshop.ui.mine.mineshop.regist.MineShopRegistViewModel;
import com.jzd.jzshop.ui.mine.mineshop.shophome.MineSelectViewModel;
import com.jzd.jzshop.ui.mine.mineshop.shophome.MineShopHomeViewModel;
import com.jzd.jzshop.ui.mine.mineteam.MineTeamViewModel;
import com.jzd.jzshop.ui.mine.news.MyPageViewModel;
import com.jzd.jzshop.ui.mine.promotion.PromotCommissionViewModel;
import com.jzd.jzshop.ui.mine.promotion.PromotionViewModel;
import com.jzd.jzshop.ui.mine.promotion.award_list.AwardAllFragmViewModel;
import com.jzd.jzshop.ui.mine.promotion.award_list.AwardListViewModel;
import com.jzd.jzshop.ui.mine.promotion.awarddetail.AwardDerailViewModel;
import com.jzd.jzshop.ui.mine.promotion.business.AttractInvestmentViewModel;
import com.jzd.jzshop.ui.mine.promotion.promotion_order.PromotOrderAllFragmViewModel;
import com.jzd.jzshop.ui.mine.promotion.promotion_order.PromotionOrderNewViewModel;
import com.jzd.jzshop.ui.mine.promotion.promotion_order.PromotionOrderViewModel;
import com.jzd.jzshop.ui.mine.promotion.withdrawal.CommissiWithdrawRecordViewModel;
import com.jzd.jzshop.ui.mine.promotion.withdrawal.CommissionWithdrawalViewModel;
import com.jzd.jzshop.ui.mine.promotion.withdrawal.InCommisWithdrawViewModel;
import com.jzd.jzshop.ui.mine.promotion.withdrawal.WithdrawApplyViewModel;
import com.jzd.jzshop.ui.mine.recharge.RechargeRecordViewModel;
import com.jzd.jzshop.ui.mine.recharge.RechargeSuccessViewModel;
import com.jzd.jzshop.ui.mine.recharge.RechargeViewModel;
import com.jzd.jzshop.ui.mine.setting.SetViewModel;
import com.jzd.jzshop.ui.mine.setting.aboutapp.AboutappViewModel;
import com.jzd.jzshop.ui.mine.setting.perfectdata.complete.CompleteViewModel;
import com.jzd.jzshop.ui.mine.withdrawals.BalabceViewModel;
import com.jzd.jzshop.ui.mine.withdrawals.BalanceApplyViewModel;
import com.jzd.jzshop.ui.mine.withdrawals.BonusViewModel;
import com.jzd.jzshop.ui.mine.withdrawals.WithdrawalsRecordViewModel;
import com.jzd.jzshop.ui.order.aftersale.AfterSalePlanViewModel;
import com.jzd.jzshop.ui.order.aftersale.AfterSaleViewModel;
import com.jzd.jzshop.ui.order.aftersale.FillExpressBillViewModel;
import com.jzd.jzshop.ui.order.aftersale.SalesApplyProgressViewModel;
import com.jzd.jzshop.ui.order.comment.CommentViewModel;
import com.jzd.jzshop.ui.order.firmorder.FirmOrderViewModel;
import com.jzd.jzshop.ui.order.logistics.LogisticInfoViewModel;
import com.jzd.jzshop.ui.order.logistics.LogisticsViewModel;
import com.jzd.jzshop.ui.order.mineorder.MineOrderViewModel;
import com.jzd.jzshop.ui.order.orderdetail.OrderDetailViewModel;
import com.jzd.jzshop.ui.pay.PaySuccessViewModel;
import com.jzd.jzshop.ui.pay.PayViewModel;
import com.jzd.jzshop.ui.shoppingcar.ShoppingCarViewModel;
import com.jzd.jzshop.ui.website.WebSiteViewModel;

/**
 * Created by jzd on 2019/3/26.
 */
public class AppViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile AppViewModelFactory INSTANCE;
    private final Application mApplication;
    private final Repository mRepository;

    public static AppViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (AppViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppViewModelFactory(application, Injection.provideDemoRepository());
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private AppViewModelFactory(Application application, Repository repository) {
        this.mApplication = application;
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MyViewModel.class)) {
            return (T) new MyViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(ShoppingCarViewModel.class)) {
            return (T) new ShoppingCarViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CategoryViewModel.class)) {
            return (T) new CategoryViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(GoodsViewModel.class)) {
            return (T) new GoodsViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(FirmOrderViewModel.class)) {
            return (T) new FirmOrderViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(ReceiptAddressViewModel.class)) {
            return (T) new ReceiptAddressViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CompileAddressViewModel.class)) {
            return (T) new CompileAddressViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MineOrderViewModel.class)) {
            return (T) new MineOrderViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(PromotionViewModel.class)) {
            return (T) new PromotionViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(PaySuccessViewModel.class)) {
            return (T) new PaySuccessViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(BalabceViewModel.class)) {
            return (T) new BalabceViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(BonusViewModel.class)) {
            return (T) new BonusViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(PayViewModel.class)) {
            return (T) new PayViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MineCollectViewModel.class)) {
            return (T) new MineCollectViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CreditsStoreViewModel.class)) {
            return (T) new CreditsStoreViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MineCouponViewModel.class)) {
            return (T) new MineCouponViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CouponCenterViewModel.class)) {
            return (T) new CouponCenterViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MerchViewModel.class)) {
            return (T) new MerchViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(GoodsListViewModel.class)) {
            return (T) new GoodsListViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(ShopViewModel.class)) {
            return (T) new ShopViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(BaseWebviewViewModel.class)) {
            return (T) new BaseWebviewViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MerchantAllianceViewModel.class)) {
            return (T) new MerchantAllianceViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(LoginVertifyViewModel.class)) {
            return (T) new LoginVertifyViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(SetViewModel.class)) {
            return (T) new SetViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MoreProductJumpLinkViewModel.class)) {
            return (T) new MoreProductJumpLinkViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(BaseWebviewViewModel.class)) {
            return (T) new BaseWebviewViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(BindMobileBViewModel.class)) {
            return (T) new BindMobileBViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MerchantEntryViewModel.class)) {
            return (T) new MerchantEntryViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(GoodsEvaluationViewModel.class)) {
            return (T) new GoodsEvaluationViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(AboutappViewModel.class)) {
            return (T) new AboutappViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(HomePageViewModel.class)) {
            return (T) new HomePageViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MerchNextViewModel.class)) {
            return (T) new MerchNextViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MerchFinallyViewModel.class)) {
            return (T) new MerchFinallyViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(AwardDerailViewModel.class)) {
            return (T) new AwardDerailViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(InviteFriendsViewModel.class)) {
            return (T) new InviteFriendsViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(OrderDetailViewModel.class)) {
            return (T) new OrderDetailViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(AfterSaleViewModel.class)) {
            return (T) new AfterSaleViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(AfterSalePlanViewModel.class)) {
            return (T) new AfterSalePlanViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CommentViewModel.class)) {
            return (T) new CommentViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(SalesApplyProgressViewModel.class)) {
            return (T) new SalesApplyProgressViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(LogisticsViewModel.class)) {
            return (T) new LogisticsViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(LogisticInfoViewModel.class)) {
            return (T) new LogisticInfoViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(FillExpressBillViewModel.class)) {
            return (T) new FillExpressBillViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MyPageViewModel.class)) {
            return (T) new MyPageViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(RechargeViewModel.class)) {
            return (T) new RechargeViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MyBalanceViewModel.class)) {
            return (T) new MyBalanceViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(FeedBackViewModel.class)) {
            return (T) new FeedBackViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(RechargeRecordViewModel.class)) {
            return (T) new RechargeRecordViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(WithdrawalsRecordViewModel.class)) {
            return (T) new WithdrawalsRecordViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(AssetsViewModel.class)) {
            return (T) new AssetsViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(AssetsIntroductionViewModel.class)) {
            return (T) new AssetsIntroductionViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(AssetsRecordViewModel.class)) {
            return (T) new AssetsRecordViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(AssetsRecoAllFragmViewModel.class)) {
            return (T) new AssetsRecoAllFragmViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(LocalLifeViewModel.class)) {
            return (T) new LocalLifeViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(OfflineFoodFragViewModel.class)) {
            return (T) new OfflineFoodFragViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(RechargeSuccessViewModel.class)) {
            return (T) new RechargeSuccessViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(FeedBackSuccessViewModel.class)) {
            return (T) new FeedBackSuccessViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(BalanceApplyViewModel.class)) {
            return (T) new BalanceApplyViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(AwardListViewModel.class)) {
            return (T) new AwardListViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(AwardAllFragmViewModel.class)) {
            return (T) new AwardAllFragmViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(PromotionOrderViewModel.class)) {
            return (T) new PromotionOrderViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(PromotOrderAllFragmViewModel.class)) {
            return (T) new PromotOrderAllFragmViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(SearchKeyWordViewModel.class)) {
            return (T) new SearchKeyWordViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(ChoiceCityViewModel.class)) {
            return (T) new ChoiceCityViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(WebSiteViewModel.class)) {
            return (T) new WebSiteViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MerchSuccessViewModel.class)) {
            return (T) new MerchSuccessViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) new SplashViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CompleteViewModel.class)) {
            return (T) new CompleteViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MessageCenterViewModel.class)) {
            return (T) new MessageCenterViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MineShopViewModel.class)) {
            return (T) new MineShopViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MessageMerchViewModel.class)) {
            return (T) new MessageMerchViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(SignRecordViewModel.class)) {
            return (T) new SignRecordViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MineShopHomeViewModel.class)) {
            return (T) new MineShopHomeViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MineShopRegistViewModel.class)) {
            return (T) new MineShopRegistViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(SignRankingViewModel.class)) {
            return (T) new SignRankingViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(ContinuousSignInViewModel.class)) {
            return (T) new ContinuousSignInViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(SignRecordViewModel.class)) {
            return (T) new SignRecordViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CreditSignNewViewModel.class)) {
            return (T) new CreditSignNewViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(TimeSelectViewModel.class)) {
            return (T) new TimeSelectViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CreditShopViewModel.class)) {
            return (T) new CreditShopViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(PromotCommissionViewModel.class)) {
            return (T) new PromotCommissionViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CommissionWithdrawalViewModel.class)) {
            return (T) new CommissionWithdrawalViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CommissiWithdrawRecordViewModel.class)) {
            return (T) new CommissiWithdrawRecordViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(InCommisWithdrawViewModel.class)) {
            return (T) new InCommisWithdrawViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(AttractInvestmentViewModel.class)) {
            return (T) new AttractInvestmentViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(PromotionOrderNewViewModel.class)) {
            return (T) new PromotionOrderNewViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(WithdrawApplyViewModel.class)) {
            return (T) new WithdrawApplyViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MineSelectViewModel.class)) {
            return (T) new MineSelectViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(OpenChatViewModel.class)) {
            return (T) new OpenChatViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(MineTeamViewModel.class)) {
            return (T) new MineTeamViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CreditsStoreViewModel.class)) {
            return (T) new CreditsStoreViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(AllCreditGoodsViewModel.class)) {
            return (T) new AllCreditGoodsViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CreditsMineViewModel.class)) {
            return (T) new CreditsMineViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CreditExchangeRecoViewModel.class)) {
            return (T) new CreditExchangeRecoViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(PartakeRecordViewModel.class)) {
            return (T) new PartakeRecordViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CreditGoodsDetailsViewModel.class)) {
            return (T) new CreditGoodsDetailsViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CreditOrderDetailViewModel.class)) {
            return (T) new CreditOrderDetailViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CreditConfirmOrderViewModel.class)) {
            return (T) new CreditConfirmOrderViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CreditPayViewModel.class)) {
            return (T) new CreditPayViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CreditShopExpressViewModel.class)) {
            return (T) new CreditShopExpressViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CreditCommentViewModel.class)) {
            return (T) new CreditCommentViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CreditPaySuccessViewModel.class)) {
            return (T) new CreditPaySuccessViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CreditLotteryViewModel.class)) {
            return (T) new CreditLotteryViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CreditDetailsWebViewModel.class)) {
            return (T) new CreditDetailsWebViewModel(mApplication, mRepository);
        } else if (modelClass.isAssignableFrom(CreditShopLogViewModel.class)) {
            return (T) new CreditShopLogViewModel(mApplication, mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
