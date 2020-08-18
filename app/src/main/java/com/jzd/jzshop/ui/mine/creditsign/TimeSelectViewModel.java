package com.jzd.jzshop.ui.mine.creditsign;

import android.app.Application;
import android.support.annotation.NonNull;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;

import java.util.Date;

import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author lwh
 * @description :
 * @date 2020/3/23.
 */
public class TimeSelectViewModel extends ToolbarViewModel {


    private TimeSelectAty timeSelectAty;
    public SingleLiveEvent selectdate =new SingleLiveEvent();
    public void setContext(TimeSelectAty timeSelectAty) {
        this.timeSelectAty=timeSelectAty;
    }
    public TimeSelectViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setRightTextVisible(View.VISIBLE);
        setRightText("完成");
    }

    @Override
    protected void rightTextOnClick() {
        super.rightTextOnClick();
        selectdate.call();
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

    //时间选择
    public BindingCommand onTimeSelectClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            TimePickerView build = new TimePickerBuilder(timeSelectAty, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                }
            }).build();
            build.show();
        }
    });


}
