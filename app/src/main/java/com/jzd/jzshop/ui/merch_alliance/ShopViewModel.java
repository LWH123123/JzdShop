package com.jzd.jzshop.ui.merch_alliance;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.entity.ShopEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.tatarka.bindingcollectionadapter2.ItemBinding;


/**
 * 商家联盟 ———》》》 专卖店铺 viewModel
 */
public class ShopViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = ShopViewModel.class.getSimpleName();
    private List<ShopEntity.ResultBean.DataBean> data;

    public ShopViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public ObservableField<String> title = new ObservableField<>();
    public ObservableList<ShopItemViewModel> shoplist = new ObservableArrayList<>();
    public ItemBinding<ShopItemViewModel> shopitem = ItemBinding.of(BR.shopitemVM, R.layout.item_shop);

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<List<BannEntity>> xbannerRefreshing = new SingleLiveEvent<>();
        public SingleLiveEvent<List<ShopEntity.ResultBean>> noDataUi = new SingleLiveEvent<>();
        public SingleLiveEvent nobann = new SingleLiveEvent<>();
    }

    private String shopid;

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setTitleText(text);
        setTitleTextColor(Color.parseColor("#000000"));

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

    public String getUserToken(){
        return model.getUserToken();
    }

    public void requestWork(String id) {
        addSubscribe(model.postShopData(id), new ParseDisposableObserver<ShopEntity>() {
            @Override
            public void onResult(ShopEntity o) {
                dismissDialog();
                List<ShopEntity.ResultBean> result = o.getResult();
                ArrayList<BannEntity> bann = new ArrayList<>();
                if (result != null && result.size() > 0) {
                    for (ShopEntity.ResultBean resultBean : result) {
                        if (resultBean.getId().equals("goods")) {
                            List<ShopEntity.ResultBean.DataBean> data = resultBean.getData();
                            for (ShopEntity.ResultBean.DataBean datum : data) {
                                ShopItemViewModel shopItemViewModel = new ShopItemViewModel(ShopViewModel.this, datum);
                                shoplist.add(shopItemViewModel);
                            }
                        }
                        if (resultBean.getId().equals("banner")) {
                            data = resultBean.getData();
                            for (ShopEntity.ResultBean.DataBean datum : data) {
                                bann.add(new BannEntity(datum.getImgurl(), datum.getLinkurl()));
                            }
                            if (bann.size() != 0) {
                                uc.xbannerRefreshing.setValue(bann);
                            }
                        }
                    }
                }else if (result == null || result.size() == 0){
                    Log.d(TAG,"result:" +result.size());
                    uc.noDataUi.setValue(result);
                }else {
                    uc.noDataUi.setValue(result);
                }
                if(data==null||data.size()==0){
                    uc.nobann.call();
                }

            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
            }
        });

    }




    public void startGood(String gid,String open){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.GOODS_KEY_GID,gid);
        bundle.putString(Constants.GOODS_OPEN_FLAG, open);
        bundle.putString(Constants.GOODLIST_GOODS,shopid);
        startActivity(GoodsActivity.class, bundle);

    }


}
