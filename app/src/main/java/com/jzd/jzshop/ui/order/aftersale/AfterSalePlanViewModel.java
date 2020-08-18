package com.jzd.jzshop.ui.order.aftersale;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;

/**
 * @author LWH
 * @description:
 * @date :2020/1/15 11:19
 */
public class AfterSalePlanViewModel extends ToolbarViewModel<Repository> {

    public AfterSalePlanViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }
}
