package com.jzd.jzshop.ui.order.mineorder.adpter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.MineOrderEntity;

import java.util.List;

/**
 * @author LWH
 * @description:
 * @date :2020/1/10 16:57
 */
public class MineGoodAdapter extends RecyclerView.Adapter {

    private List<MineOrderEntity.ResultBean.DataBeanX.DataBean.GoodsBean> dataBeans;

    public void SetList(List<MineOrderEntity.ResultBean.DataBeanX.DataBean.GoodsBean> dataBeans) {
        this.dataBeans = dataBeans;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_goods, viewGroup,false);

        return new MyGoods(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyGoods myGoods = (MyGoods) viewHolder;
        final MineOrderEntity.ResultBean.DataBeanX.DataBean.GoodsBean goodsBean = dataBeans.get(i);
        Glide.with(myGoods.good.getContext()).load(goodsBean.getThumb()).into(myGoods.good);
        myGoods.number.setText("X"+goodsBean.getNumber());
        myGoods.spec.setText("已选: "+goodsBean.getSpec_title());
        myGoods.name.setText(goodsBean.getTitle());
        myGoods.money.setText(goodsBean.getPrice());
        myGoods.itemgood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onitemClick!=null){
                    onitemClick.OnitemClick(goodsBean);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }


    public class MyGoods extends RecyclerView.ViewHolder {

        ImageView good;
        TextView name, spec, money, number;
        ConstraintLayout itemgood;

        public MyGoods(@NonNull View itemView) {
            super(itemView);
            good = itemView.findViewById(R.id.iv_good);
            name = itemView.findViewById(R.id.tv_goodname);
            spec = itemView.findViewById(R.id.tv_goodspec);
            money = itemView.findViewById(R.id.tv_money);
            number = itemView.findViewById(R.id.tv_number);
            itemgood=itemView.findViewById(R.id.con_good);
        }
    }

    public interface OnitemClick {
        void OnitemClick(MineOrderEntity.ResultBean.DataBeanX.DataBean.GoodsBean bean);
    }

    private OnitemClick onitemClick;

    public void setOnitemClick(OnitemClick listeren) {
        this.onitemClick = listeren;
    }



}
