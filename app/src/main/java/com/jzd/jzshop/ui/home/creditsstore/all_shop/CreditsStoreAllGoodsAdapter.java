package com.jzd.jzshop.ui.home.creditsstore.all_shop;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.CreditsAllGoodsEntity;
import com.jzd.jzshop.utils.SystemUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/5/9 16:15
 */
public class CreditsStoreAllGoodsAdapter extends SuperAdapter<CreditsAllGoodsEntity.ResultBean.DataBean> {
    private Context mContext;
    private List<CreditsAllGoodsEntity.ResultBean.DataBean> dataList;

    public CreditsStoreAllGoodsAdapter(Context context, List<CreditsAllGoodsEntity.ResultBean.DataBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CreditsAllGoodsEntity.ResultBean.DataBean item) {
        holder.setText(R.id.tv_title, item.getTitle());
        ImageView iv_pic = holder.findViewById(R.id.iv_pic);
        if (!TextUtils.isEmpty(item.getThumb())){
            SystemUtils.setPicTransition(mContext,item.getThumb(),iv_pic);
        }
        holder.setText(R.id.tv_credit_num, String.valueOf(item.getCredit()));
        if (!TextUtils.isEmpty(item.getMoney())) {
            if (item.getMoney().equals("0.00")) {
                holder.setVisibility(R.id.price, View.GONE);
            } else {
                holder.setVisibility(R.id.price, View.VISIBLE);
                holder.setText(R.id.price, "+¥" + item.getMoney());
            }
        }
        TextView tv_coupon = holder.findViewById(R.id.tv_coupon);
        int lotterydraws = item.getLotterydraws();
        if (lotterydraws == 1) {//抽奖
            tv_coupon.setText(mContext.getResources().getString(R.string.lotterydraws));
        } else {//兑换
            tv_coupon.setText(mContext.getResources().getString(R.string.coupon_exchage));
        }
        //type    Int    类型【0:商品；1：优惠券；2：余额；3：红包】
        TextView tv_state = holder.findViewById(R.id.tv_state);
        if (item.getType() == 0) {
            tv_state.setText(mContext.getResources().getString(R.string.shop_exchanges));
        } else if (item.getType() == 1) {
            tv_state.setText(mContext.getResources().getString(R.string.coupons));
        } else if (item.getType() == 2) {
            tv_state.setText(mContext.getResources().getString(R.string.balances));
        } else if (item.getType() == 3) {
            tv_state.setText(mContext.getResources().getString(R.string.redbags));
        } else {
        }
    }
}
