package com.jzd.jzshop.ui.shoppingcar;

import android.app.Activity;
import android.app.Application;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityShoppingcarBinding;
import com.jzd.jzshop.entity.CouponEntity;
import com.jzd.jzshop.entity.ShoppcarEntry;
import com.jzd.jzshop.ui.base.CouponItemViewModel;
import com.jzd.jzshop.ui.order.firmorder.FirmOrderActivity;
import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.MoneyFormatUtils;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.jzd.jzshop.utils.dialog.MessageDialog;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class ShoppingCarViewModel extends BaseViewModel<Repository> {

    private Activity mContext;
    public ActivityShoppingcarBinding binding;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<String> getcoupon = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> nodata = new SingleLiveEvent();
        public SingleLiveEvent<Integer> bagenumber = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> message = new SingleLiveEvent<>();
    }

    public ObservableField<Integer> goodsnumber = new ObservableField<>();
    //列表数据
    public ObservableList<MultiItemViewModel> shopCarList = new ObservableArrayList<>();
    //购物车所有数据
    public ObservableField<ShoppcarEntry> shopList = new ObservableField<>();
    //全选按钮的选中状态
    public ObservableBoolean entrys = new ObservableBoolean(false);
    //每个店铺的商品总额
    public HashMap<String, Double> hash = new HashMap<>();
    //选中商品的总数量
    public ObservableField<String> number = new ObservableField<>("0");
    //购物车所有优惠券的数据
    public ObservableList<ShoppcarEntry.ResultBean.CouponDataBean> couponList = new ObservableArrayList<>();
    //单个店铺的优惠券数据
    public ObservableList<CouponItemViewModel> coupon_ob = new ObservableArrayList<>();
    //所有商品的总额
    public ObservableField<Double> money = new ObservableField<>(0.0);
    public ObservableField<String> moneyShow = new ObservableField<>("0.00");
    //列表的布局
    public ItemBinding<MultiItemViewModel> itembind = ItemBinding.of(new OnItemBind<MultiItemViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, MultiItemViewModel item) {
            String type = (String) item.getItemType();
            if (ShoppingCarItemViewModel.ShopingCarTYPE_StoreName.equals(type)) {

                itemBinding.set(BR.shoppingcarStoreName, R.layout.item_shoppingcar_store_name);
            } else if (ShoppingCarItemViewModel.ShopingCarTYPE_ShopData.equals(type)) {
                itemBinding.set(BR.shoppingCarDataVM, R.layout.item_shoppingcar_shop);
            }
        }
    });

    public ShoppingCarViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(ActivityShoppingcarBinding binding, Activity context) {
        this.mContext = context;
        this.binding = binding;
    }

    //网络请求
    public void requestNetWork() {
        isShowDialog(false);
        if (shopCarList.size() != 0) {
            shopCarList.clear();
        }
        if (TextUtils.isEmpty(model.getUserToken())) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.CAR_LOGIN, "CAR_LOGIN");
            startActivity(LoginVertifyActivity.class, bundle);
            return;
        }
        addSubscribe(model.postCarts(model.getUserToken()), new ParseDisposableTokenErrorObserver<ShoppcarEntry>() {
            @Override
            public void onResult(ShoppcarEntry o) {
                dismissDialog();
                shopList.set(o);
                setData(o);
            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CAR_LOGIN, "MYCARLOVE");
                startActivity(LoginVertifyActivity.class, bundle);

            }
        });
    }


    public BindingCommand onClickGoShop = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(HomePageActivity.class);
        }
    });

    //遍历展示数据
    private void setData(ShoppcarEntry o) {
        int number = 0;
        if (couponList.size() != 0) {
            couponList.clear();
        }
        if (shopCarList.size() != 0) {
            shopCarList.clear();
        }
        List<ShoppcarEntry.ResultBean> result = o.getResult();
        int size = result.size();
        for (int i = 0; i < size; i++) {
            ShoppcarEntry.ResultBean resultBean = result.get(i);
            List<ShoppcarEntry.ResultBean.CouponDataBean> coupon_data1 = resultBean.getCoupon_data();
            int size1 = coupon_data1.size();
            int size2 = resultBean.getData().size();
            for (int j = 0; j < size1; j++) {
                ShoppcarEntry.ResultBean.CouponDataBean couponDataBean = coupon_data1.get(j);
                couponDataBean.setMerch_name(resultBean.getMerch_name());
                couponList.add(couponDataBean);
            }
            if (resultBean != null) {
                ShoppcarEntry.ResultBean.DataBean dataBean = new ShoppcarEntry.ResultBean.DataBean();
                dataBean.setMerch_name(resultBean.getMerch_name());
                dataBean.setMerch_id(resultBean.getMerch_id());
                dataBean.setShopischeck(false);
                if (i == 0) {
                    dataBean.setIsnextstore(0);
                } else {
                    dataBean.setIsnextstore(1);
                }
                ShoppingCarItemViewModel shopping1 = new ShoppingCarItemViewModel(this, dataBean);
                shopping1.multiItemType(ShoppingCarItemViewModel.ShopingCarTYPE_StoreName);
                shopCarList.add(shopping1);
            }
            for (int j = 0; j < size2; j++) {
                ShoppcarEntry.ResultBean.DataBean dataBean = resultBean.getData().get(j);
                dataBean.setMerch_name(resultBean.getMerch_name());
                dataBean.setMerch_id(resultBean.getMerch_id());
                dataBean.setShopischeck(false);
                number += dataBean.getNumber();
                if (j == resultBean.getData().size() - 1) {
                    dataBean.setIslast(1);
                }
                ShoppingCarItemViewModel shopping2 = new ShoppingCarItemViewModel(this, dataBean);
                shopping2.multiItemType(ShoppingCarItemViewModel.ShopingCarTYPE_ShopData);
                shopCarList.add(shopping2);
            }
        }
        if (shopCarList.size() == 0) {
            uc.nodata.setValue(0);
        } else {
            binding.mallRefreshLayout.setVisibility(View.VISIBLE);
            binding.shopdata.setVisibility(View.VISIBLE);
            binding.manage.setClickable(true);
            binding.imageView26.setVisibility(View.GONE);
            binding.text45.setVisibility(View.GONE);
            binding.goshop.setVisibility(View.GONE);
        }
        goodsnumber.set(number);
        model.saveShopCarnumber(goodsnumber.get());
        uc.bagenumber.setValue(number);
        entrys.set(false);
        if (hash.size() != 0) {
            hash.clear();
        }
        totalMoney();
        HashMap<String, Double> price = price();
        updateMoney(price);
        onAllSelect(false);

    }


    /*
     * 购物车全选按钮
     * */
    public BindingCommand<Boolean> onAllSelectClick = new BindingCommand<Boolean>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean aBoolean) {
            if (shopCarList.size() == 0) {
                return;
            }
            onAllSelect(aBoolean);
        }
    });


    /*全选功能*/
    private void onAllSelect(boolean select) {
        String a = "";
        StringBuffer sb = new StringBuffer();
        if (shopCarList.size() != 0) {
            for (int i = 0; i < shopCarList.size(); i++) {
                MultiItemViewModel multiItemViewModel = shopCarList.get(i);
                ShoppingCarItemViewModel items = ((ShoppingCarItemViewModel) multiItemViewModel);
                if (items != null && items.dataBean != null) {
                    ((ShoppingCarItemViewModel) multiItemViewModel).dataBean.get().shopischeck = select;
                    ((ShoppingCarItemViewModel) multiItemViewModel).dataBean.notifyChange();
                    if (multiItemViewModel.getItemType().equals(ShoppingCarItemViewModel.ShopingCarTYPE_ShopData)) {
                        if (sb.length() != 0) {
                            sb.append("@");
                        }
                        sb.append(((ShoppingCarItemViewModel) multiItemViewModel).dataBean.get().getCart_id());
                    }
                }
            }
            if (select) {
                setShopSelect(sb.toString(), 1);
            } else {
                setShopSelect(sb.toString(), 0);
            }
            hash.clear();
            HashMap<String, Double> price = price();
            updateMoney(price);
            totalMoney();
//                HashMap<String, Double> hashMap = couponMoney(price);
//                setHasmap(hashMap);
        }
    }

    /*
     * 管理/删除 购物车数据
     * */
    public BindingCommand OnManageClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            int visibility = binding.totalmoney.getVisibility();
            if (!TextUtils.isEmpty(model.getUserToken())) {
                if (visibility == View.VISIBLE) {
                    binding.manage.setText("完成");
                    binding.totalmoney.setVisibility(View.GONE);
                    binding.delete.setVisibility(View.VISIBLE);
                    binding.nodata.setVisibility(View.GONE);
                } else {
                    binding.manage.setText("管理");
                    binding.nodata.setVisibility(View.VISIBLE);
                    binding.totalmoney.setVisibility(View.VISIBLE);
                    binding.delete.setVisibility(View.GONE);
//                    requestNetWork();
//                    entrys.notifyChange();
                }
            } else {
                ToastUtils.showLong("请登录.");
            }
        }
    });


    /*
     * 删除数据
     * */
    public BindingCommand OnClickDelete = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (number.get().equals("0")) {
                ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                ToastUtils.showLong("您还没有选择宝贝哦!");
                return;
            }
            MessageDialog.showCancelAndSureDialog(mContext, "确认删除该商品吗？", "", R.color.line_color, R.color.red, new MessageDialog.DialogClick() {
                @Override
                public void onCancelClickListener() {
                }

                @Override
                public void onSureClickListener() {
                    StringBuffer sb = new StringBuffer();
                    int index = 0;
                    for (int i = 0; i < shopCarList.size(); i++) {
                        ShoppingCarItemViewModel items = ((ShoppingCarItemViewModel) shopCarList.get(i));
                        if (items.getItemType().equals(ShoppingCarItemViewModel.ShopingCarTYPE_ShopData)) {
                            boolean isshop = items.dataBean.get().shopischeck;
                            if (isshop) {
                                if (index != 0) {
                                    sb.append("@");
                                }
                                sb.append(items.dataBean.get().getCart_id());
                                index++;
                            }
                        }
                    }
                    if (sb.length() != 0) {
                        delete("token", sb.toString());
                    }
                    binding.manage.setText("管理");
                    binding.totalmoney.setVisibility(View.VISIBLE);
                    binding.nodata.setVisibility(View.VISIBLE);
                    binding.delete.setVisibility(View.GONE);
                }
            });
        }
    });

    /**
     * 计算价格
     * */
    public double totalMoney() {
        double total = 0;
        int num = 0;
        for (MultiItemViewModel item : shopCarList) {
            ShoppingCarItemViewModel shop = ((ShoppingCarItemViewModel) item);
            if (item.getItemType().equals(ShoppingCarItemViewModel.ShopingCarTYPE_ShopData)) {
                if (shop.dataBean.get().shopischeck == true) {
//                    Log.i("购物车信息   :", "totalMoney: "+shop.dataBean.get().getTitle());
                    ShoppingCarItemViewModel shopcar = ((ShoppingCarItemViewModel) item);
                    double number = shopcar.dataBean.get().getNumber();
                    double price = shopcar.dataBean.get().getPrice();
                    total += number * price;
                    num += number;
                }
            }
        }
        number.set(num + "");
        number.notifyChange();
        CharSequence text = binding.manage.getText();
        String s = text.toString();
        //约束布局的Group有个坑  就是group管理的控件  该控件就不能对自身显示隐藏了
        return total;
    }

    //计算单个店铺选中的商品金额
    public HashMap<String, Double> price() {
        ArrayList<Double> money = new ArrayList<>();
        double total = 0;
        for (int i = 0; i < shopList.get().getResult().size(); i++) {
            List<ShoppcarEntry.ResultBean> result = shopList.get().getResult();
            String name = result.get(i).getMerch_name();
            total = 0;
            for (MultiItemViewModel shop : shopCarList) {
                ShoppingCarItemViewModel item = ((ShoppingCarItemViewModel) shop);
                String merch_name = item.dataBean.get().getMerch_name();
                if (shop.getItemType().equals(ShoppingCarItemViewModel.ShopingCarTYPE_ShopData)) {
                    if (merch_name.equals(name)) {
                        if (item.dataBean.get().isShopischeck()) {
                            double number = item.dataBean.get().getNumber();
                            double price = item.dataBean.get().getPrice();
                            total += number * price;
                            hash.put(name, total);
                        }
                    }
                }
            }
            money.add(total);
        }
        return hash;
    }

    //更新总金额
    public void updateMoney(HashMap<String, Double> hash) {
        double allmoney = 0;
        for (HashMap.Entry<String, Double> entry : hash.entrySet()) {
            double value = entry.getValue();
            allmoney += value;
        }
        String s = MoneyFormatUtils.keepTwoDecimal(allmoney);
        moneyShow.set(s);
        money.set(allmoney);
        moneyShow.notifyChange();
        money.notifyChange();
    }

    @BindingAdapter("checkClick")
    public static void checkBoxClick(final CheckBox checkBox, final BindingCommand<Boolean> command) {
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                command.execute(checkBox.isChecked());
            }
        });
    }


    //结算按钮
    public BindingCommand onClickCompute = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            if (number.get().equals("0")) {
                ToastUtils.showLong("您还没有选择宝贝");
            } else {
                bundle.putInt(Constants.TYPE, 1);
                startActivity(FirmOrderActivity.class, bundle);
                model.saveOpenFlag("1");
            }
        }
    });

    //店铺的全选方法
    public void setShopAllCheck(String merch_id, Boolean check) {
        StringBuffer sb = new StringBuffer();
        if (shopCarList.size() != 0) {
            for (int i = 0; i < shopCarList.size(); i++) {
                MultiItemViewModel multiItemViewModel = shopCarList.get(i);
                String merch_name = ((ShoppingCarItemViewModel) multiItemViewModel).dataBean.get().getMerch_name();
                if (merch_name.equals(merch_id)) {
                    ((ShoppingCarItemViewModel) multiItemViewModel).dataBean.get().shopischeck = check;
                    ((ShoppingCarItemViewModel) multiItemViewModel).dataBean.notifyChange();
                    if (multiItemViewModel.getItemType().equals(ShoppingCarItemViewModel.ShopingCarTYPE_ShopData)) {
                        if (sb.length() != 0) {
                            sb.append("@");
                        }
                        sb.append(((ShoppingCarItemViewModel) multiItemViewModel).dataBean.get().getCart_id());
                    }
                }
            }
            selectItemCheck(merch_id, check);
            if (check) {
                setShopSelect(sb.toString(), 1);
            } else {
                setShopSelect(sb.toString(), 0);
            }
        }
        totalMoney();
    }


    //这个方法需要实现当商品的状态都选中时 商户的状态也选中 反则不选中
    public void selectItemCheck(String id, boolean ischeck) {
        //全部状态
        boolean all = ischeck;
        //商户的状态
        boolean shop = ischeck;
        ShoppingCarItemViewModel shops = null;
        for (int i = 0; i < shopCarList.size(); i++) {
            ShoppingCarItemViewModel shopitem = (ShoppingCarItemViewModel) shopCarList.get(i);
            ShoppcarEntry.ResultBean.DataBean dataBean = shopitem.dataBean.get();
            String merch_name = dataBean.getMerch_name();
            //如果他们的商户id都一样的情况下
            if (merch_name.equals(id)) {
                if (!ischeck) {
                    if (shopitem.getItemType().equals(ShoppingCarItemViewModel.ShopingCarTYPE_StoreName)) {
                        shops = shopitem;
                        dataBean.shopischeck = false;
                        shopitem.dataBean.notifyChange();
                        //全选设为false
                        entrys.set(false);
                        entrys.notifyChange();
                        break;
                    }
                }
                //这儿的判断有问题 设置商户
                if (shopitem.getItemType().equals(ShoppingCarItemViewModel.ShopingCarTYPE_ShopData)) {
                    shop = dataBean.shopischeck && shop;
                } else {
                    shops = shopitem;
                }
            }
            if (shopitem.getItemType().equals(ShoppingCarItemViewModel.ShopingCarTYPE_ShopData)) {
                all = dataBean.shopischeck && all;
            }
        }
        if (shop) {
            shops.dataBean.get().shopischeck = true;
            shops.dataBean.notifyChange();
        }
        if (all) {
            entrys.set(true);
            entrys.notifyChange();
        }
    }

    /*弹出优惠券的popwindows
     * */
    public void initCoupon(String name) {
        if (coupon_ob.size() != 0 && coupon_ob != null) {
            coupon_ob.clear();
        }
        for (int i = 0; i < couponList.size(); i++) {
            if (couponList.get(i).getMerch_name().equals(name)) {
                final CouponEntity conversion = CouponEntity.conversion(couponList.get(i));
                final CouponItemViewModel couponItemViewModel = new CouponItemViewModel(ShoppingCarViewModel.this, conversion);
                coupon_ob.add(couponItemViewModel);
                final int finalI = i;
                couponItemViewModel.setOnclick(new BindingCommand(new BindingAction() {
                    @Override
                    public void call() {
                        if (conversion.getCanget() == 1) {
                            KLog.a("领取优惠券", conversion.getCanget());
                            requestGetGoupon(couponItemViewModel);
                            couponList.get(finalI).setCanget(0);
                            synchronized (couponList) {
                                couponList.notifyAll();
                            }

                        } else {
                            ToastUtils.showLong("已经领取过了");
                        }

                    }
                }));
            }
        }
        uc.getcoupon.setValue(name);
    }


    /*领取优惠券*/
    public void requestGetGoupon(final CouponItemViewModel couponItemViewModel) {
        final CouponEntity couponEntity = couponItemViewModel.entity.get();
        addSubscribe(model.postGetGoupon(model.getUserToken(), couponEntity.getCoupon_id()), new ParseDisposableTokenErrorObserver<JSONObject>() {
            @Override
            public void onResult(JSONObject o) {
                dismissDialog();
                KLog.a("优惠券领取成功");
                synchronized (couponEntity) {
                    couponEntity.setCanget(0);
                    couponEntity.notify();
                }
                synchronized (couponList) {
                    couponList.notifyAll();
                }
                CouponItemViewModel coupon = null;
                for (int i = 0; i < coupon_ob.size(); i++) {
                    CouponItemViewModel couponItemViewModel1 = coupon_ob.get(i);
                    coupon = couponItemViewModel1;
                    coupon.entity.notifyChange();
                }
            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
                KLog.a("购物车领取优惠券失败信息", errarMessage);
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                startActivity(LoginVertifyActivity.class);
            }
        });

    }

    /*
     * 更新购物车的数据
     * */
    public void updater(String cartid, final int number, int optionid, final int type) {
        //type 为1 的时候是加  0的时候是减
        addSubscribe(model.postUpdaterShop(model.getUserToken(), cartid, number, optionid), new ParseDisposableObserver<Object>() {
            @Override
            public void onResult(Object o) {
                dismissDialog();
                //刷新 导航栏的数据
              uc.message.setValue(type);


            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
            }
        });

    }


    /*
     * 删除购物车数据请求
     * */
    public void delete(String token, String id) {
        addSubscribe(model.postUpdaterRemove(model.getUserToken(), id), new ParseDisposableObserver<BaseResponse>() {
            @Override
            public void onResult(BaseResponse o) {
                dismissDialog();
                binding.checkBox.setChecked(false);
                requestNetWork();
            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
            }
        });
    }


    /*
     * 切换商品的选种状态
     * */
    public void setShopSelect(String cartid, int isselect) {
        addSubscribe(model.postSetSelect(model.getUserToken(), cartid, isselect), new ParseDisposableObserver<BaseResponse>() {
            @Override
            public void onResult(BaseResponse o) {
                dismissDialog();
            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
            }
        });
    }


    //计算单个店铺选中后的优惠金额
  /*  public HashMap<String, Double> couponMoney(HashMap<String, Double> hash) {
        for (int i = 0; i < couponList.size(); i++) {
            ShoppcarEntry.ResultBean.CouponDataBean couponDataBean = couponList.get(i);
            String merch_name = couponDataBean.getMerch_name();

            int canget = couponDataBean.getCanget();
            if (canget == 0) {//优惠券已经被领取
                Double aDouble = hash.get(merch_name);
                if (i != 0) {
                    String merch_name1 = couponList.get(i - 1).getMerch_name();
                    if (merch_name.equals(merch_name1)) {
                        break;
                    }
                }
                if (aDouble != null) {
                    double enough = couponDataBean.getEnough();
                    if (aDouble >= enough || enough == 0) {//满足优惠条件
                        String backtype = couponDataBean.getBacktype();
                        double backmoney = couponDataBean.getBackmoney();

                        if (backtype.equals("0")) {//现金券
                            aDouble -= backmoney;
                            hash.put(merch_name, aDouble);
                        } else if (backtype.equals("1")) {//折扣券
                            double coupon = backmoney * 0.1;
                            double result = aDouble * coupon;
                            //保留小数点
//                         double result= Math.round(newDouble * 100) / 100;
                            hash.put(merch_name, result);
                        }
                    }
                }
            }
        }
        return hash;
    }*/

    //弹出优惠明细的按钮
