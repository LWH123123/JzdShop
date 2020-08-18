package com.jzd.jzshop.ui.mine.withdrawals;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ObservableField;
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
import com.jzd.jzshop.databinding.FragmentBalanceBinding;
import com.jzd.jzshop.entity.BalanceDataEntity;
import com.jzd.jzshop.ui.mine.mineshop.shophome.MineShopHomeAty;
import com.jzd.jzshop.utils.MoneyFormatUtils;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;


/**
 * 提现页面
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends BaseFragment<FragmentBalanceBinding,BalabceViewModel> {




    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_balance;
    }

    @Override
    public int initVariableId() {
        return BR.balanceVM;
    }

    @Override
    public BalabceViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(BalabceViewModel.class);
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(),false);
        //状态栏透明
//        StatusBarUtil.setRootViewFitsSystemWindows(getActivity(),true);
        StatusBarUtil.setStatusBarColor(getActivity(), Color.parseColor("#FF8621"));
        viewModel.setBinding(binding,BalanceFragment.this);
        //提现金额的输入监听
        binding.edBalacemoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                ObservableField<BalanceDataEntity.ResultBean> balancedata = viewModel.balancedata;
                if(balancedata.get()!=null&&!TextUtils.isEmpty(s.toString())){


                    BalanceDataEntity.ResultBean.PoundageBean poundage = balancedata.get().getPoundage();
                    Double percent = Double.valueOf(poundage.getPercent());
                    Double begin = Double.valueOf(poundage.getBegin());
                    Double end = Double.valueOf(poundage.getEnd());
                    Double money = Double.valueOf(s.toString());
                    Double aDouble = Double.valueOf(balancedata.get().getMoney());
                    String withdrawmoney = balancedata.get().getWithdrawmoney();
                    if(withdrawmoney!=null&&!TextUtils.isEmpty(withdrawmoney)){
                        Double withdraw = Double.valueOf(withdrawmoney);
                        if(aDouble<withdraw){
                            return;
                        }
                    }
                    if(money<=aDouble&&money!=0.0) {
                        binding.card.setCardBackgroundColor(Color.parseColor("#FFA500"));
                        if(!balancedata.get().getPoundage().getPercent().equals("0")) {
                            if (begin <= money && money <= end) {
                                binding.tvCommission.setText("本次提现将扣除手续费 ¥0.00");
                                binding.tvMoney.setText("本次提现实际到账金额 ¥" + money);
                            } else {
                                double news = money * percent * 0.01;
                                double last = money - news;
                                String deduct = MoneyFormatUtils.keepTwoDecimal(news);
                                String practical = MoneyFormatUtils.keepTwoDecimal(last);
                                binding.tvCommission.setText("本次提现将扣除手续费 ¥" + deduct);
                                binding.tvMoney.setText("本次提现实际到账金额 ¥" + practical);
                            }
                        }
                    }else {
                        binding.card.setCardBackgroundColor(Color.parseColor("#FFECCC"));
                    }
                }else {
                    binding.card.setCardBackgroundColor(Color.parseColor("#FFECCC"));
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
