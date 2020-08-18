package com.jzd.jzshop.ui.mine;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonObject;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityMyBinding;
import com.jzd.jzshop.entity.LoginEntity;
import com.jzd.jzshop.entity.MemberEntity;
import com.jzd.jzshop.entity.UserInfo;
import com.jzd.jzshop.ui.base.fragment.BaseWebviewFragment;
import com.jzd.jzshop.ui.mine.address.ReceiptAddressFragment;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.mine.recharge.RechargeFragment;
import com.jzd.jzshop.ui.mine.couponcenter.CouponCenterFragment;
import com.jzd.jzshop.ui.mine.merch.MerchantEntryAty;
import com.jzd.jzshop.ui.mine.collect.MineCollectFragment;
import com.jzd.jzshop.ui.mine.coupon.MineCouponFragment;
import com.jzd.jzshop.ui.mine.withdrawals.BalanceFragment;
import com.jzd.jzshop.ui.mine.withdrawals.BonusFragment;
import com.jzd.jzshop.ui.mine.promotion.PromotionFragment;
import com.jzd.jzshop.ui.order.mineorder.MineOrderFragment;
import com.jzd.jzshop.ui.mine.setting.SetFragment;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.EncryptInterceptor;
import com.jzd.jzshop.utils.MoneyFormatUtils;
import com.jzd.jzshop.utils.NetworkUtils;
import com.jzd.jzshop.utils.ParameterToJsonUtils;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.slodonapp.ywj_release.wxapi.WXUserInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;


public class MyViewModel extends BaseViewModel<Repository> {
    private static final String TAG = MyViewModel.class.getSimpleName();
    public static final String TOKEN_VIEWMODEL_REFRESH = "token_viewmodel_refresh";
    // 拍照相处选图
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    private ActivityMyBinding binding;
    private Context mcontext;
    public SmartRefreshLayout refresh;


    public void setbind(ActivityMyBinding binding, Context myActivity, SmartRefreshLayout refresh) {
        this.binding = binding;
        this.mcontext = myActivity;
        this.refresh = refresh;
    }

    public class UIChangeObservable {
        //更新xbanner数据
        public SingleLiveEvent<Boolean> nickeRefreshing = new SingleLiveEvent<>();
        public SingleLiveEvent<String> loginWX = new SingleLiveEvent<>();
        public SingleLiveEvent icon = new SingleLiveEvent();
    }

    public ObservableField<UserInfo> user = new ObservableField<>(model.getUserInfo());
    //    public ObservableField<WXUserInfo>  userInfo=new ObservableField<>();
    public ObservableField<MemberEntity.ResultBean> memberEntity = new ObservableField<>();

    public MyViewModel(@NonNull Application application, Repository model) {
        super(application, model);
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

    public int goodsnumber() {
        int shopCarnumber = model.getShopCarnumber();
        return shopCarnumber;
    }

    public void requestWork() {
        if (TextUtils.isEmpty(model.getUserToken())) {
            KLog.d("未登录");
//            refresh.finishRefresh();
        } else {
            //有授权信息 去登录尚品服务器 获取会员信息
            requestMemberNetWork(refresh);
        }
    }

    public void requestLoginNetWork(final SmartRefreshLayout refreshLayout, WXUserInfo info) {
        //注册自己的服务器
        if (info == null) {
            return;
        }
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
                        requestMemberNetWork(refreshLayout);
                    }

                    @Override
                    public void onError(String errarMessage) {
                        dismissDialog();
                        KLog.d(errarMessage);
                        requestMemberNetWork(refreshLayout);
                    }
                });
    }

    private int a=-1;
    public void requestMemberNetWork(final SmartRefreshLayout refreshLayout) {
        isShowDialog(false);
        if (TextUtils.isEmpty(model.getUserToken())||a==0) {
            binding.imageView9.setMessageNumber(0);
            binding.imageView10.setMessageNumber(0);
            binding.imageView11.setMessageNumber(0);
            binding.imageView12.setMessageNumber(0);
            return;
        }
        addSubscribe(model.postMember(model.getUserToken()), new ParseDisposableTokenErrorObserver<MemberEntity>() {
            @SuppressLint("CheckResult")
            @Override
            public void onResult(MemberEntity member) {
                dismissDialog();
                memberEntity.set(member.getResult());
                UserInfo userInfo = new UserInfo();
                userInfo.setNickname(member.getResult().getNickname());
                userInfo.setUsericon(member.getResult().getAvatar());
                String s = MoneyFormatUtils.keepTwoDecimal(member.getResult().getPoints());
                Log.d(TAG,"keepTwoDecimal:" +s);
                userInfo.setPoints(member.getResult().getPoints());
                userInfo.setMoney(member.getResult().getMoney());
                userInfo.setIsLogin(1);
                model.saveUserInfo(userInfo);
                user.set(model.getUserInfo());
                binding.imageView9.setMessageNumber(memberEntity.get().getDot0());
                binding.imageView10.setMessageNumber(memberEntity.get().getDot1());
                binding.imageView11.setMessageNumber(memberEntity.get().getDot2());
                binding.imageView12.setMessageNumber(memberEntity.get().getDot4());
               /* RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.mipmap.ic_launcher_round)
                              .skipMemoryCache(false)
                              .dontAnimate()
                               .centerCrop()
                              .error(R.mipmap.ic_launcher_round);

                Glide.with(mcontext)
                        .load(member.getResult().getAvatar())
                        .apply(requestOptions)
                        .into(binding.amAvatarIv);

*/
//                memberEntity.notifyChange();
                refreshLayout.finishRefresh();
            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
                a=0;
                KLog.d(errarMessage);
                if (errarMessage.equals("用户未登录")) {
//                    requestLoginNetWork(refreshLayout, model.getWXUserInfo());
                }
                refreshLayout.finishRefresh();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                a=0;
//                refreshLayout.finishRefresh();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CAR_LOGIN,"LOGIN");
                startActivity(LoginVertifyActivity.class,bundle);
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
                .execute(new MyStringCallback(refresh));


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


    public BindingCommand onClickLoginWeXin = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!TextUtils.isEmpty(model.getUserToken())) {
                KLog.d(TAG, "更换头像----->>>>");
                showChoosePicDialog();
            } else {
                startActivity(LoginVertifyActivity.class);
            }
        }
    });

    //充值
    public BindingCommand onRecharageClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
