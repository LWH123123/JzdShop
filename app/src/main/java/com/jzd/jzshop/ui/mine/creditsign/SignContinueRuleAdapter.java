package com.jzd.jzshop.ui.mine.creditsign;

import android.content.Context;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.SignEntity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description: 连续签到有礼
 * @date :2020/4/11 9:55
 */
public class SignContinueRuleAdapter extends SuperAdapter<SignEntity.ResultBean.ContinueRuleBean> {
    private Context mContext;
    public CreditSignNewViewModel mViewModel;

    public SignContinueRuleAdapter(Context context, List<SignEntity.ResultBean.ContinueRuleBean> items, int layoutResId, CreditSignNewViewModel viewModel) {
        super(context, items, layoutResId);
        mContext = context;
        this.mViewModel = viewModel;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final SignEntity.ResultBean.ContinueRuleBean item) {
        holder.setText(R.id.tv_credit_num,"+"+item.getPoints()+"积分");
        holder.setText(R.id.tv_continue_sign_day, item.getDay() + "天");
        int signed = item.getSigned(); //1：已领取  2：已达到条件，可以  3：未达到条件，不可领取
        if (signed == 1){
            holder.setBackgroundResource(R.id.iv_sign_continue_state,R.mipmap.checkbox_normal);
//            holder.setTextColor(R.id.tv_continue_sign_day,mContext.getResources().getColor(R.color.color_unsigned_txt));
        }else {
            holder.setBackgroundResource(R.id.iv_sign_continue_state,R.mipmap.checkbox_pressed);
        }
        if (signed == 1){
            holder.setBackgroundResource(R.id.tv_receive,R.drawable.boder_radius_btn_credit_sign);
            holder.setBackgroundResource(R.id.tv_credit_num,R.drawable.boder_radius_btn_credit_sign_red);
            holder.setTextColor(R.id.tv_credit_num,mContext.getResources().getColor(R.color.white));
            holder.setText(R.id.tv_receive,mContext.getString(R.string.sign_txt_received));
            holder.setBackgroundResource(R.id.tv_credit_num_tip,R.mipmap.ic_arrows_creditsign_red);
        }else if (signed ==2){
            holder.setBackgroundResource(R.id.tv_credit_num,R.drawable.boder_radius_btn_credit_sign_yellow_solid);
            holder.setTextColor(R.id.tv_credit_num,mContext.getResources().getColor(R.color.levelyellow));
            holder.setBackgroundResource(R.id.tv_receive,R.drawable.boder_radius_btn_credit_sign_yellow);
            holder.setTextColor(R.id.tv_receive,mContext.getResources().getColor(R.color.levelyellow));
            holder.setText(R.id.tv_receive,mContext.getString(R.string.sign_txt_receiv));
            holder.setOnClickListener(R.id.tv_receive, new View.OnClickListener() { //领取
                @Override
                public void onClick(View v) {
                    mViewModel.postToReceive("1",String.valueOf(item.getDay()));
                }
            });
            holder.setTextColor(R.id.tv_continue_sign_day,mContext.getResources().getColor(R.color.level5text));
            holder.setBackgroundResource(R.id.tv_credit_num_tip,R.mipmap.ic_arrows_creditsign_yellow);
        }else if (signed ==3){
            holder.setBackgroundResource(R.id.tv_receive,R.drawable.boder_radius_btn_credit_sign);
            holder.setBackgroundResource(R.id.tv_credit_num,R.drawable.boder_radius_btn_credit_sign);
            holder.setTextColor(R.id.tv_credit_num,mContext.getResources().getColor(R.color.level5text));
            holder.setText(R.id.tv_receive,mContext.getString(R.string.sign_txt_receiv));
            holder.setTextColor(R.id.tv_continue_sign_day,mContext.getResources().getColor(R.color.level5text));
            holder.setBackgroundResource(R.id.tv_credit_num_tip,0);
        }else {}
    }
}
