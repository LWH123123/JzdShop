package com.jzd.jzshop.ui.home.local_life.search;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivitySearchKeyWordBinding;
import com.jzd.jzshop.entity.LocalLifeEntity;
import com.jzd.jzshop.entity.SearchKeyWordEntity;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by lxb on 2020/2/15.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class SearchKeyWordViewModel extends BaseViewModel<Repository> {
    private static final String TAG = SearchKeyWordViewModel.class.getSimpleName();
    public static final String TOKEN_VIEWMODEL_SEARCH_KEYWORD_REFRESH = "token_viewmodel_search_keyword_refresh_data";
    private Context context;
    private ActivitySearchKeyWordBinding mBinding;

    public ObservableField<String> locationName = new ObservableField<>();    //当前城市
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {

        public SingleLiveEvent<SearchKeyWordEntity> mLiveData = new SingleLiveEvent<>();
    }

    public SearchKeyWordViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivitySearchKeyWordBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }


    public void requestData() {
        // TODO: 2020/2/15  
    }

    //返回
    public BindingCommand backOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

}
