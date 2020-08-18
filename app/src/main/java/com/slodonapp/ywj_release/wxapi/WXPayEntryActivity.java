package com.slodonapp.ywj_release.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.widget.Toast;

import com.jzd.jzshop.entity.OrderToPayEntity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import me.goldze.mvvmhabit.utils.KLog;
import tech.jianyue.auth.AuthActivity;

public class WXPayEntryActivity extends AuthActivity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, AppConst.WEIXIN_APP_ID);
        api.handleIntent(getIntent(), this);
        KLog.d();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        KLog.d();
    }

    public static void payWeixin(IWXAPI api, OrderToPayEntity.ResultBean.DataBean dataBean, WXEntryActivity.ApiCallback callback) {
        PayReq req = new PayReq();
        req.appId = dataBean.getApp_id();
        req.partnerId = dataBean.getPartner_id();
        req.prepayId = dataBean.getPrepay_id();
        req.packageValue = "Sign=WXPay";
        req.nonceStr = dataBean.getNoncestr();
        req.timeStamp = dataBean.getTimestamp();
        req.sign = dataBean.getSign();
        req.extData = "app data"; // optional
        api.sendReq(req);
        payCallback = callback;
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

//    @Override
//    public void onResp(BaseResp resp) {
//        int code = resp.errCode;
//        switch (code) {
//            case 0://支付成功后的界面
//                if (payCallback != null)
//                    payCallback.onSuccess("支付成功"+resp.getType());
//                //返回主页面 然后在跳转至订单页面
//                this.finish();
//                break;
//            case -1:
//                //ToastUtil.showToast(getApplicationContext(), "签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、您的微信账号异常等。");
//                ToastUtils.showShort("支付失败");
//                this.finish();
//                break;
//            case -2://用户取消支付后的界面
//                ToastUtils.showShort("您已取消支付");
//                this.finish();
//                break;
//        }
//        //微信支付后续操作，失败，成功，取消
//    }

    @Override
    public void onResp(BaseResp resp) {
        String result = "@=";
        Log.d("onResp:----->>>",String.valueOf(resp.errCode));
        switch (resp.errCode) {
            // 发送成功
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case ConstantsAPI.COMMAND_PAY_BY_WX:
                        result = "支付成功!";
                        if (payCallback != null)
                            payCallback.onSuccess(result);
                        finish();
                        break;
                }
                break;
            default:
                result = "支付失败";
                showMessage(result);
                if(payCallback!=null){
                    payCallback.onPayError(result);
                }
                finish();
                break;
        }
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        KLog.e(msg);

    }

    private static WXEntryActivity.ApiCallback<String> payCallback;

}