package com.jzd.jzshop.ui.mine.mineshop.shophome;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityMineSlectBinding;
import com.jzd.jzshop.ui.base.fragment.BaseWebviewFragment;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.mine.mineshop.MineShopActivity;
import com.jzd.jzshop.ui.mine.promotion.PromotCommissionActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;

import java.net.URLEncoder;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class MineSelectActivity extends BaseActivity<ActivityMineSlectBinding,MineSelectViewModel> {


    private NavigationController build;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_mine_slect;
    }

    @Override
    public int initVariableId() {
        return BR.mineselectVM;
    }

    @Override
    public MineSelectViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MineSelectViewModel.class);
    }


    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        viewModel.initToolBar("自选商品");
        initBottomTab();
         setWebData();
    }

    private void setWebData() {
        BaseWebviewFragment baseWebviewFragment = new BaseWebviewFragment();
        String encode = URLEncoder.encode(Constants.MINE_SHOP_SELECT); //url参数转义
        String url = RetrofitClient.baseUrl + Constants.MY_ORDER_JUMP_URL + encode + "&user_token=" + viewModel.getUserToken();
        Log.d("自选商品", "goToBaseWebviewNew:" + url);
        baseWebviewFragment.setUrl(url);
        baseWebviewFragment.setTYPE("自选商品");
        replaceFragment(baseWebviewFragment);
    }

    private void replaceFragment(Fragment baseWebviewFragment) {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        // 向容器内添加或替换碎片，一般使用replace()方法实现，需要传入容器的id和待添加的碎片实例
        transaction.replace(R.id.fr_shopselect, baseWebviewFragment);  //fr_container不能为fragment布局，可使用线性布局相对布局等。
        // 使用addToBackStack()方法，将事务添加到返回栈中，填入的是用于描述返回栈的一个名字
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        build.setSelect(1);
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
                break;
            case 2:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.MINE_PROMOT,"VISIBLE");
                startActivity(PromotCommissionActivity.class,bundle);
                finish();
                break;
            case 3:
                Intent intent = new Intent(this, MineShopActivity.class);
                Intent set_shop = intent.putExtra(Constants.MINE_SET, "SET_SHOP");
                startActivityForResult(set_shop,100);
                break;

        }
        overridePendingTransition(0, 0);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
