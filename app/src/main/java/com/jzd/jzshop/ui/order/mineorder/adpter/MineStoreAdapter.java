package com.jzd.jzshop.ui.order.mineorder.adpter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.MineOrderEntity;

import java.util.List;

import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LWH
 * @description:
 * @date :2020/1/10 16:25
 */
public class MineStoreAdapter extends RecyclerView.Adapter {


    private List<MineOrderEntity.ResultBean.DataBeanX.DataBean> dataBeans;

    public void SetList(List<MineOrderEntity.ResultBean.DataBeanX.DataBean> dataBeans) {
        this.dataBeans = dataBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_store_order, viewGroup, false);
        return new OrderStore(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        OrderStore store = (OrderStore) viewHolder;
        final MineOrderEntity.ResultBean.DataBeanX.DataBean dataBean = dataBeans.get(i);
        if (!TextUtils.isEmpty(dataBean.getMerch_name())) {
            store.tvstore.setText(dataBean.getMerch_name());
        }
        MineGoodAdapter mineGoodAdapter = new MineGoodAdapter();
        mineGoodAdapter.SetList(dataBean.getGoods());
        store.recycle.setAdapter(mineGoodAdapter);
        mineGoodAdapter.setOnitemClick(new MineGoodAdapter.OnitemClick() {
            @Override
            public void OnitemClick(MineOrderEntity.ResultBean.DataBeanX.DataBean.GoodsBean bean) {
                if (onitemClick != null) {
                    onitemClick.OnitemClick(bean);
                }
            }
        });
        store.recycle.setLayoutManager(new LinearLayoutManager(store.recycle.getContext()));
        store.store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onitemStoreClick != null) {
                    onitemStoreClick.onitemClick(dataBean);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if (dataBeans != null) {
            return dataBeans.size();
        }
        return 0;
    }


    public class OrderStore extends RecyclerView.ViewHolder {
        private TextView tvstore;
        private RecyclerView recycle;
        private ConstraintLayout store;

        public OrderStore(@NonNull View itemView) {
            super(itemView);
            tvstore = itemView.findViewById(R.id.tv_store);
            recycle = itemView.findViewById(R.id.recycles);
            store = itemView.findViewById(R.id.store);
        }
    }

    public interface OnitemClick {
        void OnitemClick(MineOrderEntity.ResultBean.DataBeanX.DataBean.GoodsBean beann);
    }

    private OnitemClick onitemClick;

    public void setOnitemClick(OnitemClick listeren) {
        this.onitemClick = listeren;
    }


    private OnitemStoreClick onitemStoreClick;

    public interface OnitemStoreClick {
        void onitemClick(MineOrderEntity.ResultBean.DataBeanX.DataBean dataBean);
    }

    public void setonStoreClick(OnitemStoreClick listeren) {
        this.onitemStoreClick = listeren;
    }

}
