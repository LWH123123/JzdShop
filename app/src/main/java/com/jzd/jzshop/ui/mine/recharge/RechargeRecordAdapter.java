package com.jzd.jzshop.ui.mine.recharge;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.RechargeRecordEntity;
import com.jzd.jzshop.utils.MoneyFormatUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by lxb on 2020/2/10.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class RechargeRecordAdapter extends SuperAdapter<RechargeRecordEntity.ResultBean> {
    private Context mContext;
    private List<RechargeRecordEntity.ResultBean> dataList;

    public RechargeRecordAdapter(Context context, List<RechargeRecordEntity.ResultBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, RechargeRecordEntity.ResultBean item) {
        holder.setText(R.id.tv_recharge_state,item.getTitle());
        TextView tv_status_str = holder.findViewById(R.id.tv_status_str);
//        TextView view_line = holder.findViewById(R.id.view_line);
        if (item.getStatus_str().equals("成功")){ //充值成功
            tv_status_str.setText(item.getStatus_str());
            tv_status_str.setTextColor(mContext.getResources().getColor(R.color.leve2organge));
        }else if (item.getStatus_str().equals("未充值")){
            tv_status_str.setText(item.getStatus_str());
            tv_status_str.setTextColor(mContext.getResources().getColor(R.color.color_blue));
        }else {

            tv_status_str.setText(item.getStatus_str());
            holder.setText(R.id.tv_recharge_state,mContext.getResources().getString(R.string.recharge_fail));
            tv_status_str.setTextColor(mContext.getResources().getColor(R.color.lightred));
        }
        holder.setText(R.id.tv_time,item.getCreatetime());
        String amount = MoneyFormatUtils.keepTwoDecimal(item.getMoney());
        if (!TextUtils.isEmpty(amount)) {
            holder.setText(R.id.tv_amount,"¥".concat(amount));
        }

//        if (layoutPosition ==dataList.size()-1){
//            view_line.setVisibility(View.GONE);
//        }else {
//            view_line.setVisibility(View.VISIBLE);
//        }
    }
}
