package com.jzd.jzshop.ui.mine.promotion.award_list;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.AwardListEntity;
import com.jzd.jzshop.ui.mine.promotion.awarddetail.AwardDetailFragment;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.MoneyFormatUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by lxb on 2020/2/11.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class AwardListPageFragAdapter extends SuperAdapter<AwardListEntity.ResultBean.AssetsRecordBean> {
    private Context mContext;
    private List<AwardListEntity.ResultBean.AssetsRecordBean> dataList;
    private TextView tv_state, tv_result_state;
    private boolean isHide = true;
    private  AwardAllFragmViewModel viewModel;

    public AwardListPageFragAdapter(Context context, AwardAllFragmViewModel viewModel, List<AwardListEntity.ResultBean.AssetsRecordBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.dataList = items;
        this.mContext = context;
        this.viewModel = viewModel;
    }

    @Override
    public void onBind(final SuperViewHolder holder, int viewType, final int layoutPosition, final AwardListEntity.ResultBean.AssetsRecordBean item) {
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
        if (item.getStatus() == -2) {
            tv_result_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_result_02));
            tv_result_state.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else if (item.getStatus() == -1) {
            tv_result_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_result_01));
            tv_result_state.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else if (item.getStatus() == 1) {
            tv_result_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_result_1));
            tv_result_state.setTextColor(mContext.getResources().getColor(R.color.picture_color_ffd042));
        } else if (item.getStatus() == 2) {
            tv_result_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_result_2));
            tv_result_state.setTextColor(mContext.getResources().getColor(R.color.picture_color_ffd042));
        } else {
            tv_result_state.setText(mContext.getResources().getString(R.string.assets_record_item_type_result_3));
            tv_result_state.setTextColor(mContext.getResources().getColor(R.color.picture_color_20c064));
        }

        holder.setText(R.id.tv_times, item.getDeal_time());
        holder.setText(R.id.tv_amount, "+90.00");
        String commission = MoneyFormatUtils.keepTwoDecimal(item.getCommission());
        holder.setText(R.id.tv_apply_award_amount, commission);
        String real_money = MoneyFormatUtils.keepTwoDecimal(item.getReal_money());
        holder.setText(R.id.tv_apply_actual_amount, real_money);
        String deduction_money = MoneyFormatUtils.keepTwoDecimal(item.getDeduction_money());
        holder.setText(R.id.tv_fee_amount, deduction_money);

        final ConstraintLayout cons_child_two = holder.findViewById(R.id.cons_child_two);
        final ConstraintLayout cons_child_three = holder.findViewById(R.id.cons_child_three);
        if (layoutPosition ==1){ // 默认展开一个
            isHide = false;
            cons_child_two.setVisibility(View.VISIBLE);
            cons_child_three.setVisibility(View.VISIBLE);
            holder.findViewById(R.id.iv_arrow).setBackgroundResource(R.mipmap.up);
        }else {
            isHide = true;
            cons_child_two.setVisibility(View.GONE);
            cons_child_three.setVisibility(View.GONE);
            holder.findViewById(R.id.iv_arrow).setBackgroundResource(R.mipmap.down);
        }
        holder.findViewById(R.id.iv_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHide) {
                    isHide = false;
                    cons_child_two.setVisibility(View.VISIBLE);
                    cons_child_three.setVisibility(View.VISIBLE);
                    holder.findViewById(R.id.iv_arrow).setBackgroundResource(R.mipmap.up);
                } else {
                    isHide = true;
                    cons_child_two.setVisibility(View.GONE);
                    cons_child_three.setVisibility(View.GONE);
                    holder.findViewById(R.id.iv_arrow).setBackgroundResource(R.mipmap.down);
                }
            }
        });
        // 查看提现详情
        holder.findViewById(R.id.tv_see_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BUNDLE_KEY_LOG_ID, item.getLog_id());
                viewModel.startContainerActivity(AwardDetailFragment.class.getCanonicalName(),bundle);
            }
        });
    }

    public void setListData(List<AwardListEntity.ResultBean.AssetsRecordBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();

    }

}
