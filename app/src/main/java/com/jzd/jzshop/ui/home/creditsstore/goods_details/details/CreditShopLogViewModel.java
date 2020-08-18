package com.jzd.jzshop.ui.home.creditsstore.goods_details.details;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.entity.CreditDetailsLogsEntity;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author lwh
 * @description :
 * @date 2020/5/20.
 */
public class CreditShopLogViewModel extends BaseViewModel<Repository> {



    public SingleLiveEvent<CreditDetailsLogsEntity.ResultBean> logsdata = new SingleLiveEvent<>();//参与记录

    public CreditShopLogViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void requestWork() {
        addSubscribe(model.postCreditDetailsLogs(model.getUserToken(),1,10),new ParseDisposableTokenErrorObserver<CreditDetailsLogsEntity>(){
            @Override
            public void onResult(CreditDetailsLogsEntity o) {
                super.onResult(o);
                dismissDialog();
                logsdata.setValue(o.getResult());

            }


            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();

            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);

            }
        });


    }
}
