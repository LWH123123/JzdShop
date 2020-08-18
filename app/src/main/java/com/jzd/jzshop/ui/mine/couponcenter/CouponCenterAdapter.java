package com.jzd.jzshop.ui.mine.couponcenter;

import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.databinding.ItemCouponCenterBinding;

import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

/**
 * @author LWH
 * @description:
 * @date :2020/1/2 14:03
 */
public class CouponCenterAdapter extends BindingRecyclerViewAdapter<CouponCenterItemViewModel> {

    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, CouponCenterItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        final ItemCouponCenterBinding bind = (ItemCouponCenterBinding) binding;
        final CouponCenterItemViewModel couponitemVM = bind.getCouponitemVM();



    }


}
