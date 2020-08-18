package com.jzd.jzshop.ui.home.local_life.location;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityChoiceCityBinding;
import com.jzd.jzshop.entity.ChoiceCityEntity;
import com.jzd.jzshop.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * Created by lxb on 2020/2/15.
 * 邮箱：2072301410@qq.com
 * TIP： 选择城市
 */
public class ChoiceCityAty extends BaseActivity<ActivityChoiceCityBinding, ChoiceCityViewModel> {
    private List<String> tipList;
    private List<String> chidList;
    private List<ChoiceCityEntity> dataList;
    private SearchChoiceCityAdapter mAdapter;
    private TextView tv_city_current;
    private String city_current;
    ;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_choice_city;
    }

    @Override
    public int initVariableId() {
        return BR.choiceCityVM;
    }

    @Override
    public ChoiceCityViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ChoiceCityViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        viewModel.setBinding(mContext, binding);
        viewModel.initToolBar(getResources().getString(R.string.choice_city));
        viewModel.requestData();

        Messenger.getDefault().register(this, ChoiceCityViewModel.TOKEN_VIEWMODEL_CHOICE_CITY_REFRESH,
                String.class, new BindingConsumer<String>() {
                    @Override
                    public void call(String hotCity) {//设置选择的热门城市
                        tv_city_current.setText(hotCity);
                    }
                });
    }

    private void getIntentData() {
        city_current = getIntent().getStringExtra(Constants.BUNDLE_KEY_LOCATION_CITY);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        dataList = new ArrayList<>();
        tipList = Arrays.asList(getResources().getStringArray(R.array.search_city_tip_txt));
        chidList = Arrays.asList(getResources().getStringArray(R.array.search_hot_city_txt));
        for (int i = 0; i < tipList.size(); i++) {
            dataList.add(new ChoiceCityEntity(tipList.get(i), chidList));
        }
        mAdapter = new SearchChoiceCityAdapter(mContext, viewModel, dataList, R.layout.item_rv_choice_city);
        binding.rv.setAdapter(mAdapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(mContext));
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_choice_city_header_view, null);
        initHeaderData(headerView);
        mAdapter.addHeaderView(headerView);

    }

    private void initHeaderData(View headerView) {
        tv_city_current = headerView.findViewById(R.id.tv_city_current);
        // TODO: 2020/2/15  定位获取当前城市
        tv_city_current.setText(city_current);
        tv_city_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(city_current)){
                    Messenger.getDefault().send(city_current, ChoiceCityViewModel.TOKEN_VIEWMODEL_CHOICE_CITY_REFRESH);
                    finish();
                }
            }
        });
    }
}
