package com.jzd.jzshop.utils;

import android.content.Context;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import me.goldze.mvvmhabit.utils.KLog;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

/**
 * @author LXB
 * @description: webview 基础配置
 * @date :2019/12/9 12:30
 */
public class BaseWebviewUtils {
    private static final String TAG = "BaseWebviewUtils";

    /**
     *  webview 基础配置
     * @param context
     * @param webView
     */
    public static void initWebView(Context context,WebView webView){
        WebSettings webSetting = webView.getSettings();
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setJavaScriptEnabled(true); //开启JavaScript支持
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
         webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        //---------------------------h5 调用android相机拍照和录像------------start-------------------------------
        //设置自适应屏幕，两者合用
        webSetting.setUseWideViewPort(true);
        webSetting.setLoadWithOverviewMode(true);


        webSetting.setDefaultTextEncodingName("UTF-8");
        webSetting.setAllowContentAccess(true);  // 是否可访问Content Provider的资源，默认值 true
        webSetting.setAllowFileAccess(true);   //是否可访问本地文件，默认值 true
        webSetting.setAllowFileAccessFromFileURLs(false);   //是否允许通过file url加载的Javascript读取本地文件，默认值 false
        webSetting.setAllowUniversalAccessFromFileURLs(false);  // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
       //---------------------------h5 调用android相机拍照和录像------------end-------------------------------
        //缩放操作
        webSetting.setSupportZoom(true); // 支持缩放
        webSetting.setBuiltInZoomControls(true);
        webSetting.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSetting.setTextZoom(100);
        webSetting.setDefaultFontSize(18);        //设置默认的字体大小，默认为16，有效值区间在1-72之间
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(context.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(context.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(context.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        if (!NetworkUtils.isNetworkConnected(context)) {
            webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {
            webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
        }

        webSetting.setBlockNetworkImage(true);  //先阻塞加载图片 先加载文本
//        webSetting.setBlockNetworkImage(false);     //页面加载好以后，在放开图片

        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH); //提高渲染等级
        // webSetting.setPreFectch(true);

        webSetting.setNeedInitialFocus(false);        //禁止webview上面控件获取焦点(黄色边框)
        webSetting.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSetting.setDefaultTextEncodingName("utf-8");        //设置编码格式

        long time = System.currentTimeMillis();
        KLog.d("time-cost", "cost time: " + (System.currentTimeMillis() - time));
        CookieSyncManager.createInstance(context);
        CookieSyncManager.getInstance().sync();

        //需求：让H5那边需要区分是PC、安卓APP、iOSAPP、安卓微信浏览器、iOS微信浏览器访问的。UA里面包括底层系统，手机型号，手机系统版本号等等信息
        //修改安卓内置浏览器的UA(User Agent)，即用户代理 ,传递给后台标识
        webSetting.setUserAgentString(webSetting.getUserAgentString()+Constants.WEBVIEW_USER_AGENT_NAME);
    }

    /**
     *  加载一段 网页片段是使用,自动缩放至屏幕大小
     * @param bodyHTML
     * @return
     */
    public static final String getHtmlData(String bodyHTML) {
        String head = "<head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> <style>img{max-width: 100%; width:100%; height:auto;}</style> </head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

}
