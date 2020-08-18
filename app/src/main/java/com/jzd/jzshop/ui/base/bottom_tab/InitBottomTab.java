package com.jzd.jzshop.ui.base.bottom_tab;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.jzd.jzshop.R;

import me.goldze.mvvmhabit.utils.SPUtils;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class InitBottomTab  {
    Context mContext;
    private InitBottomTab(Context context){
        mContext=context;
    };
    private static InitBottomTab sInitBottomTab;
    public static InitBottomTab getInstance(Context context){
        if(sInitBottomTab==null)
            sInitBottomTab=new InitBottomTab(context);
        return sInitBottomTab;
    }

    //首页模块
    public NavigationController init(PageNavigationView pagerBottomTab,OnTabItemSelectedListener onTabItemSelectedListener){
        NavigationController build = pagerBottomTab.custom()
                .addItem(newItem(R.mipmap.un_ic_home, R.mipmap.ic_home, "首页"))
                .addItem(newItem(R.mipmap.un_category, R.mipmap.category, "分类"))
                .addItem(newItem(R.mipmap.ic_website_unselected, R.mipmap.ic_website_selected ,"跨融平台"))
                .addItem(newItem(R.mipmap.un_shoopcar, R.mipmap.shopingcar, "购物车"))
                .addItem(newItem(R.mipmap.un_mine, R.mipmap.mine, "我的"))
                .build();
        /*int anInt = SPUtils.getInstance().getInt(Constants.MINE_GOOD);*/
        build.addTabItemSelectedListener(onTabItemSelectedListener);
        return build;
    }


    //我的小店模块
    public NavigationController initShop(PageNavigationView pagerBottomTab,OnTabItemSelectedListener onTabItemSelectedListener){
        NavigationController build = pagerBottomTab.custom()
                .addItem(newItem(R.mipmap.un_shop_home, R.mipmap.shop_home, "首页"))
                .addItem(newItem(R.mipmap.un_shop_select_self, R.mipmap.shop_select, "自选商品"))
//                .addItem(newItem(R.mipmap.ic_unsign_ranking, R.mipmap.ic_sign_ranked, "自选商品"))
                .addItem(newItem(R.mipmap.un_shop_order, R.mipmap.shop_order ,"奖励订单"))
                .addItem(newItem(R.mipmap.un_shop_set, R.mipmap.shop_set, "设置"))
                .build();
        build.addTabItemSelectedListener(onTabItemSelectedListener);
        return build;
    }

    // 积分签到
    public NavigationController initCreditSign(PageNavigationView pagerBottomTab, OnTabItemSelectedListener
            onTabItemSelectedListener){
        NavigationController build = pagerBottomTab.custom()
                .addItem(newItem(R.mipmap.ic_uncredit_sign, R.mipmap.ic_credit_signed, mContext.getResources().getString(R.string.credit_sign)))
                .addItem(newItem(R.mipmap.ic_unsign_ranking, R.mipmap.ic_sign_ranked, mContext.getResources().getString(R.string.sign_ranking_b)))
                .addItem(newItem(R.mipmap.ic_uncredit_shop, R.mipmap.ic_credit_shoped ,mContext.getResources().getString(R.string.credit_shop)))
                .build();
        build.addTabItemSelectedListener(onTabItemSelectedListener);
        return build;
    }
    //积分商城
    public NavigationController initCreditStore(PageNavigationView pagerBottomTab, OnTabItemSelectedListener
            onTabItemSelectedListener){
        NavigationController build = pagerBottomTab.custom()
                .addItem(newItem(R.mipmap.ic_uncredit_shop_home, R.mipmap.ic_credit_signed_home, mContext.getResources().getString(R.string.credit_home)))
                .addItem(newItem(R.mipmap.ic_uncredit_allshop, R.mipmap.ic_credit_allshoped, mContext.getResources().getString(R.string.credit_all_goods)))
                .addItem(newItem(R.mipmap.un_mine, R.mipmap.mine ,mContext.getResources().getString(R.string.credit_mine)))
                .build();
        build.addTabItemSelectedListener(onTabItemSelectedListener);
        return build;
    }


    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        CustomNormalItemView normalItemView = new CustomNormalItemView(mContext);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(ContextCompat.getColor(mContext, R.color.textColorVice));
        normalItemView.setTextCheckedColor(ContextCompat.getColor(mContext, R.color.red));
        return normalItemView;
    }

}
