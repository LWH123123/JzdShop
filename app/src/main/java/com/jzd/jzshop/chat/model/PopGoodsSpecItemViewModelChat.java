package com.jzd.jzshop.chat.model;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.jzd.jzshop.chat.OpenChatViewModel;
import com.jzd.jzshop.entity.GoodsSpecEntity;
import com.jzd.jzshop.ui.goods.GoodsViewModel;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * @author LXB
 * @description:
 * @date :2020/4/27 14:33
 */
public class PopGoodsSpecItemViewModelChat extends ItemViewModel<OpenChatViewModel> {
    public ObservableField<GoodsSpecEntity.ResultBean.ListBean> entity=new ObservableField<>();
    /** 规格ID的组合（将规格ID按降序的形式拼接成的字符串，多个之间用“_”拼接）*/
    public String spec_id;

    public PopGoodsSpecItemViewModelChat(@NonNull OpenChatViewModel viewModel, GoodsSpecEntity.ResultBean.ListBean itemsBean) {
        super(viewModel);
        entity.set(itemsBean);
    }

    public void setSpecID(int position){
        if(position>entity.get().getItems().size()){
            spec_id="";
            return;
        }
        String type = entity.get().getType();
        KLog.a("点击的===="+type);
        viewModel.setSpecData(entity.get().getType(),entity.get().getItems().get(position).getLid());
        spec_id=entity.get().getItems().get(position).getLid();
    }
}
