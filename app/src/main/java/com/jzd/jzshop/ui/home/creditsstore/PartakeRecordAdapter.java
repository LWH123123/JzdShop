package com.jzd.jzshop.ui.home.creditsstore;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.PartakeRecordEntity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/5/11 10:11
 */
public class PartakeRecordAdapter extends SuperAdapter<PartakeRecordEntity.ResultBean.DataBean> {
    private Context mContext;
    private List<PartakeRecordEntity.ResultBean.DataBean> dataList;

    public PartakeRecordAdapter(Context context, List<PartakeRecordEntity.ResultBean.DataBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, PartakeRecordEntity.ResultBean.DataBean item) {
        ImageView iv_tip = holder.findViewById(R.id.iv_tip);
        Glide.with(mContext).load(item.getThumb()).into(iv_tip);
        holder.setText(R.id.tv_title, item.getTitle());
        holder.setText(R.id.tv_time, "时间：" + item.getTime());
        holder.setText(R.id.tv_credits_num, item.getCredit() +"积分");
        if (!TextUtils.isEmpty(item.getMoney())){
            if (item.getMoney().equals("0.00")){
                holder.setVisibility(R.id.tv_price, View.VISIBLE);
                holder.setText(R.id.tv_price, "+¥" +item.getMoney());
            }else {
                holder.setVisibility(R.id.tv_price,View.VISIBLE);
                holder.setText(R.id.tv_price, "+¥" +item.getMoney());
            }
        }
        holder.setText(R.id.tv_status, item.getStatus_str());
        int type = item.getType(); //类型【0:兑换；1：抽奖】
        TextView tv_state = holder.findViewById(R.id.tv_state);
        if (type == 0){
            tv_state.setText(mContext.getResources().getString(R.string.coupon_exchage));
        }else {
            tv_state.setText(mContext.getResources().getString(R.string.lotterydraws));
        }

    }
}
