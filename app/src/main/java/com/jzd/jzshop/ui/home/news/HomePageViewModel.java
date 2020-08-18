package com.jzd.jzshop.ui.home.news;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.tu.loadingdialog.LoadingDailog;
import com.google.gson.Gson;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityHomeNewBinding;
import com.jzd.jzshop.entity.AppUpdateEntity;
import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.entity.HomeBannerEntity;
import com.jzd.jzshop.entity.HomeEntity;
import com.jzd.jzshop.entity.HomeGoodsEntity;
import com.jzd.jzshop.entity.HomeMenuEntity;
import com.jzd.jzshop.entity.HomeNoticeEntity;
import com.jzd.jzshop.entity.MessageCenterEntity;
import com.jzd.jzshop.entity.ShopSpecialEntity;
import com.jzd.jzshop.ui.base.fragment.BaseWebviewFragment;
import com.jzd.jzshop.ui.home.merchantalliance.MerchantAllianceFragment;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.NetworkUtils;
import com.jzd.jzshop.utils.SystemUtils;
import com.jzd.jzshop.utils.dialog.Dialog4AppUpdate;
import com.jzd.jzshop.utils.dialog.DialogAppForceUpdate;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description:
 * @date :2020/1/4 15:47
 */
public class HomePageViewModel extends BaseViewModel<Repository> {
    private static final String TAG = HomePageViewModel.class.getSimpleName();
    private HomePagerRecycleAdapter mAdapter;
    private Context context;
    private ActivityHomeNewBinding binding;
    public int pagesize = 10; //默认每页页数
    public static final String TOKEN_VIEWMODEL_HOME_MESSAGENUMBER_REFRESH = "token_viewmodel_home_messagenumber_refresh";
    private LoadingDailog customPd;
    private SmartRefreshLayout mRefreshLayout;
    private List<HomeGoodsEntity.ResultBean.DataBean> totalList = new ArrayList();

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    public ObservableField<List<HomeEntity.ResultBean.DataBeanX>> homedata = new ObservableField<>();
//    public ObservableField<HomeBtnEntity> homebtn = new ObservableField<>();
    public class UIChangeObservable {
        public SingleLiveEvent<List<BannEntity>> xbannerRefreshing = new SingleLiveEvent<>(); //更新xbanner数据
        public SingleLiveEvent<AppUpdateEntity> appUpdate = new SingleLiveEvent<>();
        public SingleLiveEvent<MessageCenterEntity.ResultBean> mMessageLiveData = new SingleLiveEvent<>();
        //商品数据集
        public SingleLiveEvent<HomeGoodsEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent<List<HomeGoodsEntity.ResultBean.DataBean>> mAllLiveData = new SingleLiveEvent<>();

    }

