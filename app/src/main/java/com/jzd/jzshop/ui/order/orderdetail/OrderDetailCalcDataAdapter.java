package com.jzd.jzshop.ui.order.orderdetail;

import android.content.Context;
import android.text.TextUtils;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.OrderDetailEntity;
import com.jzd.jzshop.utils.Constants;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author LXB
 * @description: 各种优惠、抵扣、运费等数据包信息
 * @date :2020/1/14 13:49
 */
public class OrderDetailCalcDataAdapter extends SuperAdapter<OrderDetailEntity.ResultBean.CalcDataBean> {
    private Context mContext;
    private List<OrderDetailEntity.ResultBean.CalcDataBean> dataList;
    private double price;


    public OrderDetailCalcDataAdapter(Context context, List<OrderDetailEntity.ResultBean.CalcDataBean> items, String order_price,int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList= items;
        if(!TextUtils.isEmpty(order_price)){
        this.price=Double.valueOf(order_price).doubleValue();
        }
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, OrderDetailEntity.ResultBean.CalcDataBean item) {
        if (item != null) {
            switch (item.getId()) {
                case Constants.ORDER_DETAIL_CALC_ID_DISPATCH://运费
                    holder.setText(R.id.tv_tip, "运费");
                    break;
                case Constants.ORDER_DETAIL_CALC_ID_DISPATCH_CITY://同城运费
                    holder.setText(R.id.tv_tip,"同城运费");
                    break;
                case Constants.ORDER_DETAIL_CALC_ID_LOTTERY://抽奖优惠
                    holder.setText(R.id.tv_tip,"抽奖优惠");
                    break;
                case Constants.ORDER_DETAIL_CALC_ID_DEDUCTENOUGH://满额立减
                    holder.setText(R.id.tv_tip,"满额立减");
                    break;
                case Constants.ORDER_DETAIL_CALC_ID_COUPON://优惠券优惠
                    holder.setText(R.id.tv_tip,"优惠券优惠");
                    break;
                case Constants.ORDER_DETAIL_CALC_ID_CARD_DEC://会员卡优惠
                    holder.setText(R.id.tv_tip,"会员卡优惠");
                    break;
                case Constants.ORDER_DETAIL_CALC_ID_BUYAGAIN://重复购买优惠
                    holder.setText(R.id.tv_tip,"重复购买优惠");
                    break;
                case Constants.ORDER_DETAIL_CALC_ID_DISCOUNT://会员优惠
                    holder.setText(R.id.tv_tip,"会员优惠");
                    break;
                case Constants.ORDER_DETAIL_CALC_ID_ISDISCOUNT://促销优惠
                    holder.setText(R.id.tv_tip,"促销优惠");
                    break;
                case Constants.ORDER_DETAIL_CALC_ID_DEDUCT://积分抵扣
                    holder.setText(R.id.tv_tip,"积分抵扣");
                    break;
                case Constants.ORDER_DETAIL_CALC_ID_DEDUCT_MONEY://余额抵扣
                    holder.setText(R.id.tv_tip,"余额抵扣");
                    break;
                case Constants.ORDER_DETAIL_CALC_ID_SECKILL://秒杀优惠
                    holder.setText(R.id.tv_tip,"秒杀优惠");
                    break;
            }
            if (item.getType() != null) {
                DecimalFormat fnum = new DecimalFormat("##0.00");
                if (item.getType().equals("1")){//+
                    String format = fnum.format(item.getPrice());
                    holder.setText(R.id.tv_price, "+¥".concat(format));
                    price += item.getPrice();
                }else{
                    String format = fnum.format(item.getPrice());
                    holder.setText(R.id.tv_price, "-¥".concat(format));
                    price -= item.getPrice();
                }
            }
        }
    }


    public double getDfPrice(){
        return price;
    }
    public double getPrices(){
        for (OrderDetailEntity.ResultBean.CalcDataBean calcDataBean : dataList) {
            if(calcDataBean.getType().equals("1")){
                price+=calcDataBean.getPrice();
            }else if(calcDataBean.getType().equals("2")){
                price-=calcDataBean.getPrice();
            }
        }
       return price;
    }
}
