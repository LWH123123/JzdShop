package com.jzd.jzshop.ui.home.local_life.location;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityChoiceCityBinding;
import com.jzd.jzshop.entity.ChoiceCityEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by lxb on 2020/2/15.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class ChoiceCityViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = ChoiceCityViewModel.class.getSimpleName();
    public static final String TOKEN_VIEWMODEL_CHOICE_CITY_REFRESH = "ttoken_viewmodel_choice_city_refresh_data";
    private Context context;
    private ActivityChoiceCityBinding mBinding;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<ChoiceCityEntity> mLiveData = new SingleLiveEvent<>();
    }

    public ChoiceCityViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityChoiceCityBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }


    public void requestData() {

    }


    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
