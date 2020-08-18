package com.jzd.jzshop.ui.mine.withdrawals;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentBalanceApplyBinding;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.BaseFragment;


/**
 * 意见申请
 */
public class BalanceApplyFragment extends BaseFragment<FragmentBalanceApplyBinding,BalanceApplyViewModel> {



    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_balance_apply;
    }

    @Override
    public int initVariableId() {
        return BR.balanceapplyVM;
    }

    @Override
    public BalanceApplyViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(BalanceApplyViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle arguments = getArguments();

        if(arguments!=null){
            String string = arguments.getString(Constants.RECHARGE_TIME);
            binding.tvMoney.setText(string);
        }

    }
}
