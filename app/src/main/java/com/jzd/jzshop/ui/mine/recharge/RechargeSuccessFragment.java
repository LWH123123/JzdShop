package com.jzd.jzshop.ui.mine.recharge;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentRechargeSuccessBinding;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RechargeSuccessFragment extends BaseFragment<FragmentRechargeSuccessBinding,RechargeSuccessViewModel> {


    public RechargeSuccessFragment() {
        // Required empty public constructor
    }



    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_recharge_success;
    }

    @Override
    public int initVariableId() {
        return BR.rechargesuccessVM;
    }


    @Override
    public RechargeSuccessViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(RechargeSuccessViewModel.class);
    }


    @Override
    public void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if(arguments!=null){
            String money = arguments.getString(Constants.RECHARGE_MONEY);
            String time = arguments.getString(Constants.RECHARGE_TIME);
            String type = arguments.getString(Constants.RECHARGE_TYPE);
            binding.tvMoney.setText("充值金额:  "+money+"元");
            binding.tvTime.setText(time);
            binding.tvType.setText("充值方式:  "+type);
        }
    }
}
