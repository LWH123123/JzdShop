package com.jzd.jzshop.ui.home.local_life.search;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivitySearchKeyWordBinding;
import com.jzd.jzshop.entity.SearchKeyWordEntity;
import com.jzd.jzshop.utils.KeyboardUtils;
import com.jzd.jzshop.utils.StorageListSPUtils;
import com.jzd.jzshop.utils.dialog.MessageDialog;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * Created by lxb on 2020/2/15.
 * 邮箱：2072301410@qq.com
 * TIP： 本地生活  关键词搜索
 */
public class SearchKeyWordAty extends BaseActivity<ActivitySearchKeyWordBinding, SearchKeyWordViewModel> {
    private List<String> tipList;
    private List<String> chidList;
    private List<SearchKeyWordEntity> dataList;
    private SearchKeyWordAdapter mAdapter;
    private List<String> historyList;
    //搜索历史记录
    // SharedPreferences 存取 搜索历史 的标签
    private static final String TAG_SEARCH_HISTORY = "tagSearchHistory";
    // 默认最多展示的搜索历史数；这里只展示10个搜索历史，根据需要修改为你自己想要的数值
    private static final int DEFAULT_SEARCH_HISTORY_COUNT = 15;
    private List<String> mSearchHistoryLists;     // 搜索历史

