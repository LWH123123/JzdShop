package com.jzd.jzshop.ui.message;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.MessageCenterEntity;
import com.shehuan.niv.NiceImageView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import q.rorbin.badgeview.QBadgeView;

/**
 * @author LXB
 * @description:
 * @date :2020/3/26 11:37
 */
public class MessageCenterAdapter extends SuperAdapter<MessageCenterEntity.ResultBean.DataBean> {
    private Context mContext;
    private List<MessageCenterEntity.ResultBean.DataBean> dataList;
    private boolean isRead = false;

    public MessageCenterAdapter(Context context, List<MessageCenterEntity.ResultBean.DataBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, MessageCenterEntity.ResultBean.DataBean item) {
        if (item != null) {
            holder.setText(R.id.tv_title, item.getTitle());
            holder.setText(R.id.tv_desc, item.getContent());
            holder.setText(R.id.tv_time, item.getTime());
            TextView tv_number = holder.findViewById(R.id.tv_number);
            if (item.getRead()== 0){// 0：未读 1：已读
                tv_number.setVisibility(View.VISIBLE);
                new QBadgeView(mContext).bindTarget(tv_number)
                        .setBadgeNumber(1)
                        .setBadgeTextSize(14f,true)
                        .setBadgeBackgroundColor(mContext.getResources().getColor(R.color.color_bg_message));
            }else {
                tv_number.setVisibility(View.GONE);
                new QBadgeView(mContext).bindTarget(tv_number)
                        .setBadgeNumber(0)
                        .setBadgeTextSize(14f,true)
                        .setBadgeBackgroundColor(mContext.getResources().getColor(R.color.color_bg_message));
            }
//            Glide.with(mContext).load(item.getIcon()).into(iv_icon);
            NiceImageView iv_icon = holder.findViewById(R.id.iv_icon);
            /**
             * app:order.app:order.close：会员订单（关闭通知）
             * app:order.payed：会员订单（付款成功通知）
             * app:order.send：会员订单（发货通知）
             * app:order.end：会员订单（收货通知）
             * app:order.refund：会员订单（退款申请通知）
             * app:order.refund.nosend：会员订单（维权通过后，会员待发货通知）
             * app:order.refund.send：会员订单（换货后商家发货通知）
             * app:order.refund.ends：会员订单（退款成功通知）
             * app:order.refund.refuse：会员订单（维权被驳回通知）
             */
            if (item.getType().equals("app:order.app:order.close")){//交易关闭
                iv_icon.setBackgroundResource(R.mipmap.ic_order_close);
            }else if (item.getType().equals("app:order.payed")){//付款成功
                iv_icon.setBackgroundResource(R.mipmap.ic_order_payed);
            }else if (item.getType().equals("app:order.send")){ //发货
                iv_icon.setBackgroundResource(R.mipmap.ic_order_fh);
            }else if (item.getType().equals("app:order.end")){//收货通知
                iv_icon.setBackgroundResource(R.mipmap.ic_order_qs);
            }else if (item.getType().equals("app:order.refund")){
                iv_icon.setBackgroundResource(R.mipmap.ic_order_notify);
            }else if (item.getType().equals("app:order.refund.nosend")){
                iv_icon.setBackgroundResource(R.mipmap.ic_order_notify);
            }else if (item.getType().equals("app:order.refund.send")){
                iv_icon.setBackgroundResource(R.mipmap.ic_order_notify);
            }else if (item.getType().equals("app:order.refund.ends")){ //退款
                iv_icon.setBackgroundResource(R.mipmap.ic_tkcg);
            }else if (item.getType().equals("app:order.refund.refuse")){
                iv_icon.setBackgroundResource(R.mipmap.ic_order_notify);
            }
            //商户
            else if (item.getType().equals("app:merch.order.send")){//商户订单（发货通知）
                iv_icon.setBackgroundResource(R.mipmap.ic_order_fh);
            }else if (item.getType().equals("app:merch.order.end")){//商户订单（收货通知）
                iv_icon.setBackgroundResource(R.mipmap.ic_order_qs);
            }else if (item.getType().equals("app:merch.refund")){//商户维权订单（申请通知）
                iv_icon.setBackgroundResource(R.mipmap.ic_order_notify);
            }else if (item.getType().equals("app:merch.refund.send")){//商户维权订单（发货通知）
                iv_icon.setBackgroundResource(R.mipmap.ic_order_notify);
            }else {
                iv_icon.setBackgroundResource(R.mipmap.ic_order_notify);
            }
        }
    }

    public void setReadMessage(boolean isRead) {
        this.isRead = isRead;
        notifyDataSetChanged();
    }
}
