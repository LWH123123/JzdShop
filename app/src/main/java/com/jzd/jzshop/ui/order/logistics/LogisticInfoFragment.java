package com.jzd.jzshop.ui.order.logistics;


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
import com.jzd.jzshop.databinding.FragmentLogisticInfoBinding;
import com.jzd.jzshop.entity.LogisticInfoEntity;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogisticInfoFragment extends BaseFragment<FragmentLogisticInfoBinding, LogisticInfoViewModel> {
    private String order_id, refund_id,openType;
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_logistic_info;
    }

    @Override
    public int initVariableId() {
        return BR.logisticinfoVM;
    }


    @Override
    public LogisticInfoViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(LogisticInfoViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setToolbar();
        if (getArguments() != null) {
            String order = getArguments().getString(Constants.MyOrder);
            String sendtype = getArguments().getString(Constants.ORDER_SEND_TYPE);
            String orderbundle = getArguments().getString(Constants.ORDER_BUNDLE);
            //=============申请退款、售后（查看物流信息）========start======
            order_id = getArguments().getString(Constants.ORDER_ID);
            refund_id = getArguments().getString(Constants.REFUND_ID);
            openType = getArguments().getString(Constants.SEE_REFUND_EXPRESS);
            //=============申请退款、售后（查看物流信息）=========end======
            if (openType!=null&&openType.equals("1")){
                viewModel.postExchangeLogistics(order_id, refund_id);
            }
            binding.tvGoodtotal.setVisibility(View.GONE);
            viewModel.requestword(order, sendtype, orderbundle);
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.update.observe(LogisticInfoFragment.this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                LogisticInfoEntity.ResultBean logistic = viewModel.logistic.get();
                binding.tvGoodtotal.setVisibility(View.VISIBLE);
                int statusstr = logistic.getStatusstr();
                switch (statusstr){
                    case 0://
                     binding.tvStatus.setVisibility(View.INVISIBLE);
                     break;
                    case 1://备货
                        binding.tvStatus.setText("备货中");
                        break;
                   case 2:
                       binding.tvStatus.setText("配送中");
                     break;
                   case 3:
                       binding.tvStatus.setText("已签收");
                     break;
                }
                binding.tvProfile.setText("快递公司:"+logistic.getExpresscom());
                binding.tvFormcard.setText("快递单号:"+logistic.getExpresssn());
                if(logistic.getData().size()==0){
                   binding.recycle.setVisibility(View.GONE);
                   return;
                }else {
                    binding.recycle.setVisibility(View.VISIBLE);
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

                binding.recycle.setLayoutManager(linearLayoutManager);
                LogisticAdapter logisticAdapter = new LogisticAdapter();
                logisticAdapter.setList(logistic.getData(),getActivity());
                binding.recycle.setAdapter(logisticAdapter);
            }
        });

    }
}
