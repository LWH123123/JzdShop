package com.jzd.jzshop.ui.order.logistics;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentLogisticsBinding;
import com.jzd.jzshop.ui.order.mineorder.MineOrderViewModel;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogisticsFragment extends BaseFragment<FragmentLogisticsBinding, LogisticsViewModel> {
/*
 物流列表
*  */

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_logistics;
    }

    @Override
    public int initVariableId() {
        return BR.logisticsVM;
    }

    @Override
    public LogisticsViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(LogisticsViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setToolbar();
        if (getArguments() != null) {
            String orderid = getArguments().getString(Constants.ORDER_LOGISTICS);
            viewModel.requestword(orderid);
        }
    }
}

