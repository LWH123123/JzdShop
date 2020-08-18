package com.jzd.jzshop.ui.mine.assets;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.AssetsRecordEntity;
import com.jzd.jzshop.utils.MoneyFormatUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by lxb on 2020/2/19.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class AssetsRecordItemChildAdapter extends SuperAdapter<AssetsRecordEntity.ResultBean.AssetsRecordBean.GoodBean> {
    private Context mContext;
    private List<AssetsRecordEntity.ResultBean.AssetsRecordBean.GoodBean> dataList;

    public AssetsRecordItemChildAdapter(Context context, List<AssetsRecordEntity.ResultBean.AssetsRecordBean.GoodBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.dataList = items;
        mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, AssetsRecordEntity.ResultBean.AssetsRecordBean.GoodBean item) {
        String commission = MoneyFormatUtils.keepTwoDecimal(item.getCommission());
        holder.setText(R.id.tv_award_amount, "+".concat(commission).concat("元"));
        ImageView iv_good = holder.findViewById(R.id.iv_good);
        Glide.with(mContext).load(item.getThumb()).into(iv_good);
        holder.setText(R.id.tv_goodname, item.getTitle());
        holder.setText(R.id.tv_number, "x".concat(item.getNumber()));
    }
}