//            goToBaseWebviewNew(Constants.MY_RECHARGE);
            if (!TextUtils.isEmpty(model.getUserToken())) {
                startContainerActivity(RechargeFragment.class.getCanonicalName());
            } else {
                startActivity(LoginVertifyActivity.class);
            }
        }
    });

    //设置
    public BindingCommand onSetClickListen = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            String userToken = model.getUserToken();
            if (!TextUtils.isEmpty(userToken)) {
                startContainerActivity(SetFragment.class.getCanonicalName());
            } else {
                startActivity(LoginVertifyActivity.class);
            }
        }
    });

    //待付款
    public BindingCommand onCLickWaitPay = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.MyOrder,0);
            startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
//            goToBaseWebviewNew(Constants.MY_ORDER_STATUS_0);
        }
    });
    //待发货
    public BindingCommand onCLickWaitDeliverGoods = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
//            goToBaseWebviewNew(Constants.MY_ORDER_STATUS_1);
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.MyOrder,1);
            startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
        }
    });
    //待收货
    public BindingCommand onCLickWaitReceivingGoods = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
//            goToBaseWebviewNew(Constants.MY_ORDER_STATUS_2);
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.MyOrder,2);
            startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
        }
    });
    //退换货
    public BindingCommand onCLickReturnedGoods = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
//            goToBaseWebviewNew(Constants.MY_ORDER_STATUS_4);
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.MyOrder,4);
            startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
        }
    });

    //已完成
    public BindingCommand onCLickFinished = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
