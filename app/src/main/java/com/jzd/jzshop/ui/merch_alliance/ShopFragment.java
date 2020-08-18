package com.jzd.jzshop.ui.merch_alliance;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentShopBinding;
import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.entity.ShopEntity;
import com.jzd.jzshop.ui.home.AppIdentityJumpUtils;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.KeyboardUtils;
import com.stx.xhb.xbanner.XBanner;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;


/**
 * A simple {@link Fragment} subclass.
 * <p>
 * 商家联盟 ———》》》 专卖店铺
 */
public class ShopFragment extends BaseFragment<FragmentShopBinding, ShopViewModel> implements XBanner.OnItemClickListener {

    private static final String TAG = ShopFragment.class.getSimpleName();
    private FragmentActivity activity;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_shop;
    }

    @Override
    public int initVariableId() {
        return BR.shopVM;
    }

    @Override
    public ShopViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(ShopViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        getIntentData();
        activity = getActivity();
        mKeywordSearch();
    }


    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.xbannerRefreshing.observe(this, new Observer<List<BannEntity>>() {
            @Override
            public void onChanged(@Nullable List<BannEntity> bannEntities) {
                binding.emptyView.setVisibility(View.GONE);
                if (bannEntities != null && bannEntities.size() > 0) {
                    binding.xbanner.setVisibility(View.VISIBLE);
                    binding.xbanner.setBannerData(bannEntities);
                    binding.xbanner.loadImage(new XBanner.XBannerAdapter() {
                        @Override
                        public void loadBanner(XBanner banner, Object model, View view, int position) {
                            BannEntity ban = (BannEntity) model;
                            Glide.with(getActivity().getApplication()).load(ban.getXBannerUrl()).into((ImageView) view);
                        }
                    });
                    binding.xbanner.startAutoPlay();
                }
            }
        });
        binding.xbanner.setOnItemClickListener(this);

        viewModel.uc.noDataUi.observe(this, new Observer<List<ShopEntity.ResultBean>>() {
            @Override
            public void onChanged(@Nullable List<ShopEntity.ResultBean> resultBeans) {
                binding.emptyView.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
                binding.xbanner.setVisibility(View.GONE);
            }
        });

        viewModel.uc.nobann.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                binding.xbanner.setVisibility(View.GONE);
            }
        });
    }

    private void getIntentData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            String shopid = arguments.getString(Constants.MINE_SHOP);
            KLog.d("SHOP", shopid);
            String title = arguments.getString("title");
            viewModel.setShopid(shopid);
            viewModel.requestWork(shopid);
            if (title != null && !TextUtils.isEmpty(title)) {
                viewModel.title.set(title);
                viewModel.initToolBar(title);
            } else {
                viewModel.title.set(getResources().getString(R.string.shopName));
            }
        }
    }

    private void mKeywordSearch() {
        binding.etSearchShop.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    KeyboardUtils.hideKeyboard(binding.rlSearch);//点击搜索的时候隐藏软键盘
                    String keyWord = binding.etSearchShop.getText().toString().trim();
                    // TODO: 2019/12/26  需求变动,暂时去掉
//                    viewModel.requestWork(shopid,keyWord);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(XBanner banner, Object model, View view, int position) {
        BannEntity data = (BannEntity) model;
        Bundle bundle = new Bundle();
        if (data != null) {
            String linkurl = data.getUrl();
            Log.d(TAG, "linkUrl：" + linkurl);
            AppIdentityJumpUtils.homeMenujumpLinkUrl(linkurl,viewModel,mActivity);
        }
    }

}
