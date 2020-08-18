package com.jzd.jzshop.ui.order.logistics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.LogisticInfoEntity;
import com.jzd.jzshop.entity.LogisticListEntity;

import java.util.List;

/**
 * @author LWH
 * @description:
 * @date :2020/1/16 18:34
 */
public class LogisticAdapter extends RecyclerView.Adapter {

    private List<LogisticInfoEntity.ResultBean.DataBean> resultBean;

    private Context context;
    public void setList(List<LogisticInfoEntity.ResultBean.DataBean> resultBean, Context context) {
        this.resultBean = resultBean;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_logisticinfo, viewGroup,false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder view = (MyViewHolder) viewHolder;
        LogisticInfoEntity.ResultBean.DataBean dataBean = resultBean.get(i);
        String[] s = dataBean.getTime().split(" ");
        String[] split = s[0].split("-");
        view.data.setText(split[1] + "-"+ split[2]);
//        view.data.setText(s[0]);
        String[] split1 = s[1].split(":");
        view.time.setText(split1[0] + ":"+split1[1]);
//          view.time.setText(s[1]);
        view.address.setText(dataBean.getStep());
        if(i==0){
            view.tvother.setVisibility(View.GONE);
            view.tvFrist.setVisibility(View.VISIBLE);
        }else {
            view.tvother.setVisibility(View.VISIBLE);
            view.tvFrist.setVisibility(View.GONE);
            view.address.setTextColor(context.getResources().getColor(R.color.textColorHint));
            view.data.setTextColor(context.getResources().getColor(R.color.textColorHint));
            view.time.setTextColor(context.getResources().getColor(R.color.textColorHint));
        }
        if(i==resultBean.size()-1&&resultBean.size()!=1){
           view.view.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return resultBean.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView data, time, address,tvother,tvFrist;
        View view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.tv_data);
            time = itemView.findViewById(R.id.tv_time);
            address = itemView.findViewById(R.id.address);
            view = itemView.findViewById(R.id.view);
            tvother = itemView.findViewById(R.id.tv_other);
            tvFrist = itemView.findViewById(R.id.tv_frist);
        }
    }

}
