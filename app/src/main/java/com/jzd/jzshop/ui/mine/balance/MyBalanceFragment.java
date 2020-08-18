package com.jzd.jzshop.ui.mine.balance;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentMyBalanceBinding;
import com.jzd.jzshop.ui.mine.recharge.RechargeViewModel;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.MoneyFormatUtils;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;


/**
 * 我的余额页面
 * A simple {@link Fragment} subclass.
 */
public class MyBalanceFragment extends BaseFragment<FragmentMyBalanceBinding,MyBalanceViewModel> {


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_my_balance;
    }

    @Override
    public int initVariableId() {
        return BR.mybalanceVM;
    }

    @Override
    public MyBalanceViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(MyBalanceViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setbing(binding);
        Bundle arguments = getArguments();
        if(arguments!=null) {
            double string = arguments.getDouble(Constants.MINE_BALANCE);
            viewModel.setMoney(string);
            String money = MoneyFormatUtils.keepTwoDecimal(string); //余额
            binding.mybalance.setText("¥"+money);
        }

        Messenger.getDefault().register(this,RechargeViewModel.ALIPAY_REFRESH_MONEY,String.class, new BindingConsumer<String>() {
            @Override
            public void call(String aDouble) {
                String money="¥"+aDouble;
                binding.mybalance.setText(money);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        viewModel.requestWork();
    }
}
