package com.jzd.jzshop.ui.home.creditsstore.home;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.CreditsStoreEntity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/5/9 13:38
 */
public class CreditStoreCategoryAdapter extends SuperAdapter<CreditsStoreEntity.ResultBean.CategoryBean> {
    private Context mContext;
    private List<CreditsStoreEntity.ResultBean.CategoryBean> dataList;

    public CreditStoreCategoryAdapter(Context context, CreditsStoreViewModel viewModel, List<CreditsStoreEntity.ResultBean.CategoryBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CreditsStoreEntity.ResultBean.CategoryBean item) {
        holder.setText(R.id.tv_menu,item.getCate_name());
        ImageView iv_menu = holder.findViewById(R.id.iv_menu);
        Glide.with(mContext).load(item.getCate_thumb()).into(iv_menu);
    }
}
