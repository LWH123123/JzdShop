package com.jzd.jzshop.ui.home.creditsstore.mine;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.CreditExchangeRecoEntity;
import com.jzd.jzshop.ui.home.creditsstore.comment.CreditCommentActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.dialog.MessageDialog;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/5/9 17:18
 */
public class CreditExchangeRecoFragAdapter extends SuperAdapter<CreditExchangeRecoEntity.ResultBean.DataBean> {
    private Context mContext;
    private List<CreditExchangeRecoEntity.ResultBean.DataBean> dataList;
    private TextView tv_state, tv_result_state;
    private boolean isHide = true;
    private CreditExchangeRecoViewModel viewModel;

    public CreditExchangeRecoFragAdapter(Context context, CreditExchangeRecoViewModel viewModel,
                                         List<CreditExchangeRecoEntity.ResultBean.DataBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.dataList = items;
        mContext = context;
        this.viewModel = viewModel;
    }

    @Override
    public void onBind(final SuperViewHolder holder, int viewType, int layoutPosition, final CreditExchangeRecoEntity.ResultBean.DataBean item) {
        holder.setText(R.id.tv_ordersn, "订单号：" +item.getLogno());
        holder.setText(R.id.tv_status, item.getStatus_str());
        holder.setText(R.id.tv_title, item.getTitle());
        ImageView iv_pic = holder.findViewById(R.id.iv_pic);
        Glide.with(mContext).load(item.getThumb()).into(iv_pic);
        holder.setText(R.id.tv_credit_num, String.valueOf(item.getCredit()));
        if (!TextUtils.isEmpty(item.getMoney())){
            if (item.getMoney().equals("0.00")){
                holder.setVisibility(R.id.price, View.VISIBLE);
                holder.setText(R.id.price, "+¥" +item.getMoney());
            }else {
                holder.setVisibility(R.id.price,View.VISIBLE);
                holder.setText(R.id.price, "+¥" +item.getMoney());
            }
        }
        TextView tv_coupon = holder.findViewById(R.id.tv_coupon);
        int type = item.getType();//类型【0:兑换；1：抽奖】
        if (type == 1){//抽奖
            tv_coupon.setText(mContext.getResources().getString(R.string.lotterydraws));
        }else {//兑换
            tv_coupon.setText(mContext.getResources().getString(R.string.coupon_exchage));
        }
        //type    Int    类型【0:商品；1：优惠券；2：余额；3：红包】
        TextView tv_state = holder.findViewById(R.id.tv_state);
        if (item.getType1() == 0){
            tv_state.setText(mContext.getResources().getString(R.string.shop_exchanges));
        }else if (item.getType1() == 1){
            tv_state.setText(mContext.getResources().getString(R.string.coupons));
        }else if (item.getType1() == 2){
            tv_state.setText(mContext.getResources().getString(R.string.balances));
        }else if (item.getType1() == 3){
            tv_state.setText(mContext.getResources().getString(R.string.redbags));
        }else {}
        //领取红包
        int state = item.getState();
        if (state == 2) {//用户可以领取红包
            holder.setVisibility(R.id.tv_get_redbags,View.VISIBLE);
            holder.setOnClickListener(R.id.tv_get_redbags, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String content = mContext.getResources().getString(R.string.tip_msg_redbags);
                    mCommonMethod(content, item,state);
                }
            });
        }else if (state ==0){
            holder.setVisibility(R.id.tv_confirm_receipt, View.GONE);
            mCommonVisibleOrGone(holder);
        }else if (state == 1){//用户可以确认收货
            holder.setVisibility(R.id.tv_confirm_receipt,View.VISIBLE);
            mCommonVisibleOrGone(holder);
            holder.setOnClickListener(R.id.tv_confirm_receipt, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String content = mContext.getResources().getString(R.string.tip_msg_surereceipt);
                    mCommonMethod(content, item,state);
                }
            });
        }else if (state == 3){//可以评价
            holder.setVisibility(R.id.tv_evaluaion,View.VISIBLE);
            holder.setVisibility(R.id.tv_add_evaluaion,View.GONE);
            mCommonEvaluation(holder, item,R.id.tv_evaluaion,state);
        }else if (state == 4){//可以追加评价
            holder.setVisibility(R.id.tv_add_evaluaion,View.VISIBLE);
            holder.setVisibility(R.id.tv_evaluaion,View.GONE);
            mCommonEvaluation(holder, item,R.id.tv_add_evaluaion,state);
        }else {
        }
    }

    private void mCommonMethod(String content, CreditExchangeRecoEntity.ResultBean.DataBean item, int state) {
        MessageDialog.showCancelAndSureDialog(mContext, "提示", content,
                R.color.textColor, R.color.textColor,
                new MessageDialog.DialogClick() {
                    @Override
                    public void onSureClickListener() {
                        if (state == 1){//确认收货
                            viewModel.postToSureReceipt(item.getLog_id(),"");
                        }else if (state == 2){//领取红包
                            viewModel.postGetRedBags(item.getLog_id(),"");
                        }else {}

                    }
                    @Override
                    public void onCancelClickListener() {
                    }
                });
    }

    private void mCommonEvaluation(SuperViewHolder holder, CreditExchangeRecoEntity.ResultBean.DataBean item,
                                   int resId,int state) {
        holder.setVisibility(R.id.tv_confirm_receipt, View.GONE);
        holder.setVisibility(R.id.tv_get_redbags, View.GONE);
        if (state == 3){
            holder.setOnClickListener(resId, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCreditComment(item);
                }
            });
        }else if (state ==4){
            holder.setOnClickListener(resId, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCreditComment(item);
                }
            });
        }else {}
    }

    /**
     *  打开评论/追加评论
     * @param item
     */
    private void openCreditComment(CreditExchangeRecoEntity.ResultBean.DataBean item) {
        if (!TextUtils.isEmpty(item.getLog_id())) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.GOODS_KEY_GID, item.getLog_id());
            viewModel.startActivity(CreditCommentActivity.class, bundle);
        }
    }

    private void mCommonVisibleOrGone(SuperViewHolder holder) {
        holder.setVisibility(R.id.tv_get_redbags,View.GONE);
        holder.setVisibility(R.id.tv_evaluaion,View.GONE);
        holder.setVisibility(R.id.tv_add_evaluaion,View.GONE);
    }
}
