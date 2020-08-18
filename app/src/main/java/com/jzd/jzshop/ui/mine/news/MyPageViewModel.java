package com.jzd.jzshop.ui.mine.news;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityMyPageBinding;
import com.jzd.jzshop.entity.MemberEntity;
import com.jzd.jzshop.entity.MessageCenterEntity;
import com.jzd.jzshop.entity.MyPagerEntity;
import com.jzd.jzshop.entity.UserInfo;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.base.fragment.BaseWebviewFragment;
import com.jzd.jzshop.ui.mine.balance.MyBalanceFragment;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.EncryptInterceptor;
import com.jzd.jzshop.utils.MoneyFormatUtils;
import com.jzd.jzshop.utils.NetworkUtils;
import com.jzd.jzshop.utils.ParameterToJsonUtils;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.slodonapp.ywj_release.wxapi.WechatInfoSpHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;

/**
 * @author LXB
 * @description:
 * @date :2020/1/20 16:35
 */
public class MyPageViewModel extends BaseViewModel<Repository> {
    private static final String TAG = MyPageViewModel.class.getSimpleName();
    public static final String TOKEN_VIEWMODEL_REFRESH = "token_viewmodel_refresh";
    // 拍照相处选图-------------start
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    // 拍照相处选图-------------end
    private ActivityMyPageBinding binding;
    private Context mcontext;
    public SmartRefreshLayout refresh;
    private List<MyPagerEntity> dataList;
    public int pagesize = 10; //默认每页页数

    public UIChangeObservable uc = new UIChangeObservable();
    public class UIChangeObservable {
        public SingleLiveEvent<MemberEntity> myData = new SingleLiveEvent<>();  // TODO: 2020/2/9  改版添加 是否需要添加此 观察者
        public SingleLiveEvent<String> loginWX = new SingleLiveEvent<>();
        public SingleLiveEvent icon = new SingleLiveEvent();
        public SingleLiveEvent<MessageCenterEntity.ResultBean> mMessageLiveData = new SingleLiveEvent<>();
    }

    public ObservableField<UserInfo> user = new ObservableField<>(model.getUserInfo());
    public ObservableField<MemberEntity.ResultBean> memberEntity = new ObservableField<>();

    public MyPageViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public String getUserToken() {
        return model.getUserToken();
    }
    public UserInfo getUserInfo() {
        return model.getUserInfo();
    }

