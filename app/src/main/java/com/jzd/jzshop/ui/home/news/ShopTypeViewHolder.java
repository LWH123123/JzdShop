package com.jzd.jzshop.ui.home.news;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.HomeEntity;
import com.jzd.jzshop.ui.base.viewholder.TypeAbstractViewHolder;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.MoneyFormatUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * @author LXB
 * @description:
 * @date :2020/1/7 10:38
 */
class ShopTypeViewHolder extends TypeAbstractViewHolder<HomeEntity.ResultBean.DataBeanX> {
    @BindView(R.id.consl_root)
    ConstraintLayout constraintLayout;
    @BindView(R.id.pic)
    ImageView shopIcon;
    @BindView(R.id.viewSeckills)
    View seckills;
    @BindView(R.id.name)
    TextView shopeName;
    @BindView(R.id.price)
    TextView shopePrice;
    @BindView(R.id.tv_full_reduction)
    TextView fullReduction;
    @BindView(R.id.tv_full_gift)
    TextView fullGift;

    public ShopTypeViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindHolder(final HomeEntity.ResultBean.DataBeanX dataBeanX, final Context mContext) {
        shopeName.setText(dataBeanX.getTitle());
//        shopePrice.setText(MoneyFormatUtils.keepTwoDecimal(dataBeanX.getPrice()));
        shopePrice.setText(dataBeanX.getPrice());
        if ((dataBeanX.getSeckill() == 0)) seckills.setVisibility(View.GONE);
        else seckills.setVisibility(View.VISIBLE); //秒杀
        if ((dataBeanX.getEnoughs() == 0)) fullReduction.setVisibility(View.GONE);
        else fullReduction.setVisibility(View.VISIBLE); //满减
        if ((dataBeanX.getSalegit() == 0)) fullGift.setVisibility(View.GONE);
        else fullGift.setVisibility(View.VISIBLE); //满赠
        Glide.with(mContext).load(dataBeanX.getThumb()).into(shopIcon);
        shopIcon.setTag(dataBeanX.getThumb());
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString(Constants.GOODS_KEY_GID,dataBeanX.getGid());
                bundle.putString(Constants.GOODS_OPEN_FLAG, "0");
                ((HomePageActivity)mContext).startActivity(GoodsActivity.class,bundle);
            }
        });

    }
}

