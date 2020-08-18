package com.jzd.jzshop.ui.message;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.MessageMerchEntity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/4/2 11:32
 */
public class MessageMerchListAdapter extends SuperAdapter<MessageMerchEntity.ResultBean.GoodsBean> {
    private Context mContext;
    private List<MessageMerchEntity.ResultBean.GoodsBean> dataList;

    public MessageMerchListAdapter(Context context, List<MessageMerchEntity.ResultBean.GoodsBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, MessageMerchEntity.ResultBean.GoodsBean item) {
        if (item != null) {
            holder.setText(R.id.tv_title, item.getTitle());
            holder.setText(R.id.tv_specs, item.getSpec_title());
            holder.setText(R.id.tv_price, "¥".concat(item.getPrice()));
            holder.setText(R.id.tv_number, "x" + item.getTotal());
            ImageView iv_icon = holder.findViewById(R.id.iv_icon);
            if (!TextUtils.isEmpty(item.getThumb())) {
                Glide.with(mContext).load(item.getThumb()).into(iv_icon);
            }
        }
    }
}
