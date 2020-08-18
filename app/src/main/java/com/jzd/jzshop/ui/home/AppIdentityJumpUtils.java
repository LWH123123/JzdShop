package com.jzd.jzshop.ui.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;

import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.ui.category.CategoryActivity;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.ui.goods.ImageLookActivity;
import com.jzd.jzshop.ui.goods.goodslist.GoodsListFragment;
import com.jzd.jzshop.ui.home.creditsstore.PartakeRecordActivity;
import com.jzd.jzshop.ui.home.creditsstore.all_shop.AllCreditGoodsActivity;
import com.jzd.jzshop.ui.home.creditsstore.goods_details.CreditGoodsDetailsActivity;
import com.jzd.jzshop.ui.home.creditsstore.home.CreditsStoreActivity;
import com.jzd.jzshop.ui.home.creditsstore.mine.CreditsMineActivity;
import com.jzd.jzshop.ui.home.invite_friends.InviteFriendsActivity;
import com.jzd.jzshop.ui.home.local_life.LocalLifeAty;
import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.ui.home.news.HomePageViewModel;
import com.jzd.jzshop.ui.home.merchantalliance.MerchantAllianceFragment;
import com.jzd.jzshop.ui.home.merchantalliance.MerchantAllianceViewModel;
import com.jzd.jzshop.ui.base.fragment.BaseWebviewFragment;
import com.jzd.jzshop.ui.merch_alliance.ShopFragment;
import com.jzd.jzshop.ui.mine.creditsign.CreditSignNewActivity;
import com.jzd.jzshop.ui.mine.merch.MerchantEntryAty;
import com.jzd.jzshop.ui.mine.news.MyPageActivity;
import com.jzd.jzshop.ui.order.mineorder.MineOrderFragment;
import com.jzd.jzshop.ui.merch_alliance.ShopViewModel;
import com.jzd.jzshop.ui.shoppingcar.ShoppingCarActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.SystemUtils;
import com.jzd.jzshop.utils.UrlPreprocessingUtils;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.ThumbViewInfo;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description: app 标识跳转工具类
 * @date :2020/1/9 9:41
 */
public class AppIdentityJumpUtils {
    private static final String TAG = AppIdentityJumpUtils.class.getSimpleName();
    private static ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>(); // 组建大图预览数据基

