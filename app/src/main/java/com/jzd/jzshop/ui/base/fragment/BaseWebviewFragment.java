package com.jzd.jzshop.ui.base.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentBaseWebviewBinding;
import com.jzd.jzshop.entity.EntityH5ToConfirmOrder;
import com.jzd.jzshop.entity.EntityH5ToShopDetails;
import com.jzd.jzshop.entity.EntityH5ToShopPay;
import com.jzd.jzshop.entity.OrderToPayEntity;
import com.jzd.jzshop.entity.OrderToPayEntityH5;
import com.jzd.jzshop.ui.base.viewmodel.BaseWebviewViewModel;
import com.jzd.jzshop.ui.MoreProductJumpLinkAty;
import com.jzd.jzshop.ui.category.CategoryActivity;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.ui.mine.MyViewModel;
import com.jzd.jzshop.ui.mine.creditsign.CreditSignNewActivity;
import com.jzd.jzshop.ui.mine.mineshop.shophome.MineShopHomeAty;
import com.jzd.jzshop.ui.mine.news.MyPageActivity;
import com.jzd.jzshop.ui.order.firmorder.FirmOrderActivity;
import com.jzd.jzshop.ui.pay.PayFragment;
import com.jzd.jzshop.ui.pay.PayResult;
import com.jzd.jzshop.ui.shoppingcar.ShoppingCarActivity;
import com.jzd.jzshop.utils.BaseWebviewUtils;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.NetworkUtils;
import com.jzd.jzshop.utils.SystemUtils;
import com.slodonapp.ywj_release.wxapi.WXEntryActivity;
import com.slodonapp.ywj_release.wxapi.WXPayEntryActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.friends.Wechat;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.http.cookie.store.PersistentCookieStore;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Cookie;
import pub.devrel.easypermissions.EasyPermissions;

public class BaseWebviewFragment extends BaseFragment<FragmentBaseWebviewBinding, BaseWebviewViewModel> implements View.OnLongClickListener, EasyPermissions.PermissionCallbacks {
    private static final String TAG = BaseWebviewFragment.class.getSimpleName();
    private ProgressBar mPageLoadingProgressBar = null;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private String payType;
    //是否下载海报
    private boolean isDownloadPoster = false;
    private final static int REQUEST_CODE_FILE_CHOOSER = 101;
    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private File file;
    private Bitmap bitmapPic;
    private String imagePath;
    private boolean loadError = false;//是否加载失败
    private LinearLayout webParentView;//父类
    private View mErrorView;
    private String mFailingUrl = null;    // 用于记录出错页面的url 方便重新加载

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_base_webview;
    }

    @Override
    public int initVariableId() {
        return BR.baseWebVM;
    }

    @Override
    public BaseWebviewViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(BaseWebviewViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.goBack.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (binding.afWebview.canGoBack()) {
                    if (loadError) {
                        binding.afWebview.removeAllViews();
                        getActivity().finish();
                    } else {
                        binding.afWebview.goBack();
                    }
                } else {
                    getActivity().finish();
                }
            }
        });
    }

    String url;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initData() {
        super.initData();
        viewModel.initToolbar();
        if (!TextUtils.isEmpty(type)) {
            binding.ascIncludeToolbar.toolbar.setVisibility(View.GONE);
            KLog.a("这儿没有TOOLBAR");
        }
//      mTestHandler.sendEmptyMessageDelayed(MSG_INIT_UI, 10);
        init();
//      setWebViewCookie();
        getIntentData();
        setOnClickEvent();


        if (NetworkUtils.isNetworkConnected(mActivity)) {
            KLog.i("将要加载的WebView为==>>" + url);
            binding.afWebview.loadUrl(url);
            // 开启DOM缓存，开启LocalStorage存储（html5的本地存储方式）  
            binding.afWebview.getSettings().setDomStorageEnabled(true);
            binding.afWebview.getSettings().setDatabaseEnabled(true);

        } else {
            mFailingUrl = url;
            showErrorPage();
        }
    }


    private String type;

    public void setTYPE(String type) {
        this.type = type;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    private void getIntentData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            url = arguments.getString(Constants.BUNDLE_KEY_URL);
            String title = arguments.getString(Constants.BUNDLE_KEY_TITLE);
            viewModel.setTitleText(title);
            binding.afWebview.loadUrl(url);
        }
    }

    private void initProgressBar() {
        // ProgressBar(getApplicationContext(),
        // null,
        // android.R.attr.progressBarStyleHorizontal);
        mPageLoadingProgressBar = binding.progressBar1;
        mPageLoadingProgressBar.setMax(100);
        mPageLoadingProgressBar.setProgressDrawable(this.getResources().getDrawable(R.drawable.color_progressbar));
    }

    private void setOnClickEvent() {
        binding.afWebview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && binding.afWebview.canGoBack()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.afWebview.goBack();
                        }
                    });
                    return true;
                }
                return false;
            }
        });

        binding.afWebview.setOnLongClickListener(this); //webview 长按时间
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        initProgressBar();
        binding.afWebview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //return true；//本地打开，自己处理，推荐，尤其现在android新版推行速度愈发快了
                BaseWebviewFragment.this.url = url;