    public void setBinding(ActivityMyPageBinding binding, Context myActivity, SmartRefreshLayout refresh) {
        this.binding = binding;
        this.mcontext = myActivity;
        this.refresh = refresh;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(model.getUserToken())) {
            if (memberEntity.get() != null && user.get() != null) {
                user.get().setIsLogin(0);
                memberEntity.set(null);
                user.set(null);
                uc.icon.call();
            }
        }
        user.notifyChange();
        memberEntity.notifyChange();
        requestMemberNetWork(refresh);
    }


    public void requestWork(List<MyPagerEntity> dataList) {
        this.dataList = dataList;
        if (TextUtils.isEmpty(model.getUserToken())) {
            KLog.d("未登录");
            ((MyPageActivity) mcontext).notLoginDefaultData(dataList);
            refresh.finishRefresh();
        } else {
            //有授权信息 去登录尚品服务器 获取会员信息
            requestMemberNetWork(refresh);
        }
    }


    private int a = 2;

    public void requestMemberNetWork(final SmartRefreshLayout refreshLayout) {
        isShowDialog(false);
        if (TextUtils.isEmpty(model.getUserToken()) || a == 0) {
//            binding.imageView9.setMessageNumber(0);
//            binding.imageView10.setMessageNumber(0);
//            binding.imageView11.setMessageNumber(0);
//            binding.imageView12.setMessageNumber(0);
            ((MyPageActivity) mcontext).notLoginDefaultData(dataList);

            return;
        }
        addSubscribe(model.postMember(model.getUserToken()), new ParseDisposableTokenErrorObserver<MemberEntity>() {
            @SuppressLint("CheckResult")
            @Override
            public void onResult(MemberEntity member) {
                dismissDialog();
                a=2;
                memberEntity.set(member.getResult());
                uc.myData.setValue(member);
                UserInfo userInfo = new UserInfo();
                userInfo.setToken(model.getUserToken());
                userInfo.setNickname(member.getResult().getNickname());
                userInfo.setRecommand_name(member.getResult().getRecommand_name()); //推荐人
                userInfo.setInvit_id(member.getResult().getInvit_id()); //邀请码
                userInfo.setMoney2(member.getResult().getMoney2()); //君子资产
                userInfo.setLevel(member.getResult().getLevel()); //等级
                userInfo.setUsericon(member.getResult().getAvatar());
                String s = MoneyFormatUtils.keepTwoDecimal(member.getResult().getPoints());
                Log.d(TAG, "keepTwoDecimal:" + s);
                userInfo.setPoints(member.getResult().getPoints());
                userInfo.setMoney(member.getResult().getMoney());

                userInfo.setDfk_num(member.getResult().getDot0());
                userInfo.setDfh_num(member.getResult().getDot1());
                userInfo.setDshh_num(member.getResult().getDot2());
                userInfo.setThh_num(member.getResult().getDot4());

                userInfo.setIsLogin(1);
                model.saveUserInfo(userInfo);
                user.set(model.getUserInfo());
                refreshLayout.finishRefresh();
            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
                KLog.d(errarMessage);
                if (errarMessage.equals("登录信息已过期")){
                    //先清空微信授权保存的信息
                    a = 0;
                    WechatInfoSpHelper.clearWxSp();
                    SPUtils.getInstance().clear();
                    model.saveUserToken("");
                    ToastUtils.showShort(errarMessage);
                    return;
                }else {
                    KLog.d(errarMessage);
                }
                refreshLayout.finishRefresh();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                a = 0;
//                refreshLayout.finishRefresh();
                //先清空微信授权保存的信息
                WechatInfoSpHelper.clearWxSp();
                SPUtils.getInstance().clear();
                model.saveUserToken("");

                Bundle bundle = new Bundle();
                bundle.putString(Constants.CAR_LOGIN, "LOGIN");
                startActivity(LoginVertifyActivity.class, bundle);
            }
        });
    }

    public void updateAvatar(File file, String imagePath) {
        if (!file.exists()) {
            ToastUtils.showShort("文件不存在，请重新选择图片");
            return;
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("log-header", "I am the log request header.");
        Map<String, String> params = new HashMap<>();
        String reqData = ParameterToJsonUtils.encrypReqData(model.getUserToken(), AppApplication.myDeviceID);
        String sign = ParameterToJsonUtils.encrypSign(model.getUserToken(), AppApplication.myDeviceID);
        params.put(EncryptInterceptor.KEY_REQ_DATA, reqData);
        params.put(EncryptInterceptor.KEY_SIGN, sign);
        OkHttpUtils.post()
                .addFile("avatar", file.getName(), file)
                .url(RetrofitClient.baseUrl + Constants.URL.updateAvatar)
                .params(params)
                .headers(headers)
                .build()
                .execute(new MyPageViewModel.MyStringCallback(refresh));
    }

    public class MyStringCallback extends StringCallback {
        private SmartRefreshLayout smartRefreshLayout;

        public MyStringCallback(SmartRefreshLayout refresh) {
            this.smartRefreshLayout = refresh;
        }

        @Override
        public void onBefore(Request request, int id) {
            Log.d(TAG, "loading...:");
        }

        @Override
        public void onAfter(int id) {
            Log.d(TAG, "Sample-okHttp:");
            smartRefreshLayout.autoRefresh();
        }

        @Override
        public void onResponse(String response, int id) {
            Log.d(TAG, "onResponse：complete" + response);
            ToastUtils.showShort("上传成功！");
            smartRefreshLayout.autoRefresh();

        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            Log.d(TAG, "onError():" + e.getMessage());
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
        }
    }


    public void seeBalance() {
        if (!TextUtils.isEmpty(model.getUserToken())) {
            UserInfo userInfo = model.getUserInfo();
            if (userInfo != null) {
                Bundle bundle = new Bundle();
                bundle.putDouble(Constants.MINE_BALANCE, userInfo.getMoney());
                startContainerActivity(MyBalanceFragment.class.getCanonicalName(), bundle);
            }
        } else {
            startActivity(LoginVertifyActivity.class);
        }

    }


    /**
     * 保存用户查看金额的状态
     * @param status
     */
    public void saveMemberMoneyStatus(boolean status){
        if(status){
            model.saveMemberlook(1);//如果为1时不可见 如果为2时不可见
        }else {
            model.saveMemberlook(2);
        }
    }

    /**
     * 获取会员金额状态
     * */
    public boolean getMemberMoneyStatus(){
        int memberLook = model.getMemberLook();
        if(memberLook==1){
            return true;
        }else if(memberLook==2){
            return false;
        }else {
            return true;
        }
    }




    /**
     * 统一 跳转  我的功能   对应一期 （奖金&服务）
     *
     * @param url
     * @param userToken
     */
    public void goToBaseWebview(String url, String userToken) {
        int netState = NetworkUtils.getNetWorkState(mcontext);
        Bundle bundle = new Bundle();
        if (netState == -1) {
            ToastUtils.showLong("网络连接异常");
            return;
        }
        if (memberEntity.get() != null && !TextUtils.isEmpty(userToken)) {
            bundle.putString(Constants.BUNDLE_KEY_URL, url + userToken);
            startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
        } else {
            ToastUtils.showLong("请登录！！");
        }
    }

    /**
     * H5统一url链接
     *
     * @param returnUrl
     */
    public void goToBaseWebviewNew(String returnUrl) {
        int netState = NetworkUtils.getNetWorkState(mcontext);
        Bundle bundle = new Bundle();
        if (netState == -1) {
            ToastUtils.showLong("网络连接异常");
            return;
        }
        if (!TextUtils.isEmpty(model.getUserToken())) {
            String url = RetrofitClient.baseUrl + Constants.MY_ORDER_JUMP_URL + returnUrl + "&user_token=" + model.getUserToken();
            Log.d(TAG, "goToBaseWebviewNew:" + url);
            bundle.putString(Constants.BUNDLE_KEY_URL, url);
            startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
        } else {
            ToastUtils.showLong("请登录！！");
        }
    }

    /**
     *  获取 消息总数
     * @param refresh
     * @param page
     */
    public void requestMessageData(final SmartRefreshLayout refresh, final int page) {
        isShowDialog(false);
        addSubscribe(model.postMessageData(model.getUserToken(), page, pagesize), new ParseDisposableTokenErrorObserver<MessageCenterEntity>() {
            @Override
            public void onResult(MessageCenterEntity messageCenterEntity) {
                super.onResult(messageCenterEntity);
                Log.d(TAG, "requestMessageData onSuccess:---->>>");
                if (messageCenterEntity != null) {
                    if (messageCenterEntity.getResult() != null) {
                        model.saveUserMessage(messageCenterEntity.getResult().getTotal_noread());
                        uc.mMessageLiveData.setValue(messageCenterEntity.getResult());
                    }
                }
                dismissDialog();
                refresh.finishRefresh();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                Log.d(TAG, "onError:---->>>" + throwable.getMessage());
                dismissDialog();
                refresh.finishRefresh();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                refresh.finishRefresh();
            }
        });
    }


}
