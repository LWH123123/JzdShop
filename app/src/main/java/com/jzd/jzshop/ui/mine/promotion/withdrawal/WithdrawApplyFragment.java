package com.jzd.jzshop.ui.mine.promotion.withdrawal;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentWithdrawApplyBinding;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 推广中心---提现申请
 * A simple {@link Fragment} subclass.
 */
public class WithdrawApplyFragment extends BaseFragment<FragmentWithdrawApplyBinding,WithdrawApplyViewModel> {


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_withdraw_apply;
    }

    @Override
    public int initVariableId() {
        return BR.withdrawVM;
    }

    @Override
    public WithdrawApplyViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(WithdrawApplyViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setBinding(binding);
        binding.edMoney.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString())){
                    binding.card.setCardBackgroundColor(Color.parseColor("#CC0000"));
                }else {
                    binding.card.setCardBackgroundColor(Color.parseColor("#F89B9B"));
                }
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        viewModel.request();
    }
}
