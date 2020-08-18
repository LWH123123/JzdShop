package com.jzd.jzshop.ui.order.firmorder;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jzd.jzshop.entity.FirmOrderEntity;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.ui.merch_alliance.ShopFragment;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.MultiItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class FirmOrderItemViewModle extends MultiItemViewModel<FirmOrderViewModel> {

    public static final String FiremOrderShopName="orderName";
    public static final String FiremOrderShopData="orderData";

    public ObservableField<FirmOrderEntity.ResultBean.DataBean.GoodsBean> entitys =new ObservableField<>();
    public FirmOrderEntity.ResultBean.DataBean.GoodsBean goods = new FirmOrderEntity.ResultBean.DataBean.GoodsBean();


    public FirmOrderItemViewModle(@NonNull FirmOrderViewModel viewModel, FirmOrderEntity.ResultBean.DataBean.GoodsBean goodsBean) {
        super(viewModel);
        entitys.set(goodsBean);
    }


    public BindingCommand onClickItemShop =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.GOODS_KEY_GID,entitys.get().getGid());
            viewModel.startActivity(GoodsActivity.class,bundle);
        }
    });

    //店铺点击
    public BindingCommand onClickStore =new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            String id = entitys.get().getMerch_id();
            Bundle bundle = new Bundle();
            if (!TextUtils.isEmpty(id)) {
                bundle.putString(Constants.MINE_SHOP, id);
                bundle.putString("title", entitys.get().getMerch_name());
                viewModel.startContainerActivity(ShopFragment.class.getCanonicalName(), bundle);
            } else {
                viewModel.startActivity(HomePageActivity.class);
            }

        }
    });



   /* public double getSubtotal() {
        return subtotal;
    }
    private double subtotal;//店铺金额小计
    private double oldSubtotal;//原始小计
    private int count;//件数
    private double subCoupon;//优惠金额
    private String couponID;//同一个优惠券只计算一次
    //优惠券
    public ObservableList<CouponItemViewModel> coupon_ob = new ObservableArrayList<>();

*/
   /* public FirmOrderItemViewModle(@NonNull final FirmOrderViewModel viewModel, FirmOrderEntity.ResultBean.DataBean bean) {
        super(viewModel);
        entity.set(bean);
        KLog.a("goods数据长度",afo_goods_ob.size());
        List<FirmOrderEntity.ResultBean.DataBean.GoodsBean> goods = bean.getGoods();

       *//* for (FirmOrderEntity.ResultBean.DataBean.GoodsBean goodsBean : bean.getGoods()) {
            try{
                subtotal+=Double.valueOf(goodsBean.getPrice());
            }catch(Exception e){
                e.printStackTrace();
            }
            afo_goods_ob.add(new FirmOrderGoodsItemViewModel(viewModel, goodsBean));
            count++;
        }
        oldSubtotal=subtotal;
//        for (FirmOrderEntity.ResultBean.DataBean.CouponDataBean couponDataBean : bean.getCoupon_data()) {
//            final CouponEntity couponEntity=CouponEntity.conversion(couponDataBean);
//            CouponItemViewModel model = new CouponItemViewModel(viewModel, couponEntity);
//            model.setOnclick(new BindingCommand(new BindingAction() {
//                @Override
//                public void call() {
//                    switch (discount(couponEntity)){
//                        case -1:
//                            break;
//                        case 0:
//                            viewModel.setCoupon_log_ids("");
//                            break;
//                        case 1:
//                            viewModel.setCoupon_log_ids(couponEntity.getCoupon_id());
//                            break;
//                    }
//                }
//            }));
//            coupon_ob.add(model);
//        }
        subCouponText.set(getSubCouponText());
        subtotalText.set(getSubtotalText());*//*
    }*/

/*    *//**
     * 计算优惠
     * @param couponEntity
     * @return 是否成功使用优惠券 -1是失败 0是取消 1是成功
     *//*
    private int discount(CouponEntity couponEntity){
        *//**
         * coupon_log_id：优惠券记录ID
         * backtype：0.现金券 1.折扣券
         * enough：使用条件。为0时代表不限制。
         * backmoney：backtype为0时代表直减多少元。为1时代表多少折扣。
         * timestr：有效期
         *//*
         subtotal=oldSubtotal;
         subCoupon=0;
         subCoupon=0;
        if(couponID!=null&&couponID.equals(couponEntity.getCoupon_id())){
            updata();
            return 0;
        }
        couponID=couponEntity.getCoupon_id();
        //判断有效期
        SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date today = new Date();
        try {
            Date dateD = sdf.parse(couponEntity.getTimestr().substring(4));    //转换为 date 类型 Debug：Sun Nov 11 11:11:11 CST 2018
            boolean flag = dateD.getTime() <= today.getTime();
            if(flag){
                ToastUtils.showShort("优惠券过期！");
                return -1;
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        //先判断是否有使用条件 如果有是否满足
        if(!String.valueOf(couponEntity.getEnough()).equals("0")){
            if(!(couponEntity.getEnoughNumber()<subtotal)){
                ToastUtils.showShort("不满足优惠券使用条件！");
                return -1;
            }
        }
        if(couponEntity.getBacktype().equals("0")){
            //直减
            subCoupon=couponEntity.getBackmoney();
        }else if(couponEntity.getBacktype().equals("1")){
            //折扣
            subCoupon=subtotal*couponEntity.getBackmoney();
        }
        subtotal-=(subCoupon);
        if(subtotal<0)
            subtotal=0;
        updata();
        return 1;
    }

    private void updata(){
        subtotalText.set(getSubtotalText());
        subtotalText.notifyChange();
        subCouponText.set(getSubCouponText());
        subCouponText.notifyChange();
        viewModel.uc.updataMoneyText.call();
    }

    @SuppressLint("DefaultLocale")
    private SpannableString getSubtotalText() {

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#707070"));
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.3f);

        SpannableString spannableString = new SpannableString(String.format("共%d件 小计:¥%.2f", count, subtotal));
        int end = String.valueOf(2).length() + 6;
        int sizeStart = end + 1;
        int sizeEnd = sizeStart + String.valueOf(subtotal).length();
        spannableString.setSpan(colorSpan, 0, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan, sizeStart, sizeEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
    @SuppressLint("DefaultLocale")
    private SpannableString getSubCouponText() {
        *//**
         * + $ 10.00
         *//*
        RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(1.3f);
        SpannableString spannableString=new SpannableString(String.format("- ¥ %.0f.00",subCoupon));
        int start=3;
        int end=start+String.valueOf(subCoupon).length();
        spannableString.setSpan(sizeSpan01, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }*/



}
