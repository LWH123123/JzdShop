package com.jzd.jzshop.ui.order.logistics;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.graphics.Color;
import android.support.annotation.NonNull;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.entity.LogisticListEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * @author LWH
 * @description:
 * @date :2020/1/16 10:48
 */
public class LogisticsViewModel extends ToolbarViewModel<Repository> {
    public LogisticsViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public ObservableList<LogisticListEntity.ResultBean> logistic = new ObservableArrayList<>();
    public ObservableList<LogisticItemViewModel> logisticList = new ObservableArrayList<>();
    //    public ItemBinding<HomeProductItemViewModel> product = ItemBinding.of(com.jzd.jzshop.BR.productVM, R.layout.item_home_shop);
    public ItemBinding<LogisticItemViewModel> logistics = ItemBinding.of(BR.logisticItemVM, R.layout.item_logisticlist);

    public void setToolbar() {
        setTitleText("物流列表");
        setToolbarBgColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

    public void requestword(String order) {
        addSubscribe(model.postLogistic(model.getUserToken(), order), new ParseDisposableTokenErrorObserver<LogisticListEntity>() {
            @Override
            public void onResult(LogisticListEntity o) {
                super.onResult(o);
                dismissDialog();
                logistic.clear();
                logistic.addAll(o.getResult());
                for (int i = 0; i < logistic.size(); i++) {
                    LogisticListEntity.ResultBean resultBean = logistic.get(i);
                    resultBean.setTotal(resultBean.getData().size());
                    resultBean.setTitle(resultBean.getData().get(0).getTitle());
                    resultBean.setThumb(resultBean.getData().get(0).getThumb());

                    LogisticItemViewModel logisticItemViewModel = new LogisticItemViewModel(LogisticsViewModel.this, resultBean);
                    logisticList.add(logisticItemViewModel);
                }
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
            }
        });
    }


}
