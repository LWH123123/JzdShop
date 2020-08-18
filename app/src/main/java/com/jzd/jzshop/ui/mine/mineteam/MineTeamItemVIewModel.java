package com.jzd.jzshop.ui.mine.mineteam;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.jzd.jzshop.entity.CategoryEntity;
import com.jzd.jzshop.entity.MineTeamEntity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;

/**
 * @author lwh
 * @description :
 * @date 2020/4/22.
 */
public class MineTeamItemVIewModel extends ItemViewModel {

    public ObservableField<MineTeamEntity.ResultBean.DataBean> entity =new ObservableField();
    //对时间做特殊处理
    public ObservableField<String> time=new ObservableField<>();

    public MineTeamItemVIewModel(@NonNull BaseViewModel viewModel,MineTeamEntity.ResultBean.DataBean entitys) {
        super(viewModel);
        this.entity.set(entitys);
        String time = entitys.getTime();
        String substring = time.substring(0, time.indexOf(" "));
        this.time.set(substring);
    }





}
