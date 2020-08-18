package com.jzd.jzshop.ui;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityHomeNewBinding;
import com.jzd.jzshop.databinding.ActivityStartingBinding;
import com.jzd.jzshop.entity.AppUpdateEntity;
import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.entity.HomeEntity;
import com.jzd.jzshop.entity.HomeMenuEntity;
import com.jzd.jzshop.entity.HomeNoticeEntity;
import com.jzd.jzshop.entity.ShopSpecialEntity;
import com.jzd.jzshop.ui.mine.news.MyPageActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.NetworkUtils;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;

public class SplashViewModel extends BaseViewModel<Repository> {
    private static final String TAG = SplashViewModel.class.getSimpleName();
    private Context context;
    private ActivityStartingBinding binding;

    public UIChangeObservable uc = new UIChangeObservable();
    public ObservableField<List<HomeEntity.ResultBean.DataBeanX>> homedata = new ObservableField<>();

    public class UIChangeObservable {
        public SingleLiveEvent<AppUpdateEntity> appUpdate = new SingleLiveEvent<>();
        public SingleLiveEvent nowork = new SingleLiveEvent<>();
        public SingleLiveEvent<Object> domainChange = new SingleLiveEvent<>();
    }

    public SplashViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context context, ActivityStartingBinding binding) {
        this.binding = binding;
        this.context = context;
    }

    /**
     * APP 版本更新
     *
     * @param version
     */
    public void toAppUpdate(final String version) {
        isShowDialog(false);
        int netState = NetworkUtils.getNetWorkState(context);
        if(netState==-1){
            uc.nowork.call();
            return;
        }
        addSubscribe(model.postAppUpdate(version), new ParseDisposableObserver<AppUpdateEntity>() {
            @Override
            public void onResult(final AppUpdateEntity appUpdateEntity) {
                Log.d(TAG, "toAppUpdate onResult:");
                if (appUpdateEntity != null) {
                    if (appUpdateEntity.getResult() != null) {
                        uc.appUpdate.setValue(appUpdateEntity);
                    }
                }
            }

            @Override
            public void onError(String errarMessage) {
                Log.d(TAG, "toAppUpdate onError:" + errarMessage);
            }
        });
    }

    /**
     * 缓冲首页数据
     */
    public void requestHomeDataNetWork() {
//        String json_home_data = SPUtils.getInstance().getString("json_home_data");
//        KLog.json(TAG, "缓冲的首页数据 josn ------>>>>" + json_home_data);
//        if (!TextUtils.isEmpty(json_home_data)) {
//            Gson gson = new Gson();
//            HomeEntity homeEntity = gson.fromJson(json_home_data, HomeEntity.class);
//        }
        isShowDialog(false);
        addSubscribe(model.postHomeData(), new ParseDisposableObserver<HomeEntity>() {
            @Override
            public void onResult(HomeEntity entity) {
                homedata.set(null);
                ArrayList<BannEntity> bannerData = new ArrayList<>();
                bannerData.clear();
                //----------重新构建 menu shop 数据集，便于装载适配器-------start--------
                ArrayList<HomeMenuEntity> homeMenuEntities = new ArrayList<>();
                homeMenuEntities.clear();
                ArrayList<HomeNoticeEntity> homeNoticeEntities = new ArrayList<>();
                homeNoticeEntities.clear();
                ArrayList<HomeEntity.ResultBean.DataBeanX> homeShopEntities = new ArrayList<>();
                homeShopEntities.clear();
                ArrayList<HomeEntity.ResultBean.DataBeanX> homeJunZhiQEntities = new ArrayList<>();
                homeJunZhiQEntities.clear();
                ArrayList<ShopSpecialEntity> homeshopSpecialEntities = new ArrayList<>(); //商品专题
                homeshopSpecialEntities.clear();
                //----------重新构建 menu shop 数据集，便于装载适配器--------end---------
                for (HomeEntity.ResultBean bean : entity.getResult()) {
                    for (HomeEntity.ResultBean.DataBeanX dataBean : bean.getData()) {
                        if (bean.getId().equals("goods")) {
                            homeShopEntities.add(dataBean);
                        } else if (bean.getId().equals("banner")) {
                            bannerData.add(new BannEntity(dataBean.getImgurl(), dataBean.getLinkurl()));
                        } else if (bean.getId().equals("menu")) {
                            homedata.set(bean.getData());
                            homeMenuEntities.add(new HomeMenuEntity(dataBean));
                        } else if (bean.getId().equals("notice")) {
                            homeNoticeEntities.add(new HomeNoticeEntity(dataBean));
                        } else if (bean.getId().equals("currency")) {
                            homeJunZhiQEntities.add(dataBean);
                        } else if (bean.getId().equals("picturew")) {
                            homeshopSpecialEntities.add(new ShopSpecialEntity(dataBean));
                        } else {
                            Log.e(TAG, "首页数据增加了模块 id" + bean.getId());
                        }
                    }
                }
                //-----------------缓冲首页数据-------start-----------------
                SPUtils.getInstance().putSerializableEntity(Constants.SP.CACHE_HOME_BANNER_DATA, bannerData);
                SPUtils.getInstance().putSerializableEntity(Constants.SP.CACHE_HOME_MENU_DATA, homeMenuEntities);
                SPUtils.getInstance().putSerializableEntity(Constants.SP.CACHE_HOME_SHOP_DATA, homeShopEntities);
                SPUtils.getInstance().putSerializableEntity(Constants.SP.CACHE_HOME_JUNZHIQ_DATA, homeJunZhiQEntities);
                SPUtils.getInstance().putSerializableEntity(Constants.SP.CACHE_HOME_SHOPSPECIAL_DATA, homeshopSpecialEntities);
                SPUtils.getInstance().putSerializableEntity(Constants.SP.CACHE_HOME_NOTICE_DATA, homeNoticeEntities);
                //-----------------缓冲首页数据---------end-----------------
                dismissDialog();
            }

            @Override
            public void onError(String errarMessage) {
                KLog.i(TAG, "onError: " + errarMessage);
                dismissDialog();
            }
        });
    }

}
