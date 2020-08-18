package com.jzd.jzshop.ui.mine.merch;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.jzd.jzshop.databinding.FragmentMerchNextBinding;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;

import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author LWH
 * @description:
 * @date :2020/1/3 10:35
 */
public class MerchNextViewModel extends ToolbarViewModel {
    public MerchNextViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }
    public SingleLiveEvent next=new SingleLiveEvent();

    public void setTool() {
        setTitleText("商家入驻");
    }

    private FragmentMerchNextBinding binding;

    public void setbind(FragmentMerchNextBinding binding) {
        this.binding = binding;
    }

    public ObservableField<String> mytext = new ObservableField<>();

    @Override
    protected void setBackOnClick() {
        finish();
    }

    //商家入驻的第二步
    public BindingCommand onClickNextListeren = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            next.call();


        }
    });

}
