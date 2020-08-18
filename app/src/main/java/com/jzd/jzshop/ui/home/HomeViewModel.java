package com.jzd.jzshop.ui.home;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityHomeBinding;
import com.jzd.jzshop.entity.AppUpdateEntity;
import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.entity.HomeEntity;
import com.jzd.jzshop.ui.home.merchantalliance.MerchantAllianceFragment;
import com.jzd.jzshop.ui.base.fragment.BaseWebviewFragment;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.NetworkUtils;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;
import com.jzd.jzshop.utils.dialog.Dialog4AppUpdate;
import com.jzd.jzshop.utils.dialog.DialogAppForceUpdate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class HomeViewModel extends BaseViewModel<Repository> {
    private static final String TAG = HomeViewModel.class.getSimpleName();

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public ObservableField<List<HomeEntity.ResultBean.DataBeanX>> homedata = new ObservableField<>();
//    public ObservableField<HomeBtnEntity> homebtn = new ObservableField<>();

    public class UIChangeObservable {
        //更新xbanner数据
        public SingleLiveEvent<List<BannEntity>> xbannerRefreshing = new SingleLiveEvent<>();
        public SingleLiveEvent<AppUpdateEntity> appUpdate = new SingleLiveEvent<>();
    }

    private ActivityHomeBinding binding;
    public ObservableList<HomeProductItemViewModel> productlist = new ObservableArrayList<>();
    public ObservableList<HomeEntity.ResultBean> home =new ObservableArrayList<>();
//    public ItemBinding<HomeProductItemViewModel> product = ItemBinding.of(com.jzd.jzshop.BR.productVM, R.layout.item_home_shop);

    public HomeViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    private Context context;

    public void setBinding(Context context, ActivityHomeBinding binding) {
        this.binding = binding;
        this.context = context;
    }

    public String getUserToken(){
        return model.getUserToken();
    }

    @SuppressLint("CheckResult")
    public void requestHomeDataNetWork(final SmartRefreshLayout refreshLayout) {
        //可以调用addSubscribe()添加Disposable，请求与View周期同步
        isShowDialog(false);
        addSubscribe(model.postHomeData(), new ParseDisposableObserver<HomeEntity>() {
            @Override
            public void onResult(HomeEntity entity) {
                productlist.clear();
                homedata.set(null);
                home.clear();
                home.addAll(entity.getResult());



                ArrayList<BannEntity> bannerData = new ArrayList<>();
                bannerData.clear();
                for (HomeEntity.ResultBean bean : entity.getResult()) {
                    for (HomeEntity.ResultBean.DataBeanX dataBean : bean.getData()) {
                        if (bean.getId().equals("goods")) {
                            HomeProductItemViewModel homeProductItemViewModel = new HomeProductItemViewModel(HomeViewModel.this, dataBean);
                            productlist.add(homeProductItemViewModel);
                        } else if (bean.getId().equals("banner")) {
                            bannerData.add(new BannEntity(dataBean.getImgurl(), dataBean.getLinkurl()));
                        } else if (bean.getId().equals("menu")) {
                            homedata.set(bean.getData());
//                            List<HomeEntity.ResultBean.DataBean> dataBeans = homedata.get();
//                            HomeBtnEntity homeBtnEntity = new HomeBtnEntity();
//                            homeBtnEntity.setCreditshop(dataBeans.get(0).getText());
////                            homeBtnEntity.setCreditshopImg(dataBeans.get(0).getImgurl());
//                            Glide.with(getApplication()).load(dataBeans.get(0).getImgurl()).into(binding.imageView);
//                            homeBtnEntity.setCreditshopUrl(dataBeans.get(0).getLinkurl());
//                            homeBtnEntity.setSeckill(dataBeans.get(1).getText());
////                            homeBtnEntity.setSeckillImg(dataBeans.get(1).getImgurl());
//                            Glide.with(getApplication()).load(dataBeans.get(1).getImgurl()).into(binding.imageView4);
//                            homeBtnEntity.setSeckillUrl(dataBeans.get(1).getLinkurl());
//                            homeBtnEntity.setSns(dataBeans.get(2).getText());
////                            homeBtnEntity.setSnsImg(dataBeans.get(2).getImgurl());
//                            Glide.with(getApplication()).load(dataBeans.get(2).getImgurl()).into(binding.imageView2);
//                            homeBtnEntity.setSnsUrl(dataBeans.get(2).getLinkurl());
//                            homeBtnEntity.setMerch(dataBeans.get(3).getText());
////                            homeBtnEntity.setMerchImg(dataBeans.get(3).getImgurl());
//                            Glide.with(getApplication()).load(dataBeans.get(3).getImgurl()).into(binding.imageView3);
//                            homeBtnEntity.setMerchUrl(dataBeans.get(3).getLinkurl());
//                            homebtn.set(homeBtnEntity);
                        }
                    }
                }
                if (bannerData.size() != 0) {
//                    setBanners(bannerData);
                    uc.xbannerRefreshing.setValue(bannerData);
                }
                dismissDialog();
                refreshLayout.finishRefresh();
            }

            @Override
            public void onError(String errarMessage) {
                KLog.i(TAG, "onError: " + errarMessage);
                dismissDialog();
                refreshLayout.finishRefresh();
            }
        });
    }


    public HomeEntity.ResultBean.DataBeanX getImageUrl(int index) {
        if (homedata.get() != null && homedata.get().size() != 0) {
            List<HomeEntity.ResultBean.DataBeanX> dataBeans = homedata.get();
            HomeEntity.ResultBean.DataBeanX dataBean = dataBeans.get(index);
            return dataBean;
        } else {
            KLog.a("所有的事物");
            return null;
        }
    }

    public void getLinkUrl(int index) {
        int netState = NetworkUtils.getNetWorkState(context);
        if (netState == -1) {
            ToastUtils.showLong("网络连接异常");
            return;
        }
        Bundle bundle = new Bundle();
        if (homedata.get() != null && !TextUtils.isEmpty(model.getUserToken())) {
            String url = homedata.get().get(index).getLinkurl() + model.getUserToken();
            Log.d(TAG, "积分商城url：" + url);
            Log.d(TAG, "userToken：" + model.getUserToken());
            bundle.putString(Constants.BUNDLE_KEY_URL, homedata.get().get(index).getLinkurl() + model.getUserToken());
            startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
        } else {
            ToastUtils.showLong("请登录！！");
            return;
        }

    }

    //积分商城
    public BindingCommand onintegralStoreClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getLinkUrl(0);
        }
    });


    //秒杀
    public BindingCommand onseckillClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getLinkUrl(1);
        }
    });

    //全名社区
    public BindingCommand onallCommuntiyClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getLinkUrl(2);
        }
    });

    //商家联盟
    public BindingCommand onstoreAllianceClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            int netState = NetworkUtils.getNetWorkState(context);
            if (netState == -1) {
                ToastUtils.showLong("网络连接异常");
                return;
            }
            if (homedata.get() != null && !TextUtils.isEmpty(model.getUserToken())) {
                startContainerActivity(MerchantAllianceFragment.class.getCanonicalName());
            } else {
                ToastUtils.showLong("请登录！！");
                return;
            }
        }
    });


    public void toAppUpdate(final String version) {
        isShowDialog(false);
        addSubscribe(model.postAppUpdate(version), new ParseDisposableObserver<AppUpdateEntity>() {
            @Override
            public void onResult(final AppUpdateEntity appUpdateEntity) {
                Log.d(TAG, "toAppUpdate onResult:");
                if (appUpdateEntity != null) {
                    if (appUpdateEntity.getResult() != null) {
                        int status = appUpdateEntity.getResult().getStatus();//是否强更
                        if (appUpdateEntity.getResult().getVersion() != null && appUpdateEntity.getResult().getDesc() != null
                                && appUpdateEntity.getResult().getSize() != null) {
                            String newVersion = appUpdateEntity.getResult().getVersion();
                            String size = appUpdateEntity.getResult().getSize();
                            String depict = appUpdateEntity.getResult().getDesc();
                            if (status == 0) {// 0 无需更新

                            } else if (status == 1) {//不强制更新
                                showNormalUpdateDialog(appUpdateEntity, newVersion, size, depict, version);
                            } else if (status == 2) {  //2 强制更新
                                DialogAppForceUpdate dialog = new DialogAppForceUpdate(context, "当前版本V" + version
                                        + ",是否升级到版本V" + newVersion + "?新版本大小:" + size, "新版本功能 \n" + depict.replace("\\n","\n"));
                                dialog.setCancelEnable(false);
                                dialog.setListener(new DialogAppForceUpdate.onClickSureBtnListener() {
                                    @Override
                                    public void onClickSure() {
                                        uc.appUpdate.setValue(appUpdateEntity);
                                    }
                                });
                                if (!((HomeActivity) context).isFinishing()) {
                                    dialog.show();
                                }
                            }
                        }
                    }
                }

            }

            @Override
            public void onError(String errarMessage) {
                Log.d(TAG, "toAppUpdate onError:" + errarMessage);
            }
        });

    }

    private void showNormalUpdateDialog(final AppUpdateEntity appUpdateEntity, String newVersion, String size, String depict, String version) {
        final Dialog4AppUpdate dialog = new Dialog4AppUpdate(context, "当前版本V" + version
                + ",是否升级到版本V" + newVersion + "?新版本大小:" + size, "新版本功能 \n" + depict.replace("\\n","\n"));
        dialog.setCancelEnable(false);
        dialog.setListener(new Dialog4AppUpdate.onClickSureBtnListener() {
            @Override
            public void onClickSure() {
                uc.appUpdate.setValue(appUpdateEntity);
                dialog.dismiss();
            }

            @Override
            public void onClickCancel() {
                if (!((HomeActivity) context).isFinishing()) {
                    dialog.dismiss();
                }
            }
        });
        //防止activity 已销毁，show 报错
        if (!((HomeActivity) context).isFinishing()) {
            dialog.show();
        }
    }


}
