package com.jzd.jzshop.ui.message;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.MessageCenterChatEntity;
import com.shehuan.niv.NiceImageView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import q.rorbin.badgeview.QBadgeView;

/**
 * @author LXB
 * @description:
 * @date :2020/4/26 12:11
 */
public class MessageCenterChatAdapter extends SuperAdapter<MessageCenterChatEntity.ResultBean.DataBean> {
    private Context mContext;
    private List<MessageCenterChatEntity.ResultBean.DataBean> dataList;

    public MessageCenterChatAdapter(Context context, List<MessageCenterChatEntity.ResultBean.DataBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, MessageCenterChatEntity.ResultBean.DataBean item) {
        holder.setText(R.id.tv_title, item.getName());
        TextView tv_desc = holder.findViewById(R.id.tv_desc);
        //过滤文本中携带的html 格式
        if (!TextUtils.isEmpty(item.getMsg())) {
            String msgN = item.getMsg().trim();
            final Spanned spanned = Html.fromHtml(msgN, null, null);
            String trim = spanned.toString().trim();
//            holder.setText(R.id.tv_desc, trim);
            tv_desc.setText(Html.fromHtml(trim));

        }
        holder.setText(R.id.tv_time, item.getTime());
        NiceImageView iv_icon = holder.findViewById(R.id.iv_icon);
        Glide.with(mContext).load(item.getLogo()).into(iv_icon);
        TextView tv_number = holder.findViewById(R.id.tv_number);
        //显示消息数
        tv_number.setVisibility(View.GONE);
        new QBadgeView(mContext).bindTarget(tv_number)
                .setBadgeNumber(item.getTips_cnt())
                .setBadgeTextSize(14f,true)
                .setBadgeBackgroundColor(mContext.getResources().getColor(R.color.color_bg_message));
    }
}
