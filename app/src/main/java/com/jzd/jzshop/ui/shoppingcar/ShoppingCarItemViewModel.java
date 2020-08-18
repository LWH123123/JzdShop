package com.jzd.jzshop.ui.shoppingcar;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.entity.ShoppcarEntry;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.utils.Constants;

import java.util.HashMap;

import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class ShoppingCarItemViewModel extends MultiItemViewModel<ShoppingCarViewModel> {

    public static final String ShopingCarTYPE_StoreName="StoreName";
    public static final String ShopingCarTYPE_ShopData="ShopData";
    public ObservableField<ShoppcarEntry.ResultBean.DataBean> dataBean= new ObservableField<>();
    public ShoppingCarItemViewModel(@NonNull ShoppingCarViewModel viewModel,ShoppcarEntry.ResultBean.DataBean dataBean) {
        super(viewModel);
        this.dataBean.set(dataBean);
    }
    //领取优惠券按钮
    public BindingCommand onGetCoupon =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
          viewModel.initCoupon(dataBean.get().getMerch_name());
        }
    });

    //商品数量增加
    public BindingCommand addOnClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            int number = dataBean.get().getNumber();
           //需要请求接口更新数据
            number++;
            viewModel.updater(dataBean.get().getCart_id(), number, dataBean.get().getOptionid(),1);
            dataBean.get().setNumber(number);
            dataBean.notifyChange();
            viewModel.totalMoney();
            viewModel.hash.clear();
            HashMap<String, Double> price1 = viewModel.price();
            viewModel.updateMoney(price1);


        }
    });

    //商品数量减少
    public BindingCommand subtractOnClick=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            int number = dataBean.get().getNumber();
            if(number==1){
                return;
            }
            if(number>=2){
                number--;
            }
          viewModel.updater(dataBean.get().getCart_id(), number, dataBean.get().getOptionid(),0);
            dataBean.get().setNumber(number);
            dataBean.notifyChange();
            viewModel.hash.clear();
            viewModel.totalMoney();

            HashMap<String, Double> price1 = viewModel.price();
            viewModel.updateMoney(price1);

            /*HashMap<String, Double> hashMap = viewModel.couponMoney(price1);
            viewModel.setHasmap(hashMap);*/
        }
    });

    //详情页面
    public BindingCommand onDataClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle=new Bundle();
            bundle.putString(Constants.GOODS_KEY_GID,dataBean.get().getGid());
            viewModel.startActivity(GoodsActivity.class,bundle);
        }
    });

/*
*店铺的全选
* */
    public BindingCommand<Boolean> onShopAllCheckClick = new BindingCommand<Boolean>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean aBoolean) {
              viewModel.setShopAllCheck(dataBean.get().getMerch_name(),aBoolean);

            viewModel.hash.clear();
            HashMap<String, Double> price1 = viewModel.price();
            viewModel.updateMoney(price1);
            viewModel.totalMoney();

           /* HashMap<String, Double> hashMap = viewModel.couponMoney(price1);
            viewModel.setHasmap(hashMap);*/
        }
    });

    //商品的CheckBox选中监听
    public BindingCommand<Boolean> onShopSelectClick= new BindingCommand<Boolean>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean aBoolean) {
            if(aBoolean) {
                viewModel.setShopSelect(dataBean.get().getCart_id(), 1);
            }else {
                viewModel.setShopSelect(dataBean.get().getCart_id(), 0);
            }
            dataBean.get().shopischeck=aBoolean;
            dataBean.notifyChange();
            viewModel.selectItemCheck(dataBean.get().getMerch_name(),aBoolean);
            viewModel.hash.clear();
            HashMap<String, Double> price1 = viewModel.price();
            viewModel.updateMoney(price1);
            viewModel.totalMoney();

            /*HashMap<String, Double> hashMap = viewModel.couponMoney(price1);
            viewModel.setHasmap(hashMap);*/
        }
    });




}
