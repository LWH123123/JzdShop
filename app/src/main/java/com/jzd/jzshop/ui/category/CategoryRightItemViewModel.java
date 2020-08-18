package com.jzd.jzshop.ui.category;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jzd.jzshop.entity.CategoryEntity;
import com.jzd.jzshop.ui.goods.goodslist.GoodsListFragment;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class CategoryRightItemViewModel extends ItemViewModel<CategoryViewModel> {
    public ObservableField<CategoryEntity.ResultBean.ChildrenBean> entity = new ObservableField<>();

    public CategoryRightItemViewModel(@NonNull CategoryViewModel viewModel, CategoryEntity.ResultBean.ChildrenBean bean) {
        super(viewModel);
        entity.set(bean);
    }

    public int getPosition() {
        return viewModel.ac_category_ob.indexOf(this);
    }

    public BindingCommand onCliceToRight = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_KEY_CATEGORY_ID, entity.get().getId());
//            bundle.putString(Constants.BUNDLE_KEY_KEYWORDS, entity.get().getName());
            viewModel.startContainerActivity(GoodsListFragment.class.getCanonicalName(), bundle);
        }
    });
}
