package com.jzd.jzshop.ui.home.creditsstore.goods_details.details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentCreditShopLogBinding;
import com.jzd.jzshop.entity.CreditDetailsLogsEntity;
import com.jzd.jzshop.ui.home.creditsstore.goods_details.CreditDetailsLogsAdapter;
import com.jzd.jzshop.ui.home.creditsstore.goods_details.CreditGoodsDetailsActivity;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreditShopLogFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreditShopLogFrag extends BaseFragment<FragmentCreditShopLogBinding,CreditShopLogViewModel> {

    private static final String JOIN_PEOPLE = "param1";





    public  CreditShopLogFrag newInstance(int param1) {
        CreditShopLogFrag fragment = new CreditShopLogFrag();
        Bundle args = new Bundle();
        args.putInt(JOIN_PEOPLE, param1);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_credit_shop_log;
    }

    @Override
    public int initVariableId() {
        return BR.creditshoplogViewModel;
    }

    @Override
    public CreditShopLogViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(CreditShopLogViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.requestWork();
    }


    @Override
    public void initViewObservable() {
        super.initViewObservable();
        //绑定参与记录数据
        viewModel.logsdata.observe(this, new Observer<CreditDetailsLogsEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable CreditDetailsLogsEntity.ResultBean resultBean) {
                CreditDetailsLogsAdapter creditDetailsLogsAdapter = new CreditDetailsLogsAdapter(resultBean.getData());
//                creditDetailsLogsAdapter.addData(resultBean.getData());
                binding.rvView.setAdapter(creditDetailsLogsAdapter);
                binding.rvView.setLayoutManager(new LinearLayoutManager(getActivity()));
                creditDetailsLogsAdapter.notifyDataSetChanged();
            }
        });


    }
}
