package com.jzd.jzshop.ui.home.creditsstore.order_detail;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityCreditOrderDetailBinding;
import com.jzd.jzshop.entity.CreditGetRedBagsEntity;
import com.jzd.jzshop.entity.CreditOrderDetailEntity;
import com.jzd.jzshop.entity.CreditToSureReceiptEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.home.creditsstore.comment.CreditCommentActivity;
import com.jzd.jzshop.ui.home.creditsstore.confirm_order.CreditConfirmOrderActivity;
import com.jzd.jzshop.ui.home.creditsstore.express.CreditShopExpressAty;
import com.jzd.jzshop.ui.home.creditsstore.goods_details.CreditGoodsDetailsActivity;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.dialog.MessageDialog;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author LXB
 * @description:
 * @date :2020/5/13 9:38
 */
public class CreditOrderDetailViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = CreditOrderDetailViewModel.class.getSimpleName();
    public static final String TOKEN_VIEWMODEL_EVALUATION_REFRESH = "token_viewmodel_evaluation_refresh_data";
    private Context context;
    private ActivityCreditOrderDetailBinding activityCreditOrderDetailBinding;
    private String log_id;
    private String merch_id;

    //绑定数据
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<CreditOrderDetailEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent<CreditToSureReceiptEntity> mToSureReceiptLiveData = new SingleLiveEvent<>();
//        public SingleLiveEvent<CreditGetRedBagsEntity.ResultBean> mGetRedBagsLiveData = new SingleLiveEvent<>();
    }

    public CreditOrderDetailViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context context, ActivityCreditOrderDetailBinding binding) {
        this.context = context;
        this.activityCreditOrderDetailBinding = binding;
    }


    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setTitleText(text);
        setToolbarBgColor(Color.parseColor("#D90804"));
        setTitleTextColor(Color.parseColor("#FFFFFF"));
        setIvBackIsVisible(View.GONE);
        setIvBackWhiteIsVisible(View.VISIBLE);
    }

    /**
     * 积分商城 订单详情
     *
     * @param log_id
     */
    public void requestData(String log_id,String merch_id) {
        this.log_id = log_id;
        isShowDialog(true);
        addSubscribe(model.postCreditOrderDetailData(model.getUserToken(),log_id,merch_id), new ParseDisposableTokenErrorObserver<CreditOrderDetailEntity>() {
            @Override
            public void onResult(CreditOrderDetailEntity creditOrderDetailEntity) {
                super.onResult(creditOrderDetailEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (creditOrderDetailEntity != null) {
                    CreditOrderDetailEntity.ResultBean result = creditOrderDetailEntity.getResult();
                    if (result != null) {
                        uc.mLiveData.setValue(result);
                    }
                }
                dismissDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                Log.d(TAG, "onError:---->>>" + throwable.getMessage());
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                startActivity(LoginVertifyActivity.class);
                dismissDialog();
            }
        });

    }

    /**
     *  确认收货
     * @param log_id
     * @param merch_id
     */
    public void postToSureReceipt(String log_id,String merch_id) {
        this.merch_id = merch_id;
        this.log_id = log_id;
        addSubscribe(model.postToSureReceipt(model.getUserToken(),log_id,merch_id), new ParseDisposableTokenErrorObserver<CreditToSureReceiptEntity>() {
            @Override
            public void onResult(CreditToSureReceiptEntity creditToSureReceiptEntity) {
                super.onResult(creditToSureReceiptEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (creditToSureReceiptEntity != null) {
                    uc.mToSureReceiptLiveData.setValue(creditToSureReceiptEntity);
                }
                dismissDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                Log.d(TAG, "onError:---->>>" + throwable.getMessage());
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                startActivity(LoginVertifyActivity.class);
                dismissDialog();
            }
        });

    }

    /**
     *  领取红包
     * @param log_id
     * @param merch_id
     */
    public void postGetRedBags(String log_id,String merch_id) {
        this.merch_id = merch_id;
        this.log_id = log_id;
       isShowDialog(false);
        addSubscribe(model.postGetRedBags(model.getUserToken(),log_id,merch_id), new ParseDisposableTokenErrorObserver<CreditGetRedBagsEntity>() {
            @Override
            public void onResult(CreditGetRedBagsEntity creditGetRedBagsEntity) {
                super.onResult(creditGetRedBagsEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (creditGetRedBagsEntity != null) {
//                    CreditGetRedBagsEntity.ResultBean result = creditToSureReceiptEntity.getResult();
//                    if (result != null) {
//                        uc.mGetRedBagsLiveData.setValue(result);
//                    }
                }
                dismissDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                Log.d(TAG, "onError:---->>>" + throwable.getMessage());
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                startActivity(LoginVertifyActivity.class);
                dismissDialog();
            }
        });

    }


    //确认收货
    public BindingCommand confirmReceiptOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            MessageDialog.showCancelAndSureDialog(context, "提示", "确认已收到货了吗?",
                    R.color.textColor, R.color.textColor, new MessageDialog.DialogClick() {
                        @Override
                        public void onSureClickListener() {
                            CreditOrderDetailEntity.ResultBean value = uc.mLiveData.getValue();
                            if (value != null) {
                                String log_id = value.getLog_id();
                                postToSureReceipt(log_id,merch_id);
                            }

                        }
                        @Override
                        public void onCancelClickListener() {
                        }
                    });
        }
    });

    // 查看物流信息
    public BindingCommand openExpressOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            CreditOrderDetailEntity.ResultBean value = uc.mLiveData.getValue();
            if (value != null) {
                if (!TextUtils.isEmpty(value.getLog_id())) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.GOODS_KEY_GID,value.getLog_id());
                    startActivity(CreditShopExpressAty.class, bundle);
                }
            }
        }
    });

    //打开商品详情
    public BindingCommand openStoreOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            CreditOrderDetailEntity.ResultBean value = uc.mLiveData.getValue();
            if (value != null) {
                if (!TextUtils.isEmpty(value.getGid())) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.CREDIT_GOODS_DETAIL,value.getGid());
                    startActivity(CreditGoodsDetailsActivity.class, bundle);
                }
            }
        }
    });

    //评价
    public BindingCommand evaluaionOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            CreditOrderDetailEntity.ResultBean value = uc.mLiveData.getValue();
            if (value != null) {
                if (!TextUtils.isEmpty(value.getLog_id())) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.GOODS_KEY_GID,value.getLog_id());
                    startActivity(CreditCommentActivity.class, bundle);
                }
            }
        }
    });

    //追加评价
    public BindingCommand addEvaluaionOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            CreditOrderDetailEntity.ResultBean value = uc.mLiveData.getValue();
            if (value != null) {
                if (!TextUtils.isEmpty(value.getLog_id())) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.GOODS_KEY_GID,value.getLog_id());
                    startActivity(CreditCommentActivity.class, bundle);
                }
            }
        }
    });

    //领取红包
    public BindingCommand redBagsOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            CreditOrderDetailEntity.ResultBean value = uc.mLiveData.getValue();
            if (value != null) {
                String log_id = value.getLog_id();
                postGetRedBags(log_id,merch_id);
            }
        }
    });

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        AppManager.getAppManager().finishActivity(CreditConfirmOrderActivity.class);
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().finishActivity(CreditConfirmOrderActivity.class);
        finish();
    }
}
