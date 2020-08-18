package com.jzd.jzshop.ui.mine.assets;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentAssetsIntroductionBinding;
import com.jzd.jzshop.entity.AssetsRecordEntity;
import com.jzd.jzshop.ui.goods.goodslist.GoodsListViewModel;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

/**
 * Created by lxb on 2020/2/11.
 * 邮箱：2072301410@qq.com
 * TIP： 君子权介绍
 */
public class AssetsIntroductionFragment extends BaseFragment<FragmentAssetsIntroductionBinding,AssetsIntroductionViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_assets_introduction;
    }

    @Override
    public int initVariableId() {
        return BR.assetsIntroductionVM;
    }

    @Override
    public AssetsIntroductionViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(AssetsIntroductionViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);
        getIntentData();
        viewModel.setBinding(getActivity(), binding);
        viewModel.initToolBar("");
        viewModel.requestData();
    }

    private void getIntentData() {

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

    }
}
