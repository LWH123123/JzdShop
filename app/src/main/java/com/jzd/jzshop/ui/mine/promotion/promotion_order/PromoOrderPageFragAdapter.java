package com.jzd.jzshop.ui.mine.promotion.promotion_order;

import android.content.Context;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.PromotionOrderEntity;
import com.jzd.jzshop.utils.MoneyFormatUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by lxb on 2020/2/14.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class PromoOrderPageFragAdapter extends SuperAdapter<PromotionOrderEntity.ResultBean.PromoOrderBean> {
    private Context mContext;
    private List<PromotionOrderEntity.ResultBean.PromoOrderBean> dataList;

    public PromoOrderPageFragAdapter(Context context, List<PromotionOrderEntity.ResultBean.PromoOrderBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.dataList = items;
        mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, PromotionOrderEntity.ResultBean.PromoOrderBean item) {
        String commission = MoneyFormatUtils.keepTwoDecimal(item.getCommission());
        holder.setText(R.id.tv_commission, "预计奖励：+".concat(commission));
        holder.setText(R.id.tv_createtime, "下单时间：".concat(item.getCreatetime()));
        TextView tv_level = holder.findViewById(R.id.tv_level); //分销等级
        if (item.getLevel() == 1) {
            tv_level.setText("分销等级：".concat(mContext.getResources().getString(R.string.goods_state_1)));
        } else if (item.getLevel() == 2) {
            tv_level.setText("分销等级：".concat(mContext.getResources().getString(R.string.goods_state_2)));
        } else if (item.getLevel() == 3) {
            tv_level.setText("分销等级：".concat(mContext.getResources().getString(R.string.goods_state_3)));
        } else {
            tv_level.setText("分销等级：".concat(mContext.getResources().getString(R.string.goods_state_self)));
        }
        holder.setText(R.id.tv_ordersn, "订单编号：".concat(item.getOrdersn()));
    }
}
