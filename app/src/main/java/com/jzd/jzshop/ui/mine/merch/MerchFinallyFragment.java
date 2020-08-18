package com.jzd.jzshop.ui.mine.merch;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentMerchFinallyBinding;
import com.jzd.jzshop.entity.EventFile;
import com.jzd.jzshop.entity.MerchEntity;
import com.jzd.jzshop.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;


/**
 * A simple {@link Fragment} subclass.商家入驻的第三步
 */
public class MerchFinallyFragment extends BaseFragment<FragmentMerchFinallyBinding, MerchFinallyViewModel> {


    private MerchEntity.ResultBean parcelable;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_merch_finally;
    }

    @Override
    public int initVariableId() {
        return BR.merchfinallyVM;
    }

    @Override
    public void initData() {
        super.initData();
        //注册EventBus事件
        EventBus.getDefault().register(this);
        viewModel.setToolbar();
        viewModel.setBinding(binding,MerchFinallyFragment.this);
        if (getArguments() != null) {
            parcelable = getArguments().getParcelable(Constants.MerchSeconed);
            List<MerchEntity.ResultBean.FieldsBean> parcelableArray = getArguments().getParcelableArrayList(Constants.MerchSeconedList);
            viewModel.setData(parcelable, parcelableArray);
            updatedata();
        }
    }

    private void updatedata() {
        if(!TextUtils.isEmpty(parcelable.getUname())){
            binding.username.setText(parcelable.getUname());
        }
        if(parcelable.getOpen_protocol()==0){
            binding.checkBox2.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void XXX(List<EventFile.FileBean> fileBeans) {
        viewModel.setPic(fileBeans);
    }


    @Override
    public MerchFinallyViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(MerchFinallyViewModel.class);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
