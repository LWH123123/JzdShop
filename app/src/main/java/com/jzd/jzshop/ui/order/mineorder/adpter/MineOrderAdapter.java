package com.jzd.jzshop.ui.order.mineorder.adpter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.MineOrderEntity;
import com.jzd.jzshop.ui.order.mineorder.MineOrderViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LWH
 * @description:
 * @date :2020/1/10 14:41
 */
public class MineOrderAdapter extends RecyclerView.Adapter {

    private List<MineOrderEntity.ResultBean.DataBeanX> resultBean =new ArrayList<>();

    private MineOrderViewModel viewModel;
    public MineOrderAdapter(MineOrderViewModel viewModel) {
        this.viewModel=viewModel;
    }

    public void setList(List<MineOrderEntity.ResultBean.DataBeanX> resultBean) {
        if(resultBean.size()>0) {
            this.resultBean.addAll(resultBean);
            notifyDataSetChanged();
        }
    }

    public void clearData(){
        if(this.resultBean.size()>0) {
            this.resultBean.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mineorder, null);
        return new order(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        order o = (order) viewHolder;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(o.recycle.getContext());
        linearLayoutManager.setSmoothScrollbarEnabled(false);
        o.recycle.setLayoutManager(linearLayoutManager);
        o.recycle.setNestedScrollingEnabled(false);
        o.recycle.setClickable(false);
        final MineOrderEntity.ResultBean.DataBeanX dataBeanX = resultBean.get(i);
        MineShopAdapter mineShopAdapter = new MineShopAdapter(viewModel);
        mineShopAdapter.SetList(dataBeanX);
        o.recycle.setAdapter(mineShopAdapter);

        mineShopAdapter.setonDeleteClick(new MineShopAdapter.OnitemDeleteClick() {
            @Override
            public void onitemDeleteClick(MineOrderEntity.ResultBean.DataBeanX dataBean) {
                boolean contains = resultBean.contains(dataBean);
                if(contains){
                    resultBean.remove(dataBean);
                    notifyDataSetChanged();
                }

            }
        });
        mineShopAdapter.setOnitemClick(new MineShopAdapter.OnitemClick() {
            @Override
            public void OnitemClick(MineOrderEntity.ResultBean.DataBeanX.DataBean.GoodsBean bean) {
                if(onitemClick!=null){
                    onitemClick.OnitemClick(bean);
                }
            }
        });

        mineShopAdapter.setonStoreClick(new MineShopAdapter.OnitemStoreClick() {
            @Override
            public void onitemClick(MineOrderEntity.ResultBean.DataBeanX.DataBean dataBean) {
                if(onitemStoreClick!=null){
                    onitemStoreClick.onitemClick(dataBean);
                }
            }
        });


    }

    public List<MineOrderEntity.ResultBean.DataBeanX> getData(){
        if(this.resultBean!=null&&resultBean.size()!=0){
           return resultBean;
        }
        return null;
    }



    @Override
    public int getItemCount() {
        if(resultBean==null||resultBean.size()==0){
            return 0;
        }
        return resultBean.size();
    }


    public class order extends RecyclerView.ViewHolder {
        RecyclerView recycle;

        public order(@NonNull View itemView) {
            super(itemView);
            recycle = itemView.findViewById(R.id.recycle);
        }
    }

    public interface OnitemClick {
        void OnitemClick(MineOrderEntity.ResultBean.DataBeanX.DataBean.GoodsBean bean);
    }

    private OnitemClick onitemClick;

    public void setOnitemClick(OnitemClick listeren) {
        this.onitemClick = listeren;
    }





    private OnitemStoreClick onitemStoreClick;
    public interface OnitemStoreClick{
        void onitemClick(MineOrderEntity.ResultBean.DataBeanX.DataBean dataBean);
    }

    public void setonStoreClick(OnitemStoreClick listeren) {
        this.onitemStoreClick = listeren;
    }



}
