package com.jzd.jzshop.ui.mine.promotion.withdrawal;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.CommisWithdraRecordEntity;
import com.jzd.jzshop.utils.MoneyFormatUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/4/13 10:13
 */
public class CommissiWithdrawRecordAdapter extends SuperAdapter<CommisWithdraRecordEntity.DataBean> {
    private Context mContext;
    private List<CommisWithdraRecordEntity.DataBean> dataList;
    private TextView tv_state;

    public CommissiWithdrawRecordAdapter(Context context, List<CommisWithdraRecordEntity.DataBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CommisWithdraRecordEntity.DataBean item) {
        tv_state = holder.findViewById(R.id.tv_state); //提现类型
        if (item.getType() == 0) {
            holder.setBackgroundResource(R.id.iv_tip, R.mipmap.ic_prom_balance);
            tv_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_txt_0));
        } else if (item.getType() == 1) {
            holder.setBackgroundResource(R.id.iv_tip, R.mipmap.ic_prom_wx);
            tv_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_txt_1));
        } else if (item.getType() == 2) {
            holder.setBackgroundResource(R.id.iv_tip, R.mipmap.ic_prom_zfb);
            tv_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_txt_2));
        } else { //银行卡
            holder.setBackgroundResource(R.id.iv_tip, R.mipmap.placeholder);
            tv_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_txt_3));
        }
        TextView tv_result_state = holder.findViewById(R.id.tv_result_state);
        if (item.getStatus() == 0) { //提现失败
            holder.setText(R.id.tv_result_state, mContext.getResources().getString(R.string.withdrawal_type_01));
            tv_result_state.setTextColor(mContext.getResources().getColor(R.color.lightred));
        } else if (item.getStatus() == 1) { //待审核
            holder.setText(R.id.tv_result_state, mContext.getResources().getString(R.string.withdrawal_type_02));
            tv_result_state.setTextColor(mContext.getResources().getColor(R.color.picture_color_ffd042));
        } else if (item.getStatus() == 2) {//提现成功
            holder.setText(R.id.tv_result_state, mContext.getResources().getString(R.string.withdrawal_type_03));
            tv_result_state.setTextColor(mContext.getResources().getColor(R.color.color_promo_type_succ));
        } else {
        }
        holder.setText(R.id.tv_time, item.getTime());
        String amount = MoneyFormatUtils.keepTwoDecimal(item.getMoney());
        if (!TextUtils.isEmpty(amount)) {
            holder.setText(R.id.tv_amount, "¥".concat(amount));
        }
    }
}
