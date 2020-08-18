package com.jzd.jzshop.ui.website;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityWebsiteBinding;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by lxb on 2020/2/19.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class WebSiteViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = WebSiteViewModel.class.getSimpleName();
    private Context context;
    private ActivityWebsiteBinding mBinding;
    private String url, title;

    public UIChangeObservable uc = new UIChangeObservable();


    public class UIChangeObservable {
        public SingleLiveEvent mLiveData = new SingleLiveEvent<>();
    }

    public WebSiteViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityWebsiteBinding binding,String url, String title) {
        this.context = mContext;
        this.mBinding = binding;
        this.title = title;
        this.url = url;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setIvBackIsVisible(View.GONE);
    }

    public void requestData() {

    }

}