//            goToBaseWebviewNew(Constants.MY_ORDER_STATUS_3);
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.MyOrder,3);
            startContainerActivity(MineOrderFragment.class.getCanonicalName(),bundle);
        }
    });

    /**
     * 统一跳转 我的订单
     */
    private void goToBaseWebviewNew(String orderStatus) {
        int netState = NetworkUtils.getNetWorkState(mcontext);
        Bundle bundle = new Bundle();
        if (netState == -1) {
            ToastUtils.showLong("网络连接异常");
            return;
        }
        String encode = URLEncoder.encode(orderStatus); //url参数转义
        if (memberEntity.get() != null && !TextUtils.isEmpty(model.getUserToken())) {
            String url = RetrofitClient.baseUrl + Constants.MY_ORDER_JUMP_URL + encode + "&user_token=" + model.getUserToken();
            Log.d(TAG, "goToBaseWebviewNew:" + url);
            bundle.putString(Constants.BUNDLE_KEY_URL, url);
            startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
        } else {
            ToastUtils.showLong("请登录！！");
        }
    }


    /**
     * 统一 跳转  奖金&服务
     *
     * @param url
     */
    public void goToBaseWebview(String url) {
        int netState = NetworkUtils.getNetWorkState(mcontext);
        Bundle bundle = new Bundle();
        if (netState == -1) {
            ToastUtils.showLong("网络连接异常");
            return;
        }
        if (memberEntity.get() != null && !TextUtils.isEmpty(model.getUserToken())) {
            bundle.putString(Constants.BUNDLE_KEY_URL, url + model.getUserToken());
            startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
        } else {
            ToastUtils.showLong("请登录！！");
        }
    }

    //统一跳转
    public BindingCommand onClickGoToBaseWebview = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            if (memberEntity.get() != null && !TextUtils.isEmpty(model.getUserToken())) {
                bundle.putString(Constants.BUNDLE_KEY_URL, memberEntity.get().getCommission_url() + model.getUserToken());
                startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
            } else {
                ToastUtils.showLong("请登录！！");
            }
        }
    });
    //推广中心
    public BindingCommand onTuiguangClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            KLog.a("保存的token", model.getUserToken());
            if (memberEntity.get() != null && !TextUtils.isEmpty(model.getUserToken())) {
                bundle.putString(Constants.MINE_TUI_GUANG, memberEntity.get().getCommission_url() + model.getUserToken());
                startContainerActivity(PromotionFragment.class.getCanonicalName(), bundle);
            } else {
                ToastUtils.showLong("请登录！！");
            }
        }
    });

    //奖金提现
    public BindingCommand onBonusClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            if (memberEntity.get() != null && !TextUtils.isEmpty(model.getUserToken())) {
                bundle.putString(Constants.MINE_BONUS, memberEntity.get().getCommissionwith_url() + model.getUserToken());
                startContainerActivity(BonusFragment.class.getCanonicalName(), bundle);
            } else {
                ToastUtils.showLong("请登录！！");
            }
        }
    });


    //余额提现
    public BindingCommand onBalanceClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Bundle bundle = new Bundle();
            if (memberEntity.get() != null && !TextUtils.isEmpty(model.getUserToken())) {
                bundle.putString(Constants.MINE_YUE, memberEntity.get().getMemberwith_url() + model.getUserToken());
                startContainerActivity(BalanceFragment.class.getCanonicalName(), bundle);
            } else {
                ToastUtils.showLong("请登录");
            }
        }
    });

    //积分签到
    public BindingCommand onSignClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (memberEntity.get() != null && !TextUtils.isEmpty(model.getUserToken())) {
                Bundle bundle = new Bundle();
//                bundle.putString(Constants.MINE_SIGN, memberEntity.get().getSign_url() + model.getUserToken());
//                startContainerActivity(SignFragment.class.getCanonicalName(), bundle);
                bundle.putString(Constants.BUNDLE_KEY_URL, memberEntity.get().getSign_url() + model.getUserToken());
                startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
            } else {
                ToastUtils.showLong("请登录!!");
            }
        }
    });

    //我的优惠券
    public BindingCommand myonCouponClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (memberEntity.get() != null && !TextUtils.isEmpty(model.getUserToken())) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.MINE_COUPON, memberEntity.get().getCouponmy_url() + model.getUserToken());
                startContainerActivity(MineCouponFragment.class.getCanonicalName(), bundle);
            } else {
                ToastUtils.showLong("请登录!!");
            }
        }
    });

    //收藏页面
    public BindingCommand onCollectClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (memberEntity.get() != null && !TextUtils.isEmpty(model.getUserToken())) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.MINE_COLLECT, memberEntity.get().getFavorite_url() + model.getUserToken());
                startContainerActivity(MineCollectFragment.class.getCanonicalName(), bundle);
            } else {
                ToastUtils.showLong("请登录!!!");
            }
        }
    });


    //我的抽奖
    public BindingCommand myonChoujiang = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
        }
    });

    //收货地址
    public BindingCommand onAddressClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            String userToken = model.getUserToken();
            if (!TextUtils.isEmpty(userToken)) {
                startContainerActivity(ReceiptAddressFragment.class.getCanonicalName());
//                startContainerActivity(OrederDetailFragment.class.getCanonicalName());
            } else {
                startActivity(LoginVertifyActivity.class);
            }
        }
    });

    //领券中心
    public BindingCommand getCouponCenter = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (memberEntity.get() != null && !TextUtils.isEmpty(model.getUserToken())) {
                Bundle bundle = new Bundle();
//                startContainerActivity(MerchNextFragment.class.getCanonicalName());
                startContainerActivity(CouponCenterFragment.class.getCanonicalName(), bundle);
            } else {
                startActivity(LoginVertifyActivity.class);
            }
        }
    });
    //商家入驻

    public BindingCommand onMerchClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO: 2020/1/20  一期 商家入驻 h5
