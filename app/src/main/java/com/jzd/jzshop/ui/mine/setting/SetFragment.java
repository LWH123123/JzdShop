package com.jzd.jzshop.ui.mine.setting;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentSetBinding;
import com.jzd.jzshop.utils.notification.NotificationsUtils;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetFragment extends BaseFragment<FragmentSetBinding, SetViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_set;
    }

    @Override
    public int initVariableId() {
        return BR.setVM;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), false);
        StatusBarUtil.setStatusBarColor(getActivity(), Color.parseColor("#D90804"));
        /*getActivity().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        StatusBarUtil.setRootViewFitsSystemWindows(getActivity(), false);
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);*/
        viewModel.setBinding(binding, this);
    }


    @Override
    public SetViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(SetViewModel.class);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        boolean isEnabled = NotificationsUtils.isNotificationEnabled(getContext());
        if (isEnabled) {
            binding.tvMessage.setText(R.string.message_state_already_opened);
        } else {
            binding.tvMessage.setText(R.string.message_state_not_opened);
        }
    }


}
