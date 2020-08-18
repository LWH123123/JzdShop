package com.jzd.jzshop.ui.mine.feedback;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;

import me.goldze.mvvmhabit.base.BaseModel;

public class FeedBackSuccessViewModel extends ToolbarViewModel {
    public FeedBackSuccessViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }
    public void settool(){
        setTitleText("意见反馈");
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }
}