//            if (memberEntity.get() != null && !TextUtils.isEmpty(model.getUserToken())) {
//                Bundle bundle = new Bundle();
//                bundle.putString(Constants.MINE_STORE, memberEntity.get().getMerch_url() + model.getUserToken());
////                startContainerActivity(MerchFragment.class.getCanonicalName(), bundle);
//                startActivity(MerchantEntryAty.class, bundle);
////                startContainerActivity(MerchNextFragment.class.getCanonicalName());
//            } else {
//                ToastUtils.showLong("请登录！");
//            }
            // 二期 商家入驻 原生页
            startActivity(MerchantEntryAty.class);
        }
    });


    //         =============================更换头像=========================================

    /**
     * 显示修改头像的对话框
     */
    public void showChoosePicDialog() {
        try {
            NiceDialog.init()
                    .setLayoutId(R.layout.pop_choose_pic)     //设置dialog布局文件
                    .setConvertListener(new ViewConvertListener() {     //进行相关View操作的回调
                        @Override
                        public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                            View tv_take_photo = holder.getView(R.id.tv_take_photo);//照相
                            View tv_album = holder.getView(R.id.tv_album);//相册
                            View tv_cancel = holder.getView(R.id.tv_cancel);//取消
                            setPopListener(dialog, tv_take_photo, tv_album, tv_cancel);
                        }
                    })
                    .setDimAmount(0.5f)//调节灰色背景透明度[0-1]，默认0.5f
                    .setShowBottom(true)//是否在底部显示dialog，默认flase
                    .setOutCancel(true)     //点击dialog外是否可取消，默认true
                    .setAnimStyle(R.style.PopupAnimation_Up_Down)     //设置dialog进入、退出的动画style(底部显示的dialog有默认动画)
                    .show(((MyActivity) mcontext).getSupportFragmentManager());     //显示dialog
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    private void setPopListener(final BaseNiceDialog dialog, View tv_take_photo, View tv_album, View tv_cancel) {
        tv_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
                dialog.dismiss();
            }
        });
        tv_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceImageFromGallery();
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void choiceImageFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 10003)) {
                choiceImage();
            }
        } else {
            choiceImage();
        }
    }

    /**
     * 相册选图
     */
    public void choiceImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        ((MyActivity) mcontext).startActivityForResult(intent, CHOOSE_PICTURE);
    }

    private boolean checkPermission(String permission, int requestCode) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(mcontext, permission) == PackageManager.PERMISSION_GRANTED)
            flag = true;
        else
            ActivityCompat.requestPermissions((MyActivity) mcontext, new String[]{permission}, requestCode);
        return flag;
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int check1 = ContextCompat.checkSelfPermission(mcontext, permissions[0]);
            int check2 = ContextCompat.checkSelfPermission(mcontext, permissions[1]);
            if (check1 + check2 != PackageManager.PERMISSION_GRANTED) {
                ((MyActivity) mcontext).requestPermissions(permissions, 1);
            } else {
                takePicture();
            }
        } else {
            takePicture();
        }
    }

    /**
     * 拍照
     */
    public void takePicture() {
        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment
                .getExternalStorageDirectory(), "image.jpg");
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= 24) {
            openCameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            tempUri = FileProvider.getUriForFile(mcontext, "com.jzd.jzshop.img.fileProvider", file);
        } else {
            tempUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "image.jpg"));
        }
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        ((MyActivity) mcontext).startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    private void mCommonMethod() {
        Snackbar.make(binding.textView7, R.string.in_development, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 把参数封装成json
     *
     * @param postBodyString
     * @return
     */
    private static String createJsonString(String postBodyString) {
        JsonObject jsonObject = new JsonObject();
        try {
//            postBodyString = (URLDecoder.decode(postBodyString, "UTF-8"));
            postBodyString = (URLDecoder.decode(postBodyString.replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        KLog.d(postBodyString);
        String[] params = postBodyString.split("&");
        for (String param : params) {
            String[] temp = param.split("=");
            if (temp == null || temp.length < 2)
                continue;
            StringBuffer block = new StringBuffer();
            for (int i = 1; i < temp.length; i++) {
                block.append(temp[i]);
            }
            jsonObject.addProperty(temp[0], block.toString());
//            jsonObject.addProperty(temp[0],temp[1]);
        }
        return jsonObject.toString();
    }
    /**
     * @param request
     * @return
     */
    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }


}
