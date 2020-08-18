package com.jzd.jzshop.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jzd.jzshop.R;


/**
 * @author LXB
 * @description: 打开企业qq，创建临时会话
 * 前提是你设置的QQ是通过QQ推广。需要自己去通过一下,QQ推广验证地址(http://shang.qq.com/v3/index.html).
 * @date :2019/12/31 16:42
 */
public class QQWebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq_webview);
        webView = findViewById(R.id.webview);
        Intent intent = getIntent();
        if (null != intent) {
            String url = intent.getStringExtra("url");
            webView.loadUrl(url);
            //支持App内部javascript交互
            webView.getSettings().setJavaScriptEnabled(true);
            //自适应屏幕
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            //设置可以支持缩放
            webView.getSettings().setSupportZoom(false);
            //扩大比例的缩放
            webView.getSettings().setUseWideViewPort(false);
            //设置是否出现缩放工具
            webView.getSettings().setBuiltInZoomControls(false);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith("mqqwpa")) {
                        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        in.setAction(Intent.ACTION_VIEW);
                        startActivity(in);
                    } else {
                        view.loadUrl(url);
                    }
                    return true;
                }

                @Nullable
                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                    return null;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });
        }
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
