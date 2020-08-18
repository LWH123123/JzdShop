package com.jzd.jzshop.ui.order.orderdetail;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.MineOrderEntity;
import com.jzd.jzshop.entity.OrderDetailEntity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/1/14 9:08
 */
public class OrderDetailChildAdapter extends SuperAdapter<OrderDetailEntity.ResultBean.DataBean.GoodsBean> {
    private Context mContext;
    private List<OrderDetailEntity.ResultBean.DataBean.GoodsBean> dataList;
    private double totalPrice;

    public OrderDetailChildAdapter(Context context, List<OrderDetailEntity.ResultBean.DataBean.GoodsBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition,OrderDetailEntity.ResultBean.DataBean.GoodsBean item) {
        holder.setText(R.id.tv_goodname, item.getTitle());
        holder.setText(R.id.tv_goodspec, item.getSpec_title());
        holder.setText(R.id.tv_money, item.getPrice());
        ImageView iv_good = holder.findViewById(R.id.iv_good);
        Glide.with(mContext).load(item.getThumb()).into(iv_good);
        holder.setText(R.id.tv_number, "x".concat(String.valueOf(item.getNumber())));
        // todo 本地计算商品小计，暂未使用
        if (item.getPrice() != null) {
                String replace = item.getPrice().replace(",", "");
                Double iPrice = Double.parseDouble(replace);
                double dfPrice = iPrice * item.getNumber();
                totalPrice = dfPrice+ totalPrice;
                setTotalPrice(totalPrice);
        }
    }

    public void setTotalPrice(double totalPrice){
        this.totalPrice= totalPrice;
    }

    public double getDfPrice(){
        return totalPrice;
    }
}
