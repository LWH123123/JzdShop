package com.jzd.jzshop.ui.mine.assets;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentAssetsIntroductionBinding;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by lxb on 2020/2/11.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class AssetsIntroductionViewModel extends ToolbarViewModel<Repository>{

    private static final String TAG = AssetsIntroductionViewModel.class.getSimpleName();
    private Context context;
    private FragmentAssetsIntroductionBinding mBinding;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent mLiveData = new SingleLiveEvent<>();
    }

    public AssetsIntroductionViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, FragmentAssetsIntroductionBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setToolbarBgColor(Color.parseColor("#00ffffff"));
    }

    public void requestData() {

    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

}
