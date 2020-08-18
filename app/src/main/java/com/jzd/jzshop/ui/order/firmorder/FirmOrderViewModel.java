package com.jzd.jzshop.ui.order.firmorder;

import android.app.Activity;
import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityFirmOrderBinding;
import com.jzd.jzshop.entity.BaseEntity;
import com.jzd.jzshop.entity.FirmOrderEntity;
import com.jzd.jzshop.entity.OrderDataEntity;
import com.jzd.jzshop.ui.mine.address.ReceiptAddressFragment;
import com.jzd.jzshop.ui.base.CouponItemViewModel;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.pay.PayFragment;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.MoneyFormatUtils;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.umeng.analytics.MobclickAgent;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class FirmOrderViewModel extends ToolbarViewModel<Repository> {
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    public ObservableField<FirmOrderEntity.ResultBean> entity = new ObservableField<>();
    public ObservableList<MultiItemViewModel> FireOrderList = new ObservableArrayList<>();
    public ItemBinding<MultiItemViewModel> itembind = ItemBinding.of(new OnItemBind<MultiItemViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, MultiItemViewModel item) {
            String type = (String) item.getItemType();
            if (FirmOrderItemViewModle.FiremOrderShopName.equals(type)) {
                itemBinding.set(BR.firmOrderIVM, R.layout.item_firm_order);
            } else if (FirmOrderItemViewModle.FiremOrderShopData.equals(type)) {
                itemBinding.set(BR.firmOrderGIVM, R.layout.item_firm_order_goods);
            }
        }

    });


    //标记是否选择使用过优惠券
    public ObservableField<Integer> isuser =new ObservableField<>(0);
    //商城 商户 秒杀 之后的价格
    public ObservableField<Double> moneys = new ObservableField<>();
    //新弹出的优惠券
    public ObservableList<FirmOrderPopViewModel> coupon_is = new ObservableArrayList<>();
    public ItemBinding<FirmOrderPopViewModel> coupon_ib = ItemBinding.of(BR.firmpopVM, R.layout.item_firmorder_pop);
    //优惠券
    public ObservableList<CouponItemViewModel> coupon_ob = new ObservableArrayList<>();
    public ObservableList<PopOrderItemViewModel> coupon_ol = new ObservableArrayList<>();
    public ItemBinding<PopOrderItemViewModel> pg_coupon_ib = ItemBinding.of(BR.couponIVM, R.layout.pop_item_coupon);
    //订单页面的数据更新
    public ObservableField<OrderDataEntity> updateData = new ObservableField<>();
    //卖家留言
    public ObservableField<String> remark = new ObservableField<>();

    public ObservableField<String> coupon_log_ids = new ObservableField<>();
    private int goodsNums;
    private OrderDataEntity orderDataEntity = new OrderDataEntity();

    public class UIChangeObservable {
        //        public SingleLiveEvent<com.jzd.jzshop.ui.order.firmorder.FirmOrderItemViewModle> showCouponPopwindow = new SingleLiveEvent<>();
        public SingleLiveEvent<FirmOrderEntity.ResultBean.AddressBean> updataAddress = new SingleLiveEvent<>();
        public SingleLiveEvent updataMoneyText = new SingleLiveEvent<>();
        public SingleLiveEvent<OrderDataEntity> updateData = new SingleLiveEvent<>();
        public SingleLiveEvent<String> coupon = new SingleLiveEvent<>();
    }

    public FirmOrderViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void initToolbar() {
//        setRightIconVisible(View.VISIBLE);
        setTitleText("确认订单");

    }

    /**
     * 商品数量限制最大输入
     */
