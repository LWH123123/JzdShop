package com.jzd.jzshop.ui.mine.promotion.promotion_order;

import android.content.Context;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.PromotionListEntity;
import com.jzd.jzshop.utils.MoneyFormatUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/4/16 13:44
 */
public class PromoListAdapter extends SuperAdapter<PromotionListEntity.ResultBean> {
    private Context mContext;
    private List<PromotionListEntity.ResultBean> dataList;

    public PromoListAdapter(Context context, List<PromotionListEntity.ResultBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.dataList = items;
        mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, PromotionListEntity.ResultBean item) {
        String commission = MoneyFormatUtils.keepTwoDecimal(item.getMoney());
        holder.setText(R.id.tv_commission, "预计奖励：+".concat(commission));
        holder.setText(R.id.tv_createtime, "下单时间：".concat(item.getCreatetime()));
        TextView tv_level = holder.findViewById(R.id.tv_level); //分销等级
        if (item.getLevel() == 1) {
//            tv_level.setText("分销等级：".concat(mContext.getResources().getString(R.string.goods_state_1)));
            tv_level.setText("分销等级：".concat(mContext.getResources().getString(R.string.goods_state_0)));
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
