package com.jzd.jzshop.ui.category;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.jzd.jzshop.entity.CategoryEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;

public class CategoryLeftItemViewModel extends ItemViewModel<CategoryViewModel> {
    public ObservableField<CategoryEntity.ResultBean> entity = new ObservableField<>();
    public CategoryLeftItemViewModel(@NonNull CategoryViewModel viewModel, CategoryEntity.ResultBean bean) {
        super(viewModel);
        entity.set(bean);
    }
    public int getPosition(){
        return viewModel.ac_category_ob.indexOf(this);
    }

    public void onClick(){
        viewModel.setContainerData(entity.get());

    }

}
