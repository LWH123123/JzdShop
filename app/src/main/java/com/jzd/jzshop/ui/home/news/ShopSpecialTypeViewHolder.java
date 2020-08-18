package com.jzd.jzshop.ui.home.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.HomeEntity;
import com.jzd.jzshop.entity.ShopSpecialEntity;
import com.jzd.jzshop.ui.base.viewholder.TypeAbstractViewHolder;
import com.jzd.jzshop.ui.home.AppIdentityJumpUtils;
import com.jzd.jzshop.utils.NetworkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description:  撰写 橱窗样式, 通过 recycleview  直接对 item 操作不可行，此方案失败！！
 * @date :2020/3/29 14:36
 */
class ShopSpecialTypeViewHolder extends TypeAbstractViewHolder<ShopSpecialEntity> implements View.OnClickListener {
    @BindView(R.id.shop_special_one)
    ConstraintLayout shop_special_one;
    @BindView(R.id.shop_special_two)
    ConstraintLayout shop_special_two;
    @BindView(R.id.shop_special_three)
    ConstraintLayout shop_special_three;
    @BindView(R.id.shop_special_four)
    ConstraintLayout shop_special_four;

    @BindView(R.id.iv_shop_special_left)
    AppCompatImageView iv_left;
    @BindView(R.id.iv_shop_special_right_top)
    AppCompatImageView iv_right_top;
    @BindView(R.id.iv_shop_special_right_bottom_one)
    AppCompatImageView iv_right_bottom_one;
    @BindView(R.id.iv_shop_special_right_bottom_two)
    AppCompatImageView iv_right_bottom_two;
    private Context context;
    private HomePageViewModel viewModel;
    private List<HomeEntity.ResultBean.DataBeanX.DataBean> data;
    @BindView(R.id.consl_root)
    ConstraintLayout consl_root;

    public ShopSpecialTypeViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindHolder(ShopSpecialEntity model, Context mContext) {

    }

    @Override
    public void bindHolder(ShopSpecialEntity model, Context mContext, Object viewModel) {
        super.bindHolder(model, mContext, viewModel);
        this.context = mContext;
        this.viewModel = (HomePageViewModel) viewModel;
        data = model.getData();
        if (data != null) {
            if (data.size() == 1) {
                shop_special_one.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(data.get(0).getImgurl()).into(iv_left);
            } else if (data.size() == 2) {
                shop_special_two.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(data.get(0).getImgurl()).into(iv_left);
                Glide.with(mContext).load(data.get(1).getImgurl()).into(iv_right_top);
            } else if (data.size() == 3) {
                shop_special_three.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(data.get(0).getImgurl()).into(iv_left);
                Glide.with(mContext).load(data.get(1).getImgurl()).into(iv_right_top);
                Glide.with(mContext).load(data.get(2).getImgurl()).into(iv_right_bottom_one);
            } else if (data.size() == 4) {
                shop_special_four.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(data.get(0).getImgurl()).into(iv_left);
                Glide.with(mContext).load(data.get(1).getImgurl()).into(iv_right_top);
                Glide.with(mContext).load(data.get(2).getImgurl()).into(iv_right_bottom_one);
                Glide.with(mContext).load(data.get(3).getImgurl()).into(iv_right_bottom_two);
            } else {
            }
        }

        iv_left.setOnClickListener(this);
        iv_right_top.setOnClickListener(this);
        iv_right_bottom_one.setOnClickListener(this);
        iv_right_bottom_two.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_shop_special_left:
                String url1 = data.get(0).getLinkurl();
                getLinkUrl(url1);
                break;
            case R.id.iv_shop_special_right_top:
                String url2 = data.get(1).getLinkurl();
                getLinkUrl(url2);
                break;
            case R.id.iv_shop_special_right_bottom_one:
                String url3 = data.get(2).getLinkurl();
                getLinkUrl(url3);
                break;
            case R.id.iv_shop_special_right_bottom_two:
                String url4 = data.get(3).getLinkurl();
                getLinkUrl(url4);
                break;
        }
    }

    /**
     * menu 跳转
     *
     * @param linkUrl
     */
    public void getLinkUrl(String linkUrl) {
        int netState = NetworkUtils.getNetWorkState(context);
        if (netState == -1) {
            ToastUtils.showLong("网络连接异常");
            return;
        }
        AppIdentityJumpUtils.homeMenujumpLinkUrl(linkUrl, viewModel, context);
    }
}
