package com.jzd.jzshop.ui.home.creditsstore.order_detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityCreditOrderDetailBinding;
import com.jzd.jzshop.entity.CreditOrderDetailEntity;
import com.jzd.jzshop.entity.CreditToSureReceiptEntity;
import com.jzd.jzshop.ui.home.creditsstore.confirm_order.CreditConfirmOrderActivity;
import com.jzd.jzshop.utils.Constants;

import java.text.DecimalFormat;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * @author LXB
 * @description: 积分商城 订单详情
 * @date :2020/5/13 9:37
 */
public class CreditOrderDetailActivity extends BaseActivity<ActivityCreditOrderDetailBinding, CreditOrderDetailViewModel> {
    private CreditOrderDetailEntity.ResultBean resultData;
    private String log_id;
    private String merch_id = "";

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_credit_order_detail;
    }

    @Override
    public int initVariableId() {
        return BR.creditOrderDetailVM;
    }

    @Override
    public CreditOrderDetailViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(CreditOrderDetailViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, false);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#D90804"));
        viewModel.initToolBar(getResources().getString(R.string.exchange_orderdetail));
        getIntentData();
        viewModel.setBinding(mContext, binding);
        viewModel.requestData(log_id, merch_id);

        Messenger.getDefault().register(this, CreditOrderDetailViewModel.TOKEN_VIEWMODEL_EVALUATION_REFRESH, String.class,
                new BindingConsumer<String>() {
                    @Override
                    public void call(String state) {//刷新
                        if (state.equals("evaluation")) {
                            viewModel.requestData(log_id, merch_id);
                        } else {
                        }
                    }
                });
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            log_id = bundle.getString(Constants.GOODS_KEY_GID);
            Log.d(TAG, "log_id====:" + log_id);
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<CreditOrderDetailEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable CreditOrderDetailEntity.ResultBean resultBean) {
                if (resultBean != null) {
                    resultData = resultBean;
                    setData(resultBean);
                }
            }
        });

        viewModel.uc.mToSureReceiptLiveData.observe(this, new Observer<CreditToSureReceiptEntity>() {
            @Override
            public void onChanged(@Nullable CreditToSureReceiptEntity creditToSureReceiptEntity) {
                if (creditToSureReceiptEntity != null) {
                    viewModel.requestData(log_id, merch_id);
                    ToastUtils.showShort("确认收货成功！");
                }
            }
        });
    }

    private void setData(CreditOrderDetailEntity.ResultBean resultBean) {
        //头部数据
        binding.tvWaitpay.setText(resultBean.getState_str());
        if (!TextUtils.isEmpty(resultBean.getDispatch())) {
            if (resultBean.getDispatch().equals("0.00")) {
                //实付价格计算
                double money = Double.parseDouble(resultBean.getMoney());
                double realMoney = money * resultBean.getNumber();
                DecimalFormat fnum = new DecimalFormat("##0.00");
                String format = fnum.format(realMoney);
                //积分计算
                int dNumber = resultBean.getCredit() * resultBean.getNumber();
                binding.tvOrderAmount.setText(String.valueOf(dNumber) + "积分 +¥" + format);
            } else {
                String format = mCalculatePrice(resultBean);
                //积分计算
                int dNumber = resultBean.getCredit() * resultBean.getNumber();
                binding.tvOrderAmount.setText(String.valueOf(dNumber) + "积分 +¥" + format);
            }
        } else {
        }
        //是否是实物商品兑换
        int type = resultBean.getType();
        if (type == 0) {//类型【0:商品；1：优惠券；2：余额；3：红包】
            binding.consOne.setVisibility(View.VISIBLE);
            binding.constrlExpress.setVisibility(View.VISIBLE);
            binding.tvFreightTip.setVisibility(View.VISIBLE);
            binding.tvFreight.setVisibility(View.VISIBLE);
            //收货地址
            CreditOrderDetailEntity.ResultBean.AddressBean address = resultBean.getAddress();
            if (address != null) {
                binding.tvAddressName.setText(address.getRealname());
                if (!TextUtils.isEmpty(address.getMobile())) {
                    StringBuilder sb = new StringBuilder(address.getMobile());
                    String showPhone = sb.replace(3, 7, "****").toString();
                    binding.tvAddressPhone.setText(showPhone);
                    if (!TextUtils.isEmpty(address.getProvince())&& !TextUtils.isEmpty(address.getCity())
                            &&!TextUtils.isEmpty(address.getArea()) && !TextUtils.isEmpty(address.getAddress())){
                        binding.tvAddress.setText(address.getProvince() + address.getCity() + address.getArea()+address.getAddress());
                    }else {
                        binding.tvAddress.setText(address.getAddress());
                    }
                }
            }
            //快递信息
            if (!TextUtils.isEmpty(resultBean.getExpress_com()) && !TextUtils.isEmpty(resultBean.getExpress_sn())){
                binding.constrlExpress.setVisibility(View.VISIBLE);
                binding.tvExpressName.setText("快递公司：" + resultBean.getExpress_com());
                binding.tvExpressCode.setText("快递单号：" + resultBean.getExpress_sn());
            }else {
                binding.constrlExpress.setVisibility(View.GONE);
            }
        } else {
            binding.consOne.setVisibility(View.GONE);
            binding.constrlExpress.setVisibility(View.GONE);
            binding.tvFreightTip.setVisibility(View.GONE);
            binding.tvFreight.setVisibility(View.GONE);
        }
        //商品
        //type    Int    类型【0:商品；1：优惠券；2：余额；3：红包】
        if (type == 0) {
            binding.tvState.setText(mContext.getResources().getString(R.string.shop_exchanges));
        } else if (type == 1) {
            binding.tvState.setText(mContext.getResources().getString(R.string.coupons));
        } else if (type == 2) {
            binding.tvState.setText(mContext.getResources().getString(R.string.balances));
        } else if (type == 3) {
            binding.tvState.setText(mContext.getResources().getString(R.string.redbags));
        } else {
        }
        binding.tvStore.setText(resultBean.getShopname());
        Glide.with(mContext).load(resultBean.getThumb()).into(binding.ivGood);
        binding.tvTitle.setText(resultBean.getTitle());
        if (!TextUtils.isEmpty(resultBean.getOption())) {
            binding.tvGoodspec.setText("规格：" + resultBean.getOption());
        } else {
            binding.tvGoodspec.setText("规格：暂无规格");
        }
        binding.tvCreditNum.setText(String.valueOf(resultBean.getCredit()));
        binding.tvNumber.setText("x" + String.valueOf(resultBean.getNumber()));
        //--------------------商品小计----------------start----------------
        //积分计算
        int dNumber = resultBean.getCredit() * resultBean.getNumber();
        binding.tvMerchSubtotal.setText(String.valueOf(dNumber) + "积分" );
        //计算实付金额
        double money = Double.parseDouble(resultBean.getMoney());
        double realMoney = money * resultBean.getNumber();
        DecimalFormat fnum = new DecimalFormat("##0.00");
        String format = fnum.format(realMoney);
        binding.price.setText("+¥" + format);
        //计算运费
        if (!TextUtils.isEmpty(resultBean.getDispatch())) {
            if (resultBean.getDispatch().equals("0.00")) {
                binding.tvFreight.setText("免运费");
            } else {
                double dispatch = Double.parseDouble(resultBean.getDispatch()); //运费
                double dDispatch = dispatch* resultBean.getNumber();
                binding.tvFreight.setText("+¥" + dDispatch);
            }
        }
        //计算实付费
        String formats = mCalculatePrice(resultBean);
        //积分计算
        int dNumbers = resultBean.getCredit() * resultBean.getNumber();
        binding.tvEalpayment.setText(String.valueOf(dNumbers) + "积分 +¥" + formats);
        //--------------------商品小计----------------end----------------
        //订单编号
        binding.tvOrderNumber.setText(resultBean.getLogno());
        if (resultBean.getTime() != null) {
            binding.tvCreateTime.setText(resultBean.getTime().getCreatetime());
            binding.tvPayTime.setText(resultBean.getTime().getPaytime());
            if (!TextUtils.isEmpty(resultBean.getTime().getSendtime())) {
                binding.tvSendTimeTip.setVisibility(View.VISIBLE);
                binding.tvSendTime.setVisibility(View.VISIBLE);
                binding.tvSendTime.setText(resultBean.getTime().getSendtime());
            } else {
                binding.tvSendTimeTip.setVisibility(View.GONE);
                binding.tvSendTime.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(resultBean.getTime().getFinishtime())) {
                binding.tvFinishTimeTip.setVisibility(View.VISIBLE);
                binding.tvFinishTime.setVisibility(View.VISIBLE);
                binding.tvFinishTime.setText(resultBean.getTime().getFinishtime());
            } else {
                binding.tvFinishTimeTip.setVisibility(View.GONE);
                binding.tvFinishTime.setVisibility(View.GONE);
            }
        }
        //底部button 状态
        int state = resultBean.getState();
        if (state == 0) {//无任何操作
            binding.consBottom.setVisibility(View.GONE);
        } else if (state == 1) {//用户可以确认收货
            binding.consBottom.setVisibility(View.VISIBLE);
            binding.tvConfirmReceipt.setVisibility(View.VISIBLE);
            binding.tvGetRedBags.setVisibility(View.GONE);
            binding.tvEvaluaion.setVisibility(View.GONE);
            binding.tvAddEvaluaion.setVisibility(View.GONE);
        } else if (state == 2) {//用户可以领取红包
            binding.consBottom.setVisibility(View.VISIBLE);
            binding.tvGetRedBags.setVisibility(View.VISIBLE);
            binding.tvConfirmReceipt.setVisibility(View.GONE);
            binding.tvEvaluaion.setVisibility(View.GONE);
            binding.tvAddEvaluaion.setVisibility(View.GONE);
        } else if (state == 3) {//可以评价
            binding.consBottom.setVisibility(View.VISIBLE);
            binding.tvEvaluaion.setVisibility(View.VISIBLE);
            binding.tvGetRedBags.setVisibility(View.GONE);
            binding.tvConfirmReceipt.setVisibility(View.GONE);
            binding.tvAddEvaluaion.setVisibility(View.GONE);
        } else {//可以追加评价
            binding.consBottom.setVisibility(View.VISIBLE);
            binding.tvAddEvaluaion.setVisibility(View.VISIBLE);
            binding.tvGetRedBags.setVisibility(View.GONE);
            binding.tvConfirmReceipt.setVisibility(View.GONE);
            binding.tvEvaluaion.setVisibility(View.GONE);
        }

    }

    /**
     *  计算实付价格
     * @param resultBean
     * @return
     */
    private String mCalculatePrice(CreditOrderDetailEntity.ResultBean resultBean){
        double dispatch = Double.parseDouble(resultBean.getDispatch()); //运费
        double money = Double.parseDouble(resultBean.getMoney());
        double realMoney = money * resultBean.getNumber();
        double dPrice = dispatch + realMoney;
        DecimalFormat fnum = new DecimalFormat("##0.00");
        String format = fnum.format(dPrice);
        return format;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().finishActivity(CreditConfirmOrderActivity.class);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
