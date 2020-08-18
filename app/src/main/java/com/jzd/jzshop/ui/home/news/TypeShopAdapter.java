package com.jzd.jzshop.ui.home.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.HomeEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author LXB
 * @description: 多类型布局 数据错乱采取  方案二-----商品item 通过嵌套recycleview 写
 *   问题解决，方案二后期商品列表返回多个数组时可采用
 * @date :2020/1/6 16:36
 */
@Deprecated
public class TypeShopAdapter extends RecyclerView.Adapter<TypeShopAdapter.TypeShopHolder> {
    private Context mContext;
//    private List<HomeEntity.ResultBean.DataBeanX> mHomeShop;
    private HomeEntity.ResultBean.DataBeanX mHomeShop;
    private LayoutInflater inflater;


    public TypeShopAdapter(Context mContext,/* List<HomeEntity.ResultBean.DataBeanX> data */HomeEntity.ResultBean.DataBeanX data) {
        this.mContext = mContext;
        this.mHomeShop = data;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public TypeShopAdapter.TypeShopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TypeShopAdapter.TypeShopHolder(inflater.inflate(R.layout.item_home_shop, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TypeShopAdapter.TypeShopHolder holder, final int position) {
        HomeEntity.ResultBean.DataBeanX dataBeanX = mHomeShop;
//        HomeEntity.ResultBean.DataBeanX dataBeanX = mHomeShop.get(position);
        holder.shopeName.setText(dataBeanX.getTitle());
        holder.shopePrice.setText(String.valueOf(dataBeanX.getPrice()));
        if ((dataBeanX.getSeckill() == 0)) holder.seckills.setVisibility(View.GONE);
        else holder.seckills.setVisibility(View.VISIBLE); //秒杀
        if ((dataBeanX.getEnoughs() == 0)) holder.fullReduction.setVisibility(View.GONE);
        else holder.fullReduction.setVisibility(View.VISIBLE); //满减
        if ((dataBeanX.getSalegit() == 0)) holder.fullGift.setVisibility(View.GONE);
        else holder.fullGift.setVisibility(View.VISIBLE); //满赠
        Glide.with(mContext).load(dataBeanX.getThumb()).into(holder.shopIcon);
        holder.shopIcon.setTag(dataBeanX.getThumb());

    }

    @Override
    public int getItemCount() {
        return mHomeShop == null ? 0 : /*mHomeShop.size()*/1;
    }

    static class TypeShopHolder extends RecyclerView.ViewHolder {
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

        public TypeShopHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type){
                        default:
                            return 6;
                    }
                }
            });
        }
    }
}
