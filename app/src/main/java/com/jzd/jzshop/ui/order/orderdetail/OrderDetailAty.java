package com.jzd.jzshop.ui.order.orderdetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.chat.OpenChatActivity;
import com.jzd.jzshop.databinding.ActivityOrederDetailBinding;
import com.jzd.jzshop.entity.CancelRefundEntity;
import com.jzd.jzshop.entity.OrderDetailEntity;
import com.jzd.jzshop.jpush.MyReceiver;
import com.jzd.jzshop.jpush.NotifyClickReceiver;
import com.jzd.jzshop.ui.order.logistics.LogisticInfoFragment;
import com.jzd.jzshop.ui.order.logistics.LogisticsFragment;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.MoneyFormatUtils;

import org.byteam.superadapter.OnItemClickListener;

import java.util.List;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * @author LXB
 * @description: 订单详情
 * @date :2020/1/13 9:40
 */
public class OrderDetailAty extends BaseActivity<ActivityOrederDetailBinding, OrderDetailViewModel> {
    private OrderDetailEntity.ResultBean resultList;
    private double calcDataPrice;
    private String order_id, realPrice, realFinalPrice;
    private int isEvaluation = 0; //0 评价1 追加评价2 已评价

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_oreder_detail;
    }

    @Override
    public int initVariableId() {
        return com.jzd.jzshop.BR.orderVM;
    }

    @Override
    public OrderDetailViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        OrderDetailViewModel orderDetailViewModel = ViewModelProviders.of(this, factory).get(OrderDetailViewModel.class);
        return orderDetailViewModel;
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this,false);
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#D90804"));
        viewModel.initToolBar(getResources().getString(R.string.orderdetail));
        getIntentData();
        viewModel.setBinding(mContext, binding);
        viewModel.requestData(order_id);

        //todo 一下通知是否可公用，待优化
        Messenger.getDefault().register(this, OrderDetailViewModel.TOKEN_VIEWMODEL_SUBMIT_APPLY_REFRESH, String.class, new BindingConsumer<String>() {
            @Override
            public void call(String state) {//刷新
                if (state.equals("submit_apply")) {
                    viewModel.requestData(order_id);
                } else {
                }
            }
        });
        Messenger.getDefault().register(this, OrderDetailViewModel.TOKEN_VIEWMODEL_CANCLE_APPLY_REFRESH, String.class, new BindingConsumer<String>() {
            @Override
            public void call(String state) {//刷新详情页数据
                if (state.equals("cancel_apply")) {
                    viewModel.requestData(order_id);
                } else {
                }
            }
        });
        Messenger.getDefault().register(this, OrderDetailViewModel.TOKEN_VIEWMODEL_REFUND_RECEIVE_REFRESH, String.class, new BindingConsumer<String>() {
            @Override
            public void call(String state) {//关闭详情页 刷新订单列表
                if (state.equals("refund_receive")) {
                    finish();
                } else {
                }
            }
        });

    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            order_id = bundle.getString(Constants.ORDER_ID);
