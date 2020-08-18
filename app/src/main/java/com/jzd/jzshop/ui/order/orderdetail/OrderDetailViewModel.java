package com.jzd.jzshop.ui.order.orderdetail;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.chat.OpenChatActivity;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityOrederDetailBinding;
import com.jzd.jzshop.entity.CancelRefundEntity;
import com.jzd.jzshop.entity.OrderDetailEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.order.aftersale.ApplyForRefundAty;
import com.jzd.jzshop.ui.order.aftersale.SalesApplyProgressAty;
import com.jzd.jzshop.ui.order.comment.CommentAty;
import com.jzd.jzshop.ui.pay.PayFragment;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.jzd.jzshop.utils.dialog.MessageDialog;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class OrderDetailViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = OrderDetailViewModel.class.getSimpleName();
    public static final String TOKEN_ORDERDETAILVIEWMODEL_REFRESH = "token_orderdetailviewmodel_refresh_list_data";
    public static final String TOKEN_VIEWMODEL_SUBMIT_APPLY_REFRESH = "token_viewmodel_submit_apply_refresh_list_data";
    public static final String TOKEN_VIEWMODEL_CANCLE_APPLY_REFRESH = "token_viewmodel_cancle_apply_refresh_list_data";
    public static final String TOKEN_VIEWMODEL_REFUND_RECEIVE_REFRESH = "token_viewmodel_refund_receive_refresh_list_data";
    private Context context;
    private ActivityOrederDetailBinding activityOrederDetailBinding;
    private String order_id;

    //绑定数据
    public UIChangeObservable uc = new UIChangeObservable();
    private int isEvaluation;

    public void setIsEvaluation(int isEvaluation) {
        this.isEvaluation = isEvaluation;
    }

    public int getIsEvaluation() {
        return isEvaluation;
    }

    public class UIChangeObservable {
        public SingleLiveEvent<OrderDetailEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent cancleOrderLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent deleteOrderLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent confirmReceiptLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent<CancelRefundEntity> cancleApplyLiveData = new SingleLiveEvent<>(); //取消申请

    }

    public OrderDetailViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context context, ActivityOrederDetailBinding binding) {
        this.context = context;
        this.activityOrederDetailBinding = binding;
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

    //查看快递信息
    public BindingCommand constrlExpressOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO: 2020/1/15
        }
    });

    //联系商家
    public BindingCommand cantactMerchantClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            MerchantCustomerService();
        }
    });

    //取消订单
    public BindingCommand cacelOrderOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            MessageDialog.showCancelAndSureDialog(context, "提示","确认要取消该订单吗?",
                    R.color.textColor, R.color.textColor, new MessageDialog.DialogClick() {
                @Override
                public void onSureClickListener() {
                    cancleOrder(order_id);
                }

                @Override
                public void onCancelClickListener() {
                }
            });
        }
    });

    //支付订单
    public BindingCommand payOrderOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            String realPrice = ((OrderDetailAty) context).getRealPrice();
            OrderDetailEntity.ResultBean value = uc.mLiveData.getValue();
            Log.d(TAG, "realPrice====:" + realPrice + "order_id" + value.getOrder_id());
            model.saveOpenFlag("0");//保存打开标识
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_KEY_ORDERID, value.getOrder_id());
            bundle.putString(Constants.BUNDLE_KEY_MONEY, String.valueOf(realPrice));
            startContainerActivity(PayFragment.class.getCanonicalName(), bundle);
            finish();
        }
    });

    //代付
    public BindingCommand paymentOnBehalfOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO: 2020/1/15
        }
    });

    //删除订单
    public BindingCommand deleteOrderOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            MessageDialog.showCancelAndSureDialog(context, "提示","确认要删除该订单吗?", R.color.textColor, R.color.textColor, new MessageDialog.DialogClick() {
                @Override
                public void onSureClickListener() {
                    deleteOrder(order_id);
                }

                @Override
                public void onCancelClickListener() {
                }
            });

        }
    });

    //确认收货
    public BindingCommand confirmReceiptOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            MessageDialog.showCancelAndSureDialog(context, "提示","确认已收到货了吗?", R.color.textColor, R.color.textColor, new MessageDialog.DialogClick() {
                @Override
                public void onSureClickListener() {
                    onfirmGoods(order_id);
                }

                @Override
                public void onCancelClickListener() {
                }
            });
        }
    });
    //申请退款
    public BindingCommand applyExitAmountOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            OrderDetailEntity.ResultBean value = uc.mLiveData.getValue();
            Log.d(TAG,   "order_id：=====" + value.getOrder_id());
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_KEY_TITLE, context.getResources().getString(R.string.apply_exitAmount));
            bundle.putString(Constants.ORDER_ID, value.getOrder_id());
            startActivity(ApplyForRefundAty.class,bundle);
        }
    });
    //提醒发货
    public BindingCommand reminderShipmentOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO: 2020/1/14
        }
    });
    //评价
    public BindingCommand evaluaionOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO: 2020/1/16  需要是判断是评价还是  追评
            OrderDetailEntity.ResultBean value = uc.mLiveData.getValue();
            int iscomment = value.getIscomment();
            if (iscomment != 2){ //非已评价
                if (value != null) {
                    Log.d(TAG,   "order_id：=====" + value.getOrder_id());
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.ORDER_ID, value.getOrder_id());
                    bundle.putString(Constants.IS_EVALUATION, String.valueOf(isEvaluation));
                    startActivity(CommentAty.class,bundle);
                }
            }
        }
    });

    //申请售后
    public BindingCommand applyAfterSaleOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            OrderDetailEntity.ResultBean value = uc.mLiveData.getValue();
            Log.d(TAG,"order_id：=====" + value.getOrder_id());
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_KEY_TITLE, context.getResources().getString(R.string.apply_after_sale));
            bundle.putString(Constants.ORDER_ID, value.getOrder_id());
            startActivity(ApplyForRefundAty.class,bundle);
        }
    });

    //取消申请
    public BindingCommand cancelApplyOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            OrderDetailEntity.ResultBean value = uc.mLiveData.getValue();
            if (value != null) {
                int refund_id = value.getRefund_id();
                cancelRefund(String.valueOf(refund_id));
            }
        }
    });

    //售后申请退款进度 /售后申请进度
    public BindingCommand salesRefundProgressOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            OrderDetailEntity.ResultBean value = uc.mLiveData.getValue();
            Log.d(TAG,"order_id：=====" + value.getOrder_id());
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BUNDLE_KEY_TITLE, context.getResources().getString(R.string.apply_after_sale));
            bundle.putString(Constants.ORDER_ID, value.getOrder_id());
            startActivity(SalesApplyProgressAty.class,bundle);
        }
    });

    /**
     *  订单详情数据
     * @param order_id
     */
    public void requestData(String order_id) {
        this.order_id = order_id;
        isShowDialog(true);
        addSubscribe(model.postOrderDetailData(model.getUserToken(), order_id), new ParseDisposableTokenErrorObserver<OrderDetailEntity>() {
            @Override
            public void onResult(OrderDetailEntity orderDetailEntity) {
                super.onResult(orderDetailEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (orderDetailEntity != null) {
                    OrderDetailEntity.ResultBean result = orderDetailEntity.getResult();
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

    /*取消订单*/
    public void cancleOrder(String order_id) {
        addSubscribe(model.postCancleOrder(model.getUserToken(), order_id), new ParseDisposableTokenErrorObserver() {
            @Override
            public void onResult(Object o) {
                super.onResult(o);
                dismissDialog();
                uc.cancleOrderLiveData.setValue(o);
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                startActivity(LoginVertifyActivity.class);
                dismissDialog();
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
            }
        });
    }

    public void deleteOrder(String order) {
        addSubscribe(model.postDeleteOrder(model.getUserToken(), order), new ParseDisposableTokenErrorObserver() {
            @Override
            public void onResult(Object o) {
                super.onResult(o);
                uc.deleteOrderLiveData.setValue(o);
                dismissDialog();
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
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


    public void onfirmGoods(String order) {
        addSubscribe(model.postConfirmGoods(model.getUserToken(), order), new ParseDisposableTokenErrorObserver() {
            @Override
            public void onResult(Object o) {
                super.onResult(o);
                uc.confirmReceiptLiveData.setValue(o);
                dismissDialog();
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
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
     * 申请退款、售后（取消）
     *
     * @param refund_id
     */
    public void cancelRefund(String refund_id) {
        addSubscribe(model.postCancelRefundOrder(model.getUserToken(), order_id, refund_id), new ParseDisposableTokenErrorObserver<CancelRefundEntity>() {
            @Override
            public void onResult(CancelRefundEntity cancelRefundEntity) {
                super.onResult(cancelRefundEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (cancelRefundEntity != null) {
                    ToastUtils.showShort("已取消申请退款！");
                    uc.cancleApplyLiveData.setValue(cancelRefundEntity);
                }
                dismissDialog();
                finish();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
            }
        });
    }

    private void MerchantCustomerService() {
//        if (QQUtils.installedApp(context, "com.tencent.mobileqq") || QQUtils.installedApp((context), "com.tencent.tim")) {
//            QQUtils.openPersonalQQ(context, context.getResources().getString(R.string.online_service_qq));
//        } else {
//            ToastUtils.showShort("本机未安装QQ应用");
//            return;
//        }
        //打开客服聊天
        OrderDetailEntity.ResultBean value = uc.mLiveData.getValue();
        Log.d(TAG,"chat_id：=====" + value.getChat_id());
        Bundle bundle = new Bundle();
        bundle.putString(Constants.GOODS_KEY_GID, "");
        bundle.putString(Constants.GOODS_KEY_CHAT_ID, value.getChat_id());
        bundle.putString(Constants.SP.ACTIVITY_OPEN_FLAG, "order_detail");
        startActivity(OpenChatActivity.class, bundle);
    }


    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }


}
