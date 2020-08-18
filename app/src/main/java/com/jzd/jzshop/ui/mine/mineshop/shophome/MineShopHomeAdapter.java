package com.jzd.jzshop.ui.mine.mineshop.shophome;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.MineStoreEntity;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.SystemUtils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lwh
 * @description :
 * @date 2020/4/7.
 */
public class MineShopHomeAdapter extends SuperAdapter<MineStoreEntity.ResultBean.DataBean.GoodsBean> {


    private Context context;
    private List<MineStoreEntity.ResultBean.DataBean.GoodsBean> data;
    private MineShopHomeViewModel viewModel;
    private String shopid;

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public void addData(List<MineStoreEntity.ResultBean.DataBean.GoodsBean> items){
        /*ArrayList<MineStoreEntity.ResultBean.DataBean.GoodsBean> list = new ArrayList<>();
        list.addAll(this.data);
        clear();
        this.data.clear();
        list.addAll(items);*/
        this.data.addAll(items);
        notifyDataSetChanged();
    }


    public MineShopHomeAdapter(Context context, List<MineStoreEntity.ResultBean.DataBean.GoodsBean> items, MineShopHomeViewModel viewModel, int layoutResId) {
        super(context, items, layoutResId);
        this.context=context;
        this.viewModel=viewModel;
        this.data=items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final MineStoreEntity.ResultBean.DataBean.GoodsBean item) {
        holder.setText(R.id.name, item.getTitle());
        holder.setText(R.id.price,item.getPrice());
        holder.setVisibility(R.id.tv_full_reduction, View.GONE);
        holder.setVisibility(R.id.tv_full_gift, View.GONE);
        holder.setVisibility(R.id.viewSeckills, View.GONE);
        ImageView logo = holder.findViewById(R.id.pic);
//        Glide.with(context).load(item.getThumb()).into(logo);
        SystemUtils.setPicTransition(context,item.getThumb(),logo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.GOODS_KEY_GID,item.getGid());
                bundle.putString(Constants.SHOP_GOODS,shopid);
                viewModel.startActivity(GoodsActivity.class,bundle);
            }
        });
    }
}
