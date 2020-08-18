package com.jzd.jzshop.ui.mine.promotion;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityPromotCommissionBinding;
import com.jzd.jzshop.entity.PromotCommissionEntity;
import com.jzd.jzshop.entity.SignRecordEntity;
import com.jzd.jzshop.entity.bean.PromotCommissionBean;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.mine.mineshop.MineShopActivity;
import com.jzd.jzshop.ui.mine.mineshop.shophome.MineSelectActivity;
import com.jzd.jzshop.ui.mine.mineshop.shophome.MineShopHomeAty;
import com.jzd.jzshop.ui.mine.mineteam.MineTeamActivity;
import com.jzd.jzshop.ui.mine.promotion.business.AttractInvestmentActivity;
import com.jzd.jzshop.ui.mine.promotion.promotion_order.PromotionOrderNewAty;
import com.jzd.jzshop.utils.Constants;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * @author LXB
 * @description: 推广佣金
 * @date :2020/4/11 17:20
 */
public class PromotCommissionActivity extends BaseActivity<ActivityPromotCommissionBinding, PromotCommissionViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_promot_commission;
    }
    private NavigationController build;

    @Override
    public int initVariableId() {
        return BR.promotCommissionVM;
    }

    @Override
    public PromotCommissionViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(PromotCommissionViewModel.class);

    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        viewModel.setBinding(mContext, binding);
        viewModel.initToolBar(getResources().getString(R.string.promot_commission));
        viewModel.requestData();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if(intent!=null){
            String stringExtra = intent.getStringExtra(Constants.MINE_PROMOT);
            if(!TextUtils.isEmpty(stringExtra)&&stringExtra.equals("VISIBLE")){//显示导航栏
                initBottomTab();
                binding.pagerBottomTab.setVisibility(View.VISIBLE);
            }else {
                binding.pagerBottomTab.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<PromotCommissionEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable final PromotCommissionEntity.ResultBean result) {
                List dataList = new ArrayList<PromotCommissionBean>();
                List<String> listTitle = Arrays.asList(getResources().getStringArray(R.array.promot_commission_txt));
                List<Integer> listImage = Arrays.asList(R.mipmap.ic_promo_commission, R.mipmap.ic_promo_myshop,
                        R.mipmap.ic_promo_dividend, R.mipmap.ic_promo_merch);
                for (int i = 0; i <listImage.size(); i++) {
                    if (i ==0){
                        dataList.add(new PromotCommissionBean(listImage.get(i),listTitle.get(i),result.getCommission()) );
                    }else if (i == 1){
                        dataList.add(new PromotCommissionBean(listImage.get(i),listTitle.get(i),result.getMyshop()) );
                    }else if (i == 2){
                        dataList.add(new PromotCommissionBean(listImage.get(i),listTitle.get(i),result.getDividend()) );
                    }else if (i ==3){
                        dataList.add(new PromotCommissionBean(listImage.get(i),listTitle.get(i),result.getMerch()) );
                    }else {}
                }
                PromotCommissionAdapter promotCommissionAdapter = new PromotCommissionAdapter(mContext, dataList, R.layout.item_rv_promot_commission);
                binding.rvc.setAdapter(promotCommissionAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                binding.rvc.setLayoutManager(linearLayoutManager);
                promotCommissionAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int viewType, int position) {
                        if (position == 0) { //推广
                            startActivity(PromotionOrderNewAty.class);
                        } else if (position == dataList.size() - 1) { //招商
                            startActivity(AttractInvestmentActivity.class);
                        }else if(position == dataList.size()-2){//团队
                            startActivity(MineTeamActivity.class);
                        }
                    }
                });
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(build!=null){
            build.setSelect(2);
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
                break;
            case 3:
                Intent intent = new Intent(this, MineShopActivity.class);
                Intent set_shop = intent.putExtra(Constants.MINE_SET, "SET_SHOP");
                startActivityForResult(set_shop,100);
                break;

        }
        overridePendingTransition(0, 0);
    }

}
