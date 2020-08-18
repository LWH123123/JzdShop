package com.jzd.jzshop.ui.goods.evaluate;

import android.content.Context;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.GoodsEvaluationTypeEntity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2019/12/21 15:10
 */
public class GoodsEvaluationTypeAdapter extends SuperAdapter<GoodsEvaluationTypeEntity> {
    private Context mContext;
    private int position = 0;
    public GoodsEvaluationTypeAdapter(Context context, List<GoodsEvaluationTypeEntity> items, int layoutResId) {
        super(context, items, layoutResId);
        mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, GoodsEvaluationTypeEntity item) {
        holder.setText(R.id.tv_name,item.getType());
        TextView tv_name = holder.findViewById(R.id.tv_name);
        if (position == layoutPosition) {//选中位置
            tv_name.setBackgroundResource(R.drawable.selector_btn_corners_rect_good_evaluation);
            tv_name.setTextColor(mContext.getResources().getColor(R.color.orange));
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
