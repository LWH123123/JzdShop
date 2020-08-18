package com.jzd.jzshop.ui.home.creditsstore.goods_details.details;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jzd.jzshop.data.Repository;

import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author lwh
 * @description :
 * @date 2020/5/20.
 */
public class CreditDetailsWebViewModel extends BaseViewModel<Repository> {

    public CreditDetailsWebViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }
}
