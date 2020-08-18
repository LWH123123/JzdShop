package com.jzd.jzshop.ui.mine.creditsign;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.SignEntity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description: 签到日历
 * @date :2020/4/7 17:54
 */
public class SignCalendarAdapter extends SuperAdapter<SignEntity.ResultBean.CalendarBean> {
    private Context mContext;
    private int position = -1;
    private int mDay;

    public SignCalendarAdapter(Context context, List<SignEntity.ResultBean.CalendarBean> items, int layoutResId, int currentDay) {
        super(context, items, layoutResId);
        mContext = context;
        this.mDay = currentDay;
    }




    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, SignEntity.ResultBean.CalendarBean item) {
        if (!TextUtils.isEmpty(item.getDay())) {
            holder.setText(R.id.tv_name, "" + Integer.parseInt(item.getDay()));
        }
        TextView tv_name = holder.findViewById(R.id.tv_name);
        RelativeLayout ll_date = holder.findViewById(R.id.rl_date);
        if (item.getSigned() == 1) {//已签到
            holder.setVisibility(R.id.iv_signed, View.VISIBLE);
            if (!TextUtils.isEmpty(item.getTitle())) { //有活动
                // TODO: 2020/4/10 第一版样式
//            holder.setVisibility(R.id.tv_festival, View.VISIBLE);
//            holder.setText(R.id.tv_festival, item.getTitle());
//            holder.setBackgroundColor(R.id.tv_festival, Color.parseColor(item.getColor()));
                // TODO: 2020/4/10 第二版样式
                holder.setVisibility(R.id.tv_name, View.GONE);
                holder.setVisibility(R.id.iv_signed, View.GONE);
                holder.setVisibility(R.id.tv_festival, View.GONE);
                holder.setText(R.id.tv_festival, item.getTitle());
                holder.setTextColor(R.id.tv_festival,mContext.getResources().getColor(R.color.color_signed_txt));
                holder.setVisibility(R.id.iv_sign_pic, View.VISIBLE);
                holder.setBackgroundResource(R.id.iv_sign_pic,R.mipmap.ic_signed_pic);
            }else {
                holder.setVisibility(R.id.tv_name, View.VISIBLE);
                holder.setVisibility(R.id.tv_festival, View.GONE);
                holder.setVisibility(R.id.iv_sign_pic, View.GONE);
                holder.setVisibility(R.id.iv_signed, View.VISIBLE);
            }
        }else { //未签到
            holder.setVisibility(R.id.iv_signed, View.GONE);
            if (!TextUtils.isEmpty(item.getTitle())) { //有活动
                // TODO: 2020/4/10 第二版样式
                holder.setVisibility(R.id.tv_name, View.GONE);
                holder.setVisibility(R.id.tv_festival, View.VISIBLE);
                holder.setText(R.id.tv_festival, item.getTitle());
                holder.setTextColor(R.id.tv_festival,mContext.getResources().getColor(R.color.color_unsigned_txt));
                holder.setVisibility(R.id.iv_sign_pic, View.VISIBLE);
                holder.setBackgroundResource(R.id.iv_sign_pic,R.mipmap.ic_unsign_pic);
            }else {
                holder.setVisibility(R.id.tv_name, View.VISIBLE);
                holder.setVisibility(R.id.tv_festival, View.GONE);
                holder.setVisibility(R.id.iv_sign_pic, View.GONE);
            }
        }
        //日历默认选中当天
        if (!TextUtils.isEmpty(item.getDay())) {
            if (mDay == Integer.parseInt(item.getDay())) {
                ll_date.setBackgroundResource(R.drawable.selector_item_sign_rect);
                tv_name.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            } else {
                tv_name.setTextColor(mContext.getResources().getColor(R.color.textColor));
            }
        }
//        if (position == layoutPosition) {//选中位置
//            ll_date.setBackgroundResource(R.drawable.selector_item_sign_rect);
//            tv_name.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
//        } else {
//            tv_name.setTextColor(mContext.getResources().getColor(R.color.textColor));
//        }

    }

    /**
     * 设置默认选中位置
     *
     * @param pos
     */
    public void setSelectedPosition(int pos) {
        this.position = pos;
        notifyDataSetChanged();
    }
}
