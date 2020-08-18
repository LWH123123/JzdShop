package com.jzd.jzshop.ui.login.bindmobile;


import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentBindMobileBinding;
import com.jzd.jzshop.ui.login.LoginVertifyViewModel;
import com.jzd.jzshop.ui.order.orderdetail.OrderDetailViewModel;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.bus.Messenger;

/**
 * 绑定手机号
 * A simple {@link Fragment} subclass.
 */
public class BindMobileFragment extends BaseFragment<FragmentBindMobileBinding, BindMobileBViewModel> {


    private TimeCount timeCount;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_bind_mobile;
    }

    @Override
    public int initVariableId() {
        return BR.bindmobileVM;
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setbindinig(BindMobileFragment.this);
        viewModel.setTool();
    }


    @Override
    public BindMobileBViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(BindMobileBViewModel.class);
    }


    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.getvertify.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                timeCount = new TimeCount(binding.verfity);
                binding.verfity.setClickable(false);
                timeCount.start();

            }
        });

        viewModel.uc.back.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Messenger.getDefault().send(s, Constants.BINDMOBILE);  //发送刷新
                getActivity().finish();
            }
        });

    }

    public class TimeCount extends CountDownTimer {
        TextView text;

        public TimeCount(TextView text) {
            super(60000, 1000);
            this.text = text;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {
            text.setTextColor(getResources().getColor(R.color.level1text));
            text.setText("剩余" + millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            text.setText("重新获取");
            text.setClickable(true);
            text.setTextColor(getResources().getColor(R.color.level1text));
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (timeCount != null) {
            timeCount.cancel();
        }

    }
}
