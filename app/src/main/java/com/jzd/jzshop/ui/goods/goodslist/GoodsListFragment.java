package com.jzd.jzshop.ui.goods.goodslist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.FragmentGoodsListBinding;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.CustomDividerItemDecoration;
import com.jzd.jzshop.utils.KeyboardUtils;
import com.jzd.jzshop.utils.SystemUtils;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;


/**
 * 搜索商品
 */
public class GoodsListFragment extends BaseFragment<FragmentGoodsListBinding, GoodsListViewModel> {
    private NavigationController build;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_goods_list;
    }

    @Override
    public int initVariableId() {
        return com.jzd.jzshop.BR.goodsListVM;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarDarkTheme(getActivity(),false);
        StatusBarUtil.setStatusBarColor(getActivity(), Color.parseColor("#D90804"));
        viewModel.setBinding(binding,getActivity());
        viewModel.initToolBar((mActivity.getResources().getString(R.string.search_shop_list)));
        mKeywordSearch();
        getIntentData();
        setRecycleDiverLine(false);
        initMallRefresh();
    }

    private void getIntentData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String category_id = bundle.getString(Constants.BUNDLE_KEY_CATEGORY_ID);
            CharSequence keywords = bundle.getString(Constants.BUNDLE_KEY_KEYWORDS);
            viewModel.setCategory_id(category_id);
            if (!TextUtils.isEmpty(keywords)&&keywords.toString()!=null)
                viewModel.requestGoodsList(keywords.toString());
            else {
                binding.fglSearch.setQuery(keywords,true);
//                viewModel.requestGoodsList(keywords.toString());
            }
        }
        viewModel.requestGoodsList(""); //默认检索全部数据
    }

    private void mKeywordSearch() {
        SystemUtils.customeStyle(getActivity(),binding.fglSearch);
        binding.fglSearch.onActionViewExpanded();
        binding.fglSearch.clearFocus();
        binding.fglSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                viewModel.requestGoodsList(s);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        binding.etSearchShop.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    KeyboardUtils.hideKeyboard(binding.etSearchShop);//点击搜索的时候隐藏软键盘
                    String keyWord = binding.etSearchShop.getText().toString().trim();
                    viewModel.requestGoodsList(keyWord);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public GoodsListViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(GoodsListViewModel.class);
    }

    @Override
    public void initViewObservable() {
        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //结束刷新
                binding.twinklingRefreshLayout.finishRefreshing();
            }
        });
        //监听上拉加载完成
        viewModel.uc.finishLoadmore.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //结束刷新
                binding.twinklingRefreshLayout.finishLoadmore();
            }
        });
        viewModel.uc.changeAllLableColor.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isFocus) {
                setTextColor();
                binding.aglSortAll.setTextColor(getResources().getColor(R.color.red));
                /*binding.aglSortAll.setFocusable(isFocus);
                binding.aglSortAll.setFocusableInTouchMode(isFocus);
                if (isFocus)
                    binding.aglSortAll.requestFocus();*/
            }
        });
        viewModel.uc.changeSalesLableColor.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isFocus) {
                setTextColor();
                binding.aglSortSale.setTextColor(getResources().getColor(R.color.red));
               /* binding.aglSortSale.setFocusable(isFocus);
                binding.aglSortSale.setFocusableInTouchMode(isFocus);
                if (isFocus)
                    binding.aglSortSale.requestFocus();*/

            }
        });
        viewModel.uc.changePriceLableColor.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isFocus) {
                setTextColor();
                binding.aglSortPrice.setTextColor(getResources().getColor(R.color.red));
                /*binding.aglSortPrice.setFocusable(isFocus);
                binding.aglSortPrice.setFocusableInTouchMode(isFocus);
                if (isFocus)
                    binding.aglSortPrice.requestFocus();*/
                /**
                 * ImageSpan imgSpan = new ImageSpan(this, R.drawable.apple);
                 * SpannableString spannableString = new SpannableString("012345");
                 * spannableString.setSpan(imgSpan, 1, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                 * mTextView04.setText(spannableString);
                 */
                if (TextUtils.equals(viewModel.getPriceSortBy(), Constants.SORT_ASC))
                    binding.ivSortPrice.setImageDrawable(getResources().getDrawable(R.mipmap.price_asc));
//                    binding.aglSortPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.price_asc, 0);
                else
                    binding.ivSortPrice.setImageDrawable(getResources().getDrawable(R.mipmap.price_desc));
//                binding.aglSortPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.price_desc, 0);

            }
        });
        viewModel.uc.showTypeEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String type) {
                LinearLayoutManager current = type.equals(
                        GoodsListItemViewModel.MultiRecyclerTypeList) ?
                        new LinearLayoutManager(binding.agRecycler.getContext()) :
                        new GridLayoutManager(getActivity().getApplication(), 2);
                binding.aglShowType.setImageResource(type.equals(
                        GoodsListItemViewModel.MultiRecyclerTypeList) ?
                        R.mipmap.goods_list_grid :
                        R.mipmap.goods_list_list);
                binding.agRecycler.setLayoutManager(current);
            }
        });
    }



    /**
     *设置综合  销量  价格的 颜色为默认色
    * */
    public void setTextColor(){
        binding.aglSortPrice.setTextColor(getResources().getColor(R.color.textColor));
        binding.aglSortSale.setTextColor(getResources().getColor(R.color.textColor));
        binding.aglSortAll.setTextColor(getResources().getColor(R.color.textColor));
    }

    private void initMallRefresh() {
        //刷新样式一
//        ProgressLayout header = new ProgressLayout(getActivity());
//        binding.twinklingRefreshLayout.setHeaderView(header);
//        binding.twinklingRefreshLayout.setFloatRefresh(true);
//        binding.twinklingRefreshLayout.setOverScrollRefreshShow(false);
//        binding.twinklingRefreshLayout.setHeaderHeight(140);
//        binding.twinklingRefreshLayout.setMaxHeadHeight(240);
//        binding.twinklingRefreshLayout.setOverScrollHeight(200);
//        binding.twinklingRefreshLayout.setEnableLoadmore(true);
//        header.setColorSchemeResources(R.color.colorPrimary, R.color.Blue, R.color.Yellow, R.color.Green);
        //刷新样式二
        SinaRefreshView headerView = new SinaRefreshView(getActivity());  //下拉刷新头部view设置
        headerView.setArrowResource(R.mipmap.ic_pull_refresh_arrow);
        binding.twinklingRefreshLayout.setHeaderView(headerView);
        LoadingView loadingView = new LoadingView(getActivity());        //上拉加载底部view设置
        binding.twinklingRefreshLayout.setBottomView(loadingView);
    }

    /**
     *  是否给recycleview 设置分隔线
     * @param isShow
     */
    private void setRecycleDiverLine(boolean isShow) {
        if (isShow) {
            CustomDividerItemDecoration itemDecoration = new CustomDividerItemDecoration(getActivity(),
                    new LinearLayoutManager(getActivity()).getOrientation(),false);
            itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_horizontal));
            binding.agRecycler.addItemDecoration(itemDecoration);
        }else {
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
