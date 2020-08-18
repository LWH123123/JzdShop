package com.jzd.jzshop.ui.home.creditsstore.express;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.entity.CreditExpressEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author lwh
 * @description :
 * @date 2020/5/15.
 */
public class CreditShopExpressViewModel extends ToolbarViewModel<Repository> {

    public ObservableField<CreditExpressEntity.ResultBean> entity =new ObservableField<>();
    public SingleLiveEvent<CreditExpressEntity.ResultBean> updatedata =new SingleLiveEvent<>();
    public CreditShopExpressViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

    public void requestWork(String logid, String merchid) {

        addSubscribe(model.postCreditExpress(model.getUserToken(),logid,merchid),new ParseDisposableTokenErrorObserver<CreditExpressEntity>(){

            @Override
            public void onResult(CreditExpressEntity o) {
                super.onResult(o);
                dismissDialog();
                CreditExpressEntity.ResultBean result = o.getResult();
                entity.set(result);
                updatedata.setValue(o.getResult());
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
