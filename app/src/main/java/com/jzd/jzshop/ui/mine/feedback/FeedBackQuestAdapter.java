package com.jzd.jzshop.ui.mine.feedback;

import android.content.Context;
import android.widget.TextView;

import com.jzd.jzshop.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by lxb on 2020/2/10.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class FeedBackQuestAdapter extends SuperAdapter<String> {
    private Context mContext;
    private int position = 0;

    public FeedBackQuestAdapter(Context context, List<String> items, int layoutResId) {
        super(context, items, layoutResId);
        mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, String item) {
        holder.setText(R.id.tv_name,item);
        TextView tv_name = holder.findViewById(R.id.tv_name);
        if (position == layoutPosition) {//选中位置
            tv_name.setBackgroundResource(R.drawable.selector_btn_corners_rect);
            tv_name.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            tv_name.setBackgroundResource(R.drawable.selector_btn_corners_rect_border);
            tv_name.setTextColor(mContext.getResources().getColor(R.color.textColor));
        }
    }


    /**
     * 设置默认选中位置
     * @param pos
     */
    public void setSelectedPosition(int pos) {
        this.position = pos;
        notifyDataSetChanged();
    }
}
