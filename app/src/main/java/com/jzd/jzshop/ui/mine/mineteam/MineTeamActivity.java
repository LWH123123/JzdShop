package com.jzd.jzshop.ui.mine.mineteam;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityMineTeamBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

public class MineTeamActivity extends BaseActivity<ActivityMineTeamBinding,MineTeamViewModel>
        implements OnRefreshListener, OnLoadmoreListener {

    private int page=1;

    private boolean isrefresh=true;


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_mine_team;
    }

    @Override
    public int initVariableId() {
        return BR.mineteamVM;
    }


    @Override
    public void initData() {
        super.initData();
        viewModel.initToolBar("我的团队");
        initMallRefresh();
        viewModel.request(page,true,binding.mallRefreshLayout);
        MineTeamAdapter mineTeamAdapter = new MineTeamAdapter();
        binding.rvView.setAdapter(mineTeamAdapter);
        binding.rvView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    @Override
    public MineTeamViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MineTeamViewModel.class);
    }


    //配置刷新
    private void initMallRefresh() {
        binding.mallRefreshHeader.setEnableLastTime(true);                 //时间显示
        binding.mallRefreshHeader.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshHeader.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshHeader.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）
        binding.mallRefreshFooter.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshFooter.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshFooter.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshLayout.setHeaderHeight(68);          //设置header高度
        binding.mallRefreshLayout.setFooterHeight(68);          //设置footer高度

        binding.mallRefreshLayout.setEnableRefresh(true);
        binding.mallRefreshLayout.setEnableAutoLoadmore(false);  //开启自动加载功能
        binding.mallRefreshLayout.setOnRefreshListener(this);
        binding.mallRefreshLayout.setOnLoadmoreListener(this);
//        binding.mallRefreshLayout.setEnableLoadmore(false);
    }



    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
            page++;
            viewModel.request(page,false,binding.mallRefreshLayout);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
            viewModel.mineteamlist.clear();
            page=1;
            viewModel.request(1,true,binding.mallRefreshLayout);
    }
}
