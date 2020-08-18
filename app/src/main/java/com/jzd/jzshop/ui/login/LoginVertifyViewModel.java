package com.jzd.jzshop.ui.login;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityLoginVertifyBinding;
import com.jzd.jzshop.entity.LoginEntity;
import com.jzd.jzshop.entity.UserInfo;
import com.jzd.jzshop.ui.mine.news.MyPageActivity;
import com.jzd.jzshop.utils.SystemUtils;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;
import com.slodonapp.ywj_release.wxapi.WXUserInfo;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description:
 * @date :2019/12/2 13:59
 */
public class LoginVertifyViewModel extends BaseViewModel<Repository> {
    private static final String TAG = LoginVertifyViewModel.class.getSimpleName();
    private String car;

    public void setSign(String message) {
        this.car = message;
    }

    private ActivityLoginVertifyBinding mBinding;
    private LoginVertifyActivity activity;

    //绑定手机号、验证码
    public ObservableField<String> phone = new ObservableField<>("");
    public ObservableField<String> vertifyCode = new ObservableField<>("");
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent getvertify = new SingleLiveEvent<>();
    }

    public void setBinding(ActivityLoginVertifyBinding binding, LoginVertifyActivity loginVertifyActivity) {
        this.activity = loginVertifyActivity;
        this.mBinding = binding;
    }

    public LoginVertifyViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    //发送验证码
    public BindingCommand sendVertifyOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            String etPhone = phone.get();
            if (etPhone.equals("") || etPhone.length() < 11) {
                ToastUtils.showLong("请输入正确手机号");
            } else {
                boolean mobileNO = SystemUtils.isMobileNO(etPhone);
                if (mobileNO) {
                    sendVerifyCode(etPhone, "login");
                    uc.getvertify.call();
                } else {
                    ToastUtils.showLong("请输入正确手机号");
                }
            }
        }
    });

    /**
     * 发送验证码
     *
     * @param etPhone
     */
    private void sendVerifyCode(String etPhone, String type) {
        addSubscribe(model.postSendVertifyCode(etPhone, type), new ParseDisposableObserver<BaseResponse>() {
            @Override
            public void onResult(BaseResponse sendVertifyEntity) {
                dismissDialog();
                ToastUtils.showLong("验证码发送成功!");
            }

            @Override
            public void onError(String errarMessage) {
                KLog.e("sendVerifyCode　　onError: －－－－＞＞＞" + errarMessage);
                dismissDialog();
            }
        });

    }

    //登录按钮的点击事件
    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            String etPhone = phone.get();
            String etVertifyCode = vertifyCode.get();
            login(etPhone, etVertifyCode);
        }
    });
    //登录按钮的点击事件
    public BindingCommand wxLoginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Log.d(TAG, "微信授权登录---->>>");
            /**
             * todo: 直接对接微信平台 授权登录api 接口
             * tip : 暂时保留，存在token 失效问题
             */
