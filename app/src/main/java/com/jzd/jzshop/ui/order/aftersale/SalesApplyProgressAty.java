package com.jzd.jzshop.ui.order.aftersale;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivitySalesApplyProgressBinding;
import com.jzd.jzshop.entity.CancelRefundEntity;
import com.jzd.jzshop.entity.OrderRefundProgressEntity;
import com.jzd.jzshop.ui.order.orderdetail.OrderDetailViewModel;
import com.jzd.jzshop.utils.Constants;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * @author LXB
 * @description: 售后申请进度
 * @date :2020/1/16 11:33
 */
public class SalesApplyProgressAty extends BaseActivity<ActivitySalesApplyProgressBinding, SalesApplyProgressViewModel> implements View.OnClickListener {
    private String title,order_id;
    private OrderRefundProgressEntity.ResultBean resultList;
    private OrderRefundProgressEntity.ResultBean.ExpressInfoBean express_info;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_sales_apply_progress;
    }

    @Override
    public int initVariableId() {
        return BR.aftersaleProgVM;
    }

    @Override
    public SalesApplyProgressViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SalesApplyProgressViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        viewModel.initToolBar(getResources().getString(R.string.apply_after_sale));
        viewModel.setBinding(mContext, binding);
        viewModel.requestData(order_id);
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString(Constants.BUNDLE_KEY_TITLE);
            order_id = bundle.getString(Constants.ORDER_ID);
            viewModel.initToolBar(title);
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<OrderRefundProgressEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable OrderRefundProgressEntity.ResultBean resultBean) {
                resultList = resultBean;
                int status = resultBean.getStatus();
                if (status == 1) {//退款
                    title = getResources().getString(R.string.apply_exitAmount);
                } else
                    { title = getResources().getString(R.string.apply_after_sale);
                }
                //申请处理状态(换货状态)
                int refund_status = resultList.getRefund_status();
                if (refund_status ==0){//申请中
                    binding.tvHandleResult.setText(R.string.wait_merch_sales_apply);
                    binding.constrlReturnGoods.setVisibility(View.GONE);
                    binding.constrlReturnGoodState.setVisibility(View.GONE);
                    binding.tvSubmitApply.setVisibility(View.VISIBLE);  //修改申请
                    binding.tvCancel.setVisibility(View.VISIBLE);

                    binding.tvFillExpressBill.setVisibility(View.GONE);
                    binding.tvConfirmReceiptExchange.setVisibility(View.GONE);
                    binding.tvExchangeLogistics.setVisibility(View.GONE);

                }else if (refund_status == 3){ //待用户寄回商品（需填写快递单号）
                    binding.constrlReturnGoods.setVisibility(View.VISIBLE);
                    binding.constrlReturnGoodState.setVisibility(View.VISIBLE);  //快递单号module 是否显示
                    binding.tvReturnGoodState.setText(R.string.good_state_three);
                    binding.tvFillExpressBill.setVisibility(View.VISIBLE);
                    binding.tvFillExpressBill.setText(R.string.fill_express_bill); //填写快递单号
                    binding.ivArrow1.setVisibility(View.VISIBLE);
                    binding.tvCancel.setVisibility(View.VISIBLE);
                    binding.constrlReturnGoodStateChild.setVisibility(View.GONE);   //快递单号 child module 是否显示
                    binding.viewSpace.setVisibility(View.GONE);
                }else if (refund_status == 4){//等待商家确认
                    binding.constrlReturnGoods.setVisibility(View.VISIBLE);
                    binding.constrlReturnGoodState.setVisibility(View.VISIBLE);
                    binding.constrlReturnGoodState.setEnabled(false);
                    binding.tvReturnGoodState.setEnabled(false);
                    binding.tvReturnGoodState.setText(R.string.good_state_four);
                    binding.ivArrow1.setVisibility(View.GONE);
                    binding.tvFillExpressBill.setVisibility(View.VISIBLE);
                    binding.tvFillExpressBill.setText(R.string.modify_express_bill); //修改快递单号
                    binding.tvCancel.setVisibility(View.VISIBLE);
                    binding.constrlReturnGoodStateChild.setVisibility(View.GONE);   //快递单号 child module 是否显示
                    binding.viewSpace.setVisibility(View.GONE);
                }else if (refund_status == 5){ //商家已经发货
                    binding.constrlReturnGoods.setVisibility(View.VISIBLE);
                    binding.constrlReturnGoodState.setVisibility(View.VISIBLE);
                    binding.ivArrow1.setVisibility(View.GONE);
                    binding.constrlReturnGoodState.setEnabled(false);
                    binding.tvReturnGoodState.setEnabled(false);
                    binding.constrlReturnGoodStateChild.setVisibility(View.VISIBLE);   //快递单号 child module 是否显示
                    binding.viewSpace.setVisibility(View.VISIBLE);
                    binding.tvReturnGoodState.setText(R.string.good_state_five);
                    binding.tvFillExpressBill.setVisibility(View.GONE);
                    binding.tvCancel.setVisibility(View.GONE);
                    binding.tvConfirmReceiptExchange.setVisibility(View.VISIBLE); //确认收到换货物品
                    binding.tvExchangeLogistics.setVisibility(View.VISIBLE); //查看换货物流
                }else {
                    binding.tvHandleResult.setText(R.string.get_past_merch_sales_apply);
                }
                List<String> process = resultList.getProcess();    //处理流程
                setProcessData(status,process);
                //退换货地址
                OrderRefundProgressEntity.ResultBean.RefundAddrBean refund_addr = resultList.getRefund_addr();
                setAddressData(refund_addr);
                //卖家留言
                if (!TextUtils.isEmpty(resultList.getRefund_msg())){
                    binding.tvSellerMessage.setVisibility(View.VISIBLE);
                    binding.tvSellerMessage.setText(resultList.getRefund_msg());
                }else {
                    binding.tvSellerMessage.setVisibility(View.GONE);
                }
                if (refund_status == 5){ // 商家 商家已发货
                    //物流信息合集
                    express_info = resultList.getExpress_info();
                    setExpressData();
                }else if (refund_status == 3 || refund_status == 4){ //

                } else {}
                // TODO: 2020/1/18
                //已申请信息集合
                OrderRefundProgressEntity.ResultBean.RefundInfoBean refund_info = resultList.getRefund_info();
                setRefundInfo(refund_info);
                //快递数据集合
                List<OrderRefundProgressEntity.ResultBean.ExpressDataBean> express_data = resultList.getExpress_data();
                // TODO: 2020/1/18  

            }
        });

        binding.tvReturnGoodState.setOnClickListener(this);
        binding.ivArrow1.setOnClickListener(this);

        viewModel.uc.cancleApplyLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Messenger.getDefault().send("cancel_apply", OrderDetailViewModel.TOKEN_VIEWMODEL_CANCLE_APPLY_REFRESH);  //发送刷新
                finish();
            }
        });

        //修改售后申请
        Messenger.getDefault().register(this, AfterSaleViewModel.TOKEN_AFTERSALEVIEWMODEL_MODIFY_APPLY_REFRESH, String.class, new BindingConsumer<String>() {
            @Override
            public void call(String state) {//刷新
                if (state.equals("modify_apply")) {
                    viewModel.requestData(order_id);
                } else {
                }
            }
        });
        //提交快递单号
        Messenger.getDefault().register(this, FillExpressBillViewModel.TOKEN_VIEWMODEL_SUBMIT_EXPRESS_REFRESH, String.class, new BindingConsumer<String>() {
            @Override
            public void call(String state) {//刷新
                if (state.equals("submit_express")){
                    viewModel.requestData(order_id);
                } else {
                }
            }
        });

        //确认收到换货物品
        viewModel.uc.refundReceiveLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Messenger.getDefault().send("refund_receive", OrderDetailViewModel.TOKEN_VIEWMODEL_REFUND_RECEIVE_REFRESH);  //发送刷新
                finish();
            }
        });
    }

    /**
     * 物流信息
     */
    private void setExpressData() {
        if (express_info != null) {
            binding.tvReturnGoodsExpressCompay.setText(express_info.getRexpresscom());
            binding.tvReturnGoodsExpressSn.setText(express_info.getRexpresssn());
        }

    }

    /**
     * 处理流程
     * @param status
     * @param process
     */
    private void setProcessData(int status, List<String> process) {
        if (process != null && process.size() > 0) {
            binding.tvHandleDesc.setVisibility(View.VISIBLE);
            if (status == 1){//退款流程
                binding.tvHandleResultTip.setText("退款申请流程:\n");
            }else {//退款流程
                binding.tvHandleResultTip.setText("换货申请流程:\n");
            }
            binding.linHandleResult.removeAllViews();//清空布局
            for (int i = 0; i < process.size(); i++) {
                TextView textView = new TextView(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(layoutParams);
                textView.setPadding(20, 0, 20, 0);
                textView.setText(i+1 +"、"+  process.get(i)+"\n");
//                textView.setTextColor(getResources().getColor(R.color.black));
                textView.setTextSize(14);
                textView.setLineSpacing(0.05f,1.5f);
                binding.linHandleResult.addView(textView);
            }
        }else {
            binding.tvHandleDesc.setVisibility(View.GONE);
        }
    }

    /**
     * 退换货地址信息
     * @param refund_addr
     */
    private void setAddressData(OrderRefundProgressEntity.ResultBean.RefundAddrBean refund_addr) {
        if (refund_addr != null) {
            if (!TextUtils.isEmpty(refund_addr.getAddress()) && !TextUtils.isEmpty(refund_addr.getRealname())
                    && !TextUtils.isEmpty(refund_addr.getMobile())) {
                binding.tvReturnGoodsAddress.setText(refund_addr.getAddress().concat("\n" +refund_addr.getRealname()).concat(refund_addr.getMobile()));
            }
        }
    }

    /**
     * 已申请信息集合
     * @param refund_info
     */
    private void setRefundInfo(OrderRefundProgressEntity.ResultBean.RefundInfoBean refund_info) {
        if (refund_info != null) {
            if (!TextUtils.isEmpty(refund_info.getRefund_typestr())){
                binding.tvHandleWay.setVisibility(View.VISIBLE);
                binding.tvHandleWay.setText(refund_info.getRefund_typestr());
            }else{
                binding.tvHandleWay.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(refund_info.getRefund_price())){
                binding.tvAmount.setVisibility(View.VISIBLE);
                binding.tvAmount.setText("¥ " + refund_info.getRefund_price());
            }else{
                binding.tvAmount.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(refund_info.getReason())){
                binding.tvReason.setVisibility(View.VISIBLE);
                binding.tvReason.setText(refund_info.getReason());
            }else{
                binding.tvReason.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(refund_info.getContent())){
                binding.tvExplain.setVisibility(View.VISIBLE);
                binding.tvExplain.setText(refund_info.getContent());
            }else{
                binding.tvExplain.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(refund_info.getApply_time())){
                binding.tvTime.setVisibility(View.VISIBLE);
                binding.tvTime.setText(refund_info.getApply_time());
            }else{
                binding.tvTime.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_ReturnGoodState: //填写快递单号
            case R.id.iv_arrow1:
                int refund_status = resultList.getRefund_status();
                if (refund_status == 0){
                    Intent intent = new Intent(mContext,FillExpressBillAty.class);
                    Bundle bundle = intent.getExtras();
                    bundle.putSerializable(Constants.BUNDLE_KEY_EXPRESS_INFO,express_info);
                    startActivityForResult(intent,Constants.QUEST_CODE.REQUESTCODE_SALES_APPLY_PROGRESS);
                }else {}
                break;
        }
    }
}
