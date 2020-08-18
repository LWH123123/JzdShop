package com.jzd.jzshop.ui.order.orderdetail;

import android.content.Context;
import android.text.TextUtils;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.OrderDetailEntity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description: 快递信息
 * @date :2020/1/15 14:39
 */
    public class OrderDetailExpressAdapter extends SuperAdapter<OrderDetailEntity.ResultBean.ExpressBean> {
        private Context mContext;
        private List<OrderDetailEntity.ResultBean.ExpressBean> dataList;

        public OrderDetailExpressAdapter(Context context, List<OrderDetailEntity.ResultBean.ExpressBean> items, int layoutResId) {
            super(context, items, layoutResId);
            this.mContext = context;
            this.dataList = items;
        }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, OrderDetailEntity.ResultBean.ExpressBean item) {
        if (item != null) {
            if (!TextUtils.isEmpty(item.getExpresscom()) && !TextUtils.isEmpty(item.getExpresssn())){
                holder.setText(R.id.tv_express_name, "快递公司："+item.getExpresscom());
                holder.setText(R.id.tv_expressCode, "快递单号："+item.getExpresssn());
            }
        }
    }
}