    // 存储 搜索历史集合 的工具类
    private StorageListSPUtils mStorageListSPUtils;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_search_key_word;
    }

    @Override
    public int initVariableId() {
        return BR.searchKeyWordVM;
    }

    @Override
    public SearchKeyWordViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SearchKeyWordViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        mKeywordSearch();
        getIntentData();
        viewModel.setBinding(mContext, binding);
        viewModel.requestData();
    }

    private void getIntentData() {

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        dataList = new ArrayList<>();
        tipList = Arrays.asList(getResources().getStringArray(R.array.search_hot_keys_tip_txt));
        chidList = Arrays.asList(getResources().getStringArray(R.array.search_hot_keys_hot_txt));
        // TODO: 2020/2/18 方案一，针对后台返回数据
//        for (int i = 0; i < tipList.size(); i++) {
//            dataList.add(new SearchKeyWordEntity(tipList.get(i), chidList));
//        }
//        mAdapter = new SearchKeyWordAdapter(mContext, dataList, R.layout.item_rv_search_keyword);
//        binding.rv.setAdapter(mAdapter);
//        binding.rv.setLayoutManager(new LinearLayoutManager(mContext));
        // TODO: 2020/2/18  方案二 本地数据实现
        setHistoryList();
        setHotKeyWord(chidList);
        setClickEvent();

    }

    private void setClickEvent() {  //清空搜索历史
        binding.ivClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDialog.showCancelAndSureDialog(mContext,
                        getResources().getString(R.string.app_commit_delete_history),
                        "",
                        R.color.textColor, R.color.textColor, new MessageDialog.DialogClick() {
                            @Override
                            public void onSureClickListener() {
                                mStorageListSPUtils.removeDateList(TAG_SEARCH_HISTORY);  //清空SharedPreferences 中存储的搜索历史
                                mSearchHistoryLists.clear();
                                binding.flowLayoutHistory.removeAllViews();
                                binding.consHistory.setVisibility(View.GONE); // 删除之后，搜索历史布局隐藏
                            }

                            @Override
                            public void onCancelClickListener() {
                            }
                        });
            }
        });

    }

    private void setHistoryList() {
        mSearchHistoryLists = new ArrayList<>(); // 初始化搜索历史
        mStorageListSPUtils = new StorageListSPUtils(this, TAG);// 初始化存储 搜索历史集合 的工具类
        mSearchHistoryLists = mStorageListSPUtils.loadDataList(TAG_SEARCH_HISTORY);    // 获取 SharedPreferences 中已存储的 搜索历史
        if (mSearchHistoryLists != null && mSearchHistoryLists.size() != 0) {
            binding.consHistory.setVisibility(View.VISIBLE);
            for (int i = mSearchHistoryLists.size() - 1; i >= 0; i--) {
                TextView textView = (TextView) LayoutInflater.from(mContext)
                        .inflate(R.layout.item_flowlayout_search_history, binding.flowLayoutHistory, false);
                final String historyStr = mSearchHistoryLists.get(i);
                textView.setText(historyStr);
                textView.setOnClickListener(new View.OnClickListener() { // 设置搜索历史的回显
                    @Override
                    public void onClick(View v) {
//                        binding.icToolbar.etSearchShop.setText(historyStr);
//                        binding.icToolbar.etSearchShop.setSelection(historyStr.length());
                        Messenger.getDefault().send(historyStr, SearchKeyWordViewModel.TOKEN_VIEWMODEL_SEARCH_KEYWORD_REFRESH);
                        finish();
                    }
                });
                // FlowLayout 中添加 搜索历史
                binding.flowLayoutHistory.addView(textView);
            }
        }
    }

    private void mKeywordSearch() {
        binding.icToolbar.etSearchShop.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    KeyboardUtils.hideKeyboard(binding.icToolbar.etSearchShop);//点击搜索的时候隐藏软键盘
                    String keyWord = binding.icToolbar.etSearchShop.getText().toString().trim();
                    processAction(keyWord);// 存取 SharedPreferences 中存储的搜索历史并做相应的处理
                    Messenger.getDefault().send(keyWord, SearchKeyWordViewModel.TOKEN_VIEWMODEL_SEARCH_KEYWORD_REFRESH);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * 热搜关键词
     *
     * @param hotList
     */
    private void setHotKeyWord(final List<String> hotList) {
        //流式布局适配器
        binding.flowLayout.setAdapter(new TagAdapter<String>(hotList) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_flowlayout_hot_keyword, null, false);
                TextView auto_tv = inflate.findViewById(R.id.tv_name);//修改值
                ImageView iv_hot = inflate.findViewById(R.id.iv_hot);
                if (position == 0) {
                    iv_hot.setVisibility(View.VISIBLE);
                } else {
                    iv_hot.setVisibility(View.GONE);
                }
                auto_tv.setText(hotList.get(position));//清空当前集合
//                hotList.clear();//返回视图
                return inflate;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                Log.d(TAG,"onSelected ---->>> "+ position);
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                Log.d(TAG,"unSelected ---->>> "+ position);
            }
        });
        binding.flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String keyWord = chidList.get(position);
                processAction(keyWord);// 存取 SharedPreferences 中存储的搜索历史并做相应的处理
                Messenger.getDefault().send(keyWord, SearchKeyWordViewModel.TOKEN_VIEWMODEL_SEARCH_KEYWORD_REFRESH);
                finish();
                return true;
            }
        });
    }

    /**
     * 存取 SharedPreferences 中存储的搜索历史并做相应的处理
     *
     * @param keyWord
     */
    private void processAction(String keyWord) {
        // 获取 EditText 输入内容
        String searchInput = keyWord;
        if (TextUtils.isEmpty(searchInput)) {
            ToastUtils.showLong(R.string.app_search_input);
            return;
        } else {
            // 先获取之前已经存储的搜索历史
            List<String> previousLists = mStorageListSPUtils.loadDataList(TAG_SEARCH_HISTORY);
            if (previousLists.size() != 0) {
                // 如果之前有搜索历史，则添加
                mSearchHistoryLists.clear();
                mSearchHistoryLists.addAll(previousLists);
            }
            // 去除重复，如果搜索历史中已经存在则remove，然后添加到后面
            if (!mSearchHistoryLists.contains(searchInput)) {
                // 如果搜索历史超过设定的默认个数，去掉最先添加的，并把新的添加到最后
                // 这里只展示10个搜索历史，根据需要修改为你自己想要的数值
                if (mSearchHistoryLists.size() >= DEFAULT_SEARCH_HISTORY_COUNT) {
                    mSearchHistoryLists.remove(0);
                    mSearchHistoryLists.add(mSearchHistoryLists.size(), searchInput);
                } else {
                    mSearchHistoryLists.add(searchInput);
                }
            } else {
                // 如果搜索历史已存在，找到其所在的下标值
                int inputIndex = -1;
                for (int i = 0; i < mSearchHistoryLists.size(); i++) {
                    if (searchInput.equals(mSearchHistoryLists.get(i))) {
                        inputIndex = i;
                    }
                }
                // 如果搜索历史已存在，先从 List 集合中移除再添加到集合的最后
                mSearchHistoryLists.remove(inputIndex);
                mSearchHistoryLists.add(mSearchHistoryLists.size(), searchInput);
            }
            // 存储新的搜索历史到 SharedPreferences
            mStorageListSPUtils.saveDataList(TAG_SEARCH_HISTORY, mSearchHistoryLists);
            Log.d(TAG, getResources().getString(R.string.app_search_input) + searchInput);
        }
    }
}
