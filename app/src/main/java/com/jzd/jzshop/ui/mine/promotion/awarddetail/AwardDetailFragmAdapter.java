package com.jzd.jzshop.ui.mine.promotion.awarddetail;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.AwarddetailEntity;
import com.jzd.jzshop.utils.MoneyFormatUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by lxb on 2020/2/14.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class AwardDetailFragmAdapter extends SuperAdapter<AwarddetailEntity.ResultBean.AwardDetailBean> {
    private Context mContext;
    private List<AwarddetailEntity.ResultBean.AwardDetailBean> dataList;

    public AwardDetailFragmAdapter(Context context, List<AwarddetailEntity.ResultBean.AwardDetailBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.dataList = items;
        mContext = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, AwarddetailEntity.ResultBean.AwardDetailBean item) {
        String commission = MoneyFormatUtils.keepTwoDecimal(item.getCommission()); //申请提现金额
        holder.setText(R.id.tv_commission, "申请奖励：".concat(commission));
        //--------------goods-------------start--------------------
        RecyclerView recycleChild = holder.findViewById(R.id.recycle);
        List<AwarddetailEntity.ResultBean.AwardDetailBean.GoodBean> goods = item.getGoods();
        if (goods != null && goods.size() > 0) {
            recycleChild.setVisibility(View.VISIBLE);
            AwardDetailItemChildAdapter adapter = new AwardDetailItemChildAdapter(
                    mContext, goods, R.layout.item_rv_award_detail_child_list_item);
            recycleChild.setAdapter(adapter);
            recycleChild.setLayoutManager(new LinearLayoutManager(mContext));
        } else {
            recycleChild.setVisibility(View.GONE);
        }
        //--------------goods-------------end--------------------
        holder.setText(R.id.tv_ordersn, "订单编号：".concat(item.getOrdersn()));
        String money = MoneyFormatUtils.keepTwoDecimal(item.getMoney());
        holder.setText(R.id.tv_actual_amount, "实付金额：".concat(money).concat("元"));
        holder.setText(R.id.tv_remarks, "备注：".concat(""));

    }
}
