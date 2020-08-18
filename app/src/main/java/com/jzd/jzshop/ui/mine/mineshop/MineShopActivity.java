package com.jzd.jzshop.ui.mine.mineshop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.jakewharton.rxbinding2.view.MenuItemActionViewExpandEvent;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityMineShopBinding;
import com.jzd.jzshop.entity.MineShopEntity;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.mine.mineshop.shophome.MineSelectActivity;
import com.jzd.jzshop.ui.mine.mineshop.shophome.MineShopHomeAty;
import com.jzd.jzshop.ui.mine.promotion.PromotCommissionActivity;
import com.jzd.jzshop.ui.mine.promotion.PromotionFragment;
import com.jzd.jzshop.ui.order.mineorder.adpter.MineShopAdapter;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.LocalMedias;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.ManagerFactoryParameters;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * 申请小店  (设置小店)
* */
public class MineShopActivity extends BaseActivity<ActivityMineShopBinding,MineShopViewModel> {

    private MineShopSterAdapter mineShopAdapter;
    private MineShopPersonAdapter mineShopPersonAdapter;
    private String stringExtra;
    private NavigationController build;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_mine_shop;
    }

    @Override
    public int initVariableId() {
        return BR.mineshopVM;
    }


    @Override
    public MineShopViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MineShopViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        if(getIntent()!=null){
            stringExtra = getIntent().getStringExtra(Constants.MINE_SET);
            if(!TextUtils.isEmpty(stringExtra)&& stringExtra.equals("SET_SHOP")) {
                //TODO  加载设置小店的接口
                initBottomTab();
                viewModel.requestSetWork();
                viewModel.initToolBar("设置小店");
                binding.tvApply.setText("保存设置");
            }else {
                binding.pagerBottomTab.setVisibility(View.GONE);
                viewModel.setTitleText("申请小店");
                viewModel.requestApplywork();
            }
        }else {
            binding.pagerBottomTab.setVisibility(View.GONE);
            viewModel.setTitleText("申请小店");
            viewModel.requestApplywork();
        }
        //申请小店
        viewModel.setBinding(binding,stringExtra);
        overridePendingTransition(0, 0);
        binding.rvBase.setLayoutManager(new LinearLayoutManager(this));
        binding.tvPersonal.setLayoutManager(new LinearLayoutManager(this));
        requestpermission(this);
    }


    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    public void requestpermission(Activity activity){
        if (Build.VERSION.SDK_INT >= 23) {
            int check1 = ContextCompat.checkSelfPermission(activity, permissions[0]);
            int check2 = ContextCompat.checkSelfPermission(activity, permissions[1]);
            if (check1 + check2 != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(permissions, 1);
            }
        }
    }
    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.datalist.observe(this, new Observer<MineShopEntity.ResultBean.DataBean>() {
            @Override
            public void onChanged(@Nullable MineShopEntity.ResultBean.DataBean dataBean) {
                binding.rlMineshop.setVisibility(View.VISIBLE);
                //绑定数据
                mineShopAdapter = new MineShopSterAdapter(dataBean.getBase(),MineShopActivity.this);
                binding.rvBase.setAdapter(mineShopAdapter);
                mineShopPersonAdapter = new MineShopPersonAdapter(dataBean.getPersonal(), MineShopActivity.this);
                binding.tvPersonal.setAdapter(mineShopPersonAdapter);
                viewModel.setadpater(mineShopAdapter,mineShopPersonAdapter);
            }
        });
        //设置小店时点击申请回传小店 刷新页面
        viewModel.back.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                ToastUtils.showLong("小店设置成功");
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
        if(build!=null){
            build.setSelect(3);
        }
    }

    public void initBottomTab() {
        build=  InitBottomTab.getInstance(Utils.getContext()).initShop(binding.pagerBottomTab, new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                startOtherActivity(index);
            }

            @Override
            public void onRepeat(int index) {

            }
        });
    }


    private void startOtherActivity(int index) {
        switch (index) {
            case 0:
                startActivity(MineShopHomeAty.class);
                finish();
                break;
            case 1:
                startActivity(MineSelectActivity.class);
                finish();
                break;
            case 2:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.MINE_PROMOT,"VISIBLE");
                startActivity(PromotCommissionActivity.class,bundle);
                finish();
                break;
            case 3:
                break;
        }
        overridePendingTransition(0, 0);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if(resultCode==RESULT_OK){
             switch (requestCode){
                 case PictureConfig.CHOOSE_REQUEST://基本信息
                     List<LocalMedia> loca = PictureSelector.obtainMultipleResult(data);
                     List<LocalMedias> localMedias = new ArrayList<>();
                     for (int i = 0; i < loca.size(); i++) {
                         LocalMedias httpMedia = new LocalMedias();
                         httpMedia.setPath(loca.get(i).getPath());
                         httpMedia.setChecked(loca.get(i).isChecked());
                         httpMedia.setCompressPath(loca.get(i).getCompressPath());
                         httpMedia.setCut(loca.get(i).isCut());
                         httpMedia.setCutPath(loca.get(i).getCutPath());
                         httpMedia.setDuration(loca.get(i).getDuration());
                         httpMedia.setPosition(loca.get(i).getPosition());
                         httpMedia.setCompressed(loca.get(i).isCompressed());
                         LocalMedia localMedia = loca.get(i);
                         File file = new File(localMedia.getPath());
                         httpMedia.setFile(file);
                         localMedias.add(httpMedia);
                     }
                     mineShopAdapter.addpicture(localMedias);
                     break;
                 case PictureConfig.TYPE_CAMERA://个人信息
                     List<LocalMedia> locadata = PictureSelector.obtainMultipleResult(data);
                     List<LocalMedias> local = new ArrayList<>();
                     for (int i = 0; i < locadata.size(); i++) {
                         LocalMedias httpMedia = new LocalMedias();
                         httpMedia.setPath(locadata.get(i).getPath());
                         httpMedia.setChecked(locadata.get(i).isChecked());
                         httpMedia.setCompressPath(locadata.get(i).getCompressPath());
                         httpMedia.setCut(locadata.get(i).isCut());
                         httpMedia.setCutPath(locadata.get(i).getCutPath());
                         httpMedia.setDuration(locadata.get(i).getDuration());
                         httpMedia.setPosition(locadata.get(i).getPosition());
                         httpMedia.setCompressed(locadata.get(i).isCompressed());
                         LocalMedia localMedia = locadata.get(i);
                         File file = new File(localMedia.getPath());
                         httpMedia.setFile(file);
                         local.add(httpMedia);
                     }
                     KLog.a("我传递回来的数据"+local.size());
                     mineShopPersonAdapter.addpicture(local);
                     break;
             }
         }


    }
}
