package com.jzd.jzshop.ui.home.creditsstore.confirm_order;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityCreditConfirmOrderBinding;
import com.jzd.jzshop.entity.CreditsConfirmOrderEntity;
import com.jzd.jzshop.entity.ExchageGoodsNumEntity;
import com.jzd.jzshop.ui.mine.address.CompileAddressFragment;
import com.jzd.jzshop.ui.mine.address.ReceiptAddressViewModel;
import com.jzd.jzshop.utils.Constants;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.DecimalFormat;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * @author LXB
 * @description: 积分商城 确认订单
 * @date :2020/5/13 15:51
 */
public class CreditConfirmOrderActivity extends BaseActivity<ActivityCreditConfirmOrderBinding, CreditConfirmOrderViewModel>
        implements OnRefreshListener {
    private String addr_id = "";
    private String gid;
    private String optionid;
    private CreditsConfirmOrderEntity.ResultBean resultData;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_credit_confirm_order;
    }

    @Override
    public int initVariableId() {
        return BR.creditConfirmOrderVM;
    }

    @Override
    public CreditConfirmOrderViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(CreditConfirmOrderViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        viewModel.initToolBar(getResources().getString(R.string.credit_confirmorder));
        viewModel.setBinding(mContext, binding, optionid);
        viewModel.requestData(binding.mallRefreshLayout, addr_id, gid, optionid, "");
        initMallRefresh();
        Messenger.getDefault().register(this, CompileAddressFragment.ADDRESS_VIEWMODEL_REFRESH, new BindingAction() {
            @Override
            public void call() {//刷新我的数据
                Log.d(TAG,"添加地址成功！");
                binding.mallRefreshLayout.autoRefresh();
            }
        });
        //选择地址后,刷新 确认订单页数据
        Messenger.getDefault().register(this, ReceiptAddressViewModel.TOKEN_RECEIPTADDRESSVIEWMODEL_REFRESH,
                Bundle.class, new BindingConsumer<Bundle>() {
                    @Override
                    public void call(Bundle bundle) {//选择地址
                        Log.d(TAG, "选择地址成功");
                        String addr_id = bundle.getString(Constants.ADDRESS_SELECR);
                        viewModel.requestData(binding.mallRefreshLayout, addr_id, gid, optionid, "");
                    }
                });
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            gid = bundle.getString(Constants.GOODS_KEY_GID);
            optionid = bundle.getString(Constants.GOODS_KEY_CREDIT_GOODS_TYPE);
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<CreditsConfirmOrderEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable CreditsConfirmOrderEntity.ResultBean resultBean) {
                if (resultBean != null) {
                    if (!TextUtils.isEmpty(resultBean.getMoney())){
                        if(resultBean.getMoney().equals("0")){
                            if(resultBean.getLotterydraws()==0){
                                binding.afoSubmit.setText("立即兑换");
                            }else if(resultBean.getLotterydraws()==1){
                                binding.afoSubmit.setText("立即抽奖");
                            }
                        }
                    }
                    resultData = resultBean;
                    setData(resultBean);
                }
            }
        });
        viewModel.uc.mExchageGoodsNumLiveData.observe(this, new Observer<ExchageGoodsNumEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable ExchageGoodsNumEntity.ResultBean resultBean) {
                if (resultBean != null) {
                    int status = resultBean.getStatus();//1：正常配送 0：不支持配送
                    if (status == 1) {
                        String dispatch = resultBean.getDispatch();
                        if (!TextUtils.isEmpty(dispatch)) {
                            if (dispatch.equals("0.00")) { //免运费
                                binding.tvFreight.setText("免运费");
                            } else {
                                binding.tvFreight.setText("+¥" + dispatch);
                            }
                        }
                    } else {
                        ToastUtils.showShort(resultBean.getMsg());
                        return;
                    }
                    int num = resultBean.getNum();
                    binding.tvGoodsNum.setText("" + num);
                    //计算 商品小计 积分
                    int credit = resultData.getCredit();
                    int dCredit = credit * num;
                    binding.tvMerchSubtotal.setText(String.valueOf(dCredit) + "积分");
                    //计算商品小计 price
                    double moneys = Double.parseDouble(resultData.getMoney());
                    double dPrices = moneys * num;
                    DecimalFormat fnums = new DecimalFormat("##0.00");
                    String formats = fnums.format(dPrices);
                    binding.price.setText("+¥" + formats);
                    //计算 实付金额
                    if (resultData != null) {
                        int creditS = resultData.getCredit();
                        int realCreditNumber = creditS * num;
                        double dispatch = Double.parseDouble(resultBean.getDispatch()); //运费
                        double money = Double.parseDouble(resultData.getMoney());
                        double realMoney = money * num;
                        double dPrice = dispatch + realMoney;
                        DecimalFormat fnum = new DecimalFormat("##0.00");
                        String format = fnum.format(dPrice);
                        Log.d(TAG, "实付积分====：" + realCreditNumber + "实付金额====：" + dPrice);
                        binding.tvEalpayCredit.setText(String.valueOf(realCreditNumber) + "积分");
                        binding.tvEalpayPrice.setText("+¥" + format);
                    }

                }
            }
        });
    }


    private void setData(CreditsConfirmOrderEntity.ResultBean resultBean) {
        //是否是实物商品兑换
        int type = resultBean.getType();
        if (type == 0) {
            binding.consOne.setVisibility(View.VISIBLE);
            binding.tvFreightTip.setVisibility(View.VISIBLE);
            binding.tvFreight.setVisibility(View.VISIBLE);
            //收货地址
            CreditsConfirmOrderEntity.ResultBean.AddressBean address = resultBean.getAddress();
            if (address != null) {
                if (!TextUtils.isEmpty(address.getAddr_id())) {
                    binding.consOne.setVisibility(View.VISIBLE);
                    binding.consAddAddress.setVisibility(View.GONE);
                    binding.tvAddressName.setText(address.getRealname());
                    if (!TextUtils.isEmpty(address.getMobile())) {
                        StringBuilder sb = new StringBuilder(address.getMobile());
                        String showPhone = sb.replace(3, 7, "****").toString();
                        binding.tvAddressPhone.setText(showPhone);
                        if (!TextUtils.isEmpty(address.getProvince()) && !TextUtils.isEmpty(address.getCity())
                                && !TextUtils.isEmpty(address.getArea())) {
                            binding.tvAddress.setText(address.getProvince() + address.getCity() + address.getArea()
                                    + address.getAddress());
                        } else {
                            binding.tvAddress.setText(address.getAddress());
                        }
                    }
                } else {//未编辑收货地址
                    binding.consAddAddress.setVisibility(View.VISIBLE);
                    binding.consOne.setVisibility(View.GONE);
                }
            } else {
            }
        } else {
            binding.consOne.setVisibility(View.GONE);
            binding.consAddAddress.setVisibility(View.GONE);
            binding.tvFreightTip.setVisibility(View.VISIBLE);
            binding.tvFreight.setVisibility(View.VISIBLE);
        }
        //商品
        if (type == 0) {  //type    Int    类型【0:商品；1：优惠券；2：余额；3：红包】
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
        Glide.with(mContext).load(
                resultBean.getThumb()).into(binding.ivGood);
        binding.tvTitle.setText(resultBean.getTitle());
        if (!TextUtils.isEmpty(resultBean.getOption())) {
            binding.tvGoodspec.setText("规格：" + resultBean.getOption());
        } else {
            binding.tvGoodspec.setText("规格：暂无规格参数");
        }
        binding.tvCreditNum.setText(String.valueOf(resultBean.getCredit()));
        if (!TextUtils.isEmpty(resultBean.getMoney())){
            if (resultBean.getMoney().equals("0")){
                binding.tvGoodsPrice.setVisibility(View.GONE);
            }else {
                binding.tvGoodsPrice.setVisibility(View.VISIBLE);
                binding.tvGoodsPrice.setText("+¥" + resultBean.getMoney());
            }
        }
        //商品小计
        binding.tvMerchSubtotal.setText(String.valueOf(resultBean.getCredit()) + "积分");
        binding.price.setText("+¥" + resultBean.getMoney());
        String dispatch = resultBean.getDispatch();
        if (!TextUtils.isEmpty(dispatch)) {
            if (dispatch.equals("0.00") ||dispatch.equals("0")) { //免运费
                binding.tvFreight.setText("免运费");
            } else {
                binding.tvFreight.setText("+¥" + dispatch);
            }
        }
        int lotterydraws = resultBean.getLotterydraws();
        if (lotterydraws == 1) {//是否抽奖 0：否 1：是
            binding.tvGoodsNum.setText("1"); //默认数量1
        } else {
            binding.tvGoodsNum.setText("1");
        }
        // TODO: 2020/5/13 实付积分和金额 和数量有关
        binding.tvEalpayCredit.setText(String.valueOf(resultBean.getCredit()) + "积分");
        //实付金额 + 加运费
        double dispatchP = Double.parseDouble(dispatch); //运费
        double money = Double.parseDouble(resultBean.getMoney());
        double dPrice = dispatchP + money;
        DecimalFormat fnum = new DecimalFormat("##0.00");
        String format = fnum.format(dPrice);
        binding.tvEalpayPrice.setText("+¥" + format);

    }


    private void initMallRefresh() {
        binding.mallRefreshHeader.setEnableLastTime(true);                 //时间显示
        binding.mallRefreshHeader.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshHeader.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshHeader.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshFooter.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshFooter.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshFooter.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshLayout.setHeaderHeight(68);          //设置header高度
        binding.mallRefreshLayout.setFooterHeight(68);          //设置footer高度

        binding.mallRefreshLayout.setEnableRefresh(true);
        binding.mallRefreshLayout.setEnableAutoLoadmore(false);  //开启自动加载功能
        binding.mallRefreshLayout.setOnRefreshListener(this);
//        mallRefreshLayout.setOnLoadmoreListener()
        binding.mallRefreshLayout.setEnableLoadmore(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        viewModel.requestData(binding.mallRefreshLayout, addr_id, gid, optionid, "");
    }
}