    /**
     * 首页 menu   banner  skip
     *  @param linkurl
     * @param model
     * @param context
     */
    public static void homeMenujumpLinkUrl(String linkurl, BaseViewModel model, Context context) {
        if (model instanceof HomePageViewModel) {
            HomePageViewModel viewModel = (HomePageViewModel) model;
            if (!TextUtils.isEmpty(viewModel.getUserToken())) {
                Bundle bundle = new Bundle();
                mCommonMethod(linkurl, viewModel, bundle);
            } else {
                ToastUtils.showLong("请先登录");
                return;
            }
        } else if (model instanceof MerchantAllianceViewModel) {  //商家联盟 banner jump
            MerchantAllianceViewModel viewModel = (MerchantAllianceViewModel) model;
            Bundle bundle = new Bundle();
            if (linkurl != null) {
                if (linkurl != null && !TextUtils.isEmpty(linkurl) && (linkurl.contains("http://") || linkurl.contains("https://"))) {
                    if (linkurl.contains("=goods.detail")) {
                        String param = SystemUtils.URLRequest(linkurl).get("id");   //android 获取url中的参数
                        KLog.d(TAG, "id===" + param);
                        //跳转需要跳转的页面
                        bundle.putString(Constants.GOODS_KEY_GID, param);
                        viewModel.startActivity(GoodsActivity.class, bundle);
                    } else {
                        bundle.putString(Constants.BUNDLE_KEY_URL, linkurl + viewModel.getUserToken());
                        viewModel.startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
                    }
                } else {
                    int index = UrlPreprocessingUtils.interceptUrl(linkurl);
                    if (Constants.URL.APP_UI_SKIP_FLAG_FIRST == index) {
                        viewModel.startActivity(HomePageActivity.class);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_CATEGORY == index) {
                        viewModel.startActivity(CategoryActivity.class);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_GOODS == index) {

                    } else if (Constants.URL.APP_UI_SKIP_FLAG_NOTICE == index) {

                    } else if (Constants.URL.APP_UI_SKIP_FLAG_CART == index) {
                        viewModel.startActivity(ShoppingCarActivity.class);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_DIVIDEND == index) {

                    } else if (Constants.URL.APP_UI_SKIP_FLAG_EXCHANGE == index) {

                    } else if (Constants.URL.APP_UI_SKIP_FLAG_ABONUS == index) {

                    } else if (Constants.URL.APP_UI_SKIP_FLAG_MEMBER == index) {
                        viewModel.startActivity(MyPageActivity.class);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_APP_MERCH == index) {//商家联盟
                        viewModel.startContainerActivity(MerchantAllianceFragment.class.getCanonicalName());
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_ORDER == index){ //订单列表 全部
                        // TODO: 2020/1/20  全部 缺失跳转标识
                        bundle.putInt(Constants.MyOrder,0);
                        viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);

                    }else if (Constants.URL.APP_UI_SKIP_FLAG_STATUS0 == index){
                        bundle.putInt(Constants.MyOrder,0);
                        viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
                    }else if (Constants.URL.APP_UI_SKIP_FLAG_STATUS1 == index){
                        bundle.putInt(Constants.MyOrder,1);
                        viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
                    }else if (Constants.URL.APP_UI_SKIP_FLAG_STATUS2 == index){
                        bundle.putInt(Constants.MyOrder,2);
                        viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
                    }else if (Constants.URL.APP_UI_SKIP_FLAG_STATUS4 == index){
                        bundle.putInt(Constants.MyOrder,4);
                        viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
                    }else if (Constants.URL.APP_UI_SKIP_FLAG_STATUS3 == index){
                        bundle.putInt(Constants.MyOrder,3);
                        viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
                    }else if (Constants.URL.APP_UI_SKIP_FLAG_NEW == index) {//新品商品
                        mJumpShopList(viewModel);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_HOT == index) {//热卖商品
                        mJumpShopList(viewModel);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_DISCOUNT == index) {//促销商品
                        mJumpShopList(viewModel);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_SENDFREE == index) {//卖家包邮
                        mJumpShopList(viewModel);
                    } else {
                    }
                }
            }
        }else if (model instanceof ShopViewModel){  // 商品 店铺 banner jump
            ShopViewModel viewModel = (ShopViewModel) model;
            Bundle bundle = new Bundle();
            if (linkurl != null) {
                if (linkurl != null && !TextUtils.isEmpty(linkurl) && (linkurl.contains("http://") || linkurl.contains("https://"))) {
                    if (linkurl.contains("=goods.detail")) {
                        String param = SystemUtils.URLRequest(linkurl).get("id");   //android 获取url中的参数
                        KLog.d(TAG, "id===" + param);
                        //跳转需要跳转的页面
                        bundle.putString(Constants.GOODS_KEY_GID, param);
                        viewModel.startActivity(GoodsActivity.class, bundle);
                    } else {
                        bundle.putString(Constants.BUNDLE_KEY_URL, linkurl + viewModel.getUserToken());
                        viewModel.startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
                    }
                } else {
                    int index = UrlPreprocessingUtils.interceptUrl(linkurl);
                    if (Constants.URL.APP_UI_SKIP_FLAG_FIRST == index) {
                        viewModel.startActivity(HomePageActivity.class);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_CATEGORY == index) {
                        viewModel.startActivity(CategoryActivity.class);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_GOODS == index) {

                    } else if (Constants.URL.APP_UI_SKIP_FLAG_NOTICE == index) {

                    } else if (Constants.URL.APP_UI_SKIP_FLAG_CART == index) {
                        viewModel.startActivity(ShoppingCarActivity.class);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_DIVIDEND == index) {

                    } else if (Constants.URL.APP_UI_SKIP_FLAG_EXCHANGE == index) {

                    } else if (Constants.URL.APP_UI_SKIP_FLAG_ABONUS == index) {

                    } else if (Constants.URL.APP_UI_SKIP_FLAG_MEMBER == index) {
                        viewModel.startActivity(MyPageActivity.class);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_APP_MERCH == index) {//商家联盟
                        viewModel.startContainerActivity(MerchantAllianceFragment.class.getCanonicalName());
                    }else if (Constants.URL.APP_UI_SKIP_FLAG_ORDER == index){ //订单列表 全部
                        // TODO: 2020/1/20  全部 缺失跳转标识
                        bundle.putInt(Constants.MyOrder,0);
                        viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);

                    }else if (Constants.URL.APP_UI_SKIP_FLAG_STATUS0 == index){
                        bundle.putInt(Constants.MyOrder,0);
                        viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
                    }else if (Constants.URL.APP_UI_SKIP_FLAG_STATUS1 == index){
                        bundle.putInt(Constants.MyOrder,1);
                        viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
                    }else if (Constants.URL.APP_UI_SKIP_FLAG_STATUS2 == index){
                        bundle.putInt(Constants.MyOrder,2);
                        viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
                    }else if (Constants.URL.APP_UI_SKIP_FLAG_STATUS4 == index){
                        bundle.putInt(Constants.MyOrder,4);
                        viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
                    }else if (Constants.URL.APP_UI_SKIP_FLAG_STATUS3 == index){
                        bundle.putInt(Constants.MyOrder,3);
                        viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_NEW == index) {//新品商品
                        mJumpShopList(viewModel);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_HOT == index) {//热卖商品
                        mJumpShopList(viewModel);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_DISCOUNT == index) {//促销商品
                        mJumpShopList(viewModel);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_SENDFREE == index) {//卖家包邮
                        mJumpShopList(viewModel);
                    } else if (Constants.URL.APP_UI_SKIP_FLAG_TIME == index) {//限时抢购
                        mJumpShopList(viewModel);
                    } else {
                    }
                }
            }
        }
        else {

        }
    }

    private static void mCommonMethod(String linkurl, HomePageViewModel viewModel, Bundle bundle) {
        if (linkurl != null) {
            if (linkurl != null && !TextUtils.isEmpty(linkurl) && (linkurl.contains("http://") || linkurl.contains("https://"))) {
                if (linkurl.contains("=goods.detail")) {
                    String param = SystemUtils.URLRequest(linkurl).get("id");   //android 获取url中的参数
                    KLog.d(TAG, "id===" + param);
                    //跳转需要跳转的页面
                    bundle.putString(Constants.GOODS_KEY_GID, param);
                    viewModel.startActivity(GoodsActivity.class, bundle);
                }else if (linkurl.contains("enterprise_plan")){ //新闻资讯
                    bundle.putString(Constants.BUNDLE_KEY_URL, linkurl);
                    viewModel.startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
                }else {
                    bundle.putString(Constants.BUNDLE_KEY_URL, linkurl + viewModel.getUserToken());
                    viewModel.startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
                }
            } else {
                KLog.a("商家店铺====>>>>"+linkurl);
                int index = UrlPreprocessingUtils.interceptUrl(linkurl);
                if (Constants.URL.APP_UI_SKIP_FLAG_FIRST == index) {
                    viewModel.startActivity(HomePageActivity.class);
                } else if (Constants.URL.APP_UI_SKIP_FLAG_CATEGORY == index) {
                    viewModel.startActivity(CategoryActivity.class);
                } else if (Constants.URL.APP_UI_SKIP_FLAG_GOODS == index) {

                } else if (Constants.URL.APP_UI_SKIP_FLAG_NOTICE == index) {

                } else if (Constants.URL.APP_UI_SKIP_FLAG_CART == index) {
                    viewModel.startActivity(ShoppingCarActivity.class);
                } else if (Constants.URL.APP_UI_SKIP_FLAG_DIVIDEND == index) {

                } else if (Constants.URL.APP_UI_SKIP_FLAG_EXCHANGE == index) {

                } else if (Constants.URL.APP_UI_SKIP_FLAG_ABONUS == index) {

                } else if (Constants.URL.APP_UI_SKIP_FLAG_MEMBER == index) {
                    viewModel.startActivity(MyPageActivity.class);
                } else if (Constants.URL.APP_UI_SKIP_FLAG_APP_MERCH == index) {//商家联盟
                    viewModel.startContainerActivity(MerchantAllianceFragment.class.getCanonicalName());
                } else if (Constants.URL.APP_UI_SKIP_FLAG_NEW == index) {//新品商品
                    mJumpShopList(viewModel);
                } else if (Constants.URL.APP_UI_SKIP_FLAG_HOT == index) {//热卖商品
                    mJumpShopList(viewModel);
                } else if (Constants.URL.APP_UI_SKIP_FLAG_DISCOUNT == index) {//促销商品
                    mJumpShopList(viewModel);
                } else if (Constants.URL.APP_UI_SKIP_FLAG_SENDFREE == index) {//卖家包邮
                    mJumpShopList(viewModel);
                } else if (Constants.URL.APP_UI_SKIP_FLAG_TIME == index) {//限时抢购
                    mJumpShopList(viewModel);
                }else if (Constants.URL.APP_UI_SKIP_FLAG_APP_MERCH_REGIST ==index){//商家入驻
                    viewModel.startActivity(MerchantEntryAty.class);
                }else if (Constants.URL.APP_UI_SKIP_FLAG_COMMISSION_QRCODE ==index){//邀请好友
                    viewModel.startActivity(InviteFriendsActivity.class);
                }else if (Constants.URL.APP_UI_SKIP_FLAG_APP_LOCAL_LIFE ==index){//本地生活
                    viewModel.startActivity(LocalLifeAty.class);
                }
                else if (Constants.URL.APP_UI_SKIP_FLAG_ORDER == index){ //订单列表 全部
                    // TODO: 2020/1/20  全部 缺失跳转标识
                    bundle.putInt(Constants.MyOrder,0);
                    viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);

                }else if (Constants.URL.APP_UI_SKIP_FLAG_STATUS0 == index){
                    bundle.putInt(Constants.MyOrder,0);
                    viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
                }else if (Constants.URL.APP_UI_SKIP_FLAG_STATUS1 == index){
                    bundle.putInt(Constants.MyOrder,1);
                    viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
                }else if (Constants.URL.APP_UI_SKIP_FLAG_STATUS2 == index){
                    bundle.putInt(Constants.MyOrder,2);
                    viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
                }else if (Constants.URL.APP_UI_SKIP_FLAG_STATUS4 == index){
                    bundle.putInt(Constants.MyOrder,4);
                    viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
                }else if (Constants.URL.APP_UI_SKIP_FLAG_STATUS3 == index){
                    bundle.putInt(Constants.MyOrder,3);
                    viewModel.startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
                } else if(Constants.URL.APP_UI_SKIP_FLAG_APP_SIGN == index){//签到
                    viewModel.startActivity(CreditSignNewActivity.class);
                }else if(Constants.URL.APP_UI_SKIP_FLAG_APP_MYSHOP == index){ //商家店铺
                    KLog.a("小店首页===>>>>"+Constants.URL.APP_UI_SKIP_FLAG_APP_MYSHOP);
                    String merch_id = SystemUtils.URLRequest(linkurl).get("merch_id");
                    String merch_name = SystemUtils.URLRequest(linkurl).get("merch_name");
                    KLog.d(TAG, "merch_name===" + merch_name + "merch_id===" + merch_id);
                    bundle.putString("title", merch_name);
                    bundle.putString(Constants.MINE_SHOP, merch_id);
                    viewModel.startContainerActivity(ShopFragment.class.getCanonicalName(), bundle);
                }else if(Constants.URL.APP_UI_SKIP_FLAG_APP_GOODS_DETAIL == index){ //商品详情
                    KLog.a("商品详情===>>>>"+Constants.URL.APP_UI_SKIP_FLAG_APP_GOODS_DETAIL);
                    String value = SystemUtils.URLRequest(linkurl).get("gid");
                    KLog.d(TAG, "gid===" + value);
                    bundle.putString("gid",value);
                    bundle.putString(Constants.GOODS_OPEN_FLAG, "0");
                    viewModel.startActivity(GoodsActivity.class,bundle);
                }else if (Constants.URL.APP_UI_SKIP_FLAG_CREDITSHOP == index) {//积分商城
                    viewModel.startActivity(CreditsStoreActivity.class,bundle);
                }else if (Constants.URL.APP_UI_SKIP_FLAG_CREDITSHOP_LISTS == index) {//积分商城 全部商品
                    viewModel.startActivity(AllCreditGoodsActivity.class);
                }else if (Constants.URL.APP_UI_SKIP_FLAG_CREDITSHOP_LOG == index) {//积分商城 我的
                    viewModel.startActivity(CreditsMineActivity.class);
                }else if (Constants.URL.APP_UI_SKIP_FLAG_CREDITLOG == index) {//积分商城 参与记录
                    viewModel.startActivity(PartakeRecordActivity.class);
                }else if (Constants.URL.APP_UI_SKIP_FLAG_APP_CREDITSHOP_DETAIL == index) {//积分商城 商品详情
                    KLog.a("积分商城 商品详情===>>>>"+Constants.URL.APP_UI_SKIP_FLAG_APP_CREDITSHOP_DETAIL);
                    String value = SystemUtils.URLRequest(linkurl).get("gid");
                    KLog.d(TAG, "gid===" + value);
                    bundle.putString(Constants.CREDIT_GOODS_DETAIL,value);
                    viewModel.startActivity(CreditGoodsDetailsActivity.class,bundle);
                }else {}

            }
        }
    }

    /**
     * 商品list
     *
     * @param viewModel
     */
    private static void mJumpShopList(BaseViewModel viewModel) {
        if (viewModel instanceof HomePageViewModel) {
            Bundle bundleShopList = new Bundle();
            bundleShopList.putString(Constants.BUNDLE_KEY_KEYWORDS, "");
            viewModel.startContainerActivity(GoodsListFragment.class.getCanonicalName());
        } else if (viewModel instanceof MerchantAllianceViewModel) {
            Bundle bundleShopList = new Bundle();
            bundleShopList.putString(Constants.BUNDLE_KEY_KEYWORDS, "");
            viewModel.startContainerActivity(GoodsListFragment.class.getCanonicalName());
        }else if (viewModel instanceof ShopViewModel){
            Bundle bundleShopList = new Bundle();
            bundleShopList.putString(Constants.BUNDLE_KEY_KEYWORDS, "");
            viewModel.startContainerActivity(GoodsListFragment.class.getCanonicalName());
        }
        else {
        }
    }
    //------------------------------------------查看预览大图------------------------------------

    /**
     *  海报查看
     * @param activity
     * @param bannerList
     * @param position
     */
    public static void previewLargePic(Activity activity, List<String> bannerList, int position) {
        if (bannerList != null && bannerList.size() > 0) {
            ThumbViewInfo item;
            mThumbViewInfoList.clear();
            for (int i = 0; i < bannerList.size(); i++) {
                Rect bounds = new Rect();
                item = new ThumbViewInfo(bannerList.get(i));
                item.setBounds(bounds);
                mThumbViewInfoList.add(item);
            }
            //打开预览界面
            GPreviewBuilder.from(activity)
                    //是否使用自定义预览界面，当然8.0之后因为配置问题，必须要使用
                    .to(ImageLookActivity.class)
                    .setData(mThumbViewInfoList)
                    .setCurrentIndex(position)
                    .setSingleFling(true)
                    .setType(GPreviewBuilder.IndicatorType.Number)
                    // 小圆点
                    // .setType(GPreviewBuilder.IndicatorType.Dot)
                    .start();//启动
        }
    }

    /**
     *  商品详情页查看
     * @param activity
     * @param bannerList
     * @param position
     */
    public static void previewLargePicGoods(Activity activity, List<BannEntity> bannerList, int position) {
        if (bannerList != null && bannerList.size() > 0) {
            ThumbViewInfo item;
            mThumbViewInfoList.clear();
            for (int i = 0; i < bannerList.size(); i++) {
                Rect bounds = new Rect();
                item = new ThumbViewInfo(bannerList.get(i).getImgurl());
                item.setBounds(bounds);
                mThumbViewInfoList.add(item);
            }
            //打开预览界面
            GPreviewBuilder.from(activity)
                    //是否使用自定义预览界面，当然8.0之后因为配置问题，必须要使用
                    .to(ImageLookActivity.class)
                    .setData(mThumbViewInfoList)
                    .setCurrentIndex(position)
                    .setSingleFling(true)
                    .setType(GPreviewBuilder.IndicatorType.Number)
                    // 小圆点
                    // .setType(GPreviewBuilder.IndicatorType.Dot)
                    .start();//启动
        }
    }
}
