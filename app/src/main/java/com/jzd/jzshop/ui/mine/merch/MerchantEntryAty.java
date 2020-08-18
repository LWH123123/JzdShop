package com.jzd.jzshop.ui.mine.merch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityMerchantEntryBinding;
import com.jzd.jzshop.entity.MerchEntity;
import com.jzd.jzshop.ui.category.CategoryActivity;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.ui.pay.PayFragment;
import com.jzd.jzshop.ui.shoppingcar.ShoppingCarActivity;
import com.jzd.jzshop.utils.BaseWebviewUtils;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.SystemUtils;
import com.jzd.jzshop.utils.photo_album.FileFromUriUtils;
import com.jzd.jzshop.utils.photo_album.PhotoUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description: 商家入驻
 * @date :2019/12/18 17:10
 */
public class MerchantEntryAty extends BaseActivity<ActivityMerchantEntryBinding, MerchantEntryViewModel> {
    private ProgressBar mPageLoadingProgressBar = null;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    String url;
    //webview  h5 调起原生相机和相册
    // 拍照相处选图
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private final static int PHOTO_REQUEST = 100;
    private final static int REQUEST_CODE_FILE_CHOOSER = 101;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private Uri imageUri;
    //=================text============
    private String mCameraFilePath;


    // webview   handler
    public static final int MSG_INIT_UI = 1;
    public static final int MSG_PRO_CHANGED_UI = 2;
    public static final int MSG_START_UI = 3;
    public static final int MSG_FINISH_UI = 4;
    @SuppressLint("HandlerLeak")
    private Handler mTestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg ==null)return;
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
                        binding.webview.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
                        binding.webview.getSettings().setBlockNetworkImage(false);
//                        binding.afWebview.loadUrl(url);
                    }
                    mPageLoadingProgressBar.setProgress(msg.arg1);
                    KLog.d(msg.arg1);
                    break;
                case MSG_INIT_UI:
                    initWebView();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_merchant_entry;
    }

    @Override
    public int initVariableId() {
        return BR.merchantVM;
    }

    @Override
    public MerchantEntryViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MerchantEntryViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setBinding(mContext, binding);
        viewModel.initToolBar(getResources().getString(R.string.merchant_entry));
        viewModel.requestword();
//        initWebView();
//        getIntentData();
//        binding.webview.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK && binding.webview.canGoBack()) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            binding.webview.goBack();
//                        }
//                    });
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            url = bundle.getString(Constants.MINE_STORE);
            String title = bundle.getString(Constants.BUNDLE_KEY_TITLE);
//            viewModel.setTitleText(title);
            binding.webview.loadUrl(url);
        }
    }

    private void initWebView() {
        initProgressBar();
        binding.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //return true；//本地打开，自己处理，推荐，尤其现在android新版推行速度愈发快了
                MerchantEntryAty.this.url = url;
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
//                viewModel.setTitleText(view.getTitle());
                mTestHandler.sendEmptyMessage(MSG_FINISH_UI);
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

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int errorCode = error.getErrorCode();
                    Log.d("onReceivedError", "errorCode --->>>" + errorCode);
                    Uri url = request.getUrl();
                    Log.d("onReceivedError", "url --->>>" + url);
                    Log.d("onReceivedError", "error --->>>" + error.getDescription());
                }
            }
        });

        /**
         * android要获取javascript的弹窗功能（或console）功能
         * WebChromeClient 辅助WebView处理图片上传操作【<input type=file> 文件上传标签】
         */
        binding.webview.setWebChromeClient(new WebChromeClient() {
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
//                viewModel.setTitleText(title);
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

            /**
             * 这里写入你自定义的window alert
             */
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.d("alert", message);
                result.confirm();     //劫持alert以后释放，不然会卡死
                return true;
//                return super.onJsAlert(null, url, message, result);
            }

            /**
             * todo:  webview 坑
             *  WebChromeClient的openFileChooser()只调用了一次
             *  Android开发深入理解WebChromeClient之onShowFileChooser或openFileChooser使用说明
             *  url: https://www.teachcourse.cn/2224.html
             * @param webView
             * @param filePathCallback
             * @param fileChooserParams
             * @return
             */
            // For Android 5.0+
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                Log.d(TAG, "onShowFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)");
                mUploadCallbackAboveL = filePathCallback;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    String[] acceptTypes = fileChooserParams.getAcceptTypes();
                }
