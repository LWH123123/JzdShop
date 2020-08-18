package com.jzd.jzshop.ui.order.orderdetail;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.MineOrderEntity;
import com.jzd.jzshop.entity.OrderDetailEntity;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.utils.Constants;

import org.byteam.superadapter.IMulItemViewType;
import org.byteam.superadapter.OnItemClickListener;
import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/1/13 11:10
 */
public class OrderDetailAdapter extends SuperAdapter<OrderDetailEntity.ResultBean.DataBean> {
    private Context context;
    private OrderDetailViewModel viewModel;
    private List<OrderDetailEntity.ResultBean.DataBean> dataLists;
    //多类型item
    private static final int ITEM_TYPE_ONE = 0;
    private static final int ITEM_TYPE_TWO = 1;
    private double dfPrice;

    public OrderDetailAdapter(Context context, OrderDetailViewModel viewModel, List<OrderDetailEntity.ResultBean.DataBean> items, IMulItemViewType mulItemViewType) {
        super(context, items, mulItemViewType);
        this.context = context;
        this.viewModel = viewModel;
        this.dataLists = items;
    }


    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final OrderDetailEntity.ResultBean.DataBean item) {
        switch (viewType) {
            case 1:
                holder.setText(R.id.tv_store, item.getMerch_name());
                holder.findViewById(R.id.view_space).setVisibility(View.GONE);
                final List<OrderDetailEntity.ResultBean.DataBean.GoodsBean> goods = item.getGoods();
                RecyclerView recycleChild = holder.findViewById(R.id.recycles);
                if (goods != null && goods.size() > 0) {
                    recycleChild.setLayoutManager(new LinearLayoutManager(context));
                    OrderDetailChildAdapter adapter = new OrderDetailChildAdapter(context, goods, R.layout.item_order_goods);
                    recycleChild.setAdapter(adapter);
                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View itemView, int viewType, int position) {
                            Log.d("onItemClick", "" + position);
                            Bundle bundle = new Bundle();
                            bundle.putString("gid",String.valueOf(goods.get(position).getGid()));
                            bundle.putString(Constants.GOODS_OPEN_FLAG, "0");
                            viewModel.startActivity(GoodsActivity.class,bundle);
                        }
                    });
                    dfPrice = adapter.getDfPrice();
                    setDfPrice(dfPrice);
                } else {
                }
                break;
//            case 1:
//                List<MineOrderEntity.ResultBean.DataBeanX.DataBean.GoodsBean> goods = item.getGoods();
//                RecyclerView recycleChild = holder.findViewById(R.id.recycle);
//                if (goods != null && goods.size() > 0) {
//                    recycleChild.setLayoutManager(new LinearLayoutManager(context));
//                    OrderDetailChildAdapter adapter = new OrderDetailChildAdapter(context, goods, R.layout.item_order_goods);
//                    recycleChild.setAdapter(adapter);
//                } else {
//                }
//                break;
        }
    }

    @Override
    protected IMulItemViewType<OrderDetailEntity.ResultBean.DataBean> offerMultiItemViewType() {
        return new IMulItemViewType<OrderDetailEntity.ResultBean.DataBean>() {
            @Override
            public int getViewTypeCount() {
//                return 2;
                return 1;
            }

            @Override
            public int getItemViewType(int position, OrderDetailEntity.ResultBean.DataBean mockModel) {
//                if (position % 2 == 0) {
//                    return 0;
//                }
                return 1;
            }

            @Override
            public int getLayoutId(int viewType) {
//                if (viewType == 0) {
//                    return R.layout.item_store_order; //type  one
//                }
//                return R.layout.item_mineorder; //type  two
                return R.layout.item_store_order;
            }
        };
    }


    private void setDfPrice(double dfPrice){
        this.dfPrice = dfPrice;
    }

    public double getDfPrice(){
        return dfPrice;
    }


}