//    private void mGoodsNumsInputFilter() {
//        View inflate = LayoutInflater.from(mcontext).inflate(R.layout.item_firm_order_goods, null);
//        EditText ifog_number = (EditText) inflate.findViewById(R.id.ifog_number);
//        if (Integer.parseInt(ifog_number.getText().toString()) > 500){
//            ToastUtils.showShort("商品数量最大500");
//            return;
//        }else {
//            goodsNums = Integer.parseInt(ifog_number.getText().toString());
//        }
//        orderDataEntity.setGoodnumber(goodsNums);
//        updateData.set(orderDataEntity);
//        uc.updateData.setValue(orderDataEntity);
//        updateData.notifyChange();
//    }
    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

    private Activity mcontext;
    private ActivityFirmOrderBinding binding;

    public void setContext(ActivityFirmOrderBinding binding, Activity context) {
        this.binding = binding;
        this.mcontext = context;
    }

    @Override
    public void onResume() {
    }

    private String gid;
    private String optionid;
    private String total;//数量
    private String gift_id;
    private String addid;
    private int sign;


    public void setRequestInfo(int type, String addid) {
        this.addid = addid;
        sign = type;
        if (type == 1) {
//            this.addid= SPUtils.getInstance().clear();
            return;
        } else {
            this.gid = model.getShopID();
            this.optionid = model.getOptionID();
            this.total = model.getGoodsNumber();
            this.gift_id = model.getGIftID();
        }
    }

    private double money;

    public void requestNetWork(final SmartRefreshLayout refreshLayout) {
        isShowDialog(false);
        if (coupon_ob.size() != 0) {
            coupon_ob.clear();
        }
        addSubscribe(model.postOrderCreate(model.getUserToken(), addid, gid, optionid, total, gift_id),
                new ParseDisposableObserver<FirmOrderEntity>() {
                    @Override
                    public void onResult(final FirmOrderEntity result) {
                        dismissDialog();
                        binding.linRootView.setVisibility(View.VISIBLE);
                        binding.afoBottomLayout.setVisibility(View.VISIBLE);
                        /*没有默认地址的情况下可能会去添加地址然后回来重新刷新页面*/
                        entity.set(result.getResult());
                        setData(result);
                        /*for (FirmOrderEntity.ResultBean.DataBean bean : result.getResult().getData()) {
                            afo_firmorder_ob.add(new FirmOrderItemViewModle(FirmOrderViewModel.this, bean));
                            List<FirmOrderEntity.ResultBean.DataBean.GoodsBean> goods = bean.getGoods();
                        }*/
                        uc.updataAddress.setValue(result.getResult().getAddress());
                        uc.updataMoneyText.call();
                        initData(result.getResult());
                        refreshLayout.finishRefresh();

                    }

                    @Override
                    public void onError(String errarMessage) {
                        dismissDialog();
                        refreshLayout.finishRefresh();
                    }
                });
    }


    /**
     * 添加对应的商品店铺的数据 作展示
     *
     * @param result
     */
    private void setData(FirmOrderEntity result) {
        if (FireOrderList.size() != 0) {
            FireOrderList.clear();
        }
        List<FirmOrderEntity.ResultBean.DataBean> data = result.getResult().getData();
        for (int i = 0; i < data.size(); i++) {
            FirmOrderEntity.ResultBean.DataBean dataBean = data.get(i);
            //添加店铺数据
            if (dataBean != null) {
                FirmOrderEntity.ResultBean.DataBean.GoodsBean dataBean1 = new FirmOrderEntity.ResultBean.DataBean.GoodsBean();
                dataBean1.setMerch_name(dataBean.getMerch_name());
                dataBean1.setMerch_id(dataBean.getMerch_id());
                dataBean1.setDispatch(dataBean.getDispatch());
                FirmOrderItemViewModle firmOrderItemViewModle = new FirmOrderItemViewModle(FirmOrderViewModel.this, dataBean1);
                firmOrderItemViewModle.multiItemType(FirmOrderItemViewModle.FiremOrderShopName);
                FireOrderList.add(firmOrderItemViewModle);
            }
            //添加商品数据
            for (int j = 0; j < dataBean.getGoods().size(); j++) {
                FirmOrderEntity.ResultBean.DataBean.GoodsBean goodsBean = dataBean.getGoods().get(j);
                if (j == dataBean.getGoods().size() - 1) {
                    goodsBean.setIslast(1);
                } else {
                    goodsBean.setIslast(0);
                }
                //该每个商品的bean类里添加
                goodsBean.setMerch_name(dataBean.getMerch_name());
                goodsBean.setMerch_id(dataBean.getMerch_id());
                goodsBean.setDispatch(dataBean.getDispatch());
                FirmOrderItemViewModle firmOrderItemViewModle = new FirmOrderItemViewModle(FirmOrderViewModel.this, goodsBean);
                firmOrderItemViewModle.multiItemType(FirmOrderItemViewModle.FiremOrderShopData);
                FireOrderList.add(firmOrderItemViewModle);
            }
        }
    }


    /**
     * 展示对应的商城  和商户的优惠  计算对应的金额
     *
     * @param result
     */
    private void initData(FirmOrderEntity.ResultBean result) {
//        OrderDataEntity orderDataEntity = updateData.get();
        //所有商品的数量
        int goodnumber = 0;
        //所有商品不优惠的总价格
        double price = 0;
        //计算商城 商户 秒杀 后的金额
        double money = 0;
        //商品秒杀优惠的金额
        double seckil=0;
        //商品促销优惠的金额
        double discount=0;
        //保存数据之前先清空
        List<FirmOrderEntity.ResultBean.DataBean> data = result.getData();
        for (FirmOrderEntity.ResultBean.DataBean datum : data) {
            List<FirmOrderEntity.ResultBean.DataBean.GoodsBean> goods = datum.getGoods();
            for (FirmOrderEntity.ResultBean.DataBean.GoodsBean good : goods) {
                int number = Integer.parseInt(good.getNumber());
                goodnumber +=number;
                //商品原价
                double goodprice = Double.parseDouble(good.getPrice());
                //商品的秒杀优惠的金额计算
                if(!TextUtils.isEmpty(good.getSeckillprice())&&!good.getSeckillprice().equals("0.00")){
                    double secki = Double.parseDouble(good.getSeckillprice());
                    //计算秒杀优惠后的单价
                    goodprice-=secki;
                    double seckillprice = secki*number;
                    seckil+=seckillprice;
                }
                //商品的促销优惠的金额计算
                if(!TextUtils.isEmpty(good.getDiscountprice())&&!good.getDiscountprice().equals("0.00")){
                    double discoun = Double.parseDouble(good.getDiscountprice());
                    //计算促销优惠后的单价
                    goodprice-=discoun;
                    double discountprice=discoun *number;
                    discount+=discountprice;
                }
                price += goodprice * number;
            }
        }
        money = price;
        //计算商城或商户优惠的信息
        FirmOrderEntity.ResultBean.DiscountShopBean discount_shop = result.getDiscount_shop();
        List<FirmOrderEntity.ResultBean.DiscountShopBean.EnoughInfoBean> enough_info = discount_shop.getEnough_info();
        if (enough_info != null) {
            //商城优惠的金额 (减去的金额)
            double shopmoney = 0;
            //商户优惠的金额 (减去的金额)
            double merchmoney = 0;
            for (FirmOrderEntity.ResultBean.DiscountShopBean.EnoughInfoBean infoBean : enough_info) {
                if (infoBean.getIs_shop() == 0) {
                    binding.shopcoupon.setVisibility(View.VISIBLE);
                    orderDataEntity.setShopcoupon("商城优惠：(每笔满" + infoBean.getEnough() + "元立减" + infoBean.getMoney() + "元)");
                    shopmoney += Double.parseDouble(infoBean.getMoney());
                    money -= Double.parseDouble(infoBean.getMoney());
                }
                if (infoBean.getIs_shop() == 1) {
                    binding.storecoupon.setVisibility(View.VISIBLE);
                    orderDataEntity.setStorecoupon("商户优惠：(每笔满" + infoBean.getEnough() + "元立减" + infoBean.getMoney() + "元)");
                    merchmoney += Double.parseDouble(infoBean.getMoney());
                    money -= Double.parseDouble(infoBean.getMoney());
                }
            }
            //商城优惠的金额
            orderDataEntity.setShopmoney(shopmoney);
            //商户优惠的金额
            orderDataEntity.setStoremoney(merchmoney);

        }
        if (orderDataEntity != null) {
           //计算秒杀信息
            if (seckil !=0) {
                binding.seckillcoupon.setVisibility(View.VISIBLE);
                orderDataEntity.setSeckill(MoneyFormatUtils.keepTwoDecimal(seckil));
            }
            //计算促销优惠的信息
            if(discount!=0){//促销优惠不为空  且不为0;
               binding.groupPromation.setVisibility(View.VISIBLE);
               orderDataEntity.setPromation(MoneyFormatUtils.keepTwoDecimal(discount));
            }

            if (!TextUtils.isEmpty(String.valueOf(goodnumber))) {
                orderDataEntity.setGoodnumber(goodnumber);
            }
            if (entity.get().getCoupon_data() != null) {
                orderDataEntity.setCouponnumber(entity.get().getCoupon_data().size());
            }
            double aDouble = Double.parseDouble(MoneyFormatUtils.keepTwoDecimal(price));
            orderDataEntity.setTatol(String.valueOf(aDouble));
            orderDataEntity.setFreight(result.getDispatch());
//        updateData.set(updateData.get());
            double freigh = Double.parseDouble(result.getDispatch());
            money += freigh;
            moneys.set(money);
            //商城  商户  秒杀 之后的总价格
            String s = MoneyFormatUtils.keepTwoDecimal(money);
            orderDataEntity.setMoney(s);
            if (updateData != null) {
                updateData.set(orderDataEntity);
                uc.updateData.setValue(orderDataEntity);
                updateData.notifyChange();
            }
        }


    }

    /**
     * 提交订单的接口
     */
    public void requestOrderSubmit() {
//        mGoodsNumsInputFilter();
        if (entity.get().getAddress() == null) {
            ToastUtils.showLong("请添加收获地址");
            return;
        }
        addSubscribe(model.postOrderSubmit(model.getUserToken(), entity.get().getAddress().getAddr_id(), remark.get(), gid, optionid, total, gift_id, coupon_log_ids.get()),
                new ParseDisposableObserver<BaseEntity>() {
                    @Override
                    public void onResult(BaseEntity result) {
                        dismissDialog();
                        if (result != null) {
                            if (result.getResult() != null) {
                                //---------------友盟自定义 提交订单 事件-------------
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("orderId", result.getResult().getOrder_id());
                                map.put("price", updateData.get().getMoney());
                                map.put("gid", gid);
                                map.put("optionid", optionid);
                                MobclickAgent.onEvent(mcontext, "submit_order_id", map);
                                //---------------友盟自定义 提交订单 事件-------------
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.BUNDLE_KEY_ORDERID, result.getResult().getOrder_id());
                                bundle.putString(Constants.BUNDLE_KEY_MONEY, updateData.get().getMoney());
                                startContainerActivity(PayFragment.class.getCanonicalName(), bundle);
                                setBackOnClick();
                            }
                        }
                    }

                    @Override
                    public void onError(String errarMessage) {
                        dismissDialog();
                        ToastUtils.showShort("提交订单失败：" + errarMessage);
                    }
                });
    }

    /**
     * 更换地址
     */
    public BindingCommand OnSelectedAddress = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putInt("FIRED", sign);
            startContainerActivity(ReceiptAddressFragment.class.getCanonicalName(), bundle);
        }
    });

    /**
     * 请求提交订单的按钮
     */
    public BindingCommand onClickSubmit = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            requestOrderSubmit();
        }
    });

    /**
     * 点击弹出选择优惠券按钮
     */
    public BindingCommand onCliclItem = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (coupon_is.size() != 0) {
                coupon_is.clear();
            }
            List<FirmOrderEntity.ResultBean.CouponDataBean> coupon_data = entity.get().getCoupon_data();
            for (FirmOrderEntity.ResultBean.CouponDataBean coupon_datum : coupon_data) {
                FirmOrderPopViewModel firmOrderPopViewModel = new FirmOrderPopViewModel(FirmOrderViewModel.this, coupon_datum);
                coupon_is.add(firmOrderPopViewModel);
            }
            if(isuser.get()==0){
                unUserCoupon();
            }
            //弹出优惠券的列表
            uc.coupon.setValue(coupon_is.size() + "");
        }
    });

    /**
     * 计算优惠券优惠的金额
     *
     * @param conversion 选择好的优惠券信息
     */
    private void initMoney(FirmOrderEntity.ResultBean.CouponDataBean conversion) {
        //该店铺的金额
        double money = 0;
        //获取选择的优惠券的类型 和对应商户的ID
        String backtype = conversion.getBacktype();
        String merch_id = conversion.getMerch_id();//TODO 如果id为空   则优惠券为商城的优惠券在总价中使用
        KLog.a("促销  秒杀优惠后的金额为 ===>>>"+orderDataEntity.getTatol());
        //通过商户ID匹配对应的商户下的商品总价格
        List<FirmOrderEntity.ResultBean.DataBean> data = entity.get().getData();
        for (FirmOrderEntity.ResultBean.DataBean datum : data) {
            if (datum.getMerch_id().equals(merch_id)) {//通过优惠券的Merch_id寻找对应的商户的商品
                List<FirmOrderEntity.ResultBean.DataBean.GoodsBean> goods = datum.getGoods();
                for (FirmOrderEntity.ResultBean.DataBean.GoodsBean good : goods) {
                    int number = Integer.parseInt(good.getNumber());
                    //原价
                    double price = Double.parseDouble(good.getPrice());
                    //商品的秒杀优惠
                    if(!TextUtils.isEmpty(good.getSeckillprice())&&!good.getSeckillprice().equals("0.00")){
                        double seckill = Double.parseDouble(good.getSeckillprice());
                        price-=seckill;
                    }
                    //商品的促销优惠
                    if(!TextUtils.isEmpty(good.getDiscountprice())&&!good.getDiscountprice().equals("0.00")){
                        double discountprice= Double.parseDouble(good.getDiscountprice());
                        price-=discountprice;
                    }
                    //该店铺秒杀优惠和促销优惠后的价格
                    money+=price*number;
                }
            }else if(TextUtils.isEmpty(merch_id)){//优惠券的merch_id为空则 对所有的商品进行优惠
                money=Double.parseDouble(orderDataEntity.getTatol());
            }
        }
        //总价格 商城或商户优惠后的价格
        double first = moneys.get();
        KLog.a("优惠券的类型为:---->>>>" + backtype);
        //计算优惠券优惠的金额
        if (backtype.equals("0")) {//0为现金券  直接减
            binding.coupongroup.setVisibility(View.VISIBLE);
            money -= conversion.getBackmoney();
            //设置满减券的显示内容
            if (orderDataEntity != null) {
                orderDataEntity.setCouponmessage("店铺优惠券 满" + conversion.getEnough() + "元 立减" + conversion.getBackmoney());
                orderDataEntity.setCoupon(conversion.getBackmoney());
                first -= conversion.getBackmoney();
                KLog.a("最终优惠的金额为===>>", first);
                orderDataEntity.setMoney(MoneyFormatUtils.keepTwoDecimal(first));
                if (first <= 0) {
                    orderDataEntity.setMoney(0 + "");
                }
            }
        }
        if (backtype.equals("1")) {//1位折扣券
            if (orderDataEntity != null) {
                double dolley = money;
                binding.coupongroup.setVisibility(View.VISIBLE);
                //计算折扣金额
                money *= conversion.getBackmoney() * 0.1;
                String format = new DecimalFormat("0.00").format(money);
                double aDouble = Double.parseDouble(format);
                orderDataEntity.setCouponmessage("店铺优惠券折扣  " + conversion.getBackmoney() + "折");
                double last = dolley - aDouble;
                //计算折扣券优惠了多少钱
                String lasts = new DecimalFormat("0.00").format(last);
                orderDataEntity.setCoupon(Double.parseDouble(lasts));
                double finish = first - last;
                orderDataEntity.setMoney(MoneyFormatUtils.keepTwoDecimal(finish));
                if (finish <= 0) {
                    orderDataEntity.setMoney("0.0");
                }
            }
        }
        //保存选择的优惠券记录ID  用来请求接口使用这张优惠券
        if (orderDataEntity != null) {
            coupon_log_ids.set(conversion.getCoupon_log_id());
            updateData.set(orderDataEntity);
            uc.updateData.setValue(orderDataEntity);
        }
        updateData.notifyChange();
    }


    /**
     * 选中对应的优惠券进行记录  并标记为已选择
     *
     * @param couponid 对应的优惠券ID
     */
    public void selectCoupon(String couponid) {
        FirmOrderPopViewModel firm = null;
        for (int i = 0; i < coupon_is.size(); i++) {
            FirmOrderPopViewModel firmorder = coupon_is.get(i);
            firm = firmorder;
            FirmOrderEntity.ResultBean.CouponDataBean couponDataBean = firmorder.entity.get();
            String coupon_log_id = couponDataBean.getCoupon_log_id();
            if (!couponid.equals(coupon_log_id)) {
                couponDataBean.setIscollect(0);
                entity.notifyChange();
            } else {
                entity.notifyChange();
                couponDataBean.setIscollect(1);
            }
            firm.entity.notifyChange();
        }
    }

    /**
     * 使用已经选择好的优惠券
     */
    public void useCoupon() {

        List<FirmOrderEntity.ResultBean.CouponDataBean> coupon_data = entity.get().getCoupon_data();
        for (FirmOrderEntity.ResultBean.CouponDataBean coupon_datum : coupon_data) {
            int iscollect = coupon_datum.getIscollect();
            if (iscollect == 1) {
                //计算优惠券 优惠的金额
                initMoney(coupon_datum);
                isuser.set(1);
            }
        }
    }


    /**
     * 点击取消按钮 表示不使用优惠券
     */
    public void unUserCoupon() {
        FirmOrderPopViewModel firm = null;
        for (int i = 0; i < coupon_is.size(); i++) {
            FirmOrderPopViewModel firmorder = coupon_is.get(i);
            firm = firmorder;
            FirmOrderEntity.ResultBean.CouponDataBean couponDataBean = firmorder.entity.get();
            couponDataBean.setIscollect(0);
            firmorder.entity.notifyChange();
        }
        binding.coupongroup.setVisibility(View.GONE);
        initData(entity.get());
    }

}
