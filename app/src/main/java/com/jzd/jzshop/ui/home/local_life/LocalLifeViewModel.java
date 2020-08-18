package com.jzd.jzshop.ui.home.local_life;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityLocalLifeBinding;
import com.jzd.jzshop.entity.LocalLifeEntity;
import com.jzd.jzshop.ui.home.local_life.location.ChoiceCityAty;
import com.jzd.jzshop.ui.home.local_life.search.SearchKeyWordAty;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by lxb on 2020/2/13.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class LocalLifeViewModel extends BaseViewModel<Repository>  {
    private static final String TAG = LocalLifeViewModel.class.getSimpleName();
    String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE};
    private Context context;
    private ActivityLocalLifeBinding mBinding;

//    public ObservableField<String> locationName = new ObservableField<>();    //当前城市
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {

        public SingleLiveEvent<LocalLifeEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
    }

    public LocalLifeViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityLocalLifeBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    public void saveCity(String address){
        model.saveLocationCity(address);
    }

    /**
     *  获取 本地生活数据
     * @param status
     */
    public void requestData(int status) {
        isShowDialog(false);
        addSubscribe(model.postLocalLife(model.getUserToken(),0), new ParseDisposableTokenErrorObserver<LocalLifeEntity>() {
            @Override
            public void onResult(LocalLifeEntity localLifeEntity) {
                super.onResult(localLifeEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (localLifeEntity != null) {
                    if (localLifeEntity.getResult()!=null){
                        uc.mLiveData.setValue(localLifeEntity.getResult());
                    }
                }
                dismissDialog();
            }
            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
            }
            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
            }
        });
    }

    //返回
    public BindingCommand backOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });
    //搜索
    public BindingCommand searchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (EasyPermissions.hasPermissions(context,perms)) {
                startActivity(SearchKeyWordAty.class);
            } else {
                ((LocalLifeAty)context).requestPermissions();
            }
        }
    });

    //定位
    public BindingCommand locationOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (EasyPermissions.hasPermissions(context,perms)) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.BUNDLE_KEY_LOCATION_CITY,model.getLoacationCity());
                startActivity(ChoiceCityAty.class,bundle);
            } else {
                ((LocalLifeAty)context).requestPermissions();
            }
        }
    });
    //banner
    public BindingCommand bannerOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO: 2020/2/13
        }
    });
    //优选美食
    public BindingCommand foodOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO: 2020/2/13
            ((LocalLifeAty)context).requestPermissions();
        }
    });
    //酒店住宿
    public BindingCommand hotelOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO: 2020/2/13
            ((LocalLifeAty)context).requestPermissions();
        }
    });



}
