package com.jzd.jzshop.ui.website;

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
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityWebsiteBinding;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.category.CategoryActivity;
import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.ui.mine.news.MyPageActivity;
import com.jzd.jzshop.ui.shoppingcar.ShoppingCarActivity;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * Created by lxb on 2020/2/19.
 * 邮箱：2072301410@qq.com
 * TIP：君子道
 */
public class WebSiteActivity extends BaseActivity<ActivityWebsiteBinding, WebSiteViewModel> {
    private static final String TAG = WebSiteActivity.class.getSimpleName();
    private NavigationController build;
    private AgentWeb agentWeb;
    private String url = "http://main.gtt20.com";
    private String title = "";
    private WebSettings settings;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_website;
    }

    @Override
    public int initVariableId() {
        return BR.websiteVM;
    }

    @Override
    public WebSiteViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(WebSiteViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
//        getIntentData();
        viewModel.initToolBar(getResources().getString(R.string.website));
        viewModel.setBinding(mContext, binding,url, getResources().getString(R.string.website));
        loadUrl(url);
        String userAgentString = settings.getUserAgentString();
        Log.d(TAG,"userAgentString:=="+userAgentString);
        viewModel.requestData();
        initBottomTab();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (agentWeb != null) {
            agentWeb.getWebLifeCycle().onResume();
        }
        int anInt = SPUtils.getInstance().getInt(Constants.MINE_GOOD, 0);
        if(anInt!=0) {
            build.setMessageNumber(3, anInt);
        }
        build.setSelect(2);
    }

    private void getIntentData() {
        url = getIntent().getStringExtra(Constants.BUNDLE_KEY_URL);
        title = getIntent().getStringExtra(Constants.BUNDLE_KEY_TITLE);

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
    }

    private void initBottomTab() {
        build = InitBottomTab.getInstance(Utils.getContext()).init(binding.pagerBottomTab, new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                startOtherActivity(index);
            }

            @Override
            public void onRepeat(int index) {

            }
        });
    }

    private void startOtherActivity(int index) {
        switch (index) {
            case 0:
                startActivity(HomePageActivity.class);
                break;
            case 1:
                startActivity(CategoryActivity.class);
                break;
            case 2:
                break;
            case 3:
                startActivity(ShoppingCarActivity.class);
                break;
            case 4:
                startActivity(MyPageActivity.class);
                break;
        }
        overridePendingTransition(0, 0);
    }

    private void loadUrl(String url_to_repay) {
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(binding.webViewContainer, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(Color.parseColor("#00000000"),3)//使用默认进度条  "#FF7E7D"
//                .useDefaultIndicator(Color.parseColor("#FF7E7D"),3)//使用默认进度条  "#FF7E7D"
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
        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //修改安卓内置浏览器的UA(User Agent)，即用户代理 ,传递给后台标识
        settings.setUserAgentString(settings.getUserAgentString()+ Constants.WEBVIEW_USER_AGENT_NAME);
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            if (view.getProgress() == 100) {
                agentWeb.getIndicatorController().finish();
            } else {
            }
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (view.getProgress() == 100) {
                agentWeb.getIndicatorController().finish();
            } else {
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
    protected void onDestroy() {
        super.onDestroy();
        build = null;
        if (agentWeb != null) {
            agentWeb.clearWebCache();
            agentWeb.getWebLifeCycle().onDestroy();
        }
    }

}
