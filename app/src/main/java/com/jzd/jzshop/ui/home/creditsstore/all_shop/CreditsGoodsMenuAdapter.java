package com.jzd.jzshop.ui.home.creditsstore.all_shop;

import android.content.Context;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.CreditsAllGoodsEntity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/5/11 14:49
 */
public class CreditsGoodsMenuAdapter extends SuperAdapter<CreditsAllGoodsEntity.ResultBean.CategoryBean> {
    private Context mContext;
    private int position = 0;
    private String selectedTxt;

    public CreditsGoodsMenuAdapter(Context context, String menuTitle, List<CreditsAllGoodsEntity.ResultBean.CategoryBean> items, int layoutResId) {
        super(context, items, layoutResId);
        mContext = context;
        this.selectedTxt = menuTitle;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CreditsAllGoodsEntity.ResultBean.CategoryBean item) {
        holder.setText(R.id.tv_name, item.getCate_name());
        TextView tv_name = holder.findViewById(R.id.tv_name);
        if (position == layoutPosition) {//选中位置
            tv_name.setBackgroundResource(R.drawable.shape_txt_menu_selected);
            tv_name.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            tv_name.setBackgroundResource(R.drawable.shape_txt_menu_normal);
            tv_name.setTextColor(mContext.getResources().getColor(R.color.textColor));
        }
        //记录上次选中的位置
        if (selectedTxt.equals(item.getCate_name())) {
            tv_name.setBackgroundResource(R.drawable.shape_txt_menu_selected);
            tv_name.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            tv_name.setBackgroundResource(R.drawable.shape_txt_menu_normal);
            tv_name.setTextColor(mContext.getResources().getColor(R.color.textColor));
        }
    }

    public void setSelectedPosition(int pos) {
        this.position = pos;
        notifyDataSetChanged();
    }

}
