package com.jzd.jzshop.ui.mine.promotion.awarddetail;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.AwarddetailEntity;
import com.jzd.jzshop.utils.MoneyFormatUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by lxb on 2020/2/14.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class AwardDetailItemChildAdapter extends SuperAdapter<AwarddetailEntity.ResultBean.AwardDetailBean.GoodBean> {
    private Context mContext;
    private List<AwarddetailEntity.ResultBean.AwardDetailBean.GoodBean> dataList;

    public AwardDetailItemChildAdapter(Context context, List<AwarddetailEntity.ResultBean.AwardDetailBean.GoodBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.dataList = items;
        mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, AwarddetailEntity.ResultBean.AwardDetailBean.GoodBean item) {
        String commission = MoneyFormatUtils.keepTwoDecimal(item.getCommission());
        holder.setText(R.id.tv_award_amount, "奖励：".concat(commission).concat("元"));
        ImageView iv_good = holder.findViewById(R.id.iv_good);
        Glide.with(mContext).load(item.getThumb()).into(iv_good);
        holder.setText(R.id.tv_goodname, item.getTitle());
        TextView tv_level = holder.findViewById(R.id.tv_level); //分销等级
        if (item.getLevel() == 1) {
            tv_level.setText(mContext.getResources().getString(R.string.goods_state_1));
        } else if (item.getLevel() == 2) {
            tv_level.setText(mContext.getResources().getString(R.string.goods_state_2));
        } else if (item.getLevel() == 3) {
            tv_level.setText(mContext.getResources().getString(R.string.goods_state_3));
        } else {
            tv_level.setText(mContext.getResources().getString(R.string.goods_state_self));
        }
        TextView tv_result_state = holder.findViewById(R.id.tv_result_state);//提现状态
        if (item.getStatus() == -2) {
            tv_result_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_result_02));
        } else if (item.getStatus() == -1) {
            tv_result_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_result_01));
        } else if (item.getStatus() == 1) {
            tv_result_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_result_1));
        } else if (item.getStatus() == 2) {
            tv_result_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_result_2));
        } else {
            tv_result_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_result_3));
        }
        holder.setText(R.id.tv_audit_content, "审核内容：".concat(item.getContent()));
    }
}
