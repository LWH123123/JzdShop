package com.jzd.jzshop.app;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.bumptech.glide.request.target.ViewTarget;

import com.jzd.jzshop.BuildConfig;
import com.jzd.jzshop.R;
import com.jzd.jzshop.ui.SplashActivity;
import com.jzd.jzshop.ui.SplashViewModel;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.CustomImageLoader;
import com.jzd.jzshop.utils.GetDeviceId;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;
import com.jzd.jzshop.utils.third_party.AMapUtils;
import com.previewlibrary.ZoomMediaLoader;
import com.slodonapp.ywj_release.wxapi.AppConst;
import com.slodonapp.ywj_release.wxapi.WXEntryActivity;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.crash.CaocConfig;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.StringUtils;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by jzd on 2017/7/16.
 */

public class AppApplication extends BaseApplication {
    private static final String TAG = "AppApplication";
    public static String myDeviceID;
    public static IWXAPI sApi;
    public static String pusid; //pusid

    @Override
    public void onCreate() {
        super.onCreate();
//        AMapUtils.init(sInstance);        //初始化工具类
        initCrash();     //初始化全局异常崩溃
//        if (!LeakCanary.isInAnalyzerProcess(this)) {         //内存泄漏检测
//            LeakCanary.install(this);
//        }
        initWeiXin();
        MultiDex.install(this);     //65535
        getDeviceUniqueId();
        initThridService();
//       postDomainChange();        //动态获取域名
        //Android P 谷歌限制了开发者调用非官方公开API 方法或接口，也就是说，一旦用反射直接调用源码就会有这样的提示弹窗出现
        //一旦用反射调用就会出现<Detected problems with API compatibility(visit g.co/dev/appcompat for more info>  弹窗
        closeAndroidPDialog();
    }

    private void initThridService() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                //设置线程的优先级,不与主线程抢占资源
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                KLog.init(BuildConfig.DEBUG);        //是否开启打印日志
                initOkHttpUtils();
                ZoomMediaLoader.getInstance().init(new CustomImageLoader());
                ViewTarget.setTagId(R.id.glide_tag);
                initUmeng();
                CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_APP_ID, false); //debug建议设置成true，rease 设置为false。
                initJpush();
            }
        }.start();

    }



    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initJpush() {
        JPushInterface.setDebugMode(false); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        pusid = JPushInterface.getRegistrationID(getApplicationContext());  //极光ID，用于自己后台 业务消息类推送
        if (!pusid.isEmpty()) {
            Log.d(TAG, "RegId:" + pusid);
        } else {
            KLog.a("");
            Log.d(TAG, "Get registration fail, JPush init failed!");
        }
    }

    private void initUmeng() {
        UMConfigure.setLogEnabled(false); //上线关闭
        UMConfigure.init(this, Constants.UMENG_APPKEY, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_MANUAL);  // 选用AUTO页面采集模式
        UMConfigure.setProcessEvent(true);   // 支持在子线程中统计自定义事件
    }


    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(1000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.mipmap.ic_launcher) //错误图标
                .restartActivity(SplashActivity.class) //重新启动后的activity
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
    }

    private void initWeiXin() {
        sApi = WXEntryActivity.initWeiXin(this, AppConst.WEIXIN_APP_ID);
//        Auth.init().setQQAppID("")
//                .setWXAppID(AppConst.WEIXIN_APP_ID)
//                .setWXSecret(AppConst.WEIXIN_APP_SECRET)
//                .setWBAppKey("")
//                .setWBRedirectUrl("")
//                .setWBScope("")
//                .setHWAppID("")
//                .setHWKey("")
//                .setHWMerchantID("")
//                .addFactoryForWX(AuthBuildForWX.getFactory())
//                .build();
    }

    private void getDeviceUniqueId() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取保存在sd中的 设备唯一标识符
                    String readDeviceID = GetDeviceId.readDeviceID(getApplicationContext());
                    //获取缓存在  sharepreference 里面的 设备唯一标识
                    String string = SPUtils.getInstance().getString(Constants.SP_DEVICES_ID, readDeviceID);
                    //判断 app 内部是否已经缓存,  若已经缓存则使用app 缓存的 设备id
                    if (string != null) {
                        //app 缓存的和SD卡中保存的不相同 以app 保存的为准, 同时更新SD卡中保存的 唯一标识符
                        if (StringUtils.isEmpty(readDeviceID) && !string.equals(readDeviceID)) {
                            // 取有效地 app缓存 进行更新操作
                            if (StringUtils.isEmpty(readDeviceID) && !StringUtils.isEmpty(string)) {
                                readDeviceID = string;
                                GetDeviceId.saveDeviceID(readDeviceID, getApplicationContext());
                            }
                        }
                    }
                    // app 没有缓存 (这种情况只会发生在第一次启动的时候)
                    if (StringUtils.isEmpty(readDeviceID)) {
                        //保存设备id
                        readDeviceID = GetDeviceId.getDeviceId(getApplicationContext());
                    }
                    //最后再次更新app 的缓存
                    SPUtils.getInstance().put(Constants.SP_DEVICES_ID, readDeviceID);
                    myDeviceID = readDeviceID;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     *  此网络框架主要兼容 原封装框架无法提交图片，后期原框架处理后可去掉
     */
    private void initOkHttpUtils() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new EncryptInterceptorNew())
                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }



    //============================================动态获取域名===========================================

    /**
     *  域名变更 接口
     * @param
     */
    public void postDomainChange() {
        Map<String, String> headers = new HashMap<>();
        headers.put("log-header", "I am the log request header.");
        Map<String, String> params = new HashMap<>();
        OkHttpUtils.post()
                .url(RetrofitClient.DOMAIN_URL)
                .params(params)
                .headers(headers)
                .build()
                .execute(new MyStringCallback());
    }



    public class MyStringCallback extends StringCallback {

        @Override
        public void onBefore(Request request, int id) {
            Log.d(TAG, "loading...:");
        }

        @Override
        public void onAfter(int id) {
            Log.d(TAG, "Sample-okHttp:");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.d(TAG, "onResponse：complete" + response);
            //============================域名更换================start=================================
            Log.e(TAG,"DOMAIN_URL---->>> " +response);
            String domainUrl = null;
            domainUrl = response;
            RetrofitClient.setBaseUrl(domainUrl);
            Log.e(TAG,"域名更换后  baseUrl---->>> " + RetrofitClient.getBaseUrl());
            //将动态域名 存放到SharePreference中
            SPUtils.getInstance().put("environment",domainUrl);
            //===========================域名更换==============end=================================
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            Log.d(TAG, "onError():" + e.getMessage());
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inprogress:" + progress);
        }
    }

}
