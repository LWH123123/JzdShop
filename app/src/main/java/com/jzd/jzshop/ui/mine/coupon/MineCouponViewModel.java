package com.jzd.jzshop.ui.mine.coupon;

import android.app.Application;
import android.support.annotation.NonNull;
import android.view.View;

import com.jzd.jzshop.databinding.FragmentMineCouponBinding;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;

import me.goldze.mvvmhabit.base.BaseModel;

public class MineCouponViewModel extends ToolbarViewModel {


    private FragmentMineCouponBinding binding;
    public MineCouponViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }
    public void setBinding(FragmentMineCouponBinding binding) {
        this.binding=binding;
    }

    public void initToolbar(){
        setRightIconVisible(View.GONE);
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();

    }




}
