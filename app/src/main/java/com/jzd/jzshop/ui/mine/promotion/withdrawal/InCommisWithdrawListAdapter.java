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
 * @date :2020/4/13 10:39
 */
public class InCommisWithdrawListAdapter extends SuperAdapter<CommisWithdraRecordEntity.DataBean> {
    private Context mContext;
    private List<CommisWithdraRecordEntity.DataBean> dataList;
    private TextView tv_state, tv_result_state;

    public InCommisWithdrawListAdapter(Context context, List<CommisWithdraRecordEntity.DataBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.dataList = items;
        this.mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CommisWithdraRecordEntity.DataBean item) {
        tv_state = holder.findViewById(R.id.tv_state);   //提现类型
        if (item.getType() == 0) {
            tv_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_txt_0));
        } else if (item.getType() == 1) {
            tv_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_txt_1));
        } else if (item.getType() == 2) {
            tv_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_txt_2));
        } else {
            tv_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_txt_3));
        }
        tv_result_state = holder.findViewById(R.id.tv_result_state); //提现状态
        if (item.getStatus() == 0) {
            tv_result_state.setText(mContext.getResources().getString(R.string.withdrawal_type_01));
            tv_result_state.setTextColor(mContext.getResources().getColor(R.color.lightred));
        } else if (item.getStatus() == 1) {
            tv_result_state.setText(mContext.getResources().getString(R.string.withdrawal_type_02));
            tv_result_state.setTextColor(mContext.getResources().getColor(R.color.picture_color_ffd042));
        } else if (item.getStatus() == 2) {
            tv_result_state.setText(mContext.getResources().getString(R.string.withdrawal_type_03));
            tv_result_state.setTextColor(mContext.getResources().getColor(R.color.color_promo_type_succ));
        } else {
        }
        holder.setText(R.id.tv_time, item.getTime());
        String amount = MoneyFormatUtils.keepTwoDecimal(item.getMoney());
        if (!TextUtils.isEmpty(amount)) {
            holder.setText(R.id.tv_amount, amount);
        }

    }
}
