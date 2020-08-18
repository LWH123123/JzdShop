package com.jzd.jzshop.ui.mine.mineshop.shophome;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;

/**
 * @author lwh
 * @description :
 * @date 2020/4/16.
 */
public class MineSelectViewModel extends ToolbarViewModel<Repository> {


    public MineSelectViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }

    @Override
    public void setTitleText(String text) {
        super.setTitleText(text);

    }

    public String getUserToken(){
       return model.getUserToken();
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
