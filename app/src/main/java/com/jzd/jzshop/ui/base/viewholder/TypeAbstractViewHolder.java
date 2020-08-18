package com.jzd.jzshop.ui.base.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jzd.jzshop.entity.HomeEntity;

import java.util.List;

/**
 * @author LXB
 * @description: viewHolder 抽象分离基类
 * 每一种布局都封装成一个ViewHolder，继承此类实现BindHolder方法,外界只关注不同数据实体
 * @date :2020/1/7 10:32
 */
public abstract class TypeAbstractViewHolder<T> extends RecyclerView.ViewHolder {

    public TypeAbstractViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bindHolder(T model, Context mContext);

    public void bindHolder(T model, Context mContext,Object viewModel){};
}
