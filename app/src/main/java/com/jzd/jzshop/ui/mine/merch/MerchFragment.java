package com.jzd.jzshop.ui.mine.merch;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentMerchBinding;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
@Deprecated
public class MerchFragment extends BaseFragment<FragmentMerchBinding, MerchViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_merch;
    }

    @Override
    public int initVariableId() {
        return me.goldze.mvvmhabit.BR.merchVM;
    }

    @Override
    public MerchViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(MerchViewModel.class);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initData() {
        super.initData();
        viewModel.initToolbar();
        Bundle arguments = getArguments();
        final WebView web = binding.web;
        viewModel.setbing(binding);
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                viewModel.setTitleText(title);
            }
        };
        web.setWebChromeClient(webChromeClient);
        web.setWebViewClient(new WebViewClient());
        if (arguments != null) {
            String string = arguments.getString(Constants.MINE_STORE);
            web.loadUrl(string);
        }
        web.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            web.goBack();
                        }
                    });
                    return true;
                }
                return false;
            }
        });
    }
}