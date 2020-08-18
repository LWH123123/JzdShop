package com.jzd.jzshop.ui.mine.couponcenter;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.entity.CouponCenterEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class CouponCenterViewModel extends ToolbarViewModel<Repository> {

    private int page;
    private int pagesize = 10;
    private SmartRefreshLayout smartRefreshLayout;
    public ObservableList<CouponCenterEntity.ResultBean.DataBean> coupondata = new ObservableArrayList<>();
    public ObservableList<CouponCenterItemViewModel> couponlist = new ObservableArrayList<>();
    public ItemBinding<CouponCenterItemViewModel> couponcenter = ItemBinding.of(BR.couponitemVM, R.layout.item_coupon_center);

    public CouponCenterViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }


    public void initToolbar() {
        setTitleText("领券中心");
    }


    public void requestwork(SmartRefreshLayout smartRefreshLayout, int page, boolean isrefresh) {
        this.page = page;


        addSubscribe(model.postCouponCenter(model.getUserToken(), page, pagesize), new ParseDisposableTokenErrorObserver<CouponCenterEntity>() {
            @Override
            public void onResult(CouponCenterEntity o) {
                dismissDialog();
                //优惠券总个数
                int tatol = o.getResult().getTotal();
                List<CouponCenterEntity.ResultBean.DataBean> data = o.getResult().getData();
                coupondata.addAll(data);
                for (CouponCenterEntity.ResultBean.DataBean datum : data) {
                    CouponCenterItemViewModel coupon = new CouponCenterItemViewModel(CouponCenterViewModel.this, datum);
                    couponlist.add(coupon);
                }


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
            }
        });


    }


    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

}
