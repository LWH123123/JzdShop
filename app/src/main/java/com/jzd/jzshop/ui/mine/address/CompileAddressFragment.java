package com.jzd.jzshop.ui.mine.address;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentCompileAddressBinding;
import com.jzd.jzshop.entity.AddressListEntity;

import java.lang.reflect.Method;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * 新增收货地址
 */
public class CompileAddressFragment extends BaseFragment<FragmentCompileAddressBinding, CompileAddressViewModel> {
    public static final String ADDRESS_VIEWMODEL_REFRESH = "address_viewmodel_refresh";

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_compile_address;
    }

    @Override
    public int initVariableId() {
        return BR.compileVM;
    }

    @Override
    public CompileAddressViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(CompileAddressViewModel.class);
    }


    @Override
    public void initData() {
        super.initData();
        viewModel.setContext(getActivity());
        viewModel.getAddress();
        viewModel.setBinding(binding);
        // TODO: 2019/12/3  避免edittext 获取焦点，弹出软键盘
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//大于等于21
//                binding.editTex3.setShowSoftInputOnFocus(false);
//            } else {
//                disableShowInput(binding.editTex3);
//            }
        getIntentData();
    }

    private void getIntentData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            AddressListEntity.ResultBean.DataBean address = bundle.getParcelable("address");
            String compile = bundle.getString("compile");
            viewModel.initToolbar(compile);
            if (address != null) {
                viewModel.compile(address);
            }
        }
    }

    public void disableShowInput(EditText et) {
        Class<EditText> cls = EditText.class;
        Method method;
        try {//setShowSoftInputOnFocus方法是EditText从TextView继承来的的 //可以用来设置当EditText获得焦点时软键盘是否可见
            method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            method.setAccessible(true);
            method.invoke(et, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