    public HomePageViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context context, ActivityHomeNewBinding binding, HomePagerRecycleAdapter homeMenuAdapter) {
        this.binding = binding;
        this.context = context;
        this.mAdapter = homeMenuAdapter;
    }

    public String getUserToken() {
        return model.getUserToken();
    }


    @SuppressLint("CheckResult")
    public void requestHomeDataNetWork(final SmartRefreshLayout refreshLayout) {
        isShowDialog(false);
//        showDialog("数据初始化中...，请耐心等待");
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
                ArrayList<HomeBannerEntity> banner = new ArrayList<>();
                banner.add(new HomeBannerEntity(bannerData));
                KLog.a("Menu的数据有==>>>" + homeMenuEntities.size());
                //-----------------装载数据到adapter-------start-----------------
                mAdapter.addList(banner, homeMenuEntities, homeShopEntities, homeJunZhiQEntities, homeshopSpecialEntities, homeNoticeEntities);
                mAdapter.notifyDataSetChanged();
                //-----------------装载数据到adapter---------end-----------------
                /*if (bannerData.size() != 0) {
                    uc.xbannerRefreshing.setValue(bannerData);
                }*/
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
            SystemUtils.openActivityByAnimation((HomePageActivity) context, context, LoginVertifyActivity.class);
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
                SystemUtils.openActivityByAnimation((HomePageActivity) context, context, LoginVertifyActivity.class);
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
                                        + ",是否升级到版本V" + newVersion + "?新版本大小:" + size, "新版本功能 \n" + depict.replace("\\n", "\n"));
                                dialog.setCancelEnable(false);
                                dialog.setListener(new DialogAppForceUpdate.onClickSureBtnListener() {
                                    @Override
                                    public void onClickSure() {
                                        uc.appUpdate.setValue(appUpdateEntity);
                                    }
                                });
                                if (!((HomePageActivity) context).isFinishing()) {
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
                + ",是否升级到版本V" + newVersion + "?新版本大小:" + size, "新版本功能 \n" + depict.replace("\\n", "\n"));
        dialog.setCancelEnable(false);
        dialog.setListener(new Dialog4AppUpdate.onClickSureBtnListener() {
            @Override
            public void onClickSure() {
                uc.appUpdate.setValue(appUpdateEntity);
                dialog.dismiss();
            }

            @Override
            public void onClickCancel() {
                if (!((HomePageActivity) context).isFinishing()) {
                    dialog.dismiss();
                }
            }
        });
        //防止activity 已销毁，show 报错
        if (!((HomePageActivity) context).isFinishing()) {
            dialog.show();
        }
    }

    /**
     * 获取首页 消息总数
     *
     * @param page
     */
    public void requestMessageData(final int page) {
        isShowDialog(false);
        addSubscribe(model.postMessageData(model.getUserToken(), page, pagesize), new ParseDisposableTokenErrorObserver<MessageCenterEntity>() {
            @Override
            public void onResult(MessageCenterEntity messageCenterEntity) {
                super.onResult(messageCenterEntity);
                Log.d(TAG, "requestMessageData onSuccess:---->>>");
                if (messageCenterEntity != null) {
                    if (messageCenterEntity.getResult() != null) {
                        uc.mMessageLiveData.setValue(messageCenterEntity.getResult());
                    }
                }
                dismissDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                Log.d(TAG, "onError:---->>>" + throwable.getMessage());
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
            }
        });
    }


    /**
     * 缓冲首页数据
     */
    public void cacheHomeData() {
        String json_home_data = SPUtils.getInstance().getString("json_home_data");
        KLog.json(TAG, "缓冲的首页数据 josn ------>>>>" + json_home_data);
        if (!TextUtils.isEmpty(json_home_data)) {
            Gson gson = new Gson();
            HomeEntity homeEntity = gson.fromJson(json_home_data, HomeEntity.class);

            ArrayList<BannEntity> bannerData = new ArrayList<>();
            bannerData.clear();
            ArrayList<HomeBannerEntity> bannlist = new ArrayList<>();
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
            for (HomeEntity.ResultBean bean : homeEntity.getResult()) {
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
            bannlist.add(new HomeBannerEntity(bannerData));
            //-----------------缓冲首页数据-------start-----------------
            SPUtils.getInstance().putSerializableEntity(Constants.SP.CACHE_HOME_BANNER_DATA, bannerData);
            SPUtils.getInstance().putSerializableEntity(Constants.SP.CACHE_HOME_MENU_DATA, homeMenuEntities);
            SPUtils.getInstance().putSerializableEntity(Constants.SP.CACHE_HOME_SHOP_DATA, homeShopEntities);
            SPUtils.getInstance().putSerializableEntity(Constants.SP.CACHE_HOME_JUNZHIQ_DATA, homeJunZhiQEntities);
            SPUtils.getInstance().putSerializableEntity(Constants.SP.CACHE_HOME_SHOPSPECIAL_DATA, homeshopSpecialEntities);
            SPUtils.getInstance().putSerializableEntity(Constants.SP.CACHE_HOME_NOTICE_DATA, homeNoticeEntities);
            //-----------------缓冲首页数据---------end-----------------
            //-----------------装载数据到adapter-------start-----------------
            mAdapter.addList(bannlist, homeMenuEntities, homeShopEntities, homeJunZhiQEntities, homeshopSpecialEntities, homeNoticeEntities);
            mAdapter.notifyDataSetChanged();
            //-----------------装载数据到adapter---------end-----------------
        }

    }

    /**
     *  商品列表
     * @param refreshLayout
     * @param page
     * @param pagesize
     * @param mbinding
     */
    public void requestHomeShopData(SmartRefreshLayout refreshLayout, int page, int pagesize, boolean isRefresh, ActivityHomeNewBinding mbinding) {
        isShowDialog(false);
        mRefreshLayout = refreshLayout;
        this.binding = mbinding;
        addSubscribe(model.postHomeGoodsList(page, pagesize),
                new ParseDisposableTokenErrorObserver<HomeGoodsEntity>() {
                    @Override
                    public void onResult(HomeGoodsEntity homeGoodsEntity) {
                        super.onResult(homeGoodsEntity);
                        Log.d(TAG, "onSuccess:---->>>");
                        if (homeGoodsEntity != null) {
                            if (homeGoodsEntity.getResult() != null) {
                                uc.mLiveData.setValue(homeGoodsEntity.getResult());
                            }
                            List<HomeGoodsEntity.ResultBean.DataBean> data = homeGoodsEntity.getResult().getData();
                            if (data != null && data.size() > 0) {
                                if (page == 1) {
                                    totalList.clear();
                                    totalList.addAll(data);
                                    uc.mAllLiveData.setValue(totalList);
                                } else {
                                }
                            } else if (homeGoodsEntity.getResult().getData() == null ||
                                    homeGoodsEntity.getResult().getData().size() == 0) {//空页面处理
                                mBackResultNull(homeGoodsEntity.getResult(), page);
                                mRefreshLayout.setEnableRefresh(false);
                                mRefreshLayout.setEnableAutoLoadmore(false);
                                mRefreshLayout.setEnableLoadmore(false);
                                return;
                            } else {
                                mBackResultNull(homeGoodsEntity.getResult(), page);
                                return;
                            }
                            if (isRefresh && mRefreshLayout != null) { //刷新
                                mRefreshLayout.finishRefresh();
                                mRefreshLayout.setLoadmoreFinished(false);
                            } else if (mRefreshLayout != null) {  //加载更多
                                if (homeGoodsEntity.getResult().getData() != null && homeGoodsEntity.getResult().getData().size() == 0) {
                                    totalList.clear();
                                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                                }
                                totalList.addAll(homeGoodsEntity.getResult().getData());
                                mRefreshLayout.finishLoadmore();
                                uc.mAllLiveData.setValue(totalList);
                            }
                            int currentCountSize = homeGoodsEntity.getResult().getData().size();
                            if (currentCountSize < 10 || currentCountSize == 0) {
                                if (mRefreshLayout != null) {
                                    mRefreshLayout.finishLoadmoreWithNoMoreData();    //加载数据单次小于10条，关闭加载更多
                                }
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
                        refreshLayout.finishLoadmore();
                        dismissDialog();
//                        loadError(isRefresh);
                    }
                });
    }

    /**
     * 接口返回数据为null  或者 空[] 处理
     *
     * @param resultBean
     * @param page
     */
    private void mBackResultNull(HomeGoodsEntity.ResultBean resultBean, int page) {
        if (page == 1 && resultBean.getData().size() == 0) {
            binding.rcyShop.setVisibility(View.GONE);
//            mBinding.emptyView.setVisibility(View.VISIBLE);
        } else {
            binding.rcyShop.setVisibility(View.VISIBLE);
//            mBinding.emptyView.setVisibility(View.GONE);
        }
        mRefreshLayout.finishLoadmore(true);
        mRefreshLayout.finishLoadmoreWithNoMoreData();
        dismissDialog();
        return;
    }


    /**
     * 网络请求，加载失败
     */
    public void loadError(boolean isRefresh) {
        if (mRefreshLayout != null) {
            if (isRefresh) {
                mRefreshLayout.finishRefresh(false);  //结束刷新（刷新失败）
                mRefreshLayout.setLoadmoreFinished(false);
            } else {
                mRefreshLayout.finishLoadmore(false); //结束加载（加载失败）
            }
        }
    }

    //一键置顶
    public BindingCommand onClickGoTop = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            binding.rcyShop.post(new Runnable() {
                @Override
                public void run() {
                    binding.rcyShop.scrollToPosition(0);
                }
            });
        }
    });

}
