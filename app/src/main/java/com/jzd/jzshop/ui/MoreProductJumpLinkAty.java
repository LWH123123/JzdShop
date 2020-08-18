package com.jzd.jzshop.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.PermissionInterceptor;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityMoreProductJumpLinkBinding;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * Created by ${lixuebin} on 2018/10/4.
 * 邮箱：2072301410@qq.com
 * TIP： 公用的WebView
 */

public class MoreProductJumpLinkAty extends BaseActivity<ActivityMoreProductJumpLinkBinding, MoreProductJumpLinkViewModel> {
    private static final String TAG = MoreProductJumpLinkAty.class.getSimpleName();
    private AgentWeb agentWeb;
    private String url = "";
    private String title = "";


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_more_product_jump_link;
    }

    @Override
    public int initVariableId() {
        return com.jzd.jzshop.BR.jumpModel;
    }

    @Override
    public MoreProductJumpLinkViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MoreProductJumpLinkViewModel.class);
    }

    @Override
    public void initParam() {
        super.initParam();
        getIntentData();
    }

    @Override
    public void initData() {
        super.initData();
        getIntentData();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String string = extras.getString(Constants.BUNDLE_KEY_TITLE);
            KLog.a("标题", string);
            viewModel.initToolbar(string);
        }

        viewModel.setBinding(binding, url, title);
        loadUrl(url);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
    }

    @Override
    protected void onResume() {
        if (agentWeb != null) {
            agentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    private void getIntentData() {
        url = getIntent().getStringExtra(Constants.BUNDLE_KEY_URL);
        title = getIntent().getStringExtra(Constants.BUNDLE_KEY_TITLE);

    }

    private void loadUrl(String url_to_repay) {
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(binding.webViewContainer, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(Color.parseColor("#00000000"),3)//使用默认进度条  "#FF7E7D"
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setPermissionInterceptor(mPermissionInterceptor)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go(url_to_repay);
        //配置
        WebView webView = agentWeb.getWebCreator().getWebView();
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            if (view.getProgress() == 100) {
                agentWeb.getIndicatorController().finish();
            } else {
                agentWeb.getIndicatorController().finish();
            }
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (view.getProgress() == 100) {
                agentWeb.getIndicatorController().finish();
            } else {
                agentWeb.getIndicatorController().finish();
            }
        }
    };

    protected PermissionInterceptor mPermissionInterceptor = new PermissionInterceptor() {
        @Override
        public boolean intercept(String url, String[] permissions, String action) {
            Log.i(TAG, "url:" + url + "  permission:" + permissions + " action:" + action);
            return false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (agentWeb.handleKeyEvent(keyCode, event)) {
            Log.d(TAG, "相应h5 返回键盘");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        if (agentWeb != null) {
            agentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        if (agentWeb != null) {
            agentWeb.clearWebCache();
            agentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }

}
