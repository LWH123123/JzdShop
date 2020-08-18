package com.jzd.jzshop.ui.home.creditsstore.goods_details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.CreditDetailsLogsEntity;

import java.util.List;

/**
 * @author lwh
 * @description :
 * @date 2020/5/13.
 */
public class CreditDetailsLogsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<CreditDetailsLogsEntity.ResultBean.DataBean> list;

    public CreditDetailsLogsAdapter(List<CreditDetailsLogsEntity.ResultBean.DataBean> data) {
        this.list = data;
    }


    public void addData(List<CreditDetailsLogsEntity.ResultBean.DataBean> data){
        this.list.addAll(data);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_credits_detailslogs, viewGroup,false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        CreditDetailsLogsEntity.ResultBean.DataBean dataBean = list.get(i);
        MyViewHolder view = (MyViewHolder) viewHolder;
        Glide.with(view.avatar.getContext()).load(dataBean.getAvatar()).into(view.avatar);
        String date = dataBean.getTime().substring(0, 10);
        String time = dataBean.getTime().substring(dataBean.getTime().length() - 6);
        view.name.setText(dataBean.getNickname());
        view.date.setText(date);
        view.time.setText(time);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView name,date,time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.iv_avater);
            name = itemView.findViewById(R.id.tv_name);
            date = itemView.findViewById(R.id.tv_date);
            time = itemView.findViewById(R.id.tv_time);



        }
    }

}
