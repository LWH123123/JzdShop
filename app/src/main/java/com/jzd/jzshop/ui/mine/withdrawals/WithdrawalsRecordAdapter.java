package com.jzd.jzshop.ui.mine.withdrawals;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.WithdrawalsRecordEntity;
import com.jzd.jzshop.utils.MoneyFormatUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by lxb on 2020/2/11.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class WithdrawalsRecordAdapter extends SuperAdapter<WithdrawalsRecordEntity.ResultBean> {
    private Context mContext;
    private List<WithdrawalsRecordEntity.ResultBean> dataList;

    public WithdrawalsRecordAdapter(Context context, List<WithdrawalsRecordEntity.ResultBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, WithdrawalsRecordEntity.ResultBean item) {
        holder.setText(R.id.tv_state,item.getTitle());
        TextView tv_result_state = holder.findViewById(R.id.tv_result_state);
        if (item.getStatus_str().equals("成功")){ //充值成功
            holder.setText(R.id.tv_result_state,item.getStatus_str());
            tv_result_state.setTextColor(mContext.getResources().getColor(R.color.levelorange));
        }else if(item.getStatus_str().equals("处理中")){ //处理中
            holder.setText(R.id.tv_result_state,item.getStatus_str());
            tv_result_state.setTextColor(mContext.getResources().getColor(R.color.color_blue));
        }else if(item.getStatus_str().equals("申请中")){//申请中
         holder.setText(R.id.tv_result_state,item.getStatus_str());
         tv_result_state.setTextColor(mContext.getResources().getColor(R.color.picture_color_bfe85d));
        }else {
            holder.setText(R.id.tv_result_state,mContext.getResources().getString(R.string.recharge_fail));
            tv_result_state.setTextColor(mContext.getResources().getColor(R.color.lightred));
        }
        holder.setText(R.id.tv_time,item.getCreatetime());
        String amount = MoneyFormatUtils.keepTwoDecimal(item.getMoney());
        if (!TextUtils.isEmpty(amount)) {
            holder.setText(R.id.tv_amount,"¥".concat(amount));
        }
    }
}
