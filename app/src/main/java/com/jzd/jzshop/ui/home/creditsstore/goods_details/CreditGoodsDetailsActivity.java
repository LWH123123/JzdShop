package com.jzd.jzshop.ui.home.creditsstore.goods_details;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityGoodsDetailsBinding;
import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.entity.CreditDetailsLogsEntity;
import com.jzd.jzshop.entity.CreditGoodsDetailsEntity;
import com.jzd.jzshop.ui.home.AppIdentityJumpUtils;
import com.jzd.jzshop.ui.home.creditsstore.goods_details.details.CreditDetailsWebFrag;
import com.jzd.jzshop.ui.home.creditsstore.goods_details.details.CreditShopLogFrag;
import com.jzd.jzshop.ui.mine.assets.AssetsRecordPagerAdapter;
import com.jzd.jzshop.utils.BaseWebviewUtils;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.MoneyFormatUtils;
import com.jzd.jzshop.utils.TimeToDateUtils;
import com.stx.xhb.xbanner.XBanner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;


/**
 * 积分商城 --商品详情
 */
public class CreditGoodsDetailsActivity extends BaseActivity<ActivityGoodsDetailsBinding, CreditGoodsDetailsViewModel>
        implements XBanner.OnItemClickListener {
    private ArrayList<BannEntity> bannEntities;
    private String gid;
    private CountDownTimer countDownTimer;
    //限购开始日期
    private String start;
    //限购结束日期
    private String end;
    private CreditDetailsLogsAdapter creditDetailsLogsAdapter;
    private int position;
    private int sign=1;//做标记是否需要显示查看更多按钮

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_goods_details;
    }

    @Override
    public int initVariableId() {
        return BR.goodsdetailsVM;
    }

    @Override
    public CreditGoodsDetailsViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(CreditGoodsDetailsViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        getIntentData();
        viewModel.setTitleText("积分商城");
        initTabLayout();
        viewModel.requestWork(gid);
        viewModel.setBinding(binding, CreditGoodsDetailsActivity.this);
        binding.xbBanner.setOnItemClickListener(this);
        viewModel.partlogs(gid);
    }

    private void getIntentData() {
        gid = getIntent().getStringExtra(Constants.CREDIT_GOODS_DETAIL);
    }

    private void initTabLayout() {
        TabLayout mtab = binding.tab;
        mtab.addTab(mtab.newTab().setText("商品详情"));
        mtab.addTab(mtab.newTab().setText("参与记录"));
        mtab.getTabAt(0).select();
        binding.tvMore.setVisibility(View.GONE);
        mtab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                if (position == 0) {
                    binding.tvMore.setVisibility(View.GONE);
                    binding.webView.setVisibility(View.VISIBLE);
                    binding.tvNodetails.setVisibility(View.VISIBLE);
                    binding.tvHint.setVisibility(View.GONE);
                    binding.reView.setVisibility(View.GONE);
                } else if (position == 1) {
                    if(sign==1){
                        binding.tvMore.setVisibility(View.VISIBLE);
                    }
                    binding.tvNodetails.setVisibility(View.GONE);
                    binding.webView.setVisibility(View.GONE);
                    binding.tvHint.setVisibility(View.VISIBLE);
                    binding.reView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.binddata.observe(this, new Observer<CreditGoodsDetailsEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable CreditGoodsDetailsEntity.ResultBean bean) {
                int lotterydraws = bean.getLotterydraws();
                if (lotterydraws == 0) binding.tvConvert.setText("立即兑换");
                else binding.tvConvert.setText("立即抽奖");
                binding.tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);/*删除线*/
                String money = bean.getMoney();
                if (!TextUtils.isEmpty(bean.getMoney()) && money.equals("0.00")) {
                    binding.tvMoney.setVisibility(View.GONE);
                }else {
                    //设置金额部分字体大小
                    Spannable spannable = new SpannableString("+¥"+bean.getMoney());
                    spannable.setSpan(new AbsoluteSizeSpan(40),0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    binding.tvMoney.setText(spannable);
                }
                if(bean.getIstime()==0){// 0不是限时购  1 是限时购
                    View sign= binding.viewSign;
                    ViewGroup.LayoutParams layoutParams = sign.getLayoutParams();
                    layoutParams.height=120;
                    sign.setLayoutParams(layoutParams);
                    binding.groupNotime.setVisibility(View.VISIBLE);
                }else {
                    binding.conTime.setVisibility(View.VISIBLE);
                    if(!TextUtils.isEmpty(bean.getCredit())){
                    double aDouble = Double.parseDouble(bean.getCredit());
                    String s = new MoneyFormatUtils().keepTwoDecimalNoDisplay0(aDouble);
                     binding.tvCredittime.setText(s+"积分");
                     //数据过多拥挤的情况所以不设置了
                        //Spannable spannable = new SpannableString("+¥"+bean.getMoney());
//                    spannable.setSpan(new AbsoluteSizeSpan(40),0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                     binding.tvMoneytime.setText(spannable);
                        String timeend = bean.getTimeend();
                    String timestart = bean.getTimestart();
                    if(!TextUtils.isEmpty(timeend)&&!TextUtils.isEmpty(timestart)){
                        long startlong = Long.parseLong(timestart);//将时间戳转为long类型
                        long endlong = Long.parseLong(timeend);

                        //true 表示限购开始了  否则反之
                        boolean front = TimeToDateUtils.isAfter(startlong);//判断限购是否开始
                        //true 表示限购已结束
                        boolean later = TimeToDateUtils.isAfter(endlong);//判断限购是否结束

                        //
                        start = TimeToDateUtils.stampToTime(startlong);
                        end = TimeToDateUtils.stampToTime(endlong);
                        String nowDate = TimeToDateUtils.getNowDate();
                        KLog.a("限购开始日期===>>>"+ start);
                        KLog.a("限购结束日期===>>>"+ end);//2020-5-19 15:21:00
                        KLog.a("限购期间日期===>>>"+nowDate);
                        KLog.a("限购是否开始==>>"+front+"         限购是否结束===>>>"+later);
                        if(front&&!later){//表示在限购期间
                            binding.tvConvert.setClickable(true);
                            binding.tvTimehint.setText("距离结束仅剩");
                            long timeDifference = TimeToDateUtils.getTimeDifference(nowDate, end);
                            setTimerCount(timeDifference*1000,1);
                        }else if(!front){//表示限购还未开始
                            KLog.a("限购未开始===>>>>>");
                            binding.tvTimehint.setText("距离开始仅剩");
                            binding.tvConvert.setClickable(false);
                            long timeDifference = TimeToDateUtils.getTimeDifference(nowDate, start);
                            setTimerCount(timeDifference*1000,0);
                        }else if(later){//表示限购已结束
                            //
                            binding.tvConvert.setClickable(false);
                            binding.groupTimer.setVisibility(View.GONE);
                            binding.tvEnd.setVisibility(View.VISIBLE);
                        }

                   }

                    }
                    binding.tvPricetime.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//删除线
                    binding.tvPricetime.setText(bean.getPrice());
                    Spannable spannable = new SpannableString("+¥"+bean.getMoney());
                    spannable.setSpan(new AbsoluteSizeSpan(40),0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    binding.tvMoneytime.setText(spannable);
                    binding.groupNotime.setVisibility(View.GONE);
                }
                if(!TextUtils.isEmpty(bean.getDispatch())&&bean.getDispatch().equals("0.00")){//包邮
                    binding.tvDispatch.setVisibility(View.VISIBLE);
                }else {
                    binding.tvDispatch.setVisibility(View.VISIBLE);
                    binding.tvDispatch.setText("运费:  "+bean.getDispatch());
                }
                if(bean.getType()==1){
                    binding.tvDispatch.setVisibility(View.GONE);
                }
                bindBanner(bean.getThumb());//绑定商品图片
                bindTextData(bean.getTotal(),bean.getJoins(),bean.getType());//绑定仅限 和 参与
                KLog.a("加载的HTML片段为"+bean.getContent());
                bindingWebView(bean.getContent());
            }
        });


        //绑定参与记录数据
        viewModel.uc.logsdata.observe(this, new Observer<CreditDetailsLogsEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable CreditDetailsLogsEntity.ResultBean resultBean) {
                KLog.a("当前数据的多少位 ==>>>"+resultBean.getData().size()+"    当前所在位置为====>>>>"+position);
                if(resultBean.getTotal()!=0) {
                    String total=resultBean.getTotal() + "人已参与，名额有限现在立即抽奖";
                    binding.tvHint.setText(total);
                }
                if(resultBean.getData().size()>=10){
                    sign=1;
                }else {
                    sign=0;
                    binding.tvMore.setVisibility(View.GONE);
                }
                if(creditDetailsLogsAdapter==null) {
                    creditDetailsLogsAdapter = new CreditDetailsLogsAdapter(resultBean.getData());
                }else {
                    KLog.a("第二次加载");
                creditDetailsLogsAdapter.addData(resultBean.getData());
                creditDetailsLogsAdapter.notifyDataSetChanged();
                }
                binding.reView.setAdapter(creditDetailsLogsAdapter);
                binding.reView.setLayoutManager(new LinearLayoutManager(CreditGoodsDetailsActivity.this));
                creditDetailsLogsAdapter.notifyDataSetChanged();
            }
        });
    }


    /**
     *  设置倒计时间
     * @param alltime  总的秒数
     */
    private void setTimerCount(long alltime,int type){
        countDownTimer = new CountDownTimer(alltime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long day =millisUntilFinished/86400000; //天数  86400000=60*60*24*1000   86400000代表:一天的秒数*1000
                long hour=(millisUntilFinished-day*(86400000))/3600000;//小时数  总数减去天数的时间/3600000    3600000代表一小时的秒数*1000
                long min = (millisUntilFinished-(day*86400000)-(hour*3600000))/60000;
                long s = (millisUntilFinished-(day*86400000)-(hour*3600000)-(min*60000))/1000;
                binding.tvTimer.setText(day+"天"+hour+"小时"+min+"分"+s+"秒");
            }

            @Override
            public void onFinish() {
               if(type==0){//限购开始了
                   binding.tvTimehint.setText("距离结束仅剩");
                   binding.tvConvert.setClickable(true);
                   String nowDate = TimeToDateUtils.getNowDate();
                   long timeDifference = TimeToDateUtils.getTimeDifference(nowDate, end);
                   setTimerCount(timeDifference*1000,1);
               }else if(type==1){//限购结束了
                   binding.tvConvert.setClickable(false);
                   binding.groupTimer.setVisibility(View.GONE);
                   binding.tvEnd.setVisibility(View.VISIBLE);
               }

            }
        };
        countDownTimer.start();
    }






    /**
     * 绑定H5
     * @param content
     */
    private void bindingWebView(String content) {
        if (!TextUtils.isEmpty(content)) {
            binding.tvNodetails.setVisibility(View.GONE);
            String htmlData = BaseWebviewUtils.getHtmlData(content);
            binding.webView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
        }else {
            binding.webView.setVisibility(View.GONE);
            binding.tvNodetails.setText("暂无商品详情");
            binding.tvNodetails.setVisibility(View.VISIBLE);
        }
    }


    /**
     *  @param total 仅限
     * @param joins 已参与
     * @param type 类型
     */
    private void bindTextData(int total, int joins, int type) {

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#D90804"));
        //仅限
        SpannableString spannabletotal = new SpannableString(String.format("仅限: %S份",total));
        int length = spannabletotal.length();
        spannabletotal.setSpan(colorSpan,4,length-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        binding.tvTotal.setText(spannabletotal);
        //已参与
        SpannableString spannablejoins = new SpannableString(String.format("已参与: %S次",joins));
        int index = spannablejoins.length();
        spannablejoins.setSpan(colorSpan,5,index-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        binding.tvJoins.setText(spannablejoins);
        switch (type){
            case 0://商品
              binding.tvType.setText("商品");
                break;
            case 1://优惠券
                binding.tvType.setText("优惠券");
                break;
            case 2://余额
                binding.tvType.setText("余额");
                break;
            case 3://红包
                binding.tvType.setText("红包");
                break;
        }

    }

    //绑定大图
    public void bindBanner(String banner){
        if(TextUtils.isEmpty(banner)){
            return;
        }
        bannEntities = new ArrayList<>();
        if(bannEntities.size()!=0){
            bannEntities.clear();
        }
        BannEntity bannEntity = new BannEntity(banner);
        bannEntities.add(bannEntity);
        binding.xbBanner.setBannerData(bannEntities);
        binding.xbBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                BannEntity ban = (BannEntity) model;
                Glide.with(getApplication()).load(ban.getXBannerUrl()).into((ImageView) view);
            }
        });
        binding.xbBanner.startAutoPlay();
    }



    @Override
    public void onItemClick(XBanner banner, Object model, View view, int position) {
        AppIdentityJumpUtils.previewLargePicGoods(CreditGoodsDetailsActivity.this,bannEntities,position);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer!=null){
        countDownTimer.cancel();
        }
    }
}
