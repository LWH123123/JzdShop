package com.jzd.jzshop.ui.category;

import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.databinding.ActivityCategoryBinding;
import com.jzd.jzshop.databinding.ItemCategoryLeftBinding;

import me.goldze.mvvmhabit.utils.Utils;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class CategoryAdapter extends BindingRecyclerViewAdapter<CategoryLeftItemViewModel> {
    //当前点击的item position
    private int clickPosition = 0;
    private ActivityCategoryBinding mBinding;

    @Override
    public void onBindBinding(final ViewDataBinding binding, int variableId, int layoutRes, final int position, final CategoryLeftItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        final ItemCategoryLeftBinding itemBinding = (ItemCategoryLeftBinding) binding;
        itemBinding.imclNameTv.setSelected(clickPosition == position);
        if (clickPosition == position) {
            itemBinding.imclNameTv.setCompoundDrawables(getCompoundDrawables(), null, null, null);
        } else {
            itemBinding.imclNameTv.setCompoundDrawables(null, null, null, null);
        }
        itemBinding.imclNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(true);
                notifyItemChanged(position);
                notifyItemChanged(clickPosition);
                clickPosition = position;
                item.onClick();
                notifyDataSetChanged();
            }
        });
    }

    private Drawable drawable;

    private Drawable getCompoundDrawables() {
        if (drawable != null)
            return drawable;
        drawable = Utils.getContext().getResources().getDrawable(R.mipmap.red_line);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }
}
