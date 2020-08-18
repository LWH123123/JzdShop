package com.jzd.jzshop.ui.home.news;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.HomeGoodsEntity;
import com.jzd.jzshop.utils.SystemUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description: 首页 商品组 分页
 * @date :2020/5/18 15:57
 */
public class HomeGoodsAdapter extends SuperAdapter<HomeGoodsEntity.ResultBean.DataBean> {
    private Context mContext;
    private List<HomeGoodsEntity.ResultBean.DataBean> dataList;

    public HomeGoodsAdapter(Context context, List<HomeGoodsEntity.ResultBean.DataBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.dataList = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, HomeGoodsEntity.ResultBean.DataBean item) {
        holder.setText(R.id.name,item.getTitle());
        holder.setText(R.id.price,item.getPrice());
//        if ((item.getEnoughs() == 0)) fullReduction.setVisibility(View.GONE);
//        else fullReduction.setVisibility(View.VISIBLE); //满减
//        if ((item.getSalegit() == 0)) fullGift.setVisibility(View.GONE);
//        else fullGift.setVisibility(View.VISIBLE); //满赠
        ImageView shopIcon = holder.findViewById(R.id.pic);
        if (!TextUtils.isEmpty(item.getThumb())){
            SystemUtils.setPicTransition(mContext,item.getThumb(),shopIcon);
        }
        //销量
        TextView tv_sales_real = holder.findViewById(R.id.tv_sales_real);
        if (item.getSales_real() == 0){
            tv_sales_real.setVisibility(View.GONE);
        }else {
            tv_sales_real.setVisibility(View.GONE);
            tv_sales_real.setText("销量 "+String.valueOf(item.getSales_real()));
        }
        //是否包邮 0：否 1：是
        TextView tv_sendfree = holder.findViewById(R.id.tv_sendfree);
        if ((item.getIs_sendfree() == 0)) tv_sendfree.setVisibility(View.GONE);
        else tv_sendfree.setVisibility(View.VISIBLE);
        //是否新品
        TextView tv_new = holder.findViewById(R.id.tv_new);
        if ((item.getIs_new() == 0)) tv_new.setVisibility(View.GONE);
        else tv_new.setVisibility(View.VISIBLE);

    }
}
