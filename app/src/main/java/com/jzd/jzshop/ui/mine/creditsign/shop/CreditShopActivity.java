package com.jzd.jzshop.ui.mine.creditsign.shop;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityCreditShopBinding;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.base.fragment.BaseWebviewFragment;
import com.jzd.jzshop.ui.mine.creditsign.CreditSignNewActivity;
import com.jzd.jzshop.ui.mine.creditsign.ranking.SignRankingActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;

import java.net.URLEncoder;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * @author LXB
 * @description: 积分商城 (暂时用fragment 打开h5 ，后期改为原生直接去掉即可)
 * @date :2020/4/10 9:27
 */
@Deprecated
public class CreditShopActivity extends BaseActivity<ActivityCreditShopBinding, CreditShopViewModel> {
    private NavigationController build;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_credit_shop;
    }

    @Override
    public int initVariableId() {
        return BR.creditShopVM;
    }

    @Override
    public CreditShopViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(CreditShopViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        build.setSelect(2);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        viewModel.setBinding(mContext, binding);
        viewModel.initToolBar(getResources().getString(R.string.credit_shop));
        initBottomTab();
    }

    private void getIntentData() {
        BaseWebviewFragment baseWebviewFragment = new BaseWebviewFragment();
        String encode = URLEncoder.encode(Constants.CREDIT_SHOP); //url参数转义
        String url = RetrofitClient.baseUrl + Constants.MY_ORDER_JUMP_URL + encode + "&user_token=" + viewModel.getUserToken();
        Log.d(TAG, "goToBaseWebviewNew:" + url);
        baseWebviewFragment.setUrl(url);
        baseWebviewFragment.setTYPE("积分商城");
        replaceFragment(baseWebviewFragment);
    }

    /**
     * 动态启动fragment
     *
     * @param fragment
     */
    private void replaceFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        // 向容器内添加或替换碎片，一般使用replace()方法实现，需要传入容器的id和待添加的碎片实例
        transaction.replace(R.id.fr_container, fragment);  //fr_container不能为fragment布局，可使用线性布局相对布局等。
        // 使用addToBackStack()方法，将事务添加到返回栈中，填入的是用于描述返回栈的一个名字
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

    }

    public void initBottomTab() {
        build = InitBottomTab.getInstance(Utils.getContext()).initCreditSign(binding.pagerBottomTab, new OnTabItemSelectedListener() {
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
                startActivity(CreditSignNewActivity.class);
                finish();
                break;
            case 1:
                startActivity(SignRankingActivity.class);
                finish();
                break;
            case 2:
                break;
        }
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        build = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
