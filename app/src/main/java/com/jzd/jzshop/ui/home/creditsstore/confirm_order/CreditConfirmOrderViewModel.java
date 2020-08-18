package com.jzd.jzshop.ui.home.creditsstore.confirm_order;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityCreditConfirmOrderBinding;
import com.jzd.jzshop.entity.CreditsConfirmOrderEntity;
import com.jzd.jzshop.entity.ExchageGoodsNumEntity;
import com.jzd.jzshop.entity.LotteryEntity;
import com.jzd.jzshop.entity.OrderToPayEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.home.creditsstore.goods_details.CreditGoodsDetailsActivity;
import com.jzd.jzshop.ui.home.creditsstore.lottery.CreditLotteryAty;
import com.jzd.jzshop.ui.home.creditsstore.pay.CreditPayActivity;
import com.jzd.jzshop.ui.home.creditsstore.pay.CreditPaySuccessAty;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.mine.address.CompileAddressFragment;
import com.jzd.jzshop.ui.mine.address.ReceiptAddressFragment;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.dialog.MessageDialog;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description:
 * @date :2020/5/13 15:51
 */
public class CreditConfirmOrderViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = CreditConfirmOrderViewModel.class.getSimpleName();
    private Context context;
    private ActivityCreditConfirmOrderBinding mBinding;
    private int sign = 0;
    private String optionid;
    private int number = 1;
    private String dispatch;// 改变商品数量后的运费

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<CreditsConfirmOrderEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent<ExchageGoodsNumEntity.ResultBean> mExchageGoodsNumLiveData = new SingleLiveEvent<>();
    }

    public CreditConfirmOrderViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityCreditConfirmOrderBinding binding, String optionid) {
        this.context = mContext;
        this.mBinding = binding;
        this.optionid = optionid;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }

    /**
     * 提交订单 展示数据
     *
     * @param smartRefreshLayout
     * @param gid
     * @param optionid
     * @param merch_id
     */
    public void requestData(SmartRefreshLayout smartRefreshLayout, String addr_id, String gid, String optionid, String merch_id) {
        isShowDialog(false);
        addSubscribe(model.postCreditConfirmOrder(model.getUserToken(), addr_id, gid, optionid, merch_id), new ParseDisposableTokenErrorObserver<CreditsConfirmOrderEntity>() {
            @Override
            public void onResult(CreditsConfirmOrderEntity creditsConfirmOrderEntity) {
                super.onResult(creditsConfirmOrderEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (creditsConfirmOrderEntity != null) {
                    if (creditsConfirmOrderEntity.getResult() != null) {
                        uc.mLiveData.setValue(creditsConfirmOrderEntity.getResult());
                    }
                }
                smartRefreshLayout.finishRefresh();
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
                smartRefreshLayout.finishRefresh();
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
                smartRefreshLayout.finishRefresh();
            }
        });
    }

    /**
     * 变更数量
     *
     * @param gid
     * @param optionid
     * @param addr_id
     * @param num
     * @param merch_id
     */
    public void requestExchageGoodsNum(String gid, String optionid,
                                       String addr_id, String num, String merch_id) {
        isShowDialog(false);
        addSubscribe(model.requestExchageGoodsNum(model.getUserToken(), gid, optionid, addr_id, num, merch_id),
                new ParseDisposableTokenErrorObserver<ExchageGoodsNumEntity>() {
                    @Override
                    public void onResult(ExchageGoodsNumEntity exchageGoodsNumEntity) {
                        super.onResult(exchageGoodsNumEntity);
                        Log.d(TAG, "onSuccess:---->>>");
                        if (exchageGoodsNumEntity != null) {
                            if (exchageGoodsNumEntity.getResult() != null) {
                                // 改变商品数量后的运费
                                dispatch = exchageGoodsNumEntity.getResult().getDispatch();
                                int status =  exchageGoodsNumEntity.getResult().getStatus();
                                if (status == 0){//	1：正常配送 0：不支持配送
                                    number--;
                                }else if (status ==1){
                                }else {}
                                uc.mExchageGoodsNumLiveData.setValue(exchageGoodsNumEntity.getResult());
                            }
                        }
                        dismissDialog();
                    }

                    @Override
                    protected void onTokenError() {
                        super.onTokenError();
                        number--;
                        dismissDialog();
                        startActivity(LoginVertifyActivity.class);
                    }

                    @Override
                    public void onError(String errarMessage) {
                        super.onError(errarMessage);
                        number--;
                        dismissDialog();
                    }
                });
    }

    /**
     * 积分商品详情页
     */
    public BindingCommand openGoodsDetailOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            CreditsConfirmOrderEntity.ResultBean value = uc.mLiveData.getValue();
            if (value != null) {
                if (!TextUtils.isEmpty(value.getGid())) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.CREDIT_GOODS_DETAIL, value.getGid());
                    startActivity(CreditGoodsDetailsActivity.class, bundle);
                }
            }
        }
    });

    /**
     * 更换地址
     */
    public BindingCommand AddAddressOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putString("compile", "添加地址");
            startContainerActivity(CompileAddressFragment.class.getCanonicalName(), bundle);
        }
    });
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

    //-
    public BindingCommand reduceNumOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (number > 1) {
                number--;
            } else {
            }
            CreditsConfirmOrderEntity.ResultBean value = uc.mLiveData.getValue();
            if (value != null) {
                String gid = value.getGid();
                if (value.getAddress() != null) {
                    String addr_id = value.getAddress().getAddr_id();
                    Log.d(TAG, "变更商品数量 入参：----->>>\n gid:" + gid + "\n optionid：" + optionid + "\n addr_id：" + addr_id);
                    requestExchageGoodsNum(gid, optionid, addr_id, String.valueOf(number), "");
                }
            }

        }
    });
    //+
    public BindingCommand addNumOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            number++;
            CreditsConfirmOrderEntity.ResultBean value = uc.mLiveData.getValue();
            if (value != null) {
                String gid = value.getGid();
                if (value.getAddress() != null) {
                    String addr_id = value.getAddress().getAddr_id();
                    Log.d(TAG, "变更商品数量 入参：----->>>\n gid:" + gid + "\n optionid：" + optionid + "\n addr_id：" + addr_id);
                    requestExchageGoodsNum(gid, optionid, addr_id, String.valueOf(number), "");
                }
            }
        }
    });

    //立即支付
    public BindingCommand goPayOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            CreditsConfirmOrderEntity.ResultBean value = uc.mLiveData.getValue();
            ExchageGoodsNumEntity.ResultBean valueGoodsNum = uc.mExchageGoodsNumLiveData.getValue();
            if (value != null) {
                int type = value.getType();
                int dispatch_status = value.getDispatch_status();
                if (value.getAddress() != null) {
                    Bundle bundle = new Bundle();
                    String gid = value.getGid();
                    String addr_id = value.getAddress().getAddr_id();
                    bundle.putString(Constants.GOODS_KEY_GID, gid);
                    bundle.putString(Constants.GOODS_KEY_CREDIT_GOODS_TYPE, optionid);
                    bundle.putString(Constants.GOODS_KEY_CREDIT_ADDR_ID, addr_id);
                    bundle.putInt(Constants.GOODS_KEY_CREDIT_GOODS_NUMBER, number);
                    double dispatchD = 0;
                    if (!TextUtils.isEmpty(dispatch)){
                        dispatchD = Double.parseDouble(dispatch);
                    }else {
                        String dispatch = value.getDispatch();
                        if (!TextUtils.isEmpty(dispatch)){
                            dispatchD = Double.parseDouble(value.getDispatch());
                        }else {}
                    }
                    double money = Double.parseDouble(value.getMoney());
                    double dPrice = dispatchD + money;//最终实付价
                    if (dPrice == 0) {//金额为0是 使用积分消费  不走收银台
                        if (type == 0) {//是否是商品
                            if (dispatch_status == 1) {//支持配送
                                mIsExchageOrLotterydraws(value, bundle);
                            } else if (dispatch_status == 2) {
                                ToastUtils.showShort(context.getResources().getString(R.string.delivery_not_supported));
                                return;
                            } else {
                            }
                        } else {
                            mIsExchageOrLotterydraws(value, bundle);
                        }
                    } else {// 积分+金额
                        if (type == 0) {     //是商品
                            //当前地址是否支付配送。1：支持 2：不支持（表示无法进行下单，需要重新编辑收货地址）
                            if (dispatch_status == 1) {
                                mCommon(value, bundle);
                            } else if (dispatch_status == 2) {
                                ToastUtils.showShort(context.getResources().getString(R.string.delivery_not_supported));
                                return;
                            } else {
                            }
                        } else {
                            mCommon(value, bundle);
                        }
                    }

                }
            }

        }
    });

    /**
     * 积分+金额 下单流程
     *
     * @param value
     * @param bundle
     */
    private void mCommon(CreditsConfirmOrderEntity.ResultBean value, Bundle bundle) {
        bundle.putInt(Constants.GOODS_KEY_CREDIT_ISLOTTERY, value.getLotterydraws());
        startActivity(CreditPayActivity.class, bundle);
//        finish();
    }

    /**
     * 纯积分支付 是抽奖还是退换 下单流程
     *
     * @param value
     * @param bundle
     */
    private void mIsExchageOrLotterydraws(CreditsConfirmOrderEntity.ResultBean value, Bundle bundle) {
        if (value.getLotterydraws() == 0) {//兑换 不需要付金额  弹窗确定兑换
            showDiaIsgetShop(value);
            return;
        } else if (value.getLotterydraws() == 1) {//抽奖  不用金额 直接跳转抽奖页面
            bundle.putInt(Constants.GOODS_KEY_CREDIT_ISPAY, 0);
            startActivity(CreditLotteryAty.class, bundle);
//            finish();
            return;
        } else {
        }
    }


    //是否需要兑换  弹窗
    private void showDiaIsgetShop(CreditsConfirmOrderEntity.ResultBean value) {
        MessageDialog.showIsgetShop(context, "确定要兑换吗?", new MessageDialog.DialogClick() {

            @Override
            public void onSureClickListener() {
                int option = 0;
                if (!TextUtils.isEmpty(optionid)) {
                    option = Integer.parseInt(optionid);
                }
                //预支付
                requestPayOrder("default", value.getGid(), option, value.getAddress().getAddr_id(), number);
            }

            @Override
            public void onCancelClickListener() {

            }
        });
    }


    //预支付接口
    public void requestPayOrder(String aDefault, String gid, int optionid, String add_id, int number) {
        addSubscribe(model.postCreditTopay(model.getUserToken(), aDefault, gid, optionid, add_id, number), new ParseDisposableTokenErrorObserver<OrderToPayEntity>() {
            @Override
            public void onResult(OrderToPayEntity o) {
                super.onResult(o);
                dismissDialog();
                //正式兑换
                reuquestLottery();
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

    //正式兑换
    public void reuquestLottery() {
        isShowDialog(false);
        addSubscribe(model.postCreditLottery(model.getUserToken()), new ParseDisposableTokenErrorObserver<LotteryEntity>() {
            @Override
            public void onResult(LotteryEntity o) {
                super.onResult(o);
                dismissDialog();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.GOODS_KEY_CREDIT_PAY_RESULT, o.getResult().getStatus());
                bundle.putString(Constants.GOODS_KEY_CREDIT_PAY_LOGID, o.getResult().getLog_id());
                bundle.putInt(Constants.GOODS_KEY_CREDIT_ISLOTTERY, 0);
                startActivity(CreditPaySuccessAty.class, bundle);
                finish();
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
}