//            realFinalPrice = bundle.getString(Constants.ORDER_PRICE);
        }

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<OrderDetailEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable OrderDetailEntity.ResultBean resultBean) {
                resultList = resultBean;
                String refund_reply = resultBean.getRefund_reply();//驳回
                if (!TextUtils.isEmpty(refund_reply)) {
                    binding.constrlRefuseTip.setVisibility(View.VISIBLE);
                    binding.tvRefustRease.setText("拒绝原因：" + refund_reply);
                } else {
                    binding.constrlRefuseTip.setVisibility(View.GONE);
                }
                //头部数据
                binding.tvWaitpay.setText(resultBean.getStatusstr());
                binding.tvOrderAmount.setText("¥".concat(resultBean.getOrder_price()));
                //收货地址
                OrderDetailEntity.ResultBean.AddressBean address = resultBean.getAddress();
                if (address != null) {
                    binding.tvAddressName.setText(address.getReceive_name());
                    if (!TextUtils.isEmpty(address.getReceive_mobile())) {
                        StringBuilder sb = new StringBuilder(address.getReceive_mobile());
                        String showPhone = sb.replace(3, 7, "****").toString();
                        binding.tvAddressPhone.setText(showPhone);
                        binding.tvAddress.setText(address.getReceive_address());
                    } else {
                    }
                }
                //快递信息
                List<OrderDetailEntity.ResultBean.ExpressBean> express = resultBean.getExpress();
                initExpressRecycleView(express);
                List<OrderDetailEntity.ResultBean.DataBean> data = resultBean.getData();
                if (data != null && data.size() > 0) {
                    initRecycleView(resultList, data);
                } else {
                }
                //底部button
                /**
                 * -1：订单已取消
                 * 0：普通状态（paytype=3时，显示待发货，其它显示待付款）
                 * 1：已付款
                 * 2：已发货
                 * 3：已收货
                 */
                if (resultBean.getOrder_status() == -1) {
                    binding.tvCacelOrder.setVisibility(View.GONE);
                    binding.tvPayOrder.setVisibility(View.GONE);
                } else if (resultBean.getOrder_status() == 0) {
                    if (resultBean.getPaytype() == 3) { //货到付款
                        binding.tvWaitpay.setText(R.string.cash_on_delivery);
                    } else {//待付款
                        binding.tvWaitpay.setText(R.string.wait_pay);
                        if (resultBean.getIs_peerpay() == 0) {//  is_peerpay  	是否代付。0：否 1：是
                            binding.tvPayOrder.setVisibility(View.VISIBLE);//支付订单
                            binding.tvPaymentOnBehalf.setVisibility(View.GONE);//代付
                        } else {
                            binding.tvPaymentOnBehalf.setVisibility(View.VISIBLE);//代付
                            binding.tvPayOrder.setVisibility(View.GONE);
                        }
                    }
                    binding.tvCacelOrder.setVisibility(View.VISIBLE);
                    binding.tvPayOrder.setVisibility(View.VISIBLE);
//                    binding.tvCantactMerchant.setVisibility(View.VISIBLE); //联系客服入口变动
                } else if (resultBean.getOrder_status() == 1) {//已付款（待发货）
                    binding.tvCacelOrder.setVisibility(View.GONE);
                    binding.tvPayOrder.setVisibility(View.GONE);
                    //is_refund 是否允许售后 和 发起退款 0：否 1：是 2: 已存在申请
                    if (resultBean.getIs_refund() == 0) {
                        binding.tvApplyExitAmount.setVisibility(View.GONE);//申请退款
                    } else if (resultBean.getIs_refund() == 1) {
                        binding.tvApplyExitAmount.setVisibility(View.VISIBLE);//申请退款
                        binding.tvCancelApply.setVisibility(View.GONE);
                        binding.tvSalesRefundProgress.setVisibility(View.GONE);
                    } else if (resultBean.getIs_refund() == 2) { //已存在申请
                        binding.tvApplyExitAmount.setVisibility(View.GONE);//申请退款
                        binding.tvCancelApply.setVisibility(View.VISIBLE);//取消申请
                        binding.tvSalesRefundProgress.setVisibility(View.VISIBLE);//申请退款进度
                        binding.tvSalesRefundProgress.setText(R.string.apply_exitAmount_progress);
                    } else {
                    }
                } else if (resultBean.getOrder_status() == 2) {//已发货（待收货）
                    binding.tvCacelOrder.setVisibility(View.GONE);
                    binding.tvPayOrder.setVisibility(View.GONE);
                    binding.tvConfirmReceipt.setVisibility(View.VISIBLE); //确认收货
                    //是否允许售后
                    if (resultBean.getIs_refund() == 0) {//0：否
                        binding.tvApplyAfterSale.setVisibility(View.GONE);//申请售后
                    } else if (resultBean.getIs_refund() == 1) {
                        binding.tvApplyAfterSale.setVisibility(View.VISIBLE);//申请售后
                        binding.tvApplyExitAmount.setVisibility(View.GONE);//申请退款
                        binding.tvCancelApply.setVisibility(View.GONE);
                        binding.tvSalesRefundProgress.setVisibility(View.GONE);
                    } else if (resultBean.getIs_refund() == 2) { //已存在申请
                        binding.tvApplyExitAmount.setVisibility(View.GONE);//申请退款
                        binding.tvApplyAfterSale.setVisibility(View.GONE);//申请售后
                        binding.tvCancelApply.setVisibility(View.VISIBLE);//取消申请
                        binding.tvSalesRefundProgress.setVisibility(View.VISIBLE);//售后申请进度
                        binding.tvSalesRefundProgress.setText(R.string.sales_refund_progress);
                    } else {
                    }
                } else if (resultBean.getOrder_status() == 3) {
                    binding.tvCacelOrder.setVisibility(View.GONE);
                    binding.tvPayOrder.setVisibility(View.GONE);
                    binding.tvDeleteOrder.setVisibility(View.VISIBLE); //删除订单
                    binding.tvEvaluaion.setVisibility(View.VISIBLE); //评价
                    //closecomment  是否允许评价。 0：允许 1：不允许
                    if (resultBean.getClosecomment() == 0) {
                        binding.tvEvaluaion.setVisibility(View.VISIBLE);
                        if (resultBean.getIscomment() == 0) {//iscomment 评价状态 0：可评价 1：可追加评价 2：已评价
                            binding.tvEvaluaion.setText("评价");
                            // TODO: 2020/1/15  skip  评价页
                        } else if (resultBean.getIscomment() == 1) {//追评
                            binding.tvEvaluaion.setText("追加评价");
                            // TODO: 2020/1/15  skip  评价页
                        } else if (resultBean.getIscomment() == 2) {//已评价
                            binding.tvEvaluaion.setText("已评价");
                            // TODO: 2020/1/15  skip  评价页
                        } else {
                        }
                    } else {
                        binding.tvEvaluaion.setVisibility(View.GONE);
                    }
                    //是否允许售后
                    if (resultBean.getIs_refund() == 0) {//0：否
                        binding.tvApplyAfterSale.setVisibility(View.GONE);//申请售后
                    } else if (resultBean.getIs_refund() == 1) {
                        binding.tvApplyAfterSale.setVisibility(View.VISIBLE);//申请售后
                        binding.tvApplyExitAmount.setVisibility(View.GONE);//申请退款
                        binding.tvCancelApply.setVisibility(View.GONE);
                        binding.tvSalesRefundProgress.setVisibility(View.GONE);
                    } else if (resultBean.getIs_refund() == 2) { //已存在申请
                        binding.tvApplyExitAmount.setVisibility(View.GONE);//申请退款
                        binding.tvApplyAfterSale.setVisibility(View.GONE);//申请售后
                        binding.tvCancelApply.setVisibility(View.VISIBLE);//取消申请
                        binding.tvSalesRefundProgress.setVisibility(View.VISIBLE);//售后申请进度
                        binding.tvSalesRefundProgress.setText(R.string.sales_refund_progress);
                    } else {
                    }
                } else {
                }
                //是追评还是评价
                int iscomment = resultBean.getIscomment();
                if (iscomment == 0) {//评价
                    isEvaluation = 0;
                } else if (iscomment == 1) { //追加评价
                    isEvaluation = 1;
                } else {
                    isEvaluation = 2;
                }
                viewModel.setIsEvaluation(isEvaluation);

            }
        });

        //取消订单
        viewModel.uc.cancleOrderLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Messenger.getDefault().send("cancle_order", OrderDetailViewModel.TOKEN_ORDERDETAILVIEWMODEL_REFRESH);  //发送刷新我的订单列表数据
                finish();
            }
        });
        //删除订单
        viewModel.uc.deleteOrderLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Messenger.getDefault().send("delete_order", OrderDetailViewModel.TOKEN_ORDERDETAILVIEWMODEL_REFRESH);  //发送刷新我的订单列表数据
                finish();
            }
        });
        //确认收货
        viewModel.uc.confirmReceiptLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Messenger.getDefault().send("confirm_receipt", OrderDetailViewModel.TOKEN_ORDERDETAILVIEWMODEL_REFRESH);  //发送刷新我的订单列表数据
                finish();
            }
        });

        //取消申请
        viewModel.uc.cancleApplyLiveData.observe(this, new Observer<CancelRefundEntity>() {
            @Override
            public void onChanged(@Nullable CancelRefundEntity cancelRefundEntity) {
                viewModel.requestData(order_id); //刷新详情页数据
            }
        });

    }

    private void initRecycleView(OrderDetailEntity.ResultBean resultList, final List<OrderDetailEntity.ResultBean.DataBean> data) {
        binding.recyView.setLayoutManager(new LinearLayoutManager(this));
//        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
//        binding.recyView.addItemDecoration(divider);
        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(mContext, viewModel, data, null);
        binding.recyView.setAdapter(orderDetailAdapter);
        double dfPrice = orderDetailAdapter.getDfPrice();
        View footerView = LayoutInflater.from(mContext).inflate(R.layout.order_detail_item_footer, null);
        initFooterData(footerView, resultList, dfPrice);
        orderDetailAdapter.addFooterView(footerView);
    }

    private void initFooterData(View footerView, OrderDetailEntity.ResultBean resultBean, double dfPrice) {
        TextView tv_merch_subtotal = footerView.findViewById(R.id.tv_merch_subtotal);
        RecyclerView recycle_calcData = footerView.findViewById(R.id.recycle_calcData);
        TextView tv_ealpayment = footerView.findViewById(R.id.tv_ealpayment);
        TextView tv_order_number = footerView.findViewById(R.id.tv_order_number);
        TextView tv_create_time = footerView.findViewById(R.id.tv_create_time);
        TextView tv_pay_time_tip = footerView.findViewById(R.id.tv_pay_time_tip);
        TextView tv_pay_time = footerView.findViewById(R.id.tv_pay_time);
        TextView tv_send_time_tip = footerView.findViewById(R.id.tv_send_time_tip);
        TextView tv_send_time = footerView.findViewById(R.id.tv_send_time);
        TextView tv_finish_time_tip = footerView.findViewById(R.id.tv_finish_time_tip);
        TextView tv_finish_time = footerView.findViewById(R.id.tv_finish_time);
        TextView tv_cantactMerchant_new = footerView.findViewById(R.id.tv_cantactMerchant_new);
        //setdata
        /*各种优惠、抵扣、运费等数据包信息 */
        List<OrderDetailEntity.ResultBean.CalcDataBean> calc_data = resultBean.getCalc_data();
        initRecycleCalcData(recycle_calcData, calc_data,tv_ealpayment);
        tv_merch_subtotal.setText("¥".concat(String.valueOf(resultBean.getOrder_price()))); //商品小计
//        double realPrice = dfPrice - calcDataPrice;
        setRealPrice(resultBean.getOrder_price());

//        tv_ealpayment.setText("¥".concat(realFinalPrice));  //实付金额

        tv_order_number.setText(resultBean.getOrder_sn());
        OrderDetailEntity.ResultBean.TimesBean times = resultBean.getTimes();
        if (times != null) {
            tv_create_time.setText(times.getCreate_time());
            if (!TextUtils.isEmpty(times.getPay_time())) {
                tv_pay_time_tip.setVisibility(View.VISIBLE);
                tv_pay_time.setText(times.getPay_time());
            } else {
                tv_pay_time_tip.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(times.getSend_time())) {
                tv_send_time_tip.setVisibility(View.VISIBLE);
                tv_send_time.setText(times.getSend_time());
            } else {
                tv_send_time_tip.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(times.getFinish_time())) {
                tv_finish_time_tip.setVisibility(View.VISIBLE);
                tv_finish_time.setText(times.getFinish_time());
            } else {
                tv_finish_time_tip.setVisibility(View.GONE);
            }
        }
        //联系客服
        if (resultBean.getOrder_status() == 0){
            tv_cantactMerchant_new.setVisibility(View.GONE);
            tv_cantactMerchant_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //打开客服聊天
                    String chat_id = resultBean.getChat_id();
                    Log.d(TAG,"chat_id：=====" + chat_id);
                    if (!TextUtils.isEmpty(chat_id)){

                        //打开先先关闭，防止已经打开，打开多个会话页面，聊天数据会话错乱
                        AppManager.getAppManager().finishActivity(OpenChatActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.GOODS_KEY_GID, "");
                        bundle.putString(Constants.GOODS_KEY_CHAT_NAME, "");
                        bundle.putString(Constants.GOODS_KEY_CHAT_ID, chat_id);
                        bundle.putString(Constants.GOODS_KEY_CHAT_LTYPE, "member");
                        bundle.putString(Constants.SP.ACTIVITY_OPEN_FLAG, "order_detail");
                        startActivity(OpenChatActivity.class, bundle);
                    }
                }
            });
        }else {
            tv_cantactMerchant_new.setVisibility(View.GONE);
        }

    }

    private void setRealPrice(String price) {
        this.realPrice = price;
    }

    public String getRealPrice() {
        return realPrice;
    }


    /**
     * 快递信息
     *
     * @param express
     */
    private void initExpressRecycleView(final List<OrderDetailEntity.ResultBean.ExpressBean> express) {
        if (express != null && express.size() > 0) {
            binding.recyViewExpress.setVisibility(View.VISIBLE);
            binding.recyViewExpress.setLayoutManager(new LinearLayoutManager(mContext));
            OrderDetailExpressAdapter adapter = new OrderDetailExpressAdapter(mContext, express, R.layout.item_order_goods_recy_item_express);
            binding.recyViewExpress.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int viewType, int position) {
                    OrderDetailEntity.ResultBean.ExpressBean expressBean = express.get(position);
                    String sendtype = expressBean.getSendtype();
                    String bundle = expressBean.getBundle();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(Constants.MyOrder,resultList.getOrder_id());
                    bundle1.putString(Constants.ORDER_SEND_TYPE,sendtype);
                    bundle1.putString(Constants.ORDER_BUNDLE,bundle);
                    startContainerActivity(LogisticInfoFragment.class.getCanonicalName(),bundle1);
                    return;
                }
            });
        } else {
            binding.recyViewExpress.setVisibility(View.GONE);
        }
    }


    /**
     * 各种优惠、抵扣、运费等数据包信息
     *  @param recyclerView
     * @param calc_data
     * @param tv_ealpayment
     */
    private void initRecycleCalcData(RecyclerView recyclerView, List<OrderDetailEntity.ResultBean.CalcDataBean> calc_data, TextView tv_ealpayment) {
        if (calc_data != null && calc_data.size() > 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            OrderDetailCalcDataAdapter adapter = new OrderDetailCalcDataAdapter(mContext, calc_data, resultList.getOrder_price(),
                    R.layout.item_order_goods_recy_item_calc_data);
            recyclerView.setAdapter(adapter);
            KLog.a("商品的原价"+resultList.getOrder_price());
            calcDataPrice = adapter.getPrices();
            if (calcDataPrice !=0){
                String money = MoneyFormatUtils.keepTwoDecimal(calcDataPrice); //最终的价格
                KLog.a("商品优惠的后的金额"+money);
                tv_ealpayment.setText(money);  //实付金额
            }
        } else {
        }
    }

}
