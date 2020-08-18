package com.jzd.jzshop.ui.pay;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentPaySuccessBinding;
import com.jzd.jzshop.entity.OrderPayIndexEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.base.fragment.BaseWebviewFragment;
import com.jzd.jzshop.ui.order.orderdetail.OrderDetailAty;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.NetworkUtils;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;

import java.net.URLEncoder;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class PaySuccessViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = PaySuccessViewModel.class.getSimpleName();
    private FragmentPaySuccessBinding binding;
    private Context mContext;
    public static final String TOKEN_VIEWMODEL_OPENFLAG = "token_viewmodel_openflag";

    public ObservableField<OrderPayIndexEntity.ResultBean> entity = new ObservableField<>();

    public PaySuccessViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(FragmentPaySuccessBinding binding, FragmentActivity activity) {
        this.binding = binding;
        this.mContext = activity;
    }

    public void initToolbar() {
        setTitleText("支付成功");
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        model.saveOpenFlag("0");//保存打开标识
        Messenger.getDefault().sendNoMsg(TOKEN_VIEWMODEL_OPENFLAG);  //发送 关闭收银台页面
        finish();
    }

    public void requestNetWork(String order_id, String pay_type, String ali_result) {
        addSubscribe(model.postOrderPayIndex(model.getUserToken(), order_id, pay_type, ali_result), new ParseDisposableObserver<OrderPayIndexEntity>() {
            @Override
            public void onResult(OrderPayIndexEntity orderPayIndexEntity) {
                dismissDialog();
                entity.set(orderPayIndexEntity.getResult());
            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
            }
        });
    }


    //查看订单
    public BindingCommand onClickOrderDetails = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //TODO 跳h5 ,原生订单详情也已有，调原生 我的订单
//            goToBaseWebviewNew(Constants.MY_ORDER_DETAILS);
//             setBackOnClick();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.ORDER_ID, entity.get().getOrder_id());
//            bundle.putString(Constants.ORDER_PRICE, entity.get().getOrder_price());
            startActivity(OrderDetailAty.class, bundle);
            finish();
        }
    });

    /**
     * 订单详情页
     *
     * @param orderStatus
     */
    private void goToBaseWebviewNew(String orderStatus) {
        int netState = NetworkUtils.getNetWorkState(mContext);
        Bundle bundle = new Bundle();
        if (netState == -1) {
            ToastUtils.showLong("网络连接异常");
            return;
        }
        String urlF = orderStatus + entity.get().getOrder_id();
        String encode = URLEncoder.encode(urlF); //url参数转义
        if (entity.get() != null && !TextUtils.isEmpty(model.getUserToken())) {
            String url = RetrofitClient.baseUrl + Constants.MY_ORDER_JUMP_URL + encode + "&user_token=" + model.getUserToken();
            Log.d(TAG, "goToBaseWebviewNew:" + url);
            bundle.putString(Constants.BUNDLE_KEY_URL, url);
            startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
        } else {
            ToastUtils.showLong("请登录！！");
        }
    }


}
