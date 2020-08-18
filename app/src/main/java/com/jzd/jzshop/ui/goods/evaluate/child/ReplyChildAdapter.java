package com.jzd.jzshop.ui.goods.evaluate.child;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/5/25 18:04
 */
public class ReplyChildAdapter extends SuperAdapter<String> {
    private Context mContext;
    public ReplyChildAdapter(Context context, List<String> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, String item) {
        ImageView iv_pic = holder.findViewById(R.id.iv_pic);
        Glide.with(mContext).load(item).into(iv_pic);
    }
}
