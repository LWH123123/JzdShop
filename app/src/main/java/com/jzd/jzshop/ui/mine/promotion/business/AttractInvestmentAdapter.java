package com.jzd.jzshop.ui.mine.promotion.business;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.AttractInvestmentEntity;
import com.jzd.jzshop.entity.SignRecordEntity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/4/13 12:01
 */
public class AttractInvestmentAdapter extends SuperAdapter<AttractInvestmentEntity.ResultBean.DataBean> {
    private Context mContext;
    private List<AttractInvestmentEntity.ResultBean.DataBean> dataList;

    public AttractInvestmentAdapter(Context context, List<AttractInvestmentEntity.ResultBean.DataBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, AttractInvestmentEntity.ResultBean.DataBean item) {
        ImageView iv_icon = holder.findViewById(R.id.iv_icon);
        Glide.with(mContext).load(item.getLogo()).into(iv_icon);
        holder.setText(R.id.tv_name, item.getName());
        if (!TextUtils.isEmpty(String.valueOf(item.getMoney()))) {
            holder.setText(R.id.tv_money, "+".concat(String.valueOf(item.getMoney())));
        }
    }
}
