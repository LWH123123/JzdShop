package com.jzd.jzshop.ui.mine.promotion;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.bean.PromotCommissionBean;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/4/11 18:05
 */
public class PromotCommissionAdapter extends SuperAdapter<PromotCommissionBean> {
    private Context mContext;
    private List<PromotCommissionBean> dataList;

    public PromotCommissionAdapter(Context context, List<PromotCommissionBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, PromotCommissionBean item) {
        holder.setText(R.id.tv_name, item.getMerchName());
        holder.setText(R.id.tv_desc, item.getMerchName());
        AppCompatImageView iv_logo = holder.findViewById(R.id.iv_logo);
        iv_logo.setBackgroundResource(item.getMerchLogon());
        if (!TextUtils.isEmpty(item.getMerchAmount())) {
            holder.setText(R.id.tv_sign, "+".concat(item.getMerchAmount()));
        }
    }
}
