package com.jzd.jzshop.ui.home.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.HomeEntity;
import com.jzd.jzshop.ui.base.viewholder.TypeAbstractViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author LXB
 * @description:
 * @date :2020/1/7 10:54
 */
public class MenuTypeViewHolder extends TypeAbstractViewHolder<List<HomeEntity.ResultBean.DataBeanX.DataBean>> {
    @BindView(R.id.rv_menu)
    RecyclerView rvMenu;

    public MenuTypeViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindHolder(List<HomeEntity.ResultBean.DataBeanX.DataBean> dataBeans, Context mContext) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvMenu.setLayoutManager(linearLayoutManager);

//        rvMenu.setLayoutManager(new GridLayoutManager(mContext, 4));
//        TypeMenuAdapter centerAdapter = new TypeMenuAdapter(mContext, dataBeans, itemStyles);
//        rvMenu.setAdapter(centerAdapter);
    }

}