/*    public BindingCommand onClickCoupon = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //弹出优惠明细
            if (couponDetailEntity != null) {
                uc.popCoupon.setValue(couponDetailEntity);
            }
        }
    });*/


    /*
     * 计算点击的优惠券所在
     * */
    /*public void countCoupon(CouponEntity coupon) {
        Log.i("使用的优惠券:", "countCoupon: " + coupon.getBacktype());
        hash.clear();
        HashMap<String, Double> price = price();
        String merch_name = coupon.getMerch_name();
        Double aDouble = price.get(merch_name);
        HashMap<String, Double> hashMap = couponMoney(price);
        if (price != null) {
            if (aDouble != null) {
                if (coupon.getCanget() == 0) {
                    String backtype = coupon.getBacktype();
                    double backmoney = coupon.getBackmoney();
                    double enough = coupon.getEnough();
                    if (aDouble >= enough || enough == 0) {
                        if (backtype.equals("0")) {
                            aDouble -= backmoney;
                            hashMap.put(coupon.getMerch_name(), aDouble);
                        } else if (backtype.equals("1")) {
                            double moneys = backmoney * 0.1;
                            double newDouble = aDouble * moneys;
                            //保留小数点
                            double result = Math.round(newDouble * 100) / 100;
                            hashMap.put(coupon.getMerch_name(), result);
                        }
                    }
                }
            }
        }
        setHasmap(hashMap);
    }*/



    /*    //计算HashMap的总金额
    public void setHasmap(HashMap<String, Double> hash) {
        double tatol = 0.00;
        for (HashMap.Entry<String, Double> entry : hash.entrySet()) {
            tatol += entry.getValue();
        }
        couponDetailEntity = new CouponDetailEntity();
        //没优惠之前的总价
        double v = totalMoney();
        //保留小数点
        String format1 = new DecimalFormat("0.00").format(tatol);
        couponDetailEntity.setTotal("¥" + format1);
        double coupon = v - tatol;
        String format = new DecimalFormat("0.00").format(coupon);

        TextView manage = binding.manage;
        String s = manage.getText().toString();
        if (s.equals("管理")) {
            if (coupon == 0) {
                binding.coupon.setVisibility(View.GONE);
                binding.nodata.setVisibility(View.VISIBLE);
                binding.dolley.setText("¥" + format1);
            } else {
                binding.coupon.setVisibility(View.VISIBLE);
                binding.nodata.setVisibility(View.GONE);
                binding.money.setText("" + format1);
                binding.special.setText("已优惠" + format + "元  优惠明细");
            }
            couponDetailEntity.setCoupon("-¥" + format);
            couponDetailEntity.setAllcoupon("-¥" + format);
            couponDetailEntity.setMoney("¥" + format1);
        }
    }*/
}
