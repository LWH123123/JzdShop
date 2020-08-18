package com.jzd.jzshop.chat;

import android.content.Context;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.GoodsEvaluationTypeEntity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:  客服对话 发送 热词关键字
 * @date :2020/4/23 11:22
 */
public class ChatHotKeyAdapter extends SuperAdapter<String> {
    private Context mContext;

    public ChatHotKeyAdapter(Context context, List<String> items, int layoutResId) {
        super(context, items, layoutResId);
        mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, String item) {
        holder.setText(R.id.tv_name,item);
        TextView tv_name = holder.findViewById(R.id.tv_name);
    }
}
