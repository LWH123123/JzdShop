package com.jzd.jzshop.ui.mine.mineteam;

import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.jzd.jzshop.databinding.ActivityMineTeamBinding;
import com.jzd.jzshop.databinding.ItemMineTeamBinding;
import com.jzd.jzshop.entity.MineTeamEntity;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapters;

/**
 * @author lwh
 * @description :
 * @date 2020/4/22.
 */
public class MineTeamAdapter extends BindingRecyclerViewAdapter<MineTeamItemVIewModel> {

    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, MineTeamItemVIewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);

        ItemMineTeamBinding bind= (ItemMineTeamBinding) binding;
        ObservableField<MineTeamEntity.ResultBean.DataBean> data= item.entity;
        MineTeamEntity.ResultBean.DataBean dataBean = data.get();
        if(data.get().getIsfans()==1){
            bind.ivFans.setVisibility(View.VISIBLE);
        }else {
            bind.ivFans.setVisibility(View.GONE);
        }


    }
}