//                takePhoto();
                showFileChooser();
                return true;
            }

            //For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.d(TAG, "openFileChoose(ValueCallback<Uri> uploadMsg, String acceptType, String capture)");
                mUploadMessage = uploadMsg;
//                takePhoto();
                showFileChooser();
            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                Log.d(TAG, "openFileChoose( ValueCallback uploadMsg, String acceptType )");
                mUploadMessage = uploadMsg;
//                takePhoto();
                showFileChooser();
            }

            // For Android 3.0-
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                Log.d(TAG, "openFileChoose(ValueCallback<Uri> uploadMsg)");
                mUploadMessage = uploadMsg;
//                takePhoto();
                showFileChooser();
            }

        });
        BaseWebviewUtils.initWebView(this, binding.webview);  //webview 基础配置
        //设置js调用函数方法
        binding.webview.addJavascriptInterface(new JsCallback(this), "jzsp");
    }

    private void initProgressBar() {
        mPageLoadingProgressBar = binding.progressBar1;
        mPageLoadingProgressBar.setMax(100);
        mPageLoadingProgressBar.setProgressDrawable(this.getResources().getDrawable(R.drawable.color_progressbar));
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.goBack.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (binding.webview.canGoBack()) {
                    binding.webview.goBack();
                } else {
                    finish();
                }
            }
        });

        viewModel.update.observe(this, new Observer<MerchEntity>() {
            @Override
            public void onChanged(@Nullable MerchEntity merchEntity) {
                if(merchEntity.getResult().getStatus()==0){
                    ToastUtils.showLong("您的申请正在审核中,无需重复申请");
                }
                if(merchEntity.getResult().getStatus()==-1){
                    ToastUtils.showLong(merchEntity.getResult().getReason());
                }
                MerchEntity.ResultBean result = merchEntity.getResult();
                binding.miName.setText(result.getRealname());
                binding.miPhone.setText(result.getMobile());
                binding.miStorename.setText(result.getMerchname());
                binding.miSalecate.setText(result.getSalecate());
                binding.miDesc.setText(result.getDesc());
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        mCommon();
        if (requestCode == REQUEST_CODE_FILE_CHOOSER) {
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            // 压缩到多少宽度以内
            int maxW = 1000;
            // 压缩到多少大小以内,1024kb
            int maxSize = 1024;
            if (result == null) {
                // 看是否从相机返回
                File cameraFile = new File(mCameraFilePath);
                if (cameraFile.exists()) {
                    result = Uri.fromFile(cameraFile);
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));
                }
            }
            if (result != null) {
                // 根据uri获取路径
                String path = FileFromUriUtils.getPath(this, result);
                if (!TextUtils.isEmpty(path)) {
                    File f = new File(path);
                    if (f.exists() && f.isFile()) {
                        // 按大小和尺寸压缩图片
                        Bitmap b = getCompressBitmap(path, maxW, maxW, maxSize);
                        String basePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                        String compressPath = basePath + File.separator + "photos" + File.separator
                                + System.currentTimeMillis() + ".jpg";
                        // 压缩完保存在文件里
                        if (saveBitmapToFile(b, compressPath)) {
                            Uri newUri = null;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                newUri = FileProvider.getUriForFile(MerchantEntryAty.this,
                                        "com.jzd.jzshop.img.fileProvider",
                                        new File(compressPath));
                            } else {
                                newUri = Uri.fromFile(new File(compressPath));
                            }

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                if (mUploadCallbackAboveL != null) {
                                    if (newUri != null) {
                                        mUploadCallbackAboveL.onReceiveValue(new Uri[]{newUri});
                                        mUploadCallbackAboveL = null;
                                        return;
                                    }

                                }
                            } else if (mUploadMessage != null) {
                                if (newUri != null) {
                                    mUploadMessage.onReceiveValue(newUri);
                                    mUploadMessage = null;
                                    return;
                                }
                            }
                        }
                    }
                }
            }
            clearUploadMessage();
            return;
        }


    }

    /**
     * takePhoto() 所对应回调处理
     */
    private void mCommon() {
        //        if (requestCode == PHOTO_REQUEST) {
//            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
//            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
//            if (mUploadCallbackAboveL != null) {
//                onActivityResultAboveL(requestCode, resultCode, data);
//            } else if (mUploadMessage != null) {
//                mUploadMessage.onReceiveValue(result);
//                mUploadMessage = null;
//            }
//        } else if (requestCode == REQUEST_CODE_FILE_CHOOSER) {
//            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
//            if (data!=null){
//                Uri uri = data.getData();
//                if (data.getData() == null) {
//                    Log.i("tag", "The uri is not exist.");
//                }
//                String imageName = System.currentTimeMillis() + ".jpg";
//                File imageFile = getPicFile(imageName);
//                 Uri tempUri = Uri.fromFile(imageFile);
//                 imageUri= tempUri;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    if (mUploadCallbackAboveL != null) {
//                        if (imageUri != null) {
//                            mUploadCallbackAboveL.onReceiveValue(new Uri[]{imageUri});
//                            mUploadCallbackAboveL = null;
//                            return;
//                        }
//                    }
//                } else if (mUploadMessage != null) {
//                    if (imageUri != null) {
//                        mUploadMessage.onReceiveValue(imageUri);
//                        mUploadMessage = null;
//                        return;
//                    }
//                }
//            }
//            clearUploadMessage();
//            return;
//        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != PHOTO_REQUEST || mUploadCallbackAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
    }


    /**
     * webview没有选择图片也要传null，防止下次无法执行
     */
    private void clearUploadMessage() {
        if (mUploadCallbackAboveL != null) {
            mUploadCallbackAboveL.onReceiveValue(null);
            mUploadCallbackAboveL = null;
        }
        if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(null);
            mUploadMessage = null;
        }
    }


    @Override
    public void onPause() {
        if (binding.webview != null) {
            binding.webview.pauseTimers();
            binding.webview.onPause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (binding.webview != null) {
            binding.webview.onResume();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (binding.webview != null) {
            binding.webview.stopLoading();
            binding.webview.removeAllViews();
            binding.webview.setWebViewClient(null);
            binding.webview.setWebChromeClient(null);
            binding.webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            unregisterForContextMenu(binding.webview);
            binding.webview.destroy();
        }
        super.onDestroy();
    }


    /**
     * H5链接拦截 跳转
     *
     * @param view
     * @param url
     * @return
     */
    private boolean interceptH5UrlJump(WebView view, String url) {
        if (url != null && (url.contains("http://") || url.contains("https://"))) { //是协议跳webview 打开（不包含拦截跳转）
//                    interceptH5UrlJump(url);
            if (url.contains("=goods.detail")) {
                String param = SystemUtils.URLRequest(url).get("id");   //android 获取url中的参数
                KLog.d(TAG, "id===" + param);
                //跳转需要跳转的页面
                Bundle bundle = new Bundle();
                bundle.putString(Constants.GOODS_KEY_GID, param);
                bundle.putString(Constants.GOODS_OPEN_FLAG, "3");
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
            ToastUtils.showShort("开发中，敬请期待!");
            return true;
        } else {
            return false;
        }
    }


    /**
     * 拍照（单独调起拍照可用）
     */
    private void takePhoto() {
        File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/" + SystemClock.currentThreadTimeMillis() + ".jpg");
        imageUri = Uri.fromFile(fileUri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//com.jzd.jzshop.img.fileProvider
            imageUri = FileProvider.getUriForFile(mContext, "com.jzd.jzshop.img.fileProvider", fileUri);//通过FileProvider创建一个content类型的Uri
        }
        requestPermissions();
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int check1 = ContextCompat.checkSelfPermission(mContext, permissions[0]);
            int check2 = ContextCompat.checkSelfPermission(mContext, permissions[1]);
            if (check1 + check2 != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 1);
            } else {
                PhotoUtils.takePicture(this, imageUri, PHOTO_REQUEST);
//                PhotoUtils.openPic(this, REQUEST_CODE_FILE_CHOOSER);
            }
        } else {
            PhotoUtils.takePicture(this, imageUri, PHOTO_REQUEST);
//            PhotoUtils.openPic(this, REQUEST_CODE_FILE_CHOOSER);
        }
    }

    //========================  Android webview 实现h5 调原生 选择图片调用系统相册/相机并进行图片压缩功能    start  ==================================

    /**
     * 打开选择图片/相机
     */
    private void showFileChooser() {
        if (Build.VERSION.SDK_INT >= 23) {
            int check1 = ContextCompat.checkSelfPermission(mContext, permissions[0]);
            int check2 = ContextCompat.checkSelfPermission(mContext, permissions[1]);
            if (check1 + check2 != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 1);
            } else {
                openFileChooser();
            }
        } else {
            openFileChooser();
        }

    }

    public void openFileChooser() {
        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
        intent1.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
//        intent1.addCategory(Intent.CATEGORY_OPENABLE);
//        intent1.setType("image/*");
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mCameraFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
                System.currentTimeMillis() + ".jpg";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // android7.0注意uri的获取方式改变
            Uri photoOutputUri = FileProvider.getUriForFile(
                    MerchantEntryAty.this,
                    "com.jzd.jzshop.img.fileProvider",
                    new File(mCameraFilePath));
            intent2.putExtra(MediaStore.EXTRA_OUTPUT, photoOutputUri);
        } else {
            intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));
        }

        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_TITLE, "File Chooser");
        chooser.putExtra(Intent.EXTRA_INTENT, intent1);
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent2});
        startActivityForResult(chooser, REQUEST_CODE_FILE_CHOOSER);
    }


    /**
     * 根据路径获取bitmap（压缩后）
     *
     * @param srcPath 图片路径
     * @param width   最大宽（压缩完可能会大于这个，这边只是作为大概限制，避免内存溢出）
     * @param height  最大高（压缩完可能会大于这个，这边只是作为大概限制，避免内存溢出）
     * @param size    图片大小，单位kb
     * @return 返回压缩后的bitmap
     */
    public static Bitmap getCompressBitmap(String srcPath, float width, float height, int size) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        int scaleW = (int) (w / width);
        int scaleH = (int) (h / height);
        int scale = scaleW < scaleH ? scaleH : scaleW;
        if (scale <= 1) {
            scale = 1;
        }
        newOpts.inSampleSize = scale;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        // 压缩好比例大小后再进行质量压缩
        return compressImage(bitmap, size);
    }

    /**
     * 图片质量压缩
     *
     * @param image 传入的bitmap
     * @param size  压缩到多大，单位kb
     * @return 返回压缩完的bitmap
     */
    public static Bitmap compressImage(Bitmap image, int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        // 循环判断如果压缩后图片是否大于size,大于继续压缩
        while (baos.toByteArray().length / 1024 > size) {
            // 重置baos即清空baos
            baos.reset();
            // 每次都减少10
            options -= 10;
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * bitmap保存为文件
     *
     * @param bm       bitmap
     * @param filePath 文件路径
     * @return 返回保存结果 true：成功，false：失败
     */
    public static boolean saveBitmapToFile(Bitmap bm, String filePath) {
        try {
            File file = new File(filePath);
            file.deleteOnExit();
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            boolean b = false;
            if (filePath.toLowerCase().endsWith(".png")) {
                b = bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            } else {
                b = bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            }
            bos.flush();
            bos.close();
            return b;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return false;
    }
    //========================  Android webview 实现h5 调原生 选择图片调用系统相册/相机并进行图片压缩功能   end  ==================================

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
                }
            });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            openFileChooser();
        } else {
            // 没有获取 到权限，从新请求，或者关闭app
            Toast.makeText(this, "需要存储权限", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == 10003) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openFileChooser();
            }
        }
    }
}