//                view.loadUrl(url);
//                return true;
                KLog.d(TAG, "shouldOverrideUrlLoading url===" + url);
                //这里进行url拦截
                return interceptH5UrlJump(view, url);
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                mTestHandler.sendEmptyMessage(MSG_START_UI);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                KLog.d(TAG, "onPageFinished url===" + url);
                view.getSettings().setJavaScriptEnabled(true);
                // mTestHandler.sendEmptyMessage(MSG_OPEN_TEST_URL);
//                mTestHandler.sendEmptyMessageDelayed(MSG_OPEN_TEST_URL, 5000);// 5s?
                /* mWebView.showLog("test Log"); */
                viewModel.setTitleText(view.getTitle());
                mTestHandler.sendEmptyMessage(MSG_FINISH_UI);
                if (loadError == false) {
                    if (webParentView != null && mErrorView != null) {
                        mErrorView.setVisibility(View.GONE);
                        // webParentView.removeView(mErrorView);
                        binding.afWebview.setVisibility(View.VISIBLE);
                    }
                }
            }

            /**
             * 拦截到的, 是所有的URL(除了 正常的Url 还包括图片,js, css)
             * @param view
             * @param url
             * @return
             */
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//                return super.shouldInterceptRequest(view, url);
                KLog.d("shouldInterceptRequest", "触发的url是====" + url);
                if (url.contains("=goods.detail")) {
                    KLog.d("shouldInterceptRequest", "return --->>>  =goods.detail ");
                    return new WebResourceResponse(null, null, null);
                } else if (url.contains("order.pay")) {
                    KLog.d("shouldInterceptRequest", "return --->>>  =order.pay");
                    return new WebResourceResponse(null, null, null);
                }
                return super.shouldInterceptRequest(view, url);
            }

            //WebView加载网页失败异常处理
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Uri url = request.getUrl();
                    Log.d("onReceivedError", "url --->>>" + url);
                    Log.d("onReceivedError", "error --->>>" + error.getDescription());
                }
                //6.0以下执行
                view.loadUrl("about:blank");// 避免出现默认的错误界面
                setErrorPage(view, request);// 加载自定义错误页面
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                // 这个方法在 android 6.0才出现
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    int statusCode = errorResponse.getStatusCode();
//                    Log.d(TAG,"statusCode:" + statusCode);
//                    if (404 == statusCode || 500 == statusCode) {
//                        view.loadUrl("about:blank");// 避免出现默认的错误界面
//                        view.removeAllViews();
//                        setErrorPage(view,request);// 加载自定义错误页面
//                    }
//                }
                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binding.afWebview.getSettings()
                            .setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                }
                handler.proceed();
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.d(TAG, "onReceivedError:=====" + description);
            }
        });

        /**
         * android要获取javascript的弹窗功能（或console）功能
         * WebChromeClient 辅助WebView处理图片上传操作【<input type=file> 文件上传标签】
         */
        binding.afWebview.setWebChromeClient(new WebChromeClient() {
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                viewModel.setTitleText(title);
                // android 6.0 以下通过title获取判断
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    if (title.contains("404") || title.contains("500") || title.contains("Error") || title.contains("找不到网页") || title.contains("网页无法打开")) {
                        loadError = true;
                    }
                }
            }

            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                super.onProgressChanged(webView, newProgress);
                Message msg = Message.obtain();
                msg.arg1 = newProgress;
                msg.what = MSG_PRO_CHANGED_UI;
                mTestHandler.sendMessage(msg);
                if (mPageLoadingProgressBar.getProgress() == 100) {
                    mPageLoadingProgressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2, JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                /**
                 * 这里写入你自定义的window alert
                 */
                Log.d("alert", message);
                result.confirm();     //劫持alert以后释放，不然会卡死
                return true;
//                return super.onJsAlert(null, url, message, result);
            }

        });
        BaseWebviewUtils.initWebView(getActivity(), binding.afWebview);
        WebSettings webSetting = binding.afWebview.getSettings();
        String userAgentString = webSetting.getUserAgentString();
        Log.d(TAG, "userAgentString:==" + userAgentString);
        //设置js调用函数方法
        binding.afWebview.addJavascriptInterface(new JsCallback(getActivity()), "jzsp");


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setErrorPage(WebView view, WebResourceRequest request) {
        loadError = true;
        mFailingUrl = request.getUrl().toString();
        showErrorPage();//显示错误页面
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        binding.afWebview.stopLoading();
        binding.afWebview.reload();
    }


    /**
     * H5链接拦截 跳转
     *
     * @param view
     * @param url
     * @return
     */
    private boolean interceptH5UrlJump(WebView view, String url) {
        KLog.a("我当前跳转的链接为===>>>" + url);
        if (url != null && (url.contains("http://") || url.contains("https://"))) { //是协议跳webview 打开（不包含拦截跳转）
//           nterceptH5UrlJump(url);
            if (url.contains("=goods.detail")) {
                String param = SystemUtils.URLRequest(url).get("id");   //android 获取url中的参数
                KLog.d(TAG, "id===" + param);
                //跳转需要跳转的页面
                Bundle bundle = new Bundle();
                bundle.putString(Constants.GOODS_KEY_GID, param);
                viewModel.startActivity(GoodsActivity.class, bundle);
                return true;
            } else if (url.contains("order.pay")) {
                String orderid = SystemUtils.URLRequest(url).get("id");
                KLog.d(TAG, "orderid===" + orderid);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BUNDLE_KEY_ORDERID, orderid);
                bundle.putString(Constants.BUNDLE_KEY_MONEY, "money");
                startContainerActivity(PayFragment.class.getCanonicalName(), bundle);
                return true;
            } else if (url.contains("app:myshop")) {//小店首页
                startActivity(MineShopHomeAty.class);
                return true;
            } else if (url.contains("app:sign")) {//积分签到
                startActivity(CreditSignNewActivity.class);
                return true;
            } else {
                return false;
            }
        } else if (url.contains("app:first")) {  //不是协议，按app 指定标识跳
            startActivity(HomePageActivity.class);
            return true;
        } else if (url.contains("app:shop.category")) {
            startActivity(CategoryActivity.class);
            return true;
        } else if (url.contains("app:member.cart")) {
            startActivity(ShoppingCarActivity.class);
            return true;
        } else if (url.contains("app:member")) {//会员中心
            startActivity(MyPageActivity.class);
            return true;
        } else if (url.contains("app:myshop")) {//小店首页
            startActivity(MineShopHomeAty.class);
            return true;
        } else if (url.contains("app:sign")) {//积分签到
            startActivity(CreditSignNewActivity.class);
            return true;
        } else {
            return false;
        }
    }

    public static final int MSG_INIT_UI = 1;
    public static final int MSG_PRO_CHANGED_UI = 2;
    public static final int MSG_START_UI = 3;
    public static final int MSG_FINISH_UI = 4;
    public static final int MSG_ONCLICK_SAVE_PIC_UI = 5;


    @SuppressLint("HandlerLeak")
    private Handler mTestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg==null)return;
            switch (msg.what) {
                case MSG_START_UI:
                    mPageLoadingProgressBar.setProgress(0);
                    mPageLoadingProgressBar.setVisibility(View.VISIBLE);
                    break;
                case MSG_FINISH_UI:
                    mPageLoadingProgressBar.setVisibility(View.INVISIBLE);
                    mPageLoadingProgressBar.setProgress(0);
                    break;
                case MSG_PRO_CHANGED_UI:
                    if (msg.arg1 >= 100) {
                        binding.afWebview.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
                        binding.afWebview.getSettings().setBlockNetworkImage(false);
//                        binding.afWebview.loadUrl(url);
                    }
                    mPageLoadingProgressBar.setProgress(msg.arg1);
                    KLog.d(msg.arg1);
                    break;
                case MSG_INIT_UI:
                    init();
                    break;
                case MSG_ONCLICK_SAVE_PIC_UI: //长按保存图片
                    onLongClickSavePic(msg);
                    break;
            }
            super.handleMessage(msg);
        }
    };


    /**
     * 长按保存图片
     *
     * @param msg
     */
    private void onLongClickSavePic(Message msg) {
        bitmapPic = (Bitmap) msg.obj;
        final Result ret = parsePic(bitmapPic);
        if (null == ret) {
//            ToastUtils.showShort("未解析到结果");
            Log.d(TAG, "未解析到结果");
            return;
        } else {
            if (ret.toString().startsWith("http://") || ret.toString().startsWith("https://")) {
                showDialog();
            } else {
                ToastUtils.showShort(ret.toString());
            }
        }
    }

    private void showDialog() {
        final MaterialDialog dialog = new MaterialDialog.Builder(mActivity)
                .customView(R.layout.dialog_custom_webview_qr_code, false).show();
        View customView = dialog.getCustomView();
        TextView act_send_friend = customView.findViewById(R.id.act_send_friend);
        TextView act_save_pic = customView.findViewById(R.id.act_save_pic);
        act_send_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//发送给朋友
                if (imagePath != null && bitmapPic != null) {
                    sendToFriends(imagePath, bitmapPic);
                } else {
                    Log.e(TAG, "下载推广海报失败!");
                }
                dialog.dismiss();
            }
        });
        act_save_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//保存相册图片
                if (isDownloadPoster) {
                    ToastUtils.showShort("已保存成功!");
                    dialog.dismiss();
                    return;
                } else {
                    isDownloadPoster = true;
                    saveToSystemGallery(mActivity, file, file.getName());  //把文件插入到系统图库
                    ToastUtils.showShort("保存成功!");
                }
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onPause() {
        if (binding.afWebview != null) {
            //pauseTimers()会  暂停所有 WebView 的 layout、parsing 和 JavaScript timers，这是一个全局生效的方法
            binding.afWebview.pauseTimers();
            binding.afWebview.onPause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (binding.afWebview != null) {
            binding.afWebview.onResume();
            //调用resumeTimers() 才能会能让WebView正常工作
            binding.afWebview.resumeTimers();
        }
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (binding == null) {
            return;
        }
        WebView web = binding.afWebview;
        if (web != null) {
            binding.afWebview.stopLoading();
            binding.afWebview.removeAllViews();
            binding.afWebview.setWebViewClient(null);
            binding.afWebview.setWebChromeClient(null);
            binding.afWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            unregisterForContextMenu(binding.afWebview);
            binding.afWebview.destroy();
            /*CookieSyncManager.createInstance(getActivity());
            CookieManager instance = CookieManager.getInstance();
            instance.removeAllCookie();
            CookieSyncManager.getInstance().sync();
            binding.afWebview.getSettings().setJavaScriptEnabled(false);
            binding.afWebview.clearCache(true);
            binding.afWebview.clearHistory();*/
        }
//        super.onDestroy();
    }


    /**
     * 保存图片到本地,其次把文件插入到系统图库
     *
     * @param context
     * @param file
     * @param fileName
     */
    public static void saveToSystemGallery(Context context, File file, String fileName) {
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
    }


    /**
     * 发送给好友
     */
    private void sendToFriends(final String imgUrl, final Bitmap bitmap) {
        final OnekeyShare oks = new OnekeyShare();//关闭sso授权
        oks.disableSSOWhenAuthorize();
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(imgUrl);//只分享图片
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams shareParams) {
                if (platform.getName().equals(Wechat.NAME)) {// 分享到微信朋友/朋友圈
                    shareParams.setShareType(Platform.SHARE_IMAGE);
                    shareParams.setUrl(imgUrl);
                    shareParams.setImageData(bitmap);
                }
            }
        });
        // 启动分享GUI
        oks.show(mActivity);
    }

    /**
     * 长按webview里面的图片，实现长按监听功能
     *
     * @param v
     * @return
     */
    @Override
    public boolean onLongClick(View v) {
        WebView.HitTestResult result = ((WebView) v).getHitTestResult();   //长按获取内容
        if (null == result)
            return false;
        int type = result.getType();
        switch (type) {
            case WebView.HitTestResult.EDIT_TEXT_TYPE: // 选中的文字类型
                break;
            case WebView.HitTestResult.PHONE_TYPE: // 处理拨号
                break;
            case WebView.HitTestResult.EMAIL_TYPE: // 处理Email
                break;
            case WebView.HitTestResult.GEO_TYPE: // &emsp;地图类型
                break;
            case WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
                break;
            case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE: // 带有链接的图片类型
            case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
                imagePath = result.getExtra();
                Log.d(TAG, "WebView.HitTestResult－－［IMAGE_TYPE］:" + url);
                try {
                    getDecodeAbleBitmap(imagePath);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return true;
            case WebView.HitTestResult.UNKNOWN_TYPE: //未知
                break;
        }
        return false;
    }

    private void requestPermissions() throws MalformedURLException {
        if (EasyPermissions.hasPermissions(mActivity, perms)) {
            downloadPoster();
        } else {
            EasyPermissions.requestPermissions(this, "保存图片到相册需要如下权限：", REQUEST_CODE_FILE_CHOOSER, perms);
        }

    }


    private void downloadPoster() throws MalformedURLException {
        URL url = new URL(imagePath);
        OkHttpUtils
                .get()
                .url(String.valueOf(url))
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "app_poster") {
                    @Override
                    public void onResponse(File response, int id) {
                        Log.e(TAG, "onResponse :" + response.getAbsolutePath());
                        file = response;
                        try {
                            Bitmap bitmap = mToBitmap(file);
                            Message message = Message.obtain();     //下面这是把图片携带在Message里面
                            message.what = MSG_ONCLICK_SAVE_PIC_UI;
                            message.obj = bitmap;
                            mTestHandler.sendMessage(message);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError :" + e.getMessage());
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        Log.d(TAG, "inProgress:======" + 100 * progress);
                    }
                });
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {//已授权
        try {
            downloadPoster();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {//如果拒绝，提示
        Toast.makeText(mActivity, "你拒绝了" + perms + "权限", Toast.LENGTH_SHORT).show();

    }

    //====================================与js 互调=========================================================

    /**
     * 与js 互调
     */
    public class JsCallback {
        private Context context;
        private String returnUrl;

        public JsCallback(FragmentActivity activity) {
            this.context = activity;
        }

        @JavascriptInterface
        public void jzd_native_h5(final String type, final String jsonData) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "<<jzd_native_h5>>\ntype======:" + type + "\njsonData======:" + jsonData);
                    //重新构建微信支付参数实体
                    OrderToPayEntity.ResultBean resultBean = new OrderToPayEntity.ResultBean();
                    OrderToPayEntity.ResultBean.DataBean dataBean = resultBean.getData();
                    final OrderToPayEntity.ResultBean.DataBean modelWx = new Gson().fromJson(jsonData, OrderToPayEntity.ResultBean.DataBean.class);
                    returnUrl = modelWx.getReturn_url();
                    payType = type;
                    if (type.equals("app.first")) {//商城首页
                        startActivity(HomePageActivity.class);
                    } else if (type.equals("app.member")) {//会员中心
                        startActivity(MyPageActivity.class);
                    } else if (type.equals("goods.detail")) {//商品详情
                        Gson gson = new Gson();
                        EntityH5ToShopDetails entityH5ToShopDetails = gson.fromJson(jsonData, EntityH5ToShopDetails.class);
                        String gid = entityH5ToShopDetails.getGid();
                        KLog.d(TAG, "gid===" + gid);
                        //跳转需要跳转的页面
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.GOODS_KEY_GID, gid);
                        bundle.putString(Constants.GOODS_OPEN_FLAG, "3");
//                        viewModel.saveOpenFlag("3");//保存打开标识
                        viewModel.startActivity(GoodsActivity.class, bundle);
                    } else if (type.equals("order.pay")) {//支付订单
                        Gson gson = new Gson();
                        EntityH5ToShopPay entityH5ToShopPay = gson.fromJson(jsonData, EntityH5ToShopPay.class);
                        String orderid = entityH5ToShopPay.getOrder_id();
                        KLog.d(TAG, "orderid===" + orderid);
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.BUNDLE_KEY_ORDERID, orderid);
                        bundle.putString(Constants.BUNDLE_KEY_MONEY, "money");
                        startContainerActivity(PayFragment.class.getCanonicalName(), bundle);
                    } else if (type.equals("buy.now")) {//立即购买
                        Gson gson = new Gson();
                        EntityH5ToConfirmOrder entityH5ToConfirmOrder = gson.fromJson(jsonData, EntityH5ToConfirmOrder.class);
                        viewModel.saveConfirmOrderParams(entityH5ToConfirmOrder);
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.TYPE, 0);
                        startActivity(FirmOrderActivity.class, bundle);
                    } else if (type.equals("app:member.cart")) {//购物车
                        startActivity(ShoppingCarActivity.class);
                    } else if (type.equals("app:sign")) { //签到
                        startActivity(CreditSignNewActivity.class);
                    } else if (type.equals("app:myshop")) {//我的小店
                        startActivity(MineShopHomeAty.class);
                    }
                    payWeixin(modelWx, type, jsonData);
                }
            });
        }

    }

    /**
     * 调用 微信/支付宝支付
     *
     * @param modelWx
     * @param type
     * @param jsonData
     */
    private void payWeixin(final OrderToPayEntity.ResultBean.DataBean modelWx, final String type, String jsonData) {
        if (type.equals("creditshop_wechat_pay") || type.equals("recharge_wechat_pay") || type.equals("coupon_wechat_pay")) {  //积分商城微信支付
            WXPayEntryActivity.payWeixin(AppApplication.sApi, modelWx, new WXEntryActivity.ApiCallback() {
                @Override
                public void onSuccess(Object response) {
                    KLog.d("支付onSuccess:", response);
//                   sendInfoToJs();
                    final Bundle bundle = new Bundle();
                    if (modelWx != null) {
                        if (type.equals("recharge_wechat_pay")) {//微信充值
                            Messenger.getDefault().sendNoMsg(MyViewModel.TOKEN_VIEWMODEL_REFRESH);  //发送刷新我的页面数据消息通知
                            getActivity().finish();
                            return;
                        } else if (type.equals("creditshop_wechat_pay") || type.equals("coupon_wechat_pay")) {//积分商城微信支付
                            bundle.putString(Constants.BUNDLE_KEY_URL, modelWx.getReturn_url());
                            Log.d(TAG, "return_url：" + modelWx.getReturn_url());
//                            binding.afWebview.clearCache(true);
//                            binding.afWebview.clearView();
//                            binding.afWebview.reload();

//                            MessageDialog.showMessageDialog(getActivity(),"支付成功!请确认到我的兑换记录中\n查看订单详情.",
//                            new MessageDialog.MessageDialogClick() {
//                                @Override
//                                public void onSureClickListener(){
//                                    binding.afWebview.loadUrl("http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=creditshop");
//                                }
//                            });

//                            startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
                            //方法一 无效
//                            binding.afWebview.loadUrl("http://test.gtt20.com/app/index.php?i=2&c=entry&m=ewei_shopv2&do=mobile&r=creditshop.lists&cate=12&mid=21");
//                            binding.afWebview.loadUrl( "javascript:window.location.reload( true )" );
                            //方法二 无效
//                            binding.afWebview.
//                                    evaluateJavascript("window.location.href='" + modelWx.getReturn_url() + "'",
//                                            new ValueCallback<String>() {
//                                                @Override
//                                                public void onReceiveValue(String s) {
//                                                    Log.e(TAG, "onReceiveValue " + s);
//                                                    binding.afWebview.loadUrl(modelWx.getReturn_url());
//                                                }
//                                            });
                            startActivity(MoreProductJumpLinkAty.class, bundle);
                        } else {
                        }
                    }
                }

                @Override
                public void onPayError(String res) {

                }

                @Override
                public void onError(int errorCode, String errorMsg) {
                    KLog.d("支付onError:", errorMsg);
                }

                @Override
                public void onFailure(IOException e) {
                    KLog.d("支付onFailure:", e.getMessage());
                }
            });
        } else if (type.equals("creditshop_alipay_pay") || type.equals("recharge_alipay_pay")) {  //积分商城支付宝支付
            Gson gson = new Gson();
            OrderToPayEntityH5 orderToPayEntityH5 = gson.fromJson(jsonData, OrderToPayEntityH5.class);
            String alipay_info = orderToPayEntityH5.getAlipay_info();
            Log.d("recharge_alipay_pay", "调用支付宝充值");
            payAlipay(alipay_info);
        } else {
        }

    }


    /**
     * 调用支付宝支付
     *
     * @param jsonData
     */
    private void payAlipay(final String jsonData) {
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(jsonData, true);
                String error = "aliPay error:(" + ")" + result.get("resultStatus") + "-" + result.get("result") + "-" + result.get("memo");
                Log.e("PayTask:", error);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    Log.d("resultStatus:", resultStatus);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        KLog.d("payAlipay支付onSuccess:", resultInfo);
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        if (payType.equals("recharge_alipay_pay")) {  //支付宝充值
                            Messenger.getDefault().sendNoMsg(MyViewModel.TOKEN_VIEWMODEL_REFRESH);  //发送刷新我的页面数据消息通知
                            getActivity().finish();
                            return;
                        } else {
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtils.showShort("支付失败");
                        Log.d("payAlipay支付error:", resultStatus);
                    }
                    break;
                }
            }
        }
    };


    //在java中调用js代码
    public void sendInfoToJs() {
        //调用js中的函数：appCallBack(msg)
        binding.afWebview.loadUrl("javascript:appCallBack()");
    }


    /**
     * //WebView页面共享使用Cookie
     */
    private void setWebViewCookie() {
        List<Cookie> allCookie = new PersistentCookieStore(getActivity()).getAllCookie();
        for (Cookie cookie : allCookie) {
            CookieManager.getInstance().setCookie(Uri.parse(url).getHost(), cookie.toString());
        }
    }


    /**
     * 将获取到的网络图片下载下来，通过流转成Bitmap
     *
     * @param picturePath 网络图片文件路径
     * @return
     */
    private void getDecodeAbleBitmap(final String picturePath) throws MalformedURLException {
        requestPermissions();
    }


    /**
     * 将图片文件转为bitmap
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    private Bitmap mToBitmap(File file) throws FileNotFoundException {
        if (file.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
            return bitmap;
        } else {
            return null;
        }
    }


    /**
     * 解析二维码图片
     *
     * @return
     */
    public Result parsePic(Bitmap bitmap) {
        //解析转换类型UTF-8
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        //新建一个RGBLuminanceSource对象，将bitmap图片传给此对象
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] data = new int[width * height];
        bitmap.getPixels(data, 0, width, 0, 0, width, height);
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(width, height, data);
        //将图片转换成二进制图片
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        //初始化解析对象
        QRCodeReader reader = new QRCodeReader();
        //开始解析
        Result result = null;
        try {
            result = reader.decode(binaryBitmap, hints);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * webview设置cookie,并同步
     *
     * @param context
     * @param url
     * @param cookie
     */
    public static void setCookie(Context context, String url, String cookie) {
        try {
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setCookie(url, cookie);
            if (Build.VERSION.SDK_INT < 21) {
                CookieSyncManager.getInstance().sync();
            } else {
                CookieManager.getInstance().flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除本地cookie    
     */
    public static void clearCookie(Context context) {
        try {
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();//移除
            cookieManager.removeAllCookie();
            if (Build.VERSION.SDK_INT < 21) {
                CookieSyncManager.getInstance().sync();
            } else {
                CookieManager.getInstance().flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示自定义错误提示页面，用一个View覆盖在WebView
     */
    protected void showErrorPage() {
        webParentView = (LinearLayout) binding.afWebview.getParent();
        initErrorPage();//初始化自定义页面
        Log.i(TAG, "getChildCount-->" + binding.afWebview.getChildCount());
        while (binding.afWebview.getChildCount() > 1) {
            binding.afWebview.removeViewAt(0);
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        binding.afWebview.addView(mErrorView, 0, lp);
    }


    /***
     * 显示加载失败时自定义的网页
     */
    protected void initErrorPage() {
        if (mErrorView == null) {
            mErrorView = View.inflate(mActivity, R.layout.load_error_h5_layout, null);
            TextView tv_error_relaod = mErrorView.findViewById(R.id.tv_error_relaod);
            tv_error_relaod.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (NetworkUtils.isNetworkConnected(mActivity)) {
                        loadError = false;
                        binding.afWebview.loadUrl(mFailingUrl);
                        //Toast.makeText(context,"reload",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            mErrorView.setOnClickListener(null);
        } else {
            // mErrorView.setVisibility(View.VISIBLE);
            //测试在Activity上没问题，但是Fragment上有些小问题，加上下面的没事了
            ViewParent parent = mErrorView.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(mErrorView);
            }
            mErrorView = View.inflate(mActivity, R.layout.load_error_h5_layout, null);
            TextView tv_error_relaod = mErrorView.findViewById(R.id.tv_error_relaod);
            tv_error_relaod.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (NetworkUtils.isNetworkConnected(mActivity)) {
                        loadError = false;
                        binding.afWebview.loadUrl(mFailingUrl);
                        //Toast.makeText(context,"reload",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            mErrorView.setOnClickListener(null);
        }
    }


}
