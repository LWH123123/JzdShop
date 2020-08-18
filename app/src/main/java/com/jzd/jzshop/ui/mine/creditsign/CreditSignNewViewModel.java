package com.jzd.jzshop.ui.mine.creditsign;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityCreditSignNewBinding;
import com.jzd.jzshop.entity.SignDateEntity;
import com.jzd.jzshop.entity.SignEntity;
import com.jzd.jzshop.entity.SignReceiveEntity;
import com.jzd.jzshop.entity.ToSignEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.mine.creditsign.ranking.SignRankingActivity;
import com.jzd.jzshop.utils.BaseWebviewUtils;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * @author LXB
 * @description:
 * @date :2020/4/7 16:51
 */
public class CreditSignNewViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = CreditSignNewViewModel.class.getSimpleName();
    private Context context;
    private ActivityCreditSignNewBinding activityCreditSignBinding;
    public ObservableField<SignEntity.ResultBean> signentity = new ObservableField<>();
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent getSignLiveData = new SingleLiveEvent<SignEntity>();
        public SingleLiveEvent toSignLiveData = new SingleLiveEvent<ToSignEntity>();
        public SingleLiveEvent toRepairSignLiveData = new SingleLiveEvent<ToSignEntity>();
        public SingleLiveEvent getSignDateLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent<SignDateEntity> updateCalendarData =new SingleLiveEvent<SignDateEntity>();
        public SingleLiveEvent timeselect = new SingleLiveEvent();
        public SingleLiveEvent toReceiveLiveData = new SingleLiveEvent<SignReceiveEntity>();
    }

    public void setBinding(Context context, ActivityCreditSignNewBinding binding) {
        this.context = context;
        this.activityCreditSignBinding = binding;
    }

    public CreditSignNewViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setIvBackWhiteIsVisible(View.VISIBLE);
        setIvBackIsVisible(View.GONE);
        setToolbarBgColor(Color.parseColor("#D90804"));
        setTitleTextColor(Color.parseColor("#FFFFFF"));

        setRightText(context.getResources().getString(R.string.sign_rule));
        setRightTextVisible(View.VISIBLE);
        setRightTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    protected void rightTextOnClick() { //签到规则
        super.rightTextOnClick();
        signRule();

    }


    //日期选择
    public BindingCommand onSelectTimeClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.timeselect.call();
        }
    });

    //点击签到
    public BindingCommand onSignClickListeren = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 传值表示补签，不传值表示当天签到
            postToSign("");
        }
    });

    //签到排行
    public BindingCommand onClickSignsignRankingListener = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(SignRankingActivity.class);
        }
    });
    //签到记录
    public BindingCommand onClickSignRecordListener = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startContainerActivity(SignRecordFragment.class.getCanonicalName());
        }
    });


    /**
     * 签到规则
     */
    public void signRule() {
        if (signentity.get() != null) {
            if (!TextUtils.isEmpty(signentity.get().getSign_rule())){
                showCreditSignRulesDialog(signentity.get().getSign_rule());
            }
        }
    }

    private void showCreditSignRulesDialog(final String sign_rule) {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_sign_rules)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        WebView web_view = holder.getView(R.id.web_view);
                        View tv_confirm = holder.getView(R.id.tv_confirm);//关闭
                        setPopListener(dialog, web_view, tv_confirm, sign_rule);
                    }
                })
                .setWidth(300)
                .setDimAmount(0.5f)//调节灰色背景透明度[0-1]，默认0.5f
                .setShowBottom(false)//是否在底部显示dialog，默认flase
                .setOutCancel(false)   //点击dialog外是否可取消，默认true
                .setAnimStyle(R.style.PopupAnimation_Up_Down)
                .show(((CreditSignNewActivity) context).getSupportFragmentManager());  //显示dialog
    }

    private void setPopListener(final BaseNiceDialog dialog, WebView webView, View tv_confirm, String sign_rule) {
        String htmlData = BaseWebviewUtils.getHtmlData(sign_rule);
        webView.loadDataWithBaseURL(
                null,
                htmlData,
                "text/html",
                "UTF-8",
                null
        );
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 签到首页（展示）
     */
    public void postGetSign() {
        addSubscribe(model.postGetSign(model.getUserToken()), new ParseDisposableTokenErrorObserver<SignEntity>() {
            @Override
            public void onResult(SignEntity signEntity) {
                super.onResult(signEntity);
                if (signEntity!=null){
                    SignEntity.ResultBean result = signEntity.getResult();
                    if (result!=null){
                        signentity.set(result);
                    }
                    Log.d(TAG, " postGetSign onSuccess:---->>>");
                    if (signEntity != null) {
                        uc.getSignLiveData.setValue(signEntity);
                    }
                    activityCreditSignBinding.refreshLayout.finishRefresh();
                }
                dismissDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                Log.d(TAG, "onError:---->>>" + throwable.getMessage());
                dismissDialog();
                activityCreditSignBinding.refreshLayout.finishRefresh();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                startActivity(LoginVertifyActivity.class);
                dismissDialog();
                activityCreditSignBinding.refreshLayout.finishRefresh();

            }
        });
    }


    /**
     * 签到（补签）
     *
     * @param date
     */
    public void postToSign(final String date) {
        isShowDialog(false);
        addSubscribe(model.postToSign(model.getUserToken(), date), new ParseDisposableTokenErrorObserver<ToSignEntity>() {
            @Override
            public void onResult(ToSignEntity toSignEntity) {
                super.onResult(toSignEntity);
                dismissDialog();
                Log.d(TAG, " postToSign onSuccess:---->>>");
                if (TextUtils.isEmpty(date)) {
                    if (toSignEntity != null) {
                        uc.toSignLiveData.setValue(toSignEntity);
                    }
                } else {//补签
                    if (toSignEntity != null) {
                        uc.toRepairSignLiveData.setValue(toSignEntity);
                    }
                }
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
     * 签到日期数据（展示）
     *
     * @param date
     */
    public void postGetSignDate(String date) {
        addSubscribe(model.postGetSignDate(model.getUserToken(), date), new ParseDisposableTokenErrorObserver<SignDateEntity>() {
            @Override
            public void onResult(SignDateEntity signDateEntity) {
                super.onResult(signDateEntity);
                dismissDialog();
                Log.d(TAG, " postGetSignDate onSuccess:---->>>");
                if (signDateEntity != null) {
                    uc.updateCalendarData.setValue(signDateEntity);
                }
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
     *  领取积分
     * @param type
     * @param data
     */
    public void postToReceive(final String type,String data) {
        isShowDialog(false);
        addSubscribe(model.postToReceive(model.getUserToken(), type,data), new ParseDisposableTokenErrorObserver<SignReceiveEntity>() {
            @Override
            public void onResult(SignReceiveEntity signReceiveEntity) {
                super.onResult(signReceiveEntity);
                dismissDialog();
                Log.d(TAG, " postToSign onSuccess:---->>>");
                if (signReceiveEntity != null) {
                    uc.toReceiveLiveData.setValue(signReceiveEntity);
                }
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


    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }
}
