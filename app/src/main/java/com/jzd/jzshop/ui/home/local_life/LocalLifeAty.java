package com.jzd.jzshop.ui.home.local_life;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityLocalLifeBinding;
import com.jzd.jzshop.entity.LocalLifeEntity;
import com.jzd.jzshop.ui.home.local_life.location.ChoiceCityViewModel;
import com.jzd.jzshop.ui.home.local_life.search.SearchKeyWordViewModel;
import com.jzd.jzshop.ui.mine.assets.AssetsRecordPagerAdapter;
import com.jzd.jzshop.utils.third_party.AMapUtils;
import com.jzd.jzshop.utils.SystemUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by lxb on 2020/2/13.
 * 邮箱：2072301410@qq.com
 * TIP： 本地生活
 */
public class LocalLifeAty extends BaseActivity<ActivityLocalLifeBinding, LocalLifeViewModel> implements EasyPermissions.PermissionCallbacks {
    private int currentTab = 0;      //默认选中第一个tab
    private List<String> tabList;
    private List<LocalLifeEntity.ResultBean.AssetsRecordBean> dataList;
    //permission-----------start-----------
    private final static int REQUEST_CODE_FINE_LOCATION = 0;
    String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE};
    //高德定位参数-----------start-----------
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private String address;//位置
    private String position;//经纬度
    //高德定位参数-----------end-----------

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_local_life;
    }

    @Override
    public int initVariableId() {
        return BR.localLifeVM;
    }

    @Override
    public LocalLifeViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(LocalLifeViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        viewModel.setBinding(mContext, binding);
        viewModel.requestData(0);
        requestPermissions();
        Messenger.getDefault().register(this, ChoiceCityViewModel.TOKEN_VIEWMODEL_CHOICE_CITY_REFRESH,
                String.class, new BindingConsumer<String>() {
                    @Override
                    public void call(String currentCity) {//设置当前城市，刷新数据
//                        viewModel.locationName.set(currentCity);
                        binding.tvLocation.setText(currentCity);
                        // TODO: 2020/2/15  刷新当前页面数据
                    }
                });

        Messenger.getDefault().register(this, SearchKeyWordViewModel.TOKEN_VIEWMODEL_SEARCH_KEYWORD_REFRESH,
                String.class, new BindingConsumer<String>() {
                    @Override
                    public void call(String keyWord) {//设置关键词，刷新数据
//                        binding.icToolbar.etSearchShop.setText(keyWord);
                        binding.etSearchShop.setText(keyWord);
                        // TODO: 2020/2/15  刷新当前页面数据
                    }
                });

    }

    private void getIntentData() {

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<LocalLifeEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable LocalLifeEntity.ResultBean resultBean) {
                // TODO: 2020/2/13
                dataList = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    dataList.add(new LocalLifeEntity.ResultBean.AssetsRecordBean(
                            "98", 2, 3, "2020-01-19 13:56",
                            90.00, 90.00, 90.00));
                }
                mSetTabViewPagerData(dataList);
            }
        });
    }

    private void mSetTabViewPagerData(final List<LocalLifeEntity.ResultBean.AssetsRecordBean> dataList) {
        initTabLayout(binding.slidingTabs);
        setupViewPager(binding.viewpager, dataList);
        binding.slidingTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getPosition();
                Log.d(TAG, "onTabSelected:-->>" + currentTab);
                binding.viewpager.setCurrentItem(currentTab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initTabLayout(TabLayout tabLayout) {
        tabList = Arrays.asList(getResources().getStringArray(R.array.local_life_tab_txt));
        for (int i = 0; i < tabList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabList.get(i)));
        }
        tabLayout.setupWithViewPager(binding.viewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabRippleColor(ColorStateList.valueOf(mContext.getResources().getColor(R.color.white)));
    }

    private void setupViewPager(ViewPager viewpager, List<LocalLifeEntity.ResultBean.AssetsRecordBean> dataList) {
        AssetsRecordPagerAdapter assetsRecordPagerAdapter = new AssetsRecordPagerAdapter(getSupportFragmentManager());

        assetsRecordPagerAdapter.addFragment(new OfflineFoodFragment().newInstance(dataList), tabList.get(0));
        assetsRecordPagerAdapter.addFragment(new OnlineFoodFragment().newInstance(dataList), tabList.get(1));
        viewpager.setAdapter(assetsRecordPagerAdapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyLocation();//销毁定位
        if (locationClient != null) {
            locationClient.stopLocation();  // 停止定位
        }
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }


    public void requestPermissions() {
        if (EasyPermissions.hasPermissions(this, perms)) {
            SystemUtils.showSystemParameter(this);//打印手机系统参数
            mStartLocation();
        } else {
            EasyPermissions.requestPermissions(this,
                    "定位需要如下权限：\n" +
                            "\n\t\t需要您的位置权限" +
                            "\n\t\t需要使用电话权限",
                    REQUEST_CODE_FINE_LOCATION,
                    perms);

        }
    }

    /**
     * 开启定位
     */
    private void mStartLocation() {
        initLocation(); //初始化定位
        startLocation();//开始定位
    }

    private void startLocation() {
        locationClient.startLocation(); // 启动定位
    }

    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(getApplication());
        locationOption = getDefaultOption();
        locationClient.setLocationOption(locationOption);   //设置定位参数
        locationClient.setLocationListener(locationListener);     // 设置定位监听
    }


    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    Log.d(TAG, "定位成功:\n");
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: " + location.getLocationType() + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    sb.append("提供者    : " + location.getProvider() + "\n");

                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : " + location.getSatellites() + "\n");
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省            : " + location.getProvince() + "\n");
                    sb.append("市            : " + location.getCity() + "\n");
                    sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append("区            : " + location.getDistrict() + "\n");
                    sb.append("区域 码   : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    //定位完成的时间
                    sb.append("定位时间: " + AMapUtils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                    address = location.getCity() + " " /*+ location.getDistrict()*/;
                    viewModel.saveCity(address);
                    if (!TextUtils.isEmpty(address)) {
//                        locationName.set(address);
                        binding.tvLocation.setText(address);
                    } else {
                        binding.tvLocation.setText("北京");
                    }
                    position = location.getLongitude() + "," + location.getLatitude();
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                }
                sb.append("***定位质量报告***").append("\n");
                sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启" : "关闭").append("\n");
                sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
                sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
                sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
                sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
                sb.append("****************").append("\n");
                //定位之后的回调时间
                sb.append("回调时间: " + AMapUtils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");
                //解析定位结果
                String result = sb.toString();
                Log.d(TAG, "解析定位结果:\n" + result);
            } else {
                Log.e(TAG, "定位失败，loc is null");
            }
        }
    };

    /**
     * 获取GPS状态的字符串
     *
     * @param statusCode GPS状态码
     * @return
     */
    private String getGPSStatusString(int statusCode) {
        String str = "";
        switch (statusCode) {
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {//已授权
        mStartLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {//如果拒绝，提示
        Log.d(TAG, "onPermissionsDenied 您拒绝了" + perms + "权限");
        requestPermissions();
        Toast.makeText(this, "您拒绝了" + perms + "权限", Toast.LENGTH_SHORT).show();
        StringBuffer sb = new StringBuffer();    //处理权限名字字符串
        for (String str : perms) {
            sb.append(str);
            sb.append("\n");
        }
        sb.replace(sb.length() - 2, sb.length(), "");
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            if (EasyPermissions.hasPermissions(this, this.perms)) {
            } else {
                Log.d(TAG, " somePermissionPermanentlyDenied 已拒绝权限" + sb + "并不再询问");
                new AppSettingsDialog
                        .Builder(this)
                        .setRationale("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置")
                        .setPositiveButton("是")
                        .setNegativeButton("否")
                        .build()
                        .show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
