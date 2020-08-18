package com.jzd.jzshop.ui.mine.assets;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.AssetsRecordEntity;
import com.jzd.jzshop.utils.MoneyFormatUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Created by lxb on 2020/2/11.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class AssetsRecordPageFragAdapter extends SuperAdapter<AssetsRecordEntity.ResultBean.AssetsRecordBean> {
    private Context mContext;
    private List<AssetsRecordEntity.ResultBean.AssetsRecordBean> dataList;
    private TextView tv_state, tv_result_state;
    private boolean isHide = true;
    private AssetsRecoAllFragmViewModel viewModel;

    public AssetsRecordPageFragAdapter(Context context, AssetsRecoAllFragmViewModel viewModel, List<AssetsRecordEntity.ResultBean.AssetsRecordBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.dataList = items;
        mContext = context;
        this.viewModel = viewModel;
    }

    @Override
    public void onBind(final SuperViewHolder holder, int viewType, int layoutPosition, final AssetsRecordEntity.ResultBean.AssetsRecordBean item) {
        String commission = MoneyFormatUtils.keepTwoDecimal(item.getCommission()); //申请提现金额
        holder.setText(R.id.tv_commission, "+".concat(commission));
        //--------------goods-------------start--------------------
        RecyclerView recycleChild = holder.findViewById(R.id.recycle);
        List<AssetsRecordEntity.ResultBean.AssetsRecordBean.GoodBean> goods = item.getGoods();
        if (goods != null && goods.size() > 0) {
            recycleChild.setVisibility(View.VISIBLE);
            AssetsRecordItemChildAdapter adapter = new AssetsRecordItemChildAdapter(
                    mContext, goods, R.layout.item_rv_assets_record_child_list_item);
            recycleChild.setAdapter(adapter);
            recycleChild.setLayoutManager(new LinearLayoutManager(mContext));
        } else {
            recycleChild.setVisibility(View.GONE);
        }
        //--------------goods-------------end--------------------
        holder.setText(R.id.tv_ordersn, "订单编号：".concat(item.getOrdersn()));
        holder.setText(R.id.tv_time, "下单时间：".concat(item.getCreatetime()));
    }
}
