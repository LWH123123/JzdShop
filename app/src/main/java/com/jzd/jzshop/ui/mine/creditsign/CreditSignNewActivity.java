package com.jzd.jzshop.ui.mine.creditsign;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityCreditSignNewBinding;
import com.jzd.jzshop.entity.SignDateEntity;
import com.jzd.jzshop.entity.SignEntity;
import com.jzd.jzshop.entity.SignReceiveEntity;
import com.jzd.jzshop.entity.ToSignEntity;
import com.jzd.jzshop.ui.base.bottom_tab.InitBottomTab;
import com.jzd.jzshop.ui.home.creditsstore.home.CreditsStoreActivity;
import com.jzd.jzshop.ui.mine.creditsign.ranking.SignRankingActivity;
import com.jzd.jzshop.ui.mine.creditsign.shop.CreditShopActivity;
import com.jzd.jzshop.utils.dialog.MessageDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.Utils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * @author LXB
 * @description: 积分签到 日历部分定制性太强，通过原生写，后期可优化为 日历控件
 * @date :2020/4/7 16:45
 */
public class CreditSignNewActivity extends BaseActivity<ActivityCreditSignNewBinding, CreditSignNewViewModel> implements OnRefreshListener {
    private NavigationController build;
    private SignCalendarAdapter signCalendarAdapter;
    private SignContinueRuleAdapter signcontinueruleadapter;
    private SignAllRuleAdapter signAllRuleAdapter;
    private String selectedDay;
    private int currentDay;
    private int month;
    private int year;
    //选择到当前月份的时候 展示当天效果
    private int calendar=0;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_credit_sign_new;
    }

    @Override
    public int initVariableId() {
        return BR.creditsignVM;
    }

    @Override
    public CreditSignNewViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(CreditSignNewViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        build.setSelect(0);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setBinding(mContext, binding);
        viewModel.initToolBar(getResources().getString(R.string.credit_sign));
        StatusBarUtil.setStatusBarDarkTheme(this, false);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#D90804"));
        Calendar calendar = Calendar.getInstance();
        //年
        year = calendar.get(Calendar.YEAR);
        //月
        month = calendar.get(Calendar.MONTH) + 1;
        binding.tvTimeselect.setText(year + "年" + month + "月");
        //选中当天
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        viewModel.postGetSign();
        initMallRefresh();
        initBottomTab();

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        //获取签到首页 数据
        viewModel.uc.getSignLiveData.observe(this, new Observer<SignEntity>() {
            @Override
            public void onChanged(@Nullable SignEntity signEntity) {
                if (signEntity.getResult().getCan_sign() == 0) {
                    binding.ivSign.setImageResource(R.mipmap.chicksign);
                } else {
                    binding.ivSign.setImageResource(R.mipmap.re_chicksign);
                }
                List<SignEntity.ResultBean.CalendarBean> calendar = signEntity.getResult().getCalendar(); //日期数据包
                if (calendar != null && calendar.size() > 0) {
                    initCalendarData(calendar, currentDay,signEntity);
                } else {
                }
                List<SignEntity.ResultBean.ContinueRuleBean> continue_rule = signEntity.getResult().getContinue_rule(); //连续签到数据包
                if (continue_rule != null && continue_rule.size() > 0) {
                    binding.recyContinueRule.setVisibility(View.VISIBLE);
                    binding.constContinueTip.setVisibility(View.VISIBLE);
                    initContinueRuleData(continue_rule);
                } else {
                    binding.constContinueTip.setVisibility(View.GONE);
                    binding.recyContinueRule.setVisibility(View.GONE);
                }
                List<SignEntity.ResultBean.AllRuleBean> all_rule = signEntity.getResult().getAll_rule();  //总签到数据包
                if (all_rule != null && all_rule.size() > 0) {
                    binding.recyAllRule.setVisibility(View.VISIBLE);
                    binding.constSignAllTip.setVisibility(View.VISIBLE);
                    initAllRuleData(all_rule);
                } else {
                    binding.constSignAllTip.setVisibility(View.GONE);
                    binding.recyAllRule.setVisibility(View.GONE);
                }
            }
        });
        //签到
        viewModel.uc.toSignLiveData.observe(this, new Observer<ToSignEntity>() {
            @Override
            public void onChanged(@Nullable ToSignEntity toSignEntity) {
                if (signCalendarAdapter != null) {
                    signCalendarAdapter.notifyDataSetChanged();
                }
                binding.refreshLayout.autoRefresh();
                if (toSignEntity != null) {
                    if (toSignEntity.getResult() != null) {
                        if (!TextUtils.isEmpty(toSignEntity.getResult().getMsg())) {
                            MessageDialog.showSignClickDialog(mContext, toSignEntity.getResult().getMsg(), new MessageDialog.DialogClick() {
                                @Override
                                public void onCancelClickListener() {
                                }

                                @Override
                                public void onSureClickListener() {
                                    // TODO: 2020/4/10  去抽奖
//                                    ToastUtils.showLong("我要去抽奖!");
                                }
                            });
                        } else {
                            Log.e(TAG, "签到成功:" + toSignEntity.getResult().getMsg());
                        }
                    }
                }

            }
        });
        //补签
        viewModel.uc.toRepairSignLiveData.observe(this, new Observer<ToSignEntity>() {
            @Override
            public void onChanged(@Nullable ToSignEntity toSignEntity) {
                if (signCalendarAdapter != null) {
                    signCalendarAdapter.notifyDataSetChanged();
                }
                binding.refreshLayout.autoRefresh();
                if (toSignEntity != null) {
                    if (toSignEntity.getResult() != null) {
                        if (!TextUtils.isEmpty(toSignEntity.getResult().getMsg())) { //补签成功
                            ToastUtils.showLong(toSignEntity.getResult().getMsg());
                        } else {
                            Log.e(TAG,"补签成功:" + toSignEntity.getResult().getMsg());
                        }
                    }
                }
            }
        });

        //选择日期
        viewModel.uc.timeselect.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Intent intent = new Intent(CreditSignNewActivity.this, TimeSelectAty.class);
                startActivityForResult(intent,100);
            }
        });

        //连续签到/总签到-->>领取积分
        viewModel.uc.toReceiveLiveData.observe(this, new Observer<SignReceiveEntity>() {
            @Override
            public void onChanged(@Nullable SignReceiveEntity signReceiveEntity) {
                if (signcontinueruleadapter != null) {
                    signcontinueruleadapter.notifyDataSetChanged();
                }
                if (signAllRuleAdapter != null) {
                    signAllRuleAdapter.notifyDataSetChanged();
                }
                binding.refreshLayout.autoRefresh();
                if (signReceiveEntity != null) {
                    if (signReceiveEntity.getResult() != null) {
                        if (!TextUtils.isEmpty(signReceiveEntity.getResult().getMsg())) { //补签成功
                            ToastUtils.showLong(signReceiveEntity.getResult().getMsg());
                        } else {
                            Log.e(TAG, "领取成功:" + signReceiveEntity.getResult().getMsg());
                        }
                    }
                }
            }
        });

        //更新签到的数据
        viewModel.uc.updateCalendarData.observe(this, new Observer<SignDateEntity>() {
            @Override
            public void onChanged(@Nullable SignDateEntity signDateEntity) {
                List<SignDateEntity.ResultBean> result = signDateEntity.getResult();
                if(result==null){
                    return;
                }
                ArrayList<SignEntity.ResultBean.CalendarBean> list = new ArrayList<>();
                for (int i = 0; i <result.size() ; i++) {
                    SignDateEntity.ResultBean resultBean = result.get(i);
                    SignEntity.ResultBean.CalendarBean calendarBean = new SignEntity.ResultBean.CalendarBean();
                    calendarBean.setColor(resultBean.getColor());
                    calendarBean.setDay(resultBean.getDay());
                    calendarBean.setMonth(resultBean.getMonth());
                    calendarBean.setSigned(resultBean.getSigned());
                    calendarBean.setTitle(resultBean.getTitle());
                    calendarBean.setYear(resultBean.getYear());
                    calendarBean.setSignold(resultBean.getSignold());
                    list.add(calendarBean);
                }
                KLog.a("我组装的数据为=====>>>>"+list.size());
                initCalendarData(list,calendar,null);
            }
        });


    }

    /**
     * 总 签到有礼
     *
     * @param all_rule
     */
    private void initAllRuleData(List<SignEntity.ResultBean.AllRuleBean> all_rule) {
        signAllRuleAdapter = new SignAllRuleAdapter(mContext, all_rule, R.layout.item_rv_sign_continue_rule,viewModel);
        binding.recyAllRule.setAdapter(signAllRuleAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recyAllRule.setLayoutManager(linearLayoutManager);
    }

    /**
     * 连续签到有礼
     *
     * @param continue_rule
     */
    private void initContinueRuleData(List<SignEntity.ResultBean.ContinueRuleBean> continue_rule) {
        signcontinueruleadapter = new SignContinueRuleAdapter(mContext, continue_rule, R.layout.item_rv_sign_continue_rule,viewModel);
        binding.recyContinueRule.setAdapter(signcontinueruleadapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recyContinueRule.setLayoutManager(linearLayoutManager);
    }

    /**
     * 日期数据包
     *
     * @param calendar
     * @param signEntity
     */
    private void initCalendarData(final List<SignEntity.ResultBean.CalendarBean> calendar, int calendarday,final SignEntity signEntity) {
        signCalendarAdapter = new SignCalendarAdapter(mContext, calendar, R.layout.item_rv_sign_calendar, calendarday);
        binding.recyCalendar.setAdapter(signCalendarAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 7);
        binding.recyCalendar.setLayoutManager(gridLayoutManager);
        signCalendarAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {//类型
                signCalendarAdapter.setSelectedPosition(position);     //选中，设置背景颜色
                if (position >= calendar.size()) return;
                final String year = calendar.get(position).getYear();
                final String month = calendar.get(position).getMonth();
                selectedDay = calendar.get(position).getDay(); //选中的天数
                int signold = calendar.get(position).getSignold();//是否可补签
                int selected_day = Integer.parseInt(selectedDay);
                if (selected_day >= currentDay) {
                    return;
                } else {
                    //补签
                    if (signold == 0) { //不可以补签
                        return;
                    } else if (signold == 1&&signEntity!=null) {//可以补签
                        // 当某天支持补签时，需要余额或积分进行抵扣。 0：余额 1：积分
                        int signold_type = signEntity.getResult().getSignold_type();
                        String signold_price = signEntity.getResult().getSignold_price();
                        //mCommonDialog
                        if (signold_type == 0) {
                            String content = "补签需从余额扣除" + signold_price + "，确定补签吗？";
                            mCommonDialog(year, month, content);
                        } else if (signold_type == 1) {
                            String content = "补签需要扣除" + signold_price + "积分，确定补签吗？";
                            mCommonDialog(year, month, content);
                        } else {
                        }
                    } else {
                    }
                }
            }
        });
    }





    private void mCommonDialog(final String year, final String month, String content) {
        MessageDialog.showCancelAndSureDialog(mContext, "提示", content,
                R.color.textColor, R.color.textColor,
                new MessageDialog.DialogClick() {
                    @Override
                    public void onSureClickListener() {
                        viewModel.postToSign(year + "-" + month + "-" + selectedDay);
                    }

                    @Override
                    public void onCancelClickListener() {
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            int years = data.getIntExtra("YEAR", 0);
            int months = data.getIntExtra("MONTH", 0);
            if(years==year&&month==months){
                calendar=currentDay;//标记选中为当前月份时候;
            }else {
                calendar=0;
            }
            String s = setDateType(years, months);
            KLog.a("修改的时间格式为"+s);
            viewModel.postGetSignDate(s);
            binding.tvTimeselect.setText(years+"年"+months+"月");
        }
    }


    /**
    *修改月份格式
    * */
    public String setDateType(int year,int month){
        StringBuffer date = new StringBuffer();
        date.append(year+"-");
        if(month>=10){
            date.append(month);
        }else {
            date.append("0"+month);
        }
        return date.toString();
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
                break;
            case 1:
                startActivity(SignRankingActivity.class);
                break;
            case 2:
                startActivity(CreditsStoreActivity.class);
                break;
        }
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        build = null;
    }

    private void initMallRefresh() {
        binding.mallRefreshHeader.setEnableLastTime(true);                 //时间显示
        binding.mallRefreshHeader.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshHeader.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshHeader.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshFooter.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshFooter.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshFooter.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.refreshLayout.setHeaderHeight(68);          //设置header高度
        binding.refreshLayout.setFooterHeight(68);          //设置footer高度

        binding.refreshLayout.setEnableRefresh(true);
        binding.refreshLayout.setEnableAutoLoadmore(false);  //开启自动加载功能
        binding.refreshLayout.setOnRefreshListener(this);
//        binding.refreshLayout.setOnLoadmoreListener(this);
        binding.refreshLayout.setEnableLoadmore(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        viewModel.postGetSign();
    }
}