//            WXEntryActivity.loginWeixin(AppApplication.getInstance(), AppApplication.sApi, new WXEntryActivity.ApiCallback<WXUserInfo>() {
////                @Override
////                public void onSuccess(WXUserInfo info) {
////                    Log.d("onSuccess()--->>", info.toString());
////                    requestLoginNetWork(info);
////                    //当用户使用第三方账号（如新浪微博）登录时，可以这样统计
////                    MobclickAgent.onProfileSignIn("WX",info.getOpenid());
////                }
////                @Override
////                public void onError(int errorCode, String errorMsg) {
////                    Log.d("onError()--->>", errorMsg);
////                }
////                @Override
////                public void onFailure(IOException e) {
////                    Log.d("onFailure()--->>", e.getMessage());
////                }
////                @Override
////                public void onPayError(String res) {
////
////                }
////            });

            mWxAuthorize();

        }
    });

    /**
     * ShareSdk 三方授权登录
     */
    private void mWxAuthorize() {
        showDialog();
        Log.d(TAG, "ShareSDK authorize -----" + "授权开始");
        final Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        /*final Platform qq = ShareSDK.getPlatform(QQ.NAME);*/
        /*final Platform sinaweibo = ShareSDK.getPlatform(SinaWeibo.NAME);*/
        if (wechat.isClientValid()) {
            //客户端可用
        }
        if (wechat.isAuthValid()) {
            wechat.removeAccount(true);
            Log.d(TAG, "sharSdk authorize ----->>>" + "已授权");
        }
        wechat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, final HashMap<String, Object> hashMap) {
                /*platform.getDb().exportData()获取用户数据*/
                Log.d("ShareSDK", "onComplete ---->  登录成功" + platform.getDb().exportData());
                platform.getDb().getUserId();
                // 这里授权成功跳转到程序主界面了
                String exportData = platform.getDb().exportData();
                KLog.json(TAG, exportData);        //输出所有授权信息
                Gson mGson = new Gson();
                WXUserInfo userInfo = mGson.fromJson(exportData, WXUserInfo.class);
                KLog.e("wx用户信息获取结果：" + exportData);
                //上传自己服务器，登录
//                startActivity(MyPageActivity.class);
                requestLoginNetWork(userInfo);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                dismissDialog();
                Log.d("ShareSDK", "onError ---->  登录失败" + throwable.toString());
                Log.d("ShareSDK", "onError ---->  登录失败" + throwable.getStackTrace().toString());
                Log.d("ShareSDK", "onError ---->  登录失败" + throwable.getMessage());

            }

            @Override
            public void onCancel(Platform platform, int i) {
                dismissDialog();
                Log.d("ShareSDK", "onCancel ---->  登录取消");
            }
        });
        wechat.SSOSetting(false);
        wechat.showUser(null);
    }


    /**
     * 登录
     *
     * @param etPhone
     * @param etVertifyCode
     */
    private void login(final String etPhone, String etVertifyCode) {
        addSubscribe(model.postVertifyLogin(etPhone, /*etVertifyCode*/etVertifyCode), new ParseDisposableObserver<BaseResponse>() {
            @Override
            public void onResult(BaseResponse verfifyLoginEntity) {
                dismissDialog();
                Gson gson = new Gson();
                try {
                    JSONObject js = new JSONObject(gson.toJson(verfifyLoginEntity.getResult()));
                    String user_token = js.optString("user_token");
                    SPUtils.getInstance().clear();
                    model.saveUserToken(user_token);
                    UserInfo userInfo = new UserInfo();
                    userInfo.setToken(user_token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ToastUtils.showLong("登陆成功");
                //当用户使用自有账号登录时，可以这样统计
                MobclickAgent.onProfileSignIn(model.getUserToken());
                startActivity(MyPageActivity.class);
            }

            @Override
            public void onError(String errarMessage) {
                KLog.e("　login　onError: －－－－＞＞＞" + errarMessage);
                dismissDialog();
            }
        });

    }


    /*
     *上传微信请求回来的数据
     * */

    public void requestLoginNetWork(WXUserInfo info) {
        //注册自己的服务器
        if (info == null) {
            return;
        }
        isShowDialog(true);
        addSubscribe(model.postLogin(info.openid,
                info.nickname,
                info.getSex(),
                info.headimgurl,
                info.province,
                info.city,
                info.country
                , info.unionid),
                new ParseDisposableObserver<LoginEntity>() {
                    @Override
                    public void onResult(LoginEntity user_token) {
                        dismissDialog();
                        model.saveUserToken(user_token.getResult().getUser_token());
                        UserInfo userInfo = new UserInfo();
                        userInfo.setToken(user_token.getResult().getUser_token());
                        finish();
                    }

                    @Override
                    public void onError(String errarMessage) {
                        dismissDialog();
                        KLog.d(errarMessage);
                    }
                });
    }


}
